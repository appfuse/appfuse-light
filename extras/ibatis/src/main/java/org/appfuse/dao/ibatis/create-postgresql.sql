drop table app_user;
drop sequence user_sequence;

create table app_user (id bigint not null primary key, first_name varchar(50), last_name varchar(50), birthday timestamp);
create sequence user_sequence;
