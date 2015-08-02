<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 11/07/2015
 * Time: 10:17
 */


include "dbconnect.php";

//query the player_account table to get all server users
$queryAll = "SELECT * FROM player_account";
$queryResult = $myConnection->query($queryAll);

//get the json sent by app
$json = file_get_contents('php://input');

//decode once to get username
$obj = json_decode($json);

//decode a second time (as an array, notice the "true" param, to get the decks
$obj2 = json_decode($json, true);

//count how many objects are in the array i.e. how many decks there are (less one for username)
$counter = 0;
foreach($obj2 as $obj3)
{
    $counter++;
}

//take one off for the username
$totalNoDecks = $counter-1;

//set up an array to hold the decknames
$decks = array();

//assign the username
$username = $obj->username;

//assign the decknames to the array
for ($x = 1; $x <= $totalNoDecks; $x++){

    $decks[$x] =  $obj->$x;
}

//empty variables to hold data
$email;
$flag;
$reply;
$doesDeckExist =0;

//get the custom deck data from the database for comparison
$queryDecks = "SELECT * FROM custom_decks WHERE owning_user = '$username'";
$queryDecksResult = $myConnection->query($queryDecks);

try
{
        //does the user query have anything in it?
        if ($queryResult->num_rows > 0)
        {
            //go through each row of user query
            while($row = $queryResult->fetch_assoc())
            {
                //if the current row's username is the same as the supplied username
                if ($row["user_name"] == $username)
                {
                    //set the flag to show user has been found
                    $flag = 1;
                }
            }
        }
    else {
            //create a JSON object to hold a reply saying that the username wasn't supplied
            $user = new user();
            $user->username = "NO_USERNAME";
            echo json_encode($user);
    }


    //this section handles the case that a username has been supplied
    if ($flag==1)
    {
        //cycle through each of the supplied decknames
        foreach ($decks as $currentDeck)
        {
            //variable to record if a deck is found in the db; nb it's set to zero here to "reset" it for the next
            //iteration of the foreach statement
            $doesDeckExist=0;

            //outer if statement handles if there are rows in the db, it's else statement handles the
            //eventuality there are none
            if ($queryDecksResult->num_rows > 0)
            {
                //cycle through each row in the result set
                foreach($queryDecksResult as $thisRow)
                {
                    //if the current db row matches the current supplied deck then set the flag to positive
                    if ($thisRow["deck_name"] == $currentDeck)
                    {
                        $doesDeckExist = 1;
                    }
                }
            }
            else
            {
                $doesDeckExist = 0;
            }

            //handles when there is no match in the db, adds the supplied deck to the db
            if ($doesDeckExist == 0)
            {
                addToDB($myConnection, $username, $currentDeck);
            }
        }
    }

        //this block is simply to create a reply the android JSON request will accept, the success message is in the username
        //member of the json object
        try {
            //create json object to hold user credentials
            $user = new user();
            $user->username = "SUCCESSFUL";
            echo json_encode($user);
        }
        catch (Exception $e) {
            //
        }


}
catch (Exception $e)
{
    $reply = 'PHP ERROR: ' .$e->POSTMessage();
}

function addToDB($myConnection, $username, $deck)
{
    $queryAddDeck = "INSERT INTO custom_decks (owning_user,card_name,deck_name) VALUES ('$username','','$deck')";
    if ($myConnection->query($queryAddDeck) === TRUE){
        $reply = "<br>Inserted deck: ".$deck;
    }
    else{
        $reply = "Error: ".$queryAddDeck."<br>".$myConnection->error;
    }
}

class user {
    public $username = "";
    public $password = "";
    public $email = "";
}

?>