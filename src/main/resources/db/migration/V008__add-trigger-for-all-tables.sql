

create trigger tg_func_audit_tb_areas
	after insert or update or delete
	on nr13_api.tb_areas
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_equipments
	after insert or update or delete
	on nr13_api.tb_equipments
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_devices
	after insert or update or delete
	on nr13_api.tb_devices
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_pressure_safety_valves
	after insert or update or delete
	on nr13_api.tb_pressure_safety_valves
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_pressure_indicators
	after insert or update or delete
	on nr13_api.tb_pressure_indicators
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_interventions
	after insert or update or delete
	on nr13_api.tb_interventions
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_calibrations
	after insert or update or delete
	on nr13_api.tb_calibrations
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_inspections
	after insert or update or delete
	on nr13_api.tb_inspections
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_applicable_tests
	after insert or update or delete
	on nr13_api.tb_applicable_tests
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_pendencies
	after insert or update or delete
	on nr13_api.tb_pendencies
	for each row execute procedure audit.func_audit();

create trigger tg_func_audit_tb_files
	after insert or update or delete
	on nr13_api.tb_files
	for each row execute procedure audit.func_audit();