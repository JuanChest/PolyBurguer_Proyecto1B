<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
                <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
                <title>PoliBurger - Gestión de Usuarios</title>
            </head>

            <body>
                <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
                    <div class="blanco-texto d-flex">
                        <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
                        <div class="d-flex-column">
                            <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                            <p class="m-t-0 m-b-0">Sistema de Pedidos</p>
                        </div>
                    </div>
                    <div class="d-flex-column">
                        <p class="blanco-texto m-0 h6">Bienvenido, ${sessionScope.nombreUsuario}</p>
                        <a href="${pageContext.request.contextPath}/LoginController?ruta=cerrarSesion"
                            class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Cerrar Sesión</a>
                    </div>
                </header>

                <section class="p-4">
                    <div class="p-2">
                        <a class="rojo-texto texto-none texto-hover"
                            href="${pageContext.request.contextPath}/jsp/PanelAdministrador.jsp">
                            <i class="fa-solid fa-arrow-left"></i> Volver
                        </a>
                    </div>

                    <div class="izquierda p-i-4 m-b-4">
                        <i class="fa-solid fa-users-gear fa-2xl rojo-texto p-2"></i>
                        <div>
                            <h1 class="m-0 p-0 texto-normal h2">Gestión de Personal</h1>
                            <h1 class="m-0 p-0 texto-normal h6 gris-texto">Administración de cuentas de Cocineros</h1>
                        </div>
                    </div>

                    <div>
                        <div class="p-i-2 p-d-2 d-flex space-between align-center">
                            <h1 class="h4 texto-normal gris-texto"><i class="fa-solid fa-user-pen"></i> Listado de
                                Cuentas</h1>
                            <a href="${pageContext.request.contextPath}/CuentasController?ruta=mostrarFormulario"
                                class="boton boton-l p-i-2 p-d-2 rojo-bg blanco-texto borde-none texto-none centrado">+
                                Nueva Cuenta</a>
                        </div>
                        <hr>

                        <div class="p-4 m-2 blanco-bg borde-redondeado box-shadow">
                            <table class="tabla-usuarios m-b-4 ancho-total">
                                <thead>
                                    <tr>
                                        <th class="h6 gris-texto texto-negrita p-2 texto-izquierda" style="width: 45%;">
                                            NOMBRE COMPLETO</th>
                                        <th class="h6 gris-texto texto-negrita p-2 texto-centrado" style="width: 30%;">
                                            CÉDULA (USUARIO)</th>
                                        <th class="h6 gris-texto texto-negrita p-2 texto-centrado" style="width: 25%;">
                                            ESTADO</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty cuentas}">
                                            <tr>
                                                <td colspan="3" style="text-align: center; padding: 40px;">
                                                    <p class="h4 gris-texto">No hay cuentas de cocineros registradas</p>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="c" items="${cuentas}">
                                                <tr class="borde-b-gris">
                                                    <td class="h6 p-3">
                                                        <span class="texto-negrita">${c.nombre} ${c.apellido}</span><br>
                                                        <small class="gris-texto">Cocinero</small>
                                                    </td>
                                                    <td class="h6 p-3 gris-texto texto-centrado">${c.cedula}</td>
                                                    <td class="p-3">
                                                        <div class="d-flex centrado-contenido">
                                                            <label class="switch">
                                                                <input type="checkbox" ${c.estado=='ACTIVO' ? 'checked'
                                                                    : '' }
                                                                    onchange="cambiarEstadoUsuario(${c.id}, this.checked)">
                                                                <span class="slider round"></span>
                                                            </label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

                <script>
                    function cambiarEstadoUsuario(id, estado) {
                        // Usamos fetch para llamar al controlador sin recargar la página (Paso 3.1 del diagrama)
                        const url = '${pageContext.request.contextPath}/CuentasController?ruta=desactivarCuenta&id=' + id + '&estado=' + estado;

                        fetch(url, { method: 'GET' })
                            .then(response => {
                                if (!response.ok) {
                                    alert("Error al actualizar el estado del usuario.");
                                    // Opcional: revertir el switch si falla
                                }
                            })
                            .catch(error => console.error('Error:', error));
                    }
                </script>
            </body>

            </html>