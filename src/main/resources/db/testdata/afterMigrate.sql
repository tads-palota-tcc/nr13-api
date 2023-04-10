delete from nr13_api.tb_users;

alter sequence nr13_api.tb_users_id_seq restart with 10000;

insert into nr13_api.tb_users (email) values ('alexandre.palota@gmail.com');