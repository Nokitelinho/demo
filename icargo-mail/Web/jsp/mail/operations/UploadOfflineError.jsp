<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mailtracking
* File Name			: UploadOfflineError.jsp
* Date				: 21-Oct-14
 ***************************************************--%>

<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"%>


	
<html:html>
<head>



<bean:define id="form" name="UploadOfflineMailDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"
	toScope="page" scope="request" />

<title><common:message bundle="uploadMailDetailsResources"
	key="mailtracking.defaults.uploadofflinemaildetails.lbl.errortitle" /></title>
<meta name="decorator" content="mainpanel">
</head>

<body id="bodyStyle">



<div class="iCargoContent" id="pageDiv"
	style="width: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.offlineupload.errorupload.do">
	<div class="ic-content-main">
	<div class="ic-head-container"><span
		class="ic-page-title ic-display-none"> <common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
	<div class="ic-main-container">
	<div align="center"><br>
	<b>Upload Failed.Please Retry.</b></br>
	<br>
	The following conditions may have occured</br>
	<br>
	1. Data not available</br>
	<br>
	2. Scanner not connected</br>
	<br>
	3. PC not configured for scanner upload</br>
	</div>
	</div>
	</div>
</ihtml:form></div>



	</body>
</html:html>
