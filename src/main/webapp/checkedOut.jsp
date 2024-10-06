<% String checkoutStatus = (String) request.getAttribute("checkoutStatus");; %><%--
  Created by IntelliJ IDEA.
  User: calle
  Date: 2024-10-04
  Time: 08:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <h1><%=checkoutStatus%></h1>
    <%!
    %>
    <%

        checkoutStatus = (String) request.getAttribute("checkoutStatus");


    %>
    <a href="index.jsp">home page</a>

</head>
<body>


</body>
</html>
