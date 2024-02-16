
<%-- *************************************************
* Project	 		: iCargo
* Module Code & Name: CUSTOMER MANAGEMENT
* File Name			: MaintainCustomer_AjaxProduct.jsp
* Date				: 11-03-2010
* Author(s)			: Devaprasanth

****************************************************** --%> 
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<bean:define id="form"
	name="MaintainCustomerRegistrationForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	toScope="page" />
<business:sessionBean 
	id="customerVO"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" 
	attribute="customerVO"/>
	<ihtml:form  action="customermanagement.defaults.screenloadcustomerregistration.do">		
		<div id="productparentDiv">
			<ihtml:hidden name="form" property="ajaxErrorStatusFlag"/>	
			<ihtml:hidden name="form" property="state"/>	
			<ihtml:hidden name="form" property="country"/>
			<ihtml:hidden name="form" property="city"/>
		</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>