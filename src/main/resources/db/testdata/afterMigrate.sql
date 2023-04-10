delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_plants;
delete from nr13_api.tb_users;

alter sequence nr13_api.tb_users_id_seq restart with 10000;
alter sequence nr13_api.tb_plants_id_seq restart with 1;

insert into nr13_api.tb_users (email) values ('alexandre.palota@gmail.com');