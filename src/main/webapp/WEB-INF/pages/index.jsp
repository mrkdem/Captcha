<!DOCTYPE HTML>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<body ng-app="">

<div ng-init="goToHtml = '/captcha/botDetectCaptcha';goToPdf = '/captcha/jcaptcha'">
    <p><a ng-href="{{goToHtml}}">BotDetect (HTML)</a></p>
    <p><a ng-href="{{goToPdf}}">JCaptcha (PDF)</a></p>
</div>

</body>
</html>