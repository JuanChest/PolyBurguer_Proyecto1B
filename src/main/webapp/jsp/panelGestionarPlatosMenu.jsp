<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Platos del Menú - PolyBurguer</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .contenedor {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
        }
        
        .encabezado {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .encabezado h1 {
            color: #333;
            font-size: 2.5em;
            margin: 0 0 10px 0;
        }
        
        .encabezado p {
            color: #666;
            font-size: 1.1em;
        }
        
        .mensaje-exito {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .mensaje-error {
            background: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .botones-principales {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .botones-principales button,
        .botones-principales a {
            background: #667eea;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            margin-right: 10px;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
        }
        
        .botones-principales button:hover,
        .botones-principales a:hover {
            background: #764ba2;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        
        .boton-volver {
            background: #6c757d !important;
        }
        
        .boton-volver:hover {
            background: #5a6268 !important;
        }
        
        .seccion-form {
            background: #f8f9fa;
            padding: 25px;
            border-radius: 8px;
            margin-bottom: 30px;
            border-left: 4px solid #667eea;
        }
        
        .seccion-form h2 {
            color: #333;
            margin-top: 0;
            font-size: 1.8em;
        }
        
        .form-grupo {
            margin-bottom: 20px;
        }
        
        .form-grupo label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }
        
        .form-grupo input,
        .form-grupo textarea,
        .form-grupo select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 1em;
            font-family: 'Arial', sans-serif;
            transition: border-color 0.3s ease;
            box-sizing: border-box;
        }
        
        .form-grupo input:focus,
        .form-grupo textarea:focus,
        .form-grupo select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
        }
        
        .form-grupo textarea {
            resize: vertical;
            min-height: 100px;
        }
        
        .checkbox-grupo {
            display: flex;
            align-items: center;
        }
        
        .checkbox-grupo input[type="checkbox"] {
            width: auto;
            margin-right: 10px;
            cursor: pointer;
        }
        
        .checkbox-grupo label {
            margin: 0;
            cursor: pointer;
        }
        
        .botones-form {
            display: flex;
            gap: 10px;
            justify-content: center;
        }
        
        .botones-form button {
            flex: 1;
            max-width: 200px;
            padding: 12px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            font-weight: bold;
            transition: all 0.3s ease;
        }
        
        .boton-guardar {
            background: #28a745;
            color: white;
        }
        
        .boton-guardar:hover {
            background: #218838;
        }
        
        .boton-cancelar {
            background: #6c757d;
            color: white;
        }
        
        .boton-cancelar:hover {
            background: #5a6268;
        }
        
        .seccion-lista {
            margin-top: 30px;
        }
        
        .seccion-lista h2 {
            color: #333;
            font-size: 1.8em;
            margin-bottom: 20px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        
        .tabla-platos {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }
        
        .tabla-platos thead {
            background: #667eea;
            color: white;
        }
        
        .tabla-platos th {
            padding: 15px;
            text-align: left;
            font-weight: bold;
        }
        
        .tabla-platos td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
        }
        
        .tabla-platos tbody tr {
            transition: background 0.3s ease;
        }
        
        .tabla-platos tbody tr:hover {
            background: #f8f9fa;
        }
        
        .tabla-platos tbody tr:nth-child(even) {
            background: #f9f9f9;
        }
        
        .estado-disponible {
            background: #d4edda;
            color: #155724;
            padding: 5px 10px;
            border-radius: 3px;
            font-weight: bold;
        }
        
        .estado-no-disponible {
            background: #f8d7da;
            color: #721c24;
            padding: 5px 10px;
            border-radius: 3px;
            font-weight: bold;
        }
        
        .botones-acciones {
            display: flex;
            gap: 5px;
        }
        
        .boton-accion {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9em;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
            font-weight: bold;
        }
        
        .boton-editar {
            background: #007bff;
            color: white;
        }
        
        .boton-editar:hover {
            background: #0056b3;
        }
        
        .boton-eliminar {
            background: #dc3545;
            color: white;
        }
        
        .boton-eliminar:hover {
            background: #c82333;
        }
        
        .sin-platos {
            text-align: center;
            padding: 40px;
            color: #666;
            font-size: 1.1em;
            background: #f8f9fa;
            border-radius: 5px;
            margin-top: 20px;
        }
        
        @media (max-width: 768px) {
            .tabla-platos {
                font-size: 0.9em;
            }
            
            .tabla-platos th,
            .tabla-platos td {
                padding: 10px 5px;
            }
            
            .botones-acciones {
                flex-direction: column;
            }
            
            .boton-accion {
                width: 100%;
                text-align: center;
            }
            
            .botones-principales button,
            .botones-principales a {
                margin-bottom: 10px;
                width: 100%;
                margin-right: 0;
            }
        }
    </style>
</head>
<body>
    <div class="contenedor">
        <div class="encabezado">
            <h1>🍔 Gestionar Platos del Menú</h1>
            <p>Panel de administración para crear, modificar y eliminar platos</p>
        </div>
        
        <c:if test="${not empty mensaje}">
            <div class="mensaje-exito">
                ✓ <strong>Éxito:</strong> ${mensaje}
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="mensaje-error">
                ✗ <strong>Error:</strong> ${error}
            </div>
        </c:if>
        
        <div class="botones-principales">
            <button onclick="mostrarFormularioCrear()">➕ Crear Nuevo Plato</button>
            <a href="${pageContext.request.contextPath}/MenuController?ruta=verMenu" class="boton-volver">👁️ Ver Menú</a>
        </div>
        
        <!-- Formulario para crear nuevo plato -->
        <div id="formularioCrear" class="seccion-form" style="display: none;">
            <h2>Crear Nuevo Plato</h2>
            <form method="POST" action="${pageContext.request.contextPath}/MenuController?ruta=crearPlato">
                <div class="form-grupo">
                    <label for="nombre">Nombre del Plato *</label>
                    <input type="text" id="nombre" name="nombre" required placeholder="Ej: Hamburguesa Deluxe">
                </div>
                
                <div class="form-grupo">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion" name="descripcion" required placeholder="Describe los ingredientes y características del plato"></textarea>
                </div>
                
                <div class="form-grupo">
                    <label for="precio">Precio (en pesos) *</label>
                    <input type="number" id="precio" name="precio" step="0.01" min="0.01" required placeholder="Ej: 45.99">
                </div>
                
                <div class="form-grupo">
                    <label for="categoria">Categoría *</label>
                    <select id="categoria" name="categoria" required>
                        <option value="">-- Selecciona una categoría --</option>
                        <option value="HAMBURGUESA">Hamburguesa</option>
                        <option value="BEBIDA">Bebida</option>
                    </select>
                </div>
                
                <div class="form-grupo checkbox-grupo">
                    <input type="checkbox" id="disponible" name="disponible" checked>
                    <label for="disponible">Disponible al crear</label>
                </div>
                
                <div class="botones-form">
                    <button type="submit" class="boton-guardar">✓ Guardar Plato</button>
                    <button type="button" class="boton-cancelar" onclick="ocultarFormularioCrear()">✕ Cancelar</button>
                </div>
            </form>
        </div>
        
        <!-- Formulario para modificar plato -->
        <c:if test="${not empty plato}">
            <div class="seccion-form">
                <h2>Modificar Plato: ${plato.nombre}</h2>
                <form method="POST" action="${pageContext.request.contextPath}/MenuController?ruta=modificarPlato">
                    <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                    
                    <div class="form-grupo">
                        <label for="nombreMod">Nombre del Plato *</label>
                        <input type="text" id="nombreMod" name="nombre" value="${plato.nombre}" required>
                    </div>
                    
                    <div class="form-grupo">
                        <label for="descripcionMod">Descripción *</label>
                        <textarea id="descripcionMod" name="descripcion" required>${plato.descripcion}</textarea>
                    </div>
                    
                    <div class="form-grupo">
                        <label for="precioMod">Precio (en pesos) *</label>
                        <input type="number" id="precioMod" name="precio" step="0.01" min="0.01" value="${plato.precio}" required>
                    </div>
                    
                    <div class="form-grupo">
                        <label for="categoriaMod">Categoría *</label>
                        <select id="categoriaMod" name="categoria" required>
                            <option value="HAMBURGUESA" ${plato.categoria == 'HAMBURGUESA' ? 'selected' : ''}>Hamburguesa</option>
                            <option value="BEBIDA" ${plato.categoria == 'BEBIDA' ? 'selected' : ''}>Bebida</option>
                        </select>
                    </div>
                    
                    <div class="form-grupo checkbox-grupo">
                        <input type="checkbox" id="disponibleMod" name="disponible" ${plato.disponible ? 'checked' : ''}>
                        <label for="disponibleMod">Disponible</label>
                    </div>
                    
                    <div class="botones-form">
                        <button type="submit" class="boton-guardar">✓ Guardar Cambios</button>
                        <a href="${pageContext.request.contextPath}/MenuController?ruta=gestionarPlatosMenu" class="boton-accion" style="background: #6c757d; color: white; padding: 12px 25px; text-align: center; flex: 1; max-width: 200px; border-radius: 5px;">✕ Cancelar</a>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Lista de platos -->
        <div class="seccion-lista">
            <h2>📋 Lista de Platos</h2>
            
            <c:choose>
                <c:when test="${not empty platos && platos.size() > 0}">
                    <table class="tabla-platos">
                        <thead>
                            <tr>
                                <th style="width: 10%">ID</th>
                                <th style="width: 15%">Nombre</th>
                                <th style="width: 30%">Descripción</th>
                                <th style="width: 10%">Precio</th>
                                <th style="width: 12%">Categoría</th>
                                <th style="width: 12%">Estado</th>
                                <th style="width: 11%">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="plato" items="${platos}">
                                <tr>
                                    <td><strong>${plato.idPlatoMenu}</strong></td>
                                    <td>${plato.nombre}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${plato.descripcion.length() > 50}">
                                                <span title="${plato.descripcion}">${plato.descripcion.substring(0, 50)}...</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${plato.descripcion}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><strong>$<fmt:formatNumber value="${plato.precio}" type="number" minFractionDigits="2"/></strong></td>
                                    <td>${plato.categoria}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${plato.disponible}">
                                                <span class="estado-disponible">Disponible</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="estado-no-disponible">No Disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="botones-acciones">
                                            <a href="${pageContext.request.contextPath}/MenuController?ruta=formularioModificar&idPlato=${plato.idPlatoMenu}" class="boton-accion boton-editar">✏️ Editar</a>
                                            <form method="POST" action="${pageContext.request.contextPath}/MenuController?ruta=eliminarPlato" style="display: inline; margin: 0;">
                                                <input type="hidden" name="idPlato" value="${plato.idPlatoMenu}">
                                                <button type="submit" class="boton-accion boton-eliminar" onclick="return confirm('¿Estás seguro de que deseas eliminar este plato?');">🗑️ Eliminar</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="sin-platos">
                        <p>📭 No hay platos registrados en el sistema.</p>
                        <p>Crea el primero haciendo clic en el botón "Crear Nuevo Plato" arriba.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <script>
        function mostrarFormularioCrear() {
            document.getElementById('formularioCrear').style.display = 'block';
            document.querySelector('.seccion-lista').scrollIntoView({ behavior: 'smooth' });
        }
        
        function ocultarFormularioCrear() {
            document.getElementById('formularioCrear').style.display = 'none';
        }
    </script>
</body>
</html>
