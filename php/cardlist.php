<!DOCTYPE html>
<html>
<head>
    <title>Project LLuca: Player Card List</title>
</head>
</head>
<body>

<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 27/03/2015
 * Time: 13:12
 */
include "dbconnect.php";



echo "Connected to the LLuca database successfully!<br><br>";
echo "Connected user: " . $username;
echo "<br>";
echo "<br>";
$queryAllCards = "SELECT * FROM player_cards";
$queryResult = $myConnection->query($queryAllCards);

if ($queryResult->num_rows > 0)
{
    //output each row of data in the dataset
    while($row = $queryResult->fetch_assoc())
    {
        echo $row["playercard_name"];
        echo "<br> <br>";

    }
}
else
{
    echo "No cards found";
}

?>
</body>
</html>
