<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
    <title>PoliBurger - Gestión de Disponibilidad</title>
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
        <a href="${pageContext.request.contextPath}/CuentasController?ruta=cerrarSesion" 
           class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">
            Cerrar Sesión
        </a>
    </header>

    <section class="p-4">
        <div>
            <a class="p-b-2 rojo-texto texto-none texto-hover" href="${pageContext.request.contextPath}/PedidosController?ruta=listar">
                <i class="fa-solid fa-arrow-left"></i> Volver
            </a>
        </div>

        <div class="p-4 blanco-bg borde-redondeado">
            <h1 class="h2">Gestión de Disponibilidad</h1>
            <p class="m-t-0 h6 gris-texto">Activa o desactiva los productos según su disponibilidad</p>

            <div class="m-t-4">
                <h2 class="borde-linea borde-4px borde-b ancho-contenido amarillo-borde">Hamburguesas</h2>
                <div class="tarjetero">
                    <c:forEach var="plato" items="${platos}">
                        <c:if test="${plato.categoria == 'HAMBURGUESA'}">
                            <div class="tarjeta verde-bg-light">
                                <img src="${pageContext.request.contextPath}/img/platos/${plato.imagen}" 
                                     alt="${plato.nombre}">
                                <h3 class="m-t-1 m-b-0">${plato.nombre}</h3>
                                <p class="m-t-1 m-b-1 gris-texto h6">${plato.descripcion}</p>
                                
                                <div class="d-flex-column">
                                    <p class="texto-negrita m-t-1 m-b-2">$${plato.precio}</p>
                                    
                                    <form method="POST" action="${pageContext.request.contextPath}/MenuController" 
                                          style="margin: 0;">
                                        <input type="hidden" name="ruta" value="cambiarDisponibilidad">
                                        <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                                        <input type="hidden" name="disponible" value="${!plato.disponible}">
                                        
                                        <button type="submit" 
                                                class="boton boton-m ${plato.disponible ? 'verde-bg' : 'gris-bg'} blanco-texto borde-none texto-none centrado width-100">
                                            <i class="fas ${plato.disponible ? 'fa-check-circle' : 'fa-times-circle'} m-d-1"></i>
                                            ${plato.disponible ? 'Disponible' : 'No disponible'}
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>

            <div class="m-t-4">
                <h2 class="borde-linea borde-4px borde-b ancho-contenido amarillo-borde">Bebidas</h2>
                <div class="tarjetero">
                    <c:forEach var="plato" items="${platos}">
                        <c:if test="${plato.categoria == 'BEBIDA'}">
                            <div class="tarjeta verde-bg-light">
                                <img src="${pageContext.request.contextPath}/img/platos/${plato.imagen}" 
                                     alt="${plato.nombre}">
                                <h3 class="m-t-1 m-b-0">${plato.nombre}</h3>
                                <p class="m-t-1 m-b-1 gris-texto h6">${plato.descripcion}</p>
                                
                                <div class="d-flex-column">
                                    <p class="texto-negrita m-t-1 m-b-2">$${plato.precio}</p>
                                    
                                    <form method="POST" action="${pageContext.request.contextPath}/MenuController" 
                                          style="margin: 0;">
                                        <input type="hidden" name="ruta" value="cambiarDisponibilidad">
                                        <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                                        <input type="hidden" name="disponible" value="${!plato.disponible}">
                                        
                                        <button type="submit" 
                                                class="boton boton-m ${plato.disponible ? 'verde-bg' : 'gris-bg'} blanco-texto borde-none texto-none centrado width-100">
                                            <i class="fas ${plato.disponible ? 'fa-check-circle' : 'fa-times-circle'} m-d-1"></i>
                                            ${plato.disponible ? 'Disponible' : 'No disponible'}
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
