<?php 
$rong= $_GET['rong'];
$dai= $_GET['dai'];
$chuvi= ($dai + $rong) * 2;
$dientich= $dai * $rong;
echo "Chu vi la C = " .$chuvi. "; Dien tich = " .$dientich;
?>