<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
	
<bean:define id="form" name="MailArrivalForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
   toScope="page" scope="request"/>
 
  
  
   <ihtml:form action="/mailtracking.defaults.mailarrival.screenloadarrivemailpopup.do"  styleClass="ic-main-form">

<div id="_ajax_mailOOE"><bean:write name="form" property="originOE"/></div>
<div id="_ajax_mailDOE"><bean:write name="form" property="destinationOE"/></div>
<div id="_ajax_mailCat"><bean:write name="form" property="category"/></div>
<div id="_ajax_mailSC"><bean:write name="form" property="subClass"/></div>
<div id="_ajax_mailYr"><bean:write name="form" property="year"/></div>
<div id="_ajax_mailDSN"><bean:write name="form" property="dsn"/></div>
<div id="_ajax_mailRSN"><bean:write name="form" property="rsn"/></div>
<div id="_ajax_mailHNI"><bean:write name="form" property="hni"/></div>
<div id="_ajax_mailRI"><bean:write name="form" property="ri"/></div>
<div id="_ajax_mailWt"><bean:write name="form" property="wgt"/></div>
<div id="_ajax_mailVol"><bean:write name="form" property="vol"/></div>
<div id="_ajax_mailbagId"><bean:write name="form" property="mailId"/></div>
<div id="_ajax_inValidId"><bean:write name="form" property="inValidId"/></div>
 

</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
