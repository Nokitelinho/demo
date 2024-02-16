<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: Generate Invoice.jsp
* Date				: 25-Aug-2008
* Author(s)			: A-3353
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm"%>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<bean:define id="form"
			 name="generateTransactionInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm"
		 toScope="page" />

<ihtml:form action="/uld.defaults.generatetransactioninvoicescreenload.do">

<ihtml:hidden name="form" property="invoiceFlag" />
	<div id="partyNameDiv">
	
		<ihtml:text property="name" maxlength="50" componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_INVOICETONAME"  />
	</div>
	

</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

