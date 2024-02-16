<%--
* Project	 		: iCargo
* Module Code & Name: mra airlinebilling
* File Name			: SupportingDocumentsAttachment.jsp
* Date				: 29-10-2018
* Author(s)			: A-8061
 --%>

 <%@ page language="java" %>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.MRASisSupportingDocumentDetailsForm" %>
 <%@ include file="/jsp/includes/tlds.jsp" %>

	 <html:html>
 
<head> 

<title><common:message bundle="rejectionmemobundle" key="mra.airlinebilling.rejectionmemo.pagetitle"/></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/SupportingDocumentsAttachment_Script.jsp"/>
</head>

<body>
	
	

<div class="iCargoPopupContent" style="width:100%;height:94%">
<ihtml:form action="/mail.mra.airlinebilling.inward.loadsupportingdocspopup.do" method="post" enctype="multipart/form-data" styleClass="ic-main-form">

<html:hidden property="actionStatus" />
<html:hidden property="fromScreen" />
<html:hidden property="selectedRow" />
<html:hidden property="billingType" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message key="cra.sisbilling.defaults.supportingdocspopup.pagetitle"/>
	</span>
	<div class="ic-main-container">
					<div class="ic-row" style="margin-top:30px;">
						<div class="ic-input-container ic-input-round-border">
							<div class="ic-input ic-split-100 ic-label-15">
				<label>
					<common:message key="mra.airlinebilling.rejectionmemo.attachment" scope="request"/>
				</label>
				<ihtml:file property="fileData" size="60" styleClass="iCargoTextFieldVeryLong" title="File Name"/>
			</div>	
		</div>
	</div>
				</div>
	<div class="ic-foot-container">
		<div class="ic-button-container" >
		<ihtml:nbutton componentID="CMP_SIS_SUPPORTINGDOC_ATTACH_BUTTON" property="btAttach" >
			<common:message key="mra.airlinebilling.rejectionmemo.btn.Attach" scope="request" />
		</ihtml:nbutton>
			
		<ihtml:nbutton  componentID="CMP_SIS_SUPPORTINGDOC_CANCEL_BUTTON" property="btCancel" >
			<common:message key="mra.airlinebilling.rejectionmemo.btn.Cancel" scope="request" />
		</ihtml:nbutton>
	</div>
</div>
</div>
</ihtml:form>
</div>	  			
		   
				
		
	</body>

</html:html>
