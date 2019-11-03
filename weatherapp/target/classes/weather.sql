-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2017 at 09:36 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `weather`
--

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `id` int(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`id`, `name`) VALUES
(634317, 'Akaa'),
(3464713, 'Dias dávila'),
(5795440, 'Gig Harbor'),
(3461936, 'Guará'),
(7647230, 'Koukkuniemi'),
(2643743, 'London'),
(164773, 'Sasa'),
(1566083, 'Thanh pho Ho Chi Minh');

-- --------------------------------------------------------

--
-- Table structure for table `weather`
--

CREATE TABLE `weather` (
  `id` int(255) NOT NULL,
  `city_id` int(255) NOT NULL,
  `weather_data` varchar(500) NOT NULL,
  `weather_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `weather`
--

INSERT INTO `weather` (`id`, `city_id`, `weather_data`, `weather_date`) VALUES
(64, 1566083, '{\"temperature\":\"28\",\"pressure\":\"1008\",\"humidity\":\"88\",\"windSpeed\":\"2.6\",\"cloudsAll\":\"40\",\"weatherMain\":\"Clouds\",\"weatherId\":802,\"weatherIcon\":\"http://openweathermap.org/img/w/03n.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Thanh pho Ho Chi Minh\",\"id\":1566083,\"fromIndex\":0}', '2017-06-01'),
(68, 164773, '{\"temperature\":\"18.173\",\"pressure\":\"959.02\",\"humidity\":\"42\",\"windSpeed\":\"3.25\",\"cloudsAll\":\"0\",\"weatherMain\":\"Clear\",\"weatherId\":800,\"weatherIcon\":\"http://openweathermap.org/img/w/01n.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Sasa\",\"id\":164773,\"fromIndex\":0}', '2017-06-01'),
(69, 7647230, '{\"temperature\":\"11\",\"pressure\":\"998\",\"humidity\":\"66\",\"windSpeed\":\"6.7\",\"cloudsAll\":\"40\",\"weatherMain\":\"Clouds\",\"weatherId\":802,\"weatherIcon\":\"http://openweathermap.org/img/w/03d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Koukkuniemi\",\"id\":7647230,\"fromIndex\":0}', '2017-06-01'),
(71, 3461936, '{\"temperature\":\"27\",\"pressure\":\"1018\",\"humidity\":\"39\",\"windSpeed\":\"4.6\",\"cloudsAll\":\"75\",\"weatherMain\":\"Clouds\",\"weatherId\":803,\"weatherIcon\":\"http://openweathermap.org/img/w/04d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Guará\",\"id\":3461936,\"fromIndex\":0}', '2017-06-01'),
(72, 5795440, '{\"temperature\":\"16.77\",\"pressure\":\"1013\",\"humidity\":\"68\",\"windSpeed\":\"1.4\",\"cloudsAll\":\"56\",\"weatherMain\":\"Clouds\",\"weatherId\":803,\"weatherIcon\":\"http://openweathermap.org/img/w/04d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Gig Harbor\",\"id\":5795440,\"fromIndex\":0}', '2017-06-01'),
(73, 3464713, '{\"temperature\":\"22\",\"pressure\":\"1015\",\"humidity\":\"88\",\"windSpeed\":\"3.6\",\"cloudsAll\":\"75\",\"weatherMain\":\"Clouds\",\"weatherId\":803,\"weatherIcon\":\"http://openweathermap.org/img/w/04d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Dias dávila\",\"id\":3464713,\"fromIndex\":0}', '2017-06-01'),
(74, 634317, '{\"temperature\":\"8\",\"pressure\":\"1000\",\"humidity\":\"45\",\"windSpeed\":\"2.6\",\"cloudsAll\":\"0\",\"weatherMain\":\"Clear\",\"weatherId\":800,\"weatherIcon\":\"http://openweathermap.org/img/w/01d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"Akaa\",\"id\":634317,\"fromIndex\":0}', '2017-06-01'),
(78, 2643743, '{\"temperature\":\"19.41\",\"pressure\":\"1021\",\"humidity\":\"72\",\"windSpeed\":\"4.1\",\"cloudsAll\":\"75\",\"weatherMain\":\"Rain\",\"weatherId\":521,\"weatherIcon\":\"http://openweathermap.org/img/w/09d.png\",\"fDate\":\"Jun 1,2017\",\"name\":\"London\",\"id\":2643743,\"fromIndex\":0}', '2017-06-01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `weather`
--
ALTER TABLE `weather`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `weather`
--
ALTER TABLE `weather`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
