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
delete from nr13_api.tb_user_groups;
delete from nr13_api.tb_group_permissions;
delete from nr13_api.tb_groups;
delete from nr13_api.tb_permissions;
delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_devices;
delete from nr13_api.tb_equipments;
delete from nr13_api.tb_files;
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
insert into nr13_api.tb_users (name, email, password) values ('José Antônio da Silva', 'jose.silva@email.com', '$2a$12$VoUaSPN6Axxo2T4HITqRluBfQL6JL1ksP4OPSa1.KYss4j4YjS8Ba');
insert into nr13_api.tb_users (name, email, password) values ('Paula Corrêa', 'paula.correa@email.com', '$2a$12$VoUaSPN6Axxo2T4HITqRluBfQL6JL1ksP4OPSa1.KYss4j4YjS8Ba');
insert into nr13_api.tb_users (name, email, password) values ('Carlos Fonseca', 'carlos.fonseca@email.com', '$2a$12$VoUaSPN6Axxo2T4HITqRluBfQL6JL1ksP4OPSa1.KYss4j4YjS8Ba');

insert into nr13_api.tb_user_groups (user_id, group_id) values (100000, 1), (100000, 2), (100000, 3);
insert into nr13_api.tb_user_groups (user_id, group_id) values (100001, 2), (100001, 3);
insert into nr13_api.tb_user_groups (user_id, group_id) values (100002, 3);

insert into
        nr13_api.tb_plants (code, name, city, state, active, updated_by)
    values
        ('RGD1', 'Unidade Rio Grande 1', 'Rio Grande', 'RS','true', 100000),
        ('PEL1', 'Unidade Pelotas 1', 'Pelotas', 'RS', 'true', 100000),
        ('POA1', 'Unidade Porto Alegre 1', 'Porto Alegre', 'RS', 'true', 100000),
        ('POA5', 'Unidade Porto Alegre 5', 'Porto Alegre', 'RS', 'false', 100000);

insert into nr13_api.tb_user_plants (user_id, plant_id) values (100000, 1), (100000, 2), (100000, 3), (100000, 4);

insert into
        nr13_api.tb_areas (code, name, plant_id, updated_by, active)
    values
        ('CMP-01', 'Unidade de compressão de gás', 1, 100000, true),
        ('RFG-01', 'Unidade de refrigeração', 1, 100000, true),
        ('ETA-01', 'Unidade de tratamento de água', 1, 100000, true),
        ('PNT-01', 'Unidade de pintura', 2, 100000, true),
        ('CRT-01', 'Unidade de corte', 2, 100000, true),
        ('USN-01', 'Unidade de usinagem', 3, 100000, true),
        ('RFN-01', 'Unidade de refino', 4, 100000, true);

