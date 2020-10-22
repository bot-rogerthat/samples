--liquibase formatted sql
--changeset yakushenoksi:202010221401.1
create table deposit_schema.deposit (
    id bigserial primary key,
    account_name varchar(255) unique not null,
    balance numeric not null,
    expiration_date timestamp default null
);
--rollback drop table deposit_schema.deposit;

--liquibase formatted sql
--changeset yakushenoksi:202010221401.2
create index deposit_id_idx on deposit_schema.deposit (id);
create index deposit_account_name_idx on deposit_schema.deposit (account_name);
--rollback drop index deposit_id_idx;
--rollback drop index deposit_account_name_idx;