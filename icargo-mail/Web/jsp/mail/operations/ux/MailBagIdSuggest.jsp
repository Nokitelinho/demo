
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  mail.operations
* File Name				:  MailBagIdSuggest.jsp
* Date					:  14-Sep-2018
* Author(s)				:  A-8164
*************************************************************************/ --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
	<ihtml:form action="mail.operations.ux.mbHistory.displaymailbagidsuggest.do">	
	 <business:sessionBean
			id="KEY_MAILBAGIDS"
			moduleName="mail.operations"
			screenID="mail.operations.ux.mailbaghistory"
			method="get"
			attribute="mailBagVos" />    
	<bean:define id="form"
			name="MailBagHistoryUxForm" 
			type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm" 
			toScope="page" />		
		<div id="_ajaxMailbagIds">
			<logic:present name="KEY_MAILBAGIDS">
				<logic:iterate id="mailbagVO" name="KEY_MAILBAGIDS">
				<bean:write name="mailbagVO" property="mailbagId"/>
				   <div mailbagId='<bean:write name="mailbagVO" property="mailbagId"/>'> </div>   	   
				 </logic:iterate>
			 </logic:present>	
		</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>