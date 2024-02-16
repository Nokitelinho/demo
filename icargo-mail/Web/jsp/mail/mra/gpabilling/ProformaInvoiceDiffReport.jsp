<%--

* Project	 			:  iCargo
* Module Code & Name			:   MRA-GPABilling
* File Name				:  ProformaInvoiceDiffReport.jsp
* Date					:  06-Aug-2008
* Author(s)				:  A-3271


 --%>
<%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp" %>
  <%@ page import = "java.util.Calendar" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ProformaMailInvoiceDiffReportForm" %>
<%@ page import = "org.apache.struts.action.ActionMessages" %>



		
	
<html:html>

<head>
	
	
		
	<title>
	<common:message bundle="proformaInvoiceDiffReport" key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.pagetitle"/>
	</title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/gpabilling/ProformaInvoiceDiffReport_Script.jsp"/>
	
</head>
<body class="ic-center" style="width:65%">
	
	

	
<bean:define id="form"
	 name="ProformaInvoiceDiffReportForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ProformaMailInvoiceDiffReportForm"
	 toScope="page" />


<jsp:include page="/jsp/includes/reports/printFrame.jsp" />

<business:sessionBean id="oneTimeVOs"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.proformainvoicediffreport"
	method="get" attribute="oneTimeVOs" />

<div class="iCargoContent" style="overflow:auto;">
 <ihtml:form action="mailtracking.mra.gpabilling.reports.proformainvoicescreenload.do">



<div class="ic-content-main">
<span class="ic-page-title"><common:message bundle="proformaInvoiceDiffReport" key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.title"/></span>
	<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<h4><common:message bundle="proformaInvoiceDiffReport" key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.search" /></h4>
				</div>
				<div class=" ic-border">
					<div class="ic-row ">
						<div class="ic-input ic-mandatory ic-split-25">
							<label><common:message bundle="proformaInvoiceDiffReport" key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.functionpoint" /></label>
			
							 <ihtml:select componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_FUNCTIONPOINT" property="functionPoint">

								<logic:present name="oneTimeVOs">
									<bean:define id="onetime" name="oneTimeVOs" toScope="page"/>
									<ihtml:option value=""></ihtml:option>
									<logic:iterate id="onetmvo" name="onetime">
									<bean:define id="onetimevo" name="onetmvo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" />
									 <% String oneTimeCode = "";
										  oneTimeCode= onetimevo.getFieldValue();
										  if((("G").equals(oneTimeCode))){%>

									<ihtml:option value="<%= onetimevo.getFieldValue() %>"><%= onetimevo.getFieldDescription() %></ihtml:option>

									<%}%>
									</logic:iterate>
								</logic:present>
							 </ihtml:select>
						</div>

			 <div class="ic-input ic-mandatory ic-split-50">
			  <fieldset class="ic-field-set">
			  <legend ><common:message key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.legendperiod"/></legend>
				
				  <div class="ic-row ">
						<div class="ic-input  ic-mandatory  ic-split-50">
							<label><common:message  key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.datefrom" scope="request"/></label>
							<ibusiness:calendar property="fromDate" id="incalender1" type="image"
							componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_FROMDATE" onblur="autoFillDate(this)"/>
						</div>
					<div class="ic-input  ic-mandatory  ic-split-50">
						<label><common:message  key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.dateto" scope="request"/></label>
						<ibusiness:calendar property="toDate" id="incalender2" type="image"
						componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_TODATE"  onblur="autoFillDate(this)"/>
				     </div>
					 </div>
				 </fieldset>
			  </div>
			  <div class="ic-input ic-split-25">
				<label><common:message  key="mailtracking.mra.gpabilling.proforminvoicereport.lbl.country" scope="request"/></label>
				<ihtml:text property="country" componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_COUNTRY" name="ProformaInvoiceDiffReportForm" maxlength="3"/>
				<img name="countryLOV" id="countryLOV" value="countryLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18"/>
			  </div>

		        </div>
		     	</div></div>




				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_PRINT" property="btnPrint">
						  <common:message key="mailtracking.mra.gpabilling.proforminvoicereport.btn.btprint" />
							</ihtml:nbutton>
						<ihtml:nbutton componentID="MRA_GPABILLING_REPORTS_PROFORMAINVOICE_CLOSE" property="btnClose">
						  <common:message key="mailtracking.mra.gpabilling.proforminvoicereport.btn.btclose" />
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

