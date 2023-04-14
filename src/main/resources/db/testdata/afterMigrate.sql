drop table if exists audit.tb_plants_audit;

delete from nr13_api.tb_user_groups;
delete from nr13_api.tb_group_permissions;
delete from nr13_api.tb_groups;
delete from nr13_api.tb_permissions;
delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_plants;
delete from nr13_api.tb_users;

alter sequence nr13_api.tb_users_id_seq restart with 100000;
alter sequence nr13_api.tb_plants_id_seq restart with 1;
alter sequence nr13_api.tb_groups_id_seq restart with 1;
alter sequence nr13_api.tb_permissions_id_seq restart with 1;

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