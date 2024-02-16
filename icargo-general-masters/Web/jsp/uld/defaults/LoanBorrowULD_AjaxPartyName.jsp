<%--
* Project	 		: iCargo
* Module Code & Name: uld-defaults
* File Name			: LoanBorrowULD.jsp
* Date				: 28-Jan-2008
* Author(s)			: A-2408
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm" %>



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
      name="maintainULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm"
      toScope="page" />


<ihtml:form action="/uld.defaults.transaction.screenloadloanborrowuld.do">
<ihtml:hidden name="form" property="errorStatusFlag" />
	<div id="partyDiv">
	<common:write name="form" property="partyName"/>
	</div>

</ihtml:form>
		<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

