<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 11/07/2015
 * Time: 10:17
 */
include "dbconnect.php";
include "finduserindbstring.php";

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
?>