<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
            <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
            <title>Panel Administrador - PoliBurger</title>
        </head>

        <body class="neutro-bg">
            <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
                <div class="blanco-texto d-flex">
                    <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
                    <div class="d-flex-column">
                        <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                        <p class="m-t-0 m-b-0">Panel de Administrador</p>
                    </div>
                </div>
                <div class="d-flex-column">
                    <p class="blanco-texto m-0 h6">Bienvenido, ${sessionScope.nombreUsuario}</p>
                    <a href="${pageContext.request.contextPath}/LoginController?ruta=cerrarSesion"
                        class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">
                        Cerrar Sesión
                    </a>
                </div>
            </header>

            <section class="p-4">
                <div class="centrado d-flex-column">
                    <h2 class="h3 gris-texto">¿Qué deseas gestionar?</h2>

                    <div class="panel-opciones d-flex space-around m-t-3" style="gap: 2rem;">
                        <!-- Opción 1: Gestionar Cuentas del Personal -->
                        <a href="${pageContext.request.contextPath}/CuentasController?ruta=listarCuentas"
                            class="tarjeta-opcion blanco-bg borde-redondeado box-shadow p-4 centrado d-flex-column texto-none"
                            style="width: 300px;">
                            <i class="fa-solid fa-users fa-3x rojo-texto m-b-2"></i>
                            <h3 class="h4 gris-texto">Gestionar Cuentas</h3>
                            <p class="gris-texto centrado">Crear y administrar cuentas del personal</p>
                        </a>

                        <!-- Opción 2: Gestionar Platos del Menú -->
                        <a href="${pageContext.request.contextPath}/MenuController?ruta=gestionarPlatosMenu"
                            class="tarjeta-opcion blanco-bg borde-redondeado box-shadow p-4 centrado d-flex-column texto-none"
                            style="width: 300px;">
                            <i class="fa-solid fa-burger fa-3x rojo-texto m-b-2"></i>
                            <h3 class="h4 gris-texto">Gestionar Platos del Menú</h3>
                            <p class="gris-texto centrado">Crear, modificar y eliminar productos del menú</p>
                        </a>
                    </div>
                </div>
            </section>
        </body>

        </html>