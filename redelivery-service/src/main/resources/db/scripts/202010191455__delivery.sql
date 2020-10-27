--liquibase formatted sql
--changeset yakushenoksi:202010191455.1
create table redelivery.delivery (
    uuid varchar(255) not null,
    service_id varchar(255) not null,
    context jsonb not null,
    activation_date timestamp default null,
    trace_id varchar(16)
);
--rollback drop table redelivery.delivery;

--liquibase formatted sql
--changeset yakushenoksi:202010191455.2
create index delivery_uuid_idx on redelivery.delivery (uuid);
create index delivery_activation_date_idx on redelivery.delivery (activation_date);
--rollback drop index delivery_uuid_idx;
--rollback drop index delivery_activation_date_idx;