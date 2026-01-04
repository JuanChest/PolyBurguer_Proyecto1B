<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
    <title>Cocinero Dashboard - PoliBurger</title>
    
    <style>
        /* Clases de apoyo por si no están en tu framework */
        .azul-bg-light { background-color: #e3f2fd !important; border: 1px solid #90caf9; }
        .azul-bg { background-color: #2196f3 !important; color: white !important; }
        .verde-bg { background-color: #4caf50 !important; color: white !important; }
        .pointer { cursor: pointer; }
    </style>
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
        <a href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarMenu" class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Cerrar Sesión</a> 	
    </header>

    <section class="dashboard-cocinero p-4">
        <h2 class="m-b-3 h4 texto-negrita gris-texto">Pedidos Activos</h2>
        
        <div class="pedidos-grid">
            <c:forEach items="${pedido}" var="p">
                <c:if test="${p.estadoPedido != 'LISTO'}">
                    <c:set var="estiloTarjeta" value="${p.estadoPedido == 'PENDIENTE' ? 'blanco-bg' : 'azul-bg-light'}" />
                    <c:set var="estiloEtiqueta" value="${p.estadoPedido == 'PENDIENTE' ? 'amarillo-bg' : 'azul-bg'}" />

                    <div class="tarjeta-pedido-activo ${estiloTarjeta} borde-redondeado p-2 box-shadow m-b-2">
                        <div class="d-flex space-between m-b-1">
                            <p class="h6 texto-negrita gris-texto m-0">Pedido #${p.nroPedido}</p>
                            <span class="etiqueta-estado ${estiloEtiqueta} gris-texto texto-negrita h7 borde-redondeado p-t-7 p-b-7 p-i-1">
                                ${p.estadoPedido}
                            </span>
                        </div>
                        <p class="h7 gris-texto m-t-0 m-b-1">${p.fechaCreacion}</p>
                        <p class="h6 texto-negrita m-t-1 m-b-2 gris-texto">1x PoliBurguer Especial</p>

                        <div class="d-flex space-between align-items-center">
                            <p class="h5 texto-negrita rojo-texto m-0">Total: $${p.totalPedido}</p>
                            <form action="${pageContext.request.contextPath}/PedidosController" method="POST">
                                <input type="hidden" name="idPedido" value="${p.idPedido}">
                                <input type="hidden" name="ruta" value="seleccionarPedido">
                                <c:choose>
                                    <c:when test="${p.estadoPedido == 'PENDIENTE'}">
                                        <button type="submit" class="boton borde-none rojo-bg blanco-texto p-1 pointer">
                                            Iniciar Preparación
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="boton borde-none verde-bg blanco-texto p-1 pointer">
                                            Terminar Pedido
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <hr class="divisor m-t-4 m-b-4">

        <h2 class="m-b-3 h4 texto-negrita gris-texto">Pedidos Completados</h2>
        <div class="completados-grid">
            <c:forEach items="${pedido}" var="p">
                <c:if test="${p.estadoPedido == 'LISTO'}">
                    <div class="tarjeta-completado verde-bg-light borde-redondeado p-1 box-shadow">
                        <div class="d-flex space-between align-items-start">
                            <div>
                                <p class="h6 texto-negrita gris-texto m-t-0 m-b-0">#${p.nroPedido}</p>
                                <p class="h7 gris-texto m-t-0 m-b-0">$${p.totalPedido}</p>
                                <p class="h7 gris-texto m-t-0 m-b-0">${p.fechaCreacion}</p>
                            </div>
                            <i class="fas fa-check-circle verde-texto h5"></i>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </section>
</body>
</html>