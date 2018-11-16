<%-- 
    Document   : registroEmpleado
    Created on : 15/11/2018, 11:23:27 AM
    Author     : jozaf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Regsitro Administrador</title>
    </head>
    <body>
        <form action="clase.jsp" method="post">
            <div>
                <p>Registro Prestamo</p>
                <h5>Nombre de usuario</h5>
                <input type="text" name ="nombreUsuario" placeholder="Nombre de Usuario">
                <h5>Contraseña</h5>
                <input type="password" name ="contraseña" placeholder="contraseña">
                <h5>Nombre</h5>
                <input type="text" name ="nombre" placeholder="Nombre">
                <h5>Apellido paterno</h5>
                <input type="text" name ="ApellidoPaterno" placeholder="Apellido Paterno">
                <h5>Apellido materno</h5>
                <input type="text" name ="ApellidoMaterno" placeholder="Apellido materno">
                <h5>Tipo de usuario</h5>
                <select name="tipo">
                    <option value="empleado">Empleado</option>
                    <option value="cliente">Cliente</option>
                </select>
                <br>
                <br>
                <input type="submit" value="registrar" name="Registrar">
            </div>
        </form>
    </body>
</html>
