--liquibase formatted sql
--changeset yakushenoksi:003
insert into prod.article (name, description, date_added) values( 'Name 1', 'Desc 1', '2020-06-09');
insert into prod.article (name, description, date_added) values( 'Name 2', 'Desc 2', '2020-06-09');
--rollback delete from prod.article where name in ('Name 1', 'Name 2');