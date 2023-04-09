create table nr13_api.tb_plants (
    id bigserial primary key,
    code varchar(10) not null,
    name varchar(40) not null,
    street_name varchar(40),
    number varchar(10),
    complement varchar(20),
    neighborhood varchar(40),
    zip_code varchar(8),
    city varchar(40),
    state char(2),
    active boolean default true,
    updated_by bigint not null,
    constraint fk_plants_updated_by foreign key(updated_by) references nr13_api.tb_users(id)
);

create table nr13_api.tb_user_plants (
    user_id bigint not null,
    plant_id bigint not null,
    constraint fk_user_plants_user_id foreign key (user_id) references nr13_api.tb_users(id),
    constraint fk_user_plants_plant_id foreign key (plant_id) references nr13_api.tb_plants(id),
    primary key (user_id, plant_id)
);

create table nr13_api.tb_areas (
    id bigserial primary key,
    code varchar(20) not null,
    name varchar(40) not null,
    plant_id bigint not null,
    updated_by bigint not null,
    constraint fk_areas_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_areas_plant_id foreign key (plant_id) references nr13_api.tb_plants(id)
);

create table nr13_api.tb_files (
    id bigserial primary key,
    name varchar(40) not null,
    description varchar(100),
    type varchar(10),
    url varchar(255) not null,
    updated_by bigint not null,
    constraint fk_files_updated_by foreign key (updated_by) references nr13_api.tb_users(id)
);

create table nr13_api.tb_equipments (
    id bigserial primary key,
    tag varchar(20) not null,
    area_id bigint not null,
    name varchar(40) not null,
    hydrostatic_test_pressure decimal (7, 2),
    max_operation_pressure decimal(7, 2) not null,
    max_allowed_work_pressure decimal(7, 2) not null,
    manufacturer varchar(40),
    model varchar(20),
    serial_number varchar(40),
    manufacture_year varchar(4),
    project_code varchar(20),
    project_code_edition_year varchar(4),
    diameter decimal(7, 2) not null,
    volume decimal(8, 2) not null,
    fluid_class varchar(5) not null,
    category varchar(5),
    has_two_wide_exits boolean default false,
    has_ventilation boolean default false,
    has_maintenance_access boolean default false,
    has_local_lighting boolean default false,
    has_emergency_lighting boolean default false,
    has_identification_tag boolean default false,
    has_category_tag boolean default false,
    has_databook boolean default false,
    has_safety_journal boolean default false,
    has_installation_project boolean default false,
    databook_file_id bigint,
    safety_journal_file_id bigint,
    installation_project_file_id bigint,
    updated_by bigint not null,
    constraint fk_equipments_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_equipments_area_id foreign key (area_id) references nr13_api.tb_areas(id),
    constraint fk_equipments_databook_file_id foreign key (databook_file_id) references nr13_api.tb_files(id),
    constraint fk_equipments_safety_journal_file_id foreign key (safety_journal_file_id) references nr13_api.tb_files(id),
    constraint fk_equipments_installation_project_file_id foreign key (installation_project_file_id) references nr13_api.tb_files(id)
);

create table nr13_api.tb_devices (
    id bigserial primary key,
    tag varchar(20) not null,
    manufacturer varchar(40),
    model varchar(40),
    plant_id bigint not null,
    equipment_id bigint,
    updated_by bigint not null,
    constraint fk_devices_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_devices_equipment_id foreign key (equipment_id) references nr13_api.tb_equipments(id)
);

create table nr13_api.tb_pressure_safety_valves (
    id bigint primary key,
    size varchar(20),
    opening_pressure decimal(7, 2),
    closing_pressure decimal(7, 2),
    updated_by bigint not null,
    constraint fk_pressure_safety_valves_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_pressure_safety_valves_id foreign key (id) references nr13_api.tb_devices(id)
);

create table nr13_api.tb_pressure_indicators (
    id bigint primary key,
    gauge_size varchar(10),
    connection_size varchar(10),
    min_gauge decimal(7, 2),
    max_gauge decimal(7, 2),
    updated_by bigint not null,
    constraint fk_pressure_indicators_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_pressure_indicators_id foreign key (id) references nr13_api.tb_devices(id)
);

create table nr13_api.tb_interventions (
    id bigserial primary key,
    report_number varchar(10),
    executor_company varchar(40) not null,
    execution_date date not null,
    comments text,
    status varchar(20),
    file_id bigint,
    updated_by bigint not null,
    constraint fk_interventions_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_interventions_file_id foreign key (file_id) references nr13_api.tb_files(id)
);

create table nr13_api.tb_calibrations (
    id bigint primary key,
    device_id bigint not null,
    updated_by bigint not null,
    constraint fk_calibrations_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_calibrations_id foreign key (id) references nr13_api.tb_interventions(id)
);

create table nr13_api.tb_tests (
    id bigserial primary key,
    name varchar(40) not null,
    description text,
    frequency integer not null,
    frequency_type varchar(20) not null
);

create table nr13_api.tb_applicable_tests (
    equipment_id bigint not null,
    test_id bigint not null,
    frequency integer,
    frequency_type varchar(20),
    updated_by bigint not null,
    constraint fk_applicable_tests_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_applicable_tests_equipment_id foreign key (equipment_id) references nr13_api.tb_equipments(id),
    constraint fk_applicable_tests_test_id foreign key (test_id) references nr13_api.tb_tests(id),
    primary key(equipment_id, test_id)
);

create table nr13_api.tb_inspections (
    id bigint primary key,
    equipment_id bigint not null,
    test_id bigint not null,
    updated_by bigint not null,
    constraint fk_inspections_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_inspections_equipment_id_test_id foreign key (equipment_id, test_id) references nr13_api.tb_applicable_tests(equipment_id, test_id)
);

create table nr13_api.tb_pendencies (
    id bigserial primary key,
    inspection_id bigint not null,
    author bigint not null,
    type varchar(20) not null,
    status varchar(20) not null,
    responsible bigint not null,
    description text not null,
    action text not null,
    opened_at date not null,
    dead_line date not null,
    updated_by bigint not null,
    constraint fk_pendencies_updated_by foreign key (updated_by) references nr13_api.tb_users(id),
    constraint fk_pendencies_inspection_id foreign key (inspection_id) references nr13_api.tb_inspections(id),
    constraint fk_pendencies_author foreign key (author) references nr13_api.tb_users(id),
    constraint fk_pendencies_responsible foreign key (responsible) references nr13_api.tb_users(id)
);

create table nr13_api.tb_pendency_images (
    id bigserial primary key,
    title varchar(40),
    description varchar(255),
    file_id bigint not null,
    pendency_id bigint not null,
    constraint fk_pendency_images_file_id foreign key (file_id) references nr13_api.tb_files(id),
    constraint fk_pendency_images_pendency_id foreign key (pendency_id) references nr13_api.tb_pendencies(id)
);