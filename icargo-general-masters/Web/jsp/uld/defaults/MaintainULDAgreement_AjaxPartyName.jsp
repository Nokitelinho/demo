<%--
* Project	 		: iCargo
* Module Code & Name		: IN - ULD Management
* File Name			: MaintainULDAgreement_AjaxPartyName.jsp
* Date				: 13-Nov-2008
* Author(s)			: A-3278
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm" %>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
	 name="maintainULDAgreementForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm"
	 toScope="page" />

<ihtml:form action="/uld.defaults.screenloaduldagreement.do">
<ihtml:hidden name="form" property="errorStatusFlag" />
	<div id="partyDiv">
	<common:write name="form" property="agrPartyName"/>
	</div>
</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

