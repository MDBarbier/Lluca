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
last_sync varchar(50)
);

/*
* This is an EAV table for holding the details of a players owned packs
* Each record refers to the player by their user name and has the pack name
*/
CREATE TABLE owned_packs
(
id int NOT NULL PRIMARY KEY,
owning_user varchar(50) NOT NULL,
pack_name varchar(80)
);

/*
* Holds the data of a players saved decks
* Uses EAV format
* A row with no card_name indicates a deck that has been created
* but no cards have been added
*/
CREATE TABLE custom_decks
(
id int NOT NULL PRIMARY KEY,
owning_user varchar(50) NOT NULL,
card_name varchar(40),
deck_name varchar(40) NOT NULL
);



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
encountercard_box int,
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
herocard_special_rules varchar(400),
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
questcard_no int,
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
questcard_defence int,
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
deckpart_cycle varchar(80),
deckpart_parent int /*1 for parent 0 for child*/
);