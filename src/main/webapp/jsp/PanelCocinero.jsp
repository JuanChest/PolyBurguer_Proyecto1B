<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
            <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
            <title>Panel Cocinero - PoliBurger</title>
        </head>

        <body class="neutro-bg">
            <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
                <div class="blanco-texto d-flex">
                    <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
                    <div class="d-flex-column">
                        <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                        <p class="m-t-0 m-b-0">Panel de Cocinero</p>
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
                    <h2 class="h3 gris-texto">¿Qué deseas hacer?</h2>

                    <div class="panel-opciones d-flex space-around m-t-3" style="gap: 2rem;">
                        <!-- Opción 1: Bandeja de Pedidos -->
                        <a href="${pageContext.request.contextPath}/PedidosController?ruta=listar"
                            class="tarjeta-opcion blanco-bg borde-redondeado box-shadow p-4 centrado d-flex-column texto-none"
                            style="width: 300px;">
                            <i class="fa-solid fa-clipboard-list fa-3x rojo-texto m-b-2"></i>
                            <h3 class="h4 gris-texto">Bandeja de Pedidos</h3>
                            <p class="gris-texto centrado">Ver y gestionar pedidos activos</p>
                        </a>

                        <!-- Opción 2: Gestión de Disponibilidad (Por implementar) -->
                        <div class="tarjeta-opcion blanco-bg borde-redondeado box-shadow p-4 centrado d-flex-column"
                            style="width: 300px; opacity: 0.5; cursor: not-allowed;">
                            <i class="fa-solid fa-utensils fa-3x gris-texto m-b-2"></i>
                            <h3 class="h4 gris-texto">Gestión de Disponibilidad</h3>
                            <p class="gris-texto centrado">Próximamente</p>
                        </div>
                    </div>
                </div>
            </section>
        </body>

        </html>