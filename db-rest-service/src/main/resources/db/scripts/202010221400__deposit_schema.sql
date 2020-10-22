--liquibase formatted sql
--changeset yakushenoksi:202010221400
create schema deposit_schema;
--rollback drop schema deposit_schema;