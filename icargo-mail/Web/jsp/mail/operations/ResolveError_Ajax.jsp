<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page language="java" %>
 
					



<ihtml:form action="admin.monitoring.openresendorreprocesspopup.do">	



<bean:define id="form" name="ErrorHandlingPopUpForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm" toScope="page" />



<div id="RelistStatus" >
					
Resolved
				
</div>
<div id="_ajax_mailOOE"><bean:write name="form" property="ooe"/></div>
<div id="_ajax_mailDOE"><bean:write name="form" property="doe"/></div>
<div id="_ajax_mailCat"><bean:write name="form" property="category"/></div>
<div id="_ajax_mailSC"><bean:write name="form" property="subclass"/></div>
<div id="_ajax_mailYr"><bean:write name="form" property="year"/></div>
<div id="_ajax_mailDSN"><bean:write name="form" property="dsn"/></div>
<div id="_ajax_mailRSN"><bean:write name="form" property="rsn"/></div>
<div id="_ajax_mailHNI"><bean:write name="form" property="hni"/></div>
<div id="_ajax_mailRI"><bean:write name="form" property="ri"/></div>
<div id="_ajax_mailWt"><bean:write name="form" property="weight"/></div>
<div id="_ajax_mailbagId"><bean:write name="form" property="mailBag"/></div>
<div id="_ajax_inValidId"><bean:write name="form" property="invalidMailbagId"/></div>
</ihtml:form>	
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>