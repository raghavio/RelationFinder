<%--
Created by IntelliJ IDEA.
User: RaghavFTW
Date: 17/12/15
Time: 23:24
To change this template use File | Settings | File Templates.
--%>
<%--
Can't change name to index.jsp. It doesn't run the servlet on Heroku.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <header style="padding-top: 200px;">
        <canvas id="canvas"></canvas>
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 center-block" style="float:none">
            <form action="${pageContext.request.contextPath}/find">
                <p class="text-center title-text">
                    How is my
                    <input class="title-text query_input_box" type="text" id="query" name="query"
                    <c:if test="${requestScope.query != null}">
                           value="${requestScope.query}"
                    </c:if>
                           style="border-radius: 0">
                    related to me?
                </p>
            </form>
            <c:set var="results_length" value="${fn:length(requestScope.results)}"/>
            <c:if test="${results_length != 0}">
                <p class="text-center title-text">
                    <c:choose>
                        <c:when test="${requestScope.gender == 'male'}">
                            He
                        </c:when>
                        <c:when test="${requestScope.gender == 'female'}">
                            She
                        </c:when>
                        <c:otherwise>
                            It
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${results_length == 1}">
                            is
                        </c:when>
                        <c:otherwise>
                            could be
                        </c:otherwise>
                    </c:choose>
                    your
                    <c:forEach var="result" items="${requestScope.results}" varStatus="loop">
                        <b style="text-decoration: underline;">${result.key}</b><c:set var="remaining" value="${results_length - (loop.index+1)}"/>
                        <c:if test="${remaining > 1}">, </c:if>
                        <c:if test="${remaining == 1}"> or </c:if>
                    </c:forEach>.
                </p>
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
<script src="js/jquery.autoresize.js"></script>
<script>
    $(function () {
        $("input.query_input_box").autoGrowInput({minWidth: 2, comfortZone: 25, maxWidth: 690});

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
