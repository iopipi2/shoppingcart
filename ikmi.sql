-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: shoppingcart
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bill_date` timestamp NULL DEFAULT NULL,
  `price_total` mediumint DEFAULT NULL,
  `buyer_id` int DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_user_idx` (`buyer_id`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,'2022-07-09 17:00:00',600,52,'done'),(2,'2022-06-30 17:00:00',1000,90,'pending'),(3,'2022-06-24 17:00:00',150,52,'done');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_line`
--

DROP TABLE IF EXISTS `cart_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_line` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `cart_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_product` (`product_id`),
  KEY `fk_cart_bill_idx` (`cart_id`),
  CONSTRAINT `fk_cart_cartline` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_line`
--

LOCK TABLES `cart_line` WRITE;
/*!40000 ALTER TABLE `cart_line` DISABLE KEYS */;
INSERT INTO `cart_line` VALUES (7,20,1,2),(15,20,NULL,5),(16,45,NULL,2),(29,3,NULL,1),(30,1,NULL,1),(31,1,NULL,1),(32,15,NULL,3),(33,5,NULL,1);
/*!40000 ALTER TABLE `cart_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id_cate` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `active` tinyint NOT NULL,
  PRIMARY KEY (`id_cate`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Women',1),(2,'Men',1),(3,'Bag',1),(4,'Shoes',1),(5,'Watches',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin NOT NULL,
  `price` bigint NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `categories_id` int DEFAULT NULL,
  `img_main` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `img_hover` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `img_sub` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `active` tinyint DEFAULT NULL,
  `product_quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pro_cate_idx` (`categories_id`),
  CONSTRAINT `fk_pro_cate` FOREIGN KEY (`categories_id`) REFERENCES `category` (`id_cate`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Áo T-Shirt Oversize',20,'<div><ul><li>Chất liệu: Cotton 230 GSM</li><li> Sử dụng công nghệ in ép nhiệt chất lượng cao</li><li> Độ bền cao, lộn trái áo khi giặt</li><ul></div>',2,'product1.2.jpg','product1.1.jpg','product1.3.jpg',1,0),(2,'Áo T-Shirt Blood Diamond',40,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'blooddiamond2.jpg','blooddiamond1.jpg','blooddiamond3.jpg',1,0),(3,'Áo T-Shirt Basic Over',120,'Chất liệu 100% cotton, sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, không bị bong tróc khi giặt',2,'basicover1.jpg','basicover2.jpg','basicover3.jpg',1,0),(4,'Áo T-Shirt Racing',120,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'racing1.jpg','racing2.jpg','racing3.jpg',1,0),(5,'Áo T-Shirt Teddie',120,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'teddie1.jpg','teddie2.jpg','teddie3.jpg',1,0),(6,'Áo T-Shirt King of School ',120,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'KingofSchool1.jpg','kingofschool2.jpg','kingofschool3.jpg',1,0),(7,'Áo T-Shirt Skull ',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'skull1.jpg','skull2.jpg','skull3.jpg',1,0),(8,'Áo T-Shirt Agent',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'agent1.jpg','agent2.jpg','agent3.jpg',1,0),(9,'Áo T-Shirt Waster',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'8.1.jpg','8.2.jpg','8.3.jpg',1,0),(10,'Áo T-Shirt Skull ',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'10-1.jpg','10-2.jpg','10-3.jpg',1,0),(11,'Áo T-Shirt Skull ',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',2,'skull1.jpg','skull2.jpg','skull3.jpg',1,0),(14,'Áo T-Shirt Basic',35,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',1,'product-01.jpg','product-012.jpg','product-013.jpg',1,0),(15,'Áo T-Shirt Slevee',35,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',1,'product-161.jpg','product-162.jpg','product-163.jpg',1,0),(16,'Áo T-Shirt Black',35,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',1,'chi-ghi-trc.jpg','chi-ghi-sau.jpg','chi-trang-sau.jpg',1,0),(17,'Áo T-Shirt Color',70,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',1,'v1-vang.png','0c530625-700d-4923-8268-43af5e699580.jpg','58bb88e1-5dbe-44de-a5a1-77ad1aff1131.jpg',1,0),(18,'Áo COAST Brown',120,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',1,'clowz-5-1-2.jpg','clowz-5-9.jpg','clowz-5-3.jpg',1,0),(19,'Converse Basic',100,'Chất liệu: Cotton 230 GSM, Sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, lộn trái áo khi giặt',4,'product-09.jpg','product092.jpg','product-093.jpg',1,0),(20,'Đồng hồ Basic',300,'Chất liệu 100% cotton, sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, không bị bong tróc khi giặt',5,'product-06.jpg','product-062.jpg','product-063.jpg',1,0),(21,'Đồng hồ BlackPanther',300,'Chất liệu 100% cotton, sử dụng công nghệ in ép nhiệt chất lượng cao. Độ bền cao, không bị bong tróc khi giặt',5,'product-15.jpg','product-152.jpg','product-153.jpg',1,0),(42,'Túi Utility',35,'Size: 23x16x8 cm\r , Mô tả: Túi 3 ngăn in logo\r,  Dây quai đeo: Họa tiết caro đen trắng in chạy dọc quai đeo',3,'bag1.jpg','bag3.jpg','bag2.jpg',1,0),(43,' Túi Ulitity SATCHEL',40,'Dây quai đeo: Họa tiết caro đen trắng in chạy dọc quai đeo. Dây đeo có thể điều chỉnh hoặc tháo rời, Thiết kế, chất liệu, và hình in chất lượng cao.',3,'clowz-1-1.jpg','clowz1-2.jpg','clowz-3-1.jpg',1,0),(45,'Túi Crytal Clear',35,'Dây đeo: Chữ \"CLOWNZ\" thiết kế theo phong cách graphic in chìm, dây đeo dài có thể điều chỉnh và tháo rời.Chất liệu: Nhựa PU, Thiết kế, chất liệu, và hình in chất lượng cao.',3,'clear-bag-black.jpg','1a7ff27f-75e2-4917-98bb-6d62bd9c3518.jpg','35e41285-3b94-4dca-96de-fff0b8b19273.jpg',1,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mail` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT NULL,
  `role` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `enabled` tinyint DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  `one_time_password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hoangnh180897@gmail.com','$2a$10$QhXuxFd2CugaZT.hiLxeK.GNIetEd4IMAqPnUeRzjzSMoYbNgQBC6','Nguyễn Huy Hoàng','0962029497','CT3B, KĐT Nam Cường, Cổ Nhuế 1, Quận Bắc Từ Liêm, HN','2022-07-19 03:55:55','ROLE_ADMIN',1,'hoangvip (1).jpg',NULL),(52,'iamghost06@gmail.com','123456','Nguyen Ngoc Bach',NULL,NULL,NULL,'ROLE_USER',1,NULL,NULL),(54,'iamghost827@gmail.com','123456','Nguyễn Ngọc Bách','0969374719','64, Nguyễn Văn Trỗi, Hà Đông, Hà Nội',NULL,'ROLE_ADMIN',1,'ba2b7156370cf989082c7daa8a8c336f.jpg',NULL),(90,'ngocbachnguyen100@gmail.com','$2a$10$XZOHSDmtkUdck5M7NdelMe6Xnupo1csaviwZ3JLr8yoCUEnUYnRDm','Nguyễn Ngọc Bách',NULL,NULL,'2021-09-24 09:08:31','ROLE_USER',1,'69402738_2326329874274213_15742996465057792_n.jpg',NULL),(110,'lequocvietdp1999@gmail.com','$2a$10$Km6PlinGSHE0CsdWIIuql.7WrZDVFxbp3bt49k0Nhnw1/OHMNIyFe','Lê Quốc Việt',NULL,NULL,'2022-07-19 03:55:55','ROLE_USER',1,NULL,NULL);
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

-- Dump completed on 2022-07-28 14:03:00
