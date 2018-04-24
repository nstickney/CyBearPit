-- MySQL dump 10.16  Distrib 10.1.32-MariaDB, for Linux (x86_64)
--
-- Host: 172.17.0.1    Database: beanpollDS
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Announcement`
--

DROP TABLE IF EXISTS `Announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Announcement` (
  `id` varchar(255) NOT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `published` datetime NOT NULL,
  `contest_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2k94u55knoy3dv5yi26eddwug` (`name`),
  KEY `FK515u1rur5yydx9dit41brdc1i` (`contest_id`),
  CONSTRAINT `FK515u1rur5yydx9dit41brdc1i` FOREIGN KEY (`contest_id`) REFERENCES `Contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Announcement`
--

LOCK TABLES `Announcement` WRITE;
/*!40000 ALTER TABLE `Announcement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Capturable`
--

DROP TABLE IF EXISTS `Capturable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Capturable` (
  `id` varchar(255) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `pointValue` int(11) NOT NULL,
  `contest_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa8ag5kv6uiyy16tkc9rbj4l2m` (`contest_id`),
  CONSTRAINT `FKa8ag5kv6uiyy16tkc9rbj4l2m` FOREIGN KEY (`contest_id`) REFERENCES `Contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Capturable`
--

LOCK TABLES `Capturable` WRITE;
/*!40000 ALTER TABLE `Capturable` DISABLE KEYS */;
INSERT INTO `Capturable` VALUES ('2fd71b73-6b7c-46d6-ab28-7f3363cac465','TEST_CAPTURABLE_FLAG','TestCapturable',1234,'a51c930e-ea6a-4c27-8764-06ee921dca3b');
/*!40000 ALTER TABLE `Capturable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Captured`
--

DROP TABLE IF EXISTS `Captured`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Captured` (
  `id` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  `capturable_id` varchar(255) DEFAULT NULL,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1erhg8vybek8vbr2ye8e5thwg` (`capturable_id`),
  KEY `FKnbgtpm5xo2bjdpofwnpgxkcjj` (`team_id`),
  CONSTRAINT `FK1erhg8vybek8vbr2ye8e5thwg` FOREIGN KEY (`capturable_id`) REFERENCES `Capturable` (`id`),
  CONSTRAINT `FKnbgtpm5xo2bjdpofwnpgxkcjj` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Captured`
--

LOCK TABLES `Captured` WRITE;
/*!40000 ALTER TABLE `Captured` DISABLE KEYS */;
/*!40000 ALTER TABLE `Captured` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contest`
--

DROP TABLE IF EXISTS `Contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contest` (
  `id` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `ends` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `running` bit(1) NOT NULL,
  `starts` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7uabp24970ktvntu4ajw95wgp` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contest`
--

LOCK TABLES `Contest` WRITE;
/*!40000 ALTER TABLE `Contest` DISABLE KEYS */;
INSERT INTO `Contest` VALUES ('a51c930e-ea6a-4c27-8764-06ee921dca3b','\0','2018-05-31 18:00:00','TestContest','\0','2018-01-01 08:00:00');
/*!40000 ALTER TABLE `Contest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Parameter`
--

DROP TABLE IF EXISTS `Parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Parameter` (
  `id` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `resource_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK503lk5v90fhygu9d9nqpck274` (`resource_id`),
  CONSTRAINT `FK503lk5v90fhygu9d9nqpck274` FOREIGN KEY (`resource_id`) REFERENCES `Resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Parameter`
--

LOCK TABLES `Parameter` WRITE;
/*!40000 ALTER TABLE `Parameter` DISABLE KEYS */;
INSERT INTO `Parameter` VALUES ('b7e9aac3-990e-4f48-a513-6a49d9debcf8','HTTP_RESOLVER',NULL,'d24ceab6-b371-42ee-9de5-9b94814300f3');
/*!40000 ALTER TABLE `Parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Poll`
--

DROP TABLE IF EXISTS `Poll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Poll` (
  `id` varchar(255) NOT NULL,
  `results` longtext,
  `score` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `resource_id` varchar(255) DEFAULT NULL,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg6m4whkqocpm4ratwcrkopog8` (`resource_id`),
  KEY `FKr3bl19afpvfbpmxxu4v3p5mk8` (`team_id`),
  CONSTRAINT `FKg6m4whkqocpm4ratwcrkopog8` FOREIGN KEY (`resource_id`) REFERENCES `Resource` (`id`),
  CONSTRAINT `FKr3bl19afpvfbpmxxu4v3p5mk8` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Poll`
--

LOCK TABLES `Poll` WRITE;
/*!40000 ALTER TABLE `Poll` DISABLE KEYS */;
/*!40000 ALTER TABLE `Poll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report`
--

DROP TABLE IF EXISTS `Report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report` (
  `id` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `fileType` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `uploaded` longblob,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7a2by1jojtftqnfwha7vtvv2x` (`team_id`),
  CONSTRAINT `FK7a2by1jojtftqnfwha7vtvv2x` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report`
--

LOCK TABLES `Report` WRITE;
/*!40000 ALTER TABLE `Report` DISABLE KEYS */;
/*!40000 ALTER TABLE `Report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Resource`
--

DROP TABLE IF EXISTS `Resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Resource` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `available` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `pointValue` int(11) NOT NULL,
  `port` int(11) NOT NULL,
  `timeout` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `contest_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t51o07lcts4qhxgwwujbg51u5` (`name`),
  KEY `FKaeuhwpm0bodw0i5w4eflck668` (`contest_id`),
  CONSTRAINT `FKaeuhwpm0bodw0i5w4eflck668` FOREIGN KEY (`contest_id`) REFERENCES `Contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Resource`
--

LOCK TABLES `Resource` WRITE;
/*!40000 ALTER TABLE `Resource` DISABLE KEYS */;
INSERT INTO `Resource` VALUES ('d24ceab6-b371-42ee-9de5-9b94814300f3','httpbin.org','','TestHTTP',1,80,3,1,'a51c930e-ea6a-4c27-8764-06ee921dca3b');
/*!40000 ALTER TABLE `Resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceTeams`
--

DROP TABLE IF EXISTS `ResourceTeams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceTeams` (
  `resource_id` varchar(255) NOT NULL,
  `team_id` varchar(255) NOT NULL,
  KEY `FKcq21apcdolbcl2hlivf9lnf61` (`team_id`),
  KEY `FK76i3aw87u6rmifyj7ey478fpd` (`resource_id`),
  CONSTRAINT `FK76i3aw87u6rmifyj7ey478fpd` FOREIGN KEY (`resource_id`) REFERENCES `Resource` (`id`),
  CONSTRAINT `FKcq21apcdolbcl2hlivf9lnf61` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceTeams`
--

LOCK TABLES `ResourceTeams` WRITE;
/*!40000 ALTER TABLE `ResourceTeams` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResourceTeams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Response`
--

DROP TABLE IF EXISTS `Response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Response` (
  `id` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `fileType` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `uploaded` longblob,
  `task_id` varchar(255) DEFAULT NULL,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5xge2bnxt4feaktg38j3c8ino` (`task_id`),
  KEY `FKls149ru2pkddji2pf9tep6k2` (`team_id`),
  CONSTRAINT `FK5xge2bnxt4feaktg38j3c8ino` FOREIGN KEY (`task_id`) REFERENCES `Task` (`id`),
  CONSTRAINT `FKls149ru2pkddji2pf9tep6k2` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Response`
--

LOCK TABLES `Response` WRITE;
/*!40000 ALTER TABLE `Response` DISABLE KEYS */;
/*!40000 ALTER TABLE `Response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `id` varchar(255) NOT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `due` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `pointValue` int(11) NOT NULL,
  `published` datetime DEFAULT NULL,
  `contest_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_awswgpgqdgcos1g5t6wehc24m` (`name`),
  KEY `FKlfsq4vfeyb8al1j8rcwyuwvtx` (`contest_id`),
  CONSTRAINT `FKlfsq4vfeyb8al1j8rcwyuwvtx` FOREIGN KEY (`contest_id`) REFERENCES `Contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
INSERT INTO `Task` VALUES ('d59f69a0-b789-40ca-a45d-d1c5bd8adceb','Upload a file.','2018-05-31 18:00:00','TestTask',100,'2018-01-01 08:00:00','a51c930e-ea6a-4c27-8764-06ee921dca3b');
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Team`
--

DROP TABLE IF EXISTS `Team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Team` (
  `id` varchar(255) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `contest_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26buvs5vcmmkhh8o5rcd5m7kd` (`contest_id`),
  CONSTRAINT `FK26buvs5vcmmkhh8o5rcd5m7kd` FOREIGN KEY (`contest_id`) REFERENCES `Contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Team`
--

LOCK TABLES `Team` WRITE;
/*!40000 ALTER TABLE `Team` DISABLE KEYS */;
INSERT INTO `Team` VALUES ('744dce04-1126-4d65-a2f9-daebb8cfec5b','BONUSPOINTS','TestTeam','a51c930e-ea6a-4c27-8764-06ee921dca3b');
/*!40000 ALTER TABLE `Team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` varchar(255) NOT NULL,
  `admin` bit(1) NOT NULL,
  `judge` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `secret` varchar(255) NOT NULL,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_syftr7gx86fwf7ox7bgvnnta7` (`name`),
  KEY `FK7ymuswrn4uveb9skowwwms1wg` (`team_id`),
  CONSTRAINT `FK7ymuswrn4uveb9skowwwms1wg` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('f91405ca-5750-4845-82c5-c8c40abc81fb','\0','\0','TestTeamUser','8b8e089c-c093-4541-997a-7fa6d5efe202','$argon2id$v=19$m=1024,t=2,p=2$qdiP4n1m7AS9/SM5L1XJ3g$vP/2EcD/jtS67d3RzKSR0QTORivPPSiC0MG9EGwkIIc','744dce04-1126-4d65-a2f9-daebb8cfec5b'),('UA','','','beanpoll','b97ebe9b-4db5-4dc4-8c31-c1d46df88d83','$argon2id$v=19$m=1024,t=2,p=2$/bDvwgcKFx/SLUhz768+mQ$qdptwK3x7Z8u6/LBE9FjW0+IAZtVINmczJjEOGmhBUc',NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-24 16:36:48
