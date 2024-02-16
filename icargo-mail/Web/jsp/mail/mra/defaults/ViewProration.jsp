<%--
* Project	 		: iCargo
* Module Code & Name		: MailTracking
* File Name			: ViewProration.jsp
* Date				: 08/08/08
* Author(s)			: A-2554,Tito Cheriachan
--%>


<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm"%>
 <%@ page import="com.ibsplc.icargo.framework.util.currency.Money"%>

<bean:define id="form"
			 name="MRAViewProrationForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm"
			 toScope="page" />


<business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.viewproration"
	 	  method="get"
	  attribute="OneTimeVOs" />

<business:sessionBean id="ProrationVOs"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="ProrationVOs" />
 <business:sessionBean id="PrimaryProrationVOs"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="PrimaryProrationVOs" />

<business:sessionBean id="SecondaryProrationVOs"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="SecondaryProrationVOs" />

<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.viewproration"
	method="get" attribute="systemparametres" />

<html:html>

<head>



	<title>View Proration</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/defaults/ViewProRation_Script.jsp"/>
</head>

<body id="bodyStyle">





<%

   double Pweight=0.0;
   double PamtUSD=0.0;
   double PamtBASE=0.0;
   double PamtSDR=0.0;
   double PamtCTR=0.0;


	double Sweight=0.0;
	double SamtUSD=0.0;
	double SamtBASE=0.0;
	double SamtSDR=0.0;
	String contractCurrency = "";






%>



