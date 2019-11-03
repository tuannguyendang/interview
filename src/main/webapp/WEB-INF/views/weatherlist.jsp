<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:if test="${not empty weatherModels && weatherModels.size() > 0}">
	<c:forEach items="${weatherModels}" var="weather">
		<table class="table center">
			<tbody>
				<tr>
					<td><img src="${weather.weatherIcon}"></td>
					<td><b>${weather.name},</b> <b><i> broken clouds</i></b>
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
	</c:forEach>
	<spring:url var="showmore" value='/showmore' />
	<form:form id="f1" method="POST" modelAttribute="weather" action="${showmore}">
		<form:input path="name" type="hidden" />
		<form:input path="fDate" type="hidden" />
		<form:input path="fromIndex" type="hidden" />
		<input id="btshow" type="button" class="btn btn-color-gray" value="ShowMore" onclick="showMore();" />
	</form:form>
</c:if>
