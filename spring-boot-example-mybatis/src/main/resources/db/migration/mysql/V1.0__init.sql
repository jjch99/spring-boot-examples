
drop table if exists merchant;

create table merchant
(
    id bigint(11) unsigned not null auto_increment,
	mer_id varchar(32) not null,
	mer_name varchar(128) not null,
	address varchar(200),
	create_time date,
	primary key (id)
)
ENGINE=InnoDB;

create unique index merchant_uk on merchant(mer_id);
