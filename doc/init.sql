CREATE TABLE `tlot` (
  `flowno` int(11) NOT NULL auto_increment,
  `batchcode` varchar(255) default NULL,
  `betcode` varchar(255) default NULL,
  `lotmulti` decimal(19,2) default NULL,
  `lotno` varchar(255) default NULL,
  PRIMARY KEY  (`flowno`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



CREATE TABLE `tstatisticsdata` (
  `batchcode` varchar(255) NOT NULL,
  `lotno` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` mediumtext,
  `valueString` varchar(255) default NULL,
  PRIMARY KEY  (`batchcode`,`lotno`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `windata` (
  `id` int(11) NOT NULL auto_increment,
  `amount` decimal(19,2) default NULL,
  `code` varchar(255) default NULL,
  `data` datetime default NULL,
  `email` varchar(255) default NULL,
  `lotno` varchar(255) default NULL,
  `mobildid` varchar(255) default NULL,
  `nickName` varchar(255) default NULL,
  `playtype` varchar(255) default NULL,
  `userno` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


