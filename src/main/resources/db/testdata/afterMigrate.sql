drop table if exists audit.tb_plants_audit;
drop table if exists audit.tb_areas_audit;
drop table if exists audit.tb_equipments_audit;
drop table if exists audit.tb_devices_autit;
drop table if exists audit.tb_interventions_audit;
drop table if exists audit.tb_calibrations_audit;
drop table if exists audit.tb_inspections_audit;
drop table if exists audit.tb_pendencies;

delete from nr13_api.tb_pendencies;
delete from nr13_api.tb_calibrations;
delete from nr13_api.tb_inspections;
delete from nr13_api.tb_interventions;
delete from nr13_api.tb_applicable_tests;
delete from nr13_api.tb_files;
delete from nr13_api.tb_user_groups;
delete from nr13_api.tb_group_permissions;
delete from nr13_api.tb_groups;
delete from nr13_api.tb_permissions;
delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_devices;
delete from nr13_api.tb_equipments;
delete from nr13_api.tb_tests;
delete from nr13_api.tb_areas;
delete from nr13_api.tb_plants;
delete from nr13_api.tb_users;
delete from nr13_api.tb_files;

alter sequence nr13_api.tb_pendencies_id_seq restart with 1;
alter sequence nr13_api.tb_interventions_id_seq restart with 1;
alter sequence nr13_api.tb_files_id_seq restart with 1;
alter sequence nr13_api.tb_users_id_seq restart with 100000;
alter sequence nr13_api.tb_plants_id_seq restart with 1;
alter sequence nr13_api.tb_areas_id_seq restart with 1;
alter sequence nr13_api.tb_equipments_id_seq restart with 1;
alter sequence nr13_api.tb_groups_id_seq restart with 1;
alter sequence nr13_api.tb_permissions_id_seq restart with 1;
alter sequence nr13_api.tb_devices_id_seq restart with 1;
alter sequence nr13_api.tb_files_id_seq restart with 1;
alter sequence nr13_api.tb_tests_id_seq restart with 1;

insert into nr13_api.tb_groups(name) values ('Admin'), ('Inspetor'), ('Usuário');
insert into
        nr13_api.tb_permissions(name)
    values
        ('USER_WRITE'),         -- 1
        ('USER_READ'),          -- 2
        ('EQUIPMENT_WRITE'),    -- 3
        ('EQUIPMENT_READ'),     -- 4
        ('INSTALATION_WRITE'),  -- 5
        ('INSTALATION_READ'),   -- 6
        ('INTERVENTION_WRITE'), -- 7
        ('INTERVENTION_READ'),  -- 8
        ('ACTIONS_WRITE'),      -- 9
        ('ACTIONS_READ'),       -- 10
        ('REPORTS_READ');       -- 11

insert into nr13_api.tb_group_permissions(group_id, permission_id)
    values
        (1, 2), (1, 2), (1, 5), (1, 6),
        (2, 2), (2, 3), (2, 4), (2, 7), (2, 8), (2, 9), (2, 10), (2, 11),
        (3, 2), (3, 4), (3, 6), (3, 8), (3, 9), (3, 10), (3, 11);

insert into nr13_api.tb_users (name, email, password) values ('Alexandre Palota da Silva', 'alexandre.palota@gmail.com', '$2a$12$VoUaSPN6Axxo2T4HITqRluBfQL6JL1ksP4OPSa1.KYss4j4YjS8Ba');

insert into nr13_api.tb_user_groups (user_id, group_id) values (100000, 1), (100000, 2), (100000, 3);

