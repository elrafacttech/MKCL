<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title><spring:message code="actionLogSessionlist.title" /></title>
<spring:message code="project.resources" var="resourcespath" />


</head>

<body>
	
	<spring:message code="global.dateFormatWithoutTime" var="dateFormat" />
	<spring:message code="global.currencyCode" var="currencyCode" />
	<fmt:setTimeZone value="${sessionScope.timeZone}" />
	
	
	<div class="row">
		<div class="col-sm main-header">
			<h2>
				<spring:message code="actionLogSessionlist.users" />
			</h2>
			<label><b><spring:message code="actionLogSessionlist.usernameKey" /> ${userName} </b></label>
			<br>
			<label><b><spring:message code="actionLogSessionlist.useridKey" /> ${userId} </b></label>
		</div>
		
		<div class="col-sm">
			<div  class="btn-group pull-right">
				<a class="btn btn-main btn-rounded btn-long btn-sm" style="margin-bottom: 20px" href="userLogList"><spring:message code="actionLogSessionlist.userDetails" /></a> 
			</div>
		</div>
	</div>

<div class="holder">

		
	<div ><!-- class="holder" -->
		

		<c:if test="${UserSessionList != null}">
			<!--Integrated data table  -->
			<table class="table table-striped table-bordered" id="demotable" style="font-size: 15px;">
				<thead>
					<tr>
						<th><spring:message code="actionLogSessionlist.login" /></th>
						<th><spring:message code="actionLogSessionlist.logout" /></th>
						<th><spring:message code="actionLogSessionlist.duration" /></th>		
						<th><div class="visible-desktop">
								<spring:message code="actionLogSessionlist.viewActions" />
							</div></th>
					</tr>
				</thead>
				<tfoot>
					<tr>

					
					<th><spring:message code="actionLogSessionlist.login" /></th>
						<th><spring:message code="actionLogSessionlist.logout" /></th>
						<th><spring:message code="actionLogSessionlist.duration" /></th>	
	

						<th><div class="visible-desktop">
								<spring:message code="actionLogSessionlist.viewActions" />
							</div></th>
						<%-- <th><div class="visible-desktop">
								<spring:message code="global.delete" />
							</div></th> --%>
					</tr>
				</tfoot>
				
				<c:if test="${fn:length(UserSessionList) != 0}">
					<tbody>
						<c:forEach var="listObj" items="${UserSessionList}">
							<tr>

								<td>${listObj.startTime}</td>
								<td>${listObj.endTime}</td>
								<td>${listObj.duration}</td>
								<td><a href="userActivityLogList?userSessionId=${listObj.sessionId}&startTime=${listObj.startTime}&endTime=${listObj.endTime}&userId=${userId}&userName=${userName}" class="btn btn-success btn-rounded btn-long btn-sm"><spring:message
											code="actionLogSessionlist.viewDetails" /></a></td>
								
							</tr>
						</c:forEach>
					</tbody>

				</c:if>
			</table>
				<center>
					<a href="../actionLog/userLogList" class="btn btn-secondary btn-rounded btn-long btn-sm ">
					<spring:message code="global.back" />
					</a>
			</center>
			
			<c:if test="${fn:length(UserSessionList) != 0}">
				<!--Display count of records  -->
				<div class="span5 ml-0">
					<spring:message code="global.pagination.showing"></spring:message>
					${pagination.start+1} - ${pagination.end}
					<spring:message code="global.pagination.of"></spring:message>
					<c:choose>
						<c:when test="${disableNext==true}">
							 ${pagination.end} 
						</c:when>
						<c:otherwise>
							<spring:message code="actionLogSessionlist.ManyKey" />
						</c:otherwise>
					</c:choose>
				</div>

				<!--Display pagination buttons  -->
				<div class="dataTables_paginate paging_bootstrap pagination">
						<ul>
						<c:choose>
							<c:when
								test="${disablePrev==true || (fn:length(UserSessionList)<=pagination.recordsPerPage && pagination.start==0)}">
								<li class="prev"><a href="#" id="prev_anchor_tag"
									style="display: none;"><spring:message
											code="global.previous"></spring:message>
								</a></li>
							</c:when>
							<c:otherwise>
								<li class="prev"><a href="#" id="prev_anchor_tag"><spring:message
											code="global.previous"></spring:message>
								</a></li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${disableNext==true}">
								<li class="next"><a href="#" id="next_anchor_tag"
									style="display: none;"><spring:message code="global.next"></spring:message>
										→ </a></li>
							</c:when>
							<c:otherwise>
								<li class="next"><a href="#" id="next_anchor_tag"><spring:message
											code="global.next"></spring:message> → </a></li>
							</c:otherwise>
						</c:choose>
					</ul>
					<!--Form For previous,next buttons  -->
					<form:form action="prevActionSession" method="POST" modelAttribute="pagination"
						id="prev_selector_for_the_form" name="prev_selector_for_the_form">
						<form:hidden path="end" />
						<form:hidden path="start" />
						<form:hidden path="recordsPerPage" />
						<input type="hidden" id="searchText" name="searchText"
							value="${searchText}" />
						<input type="hidden" id="userId" name="userId"
							value="${userId}" />
						<input type="hidden" id="userName" name="userName"
							value="${userName}" />
					</form:form>
					<form:form action="nextActionSession" method="POST" modelAttribute="pagination"
						id="next_selector_for_the_form">
						<form:hidden path="end" />
						<form:hidden path="start" />
						<form:hidden path="recordsPerPage" />
						<input type="hidden" id="searchText" name="searchText"
							value="${searchText}" />
						<input type="hidden" id="userId" name="userId"
							value="${userId}" />
						<input type="hidden" id="userName" name="userName"
							value="${userName}" />
					</form:form>
				</div>
			</c:if>
		</c:if>
</div>
</div>
	<script type="text/javascript"
		src="<c:url value="${resourcespath}js/gridDataTable.js"></c:url>">
		
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$('#demotable')
									.dataTable(
											{
												"sDom" : "<'row-fluid'<'span4'><'span12'f>r>t<'row-fluid'<'span4'><'span8'>>",
												"iDisplayLength" : -1,"bSort": false,
												"aoColumns" : [ null, null,
														null, {
															"bSortable" : false
														}, ],
												"oTableTools" : {
													"sSwfPath" : "../resources/media/csv_xls_pdf.swf",
													"aButtons" : [
															"copy",
															"print",
															{
																"sExtends" : "collection",
																"sButtonText" : 'Save <span class="caret" />',
																"aButtons" : [
																		{
																			'sExtends' : 'csv',
																			'mColumns' : [
																					0,
																					1,
																					2,
																					3,
																					4 ]
																		},
																		{
																			'sExtends' : 'xls',
																			'mColumns' : [
																					0,
																					1,
																					2,
																					3,
																					4 ]
																		},
																		{
																			'sExtends' : 'pdf',
																			'mColumns' : [
																					0,
																					1,
																					2,
																					3,
																					4 ]
																		} ]
															} ]
												}
											});

							$("#prev_anchor_tag").click(function() {
								$("#prev_selector_for_the_form").submit();
								return false;
							});

							$("#next_anchor_tag").click(function() {
								$("#next_selector_for_the_form").submit();
								return false;
							});

							$('#go')
									.click(
											function() {
											
												var searchText = $('#search').val();
												var userId = $('#userId').val();
												window.location.href = '../actionLog/userSessionLogList?searchText='+ searchText +'&userId='+ userId;
											});

						});
	</script>
</body>
</html>
