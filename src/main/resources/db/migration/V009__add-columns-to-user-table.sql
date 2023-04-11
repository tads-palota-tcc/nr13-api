alter table nr13_api.tb_users add column name varchar(60);
update nr13_api.tb_users set name = substring(email from 1 for (position('@' in email) - 1)) where name is null;
alter table nr13_api.tb_users alter column name set not null;

alter table nr13_api.tb_users add column password varchar(255);
update nr13_api.tb_users set password = '$2a$12$VoUaSPN6Axxo2T4HITqRluBfQL6JL1ksP4OPSa1.KYss4j4YjS8Ba';
alter table nr13_api.tb_users alter column password set not null;

alter table nr13_api.tb_users add column active boolean default true;
update nr13_api.tb_users set active = true;

alter table nr13_api.tb_users add column locked boolean default false;
update nr13_api.tb_users set locked = false;

create table nr13_api.tb_groups (
    id serial primary key,
    name varchar(40) unique not null
);

create table nr13_api.tb_permissions (
    id serial primary key,
    name varchar(40) unique not null
);

create table nr13_api.tb_user_groups (
    user_id bigint not null,
    group_id bigint not null,
    constraint fk_user_groups_user_id foreign key (user_id) references nr13_api.tb_users(id),
    constraint fk_user_groups_group_id foreign key (group_id) references nr13_api.tb_groups(id)
);

create table nr13_api.tb_group_permissions (
    group_id bigint not null,
    permission_id bigint not null,
    constraint fk_group_permissions_group_id foreign key (group_id) references nr13_api.tb_groups(id),
    constraint fk_group_permissions_permission_id foreign key (permission_id) references nr13_api.tb_permissions(id)
);