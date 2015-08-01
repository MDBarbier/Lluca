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
$lastsync = $_POST["localsynctime"];

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

    if($flag)
    {
        try {
            $updateUser = "UPDATE player_account SET last_sync = '$lastsync' WHERE user_name = '$username'";
            $myConnection->query($updateUser);
            echo "ACCOUNT UPDATED";
        }
        catch (Exception $e)
        {
            echo "ERROR UPDATING";
        }
    }
    else
    {
        echo "USERNAME NOT FOUND";
    }


}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->POSTMessage();
}
?>