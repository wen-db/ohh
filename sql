DROP TABLE IF EXISTS `o_admin_user`;
CREATE TABLE `o_admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name1` varchar(20) DEFAULT '',
  `msg` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

DROP TABLE IF EXISTS `o_admin_login_log`;
CREATE TABLE `o_admin_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT '',
  `login_date` datetime DEFAULT now(),
  `msg` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员日志';