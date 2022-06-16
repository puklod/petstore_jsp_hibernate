<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Papagájaink</title>
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
				
				<h2 style="text-align:center; margin: 20px 0 20px 0">Papagájaink</h2>
				
				<c:if test="${privileg eq 'administrator'}">
				
					<div id="saveEntity">
					
						<c:if test="${saveErrorMessage != null}">
						
							<div id="saveError" class="alert alert-danger" role="alert">
								<c:out value="${saveErrorMessage}"/>
							</div>
							
						</c:if>
						
						<form action="parrots" method="post">
						
							<table style="text-align:center" class="table">
							
								<thead>
								
									<tr>
										<th>Fajta<span style="color:red">*</span></th>
										<th>Toll szín</th>
										<th>Testméret</th>
										<th>Szükséges kalitka mérete</th>
										<th>Tud beszélni?</th>
										<th>Üzlet</th>
									</tr>
									
								</thead>
								
								<tbody>
								
									<tr>
										
										<th>
											<input type="text" class="form-control" name="type"/>
										</th>
										
										<th>
											<input type="text" class="form-control" name="featherColor"/>
										</th>
										
										<th>
											<select class="form-select" name="size">
												<option value="Kicsi">Kicsi</option>
												<option value="Közepes">Közepes</option>
												<option value="Nagy">Nagy</option>
											</select>								
										</th>
										
										<th>
											<select class="form-select" name="cageSizeRequirment">
												<option value="Kicsi">Kicsi</option>
												<option value="Közepes">Közepes</option>
												<option value="Nagy">Nagy</option>
											</select>
										</th>										
										
										<th>
											<select class="form-select" name="canTalk">
												<option value="false">Nem</option>
												<option value="true">Igen</option>
											</select>
										</th>										
										
										<th>
											<select class="form-select" name="store">
											
												<c:forEach items="${listStore}" var="store">
													<option value="${store.id}">${store.name}</option>
												</c:forEach>
												
											</select>
										</th>
										
										<th>
											<button type="submit" class="btn btn-info" name="save" value="save">Hozzáad</button>
										</th>
										
									</tr>
									
								</tbody>
								
							</table>
							
							<h6><span style="color:red">*</span>=kötelező mező</h6>
							
						</form>
						
					</div>
					
				</c:if>
				
				<c:choose>
				
					<c:when test="${listEntity.size() > 0}">
					
						<div id="entityTable">
						
							<c:if test="${updateErrorMessage != null}">
							
								<div id="updateError" class="alert alert-danger" role="alert">
									<c:out value="${updateErrorMessage}"/>
								</div>
								
							</c:if>
							
							<form id="editForm"  action="parrots" method="post">
							
								<table class="table table-striped table-bordered" style="text-align:center">
								
									<tr class="thead-dark">
									
										<c:if test="${privileg eq 'administrator'}">
											<th>#</th>
										</c:if>
										
										<th>Fajta</th>
										<th>Toll szín</th>
										<th>Testméret</th>
										<th>Szükséges kalitka mérete</th>
										<th>Tud beszélni?</th>
										<th>Üzlet</th>
										
										<c:if test="${privileg eq 'administrator'}">
											<th></th>
											<th></th>
										</c:if>
										
									</tr>
									
									<c:forEach items="${listEntity}" var="entity">
									
										<c:choose>
										
											<c:when test="${privileg eq 'administrator' && editId == entity.id}">
												
												<tr>
												
													<td>
														${entity.id}
														<input type="hidden" name="id" value="${entity.id}"/>
													</td>
													
													<td>
														<input type="text" class="form-control" name="type" value="${entity.type}">
													</td>
													
													<td>
														<input type="text" class="form-control" name="featherColor" value="${entity.featherColor}"/>
													</td>
													
													<td>
														<select class="form-select" name="size">
															<option value="${entity.size}" selected hidden>${entity.size}</option>
															<option value="Kicsi">Kicsi</option>
															<option value="Közepes">Közepes</option>
															<option value="Nagy">Nagy</option>
														</select>												
													</td>
													
													<td>
														<select class="form-select" name="cageSizeRequirment">
															<option value="${entity.size}" selected hidden>${entity.size}</option>
															<option value="Kicsi">Kicsi</option>
															<option value="Közepes">Közepes</option>
															<option value="Nagy">Nagy</option>
														</select>												
													</td>
													
													<td>
														<select class="form-select" name="canTalk" >
															<option value="${entity.canTalk}" selected hidden>${entity.canTalk}</option>
															<option value="false">Nem</option>
															<option value="true">Igen</option>
														</select>												
													</td>
													
													<td>
														<select class="form-select" name="store">
															<option value="${entity.store.id}" selected hidden>${entity.store.name}</option>
															
															<c:forEach items="${listStore}" var="store">
																<option value="${store.id}">${store.name}</option>
															</c:forEach>
														</select>
													</td>
													
													<td>
														<button type="submit" class="btn btn-success" name="update">Frissítés</button>
													</td>
													
													<td>
														<button type="submit" class="btn btn-info" name="abort">Mégse</button>
													</td>
													
												</tr>
												
											</c:when>
											
											<c:otherwise>
											
												<tr>
												
													<c:if test="${privileg eq 'administrator'}">
														<td>${entity.id}</td>
													</c:if>
													
													<td>${entity.type}</td>
													<td>${entity.featherColor}</td>
													<td>${entity.size}</td>
													<td>${entity.cageSizeRequirment}</td>
													
													<c:choose>
													
														<c:when test="${!entity.canTalk}">
															<td>Nem</td>
														</c:when>
														
														<c:otherwise>
															<td>Igen</td>
														</c:otherwise>
													</c:choose>
													
													<td>${entity.store.name}</td>
													
													<c:choose>
													
														<c:when test="${editId == null && privileg eq 'administrator'}">
														
															<td>
																<button class="btn btn-info" name="edit" value="${entity.id}">Szerkesztés</button>
															</td>	
															
															<td>
																<button type="submit" class="btn btn-danger" onclick="confirmDelete()" name="delete" value="${entity.id}">Töröl</button>
															</td>
															
														</c:when>
														
														<c:when test="${privileg eq 'administrator'}">
															<td></td>
															<td></td>
														</c:when>
														
													</c:choose>
													
												</tr>
												
											</c:otherwise>
											
										</c:choose>
										
									</c:forEach>
									
								</table>
								
							</form>
							
						</div>
						
					</c:when>
					
					<c:otherwise>
						<h3 style="text-align:center; color:red">Jelenleg egyik üzletünkben sincs papagáj!</h3>
					</c:otherwise>
					
				</c:choose>
				
			</div>
			
		</c:otherwise>
		
	</c:choose>
	
	<script type="text/javascript">
		const form = document.getElementById('editForm');
		function confirmDelete() {
			if(confirm("Biztos törli a bejegyzést az adatbázisból?")){
			} else {
				form.addEventListener('submit', e => {
					e.preventDefault();
					window.location.href = window.location.href;
				});
			}
		}													
	</script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
</html>