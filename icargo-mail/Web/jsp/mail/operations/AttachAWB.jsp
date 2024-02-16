<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: AttachAWB.jsp
* Date				: 18-Aug-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>


<html:html>

<head> 
		
			
	
	<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.attachawb.lbl.pagetitle" /></title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/mail/operations/AttachAwb_Script.jsp" />
</head>

<body>
	
	

<div class="iCargoPopUpContent" >
<ihtml:form action="/mailtracking.defaults.attachawb.screenload.do" styleClass="ic-main-form" >

<bean:define id="form"
	name="MailManifestForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
    toScope="page"
    scope="request"/>

<business:sessionBean id="systemParameters"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.mailmanifest"
					  method="get"
					  attribute="systemParameters" />

<business:sessionBean id="weightCodes"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.mailmanifest"
					  method="get"
					  attribute="weightCodes" />

<business:sessionBean id="agentCode"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.mailmanifest"
					  method="get"
					  attribute="agentCode" />

<ihtml:hidden property="screenStatus" />
<ihtml:hidden property="density" />
<ihtml:hidden property="fromScreen" /><!--added by a-7871 for ICRD-262855-->

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.attachawb.lbl.title" />
		</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
                <div class="ic-input-container">
					<div class="ic-section ic-border">
						<div class="ic-col-70">
							<div class="ic-row ">
								<div class="ic-input ic-split-50">
									<!--Modified by A-7938 for ICRD-243746-->
									<!--<common:message key="mailtracking.defaults.attachawb.lbl.awb" />-->
									<ibusiness:awb id="awbNumber" awpProperty="shipmentPrefix"  awbProperty="documentNumber"
								   awpValue="<%= form.getShipmentPrefix() %>" awbValue="<%= form.getDocumentNumber() %>"
								   componentID="CMP_MailTracking_Defaults_AttachAwb_AWB" isCheckDigitMod="false" tabindex="1"/>
								</div>
								<div class="ic-input ic-split-50">
									<common:message key="mailtracking.defaults.attachawb.lbl.agentcode" />
									<logic:present name="agentCode">
									<bean:define id="agentCode" name="agentCode" />
									<ihtml:text property="agentCode" maxlength="5" value="<%=(String)agentCode%>" readonly="true" componentID="CMP_MailTracking_Defaults_AttachAwb_AgentCode" />
									</logic:present>

								</div>
							</div>
						</div>	
						<div class="ic-col-30">
							<ihtml:nbutton property="btList" accesskey="l" tabindex="2" componentID="CMP_MailTracking_Defaults_AttachAwb_btList">
									<common:message key="mailtracking.defaults.attachawb.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear" componentID="CMP_MailTracking_Defaults_AttachAwb_btClear" tabindex="4">
									<common:message key="mailtracking.defaults.attachawb.btn.clear" />
							</ihtml:nbutton>
							</div>
						</div>
					</div>
					</div>	
				</div>
			</div>	
		<div class="ic-main-container">	
			<div class="ic-row ic-label-30">
				<div class="ic-input ic-split-50">
				  <label> <common:message key="mailtracking.defaults.attachawb.lbl.origin" /></label>
						<ihtml:text property="origin" maxlength="4" readonly="true" componentID="CMP_MailTracking_Defaults_AttachAwb_Origin" />
				</div>
				<div class="ic-input ic-split-50">
					  <label> <common:message key="mailtracking.defaults.attachawb.lbl.destination" /></label>
							<ihtml:text property="destination" maxlength="4" readonly="true" componentID="CMP_MailTracking_Defaults_AttachAwb_Destination" />
				</div>
			</div>		
			<div class="ic-row ic-label-30">
				<div class="ic-input ic-split-50">
				  <label> <common:message key="mailtracking.defaults.attachawb.lbl.stdpcs" /></label>
						 <ihtml:text property="stdPieces" tabindex="4" componentID="CMP_MailTracking_Defaults_AttachAwb_StdPcs" maxlength="5"/>
				</div>
				<div class="ic-input ic-split-50">
					  <label> <common:message key="mailtracking.defaults.attachawb.lbl.stdwt" /></label>
							 <ihtml:text property="stdWeight" tabindex="5" componentID="CMP_MailTracking_Defaults_AttachAwb_StdWt" maxlength="9"/>

							<ihtml:select property="weightStandard" tabindex="6" componentID="CMP_MailTracking_Defaults_AttachAwb_WtStd">
							  <logic:present name="weightCodes">
								<bean:define id="weightcodes" name="weightCodes" toScope="page"/>

									<logic:iterate id="onetmvo" name="weightcodes">
										<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
										<bean:define id="value" name="onetimevo" property="fieldValue"/>
										<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
										<html:option value="<%=(String)value %>"><bean:write name="onetimevo" property="fieldDescription"/></html:option>

									</logic:iterate>
								</logic:present>
							</ihtml:select>
				</div>
			</div>				
			<div class="ic-row ">
				<div class="ic-input ic-split-100 ">
				  <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<common:message key="mailtracking.defaults.attachawb.lbl.desc" /></label>
					<ihtml:text property="shipmentDesc" tabindex="7" componentID="CMP_MailTracking_Defaults_AttachAwb_ShipmntDesc" />
				</div>
				
			</div>				
		</div>			
		<div class="ic-foot-container">
			<div class="ic-button-container">				
					<ihtml:nbutton property="btSave" tabindex="8" componentID="CMP_MailTracking_Defaults_AttachAwb_btSave">
						<common:message key="mailtracking.defaults.attachawb.btn.save" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btClose" tabindex="9" componentID="CMP_MailTracking_Defaults_AttachAwb_btClose">
						<common:message key="mailtracking.defaults.attachawb.btn.close" />
					</ihtml:nbutton>

			</div>
		</div>
	</div>

</ihtml:form>
</div>
			
		  
	</body>

</html:html>
