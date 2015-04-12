<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 27/03/2015
 * Time: 13:15
 */

$servername = "localhost";
$username = "lluca_bot";
$password = "botsloveoil";
$database = "lluca_master";

//Create the connection to the database
$myConnection = new mysqli($servername, $username, $password, $database);

//Check the connection
if ($myConnection->connect_error)
{
    die("The connection to the database failed with error: " . $myConnection->connect_error);
}