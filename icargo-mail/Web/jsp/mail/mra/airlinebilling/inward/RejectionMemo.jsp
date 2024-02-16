<%--
* Project	 		: iCargo
* Module Code & Name: mra-airlinebilling
* File Name			: RejectionMemo.jsp
* Date				: 18-May-2007
* Author(s)			: A-2408
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>



			
	
<html:html>
<head>

		<%@ include file="/jsp/includes/customcss.jsp" %> 
	



<title><bean:message bundle="rejectionmemobundle" key="mra.airlinebilling.rejectionmemo.title" /></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/RejectionMemo_Script.jsp" />

</head>

<body>
	
	



<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<bean:define id="form" name="MRARejectionMemoForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm" toScope="page" />
  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="pageDiv" style="overflow:auto;width:100%;height:100%">
  <ihtml:form action="/mailtracking.mra.airlinebilling.inward.rejectionmemo.onScreenLoad.do">

  <ihtml:hidden property="fromScreenFlag"/>
	<ihtml:hidden property="screenFlag"/>
	<ihtml:hidden property="cn66CloseFlag"/>
	<ihtml:hidden property="invokingScreen"/>
	<ihtml:hidden property="lovClicked" />
	  <business:sessionBean id="REJECTIONMEMOVO"
	  moduleName="mailtracking.mra.airlinebilling"
	  screenID="mailtracking.mra.airlinebilling.inward.rejectionmemo"
	  method="get"
	  attribute="rejectionMemoVO" />
	  
	  	<business:sessionBean id="KEY_ONETIMEVALUES"
		 moduleName="mailtracking.mra.airlinebilling"
		 screenID="mailtracking.mra.airlinebilling.inward.rejectionmemo"
		 method="get"
		 attribute="oneTimeVOs" />
		 
		 
		<div class="ic-content-main" >
			<span class="ic-page-title ic-display-none">
				<common:message key="mra.airlinebilling.rejectionmemo.pagetitle" scope="request"/></td>
			</span>
			<div class="ic-head-container" >
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-30 ">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.rejectionmemono" scope="request"/>
								</label>
								<ihtml:text property="memoCode" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_MEMOCODE" tabindex="1" style="width:150px" maxlength="18"/>
								<div class= "lovImg"><img name="memoCodeLov" id="memoCodeLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-30 ">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.invoiceno" scope="request"/>
								</label>
								<ihtml:text property="invoiceNumber" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_INVOICENO" tabindex="2" style="width:150px" maxlength="18"/>
								<div class= "lovImg"><img name="invoiceNumberLov" id="invoiceNumberLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.dsn" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="dsn">
              					<bean:define id="dsn" name="REJECTIONMEMOVO" property="dsn"/>
									<ihtml:text property="dsn" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DSN" value="<%=(String)dsn%>" maxlength="4" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" name="dsnlov" height="22" width="22" alt=""/></div>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="dsn">
									<ihtml:text property="dsn" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DSN" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" name="dsnlov" height="22" width="22" alt=""/></div>
								</logic:notPresent>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_LIST" tabindex="4" accesskey="L">
								<common:message key="mra.airlinebilling.rejectionmemo.btn.list" />
								</ihtml:nbutton>

								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_CLEAR" tabindex="5" accesskey="C">
								<common:message key="mra.airlinebilling.rejectionmemo.btn.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container" >
			<div class="ic-row">
					<div class="ic-input-container ic-border">
						<div class="ic-row ic-label-20">
							<div class="ic-input ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.airlinecode" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="airlineCode">
									<ihtml:text property="airlineCode" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_ARLINECODE" tabindex="-1"   maxlength="3" readonly="true"/>
								</logic:present>
								 <logic:notPresent name="REJECTIONMEMOVO" property="airlineCode">
									<ihtml:text property="airlineCode" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_ARLINECODE" tabindex="-1"   maxlength="3" readonly="true"/>
								</logic:notPresent>
								<logic:present name="REJECTIONMEMOVO" property="airlineIdentifier">
									<bean:define id="airlineIdentifier" name="REJECTIONMEMOVO" property="airlineIdentifier"/>
									<ihtml:hidden  property="airlineIdentifier" value="<%=String.valueOf(airlineIdentifier)%>"/>
								</logic:present>
								<logic:present name="REJECTIONMEMOVO" property="inwardClearancePeriod">
									<bean:define id="inwardClearancePeriod" name="REJECTIONMEMOVO" property="inwardClearancePeriod"/>
									<ihtml:hidden  property="inwardClearancePeriod" value="<%=String.valueOf(inwardClearancePeriod)%>"/>
								</logic:present>
							</div>
						
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.yourinvoiceno" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="inwardInvoiceNumber">
									<ihtml:text property="inwardInvoiceNumber" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_YOURINVNO" tabindex="-1"   maxlength="18" readonly="true"/>
									<bean:define id="inwardInvoiceNumber" name="REJECTIONMEMOVO" property="inwardInvoiceNumber"/>
									<ihtml:hidden  property="yourInvoiceNumber" value="<%=String.valueOf(inwardInvoiceNumber)%>"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="inwardInvoiceNumber">
									<ihtml:text property="yourInvoiceNumber" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_YOURINVNO" tabindex="-1"  maxlength="18" readonly="true"/>
								</logic:notPresent>
							</div>
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.dated" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="inwardInvoiceDate">
									<bean:define id="inwardInvoiceDate" name="REJECTIONMEMOVO" property="inwardInvoiceDate" />
									<%
									String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)inwardInvoiceDate).toCalendar(),"dd-MMM-yyyy");
									%>
									<ihtml:text property="inwardInvoiceDate" value="<%=assignedLocalDate%>" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_INVDATE" tabindex="-1" readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="inwardInvoiceDate">
									<ihtml:text property="yourInvoiceDate" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_INVDATE" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>

							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.monthofclearance" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="outwardClearancePeriod">
									<bean:define id="outwardClearancePeriod" name="REJECTIONMEMOVO" property="outwardClearancePeriod"/>
									<ihtml:text property="monthOfClearance" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_MONTHCLEARANCE" value="<%=(String)outwardClearancePeriod%>" tabindex="-1"  readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="outwardClearancePeriod">
									<ihtml:text property="monthOfClearance" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_MONTHCLEARANCE" value="" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.monthoftransaction" scope="request"/>
								</label>
								<ihtml:text property="monthOfTransaction" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_MONTHTRANSACTION" tabindex="-1"  readonly="true"/>
							</div>
						</div>
						<div class="ic-row ic-label-20">
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.origin" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="origin">
									<bean:define id="origin" name="REJECTIONMEMOVO" property="origin"/>
									<ihtml:text property="origin" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_ORIGIN" value="<%=(String)origin%>" tabindex="-1"  readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="origin">
									<ihtml:text property="origin" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_ORIGIN" value="" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.destination" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="destination">
									<bean:define id="destination" name="REJECTIONMEMOVO" property="destination"/>
									<ihtml:text property="destination" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DESTINATION" value="<%=(String)destination%>" tabindex="-1"  readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="destination">
									<ihtml:text property="destination" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DESTINATION" value="" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>
					
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.uplift" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="sectorFrom">
									<bean:define id="sectorFrom" name="REJECTIONMEMOVO" property="sectorFrom"/>
									<ihtml:text property="sectorFrom" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_UPLIFT" value="<%=(String)sectorFrom%>" tabindex="-1"  readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="sectorFrom">
									<ihtml:text property="sectorFrom" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_UPLIFT" value="" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>
							<div class="ic-input  ic-split-20 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.rejectionmemo.discharge" scope="request"/>
								</label>
								<logic:present name="REJECTIONMEMOVO" property="sectorTo">
								<bean:define id="sectorTo" name="REJECTIONMEMOVO" property="sectorTo"/>
								<ihtml:text property="sectorTo" name="REJECTIONMEMOVO" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DISCHARGE" value="<%=(String)sectorTo%>" tabindex="-1"  readonly="true"/>
								</logic:present>
								<logic:notPresent name="REJECTIONMEMOVO" property="sectorTo">
										<ihtml:text property="sectorTo" name="form" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_DISCHARGE" value="" tabindex="-1"  readonly="true"/>
								</logic:notPresent>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input-container ic-border marginT5">
						<div class="ic-row">
							<div class="ic-col-60" style="height:260px;">
								<div id="div1" class="tableContainer" style="top:30px;">
									<table class="fixed-header-table" style="height:70px;">
										<thead>
											 <tr class="ic-th-all">
												<th width="23%">
												<th width="13%">
												<th width="13%">
												<th width="13%">
												<th width="13%">
												<th width="13%">
												<th width="13%">

											</tr>
											<tr>
												<td width="60%" rowspan="3"></td>
												<td  width="40%"colspan="6" ><common:message key="mra.airlinebilling.rejectionmemo.rejthrclearance" scope="request"/></td>

											</tr>
											<tr>
												<td  colspan="2" ><common:message key="mra.airlinebilling.rejectionmemo.billed" scope="request"/></td>
												<td   colspan="2"><common:message key="mra.airlinebilling.rejectionmemo.accept" scope="request"/></td>
												<td   colspan="2"><common:message key="mra.airlinebilling.rejectionmemo.reject" scope="request"/></td>
											</tr>
											<tr>
												<td width="50%"><common:message key="mra.airlinebilling.rejectionmemo.curr" scope="request"/></td>
												<td  width="50%"><common:message key="mra.airlinebilling.rejectionmemo.amount" scope="request"/></td>
												<td  width="50%"><common:message key="mra.airlinebilling.rejectionmemo.curr" scope="request"/></td>
												<td  width="50%"><common:message key="mra.airlinebilling.rejectionmemo.amount" scope="request"/></td>
												<td width="50% "><common:message key="mra.airlinebilling.rejectionmemo.curr" scope="request"/></td>
												<td  width="50%"><common:message key="mra.airlinebilling.rejectionmemo.amount" scope="request"/></td>

											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="iCargoLabelRightAligned">
												<label><common:message key="mra.airlinebilling.rejectionmemo.currofcontract" scope="request"/></td></label>
												<td>
												<logic:present name="REJECTIONMEMOVO">
												<common:write name="REJECTIONMEMOVO" property="contractCurrencyCode"/>
												</logic:present>
												</td>
												<td>
												<logic:present name="REJECTIONMEMOVO" property="contractBilledAmount"><div align="right">
												<bean:define id="billedAmount" name="REJECTIONMEMOVO" property="contractBilledAmount"/>
												<ihtml:text property="billedAmount" value=""  maxlength="7" readonly="true" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILLEDAMT" tabindex="-1" style="text-align:right"/>
												</div></logic:present>
												<logic:notPresent name="REJECTIONMEMOVO" property="contractBilledAmount"><div align="right">
													<ihtml:text property="billedAmount" value="" maxlength="7" readonly="true" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILLEDAMT" tabindex="-1"  />
												</div></logic:notPresent>
												</td>
												<td><logic:present name="REJECTIONMEMOVO">
												<common:write name="REJECTIONMEMOVO" property="contractCurrencyCode"/></logic:present>
												</td>

												<td>

												<logic:present name="REJECTIONMEMOVO" property="contractAcceptedAmount"><div align="right">
													<bean:define id="contractAcceptedAmount" name="REJECTIONMEMOVO" property="contractAcceptedAmount"/>
													<ihtml:text property="acceptedAmount" value=""  maxlength="7" readonly="true" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_ACCEPTEDAMT" tabindex="6"  style="text-align:right"/>
													</div></logic:present>
													<logic:notPresent name="REJECTIONMEMOVO" property="contractBilledAmount"><div align="right">
														<ihtml:text property="acceptedAmount" value="" maxlength="7"  readonly="true" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_ACCEPTEDAMT" tabindex="6"  style="text-align:right"/>
													</div>
													</logic:notPresent>
												</td>

												<td><logic:present name="REJECTIONMEMOVO">
												<common:write name="REJECTIONMEMOVO" property="contractCurrencyCode"/></logic:present>
												</td>
												<td>
												<logic:present name="REJECTIONMEMOVO" property="contractRejectedAmount">
												<div align="right">
													<bean:define id="contractRejectedAmount" name="REJECTIONMEMOVO" property="contractRejectedAmount"/>
													<ihtml:text property="rejectedAmount" maxlength="7" readonly="true" value=""  componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_REJECTEDAMT"  tabindex="-1" style="text-align:right"/>
												</div>
												</logic:present>
												<logic:notPresent name="REJECTIONMEMOVO" property="contractRejectedAmount"><div align="right">
														<ihtml:text property="rejectedAmount" value="" readonly="true" maxlength="7"  componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_REJECTEDAMT" tabindex="-1"  />
												</div></logic:notPresent>

												</td>
											</tr>
											<tr class="iCargoTableDataRow2">
												<td class="iCargoLabelRightAligned">
													<label><common:message key="mra.airlinebilling.rejectionmemo.exgrateused" scope="request"/></label>
													</td>
													<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div></td>
													<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div></td>
													<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div>
												</td>
												<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div>
												</td>
												<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div>
												</td>
												<td><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div>
												</td>
											</tr>
											<tr class="iCargoTableDataRow1">
												<td class="iCargoLabelRightAligned">
												<label><common:message key="mra.airlinebilling.rejectionmemo.incurrofbilling" scope="request"/></label>
												</td>
												<td><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingCurrencyCode"/></logic:present></td>
												<td>
													<logic:present name="REJECTIONMEMOVO" property="billingBilledAmount">
														 <ibusiness:moneyEntry name="REJECTIONMEMOVO" id="billingBilledAmt" moneyproperty="billingBilledAmount"  property="billCurBilledAmount" formatMoney="false"   componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILBILLEDAMT" readonly="true"/>
													</logic:present>
													<logic:notPresent name="REJECTIONMEMOVO" property="billingBilledAmount">
														 <ibusiness:moneyEntry  id="billingBilledAmt"  property="billCurBilledAmount" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILBILLEDAMT" readonly="true"/>
													</logic:notPresent>
												</td>
												<td><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingCurrencyCode"/></logic:present></td>
												<td>
													<logic:present name="REJECTIONMEMOVO" property="billingAcceptedAmount">
														 <ibusiness:moneyEntry name="REJECTIONMEMOVO" id="billingAcceptedAmt" moneyproperty="billingAcceptedAmount"  property="bilCuracceptedAmount" formatMoney="false"   componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILACCEPTEDAMT" />
													</logic:present>
													<logic:notPresent name="REJECTIONMEMOVO" property="billingAcceptedAmount">
														 <ibusiness:moneyEntry  id="billingAcceptedAmt"  property="bilCuracceptedAmount" formatMoney="true" value="0.0"  componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILACCEPTEDAMT" />
													</logic:notPresent>
												</td>
												<td><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingCurrencyCode"/></logic:present></td>
												<td>
												<logic:present name="REJECTIONMEMOVO" property="billingRejectedAmount">
													 <ibusiness:moneyEntry name="REJECTIONMEMOVO" id="billingRejectedAmt" moneyproperty="billingRejectedAmount"  property="bilCurrejectedAmount" formatMoney="false"   componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILREJECTEDAMT" readonly="true"/>
												</logic:present>
												<logic:notPresent name="REJECTIONMEMOVO" property="billingRejectedAmount">
													 <ibusiness:moneyEntry  id="billingRejectedAmt"  property="bilCurrejectedAmount" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLING_BILREJECTEDAMT" readonly="true"/>
												</logic:notPresent>

												</td>
											</tr>
											<tr class="iCargoTableDataRow2">
												<td class="iCargoLabelRightAligned">
													<label><common:message key="mra.airlinebilling.rejectionmemo.exgrateused" scope="request"/></td></label>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
											</tr>
											<tr class="iCargoTableDataRow1">
												<td class="iCargoLabelRightAligned">
												<label><common:message key="mra.airlinebilling.rejectionmemo.incurrofclearance" scope="request"/></td></label>
												<td>&nbsp;<!--<logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceCurrencyCode"/></logic:present>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceBilledAmount"/></logic:present></div>--></td>
												<td>&nbsp;<!--<logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceCurrencyCode"/></logic:present>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceAcceptedAmount"/></logic:present></div>--></td>
												<td>&nbsp;<!--<logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceCurrencyCode"/></logic:present>--></td>
												<td>&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="clearanceRejectedAmount"/></logic:present></div>--></td>
											</tr>
										</tbody>

									</table>
									</div>
								</div>

								<div class="ic-col-40">
									<div class="ic-row ic-center">
										<h3><common:message key="mra.airlinebilling.rejectionmemo.reasons" scope="request"/></h3>
									</div>
									<div class="ic-input-container ic-border" style="height:230px;">
										<div>
											<label>
												<common:message key="mra.airlinebilling.rejectionmemo.remarks" scope="request"/>
											</label>
											<logic:present name="REJECTIONMEMOVO" property="remarks">
												<ihtml:textarea name="REJECTIONMEMOVO" property="remarks" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_REMARKS" tabindex="21" cols="30" rows="7" >
												</ihtml:textarea>
											</logic:present>
											<logic:notPresent name="REJECTIONMEMOVO" property="remarks">
												<ihtml:textarea name="form" property="remarks" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_REMARKS" tabindex="21"  cols="30" rows="7" >
												</ihtml:textarea>
											</logic:notPresent>
										</div>
									</div>
									
									
									<fieldset class="ic-field-set">
										<legend ><common:message key="mra.airlinebilling.rejectionmemo.attachments" scope="request"/></legend>
										<div id="divDocpanel" style="height:50%;width:100%;">
												<div class="ic-row">
													<div class="ic-button-container">
													<a href="#" class="iCargoLink" name="addLink"  id="addLink" enabled="true" >
																<common:message key="mra.airlinebilling.rejectionmemo.add" scope="request"/> </a> |
													<a href="#" class="iCargoLink"  name="deleteLink" id="deleteLink">
																<common:message key="mra.airlinebilling.rejectionmemo.delete" scope="request"/> </a>
								</div>
												</div>
												<div class="ic-row">	     	       
															<logic:present name="REJECTIONMEMOVO" property="attachmentIndicator">
																<bean:define id="attachmentIndicator" name="REJECTIONMEMOVO" property="attachmentIndicator" />
																	   
															<logic:equal name="attachmentIndicator" value="Y" >
															<input type="checkbox"  id="attachmentIndicator" name="attachmentIndicator" value="Y" checked style="width:40px;" />
															</logic:equal>
															<logic:notEqual name="attachmentIndicator" value="Y">
															<input type="checkbox"  id="attachmentIndicator" name="attachmentIndicator" value="N" style="width:40px;" />
															</logic:notEqual>
															</logic:present>
															<logic:notPresent name="REJECTIONMEMOVO" property="attachmentIndicator">
															<input type="checkbox"  id="attachmentIndicator" name="attachmentIndicator"  style="width:40px;" />
																</logic:notPresent>		
																			<common:message scope="request" key="mra.airlinebilling.rejectionmemo.attachmentIndicator" />
												</div>
												<div class="ic-row">     
             
																	<logic:present name="REJECTIONMEMOVO" property="sisSupportingDocumentDetailsVOs">
																		<bean:define id="supportingDocumentDetailsVO" name="REJECTIONMEMOVO" property="sisSupportingDocumentDetailsVOs" />
																			<logic:iterate id="supportingDocumentDetails" name="supportingDocumentDetailsVO" indexId="nIndex" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO">
																			<logic:notEqual name="supportingDocumentDetails" property="operationFlag" value="D">
																			<div class="ic-row">
																			<input type="checkbox"  name="fileNameCheck" value="<%=nIndex%>" style="width:40px;" />
																			<logic:present name="supportingDocumentDetails" property="filename">
																			<bean:define id="filename" name="supportingDocumentDetails" property="filename" />
																				<a href="#" id="fileDownload" class="iCargoLink" indexId="nIndex" 
																					onclick="onfileDownload(<%=String.valueOf(nIndex)%>)" >
																						<%=String.valueOf(filename)%></a>
																			</logic:present>
																			</div>
																			</logic:notEqual> 
																			</logic:iterate></logic:present>
												</div>
												
													</div>   
									</fieldset>
	
	
							<div class="ic-row marginT5">
							<div class="ic-col-20 ">
								<div class="ic-row">
								<h4></h4>
								</div>
								<common:message scope="request"  key="mra.airlinebilling.rejectionmemo.rejectionstage" />
								<div class="ic-row">
								<h4></h4>
								</div>
							</div>
							<div class="ic-col-70">
								<div class="ic-row">
								<h4></h4>
								</div>
							<logic:present name="REJECTIONMEMOVO" property="rejectionStage">
									
														<logic:present name="KEY_ONETIMEVALUES">
														
												<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
												
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<logic:equal name="parameterCode" value="cra.sisbilling.rejectionstage">
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue" property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<logic:equal name="REJECTIONMEMOVO" property="rejectionStage" value="<%=(String)fieldValue%>" >
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<common:display name="parameterValue" property="fieldDescription" />
																<ihtml:hidden  property="rejectionStage" value="<%=(String)fieldValue%>"/>
															</logic:equal>
															</logic:present>
															</logic:iterate>
															</logic:equal>
														</logic:iterate>
														 </logic:present>
												</logic:present>
								<div class="ic-row">
								<h4></h4>
								</div>
							</div>
						</div>
									
									
									
									
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container" >
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnPrint" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_PRINT" tabindex="22" accesskey="P">
							<common:message key="mra.airlinebilling.rejectionmemo.btn.print"  />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnSave" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_SAVE" tabindex="23" accesskey="S">
							<common:message key="mra.airlinebilling.rejectionmemo.btn.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_REJECTIONMEMO_CLOSE" tabindex="24" accesskey="O">
							<common:message key="mra.airlinebilling.rejectionmemo.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>








	</ihtml:form>
</div>
<!---CONTENT ENDS-->





	</body>
</html:html>
