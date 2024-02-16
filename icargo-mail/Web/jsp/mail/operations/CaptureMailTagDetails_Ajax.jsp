<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
	
<bean:define id="form" name="MailAcceptanceForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   toScope="page" scope="request"/>
 
  
  
   <ihtml:form action="/mailtracking.defaults.mailacceptance.populatevolume.do" styleClass="ic-main-form">

<div id="_ajax_mailVol"><bean:write name="form" property="vol"/></div>


</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
