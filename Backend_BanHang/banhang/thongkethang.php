<?php
include "connect.php";

$query = "SELECT *,SUM(tongtien) as tongtienthang, MONTH(`ngaydat`) as thang FROM `donhang` GROUP BY YEAR(`ngaydat`), MONTH(`ngaydat`)";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
if (!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result
	];
}
print_r(json_encode($arr));

?>