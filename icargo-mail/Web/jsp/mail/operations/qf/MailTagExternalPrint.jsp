<%--****************************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Operations
* File Name			: MailTagExternalPrint.jsp
* Date				: 09-AUG-2023
* Author(s)			: A-9090
 ***************************************************--%>
 


<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>

	
<html:html>
	<head>
		
		<title>
			MailTagExternalPrint
		</title>
		<meta name="decorator" content="popup_panel">	
		<common:include type="script" src="/js/mail/operations/qf/MailTagExternalPrint_Script.jsp" />
		
	</head>
	<body>
		<logic:present name="popup">
			<div id="walkthroughholder"/>
		</logic:present>
		<bean:define id="form" name="MailTagExternalPrintForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTagExternalPrintForm" toScope="page" />
		<div id="mainDiv" class="iCargoPopUpContent" style="overflow:auto; width:100%;height:100%">	
		<ihtml:form action="/mailtracking.defaults.printmailtag.externalprinttag.do" style="display:none;">


		<ihtml:hidden property="redirectURL"/>

		<ihtml:hidden property="screenMode"/>

		<ihtml:hidden property="mailTagLabelDetails"/>
					
		</ihtml:form>
		</div>
			
		
	</body>
</html:html>