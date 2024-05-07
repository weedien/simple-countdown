-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: remote    Database: countdown
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Current Database: `countdown`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `countdown` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;

USE `countdown`;

--
-- Table structure for table `countdowns`
--

DROP TABLE IF EXISTS `countdowns`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countdowns`
(
    `id`         bigint                                                       NOT NULL COMMENT '雪花算法生成id',
    `nid`        bigint                                                                DEFAULT NULL COMMENT '通知任务id',
    `query_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '查询码',
    `expire_at`  datetime                                                     NOT NULL COMMENT '到期时间',
    `remark`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         DEFAULT NULL COMMENT '备注信息',
    `message`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         DEFAULT NULL COMMENT '消息（倒计时结束返回）',
    `created_at` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`    tinyint                                                      NOT NULL DEFAULT '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `query_code_index` (`query_code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='倒计时信息表\r\n倒计时一旦创建，只能取消不能修改';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countdowns`
--

LOCK TABLES `countdowns` WRITE;
/*!40000 ALTER TABLE `countdowns`
    DISABLE KEYS */;
INSERT INTO `countdowns`
VALUES (544185971052613, NULL, '441591', '2024-05-06 19:28:25', NULL, 'Time\'s up!', '2024-05-06 19:18:25',
        '2024-05-06 19:18:25', 0),
       (1778681057168592896, NULL, '771776', '2024-04-12 15:16:25', NULL, 'Time\'s up!', '2024-04-12 15:06:25',
        '2024-04-12 15:06:25', 0),
       (1779048266386640896, NULL, '670966', '2024-04-13 20:25:34', NULL, 'Time\'s up!', '2024-04-13 15:25:34',
        '2024-04-13 15:25:34', 0),
       (1781973558407204864, NULL, '214152', '2024-05-06 17:09:38', 'abstinence', 'A day of freedom was granted.',
        '2024-04-21 17:09:38', '2024-04-21 17:09:38', 0),
       (1786269967109328896, NULL, '527351', '2024-05-21 13:42:02', 'caged', 'Time\'s up!', '2024-05-03 13:42:02',
        '2024-05-03 13:42:02', 0),
       (1786270969963220992, NULL, '530802', '2024-05-21 13:46:01', NULL, 'Time\'s up!', '2024-05-03 13:46:01',
        '2024-05-03 13:46:01', 0),
       (1786271304568016896, NULL, '806359', '2024-05-21 13:47:21', 'caged with lid lock',
        'A day of freedom was granted.', '2024-05-03 13:47:21', '2024-05-03 13:47:21', 0);
/*!40000 ALTER TABLE `countdowns`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2024-05-07 15:36:21
