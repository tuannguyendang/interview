<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"> 
<script src="<c:url value="/resources/js/weather.js" />"></script>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
<head>
<body>
	<div class="container">
		<div>
			<div>
				<h2 class="headline first-child text-color">
					<span class="border-color">Weather in your city</span>
				</h2>
			</div>
			<div class="body-orange">
				<spring:url var="searchweather" value='/search'></spring:url>
				<form:form method="GET" modelAttribute="weather" action="${searchweather}" class="form-inline text-center first-child" role="form" id="searchform">
					<form:input class="form-control border-color col-sm-12" path="name" type="text" name="search" placeholder="City Search.."/>
					<button type="submit" class="btn btn-color">
						Search
					</button>
					<c:if test="${not empty weather.message}">
						<div class="error errorblock">${weather.message}</div>
					</c:if>
				</form:form>
			</div>
		</div>
		<div class="tab-pane active" id="forecast-list">
			<div id="forecast_list_ul">
				<c:if test="${not empty weather && not empty weather.id}">
					<table class="table center">
						<tbody>
							<tr>
								<td><img src="${weather.weatherIcon}"></td>
								<td><b>${weather.name},</b> <b><i> ${weather.weatherMain}</i></b>
									<p>${weather.fDate}</p>
									<p>
										<span class="badge badge-info">${weather.temperature} °С,</span> wind
										${weather.windSpeed}m/s, clouds ${weather.cloudsAll}%,
										${weather.humidity}hpa
									</p>
									<p>
										<spring:url var="deletecity" value='/deleteCity'>
											<spring:param name="cityID" value="${weather.id}" />
										</spring:url>
										<form:form id="deletecity" method="POST" action="${deletecity}">
											<input class="btn btn-color-red" type="submit" value="Delete" />
										</form:form>
									</p></td>
							</tr>
						</tbody>
					</table>
					<div id="newcontent">
						<spring:url var="showmore" value='/showmore'/>
						<form:form id="f1" method="POST" modelAttribute="weather" action="${showmore}">
							<form:input path="name" type="hidden"/>
							<form:input path="fDate" type="hidden"/>
							<form:input path="fromIndex" type="hidden"/>
							<input id="btshow" type="button" class="btn btn-color-gray" value="ShowMore" onclick="showMore();" />
						</form:form>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>