drop table if exists audit.tb_plants_audit;

delete from nr13_api.tb_user_plants;
delete from nr13_api.tb_plants;
delete from nr13_api.tb_users;

alter sequence nr13_api.tb_users_id_seq restart with 100000;
alter sequence nr13_api.tb_plants_id_seq restart with 1;

insert into nr13_api.tb_users (email) values ('alexandre.palota@gmail.com');

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