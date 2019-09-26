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
