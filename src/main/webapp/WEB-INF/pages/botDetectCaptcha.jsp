<%@page import="com.captcha.botdetect.web.servlet.Captcha"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="botDetect" uri="https://captcha.com/java/jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BotDetect example</title>
	
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
</head>
<body>
	<h2>BotDetect example</h2>
    <form method="post" action="">
		<!-- Adding BotDetect Captcha to the page -->
		<botDetect:captcha id="captchaData" userInputID="captchaCode" />

		<div ng-app="myApp" ng-controller="myCtrl">
			<input name="captchaCode" type="text" id="captchaCode" value="${captchaData.captchaCode}" />
<%--
			<a ng-href="#/botDetectCaptcha?type=html">HTML</a>
			<a ng-href="#/botDetectCaptcha?type=pdf">PDF</a>
			<div ng-init="goToHtml = '/captcha/botDetectCaptcha?type=html';goToPdf = '/captcha/botDetectCaptcha?type=pdf'">
				<p><a ng-href="{{goToHtml}}">Pobierz HTML</a></p>
				<p><a ng-href="{{goToPdf}}">Pobierz PDF</a></p>
			</div>
			<button ng-click="go('/botDetectCaptcha?type=pdf')">Button PDF</button>
--%>
			<button ng-click="go('/botDetectCaptcha')">Pobierz HTML</button>
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
//            $scope.go = function($path) {
//                $window.open($path);
//            };
        });
    </script>
</body>
</html>
