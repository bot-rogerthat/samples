--liquibase formatted sql
--changeset yakushenoksi:002.1
create table prod.article (
    id bigserial primary key,
    name varchar(255) not null,
    description text not null,
    date_added timestamp default null
);
--rollback drop table prod.article;

--liquibase formatted sql
--changeset yakushenoksi:002.2
create index article_name_idx on prod.article (name);
--rollback drop index article_name_idx;