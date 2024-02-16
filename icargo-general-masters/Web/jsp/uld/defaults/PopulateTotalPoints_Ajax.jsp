<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: PopulateTotalPointss_Ajax.jsp
* Date				: 13/03/2018
* Author(s)			: A-8176
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

  
  <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
  
	<ihtml:form action="uld.defaults.misc.showDamageDetails.do">
  
		<bean:define id="form"  name="UpdateMultipleULDDetailsForm"  
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"  toScope="page" /> 
		<div id="_ajax_totalpoints"><bean:write name="form" property="totalPoints"/></div>
		


	</ihtml:form>		    

   <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
             

