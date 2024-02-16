<%--/**********************************************************************
* Project	 				: iCargo
* Module Code & Name		: admin-ErrorHandling
* File Name					: ErrorHandling.jsp
* Date						: 20-mar-2014
* Author(s)					: Sowjanya Varma.G
 ************************************************************************/
 --%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.session.interfaces.admin.monitoring.ErrorHandlingSession"%>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "no-cache");
	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache");
	}
%>
<html:html>
<head>
<title><common:message bundle="errorhandlingresources"
	key="icargo.admin.monitoring.errorhandling.lbl.popuppagetitle" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script"
	src="/js/mail/operations/ResolveError_Script.jsp" />
</head>
<body>

<%
	boolean turkishFlag = false;
%>
<common:xgroup>
	<common:xsubgroup id="TURKISH_SPECIFIC">
		<%
			turkishFlag = true;
		%>
	</common:xsubgroup>
</common:xgroup>
<business:sessionBean id="oneTimeCatSession"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.errorhandligpopup" method="get"
	attribute="oneTimeCat" />
<business:sessionBean id="oneTimeHNISession"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.errorhandligpopup" method="get"
	attribute="oneTimeHni" />
<business:sessionBean id="oneTimeRISession"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.errorhandligpopup" method="get"
	attribute="oneTimeRi" />
<%
	if (turkishFlag) {
%>
<business:sessionBean id="oneTimeMailCompanyCodeSession"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.errorhandligpopup" method="get"
	attribute="oneTimeCompanyCode" />
<%
	}
%>
<bean:define id="form" name="ErrorHandlingPopUpForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm"
	toScope="page" scope="request" />
<div class="iCargoPopUpContent" id="mainDiv" style="height: 100%">
<ihtml:form action="admin.monitoring.openresendorreprocesspopup.do" styleClass="ic-main-form">
	<ihtml:hidden
		property="selectedtxnid"  /> <ihtml:hidden
		property="functionType" /> <ihtml:hidden
		property="currentDialogOption" /> <ihtml:hidden
		property="currentDialogId" /> <ihtml:hidden property="selectedIndex" />
	<ihtml:hidden property="bulk" />
	<ihtml:hidden property="transactionName" />
	  <html:hidden property="lastPopupPageNum" />
	  <html:hidden property="displayPopupPage" />
	  <html:hidden property="totalViewRecords" />
	  <ihtml:hidden property="operationFlag"  />
 <ihtml:hidden	property="isFlightCarrierCodeChanged"  />
