<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/domeframework.css">
    <script src="https://kit.fontawesome.com/955adb8bca.js" crossorigin="anonymous"></script>
    <title>PoliBurger - Nueva Cuenta</title>
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
        <a href="${pageContext.request.contextPath}/CuentasController?ruta=cerrarSesion" class="boton borde-none p-1 h6 amarillo-bg rojo-texto texto-none centrado">Cerrar Sesión</a>
    </header>

    <section class="p-4">
        <div>
            <a class="rojo-texto texto-none texto-hover" href="${pageContext.request.contextPath}/CuentasController?ruta=listarCuentas"> 
                <i class="fa-solid fa-arrow-left"></i> Volver al Panel
            </a>
        </div>

        <div class="d-flex centrado m-t-2">
		    <div class="width-3 blanco-bg borde-redondeado box-shadow p-4">
		        <div class="centrado d-flex-column">
		            <i class="fa-solid fa-user-plus fa-3x rojo-texto m-b-1"></i>
		            <h2 class="texto-normal h3 m-t-0 m-b-0">Nueva Cuenta</h2>
		            <p class="gris-texto m-t-1">Registra un nuevo miembro del personal</p>
		        </div>
		        
		        <hr class="m-t-2 m-b-2">
		
		        <form action="${pageContext.request.contextPath}/CuentasController" method="POST" class="d-flex-column">
		            <input type="hidden" name="ruta" value="crearUsuario">
		
		            <div class="m-b-2">
		                <label class="d-block texto-negrita gris-texto h6 m-b-1">Nombre</label>
		                <input type="text" name="nombre" class="borde-gris p-2 width-100" placeholder="Ej: Juan" required>
		            </div>
		
		            <div class="m-b-2">
		                <label class="d-block texto-negrita gris-texto h6 m-b-1">Apellido</label>
		                <input type="text" name="apellido" class="borde-gris p-2 width-100" placeholder="Ej: Pérez" required>
		            </div>
		
		            <div class="m-b-2">
		                <label class="d-block texto-negrita gris-texto h6 m-b-1">Cédula (Usuario)</label>
		                <input type="text" name="cedula" class="borde-gris p-2 width-100" placeholder="Ej: 1726354410" required>
		            </div>
		
		            <div class="m-b-2">
		                <label class="d-block texto-negrita gris-texto h6 m-b-1">Contraseña</label>
		                <input type="password" name="contrasenia" class="borde-gris p-2 width-100" placeholder="********" required>
		            </div>
		
		            <div class="d-flex-column centrado m-t-3">
					    <div class="d-flex-column width-2">
					        <button type="submit" class="boton rojo-bg blanco-texto borde-none p-2 h5 width-100 cursor-pointer m-b-2">
					            Crear Cuenta
					        </button>
					        
					        <a href="${pageContext.request.contextPath}/CuentasController?ruta=listarCuentas"
					           class="boton gris-bg blanco-texto borde-none p-2 h5 width-100 centrado texto-none">
					            Cancelar
					        </a>
					    </div>
					</div>
		        </form>
		    </div>
		</div>
    </section>
</body>
</html>