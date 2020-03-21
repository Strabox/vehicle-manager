

--
-- Current Database: `vehiclemanager`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `vehiclemanager` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vehiclemanager`;

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
-- Table structure for table `license`
--

DROP TABLE IF EXISTS `license`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `license` (
  `license` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`license`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `license`
--

LOCK TABLES `license` WRITE;
/*!40000 ALTER TABLE `license` DISABLE KEYS */;
INSERT INTO `license` VALUES ('25-76-OM','1999-11-01'),('30-38-BV','1996-03-30'),('76-DE-21','2007-02-01'),('89-30-OZ','2000-02-01'),('AR-68-23','1969-01-01'),('GI-48-39','1966-03-23');
/*!40000 ALTER TABLE `license` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,'Agria 7714 Nº723718'),(2,'Correias Ref: DSL B780 B29 =2'),(3,'Facas Ref: 0188'),(4,'Nº de facas: 14 + 14 = 28'),(5,'Facas ref: 0165'),(6,'Nº de facas: 12 + 12 = 24'),(7,'2 rolamentos 6205 2RS'),(8,'2 rolamentos 6204 2RS'),(9,'2 retentores'),(10,'Correia da Ventoinha Referência: QH QBB140'),(11,'Óleo motor: (SHEL HELIX  HX7 10W- 40) PAULO 30€'),(12,'Filtro de Ar: LX 504  MR 127077'),(13,'Chave do carter 17mm'),(14,'Adquirido no dia 19-11-2016 com 168.000 Km'),(16,'Bujom da caixa de velocidades = 2 chave quadrada de 8mm '),(17,'Bujom do motor = 1 chave caixa 21mm '),(18,'Caixa de velocidades leva 2 litros de oléo '),(19,'Motor leva 4,5 litros de oléo'),(20,'Referencia dos peneus 165/65 R14'),(21,'Retentor da caixa lado do condutor Ref.Citroên 1608816780'),(22,'Filtro de óleo do motor Ref. Citroen 1109AP '),(23,'Filtro de ar Ref. Citroen 1444VA '),(24,'Filtro de gasóleo Ref. Citroen E148086 '),(26,'Adquirida em 07-4-2017 com 194890 Km'),(28,'Referencia do filtro de ar K-A139 Mena Peças'),(34,'Antigo dono Aníbal Martins Tel.965 695 637'),(36,'Referencia das córreas de ventoinha (2) QH Ref. QBA987 10X987  Mena Peças'),(37,'Referencia do filtro de gasóleo Ref. K-FC158 Mena Peças'),(38,'Referencia do filtro de óleo do motor K-C115 Mena Peças'),(39,'Correa de distribuição Menapeças QH 89€ --- Optimol  77€--- B.Agua 26€'),(41,'Alavanca das mudanças dimensão ( FOL) Diâmetro maior 48 menor 12 '),(42,'Filtro de óleo do motor Ref. Mena Peças EOF005 '),(43,'Escovas L / Vidros 400mm NORMAL Ref. Mena Peças SS-X40C'),(44,'A revisão  dos 92836 Km foi feita pelo Mário da Alencauto no dia 31-10-2017'),(45,'Lampada do farol da frente Ref. casquillho P43T  Tipo H4  Potencia 60/55W'),(46,'Citroen acidentado no dia 20-10-2017 190.100 Km.  Recuperado no dia 21-01-2018 '),(47,'KIT Dustribuição KDD-T03  Termostato Quinton Hazell QTH321K  B/Agua GWT61A'),(49,'Motor Jonsered 630  nº 9120557  A B SWEDEN'),(50,'Tampa do deposito de expansão  da agua radiador Ref. 2339 1,4 bar'),(51,'O turbo foi substituido en Castelo Branco José Pinheiro');
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_task`
--

