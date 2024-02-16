<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  IN - ULD
* File Name				:  ULDDiscrepancyReport.jsp
* Date					:  20-Mar-2008
* Author(s)				:  A-3045
*************************************************************************/
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ULDDiscrepancyReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.ArrayList"%>

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>


<html:html locale="true">
<head>
		
		
	
<title>
 <common:message bundle="ulddiscrepancyreport" key="uld.defaults.ulddiscrepancyreport.ulddiscrepancy" />
</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/uld/defaults/ULDDiscrepancyReport_Script.jsp" />
</head>
<body>
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<bean:define id="form"
 		name="ULDDiscrepancyReportForm"
 		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ULDDiscrepancyReportForm"
 		toScope="page" />

<div class="iCargoContent" id="pageDiv" >
<ihtml:form action="/uld.defaults.ulddiscrepancyreport.creatediscrepancyreport.do">

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<div class="ic-content-main ic-center bg-white" style="width:80%; border:0;">
		<span class="ic-page-title ic-bold"><common:message bundle="ulddiscrepancyreport" key="uld.defaults.ulddiscrepancyreport.ulddiscrepancy" /></span>
			<div class="ic-head-container ic-center" >
				
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row">
								<h4><common:message key="uld.defaults.ulddiscrepancyreport.searchcriteria"/></h4>
							</div>
							<div class="ic-section ">
								<div class="ic-row ic-label-45">
									<div class="ic-input ic-split-35">
										<label><common:message key="uld.defaults.ulddiscrepancyreport.lbl.fromDate"/></label>
										<ibusiness:calendar type="image"  id="fromDate" property="fromDate" componentID="TXT_ULD_DEFAULTS_ULDDISCREPANCYREPORT_FROMDATE"/>
									</div>
									<div class="ic-input ic-split-35">
										<label><common:message key="uld.defaults.ulddiscrepancyreport.lbl.toDate"/></label>
										<ibusiness:calendar type="image"  id="toDate" property="toDate" componentID="TXT_ULD_DEFAULTS_ULDDISCREPANCYREPORT_TODATE"/>
									</div>
									<div class="ic-input ic-split-30">
										<label><common:message key="uld.defaults.ulddiscrepancyreport.lbl.uldno"/></label>
										<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_ULDDISCREPANCYREPORT_ULDNO" style="text-transform: uppercase"/>
										<div class="lovImg"><img name="airlinelov" id="airlinelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline','1','airlineCode','',0)"  alt="Airline LOV"/>
										</div>
									</div>
								</div>
								
								<div class="ic-row ic-label-45 marginT5">
									<div class="ic-input ic-split-35">
										<label><common:message key="uld.defaults.ulddiscrepancyreport.lbl.airline"/></label>
										<ihtml:text componentID="TXT_ULD_DEFAULTS_ULDDISCREPANCYREPORT_AIRLINE" property="airlineCode" maxlength="3"/>			
									</div>
									<div class="ic-input ic-split-35">
										<label><common:message key="uld.defaults.ulddiscrepancyreport.lbl.reportingairport"/></label>
										<ihtml:text componentID="TXT_ULD_DEFAULTS_ULDDISCREPANCYREPORT_REPORTINGAIRPORT" property="reportingAirportCode" maxlength="3"/>			
										<div class="lovImg"><img name="reportingairportlov" id="reportingairportlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].reportingAirportCode.value,'Airport','1','reportingAirportCode','',0)" alt="Airport LOV"/>
										</div>
									</div>
								</div>
							</div>
						</div>
						
									
				<div class="ic-row">
					<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnGenerateReport" componentID="BTN_ULD_DEFAULTS_ULDDISCREPANCYREPORT_GENERATEREPORT" accesskey="R">
					<common:message key="uld.defaults.ulddiscrepancyreport.btn.btgeneratereport" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnPrint" componentID="BTN_ULD_DEFAULTS_ULDDISCREPANCYREPORT_PRINT" accesskey="P">
					<common:message key="uld.defaults.ulddiscrepancyreport.btn.btprint" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_ULDDISCREPANCYREPORT_CLEAR" accesskey="C">
					<common:message key="uld.defaults.ulddiscrepancyreport.btn.btclear" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_ULDDISCREPANCYREPORT_CLOSE" accesskey="O">
					<common:message key="uld.defaults.ulddiscrepancyreport.btn.btclose" />
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

