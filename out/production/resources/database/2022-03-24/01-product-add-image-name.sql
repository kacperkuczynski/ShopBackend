--liquibase formatted sql
--changeset kkuczynski:2
alter table product add image varchar(128) after currency;