<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: CapturULDDetails_Ajax.jsp
* Date				: 13/03/2018
* Author(s)			: A-8176
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
  
  <%@ page import = "java.util.Calendar" %>
  <%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
  <%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>  
  
  <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
  
	<ihtml:form action="uld.defaults.misc.updatemultipleulddamage.do">
  
		<bean:define id="form"  name="UpdateMultipleULDDetailsForm"  
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"  toScope="page" /> 
		<div id="_ajax_operationalstatus"><bean:write name="form" property="newOperationalStatus"/></div>
		<div id="_ajax_damagedstatus"><bean:write name="form" property="newDamagedStatus"/></div>


	</ihtml:form>		    

   <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
             

