SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS merchant;
CREATE TABLE merchant
(
	id               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	mer_id           VARCHAR(32)         NOT NULL COMMENT '商户ID',
	mer_name         VARCHAR(128)        NOT NULL COMMENT '商户名称',
	address          VARCHAR(200)        NOT NULL DEFAULT ''  COMMENT '地址',
	status           TINYINT(2)          NOT NULL DEFAULT '1' COMMENT '状态:1待审核,2正常',
	create_time      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	modify_time      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'更新时间',
	is_del           TINYINT(2)          NOT NULL DEFAULT '0' COMMENT '删除标志:0未删除,1删除',
	PRIMARY KEY (id),
	UNIQUE INDEX idx_merchant_id(mer_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商户表';

insert into merchant(mer_id, mer_name, address, status) values('111222233330001', '商户1', '北京', 2);
insert into merchant(mer_id, mer_name, address, status) values('111222233330002', '商户2', '北京', 2);
insert into merchant(mer_id, mer_name, address, status) values('111222233330003', '商户3', '北京', 1);
insert into merchant(mer_id, mer_name, address, status) values('111222233330004', '商户4', '北京', 2);

SET FOREIGN_KEY_CHECKS = 1;
