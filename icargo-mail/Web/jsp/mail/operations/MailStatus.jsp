<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: MailStatus.jsp
* Date				: 28-FEB-2008
* Author(s)			: A-3227 RENO K ABRAHAM
 ***************************************************--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>

		<html:html>

<head>
		
	
	<title><common:message bundle="mailStatusResources" key="mailtracking.defaults.mailstatus.lbl.title" /></title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/MailStatus_Script.jsp" />
</head>

<body>
	
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	
<div id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto;" >
<ihtml:form action="/mailtracking.defaults.mailstatus.screenload.do" styleClass="ic-main-form">
	<bean:define id="form"
		name="MailStatusForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailStatusForm"
		toScope="page"
		scope="request"/>
		<business:sessionBean id="currentStatus"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailstatus"
		  method="get"
		  attribute="currentStatus" />

<html:hidden property="dummyFlightDate" />
<html:hidden property="flightCarrierIdr" />
<html:hidden property="carrierIdr" />
<html:hidden property="dummyFlightDate" />
<html:hidden property="validFlag" />
<html:hidden property="status" />

	<div class="ic-content-main bg-white" >
		<span class="ic-page-title ic-display-none">
		<common:message key="mailtracking.defaults.mailstatus.lbl.pagetitle" />
		</span>
			<div class="ic-head-container">	
			<div  class="ic-border">
		
				<div class="ic-filter-panel">
				<div  class="ic-row">
					<div class="ic-col">
						<h4>
							<common:message key="mailtracking.defaults.mailstatus.lbl.searchCriteria" />
						</h4>
					</div>
				</div>
				
					<div class="ic-input-container">
							<div class="ic-row ic-label-50">
							
								<div class="ic-input ic-split-20 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.fromdate" />
									</label>
									<ibusiness:calendar property="fromDate" type="image" id="incalender1"
									componentID="CMP_MailTracking_Defaults_MailStatus_FromDate" onblur="autoFillDate(this)" tabindex="1"/>
								</div>
								
								<div class="ic-input ic-split-20 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.toDate" />
									</label>
									<ibusiness:calendar property="toDate" type="image" id="incalender2"
									componentID="CMP_MailTracking_Defaults_MailStatus_ToDate" onblur="autoFillDate(this)" tabindex="2"/>
								</div>
								
								<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.carrier" />
									</label>
									<ihtml:text property="carrierCode" maxlength="3" value="<%=form.getCarrierCode()%>" componentID="TXT_MailTracking_Defaults_MailStatus_CarrierCode" tabindex="3"/>
									<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','1','carrierCode','',0)"></div>
								</div>
								
								<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.flightno" />
									</label>
									<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>" componentID="CMP_MailTracking_Defaults_MailStatus_FlightNo" tabindex="4"/>
									<ihtml:nbutton property="flightlov" id="flightlov"  value="" styleClass="iCargoLovButton" />
								</div>
								
								<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.pacode" />
									</label>
									<ihtml:text property="paCode" value="<%=form.getPaCode()%>" componentID="TXT_MailTracking_Defaults_MailStatus_PACode" maxlength="5" tabindex="5"/>
									<div class="lovImg"><img id="paCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.paCode.value,'PA','1','paCode','',0)"></div>
								</div>
								
								
							</div>
							<div class="ic-row ic-label-50">
								<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.pol" />
									</label>
									<ihtml:text property="pol" maxlength="4" value="<%=form.getPol()%>" componentID="TXT_MailTracking_Defaults_MailStatus_POL" tabindex="6"/>
									<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pol.value,'Airport','1','pol','',0)"></div>
								</div>
								
								<div class="ic-input ic-split-20">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.pou" />
									</label>
									<ihtml:text property="pou" maxlength="4" value="<%=form.getPou()%>" componentID="TXT_MailTracking_Defaults_MailStatus_POU" tabindex="7"/>
									<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pou.value,'Airport','1','pou','',0)"></div>
								</div>
								
								<div class="ic-input ic-split-60">
									<label>
										<common:message key="mailtracking.defaults.mailstatus.lbl.currentstatus" />
									</label>
									<ihtml:select property="currentStatus" componentID="CMP_MailTracking_Defaults_MailStatus_currentStatus" style="width:270px" tabindex="8">
										<bean:define id="currentStat" name="currentStatus" toScope="page" />
										<logic:iterate id="status" name="currentStat" >
										<html:option value="<%=(String)status %>"><%=(String)status %></html:option>				          
										</logic:iterate>
									</ihtml:select>
								</div>
							
							
							
							</div>
					</div>
					<div class="ic-row">
			
				<div class="ic-button-container">
					
					  <ihtml:nbutton property="btnGenerateReport"  componentID="BTN_MailTracking_Defaults_MailStatus_btnGenerateReport" accesskey="R" tabindex="9">
						<common:message key="mailtracking.defaults.mailstatus.lbl.generatereport" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnPrint"  componentID="BTN_MailTracking_Defaults_MailStatus_btnPrint" accesskey="P" tabindex="10">
						<common:message key="mailtracking.defaults.mailstatus.btn.print" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnClear"  componentID="BTN_MailTracking_Defaults_MailStatus_btnClear" accesskey="C" tabindex="11">
						<common:message key="mailtracking.defaults.mailstatus.btn.clear" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnClose"  componentID="BTN_MailTracking_Defaults_MailStatus_btnClose" accesskey="O" tabindex="12">
						<common:message key="mailtracking.defaults.mailstatus.btn.close" />
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
