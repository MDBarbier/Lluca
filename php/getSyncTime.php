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
$synctime = "";
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
                    $synctime = $row["last_sync"];
                }
            }
        }
    }
    else echo "USERNAME NOT SUPPLIED";

    if ($flag)
    {
        echo $synctime;
    }
    else {echo "NO SYNC";}

}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->getMessage();
}
?>