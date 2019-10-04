CREATE SCHEMA `dbinternetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `dbinternetshop`.`items` (item_id int);

ALTER TABLE `dbinternetshop`.`items`
ADD COLUMN `name` VARCHAR(225) NOT NULL AFTER `item_id`,
ADD COLUMN `price` DECIMAL(6,2) NOT NULL AFTER `name`,
CHANGE COLUMN `item_id` `item_id` INT NOT NULL AUTO_INCREMENT ,
ADD PRIMARY KEY (`item_id`);
;

INSERT INTO `dbinternetshop`.`items` (`name`, `price`) VALUES ('nokia', '50');
INSERT INTO `dbinternetshop`.`items` (`name`, `price`) VALUES ('samsung', '300');
INSERT INTO `dbinternetshop`.`items` (`name`, `price`) VALUES ('iphone11', '1000');

CREATE TABLE `dbinternetshop`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_id`));

CREATE TABLE `dbinternetshop`.`orders_items` (
  `orders_items_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  PRIMARY KEY (`orders_items_id`),
  INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_itmes_items_fk_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `orders_items_orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `dbinternetshop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_itmes_items_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `dbinternetshop`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dbinternetshop`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));

ALTER TABLE `dbinternetshop`.`orders`
ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `dbinternetshop`.`orders`
ADD CONSTRAINT `orders_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `dbinternetshop`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `dbinternetshop`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` ENUM('USER', 'ADMIN') NOT NULL,
  PRIMARY KEY (`role_id`));

INSERT INTO roles (role_name) values ('USER');
INSERT INTO roles (role_name) values ('ADMIN');

CREATE TABLE `dbinternetshop`.`users_roles` (
  `users_roles_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `role_id` INT NULL,
  PRIMARY KEY (`users_roles_id`),
  INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users_roles_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `dbinternetshop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `users_roles_roles_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `dbinternetshop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `dbinternetshop`.`orders`
DROP FOREIGN KEY `orders_users_fk`;
ALTER TABLE `dbinternetshop`.`orders`
ADD CONSTRAINT `orders_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `dbinternetshop`.`users` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `dbinternetshop`.`orders_items`
DROP FOREIGN KEY `orders_items_orders_fk`,
DROP FOREIGN KEY `orders_itmes_items_fk`;
ALTER TABLE `dbinternetshop`.`orders_items`
ADD CONSTRAINT `orders_items_orders_fk`
  FOREIGN KEY (`order_id`)
  REFERENCES `dbinternetshop`.`orders` (`order_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `orders_itmes_items_fk`
  FOREIGN KEY (`item_id`)
  REFERENCES `dbinternetshop`.`items` (`item_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `dbinternetshop`.`users_roles`
DROP FOREIGN KEY `users_roles_roles_fk`,
DROP FOREIGN KEY `users_roles_users_fk`;
ALTER TABLE `dbinternetshop`.`users_roles`
ADD CONSTRAINT `users_roles_roles_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `dbinternetshop`.`roles` (`role_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `users_roles_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `dbinternetshop`.`users` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE TABLE `dbinternetshop`.`buckets` (
  `bucket_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`bucket_id`),
  INDEX `buckets_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `buckets_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `dbinternetshop`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `dbinternetshop`.`buckets_items` (
  `buckets_items_id` INT NOT NULL AUTO_INCREMENT,
  `bucket_id` INT NOT NULL,
  `item_id` INT NULL,
  PRIMARY KEY (`buckets_items_id`),
  INDEX `buckets_items_buckets_fk_idx` (`bucket_id` ASC) VISIBLE,
  INDEX `buckets_items_items_fk_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `buckets_items_buckets_fk`
    FOREIGN KEY (`bucket_id`)
    REFERENCES `dbinternetshop`.`buckets` (`bucket_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `buckets_items_items_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `dbinternetshop`.`items` (`item_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

ALTER TABLE `dbinternetshop`.`users`
ADD COLUMN `salt` VARCHAR(128) NOT NULL AFTER `password`;

ALTER TABLE `dbinternetshop`.`users`
CHANGE COLUMN `salt` `salt` BLOB NOT NULL ;

ALTER TABLE `dbinternetshop`.`users`
CHANGE COLUMN `password` `password` VARCHAR(256) NOT NULL ;
