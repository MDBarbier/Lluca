-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.21 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.1.0.4913
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Dumping data for table lluca_master.deckparts: ~0 rows (approximately)
/*!40000 ALTER TABLE `deckparts` DISABLE KEYS */;
INSERT INTO `deckparts` (`deckpart_id`, `deckpart_name`, `deckpart_box_id`, `deckpart_box`, `deckpart_cycle`) VALUES
	(1, 'Spiders of Mirkwood', 1, 'Core Set', 'Shadows of Mirkwood'),
	(2, 'Passage Through Mirkwood', 1, 'Core Set', 'Shadows of Mirkwood'),
	(3, 'Journey Down the Anduin', 1, 'Core Set', 'Shadows of Mirkwood'),
	(4, 'Wilderlands', 1, 'Core Set', 'Shadows of Mirkwood'),
	(5, 'Escape from Dol Guldur', 1, 'Core Set', 'Shadows of Mirkwood'),
	(6, 'Dol Guldur Orcs', 1, 'Core Set', 'Shadows of Mirkwood'),
	(7, 'Saurons Reach', 1, 'Core Set', 'Shadows of Mirkwood'),
	(8, 'The Hunt for Gollum', 2, 'The Hunt for Gollum', 'Shadows of Mirkwood'),
	(9, 'Conflict at the Carrock', 3, 'Conflict at the Carrock', 'Shadows of Mirkwood'),
	(10, 'A Journey to Rhosgobel', 4, 'A Journey to Rhosgobel', 'Shadows of Mirkwood'),
	(11, 'The Hills of Emyn Muil', 5, 'The Hills of Emyn Muil', 'Shadows of Mirkwood'),
	(12, 'The Dead Marshes', 6, 'The Dead Marshes', 'Shadows of Mirkwood'),
	(13, 'Return to Mirkwood', 7, 'Return to Mirkwood', 'Shadows of Mirkwood'),
	(14, 'Twists and Turns', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(15, 'Plundering Goblins', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(16, 'Misty Mountains', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(17, 'Into the Pit', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(18, 'Hazards of the Pit', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(19, 'Goblins of the Deep', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(20, 'Flight from Moria', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(21, 'Deeps of Moria', 8, 'Khazad-dum', 'Dwarrowdelf'),
	(22, 'The Redhorn Gate', 9, 'The Redhorn Gate', 'Dwarrowdelf'),
	(23, 'Road to Rivendell', 10, 'Road to Rivendell', 'Dwarrowdelf'),
	(24, 'The Watcher in the Water', 11, 'The Watcher in the Water', 'Dwarrowdelf'),
	(25, 'The Long Dark', 12, 'The Long Dark', 'Dwarrowdelf'),
	(26, 'Foundations of Stone', 13, 'Foundations of Stone', 'Dwarrowdelf'),
	(27, 'Shadow and Flame', 14, 'Shadow and Flame', 'Dwarrowdelf'),
	(28, 'The Siege of Cair Andros', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(29, 'Streets of Gondor', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(30, 'Southrons', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(31, 'Ravaging Orcs', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(32, 'Peril in Pelargir', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(33, 'Mordor Elite', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(34, 'Into Ithilien', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(35, 'Creatures of the Forest', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(36, 'Brooding Forest', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(37, 'Brigands', 15, 'Heirs of Numenor', 'Against the Shadow'),
	(38, 'The Stewards Fear', 16, 'The Stewards Fear', 'Against the Shadow'),
	(39, 'The Druadan Forest', 17, 'The Druadan Forest', 'Against the Shadow'),
	(40, 'Encounter at Amon Din', 18, 'Encounter at Amon Din', 'Dwarrowdelf'),
	(41, 'Assault on Osgiliath', 19, 'Assault on Osgiliath', 'Against the Shadow'),
	(42, 'The Blood of Gondor', 20, 'The Blood of Gondor', 'Against the Shadow'),
	(43, 'The Morgul Vale', 21, 'The Morgul Vale', 'Against the Shadow'),
	(44, 'Weary Travelers', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(45, 'To Catch an Orc', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(46, 'The Fords of Isen', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(47, 'The Dunland Warriors', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(48, 'The Dunland Raiders', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(49, 'Misty Mountain Orcs', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(50, 'Into Fangorn', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(51, 'Broken Lands', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(52, 'Ancient Forest', 22, 'The Voice of Isengard', 'The Ring-maker'),
	(53, 'Wilderland', 23, 'On the Doorstep', 'Hobbit'),
	(54, 'The Lonely Mountain', 23, 'On the Doorstep', 'Hobbit'),
	(55, 'The Battle of Five Armies', 23, 'On the Doorstep', 'Hobbit'),
	(56, 'Flies and Spiders', 23, 'On the Doorstep', 'Hobbit'),
	(57, 'Western Lands', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(58, 'We Must Away Ere Break of Day', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(59, 'The Great Goblin', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(60, 'Over the Misty Mountains Grim', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(61, 'Misty Mountain Goblins', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(62, 'Dungeons Deep and Caverns Dim', 24, 'Over Hill and Under Hill', 'Hobbit'),
	(63, 'The Battle of Lake-town', 25, 'The Battle of Lake-town', 'POD'),
	(64, 'The Massing at Osgiliath', 26, 'The Massing at Osgiliath', 'POD'),
	(65, 'The Stone of Erech', 27, 'The Stone of Erech', 'POD'),
	(66, 'The Ring', 28, 'The Black Riders', 'The Black Riders'),
	(67, 'The Nazgul', 28, 'The Black Riders', 'The Black Riders'),
	(68, 'The Black Riders', 28, 'The Black Riders', 'The Black Riders'),
	(69, 'Hunted', 28, 'The Black Riders', 'The Black Riders'),
	(70, 'Flight to the Ford', 28, 'The Black Riders', 'The Black Riders'),
	(71, 'A Shadow of the Past', 28, 'The Black Riders', 'The Black Riders'),
	(72, 'A Knife in the Dark', 28, 'The Black Riders', 'The Black Riders');
/*!40000 ALTER TABLE `deckparts` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
