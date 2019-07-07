-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 07, 2019 at 03:48 AM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `caspev`
--

-- --------------------------------------------------------

--
-- Table structure for table `door`
--

CREATE TABLE `door` (
  `id` bigint(20) NOT NULL,
  `level_access` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `uuid` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event_log`
--

CREATE TABLE `event_log` (
  `id` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `door` bigint(20) DEFAULT NULL,
  `ncf_card` bigint(20) DEFAULT NULL,
  `user` bigint(20) DEFAULT NULL,
  `vehicle` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `nfc_card`
--

CREATE TABLE `nfc_card` (
  `id` bigint(20) NOT NULL,
  `code` varchar(11) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(3, 'ROLE_ANONYMOUS'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `level_access` int(11) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `rut` varchar(8) NOT NULL,
  `uuid` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`, `activated`, `email`, `first_name`, `last_name`, `level_access`, `password_hash`, `rut`, `uuid`) VALUES
(1, 'anonymousUser', '2019-07-06 21:47:59', 'anonymousUser', '2019-07-06 21:47:59', b'1', 'admin@mail.com', 'Admin', 'Admin', 3, '$2a$10$6ikE/LSqapoIZp9JSgyMO.maz3xtirMi4l4RsOXdPIrl7wBAZ71o2', '99999999', '8bc238d2-1338-4c49-ae2d-65452fdfa9f9');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` bigint(20) NOT NULL,
  `plate` varchar(6) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `door`
--
ALTER TABLE `door`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qrgojrnbh3jsfj1bbajv4jwun` (`uuid`);

--
-- Indexes for table `event_log`
--
ALTER TABLE `event_log`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_mh69h3x43kri0m3ohia4dwd7r` (`uuid`),
  ADD KEY `FK10r0hswlt8b5iycvie3p5k02v` (`door`),
  ADD KEY `FKatuf7ipe4cbagcu42skbtl227` (`ncf_card`),
  ADD KEY `FKnxijwprxc5ctlt3j28h06tlud` (`user`),
  ADD KEY `FKhhxlnq438h0jw79xtjndx2wd5` (`vehicle`);

--
-- Indexes for table `nfc_card`
--
ALTER TABLE `nfc_card`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_hh9ul8dw25th9oytoyubriv4d` (`code`),
  ADD UNIQUE KEY `UK_fj639gv6nujpfeooe9svclqxh` (`uuid`),
  ADD KEY `FKmvaqvlgg2hmxhegyxilhfklwa` (`user`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK_12eweocf4us5meyparocp1l4t` (`rut`),
  ADD UNIQUE KEY `UK_1xc1iry6gqjrvh5cpajiq7l2f` (`uuid`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_dptrc1lh70j3qrg5v38fmmw7g` (`plate`),
  ADD UNIQUE KEY `UK_4m0d2to8jelo33lcu5siwp5fm` (`uuid`),
  ADD KEY `FKafmyw13bui0mea0w4d3css2sr` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `door`
--
ALTER TABLE `door`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `event_log`
--
ALTER TABLE `event_log`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nfc_card`
--
ALTER TABLE `nfc_card`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event_log`
--
ALTER TABLE `event_log`
  ADD CONSTRAINT `FK10r0hswlt8b5iycvie3p5k02v` FOREIGN KEY (`door`) REFERENCES `door` (`id`),
  ADD CONSTRAINT `FKatuf7ipe4cbagcu42skbtl227` FOREIGN KEY (`ncf_card`) REFERENCES `nfc_card` (`id`),
  ADD CONSTRAINT `FKhhxlnq438h0jw79xtjndx2wd5` FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`id`),
  ADD CONSTRAINT `FKnxijwprxc5ctlt3j28h06tlud` FOREIGN KEY (`user`) REFERENCES `user` (`id`);

--
-- Constraints for table `nfc_card`
--
ALTER TABLE `nfc_card`
  ADD CONSTRAINT `FKmvaqvlgg2hmxhegyxilhfklwa` FOREIGN KEY (`user`) REFERENCES `user` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `FKafmyw13bui0mea0w4d3css2sr` FOREIGN KEY (`user`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
