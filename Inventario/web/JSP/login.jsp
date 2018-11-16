<%-- 
    Document   : login
    Created on : 15/11/2018, 11:13:01 AM
    Author     : jozaf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="clase.jsp" method="post">
            <div>
                <p>Inicio de Sesion</p>
                <h5>Nombre de usuario</h5>
                <input type="text" name ="NombreUsuario" placeholder="Nombre de usuario">
                <h5>Contraseña</h5>
                <input type="password" name ="contraseña" placeholder="contraseña">
                <br>
                <input type="submit" value="Ingresar" name="ingresar">
            </div>
        </form>
    </body>
</html>
