drop table app_user if exists;

create table app_user (id integer identity, first_name varchar(50), last_name varchar(50), birthday timestamp);
