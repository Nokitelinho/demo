<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mailtracking
* File Name			: UploadMailDetails.jsp
* Date				: 14-Sep-09
* Author(s)			: Lijash M
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
	key="mailtracking.defaults.uploaderror.lbl.title" /></title>
<meta name="decorator" content="mainpanel">
</head>

<body id="bodyStyle">



<div class="iCargoContent" id="pageDiv"
	style="width: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.errorupload.do">
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
	3. PC not confgiured for scanner upload</br>
	</div>
	</div>
	</div>
</ihtml:form></div>



	</body>
</html:html>
