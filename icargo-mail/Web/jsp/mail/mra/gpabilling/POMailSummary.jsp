
<%--
 /***********************************************************************
* Project	 		:  iCargo
* Module Code & Name		:  Mailtracking
* File Name			:  POMailSummary.jsp
* Date				:  11-May-2012
* Author(s)			:  A-4823
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import="org.apache.struts.Globals"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.POMailSummaryForm" %>

 <bean:define id="form" name="POMailSummaryForm"
	   type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.POMailSummaryForm"
	   toScope="page" scope="request"/>

<html:html>	
 <head>
	
	
	
		
			
	
 	<title><common:message bundle="poMailSummaryResources" key="mailtracking.mra.gpabilling.pomailsummary.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/gpabilling/POMailSummary_Script.jsp"/>
 
 </head>

 <body>
	
	

	
	
 <%@include file="/jsp/includes/reports/printFrame.jsp" %>
  	

	
<div class="iCargoContent ic-masterbg">
	<ihtml:form action="mailtracking.mra.gpabilling.pomailsummary.screenload.do">

	
<div class="ic-content-main bg-white">
	<span class="ic-page-title"><common:message key="mailtracking.mra.gpabilling.pomailsummary.lbl.title" /></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-section ">
						<div class="ic-row ic-label-40">
							<div class="ic-input ic-split-40">
								<label><common:message key="mailtracking.mra.gpabilling.pomailsummary.lbl.station" /></label>
								<ihtml:select componentID="CMP_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_STATION" property="locationType" >
									<ihtml:option value="City">City</ihtml:option>
									<ihtml:option value="Country">Country</ihtml:option>
								</ihtml:select>
								<ihtml:text property="location"  componentID="CMP_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_STATIONTEXT" maxlength="5"/> 
								<div class="lovImg">
								<img name="stationLov" id="stationLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
								</div>	
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label><common:message key="mailtracking.mra.gpabilling.pomailsummary.lbl.fromdate" /></label>
								<ibusiness:calendar property="fromDate" type="image" id="incalender1"
								value=""
								componentID="CMP_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_FROMDATE" 
								onblur="autoFillDate(this)"/>
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label><common:message key="mailtracking.mra.gpabilling.pomailsummary.lbl.todate" /></label>
								<ibusiness:calendar property="toDate" type="image" id="incalender2"
								value=""
								componentID="CMP_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_TODATE" onblur="autoFillDate(this)"/>
							</div>
						</div>
					</div>
				</div>
			<div class="ic-row">
				<div class="ic-button-container">
						<ihtml:nbutton property="btnPrint" componentID="BTN_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_PRINT" accesskey="P" >
							<common:message key="mailtracking.mra.gpabilling.pomailsummary.btn.print" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_CLEAR" accesskey="C" >
							<common:message key="mailtracking.mra.gpabilling.pomailsummary.btn.clear" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_MRA_GPABILLING_POMAILSUMMARY_CLOSE" accesskey="O" >
							<common:message key="mailtracking.mra.gpabilling.pomailsummary.btn.close" />
						</ihtml:nbutton>
				</div>
			</div>
    	</div>
	</div>			
	</div>			
		</ihtml:form>
	</div> 
	
	</body>

 </html:html>
