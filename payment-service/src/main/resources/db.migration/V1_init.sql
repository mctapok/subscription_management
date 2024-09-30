create table payment_accounts (
   id bigserial primary key,
   account_id bigint not null,
   client_id bigint not null,
   balance decimal(6,2) not null
);

