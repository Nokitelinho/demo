<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name  : Uld
* File Name			: ViewInvoice.jsp
* Date				: 30-Jan-2006
* Author(s)			: A-2001
********************************************************************
--%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
<title>
	<common:message bundle="repairinvoice" key="uld.defaults.repairinvoice.icargorepairInvoice" />
</title>
<meta name="decorator" content="mainpanel">
<common:include src="/js/uld/defaults/RepairInvoice_Script.jsp" type="script"/>
</head>
<body >
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<business:sessionBean
						id="uldRepairDetailsVOs"
						moduleName="uld.defaults"
						screenID="uld.defaults.repairinvoice"
						method="get"
						attribute="uldRepairDetailsVO" />
		<bean:define id="form"
					 name="repairInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm"
					 toScope="page" />
		<div class="iCargoContent ic-center" id="pageDiv" style="overflow:auto;height:70%;width:70%">
			<ihtml:form action="uld.defaults.viewrepairinvoice.do">
			<ihtml:hidden property="displayPage"/>
			<ihtml:hidden property="lastPageNum"/>
			<ihtml:hidden property="currentPage"/>
	        <ihtml:hidden property="totalRecords"/>
			<ihtml:hidden property="closeStatus"/>
			<ihtml:hidden property="airlineBaseCurrency"/>
			 <div class="ic-content-main" style="ic-centre">
				<span class="ic-page-title ic-display-none">
					Repair Invoice
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
									<label>	<common:message key="uld.defaults.repairinvoice.InvoiceRefid" /></label>
									<ihtml:text property="invoiceRefNo"  componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_INVOICEREFID" name="repairInvoiceForm" maxlength="12" />
								</div>
								<div class="ic-input ic-split-25">
									<label>	 <common:message key="uld.defaults.repairinvoice.invoiceDate" /></label>
									<ihtml:text property="invoicedDate"  componentID="CAL_ULD_DEFAULTS_REPAIRINVOICE_INVOICEDATE" name="repairInvoiceForm" maxlength="11" />
								</div>
								<div class="ic-input ic-split-25">
									<label>	<common:message key="uld.defaults.repairinvoice.Invoiceto" /></label>
									<ihtml:text property="invoicedToCode"  componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_INVOICETOCODE" name="repairInvoiceForm" maxlength="3" />
								</div>
								<div class="ic-input ic-split-25">
									<label>	<common:message key="uld.defaults.repairinvoice.name" /></label>
									<ihtml:text property="name"  componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_INVOICETONAME" name="repairInvoiceForm" />
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
										<td><common:message key="uld.defaults.repairinvoice.uldnumber" /></td>
										<td><common:message key="uld.defaults.repairinvoice.repairhead" /></td>
										<td><common:message key="uld.defaults.repairinvoice.repairstation" /></td>
										<td><common:message key="uld.defaults.repairinvoice.damagerefno" /></td>
										<td><common:message key="uld.defaults.repairinvoice.actualamount" />(<bean:write  name="repairInvoiceForm" property="airlineBaseCurrency"/>)</td>
										<td><common:message key="uld.defaults.repairinvoice.waived" />(<bean:write  name="repairInvoiceForm" property="airlineBaseCurrency"/>)</td>
										<td><common:message key="uld.defaults.repairinvoice.invoicedamount" />(<bean:write  name="repairInvoiceForm" property="airlineBaseCurrency"/>)</td>
										<td ><common:message key="uld.defaults.repairinvoice.remarks" /></td>
								 </thead>	
								 <tbody>
                                            <logic:present name="uldRepairDetailsVOs">
											<logic:iterate id="uldRepairDetailsVO" name="uldRepairDetailsVOs"  type="ULDRepairInvoiceDetailsVO" indexId="index">
											<common:rowColorTag index="index">
											<tr>
												<% String style="iCargoEditableTextFieldRowColor1";
												   if( index%2 == 0 ) {
												    	style="background :#F6F7F9;border : 0px solid #77879D;";
												    }
												    else {
												    	style="background :#EDF0F4;border : 0px solid #77879D;";
												    }
												   %>
		                                        <td  class="iCargoTableDataTd" >
                                                  	<logic:present name="uldRepairDetailsVO" property="uldNumber">
												  			<bean:write name="uldRepairDetailsVO" property="uldNumber" />
						   							</logic:present>
                                                </td>
                                                <td  class="iCargoTableDataTd">
                                                  	<logic:present name="uldRepairDetailsVO" property="repairHead">
												  		<bean:write name="uldRepairDetailsVO" property="repairHead" />
						   						  	</logic:present>
                                                </td>
                                                <td  class="iCargoTableDataTd">
                                                	<logic:present name="uldRepairDetailsVO" property="repairStation">
												  		<bean:write name="uldRepairDetailsVO" property="repairStation" />
						   						  	</logic:present>
						   						 </td>
                                                <td  class="iCargoTableDataTd" align="center">
                                                  	<logic:present name="uldRepairDetailsVO" property="damageRefNumbers">
                                                  		<bean:define id="damageRefNumbers" name="uldRepairDetailsVO" property="damageRefNumbers"/>
                                                  		<logic:iterate id="refNumber" name="damageRefNumbers" indexId="indexId">
                                                  		<logic:equal name="indexId" value="0">
                                                  			<bean:write name="refNumber"/>
                                                  		</logic:equal>
                                                  		<logic:notEqual name="indexId" value="0">
                                                  			, <bean:write name="refNumber"/>
                                                  		</logic:notEqual>
												  		</logic:iterate>
						   						  	</logic:present>
						   						 </td>
                                                 <td  align="center" class="iCargoTableDataTd">
                                                  	<logic:present name="uldRepairDetailsVO" property="actualAmount">
														<bean:write name="uldRepairDetailsVO" property="actualAmount" />
														<input type="hidden" name="actualAmounts" value="<%=uldRepairDetailsVO.getActualAmount()%>"/>
					   								</logic:present>
						   						 </td>
												 <td  align="center" class="iCargoTableDataTd">
												   	<logic:present name="uldRepairDetailsVO" property="waivedAmount">
												   		<ihtml:text property="waivedAmounts" style="<%=style%>" value="<%=Double.toString(uldRepairDetailsVO.getWaivedAmount())%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_WAIVEDAMOUNT" maxlength="10" />
					   								</logic:present>
						   						  	 <logic:notPresent name="uldRepairDetailsVO" property="waivedAmount">
						   						  	 	<ihtml:text property="waivedAmounts" value=" " style="<%=style%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_WAIVEDAMOUNT" maxlength="17" />
												   	</logic:notPresent>
												 </td>
												 <td  align="center" class="iCargoTableDataTd">
												 	<logic:present name="uldRepairDetailsVO" property="invoicedAmount">
												 		<ihtml:text property="invoicedAmounts" style="<%=style%>" value="<%=Double.toString(uldRepairDetailsVO.getInvoicedAmount())%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_WAIVEDAMOUNT" maxlength="10" />
					   								</logic:present>
												 	<logic:notPresent name="uldRepairDetailsVO" property="invoicedAmount">
														<ihtml:text property="invoicedAmounts" value=" " style="<%=style%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_INVOICEDAMOUNT" maxlength="17" />
													</logic:notPresent>
                                                </td>
												<td  align="center" class="iCargoTableDataTd">
                                                  <logic:present name="uldRepairDetailsVO" property="repairRemarks">
												   		<ihtml:text property="remarks" style="<%=style%>" value="<%=uldRepairDetailsVO.getRepairRemarks()%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_REMARKS" maxlength="500" />
					   								</logic:present>
						   						  	 <logic:notPresent name="uldRepairDetailsVO" property="repairRemarks">
						   						  	 	<ihtml:text property="remarks" value="   "  style="<%=style%>" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_REMARKS" maxlength="500" />
													</logic:notPresent>
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
						<div class="ic-button-container">
							 <ihtml:nbutton property="btShowSummary" componentID="BTN_ULD_DEFAULTS_REPAIRINVOICE_SHOWSUMMARY">
										<common:message key="uld.defaults.repairinvoice.showsummary" />
							</ihtml:nbutton>
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input-round-border">
							<div class="ic-input ic-split-30">
								<label>	Total Amount</label>
								<ihtml:text property="totalAmount" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_TOTALAMOUNT" maxlength="17" />
							</div>
							<div class="ic-input ic-split-30">
								<label>	Total Waived</label>
								<ihtml:text property="totalWaived" componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_TOTALWAIVED" maxlength="17" />
							</div>
							<div class="ic-input ic-split-40">
								<label>	Total Invoiced</label>
								<ihtml:text property="totalInvoiced"  componentID="TXT_ULD_DEFAULTS_REPAIRINVOICE_TOTALINVOICED" maxlength="17" />
							</div>
						</div>
					</div>
				 </div>
				 <div class="ic-foot-container" >
					<div class="ic-button-container">
						   <ihtml:nbutton property="btPrint" componentID="BTN_ULD_DEFAULTS_REPAIRINVOICE_PRINT">
							<common:message key="Print" />
						</ihtml:nbutton>
                        <ihtml:nbutton property="btSave" componentID="BTN_ULD_DEFAULTS_REPAIRINVOICE_SAVE">
								<common:message key="Save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_REPAIRINVOICE_CLOSE">
								<common:message key="Close" />
						</ihtml:nbutton>
					</div>
				</div>
			 </div>
			</ihtml:form>
		</div>
</body>
</html:html>