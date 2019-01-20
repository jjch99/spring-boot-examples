
-- for user root

create database if not exists ds_00 default character set utf8;
create database if not exists ds_01 default character set utf8;
create database if not exists ds_biz default character set utf8;
create database if not exists ds_summary default character set utf8;

grant all privileges on ds_00.* to 'test'@'%';
grant all privileges on ds_01.* to 'test'@'%';
grant all privileges on ds_biz.* to 'test'@'%';
grant all privileges on ds_summary.* to 'test'@'%';


-- for user test

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `tb_user` (
   `ID`                bigint(20)          NOT NULL,
  `USER_ID`            bigint(20)          NOT NULL COMMENT '用户ID',
  `USER_NAME`          varchar(500)        NOT NULL COMMENT '用户名',
  `STATUS`             tinyint(2)          NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

CREATE TABLE `tb_order_0` (
  `ID`                 bigint(20) unsigned NOT NULL,
  `USER_ID`            bigint(20)          NOT NULL COMMENT '用户ID',
  `STATUS`             tinyint(2)          NOT NULL DEFAULT '1',
  `COMMENTS`           varchar(500)                            ,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

CREATE TABLE `tb_order_1` (
  `ID`                 bigint(20) unsigned NOT NULL,
  `USER_ID`            bigint(20)          NOT NULL COMMENT '用户ID',
  `STATUS`             tinyint(2)          NOT NULL DEFAULT '1',
  `COMMENTS`           varchar(500)                            ,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

CREATE TABLE `tb_order_all` (
  `ID`                 bigint(20) unsigned NOT NULL,
  `USER_ID`            bigint(20)          NOT NULL COMMENT '用户ID',
  `STATUS`             tinyint(2)          NOT NULL DEFAULT '1',
  `COMMENTS`           varchar(500)                            ,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

SET FOREIGN_KEY_CHECKS = 1;
