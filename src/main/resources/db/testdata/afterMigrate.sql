drop table if exists audit.tb_plants_audit;

delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_plants;
delete from nr13_api.tb_users;

alter sequence nr13_api.tb_users_id_seq restart with 100000;
alter sequence nr13_api.tb_plants_id_seq restart with 1;

insert into nr13_api.tb_users (email) values ('alexandre.palota@gmail.com');

insert into
        nr13_api.tb_plants (code, name, street_name, number, complement, neighborhood, city, state, zip_code, active, updated_by)
    values
        ('RGD1', 'Unidade Rio Grande 1', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('PEL1', 'Unidade Pelotas 1', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('POA1', 'Unidade Porto Alegre 1', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000);