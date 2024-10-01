<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%! int amountInShoppingCart=0;

%>
<%!

%>
<h1><%= "welcome to our store" %></h1>
<p>the time on the server is <%=new java.util.Date() %></p>
<br/>
<!--
<%=new java.util.Date()/* denna är för ett java expression. i.e. en rad*/%>
<%/* denna ärför flera rader kod*/%>
<%! /* denna är till för declaration. avvariabler eller metoder.*/%>
-->

<a href="shoppingCart.jsp">kundvagn(<%=amountInShoppingCart%>)</a>
<ul>
    <li>äpple <button>köp</button></li>
    <li>annanas <button>köp</button></li>
    <li>appelsin<button>köp</button></li>
    <li>mango<button>köp</button></li>
</ul>
<a href="login.jsp">login</a>


</body>
</html>