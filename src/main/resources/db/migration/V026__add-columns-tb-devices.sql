alter table nr13_api.tb_devices add column last_calibration_cost decimal(10, 2) default '0.0';
update nr13_api.tb_devices set last_calibration_cost = '0.0' where last_calibration_cost is null;
