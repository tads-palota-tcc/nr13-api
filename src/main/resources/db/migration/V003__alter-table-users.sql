drop table nr13_api.tb_user_roles;

alter table nr13_api.tb_users drop column name;
alter table nr13_api.tb_users drop column email;
alter table nr13_api.tb_users drop column password;
alter table nr13_api.tb_users drop column active;