insert into nr13_api.tb_equipments
            (tag, area_id, name, hydrostatic_test_pressure, max_operation_pressure, max_allowed_work_pressure, diameter, volume, fluid_class, active, updated_by)
    values
        ('CPG-001', 1, 'Compressor de gás', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('CPG-002', 1, 'Compressor de ar', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('SL-01A', 2, 'Separador de líquido', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('CP-01', 3, 'Compressor de ar', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('RA-01', 4, 'Reservatório de ar 01', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('RA-02', 4, 'Reservatório de ar 01', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('RA-03', 4, 'Reservatório de ar 01', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('COX-01', 5, 'Central de oxigêncio', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('RO-01A', 6, 'Reservatório de óleo hidráulico', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000),
        ('CP-01B', 7, 'Compressor de ar', 15.0, 8.0, 10.0, 300, 0.45, 'C', true, 100000);

insert into
        nr13_api.tb_devices
        (tag, manufacturer, model, plant_id, equipment_id, updated_by, last_calibration_date, active, type, connection_size, body_size, min_gauge, max_gauge, opening_pressure, closing_pressure)
    values
        ('PI-0001', 'ASCO', 'ASCO-PI40', 1, 1, 100000, '2022-06-20', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0002', 'ASCO', 'ASCO-PI40', 1, 2, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0003', 'ASCO', 'ASCO-PI40', 1, 3, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0004', 'ASCO', 'ASCO-PI40', 1, 4, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0005', 'ASCO', 'ASCO-PI40', 2, 5, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0006', 'ASCO', 'ASCO-PI40', 2, 6, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0007', 'ASCO', 'ASCO-PI40', 2, 7, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0008', 'ASCO', 'ASCO-PI40', 2, 8, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0009', 'ASCO', 'ASCO-PI40', 3, 9, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PI-0010', 'ASCO', 'ASCO-PI40', 4, 10, 100000, '2021-05-02', true, 'PI', '3/4"', null, 0.0, 25.0, null, null),
        ('PSV-0001', 'ASCO', 'ASCO-PSV40', 1, 1, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0002', 'ASCO', 'ASCO-PSV40', 1, 2, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0003', 'ASCO', 'ASCO-PSV40', 1, 3, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0004', 'ASCO', 'ASCO-PSV40', 1, 4, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0005', 'ASCO', 'ASCO-PSV40', 2, 5, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0006', 'ASCO', 'ASCO-PSV40', 2, 6, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0007', 'ASCO', 'ASCO-PSV40', 2, 7, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0008', 'ASCO', 'ASCO-PSV40', 2, 8, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-0009', 'ASCO', 'ASCO-PSV40', 3, 9, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0),
        ('PSV-00010', 'ASCO', 'ASCO-PSV40', 4, 10, 100000, '2021-05-02', true, 'PSV', null, '2"', null, null, 14.0, 12.0);


insert into
        nr13_api.tb_interventions (report_number, executor_company, execution_date, comments, status, updated_by, cost, file_id)
    values
        ('22-0010', 'MegaSteam', '2022-04-01', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0011', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100002, '45.16', null),
        ('22-0012', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0013', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0014', 'MegaSteam', '2022-04-12', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0015', 'MegaSteam', '2022-12-30', 'Calibrado no local', 'DONE', 100002, '45.16', null),
        ('22-0016', 'MegaSteam', '2022-12-10', 'Calibrado no local', 'DONE', 100002, '45.16', null),
        ('22-0017', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0018', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0019', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0020', 'MegaSteam', '2022-04-01', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0021', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0022', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0023', 'MegaSteam', '2022-12-20', 'Calibrado no local', 'DONE', 100000, '45.16', null),
        ('22-0024', 'MegaSteam', '2022-04-12', 'Calibrado no local', 'DONE', 100002, '45.16', null),
        ('22-0025', 'MegaSteam', '2022-12-30', 'Calibrado no local', 'DONE', 100002, '45.16', null),
        ('22-0026', 'MegaSteam', '2022-12-10', 'Calibrado no local', 'DONE', 100003, '45.16', null),
        ('22-0027', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100003, '45.16', null),
        ('22-0028', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100003, '45.16', null),
        ('22-0029', 'MegaSteam', '2022-12-25', 'Calibrado no local', 'DONE', 100001, '45.16', null),
        ('22-0030', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0031', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0032', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100002, '45.16', null),
        ('22-0033', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100002, '45.16', null),
        ('22-0034', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0035', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0036', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100001, '45.16', null),
        ('22-0037', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100001, '45.16', null),
        ('22-0038', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100001, '45.16', null),
        ('22-0039', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0040', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100002, '45.16', null),
        ('22-0041', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0042', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100001, '45.16', null),
        ('22-0043', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0044', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100003, '45.16', null),
        ('22-0045', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0046', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100003, '45.16', null),
        ('22-0047', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null),
        ('22-0048', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100003, '45.16', null),
        ('22-0049', 'MegaSteam', '2022-12-25', 'Inspecionado no local', 'DONE', 100000, '45.16', null);

insert into
        nr13_api.tb_calibrations (id, device_id)
    values
        (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10),
        (11, 11), (12, 12), (13, 13), (14, 14), (15, 15), (16, 16), (17, 17), (18, 18), (19, 19), (20, 20);

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
        (1, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (1, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (2, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (2, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (3, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (3, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (4, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (4, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (5, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (5, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (6, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (6, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (7, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (7, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (8, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (8, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (9, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (9, 3, 1, 'YEAR', '2023-05-29', 'true', 100000),
        (10, 1, 3, 'MONTH', '2023-05-29', 'true', 100000),
        (10, 3, 1, 'YEAR', '2023-05-29', 'true', 100000);

insert into
        nr13_api.tb_inspections (id, equipment_id, test_id)
    values
        (21, 1, 1),
        (22, 1, 3),
        (23, 2, 1),
        (24, 2, 3),
        (25, 3, 1),
        (26, 3, 3),
        (27, 4, 1),
        (28, 4, 3),
        (29, 5, 1),
        (30, 5, 3),
        (31, 6, 1),
        (32, 6, 3),
        (33, 7, 1),
        (34, 8, 3),
        (35, 8, 1),
        (36, 8, 3),
        (37, 9, 1),
        (38, 9, 3),
        (39, 10, 1),
        (40, 10, 3);

insert into
    nr13_api.tb_pendencies (inspection_id, author, type, status, responsible, description, action, opened_at, dead_line, updated_by, cost)
    values
        (21, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (22, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (22, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (23, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (24, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (24, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (25, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (26, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (26, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (27, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (28, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (28, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (29, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (30, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (30, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (31, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (32, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (32, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (33, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (34, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (34, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (35, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (36, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (36, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (37, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (38, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (38, 100000, 'MANDATORY', 'STARTED', 100001, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (39, 100000, 'MANDATORY', 'STARTED', 100002, 'Base enferrujada', 'Pintar a base', '2023-05-20', '2023-12-30', 100000, '200.0'),
        (40, 100000, 'MANDATORY', 'STARTED', 100003, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0'),
        (40, 100000, 'MANDATORY', 'STARTED', 100000, 'Base enferrujada', 'Pintar a base', '2023-10-20', '2023-12-30', 100000, '200.0');