<!-- The content div -->
<div class="iCargoContent ic-masterbg" id="mainDiv" >

	<ihtml:form action="/mailtracking.mra.defaults.viewprorationscreenload.do">
	<ihtml:hidden property="fromScreen"/>
	<ihtml:hidden property="fromAction" />
	<ihtml:hidden property="dsn" />
	<ihtml:hidden property="showDsnPopUp"/>
	<ihtml:hidden property="formStatusFlag"/>
	<ihtml:hidden property="sector"/>
	<ihtml:hidden property="currencyChangeFlag"/>
    <%String roundingReq="true";%>
	<ihtml:hidden property="overrideRounding" value="N" />
	<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
			<logic:notEqual name="parameterValue" value="N">
				<%form.setOverrideRounding("Y");%>
			</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>
	<ihtml:hidden property="awmSpecificFlag" value="N" />
	<common:xgroup>
		<common:xsubgroup id="KE_SPECIFIC">
			<%form.setAwmSpecificFlag("KE");%>
	</common:xsubgroup>
	</common:xgroup >
	<div class="ic-content-main">
	<span class="ic-page-title"><common:message bundle="mailproration" key="mailtracking.mra.defaults.proration.pagetitle" /></td></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row ">

							<div class="ic-input ic-split-30 ic-mandatory">
								<label><common:message key="mailtracking.mra.defaults.proration.dispatch" /></label>
								<ihtml:text property="dispatch"  componentID="CMP_MRA_DEFAULTS_PRORATION_DISPATCH" maxlength="29" style="width:200px"  />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt=""/>
								</div>
    						</div>
							<div class="ic-input ic-split-20" >
								<label><common:message key="mailtracking.mra.defaults.proration.condocno" /></label>
								<ihtml:text property="conDocNo"  componentID="CMP_MRA_DEFAULTS_PRORATION_CONDOCNO" maxlength="14"  />
    					 	</div>
    						<div class="ic-input ic-split-20" >
								<label><common:message key="mailtracking.mra.defaults.proration.flightno" /></label>
								<ibusiness:flightnumber carrierCodeProperty="carrierCode" flightCodeProperty="flightNo" carrierCodeMaxlength="3"
								flightCodeMaxlength="5" componentID="CMP_MRA_DEFAULTS_PRORATION_FLIGHTNO" id="flightNo"/>
							</div>
							<div class="ic-input ic-split-20" >
								<label><common:message key="mailtracking.mra.defaults.proration.flightdate" /></label>
								<ibusiness:calendar property="flightDate" type="image" componentID="CMP_MRA_DEFAULTS_PRORATION_FLIGHTDATE" id="date" />
							</div>

								<div class="ic-button-container">
									<ihtml:nbutton property="btList" componentID="CMP_MRA_DEFAULTS_PRORATION_LIST" accesskey="L" >
										<common:message key="mailtracking.mra.defaults.proration.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_PRORATION_CLEAR" accesskey="C" >
										<common:message key="mailtracking.mra.defaults.proration.btn.clear" />
									</ihtml:nbutton>
								</div>

					</div>
			</div>

		</div>
	</div>
	<div class="ic-main-container">
		<div class=" ic-border">
			<div class="ic-row ic-label-45">
									<div class="ic-input ic-split-20">
				  							<label><common:message key="mailtracking.mra.defaults.proration.origin" /></label>


				  							<ihtml:text property="origin"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_ORIGIN" maxlength="6"   />

				      						</div>

									<div class="ic-input ic-split-20">
				  							<label><common:message key="mailtracking.mra.defaults.proration.destination" /></label>


				  							<ihtml:text property="dest"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_DEST" maxlength="6"  />

				      						</div>
				      						<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.dsn" /></label>

											<ihtml:text property="dsn"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_DSN" maxlength="4"/>
				  						</div>
											<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.totwt" />
												<logic:present name="form" property="displayWeightUnit">
												(<common:write name="form" property="displayWeightUnit" unitFormatting="false" />)
												</logic:present>
											</label>

											<ihtml:text property="totWt"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_TOTWT" maxlength="16"   style="text-align:right"/>
				  						</div>
				  						<div class="ic-input ic-split-20">

											<label><common:message key="mailtracking.mra.defaults.proration.category" /></label>



											<ihtml:text property="category"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_CATEGORY" maxlength="2"   />

										</div>

				  					  </div>
				  					 <div class="ic-row ic-label-45">
									<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.gpa" /></label>


											<ihtml:text property="gpa"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_GPA" maxlength="5"  />

										</div>
										<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.gpaname" /></label>


											<ihtml:text property="gpaName"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_GPANAME" maxlength="50"   />

										</div>
										<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.rsn" /></label>


											<ihtml:text property="rsn"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_RSN" maxlength="3"  />
										</div>
										<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.proration.subclass" /></label>


											<ihtml:text property="subClass"  readonly="true" componentID="CMP_MRA_DEFAULTS_PRORATION_SUBCLASS" maxlength="3"  />
										</div>


				  					  </div>

					</div>

		 <div class="ic-row ">

			<h4><common:message key="mailtracking.mra.defaults.proration.primaryprorate" /></h4>
			 	 <div class="tableContainer table-border-solid" id="div1" style="height:270px;"><!--modified by A-7371 as part of ICRD-249058 -->
					<table class="fixed-header-table"  width="100%" id="primarytable" >
					  <thead>
					  <tr class="ic-th-all">
										<th id="td0" style="width:4%"/>
											<th id="td1" style="width:3%"/>
											<th id="td2" style="width:3%"/>
											<th id="td3" style="width:3%"/>
											<th id="td4" style="width:7%"/>
											<th id="td5" style="width:3%"/>
											<th id="td6" style="width:4%"/>
											<th id="td7" style="width:4%"/>
											<th id="td8" style="width:5%"/>
											<th id="td9" style="width:8%"/>
											<th id="td10" style="width:7%"/>
											<th id="td11" style="width:8%"/>
											<th id="td12" style="width:7%"/>
											<th id="td13" style="width:8%"/>
											<th id="td14" style="width:7%"/>
											<th id="td15" style="width:7%"/>
											<th id="td16" style="width:7%"/>
											<th id="td17" style="width:4%"/>
											<th id="td18" style="width:5%"/>



					  </tr>
						<tr class="iCargoTableHeadingLeft" >

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
						  <common:message key="mailtracking.mra.defaults.proration.carrcode" /></td>

						  <td colspan="2" class="iCargoTableHeaderLabel"  >
							<common:message key="mailtracking.mra.defaults.proration.sector" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.noofmailbags" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.wt" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.proratemethod" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.proratefactor" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.proratepercentage" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.payorrecieve" /></td>

						  <td colspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.usd" /></td>

						  <td colspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.basecurrency" /></td>

						  <td colspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.sdr" /></td>

						  <td colspan="2" valign="top" class="iCargoTableHeaderLabel" >
						  	<common:message key="mailtracking.mra.defaults.proration.contractcurrency" /></td>

						  <td rowspan="2" valign="top" class="iCargoTableHeaderLabel" >
						  	<common:message key="mailtracking.mra.defaults.proration.contractcurrencycode" /></td>

						  <td rowspan="2" class="iCargoTableHeaderLabel" >
							<common:message key="mailtracking.mra.defaults.proration.sectorstatus" /></td>

						</tr>

						<tr>
						<td class="iCargoTableHeaderLabel" >
						<span  style="width:40px;" readonly="true">	<common:message key="mailtracking.mra.defaults.proration.from" /></span></td>
						<td class="iCargoTableHeaderLabel" >
						<span  style="width:60px;" readonly="true"><common:message key="mailtracking.mra.defaults.proration.to" /></span></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
						<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
						</tr>




					  </thead>

					  <tbody>

					  <logic:present name="PrimaryProrationVOs">
						<logic:iterate id="ProrationVO" name="PrimaryProrationVOs">

					  <tr>

						<logic:present name="ProrationVO" property="carrierCode">
							<bean:define id="carrierCode" name="ProrationVO" property="carrierCode" />
							<td class="iCargoTableDataTd">
							<%=String.valueOf(carrierCode)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="carrierCode">
							<td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="sectorFrom">
							  <bean:define id="sectorFrom" name="ProrationVO" property="sectorFrom" />
							  <td class="iCargoTableDataTd">
							  <%=String.valueOf(sectorFrom)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="sectorFrom">
						  	 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="sectorTo">
							  <bean:define id="sectorTo" name="ProrationVO" property="sectorTo" />
							  <td class="iCargoTableDataTd">
							  <%=String.valueOf(sectorTo)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="sectorTo">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="numberOfPieces">
							  <bean:define id="numberOfPieces" name="ProrationVO" property="numberOfPieces" />
							  <td  style="text-align:right" class="iCargoTableDataTd">
							  <%=String.valueOf(numberOfPieces)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="numberOfPieces">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="weight">
							  <bean:define id="weight" name="ProrationVO" property="weight" />
							  <td   style="text-align:right" class="iCargoTableDataTd">
							  <common:write name="ProrationVO" property="weight"  />
								<logic:present name="form" property="displayWeightUnit">
								<common:write name="form" property="displayWeightUnit" unitFormatting="false" />
								</logic:present>
							  </td>
							  <% Pweight=Pweight+Double.parseDouble(String.valueOf(weight)); %>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="weight">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="prorationType">
							  <bean:define id="prorationType" name="ProrationVO" property="prorationType" />
							  <td class="iCargoTableDataTd">
							  <%=String.valueOf(prorationType)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationType">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="prorationFactor">
							  <bean:define id="prorationFactor" name="ProrationVO" property="prorationFactor" />
							  <td class="iCargoTableDataTd">
							  <%=String.valueOf(prorationFactor)%></td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationFactor">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="prorationPercentage">
							  <bean:define id="prorationPercentage" name="ProrationVO" property="prorationPercentage" />
							  <td  style="text-align:right" class="iCargoTableDataTd">
							  <bean:write name="ProrationVO" property="prorationPercentage" format="####"/>

						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationPercentage">
							 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="payableFlag">
							  <bean:define id="payableFlag" name="ProrationVO" property="payableFlag" />
							  <td class="iCargoTableDataTd">

								  <logic:present name="OneTimeValues">
										<logic:iterate id="oneTimeValue" name="OneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue"
												property="key" />
											<logic:equal name="parameterCode"
												value="mailtracking.mra.defaults.payflag">
												<bean:define id="parameterValues" name="oneTimeValue"
													property="value" />
												<logic:iterate id="parameterValue"
													name="parameterValues"
													type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue"
														property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue"
															property="fieldValue" />
															<logic:equal name="ProrationVO" property="payableFlag" value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												                 	<bean:define id="fieldDescription"
															         name="parameterValue" property="fieldDescription" />
												                     <%=String.valueOf(fieldDescription)%>
												             </logic:equal>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="payableFlag">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="prorationAmtInUsd">
							<td  style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO" id="prorationAmtInUsd"
								moneyproperty="prorationAmtInUsd"
								showCurrencySymbol="false" property="prorationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO" id="prorationAmtInUsd"
								moneyproperty="prorationAmtInUsd"
								showCurrencySymbol="false" property="prorationAmtInUsd"/>
													</logic:notEqual>

							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationAmtInUsd">
							  <td class="iCargoTableDataTd">&nbsp;</td>

						</logic:notPresent>
						<logic:present name="ProrationVO" property="surProrationAmtInUsd">
							<td  style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO" id="surProrationAmtInUsd"
								moneyproperty="surProrationAmtInUsd"
								showCurrencySymbol="false" property="surProrationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO" id="surProrationAmtInUsd"
								moneyproperty="surProrationAmtInUsd"
								showCurrencySymbol="false" property="surProrationAmtInUsd"/>
													</logic:notEqual>

							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="surProrationAmtInUsd">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="prorationAmtInBaseCurr">
							<td   style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="prorationAmtInBaseCurr"
								showCurrencySymbol="false" property="prorationAmtInBaseCurr" overrideRounding = "true"/>
								</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="prorationAmtInBaseCurr"
								showCurrencySymbol="false" property="prorationAmtInBaseCurr"/>
													</logic:notEqual>
							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationAmtInBaseCurr">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>
						<logic:present name="ProrationVO" property="surProrationAmtInBaseCurr">
							<td   style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProrationAmtInBaseCurr"
								showCurrencySymbol="false" property="surProrationAmtInBaseCurr" overrideRounding = "true"/>
									</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProrationAmtInBaseCurr"
								showCurrencySymbol="false" property="surProrationAmtInBaseCurr"/>
													</logic:notEqual>
							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="surProrationAmtInBaseCurr">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="prorationAmtInSdr">
							<td  style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="prorationAmtInSdr"
								showCurrencySymbol="false" property="prorationAmtInSdr" overrideRounding = "true"/>
							</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="prorationAmtInSdr"
								showCurrencySymbol="false" property="prorationAmtInSdr"/>
													</logic:notEqual>

							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="prorationAmtInSdr">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>
						<logic:present name="ProrationVO" property="surProrationAmtInSdr">
							<td  style="text-align:right" class="iCargoTableDataTd">
								<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProrationAmtInSdr"
								showCurrencySymbol="false" property="surProrationAmtInSdr" overrideRounding = "true"/>
									</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProrationAmtInSdr"
								showCurrencySymbol="false" property="surProrationAmtInSdr"/>
													</logic:notEqual>


							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="surProrationAmtInSdr">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="proratedAmtInCtrCur">
							<td  style="text-align:right" class="iCargoTableDataTd">
	<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="proratedAmtInCtrCur"
								showCurrencySymbol="false" property="proratedAmtInCtrCur" overrideRounding = "true"/>
									</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="proratedAmtInCtrCur"
								showCurrencySymbol="false" property="proratedAmtInCtrCur"/>
													</logic:notEqual>

							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="proratedAmtInCtrCur">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>
						<logic:present name="ProrationVO" property="surProratedAmtInCtrCur">
							<td  style="text-align:right" class="iCargoTableDataTd">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProratedAmtInCtrCur"
								showCurrencySymbol="false" property="surProratedAmtInCtrCur" overrideRounding = "true"/>
									</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay   name="ProrationVO"
								moneyproperty="surProratedAmtInCtrCur"
								showCurrencySymbol="false" property="surProratedAmtInCtrCur"/>
													</logic:notEqual>

								<bean:define id="surChg" name="ProrationVO" property="surProratedAmtInCtrCur"/>
								<%String surChrg=surChg.toString();%>
								<ihtml:hidden property="surCharge" value="<%=surChrg%>"/>
							</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="surProratedAmtInCtrCur">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>

						<logic:present name="ProrationVO" property="ctrCurrencyCode">
													  <bean:define id="ctrCurrencyCode" name="ProrationVO" property="ctrCurrencyCode" />
													  <td class="iCargoTableDataTd">
													  <%=String.valueOf(ctrCurrencyCode)%></td>
													  <% contractCurrency = String.valueOf(ctrCurrencyCode); %>
												</logic:present>
												<logic:notPresent name="ProrationVO" property="ctrCurrencyCode">
													 <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>


						<logic:present name="ProrationVO" property="sectorStatus">
							  <td class="iCargoTableDataTd">

								  <logic:present name="OneTimeValues">
										<logic:iterate id="oneTimeValue" name="OneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue"
												property="key" />
											<logic:equal name="parameterCode"
												value="mailtracking.mra.proration.sectorstatus">
												<bean:define id="parameterValues" name="oneTimeValue"
													property="value" />
												<logic:iterate id="parameterValue"
													name="parameterValues"
													type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue"
														property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue"
															property="fieldValue" />
															<logic:equal name="ProrationVO" property="sectorStatus" value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												                 	<bean:define id="fieldDescription"
															         name="parameterValue" property="fieldDescription" />
												                     <%=String.valueOf(fieldDescription)%>
												             </logic:equal>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
									</td>
						</logic:present>
						<logic:notPresent name="ProrationVO" property="sectorStatus">
							  <td class="iCargoTableDataTd">&nbsp;</td>
						</logic:notPresent>
					  </tr>

					  </logic:iterate>

					  	</logic:present>

					  </tbody>

					  <tfoot>

					  <tr>

						  <td style="border:none"><h4>Total</h4></td>
						   <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td>
						  <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay   property="totalInUsdForPri"    currencyCode="USD" name="form" overrideRounding = "true"/>
						  </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
