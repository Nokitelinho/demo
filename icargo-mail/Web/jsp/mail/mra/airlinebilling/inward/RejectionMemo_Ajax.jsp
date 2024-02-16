<%--
* Project	 		: iCargo
* Module Code & Name: mra-airlinebilling
* File Name			: RejectionMemo_Ajax.jsp
* Date				: 16-Oct-2008
* Author(s)			: A-3429
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>


<ihtml:form action="/mailtracking.mra.airlinebilling.inward.rejectionmemo.onScreenLoad.do">

  <ihtml:hidden property="fromScreenFlag"/>
	<ihtml:hidden property="screenFlag"/>
	<ihtml:hidden property="cn66CloseFlag"/>
	<ihtml:hidden property="invokingScreen"/>
	  <business:sessionBean id="REJECTIONMEMOVO"
	  moduleName="mailtracking.mra.airlinebilling"
	  screenID="mailtracking.mra.airlinebilling.inward.rejectionmemo"
	  method="get"
	  attribute="rejectionMemoVO" />
				<div class="ic-row">
					<div class="ic-input-container ic-border ic-pad-4">
						<div class="ic-row ">														
							<div class="ic-col-70">
								<div id="div1" class="tableContainer" style="height:250px;">	
									<table class="fixed-header-table" style="width:95%;">
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
												<td  width="40%"colspan="3" ><common:message key="mra.airlinebilling.rejectionmemo.rejthrclearance" scope="request"/></td>
												
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
											<tr class="iCargoTableDataRow1">
												<td class="iCargoLabelRightAligned">
												<common:message key="mra.airlinebilling.rejectionmemo.currofcontract" scope="request"/></td>
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
													<common:message key="mra.airlinebilling.rejectionmemo.exgrateused" scope="request"/>
													</td>
													<td colspan="2"><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div></td>
													<td colspan="2"><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div></td>
													<td colspan="2"><div align="right"><logic:present name="REJECTIONMEMOVO">
													&nbsp;
													<!--<common:write name="REJECTIONMEMOVO" property="contractBillingExchangeRate"/>--></logic:present>
													</div>
												</td>
											</tr>
											<tr class="iCargoTableDataRow1">
												<td class="iCargoLabelRightAligned">
												<common:message key="mra.airlinebilling.rejectionmemo.incurrofbilling" scope="request"/>
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
												<td>
													<common:message key="mra.airlinebilling.rejectionmemo.exgrateused" scope="request"/></td>
												<td colspan="2">&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td colspan="2">&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
												<td colspan="2">&nbsp;<!--<div align="right"><logic:present name="REJECTIONMEMOVO"><common:write name="REJECTIONMEMOVO" property="billingClearanceExchangeRate"/></logic:present></div>--></td>
											</tr>
											<tr class="iCargoTableDataRow1">
												<td class="iCargoLabelRightAligned">
												<common:message key="mra.airlinebilling.rejectionmemo.incurrofclearance" scope="request"/></td>
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
							</div>
						</div>
					</div>
	
 </ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>