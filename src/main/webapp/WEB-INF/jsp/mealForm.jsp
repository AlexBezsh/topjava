<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title><spring:message code="mealForm.title"/></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section>
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <hr>
    <h2><spring:message code="${param.action == 'create' ? 'mealForm.createMeal' : 'mealForm.editMeal'}"/></h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="mealForm.dateTime"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="mealForm.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="mealForm.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
    <jsp:include page="fragments/footer.jsp"/>
</section>
</body>
</html>
