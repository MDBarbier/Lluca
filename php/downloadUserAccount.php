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
$flag = false;
$email;

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
                    $email = $row["email_address"];
                }
            }
        }
    }
    else echo "USERNAME NOT SUPPLIED";

    if(!$flag)
    {
        try {
            //create json object to hold user credentials
            $resultObj = new stdClass();
            $resultObj->label="USERACCOUNT";
            $resultObj->data = array(array('username',$username), array('password',$password), array('email',$email));

            //return the json object
            echo json_encode($resultObj);
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
    echo 'ERROR: ' .$e->POSTMessage();
}
?>