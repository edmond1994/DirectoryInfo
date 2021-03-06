<#ftl encoding="utf-8">
<#import "/spring.ftl" as spring />
<html>
<head>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>Директории и файлы</title>
    <link href="/css/main.css" rel="stylesheet">
    <script type="text/javascript">

        function getRoot() {
            return window.location.origin?window.location.origin+'/':window.location.protocol+'/'+window.location.host+'/';
        }

        $(document).ready(function(){
            $(".get-content-button").click(function(evt) {
                evt.preventDefault();

                var thisrow = $(this).closest("tr");
                var itemtext = thisrow.find(".table-path").text()+ " " + thisrow.find(".table-addtime").text();
                var dirId = "directoryId=" + $(this).val();


                $.ajax({
                    url: getRoot() + "/showContents",
                    type: "POST",
                    data: dirId,
                    cache: false,
                    timeout : 100000,
                    success: function(result) {
                        $("#content-title").html("<h2>" + itemtext + "</h2>");
                        if (result.ajaxStatus === "Done") {
                            $('#content-list').empty();
                            if (result.data.length > 0) {
                                var contentTable = "<table class='dirtable'>";
                                contentTable += "<thead><tr><th>Файл</th><th>Размер</th></tr></thead><tbody>";
                                $.each(result.data, function (i, file) {
                                    contentTable += "<tr><td>" + file.name + "</td><td>" + file.size + "</td></tr>";
                                });
                                contentTable += "</tbody></table>";
                                $('#content-list').append(contentTable);
                            } else {
                                $('#content-list').append("Директория пуста");
                            }

                        } else {
                            $("#content-list").html("<strong>Ошибка получения файлов и директорий!</strong>");

                        }
                        //Показ модального окна
                        $(".modal").show();
                    },
                    error: function (errorXMLHttpRequest, exception) {
                        var message = '';
                        if (errorXMLHttpRequest.status === 0) {
                            message = 'Сервер не отвечает [код 0].';
                        } else if (exception === 'abort') {
                            message = 'Запрос был отменен.';
                        } else if (exception === 'timeout') {
                            message = 'Произошел тайм-аут запроса.';
                        }  else {
                            message = 'Неизвестная ошибка: \n' + errorXMLHttpRequest.responseText;
                        }
                        $("#content-title").html("<h2>" + "Ошибка" + "</h2>");
                        $('#content-list').empty();
                        $('#content-list').append(message);
                        //Показ модального окна
                        $(".modal").show();
                    }
                });
            });


            $(".modal-content-close").click(function() {
                $(".modal").hide();
            });

            $(".modal-content-close-button").click(function() {
                $(".modal").hide();
            });

            //Клик вне модального окна закрывает его
            $(window).click(function(evt) {
                if(evt.target.id === "modal-background") {
                    $(".modal").hide();
                }
            });

        })
    </script>
</head>
<body>
<div id="header">
    <h1>Директории и файлы</h1>
</div>

<div id="main">
    <br/>
    <!-- Форма добавления новой директории -->
    <div id="add-form">
        <@spring.bind "newDir"/>
        <form action="/addDirectory" method="post">
            <div id="add-form-input">
            Новая директория: <@spring.formInput "newDir.path"/>
                <input type="submit" value="Добавить в список"><br/>
            </div>
            <#if errorMessage??>
                <div class ="directory-error">${errorMessage}</div>
            <#else>
                <br/>
            </#if>
        </form>
    </div>


     <br/>

<#if directories?? && directories?size != 0>
    <span class = "list-title">Список директорий и файлов</span>
    <br/><br/>
    <table class="dirtable">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Базовая директория</th>
            <th>Директорий</th>
            <th>Файлов</th>
            <th>Суммарный размер файлов</th>
            <th>  </th>
        </tr>
        </thead>
        <tbody>
            <#list directories as directory>
            <tr>
                <td class="table-addtime">${directory.addTime?string["dd.MM.yyyy HH:mm"]}</td>
                <td class="table-path">${directory.path}</td>
                <td class="table-dircount">${directory.dirCount}</td>
                <td class="table-filecount">${directory.fileCount}</td>
                <td class="table-size">${directory.totalSize}</td>
                <td class="td-button"><button value=${directory.id} class="get-content-button">Файлы</button></td>
            </tr>
            </#list>
        </tbody>
     </table>
<#else>
      <span class = "list-title"><i>Список директорий пуст</i></span>
</#if>


</div>


<!-- Фон модального окна -->
<div id="modal-background" class="modal">

    <!-- Модальное окно -->
    <div class="modal-content">
        <div id="content-title"></div> <span class="modal-content-close">&times;</span><br/><br/>
        <div id="content-list">
        </div>
        <br/>
        <button class="modal-content-close-button">Закрыть</button>
    </div>

</div>


</body>
</html