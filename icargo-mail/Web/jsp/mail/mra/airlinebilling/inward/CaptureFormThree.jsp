<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : CaptureFormThree.jsp
* Date                 	 : 06-August-2008
* Author(s)              : A-3108
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm" %>

<bean:define id="form"
name="CaptureMailFormThreeForm"
type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm"
toScope="page" />
<business:sessionBean
		id="KEY_AIRLINEFORBILLING_VOS"
		moduleName="mailtracking.mra"
		screenID="mailtracking.mra.airlinebilling.inward.captureformthree"
		method="get"
		attribute="airlineForBillingVOs" />
<business:sessionBean
  		id="KEY_ONETIMEVALUES"
  		moduleName="mailtracking.mra"
  		screenID="mailtracking.mra.airlinebilling.inward.captureformthree"
  		method="get"
  		attribute="oneTimeValues" />
	
	
<html:html>
<head>
	
	
		
	

<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.airlinebilling.inward.Captureform3.title" /></title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/mail/mra/airlinebilling/inward/CaptureFormThree_Script.jsp" />
</head>

<body id="bodyStyle">
	
	
	
	

	
	<div id="mainDiv" class="iCargoContent" style="overflow:auto;">
	<ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureFormThree.onScreenLoad.do">
	<ihtml:hidden name="form" property="miscTotalAmountInBilling" />
	<ihtml:hidden name="form" property="grossTotalAmountInBilling" />
	<ihtml:hidden name="form" property="creditTotalAmountInBilling" />
	<ihtml:hidden name="form" property="netTotalValueInBilling" />
	<ihtml:hidden name="form" property="invokingScreen" />
	<ihtml:hidden name="form" property="rowCounter" />
	<ihtml:hidden name="form" property="linkDisable" />
	
		<div class="ic-content-main" >
			<span class="ic-page-title ic-display-none">
				<common:message   key="mailtracking.mra.airlinebilling.inward.captureform3.pagetitle" scope="request"/>
			</span>
			<div class="ic-head-container" >
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							
								<div class="ic-input ic-mandatory">
									<label>
										<common:message key="mra.inwardbilling.captureform3.clearanceperiod" scope="request"/>
									</label>
									<ihtml:text property="clearancePeriod" componentID="CMP_MRA_AIRLINEBILLING_INWARDBILLINGD_CLEARANCEPERIOD" maxlength="10"/>
									<img src="<%=request.getContextPath()%>/images/lov.gif" id="clearancePeriodlov" height="16" width="16" />
								</div>
							
							
								<div class="ic-button-container">
									<div class="ic-row">
										<ihtml:nbutton  property="btnList" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_LIST" >
										<common:message key="mailtracking.mra.airlinebilling.inward.captureform3.button.list" />
										</ihtml:nbutton>

										<ihtml:nbutton    property="btnClear" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_CLEAR" >
										<common:message key="mailtracking.mra.airlinebilling.inward.captureform3.button.clear" />
										</ihtml:nbutton>
									</div>
								</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.tableheading" /><h4>
				</div>
				<div class="ic-border ic-pad-3">
					<div class="ic-row">
						<div class="ic-button-container">
							<a tabindex="4"  href="#" class="iCargoLink" name="addLink">Add</a>
							| <a tabindex="5" href="#" class="iCargoLink" name="deleteLink">Delete</a>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="captureAWBDetails" style="height:500px">
							<table class="fixed-header-table ic-pad-3">
								<thead>
									<tr>
										<td width="2%" class="iCargoTableHeadingCenter"  rowspan="3"><input type="checkbox" name="check"> </td>
										<td rowspan="3"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.airlinecode" /> </td>
										<td width="10%" rowspan="3"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.airlineno" /></td>
										<td width="70%"colspan="8"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.debit"/></td>
										<td width="9%"align="center" rowspan="3"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.creditamount"/></td>
										<td width="9%"align="center" rowspan="3"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.netamount"/></td>
										<td width="9%"align="center" rowspan="3"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.status"/></td>
									</tr>
									<tr>
										<td width="8%" align="center" rowspan="2">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.passenger" /> </td>
										<td width="8%" align="center" rowspan="2">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.uatp" /> </td>
										<td width="8%"colspan="4"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.cargo" /> </td>
										<td width="5%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.misc" /> </td>
										<td width="5%" align="center" rowspan="2">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.total" /> </td>
									</tr>
									<tr>
										<td width="8%">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.zoneA" /> </td>
										<td width="8%">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.zoneB" /> </td>
										<td width="8%">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.zoneC" /> </td>
										<td width="8%">  <common:message key="mailtracking.mra.airlinebilling.inward.captureform3.lbl.zoneD" /> </td>
									</tr>
								</thead>
								<tbody id="captureFormThreeTbody">
										<logic:present name="KEY_AIRLINEFORBILLING_VOS">
										<logic:iterate id="airlineForBillingvo" name="KEY_AIRLINEFORBILLING_VOS"
										type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO" indexId="rowCount">
										<logic:equal name="airlineForBillingvo" property="operationFlag" value="D">
										<ihtml:hidden property="hiddenOperationFlag" value="D" />

										<ihtml:hidden property="airlineIdentifier" name="airlineForBillingvo" />
										<logic:present name="airlineForBillingvo" property="airlineCode" >
										<ihtml:hidden name="airlineForBillingvo" property="airlineCode"/>
										</logic:present>

										<logic:notPresent name="airlineForBillingvo" property="airlineCode" >
										<ihtml:hidden  property="airlineCode" value=""/>
										</logic:notPresent>

										<logic:present name="airlineForBillingvo" property="airlineNumber" >
										<ihtml:hidden name="airlineForBillingvo" property="airlineNumber"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="airlineNumber" >
										<ihtml:hidden  property="airlineNumber" value=""/>
										</logic:notPresent>

										<logic:present name="airlineForBillingvo" property="miscAmountInBilling" >
										<ihtml:hidden name="airlineForBillingvo" property="miscAmountInBilling"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="miscAmountInBilling" >
										<ihtml:hidden property="miscAmountInBilling" value="0.0"/>
										</logic:notPresent>

										<logic:present name="airlineForBillingvo" property="totalAmountInBilling" >
										<ihtml:hidden name="airlineForBillingvo" property="totalAmountInBilling"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="totalAmountInBilling" >
										<ihtml:hidden property="totalAmountInBilling" value="0.0"/>
										</logic:notPresent>

										<logic:present name="airlineForBillingvo" property="creditAmountInBilling" >
										<ihtml:hidden name="airlineForBillingvo" property="creditAmountInBilling"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="creditAmountInBilling" >
										<ihtml:hidden property="creditAmountInBilling" value="0.0"/>
										</logic:notPresent>

										<logic:present name="airlineForBillingvo" property="netValueInBilling" >
										<ihtml:hidden name="airlineForBillingvo" property="netValueInBilling"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="netValueInBilling" >
										<ihtml:hidden property="netValueInBilling" value="0.0"/>
										</logic:notPresent>
										<logic:present name="airlineForBillingvo" property="status" >
										<ihtml:hidden name="airlineForBillingvo" property="status"/>
										</logic:present>
										<logic:notPresent name="airlineForBillingvo" property="netValueInBilling" >
										<ihtml:hidden property="netValueInBilling" value="N"/>
										</logic:notPresent>

										</logic:equal>
										<common:rowColorTag index="rowCount">
										<logic:notEqual name="airlineForBillingvo" property="operationFlag" value="D">
										<tr bgcolor="<%=color%>">
										<%boolean readonlyStatus=true;%>
										<%boolean amountStatus=false;%>
										<logic:equal name="airlineForBillingvo" property="operationFlag" value="I">

										<ihtml:hidden property="hiddenOperationFlag" value="I" />
										</logic:equal>
										<logic:notEqual name="airlineForBillingvo" property="operationFlag" value="I">
										<ihtml:hidden property="hiddenOperationFlag" value="N" />
										</logic:notEqual>
										<ihtml:hidden property="airlineIdentifier" name="airlineForBillingvo" />
										<td>
											<input type="checkbox" name="setCheck" value="<%=rowCount%>" />
										</td>
										<td>
											<logic:present name="airlineForBillingvo" property="airlineCode" >
											<ihtml:text tabindex="6" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINE" name="airlineForBillingvo" property="airlineCode" styleId="airlineCode"   maxlength="3" style="border: 0px;background:"  readonly="true" indexId="rowCount"/>
											</logic:present>							
											<logic:notPresent name="airlineForBillingvo" property="airlineCode" >
											<ihtml:text  tabindex="6" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINE" property="airlineCode" styleId="airlineCode" value="" maxlength="3" style="border: 0px;background:" readonly="true" indexId="rowCount"/>	
											</logic:notPresent>
										</td>
										<td>
											<logic:present name="airlineForBillingvo" property="airlineNumber" >
												<ihtml:text tabindex="7"  componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINENO" name="airlineForBillingvo" property="airlineNumber" styleId="airlineNumber"   maxlength="5" style="border: 0px;background:" readonly="true" indexId="rowCount" />
											 </logic:present>
											 <logic:notPresent name="airlineForBillingvo" property="airlineNumber" >
												<ihtml:text  tabindex="7"  componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINENO" property="airlineNumber" styleId="airlineNumber" value="" maxlength="5" style="border: 0px;background:"  readonly="true" indexId="rowCount" />
											 </logic:notPresent>
										</td>
										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="passengerAmountInBilling" >
												<bean:define id="passengerAmountInBilling" name="airlineForBillingvo" property="passengerAmountInBilling"/>
												<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_CRA_INWARDBILLING_CAPTUREFORM3_PASSENGER" id="passengerAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="passengerAmountInBilling" property="passengerAmountInBilling" onmoneychange="updateTotal" />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="passengerAmountInBilling" >
												<ibusiness:moneyEntry     formatMoney="true"   componentID="CMP_CRA_INWARDBILLING_CAPTUREFORM3_PASSENGER" id="passengerAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="passengerAmountInBilling" property="passengerAmountInBilling" onmoneychange="updateTotal" />

											</logic:notPresent>
											</div>

										</td>

										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="uatpAmountInBilling" >
												<bean:define id="uatpAmountInBilling" name="airlineForBillingvo" property="uatpAmountInBilling"/>
												<ibusiness:moneyEntry   formatMoney="true"   componentID="CMP_INWARDBILLING_CAPTUREFORM3_UATP" id="uatpAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="uatpAmountInBilling" property="uatpAmountInBilling" onmoneychange="updateTotal" />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="uatpAmountInBilling" >
												<ibusiness:moneyEntry    formatMoney="true"   componentID="CMP_INWARDBILLING_CAPTUREFORM3_UATP" id="uatpAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="uatpAmountInBilling" property="uatpAmountInBilling" onmoneychange="updateTotal" />
											</logic:notPresent>
											</div>

										</td>

										<%  String currency = ""; %>


										 <logic:present name="airlineForBillingvo" property="cargoAmountInBilling" >
											<% currency = airlineForBillingvo.getCargoAmountInBilling().getCurrencyCode();%>

										 </logic:present>
										<td>
											<div align="right">
											<logic:equal name="airlineForBillingvo" property="zoneIndicator" value="A">
											<logic:present name="airlineForBillingvo" property="cargoAmountInBilling" >
											<bean:define id="cargoAmountInBilling" name="airlineForBillingvo" property="cargoAmountInBilling"/>
												<ibusiness:moneyEntry    formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEA" id="zonea"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zonea" onmoneychange="doCalculate" />

											</logic:present>
											</logic:equal>

											<logic:notEqual name="airlineForBillingvo" property="zoneIndicator" value="A">
												<ibusiness:moneyEntry    formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEA" id="zonea"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zonea"  onmoneychange="doCalculate"  currencyCode="<%=currency%>" value="0.0"  />
											</logic:notEqual>
											</div>
										</td>

										<td>
											<div align="right">

											<logic:equal name="airlineForBillingvo" property="zoneIndicator" value="B">
											<logic:present name="airlineForBillingvo" property="cargoAmountInBilling" >
											<bean:define id="cargoAmountInBilling" name="airlineForBillingvo" property="cargoAmountInBilling"/>
												<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEB" id="zoneb"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zoneb"  onmoneychange="doCalculate" />

											</logic:present>
											</logic:equal>

											<logic:notEqual name="airlineForBillingvo" property="zoneIndicator" value="B">
												<ibusiness:moneyEntry   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEB" id="zoneb"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zoneb"  onmoneychange="doCalculate"  currencyCode="<%=currency%>" value="0.0"  />
											</logic:notEqual>

											</div>
										</td>

										<td>
											<div align="right">
											<logic:equal name="airlineForBillingvo" property="zoneIndicator" value="C">
											<logic:present name="airlineForBillingvo" property="cargoAmountInBilling" >
											<bean:define id="cargoAmountInBilling" name="airlineForBillingvo" property="cargoAmountInBilling"/>
												<ibusiness:moneyEntry   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEC" id="zonec"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zonec" onmoneychange="doCalculate" />

											</logic:present>
											</logic:equal>

											<logic:notEqual name="airlineForBillingvo" property="zoneIndicator" value="C">
												<ibusiness:moneyEntry   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEC" id="zonec"   indexId="rowCount" readonly="true" name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zonec"  onmoneychange="doCalculate"  currencyCode="<%=currency%>" value="0.0"  />
											</logic:notEqual>

											</div>
										</td>

										<td>
											<div align="right">
											<logic:equal name="airlineForBillingvo" property="zoneIndicator" value="D">
											<logic:present name="airlineForBillingvo" property="cargoAmountInBilling" >
											<bean:define id="cargoAmountInBilling" name="airlineForBillingvo" property="cargoAmountInBilling"/>
												<ibusiness:moneyEntry   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONED" id="zoned"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zoned" onmoneychange="doCalculate" />

											</logic:present>
											</logic:equal>

											<logic:notEqual name="airlineForBillingvo" property="zoneIndicator" value="D">
												<ibusiness:moneyEntry    formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONED" id="zoned"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="cargoAmountInBilling" property="zoned"  onmoneychange="doCalculate"  currencyCode="<%=currency%>" value="0.0" />
											</logic:notEqual>

											</div>
										</td>

										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="miscAmountInBilling" >
												<bean:define id="miscAmountInBilling" name="airlineForBillingvo" property="miscAmountInBilling"/>
												<ibusiness:moneyEntry tabindex="8"   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_MISC" id="miscAmountInBilling"   indexId="rowCount" readonly="<%=amountStatus%>"  name="airlineForBillingvo" moneyproperty="miscAmountInBilling" property="miscAmountInBilling" onmoneychange="updateTotal" />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="miscAmountInBilling" >
												<ibusiness:moneyEntry  tabindex="8"  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_MISC" id="miscAmountInBilling"   indexId="rowCount" readonly="<%=amountStatus%>"  name="airlineForBillingvo" moneyproperty="miscAmountInBilling" property="miscAmountInBilling" onmoneychange="updateTotal" />
											</logic:notPresent>
											</div>
										</td>

										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="totalAmountInBilling" >
												<bean:define id="totalAmountInBilling" name="airlineForBillingvo" property="totalAmountInBilling"/>
												<ibusiness:moneyEntry  tabindex="9"   formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_TOTAL" id="totalAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="totalAmountInBilling" property="totalAmountInBilling"  />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="totalAmountInBilling" >
												<ibusiness:moneyEntry  tabindex="9"  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_TOTAL" id="totalAmountInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="totalAmountInBilling" property="totalAmountInBilling"  />
											</logic:notPresent>
											</div>
										</td>

										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="creditAmountInBilling" >
												<bean:define id="creditAmountInBilling" name="airlineForBillingvo" property="creditAmountInBilling"/>
												<ibusiness:moneyEntry tabindex="10"  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_CREDITAMT" id="creditAmountInBilling"   indexId="rowCount" readonly="<%=amountStatus%>"  name="airlineForBillingvo" moneyproperty="creditAmountInBilling" property="creditAmountInBilling" onmoneychange="updateTotal" />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="creditAmountInBilling" >
												<ibusiness:moneyEntry tabindex="10" formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_CREDITAMT" id="creditAmountInBilling"   indexId="rowCount" readonly="<%=amountStatus%>"  name="airlineForBillingvo" moneyproperty="creditAmountInBilling" property="creditAmountInBilling" onmoneychange="updateTotal" />
											</logic:notPresent>
											</div>
										</td>
										<td>
											<div align="right">
											<logic:present name="airlineForBillingvo" property="netValueInBilling" >
												<bean:define id="netValueInBilling" name="airlineForBillingvo" property="netValueInBilling"/>
												<ibusiness:moneyEntry  tabindex="11" formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_NETAMT" id="netValueInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="netValueInBilling" property="netValueInBilling" />

											</logic:present>
											<logic:notPresent name="airlineForBillingvo" property="netValueInBilling" >
												<ibusiness:moneyEntry  tabindex="11" formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_NETAMT" id="netValueInBilling"   indexId="rowCount" readonly="true"  name="airlineForBillingvo" moneyproperty="netValueInBilling" property="netValueInBilling" />
											</logic:notPresent>
											</div>
										</td>
										<td>
										<logic:present name="airlineForBillingvo" property="status" >
										<logic:present name="KEY_ONETIMEVALUES">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mra.airlinebilling.formThreeStatus">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
															<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<logic:equal name="airlineForBillingvo" property="status" value="<%=fieldValue.toString()%>">
																<bean:write name="parameterValue" property="fieldDescription"/>
																<ihtml:hidden name="airlineForBillingvo" property="status"/>
															</logic:equal>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
										</logic:present>							
										<logic:notPresent name="airlineForBillingvo" property="status" >
										<ihtml:text  tabindex="12" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_STATUS" name="airlineForBillingvo" property="status"  readonly="true"  indexId="rowCount"/>	
										</logic:notPresent>
										</td>


									</logic:notEqual>
									</common:rowColorTag>
									</logic:iterate>
									</logic:present>					


									<!-- template row starts-->
								<bean:define id="templateRowCount" value="0"/>
								<tr template="true" id="captureform3Row" style="display:none">
								<%boolean readonlyStatus=true;%>
								<%boolean amountStatus=false;%>
								<ihtml:hidden property="hiddenOperationFlag" value="NOOP" />
								<ihtml:hidden property="airlineIdentifier" value="0" />

								<%  String currency ="USD";%>
								<td class="iCargoTableDataTd" >

									<input type="checkbox" name="setCheck" value="<%=templateRowCount%>">
									<ihtml:hidden name="form" property="opFlag" value="NOOP"/>
								</td>
								<div id="captureFormThreeParent">
				
									<td class="iCargoTableDataTd" >
										<ihtml:text componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINE" property="airlineCode" styleId="airlineCode" value="" maxlength="3" style="border: 0px;background:"  />
										<button type="button" class="iCargoLovButton" name="airlinelov" id="airlinelov" onclick="showAirlineLovForTemplateRow(this)" />
									</td>
									<td class="iCargoTableDataTd">
										<ihtml:text componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_AIRLINENO" property="airlineNumber" styleId="airlineNumber" value="" maxlength="5" style="border: 0px;background:"  />

									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_PASSENGER" id="passengerAmountInBilling"   indexId="templateRowCount" readonly="true"  currencyCode="<%=currency%>" value="0.0"  />
										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_UATP" id="uatpAmountInBilling"   indexId="templateRowCount" readonly="true"    currencyCode="<%=currency%>" value="0.0"  />
										</div>

									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEA" id="zonea"   indexId="templateRowCount" readonly="true"      currencyCode="<%=currency%>" value="0.0"  />

										</div>
									</td>
									<td>
										<div align="right">

											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEB" id="zoneb"   indexId="templateRowCount" readonly="true"       currencyCode="<%=currency%>" value="0.0"  />


										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONEC" id="zonec"   indexId="templateRowCount" readonly="true"      currencyCode="<%=currency%>" value="0.0"  />


										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_ZONED" id="zoned"   indexId="templateRowCount" readonly="true"      currencyCode="<%=currency%>" value="0.0" />


										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_MISC" id="miscAmountInBilling"   indexId="templateRowCount" readonly="<%=amountStatus%>"    currencyCode="<%=currency%>" value="0.0" property="miscAmountInBilling" onmoneychange="updateTotal" />

										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_TOTAL" id="totalAmountInBilling"   indexId="templateRowCount" readonly="true"  currencyCode="<%=currency%>" value="0" property="totalAmountInBilling"  />

										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_CREDITAMT" id="creditAmountInBilling"   indexId="templateRowCount" readonly="<%=amountStatus%>"    currencyCode="<%=currency%>" value="0.0" property="creditAmountInBilling" onmoneychange="updateTotal" />

										</div>
									</td>
									<td>
										<div align="right">
											<ibusiness:moneyEntry  formatMoney="true"   componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_NETAMT" id="netValueInBilling"   indexId="templateRowCount" readonly="true"   currencyCode="<%=currency%>" value="0.0" property="netValueInBilling" />

										</div>
									</td>
									<td class="iCargoTableDataTd">
									<ihtml:text componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_STATUS" name="airlineForBillingvo" property="status"  readonly="true"  indexId="rowCount" value="New"/>	
										<ihtml:hidden property="status" value="N"/>
									</td>

								</tr>			
			 <!-- template row ends-->
								</tbody>
								<tfoot>
								<tr>
									<td width="30" align="center"></td>
									<td width="9%"></td>
									<td width="9%"  id="summaryPasgr" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="passengerTotalAmountInBillingMoney"   showCurrencySymbol="false"  property="passengerTotalAmountInBilling" />
					
									</td>
									<td  width="5%"  id="summaryUatp" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="uatpTotalAmountInBillingMoney"   showCurrencySymbol="false"  property="uatpTotalAmountInBilling" />
					
									</td>
									<td  width="9%"  id="summaryzonea" style="text-align:right">
					
										<ibusiness:moneyDisplay moneyproperty="zoneaTotalMoney"   showCurrencySymbol="false"  property="zoneaTotal" />
									</td>
									<td width="9%"  id="summaryzoneb" style="text-align:right">
					
										<ibusiness:moneyDisplay moneyproperty="zonebTotalMoney"   showCurrencySymbol="false"  property="zonebTotal" />
									</td>
									<td width="9%"  id="summaryzonec" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="zonecTotalMoney"   showCurrencySymbol="false"  property="zonecTotal" />
					
									</td>
									<td width="9%"  id="summaryzoned" style="text-align:right">
					
										<ibusiness:moneyDisplay moneyproperty="zonedTotalMoney"   showCurrencySymbol="false"  property="zonedTotal" />
					
									</td>
									<td width="9%"  id="summaryMisc" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="miscTotalAmountInBillingMoney"   showCurrencySymbol="false"  property="miscTotalAmountInBilling" />
					
									</td>
									<td width="9%"  id="summaryTotal" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="grossTotalAmountInBillingMoney"   showCurrencySymbol="false"  property="grossTotalAmountInBilling" />
					
									</td>
									<td  width="9%" id="summaryCredit" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="creditTotalAmountInBillingMoney"   showCurrencySymbol="false"  property="creditTotalAmountInBilling" />
					
									</td>
									<td  width="9%" id="summaryNet" style="text-align:right">
										<ibusiness:moneyDisplay moneyproperty="netTotalValueInBillingMoney"   showCurrencySymbol="false"  property="netTotalValueInBilling" />
					
									</td>
									<td></td>
								</tr>
							</tfoot>
							</table>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">
				<div class="ic-row ">
					<ihtml:nbutton tabindex="13" property="btnSave" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_SAVE" >
					<common:message key="mailtracking.mra.airlinebilling.inward.captureform3.button.save" />
					</ihtml:nbutton>

					<ihtml:nbutton tabindex="14" property="btnClose" componentID="CMP_MRA_INWARDBILLING_CAPTUREFORM3_CLOSE" >
					<common:message key="mailtracking.mra.airlinebilling.inward.captureform3.button.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
</ihtml:form>
</div>
<span style="display:none" id="tmpSpan"></span>

	
				
		<jsp:include page="/jsp/includes/footerSection.jsp"/>
			
		<logic:present name="popup">        		
		<logic:present name="icargo.uilayout">
			<logic:equal name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
			</logic:equal>

			<logic:notEqual name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/popupfooter_new.jsp" />
			</logic:notEqual>
		</logic:present>
		</logic:present>
		
		<logic:notPresent name="popup">	
		<logic:present name="icargo.uilayout">
			<logic:equal name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/footer_new_ui.jsp" />
			</logic:equal>

			<logic:notEqual name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/footer_new.jsp" />
			</logic:notEqual>
		</logic:present>		
		</logic:notPresent>
		
		<common:registerCharts/>
		<common:registerEvent />
	</body>
</html:html>
