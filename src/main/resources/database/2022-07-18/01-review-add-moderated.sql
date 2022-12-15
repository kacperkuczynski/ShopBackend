--liquibase formatted sql
--changeset kkuczynski:9
alter table review add moderated boolean default false;
