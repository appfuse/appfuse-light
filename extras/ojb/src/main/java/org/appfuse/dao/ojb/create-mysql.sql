DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user (
  id int(8) not null auto_increment,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  birthday datetime default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
