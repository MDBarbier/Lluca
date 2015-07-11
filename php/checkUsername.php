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
                    echo "USERNAME TAKEN";
                }
                else
                {
                    echo "USERNAME FREE";
                }
            }
        }
        else
        {
            echo "USER TABLE EMPTY";
        }
    }
    else echo "USERNAME NOT SUPPLIED";


}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->getMessage();
}
?>