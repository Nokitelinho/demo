<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name				: mra.flown
* File Name					: FlownReports.jsp
* Date						: 01-Mar-2007
* Author(s)					: Santha Kumar P.M
 *********************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.lang.String"%>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>



<html>

<head>

	

	
    <title><common:message bundle="flownreportsresources" key="mra.flown.flownreports.title"/></title>
    <meta name="decorator" content="mainpanelrestyledui">
    <common:include type="script" src="/js/mail/mra/flown/FlownReports_Script.jsp"/>
</head>

<body class="ic-center" style="width:75%">
	
	

<bean:define id="form" name="FlownReportsForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.report.FlownReportsForm" toScope="page" />

<business:sessionBean id="KEY_FLIGHTSTATUS" moduleName="mailtracking.mra.flown"
	screenID="mra.flown.flownreports" method="get"
	attribute="flightStatus" />

<%@ include file="/jsp/includes/reports/printFrame.jsp" %>
<!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="pageDiv">
<ihtml:form action="/mra.flown.flownreports.screenloadflownreports.do" >

<ihtml:hidden property="comboFlag"/>

	<div class="ic-content-main bg-white">
		<span class="ic-page-title ic-display-none">
		<common:message  key="mra.flown.flownreports.title"/>
		</span>
	<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-col">
					<div class="ic-input ic-mandatory ic-center" >
						<label><common:message  key="cra.noncass.exportbilling.exportbillingreports.reportid"/></label>
							<ihtml:select property="reportId" componentID="CMP_MRA_Flown_FlownReports_reportId" style="width:250px;" >
							<ihtml:option value="ListOfFlightsWithFlightStatus"><common:message  key="mra.flown.flownreports.listofflightswithflightstatus"/></ihtml:option>
							<ihtml:option value="ListOfFlownMails"><common:message  key="mra.flown.flownreports.listofflownmails"/></ihtml:option>
							<ihtml:option value="MailRevenueReport"><common:message  key="mra.flown.flownreports.MailRevenueReport"/></ihtml:option>
							</ihtml:select>
					</div>
				</div>
			</div>
	<logic:equal name="form" property="comboFlag" value="ListOfFlights">
		<div id="ListOfFlightsWithFlightStatus" class="ic-label-50" >
			<fieldset class="ic-field-set">
			<legend><common:message  key="mra.flown.flownreports.listofflightswithflightstatus"/></legend>
				<div class="ic-row">
					<div class="ic-input ic-split-50">
					<label><common:message key="mra.flown.flownreports.FlightNo" /></label>
					<ibusiness:flightnumber carrierCodeProperty="carrierCodeForListOfFlights"
					id="flightNumber"
					flightCodeProperty="flightNumberForListOfFlights"
					carriercodevalue=""
					flightcodevalue=""
					componentID="CMP_MRA_Flown_FlownReports_ListOfFlights_FlightNo"
					carrierCodeStyleClass="iCargoTextFieldVerySmall"
					flightCodeStyleClass="iCargoTextFieldSmall"/>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-50">
						<label><common:message  key="mra.flown.flownreports.fromdate"/></label>
						<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_ListOfFlights_FromDate" id="fromdateListOfFlights" property="fromDateForListOfFlights" />
					</div>
					<div class="ic-input ic-split-50">
						<label><common:message  key="mra.flown.flownreports.todate"/></label>
						<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_ListOfFlights_ToDate" id="todateListOfFlights" property="todateForListOfFlights" />
					</div>
				</div>
			</fieldset>
		</div>
	</logic:equal>
	<logic:equal name="form" property="comboFlag" value="ListOfFlown">

					<div id="ListOfFlownMails" class="ic-label-50">
					<fieldset class="ic-field-set">
					<legend><common:message  key="mra.flown.flownreports.listofflownmails"/></legend>
					<div class="ic-row">
						<div class="ic-input ic-split-50">
						<label>
						<common:message key="mra.flown.flownreports.FlightNo" />
						</label>
						<ibusiness:flightnumber carrierCodeProperty="carrierCodeForListOfFlownMails"
									  id="flightNumber"
									  flightCodeProperty="flightNumberForListOfFlownMails"
									  carriercodevalue=""
									  flightcodevalue=""
									  componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_FlightNo"
									  carrierCodeStyleClass="iCargoTextFieldVerySmall"
									  flightCodeStyleClass="iCargoTextFieldSmall"/>
						</div>
					</div>
					<div class="ic-row">
					<div class="ic-input ic-split-50">
						<label><common:message  key="mra.flown.flownreports.fromdate"/></label>
							<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_FromDate" id="fromdateListOfFlownMails" property="fromdateForListOfFlownMails" />
					</div>
					<div class="ic-input ic-split-50">
					<label><common:message  key="mra.flown.flownreports.todate"/></label>
							<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_ToDate" id="todateListOfFlownMails" property="todateForListOfFlownMails" />
					</div>
					</div>
					<div class="ic-row">
					<div class="ic-input ic-split-50">
					<label><common:message key="mra.flown.flownreports.flightOrigin"/></label>
							<ihtml:text  property="flightOrigin" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_FlightOrigin" name="form" maxlength="4" />
					</div>
					<div class="ic-input ic-split-50">
					<label><common:message key="mra.flown.flownreports.flightDestination"/></label>
						<ihtml:text  property="flightDestination" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_FlightDestination" name="form" maxlength="4" />
					</div>
					</div>
					<div class="ic-row">
					<div class="ic-input ic-split-50">
					<label><common:message key="mra.flown.flownreports.mailOrigin"/></label>
						<ihtml:text  property="mailOrigin" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_MailOrigin" name="form" maxlength="4" />
					</div>
					<div class="ic-input ic-split-50">
					<label><common:message key="mra.flown.flownreports.mailDestination"/></label>
						<ihtml:text  property="mailDestination" componentID="CMP_MRA_Flown_FlownReports_ListOfFlownMails_MailDestination" name="form" maxlength="4" />
					</div>
					</div>
						</fieldset>
					</div>
	</logic:equal>
					<logic:equal name="form" property="comboFlag" value="MailRevenue">
					
					<div id="MailRevenueReport" class="ic-label-50">
					<fieldset class="ic-field-set">
						<legend class="iCargoLegend"><common:message  key="mra.flown.flownreports.mailrevenuereport"/></legend>
					<div class="ic-row">
						<div class="ic-input ic-split-50">
						<label>
						<common:message key="mra.flown.flownreports.accountmonth" />
						</label>
						<ihtml:text property="accountMonth" componentID="CMP_MRA_Flown_FlownReports_accMonth"/>
						<!-- <img name="accMonLov" id="accMonLov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" /> -->
						<!-- Modified by A-8236 for ICRD-252976 -->
						<div class="lovImg"><img name= "accMonLov" id ="accMonLov" src="<%=request.getContextPath()%>/images/lov.png"  height="22" width="22" /></div>
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-50">
						<label><common:message key="mra.flown.flownreports.FlightNo" /></label>
						<ibusiness:flightnumber carrierCodeProperty="carrierCodeForRevReport"
												id="flightNumber"
												flightCodeProperty="flightNumberForRevReport"
												carriercodevalue=""
												flightcodevalue=""
												componentID="CMP_MRA_Flown_FlownReports_RevReport_FlightNo"
												carrierCodeStyleClass="iCargoTextFieldVerySmall"
												flightCodeStyleClass="iCargoTextFieldSmall"/>
						</div>
					</div>
					<div class="ic-row">
					<div class="ic-input ic-split-50">
						<label><common:message  key="mra.flown.flownreports.fromdate"/></label>
						<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_RevReport_FromDate" id="fromDateForRevReport" property="fromDateForRevReport" />
					</div>
					<div class="ic-input ic-split-50">
					<label><common:message  key="mra.flown.flownreports.todate"/></label>
						<ibusiness:calendar type="image" componentID="CMP_MRA_Flown_FlownReports_RevReport_ToDate" id="toDateForRevReport" property="toDateForRevReport" />
					</div>
					</div>
				</fieldset>
					</div>
					</logic:equal>
			<div class="ic-row">
				<div class="ic-button-container">
				<ihtml:nbutton property="btnPrint"  componentID="CMP_MRA_Flown_FlownReports_Print"  accesskey="P">
						<common:message  key="mra.flown.flownreports.btn.print"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose"  componentID="CMP_MRA_Flown_FlownReports_Close"  accesskey="O">
						<common:message  key="mra.flown.flownreports.btn.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
	</div>
	</ihtml:form>
</div>

	</body>
</html>
