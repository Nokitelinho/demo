<%--
*******************************************************************************************
 * This JSP file handles the uploading of data from the scanner after the user has logged
 * on to the system. This page appears while uploading is taking place. After successful
 * uploading of data from the scanner, the Upload Data screen appears
 * @author Kiran S R
 * @version 0.1, 22/09/06
*******************************************************************************************
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>

	
<html:html>
<head>
<meta name="decorator" content="mainpanelrestyledui">

<title>iCargo:Upload Applet</title>
<meta name="decorator" content="mainpanel">
<script language="javascript">
	function postUploadFromApplet(){
		submitForm(targetFormName,arguments[0]);
		
	}
	function initiateUpload() {
		alert("Please initiate the upload from the HHT");
	}
	</script>
</head>
<body>



<div class="iCargoContent" style="height: 580px;">
<div class="ic-content-main">
<div class="ic-head-container"><span
	class="ic-page-title ic-display-none"> </span></div>
<div class="ic-main-container" style="overflow-y: hidden;">
<h1><span class="iCargoLabel">&nbsp;iCargo : Mail upload</span></h1>
<applet codebase="<%=request.getContextPath ()%>"
	code="com.ibsplc.icargo.framework.comm.batch.client.BatchTransferApplet.class"
	archive="icargo-upload-applet.jar" width=0 height=0 MAYSCRIPT>
	<hr>
	Please install the Java Plug In.
	<hr>
</applet> <br>
<span class="iCargoLabel">&nbsp;uploading data...</span> </br>
<ihtml:form action="/mailtracking.defaults.upload.screenload.do" styleClass="ic-main-form">
</ihtml:form>
</div>
</div>
</div>



	</body>
</html:html>
