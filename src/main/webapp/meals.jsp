<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<jsp:useBean id="meals" scope="request" type="java.util.List"/>--%>


<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>



<table>
    <tbody>
    <tr>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
<%--        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.to.MealTo"/>--%>
        <tr>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>