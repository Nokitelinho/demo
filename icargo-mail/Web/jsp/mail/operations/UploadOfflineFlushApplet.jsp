<%--****************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  UploadOfflineFlushApplet.jsp
* Date					:  13-Oct-2014
*************************************************************************/
 --%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"%>


<html:html>

<head>



<title>iCargo:Upload Flush</title>
<meta name="decorator" content="mainpanel">
<script language="javascript">
	function postUploadFromApplet(){
				var strUrl = ""+arguments[0];
				submitForm(targetFormName,strUrl);
	}
	</script>

</head>

<body>



<bean:define id="form" name="UploadOfflineMailDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"
	toScope="page" scope="request" />

<div class="iCargoContent"
	style="width: 100%; height: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.offlineupload.flush.do">
	<div class="ic-content-main">
	<div class="ic-head-container"><span
		class="ic-page-title ic-display-none"> <common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
	<div class="ic-main-container">
	<h1><span class="iCargoLabel">Flushing data from
	scanner......</span></h1>
	<applet codebase="."
		code="com.ibsplc.icargo.framework.comm.batch.client.BatchTransferApplet.class"
		archive="icargo-upload-applet.jar" id=delete width=0 height=0
		MAYSCRIPT>
		<PARAM name="delete" value="true">
	</applet> <br>

	</br>
	</div>
	</div>
</ihtml:form></div>



	</body>
</html:html>
