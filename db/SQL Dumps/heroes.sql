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
-- Dumping data for table lluca_master.heroes: ~51 rows (approximately)
/*!40000 ALTER TABLE `heroes` DISABLE KEYS */;
INSERT INTO `heroes` (`herocard_no`, `herocard_box`, `herocard_name`, `herocard_threat`, `herocard_quest`, `herocard_attack`, `herocard_defence`, `herocard_hp`, `herocard_sphere`, `herocard_special_rules`, `herocard_keyword1`, `herocard_keyword2`, `herocard_keyword3`, `herocard_keyword4`, `herocard_trait1`, `herocard_trait2`, `herocard_trait3`, `herocard_trait4`) VALUES
	(25, 3, 'Frodo Baggins', 7, 2, 1, 2, 2, 'Spirit', 'Response: After Frodo Baggins is damaged cancel the damage and instead raise your threat by the amount of damage he would have been dealt. [Limit once per phase.]', '', '', '', '', 'Hobbit', '', '', ''),
	(0, 2, 'Bilbo Baggins [HfG]', 9, 1, 1, 2, 2, 'Lore', 'The first player draws 1 additional card in the resource phase.', '', '', '', '', 'Hobbit', '', '', ''),
	(50, 4, 'Prince Imrahil', 11, 2, 3, 2, 4, 'Leadership', 'Response: After a character leaves play ready Prince Imrahil. [Limit once per round.]', '', '', '', '', 'Gondor', 'Noble', '', ''),
	(81, 19, 'Faramir', 0, 2, 2, 2, 5, 'Lore', 'Faramir gets +1 [Attack] for each enemy in the staging area.', 'Ranged', '', '', '', 'Gondor', 'Ranger', 'Noble', ''),
	(1, 1, 'Aragorn [CORE]', 12, 2, 3, 2, 5, 'Leadership', 'Response: After Aragorn commits to a quest spend 1 resource from his resource pool to ready him.', 'Sentinel', '', '', '', 'Dundedain', 'Noble', 'Ranger', ''),
	(2, 1, 'Theodred', 8, 1, 2, 1, 4, 'Leadership', 'Response: After Th‚odred commits to a quest choose a hero committed to that quest.  Add 1 resource to that heros resource pool.', '', '', '', '', 'Noble', 'Rohan', 'Warrior', ''),
	(3, 1, 'Gloin', 9, 2, 2, 1, 4, 'Leadership', 'Response: After Gl¢in suffers damage add 1 resource to his resource pool for each point of damage he just suffered.', '', '', '', '', 'Dwarf', 'Noble', '', ''),
	(4, 1, 'Gimli', 11, 2, 2, 2, 5, 'Tactics', 'Gimli gets +1 Attack for each damage token on him.', '', '', '', '', 'Drawf', 'Noble', 'Warrior', ''),
	(5, 1, 'Legolas', 9, 1, 3, 1, 4, 'Tactics', 'Response: After Legolas participates in an attack that destroys an enemy place 2 progress tokens on the current quest.', 'Ranged', '', '', '', 'Noble', 'Silvan', 'Warrior', ''),
	(6, 1, 'Thalin', 9, 1, 2, 2, 4, 'Tactics', 'While Thalin is committed to a quest deal 1 damage to each enemy as it is revealed by the encounter deck.', '', '', '', '', 'Dwarf', 'Warrior', '', ''),
	(7, 1, 'Owyn', 9, 4, 1, 1, 3, 'Spirit', 'Action: Discard 1 card from your hand to give owyn +1 Willpower until the end of the phase. This effect may be triggered by each player once each round.', '', '', '', '', 'Noble', 'Rohan', '', ''),
	(8, 1, 'Eleanor', 7, 1, 1, 2, 3, 'Spirit', 'Response: Exhaust Eleanor to cancel the when revealed effects of a treachery card just revealed by the encounter deck. Then discard that card and replace it with the next card from the encounter deck.', '', '', '', '', 'Gondor', 'Noble', '', ''),
	(9, 1, 'Dunhere', 8, 1, 2, 1, 4, 'Spirit', 'D£nhere can target enemies in the staging area when he attacks alone. When doing so he gets +1 Attack.', '', '', '', '', 'Rohan', 'Warrior', '', ''),
	(10, 1, 'Denethor', 8, 1, 1, 3, 3, 'Lore', 'Action: Exhaust Denethor to look at the top card of the encounter deck. You may move that card to the bottom of the deck.', '', '', '', '', 'Gondor', 'Noble', 'Steward', ''),
	(11, 1, 'Glorfindel [CORE]', 12, 3, 3, 1, 5, 'Lore', 'Action: Pay 1 resource from Glorfindels pool to heal 1 damage on any character. [Limit once per round.]', '', '', '', '', 'Noble', 'Noldor', 'Warrior', ''),
	(12, 1, 'Beravor', 10, 2, 2, 2, 4, 'Lore', 'Action: Exhaust Beravor to choose a player. That player draws 2 cards.', '', '', '', '', 'Dundedain', 'Ranger', '', ''),
	(56, 18, 'Pippin', 6, 2, 1, 1, 2, 'Spirit', 'If each hero you control has the Hobbit trait Pippin gains: Response: After an enemy engages you raise your threat by 3 to return it to the staging area. Until the end of the round that enemy cannot engage you.', '', '', '', '', 'Hobbit', '', '', ''),
	(101, 13, 'Glorfindel [FoS]', 5, 3, 3, 1, 5, 'Spirit', 'Forced: After Glorfindel exhausts to commit to a quest raise your threat by 1.', '', '', '', '', 'Noldor', 'Noble', 'Warrior', ''),
	(1, 15, 'Beregond', 10, 0, 1, 4, 4, 'Tactics', 'Lower the cost to play Weapon and Armor attachments on Beregond by 2.', 'Sentinel', '', '', '', 'Gondor', 'Warrior', '', ''),
	(2, 15, 'Boromir', 11, 1, 3, 2, 5, 'Leadership', 'While Boromir has at least 1 resource in his resource pool Gondor allies get +1 Attack.', '', '', '', '', 'Gondor', 'Warrior', 'Noble', ''),
	(1, 8, 'Dwalin', 9, 1, 2, 2, 4, 'Spirit', 'Response: After Dwalin attacks and destroys an Orc enemy lower your threat by 2.', '', '', '', '', 'Dwarf', '', '', ''),
	(2, 8, 'Bifur', 7, 2, 1, 2, 3, 'Lore', 'Action: Pay 1 resource from a heros resource pool to add 1 resource to Bifurs resource pool. Any player may trigger this ability. [Limit once per round.]', '', '', '', '', 'Dwarf', '', '', ''),
	(1, 23, 'Bilbo Baggins [OtD]', 0, 1, 1, 1, 3, 'Baggins', 'Bilbo Baggins does not count against the hero limit and cannot gain resources from non-treasure cards. The first player gains control of Bilbo Baggins.\nAction: Spend one [Baggins] resource to search your deck for a treasure card and add it to your hand.\nIf Bilbo Baggins leaves play the players have lost the game.', '', '', '', '', 'Hobbit', 'Burglar', '', ''),
	(2, 23, 'Balin', 9, 2, 1, 2, 4, 'Leadership', 'Response: Play 1 resource from Balins resource pool to cancel a shadow effect just triggered during an attack. Then deal the attacking enemy another shadow card. [Limit once per attack.]', '', '', '', '', 'Dwarf', '', '', ''),
	(3, 23, 'Bard the Bowman', 11, 2, 3, 2, 4, 'Tactics', 'When Bard the Bowman makes a ranged attack the enemy he attacks gets -2 [Defense] until the end of the phase.', '', '', '', '', 'Esgaroth', 'Warrior', '', ''),
	(4, 23, 'Oin', 8, 2, 1, 1, 4, 'Spirit', 'While you control at least 5 Dwarf characters Oin gets +1 [Attack] and gains the [Tactics] resource icon.', '', '', '', '', 'Dwarf', '', '', ''),
	(5, 23, 'Bombur [OtD]', 8, 0, 1, 2, 5, 'Lore', 'When counting the number of Dwarf characters you control Bombur counts as two.', '', '', '', '', 'Dwarf', '', '', ''),
	(1, 24, 'Bilbo Baggins [OH]', 0, 1, 1, 1, 3, 'Baggins', 'The first player gains control of Bilbo Baggins. Bilbo Baggins cannot gain resources from player card effects. If Bilbo leaves play the players have lost the game', '', '', '', '', 'Hobbit', '', '', ''),
	(2, 24, 'Thorin Oakenshield', 12, 3, 3, 1, 5, 'Leadership', 'If you control at least 5 Dwarf characters add 1 additional resource to Thorin Oakenshields pool when you collect resources during the resource phase.', '', '', '', '', 'Dwarf', 'Noble', 'Warrior', ''),
	(3, 24, 'Nori', 9, 2, 1, 2, 4, 'Spirit', 'Response: After a Dwarf ally enters play under your control reduce your threat by 1.', '', '', '', '', 'Dwarf', '', '', ''),
	(4, 24, 'Ori', 8, 2, 2, 1, 3, 'Lore', 'If you control at least 5 Dwarf characters draw 1 additional card at the beginning of the resource phase. ', '', '', '', '', 'Dwarf', '', '', ''),
	(5, 24, 'Beorn', 12, 0, 5, 1, 10, 'Tactics', 'Cannot have attachments.\nImmune to player card effects.\nBeorn does not exhaust to defend.', 'Sentinel', '', '', '', 'Beorning', 'Warrior', '', ''),
	(116, 7, 'Dain Ironfoot', 11, 1, 2, 3, 5, 'Leadership', 'While Dain Ironfoot is ready Dwarf characters get +1 Attack and +1 Willpower.', '', '', '', '', 'Dwarf', '', '', ''),
	(28, 10, 'Elladan', 10, 2, 1, 2, 4, 'Tactics', 'While Elrohir is in play Elladan gets +2 Attack.\nResponse: After Elladan is declared as an attacker pay 1 resource from his resource pool to ready him. ', '', '', '', '', 'Noldor', 'Noble', 'Ranger', ''),
	(128, 14, 'Elrond', 13, 3, 2, 3, 4, 'Lore', 'You may spend resources from Elronds resource pool to pay for [Spirit] [Leadership] and [Tactics] allies. Response: After a character is healed by another card effect heal 1 damage on it. ', '', '', '', '', 'Noldor', 'Noble', '', ''),
	(1, 28, 'Frodo Baggins', 0, 2, 1, 2, 2, 'Fellowship', 'Response: Spend 1 [Fellowship] resource and exhaust The One Ring to cancel the effects of an encounter card just revealed from the encounter deck. Shuffle that card back into the encounter deck and reveal another encounter card. ', '', '', '', '', 'Hobbit', 'Ringbearer', '', ''),
	(2, 28, 'Sam Gamgee', 8, 3, 1, 1, 3, 'Leadership', 'Response: After you engage an enemy with a higher engagement cost than your threat ready Sam Gamgee. He gets +1 [Willpower] +1 [Attack] and +1 [Defense] until the end of the round.', '', '', '', '', 'Hobbit', '', '', ''),
	(3, 28, 'Merry', 6, 2, 0, 1, 2, 'Tactics', 'Merry gets +1 [Attack] for each Hobbit hero you control.\nResponse: After Merry participates in an attack that destroys an enemy ready another character that participated in that attack. ', '', '', '', '', 'Hobbit', '', '', ''),
	(4, 28, 'Pippin', 6, 2, 1, 1, 2, 'Lore', 'Each enemy in play gets +1 engagement cost for each Hobbit hero you control.\nResponse: After you engage an enemy with a engagement cost higher than your threat draw a card. ', '', '', '', '', 'Hobbit', '', '', ''),
	(5, 28, 'Fatty Bolger', 7, 1, 1, 2, 3, 'Spirit', 'Action: Exhaust Fatty Bolger to choose an enemy in the staging area and raise your threat by that enemys [Threat]. Until the end of the phase that enemy does not contribute its [Threat]. [Limit once per round.] ', '', '', '', '', 'Hobbit', '', '', ''),
	(107, 20, 'Caldara', 8, 2, 1, 2, 3, 'Spirit', 'Action: Discard Caldara to put 1 [Spirit] ally from your discard pile into play for each other hero you control with a printed [Spirit] resource icon.', '', '', '', '', 'Gondor', '', '', ''),
	(95, 6, 'Boromir', 11, 1, 3, 2, 5, 'Tactics', 'Action: Raise your threat by 1 to ready Boromir.\nAction: Discard Boromir to deal 2 damage to each enemy engaged with a single player.', '', '', '', '', 'Gondor', 'Noble', 'Warrior', ''),
	(32, 17, 'Mirlonde', 8, 2, 2, 1, 3, 'Lore', 'Each hero  you control with a printed [Lore] resource icon gets -1 threat cost.', '', '', '', '', 'Silvan', '', '', ''),
	(72, 5, 'Brand son of Bain', 10, 2, 3, 2, 2, 'Tactics', 'Response: After Brand son of Bain attacks and defeats an enemy engaged with another player choose and ready one of that players characters.', 'Ranged', '', '', '', 'Dale', '', '', ''),
	(76, 12, 'Hama', 9, 1, 3, 1, 4, 'Tactics', 'Response: After Hama is declared as an attacker return a [Tactics] event from your discard pile to your hand. Then choose and discard 1 card from your hand. ', '', '', '', '', 'Rohan', 'Warrior', '', ''),
	(134, 21, 'Theodeny', 12, 2, 3, 2, 4, 'Tactics', 'Each Hero with a printed [Tactics] resource icon gets +1 [Willpower]', 'Sentinel', '', '', '', 'Rohan', 'Noble', 'Warrior', ''),
	(1, 9, 'Elrohir', 10, 2, 2, 1, 4, 'Leadership', 'When Elladan is in play Elrohir gets +2 Defense. Response: After Elrohir is declared as a defender pay 1 resource from his resource pool to ready him.', '', '', '', '', 'Noldor', 'Noble', 'Ranger', ''),
	(1, 16, 'Hirluin the Fair', 0, 1, 1, 1, 4, 'Leadership', 'You may use resources from Hirluin the Fairs resource pool to pay for Outlands ally cards of any sphere.', 'Ranged', '', '', '', 'Outlands', '', '', ''),
	(1, 22, 'Eomer', 10, 1, 3, 2, 4, 'Tactics', 'Response: After a character leaves play Eomer gets +2 [Attack] until the end of the round. [Limit once per round.]', '', '', '', '', 'Rohan', 'Noble', 'Warrior', ''),
	(2, 22, 'Grima', 9, 2, 1, 2, 3, 'Lore', 'Action: Lower the cost of the next card you play from your hand this round by 1. That card gains Doomed 1. [Limit once per round.]', '', '', '', '', 'Rohan', 'Isengard', '', ''),
	(53, 11, 'Aragorn [WitW]', 12, 2, 3, 2, 5, 'Lore', 'Refresh Action: Reduce your threat to your starting threat level. [Limit once per game.]', 'Sentinel', '', '', '', 'Dundedain', 'Ranger', '', '');
/*!40000 ALTER TABLE `heroes` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
