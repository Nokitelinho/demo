<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
	
<bean:define id="form" name="ConsignmentForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm"
   toScope="page" scope="request"/>
 
  
  
   <ihtml:form action="/mailtracking.defaults.consignment.screenloadconsignment.do" styleClass="ic-main-form">

<div id="_ajax_mailOOE"><bean:write name="form" property="mailOOE"/></div>
<div id="_ajax_mailDOE"><bean:write name="form" property="mailDOE"/></div>
<div id="_ajax_mailCat"><bean:write name="form" property="mailCat"/></div>
<div id="_ajax_mailClass"><bean:write name="form" property="mailClassType"/></div>
<div id="_ajax_mailSC"><bean:write name="form" property="mailSC"/></div>
<div id="_ajax_mailYr"><bean:write name="form" property="mailYr"/></div>
<div id="_ajax_mailDSN"><bean:write name="form" property="mailDSN"/></div>
<div id="_ajax_mailRSN"><bean:write name="form" property="mailRSN"/></div>
<div id="_ajax_mailHNI"><bean:write name="form" property="hni"/></div>
<div id="_ajax_mailRI"><bean:write name="form" property="ri"/></div>
<div id="_ajax_mailWt"><bean:write name="form" property="mailWt"/></div>
<div id="_ajax_mailbagId"><bean:write name="form" property="mailId"/></div>
 

</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
