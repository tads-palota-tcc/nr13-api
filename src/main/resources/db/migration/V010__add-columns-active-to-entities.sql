alter table nr13_api.tb_areas add column active boolean default true;
update nr13_api.tb_areas set active = true;

alter table nr13_api.tb_equipments add column active boolean default true;
update nr13_api.tb_equipments set active = true;

alter table nr13_api.tb_devices add column active boolean default true;
update nr13_api.tb_devices set active = true;
