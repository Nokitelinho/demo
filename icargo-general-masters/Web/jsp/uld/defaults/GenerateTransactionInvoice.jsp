<%--
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: Generate Transaction Invoice.jsp
* Date				: 16-Feb-2006
* Author(s)			: A-2001
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
	<!DOCTYPE html>	
<html:html locale="true">
<head>
		
	<%@ include file="/jsp/includes/customcss.jsp" %>
<title>
	<common:message bundle="generatetransactioninvoice" key="uld.defaults.generatetransactioninvoice.icargogenerateInvoice" />
</title>
<meta name="decorator" content="popup_panel">
<common:include src="/js/uld/defaults/GenerateTransactionInvoice_Script.jsp" type="script"/>

</head>
<body style="overflow:auto;">

	<business:sessionBean
			id="uldTransactionDetailsVOs"
			moduleName="uld.defaults"
			screenID="uld.defaults.generatetransactioninvoice"
			method="get"
			attribute="uldTransactionDetailsVO" />

	<bean:define id="form"
			 name="generateTransactionInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm"
		 toScope="page" />

	<div class="iCargoPopUpContent" style="height:200px;width:680px;">
	<ihtml:form action="/uld.defaults.generatetransactioninvoicescreenload.do">

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
	<ihtml:hidden property="originalDmgAmt"/>
	<ihtml:hidden property="originalWaiver"/>
	<ihtml:hidden property="prevIndex"/>
<div class="ic-content-main">
<span class="ic-page-title ic-display-none">
				 <common:message  key="uld.defaults.generatetransactioninvoice.generateInvoice" />
			</span>
</div>
	<div class="ic-head-container">
	
	</div>
	<div class="ic-main-container">
		<div class="ic-row">
					<div class="ic-input ic-split-33 ic-label-38">
						<label> <common:message  key="uld.defaults.generatetransactioninvoice.invoiceDate" /><span class="iCargoMandatoryFieldIcon">*</span></label>
						<business:calendar type="image" property="invoicedDate" value="<%=form.getInvoicedDate()%>" maxlength="11" />
					</div>
					<div class="ic-input ic-split-33 ic-label-50">
						<label>  <common:message  key="uld.defaults.generatetransactioninvoice.Invoiceto" /><span class="iCargoMandatoryFieldIcon">*</span></label> 
						<ihtml:text property="invoicedToCode"  componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_INVOICETOCODE" name="form"  style="width:50px"/>
	    	<img src="<%=request.getContextPath()%>/images/lov.gif" height="16" width="16" id="airlinelov" name="airlinelov" alt="Airline LOV"/>
					</div>
					
						<div class="ic-input ic-split-34 ic-label-18">
						<label>   <common:message  key="uld.defaults.generatetransactioninvoice.name" /><span class="iCargoMandatoryFieldIcon">*</span></label> 
						<ihtml:text property="name"  componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_INVOICETONAME" name="form" maxlength="15"  style="width:180px"/>
					</div>
				</div>
					<div class="ic-row">
		<div class="ic-input ic-split-100 ic-label-10">
						<label>  <common:message  key="uld.defaults.generatetransactioninvoice.remarks" /></label>
						 <ihtml:textarea property="remarks"  rows="2" cols="80" componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_REMARKS" name="form" />
					</div>
		</div>
		<div class="ic-row ic-border">
		<div class="ic-input ic-split-26 ic-label-30">
						<label>   <common:message  key="uld.defaults.generatetransactioninvoice.uldnumber" /></label>
						 <ihtml:select property="uldNumber" componentID="CMB_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_ULDNUMBER">
				  						 <logic:present name="uldTransactionDetailsVOs">
				  							<logic:iterate id="uldTransactionVO" name="uldTransactionDetailsVOs">
				  								<bean:define id="uldNumber" name="uldTransactionVO" property="uldNumber" />
				  								<html:option value="<%=(String)uldNumber%>">
				  									<%=(String)uldNumber%>
				  								</html:option>

				  							</logic:iterate>
				  						</logic:present>
				  					</ihtml:select>
					</div>
		
		
			
		<div class="ic-input ic-split-22 ic-label-50">
						<label>  <common:message  key="uld.defaults.generatetransactioninvoice.totalDemmAmt" /><span class="iCargoMandatoryFieldIcon">*</span></label>
						 <ihtml:text property="demAmt"  componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_TOTDEMAMT" name="form" maxlength="15" readonly="true"/>
					</div>
	
		
		<div class="ic-input ic-split-26 ic-label-30">
						<label>  <common:message  key="uld.defaults.generatetransactioninvoice.waiver" /><span class="iCargoMandatoryFieldIcon">*</span></label>
						 <ihtml:text property="waiver"  componentID="TXT_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_WAIVER" name="form" />
					</div>
		
		
		<div class="ic-input ic-split-22 ic-label-20">
						<div class="ic-button-container">
						<ihtml:nbutton property="btShowTotal" componentID="BTN_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_SHOWTOTAL">
				  					<common:message  key="ShowTotal" />
				  			     </ihtml:nbutton>
							</div>	 
					</div>
					</div>
		
		
	</div>
	
	
<div class="ic-foot-container"  style="bottom:10px;">
	<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton property="btGenerate" componentID="BTN_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_GENERATE">
				 		<common:message  key="Generate" />
				 </ihtml:nbutton>
	            <ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_GENERATETRANSACTIONINVOICE_CLOSE">
						<common:message  key="Close" />
				 </ihtml:nbutton>
					</div>
				</div>
	</div>
	</ihtml:form>
   	</div>

		
</body>
</html:html>

