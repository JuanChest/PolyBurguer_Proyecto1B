<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <title>PoliBurguer - Sistema de Pedidos</title>
</head>
<body class="neutro-bg">
    <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
        <div class="blanco-texto d-flex">
            <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png" alt="Logo PoliBurguer">
            <div class="d-flex-column">
                <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                <p class="m-t-0 m-b-0">Sistema de Pedidos</p>
            </div>
        </div>
        <a href="accesopersonal.html" class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Acceso Personal</a>
    </header>

    <section class="p-i-5 p-d-5">
        <div>
            <h1 class="texto-normal h1">Nuestro Menú</h1>
            <p class="h4">Selecciona tus productos favoritos</p>
        </div>

        <!-- Mostrar mensaje de error si existe -->
        <c:if test="${not empty error}">
            <div style="background-color: #ffcccc; padding: 15px; border-radius: 5px; margin: 20px 0;">
                <p style="color: #cc0000; margin: 0;">${error}</p>
            </div>
        </c:if>

        <div>
            <h1 class="borde-linea borde-4px borde-b ancho-contenido amarillo-borde">Nuestros Platos</h1>
            <div class="tarjetero">
                <!-- Iterar sobre la lista de platos usando JSTL -->
                <c:forEach var="plato" items="${platos}">
                    <div class="tarjeta">
                        <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop" 
                            alt="${plato.nombrePlato}">
                        <h1>${plato.nombrePlato}</h1>
                        <p>${plato.descripcion}</p>
                        <div>
                            <p>$<fmt:formatNumber value="${plato.precio}" pattern="#,##0.00"/></p>
                            <a href="#" class="boton boton-m rojo-bg blanco-texto borde-none texto-none centrado">+ Agregar</a>
                        </div>
                    </div>
                </c:forEach>
                
                <!-- Mensaje si no hay platos disponibles -->
                <c:if test="${empty platos}">
                    <div style="width: 100%; text-align: center; padding: 40px;">
                        <p class="h3">No hay platos disponibles en este momento.</p>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
    
    <a href="carrito.html" class="boton-flotante boton amarillo-bg negro-texto borde-redondeado texto-none">
        Ver Carrito <span class="contador">0</span>
    </a>
</body>
</html>