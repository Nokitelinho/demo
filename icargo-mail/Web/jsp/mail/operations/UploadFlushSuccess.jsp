<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mailtracking
* File Name			: UploadFlushSuccess.jsp
* Date				: 14-Sep-09
* Author(s)			: a-3817
 ***************************************************--%>

<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm"%>
		
	
	
<html:html>
<head> 



<bean:define id="form" name="UploadMailDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm"
	toScope="page" scope="request" />

<title><common:message bundle="uploadMailDetailsResources"
	key="mailtracking.defaults.batchmailupload.flushsuccesss.lbl.title" /></title>
<meta name="decorator" content="popup_panel">
<common:include type="script"
	src="/js/mail/operations/UploadMailDetails_Script.jsp" />
</head>

<body id="bodyStyle">
	

<div class="iCargoPopUpContent" id="pageDiv"
	style="width: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.batchmailupload.flushsuccess.ok.do">
	<ihtml:hidden property="inboundFlightCloseflag" />
	<div class="ic-content-main">
	<div class="ic-head-container"><span
		class="ic-page-title ic-display-none"> <common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
	<div class="ic-main-container">
	<div align="center"><br>
	<b>Data successfully flushed from the scanner</b></br>
	<ihtml:nbutton property="btFlushOk"
		componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_FLUSHSUCCESS_OK">
		<common:message
			key="mailtracking.defaults.uploadmaildetails.flushsuccess.btn.ok" />
	</ihtml:nbutton></div>

	</div>
	</div>
</ihtml:form></div>


	</body>
</html:html>
