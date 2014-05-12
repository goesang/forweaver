drop database forweaver;
create database forweaver;
use forweaver

drop table Lecture;
CREATE TABLE `Lecture` (
  `lectureID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL unique,
  `description` varchar(300) NOT NULL,
  `category` tinyint NOT NULL,
  `openingDate` datetime NOT NULL,
  `deadLine` datetime DEFAULT NULL,
  `creatorName` varchar(20) NOT NULL,
  `creatorEmail` varchar(30) NOT NULL,
  `imgSrc` blob NOT NULL,
  `push` int Unsigned DEFAULT '0',
  PRIMARY KEY (`lectureID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `Lecture` VALUES (1,'c-charp2','C샾 프로그래밍을 배워보는 강좌입니다',0,'2013-09-04 21:28:18',NULL,'hello','goesanghan@gmail.com');

drop table Repo;
CREATE TABLE `Repo` (
  `repoID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(300) NOT NULL,
  `category` tinyint NOT NULL,
  `openingDate` datetime NOT NULL,
  `deadLine` datetime DEFAULT NULL,
  `lectureName` varchar(30) NOT NULL,
  PRIMARY KEY (`repoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `Repo` VALUES (1,'example','강의 자료를 올릴 수 있는 저장소입니다.',0,'2013-09-04 21:28:19',NULL,'c-charp2'),(2,'wwwwe','그냥 만들어 봄',0,'2013-09-04 23:09:46',NULL,'c-charp2'),(3,'c-charp5','그냥 만들어 봄',0,'2013-09-04 23:24:43',NULL,'c-charp2'),(4,'sdsd66','그냥 만들어 봄',0,'2013-09-04 23:48:17',NULL,'c-charp2');

drop table Project;
CREATE TABLE `Project` (
  `projectID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL unique,
  `description` varchar(300) NOT NULL,
  `category` tinyint NOT NULL,
  `openingDate` datetime NOT NULL,
  `deadLine` datetime DEFAULT NULL,
  `creatorName` varchar(20) NOT NULL,
  `creatorEmail` varchar(30) NOT NULL,
  `imgSrc` blob NOT NULL,
  `push` int Unsigned DEFAULT '0',
  PRIMARY KEY (`projectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Project` VALUES (1,'pro/forweaver','학생들을 위한 소셜 코딩을 목표로 제작중인 프로젝트입니다.',0,'2013-09-02 17:00:11',NULL,'hello','goesanghan@gmail.com'),(2,'hello/minsoddd','그냥 만들어 봄',0,'2013-09-02 16:46:52',NULL,'hello','goesanghan@gmail.com'),(3,'olleh/java','자바 강의',0,'2013-09-04 00:27:56',NULL,'olleh','rootroot2@gmail.com');

drop table Pass;
CREATE TABLE `Pass` (
  `passID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `weaverNickName` varchar(20) NOT NULL,
  `kind` tinyint NOT NULL,
  `joinName` varchar(30) NOT NULL,
  `joinDate` datetime NOT NULL,
  `permission` tinyint NOT NULL,
  PRIMARY KEY (`passID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Pass` VALUES (1,'hello',0,'ROLE_USER','2013-09-01 18:59:56',0),(15,'aaaa',0,'ROLE_USER','2013-09-02 02:27:54',0),(16,'hello',2,'hello/minsoddd','2013-09-02 16:46:52',1),(17,'hello',2,'hello/kkkk','2013-09-02 17:00:12',1),(18,'olleh',0,'ROLE_USER','2013-09-04 00:16:46',0),(20,'olleh',2,'olleh/java','2013-09-04 00:27:57',1),(21,'hello',1,'c-charp2','2013-09-04 21:28:19',1),(22,'test',0,'ROLE_USER','2013-09-12 10:51:52',0);

drop table Weaver;
CREATE TABLE `Weaver` (
  `nickName` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL unique,
  `password` varchar(20) DEFAULT NULL,
  `imgSrc`blob NOT NULL,
  `twitterID` bigint Unsigned DEFAULT '0',
  `socialMode` tinyint Unsigned DEFAULT '0',
  PRIMARY KEY (`nickName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `Weaver` VALUES ('hello','goesanghan@gmail.com','1234','','');


drop table Post;
CREATE TABLE `Post` (
  `postID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL,
  `content` text DEFAULT NULL,
  `isLong` bool NOT NULL,
  `kind` tinyint NOT NULL,
  `created` datetime NOT NULL,
  `recentRePostDate` datetime DEFAULT NULL,
  `writerName` varchar(20) NOT NULL,
  `writerEmail` varchar(30) NOT NULL,
  `imgSrc`blob NOT NULL,
  `push` int Unsigned DEFAULT '0',
  `rePostCount` int Unsigned DEFAULT '0',
  PRIMARY KEY (`postID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Post` VALUES (1,'자바에 관한 글을 하나 써봅니다!','',0,2,'2013-09-12 10:31:10','hello','goesanghan@gmail.com',0),(2,'hello님에게 드릴 말씁이 있습니다!','',0,2,'2013-09-12 10:44:16','olleh','rootroot2@gmail.com',0),(3,'두분께 할말있는데 들리시나요?','',0,2,'2013-09-12 10:52:21','test','rootroot2@gmail.com',0),(4,'프로젝트 minsoddd에서 글을 써봄','',0,1,'2013-09-12 12:08:05','hello','goesanghan@gmail.com',0),(5,'자바 프로그래밍에 관한 짧은 글','',0,0,'2013-09-12 13:21:36','hello','goesanghan@gmail.com',0),(6,'프로그래밍에 관한 글을 하나 써봅니다!','',0,0,'2013-09-12 17:49:18','hello','goesanghan@gmail.com',0),(7,'자바에 관한 글을 하나 써봅니다!','',0,0,'2013-09-12 17:51:25','hello','goesanghan@gmail.com',0),(8,'자바에 관한 글을 하나 써봅니다!','',0,0,'2013-09-12 17:55:40','hello','goesanghan@gmail.com',0);


drop table RePost;
CREATE TABLE `RePost` (
  `originalPostID` bigint Unsigned DEFAULT NULL, 
  `rePostID` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL,
  `content` text DEFAULT NULL,
  `isLong` bool NOT NULL,
  `kind` tinyint NOT NULL,
  `created` datetime NOT NULL,
  `writerName` varchar(20) NOT NULL,
  `writerEmail` varchar(30) NOT NULL,
  `imgSrc` blob NOT NULL,
  `push` int Unsigned DEFAULT '0',
  PRIMARY KEY (`rePostID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table Data;
CREATE TABLE `Data` (
  `dataID` bigint Unsigned NOT NULL AUTO_INCREMENT, 
  `postID` bigint Unsigned NOT NULL ,
  `image` mediumblob NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `kind` tinyint NOT NULL,
  PRIMARY KEY (`dataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table Tag;
CREATE TABLE `Tag` (
  `tagID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `tagName` varchar(20) NOT NULL,
  PRIMARY KEY (`tagID`),UNIQUE (tagName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `Tag` VALUES (1,'$aaa'),(2,'$hello'),(3,'$olleh'),(4,'@hello/minsoddd'),(5,'자바'),(6,'프로그래밍');


CREATE TABLE `PAT` (
  `postID` bigint Unsigned NOT NULL,
  `tagID` bigint Unsigned NOT NULL,
  PRIMARY KEY (`postID`,`tagID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `PAT` VALUES (1,1),(2,2),(3,2),(3,3),(4,4),(5,5),(5,6),(6,6),(7,6),(8,6);


drop table ProAT;

CREATE TABLE `ProAT` (
  `projectID` bigint Unsigned NOT NULL,
  `tagID` bigint Unsigned NOT NULL,
  PRIMARY KEY (`projectID`,`tagID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ProAT` VALUES (1,1),(1,6),(1,7);

drop table LAT;
CREATE TABLE `LAT` (
  `lectureID` bigint Unsigned NOT NULL,
  `tagID` bigint Unsigned NOT NULL,
  PRIMARY KEY (`lectureID`,`tagID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `LAT` VALUES (1,5),(1,6);

drop table WaitJoin;
CREATE TABLE `WaitJoin` (
  `waitJoinID` bigint Unsigned NOT NULL AUTO_INCREMENT,
  `joinTeam` varchar(30) NOT NULL,
  `proposer` varchar(20) NOT NULL,
  `waitingWeaver` varchar(20) NOT NULL,
  `postID` bigint Unsigned NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`waitJoinID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
