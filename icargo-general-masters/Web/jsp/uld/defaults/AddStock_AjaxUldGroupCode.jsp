<%--
* Project	 		: iCargo
* Module Code & Name: uld-defaults
* File Name			: AddStock.jsp
* Date				: 01-Apr-08
* Author(s)			: A-2412
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm" %>



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
		name="maintainULDStockForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm"
		toScope="page" />

<ihtml:form action="/uld.defaults.screenloadcreateuldsetupstock.do">

	<div id="uldGroupCodeDiv">
		<!--<common:write name="form" property="uldGroupCode"/>-->
		<label><common:message bundle="maintainuldstock" key="uld.defaults.uldGroupCode" />	</label>
		<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_ULDGRP_DTL" property="uldGroupCode" name="maintainULDStockForm" maxlength="6" disabled="true" />
	</div>

</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

