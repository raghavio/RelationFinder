<%--
Created by IntelliJ IDEA.
User: RaghavFTW
Date: 17/12/15
Time: 23:24
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="css/custom.css">
</head>
<body>
<div class="container">
    <header style="padding-top: 100px;">
        <canvas id="canvas"></canvas>
        <div class="col-xs-12 col-sm-10 col-md-8 col-lg-6 center-block" style="float:none">
            <h1 class="text-center" style="color: white; margin-bottom: 30px">Find Relation</h1>
            <form action="${pageContext.request.contextPath}/find">
                <div class="input-group">
                    <input class="form-control" type="text" id="query" name="query"
                           placeholder="father's mother's daughter"
                    <c:if test="${requestScope.query != null}">
                           value="${requestScope.query}"
                    </c:if>
                           style="border-radius: 0">
                    <div class="input-group-btn btn-default">
                        <button class="btn btn-primary" style="border-radius: 0" type="submit">Submit</button>
                    </div>
                </div>
            </form>
            <c:if test="${requestScope.results != null}">
                <ol>
                    <c:forEach var="result" items="${requestScope.results}">
                        <h3>
                            <li>
                                <c:forEach var="relation" items="${result}">
                                    ${relation}
                                </c:forEach>
                            </li>
                        </h3>
                    </c:forEach>
                </ol>
            </c:if>
        </div>
    </header>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.2/TweenMax.min.js"></script>
<script src="js/canvas.js"></script>
<script>
    $(function () {
        var availableTags = [
            <c:forEach var="relation" items="${applicationScope.all_relations}">
            "${relation}",
            </c:forEach>
        ];

        function split(val) {
            return val.split(/'s\s*/);
        }

        function extractLast(term) {
            return split(term).pop();
        }

        $("#query")
        // don't navigate away from the field on tab when selecting an item
                .bind("keydown", function (event) {
                    if (event.keyCode === $.ui.keyCode.TAB &&
                            $(this).autocomplete("instance").menu.active) {
                        event.preventDefault();
                    }
                })
                .autocomplete({
                    minLength: 0,
                    source: function (request, response) {
                        // delegate back to autocomplete, but extract the last term
                        response($.ui.autocomplete.filter(
                                availableTags, extractLast(request.term)));
                    },
                    focus: function () {
                        // prevent value inserted on focus
                        return false;
                    },
                    select: function (event, ui) {
                        var terms = split(this.value);
                        // remove the current input
                        terms.pop();
                        // add the selected item
                        terms.push(ui.item.value);
                        // add placeholder to get the comma-and-space at the end
                        terms.push("");
                        this.value = terms.join("'s ");
                        return false;
                    }
                });
    });
</script>
</body>
</html>
