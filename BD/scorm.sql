SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `scorm` DEFAULT CHARACTER SET utf8 ;
USE `scorm` ;

-- -----------------------------------------------------
-- Table `scorm`.`metadata`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`metadata` (
  `id_metadata` INT NOT NULL AUTO_INCREMENT ,
  `metadata_type` VARCHAR(12) NOT NULL COMMENT '(Created for this DB)\nVocabulary:\nmanifiest\norganization\nitem\nresource\nfile' ,
  `metadata_schema` VARCHAR(100) NULL ,
  `metadata_schemaversion` VARCHAR(20) NULL ,
  `location` VARCHAR(2000) NULL COMMENT 'adlcp:location' ,
  `metadata` INT(11) NULL ,
  PRIMARY KEY (`id_metadata`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`manifest`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`manifest` (
  `scorm_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `manifest_identifier` VARCHAR(45) NOT NULL ,
  `manifest_version` VARCHAR(20) NULL COMMENT 'contradiction in documentation:\nNot Nullable?' ,
  `manifest_base` TEXT NULL COMMENT 'xml:base' ,
  `organizations_default` VARCHAR(45) NULL ,
  `resources_base` TEXT NULL COMMENT 'xml:base' ,
  `manifiest_scorm_id` INT(11) NULL COMMENT 'Father\'s PK. \nOnly if this is a subnode.' ,
  `id_metadata` INT NULL ,
  PRIMARY KEY (`scorm_id`) ,
  INDEX `fk_manifiest_manifiest1_idx` (`manifiest_scorm_id` ASC) ,
  INDEX `fk_manifiest_metadata1_idx` (`id_metadata` ASC) ,
  CONSTRAINT `fk_manifiest_manifiest1`
    FOREIGN KEY (`manifiest_scorm_id` )
    REFERENCES `scorm`.`manifest` (`scorm_id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_manifiest_metadata1`
    FOREIGN KEY (`id_metadata` )
    REFERENCES `scorm`.`metadata` (`id_metadata` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`organization`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`organization` (
  `id_organization` INT(11) NOT NULL AUTO_INCREMENT ,
  `scorm_id` INT(11) NOT NULL ,
  `identifier` VARCHAR(45) NOT NULL ,
  `title` VARCHAR(200) NOT NULL COMMENT 'Contradiction in documentation:\nNot Nullable?' ,
  `structure` VARCHAR(200) NULL DEFAULT 'hierarchical' ,
  `id_metadata` INT NULL ,
  PRIMARY KEY (`id_organization`) ,
  INDEX `fk_organizations_organization_1_idx` (`scorm_id` ASC) ,
  INDEX `fk_organization_metadata1_idx` (`id_metadata` ASC) ,
  CONSTRAINT `fk_organizations_organization_1`
    FOREIGN KEY (`scorm_id` )
    REFERENCES `scorm`.`manifest` (`scorm_id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organization_metadata1`
    FOREIGN KEY (`id_metadata` )
    REFERENCES `scorm`.`metadata` (`id_metadata` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`item`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`item` (
  `id_item` INT(11) NOT NULL AUTO_INCREMENT ,
  `id_organization` INT(11) NOT NULL ,
  `identifier` VARCHAR(45) NOT NULL ,
  `title` VARCHAR(200) NOT NULL COMMENT 'Contradiction in documentation:\nNot Nullable?' ,
  `identifierref` VARCHAR(200) NULL ,
  `isvisible` TINYINT(1) NULL ,
  `parameters` VARCHAR(1000) NULL ,
  `prerequisites` VARCHAR(200) NULL COMMENT 'adlcp:prerequisites' ,
  `prerequisites_type` VARCHAR(12) NULL COMMENT 'vocabulary:\naicc_script' ,
  `maxtimeallowed` TIME NULL COMMENT 'adlcp_maxtimeallowed' ,
  `timelimitaction` VARCHAR(24) NULL COMMENT 'adlcp:timelimitaction\n\nvocabulary:\nexit,message\nexit,no message\ncontinue,message\ncontinue,no message\n' ,
  `datafromlms` VARCHAR(255) NULL COMMENT 'adlcp:datafromlms' ,
  `masteryscore` VARCHAR(200) NULL COMMENT 'adlcp:masterycore' ,
  `item_id_item` INT(11) NULL COMMENT 'Father\'s PK. \nOnly if this is a subnode.' ,
  `id_metadata` INT NULL ,
  PRIMARY KEY (`id_item`) ,
  INDEX `fk_organization_organization_item_1_idx` (`id_organization` ASC) ,
  INDEX `fk_item_item1_idx` (`item_id_item` ASC) ,
  INDEX `fk_item_metadata1_idx` (`id_metadata` ASC) ,
  CONSTRAINT `fk_organization_organization_item_1`
    FOREIGN KEY (`id_organization` )
    REFERENCES `scorm`.`organization` (`id_organization` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_item1`
    FOREIGN KEY (`item_id_item` )
    REFERENCES `scorm`.`item` (`id_item` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_metadata1`
    FOREIGN KEY (`id_metadata` )
    REFERENCES `scorm`.`metadata` (`id_metadata` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`resource`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`resource` (
  `id_resource` INT(11) NOT NULL AUTO_INCREMENT ,
  `scorm_id` INT(11) NOT NULL ,
  `identifier` VARCHAR(45) NOT NULL ,
  `type` VARCHAR(1000) NOT NULL ,
  `href` VARCHAR(2000) NOT NULL ,
  `scormtype` VARCHAR(45) NULL COMMENT 'adclp:scormtype\n\nvocabulary:\nsco\nasset' ,
  `id_metadata` INT NULL ,
  `resource_base` TEXT NULL COMMENT 'xml:base' ,
  PRIMARY KEY (`id_resource`) ,
  INDEX `fk_resources_resource_1_idx` (`scorm_id` ASC) ,
  INDEX `fk_resource_metadata1_idx` (`id_metadata` ASC) ,
  CONSTRAINT `fk_resources_resource_1`
    FOREIGN KEY (`scorm_id` )
    REFERENCES `scorm`.`manifest` (`scorm_id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resource_metadata1`
    FOREIGN KEY (`id_metadata` )
    REFERENCES `scorm`.`metadata` (`id_metadata` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`file`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`file` (
  `id_file` INT(11) NOT NULL AUTO_INCREMENT ,
  `id_resource` INT(11) NOT NULL ,
  `href` VARCHAR(2000) NULL ,
  `id_metadata` INT NULL ,
  PRIMARY KEY (`id_file`) ,
  INDEX `fk_file_metadata1_idx` (`id_metadata` ASC) ,
  INDEX `fk_file_resource1_idx` (`id_resource` ASC) ,
  CONSTRAINT `fk_file_metadata1`
    FOREIGN KEY (`id_metadata` )
    REFERENCES `scorm`.`metadata` (`id_metadata` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_file_resource1`
    FOREIGN KEY (`id_resource` )
    REFERENCES `scorm`.`resource` (`id_resource` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scorm`.`dependency`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scorm`.`dependency` (
  `id_dependency` INT(11) NOT NULL AUTO_INCREMENT ,
  `identifierref` VARCHAR(45) NOT NULL ,
  `id_resource` INT(11) NOT NULL ,
  PRIMARY KEY (`id_dependency`) ,
  INDEX `fk_dependency_resource1_idx` (`id_resource` ASC) ,
  CONSTRAINT `fk_dependency_resource1`
    FOREIGN KEY (`id_resource` )
    REFERENCES `scorm`.`resource` (`id_resource` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `scorm`;

DELIMITER $$
USE `scorm`$$


CREATE TRIGGER `manifest_ADEL` AFTER DELETE ON manifest FOR EACH ROW
	DELETE FROM metadata WHERE metadata.id_metadata=manifest.id_metada
-- Edit trigger body code below this line. Do not edit lines above this one

$$


DELIMITER ;

DELIMITER $$
USE `scorm`$$


CREATE TRIGGER `organization_ADEL` AFTER DELETE ON organization FOR EACH ROW
	DELETE FROM metadata WHERE metadata.id_metadata=organizaion.id_metadata
-- Edit trigger body code below this line. Do not edit lines above this one

$$


DELIMITER ;

DELIMITER $$
USE `scorm`$$
$$


DELIMITER ;

DELIMITER $$
USE `scorm`$$


CREATE TRIGGER `resource_ADEL` AFTER DELETE ON resource FOR EACH ROW
	DELETE FROM metadata WHERE metadata.id_metadata=resource.id_metadata
-- Edit trigger body code below this line. Do not edit lines above this one

$$


DELIMITER ;

DELIMITER $$
USE `scorm`$$


CREATE TRIGGER `file_ADEL` AFTER DELETE ON file FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
DELETE FROM metadata WHERE metadata.id_metadata=file.id_metadata
$$


DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