<ihtml:hidden property="isFlightNumberChanged"  />
<ihtml:hidden property="isFlightDateChanged"  />
<ihtml:hidden property="isContainerTypeChanged"  />
<ihtml:hidden property="isContainerNumberChanged"  />
<ihtml:hidden property="isDestinationChanged"  />
<ihtml:hidden property="isMailCompanyCodeChanged"  />
<ihtml:hidden property="isPouChanged"  />
<ihtml:hidden property="isPolChanged"  />
<ihtml:hidden property="isOOEChanged"  />
<ihtml:hidden property="isDOEChanged"  />
<ihtml:hidden property="isCategoryChanged"  />
<ihtml:hidden property="isSubClassChanged"  />
<ihtml:hidden property="isYearChanged"  />
<ihtml:hidden property="isDsnChanged"  />
<ihtml:hidden property="isRsnChanged"  />
<ihtml:hidden property="isHniChanged"  />
<ihtml:hidden property="isRiChanged"  />
<ihtml:hidden property="isWeightChanged"  />
<ihtml:hidden property="isTransferCarrierChanged"  />
	  

	<div class="ic-content-main">
	<div class="ic-main-container">
	<span class="ic-page-title ic-display-none">
	<common:message
		key="icargo.admin.monitoring.errorhandling.lbl.popuppagetitle" /> </span>
		<div class="ic-row">
		<div class="ic-col-100 ic-right">
		<common:popuppaginationtag
						pageURL="javascript:displayNextError('lastPageNum','displayPage')"
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoResultsLabel"
						displayPage="<%=form.getDisplayPopupPage()%>"
						totalRecords="<%=form.getTotalViewRecords()%>" />
		</div>
		
		</div>
		
						
				
	
					  
					 
	<div class="ic-row ic-border">
	   
	
	<div class="ic-row">
	  <div class="ic-input ic-split-25">
	      <label><common:message key="icargo.admin.monitoring.errorhandling.lbl.mailbag" />  </label>
	      <ihtml:text property="mailBag" componentID="CMP_Admin_Monitoring_ErrorHandling_Mailbag" onchange="mailbagChangeOnEdit()" value="<%=form.getMailBag()%>" style="width:200px" maxlength="29"/>
	  </div>
	<div class="ic-input ic-split-15"><label> <common:message
		key="icargo.admin.monitoring.errorhandling.lbl.flightnumber" /> </label> <ibusiness:flightnumber
		id="fltNo" carrierCodeProperty="flightCarrierCode"
		flightCodeProperty="flightNumber"
		carriercodevalue="<%=form.getFlightCarrierCode()%>"
		flightcodevalue="<%=form.getFlightNumber()%>" tabindex="1" onchange="flightNumberChangeOnEdit()"
		componentID="CMP_Admin_Monitoring_ErrorHandling_PopUp_FlightNumber" />
	</div>
	<div class="ic-input ic-split-15">
	
	<label> <common:message
		key="icargo.admin.monitoring.errorhandling.lbl.flightdate" /> </label> <ibusiness:calendar
		 property="flightDate"  id="flightDate"  type="image"
		componentID="CMP_Admin_Monitoring_ErrorHandling_Popup_FlightDate"
		tabindex="2"  value="<%=form.getFlightDate()%>"  /> </div>
	<div class="ic-input ic-split-30 borrowUld marginT15">
							<ibusiness:uld id="container" uldProperty="container" barrowFlag="true" barrowFlagProperty="barrowCheck" barrowCheck="<%=form.getBulk()%>"
				style="text-transform:uppercase;"  onchange="containerNumberChangeOnEdit()" componentID="CMP_Admin_Monitoring_ErrorHandling_PopUp_Container" maxlength="10" uldValue="<%=form.getContainer()%>"/>
							</div>
							<div class="ic-input ic-split-12">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.transferCarrier" />
								</label>
								<ihtml:text property="transferCarrier" onchange="transferCarrierChangeOnEdit()" componentID="CMP_Admin_Monitoring_ErrorHandling_PopUp_TransferCarrier" value="<%=form.getTransferCarrier()%>" maxlength="3"/>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.pol.tooltip" />
								</label>
								<ihtml:text property="pol" componentID="CMP_Admin_Monitoring_ErrorHandling_POL" onchange="polChangeOnEdit()" value="<%=form.getPol()%>" maxlength="3"/>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.pointofunloading" />
								</label>
								<ihtml:text property="pou" componentID="CMP_Admin_Monitoring_ErrorHandling_POU" onchange="pouChangeOnEdit()" value="<%=form.getPou()%>" maxlength="3"/>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.destination" />
								</label>
								<ihtml:text property="destination" onchange="destinationChangeOnEdit()" componentID="CMP_Admin_Monitoring_ErrorHandling_DESTINATION" value="<%=form.getDestination()%>" maxlength="3"/>
							</div>
							<% if(turkishFlag){%>
							<div class="ic-input ic-split-40">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.companycode" />
								</label>
								   <ihtml:select property="mailCompanyCode"
          				  componentID="CMP_Admin_Monitoring_ErrorHandling_CMPCOD" style="width:75px" onchange="mailCompanyCodeChangeOnEdit()"
          				  >
					<%String cmpcod = "";
														String cmpcodval = "";%>
		          <logic:present name="form"  property="mailCompanyCode">
		                <bean:define id="mail_cmpcod" name="form"  property="mailCompanyCode"  toScope="page"/><%=mail_cmpcod%>
		                <bean:define id="mailcmpcodColln" name="oneTimeMailCompanyCodeSession" toScope="page" />
					  <logic:iterate id="cmpcodVO" name="mailcmpcodColln" >
							<bean:define id="fieldValue" name="cmpcodVO" property="fieldValue" />
							<%if (fieldValue
																					.equals(mail_cmpcod)) {%>
						<bean:define id="fieldDesc" name="cmpcodVO" property="fieldValue" />
							<%cmpcod = (String) fieldDesc;
																					cmpcodval = (String) fieldValue;
																				}%>
					  </logic:iterate>
		                <html:option value="<%=(String)cmpcodval%>"><%=(String)cmpcod%></html:option>
		          </logic:present>
		               <logic:notPresent name="form"  property="mailCompanyCode">
		               		<html:option value=""><common:message key="combo.select"/></html:option>
		               </logic:notPresent>
		                <bean:define id="mailcmpcodColln" name="oneTimeMailCompanyCodeSession" toScope="page" />
		                    <logic:iterate id="cmpcodVO" name="mailcmpcodColln" >
		                              <bean:define id="fieldValue" name="cmpcodVO" property="fieldValue" toScope="page" />
		              				<%if (!cmpcodval
																								.equals(fieldValue)) {%>
		                              <html:option value="<%=(String)fieldValue %>"><bean:write name="cmpcodVO" property="fieldValue" /></html:option>
		                      <%}%>
		                   </logic:iterate>
           </ihtml:select>
							</div>
							 <%}%>
						</div>
						<div class="ic-row">
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.ooe" />
								</label>
								<ihtml:text property="ooe" componentID="CMP_Admin_Monitoring_ErrorHandling_OOE" onchange="ooeChangeOnEdit()" value="<%=form.getOoe()%>" maxlength="6"/>
								<div class="lovImg">
								<img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" >
								</div>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.doe" />
								</label>
								<ihtml:text property="doe" componentID="CMP_Admin_Monitoring_ErrorHandling_DOE" onchange="doeChangeOnEdit()" value="<%=form.getDoe()%>" maxlength="6"/>
								<div class="lovImg">
								<img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" >
								</div>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.category" />
								</label>
								<ihtml:select property="category"
						disabled="false"
							componentID="CMP_Admin_Monitoring_ErrorHandling_CATEGORY" onchange="categoryChangeOnEdit()" value="<%=form.getCategory()%>" >
							<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
											<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
											<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
													<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
											</logic:iterate>
					</ihtml:select>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.subclass" />
								</label>
							<ihtml:text property="subclass" componentID="CMP_Admin_Monitoring_ErrorHandling_SUBCLASS" onchange="subclassChangeOnEdit()" value="<%=form.getSubclass()%>" maxlength="2"/>
								<div class="lovImg">
								<img name="mailSCLov" id="mailSCLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" >
								</div>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.year" />
								</label>
							<ihtml:text property="year" componentID="CMP_Admin_Monitoring_ErrorHandling_YEAR" onchange="yearChangeOnEdit()" value="<%=form.getYear()%>" maxlength="1"/>
							</div>
						</div>
							<div class="ic-row">
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.dsn" />
								</label>
							<ihtml:text property="dsn" componentID="CMP_Admin_Monitoring_ErrorHandling_DSN" onchange="dsnChangeOnEdit()" value="<%=form.getDsn()%>" maxlength="4"/>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.rsn" />
								</label>
							<ihtml:text property="rsn" componentID="CMP_Admin_Monitoring_ErrorHandling_RSN" onchange="rsnChangeOnEdit()" value="<%=form.getRsn()%>" maxlength="3"/>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message key="icargo.admin.monitoring.errorhandling.lbl.popup.hni" />
								</label>
								<ihtml:select property="hni"
											disabled="false"
											onchange="hniChangeOnEdit()" componentID="CMP_Admin_Monitoring_ErrorHandling_HNI" value="<%=form.getHni()%>">
							<bean:define id="oneTimeHNISession" name="oneTimeHNISession" toScope="page" />
											<logic:iterate id="oneTimeHniVO" name="oneTimeHNISession" >
											<bean:define id="fieldValue" name="oneTimeHniVO" property="fieldValue" toScope="page" />
													<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHniVO" property="fieldValue"/></html:option>
											</logic:iterate>
											</ihtml:select>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.ri" />
								</label>
							<ihtml:select property="ri"
											disabled="false"
											onchange="riChangeOnEdit()" componentID="CMP_Admin_Monitoring_ErrorHandling_RI" value="<%=form.getRi()%>">
							<bean:define id="oneTimeRISession" name="oneTimeRISession" toScope="page" />
											<logic:iterate id="oneTimeRiVO" name="oneTimeRISession" >
											<bean:define id="fieldValue" name="oneTimeRiVO" property="fieldValue" toScope="page" />
													<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRiVO" property="fieldValue"/></html:option>
											</logic:iterate>
											</ihtml:select>
							</div>
							<div class="ic-input ic-split-20">
								<label> <common:message  key="icargo.admin.monitoring.errorhandling.lbl.popup.weight" />
								</label>
						<ihtml:text property="weight" componentID="CMP_Admin_Monitoring_ErrorHandling_WEIGHT" onchange="weightChangeOnEdit()" value="<%=form.getWeight()%>" maxlength="4"/>
							</div>
						</div>
					</div>
					
						<fieldset class="ic-field-set inline_filedset" >
									
						<div class="ic-row ic-input">
						<div class="ic-col-20">
						<!--Modified as part of ICRD-331244-->
					<label> &nbsp;Origin&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<ihtml:text property="orgin" componentID="CMP_Admin_Monitoring_ErrorHandling_OOE" style="width:45px" maxlength="4"/>
						<div class="lovImg">
						<img name="mailOrgLov" id="mailOrgLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
						</div>
						</div>
						<div class="ic-col-22">
						<label>Destination&nbsp;&nbsp;&nbsp;</label><!--Modified as part of ICRD-331244-->
						<ihtml:text property="destn" componentID="CMP_Admin_Monitoring_ErrorHandling_DOE" style="width:45px" maxlength="4"/>
						<div class="lovImg">
						<img name="mailDestLov" id="mailDestLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
						</div>
						</div>
						<div class="ic-col-20">
						<label>GPA&nbsp;&nbsp;&nbsp;</label><!--Modified as part of ICRD-331244-->
						<ihtml:text property="gpaCode" componentID="CMP_Admin_Monitoring_ErrorHandling_GPA" style="width:45px" maxlength="5"/>
						<div class="lovImg">
						<img name="gpaCodelov" id="gpaCodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
						</div>
						</div>
</div>					
					
			</fieldset>			
							
						
</div>
 <div class="ic-foot-container">
		  <div class="ic-button-container">
		   <ihtml:nbutton property="btOK"  componentID="CMP_Admin_Monitoring_ErrorHandling_OK_POPUP_BUTTON" onclick="btsave()">
							<common:message  key="icargo.admin.monitoring.errorhandling.btn.ok" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose"  componentID="CMP_Admin_Monitoring_ErrorHandling_CLOSE_POPUP_BUTTON" >
							<common:message  key="icargo.admin.monitoring.errorhandling.btn.close" />
				</ihtml:nbutton>
		  </div>
		  </div>
		</div>
	</ihtml:form>
	</div>
	</body>
</html:html>