<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mailtracking
 ***************************************************--%>
<html>
<head>



<title>iCargo:Upload Flush</title>
<meta name="decorator" content="mainpanel">
<script language="javascript">
	function postUploadFromApplet(){
		self.location=ctxPath+"/"+arguments[0];
		
	}
	</script>

</head>
<body>


<div class="iCargoContent" style="height: 80px;">
<div class="ic-content-main">
<div class="ic-head-container"><span
	class="ic-page-title ic-display-none"> <common:message
	key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
<div class="ic-main-container">
<center>
<H1><span class="iCargoLabel">Flushing data from
scanner......</span></H1>
</center>
<applet codebase="."
	code="com.ibsplc.icargo.framework.comm.batch.client.BatchTransferApplet.class"
	archive="icargo-upload-applet.jar" id=delete width=0 height=0 MAYSCRIPT>
	<PARAM name="delete" value=mailtracking>
</applet></div>
</div>
</div>



	</body>
</html>

