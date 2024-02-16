<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: DamageMailReport.jsp
* Date				: 29-FEB-2008
* Author(s)			: A-3227 RENO K ABRAHAM
 ***************************************************--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>


<html:html>

	<head>
		
		<title><common:message bundle="damageMailReportResources" key="mailtracking.defaults.damagemailreport.lbl.title" /></title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/mail/operations/DamageMailReport_Script.jsp" />
	</head>

	<body class="ic-center" style="width:70%;">
		
		<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<div id="pageDiv" class="iCargoContent">

			<ihtml:form action="/mailtracking.defaults.damagemailreport.screenload.do" >

			<bean:define id="form"
					name="DamageMailReportForm"
					type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamageMailReportForm"
					toScope="page"
					scope="request"/>
			<business:sessionBean id="damageCodes"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.damagemailreport"
					  method="get"
					  attribute="oneTimeDamageCodes" />
			<business:sessionBean id="subClassGroup"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.damagemailreport"
					  method="get"
					  attribute="subClassGroups" />

			<html:hidden property="validFlag" />
			<html:hidden property="status" />		  
			<html:hidden property="airlineId" />		  
					  
			<common:xgroup>
				<common:xsubgroup id="TURKISH_SPECIFIC">
					<ihtml:hidden  property="reportFlag" value="Y"/>
				</common:xsubgroup>
			</common:xgroup >		  
			<div class="ic-content-main">

				<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.damagemailreport.lbl.pagetitle" />
				</span>
				<div class="ic-head-container">

					<div class="ic-filter-panel">
					
						<div class="ic-row">
	<div class="ic-col">
	<h4><common:message   key="mailtracking.defaults.damagemailreport.lbl.searchCriteria" scope="request"/></h4>
						</div>
	</div>
						
							<div class="ic-section  ic-pad-2">
						<div class="ic-row">
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.fromdate" />
									<span class="iCargoMandatoryFieldIcon">*</span>
								</label>
								<ibusiness:calendar property="fromDate" type="image" id="incalender1"
										componentID="CMP_MailTracking_Defaults_DamageMailReport_FromDate" onblur="autoFillDate(this)" />
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.toDate" />
									<span class="iCargoMandatoryFieldIcon">*</span>
								</label>
								<ibusiness:calendar property="toDate" type="image" id="incalender2"
										componentID="CMP_MailTracking_Defaults_DamageMailReport_ToDate" onblur="autoFillDate(this)" />
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.airport" />
								</label>
								<ihtml:text property="airport" maxlength="4" componentID="TXT_MailTracking_Defaults_DamageMailReport_Airport" />
								<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" 
										onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airport.value,'Airport','1','airport','',0)"></div>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.flightno" />
								</label>
								<ibusiness:flightnumber id="flightNo" componentID="CMP_MailTracking_Defaults_DamageMailReport_FlightNo"
									carrierCodeMaxlength="3" flightCodeMaxlength="5" carrierCodeProperty="flightCarrierCode" flightCodeProperty="flightNumber"
									carrierCodeStyleClass="iCargoTextFieldVerySmall"
									flightCodeStyleClass="iCargoTextFieldSmall"/>
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.flightdate" />
								</label>
								<ibusiness:calendar id="flightDate" componentID="CMP_MailTracking_Defaults_DamageMailReport_FlightDate" property="flightDate" type="image"
								indexId="rowCount"/>
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.flightorigin" />
								</label>
								<ihtml:text property="flightOrigin" componentID="CMP_MailTracking_Defaults_DamageMailReport_Origin" maxlength="3"/>
								<div class="lovImg"><img id="lovOrigin" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</div>
							<div class="ic-split-25 ic-input  ic-label-40">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.flightdestn" />
								</label>
								<ihtml:text property="flightDestination" componentID="CMP_MailTracking_Defaults_DamageMailReport_Destination" maxlength="3"/>
								<div class="lovImg"><img id="lovDestination" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.gpacode" />
								</label>
								<ihtml:text property="gpaCode" componentID="CMP_MailTracking_Defaults_DamageMailReport_GPACODE" maxlength="5"/>
								<div class="lovImg"><img id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" /></div>
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.originoe" />
								</label>
								<ihtml:text property="originOE" componentID="CMP_MailTracking_Defaults_DamageMailReport_OriginOE" maxlength="6"/>
								<div class="lovImg"><img id="originOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="org"/></div>
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.destoe" />
								</label>
								<ihtml:text property="destinationOE" componentID="CMP_MailTracking_Defaults_DamageMailReport_DestinationOE" maxlength="6"/>
								<div class="lovImg"><img id="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/></div>
							</div>
							<div class="ic-split-25 ic-input  ic-label-40">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.subclassgrp" />
								</label>
								<ihtml:select property="subClassGroup"  componentID="CMP_MailTracking_Defaults_DamageMailReport_SubClassGrp" >
									<logic:present name="subClassGroup">
										<bean:define id="subClassGroup" name="subClassGroup" toScope="page"/>
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:iterate id="onetmvo" name="subClassGroup">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
												<html:option value="<%= value.toString() %>"><%= desc %></html:option>
											</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.sc" />
								</label>
								<ihtml:text property="subClassCode" componentID="CMP_MailTracking_Defaults_DamageMailReport_SC" maxlength="2" />
								<div class="lovImg"><img name="SCLov" id="SCLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
							</div>
							<div class="ic-split-25 ic-input  ic-label-35">
								<label>
									<common:message key="mailtracking.defaults.damagemailreport.lbl.type" />
								</label>
								<ihtml:select property="damageCode"  componentID="CMP_MailTracking_Defaults_DamageMailReport_DamageType" >
									<logic:present name="damageCodes">
										<bean:define id="damagecodes" name="damageCodes" toScope="page"/>
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:iterate id="onetmvo" name="damagecodes">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

												<html:option value="<%= value.toString() %>"><%= desc %></html:option>

											</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
						</div>
						</div>
						<div class="ic-section  ic-pad-2">
						<div class="ic-row" >
					<div class="ic-button-container">
					
						<ihtml:nbutton property="btnGenerateReport"  componentID="BTN_MailTracking_Defaults_DamageMailReport_btnGenerateReport" accesskey="R">
							<common:message key="mailtracking.defaults.damagemailreport.lbl.generatereport" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnPrint"  componentID="BTN_MailTracking_Defaults_DamageMailReport_btnPrint" accesskey="P" >
							<common:message key="mailtracking.defaults.damagemailreport.btn.print" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear"  componentID="BTN_MailTracking_Defaults_DamageMailReport_btnClear" accesskey="C" >
							<common:message key="mailtracking.defaults.damagemailreport.btn.clear" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose"  componentID="BTN_MailTracking_Defaults_DamageMailReport_btnClose" accesskey="O" >
							<common:message key="mailtracking.defaults.damagemailreport.btn.close" />
						</ihtml:nbutton>			 
					</div>
				</div>
			</div>
				</div>
				</div>
			</div>
		</ihtml:form>
		</div>

	</body>
</html:html>
