<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 11/07/2015
 * Time: 10:17
 */
include "dbconnect.php";

$queryAll = "SELECT * FROM player_account";
$queryResult = $myConnection->query($queryAll);


$json = file_get_contents('php://input');
$obj = json_decode($json);
//var_dump($obj);

$username = $obj->username;

//echo $username;
//echo $password;

$flag = false;
$email;
$lastsync;


try
{

        if ($queryResult->num_rows > 0)
        {

            while($row = $queryResult->fetch_assoc())
            {
                if ($row["user_name"] == $username)
                {
                    $flag = true;
                    $email = $row["email_address"];
                    $lastsync = $row["last_sync"];
                }
            }
        }
    else echo "USERNAME NOT SUPPLIED";

    if($flag)
    {
        try {
            //create json object to hold user credentials
            $user = new user();
            $user->username = $username;
            $user->email = $email;
            $user->lastsync = $lastsync;
            echo json_encode($user);
        }
        catch (Exception $e)
        {
            echo "ERROR";
        }
    }
    else
    {
        echo "USERNAME NOT FOUND";
    }


}
catch (Exception $e)
{
    echo 'PHP ERROR: ' .$e->POSTMessage();
}

class user {
    public $username = "";
    public $password = "";
    public $email = "";
    public $lastsync = "";
}

?>