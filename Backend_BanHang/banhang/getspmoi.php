<?php
include "connect.php";
$query = "SELECT * FROM `sanphammoi` ORDER BY id DESC;";
$data = mysqli_query($conn, $query) or die( mysqli_error($conn));
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
$arr = [
'success' => true,
'message' => "",
'result' => $result 
];
print_r(json_encode($arr));

?>