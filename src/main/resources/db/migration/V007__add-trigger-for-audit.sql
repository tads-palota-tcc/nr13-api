create or replace function audit.func_audit()
	returns trigger
	language plpgsql
	security definer
as
$$
declare
	-- "tg_relname" contém o nome da tabela que disparou a trigger. Concatena com '_audit' para formar o nome da
    -- tabela que será criada para salvar os logs.
	f_table_name_audit 	constant text := tg_relname || '_audit';
	f_tg_op				constant text := substring(tg_op, 1, 1);
	f_json				constant text := case
											when f_tg_op = 'D'
												then row_to_json(old)
											else row_to_json(new)
										end;
	is_new_row			constant boolean := case when f_tg_op = 'D' then false else true end;
begin
	-- Sempre será executado o create, mas caso a tabela já tiver sido criada anteriormente, nada será feito.
	execute format(
		'create table if not exists audit.%I (
			id						bigserial primary key,
			date_time_utc timestamp	with time zone default now(),
			operation				varchar(1),
			query					text,
			pg_username				text,
			pg_application_name     text,
			row_log                 jsonb,
			is_new_row              boolean
		);', f_table_name_audit);

	execute format(
            'insert into audit.%I (operation, query, row_log, is_new_row, pg_username, pg_application_name)
             values (%L, %L, %L, %L, %L, %L);',
            f_table_name_audit,
            f_tg_op,
            current_query(),
            f_json,
            is_new_row,
            session_user,
            (select current_setting('application_name')));
    return null;
end;
$$;

create trigger tg_func_audit_tb_plants
	after insert or update or delete
	on nr13_api.tb_plants
	for each row execute procedure audit.func_audit();