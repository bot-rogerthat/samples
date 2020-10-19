--liquibase formatted sql
--changeset yakushenoksi:202010191455.1
create table prod.delivery (
    uuid varchar(255) not null,
    service_id varchar(255) not null,
    context jsonb not null,
    activation_date timestamp default null
);
--rollback drop table prod.delivery;

--liquibase formatted sql
--changeset yakushenoksi:202010191455.2
create index delivery_uuid_idx on prod.delivery (uuid);
create index delivery_activation_date_idx on prod.delivery (activation_date);
--rollback drop index delivery_uuid_idx;
--rollback drop index delivery_activation_date_idx;