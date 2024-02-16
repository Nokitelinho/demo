<%--
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: Generate Invoice.jsp
* Date				: 16-Feb-2006
* Author(s)			: A-6875
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<html:html locale="true">
<head>
		
			
	
<title>
	<common:message bundle="generateinvoice" key="uld.defaults.generateinvoice.icargogenerateInvoice" />
</title>
<meta name="decorator" content="popup_panel">
<common:include src="/js/uld/defaults/GenerateInvoice_Script.jsp" type="script"/>

</head>
<body style="overflow:auto;">
	
	<bean:define id="form"
			 name="generateInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateInvoiceForm"
		 toScope="page" />

	<div class="iCargoPopUpContent" style="height:160px;width:700px;">
		<ihtml:form action="/uld.defaults.generateinvoicescreenload.do" styleclass="ic-main-form">

		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="uldNumbers"/>
		<ihtml:hidden property="txnRefNos"/>
		<ihtml:hidden property="txnType"/>
		<ihtml:hidden property="statusFlag"/>
		<ihtml:hidden property="invoiceId"/>
		<ihtml:hidden property="txndates"/>
		<ihtml:hidden property="partyTypeFlag"/>
		<ihtml:hidden property="hiddenDmgAmt"/>
		<ihtml:hidden property="hiddenWaiver"/>
		<!-- Added by A-7426 as part of ICRD-196074 starts -->
		<ihtml:hidden property="waiver"/>
		<ihtml:hidden property="demAmt"/>
		<!-- Added by A-7426 as part of ICRD-196074 ends -->
             <div class="ic-content-main">
			    <span class="ic-page-title">
                     <common:message  key="uld.defaults.generateinvoice.generateInvoice" />				
				</span>
				<div class="ic-main-container">
				    <div class="ic-border">
					    <div class="ic-row">
						    <div class="ic-input ic-mandatory ic-split-36 ">
								<label>
									<common:message  key="uld.defaults.generateinvoice.invoiceDate" />
								</label>
								    <business:calendar type="image" property="invoicedDate" value="<%=form.getInvoicedDate()%>" maxlength="11" />
							</div>
							<div class="ic-input ic-mandatory ic-split-40 ">
								<label>
								    <common:message  key="uld.defaults.generateinvoice.Invoiceto" />
								</label>
								    <ihtml:text property="invoicedToCode"  componentID="TXT_ULD_DEFAULTS_GENERATEINVOICE_INVOICETOCODE" name="generateInvoiceForm" />
	                               <img height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" id="airlinelov" alt="Airline LOV"/>
							</div>
							<div class="ic-input ic-mandatory ic-split-26">
								<label>
								     <common:message  key="uld.defaults.generateinvoice.name" />
								</label>
								      <ihtml:text property="name"  componentID="TXT_ULD_DEFAULTS_GENERATEINVOICE_INVOICETONAME" name="generateInvoiceForm" styleClass="iCargoTextFieldSmall" maxlength="15" />
							</div>
						</div>
						<div class="ic-row">
						    <div class="ic-input ic-split-80 ic-label-15">
								<label>
									<common:message  key="uld.defaults.generateinvoice.remarks" />
								</label>
							 <ihtml:textarea property="remarks"  rows="2" cols="50" componentID="TXT_ULD_DEFAULTS_GENERATEINVOICE_REMARKS" name="generateInvoiceForm" />
							   
							</div>
							<!--div class="ic-input ic-split-20">
							<div class="ic-button-container">
								<ihtml:nbutton property="btShowTotal" componentID="BTN_ULD_DEFAULTS_GENERATEINVOICE_SHOWTOTAL">
									<common:message  key="ShowTotal" />
								</ihtml:nbutton>
								</div>
							</div -->
							</div>
						</div>
					</div>
				</div>
				<div class="ic-foot-container">
				   <div class="ic-button-container">
				        <ihtml:nbutton property="btGenerate" componentID="BTN_ULD_DEFAULTS_GENERATEINVOICE_GENERATE">
				 		    <common:message  key="Generate" />
				        </ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_GENERATEINVOICE_CLOSE">
							<common:message  key="Close" />
						 </ihtml:nbutton>
				   </div>
				</div>
			</div>
			    
	</ihtml:form>
   	</div>

			
		
	</body>
</html:html>

