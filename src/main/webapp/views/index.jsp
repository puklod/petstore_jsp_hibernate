<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Üdvözlet</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>	
			<div class="container">
			
				<div id="loginform" style="margin:5px">
					
					<form action="LoginController" method="post">
					
						<c:choose>
						
							<c:when test="${privileg == null}">	
								<div class="d-flex flex-row justify-content-end">
									<div class="p-2">
										<input type="text" class="form-control" name="userName" placeholder="Felhasználónév"/>
									</div>
									<div class="p-2">
										<input type="password" class="form-control" name="password" placeholder="Jelszó"/>
									</div>
									<div class="p-2">
										<button type="submit" class="btn btn-info" name="login" value="login">Bejelentkezés</button>
									</div>
								</div>
							</c:when>
							
							<c:otherwise>
								<div class="d-flex flex-row justify-content-end">
									<div class="p-2">
										<h4>${username}</h4>
									</div>
									<div class="p-2">
										<button type="submit" class="btn btn-info" name="logout" value="logout">Kijelentkezés</button>
									</div>
								</div>
							</c:otherwise>	
							
						</c:choose>
						
					</form>
					
					<h5 style="color:red;text-align:right">${loginErrorMessage}</h5>
					
				</div>
				
				<nav class="navbar navbar-expand-lg navbar-light bg-light">
				
					<div class="navbar-nav">
					
						<a class="nav-item nav-link" href="index">Főoldal</a>
						<a class="nav-item nav-link" href="mamals">Emlősök</a>
						<a class="nav-item nav-link" href="reptiles">Hüllők</a>
						<a class="nav-item nav-link" href="birds">Madarak</a>
						<a class="nav-item nav-link"href="aquatics">Vizi állatok</a>
						<a class="nav-item nav-link" href="stores">Üzleteink</a>
						
						<c:if test="${privileg eq 'administrator'}">
							<a class="nav-item nav-link" href="users">Felhasználó hozzáadása</a>
						</c:if>
						
					</div>
					
				</nav>
<div style="margin-top:50px">	
<h3>Üdv!</h3>
<h4>Ez az oldal egy állatkereskedést szeretne szimulálni.</h4>
<h4>Az oldalhoz használt adatbázis dinamikusan frissül, alapértelmezett neve <span style="color:red">petstore_jsp_hbernate</span>.</h4>
<h4>Az oldal teljeskörű használhatához regisztrálni kell egy felhasználót, ezt legegyszerűbben a <a href="./users">Felhasználók</a> oldalon tehetjük meg.</h4>
<h4>Adatbázis szerkesztőn keresztül az user "<span style="color:red">privileg</span>" mezőjében "<span style="color:red">administrator</span>"-t kell megadni.</h4>
<h4>A git repo-n található adatbázisban alapból szerepel egy <span style="color:red">admin</span> felhasználónevű, <span style="color:red">admin</span> jelszóval ellátott felhasználó.</h4>
<h4>A felhasználó létrehozását követően, belépés után (jobb felső sarok), az oldal teljeskörűen használható. Bejelentkezés nélkül csak a már hozzáadott adatok jelennek meg.</h4>
<h4>Az oldal java 11 és tomcat 10 alatt készült</h4>
<h4>Pukló Dániel</h4>
</div>
</div>
</body>
</html>