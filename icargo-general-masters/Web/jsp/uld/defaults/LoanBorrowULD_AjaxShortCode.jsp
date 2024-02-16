<%--
* Project	 		: iCargo
* Module Code & Name: uld-defaults
* File Name			: LoanBorrowULD_AjaxShortCode.jsp
* Date				: 22-Sep-2010
* Author(s)			: A-1883
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm" %>



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
      name="maintainULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm"
      toScope="page" />


<ihtml:form action="/uld.defaults.transaction.screenloadloanborrowuld.do">
<ihtml:hidden name="form" property="errorStatusFlag" />
	<div id="ajaxToShortCodeDiv">
		<common:write name="form" property="toShortCode"/>
	</div>
	<div id="ajaxToPartyCodeDiv">
		<common:write name="form" property="toPartyCode"/>
	</div>
	<div id="ajaxToPartyNameDiv">
		<common:write name="form" property="toPartyName"/>
	</div>
	<div id="ajaxFromShortCodeDiv">
			<common:write name="form" property="fromShortCode"/>
	</div>
	<div id="ajaxFromPartyCodeDiv">
		<common:write name="form" property="fromPartyCode"/>
	</div>
	<div id="ajaxFromPartyNameDiv">
		<common:write name="form" property="fromPartyName"/>
	</div>

</ihtml:form>
		<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

