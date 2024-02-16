<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name  : Uld
* File Name			: ViewInvoice.jsp
* Date				: 30-Jan-2006
* Author(s)			: A-2001
********************************************************************
--%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
<title>
	<common:message bundle="viewinvoice" key="uld.defaults.viewinvoice.icargoviewInvoice" />
</title>
<meta name="decorator" content="mainpanel">
<common:include src="/js/uld/defaults/ViewInvoice_Script.jsp" type="script"/>
</head>
<body id="bodyStyle">
		<business:sessionBean
				id="uldTransactionDetailsVOs"
				moduleName="uld.defaults"
				screenID="uld.defaults.viewinvoice"
				method="get"
		attribute="uldTransactionDetailsVO" />
		<business:sessionBean
			id="oneTimeValues"
			moduleName="uld.defaults"
			screenID="uld.defaults.listinvoice"
			method="get"
			attribute="oneTimeValues" />
		<bean:define id="form"
				 name="viewInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ViewInvoiceForm"
		 toScope="page" />
		<div class="iCargoContent ic-center" id="pageDiv" style="overflow:auto;height:70%;width:70%">
		<ihtml:form action="/uld.defaults.viewinvoice.do">
			<ihtml:hidden property="displayPage"/>
		    <ihtml:hidden property="lastPageNum"/>
		    <ihtml:hidden property="currentPage"/>
	        <ihtml:hidden property="totalRecords"/>
	        <ihtml:hidden property="airlineBaseCurrency"/>
          <div class="ic-content-main" style="ic-centre">	
			<span class="ic-page-title ic-display-none">
				<common:message key="uld.defaults.viewinvoice.viewInvoice" />
		    </span>
		  <div class="ic-head-container">
			<div class="ic-row">
				<div class="ic-button-container">
					<common:popuppaginationtag
							pageURL="javascript:NavigateZoneDetails('lastPageNum','displayPage')"
							linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
							displayPage="<%=form.getDisplayPage()%>"
							totalRecords="<%=form.getTotalRecords()%>" />
				</div>
			</div>
			<div class="ic-row">
					<div class="ic-input-round-border">
						<div class="ic-input ic-split-25">
							<label>	<common:message key="uld.defaults.viewinvoice.InvoiceRefid" /></label>
							<ihtml:text property="invoiceRefNo"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_INVOICEREFID" name="viewInvoiceForm" maxlength="12" />
						</div>
						<div class="ic-input ic-split-25">
							<label>	<common:message key="uld.defaults.viewinvoice.invoiceDate" /></label>
							 <ihtml:text property="invoicedDate"  componentID="CAL_ULD_DEFAULTS_VIEWINVOICE_INVOICEDATE" name="viewInvoiceForm" maxlength="11" />
						</div>
						<div class="ic-input ic-split-25">
							<label>	<common:message key="uld.defaults.viewinvoice.Invoiceto" /></label>
							 <ihtml:text property="invoicedToCode"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_INVOICETOCODE" name="viewInvoiceForm" maxlength="3" />
						</div>
						<div class="ic-input ic-split-25">
							<label>	<common:message key="uld.defaults.viewinvoice.name" /></label>
							 <ihtml:text property="name"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_INVOICETONAME" name="viewInvoiceForm" />
						</div>
					</div>
			</div>
		  </div>
		   <div class="ic-main-container">
				<div class="ic-row">
					<h3>Invoice Details</h3>
				</div>
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:380px">
						<table class="fixed-header-table">
							 <thead>
								 <tr class="iCargoTableHeadingLeft">
									<td width="6%"><common:message key="uld.defaults.viewinvoice.uldnumber" /></td>
                                    <td width="5%"><common:message key="uld.defaults.viewinvoice.station" /></td>
                                    <td width="5%"><common:message key="uld.defaults.viewinvoice.partytype" /></td>
                                    <td width="5%"><common:message key="uld.defaults.viewinvoice.partycode" /></td>
                                    <td width="10%"><common:message key="uld.defaults.viewinvoice.txndate" /></td>
                                    <td width="10%"><common:message key="uld.defaults.viewinvoice.returndate" /></td>
                                    <td width="10%"><common:message key="uld.defaults.viewinvoice.demerageAccruded" />(<bean:write  name="viewInvoiceForm" property="airlineBaseCurrency"/>)</td>
                                    <td width="10%">Other Charges</td>
                                    <td width="10%">Tax</td>
                                    <td width="10%"><common:message key="uld.defaults.viewinvoice.waived" />(<bean:write  name="viewInvoiceForm" property="airlineBaseCurrency"/>)</td>
                                    <td width="8%"><common:message key="uld.defaults.viewinvoice.demurrageamount" />(<bean:write  name="viewInvoiceForm" property="airlineBaseCurrency"/>)</td>
                                    <td width="15%"><common:message key="uld.defaults.viewinvoice.remarks" /></td>
								 </tr>
							 </thead>	
							 <tbody>
								<logic:present name="uldTransactionDetailsVOs">
											<logic:iterate id="uldTransactionDetailsVO" name="uldTransactionDetailsVOs"  type="ULDTransactionDetailsVO" indexId="index">
											<common:rowColorTag index="index">
											<tr>
                                                <td  class="iCargoTableDataTd" >
                                                   <logic:present name="uldTransactionDetailsVO" property="uldNumber">
												  			<bean:write name="uldTransactionDetailsVO" property="uldNumber" />
						   							</logic:present>
                                                </td>
                                                <td  class="iCargoTableDataTd">
                                                   <logic:present name="uldTransactionDetailsVO" property="transactionStationCode">
												  			<bean:write name="uldTransactionDetailsVO" property="transactionStationCode" />
						   							</logic:present>
						   						</td>
                                                <td  class="iCargoTableDataTd">
                                                <logic:present name="uldTransactionDetailsVO" property="partyType">
												<logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="uld.defaults.PartyType">
														<bean:define id="parameterValues" name="oneTimeValue" property="value" />
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<logic:equal name="uldTransactionDetailsVO" property="partyType" value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
															</logic:equal>
															</logic:present>
															</logic:iterate>
													</logic:equal>
												</logic:iterate>
												</logic:present>
												</logic:present>
						   						</td>
                                                <td  class="iCargoTableDataTd" align="center">
                                                 <logic:present name="uldTransactionDetailsVO" property="toPartyCode">
												  			<bean:write name="uldTransactionDetailsVO" property="toPartyCode" />
						   						</logic:present>
						   						</td>
                                                <td class="iCargoTableDataTd" align="center">
                                                  <logic:present name="uldTransactionDetailsVO" property="transactionDate">
												  		<%String txnDate = "";
														   if(uldTransactionDetailsVO.getTransactionDate() != null) {
																txnDate = TimeConvertor.toStringFormat(
																			uldTransactionDetailsVO.getTransactionDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
															}
														   %>
						   						<%=txnDate%>
						   						</logic:present>
						   						</td>
                                                <td  class="iCargoTableDataTd" align="center">
                                                   <logic:present name="uldTransactionDetailsVO" property="returnDate">
												  		<%String returnDate = "";
														   if(uldTransactionDetailsVO.getReturnDate() != null) {
																returnDate = TimeConvertor.toStringFormat(
																			uldTransactionDetailsVO.getReturnDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
															}
														   %>
						   						<%=returnDate%>
						   						</logic:present>
						   						</td>
                                                <td  align="center" class="iCargoTableDataTd">
                                                  	 <logic:present name="uldTransactionDetailsVO" property="demurrageAmount">
															<bean:write name="uldTransactionDetailsVO" property="demurrageAmount" localeformat="true" />
						   							</logic:present>
						   						</td>
						   						<td  align="center" class="iCargoTableDataTd">
													 <logic:present name="uldTransactionDetailsVO" property="otherCharges">
															<bean:write name="uldTransactionDetailsVO" property="otherCharges" />
													</logic:present>
						   						</td>
						   						<td  align="center" class="iCargoTableDataTd">
													 <logic:present name="uldTransactionDetailsVO" property="taxes">
															<bean:write name="uldTransactionDetailsVO" property="taxes" />
													</logic:present>
						   						</td>
                                                <td  align="center" class="iCargoTableDataTd">
                                                   <logic:present name="uldTransactionDetailsVO" property="waived">
												  		<bean:write name="uldTransactionDetailsVO" property="waived" localeformat="true"  />
						   						</logic:present>
                                                </td>
                                                <td  align="center" class="iCargoTableDataTd">
                                                 <logic:present name="uldTransactionDetailsVO" property="total">
												  		<bean:write name="uldTransactionDetailsVO" property="total" localeformat="true" />
						   						</logic:present>
						   						</td>
                                                <td  align="center" class="iCargoTableDataTd">
                                                  <logic:present name="uldTransactionDetailsVO" property="transactionRemark">
												  		<bean:write name="uldTransactionDetailsVO" property="transactionRemark" />
						   						</logic:present>
						   						</td>
                                              </tr>
                                              </common:rowColorTag>
                                              </logic:iterate>
                                              </logic:present>
							 </tbody>
						</table>
					</div>
				</div>
				<div class="ic-row">
					<h3>Grand Total</h3>
				</div>
				<div class="ic-row">
				<div class="ic-input-round-border">
					<div class="ic-input ic-split-30">
						<label>	Demurrage Accrued</label>
						<ihtml:text property="demerageAccured"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_DEMERAGEACCURED" name="viewInvoiceForm"  />
					</div>
					<div class="ic-input ic-split-30">
						<label>	Total Waived</label>
						<ihtml:text property="waivedAmount"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_WAIVEDAMOUNT" name="viewInvoiceForm"  />
					</div>
					<div class="ic-input ic-split-40">
						<label>	Demurrage Amount</label>
						<ihtml:text property="demerageAmount"  componentID="TXT_ULD_DEFAULTS_VIEWINVOICE_DEMERAGEAMOUNT" name="viewInvoiceForm" />
					</div>
				</div>
				</div>
		   </div>
		   <div class="ic-foot-container" >
				<div class="ic-button-container">
					 <ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_VIEWINVOICE_CLOSE">
								<common:message key="Close" />
				 		</ihtml:nbutton>
				</div>
		   </div>
       </div>
		</ihtml:form>
		</div>
</body>
</html:html>