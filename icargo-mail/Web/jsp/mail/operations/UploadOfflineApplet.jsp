<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  UploadOfflineApplet.jsp
* Date					:  13-Oct-2014
*************************************************************************/
 --%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"%>

	
	
<html:html>

<head>



<title>iCargo:Upload Applet</title>
<meta name="decorator" content="mainpanel">
<script language="javascript">
			function postUploadFromApplet(){
				var strUrl = ""+arguments[0];
				submitForm(targetFormName,strUrl);
			}
			function initiateUpload() {
				alert("Please initiate the upload from the HHT");
			}
	</script>
</head>

<body>


<bean:define id="form" name="UploadOfflineMailDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"
	toScope="page" scope="request" />

<div class="iCargoContent"
	style="width: 100%; height: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.offlineupload.screenload.do">
	<div class="ic-content-main">
	<div class="ic-head-container"><span
		class="ic-page-title ic-display-none"> <common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
	<div class="ic-main-container">
	<h1><span class="iCargoHeadingLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;iCargo
	: Mail upload</span></h1>
	<jsp:include page="/jsp/includes/uploadapplet/UploadApplet.jsp" /> <br>
	<span class="iCargoLabel"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;uploading
	data...</b></span> </br>
	</div>
	</div>
</ihtml:form></div>



	</body>
</html:html>
