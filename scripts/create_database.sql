SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `geformDB` ;
CREATE SCHEMA IF NOT EXISTS `geformDB` ;
SHOW WARNINGS;
USE `geformDB` ;

-- -----------------------------------------------------
-- Table `geformDB`.`Form`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Form` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Form` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `creator` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Collection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Collection` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Collection` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `collector` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Type` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Type` (
  `id` TINYINT UNSIGNED NOT NULL,
  `value` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Options`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Options` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Options` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Item` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Item` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(500) NOT NULL,
  `type_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Item_Type_idx` (`type_id` ASC),
  CONSTRAINT `fk_Item_Type`
    FOREIGN KEY (`type_id`)
    REFERENCES `geformDB`.`Type` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Item_Option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Item_Option` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Item_Option` (
  `item_id` BIGINT UNSIGNED NOT NULL,
  `option_id` BIGINT UNSIGNED NOT NULL,
  `option_index` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`item_id`, `option_id`),
  INDEX `fk_item_option_option_idx` (`option_id` ASC),
  INDEX `fk_item_option_item_idx` (`item_id` ASC),
  CONSTRAINT `fk_item_option_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `geformDB`.`Item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_item_option_option`
    FOREIGN KEY (`option_id`)
    REFERENCES `geformDB`.`Options` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Form_Item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Form_Item` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Form_Item` (
  `form_id` BIGINT UNSIGNED NOT NULL,
  `item_id` BIGINT UNSIGNED NOT NULL,
  `item_index` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`form_id`, `item_id`),
  INDEX `fk_form_item_item_idx` (`item_id` ASC),
  INDEX `fk_form_item_form_idx` (`form_id` ASC),
  CONSTRAINT `fk_form_item_form`
    FOREIGN KEY (`form_id`)
    REFERENCES `geformDB`.`Form` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_form_item_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `geformDB`.`Item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Choice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Choice` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Choice` (
  `collection_id` BIGINT UNSIGNED NOT NULL,
  `item_id` BIGINT UNSIGNED NOT NULL,
  `option_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`collection_id`, `item_id`, `option_id`),
  INDEX `fk_choice_item_option_idx` (`item_id` ASC, `option_id` ASC),
  CONSTRAINT `fk_choice_collection`
    FOREIGN KEY (`collection_id`)
    REFERENCES `geformDB`.`Collection` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_choice_item_option`
    FOREIGN KEY (`item_id` , `option_id`)
    REFERENCES `geformDB`.`Item_Option` (`item_id` , `option_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Text`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Text` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Text` (
  `item_id` BIGINT UNSIGNED NOT NULL,
  `collection_id` BIGINT UNSIGNED NOT NULL,
  `value` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`item_id`, `collection_id`),
  INDEX `fk_item_collection_collection_idx` (`collection_id` ASC),
  INDEX `fk_item_collection_item_idx` (`item_id` ASC),
  CONSTRAINT `fk_item_collection_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `geformDB`.`Item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_item_collection_collection`
    FOREIGN KEY (`collection_id`)
    REFERENCES `geformDB`.`Collection` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `geformDB`.`Form_Collection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geformDB`.`Form_Collection` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `geformDB`.`Form_Collection` (
  `form_id` BIGINT UNSIGNED NOT NULL,
  `collection_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`form_id`, `collection_id`),
  INDEX `fk_form_collection_collection_idx` (`collection_id` ASC),
  INDEX `fk_form_collection_form_idx` (`form_id` ASC),
  CONSTRAINT `fk_form_collection_form`
    FOREIGN KEY (`form_id`)
    REFERENCES `geformDB`.`Form` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_form_collection_collection`
    FOREIGN KEY (`collection_id`)
    REFERENCES `geformDB`.`Collection` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `geformDB`.`Type`
-- -----------------------------------------------------
START TRANSACTION;
USE `geformDB`;
INSERT INTO `geformDB`.`Type` (`id`, `value`) VALUES (0, 'text');
INSERT INTO `geformDB`.`Type` (`id`, `value`) VALUES (1, 'single');
INSERT INTO `geformDB`.`Type` (`id`, `value`) VALUES (2, 'multiple');

COMMIT;

