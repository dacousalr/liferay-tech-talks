<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="tasks.management.api.model.Task"%>

<%@ include file="/init.jsp"%>

<p>
	<b><liferay-ui:message
			key="tasks_management_portlet_TasksPortlet.caption" /></b> <b><liferay-ui:message
			key="tasks_management_portlet_TasksPortlet.list_of_tasks" /></b>

	<aui:button-row>
		<portlet:renderURL var="editTaskURL">
			<portlet:param name="mvcPath" value="/edit_task.jsp" />
			<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
		</portlet:renderURL>
	</aui:button-row>

	<%
		List<Task> tasks = (List<Task>) request.getAttribute("tasks");
	%>

	<liferay-ui:search-container total="<%=tasks.size()%>">
		<liferay-ui:search-container-results results="<%=tasks%>" />

		<liferay-ui:search-container-row
			className="tasks.management.api.model.Task" modelVar="task">

			<liferay-ui:search-container-column-text name="title" valign="top">
				<strong><%=task.getTitle()%></strong>

				<br />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text name="description"
				valign="top">
				<strong><%=task.getDescription()%></strong>

				<br />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</p>