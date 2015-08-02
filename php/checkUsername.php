<?php
/**
 * Created by PhpStorm.
 * User: Admin
 * Date: 11/07/2015
 * Time: 10:17
 */
include "dbconnect.php";
include "finduserindbstring.php";

    if ($flag)
    {
        echo "USERNAME TAKEN";
    }
    else {echo "USERNAME FREE";}

?>