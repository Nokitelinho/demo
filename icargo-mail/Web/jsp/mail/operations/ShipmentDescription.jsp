
<%--
 /***********************************************************************
* Project	 				:  iCargo
* Module Code & Name				:  Mailtracking
* File Name					:  ShipmentDescription.jsp
* Date						:  07-APR-2009
* Author(s)					:  A-3227
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"%>


	
	 <html:html>
 <head> 
		
	
	
 	<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.shipmentDecription.lbl.title" /></title>
 	<meta name="decorator" content="popuppanelrestyledui"> 
	
 	<common:include type="script" src="/js/mail/operations/ShipmentDescription_Script.jsp"/>
 
 </head>

 <body id="bodyStyle">
	
	
 	<bean:define id="MailManifestForm" name="MailManifestForm"
	   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
	   toScope="page" scope="request"/>

	<business:sessionBean id="shipmentDescriptionSession" 
                 moduleName="mail.operations" 
                 screenID="mailtracking.defaults.mailmanifest" 
                 method="get" attribute="shipmentDescription" />
	
	<div class="iCargoPopUpContent" >
	<ihtml:form action="mailtracking.defaults.mailmanifest.autoattach.screenload.do" styleClass="ic-main-form">
		<ihtml:hidden property="parentContainer" />
		<ihtml:hidden property="selectChild" />
		<ihtml:hidden property="autoAttach" />
		
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.mailmanifest.shipmentDecription.lbl.pagetitle" />
		</span>
		<div class="ic-head-container">
		<!--Modified by A-7938 for ICRD-243746-->
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<div class="ic-input ic-split-100">
						<common:message key="mailtracking.defaults.mailmanifest.shipmentDecription.lbl.layout" />
							<ihtml:select property="shipmentDesc"   componentID="CMB_MAILTRACKING_DEFAULTS_SHIPMENTDESCRIPTION_LAYOUT">
								<logic:present name="shipmentDescriptionSession">
									<bean:define id="shipmentDescSess" name="shipmentDescriptionSession" toScope="page"/>
									<logic:iterate id="oneTimeVO" name="shipmentDescSess">
										<bean:define id="fieldValue" name="oneTimeVO" property="fieldValue" toScope="page" />
										<bean:define id="fieldDesc" name="oneTimeVO" property="fieldDescription" toScope="page" />
										<html:option value="<%=(String)fieldValue %>"><%=(String)fieldDesc %></html:option>
									</logic:iterate>
								</logic:present>						
							</ihtml:select>					
					</div>
				</div>					
			</div>						
		</div>							
		</div>							
		<div class="ic-foot-container">
			<div class="ic-button-container">		
				<ihtml:nbutton property="btOk" componentID="CMB_MAILTRACKING_DEFAULTS_SHIPMENTDESCRIPTION_ATTACH">
					<common:message key="mailtracking.defaults.mailmanifest.shipmentDecription.btn.autoattach" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnCancel" componentID="CMB_MAILTRACKING_DEFAULTS_SHIPMENTDESCRIPTION_CANCEL">
					<common:message key="mailtracking.defaults.mailmanifest.shipmentDecription.btn.cancel" />
				</ihtml:nbutton>
			</div>
		</div>
					
    </div>				
		</ihtml:form>
</div>

 			
		  
	</body>

 </html:html>
