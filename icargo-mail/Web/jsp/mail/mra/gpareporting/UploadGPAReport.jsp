<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name: mra
* File Name			: UploadGPAReport.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2257

 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.UploadGPAReportForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>



	
	
<html:html locale="true">
<head>

	
		
	
	<title><common:message bundle="uploadgpareport" key="mailtracking.mra.gpareporting.uploadgrareport.title.uploadgrareport" />

	</title>
	<meta name="decorator" content="mainpanel">
	<common:include type="css" src="../css/icargo.css" />
	<common:include type="script" src="/js/mail/mra/gpareporting/UploadGPAReport_Script.jsp"/>


</head>
<body style="overflow:auto;">
	
	


  <!--CONTENT STARTS-->
 <div class="iCargoContent" style="overflow:auto;">

<ihtml:form action="/mailtracking.mra.gpareporting.screenloaduploadgpareport.do" enctype="multipart/form-data">
        <div class="ic-content-main">
	        <span class="ic-page-title ic-display-none">
			    <common:message key="mailtracking.mra.gpabilling.listcn51cn66.tabletitle" />
			</span>
			<div class="ic-main-container">
			<div class="ic-input-container">
			<div class="ic-input ic-split-35 ic-label-40">
			
				<label><common:message key="mailtracking.mra.gpareporting.uploadgrareport.sourcepath" /></label>
				<ihtml:file property="theFile" size="30" />
			</div>
			</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container">
				<ihtml:nbutton property="btnUpload"  componentID="CMP_MRA_UploadGPAReport_BTNUPLOAD" accesskey="U">
				<common:message key="mailtracking.mra.gpareporting.uploadgrareport.upload" />
			    </ihtml:nbutton>
			    <ihtml:nbutton property="btnClose" componentID="CMP_MRA_UploadGPAReport_BTNCLOSE" accesskey="O">
				<common:message key="mailtracking.mra.gpareporting.uploadgrareport.close" />
			    </ihtml:nbutton>
				</div>
			</div>
		</div>



</ihtml:form>
 </div>

<!---CONTENT ENDS---->
  
	
	</body>
</html:html>

