
-- for user root

create database if not exists db1 default character set utf8;
create database if not exists db2 default character set utf8;

grant all privileges on db1.* to 'test'@'%';
grant all privileges on db2.* to 'test'@'%';

SET FOREIGN_KEY_CHECKS = 1;
