<%--************************************************************
* Project	 					: iCargo
* Module Code & Name      		: mra-defaults
* File Name						: BillingSiteMaster.jsp
* Date							: 14/11/2013
* Author(s)						: A-5219
 ****************************************************************--%>

<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

	
	
	
<html:html locale="true">
<head>
	
	
		
	

	<title>
	<common:message  key="mra.defaults.billingsite.title.billingSite" bundle="billingsiteresource" scope="request"/>
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/defaults/BillingSiteMaster_Script.jsp" />
	

</head>


<body>
	
	
	
	<business:sessionBean id="billingSiteVO" moduleName="mailtracking.mra.defaults" screenID="mailtracking.mra.defaults.billingsitemaster" method="get" attribute="billingSiteVO" />
	<business:sessionBean id="gpaCountriesVOs" moduleName="mailtracking.mra.defaults" screenID="mailtracking.mra.defaults.billingsitemaster" method="get" attribute="billingSiteGPACountiresVOs" />
	<business:sessionBean id="bankDetailsVOs" moduleName="mailtracking.mra.defaults" screenID="mailtracking.mra.defaults.billingsitemaster" method="get" attribute="billingSiteBankDetailsVOs" />			
																							
<bean:define id="form"
    	name="BillingSiteMasterForm"
    	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm"
    	toScope="page"/>

  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" >

	<ihtml:form action="/mra.defaults.billingsitemaster.do" >
	<ihtml:hidden property="checkFlag" /> 
	<ihtml:hidden property="status" /> 
	<ihtml:hidden property="siteExpired" />
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
  
	<div class="ic-content-main">
		<div class="ic-head-container">
			<div class="ic-filter-panel">
                <div class="ic-input-container">
					
						<div class="ic-row ic-label-35">
							<div class="ic-input ic-split-20 ic-mandatory">
								<label><common:message key="mra.defaults.billingsite.lbl.billingSiteCode"/></label>
								<ihtml:text property="billingSiteCode" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BILLINGSITECODE" maxlength="6" style="text-transform : uppercase;"/>
								<div class="lovImg">
								<img height="22" id="billingsitelov" name="billingsitelov" src="<%=request.getContextPath()%>/images/lov.png" width="22"/>
                                </div>								
							</div>			   
							<div class="ic-input ic-split-20">			   
								<label><common:message key="mra.defaults.billingsite.lbl.billingSite"/></label>
										<logic:present name="billingSiteVO">
											<logic:present name="billingSiteVO" property="billingSite">
												<bean:define id="billingSite" name="billingSiteVO" property="billingSite"/>
													<ihtml:text property="billingSite" value="<%=(String)billingSite%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BILLINGSITE" maxlength="16"/>
											</logic:present>
											<logic:notPresent name="billingSiteVO" property="billingSite">
													<ihtml:text property="billingSite" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BILLINGSITE" maxlength="50"/>
											</logic:notPresent>
										</logic:present>
										<logic:notPresent name="billingSiteVO">
													<ihtml:text property="billingSite" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BILLINGSITE" maxlength="50"/>
										</logic:notPresent>
							</div>
							<div class="ic-button-container">
							<ihtml:nbutton accesskey="L" property="btDisplay" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BTN_DISPLAY">
								<common:message key="mra.defaults.billingsite.btn.Display"/>
							</ihtml:nbutton>
							<ihtml:nbutton accesskey="C" property="btClear" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BTN_CLEAR">
								<common:message key="mra.defaults.billingsite.btn.Clear"/>
							</ihtml:nbutton>
							</div>
						
							<div class="ic-input ic-split-20 ic-mandatory">
								<label><common:message key="mra.defaults.billingsite.lbl.fromDate"/></label>
								
									<logic:present name="billingSiteVO">
										<logic:present name="billingSiteVO" property="fromDate">
											<bean:define id="fromDate" name="billingSiteVO" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<ibusiness:calendar id="fromDate" property="fromDate" value="<%=TimeConvertor.toStringFormat(fromDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT)%>" type="image" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FROMDATE"/>
										</logic:present>
										<logic:notPresent name="billingSiteVO" property="fromDate">
											<ibusiness:calendar id="fromDate" property="fromDate" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FROMDATE" type="image" maxlength="11" readonly="false"/>
										</logic:notPresent>
									</logic:present>
									<logic:notPresent name="billingSiteVO">
										<ibusiness:calendar id="fromDate" property="fromDate" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FROMDATE" type="image" maxlength="11" readonly="false"/>
									</logic:notPresent>
							</div>
							<div class="ic-input ic-split-20 ic-mandatory">	
							  <label><common:message key="mra.defaults.billingsite.lbl.toDate"/></label>
								
								<logic:present name="billingSiteVO">
									<logic:present name="billingSiteVO" property="toDate" >
										<bean:define id="toDate" name="billingSiteVO" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
										<ibusiness:calendar id="toDate" property="toDate" value="<%=TimeConvertor.toStringFormat(toDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT)%>" type="image" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_TODATE"/>
									</logic:present>
									<logic:notPresent name="billingSiteVO" property="toDate">
										<ibusiness:calendar id="toDate" property="toDate" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_TODATE" type="image" maxlength="11" readonly="false"/>
									</logic:notPresent>
								</logic:present>
								<logic:notPresent name="billingSiteVO">
									<ibusiness:calendar id="toDate" property="toDate" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_TODATE" type="image" maxlength="11" readonly="false"/>
								</logic:notPresent>
							</div>
						</div>
					</div>
				</div>
			</div>
		
		<div class="ic-main-container">	
			<div class="ic-row">
				<div class="ic-col-45">
					<fieldset class="ic-field-set" id="address">
						<legend ><common:message key="mra.defaults.billingsite.Address"/></legend>
							<div class="ic-row">
								<div class="ic-input ic-split-100 ic-left">				
										
										
										<label><common:message key="mra.defaults.billingsite.airlineAddress"/></label>
										<logic:present name="billingSiteVO">
											<logic:present name="billingSiteVO" property="airlineAddress">
												<bean:define id="airlineAddress" name="billingSiteVO" property="airlineAddress"/>
												<ihtml:textarea property="airlineAddress" value="<%=(String)airlineAddress%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_AIRLINEADDRESS"  maxlength="300" rows="4" cols="50" style="width:400px;"/>
											</logic:present>	
											<logic:notPresent name="billingSiteVO" property="airlineAddress">
												<ihtml:textarea property="airlineAddress" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_AIRLINEADDRESS" maxlength="300" rows="4" cols="50" style="width:400px;"/>
											</logic:notPresent>
										</logic:present>

										<logic:notPresent name="billingSiteVO">
											<ihtml:textarea property="airlineAddress" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_AIRLINEADDRESS" maxlength="300" rows="4" cols="50" style="width:400px;"/>
										</logic:notPresent>
								</div>
							</div>					
							<div class="ic-row">
								<div class="ic-input ic-split-100">		
									<label><common:message key="mra.defaults.billingsite.correspondenceAddress"/></label>
											<logic:present name="billingSiteVO">
												<logic:present name="billingSiteVO" property="correspondenceAddress">
													<bean:define id="correspondenceAddress" name="billingSiteVO" property="correspondenceAddress"/>
													<ihtml:textarea property="correspondenceAddress" value="<%=(String)correspondenceAddress%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CORRESADDRESS"  maxlength="300" rows="4" cols="50" style="width:400px;"/>
												</logic:present>	
												<logic:notPresent name="billingSiteVO" property="correspondenceAddress">
													<ihtml:textarea property="correspondenceAddress" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CORRESADDRESS" maxlength="300" rows="4" cols="50" style="width:400px;"/>
												</logic:notPresent>
											</logic:present>

											<logic:notPresent name="billingSiteVO">
												<ihtml:textarea property="correspondenceAddress" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CORRESADDRESS" maxlength="300" rows="4" cols="50" style="width:400px;"/>
											</logic:notPresent>
								</div>
							</div>
					</fieldset>
				</div>
				<div class="ic-col-55">
					<fieldset class="ic-field-set" id="countries">
						<legend ><common:message key="mra.defaults.billingsite.countries"/></legend>
							<div class="ic-row">
								<div class="ic-button-container">
										<a href="#" id="addLink" name="addLink" class="iCargoLink"><common:message key="mra.defaults.billingsite.lbl.AddLink"/>
										</a>
										| <a href="#" id="delLink" name="delLink" class="iCargoLink"><common:message key="mra.defaults.billingsite.lbl.DeleteLink"/>
										</a>
								</div>
							</div>
							<div class="ic-row">
								<div class="tableContainer" style="height:200px">
									<table class="fixed-header-table">
										<thead>
											<tr>
												<td class="iCargoTableHeader" width="5%">
													<input type="checkbox" name="checkAllCountriesBox" onclick="doCheckAll(this.form);" width="1%"/>
												</td>
												<td class="iCargoTableHeader" width="95%"><common:message key="mra.defaults.billingsite.gpacountry"/>
												</td>
											</tr>
										</thead>
										<tbody id="gpaCountriesTable">
											<logic:present name="gpaCountriesVOs">
												<logic:iterate id="gpaCountriesVO" name="gpaCountriesVOs" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO" indexId="rowCount">
													
														<logic:notEqual name="gpaCountriesVO" property="operationalFlag" value="D">
															<tr>
																<td class="iCargoTableDataTd ic-center">
																	<html:checkbox property="checkForCountry" onclick="toggleTableHeaderCheckbox('checkForCountry',this.form.checkAllCountriesBox)"  />
																</td>
																<td class="iCargoTableDataTd ic-center"  >
																	<logic:present name="gpaCountriesVO" property="gpaCountry">
																		<bean:define id="gpaCountry" name="gpaCountriesVO" property="gpaCountry"/>
																		<ihtml:text property="gpaCountries" indexId="rowCount" value="<%=(String)gpaCountry%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRIES" maxlength="20" style="text-transform:uppercase;width:400px;"/>
																	</logic:present >
																	<logic:notPresent name="gpaCountriesVO" property="gpaCountry">
																		<ihtml:text property="gpaCountries" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRIES" maxlength="20" style="text-transform:uppercase;width:400px;"/>
																	</logic:notPresent >
																	<div class="lovImgTbl valignT">
																	<img id="gpaCountryCodelov<%=rowCount%>"  name="gpaCountryCodelov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16"/>	
                                                                    </div>																	
																	<logic:present name="gpaCountriesVO" property="serialNumber">
																		<bean:define id ="serialNumber" name ="gpaCountriesVO" property="serialNumber" />
																		<ihtml:hidden property="serialNumber" value="<%=String.valueOf(serialNumber)%>" />
																	</logic:present>
																</td>	
																<logic:notEqual name="gpaCountriesVO" property="operationalFlag" value="">
																	<bean:define id="opFlag" name="gpaCountriesVO" property="operationalFlag"/>
																	<ihtml:hidden property="hiddenOpFlagForCountry" value="<%=String.valueOf(opFlag)%>" />
																</logic:notEqual>
																<logic:equal name="gpaCountriesVO" property="operationalFlag" value="">
																	<ihtml:hidden property="hiddenOpFlagForCountry" value="N" />
																</logic:equal>
																
															</tr>
														</logic:notEqual>
														<logic:equal name="gpaCountriesVO" property="operationalFlag" value="D">
															<ihtml:hidden property="hiddenOpFlagForCountry" value="D" />
															<logic:present name="gpaCountriesVO" property="gpaCountry">
																<bean:define id="gpaCountry" name ="gpaCountriesVO" property="gpaCountry"/>
																<ihtml:hidden property="gpaCountry" value="<%=String.valueOf(gpaCountry)%>" />
															</logic:present>
															<logic:present name="gpaCountriesVO" property="gpaCountry">
																<bean:define id="gpaCountry" name ="gpaCountriesVO" property="gpaCountry"/>
																<ihtml:hidden property="gpaCountry" value="<%=String.valueOf(gpaCountry)%>" />
															</logic:present>
														</logic:equal>
													
												</logic:iterate>
											</logic:present>
											<tr template="true" id="billingsiteCountryTemplateRow" style="display:none">
												<ihtml:hidden property="hiddenOpFlagForCountry" value="NOOP" />
												<ihtml:hidden property="serialNumberForCountry"  />
												<td class="iCargoTableDataTd ic-center">
													<html:checkbox property="checkForCountry" onclick="toggleTableHeaderCheckbox('checkForCountry',this.form.checkAllCountriesBox)" />
												</td>
												<td class="ic-center">
													<ihtml:text property="gpaCountries" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRIES" maxlength="20" style="text-transform:uppercase;width:400px;"/>
													<div class="lovImgTbl valignT">
													<img id="gpaCountryCodelov"  name="gpaCountryCodelov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16"/>	
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
					</fieldset>	
				</div>
			</div>	 
			<div class="ic-row">	
				<fieldset class="ic-field-set" id="bank">
					<legend class="iCargoLegend"><common:message key="mra.defaults.billingsite.bankdetails"/>
					</legend>
						<div class="ic-row">
							<div class="ic-button-container">
								<a href="#" id="add" name="add" class="iCargoLink"><common:message key="mra.defaults.billingsite.lbl.AddLink"/>
								</a>
								| <a href="#" id="del" name="del" class="iCargoLink"><common:message key="mra.defaults.billingsite.lbl.DeleteLink"/>
								</a>
							</div>
						</div>
						<div class="ic-row">
							<div class="tableContainer" style="height:160px">
								<table class="fixed-header-table">
									<thead>
										<tr>
											<td class="iCargoTableHeader" width="2%">
												<input type="checkbox" name="checkAllBanksBox" onclick="doCheckAll(this.form);" width="1%" />
											</td>
											<td class="iCargoTableHeader" width="13%"><common:message key="mra.defaults.billingsite.currency"/><span class="iCargoMandatoryFieldIcon">*</span>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.bankname"/>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.branch"/>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.accno"/>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.city"/>
											</td>
											<td class="iCargoTableHeader" width="13%"><common:message key="mra.defaults.billingsite.country"/>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.swiftcode"/>
											</td>
											<td class="iCargoTableHeader" width="12%"><common:message key="mra.defaults.billingsite.ibanno"/>
											</td>
										</tr>
									</thead>
									<tbody id="bankDetailsTable">
										<logic:present name="bankDetailsVOs">
											<logic:iterate id="bankDetailsVO" name="bankDetailsVOs" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO" indexId="rowCount">
												
													<logic:notEqual name="bankDetailsVO" property="operationalFlag" value="D">
														<tr>
															<logic:notEqual name="bankDetailsVO" property="operationalFlag" value="">
																<bean:define id="opFlag" name="bankDetailsVO" property="operationalFlag"/>
																<ihtml:hidden property="hiddenOpFlagForBank" value="<%=String.valueOf(opFlag)%>" />
															</logic:notEqual>
															<logic:equal name="bankDetailsVO" property="operationalFlag" value="">
															<ihtml:hidden property="hiddenOpFlagForBank" value="N" />
															</logic:equal>
															<td class="iCargoTableDataTd ic-center">
																<html:checkbox property="checkForBank" onclick="toggleTableHeaderCheckbox('checkForBank',this.form.checkAllBanksBox)"  />
															</td>
																		
															<td class="iCargoTableDataTd ic-center" >
																<logic:present name="bankDetailsVO" property="currency" >
																	<bean:define id="currency" name ="bankDetailsVO" property="currency"/>
																	<ihtml:text property="currencies" indexId="rowCount"  value="<%=(String)currency%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CURRENCY" size="10" maxlength="3" style="text-transform : uppercase;"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="currency">
																	<ihtml:text property="currencies" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CURRENCY"  maxlength="3" style="text-transform : uppercase;"/>
																</logic:notPresent>	
																<div class="lovImgTbl valignT">
																<img id="currencylov<%=rowCount%>"  name="currencylov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16"/>
															    </div>
															</td>
															<td class="iCargoTableDataTd ic-center" >		
																<logic:present name="bankDetailsVO" property="bankName" >
																	<bean:define id="bankName" name ="bankDetailsVO" property="bankName"/>
																	<ihtml:text property="bankName" indexId="rowCount"  value="<%=(String)bankName%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BANK" size="10" maxlength="50"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="bankName">
																	<ihtml:text property="bankName" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BANK"  size="10" maxlength="50"/>
																</logic:notPresent>
															</td>
															<td class="iCargoTableDataTd ic-center" >			
																<logic:present name="bankDetailsVO" property="branch" >
																	<bean:define id="branch" name ="bankDetailsVO" property="branch"/>
																	<ihtml:text property="branch" indexId="rowCount"  value="<%=(String)branch%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BRANCH" size="10" maxlength="20"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="branch">
																	<ihtml:text property="branch" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BRANCH"  size="10" maxlength="20" />
																</logic:notPresent>	
															</td>
															<td class="iCargoTableDataTd ic-center" >		
																<logic:present name="bankDetailsVO" property="accNo" >
																	<bean:define id="accNo" name ="bankDetailsVO" property="accNo"/>
																	<ihtml:text property="accno" indexId="rowCount"  value="<%=(String)accNo%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_ACCNO" size="10" maxlength="20"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="accNo">
																	<ihtml:text property="accno" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_ACCNO"  size="10" maxlength="20"/>
																</logic:notPresent>									
															</td>
															<td class="iCargoTableDataTd ic-center" >	
																<logic:present name="bankDetailsVO" property="city" >
																	<bean:define id="city" name ="bankDetailsVO" property="city"/>
																	<ihtml:text property="city" indexId="rowCount"  value="<%=(String)city%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CITY" size="10" maxlength="50"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="city">
																	<ihtml:text property="city" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CITY"  size="10" maxlength="50"/>
																</logic:notPresent>	
															</td>
															<td class="iCargoTableDataTd ic-center" >	
																<logic:present name="bankDetailsVO" property="country" >
																	<bean:define id="country" name ="bankDetailsVO" property="country"/>
																	<ihtml:text property="country" indexId="rowCount"  value="<%=(String)country%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRY" size="10" maxlength="20"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="country">
																	<ihtml:text property="country" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRY"  size="10" maxlength="20"/>
																</logic:notPresent>	
															</td>
															<td class="iCargoTableDataTd ic-center" >	
																<logic:present name="bankDetailsVO" property="swiftCode" >
																	<bean:define id="swiftCode" name ="bankDetailsVO" property="swiftCode"/>
																	<ihtml:text property="swiftCode" indexId="rowCount"  value="<%=(String)swiftCode%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SWIFTCODE" maxlength="20"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="swiftCode">
																	<ihtml:text property="swiftCode" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SWIFTCODE"  size="10" maxlength="20"/>
																</logic:notPresent>	
															</td>
															<td class="iCargoTableDataTd ic-center">	
																<logic:present name="bankDetailsVO" property="ibanNo" >
																	<bean:define id="ibanNo" name ="bankDetailsVO" property="ibanNo"/>
																	<ihtml:text property="ibanNo" indexId="rowCount"  value="<%=(String)ibanNo%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_IBAN" size="10" maxlength="35"/>
																</logic:present>		
																<logic:notPresent  name="bankDetailsVO" property="ibanNo">
																	<ihtml:text property="ibanNo" indexId="rowCount" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_IBAN"  size="10" maxlength="35"/>
																</logic:notPresent>	
															</td>
																		
														</tr>
													</logic:notEqual>
													<logic:equal name="bankDetailsVO" property="operationalFlag" value="D">
														<ihtml:hidden property="hiddenOpFlagForBank" value="D" />
														<logic:present name="bankDetailsVO" property="currency">
															<bean:define id="currency" name ="bankDetailsVO" property="currency"/>
															<ihtml:hidden property="currencies" value="<%=String.valueOf(currency)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="bankName">
															<bean:define id="bankName" name ="bankDetailsVO" property="bankName"/>
															<ihtml:hidden property="bankName" value="<%=String.valueOf(bankName)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="branch">
															<bean:define id="branch" name ="bankDetailsVO" property="branch"/>
															<ihtml:hidden property="branch" value="<%=String.valueOf(branch)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="accno">
															<bean:define id="accno" name ="bankDetailsVO" property="accno"/>
															<ihtml:hidden property="accno" value="<%=String.valueOf(accno)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="city">
															<bean:define id="city" name ="bankDetailsVO" property="city"/>
															<ihtml:hidden property="city" value="<%=String.valueOf(city)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="country">
															<bean:define id="country" name ="bankDetailsVO" property="country"/>
															<ihtml:hidden property="country" value="<%=String.valueOf(country)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="swiftCode">
															<bean:define id="swiftCode" name ="bankDetailsVO" property="swiftCode"/>
															<ihtml:hidden property="swiftCode" value="<%=String.valueOf(swiftCode)%>" />
														</logic:present>
														<logic:present name="bankDetailsVO" property="ibanNo">
															<bean:define id="ibanNo" name ="bankDetailsVO" property="ibanNo"/>
															<ihtml:hidden property="ibanNo" value="<%=String.valueOf(ibanNo)%>" />
														</logic:present>
													</logic:equal>
												
											</logic:iterate>
										</logic:present>
										<tr template="true" id="billingsiteBankTemplateRow" style="display:none">
											<ihtml:hidden property="hiddenOpFlagForBank" value="NOOP" />
											<ihtml:hidden property="serialNumberForBank"  />
											<td class="iCargoTableDataTd ic-center" >
												<html:checkbox property="checkForBank" onclick="toggleTableHeaderCheckbox('checkForBank',this.form.checkAllBanksBox)" />
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="currencies" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CURRENCY" maxlength="3" style="text-transform : uppercase;"/>
												<div class="lovImgTbl valignT">
												<img id="currencylov"  name="currencylov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16"/>
												</div>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="bankName" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BANK" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="branch" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BRANCH" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="accno" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_ACCNO" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="city" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CITY" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="country" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_COUNTRY" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="swiftCode" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SWIFTCODE" maxlength="20"/>
											</td>
											<td class="iCargoTableDataTd" style="text-align:center; width=11%">
												<ihtml:text property="ibanNo" value="" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_IBAN" maxlength="35"/>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
				</fieldset>
			</div>
				
		
			<div class="ic-row">
				<div class="ic-col-35">
					<fieldset class="ic-field-set" id="signator">
						<legend class="iCargoLegend"><common:message key="mra.defaults.billingsite.signatories"/>
						</legend>
							<div class="ic-row ic-label-100">
								<div class="ic-input ic-split-50">	
									<label><common:message key="mra.defaults.billingsite.signator1"/></label>
										<logic:present name="billingSiteVO">
											<logic:present name="billingSiteVO" property="signator1">
												<bean:define id="signator1" name="billingSiteVO" property="signator1"/>
												<ihtml:text property="signator1" value="<%=(String)signator1%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR1" maxlength="20"/>
											</logic:present>
											<logic:notPresent name="billingSiteVO" property="signator1">
												<ihtml:text property="signator1" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR1" maxlength="20"/>
											</logic:notPresent>
										</logic:present>

										<logic:notPresent name="billingSiteVO">
											<ihtml:text property="signator1" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR1" maxlength="20"/>
										</logic:notPresent>
								</div>	
								<div class="ic-input ic-split-50">	
									<label><common:message key="mra.defaults.billingsite.designation"/></label>			
										<logic:present name="billingSiteVO">
													<logic:present name="billingSiteVO" property="designator1">
														<bean:define id="designator1" name="billingSiteVO" property="designator1"/>
														<ihtml:text property="designation1" value="<%=(String)designator1%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION1" maxlength="20"/>
													</logic:present>	
													<logic:notPresent name="billingSiteVO" property="designator1">
														<ihtml:text property="designation1" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION1" maxlength="20"/>
													</logic:notPresent>
												</logic:present>

												<logic:notPresent name="billingSiteVO">
													<ihtml:text property="designation1" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION1" maxlength="20"/>
												</logic:notPresent>
								</div>				
							</div>
							<div class="ic-row ic-label-100">
								<div class="ic-input ic-split-50">	
									<label><common:message key="mra.defaults.billingsite.signator2"/></label>			
												<logic:present name="billingSiteVO">
													<logic:present name="billingSiteVO" property="signator2">
														<bean:define id="signator2" name="billingSiteVO" property="signator2"/>
														<ihtml:text property="signator2" value="<%=(String)signator2%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR2" maxlength="20"/>
													</logic:present>	
													<logic:notPresent name="billingSiteVO" property="signator2">
														<ihtml:text property="signator2" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR2" maxlength="20"/>
													</logic:notPresent>
												</logic:present>

												<logic:notPresent name="billingSiteVO">
													<ihtml:text property="signator2" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_SIGNATOR2" maxlength="20"/>
												</logic:notPresent>
												
								</div>			
							
								<div class="ic-input ic-split-50">	
									<label><common:message key="mra.defaults.billingsite.designation"/></label>		
										<logic:present name="billingSiteVO">
											<logic:present name="billingSiteVO" property="designator2">
												<bean:define id="designator2" name="billingSiteVO" property="designator2"/>
												<ihtml:text property="designation2" value="<%=(String)designator2%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION2" maxlength="20"/>
											</logic:present>
											<logic:notPresent name="billingSiteVO" property="designator2">
											<ihtml:text property="designation2" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION2" maxlength="20" />
										</logic:notPresent>
										</logic:present>
										<logic:notPresent name="billingSiteVO">
											<ihtml:text property="designation2" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_DESIGNATION2" maxlength="20" />
										</logic:notPresent>
								</div>		
							</div>
					</fieldset>
				</div>	
				<div class="ic-col-15">
				</div>	
				<div class="ic-col-50">
					<div class="ic-row">
					  <div class="ic-input">
								<label><common:message key="mra.defaults.billingsite.freetext"/></label>
									<logic:present name="billingSiteVO">
										<logic:present name="billingSiteVO" property="freeText">
											<bean:define id="freeText" name="billingSiteVO" property="freeText"/>
											<ihtml:textarea property="freeText" id="freeText" value="<%=(String)freeText%>" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FREETEXT" maxlength="500" rows="10" cols="100" style="width:500px;"/>
										</logic:present>
										<logic:notPresent name="billingSiteVO" property="freeText">
											<ihtml:textarea property="freeText" id="freeText" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FREETEXT" maxlength="500" rows="10" cols="100" style="width:500px;"/>
										</logic:notPresent>
									</logic:present>

									<logic:notPresent name="billingSiteVO">
										<ihtml:textarea property="freeText" id="freeText" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_FREETEXT" maxlength="500" rows="10" cols="100" style="width:500px;"/>
									</logic:notPresent>
					</div>
				</div>	
			</div>
		</div>		
		</div>		
		<div class="ic-foot-container">
			<div class="ic-button-container paddR5">
				<div class="ic-row">
					<ihtml:nbutton accesskey="S" property="btSave" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BTN_SAVE" onclick="javaScript:CheckList()">
						<common:message key="mra.defaults.billingsite.btn.Save"/>
					</ihtml:nbutton>
					<ihtml:nbutton accesskey="E" property="btDelete" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BTN_DELETE">
						<common:message key="mra.defaults.billingsite.btn.Delete"/>
					</ihtml:nbutton>
					<ihtml:nbutton accesskey="O" property="btClose" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_BTN_CLOSE">
						<common:message key="mra.defaults.billingsite.btn.Close"/>
					</ihtml:nbutton>
				</div>	
			</div>
		</div>
		
	</div>	
	</ihtml:form>
</div>
		<span id="prevFormVals" style="display:none"></span>
		<span id="ignoreUnSaveCheck" style="display:none">N</span>
		<span id="ignoreHiddenCheck" style="display:none">N</span>
		<span id="currentSuggestCtrl" style="display:none;width:0px"></span>
		<div style="font-family: arial, helvetica, sans-serif;font-size: 11px;z-index: 21223;display:none;border:1px solid black;background-color:#77879D"  id="auto_completer_suggestions" ></div>
		<span id="suggestContainer" class="suggestspan" style="display:all;width:0px"></span>
		<iframe id="suggestContainerFrame" src="javascript:false;" scrolling="no" frameborder="0" style="position:absolute; top:0px; left:0px; display:none;filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);"></iframe>
		<span id="ajaxSpan" style="display:none;width:0px"></span>
		<iframe name="ajaxFrame" style="display:none;width:0px:height:0px">
		</iframe>
		<iframe src="<%=request.getContextPath()%>/jsp/includes/loadingblocker.html" style="top:0px;left:0px;width:0px;height:0px;position:absolute" id="loadNameFrame" name="loadNameFrame" scrolling="no" frameborder="0">
		</iframe>
		

	</body>
</html:html>