<ibusiness:moneyDisplay   property="totalInUsdForPri"    currencyCode="USD" name="form"/>

													</logic:notEqual>
						  </td>
						  <td style="text-align:right">
						  <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInUsdForPri" name="form" currencyCode="USD" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInUsdForPri" name="form" currencyCode="USD"/>
						</logic:notEqual>
						 <!-- <ibusiness:moneyDisplay  property="totalSurchgInUsdForPri" name="form"  roundMoney="<%=roundingReq%>"/>-->
						  </td>
						  <td>
						   <logic:equal name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay   property="totalInBasForPri" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>" overrideRounding = "true"/>
						  </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay   property="totalInBasForPri" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>

													</logic:notEqual>
						  </td>
						  <td style="text-align:right">
						  <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInBasForPri" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"  overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInBasForPri" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
						</logic:notEqual>
						 <!-- <ibusiness:moneyDisplay  property="totalSurchgInBasForPri" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>" roundMoney="<%=roundingReq%>"/>-->
						  </td>
						  <td>
						  						   <logic:equal name="form" property="overrideRounding" value = "Y">

							 <ibusiness:moneyDisplay   property="totalInSdrForPri" name="form"  currencyCode="XDR" overrideRounding = "true"/>
							 </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							 <ibusiness:moneyDisplay   property="totalInSdrForPri" name="form"  currencyCode="XDR" />

													</logic:notEqual>
						  </td>
						    <td style="text-align:right">
							<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInSdrForPri" name="form" currencyCode="XDR" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInSdrForPri" name="form" currencyCode="XDR"/>
						</logic:notEqual>
						  <!--<ibusiness:moneyDisplay  property="totalSurchgInSdrForPri" name="form" currencyCode="XDR"  roundMoney="<%=roundingReq%>"/>-->

						  </td>
						  <td style="text-align:right">
						   <logic:equal name="form" property="overrideRounding" value = "Y">
						   <logic:equal name="form" property="currencyChangeFlag" value = "Y">
							<ibusiness:moneyDisplay   showCurrencySymbol="false" property="totalInCurForPri" name="form" currencyCode="<%=String.valueOf(form.getBaseCurrency())%>" overrideRounding = "true"/>
							</logic:equal>
						 <logic:notEqual name="form" property="currencyChangeFlag" value = "Y">
							&nbsp;
						</logic:notEqual>

							</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													 <logic:equal name="form" property="currencyChangeFlag" value = "Y">
							<ibusiness:moneyDisplay   showCurrencySymbol="false" property="totalInCurForPri" name="form" currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
						</logic:equal>
						 <logic:notEqual name="form" property="currencyChangeFlag" value = "Y">
							&nbsp;
													</logic:notEqual>

													</logic:notEqual>


						  </td>



						  <td style="text-align:right">
						  <logic:equal name="form" property="overrideRounding" value = "Y">
						 <logic:equal name="form" property="currencyChangeFlag" value = "Y">
							<ibusiness:moneyDisplay showCurrencySymbol="false" property="totalSurchgInCurForPri" name="form" currencyCode="<%=String.valueOf(form.getBaseCurrency())%>" overrideRounding = "true"/>
						</logic:equal>
						 <logic:notEqual name="form" property="currencyChangeFlag" value = "Y">
							&nbsp;
						</logic:notEqual>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
						 <logic:equal name="form" property="currencyChangeFlag" value = "Y">
							<ibusiness:moneyDisplay  showCurrencySymbol="false" property="totalSurchgInCurForPri" name="form" currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
						</logic:equal>
						 <logic:notEqual name="form" property="currencyChangeFlag" value = "Y">
							&nbsp;
						</logic:notEqual>

						</logic:notEqual>
						  <!-- <ibusiness:moneyDisplay showCurrencySymbol="false" property="totalSurchgInCurForPri" name="form" roundMoney="<%=roundingReq%>"/>-->

						  </td>
						   <td style="border:none"></td>
						   <td style="border:none"></td>

						</tr>
						 </tfoot>
					</table>

				  </div>
  </div>
				 <div class="ic-row ">
			  <h4><common:message key="mailtracking.mra.defaults.proration.secondaryprorate" /></h4>

				  <!-- Flight Discrepancy damage disc table $starts$-->
				  <div class="tableContainer table-border-solid" id="div2" style="height:270px;">
					<table width="100%" class="fixed-header-table" id="secondarytable" >
					  <thead>
					   <tr class="ic-th-all">
										<th id="td0" style="width:7%"/>
											<th id="td1" style="width:6%"/>
											<th id="td2" style="width:6%"/>
											<th id="td3" style="width:7%"/>
											<th id="td4" style="width:6%"/>
											<th id="td5" style="width:6%"/>
											<th id="td6" style="width:5%"/>
											<th id="td7" style="width:8%"/>
											<th id="td8" style="width:8%"/>
											<th id="td9" style="width:8%"/>
											<th id="td10" style="width:8%"/>
											<th id="td11" style="width:8%"/>
											<th id="td12" style="width:8%"/>
											<th id="td13" style="width:9%"/>



					  </tr>
						<tr class="iCargoTableHeadingLeft">

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.flightNumber"/></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.sector" /> </span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.noofmailbags" /></span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.wt" /></span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.proratemethod" /></span>

						  </td>

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.proratepercentage" /></span>

						  </td>

						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.usd" /></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.basecurrency" /></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.sdr" /></span>

						  </td>

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.sectorstatus" /></span>


						  </td>
						</tr>
						<tr>
							<td class="iCargoTableHeaderLabel">
							<span  style="width:40px;" readonly="true" ><common:message key="mailtracking.mra.defaults.proration.from" /></span></td>
							<td class="iCargoTableHeaderLabel">
							<span  style="width:60px;" readonly="true" ><common:message key="mailtracking.mra.defaults.proration.to" /></span></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
							<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.mailchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.surchg" /></td>
						</tr>
					  </thead>

					      <tbody>

					    					  <logic:present name="SecondaryProrationVOs">
					    						<logic:iterate id="ProrationVO" name="SecondaryProrationVOs">

					    					  <tr>

					    						<logic:present name="ProrationVO" property="flightNumber">
					    							<bean:define id="carrierCode" name="ProrationVO" property="carrierCode" />
													<bean:define id="fltnum" name="ProrationVO" property="flightNumber" />
					    							<td  class="iCargoTableDataTd">
					    							<%=String.valueOf(carrierCode)%>  <%=String.valueOf(fltnum)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="flightNumber">
					    							<td class="iCargoTableDataTd"><bean:write name="ProrationVO" property="carrierCode"/></td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorFrom">
					    							  <bean:define id="sectorFrom" name="ProrationVO" property="sectorFrom" />
					    							  <td  class="iCargoTableDataTd">
					    							  <%=String.valueOf(sectorFrom)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="sectorFrom">
					    						  	 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorTo">
					    							  <bean:define id="sectorTo" name="ProrationVO" property="sectorTo" />
					    							  <td  class="iCargoTableDataTd">
					    							  <%=String.valueOf(sectorTo)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="sectorTo">
					    							 <td width="10%" class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>

					    						<logic:present name="ProrationVO" property="numberOfPieces">
					    							  <bean:define id="numberOfPieces" name="ProrationVO" property="numberOfPieces" />
					    							  <td  style="text-align:right" width="10%" class="iCargoTableDataTd">
					    							  <%=String.valueOf(numberOfPieces)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="numberOfPieces">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="weight">
					    							  <bean:define id="weight" name="ProrationVO" property="weight" />
					    							  <td  style="text-align:right" width="10%" class="iCargoTableDataTd">
													  <common:write name="ProrationVO" property="weight" unitFormatting="false" />
														<logic:present name="form" property="displayWeightUnit">
														<common:write name="form" property="displayWeightUnit" unitFormatting="false" />
														</logic:present>
					    							  </td>
							  					  <% Sweight=Sweight+Double.parseDouble(String.valueOf(weight)); %>

					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="weight">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>

					    						<logic:present name="ProrationVO" property="prorationType">
					    							  <bean:define id="prorationType" name="ProrationVO" property="prorationType" />
					    							  <td width="10%" class="iCargoTableDataTd">
					    							  <%=String.valueOf(prorationType)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationType">
					    							 <td class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationPercentage">
					    							  <bean:define id="prorationPercentage" name="ProrationVO" property="prorationPercentage" />
					    							  <td  style="text-align:right"  class="iCargoTableDataTd">
					    							   <bean:write name="ProrationVO" property="prorationPercentage" format="####"/>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationPercentage">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationAmtInUsd">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													  <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInUsd"
					    								showCurrencySymbol="false" property="prorationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInUsd"
					    								showCurrencySymbol="false" property="prorationAmtInUsd"/>
													</logic:notEqual>
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInUsd">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInUsd">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInUsd"
													showCurrencySymbol="false" property="surProrationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInUsd"
													showCurrencySymbol="false" property="surProrationAmtInUsd"/>
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInUsd">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationAmtInBaseCurr">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													  <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInBaseCurr"
					    								showCurrencySymbol="false" property="prorationAmtInBaseCurr" overrideRounding = "true"/>
					    							</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInBaseCurr"
					    								showCurrencySymbol="false" property="prorationAmtInBaseCurr"/>
													</logic:notEqual>
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInBaseCurr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInBaseCurr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
												<logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInBaseCurr"
													showCurrencySymbol="false" property="surProrationAmtInBaseCurr" overrideRounding = "true"/>
												</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInBaseCurr"
													showCurrencySymbol="false" property="surProrationAmtInBaseCurr"/>
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInBaseCurr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
											</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationAmtInSdr">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													 <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInSdr"
					    								showCurrencySymbol="false" property="prorationAmtInSdr" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInSdr"
					    								showCurrencySymbol="false" property="prorationAmtInSdr"/>
													</logic:notEqual>
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInSdr">
											  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInSdr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
													 <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInSdr"
													showCurrencySymbol="false" property="surProrationAmtInSdr" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInSdr"
													showCurrencySymbol="false" property="surProrationAmtInSdr"/>
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInSdr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorStatus">
												  <td  class="iCargoTableDataTd">
												  <logic:present name="OneTimeValues">
												<logic:iterate id="oneTimeValue" name="OneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue"
														property="key" />
													<logic:equal name="parameterCode"
														value="mailtracking.mra.proration.sectorstatus">
														<bean:define id="parameterValues" name="oneTimeValue"
															property="value" />
														<logic:iterate id="parameterValue"
															name="parameterValues"
															type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue"
																property="fieldValue">
															<bean:define id="fieldValue" name="parameterValue"
																property="fieldValue" />
																<logic:equal name="ProrationVO" property="sectorStatus" value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																<bean:define id="fieldDescription"
																	 name="parameterValue" property="fieldDescription" />
															     <%=String.valueOf(fieldDescription)%>
												             </logic:equal>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present></td>
											</logic:present>
											<logic:notPresent name="ProrationVO" property="sectorStatus">
												  <td  class="iCargoTableDataTd">&nbsp;</td>
											</logic:notPresent>


					    					  </tr>
					    					  	</logic:iterate>
					  					</logic:present>

					  </tbody>
					  <tfoot>
					  <tr >
						  <td style="border:none"><h4>Total</h4></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td  style="text-align:right">
						  <logic:equal name="form" property="overrideRounding" value = "Y">
						  <ibusiness:moneyDisplay    property="totalInUsdForSec"  name="form" currencyCode="USD" overrideRounding = "true"/>
						  </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
						  <ibusiness:moneyDisplay    property="totalInUsdForSec"  name="form" currencyCode="USD"/>

													</logic:notEqual>
						  </td>
						  <td style="text-align:right">
						  <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInUsdForSec" name="form" currencyCode="USD" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInUsdForSec" name="form" currencyCode="USD"/>
						</logic:notEqual>
						  <!--<ibusiness:moneyDisplay  property="totalSurchgInUsdForSec" name="form"  roundMoney="<%=roundingReq%>"/>-->
						  </td>
						  <td  style="text-align:right">
				  <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalInBasForSec" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"  overrideRounding = "true"/>
						   	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalInBasForSec" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
													</logic:notEqual>
						  </td>
						<td style="text-align:right">
						 <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInBasForSec" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"  overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInBasForSec" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
						</logic:notEqual>
						  <!--<ibusiness:moneyDisplay  property="totalSurchgInBasForSec" name="form" currencyCode="<%=String.valueOf(form.getBaseCurrency())%>" roundMoney="<%=roundingReq%>"/>-->
						  </td>
						  <td  style="text-align:right">
						    <logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalInSdrForSec" name="form" currencyCode="XDR" overrideRounding = "true"/>
							</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalInSdrForSec" name="form" currencyCode="XDR"/>
													</logic:notEqual>



						  </td>
						  <td style="text-align:right">
						  	<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInSdrForSec" name="form" currencyCode="XDR" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalSurchgInSdrForSec" name="form" currencyCode="XDR"/>
						</logic:notEqual>
						   <!--<ibusiness:moneyDisplay  property="totalSurchgInSdrForSec" name="form" currencyCode="XDR" roundMoney="<%=roundingReq%>"/></td>-->
						  <td style="display:none">

						  </td>
						</tr>
					  </tfoot>
					</table>
				  </div>
				</div>
				</div>
				<div class="ic-foot-container paddR5">

					<div class="ic-button-container">
				   <logic:equal name="form" property="awmSpecificFlag" value = "KE">
					<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_AWM_PRORATION" property="btAWMProrate" accesskey="A" >
					<common:message key="mailtracking.mra.defaults.proration.btn.awmprorate" />
					</ihtml:nbutton>
					</logic:equal>


					<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_PRORATION_SURCHG" property="btSurcharge" accesskey="S" >
						<common:message key="mailtracking.mra.defaults.proration.btn.surcharge" />
					</ihtml:nbutton>

					<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_PRORATION_CLOSE" property="btClose" accesskey="O" >
						<common:message key="mailtracking.mra.defaults.proration.btn.close" />
					</ihtml:nbutton>

				</div>
				</div>
					</div>
	</ihtml:form>
<span style="display:none" id="tmpSpan"></span>

</div>



	</body>
</html:html>
