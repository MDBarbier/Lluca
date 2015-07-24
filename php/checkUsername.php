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

    if ($flag)
    {
        echo "USERNAME TAKEN";
    }
    else {echo "USERNAME FREE";}

}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->getMessage();
}
?>