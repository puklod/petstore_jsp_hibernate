CREATE DATABASE  IF NOT EXISTS `petstore_jsp_hibernate` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `petstore_jsp_hibernate`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: petstore_jsp_hibernate
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `animal_id_sequence`
--

DROP TABLE IF EXISTS `animal_id_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `animal_id_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal_id_sequence`
--

LOCK TABLES `animal_id_sequence` WRITE;
/*!40000 ALTER TABLE `animal_id_sequence` DISABLE KEYS */;
INSERT INTO `animal_id_sequence` VALUES (1);
/*!40000 ALTER TABLE `animal_id_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dog`
--

DROP TABLE IF EXISTS `dog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dog` (
  `id` int NOT NULL,
  `size` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `store_id` int DEFAULT NULL,
  `age` double DEFAULT NULL,
  `fur_color` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `fur_length` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8ui0pms5frh2utkn5dtc8jdo3` (`store_id`),
  CONSTRAINT `FK_8ui0pms5frh2utkn5dtc8jdo3` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dog`
--

LOCK TABLES `dog` WRITE;
/*!40000 ALTER TABLE `dog` DISABLE KEYS */;
/*!40000 ALTER TABLE `dog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fish`
--

DROP TABLE IF EXISTS `fish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fish` (
  `id` int NOT NULL,
  `size` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `store_id` int DEFAULT NULL,
  `color` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `tank_size_requirment` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e50kbg1p13kxy0m0njyxm8rm1` (`store_id`),
  CONSTRAINT `FK_e50kbg1p13kxy0m0njyxm8rm1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fish`
--

LOCK TABLES `fish` WRITE;
/*!40000 ALTER TABLE `fish` DISABLE KEYS */;
/*!40000 ALTER TABLE `fish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parrot`
--

DROP TABLE IF EXISTS `parrot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parrot` (
  `id` int NOT NULL,
  `size` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `store_id` int DEFAULT NULL,
  `cage_size_requirment` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `feather_color` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `can_talk` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jtd4aim7j177dhlr3u3qbp3nb` (`store_id`),
  CONSTRAINT `FK_jtd4aim7j177dhlr3u3qbp3nb` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parrot`
--

LOCK TABLES `parrot` WRITE;
/*!40000 ALTER TABLE `parrot` DISABLE KEYS */;
/*!40000 ALTER TABLE `parrot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snake`
--

DROP TABLE IF EXISTS `snake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `snake` (
  `id` int NOT NULL,
  `size` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `store_id` int DEFAULT NULL,
  `color` varchar(255) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `venomous` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rmnvjl2kap2u6340igmv66tab` (`store_id`),
  CONSTRAINT `FK_rmnvjl2kap2u6340igmv66tab` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snake`
--

LOCK TABLES `snake` WRITE;
/*!40000 ALTER TABLE `snake` DISABLE KEYS */;
/*!40000 ALTER TABLE `snake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `privileg` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `user_name` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_hungarian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','administrator','admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-16  8:14:26
