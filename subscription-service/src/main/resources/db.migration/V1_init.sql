create table subscriptions
(
    id                 bigserial primary key,
    subscription_id    bigint,
    client_id          bigint,
    subscription_title varchar(255),
    duration           bigint,
    price              numeric(6, 2),
    status             varchar(16),
    created_at         timestamp,
    updated_at         timestamp,
    foreign key (subscription_id) references subscription_plans(id) on delete cascade
);

create table subscription_plans
(
    id                 bigserial primary key,
    subscription_title varchar(255),
    duration           bigint,
    price              numeric(6, 2),
    created_at         timestamp,
    updated_at         timestamp
);

insert into subscriptions (subscription_id, client_id, subscription_title, duration, price, status)
VALUES (1, 1, 'netflix', 30, 15, 'active'),
       (5, 1, 'photoshop', 30, 9, 'active');

insert into subscription_plans (subscription_title, duration, price)
VALUES ('netflix', 30, 13.99),
       ('spotify', 30, 9.99),
       ('amazon prime', 30, 14.95),
       ('bank BOA premium', 30, 4.95),
       ('photoshop', 30, 17.95);
