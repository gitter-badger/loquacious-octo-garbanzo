<#macro dashboardTemplate title="defaultTitle">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="../../favicon.ico">

	<title>Theme Template for Bootstrap</title>

	<!-- Bootstrap core CSS -->
	<link href="/webjars/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

	<!-- Bootstrap theme -->
	<link href="/webjars/bootstrap/3.3.5/css/bootstrap-theme.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="/assets/css/theme.css" rel="stylesheet">

	<link href="//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Droid+Sans+Mono" rel="stylesheet"
		  type="text/css">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body role="document">

	<#nested>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/webjars/bootstrap/3.3.5/js/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/webjars/bootstrap/3.3.5/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
</#macro>