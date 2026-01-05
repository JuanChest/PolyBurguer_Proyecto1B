<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
    <title>Pedido</title>
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
        <a href="${pageContext.request.contextPath}/PedidosController?ruta=listar" class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Acceso Personal</a>
    </header>

    <section class="p-2">
        <div>
            <a class="rojo-texto texto-none texto-hover" href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarMenu">
                <i class="fa-solid fa-arrow-left"></i> Volver
            </a>
        </div>
        
        <div class="carrito-contenedor">
            <div class="carrito-titulo">
                <h2><i class="fa-solid fa-cart-shopping"></i> Tu Carrito</h2>
            </div>
            <hr>
            
            <c:choose>
                <c:when test="${empty carrito}">
                    <div style="text-align: center; padding: 40px;">
                        <p class="h4 gris-texto">Tu carrito está vacío</p>
                        <a href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarMenu" 
                           class="boton rojo-bg blanco-texto borde-none p-1 texto-none">
                            Ver Menú
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <c:forEach var="detalle" items="${carrito}">
                            <div class="producto-carrito">
                                <img src="${pageContext.request.contextPath}/img/platos/${detalle.platoMenu.imagen}" 
                                     alt="${detalle.platoMenu.nombre}"
                                     onerror="this.src='https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop'">
                                <div class="d-flex-column">
                                    <h3 class="h5 m-b-0">${detalle.platoMenu.nombre}</h3>
                                    <p class="h6 m-t-0 m-b-1">${detalle.platoMenu.descripcion}</p>
                                    <div class="controles-cantidad">
                                        <form action="${pageContext.request.contextPath}/PedidosController" method="POST" style="display:inline;">
                                            <input type="hidden" name="ruta" value="decrementarCantidad">
                                            <input type="hidden" name="idPlato" value="${detalle.platoMenu.idPlatoMenu}">
                                            <button type="submit">-</button>
                                        </form>
                                        <span>${detalle.cantidad}</span>
                                        <form action="${pageContext.request.contextPath}/PedidosController" method="POST" style="display:inline;">
                                            <input type="hidden" name="ruta" value="incrementarCantidad">
                                            <input type="hidden" name="idPlato" value="${detalle.platoMenu.idPlatoMenu}">
                                            <button type="submit">+</button>
                                        </form>
                                    </div>
                                </div>
                                <div class="info-precio">
                                    <p class="rojo-texto">$<fmt:formatNumber value="${detalle.subtotal}" pattern="#,##0.00"/></p>
                                    <form action="${pageContext.request.contextPath}/PedidosController" method="POST" style="display:inline;">
                                        <input type="hidden" name="ruta" value="eliminarDelCarrito">
                                        <input type="hidden" name="idPlato" value="${detalle.platoMenu.idPlatoMenu}">
                                        <button type="submit" class="borde-none blanco-bg rojo-texto h4">
                                            <i class="fa-solid fa-trash-can"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <hr>
                    
                    <div class="resumen-orden">
                        <div class="p-t-0 p-b-0 m-t-0 m-b-0 gris-texto">
                            <p class="p-t-0 p-b-0 m-t-1 m-b-0">Subtotal</p>
                            <p class="p-t-0 p-b-0 m-t-1 m-b-0">$<fmt:formatNumber value="${subtotal}" pattern="#,##0.00"/></p>
                        </div>
                        <div class="p-t-0 p-b-0 m-t-0 m-b-0 gris-texto">
                            <p class="p-t-0 p-b-0 m-t-1 m-b-1">Impuesto (15%)</p>
                            <p class="p-t-0 p-b-0 m-t-1 m-b-1">$<fmt:formatNumber value="${impuesto}" pattern="#,##0.00"/></p>
                        </div>
                        <hr>
                        <div class="p-t-1">
                            <h3 class="m-0">Total</h3>
                            <h3 class="m-0 rojo-texto">$<fmt:formatNumber value="${total}" pattern="#,##0.00"/></h3>
                        </div>
                    </div>
                    
                    <div class="p-t-2">
                        <form action="${pageContext.request.contextPath}/PedidosController" method="POST">
                            <input type="hidden" name="ruta" value="confirmarPedido">
                            <button type="submit" class="boton d-flex rojo-bg blanco-texto h4 borde-none p-1 texto-none">
                                Confirmar Pedido
                            </button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</body>
</html>
