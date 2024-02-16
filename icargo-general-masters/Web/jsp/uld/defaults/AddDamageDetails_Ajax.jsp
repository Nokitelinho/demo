<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: AddDamageDetails_Ajax.jsp
* Date				: 14-Oct-2008
* Author(s)			: A-3154
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

 <bean:define id="form"
 		name="maintainDamageReportForm"
 		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
 		toScope="page" />
		
	<ihtml:form action="/uld.defaults.populatePartyName.do"  >
	<ihtml:hidden name="form" property="ajaxErrorStatusFlag" />
	<div id="partyName">
		<common:write name="form" property="ajaxPartyName"/>
	</div>

    </ihtml:form>
		<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

