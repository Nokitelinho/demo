<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - CUSTOMER MANAGEMENT
* File Name				:  UploadTSA.jsp
* Date					:  10-Jun-2015
* Author(s)				:  A-5290
*************************************************************************/
 --%>
 
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.UploadTSAForm"%>

<html:html locale="true">
<head>	
	
	<title>iCargo : Upload TSA File</title>
	<meta name="decorator" content="popuppanelrestyledui" >
	<common:include type="script" src="/js/customermanagement/defaults/UploadTSA_Script.jsp"/>
</head> 
<body>
	
	
<bean:define id="form"
	 name="UploadTSAForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.UploadTSAForm"
	 toScope="page" />
<business:sessionBean id="fileTypes"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.customerlisting"
			method="get" attribute="TSAFiletype"/>
			
<div class="iCargoPopUpContent" style="overflow:auto;height:100%" >
	<ihtml:form enctype="multipart/form-data" action="/customermanagement.defaults.customerlisting.uploadscreenloadcommand.do"
		styleClass="ic-main-form">
	
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="customermanagement.defaults.listcustomer.tsa.lbl.loadfromfile" scope="request"/>
		</span>	
		<div class="ic-main-container">
			<div class="ic-section ic-pad-3">
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-100 ic-label-15">
						<label>
							<common:message key="customermanagement.defaults.listcustomer.tsa.lbl.filetype" scope="request"/>
						</label>
						<ihtml:select property="fileType" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_TSA_FILETYPE">
							<logic:present name="fileTypes">
								<bean:define id="types" name="fileTypes"/>
								<ihtml:options collection="types" property="fieldValue" labelProperty="fieldDescription"/>
							</logic:present>
						</ihtml:select>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-100 ic-label-15">
						<label>
							<common:message key="customermanagement.defaults.listcustomer.tsa.lbl.filename" scope="request"/>
						</label>
						<html:file property="selectedFile" styleClass="iCargoTextFieldExtraLong" />
					</div>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
					<ihtml:nbutton componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_TSA_LOAD" property="btnLoad"  >
						<common:message key="customermanagement.defaults.listcustomer.tsa.btn.load"  />
					</ihtml:nbutton>
					<ihtml:nbutton componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_TSA_CLOSE" property="btnClose" >
						<common:message key="customermanagement.defaults.listcustomer.tsa.btn.close"  />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
	</ihtml:form>
</div>
	

	</body>
</html:html>
