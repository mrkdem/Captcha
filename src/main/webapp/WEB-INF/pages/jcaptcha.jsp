<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JCaptcha example</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
</head>
<body>
	<h2>JCaptcha example</h2>
	<form method="post" action="">
		<div ng-app="myApp" ng-controller="myCtrl">
			<img src="/captcha/captcha.html"/>
			<input type="text" name="captchaCode" value="${captchaData.captchaCode}" />
			<button ng-click="go('/jcaptcha')">Pobierz PDF</button>
			<span class="correct">${captchaData.captchaCorrect}</span>
			<span class="incorrect">${captchaData.captchaIncorrect}</span>
		</div>
	</form>

    <script>
        var app = angular.module('myApp', []);

        app.controller('myCtrl', function($scope, $window) {
            $scope.go = function ( path ) {
                $location.url( path );
            };
        });
    </script>
</body>
</html>