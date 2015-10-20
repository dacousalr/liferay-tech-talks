<%@page import="tasks.management.api.model.Task"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Task task = null;

%>

<aui:form action="<%= renderResponse.createActionURL() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= task == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (task != null) ? task.getTitle() : "new-task" %>'
	/>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:fieldset>
		<aui:input name="title" />
		
		<aui:input name="description" />
    </aui:fieldset>

    <aui:button-row>
            <aui:button type="submit" />

            <aui:button href="<%= redirect %>" type="cancel" />
    </aui:button-row>
</aui:form>