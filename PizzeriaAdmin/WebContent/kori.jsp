<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pizzeria Tyyni - Ostoskori</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="wrapper">

		<div id="header">
			<div id="banner">
				<img class="logo" src="img/logo2.png" alt="logo">
			</div>
			<nav id="nav">
				<ul>
					<li><a href="index.jsp">Koti</a></li>
					<li><a href="menu">Menu</a></li>
					<li class="nav-bar-active"><a href="kori.jsp">Kori</a></li>
					<li><a href="me.jsp">Me</a></li>
				</ul>
			</nav>
		</div>

		<div id="main">

			<div id="pizzalista">
				<div id="pizzablock">


					<div>
						<ul id="muru_step" class="muru">

							<li class="muru_current"><span class="muru_text_current">Kori</span></li>
							<li class="muru_todo"><span class="muru_text_todo">Tilaus</span></li>
							<li class="muru_todo"><span class="muru_text_todo">Yhteenveto</span></li>
							<li id="muru_end" class="muru_loppu"><span
								class="muru_text_todo">Vahvistus</span></li>

						</ul>
					</div>
					<p class="otsikko">Ostoskori:</p>

					<hr class="pviiva" />
					<c:if test="${empty kori.ostokset}">
						<p>
							<i>Kori on tyhjä.</i>
						</p>
					</c:if>
					<c:forEach items="${kori.ostokset}" var="k" varStatus="looppi">

						<table class="ktaulu">
							<tr>
								<td class="pnimi"><c:out value="${k.tuote.id}. " /> <c:out
										value="${k.tuote.nimi}" /> <c:out value=" x${k.lkm}" /></td>
								<td></td>
								<td class="phinta"><fmt:formatNumber type="number"
										minFractionDigits="2" maxFractionDigits="2"
										value="${k.rivihinta}" /> &#x20ac;</td>
							</tr>

							<tr>

								<td><c:if
										test="${k.tuote.getClass().simpleName == 'Pizza'}">
										<c:forEach items="${k.tuote.taytteet}" var="tayte"
											varStatus="status">
    								${tayte}<c:if test="${!status.last}">,</c:if>
										</c:forEach>
									</c:if></td>
							</tr>
							<tr>
								<td class="mnimi"><c:if
										test="${k.tuote.getClass().simpleName == 'Pizza'}">
										<c:if test="${k.valkosipuli == true}">							
							valkosipuli<c:if
												test="${k.valkosipuli == true && k.oregano == true}">, 
							</c:if>

										</c:if>
										<c:if test="${k.oregano == true}">
							oregano
							</c:if>
									</c:if></td>

								<td></td>

								<td class="c_nappula">
									<form action="menu" method="post">
									<input type="hidden" name="action" value="del">
										<input type="hidden" name="rivinumero"
											value="<c:out value="${looppi.index}"/>"> 
										<button class="pnappula" type="submit" value="del">Poista</button>
									</form>
								</td>
							</tr>
						</table>
						<br />

					</c:forEach>
					<br />
					<hr class="pviiva" />
					<c:if test="${not empty kori.ostokset}">
						<table>
							<tr>
								<td>
									<form action="menu" method="post">
										<input type="hidden" name="action" value="tyhjkori">
										<button class="tnappula" type="submit" value="tyhjkori">Tyhjennä
											kori</button>
									</form>
									<p class="knimi">
										Yhteensä:
										<fmt:formatNumber type="number" minFractionDigits="2"
											maxFractionDigits="2" value="${hinta}" />
										&#x20ac; &#8199;
									</p>
								</td>
							</tr>
							<tr>
								<td>
									<p class="knimi">
										Opiskelijalle:
										<fmt:formatNumber type="number" minFractionDigits="2"
											maxFractionDigits="2" value="${ohinta}" />
										&#x20ac; &#8199;
									</p>
								</td>
								<td>
									<form action="tilaus.jsp">
										<input class="nappula" type="submit" value="Osta">
									</form>

								</td>
							</tr>
						</table>
					</c:if>
				</div>
			</div>
		</div>

		<div id="footer">Copyright © 2014 Pizzeria Tyyni</div>

	</div>
</body>
</html>