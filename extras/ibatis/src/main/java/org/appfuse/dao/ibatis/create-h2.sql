drop table app_user if exists;
drop table user_sequence if exists;

create table app_user (id bigint not null primary key, first_name varchar(50), last_name varchar(50), birthday timestamp);
create table user_sequence (value identity);
insert into user_sequence values(0);