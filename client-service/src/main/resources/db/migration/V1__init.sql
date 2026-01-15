create table accounts (
    id bigserial primary key not null,
    client_id bigint not null,
    account_id bigint,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    email varchar(255) not null,
    created_at timestamp,
    updated_at timestamp
);

create table client_history (
    id bigserial primary key not null,
    client_id bigint not null,
    title varchar(255) not null ,
    service varchar(255) not null ,
    message varchar(255),
    created_at timestamp
);

insert into accounts (client_id, firstname, lastname, email)
values (1, 'client1', 'client1', 'client1@mail.com'),
       (2, 'client2', 'client2', 'client2@mail.com');
