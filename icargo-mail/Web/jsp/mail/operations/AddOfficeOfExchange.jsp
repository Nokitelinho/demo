
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  AddOfficeOfExchange.jsp
* Date					:  23-Aug-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO"%>
	
	 <html:html>

 <head> 
		<title><common:message bundle="officeOfExchangeResources" key="mailtracking.defaults.addoe.lbl.title" /></title>
		<!--modified by A-7938 for ICRD-244170-->
		<meta name="decorator" content="popuppanelrestyledui">
		<common:include type="script" src="/js/mail/operations/AddOfficeOfExchange_Script.jsp"/>
 </head>

 <body>
	

  	<bean:define id="form"
  		 name="OfficeOfExchangeMasterForm"
  		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm"
		 toScope="page" />

	<business:sessionBean id="officeOfExchangeVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.officeofexchange"
		 method="get"
	 	 attribute="officeOfExchangeVO"/>



	<div class="iCargoPopUpContent">
		<ihtml:form action="/mailtracking.defaults.addoe.screenload.do" styleClass="ic-main-form" >
			<ihtml:hidden property="popUpStatus"/>
			<ihtml:hidden property="status"/>
			<ihtml:hidden property="ooeInfo"/>
			<!--Added by A-8527 for bug IASCB-30982 starts-->
			<ihtml:hidden property="ooexchfltrval"/>
			<ihtml:hidden property="displayPage"/>
			<ihtml:hidden property="officeOfExchange"/>
			<!--Added by A-8527 for bug IASCB-30982 starts-->
			<bean:define id="officeOfExchangeVO" name="officeOfExchangeVO"/>

			<div class="ic-content-main">
				<div class="ic-main-container">
				   <div class="ic-filter-panel">
				<div class="ic-row">

					<div class="ic-input-container ic-label-40 ic-round-border">
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-35">

									<common:message key="mailtracking.defaults.oemaster.lbl.exchangeOffice" />

								<ihtml:text property="code" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_EXGOFC" maxlength="6"/>
							</div>
							<div class="ic-input ic-mandatory ic-split-30">

									<common:message key="mailtracking.defaults.oemaster.lbl.pacode" /><span class="iCargoMandatoryFieldIcon"></span>

								<ihtml:text property="poaCode" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_PACODE"/>
								<!--modified by A-7938 for ICRD-244170-->
								<div class="lovImg">
								<img  id="paLOV" value="paLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								</div>
							</div>
							<div class="ic-input ic-split-35">

									<common:message key="mailtracking.defaults.oemaster.lbl.mailboxid" />

								<ihtml:text property="mailboxId" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_MAILBOXID" maxlength="40"/>
								<!--modified by A-7938 for ICRD-244170-->
								<div class="lovImg">
								<img name="mailboxidLOV" id="mailboxidLOV" value="mailboxidLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								</div>
						</div>
						</div>
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-25">
								<label class="ic-label-50">
									<common:message key="mailtracking.defaults.oemaster.lbl.city" /><span class="iCargoMandatoryFieldIcon"></span>
								</label>
								<ihtml:text property="cityCode" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_CITY" maxlength="4"/>
								<!--modified by A-7938 for ICRD-244170-->
								<div class="lovImg">
								<img id="cityLOV" value="cityLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
								</div>
							</div>
							
							<div class="ic-input ic-mandatory ic-split-25">
								<label class="ic-label-50">
									Country Code<span class="iCargoMandatoryFieldIcon"></span>
								</label>
								<ihtml:text property="countryCode" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_COUNTRY" maxlength="2"/>
								<!--modified by A-7938 for ICRD-244170-->
								<div class="lovImg">
									<img id="countryCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png"  onclick="displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.countryCode.value,'Country','1','countryCode','',0)" title="Country Code"/>
								</div>
							</div>
							
							<div class="ic-input ic-split-25">
								<label class="ic-label-50">
									<common:message key="mailtracking.defaults.oemaster.lbl.active" />
								</label>
								<input type="checkbox" name="active" value="true" <%if(((OfficeOfExchangeVO)officeOfExchangeVO).isActive()){%>checked<%}%>/>
							</div>
							<div class="ic-input ic-split-25">
								<label class="ic-label-40">
									<common:message key="mailtracking.defaults.oemaster.lbl.arpcod" />
								</label>
								<ihtml:text property="airportCode" name="officeOfExchangeVO" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_ARPCOD" maxlength="4"/>
								<!--modified by A-7938 for ICRD-244170-->
								<div class="lovImg">
								<img id="arpcodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airportCode.value,'Airport','1','airportCode','',0)">
							</div>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input ic-split-80">
								<label class="ic-label-25">
									<common:message key="mailtracking.defaults.oemaster.lbl.desc" />
								</label>
								<ihtml:text property="codeDescription" name="officeOfExchangeVO" style="width:200px" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_OEDESC" maxlength="500"/>
							</div>
						</div>
					</div>
				</div>
				</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">

							<ihtml:nbutton property="btSave" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_SAVE">
								<common:message key="mailtracking.defaults.oemaster.btn.save" />
							</ihtml:nbutton>
							<logic:equal name="form" property="status" value="ADD">
								<ihtml:nbutton property="btDelete" disabled="true"  componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_DELETE">
									<common:message key="mailtracking.defaults.oemaster.btn.delete" />
								</ihtml:nbutton>
							</logic:equal>
							<logic:notEqual name="form" property="status" value="ADD">
								<ihtml:nbutton property="btDelete" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_DELETE">
									<common:message key="mailtracking.defaults.oemaster.btn.delete" />
								</ihtml:nbutton>
							</logic:notEqual>
							<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_CLOSE">
								<common:message key="mailtracking.defaults.oemaster.btn.close" />
							</ihtml:nbutton>

					</div>
				</div>
			</div>
		</ihtml:form>
	</div>

	</body>
 </html:html>



