alter table nr13_api.tb_calibrations add constraint fk_calibrations_device_id foreign key (device_id) references nr13_api.tb_devices (id);
alter table nr13_api.tb_inspections add constraint fk_inspections_id foreign key (id) references nr13_api.tb_interventions (id);
alter table nr13_api.tb_devices add constraint fk_devices_plant_id foreign key (plant_id) references nr13_api.tb_plants (id);
