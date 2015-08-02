<?php
$queryAll = "SELECT * FROM player_account";
$queryResult = $myConnection->query($queryAll);

if (isset($_POST["username"])) {
    $username = $_POST["username"];
}
if (isset($_POST["password"])) {
    $password = $_POST["password"];
}
if (isset($_POST["lastsync"])) {
    $lastsync = $_POST["lastsync"];
}
if (isset($_POST["email"])) {
    $email = $_POST["email"];
}

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
}
catch (Exception $e)
{
    echo 'ERROR: ' .$e->POSTMessage();
}