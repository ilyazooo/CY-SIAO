-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : Dim 28 mai 2023 à 20:58
-- Version du serveur :  5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cysiao`
--

-- --------------------------------------------------------

--
-- Structure de la table `bed`
--

DROP TABLE IF EXISTS `bed`;
CREATE TABLE IF NOT EXISTS `bed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bed_size` varchar(255) NOT NULL,
  `id_occupant` int(11) NOT NULL,
  `id_room` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `bed`
--

INSERT INTO `bed` (`id`, `bed_size`, `id_occupant`, `id_room`) VALUES
(21, '2', 0, 1),
(20, '2', 0, 2),
(19, '1', 0, 2),
(18, '1', 0, 1),
(17, '1', 0, 3),
(16, '1', 0, 4),
(15, '1', 0, 2);

-- --------------------------------------------------------

--
-- Structure de la table `bedoccupancy`
--

DROP TABLE IF EXISTS `bedoccupancy`;
CREATE TABLE IF NOT EXISTS `bedoccupancy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `bedId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `bedoccupancy`
--

INSERT INTO `bedoccupancy` (`id`, `personId`, `bedId`, `startDate`, `endDate`) VALUES
(36, 8, 17, '2023-05-31', '2023-07-04'),
(35, 7, 18, '2023-05-31', '2023-07-04'),
(32, 6, 21, '2023-05-28', '2023-06-14'),
(31, 1, 21, '2023-05-28', '2023-06-14');

-- --------------------------------------------------------

--
-- Structure de la table `people`
--

DROP TABLE IF EXISTS `people`;
CREATE TABLE IF NOT EXISTS `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastName` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `gender` int(11) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `birthCity` varchar(255) NOT NULL,
  `socialSecurityNumber` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `people`
--

INSERT INTO `people` (`id`, `lastName`, `firstName`, `gender`, `dateOfBirth`, `birthCity`, `socialSecurityNumber`) VALUES
(1, 'Mahmoud ', 'Moustarazi ', 0, '2002-05-10', 'Mantes-La-Jolie', '165161620'),
(6, 'Yenni', 'Tadjer', 0, '2002-06-13', 'Poissy', '1020678498296'),
(7, 'ADEL ', 'YSF', 0, '2002-06-05', 'MARSEILLE', '1020613205014'),
(8, 'Alexandre ', 'TRAN', 0, '2002-03-04', 'Vitry', '0360632162162');

-- --------------------------------------------------------

--
-- Structure de la table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numberOfBeds` int(11) NOT NULL,
  `bedIds` varchar(255) NOT NULL,
  `isAgeRestricted` tinyint(1) NOT NULL,
  `isGenderRestricted` tinyint(1) NOT NULL,
  `title` varchar(255) NOT NULL,
  `ageMin` int(11) NOT NULL,
  `ageMax` int(11) NOT NULL,
  `genderRestriction` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `room`
--

INSERT INTO `room` (`id`, `numberOfBeds`, `bedIds`, `isAgeRestricted`, `isGenderRestricted`, `title`, `ageMin`, `ageMax`, `genderRestriction`) VALUES
(1, 5, '0', 1, 1, 'SALLE DU TEMPS', 18, 63, '0'),
(2, 5, '1,2', 1, 1, 'SALLE 212', 19, 58, '1,2'),
(3, 5, '', 0, 1, 'SALLE 313', 12, 29, '0,1'),
(4, 4, '', 0, 1, 'SALLE 652', 16, 32, '0');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
