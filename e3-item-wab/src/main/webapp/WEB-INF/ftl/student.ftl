<html>
<head>
	<title>student</title>
</head>
<body>
<h2> student message !!</h2>
	<table>
		<tr><th>NAME</th><th>NO</th><th>AGE</th></tr>
		<#list StudentList as student>
		<tr>
		<td>${student.name}</td>
		<td>${student.id}</td>
		<td>${student.age}</td>
		</tr>
		</#list>
	</table>
</body>
</html>