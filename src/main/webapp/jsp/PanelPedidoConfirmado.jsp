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
                <title>Pedido Confirmado</title>
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
                    <a href="${pageContext.request.contextPath}/LoginController?ruta=mostrarFormulario"
                        class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Acceso Personal</a>
                </header>

                <section>
                    <div>
                        <a class="p-2 rojo-texto texto-none texto-hover"
                            href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarMenu">
                            <i class="fa-solid fa-arrow-left"></i> Volver
                        </a>
                    </div>

                    <div class="contenedor-principal">
                        <div class="tarjeta-pedido">
                            <!-- Header de confirmación -->
                            <div class="header-tarjeta-pedido texto-center">
                                <p class="verde-texto texto-negrita h6">
                                    <i class="fas fa-check-circle"></i> ¡Pedido Confirmado!
                                </p>
                                <h3 class="gris-texto">ID del Pedido</h3>
                                <h1 class="rojo-texto texto-negrita h2 m-t-0 m-b-1">${pedido.nroPedido}</h1>
                                <p class="gris-texto m-t-0 m-b-2 h7">
                                    <fmt:formatDate value="${pedido.fechaCreacion}" pattern="dd MMM yyyy, HH:mm" />
                                </p>
                            </div>

                            <!-- Línea de tiempo -->
                            <div class="linea-tiempo d-flex space-around">
                                <div class="paso">
                                    <div
                                        class="circulo ${pedido.estadoPedido == 'PENDIENTE' ? 'rojo-bg' : 'neutro-bg gris-borde'}">
                                        <i
                                            class="fas fa-clock ${pedido.estadoPedido == 'PENDIENTE' ? 'blanco-texto' : 'gris-texto'}"></i>
                                    </div>
                                    <p
                                        class="${pedido.estadoPedido == 'PENDIENTE' ? 'rojo-texto texto-negrita' : 'gris-texto'} h7 m-t-1 m-b-0">
                                        Pendiente</p>
                                </div>
                                <div class="paso">
                                    <div
                                        class="circulo ${pedido.estadoPedido == 'EN_PREPARACION' ? 'rojo-bg' : 'neutro-bg gris-borde'}">
                                        <i
                                            class="fas fa-utensils ${pedido.estadoPedido == 'EN_PREPARACION' ? 'blanco-texto' : 'gris-texto'}"></i>
                                    </div>
                                    <p
                                        class="${pedido.estadoPedido == 'EN_PREPARACION' ? 'rojo-texto texto-negrita' : 'gris-texto'} h7 m-t-1 m-b-0">
                                        En Preparación</p>
                                </div>
                                <div class="paso">
                                    <div
                                        class="circulo ${pedido.estadoPedido == 'LISTO' ? 'verde-bg' : 'neutro-bg gris-borde'}">
                                        <i
                                            class="fas fa-box ${pedido.estadoPedido == 'LISTO' ? 'blanco-texto' : 'gris-texto'}"></i>
                                    </div>
                                    <p
                                        class="${pedido.estadoPedido == 'LISTO' ? 'verde-texto texto-negrita' : 'gris-texto'} h7 m-t-1 m-b-0">
                                        Listo</p>
                                </div>
                            </div>

                            <hr class="divisor">

                            <!-- Estado actual -->
                            <div class="estado-actual amarillo-bg">
                                <p class="h6"><i class="fas fa-clock texto-negrita"></i> Estado Actual</p>
                                <h4 class="texto-negrita m-t-0 m-b-1">${pedido.estadoPedido}</h4>
                                <p class="h7 m-t-0 m-b-0 gris-texto">Tu pedido ha sido recibido y está en cola para
                                    preparación</p>
                            </div>

                            <hr class="divisor">

                            <!-- Detalle del pedido -->
                            <h3 class="gris-texto">Detalle del Pedido</h3>
                            <c:forEach var="detalle" items="${pedido.detalles}">
                                <div class="detalle-linea d-flex space-between">
                                    <p class="gris-texto h6 m-t-0 m-b-1">${detalle.cantidad}x
                                        ${detalle.platoMenu.nombre}</p>
                                    <p class="gris-texto h6 m-t-0 m-b-1 texto-negrita">$
                                        <fmt:formatNumber value="${detalle.subtotal}" pattern="#,##0.00" />
                                    </p>
                                </div>
                            </c:forEach>

                            <hr class="divisor">

                            <!-- Total -->
                            <div class="total-linea d-flex space-between">
                                <h3 class="gris-texto m-t-0 m-b-0">Total</h3>
                                <h3 class="rojo-texto texto-negrita m-t-0 m-b-0">$
                                    <fmt:formatNumber value="${pedido.totalPedido}" pattern="#,##0.00" />
                                </h3>
                            </div>
                        </div>
                    </div>
                </section>
            </body>

            </html>