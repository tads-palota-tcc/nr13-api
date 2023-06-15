alter table nr13_api.tb_applicable_tests add column last_cost decimal(10, 2) default '0.0';
update nr13_api.tb_applicable_tests set last_cost = '0.0' where last_cost is null;
