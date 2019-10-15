DROP TABLE IF EXISTS `buckets`;

CREATE TABLE `buckets` (
  `bucket_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`bucket_id`),
  KEY `buckets_users_fk_idx` (`user_id`),
  CONSTRAINT `buckets_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

LOCK TABLES `buckets` WRITE;
INSERT INTO `buckets` VALUES (30,39),(31,40);
UNLOCK TABLES;

DROP TABLE IF EXISTS `buckets_items`;

CREATE TABLE `buckets_items` (
  `buckets_items_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bucket_id` bigint(20) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`buckets_items_id`),
  KEY `buckets_items_items_fk_idx` (`item_id`),
  KEY `buckets_items_buckets_fk_idx` (`bucket_id`),
  CONSTRAINT `buckets_items_buckets_fk` FOREIGN KEY (`bucket_id`) REFERENCES `buckets` (`bucket_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `buckets_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

LOCK TABLES `buckets_items` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

LOCK TABLES `items` WRITE;

INSERT INTO `items` VALUES (27,'nokia',50.00),(28,'samsung',300.00),(29,'iphone11',1000.00);

UNLOCK TABLES;

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_users_fk_idx` (`user_id`),
  CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

LOCK TABLES `orders` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `orders_items`;

CREATE TABLE `orders_items` (
  `orders_items_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`orders_items_id`),
  KEY `orders_itmes_items_fk_idx` (`item_id`),
  KEY `orders_items_orders_fk_idx` (`order_id`),
  CONSTRAINT `orders_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_items_orders_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

LOCK TABLES `orders_items` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `roles` WRITE;

INSERT INTO `roles` VALUES (2,'ADMIN'),(3,'USER');

UNLOCK TABLES;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` blob NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;

INSERT INTO `users` VALUES (39,'1','1','facce8058981cf9b8750f0181792b781286444160ef93ba93c46d6c2253d4382a57fd80df004ce4f4a1d552d95133f5e6257273c25b6bfc6cd35046a35165ee8',_binary '\ЮЉі:\кєz9¬ёA\ЩсЎ','b18e8d19-8886-4d7b-b97c-b762965975ed'),(40,'2','2','67a5dd6a6db127b52f24b668f69fade16cdcaa7cc22e33e321585e32f43440e632b303f3b76c0682ce02be4ba461c0874ff97855a061f5848e9cd9538d8e679a',_binary 'Љ:ф\\‹Th\г\Й%ћ2¦','fe4ce49b-9e41-4bd0-af3c-22ae71a5d0aa');

UNLOCK TABLES;

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `users_roles_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`users_roles_id`),
  KEY `users_roles_users_fk_idx` (`user_id`),
  KEY `users_roles_roles_fk_idx` (`role_id`),
  CONSTRAINT `users_roles_roles_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_roles_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

LOCK TABLES `users_roles` WRITE;

INSERT INTO `users_roles` VALUES (23,39,3),(24,40,2);

UNLOCK TABLES;