insert into
        nr13_api.tb_plants (code, name, street_name, number, complement, neighborhood, city, state, zip_code, active, updated_by)
    values
        ('RGD1', 'Unidade Rio Grande 1', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('RGD2', 'Unidade Rio Grande 2', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('RGD3', 'Unidade Rio Grande 3', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('RGD4', 'Unidade Rio Grande 4', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('RGD5', 'Unidade Rio Grande 5', 'Rua Valporto', '1000', null, 'Lar Gaúcho', 'Rio Grande', 'RS', '96200000', 'true', 100000),
        ('PEL1', 'Unidade Pelotas 1', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('PEL2', 'Unidade Pelotas 2', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('PEL3', 'Unidade Pelotas 3', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('PEL4', 'Unidade Pelotas 4', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('PEL5', 'Unidade Pelotas 5', 'Rua General Osório', '1000', null, 'Centro', 'Pelotas', 'RS', '96200000', 'false', 100000),
        ('POA1', 'Unidade Porto Alegre 1', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000),
        ('POA2', 'Unidade Porto Alegre 2', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000),
        ('POA3', 'Unidade Porto Alegre 3', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000),
        ('POA4', 'Unidade Porto Alegre 4', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000),
        ('POA5', 'Unidade Porto Alegre 5', 'Av. Ipiranta', '121', null, 'Centro', 'Porto Alegre', 'RS', '96200000', 'true', 100000);

insert into nr13_api.tb_user_plants (user_id, plant_id) values (100000, 1), (100000, 3), (100000, 6), (100000, 11);

insert into
        nr13_api.tb_areas (code, name, plant_id, updated_by, active)
    values
        ('CMP-01', 'Unidade de compressão de gás', 1, 100000, true),
        ('RFG-01', 'Unidade de refrigeração', 1, 100000, true),
        ('RFG-02', 'Unidade de refrigeração de água', 1, 100000, false),
        ('PNT-01', 'Unidade de pintura', 2, 100000, true);

insert into nr13_api.tb_equipments
            (tag, area_id, name, hydrostatic_test_pressure, max_operation_pressure, max_allowed_work_pressure, diameter, volume, fluid_class, active, updated_by)
    values
        ('CP-001', 1, 'Compressor de ar', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('CP-002', 1, 'Compressor de ar', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000);

insert into
        nr13_api.tb_devices
        (tag, manufacturer, model, plant_id, equipment_id, updated_by, last_calibration_date, active, type, connection_size, body_size, min_gauge, max_gauge, opening_pressure, closing_pressure)
    values
        ('PI-0001', 'ASCO', 'ASCO-PI40', 1, 1, 100000, '2022-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0002', 'ASCO', 'ASCO-PI40', 1, 1, 100000, '2023-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0003', 'ASCO', 'ASCO-PI40', 2, null, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0004', 'ASCO', 'ASCO-PI40', 2, null, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PSV-0001', 'ASCO', 'ASCO-PSV40', 1, 1, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0002', 'ASCO', 'ASCO-PSV40', 1, 1, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0003', 'ASCO', 'ASCO-PSV40', 2, null, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0004', 'ASCO', 'ASCO-PSV40', 2, null, 100000, null, true, 'PSV', null, '2"', null, null, 14.0, 12.0);

insert into
        nr13_api.tb_interventions (report_number, executor_company, execution_date, comments, status, updated_by, cost, file_id)
    values
        ('22-0125', 'MegaSteam', '2021-05-02', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        (null, 'MegaSteam', '2022-05-02', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0125', 'MegaSteam', '2021-05-02', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        (null, 'MegaSteam', '2022-05-02', 'Calibrado no local', 'WAITING_REPORT', 100000, '45.16', null),
        ('22-0125', 'MegaSteam', '2023-05-02', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('23-0122', 'MegaSteam', '2021-05-02', 'Inspeção no local', 'DONE', 100000, '45.16', null),
        ('22-0125', 'MegaSteam', '2021-05-02', 'Inspeção no local', 'DONE', 100000, '45.16', null),
        ('23-0521', 'MegaSteam', '2021-05-02', 'Inspeção no local', 'DONE', 100000, '45.16', null),
        ('22-0125', 'MegaSteam', '2021-05-02', 'Inspeção no local', 'DONE', 100000, '45.16', null),
        ('23-0008', 'MegaSteam', '2021-05-02', 'Inspeção no local', 'DONE', 100000, '45.16', null);

insert into
        nr13_api.tb_calibrations (id, device_id)
    values
        (1, 1), (2, 1), (3, 2), (4, 2), (5, 2);

insert into
        nr13_api.tb_tests (name, description, frequency, frequency_type)
    values
        ('Inspeção visual externa', 'Inspeção visual realizada no exterior do equipamento para detectar anomalias simples', 1, 'MONTH'),
        ('Medição de espessura', 'Medição da espessura das paredes do equipamento para detecção de desgastes', 6, 'MONTH'),
        ('Inspeção de integridade física', 'Inspeção realizada com equipamento fora de operação, sob supervição direta do PH', 1, 'YEAR'),
        ('Teste hidrostático', 'Ensaio realizado sob supervisão do PH para validação da PMTA do equipamento', 5, 'YEAR'),
        ('Ultrassom das juntas', 'Ensaio alternativo ao teste hidrostático, verificando a integridade das soldas', 5, 'YEAR');

insert into
        nr13_api.tb_applicable_tests (equipment_id, test_id, frequency, frequency_type, last_test_date, active, updated_by)
    values
        (1, 1, null, null, '2023-05-10', 'true', 100000);

insert into
        nr13_api.tb_inspections (id, equipment_id, test_id)
    values
        (6, 1, 1),
        (7, 1, 1),
        (8, 1, 1),
        (9, 1, 1),
        (10, 1, 1);
