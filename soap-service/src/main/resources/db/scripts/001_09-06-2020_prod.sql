--liquibase formatted sql
--changeset yakushenoksi:001
create schema prod;
--rollback drop schema prod;
