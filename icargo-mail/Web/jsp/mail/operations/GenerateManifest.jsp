
<%--
 /***********************************************************************
* Project	 		:  iCargo
* Module Code & Name		:  Mailtracking
* File Name			:  GenerateManifest.jsp
* Date				:  20-Jan-2007
* Author(s)			:  A-1876
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"%>
 	
	 <html:html>
 <head> 
		
			
	
 	<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.generatemanifest.lbl.title" /></title>
	<!--Modified by A-7938 for ICRD-243746-->
 	<meta name="decorator" content="popuppanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/GenerateManifest_Script.jsp"/>
 
 </head>

 <body id="bodyStyle">
	
 <%@include file="/jsp/includes/reports/printFrame.jsp" %>
  	<bean:define id="MailManifestForm" name="MailManifestForm"
	   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
	   toScope="page" scope="request"/>

	<business:sessionBean id="printTypesSession" 
                 moduleName="mail.operations" 
                 screenID="mailtracking.defaults.mailmanifest" 
                 method="get" attribute="printTypes" />
	
	<div class="iCargoPopUpContent">
	<ihtml:form action="/mailtracking.defaults.mailmanifest.generatemailmanifest.do" styleClass="ic-main-form">

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.mailmanifest.generatemanifest.lbl.pagetitle" />
		</span>
		<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<div class="ic-input ic-split-100">
						<common:message key="mailtracking.defaults.mailmanifest.generatemanifest.lbl.layout" />
						<ihtml:select property="printType" componentID="CMB_MAILTRACKING_DEFAULTS_GENERATEMANIFEST_LAYOUT">
						<bean:define id="printTypesSess" name="printTypesSession" toScope="page" />
						<logic:iterate id="printTypes" name="printTypesSess" >
							 <html:option value="<%=(String)printTypes %>"><%=(String)printTypes %></html:option>				          
						</logic:iterate>
						</ihtml:select>
					</div>
				</div>					
			</div>						
		</div>							
		</div>							
		<div class="ic-foot-container">
			<div class="ic-button-container">	

				<ihtml:nbutton property="btnPrint" componentID="BTN_MAILTRACKING_DEFAULTS_GENERATEMANIFEST_PRINT" styleClass="btn-inline btn-secondary">
					<common:message key="mailtracking.defaults.mailmanifest.generatemanifest.btn.print" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnCancel" componentID="BTN_MAILTRACKING_DEFAULTS_GENERATEMANIFEST_CANCEL" styleClass="btn-inline btn-secondary">
					<common:message key="mailtracking.defaults.mailmanifest.generatemanifest.btn.cancel" />
				</ihtml:nbutton>
			</div>
		</div>	
	</div>	
		</ihtml:form>
	</div>

 			
		  
	</body>

 </html:html>
