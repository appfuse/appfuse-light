DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user (
  id bigint(20) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  birthday datetime default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_sequence;
CREATE TABLE user_sequence (value int not null) type=MYISAM;
INSERT INTO user_sequence values(0);