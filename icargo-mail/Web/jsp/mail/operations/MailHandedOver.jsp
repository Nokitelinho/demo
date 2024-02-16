<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  MailHandedOver.jsp
* Date          	 :  16-Feb-2016
* Author(s)     	 :  Manish M

*************************************************************************/
 --%>
 
 <%@ include file="/jsp/includes/tlds.jsp" %> 
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailHandedOverReportForm" %>
 
 


	
	 <html:html>
 
 <head>
		
	
	 <title>
		<common:message bundle="mailHandedOverReportResources" key="mailtracking.defaults.mailhandedover.lbl.title" />
	 </title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/MailHandedOver_Script.jsp" />
 </head>
 
 <body class="ic-center" style="width:64%;">
	
	
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	<div id="pageDiv" class="iCargoContent">
		<ihtml:form action="/mailtracking.defaults.mailhandedover.screenload.do" >

		<bean:define id="form"
		name="MailHandedOverReportForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailHandedOverReportForm"
			toScope="page"
			scope="request"/>
			
		<html:hidden property="flightCarrierId" />
		<html:hidden property="carrierId" />
		<html:hidden property="validFlag" />
		<html:hidden property="status" />		  	
	
     
    	<div class="ic-content-main">
			<div class="ic-head-container">
				<span class="ic-page-title ic-display-none"><common:message key="mailtracking.defaults.mailhandedover.lbl.mailhandleoverotherairlines" /></span>
					<div class="ic-row">
						<h4><common:message key="mailtracking.defaults.mailhandedover.lbl.search" /></h4>
					</div>
				<div class="ic-filter-panel">
					<div class="ic-row " style="width: 99%;">
						<div class="ic-input ic-split-25 ">
							<label><common:message key="mailtracking.defaults.mailhandedover.lbl.fromdate" /></label>
							<ibusiness:calendar property="fromDate" id="fromflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_FROMFLIGHTDATE" tabindex="1"/>
						</div>
						<div class="ic-input ic-split-25 ">
							<label><common:message key="mailtracking.defaults.mailhandedover.lbl.todate" /></label>
							<ibusiness:calendar property="toDate" id="toflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_TOFLIGHTDATE" tabindex="2"/>	
						</div>
						<div class="ic-input ic-split-20 ">
							<label><common:message key="mailtracking.defaults.mailhandedover.lbl.airline" /></label>
							<ihtml:text property="carrierCode" maxlength="3" value="<%=form.getCarrierCode()%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_CARRIERCODE" tabindex="3"/>
							<div class="lovImg"><img id = airlineLOV src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"/></div>
						</div>
						<div class="ic-input ic-split-30 ">
							<label><common:message key="mailtracking.defaults.mailhandedover.lbl.flightnumber" /></label>
							<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_FLIGHTNO" tabindex="4"/>
							<ihtml:nbutton property="flightlov"  value="" styleClass="iCargoLovButton" />
						</div>
					</div>
					<div class="ic-row " style="width: 99%;">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnGenerateReport" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_REPORT" accesskey="R" tabindex="5">
								<common:message key="mailtracking.defaults.mailhandedover.btn.report" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnPrint" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_PRINT" accesskey="P" tabindex="6">
								<common:message key="mailtracking.defaults.mailhandedover.btn.print" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_CLEAR" accesskey="C" tabindex="7">
								<common:message key="mailtracking.defaults.mailhandedover.btn.clear" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_DEFAULTS_MAILHANDOVER_CLOSE" accesskey="O" tabindex="8">
								<common:message key="mailtracking.defaults.mailhandedover.btn.close" />
							</ihtml:nbutton>
						</div>	
					</div>
				</div>
			</div>		
		</ihtml:form>
	</div>
	 
				
		
	</body>
</html:html>
