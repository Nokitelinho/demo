<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name		: MRA.DEFAULTS
* File Name					: SurChargeDetailPopup.jsp
* Date						: 11-JUN-2015
 *********************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"%>

				
	
<html:html>
<head> 
	
	

			
	
		
			
	
<title><common:message key="mailtracking.mra.defaults.surchgdetailpopup.title" bundle="billingmatrix" scope="request" /></title>
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body>
	
	
	<bean:define id="form" name="BillingMatrixForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm"
	toScope="page" />



<business:sessionBean id="selectedBillingLineVO"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintainbillingmatrix"
		method="get" attribute="selectedBillingLine" />

<div class="iCargoPopUpContent" style="width:430px;">
 <ihtml:form action="/mailtracking.mra.defaults.maintainbillingmatrix.surchargedetails.do" styleClass="ic-main-form" >
 
 <div class="ic-content-main">
 <span class="ic-page-title ic-display-none">
				 <common:message  key="mailtracking.mra.defaults.surchgdetailpopup.title" />
			</span>
 <div class="ic-main-container">
		  <div class="ic-row">
		  <div class="tableContainer" id="div1" style="height:150px">
		  <table class="fixed-header-table">
				<thead>
					<tr>
						<td class="iCargoTableHeaderLabel" colspan=4>
						<div class="iCargoHeadingLabel">
						<common:message key="mailtracking.mra.defaults.surchgdetailpopup.surchgdetails" />
						</div>
						</td>						
					</tr>
					<tr>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.surchgdetailpopup.chargehead" /></td>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.surchgdetailpopup.ratingbasis" /></td>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.surchgdetailpopup.chargetype" /></td>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.surchgdetailpopup.ratecharge" /></td>
					</tr>
				</thead>
				<tbody>
				<logic:present name="selectedBillingLineVO">
					<logic:present name="selectedBillingLineVO" property="billingLineDetails">
						<bean:define id="billingLineDetailsList" name="selectedBillingLineVO" property="billingLineDetails" />
						<logic:iterate id="billingLineDetailVO" name="billingLineDetailsList" 
								type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO">								
							<logic:notEqual name="billingLineDetailVO" property="chargeType" value="M">
							
								<logic:equal name="billingLineDetailVO" property="ratingBasis" value="FR">
									<tr>
										<td class="iCargoTableDataTd">
										
										<logic:equal name="billingLineDetailVO" property="chargeType" value="HC">
											Handling Charge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="SS">
											Security Surcharge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="FS">
											Fuel Surcharge
										</logic:equal>
										</td>
										<td class="iCargoTableDataTd">Flat Rate</td>
										<td class="iCargoTableDataTd"></td>
										<logic:present name="billingLineDetailVO" property="billingLineCharges">
										<bean:define id="billingLineChargesList" name="billingLineDetailVO" property="billingLineCharges" />
										<logic:iterate id="billingLineChargeVO" name="billingLineChargesList" 
											type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">	
											<td class="iCargoTableDataTd"><bean:write name="billingLineChargeVO" property="applicableRateCharge" /></td>
										</logic:iterate>
										</logic:present>
									</tr>
								</logic:equal>
								
								<logic:equal name="billingLineDetailVO" property="ratingBasis" value="FC">
									<tr>
										<td class="iCargoTableDataTd">
										<logic:equal name="billingLineDetailVO" property="chargeType" value="HC">
											Handling Charge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="SS">
											Security Surcharge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="FS">
											Fuel Surcharge
										</logic:equal>
										</td>
										<td class="iCargoTableDataTd">Flat Charge</td>
										<td class="iCargoTableDataTd"></td>
										<logic:present name="billingLineDetailVO" property="billingLineCharges">
										<bean:define id="billingLineChargesList" name="billingLineDetailVO" property="billingLineCharges" />
										<logic:iterate id="billingLineChargeVO" name="billingLineChargesList" 
											type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">	
											<td class="iCargoTableDataTd">
											  <logic:equal name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineChargeVO"  moneyproperty="aplRatChg" property="aplRatChg" overrideRounding = "true"/>
											</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineChargeVO"  moneyproperty="aplRatChg" property="aplRatChg"/>
													</logic:notEqual>	
											</td>
										</logic:iterate>
										</logic:present>
									</tr>
								</logic:equal>
								
								<logic:equal name="billingLineDetailVO" property="ratingBasis" value="WB">
									<tr>
										<td class="iCargoTableDataTd">
										<logic:equal name="billingLineDetailVO" property="chargeType" value="HC">
											Handling Charge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="SS">
											Security Surcharge
										</logic:equal>
										<logic:equal name="billingLineDetailVO" property="chargeType" value="FS">
											Fuel Surcharge
										</logic:equal>
										</td>
										<td class="iCargoTableDataTd">Weight Break</td>										
										<logic:present name="billingLineDetailVO" property="billingLineCharges">
										<bean:define id="billingLineChargesList" name="billingLineDetailVO" property="billingLineCharges" />
										<td class="iCargoTableDataTd">
											<table class="iCargoBorderLessTable" style="width:100%;">
											<logic:iterate id="billingLineChargeVO" name="billingLineChargesList" 
												type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">	
													<bean:define id="fromWgtVal" name="billingLineChargeVO" property="frmWgt" />
													
													<logic:lessEqual name="fromWgtVal" value="0">
														<logic:equal name="fromWgtVal" value="-1">
														<tr>
															<td>M</td>
														</tr>
														</logic:equal>
														<logic:equal name="fromWgtVal" value="0">
														<tr>
															<td>N</td>
														</tr>
														</logic:equal>
													</logic:lessEqual>
													<logic:greaterThan name="fromWgtVal" value="0">
													<tr>
														<td>+<bean:write name="fromWgtVal" /></td>
													</tr>
													</logic:greaterThan>
											
											</logic:iterate>
											</table>
										</td>
										<td class="iCargoTableDataTd">
											<table class="iCargoBorderLessTable" style="width:100%;">											
											<logic:iterate id="billingLineChargeVO" name="billingLineChargesList" 
												type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">	
													<bean:define id="fromWgtVal" name="billingLineChargeVO" property="frmWgt" />
													
													<logic:lessEqual name="fromWgtVal" value="0">
														<logic:equal name="fromWgtVal" value="-1">
														<tr>
															<td>
