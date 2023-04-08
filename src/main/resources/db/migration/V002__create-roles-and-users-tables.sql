create table nr13_api.tb_roles (
    id serial primary key,
    name varchar(20) not null
);

create table nr13_api.tb_users (
    id bigserial primary key,
    name varchar(60) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    active boolean default true
);

create table nr13_api.tb_user_roles (
    user_id bigint references nr13_api.tb_users(id),
    role_id integer references nr13_api.tb_roles(id),
    primary key(user_id, role_id)
);