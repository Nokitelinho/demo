<%--
********************************************************************
* Project	 		: iCargo
* Module            : Mailtracking
* File Name			: DailyMailStation.jsp
* Date				: 28-feb-2008
* Author(s)			: A-3251 SREEJITH P.C.
********************************************************************
--%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %> 

<html:html>

<head>
		
		
	<title>
	   <common:message bundle="DailyMailStationResources" key="mailtracking.defaults.DailyMailStation.title" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/DailyMailStation_Script.jsp" />
</head>

<body class="ic-center" style="width:60%;">
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>

<div  class="iCargoContent" style="overflow-y:auto; " >
<bean:define
	 id="form"
	 name="DailyMailStationForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm"
	 toScope="page"/>

<ihtml:form action="/mailtracking.defaults.DailyMailStationScreenLoad.do">

<ihtml:hidden property="validreport"/>
<ihtml:hidden property="acfilghtDate"/>
<ihtml:hidden property="acflightNumber"/>
<ihtml:hidden property="acflightCarrireID"/>
<ihtml:hidden property="acflightSeqNumber"/>
<ihtml:hidden property="accompanyCode"/>
<ihtml:hidden property="acorigin"/>
<ihtml:hidden property="acdestination"/>
<ihtml:hidden property="accarrierCode"/>
<ihtml:hidden property="printstatus"/>
<ihtml:hidden property="acfilghtFromDate"/>
<ihtml:hidden property="acfilghtToDate"/>
	<div class="ic-content-main">
		<div class="ic-head-container">
			<span class="ic-page-title"><common:message   key="mailtracking.defaults.DailyMailStation.tabletitle" scope="request"/></span>
			<div class="ic-row">
	<div class="ic-col">
	<h4><common:message   key="mailtracking.defaults.DailyMailStation.lbl.searchcriteria" scope="request"/></h4>
	</div>
	</div>
			<div class="ic-row">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
				
			<div class="ic-row">
							
								
					<div class="ic-row">
						<!--<div class="ic-split-30 ic-input ic-center">
							<label>
								<common:message key="mailtracking.defaults.DailyMailStation.flightdate" />
								<span class="iCargoMandatoryFieldIcon">*</span>
							</label>				
								<ibusiness:calendar type="image" componentID="TXT_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_FLIGHTDATE" id="fdt" property="flightDate" value="<%=form.getFlightDate()%>" />
						</div>-->
						<!--Commented and added by A-6991 for CR CRD-197259 Starts-->
						<div class="ic-split-28 ic-input ic-center">
							<label>
								<common:message key="mailtracking.defaults.DailyMailStation.flightfromdate" />
								<span class="iCargoMandatoryFieldIcon">*</span>
							</label>				
								<ibusiness:calendar type="image" componentID="TXT_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_FLIGHTFROMDATE" id="ffromdt" property="flightFromDate" value="<%=form.getFlightFromDate()%>" />
						</div>
						<div class="ic-split-28 ic-input ic-center">
							<label>
								<common:message key="mailtracking.defaults.DailyMailStation.flighttodate" />
								<span class="iCargoMandatoryFieldIcon">*</span>
							</label>				
								<ibusiness:calendar type="image" componentID="TXT_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_FLIGHTTODATE" id="ftodt" property="flightToDate" value="<%=form.getFlightToDate()%>" />
						</div>
						<!--Commented and added by A-6991 for CR CRD-197259 Ends-->	
						<div class="ic-split-24 ic-input ic-center">
							<label>
								<common:message key="mailtracking.defaults.DailyMailStation.flightnumber" />
							</label>
							<ibusiness:flightnumber 
								  carrierCodeProperty="carrierCode"
								  id="flightNumber"
								  flightCodeProperty="flightNumber"
								  flightDatePickerId="flightDate"
								  carriercodevalue="<%=form.getCarrierCode()%>"
								  flightcodevalue="<%=form.getFlightNumber()%>"
								  componentID="TXT_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_FLIGHTNUMBER"
								  carrierCodeStyleClass="iCargoTextFieldVerySmall"
								  flightCodeStyleClass="iCargoTextFieldSmall"/>
							
													
								<ihtml:nbutton property="flightlov"
											  value="" styleClass="iCargoLovButton" tabindex="2"/>
												
						</div>
						<div class="ic-split-28 ic-input ic-center">
							<label>
								<common:message key="mailtracking.defaults.DailyMailStation.destination" />
							</label>
								<ihtml:text property="destination"   componentID="TXT_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_DESTINATION" maxlength="4" />
																		
								<div class="lovImg"><img src="<%= request.getContextPath()%>/images/lov.png" tabindex="16"  width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Destination','1','destination','',0)" /></div>									
						</div>
		
					</div>
		
		</div>
		
				<div class="ic-row ">
									
									
									
									
			<div class="ic-button-container">
				<ihtml:nbutton property="btnGenreport" componentID="BTN_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_GENERATE_REPORT" accesskey="R">
					<common:message key="mailtracking.defaults.DailyMailStation.report" />
				</ihtml:nbutton>
	
				<ihtml:nbutton property="btnPrint" componentID="BTN_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_PRINT" accesskey="P">
					<common:message key="mailtracking.defaults.DailyMailStation.print" />
				</ihtml:nbutton>
	
				
				<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_CLEAR" accesskey="C">
					<common:message key="mailtracking.defaults.DailyMailStation.clear" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_DAILYMAILSTATION_CLOSE" accesskey="O">
					<common:message key="mailtracking.defaults.DailyMailStation.close" />
				</ihtml:nbutton>
			</div>
		</div>
								
							</fieldset>
			</div>
		</div>
				</div>		
			
		</div>
	
		
		
		
</div>
</ihtml:form>

</div>

	</body>
</html:html>
