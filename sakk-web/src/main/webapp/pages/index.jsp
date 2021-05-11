<%@ page import="model.GameInfo" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <jsp:include page="../WEB-INF/common/common-header.jsp"/>
    <title>Jatekok</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/" method="get">
    <div class="container">
        <label for="white">Fehér játékos:</label>
        <input id="white" type="text" name="whiteP">
    </div>
    <div class="container">
        <label for="black">Fekete játékos:</label>
        <input id="black" type="text" name="blackP">
    </div>
    <div class="container">
        <label for="winner">Kimenetel:</label>
        <input id="winner" type="text" name="winner">
    </div>
    <div class="container">
        <label for="date">dátum:</label>
        <input id="date" type="date" name="date"></div>
    <div class="container"><input id="button" type="submit" value="go"><br/></div>
</form>
<table class="table">
    <tr>
        <th>Fekete játékos</th>
        <th>Fehér játékos</th>
        <th>Kimenetel:</th>
        <th>Dátum:</th>
    </tr>
    <%ArrayList<GameInfo> list = (ArrayList<GameInfo>) request.getAttribute("gameList");
        if (list != null) {
            for (GameInfo gameInfo : list) {
                out.print("<tr>");
                out.print("<td> " + gameInfo.getBlackPlayerName() + "</td>");
                out.print("<td> " + gameInfo.getWhitePlayerName() + "</td>");
                out.print("<td> " + gameInfo.getWinner() + "</td>");
                out.print("<td> " + gameInfo.getDate() + "</td>");
                out.print("</tr>");
            }
        }
// print the information about every category of the list
    %>
</table>


</body>
</html>
