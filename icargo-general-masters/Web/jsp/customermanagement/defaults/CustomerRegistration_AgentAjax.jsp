<%--
* Project	 		: iCargo
* Module Code & Name: CustomerManagement
* File Name			: CustomerRegistration_AgentAjax.jsp
* Date				: 31/03/2014
* Author(s)			: A-5791
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="customermanagement.defaults.screenloadcustomerregistration.do">
    <bean:define id="form"  name="MaintainCustomerRegistrationForm" type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"  toScope="page" /> 
	
		<div id="_ajax_agentDescription">
		    <bean:write name="form" property="customerAgentName"/>
		</div>
		
		
</ihtml:form>		    

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>