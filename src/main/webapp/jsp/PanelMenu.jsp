<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
                <title>PoliBurguer - Menú</title>
            </head>

            <body class="neutro-bg">
                <header class="header-flex rojo-bg p-t-2 p-b-2 p-i-4 p-d-4">
                    <div class="blanco-texto d-flex">
                        <img class="imagen-circular-l" src="${pageContext.request.contextPath}/img/logo.png"
                            alt="Logo PoliBurguer">
                        <div class="d-flex-column">
                            <h1 class="m-t-0 m-b-0 texto-negrita">PoliBurger</h1>
                            <p class="m-t-0 m-b-0">Sistema de Pedidos</p>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/LoginController?ruta=mostrarFormulario"
                        class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Acceso Personal</a>
                </header>

                <!-- Mensajes de retroalimentación -->
                <c:if test="${not empty sessionScope.mensaje}">
                    <section class="p-2">
                        <div
                            class="${sessionScope.tipoMensaje == 'error' ? 'mensaje-error rojo-bg blanco-texto' : 'mensaje-exito verde-bg blanco-texto'} p-2 borde-redondeado">
                            <p class="m-0">${sessionScope.mensaje}</p>
                        </div>
                    </section>
                    <c:remove var="mensaje" scope="session" />
                    <c:remove var="tipoMensaje" scope="session" />
                </c:if>

                <section class="p-i-5 p-d-5">
                    <div>
                        <h1 class="texto-normal h1">Nuestro Menú</h1>
                        <p class="h4">Selecciona tus productos favoritos</p>
                    </div>

                    <!-- Sección Hamburguesas -->
                    <div>
                        <h1 class="borde-linea borde-4px borde-b ancho-contenido amarillo-borde">Hamburguesas</h1>
                        <div class="tarjetero">
                            <c:forEach var="plato" items="${platos}">
                                <c:if test="${plato.categoria == 'HAMBURGUESA'}">
                                    <div class="tarjeta">
                                        <img src="${pageContext.request.contextPath}/img/platos/${plato.imagen}"
                                            alt="${plato.nombre}"
                                            onerror="this.src='https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop'">
                                        <h1>${plato.nombre}</h1>
                                        <p>${plato.descripcion}</p>
                                        <div>
                                            <p>$
                                                <fmt:formatNumber value="${plato.precio}" pattern="#,##0.00" />
                                            </p>
                                            <form action="${pageContext.request.contextPath}/PedidosController"
                                                method="POST" style="display:inline;">
                                                <input type="hidden" name="ruta" value="agregarPlato">
                                                <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                                                <button type="submit"
                                                    class="boton boton-m rojo-bg blanco-texto borde-none texto-none centrado">+
                                                    Agregar</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>

                            <!-- Mensaje si no hay hamburguesas -->
                            <c:set var="hayHamburguesas" value="false" />
                            <c:forEach var="plato" items="${platos}">
                                <c:if test="${plato.categoria == 'HAMBURGUESA'}">
                                    <c:set var="hayHamburguesas" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${!hayHamburguesas}">
                                <div style="width: 100%; text-align: center; padding: 20px;">
                                    <p class="h5 gris-texto">No hay hamburguesas disponibles en este momento.</p>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- Sección Bebidas -->
                    <div>
                        <h1 class="borde-linea borde-4px borde-b ancho-contenido amarillo-borde">Bebidas</h1>
                        <div class="tarjetero">
                            <c:forEach var="plato" items="${platos}">
                                <c:if test="${plato.categoria == 'BEBIDA'}">
                                    <div class="tarjeta">
                                        <img src="${pageContext.request.contextPath}/img/platos/${plato.imagen}"
                                            alt="${plato.nombre}"
                                            onerror="this.src='https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400&h=300&fit=crop'">
                                        <h1>${plato.nombre}</h1>
                                        <p>${plato.descripcion}</p>
                                        <div>
                                            <p>$
                                                <fmt:formatNumber value="${plato.precio}" pattern="#,##0.00" />
                                            </p>
                                            <form action="${pageContext.request.contextPath}/PedidosController"
                                                method="POST" style="display:inline;">
                                                <input type="hidden" name="ruta" value="agregarPlato">
                                                <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                                                <button type="submit"
                                                    class="boton boton-m rojo-bg blanco-texto borde-none texto-none centrado">+
                                                    Agregar</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>

                            <!-- Mensaje si no hay bebidas -->
                            <c:set var="hayBebidas" value="false" />
                            <c:forEach var="plato" items="${platos}">
                                <c:if test="${plato.categoria == 'BEBIDA'}">
                                    <c:set var="hayBebidas" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${!hayBebidas}">
                                <div style="width: 100%; text-align: center; padding: 20px;">
                                    <p class="h5 gris-texto">No hay bebidas disponibles en este momento.</p>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- Mensaje si no hay platos en absoluto -->
                    <c:if test="${empty platos}">
                        <div style="width: 100%; text-align: center; padding: 40px;">
                            <p class="h3">No hay platos disponibles en este momento.</p>
                        </div>
                    </c:if>
                </section>

                <a href="${pageContext.request.contextPath}/PedidosController?ruta=mostrarResumenPedido"
                    class="boton-flotante boton amarillo-bg negro-texto borde-redondeado texto-none">
                    Ver Carrito <span class="contador">${not empty sessionScope.pedidoEnCurso ?
                        sessionScope.pedidoEnCurso.detalles.size() :
                        0}</span>
                </a>
                <script>
                    // Mantener posición del scroll al recargar la página
                    document.addEventListener('DOMContentLoaded', function () {
                        // Restaurar posición del scroll si existe
                        const scrollPos = sessionStorage.getItem('scrollPosition');
                        if (scrollPos) {
                            window.scrollTo(0, parseInt(scrollPos));
                            sessionStorage.removeItem('scrollPosition');
                        }

                        // Guardar posición antes de enviar cualquier formulario
                        const forms = document.querySelectorAll('form');
                        forms.forEach(form => {
                            form.addEventListener('submit', function () {
                                sessionStorage.setItem('scrollPosition', window.scrollY);
                            });
                        });
                    });
                </script>
            </body>

            </html>