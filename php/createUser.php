<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 11/07/2015
 * Time: 10:17
 */
include "dbconnect.php";

$queryAllCards = "SELECT * FROM player_account";
$queryResult = $myConnection->query($queryAllCards);
$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$flag = false;

try
{
    if (isset($_POST["username"])) {
        $username = htmlspecialchars($_POST["username"]);
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
            $createUser = "INSERT INTO player_account (user_name, user_password, email_address) VALUES ('$username', '$password', '$email')";
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