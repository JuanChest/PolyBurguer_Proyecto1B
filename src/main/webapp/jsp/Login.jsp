<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
            <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
            <title>Acceso Personal - PoliBurger</title>
        </head>

        <body class="neutro-bg">
            <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
                <div class="blanco-texto d-flex">
                    <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
                    <div class="d-flex-column">
                        <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                        <p class="m-t-0 m-b-0">Sistema de Pedidos</p>
                    </div>
                </div>
            </header>

            <section class="p-2">
                <div>
                    <a class="rojo-texto texto-none texto-hover"
                        href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarMenu">
                        <i class="fa-solid fa-arrow-left"></i> Volver
                    </a>
                </div>

                <!-- Mensajes de retroalimentación -->
                <c:if test="${not empty sessionScope.mensaje}">
                    <div class="m-t-2">
                        <div
                            class="${sessionScope.tipoMensaje == 'error' ? 'rojo-bg' : 'verde-bg'} blanco-texto p-2 borde-redondeado centrado">
                            <p class="m-0">${sessionScope.mensaje}</p>
                        </div>
                    </div>
                    <c:remove var="mensaje" scope="session" />
                    <c:remove var="tipoMensaje" scope="session" />
                </c:if>

                <div
                    class="width-2 borde-redondeado centrado d-flex-column blanco-bg p-t-3 p-b-3 p-i-1 p-d-1 box-shadow m-t-3">
                    <i class="fa-regular fa-id-badge fa-2xl rojo-texto p-2"></i>
                    <h1 class="texto-normal p-b-0 m-b-0 h3">Acceso Personal</h1>
                    <h2 class="texto-normal p-t-0 m-t-0 h6">Ingresa tus credenciales</h2>

                    <form action="${pageContext.request.contextPath}/LoginController" method="POST">
                        <input type="hidden" name="ruta" value="autenticar">

                        <div>
                            <label for="cedula">Cédula</label>
                            <div class="input-con-icono">
                                <i class="fa-regular fa-id-card"></i>
                                <input type="text" id="cedula" name="cedula" placeholder="Ingresa tu cédula" required>
                            </div>
                        </div>

                        <div>
                            <label for="contrasena">Contraseña</label>
                            <div class="input-con-icono">
                                <i class="fa-solid fa-lock"></i>
                                <input type="password" id="contrasena" name="contrasenia"
                                    placeholder="Ingresa tu contraseña" required>
                            </div>
                        </div>

                        <button type="submit"
                            class="boton boton-s ancho-total rojo-bg blanco-texto borde-none texto-none centrado">
                            Iniciar Sesión
                        </button>
                    </form>
                </div>
            </section>
        </body>

        </html>