<%-- 
    Document   : logout
    Created on : 3 nov. 2025, 13:17:39
    Author     : Kewo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Invalidar la sesión
    session.invalidate();
    // Redirigir al login
    response.sendRedirect("login.jsp");
%>