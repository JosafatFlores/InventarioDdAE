<%-- 
    Document   : registroProdcuto
    Created on : 15/11/2018, 11:41:22 AM
    Author     : jozaf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Producto</title>
    </head>
    <body>
         <form action="clase.jsp" method="post">
            <div>
                <p>Registro Producto</p>
                <h5>Nombre</h5>
                <input type="text" name ="nombre" placeholder="Nombre">
                <h5>Descripcion</h5>
                <input type="text" name ="descripcion" placeholder="Descripcion">
                <h5>Serie</h5>
                <input type="text" name ="serie" placeholder="Serie">
                <br>
                <br>
                <input type="submit" value="registrar" name="Registrar">
            </div>
        </form>
    </body>
</html>