DROP TABLE IF EXISTS `notification_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_task` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `noti_date` date DEFAULT NULL,
  `notification_sent` bit(1) DEFAULT NULL,
  `expired` bit(1) DEFAULT NULL,
  `vehicle_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7qq0k4hxrv4vohntl18pnn1h0` (`vehicle_name`),
  CONSTRAINT `FK_7qq0k4hxrv4vohntl18pnn1h0` FOREIGN KEY (`vehicle_name`) REFERENCES `vehicle` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_task`
--

LOCK TABLES `notification_task` WRITE;
/*!40000 ALTER TABLE `notification_task` DISABLE KEYS */;
INSERT INTO `notification_task` VALUES ('NotificationTaskYear',1,'Mudar o óleo do motor','2017-12-17','',NULL,'Citroen Saxo'),('NotificationTaskYear',2,'Mudar o óleo do motor','2018-07-14','',NULL,'Mazda 3'),('NotificationTaskYear',3,'Mudar o óleo do motor','2017-03-20','',NULL,'Tractor Ford'),('NotificationTaskYear',4,'Mudar o óleo do motor','2017-07-05','',NULL,'Tractor Agria'),('NotificationTaskYear',5,'Mudar o óleo do motor','2017-03-12','',NULL,'Tractor MF');
/*!40000 ALTER TABLE `notification_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=368 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (1,'2015-04-25','Comprei manga extriada e balader da fresa novo',0),(2,'2015-07-05','Bateria nova',0),(3,'2015-07-05','Substituir o óleo do motor (15W40 Paulo)',0),(5,'2015-07-05','Substituir o óleo da caixa de velocidades',0),(6,'2015-07-05','Substituir o óleo da fresa',0),(7,'2015-07-05','Substituir as facas da fresa',0),(8,'2015-07-05','Substituir a parte de acrílico do fltro de ar',0),(9,'2009-04-19','Substituir roamantos do motor',0),(10,'2009-04-19','Substituir vedante da bomba',0),(11,'2009-04-19','Substituir turbina',0),(12,'2013-05-19','Substituir foles da transmição Diametro 68 - 22',0),(13,'2012-01-28','2 Rolamentos 6204',0),(14,'2012-01-28','2 Rolamentos 6303',0),(15,'2006-05-10','Reparar motor de arranque',0),(16,'2006-05-10','Montagem bomba de gasóleo',0),(17,'2006-05-10','Substituição carreto inversor da caixa',0),(18,'2010-03-10','Fabricado na Serra',0),(19,'2010-03-10','Montagem de um redutor novo',0),(20,'2007-06-16','Pintar de novo',0),(21,'2007-06-16','Reparar diferencial da frente',0),(22,'2007-06-16','Reparar diferencial de trás',0),(23,'2007-06-16','Substituir transmissão traseira',0),(24,'2007-06-16','Reparar caixa de velocidades',0),(25,'2007-06-16','Substituir bomba de óleo da caixa',0),(26,'2007-06-16','Substituir discos e vedantes da embraiagem',0),(27,'2007-06-16','Substituir todos os filtros',0),(28,'2007-06-16','Substituir todos os óleos',0),(29,'2007-06-16','Lubrificação geral',0),(30,'2007-06-16','Substituir bomba hidráulica (principal)',0),(31,'2007-06-16','Substituir tubo da caixa de velocidades',0),(32,'2007-05-26','2 retentores 50X62X8',0),(33,'2007-05-26','1 retentor 35X72X10',0),(34,'2007-05-27','2 rolamentos 6010',0),(35,'2007-05-28','2 freios veio diâmetro 35',0),(36,'2007-05-29','28 facas',0),(37,'2007-05-30','Substituir todos os óleos',0),(38,'2007-06-01','Redutor',0),(39,'2007-06-02','2 retentores 50X62X8',0),(40,'2007-06-03','1 retentor 35X72X10',0),(41,'2007-06-04','2 rolamentos 6010',0),(42,'2007-03-25','Montagem de duas novas baterias',0),(43,'2006-08-20','Reparar bomba',0),(44,'2006-08-20','Palhetas novas',0),(45,'2006-08-20','Todos os rolamentos',0),(46,'2009-08-13','Montagem de travão hidráulico',0),(47,'2012-12-27','Decapagem e pintura',0),(48,'2007-03-30','Substituir óleo da caixa e retficar tampa',0),(49,'2009-05-17','Substituir óleo do cárter',0),(50,'2013-09-19','Correia de ventoinha Ref: 10AV0700 Goodyear',0),(51,'2011-09-19','Montar novo vedante na bomba diâmetro 25mm',0),(52,'2009-08-13','Substituir óleo do cárter',0),(53,'2013-07-06','Substituir óleo do cárter',0),(54,'2014-07-30','Substituir óleo do cárter',0),(55,'2005-07-30','Adaptar bomba de água',0),(56,'2005-07-30','Montagem de um novo depósito',0),(57,'2008-10-25','Corrente nova',0),(58,'2013-09-18','Lâmina nova',0),(59,'2013-09-18','Corrente nova',0),(60,'2009-11-25','Corrente nova',0),(61,'2009-11-25','Diafragamo carburador',0),(62,'2009-11-25','Fol cabeça carburador',0),(63,'2009-11-25','Tubo de alimentação da gasolina',0),(64,'2009-11-25','Vela nova',0),(65,'2008-10-18','Corrente nova',0),(66,'2008-10-25','Cilindro VED.110X130X16 K33-110/2 PU =3',0),(67,'2008-10-25','Cilindro Raspador GA 80X90X7/10 =1',0),(68,'2008-10-25','Cilindro VED.80X90X13 K38-080/2 PU =2',0),(69,'2008-10-25','Diâmetro da camisa interior 130 mm',0),(70,'2008-10-25','Diâmetro da haste 80 mm',0),(71,'2015-05-15','Reparar macaco báscula',0),(72,'2011-01-20','Substituir todos os rolamentos da caixa de velocidade',0),(73,'2007-05-16','Reparar eixo da frente',0),(74,'2007-05-16','Reforçar apoio das rodas de trás',0),(75,'2009-05-08','Substituir pneus da frente',0),(76,'2009-05-08','Bateria nova',0),(77,'2007-01-02','Reparação geral',0),(78,'2007-01-02','Montagem de novo modelo de motor de arranque',0),(79,'2007-01-02','Montagem de nova bomba de óleo hidráulica',0),(80,'2007-01-02','Substituir veio do carreto da caixa de velocidades',0),(81,'2007-01-02','Substituir todos os filtros (filtro de ar novo modelo)',0),(82,'2007-01-02','Substituir todos os óleos',0),(83,'2007-01-02','Substituir ninho do radiador',0),(84,'2007-01-02','Montagem de novos manípulos hidráulicos',0),(85,'2007-01-02','Fabricar novo deposito de gasóleo',0),(86,'2007-01-02','Montagem de uma bomba eléctrica de gasóleo',0),(87,'2007-01-02','Reparar bomba injectora',0),(88,'2007-01-02','Substituir duas rotulas de direcção',0),(89,'2007-01-02','Substituir retentor da roda traseira lado direito',0),(90,'2007-01-02','Substituir pneus da frente',0),(91,'2007-01-02','Montagem de novo alternador',0),(92,'2007-01-02','Substituir retentor da tomada de força 35x72x10',0),(93,'2007-01-02','Substituir rolamento da tomada de força 6207=2',0),(94,'2007-01-02','Substituir retentor da cambota (poli)',0),(95,'2007-01-02','Substituir correia de ventoinha',0),(96,'2007-01-02','Adaptar manómetro de temperatura',0),(97,'2009-08-13','Substituir o óleo do carter',0),(98,'2009-08-13','Substituir o filtro de óleo',0),(99,'2009-08-13','Novo filtro de gasóleo',0),(100,'2009-08-13','Substituir líquido do radiador',0),(101,'2009-08-13','Montagem de novas pastilhas (móvel com sutamento)',0),(102,'2010-03-03','Reparar motor duas camisas novas segmentos e juntas',0),(103,'2010-03-03','Substituir o óleo do carter',0),(104,'2010-03-03','Substituir o filtro de óleo',0),(105,'2011-07-30','Substituir pastilhas de travão',0),(106,'2013-08-03','Substituir o óleo do carter 15W40 (Paulo)',0),(107,'2013-08-03','Substituir o filtro de óleo',0),(108,'2013-08-03','Substituir o oleo da caixa  Ref: THB68',0),(109,'2016-03-12','Substituir óleo do motor 15W40 GALP',0),(111,'1996-04-11','Substituir bicha do conta rotações',357),(112,'1996-05-06','Reparar diferencial da frente',370),(113,'1998-06-01','Substituir correia de ventoinha LUCAS KDB950(9.5X950)',636),(114,'1998-06-01','Substituir bicha do conta rotações',636),(115,'1998-06-01','Substituir correa do alternador AVx10x685',636),(116,'1998-06-01','Substituir rolamentos do rolo tensor 2-6301',636),(117,'2001-05-01','Revisão das 1000 horas',1027),(118,'2001-05-01','Substituir filtro de óleo',1027),(119,'2001-05-01','Substituir óleo do motor',1027),(120,'2001-05-01','Substituir óleo do diferencial da frente',1027),(121,'2001-05-01','Substituir óleo da caixa de velocidades',1027),(122,'2001-05-01','Substituir filtro de gasóleo',1027),(123,'2007-09-17','Substituir filtro de ar',1127),(124,'2008-08-15','Medição de horas',1703),(125,'2008-10-19','Medição de horas',1710),(126,'2009-08-13','Substituir óleo do motor',1786),(127,'2009-08-13','Substituir filtro do óleo',1786),(128,'2009-08-13','Substituir filtro do gasóleo',1786),(129,'2009-08-13','Substituir correia de ventoinha QH QBA 950',1786),(130,'2009-08-13','Substituir correia do alternador QH QBA 687',1786),(131,'2011-04-12','Reparar diferencial da frente (Eixo central)',1902),(132,'2011-04-12','Montar distribuidor hidráulico novo',1902),(133,'2013-09-22','Medição de horas',1978),(134,'2014-06-01','Medição de horas',2000),(135,'2015-06-27','Revisão das 2000 Horas',2039),(136,'2015-06-27','Substituir óleo da caixa',2039),(137,'2015-06-27','Substituir o oléo do motor 15w40 (Paulo)',2039),(138,'2015-06-27','Substituir o filtro de óleo do motor Ref: 01.09.OC115 Tomar',2039),(139,'2015-06-27','Substituir o filtro de ar Ref: 01.09.CH1245K Tomar',2039),(140,'2016-01-12','Medição de horas',2060),(141,'2016-03-12','Reparar fuga de óleo roda da frente esquerda',2100),(142,'2013-04-25','Substituir filtro de óleo do motor',7300),(143,'2013-04-25','Substituir filtro de gasóleo = 2',7300),(144,'2013-04-25','Substituir óleo do diferencial',7300),(145,'2013-04-25','Substituir óleo da caixa de velocidades',7300),(146,'2013-04-25','Substituir filtro de óleo do motor',7300),(147,'2013-04-25','Substituir água do radiador',7300),(148,'2013-04-25','Lubrificação geral',7300),(149,'2013-04-25','Substituir correia da ventoinha Referência: QH QBB140',7300),(150,'2013-04-25','Substituir óleo da bomba injectora',7300),(151,'2013-04-25','Bateria nova',7300),(152,'2013-04-25','Montagem de manómetro de temperatura',7300),(153,'2013-04-25','Chapa de matrícula a frente nova',7300),(154,'2013-04-25','Montagem de puxo de reboque',7300),(155,'2013-05-19','Montagem de torneira de gasóleo no depósito',7300),(156,'2013-05-19','Montagem de direcção assistida',7300),(157,'2013-05-19','Refazer instalação eléctrica',7300),(158,'2013-05-19','Reparar dinamo 1 rolamento 6302',7300),(159,'2013-05-19','Reparar bomba de água',7300),(160,'2013-05-19','Retirar termostato',7300),(161,'2013-05-19','Colar calços de travão (Maxilas de traz)',7300),(162,'2013-05-19','Montagem de asselerador de pé',7300),(163,'2013-08-03','Substituir óleo da bomba injectora',7338),(164,'2013-08-03','Montagem dos esticadores dos braços traseiros',7338),(165,'2013-09-10','Forrar maxilas dos travões rodas traseiras',7338),(166,'2013-09-10','Revisão dos cubos das rodas da frente',7338),(167,'2013-09-22','Medição de horas',7344),(168,'2014-07-30','Medição de horas',7369),(169,'2016-01-12','Medição de horas',7404),(170,'2016-03-20','Substituir óleo do motor 15w40 GALP',7404),(171,'2016-03-20','Substitur o óleo da bomba injectora 15w40 GALP',7404),(172,'2016-03-20','Substitur o filtro de óleo do motor',7404),(173,'2006-02-23','Correia bomba de água-alternador',90019),(174,'2006-02-23','Correia bomba de direcção L=1060',90019),(175,'2006-02-23','Tensor correia distribuição',90019),(176,'2006-02-23','Correia distribuição',90019),(177,'2006-02-23','Filtro óleo elemento',90019),(178,'2006-02-23','Velas',90019),(179,'2006-02-23','Jogo de pastilhas frente',90019),(180,'2006-02-23','Bomba de água',90019),(181,'2006-02-23','Óleo motor 3,30L',90019),(182,'2006-02-23','valvulina caixa 3L',90019),(183,'2006-02-23','Anti-congelante 2L',90019),(184,'2006-10-02','Sensor Electr.oxigenio',99142),(185,'2007-01-21','Óleo motor 3,30L (REPSOL)',104090),(186,'2007-01-21','Filtro óleo elemento',104090),(187,'2007-09-10','Óleo motor 3,30L (REPSOL)',115475),(188,'2007-09-10','Filtro óleo elemento',115475),(189,'2007-12-22','Substituir bateria',115475),(190,'2008-08-15','Óleo motor 3,30L (REPSOL)',131324),(191,'2008-08-15','Filtro óleo elemento',131324),(192,'2008-11-02','Medição kilómetros',134792),(193,'2009-03-13','Medição kilómetros',140630),(194,'2009-03-22','Óleo motor 3,30L (REPSOL)',141147),(195,'2009-03-22','Filtro óleo elemento',141147),(196,'2011-06-11','Óleo motor 3,30L (REPSOL)',177631),(197,'2011-06-10','Filtro óleo elemento',177631),(198,'2011-06-10','Pastilhas de travão da frente',177631),(199,'2012-05-05','Óleo motor 3,30L (SHEL HELIX  HX7 10W- 40) PAULO 30€',191470),(200,'2012-05-05','Filtro óleo elemento',191470),(201,'2013-02-04','Inspeção sem problemasmento',199640),(202,'2013-03-25','Substituir bomba auxiliar da embraiagem',200144),(203,'2013-08-31','Substituir amortecedores do porta bagagem',200144),(204,'2013-09-22','Medição kilómetros',207103),(205,'2014-05-25','Acidente com a Clara',207103),(206,'2014-07-30','Substituir filtro de ar',216736),(207,'2014-08-07','Substituir correia de distribuição (ALCIDES)',217074),(208,'2014-08-07','Substituir correias auxiliares',217074),(209,'2014-08-07','Substituir bomba de água',217074),(211,'2014-08-07','Substituir fol inferior alavanca das mudanças (Original)',217074),(212,'2014-08-07','Substituir óleo caixa de velocidades',217074),(213,'2014-11-08','Óleo motor 3,30L (SHEL HELIX  HX7 10W- 40) PAULO 30€',220000),(214,'2014-11-08','Filtro óleo elemento',220000),(215,'2015-02-05','Inspeção sem problemas',220432),(216,'2016-01-12','Medição kilómetros',222316),(217,'2016-02-12','Inspeção sem problemas',222512),(218,'2014-11-10','Compra do veículo',56200),(219,'2014-11-10','Bateria de origem',56200),(220,'2015-03-09','Inspeção LAMY',58574),(221,'2015-03-09','Escova limpa vidros SS-F53 530mm',58574),(222,'2015-03-09','Escova limpa vidros SS-F48 480mm',58574),(223,'2015-03-09','Bateria da chave CR 1620',58574),(224,'2015-03-09','Substituir óleo do motor Ref. MAZOO 5L DPF 5W30',58574),(225,'2015-03-09','Substituir filtro de óleo Ref. Y40114302 9A',58574),(226,'2015-03-09','Substituir filtro de gasóleo Y60213ZA5',58574),(227,'2015-03-09','Substituir filtro de ar',58574),(228,'2015-03-09','Substituir velas de encandessencia (Alencauto)',58574),(229,'2015-03-09','Substituir o deposito do liquido do aditivo do gasóleo (Mazda)',58574),(230,'2015-03-09','Atestar de liquido (Mazda)',58574),(231,'2016-01-12','Medição kilómetros',69854),(233,'2016-03-15','Inspeção LAMY',71675),(235,'2016-12-10','Mudar o oleo da caixa de velocidades (REPSOL CARTAGO Multigraduado EP 80W90)',169572),(236,'2016-11-07','Peneus novos a frente Ref. 175/65 R14 MABOR',224272),(237,'2016-11-26','Pneus novos a frente Ref. 165/65 R14 MABOR',168673),(240,'2016-12-17','Substituir o óleo do motor Ref. TOTAL QUARTZ ENE 700',170202),(241,'2016-12-17','Substituir o filtro do oleo ',170202),(243,'2016-01-22','Pneus novos a traz Ref. BRI. 205/55 R16',70146),(244,'2016-05-05','Pneus novos a frente Ref. BRI. 205/55 R16',73654),(245,'2016-07-14','Substituir o óleo do motor',76626),(246,'2016-07-14','Substituir o  filtro de óleo do motor',76626),(247,'2016-12-27','Substituir o retentor da caixa de velocidades lado do condutor',170868),(248,'2016-12-27','Substituir o filtro de gasoleo',170868),(249,'2017-04-08','Limpeza do radiador 5 litros de anticongelante',195097),(250,'2017-04-08','Substituir o Bendixe do motor de arranque 50€',0),(251,'2017-04-08','Substituir o liquido do radiador 15 litros',7404),(252,'2017-04-14','Mudar o óleo do motor',195097),(253,'2017-04-14','Substituir o filtro de óleo do motor',195097),(254,'2017-04-14','Substituir o filtro de ar',195097),(255,'2017-04-08','Medição de horas',115),(256,'2017-04-08','Medição de horas',2115),(257,'2017-04-08','Limpeza do radiador ',0),(258,'2017-04-14','Substituir o óleo da caixa',195097),(259,'2017-04-14','Substituir o óleo do difrencial',195097),(260,'2017-05-29','Substituir as escovas do limpa vidros',195439),(262,'2017-05-30','Registo de Km',195439),(263,'2017-05-29','Mudar o óleo do motor Ref. TOTAL Quartz 7000 10W40',180564),(264,'2017-05-29','Substituir o filtro de óleo Ref. Mena Peças ',180564),(265,'2017-05-20','Contro de horas',117),(267,'2017-05-29','Medição de horas',2017),(268,'2017-09-25','Contagem de km',187573),(269,'2017-09-25','Contagem de km',225959),(270,'2017-09-25','Contagem de km',92759),(272,'2017-10-31','Substituir o filtro de oleo',92836),(273,'2017-10-31','Substituir o filtro de ar',92836),(274,'2017-10-31','Substituir o filtro de gasoleo',92836),(275,'2017-10-31','Substituir a correa de destribuição',92836),(276,'2017-10-31','Substituir a correa auxiliar',92836),(277,'2017-10-31','Substituir as pastilhas da frente',92836),(278,'2017-10-31','Substituir o anticongelante do radiador',92836),(279,'2017-10-31','Pulir as oticas da trente',92836),(280,'2017-10-31','Mudar o óleo do motor',92836),(281,'2017-10-22','Substituir as quatro velas (Origem)',226250),(282,'2017-10-22','Substituir os discos de travão ( Macar Alferrerede)',226250),(283,'2017-10-22','Substituir as pastilhas de travão ( Macar Alferrerede)',226250),(284,'2017-10-22','Substituir o fol inferior da alavanca das mudanças (Origem)',226250),(286,'2018-01-14','Contagem de km',100000),(287,'2017-10-19','Substituir a tampa do termostato',196000),(288,'2017-12-07','Reparar radiador e substituir o anticongelante',196500),(289,'2018-04-17','Contagem de km',102942),(291,'2018-04-17','Contagem de horas',2132),(292,'2018-04-17','Contagem de km',197645),(293,'2018-04-19','Contagem de km',228871),(294,'2018-06-19','Mudar o óleo do motor',201200),(295,'2018-06-19','Substituir o filtro óleo do motor',201200),(296,'2018-06-19','Substituir o quite de distribuição e bomba de agua',201200),(297,'2018-06-19','Substituir junta da cabeça do motor rectificar a face da mesma',201200),(298,'2018-06-19','Substituir o liquido do radiador',201200),(299,'2018-06-19','Substituir vários tubos de agua',201200),(300,'2018-08-29','Guia nova OREGON  16\" 3/8 058 60E      REGAOESTE Torres Vedras',0),(301,'2018-08-29','Corrente nova  S 3/8\" 16\" / 30 facas      REGAOESTE Torres Vedras',0),(302,'2018-07-31','Mudar o óleo do motor',0),(303,'2018-07-31','Mudar o óleo do motor',0),(304,'2018-07-30','Mudar o óleo do motor',0),(305,'2018-07-31','Mudar o óleo do motor',0),(306,'2018-07-31','Substituir o filtro óleo do motor',0),(307,'2018-04-28','Reparar caixa de direcção',0),(309,'2018-07-31','Reparar as mangas das rodas da frente casquilhos novos veio cheios',0),(310,'2018-07-31','Mudar o óleo do motor',2150),(311,'2018-07-31','Substituir o filtro óleo do motor',2150),(312,'2018-07-31','Substituir o filtro de ar',2150),(314,'2018-07-31','Substituir correa de destribuição',198710),(315,'2018-07-31','Substituir boba de agua',198710),(316,'2018-07-31','Substituir correa de ventoinha e liquido do radiador',198710),(317,'2018-07-31','Montagem de um termostato novo',198710),(318,'2018-07-31','Substituir o filtro óleo do motor',0),(319,'2018-09-01','Substituir o filtro óleo do motor',0),(321,'2018-09-01','Substituir o óleo do motor',0),(322,'2018-10-16','Contagem de km',108213),(323,'2018-10-16','Contagem de km',209813),(324,'2018-10-16','Substituir quite de embraiagem (carregado)',229825),(325,'2018-10-16','Substituir retentor do veio primário (carregado)',229825),(326,'2018-10-16','Substituir retentor do selector das mudanças (carregado)',229825),(327,'2018-10-16','Substituir valvulina da caixa de velocidades (carregado)',229825),(328,'2018-10-16','Substituir fole da transmissão lado do volante (Carregado)',229825),(329,'2018-09-22','Substituir óleo e filtro do motor    óleo ( Paulo)',198752),(330,'2018-09-21','Contagem de km',198822),(331,'2018-10-21','Contagem de km',47954),(332,'2018-10-21','Contagem de km',14023),(333,'2018-11-01','Substituir a bateria  55AH  450A0 C REDPOWER  ROADY Carregado',230360),(334,'2018-12-02','Substituir filtro e óleo do motor',214000),(335,'2018-12-05','Substituir a válvula da temperatura do radiador (ventoinha)',214200),(336,'2018-12-11','Substituir a tampa do deposito de expansão de agua (radiador)',214406),(337,'2019-07-05','Substituir turbo reconstruido',117677),(338,'2019-07-05','Substituir turbo reconstruido',117677),(339,'2019-07-05','Substituir turbo reconstruido',117677),(341,'2019-07-05','Mudar o óleo do motor',117677),(342,'2019-07-05','Substituir o filtro óleo do motor',117677),(343,'2019-10-21','Contagem de km',126153),(344,'2019-02-09','Reparação do motor (agua no óleo)',199000),(345,'2019-02-09','Jogo de juntas',199000),(346,'2019-02-09','Junta da cabeça de origem',199000),(347,'2019-02-09','Jogo de parafusos da cabeça',199000),(348,'2019-02-09','Jogo de capas de biela',199000),(349,'2019-02-09','Jogo de capas da cambota',199000),(350,'2019-02-09','Jogo de segmentos',199000),(351,'2019-02-09','Relas todas novas em bronze',199000),(352,'2019-02-09','Rectificar face do bloco',199000),(353,'2019-02-09','Rectificar face da cabeça',199000),(354,'2019-02-09','Rodar válvulas',199000),(355,'2019-02-09','Despulir cilindros',199000),(356,'2019-02-09','Tampa de alumínio frente do motor',199000),(357,'2019-02-09','Substituir escovas do alternador',199000),(358,'2019-02-09','Substituir tubo do escape saída do colector',199000),(359,'2019-02-09','Substituir o óleo do motor Galp 15w40',199000),(360,'2019-02-09','Substituir o filtro óleo do motor',199000),(361,'2019-02-09','Substituir o óleo da caixa',199000),(362,'2019-02-09','Substituir o anticongelante',199000),(363,'2019-02-09','Rectificar polis das rodas traseiras',199000),(364,'2019-02-09','Substituir casos das rodas traseiras',199000),(365,'2019-04-12','Contagem de km 200000',200000),(367,'2019-11-05','Substituir a bateria BAERMAQ',127700);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `encoded_password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','pardal.pires@gmail.com','$2a$10$CNUqcF1e1sSaD8y5Aq8nGuayEqmujyC3eCg08p0qyzaERTlyqqVYe','ROLE_ADMIN'),('jose','jmapires263@gmail.com','$2a$10$7MXNPgxEv7VjpBIH4UK5v.wl.q7da4VHydqVmwFmbzlECQaz1XaBG','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle` (
  `dtype` varchar(31) NOT NULL,
  `name` varchar(255) NOT NULL,
  `acquisition_date` date DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `fabrication_year` int(11) DEFAULT NULL,
  `license_license` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `FK_oiavxlr9rv2tgm3p6p7qeg0d0` (`license_license`),
  CONSTRAINT `FK_oiavxlr9rv2tgm3p6p7qeg0d0` FOREIGN KEY (`license_license`) REFERENCES `license` (`license`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES ('VehicleUnlicensed','Bomba Dab Balão','2009-03-25','Desconhecida',1900,NULL),('VehicleUnlicensed','Bomba de 2 polegadas','2009-04-25','Desconhecida',1900,NULL),('VehicleLicensed','Carrinha Mitsubishi','2000-02-01','Mitshubishi',NULL,'89-30-OZ'),('VehicleLicensed','Citroen Saxo','2016-11-26','Citroen',NULL,'25-76-OM'),('VehicleUnlicensed','Corta-Mato','1995-04-25','Desconhecida',1900,NULL),('VehicleUnlicensed','Cubos J IPodec','2012-01-12','Desconhecida',1900,NULL),('VehicleUnlicensed','Decapinador','2010-03-10','Desconhecida',1900,NULL),('VehicleUnlicensed','Empilhador','2002-04-25','Desconhecida',1900,NULL),('VehicleUnlicensed','FAI','1987-04-10','Desconhecida',1900,NULL),('VehicleUnlicensed','Fresa Mallet','1991-01-16','Mallet',1900,NULL),('VehicleUnlicensed','Gerador','2005-05-16','Desconhecida',1900,NULL),('VehicleLicensed','Mazda 3','2014-11-10','Mazda',NULL,'76-DE-21'),('VehicleUnlicensed','Motoenchada kubota','2006-04-16','Kubota',1900,NULL),('VehicleUnlicensed','Motor Carmindo','2013-06-30','Desconhecida',1900,NULL),('VehicleUnlicensed','Motor Clinton','2011-04-13','Clinton',1900,NULL),('VehicleUnlicensed','Motor do Joper','1991-01-16','Desconhecida',1900,NULL),('VehicleUnlicensed','Motor Kubota','9999-12-30','Kubota',1900,NULL),('VehicleUnlicensed','Motor Lombardine','2005-03-18','Lombardine',1900,NULL),('VehicleUnlicensed','Motor Petter','2017-05-10','Petter',1900,NULL),('VehicleUnlicensed','Motoserra Jonsered','1995-06-01','Jonsered',1900,NULL),('VehicleUnlicensed','Motoserra Stihl','1998-04-10','Stihl',1900,NULL),('VehicleUnlicensed','Motoserra Stihl 2','2009-04-01','Stihl',1900,NULL),('VehicleUnlicensed','Motoserra Stihl MS 192 T','2013-06-01','Stihl',1900,NULL),('VehicleUnlicensed','Racha Lenha','2005-06-01','Desconhecida',1900,NULL),('VehicleUnlicensed','Reboque Honda','1991-01-16','Honda',1900,NULL),('VehicleUnlicensed','Reboque Joper','2015-05-15','Joper',1900,NULL),('VehicleUnlicensed','Reboque Motoenchada','2010-03-16','Desconhecida',1900,NULL),('VehicleUnlicensed','Retro Casse','2007-05-16','Desconhecida',1900,NULL),('VehicleUnlicensed','Tanque Joper','1991-01-16','Joper',1902,NULL),('VehicleUnlicensed','TOYOTA QQ-27-51','2017-04-07','TOYOTA HILUX LN56L',1989,NULL),('VehicleUnlicensed','Tractor Agria','2015-04-25','Agria',1900,NULL),('VehicleLicensed','Tractor Ford','2014-04-25','Ford',NULL,'AR-68-23'),('VehicleLicensed','Tractor Kubota','1996-03-30','Kubota',NULL,'30-38-BV'),('VehicleLicensed','Tractor MF','2005-05-23','Massey Fergunson',NULL,'GI-48-39'),('VehicleUnlicensed','Zundapp 2 Famel','1966-03-30','Famel',2017,NULL),('VehicleUnlicensed','Zundapp 4 Motobil','1966-03-30','Motobil',2017,NULL);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_notes`
--

DROP TABLE IF EXISTS `vehicle_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle_notes` (
  `vehicle_name` varchar(255) NOT NULL,
  `notes_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_llxoi8kjguuyds4ngmmtevisk` (`notes_id`),
  KEY `FK_91qak1b61fmsx7njqcilpauyn` (`vehicle_name`),
  CONSTRAINT `FK_91qak1b61fmsx7njqcilpauyn` FOREIGN KEY (`vehicle_name`) REFERENCES `vehicle` (`name`),
  CONSTRAINT `FK_llxoi8kjguuyds4ngmmtevisk` FOREIGN KEY (`notes_id`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_notes`
--

LOCK TABLES `vehicle_notes` WRITE;
/*!40000 ALTER TABLE `vehicle_notes` DISABLE KEYS */;
INSERT INTO `vehicle_notes` VALUES ('Carrinha Mitsubishi',11),('Carrinha Mitsubishi',12),('Carrinha Mitsubishi',13),('Citroen Saxo',14),('Citroen Saxo',16),('Citroen Saxo',17),('Citroen Saxo',18),('Citroen Saxo',19),('Citroen Saxo',20),('Citroen Saxo',21),('Citroen Saxo',22),('Citroen Saxo',23),('Citroen Saxo',24),('Citroen Saxo',39),('Citroen Saxo',42),('Citroen Saxo',45),('Citroen Saxo',46),('Citroen Saxo',50),('Corta-Mato',2),('Fresa Mallet',3),('Fresa Mallet',4),('Mazda 3',44),('Mazda 3',51),('Motoenchada kubota',5),('Motoenchada kubota',6),('Motoserra Jonsered',49),('Reboque Honda',7),('Reboque Honda',8),('Reboque Honda',9),('TOYOTA QQ-27-51',26),('TOYOTA QQ-27-51',28),('TOYOTA QQ-27-51',34),('TOYOTA QQ-27-51',36),('TOYOTA QQ-27-51',37),('TOYOTA QQ-27-51',38),('TOYOTA QQ-27-51',43),('TOYOTA QQ-27-51',47),('Tractor Agria',1),('Tractor Ford',10),('Tractor Kubota',41);
/*!40000 ALTER TABLE `vehicle_notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_notifications`
--

DROP TABLE IF EXISTS `vehicle_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle_notifications` (
  `vehicle_name` varchar(255) NOT NULL,
  `notifications_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_hmqskquoxsyu2bh2lajyawooi` (`notifications_id`),
  KEY `FK_6ai14b4s1njyllyqf0sl0pkcj` (`vehicle_name`),
  CONSTRAINT `FK_6ai14b4s1njyllyqf0sl0pkcj` FOREIGN KEY (`vehicle_name`) REFERENCES `vehicle` (`name`),
  CONSTRAINT `FK_hmqskquoxsyu2bh2lajyawooi` FOREIGN KEY (`notifications_id`) REFERENCES `notification_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_notifications`
--

LOCK TABLES `vehicle_notifications` WRITE;
/*!40000 ALTER TABLE `vehicle_notifications` DISABLE KEYS */;
INSERT INTO `vehicle_notifications` VALUES ('Citroen Saxo',1),('Mazda 3',2),('Tractor Agria',4),('Tractor Ford',3),('Tractor MF',5);
/*!40000 ALTER TABLE `vehicle_notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_registries`
--

DROP TABLE IF EXISTS `vehicle_registries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle_registries` (
  `vehicle_name` varchar(255) NOT NULL,
  `registries_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_cbfwtjv7cq7itpd7pdh0njd2y` (`registries_id`),
  KEY `FK_gcpugpb7rh4buerpqf7lr5bq6` (`vehicle_name`),
  CONSTRAINT `FK_cbfwtjv7cq7itpd7pdh0njd2y` FOREIGN KEY (`registries_id`) REFERENCES `registration` (`id`),
  CONSTRAINT `FK_gcpugpb7rh4buerpqf7lr5bq6` FOREIGN KEY (`vehicle_name`) REFERENCES `vehicle` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_registries`
--

LOCK TABLES `vehicle_registries` WRITE;
/*!40000 ALTER TABLE `vehicle_registries` DISABLE KEYS */;
INSERT INTO `vehicle_registries` VALUES ('Bomba Dab Balão',9),('Bomba Dab Balão',10),('Bomba Dab Balão',11),('Bomba de 2 polegadas',12),('Carrinha Mitsubishi',173),('Carrinha Mitsubishi',174),('Carrinha Mitsubishi',175),('Carrinha Mitsubishi',176),('Carrinha Mitsubishi',177),('Carrinha Mitsubishi',178),('Carrinha Mitsubishi',179),('Carrinha Mitsubishi',180),('Carrinha Mitsubishi',181),('Carrinha Mitsubishi',182),('Carrinha Mitsubishi',183),('Carrinha Mitsubishi',184),('Carrinha Mitsubishi',185),('Carrinha Mitsubishi',186),('Carrinha Mitsubishi',187),('Carrinha Mitsubishi',188),('Carrinha Mitsubishi',189),('Carrinha Mitsubishi',190),('Carrinha Mitsubishi',191),('Carrinha Mitsubishi',192),('Carrinha Mitsubishi',193),('Carrinha Mitsubishi',194),('Carrinha Mitsubishi',195),('Carrinha Mitsubishi',196),('Carrinha Mitsubishi',197),('Carrinha Mitsubishi',198),('Carrinha Mitsubishi',199),('Carrinha Mitsubishi',200),('Carrinha Mitsubishi',201),('Carrinha Mitsubishi',202),('Carrinha Mitsubishi',203),('Carrinha Mitsubishi',204),('Carrinha Mitsubishi',205),('Carrinha Mitsubishi',206),('Carrinha Mitsubishi',207),('Carrinha Mitsubishi',208),('Carrinha Mitsubishi',209),('Carrinha Mitsubishi',211),('Carrinha Mitsubishi',212),('Carrinha Mitsubishi',213),('Carrinha Mitsubishi',214),('Carrinha Mitsubishi',215),('Carrinha Mitsubishi',216),('Carrinha Mitsubishi',217),('Carrinha Mitsubishi',236),('Carrinha Mitsubishi',269),('Carrinha Mitsubishi',281),('Carrinha Mitsubishi',282),('Carrinha Mitsubishi',283),('Carrinha Mitsubishi',284),('Carrinha Mitsubishi',293),('Carrinha Mitsubishi',324),('Carrinha Mitsubishi',325),('Carrinha Mitsubishi',326),('Carrinha Mitsubishi',327),('Carrinha Mitsubishi',328),('Carrinha Mitsubishi',333),('Citroen Saxo',235),('Citroen Saxo',237),('Citroen Saxo',240),('Citroen Saxo',241),('Citroen Saxo',247),('Citroen Saxo',248),('Citroen Saxo',263),('Citroen Saxo',264),('Citroen Saxo',268),('Citroen Saxo',294),('Citroen Saxo',295),('Citroen Saxo',296),('Citroen Saxo',297),('Citroen Saxo',298),('Citroen Saxo',299),('Citroen Saxo',323),('Citroen Saxo',334),('Citroen Saxo',335),('Citroen Saxo',336),('Cubos J IPodec',13),('Cubos J IPodec',14),('Decapinador',18),('Decapinador',19),('Empilhador',15),('Empilhador',16),('Empilhador',17),('FAI',20),('FAI',21),('FAI',22),('FAI',23),('FAI',24),('FAI',25),('FAI',26),('FAI',27),('FAI',28),('FAI',29),('FAI',30),('FAI',31),('Fresa Mallet',32),('Fresa Mallet',33),('Fresa Mallet',34),('Fresa Mallet',35),('Fresa Mallet',36),('Fresa Mallet',37),('Fresa Mallet',38),('Fresa Mallet',39),('Fresa Mallet',40),('Fresa Mallet',41),('Gerador',42),('Mazda 3',218),('Mazda 3',219),('Mazda 3',220),('Mazda 3',221),('Mazda 3',222),('Mazda 3',223),('Mazda 3',224),('Mazda 3',225),('Mazda 3',226),('Mazda 3',227),('Mazda 3',228),('Mazda 3',229),('Mazda 3',230),('Mazda 3',231),('Mazda 3',233),('Mazda 3',243),('Mazda 3',244),('Mazda 3',245),('Mazda 3',246),('Mazda 3',270),('Mazda 3',272),('Mazda 3',273),('Mazda 3',274),('Mazda 3',275),('Mazda 3',276),('Mazda 3',277),('Mazda 3',278),('Mazda 3',279),('Mazda 3',280),('Mazda 3',286),('Mazda 3',289),('Mazda 3',322),('Mazda 3',337),('Mazda 3',339),('Mazda 3',341),('Mazda 3',342),('Mazda 3',343),('Mazda 3',367),('Motoenchada kubota',48),('Motoenchada kubota',49),('Motoenchada kubota',304),('Motor Carmindo',50),('Motor Clinton',51),('Motor do Joper',52),('Motor do Joper',53),('Motor do Joper',303),('Motor Kubota',54),('Motor Kubota',302),('Motor Lombardine',55),('Motor Lombardine',56),('Motor Petter',318),('Motoserra Jonsered',65),('Motoserra Jonsered',300),('Motoserra Jonsered',301),('Motoserra Stihl',57),('Motoserra Stihl',58),('Motoserra Stihl',59),('Motoserra Stihl 2',60),('Motoserra Stihl 2',61),('Motoserra Stihl 2',62),('Motoserra Stihl 2',63),('Motoserra Stihl 2',64),('Racha Lenha',66),('Racha Lenha',67),('Racha Lenha',68),('Racha Lenha',69),('Racha Lenha',70),('Reboque Joper',71),('Reboque Motoenchada',72),('Retro Casse',73),('Retro Casse',74),('Retro Casse',75),('Retro Casse',76),('Tanque Joper',43),('Tanque Joper',44),('Tanque Joper',45),('Tanque Joper',46),('Tanque Joper',47),('TOYOTA QQ-27-51',249),('TOYOTA QQ-27-51',252),('TOYOTA QQ-27-51',253),('TOYOTA QQ-27-51',254),('TOYOTA QQ-27-51',258),('TOYOTA QQ-27-51',259),('TOYOTA QQ-27-51',260),('TOYOTA QQ-27-51',262),('TOYOTA QQ-27-51',287),('TOYOTA QQ-27-51',288),('TOYOTA QQ-27-51',292),('TOYOTA QQ-27-51',314),('TOYOTA QQ-27-51',315),('TOYOTA QQ-27-51',316),('TOYOTA QQ-27-51',317),('TOYOTA QQ-27-51',329),('TOYOTA QQ-27-51',330),('TOYOTA QQ-27-51',344),('TOYOTA QQ-27-51',345),('TOYOTA QQ-27-51',346),('TOYOTA QQ-27-51',347),('TOYOTA QQ-27-51',348),('TOYOTA QQ-27-51',349),('TOYOTA QQ-27-51',350),('TOYOTA QQ-27-51',351),('TOYOTA QQ-27-51',352),('TOYOTA QQ-27-51',353),('TOYOTA QQ-27-51',354),('TOYOTA QQ-27-51',355),('TOYOTA QQ-27-51',356),('TOYOTA QQ-27-51',357),('TOYOTA QQ-27-51',358),('TOYOTA QQ-27-51',359),('TOYOTA QQ-27-51',360),('TOYOTA QQ-27-51',361),('TOYOTA QQ-27-51',362),('TOYOTA QQ-27-51',363),('TOYOTA QQ-27-51',364),('TOYOTA QQ-27-51',365),('Tractor Agria',1),('Tractor Agria',2),('Tractor Agria',3),('Tractor Agria',5),('Tractor Agria',6),('Tractor Agria',7),('Tractor Agria',8),('Tractor Agria',250),('Tractor Ford',142),('Tractor Ford',143),('Tractor Ford',144),('Tractor Ford',145),('Tractor Ford',146),('Tractor Ford',147),('Tractor Ford',148),('Tractor Ford',149),('Tractor Ford',150),('Tractor Ford',151),('Tractor Ford',152),('Tractor Ford',153),('Tractor Ford',154),('Tractor Ford',155),('Tractor Ford',156),('Tractor Ford',157),('Tractor Ford',158),('Tractor Ford',159),('Tractor Ford',160),('Tractor Ford',161),('Tractor Ford',162),('Tractor Ford',163),('Tractor Ford',164),('Tractor Ford',165),('Tractor Ford',166),('Tractor Ford',167),('Tractor Ford',168),('Tractor Ford',169),('Tractor Ford',170),('Tractor Ford',171),('Tractor Ford',172),('Tractor Ford',251),('Tractor Ford',307),('Tractor Ford',309),('Tractor Ford',319),('Tractor Ford',321),('Tractor Kubota',111),('Tractor Kubota',112),('Tractor Kubota',113),('Tractor Kubota',114),('Tractor Kubota',115),('Tractor Kubota',116),('Tractor Kubota',117),('Tractor Kubota',118),('Tractor Kubota',119),('Tractor Kubota',120),('Tractor Kubota',121),('Tractor Kubota',122),('Tractor Kubota',123),('Tractor Kubota',124),('Tractor Kubota',125),('Tractor Kubota',126),('Tractor Kubota',127),('Tractor Kubota',128),('Tractor Kubota',129),('Tractor Kubota',130),('Tractor Kubota',131),('Tractor Kubota',132),('Tractor Kubota',133),('Tractor Kubota',134),('Tractor Kubota',135),('Tractor Kubota',136),('Tractor Kubota',137),('Tractor Kubota',138),('Tractor Kubota',139),('Tractor Kubota',140),('Tractor Kubota',141),('Tractor Kubota',255),('Tractor Kubota',256),('Tractor Kubota',265),('Tractor Kubota',267),('Tractor Kubota',291),('Tractor Kubota',310),('Tractor Kubota',311),('Tractor Kubota',312),('Tractor MF',77),('Tractor MF',78),('Tractor MF',79),('Tractor MF',80),('Tractor MF',81),('Tractor MF',82),('Tractor MF',83),('Tractor MF',84),('Tractor MF',85),('Tractor MF',86),('Tractor MF',87),('Tractor MF',88),('Tractor MF',89),('Tractor MF',90),('Tractor MF',91),('Tractor MF',92),('Tractor MF',93),('Tractor MF',94),('Tractor MF',95),('Tractor MF',96),('Tractor MF',97),('Tractor MF',98),('Tractor MF',99),('Tractor MF',100),('Tractor MF',101),('Tractor MF',102),('Tractor MF',103),('Tractor MF',104),('Tractor MF',105),('Tractor MF',106),('Tractor MF',107),('Tractor MF',108),('Tractor MF',109),('Tractor MF',257),('Tractor MF',305),('Tractor MF',306),('Zundapp 2 Famel',332),('Zundapp 4 Motobil',331);
/*!40000 ALTER TABLE `vehicle_registries` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;