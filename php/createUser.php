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

$username = $_POST["username"];
$password = $_POST["password"];
$lastsync = $_POST["lastsync"];
$email = $_POST["email"];

$flag = false;

try
{
    if (isset($_POST["username"])) {

        if ($queryResult->num_rows > 0)
        {

            while($row = $queryResult->fetch_assoc())
            {
                if ($row["user_name"] == $username)
                {
                    $flag = true;
                }
            }
        }
    }
    else echo "USERNAME NOT SUPPLIED";

    if(!$flag)
    {
        try {
            $createUser = "INSERT INTO player_account (user_name, user_password, email_address, last_sync) VALUES ('$username', '$password', '$email', '$lastsync')";
            $myConnection->query($createUser);
            echo "ACCOUNT CREATED";
        }
        catch (Exception $e)
        {
            echo "ERROR INSERTING";
        }
    }
    else
    {
        echo "USERNAME TAKEN";
    }


}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->POSTMessage();
}
?>