<logic:equal name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineChargeVO"  moneyproperty="aplRatChg" property="aplRatChg" overrideRounding = "true"/>
															 	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineChargeVO"  moneyproperty="aplRatChg" property="aplRatChg"/>
													</logic:notEqual>
															</td>
														</tr>
														</logic:equal>
														<logic:equal name="fromWgtVal" value="0">
														<tr>
															<td><bean:write name="billingLineChargeVO" property="applicableRateCharge" /></td>
														</tr>
														</logic:equal>
													</logic:lessEqual>
													<logic:greaterThan name="fromWgtVal" value="0">
													<tr>
														<td><bean:write name="billingLineChargeVO" property="applicableRateCharge" /></td>
													</tr>
													</logic:greaterThan>
											
											</logic:iterate>																						
											</table>
										</td>
										</logic:present>
									</tr>
								</logic:equal>
								
							</logic:notEqual>							
						</logic:iterate>
					</logic:present>
				</logic:present>
				
				
				</tbody>
			</table>
		  </div>
		 
		 
		 
		</div>
		
 </div>
	<div class="ic-foot-container paddR5">
		<div class="ic-row">
			<div class="ic-button-container">
			<ihtml:nbutton property="btClose" componentID="CMP_Mailtracking_Mra_Defaults_SurchargeDetailsPopup_Close" onclick="window.close();">
				<common:message key="mailtracking.mra.defaults.surchgdetailpopup.btn.btnClose" />
			</ihtml:nbutton>
			</div>
		</div>
	</div>
 </div>
</ihtml:form>
</div>
		
	</body>
</html:html>
