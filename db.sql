-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2014 at 03:13 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db`
--

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE IF NOT EXISTS `history` (
  `itemname` varchar(40) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int(10) NOT NULL,
  `user` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `hostel` varchar(30) NOT NULL,
  `room` int(3) NOT NULL,
  `phone` bigint(12) NOT NULL,
  `email` varchar(30) NOT NULL,
  `searcher` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`itemname`, `description`, `price`, `user`, `name`, `hostel`, `room`, `phone`, `email`, `searcher`) VALUES
('sumitanh das', 'fkhbrt;', 23, 'bjfdd', 'vcnjgfnj ', '0', 242, 23728947342, 'kgfdkgdk@2bdgjbfd.vbdbvd', 'manku'),
('sumitanh', 'gdsgoieorihfoiofi', 32, 'bjfdd', 'vcnjgfnj ', '0', 242, 23728947342, 'kgfdkgdk@2bdgjbfd.vbdbvd', 'manku');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE IF NOT EXISTS `items` (
  `itemname` varchar(50) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int(10) NOT NULL,
  `books` varchar(1) NOT NULL,
  `gadgets` varchar(1) NOT NULL,
  `formals` varchar(1) NOT NULL,
  `lab_practicals` varchar(1) NOT NULL,
  `Hostels` varchar(1) NOT NULL,
  `Miscellaneous` varchar(1) NOT NULL,
  `user` varchar(30) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`itemname`, `description`, `price`, `books`, `gadgets`, `formals`, `lab_practicals`, `Hostels`, `Miscellaneous`, `user`, `date`) VALUES
('sumitanh das', 'fkhbrt;', 23, '1', '0', '0', '0', '0', '0', 'bjfdd', '2014-10-14'),
('sumitanh', 'gdsgoieorihfoiofi', 32, '0', '1', '0', '1', '0', '0', 'bjfdd', '2014-10-14'),
('nfs rivals', 'nfs cracked iso with unlimited cheats', 32, '0', '1', '0', '0', '0', '0', 'bjfdd', '2014-10-14'),
('foot', 'dfgdfkls', 34, '0', '0', '0', '0', '1', '0', 'bjfdd', '2014-10-14'),
('goalmal', 'movie', 10, '0', '0', '0', '0', '0', '1', 'bjfdd', '2014-10-14'),
('assassins', 'game234', 23, '0', '0', '0', '0', '0', '1', 'bjfdd', '2014-10-16'),
('fgk;lhtr', 'fhfdkhbjldfkbd2', 33, '0', '0', '0', '0', '1', '0', 'bjfdd', '2014-10-16'),
('kdjkldj', 'jkkfldfkl', 322, '0', '1', '0', '0', '0', '0', 'manku', '2014-10-16');

-- --------------------------------------------------------

--
-- Table structure for table `notific`
--

CREATE TABLE IF NOT EXISTS `notific` (
  `itemname` varchar(30) NOT NULL,
  `recipient` varchar(30) NOT NULL,
  `user` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `hostel` varchar(30) NOT NULL,
  `room` varchar(3) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `email` varchar(30) NOT NULL,
  `status` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notific`
--

INSERT INTO `notific` (`itemname`, `recipient`, `user`, `name`, `hostel`, `room`, `phone`, `email`, `status`) VALUES
('jfvjs', 'fjgkjfgerk', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('jfvjsfbglnb gl', 'fjgkjfgerk', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('jvklfjbvkletjl', 'bjfdd', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
(',mbnmdfs', 'bjfdd', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('umang123', 'bjfdd', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('games ', 'manku', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('sumitabha das', 'manku', 'bjfdd', 'vcnjgfnj ', '0', '242', '23728947342', 'kgfdkgdk@2bdgjbfd.vbdbvd', '1'),
('fdhjdfo', 'bjfdd', 'umang', 'gklgtkn gndfngkl', '0', '123', '34932578937', 'gfdjf@dfkjvdfk.cjkrvrk', '1'),
('sumitanh das', 'bjfdd', 'manku', 'fdbgdfkjk', '0', '12', '3454387568', 'dfdfjsk@dfhvkjf.vdfv', '1');

-- --------------------------------------------------------

--
-- Table structure for table `tb`
--

CREATE TABLE IF NOT EXISTS `tb` (
  `name` varchar(30) DEFAULT NULL,
  `regno` varchar(30) DEFAULT NULL,
  `hostel` varchar(30) NOT NULL,
  `room` int(3) NOT NULL,
  `phone` bigint(12) NOT NULL,
  `email` varchar(30) NOT NULL,
  `depart` varchar(40) NOT NULL,
  `user` varchar(30) NOT NULL,
  `pass` varchar(30) NOT NULL,
  `security` varchar(60) NOT NULL,
  `answer` varchar(30) NOT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb`
--

INSERT INTO `tb` (`name`, `regno`, `hostel`, `room`, `phone`, `email`, `depart`, `user`, `pass`, `security`, `answer`) VALUES
('vcnjgfnj ', 'jjbljfldjl', '0', 242, 23728947342, 'kgfdkgdk@2bdgjbfd.vbdbvd', '0', 'bjfdd', '12345', '0', 'dvjdfghfd'),
('dfjdsklgdsj', 'jlkjlgvjkl', '0', 123, 3242343274937, 'gkjbhkt@bkdfmbl.chjvk', '0', 'gvkjfghk', '12345', '0', 'fbvjfdk'),
('fdbgdfkjk', '223739', '0', 12, 3454387568, 'dfdfjsk@dfhvkjf.vdfv', '0', 'manku', 'manku', '0', 'mine'),
('erufruhgj', 'hghkjhdfhg', '0', 123, 253712577979, 'hgjdsfhjvk@hbdjfh.chvdkjh', '0', 'shiva', '12345', '0', 'yuygfre'),
('gklgtkn gndfngkl', 'klgvdflnbfkd', '0', 123, 34932578937, 'gfdjf@dfkjvdfk.cjkrvrk', '0', 'umang', '1234', '0', '123');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
