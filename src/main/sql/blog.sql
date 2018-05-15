# Host: localhost  (Version 5.7.20)
# Date: 2018-05-15 20:14:04
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "blog_category"
#

DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `categoryId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(20) NOT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryName` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "blog_category"
#

INSERT INTO `blog_category` VALUES (1,'about'),(2,'无关技术'),(3,'java基础'),(4,'c语言'),(5,'test');

#
# Structure for table "blog_image"
#

DROP TABLE IF EXISTS `blog_image`;
CREATE TABLE `blog_image` (
  `imageId` int(11) NOT NULL AUTO_INCREMENT,
  `imageType` int(11) NOT NULL,
  `imageName` varchar(500) NOT NULL,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

#
# Data for table "blog_image"
#

INSERT INTO `blog_image` VALUES (18,0,'ted.jpg'),(25,1,'blog.jpg'),(26,1,'Hello.png'),(27,1,'HelloWorld.jpg'),(28,1,'java.jpg'),(48,1,'IMG_1992.JPG'),(50,1,'IMG_2025.JPG'),(52,1,'IMG_2002.JPG'),(53,1,'IMG_2037.JPG'),(54,1,'IMG_2021.JPG'),(56,1,'3d649f25bc315c60e692803c84b1cb134854777d.jpg'),(59,0,'timg2.jpg'),(60,1,'IMG_1178.JPG'),(61,3,'IMG_1178.JPG'),(62,3,'3d649f25bc315c60e692803c84b1cb134854777d.jpg'),(64,3,'timg2.jpg'),(65,3,'timg.jpg'),(67,3,'IMG_7566.JPG'),(68,3,'IMG_3612.JPG'),(70,3,'timg2.jpg'),(72,3,'IMG_1995.JPG'),(73,3,'IMG_1996.JPG'),(76,3,'IMG_1997.JPG'),(77,3,'IMG_1992.JPG'),(78,1,'IMG_2007.JPG'),(79,0,'QQ图片20151227213302.jpg'),(80,3,'IMG_3612.JPG');

#
# Structure for table "blog_link"
#

DROP TABLE IF EXISTS `blog_link`;
CREATE TABLE `blog_link` (
  `linkId` int(11) NOT NULL AUTO_INCREMENT,
  `linkName` varchar(20) NOT NULL,
  `url` varchar(50) NOT NULL,
  PRIMARY KEY (`linkId`),
  UNIQUE KEY `linkName` (`linkName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "blog_link"
#

INSERT INTO `blog_link` VALUES (1,'百度','www.baidu.com'),(2,'腾讯','www.'),(3,'搜狐','www.souhu.com'),(4,'444','444');

#
# Structure for table "blog_user"
#

DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userType` int(11) DEFAULT '0',
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `imageId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`),
  KEY `blog_user_ibfk_1` (`imageId`),
  CONSTRAINT `blog_user_ibfk_1` FOREIGN KEY (`imageId`) REFERENCES `blog_image` (`imageId`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Data for table "blog_user"
#

INSERT INTO `blog_user` VALUES (1,1,'admin','4QrcOUm6Wau+VuBX8g+IPg==',18),(8,0,'Burton','4QrcOUm6Wau+VuBX8g+IPg==',18);

#
# Structure for table "blog_message"
#

DROP TABLE IF EXISTS `blog_message`;
CREATE TABLE `blog_message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `messageType` int(11) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `pubDate` datetime NOT NULL,
  PRIMARY KEY (`messageId`),
  KEY `userId` (`userId`),
  CONSTRAINT `blog_message_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `blog_user` (`userId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "blog_message"
#


#
# Structure for table "blog_article"
#

DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `content` mediumtext,
  `pubDate` datetime NOT NULL,
  `clicks` int(11) DEFAULT '0',
  `imageId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`articleId`),
  KEY `categoryId` (`categoryId`),
  KEY `userId` (`userId`),
  KEY `blog_article_ibfk_3` (`imageId`),
  CONSTRAINT `blog_article_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `blog_category` (`categoryId`) ON DELETE SET NULL,
  CONSTRAINT `blog_article_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `blog_user` (`userId`) ON DELETE SET NULL,
  CONSTRAINT `blog_article_ibfk_3` FOREIGN KEY (`imageId`) REFERENCES `blog_image` (`imageId`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

#
# Data for table "blog_article"
#

INSERT INTO `blog_article` VALUES (1,1,1,'about','<h1>关于我：</h1>\r\n\r\n<p>2019年毕业生&nbsp; 目前大三下 初学SSM 练手&nbsp; 希望程序员这条路越走越远</p>\r\n\r\n<p>没有任何公司实战经验</p>\r\n\r\n<hr />\r\n<h1>关于这个博客：</h1>\r\n\r\n<p>这个博客可从github上git下来 我做了部分修改 去除一些bug 增加了一些功能</p>\r\n','2018-05-09 16:07:00',0,54),(12,3,1,'JAVA大法好','<p>测试</p>\r\n','2018-05-09 11:29:20',0,27),(13,3,1,'String、StringBuffer','<p>测试</p>\r\n','2018-05-09 11:30:04',0,25),(14,4,1,'c++ c--','<p>测试</p>\r\n','2018-05-09 11:30:25',0,28),(15,4,1,'c','<p>测试</p>\r\n','2018-05-09 11:30:48',2,28),(16,5,1,'testarticle','<p>测试</p>\r\n','2018-05-09 11:31:18',1,26),(17,5,1,'testarticle2','<p>测试</p>\r\n','2018-05-09 11:31:36',1,27),(18,2,1,'生活','<p>测试</p>\r\n','2018-05-09 11:31:56',1,28),(19,2,1,'郊游','<p>测试</p>\r\n','2018-05-09 11:32:11',2,26),(20,2,1,'佛寺','<p>测试</p>\r\n','2018-05-09 14:47:20',0,50),(21,2,1,'博客','<p>测试</p>\r\n','2018-05-09 14:47:56',6,25),(22,5,1,'女孩','<p>测试bbbbb</p>\r\n','2018-05-09 15:54:46',3,52),(23,5,1,'熊','<p>测试cccccc灌灌灌灌灌</p>\r\n','2018-05-09 15:55:11',17,54),(25,2,1,'威斯布鲁克','<h2><strong>我喜欢</strong></h2>\r\n\r\n<hr />\r\n<p><img alt=\"\" src=\"/images/article/3d649f25bc315c60e692803c84b1cb134854777d.jpg\" style=\"height:255px; width:400px\" /></p>\r\n','2018-05-15 17:51:23',3,25),(26,2,1,'6666','<p><img alt=\"\" src=\"/images/article/IMG_1992.JPG\" style=\"height:267px; width:400px\" /></p>\r\n','2018-05-15 18:03:41',8,53),(27,5,1,'修改后测试','<h1><strong>我在测试 我爱高雨帆</strong></h1>\r\n\r\n<hr />\r\n<p><img alt=\"\" src=\"/images/article/IMG_3612.JPG\" style=\"height:300px; width:300px\" /></p>\r\n','2018-05-15 20:04:39',1,28);

#
# Structure for table "blog_visitor"
#

DROP TABLE IF EXISTS `blog_visitor`;
CREATE TABLE `blog_visitor` (
  `visitorId` int(11) NOT NULL AUTO_INCREMENT,
  `visitorIp` varchar(20) NOT NULL,
  `visitorTime` datetime NOT NULL,
  PRIMARY KEY (`visitorId`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

#
# Data for table "blog_visitor"
#

INSERT INTO `blog_visitor` VALUES (17,'127.0.0.1','2018-05-14 16:10:54'),(18,'127.0.0.1','2018-05-14 16:10:56'),(19,'0:0:0:0:0:0:0:1','2018-05-14 16:10:56'),(20,'0:0:0:0:0:0:0:1','2018-05-14 16:11:36'),(21,'0:0:0:0:0:0:0:1','2018-05-14 16:11:47'),(22,'0:0:0:0:0:0:0:1','2018-05-14 16:12:53'),(23,'0:0:0:0:0:0:0:1','2018-05-14 16:12:58'),(24,'0:0:0:0:0:0:0:1','2018-05-14 16:13:03'),(25,'0:0:0:0:0:0:0:1','2018-05-14 16:13:25'),(26,'0:0:0:0:0:0:0:1','2018-05-14 16:13:39'),(27,'0:0:0:0:0:0:0:1','2018-05-14 16:13:59'),(28,'127.0.0.1','2018-05-14 16:14:48'),(29,'127.0.0.1','2018-05-14 16:14:50'),(30,'0:0:0:0:0:0:0:1','2018-05-14 16:14:51'),(31,'127.0.0.1','2018-05-14 16:20:10'),(32,'127.0.0.1','2018-05-14 16:20:11'),(33,'0:0:0:0:0:0:0:1','2018-05-14 16:20:12'),(34,'0:0:0:0:0:0:0:1','2018-05-14 16:20:35'),(35,'0:0:0:0:0:0:0:1','2018-05-14 16:20:45'),(36,'127.0.0.1','2018-05-14 16:23:42'),(37,'127.0.0.1','2018-05-14 16:23:43'),(38,'0:0:0:0:0:0:0:1','2018-05-14 16:23:44'),(39,'127.0.0.1','2018-05-14 16:29:24'),(40,'127.0.0.1','2018-05-14 16:29:26'),(41,'0:0:0:0:0:0:0:1','2018-05-14 16:29:27'),(42,'127.0.0.1','2018-05-14 16:35:12'),(43,'0:0:0:0:0:0:0:1','2018-05-14 16:35:15'),(44,'127.0.0.1','2018-05-14 23:11:07'),(45,'127.0.0.1','2018-05-14 23:11:10'),(46,'0:0:0:0:0:0:0:1','2018-05-14 23:11:11'),(47,'0:0:0:0:0:0:0:1','2018-05-14 23:11:30'),(48,'127.0.0.1','2018-05-15 10:34:11'),(49,'127.0.0.1','2018-05-15 10:34:13'),(50,'0:0:0:0:0:0:0:1','2018-05-15 10:34:14'),(51,'0:0:0:0:0:0:0:1','2018-05-15 10:34:30'),(52,'0:0:0:0:0:0:0:1','2018-05-15 10:34:39'),(53,'127.0.0.1','2018-05-15 12:01:16'),(54,'127.0.0.1','2018-05-15 12:01:17'),(55,'0:0:0:0:0:0:0:1','2018-05-15 12:01:42'),(56,'0:0:0:0:0:0:0:1','2018-05-15 12:01:46'),(57,'127.0.0.1','2018-05-15 13:25:22'),(58,'127.0.0.1','2018-05-15 13:25:25'),(59,'0:0:0:0:0:0:0:1','2018-05-15 13:25:54'),(60,'0:0:0:0:0:0:0:1','2018-05-15 13:26:00'),(61,'0:0:0:0:0:0:0:1','2018-05-15 13:26:01'),(62,'0:0:0:0:0:0:0:1','2018-05-15 13:26:09'),(63,'127.0.0.1','2018-05-15 13:28:32'),(64,'127.0.0.1','2018-05-15 13:28:33'),(65,'0:0:0:0:0:0:0:1','2018-05-15 13:28:44'),(66,'127.0.0.1','2018-05-15 13:35:14'),(67,'127.0.0.1','2018-05-15 13:35:16'),(68,'0:0:0:0:0:0:0:1','2018-05-15 13:35:33'),(69,'127.0.0.1','2018-05-15 13:50:08'),(70,'127.0.0.1','2018-05-15 13:50:11'),(71,'0:0:0:0:0:0:0:1','2018-05-15 13:50:15'),(72,'0:0:0:0:0:0:0:1','2018-05-15 13:50:43'),(73,'0:0:0:0:0:0:0:1','2018-05-15 13:51:43'),(74,'0:0:0:0:0:0:0:1','2018-05-15 13:54:51'),(75,'0:0:0:0:0:0:0:1','2018-05-15 13:55:03'),(76,'0:0:0:0:0:0:0:1','2018-05-15 13:55:39'),(77,'0:0:0:0:0:0:0:1','2018-05-15 13:57:42'),(78,'0:0:0:0:0:0:0:1','2018-05-15 13:59:20'),(79,'0:0:0:0:0:0:0:1','2018-05-15 14:04:28'),(80,'127.0.0.1','2018-05-15 14:06:11'),(81,'127.0.0.1','2018-05-15 14:06:12'),(82,'0:0:0:0:0:0:0:1','2018-05-15 14:06:13'),(83,'127.0.0.1','2018-05-15 14:31:13'),(84,'127.0.0.1','2018-05-15 14:31:15'),(85,'0:0:0:0:0:0:0:1','2018-05-15 14:31:16'),(86,'127.0.0.1','2018-05-15 14:35:27'),(87,'127.0.0.1','2018-05-15 14:35:29'),(88,'0:0:0:0:0:0:0:1','2018-05-15 14:35:29'),(89,'127.0.0.1','2018-05-15 14:39:24'),(90,'127.0.0.1','2018-05-15 14:39:26'),(91,'0:0:0:0:0:0:0:1','2018-05-15 14:39:26'),(92,'127.0.0.1','2018-05-15 14:57:14'),(93,'127.0.0.1','2018-05-15 14:57:15'),(94,'0:0:0:0:0:0:0:1','2018-05-15 14:57:16'),(95,'0:0:0:0:0:0:0:1','2018-05-15 14:57:24'),(96,'0:0:0:0:0:0:0:1','2018-05-15 15:51:07'),(97,'127.0.0.1','2018-05-15 16:52:33'),(98,'127.0.0.1','2018-05-15 16:52:35'),(99,'0:0:0:0:0:0:0:1','2018-05-15 16:52:36'),(100,'127.0.0.1','2018-05-15 17:05:07'),(101,'127.0.0.1','2018-05-15 17:05:08'),(102,'0:0:0:0:0:0:0:1','2018-05-15 17:05:09'),(103,'127.0.0.1','2018-05-15 17:09:39'),(104,'127.0.0.1','2018-05-15 17:09:41'),(105,'0:0:0:0:0:0:0:1','2018-05-15 17:09:42'),(106,'127.0.0.1','2018-05-15 17:23:53'),(107,'127.0.0.1','2018-05-15 17:23:55'),(108,'0:0:0:0:0:0:0:1','2018-05-15 17:23:55'),(109,'127.0.0.1','2018-05-15 17:30:38'),(110,'127.0.0.1','2018-05-15 17:30:40'),(111,'0:0:0:0:0:0:0:1','2018-05-15 17:30:41'),(112,'127.0.0.1','2018-05-15 17:41:24'),(113,'127.0.0.1','2018-05-15 17:41:25'),(114,'0:0:0:0:0:0:0:1','2018-05-15 17:41:26'),(115,'127.0.0.1','2018-05-15 17:50:08'),(116,'127.0.0.1','2018-05-15 17:50:10'),(117,'0:0:0:0:0:0:0:1','2018-05-15 17:50:11'),(118,'0:0:0:0:0:0:0:1','2018-05-15 17:50:24'),(119,'127.0.0.1','2018-05-15 17:56:47'),(120,'127.0.0.1','2018-05-15 17:56:49'),(121,'0:0:0:0:0:0:0:1','2018-05-15 17:56:49'),(122,'127.0.0.1','2018-05-15 19:46:23'),(123,'127.0.0.1','2018-05-15 19:46:25'),(124,'0:0:0:0:0:0:0:1','2018-05-15 19:46:26'),(125,'0:0:0:0:0:0:0:1','2018-05-15 19:46:43');
