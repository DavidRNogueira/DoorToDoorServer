SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `organization` (
    `name` varchar(100) DEFAULT NULL,
    `city` varchar(100) DEFAULT NULL,
    `state` varchar(30) DEFAULT NULL,
    `country`varchar(100) DEFAULT NULL,
    `id` varchar(50) NOT NULL UNIQUE,

    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT  CHARSET=latin1;

CREATE TABLE `user` (
    `email` varchar(55) UNIQUE,
    `password` varchar(500) DEFAULT NULL,
    `country` varchar(50) DEFAULT NULL,
    `org_fk`varchar(200) DEFAULT NULL,
    `phone_number` varchar(40) DEFAULT NULL,
    `id` varchar(36) NOT NULL UNIQUE,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`org_fk`)
    REFERENCES `organization`(`id`)
) ENGINE=InnoDB DEFAULT  CHARSET=latin1;

CREATE TABLE `coordinate` (
    `id` VARCHAR(55) UNIQUE,
    `lat` VARCHAR(100) DEFAULT NULL,
    `lng` VARCHAR(100) DEFAULT NULL,
    `task_fk` VARCHAR(55) DEFAULT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`task_fk`)
    REFERENCES `task`(`id`)
) ENGINE=InnoDB DEFAULT  CHARSET=latin1;

CREATE TABLE `task` (
    `id` VARCHAR(55) UNIQUE,
    `address` VARCHAR(300) DEFAULT NULL,
    `city` VARCHAR(100) DEFAULT NULL,
    `state` VARCHAR(100) DEFAULT NULL,
    `map_number` int(10) DEFAULT NULL,
    `org_fk` VARCHAR(100) DEFAULT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`org_fk`)
    REFERENCES `organization`(`id`)
) ENGINE=InnoDB DEFAULT  CHARSET=latin1;

SET FOREIGN_KEY_CHECKS=1;