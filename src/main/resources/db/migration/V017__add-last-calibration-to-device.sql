alter table nr13_api.tb_devices add column last_calibration_date date;

update
	nr13_api.tb_devices
set
	last_calibration_date = (
	select
		max(i.execution_date)
	from
		nr13_api.tb_calibrations c
	join nr13_api.tb_interventions i on
		c.id = i.id
	join nr13_api.tb_devices d on
		d.id = c.device_id
	where
		d.id = nr13_api.tb_devices.id);