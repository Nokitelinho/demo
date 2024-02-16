<%--/
*************************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  MRA.AIRLINEBILLING, ViewFormTwo.jsp
* Date					:  23-July-2008
* Author(s)				:  A-3434
*************************************************************************/
 --%>

 <%@ include file="/jsp/includes/tlds.jsp" %>
 
 

	
	
<html:html>
<head>
	
	
	
	<title><common:message key="mailtracking.mra.airlinebilling.outward.viewform2.title" bundle="viewForm2" /></title>
	<common:include type="script" src="/js/mail/mra/airlinebilling/outward/ViewFormTwo_Script.jsp"/>
	<meta name="decorator" content="mainpanel">

</head>

<body style="overflow:auto;">
	
	
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	 <business:sessionBean
		id="AIRLINEFORBILLINGVOS"
		moduleName="mra.airlinebilling"
		screenID="mailtracking.mra.airlinebilling.outward.viewform2"
		method="get"
		attribute="airlineForBillingVOs" />

	<div id="pageDiv" class="iCargoContent" style="overflow:auto;">
		<ihtml:form action="/mailtracking.mra.airlinebilling.outward.screenloadviewform2.do">
			<bean:define
			id="form"
			name="ViewMailFormTwoForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormTwoForm"
			toScope="page" />

			 <div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.airlinebilling.outward.viewform2.pagetitle"/></span>
					<div class="ic-filter-panel">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-40 ic-mandatory">
								<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.clearanceperiod"/>
								<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEWFORM2_CLEARANCEPERIOD" property="clearancePeriod" maxlength="10" />
								<img name="clearancePeriodLOV" src="<%=request.getContextPath()%>/images/lov.gif"/>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEWFORM2_DETAILS" >
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.details" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEWFORM2_CLEAR" >
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-section ic-border">
						<div class="tableContainer" id="div3" style="width:100%;">
							<table width="100%" class="iCargoTable1" id="form-2Details" >
								<thead>
									<tr class="ic-th-all">
										<th style="width: 12%" />
										<th style="width: 12%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
									</tr>
									<tr>
										<td class="iCargoTableHeader"width="12%">
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.toaircode"/>
										</td>
										<td class="iCargoTableHeader"width="12%">
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.toairno"/>
										</td>
										<td class="iCargoTableHeader"width="10%">
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.currency"/>
										</td>
										<td class="iCargoTableHeader"width="10%">
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.cargoamt"/>
										</td>
										<td class="iCargoTableHeader"width="10%">
											 <common:message key="mailtracking.mra.airlinebilling.outward.viewform2.missamt" />
										 </td>
										<td class="iCargoTableHeader" width="10%">
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.total"/>
										</td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="AIRLINEFORBILLINGVOS">
										<logic:iterate id = "VO" name="AIRLINEFORBILLINGVOS" indexId="rowCount" scope="page" >
											<common:rowColorTag index="rowCount">
												<tr bgcolor="<%=color%>">
													<td class="iCargoTableDataTd" ><div align = "center">
														<common:write name="VO" property="airlineCode"/>
														</div>
													</td>
													<td class="iCargoTableDataTd">
													<div align = "center">
														<common:write name="VO" property="airlineNumber"/>
													</div>
													</td>
													<td class="iCargoTableDataTd"><div align = "center">
														<common:write name="VO" property="billingCurrency"/>

														</div>
													</td>
													<td>
													</td>
													<td class="iCargoTableDataTd"><div align = "center">
														<logic:present name="VO" property="miscAmountInBilling" >
														<bean:define id ="miscAmountInBilling" name="VO" property="miscAmountInBilling" />
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="VO" moneyproperty="miscAmountInBilling" property="miscAmountInBilling" />
														</logic:present>
														</div>
													</td>
													<td class="iCargoTableDataTd"><div align = "center">
														<logic:present name="VO" property="miscBillingTotal" >
														<bean:define id ="miscBillingTotal" name="VO" property="miscBillingTotal" />
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="VO" moneyproperty="miscBillingTotal" property="miscBillingTotal" />
														</logic:present>
														</div>
													</td>
												</tr>
											</common:rowColorTag>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>
					</div>
					<div class="ic-button-container">
						<ihtml:nbutton property="btnPrint" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEWFORM2_PRINT" >
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.print" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEWFORM2_CLOSE" >
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform2.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>	
		</ihtml:form>
	</div>


	</body>
</html:html>






















