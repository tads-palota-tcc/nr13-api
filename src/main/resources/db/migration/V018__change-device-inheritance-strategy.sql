drop table nr13_api.tb_pressure_indicators;
drop table nr13_api.tb_pressure_safety_valves;

alter table nr13_api.tb_devices add column type varchar(10);
alter table nr13_api.tb_devices add column gauge_size varchar(10);
alter table nr13_api.tb_devices add column connection_size varchar(10);
alter table nr13_api.tb_devices add column min_gauge decimal(7, 2);
alter table nr13_api.tb_devices add column max_gauge decimal(7, 2);
alter table nr13_api.tb_devices add column body_size varchar(20);
alter table nr13_api.tb_devices add column opening_pressure decimal(7, 2);
alter table nr13_api.tb_devices add column closing_pressure decimal(7, 2);