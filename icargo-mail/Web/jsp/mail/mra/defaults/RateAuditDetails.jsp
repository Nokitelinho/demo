<%--
* Project	 		: iCargo
* Module Code & Name: operations
* File Name			: RateAuditDetails.jsp
* Date				: 2-Jul-2008
* Author(s)			: Tito Cheriachan,A-2554
--%>




<%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm"%>
<%@ page import="com.ibsplc.icargo.framework.util.currency.Money"%>



<bean:define id="form"
			 name="RateAuditDetailsForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm"
			 toScope="page" />

	
	
<html:html>

<head>

		
	
	<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.rateauditdetails.title"/></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/defaults/RateAuditDetails_Script.jsp" />

</head>

<body id="bodyStyle">
	
	
	
	
	<business:sessionBean id="RateAuditVO"
					 moduleName="mailtracking.mra.defaults"
					 screenID="mailtracking.mra.defaults.rateauditdetails"
					 method="get"
					 attribute="rateAuditVO"/>

	<business:sessionBean id="oneTimeCatSession"
					moduleName="mailtracking.mra.defaults"
					screenID="mailtracking.mra.defaults.rateauditdetails"
					method="get"
					attribute="oneTimeCatVOs" />


	<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.rateauditdetails"
		  method="get"
		  attribute="weightRoundingVO" />

	<business:sessionBean id="KEY_VOLUMEROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.rateauditdetails"
		  method="get"
		  attribute="volumeRoundingVO" />


	<div id="pageDiv" class="iCargoContent">

		<ihtml:form action="/mailtracking.mra.defaults.rateauditdetails.screenload.do">
			<ihtml:hidden property="lovFlag"/>
			<ihtml:hidden property="listFlag"/>
			<ihtml:hidden property="validateFrom"/>
			<ihtml:hidden property="isFromListRateAuditScreen"/>
			<ihtml:hidden property="fromScreen"/>
			<ihtml:hidden property="showDsnPopUp"/>
			
			<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.defaults.rateauditdetails.title"/></span> 
					<div class="ic-filter-panel">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-30 ">
								<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.dsn"/>
								<ihtml:text property="dsnNumber" componentID="CMP_MRA_DEFAULTS_RATEAUDITDETAILS_DSN" styleClass="iCargoTextFieldVeryLong" maxlength="4"/>
							</div>	
							<div class="ic-input ic-split-30 ">
								<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.dsndate"/>
								<ihtml:text property="dsnDate" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSNDATE" readonly="true"/>
							</div>
							<div class="ic-input ic-split-30 ">
								<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.dsnstatus"/>
								<ihtml:text property="dsnStatus" componentID="CMP_MRA_DEFAULTS_RATEAUDITDETAILS_DSNSTATUS" styleClass="iCargoTextFieldVeryLong" readonly="true"/>
							</div>
							<div class="ic-row">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MTRACK_MRA_DEFAULTS_RATEAUDITDETAILS_BTN_LIST">
										<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MTRACK_MRA_DEFAULTS_RATEAUDITDETAILS_BTN_CLEAR">
										<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>		
					</div>
				</div>	
				<div class="ic-main-container">
					<div class="ic-row ic-border">
						<div class="ic-input ic-split-15 ">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.Origin"/>
							<ihtml:text property="origin" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTRATEAUDIT_ORIGIN" readonly="true" styleClass="iCargoTextFieldVerySmall"/>
						</div>
						<div class="ic-input ic-split-15 ">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.destination"/>
							<ihtml:text property="destination" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTRATEAUDIT_DSTN" readonly="true" styleClass="iCargoTextFieldVerySmall"/>
						</div>
						<div class="ic-input ic-split-15 ">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.gpacode" />
							<ihtml:text property="gpaCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTRATEAUDIT_GPACODE" readonly="true" styleClass="iCargoTextFieldSmall"/>					
						</div>	
						<div class="ic-input ic-split-30 ">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.condocno" />
							<ihtml:text property="consignmentDocNo" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTRATEAUDIT_CONDOCNUM" readonly="true" styleClass="iCargoTextFieldVeryLong" />	
						</div>
						<div class="ic-input ic-split-20 ">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.route"/>
							<ihtml:text property="route" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTRATEAUDIT_ROUTE" readonly="true" styleClass="iCargoTextFieldVeryLong"/>				
						</div>
					</div>
					<div class="ic-row ic-border">
						<div class="ic-row iCargoHeadingLabel">
							<b><common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.chargedetails"/></b>
						</div>
						<div id="mailBagEnquiryTable" >
							<div class="tableContainer" id="div1" style="height: 600px">
								<table  id="mailbagenquiry" class="fixed-header-table" >
												<thead>
													<tr class="iCargoTableHeadingLeft">

														  <td class="iCargoTableHeaderLabel" width="3%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.nopcs"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="7%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.grswgt"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="4%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.category"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="5%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.subclass"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="10%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.uld"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="10%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.fltno"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="5%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.rate"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="5%">
														  			Currency
														  </td>
														  <td class="iCargoTableHeaderLabel" width="12%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.presentwgtcharge"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="12%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.autwgtcharge"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="7%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.discrepancy"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="7%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.billto"/>
														  </td>
														  <td class="iCargoTableHeaderLabel" width="4%">
														  	<common:message key="mailtracking.mra.defaults.rateauditdetails.lbl.applyautd"/>
														  </td>
													</tr>
												</thead>

												<tbody>
												<tr>
														<td class="iCargoTableDataTd">
														<center>
														<logic:present	name="RateAuditVO" property="pcs">
															<bean:write name="RateAuditVO" property="pcs"/>
														</logic:present>
														</center>
														</td>
														<td >
														<center>

														<logic:notPresent name="RateAuditDetailsForm" property="updWt">

																	<logic:present name="KEY_WEIGHTROUNDINGVO">

																		<bean:define id="revGrossWeightID" name="KEY_WEIGHTROUNDINGVO" />
																			<% request.setAttribute("updWt",revGrossWeightID); %>
																				<ibusiness:unitdef id="updWt" unitTxtName="updWt" label=""  unitReq = "false" dataName="updWt"
																				unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Gross Weight"
																				unitValue="0.0" style="background :'<%=color%>'"
																				indexId="index" styleId="updWt" />
																	</logic:present>
														</logic:notPresent>

														<logic:present name="RateAuditDetailsForm" property="updWt">

																	<bean:define id="revGrossWeightID" name="RateAuditDetailsForm" property="updWt" toScope="page"/>

																	<logic:present name="KEY_WEIGHTROUNDINGVO">

																		<bean:define id="revGrossWeight" name="KEY_WEIGHTROUNDINGVO" />
																			<% request.setAttribute("updWt",revGrossWeight); %>
																				<ibusiness:unitdef id="updWt" unitTxtName="updWt" label=""  unitReq = "false" dataName="updWt"
																				unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Gross Weight"
																				unitValue="<%=revGrossWeightID.toString()%>" style="background :'<%=color%>'"
																				indexId="index" styleId="updWt"/>
																	</logic:present>

														 </logic:present>


														</center>
														</td>
														<td><center>
														  	<% String catValue = ""; %>
															<logic:present name="RateAuditVO" property="category">
																<bean:define id="mailCtgyCode" name="RateAuditVO" property="category" toScope="page"/>
																<% catValue = (String) mailCtgyCode; %>
															</logic:present>
														<ihtml:select property="category" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_CATEGORY" value="<%=catValue%>" style="width:35px">
														<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
															<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
															<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
																	<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
															</logic:iterate>
														</ihtml:select>
														</center></td>
														<td><center><ihtml:text property="subClass" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_SUBCLASS" maxlength="2" />
														<img name="mailSCLov" id="mailSCLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
														</center></td>
														<td>
														<center>
															<logic:present	name="RateAuditVO" property="poaFlag">
																<logic:equal name="RateAuditVO" property="poaFlag" value="Y">
															<ihtml:text property="ULD" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_ULD" maxlength="13"/>
															    </logic:equal>
														    <logic:notEqual name="RateAuditVO" property="poaFlag" value="Y">
																<ihtml:text property="ULD" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_ULD" maxlength="13" disabled="true"/>
															 </logic:notEqual>
															</logic:present>
															<logic:notPresent name="RateAuditVO" property="poaFlag">
															<ihtml:text property="ULD" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_ULD" maxlength="13" disabled="true"/>
															</logic:notPresent>

														</center>
														</td>

														<td><center>
														<ibusiness:flightnumber carrierCodeProperty="flightCarCod"
															  id="flightNumber"
															  flightCodeProperty="flightNo"
															  carriercodevalue="<%=(String)form.getFlightCarCod()%>"
															  flightcodevalue="<%=(String)form.getFlightNo()%>"
															  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FLIGHTNO"
															  carrierCodeStyleClass="iCargoTextFieldVerySmall"
															  flightCodeStyleClass="iCargoTextFieldSmall"
														 />
														</center></td>
														<td>
														<center>
														<logic:present	name="RateAuditVO" property="initialRate">
															<bean:define id="rate" name="RateAuditVO" property="initialRate" type="Double"/>
															<bean:write name="RateAuditVO" property="initialRate" format="####.0000"/>
															<%--
															<ihtml:text property="rate" disabled="true" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_RATE" maxlength="9" value="<%=rate.toString()%>"/>
															--%>
															<%String rateval=rate.toString();%>
															<ihtml:hidden name="form" property="rateval" value="<%=rateval%>"/>
														</logic:present>
														<logic:notPresent	name="RateAuditVO" property="rate">
															<ihtml:hidden name="form" property="rateval" value=""/>
														</logic:notPresent>
														</center>
														</td>
														<td>
															<center>

															<logic:present  name="RateAuditVO" property="currency">

															<bean:write name="RateAuditVO" property="currency" />

															</logic:present>

															</center>
														</td>
														<td>
														<center>
														<logic:present  name="RateAuditVO" property="presentWtCharge">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="RateAuditVO"  moneyproperty="presentWtCharge" property="presentWtCharge" />
														</logic:present>
														<logic:notPresent name="RateAuditVO" property="presentWtCharge">
															<ihtml:hidden name="form" property="presentWgtCharge"/>
														</logic:notPresent>
														</center>
														</td>
														<td>
														<center>
														<logic:present name="RateAuditVO" property="auditedWtCharge">
														  <ibusiness:moneyEntry name="RateAuditVO" id="auditWgtChge" moneyproperty="auditedWtCharge"  property="auditWgtCharge" formatMoney="false"   componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_AUDITEDWIEGHT_CHARGE" />
														</logic:present>
														<logic:notPresent name="RateAuditVO" property="auditedWtCharge">
														 <ibusiness:moneyEntry  id="auditWgtChge"  property="auditWgtCharge" formatMoney="false" value="0"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_AUDITEDWIEGHT_CHARGE" />
														</logic:notPresent>
														</center>
														</td>
														<td>
														 <center>
															<logic:present name="RateAuditVO" property="discrepancyNo">
														 		<logic:equal name="RateAuditVO" property="discrepancyNo" value="Y">
														  			N
														 		</logic:equal>
														  		<logic:notEqual name="RateAuditVO" property="discrepancyNo" value="Y">
														   			<ibusiness:moneyDisplay showCurrencySymbol="false" name="RateAuditVO"  moneyproperty="discrepancyYes" property="discrepancyYes" />
														  		</logic:notEqual>
														 	</logic:present>
														 	<logic:notPresent name="RateAuditVO" property="discrepancyNo">

														 	</logic:notPresent>
														 </center>
														</td>
														<td>
														<center>
														<ihtml:text property="billTo" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_BILLTO" styleClass="iCargoTextFieldSmall"/>
														</center>
														</td>
														<td>
														<center>
														<logic:present	name="RateAuditVO" property="applyAutd">
														<bean:define id="applyAutd" name="RateAuditVO" property="applyAutd"/>
														<%String applyAut=applyAutd.toString();%>
														<ihtml:checkbox property="applyAudit" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_APPLY_AUDIT_CHK" value="<%=applyAut%>"/>
														</logic:present>
														<logic:notPresent	name="RateAuditVO" property="applyAutd">
														<ihtml:checkbox property="applyAudit" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_APPLY_AUDIT_CHK" />
														</logic:notPresent>
														</center>
														</td>

												</tr>

												</tbody>
								</table>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnComputeTotal" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_COMPUTETOTAL">
									<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.computetotal" />
								</ihtml:nbutton>
							</div>
						</div>		
					</div>
					<div class="ic-button-container">
						<ihtml:nbutton property="btnViewProration" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_VIEWPRORATION">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.viewproration" />
						</ihtml:nbutton>


						<ihtml:nbutton property="btnCCA" componentID="CMP_MTRACK_MRA_DEFAULTS_RATEAUDITDETAILS_BTN_CCA">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.cca" />
						</ihtml:nbutton>


						<ihtml:nbutton property="btnRateAudit" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_RATEAUDIT">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.rateaudit" />
						</ihtml:nbutton>


						<ihtml:nbutton property="btnSave" componentID="CMP_MTRACK_MRA_DEFAULTS_RATEAUDITDETAILS_BTN_SAVE">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.save" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnClose" componentID="CMP_MTRACK_MRA_DEFAULTS_RATEAUDITDETAILS_BTN_CLOSE">
							<common:message key="mailtracking.mra.defaults.rateauditdetails.btn.close" />
						</ihtml:nbutton>		
					</div>
				</div>
			</div>				
	
	
	</ihtml:form>
</div>

	</body>
</html:html>
