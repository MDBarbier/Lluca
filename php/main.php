<!DOCTYPE html>
<html>
<head>
    <title>Project LLuca</title>
    </head>
</head>
<body>
<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 21/03/2015
 * Time: 09:52
 */
echo "<h1>Welcome to Project LLuca</h1><br>";

echo "This site is a portal to the database for the Lord of the Rings LCG unofficial companion app (LLuca).<br>";

echo "Currently the main functionality of Project LLuca is being developed for Android, this site is just for testing.<br><br>";

include "dbconnect.php";

echo "Connected to the LLuca database successfully!<br><br>";
echo "Connected user: " . $username;

$queryDatabaseVersionNo = "SELECT version FROM control_data";
$queryResult = $myConnection->query($queryDatabaseVersionNo);

if ($queryResult->num_rows > 0)
  {
    //output each row of data in the dataset
    while($row = $queryResult->fetch_assoc())
    {
       echo "Database version (taken from the control_data table in the live database): " . $row["version"];
    }
  }


echo "<h3><a href='cardlist.php'>Click here to view player card list</h3>"
?>
</body>
</html>