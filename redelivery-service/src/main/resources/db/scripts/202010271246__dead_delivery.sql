--liquibase formatted sql
--changeset yakushenoksi:202010271246.1
create table redelivery.dead_delivery (
    uuid varchar(255) not null,
    service_id varchar(255) not null,
    context jsonb not null,
    activation_date timestamp default null,
    trace_id varchar(16)
);
--rollback drop table redelivery.dead_delivery;

--liquibase formatted sql
--changeset yakushenoksi:202010271246.2
create index dead_delivery_uuid_idx on redelivery.dead_delivery (uuid);
create index dead_delivery_activation_date_idx on redelivery.dead_delivery (activation_date);
--rollback drop index dead_delivery_uuid_idx;
--rollback drop index dead_delivery_activation_date_idx;