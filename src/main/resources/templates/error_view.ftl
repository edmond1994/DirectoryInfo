<#ftl encoding="utf-8">
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Ошибка</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<div id="header">
    <h1>${errorMessage}</h1>
</div>

<div id="main">
    <br/>
    <div class="exception-message">
        ${exceptionMessage}
    </div>
</div>
</body>
</html>