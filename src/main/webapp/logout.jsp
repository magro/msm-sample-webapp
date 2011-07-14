<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<html>
<head>
<title>Logout</title>
</head>

<body>
   <h1>Logout</h1>
  <% 
    HttpSession s = request.getSession(false);
    if(s != null) {
        s.invalidate();
        %>
        <p>You are now logged out.</p> 
        <%
    }
    else {
        %>
        <p>You're not logged in.</p> 
        <%
    }
   %>
   <a href="protected/list">Login</a>
</body>

</html>
