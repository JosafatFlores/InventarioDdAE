<%-- 
    Document   : catalogoUsuario
    Created on : 16-nov-2018, 22:59:20
    Author     : HP PAVILION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogo de Usuarios</title>
    </head>
    <body>
         <form action="clase.jsp" method="post">
            <div>
                <h4>Tabla de usuarios registrados</h4>
                <table border="1">
                    <tr>
                        <th>Nombre de usuario</th>
                        <th>Contraseña</th>
                        <th>Nombre</th>
                        <th>Apellido Paterno</th>
                        <th>Apellido Materno</th>
                        <th>Tipo de usuario</th>
                    </tr>
                </table>
                <br>
                <br>
                <input type="submit" value="Regresar al Menu" name="regresarM">
                <br>
                <br>
                <input type="submit" value="Consultar usuarios" name="buscar">
            </div>
        </form>
    </body>
</html>
