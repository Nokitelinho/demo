<%--
* Project	 		: iCargo
* Module Code & Name: Stock control
* File Name			: DocumentRange_Ajax.jsp
* Date				: 13/06/2014
* Author(s)			: Nimmi Gopinath
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DocumentRangeForm" %>

<ihtml:form action="/stockcontrol.defaults.documentrange.do">
<bean:define id="form"
	 name="DocumentRangeForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DocumentRangeForm"
	 toScope="page" />
	<div id="documentRange"><bean:write name="form" property="documentRange"/></div>	
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>