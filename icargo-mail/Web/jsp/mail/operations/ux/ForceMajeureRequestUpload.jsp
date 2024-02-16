<%@ include file="/jsp/includes/tlds.jsp"%>


		
		
	
<html:html>

<head>
		
		<jsp:include page="/jsp/includes/ux/includes.jsp">		
			<jsp:param name="pageInfo" value="<%= ((Servlet)page).getServletInfo()%>"/>
		</jsp:include>	
	
<title><common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.uploadpagetitle" /></title>

	<meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/operations/ux/ForceMajeureRequestUpload_Script.jsp"/>

</head>
<body>

	<bean:define id="form" name="ForceMajeureRequestForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm" toScope="page" />
	 
	<div title="Force Majeure Upload" class="poppane lg" id="formjr_upl">
	<ihtml:form enctype="multipart/form-data" action="/mail.operations.ux.forcemajeure.screenloadupload.do" styleClass="ic-main-form">
	<ihtml:hidden property="uploadStatus"/>
	<div class="pad-md pad-b-3xs" style="height:200px">
	 <div class="row">
	            <div class="col-5 form-group">
                    <label class="form-control-label"><common:message key="mail.operations.ux.forcemajeure.uploadfiletype" /></label>
					<ihtml:select property="fileType" componentID="CMP_Mail_Operations_ForceMajeure_FileType" styleClass="form-control">
						<html:option value=""><common:message key="combo.select"/></html:option>
								<%String fieldValue = "MALFORMJRREQUPL";%>
								<%String fieldDescription = "Mail Force Majeure Request";%>
							<html:option value="<%=fieldValue%>"><%=fieldDescription%></html:option>							
					</ihtml:select>
                </div>
				<div class="col-5 form-group">
				</div>
					<div class="col-10 form-group">
						<label class="form-control-label"><common:message key="mail.operations.ux.forcemajeure.filename" /></label>
							<html:file property="file" styleClass="iCargoTextFieldExtraLong" title="Select a file" /> 
					</div>	
	</div>
				<footer>
					<ihtml:nbutton id="btnUpload" property="btnUpload" styleClass="btn btn-primary" componentID="BUT_Mail_Operations_ForceMajeure_Upload" accesskey="O">
						<common:message key="mail.operations.ux.forcemajeure.btn.upload"/>
					</ihtml:nbutton>
					<ihtml:nbutton id="btnClosePopUp" property="btnClosePopUp" styleClass="btn btn-primary" componentID="BUT_Mail_Operations_ForceMajeure_Upload_Close" accesskey="C">
						<common:message key="mail.operations.ux.forcemajeure.btn.close"/>
					</ihtml:nbutton>
				</footer>
	 </div>
	
	</ihtml:form>
</div>
	</body>
</html:html>
