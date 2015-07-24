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
$flag = false;

try
{
    if (isset($_POST["username"])) {
        $username = htmlspecialchars($_POST["username"]);
        if ($queryResult->num_rows > 0)
        {

            while($row = $queryResult->fetch_assoc())
            {
                if ($row["user_name"] == $username && $row["user_password"] == $password)
                {
                    $flag = true;
                }
            }
        }
    }
    else echo "USERNAME NOT SUPPLIED";

    if ($flag)
    {
        echo "ACCOUNT EXISTS";
    }
    else {echo "NO ACCOUNT EXISTS";}

}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->getMessage();
}
?>