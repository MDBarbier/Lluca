package com.tm470.mb24853.projectlluca;

/**
 * Created by Admin on 06/04/2015.
 */
public class LLuca_Local_DB_schema {

    //db definition
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LLuca_Local.db";
    public static final String KEY_ID = "_id";


    //-------------------------------------------------------------------------------------------------------------------------------------//
    //
    // PLAYER_ACCOUNT
    //
    //-------------------------------------------------------------------------------------------------------------------------------------//


    //table definition for player accounts
    public static final String TABLE_NAME_PLAYERS = "Player_account";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_EMAIL = "email_address";
    public static final String COLUMN_NAME_LOGGED_IN = "logged_in";


    //SQL statement to create player_account table
    private static final String SQL_CREATE_PLAYER_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_NAME_PLAYERS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_USERNAME + " TEXT NOT NULL, " + COLUMN_NAME_PASSWORD + " TEXT NOT NULL, " + COLUMN_NAME_EMAIL + " TEXT, " + COLUMN_NAME_LOGGED_IN + " INT)";

    //Getter for the player account table SQL
    public String getPlayerAccountCreate()
    {
        return SQL_CREATE_PLAYER_ACCOUNT_TABLE;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------//
    //
    // OWNED_PACKS
    //
    //-------------------------------------------------------------------------------------------------------------------------------------//


    //table definition for owned packs
    public static final String TABLE_NAME_OWNED_PACKS = "Owned_packs";
    public static final String COLUMN_NAME_OWNED_PACK_ID = "id";
    public static final String COLUMN_NAME_OWNING_USER = "owning_user" ;
    public static final String COLUMN_NAME_PACK_NAME = "pack_name";


    //SQL statement to create player_account table
    private static final String SQL_CREATE_OWNED_PACKS_TABLE = "CREATE TABLE " + TABLE_NAME_OWNED_PACKS + " (" + COLUMN_NAME_OWNED_PACK_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_OWNING_USER + " TEXT NOT NULL, " + COLUMN_NAME_PACK_NAME + " TEXT)";

    //Getter for the player account table SQL
    public String getOwnedPacksCreate()
    {
        return SQL_CREATE_OWNED_PACKS_TABLE;
    }


    //-------------------------------------------------------------------------------------------------------------------------------------//
    //
    // DECKPARTS
    //
    //-------------------------------------------------------------------------------------------------------------------------------------//

    //table definition for deckparts
    public static final String TABLE_NAME_DECKPARTS = "Deckparts";
    public static final String COLUMN_NAME_DECKPART_ID = "deckpart_id";
    public static final String COLUMN_NAME_DECKPART_NAME = "deckpart_name";
    public static final String COLUMN_NAME_DECKPART_BOX_ID = "deckpart_box_id";
    public static final String COLUMN_NAME_DECKPART_CYCLE = "deckpart_cycle";
    public static final String COLUMN_NAME_DECKPART_BOX = "deckpart_box";

    //SQL statement to create deckpart table
    private static final String SQL_CREATE_DECKPART_TABLE = "CREATE TABLE " + TABLE_NAME_DECKPARTS + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_DECKPART_ID + " INT, " + COLUMN_NAME_DECKPART_NAME + " TEXT, " + COLUMN_NAME_DECKPART_BOX_ID + " INT, " + COLUMN_NAME_DECKPART_CYCLE + " TEXT, " + COLUMN_NAME_DECKPART_BOX + " TEXT)";

    //Getter for the deckpart table SQL
    public String getDeckpartCreate()
    {
        return SQL_CREATE_DECKPART_TABLE;
    }

    //SQL to populate deckpart table
    private static final String SQL_POPULATE_DECKPART = "INSERT INTO 'deckparts' ('deckpart_id', 'deckpart_name', 'deckpart_box_id', 'deckpart_box', 'deckpart_cycle') VALUES (1, 'Spiders of Mirkwood', 1, 'Core Set', 'Shadows of Mirkwood'),(2, 'Passage Through Mirkwood', 1, 'Core Set', 'Shadows of Mirkwood'),(3, 'Journey Down the Anduin', 1, 'Core Set', 'Shadows of Mirkwood'),(4, 'Wilderlands', 1, 'Core Set', 'Shadows of Mirkwood'),(5, 'Escape from Dol Guldur', 1, 'Core Set', 'Shadows of Mirkwood'),(6, 'Dol Guldur Orcs', 1, 'Core Set', 'Shadows of Mirkwood'),(7, 'Saurons Reach', 1, 'Core Set', 'Shadows of Mirkwood'),(8, 'The Hunt for Gollum', 2, 'The Hunt for Gollum', 'Shadows of Mirkwood'),(9, 'Conflict at the Carrock', 3, 'Conflict at the Carrock', 'Shadows of Mirkwood'),(10, 'A Journey to Rhosgobel', 4, 'A Journey to Rhosgobel', 'Shadows of Mirkwood'),(11, 'The Hills of Emyn Muil', 5, 'The Hills of Emyn Muil', 'Shadows of Mirkwood'),(12, 'The Dead Marshes', 6, 'The Dead Marshes', 'Shadows of Mirkwood'),(13, 'Return to Mirkwood', 7, 'Return to Mirkwood', 'Shadows of Mirkwood'),(14, 'Twists and Turns', 8, 'Khazad-dum', 'Dwarrowdelf'),(15, 'Plundering Goblins', 8, 'Khazad-dum', 'Dwarrowdelf'),(16, 'Misty Mountains', 8, 'Khazad-dum', 'Dwarrowdelf'),(17, 'Into the Pit', 8, 'Khazad-dum', 'Dwarrowdelf'),(18, 'Hazards of the Pit', 8, 'Khazad-dum', 'Dwarrowdelf'),(19, 'Goblins of the Deep', 8, 'Khazad-dum', 'Dwarrowdelf'),(20, 'Flight from Moria', 8, 'Khazad-dum', 'Dwarrowdelf'),(21, 'Deeps of Moria', 8, 'Khazad-dum', 'Dwarrowdelf'),(22, 'The Redhorn Gate', 9, 'The Redhorn Gate', 'Dwarrowdelf'),(23, 'Road to Rivendell', 10, 'Road to Rivendell', 'Dwarrowdelf'),(24, 'The Watcher in the Water', 11, 'The Watcher in the Water', 'Dwarrowdelf'),(25, 'The Long Dark', 12, 'The Long Dark', 'Dwarrowdelf'),(26, 'Foundations of Stone', 13, 'Foundations of Stone', 'Dwarrowdelf'),(27, 'Shadow and Flame', 14, 'Shadow and Flame', 'Dwarrowdelf'),(28, 'The Siege of Cair Andros', 15, 'Heirs of Numenor', 'Against the Shadow'),(29, 'Streets of Gondor', 15, 'Heirs of Numenor', 'Against the Shadow'),(30, 'Southrons', 15, 'Heirs of Numenor', 'Against the Shadow'),(31, 'Ravaging Orcs', 15, 'Heirs of Numenor', 'Against the Shadow'),(32, 'Peril in Pelargir', 15, 'Heirs of Numenor', 'Against the Shadow'),(33, 'Mordor Elite', 15, 'Heirs of Numenor', 'Against the Shadow'),(34, 'Into Ithilien', 15, 'Heirs of Numenor', 'Against the Shadow'),(35, 'Creatures of the Forest', 15, 'Heirs of Numenor', 'Against the Shadow'),(36, 'Brooding Forest', 15, 'Heirs of Numenor', 'Against the Shadow'),(37, 'Brigands', 15, 'Heirs of Numenor', 'Against the Shadow'),(38, 'The Stewards Fear', 16, 'The Stewards Fear', 'Against the Shadow'),(39, 'The Druadan Forest', 17, 'The Druadan Forest', 'Against the Shadow'),(40, 'Encounter at Amon Din', 18, 'Encounter at Amon Din', 'Dwarrowdelf'),(41, 'Assault on Osgiliath', 19, 'Assault on Osgiliath', 'Against the Shadow'),(42, 'The Blood of Gondor', 20, 'The Blood of Gondor', 'Against the Shadow'),(43, 'The Morgul Vale', 21, 'The Morgul Vale', 'Against the Shadow'),(44, 'Weary Travelers', 22, 'The Voice of Isengard', 'The Ring-maker'),(45, 'To Catch an Orc', 22, 'The Voice of Isengard', 'The Ring-maker'),(46, 'The Fords of Isen', 22, 'The Voice of Isengard', 'The Ring-maker'),(47, 'The Dunland Warriors', 22, 'The Voice of Isengard', 'The Ring-maker'),(48, 'The Dunland Raiders', 22, 'The Voice of Isengard', 'The Ring-maker'),(49, 'Misty Mountain Orcs', 22, 'The Voice of Isengard', 'The Ring-maker'),(50, 'Into Fangorn', 22, 'The Voice of Isengard', 'The Ring-maker'),(51, 'Broken Lands', 22, 'The Voice of Isengard', 'The Ring-maker'),(52, 'Ancient Forest', 22, 'The Voice of Isengard', 'The Ring-maker'),(53, 'Wilderland', 23, 'On the Doorstep', 'Hobbit'),(54, 'The Lonely Mountain', 23, 'On the Doorstep', 'Hobbit'),(55, 'The Battle of Five Armies', 23, 'On the Doorstep', 'Hobbit'),(56, 'Flies and Spiders', 23, 'On the Doorstep', 'Hobbit'),(57, 'Western Lands', 24, 'Over Hill and Under Hill', 'Hobbit'),(58, 'We Must Away, Ere Break of Day', 24, 'Over Hill and Under Hill', 'Hobbit'),(59, 'The Great Goblin', 24, 'Over Hill and Under Hill', 'Hobbit'),(60, 'Over the Misty Mountains Grim', 24, 'Over Hill and Under Hill', 'Hobbit'),(61, 'Misty Mountain Goblins', 24, 'Over Hill and Under Hill', 'Hobbit'),(62, 'Dungeons Deep and Caverns Dim', 24, 'Over Hill and Under Hill', 'Hobbit'),(63, 'The Battle of Lake-town', 25, 'The Battle of Lake-town', 'POD'),(64, 'The Massing at Osgiliath', 26, 'The Massing at Osgiliath', 'POD'),(65, 'The Stone of Erech', 27, 'The Stone of Erech', 'POD'),(66, 'The Ring', 28, 'The Black Riders', 'The Black Riders'),(67, 'The Nazgul', 28, 'The Black Riders', 'The Black Riders'),(68, 'The Black Riders', 28, 'The Black Riders', 'The Black Riders'),(69, 'Hunted', 28, 'The Black Riders', 'The Black Riders'),(70, 'Flight to the Ford', 28, 'The Black Riders', 'The Black Riders'),(71, 'A Shadow of the Past', 28, 'The Black Riders', 'The Black Riders'),(72, 'A Knife in the Dark', 28, 'The Black Riders', 'The Black Riders')";

    //Getter for the populate deckpart
    public String getDeckpartPopulate()
    {
        return SQL_POPULATE_DECKPART;
    }


    //-------------------------------------------------------------------------------------------------------------------------------------//
    //
    // PLAYER CARDS
    //
    //-------------------------------------------------------------------------------------------------------------------------------------//

    //table definition for deckparts
    public static final String TABLE_NAME_PLAYERCARD = "player_cards";
    public static final String COLUMN_NAME_PLAYERCARD_NO = "playercard_no";
    public static final String COLUMN_NAME_PLAYERCARD_BOX = "playercard_box";
    public static final String COLUMN_NAME_PLAYERCARD_NAME = "playercard_name";



    //SQL for creation
    private static final String SQL_CREATE_PLAYERCARD_TABLE = "";

    //Getter for creation

    //SQL for population

    //Getter for population

}
