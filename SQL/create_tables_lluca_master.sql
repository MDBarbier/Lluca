USE lluca_master;

/*-*********************************************\
* DYNAMIC DATA - PLAYER ACCOUNTS, DECKS ETC
* These tables need PKs to prevent duplicates
/*-**********************************************\

/*this is a representation of the latest pack in the master database where core set is c0, 
*cycle 1 is ap1 to ap6, deluxe expansion 1 is d7, cycle 2 is ap8-ap13 and so on 
*/
CREATE TABLE control_data
(
version int NOT NULL PRIMARY KEY);

/*
* Holds the data of a player, each row being a player profile. cards_xyy fields are 
* binary integer values indicating if the player owns the pack in question
*/
CREATE TABLE player_account 
(
user_name varchar(50) NOT NULL PRIMARY KEY,
user_password varchar(50),
email_address varchar(50),
cards_c0 int,
cards_ap1 int,
cards_ap2 int,
cards_ap3 int,
cards_ap4 int,
cards_ap5 int,
cards_ap6 int,
cards_d7 int,
cards_ap8 int,
cards_ap9 int,
cards_ap10 int,
cards_ap11 int,
cards_ap12 int,
cards_ap13 int,
cards_d14 int,
cards_ap15 int,
cards_ap16 int,
cards_ap17 int,
cards_ap18 int,
cards_ap19 int,
cards_ap20 int);

/*
* Holds the data of saved quests
*/
CREATE TABLE saved_quests
(
user_name varchar(40) NOT NULL,
deck_name varchar(40) NOT NULL,
PRIMARY KEY (user_name, deck_name),
quest_completed varchar(40),
quest_id int,
quest_player_assigned_name varchar(80),
deck_used int,
date_completed date,
vps_achieved int,
no_of_players int,
hero1 int, /* these fields refer to heroes.hero_id*/
hero2 int,
hero3 int,
hero4 int,
hero5 int,
hero6 int,
hero7 int,
hero8 int,
hero9 int,
hero10 int,
hero11 int,
hero12 int,
hero13 int,
hero14 int,
hero15 int,
hero16 int
);


/*
* Holds the data of a players saved decks
*/
CREATE TABLE saved_decks
(
user_name varchar(40) NOT NULL,
deck_name varchar(40) NOT NULL,
PRIMARY KEY (user_name, deck_name),
deck_description varchar (200),
date_created date,
card1 varchar(40),
card2 varchar(40),
card3 varchar(40),
card4 varchar(40),
card5 varchar(40),
card6 varchar(40),
card7 varchar(40),
card8 varchar(40),
card9 varchar(40),
card10 varchar(40),
card11 varchar(40),
card12 varchar(40),
card13 varchar(40),
card14 varchar(40),
card15 varchar(40),
card16 varchar(40),
card17 varchar(40),
card18 varchar(40),
card19 varchar(40),
card20 varchar(40),
card21 varchar(40),
card22 varchar(40),
card23 varchar(40),
card24 varchar(40),
card25 varchar(40),
card26 varchar(40),
card27 varchar(40),
card28 varchar(40),
card29 varchar(40),
card30 varchar(40),
card31 varchar(40),
card32 varchar(40),
card33 varchar(40),
card34 varchar(40),
card35 varchar(40),
card36 varchar(40),
card37 varchar(40),
card38 varchar(40),
card39 varchar(40),
card40 varchar(40),
card41 varchar(40),
card42 varchar(40),
card43 varchar(40),
card44 varchar(40),
card45 varchar(40),
card46 varchar(40),
card47 varchar(40),
card48 varchar(40),
card49 varchar(40),
card50 varchar(40),
card51 varchar(40),
card52 varchar(40),
card53 varchar(40),
card54 varchar(40),
card55 varchar(40),
card56 varchar(40),
card57 varchar(40),
card58 varchar(40),
card59 varchar(40),
card60 varchar(40));



/*-*********************************************\
* FIXED DATA - STATIC AND WILL NOT CHANGE 
* These tables don't need PKs
/*-**********************************************\


/*
* Holds the data of player cards
*/
CREATE TABLE player_cards
(
playercard_no int,
playercard_box int,
playercard_name varchar(40),
playercard_unique int, /* 1 for true 0 for false*/
playercard_type varchar(40),
playercard_cost int,
playercard_sphere varchar(40),
playercard_special_rules varchar (400),
playercard_count int,
playercard_keyword1 varchar(40),
playercard_keyword2 varchar(40),
playercard_keyword3 varchar(40),
playercard_keyword4 varchar(40),
playercard_trait1 varchar(40),
playercard_trait2 varchar(40),
playercard_trait3 varchar(40),
playercard_trait4 varchar(40),
playercard_ally_quest int,
playercard_ally_attack int,
playercard_ally_defence int,
playercard_ally_hp int,
playercard_secrecy int);
/*
* Holds the data of encounter deck cards
*/
CREATE TABLE encounter_cards
(
encountercard_no int,
encountercard_deckpart int,
encountercard_name varchar(40),
encountercard_type varchar(40),
encountercard_threat int,
encountercard_engage int,
encountercard_attack int,
encountercard_defence int,
encountercard_hp int,
encountercard_special_rules varchar (400),
encountercard_shadow varchar (200),
encountercard_unique int, /* 1 for true 0 for false*/
encountercard_vp int,
encountercard_trait1 varchar(40),
encountercard_trait2 varchar(40),
encountercard_trait3 varchar(40),
encountercard_trait4 varchar(40),
encountercard_keyword1 varchar(40),
encountercard_keyword2 varchar(40),
encountercard_keyword3 varchar(40),
encountercard_keyword4 varchar(40)
);

/*
* Holds the data of keywords and rules
*/
CREATE TABLE keywords_and_rules
(
rule_no int,
rule_name varchar(40),
rule_description varchar(500),
rule_effects varchar(400)
);

/*
* Holds the data of hero cards
*/
CREATE TABLE heroes
(
herocard_no int,
herocard_box int,
herocard_name varchar(40),
herocard_threat int,
herocard_quest int,
herocard_attack int,
herocard_defence int,
herocard_hp int,
herocard_sphere varchar(40),
herocard_specialrules varchar(400),
herocard_keyword1 varchar(40),
herocard_keyword2 varchar(40),
herocard_keyword3 varchar(40),
herocard_keyword4 varchar(40),
herocard_trait1 varchar(40),
herocard_trait2 varchar(40),
herocard_trait3 varchar(40),
herocard_trait4 varchar(40));

/*
* Holds the data of quest cards
*/
CREATE TABLE quest_cards
(
questcard_number int,
questcard_box int,
questcard_deckpart int,	
questcard_name varchar(40),	
questcard_type varchar(40),	
questcard_part int,	
questcard_progress_req int,	
questcard_cost int,
questcard_unique int,	
questcard_text varchar(400),
questcard_count	int,
questcard_keyword1 varchar(40),
questcard_keyword2 varchar(40),
questcard_keyword3 varchar(40),
questcard_keyword4 varchar(40),
questcard_trait1 varchar(40),
questcard_trait2 varchar(40),
questcard_trait3 varchar(40),
questcard_trait4 varchar(40),
questcard_hp int,
questcard_quest int,
questcard_attack int,
questcard_def int,
questcard_secrecy int,
questcard_vp int
);

/*
* Holds the various deckpart details and the corresponding ID codes used elswhere
*/
CREATE TABLE deckparts
(
deckpart_id int,
deckpart_name varchar(80),
deckpart_box_id int,
deckpart_box varchar(80),
deckpart_cycle varchar(80)
);