<%--
  Created by IntelliJ IDEA.
  User: samao
  Date: 1/4/16
  Time: 2:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List Products</title>
</head>

<body>
<table border="1">
    <g:each in="${allProducts}" var="thisProduct">
        <tr>
            <td>${thisProduct.name}</td>
            <td>${thisProduct.sku}</td>
            <td>${thisProduct.price}</td>
        </tr>
    </g:each>
</table>
</body>
</html>