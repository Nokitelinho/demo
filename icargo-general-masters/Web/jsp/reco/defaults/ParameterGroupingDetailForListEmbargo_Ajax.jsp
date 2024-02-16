<%--
* Project	 		: iCargo
* Module Code & Name: Reco
* File Name			: ParameterGroupingDetailForListEmbargo_Ajax.jsp
* Date				: 04/07/2018
* Author(s)			: A-6843
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>

 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 <html>
 <head>

 </head>
 <body>
	<business:sessionBean id="groupDetails" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="groupDetails"/>	
	<div id="ajax_groupingDetailsBodyForListEmbargo" class="ic-main-container" style="visibility:visible;position:static;">
		<bean:define id="groupNames" name="groupDetails"/>
		<%String groupValue = groupNames.toString();%>
		<%=groupValue.replace("[", "").replace("]", "")%>
	</div>
  </body>
</html>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>