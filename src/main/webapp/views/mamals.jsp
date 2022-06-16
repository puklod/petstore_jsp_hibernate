<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Emlősök</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
	<c:choose>
	
		<c:when test="${databaseErrorMessage != null}">
		
			<h1 style="text-align:center; color:red">${databaseErrorMessage}</h1>
			
		</c:when>
	
		<c:otherwise>
		
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
						<a class="nav-item nav-link active" href="mamals">Emlősök</a>
						<a class="nav-item nav-link" href="reptiles">Hüllők</a>
						<a class="nav-item nav-link" href="birds">Madarak</a>
						<a class="nav-item nav-link"href="aquatics">Vizi állatok</a>
						<a class="nav-item nav-link" href="stores">Üzleteink</a>
						
						<c:if test="${privileg eq 'administrator'}">
							<a class="nav-item nav-link" href="users">Felhasználó hozzáadása</a>
						</c:if>
						
					</div>
					
				</nav>
				
				<h2 style="text-align:center; margin: 20px 0 20px 0">Emlősök</h2>
				
				<table class="table table-striped table-bordered">
				
				<tbody>
					<tr>
						<td><a href="dogs">Kutyáink</a></td>
					</tr>				
				</tbody>
			</table>
</div>
</c:otherwise>
</c:choose>
</body>
</html>