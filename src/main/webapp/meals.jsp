<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>

    <table border="2">

        <tr>
            <th width="130">Date/Time</th>
            <th width="400">Description</th>
            <th width="80">Calories</th>
        </tr>

        <c:forEach var="mealTo" items="${requestScope.meals}">
            <tr style="color: ${mealTo.excess ? "#ff0000" : "#008000"}">
                <td><c:out value="${mealTo.dateTimeInString()}"/></td>
                <td><c:out value="${mealTo.description}"/></td>
                <td><c:out value="${mealTo.calories}"/></td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>
