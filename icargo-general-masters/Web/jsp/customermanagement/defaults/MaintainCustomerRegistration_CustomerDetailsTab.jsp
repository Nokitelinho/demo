<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@page import ="java.util.ArrayList"%>

<business:sessionBean id="currencies"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer" method="get"
	attribute="currency"/>

<business:sessionBean id="statusValues"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="customerStatus"/>
	
<business:sessionBean id="creditPeriods"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="creditPeriod"/>

<business:sessionBean id="customerVO"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="customerVO"/>

<business:sessionBean id="customerCodesFromListing"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="customerCodesFromListing"/>

<business:sessionBean id="defaultNotifyModes"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="defaultNotifyModes"/>

<business:sessionBean id="forwarderTypes"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="forwarderTypes"/>
			
<business:sessionBean id="billingPeriods"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="billingPeriods"/>

<business:sessionBean id="OneTimeValues"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get"
	attribute="OneTimeValues" />
			
<business:sessionBean id="customerIDGenerationRequired"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get"
	attribute="customerIDGenerationRequired" />
				
	<%boolean jtoflg = false;%>			 
	<common:xgroup>
		<common:xsubgroup id="JTO_SPECIFIC">
			<% jtoflg = true;%>
		</common:xsubgroup>
	</common:xgroup >
	
<div id ="generalDiv" >
	<div class="ic-row">
		<div class="ic-border">
			<div class="ic-row">			
				<div class="ic-input ic-mandatory ic-split-20" >
					<label class="ic-label-45">
						<common:message key="customermanagement.defaults.customerregistration.lbl.custname" scope="request"/>
					</label>
					<ihtml:text property="custName" maxlength="150" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTNAME"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-20" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.station" scope="request"/>
					</label>
					<ihtml:text property="station" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_STATION"/>
				    <div class="lovImg">
					<img height="22" id="stationlov"src="<%=request.getContextPath()%>/images/lov.png" width="22" />
				</div>
				</div>
				<div class="ic-input ic-split-18" >
					<label class="ic-label-40">
						<common:message key="customermanagement.defaults.customerregistration.lbl.shortcode" scope="request"/>
					</label>
					<ihtml:text property="customerShortCode" maxlength="14" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SHORTCODE"/>
				</div>
				<div class="ic-input ic-split-20" >
					<label class="ic-label-40">
						<common:message key="customermanagement.defaults.customerregistration.lbl.recipientcode" scope="request"/>
					</label>
					<ihtml:text id="recipientCode" property="recipientCode" maxlength="11" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_RECIPIENTCODE"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-21" >
					<label class="ic-label-60">
						<common:message key="customermanagement.defaults.customerregistration.lbl.iatacode"/>
					</label>
					<ihtml:text property="iataAgentCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_IATACODE" maxlength="15"/>
				</div>
				<common:xgroup>
					<common:xsubgroup id="INDIGO_SPECIFIC">
						<div class="ic-input ic-split-11" >
							<logic:present name="customerVO" property="vendorFlag" >
								<bean:define id="vendorFlag" name="customerVO" property="vendorFlag" />
								<logic:equal name="vendorFlag" value="Y">
									<input type="checkbox" name="vendorCheck" value="" checked  title="Vendor"/>
								</logic:equal>
								<logic:notEqual name="vendorFlag" value="Y">
									<input type="checkbox" name="vendorCheck" value=""  title="Vendor"/>
								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="customerVO" property="vendorFlag" >
								<input type="checkbox" name="vendorCheck" value=""  title="Vendor"/>
							</logic:notPresent>
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.vendor" scope="request"/>
							</label>
						</div>
					</common:xsubgroup>
				</common:xgroup >
			</div>
			<div class="ic-row">
				<div class="ic-input ic-split-20" >
					<label class="ic-label-45">
						<common:message key="customermanagement.defaults.customerregistration.lbl.establisheddate" scope="request"/>
					</label>
					<ibusiness:calendar componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ESTDATE" 
					id="establishedDate" property="establishedDate" type="image" />
				</div>
				<div class="ic-input ic-split-20 " >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.accno" scope="request"/>
					</label>
					<ihtml:text property="accountNumber"  componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ACCNO" maxlength="14"/>
				</div>
				<div class="ic-input ic-split-18 " >
					<label class="ic-label-40">
						<common:message key="customermanagement.defaults.customerregistration.lbl.vatRegNumber" />
					</label>
					<ihtml:text property="vatRegNumber" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_VatRegNumber" maxlength="20" />
				</div>
				<div class="ic-input ic-split-20" >
					<label class="ic-label-40">
						<common:message key="customermanagement.defaults.customerregistration.lbl.custgroup" scope="request"/>
					</label>
					<common:xgroup>
						<common:xsubgroup id="S7_SPECIFIC">
						<ihtml:text property="customerGroup" disabled="true" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTGROUP"/>
						<button type="button" class="iCargoLovButton" name="groupnamecustlov" id="groupnamecustlov"  disabled="true" />
						</common:xsubgroup>
					</common:xgroup >
					<common:xgroup>
					<common:xsubgroup id="RESTRICT_S7">
					<ihtml:text property="customerGroup"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTGROUP"/>
				    <div class="lovImg">
					<img height="22" width="22" id="groupcodelov"  src="<%=request.getContextPath()%>/images/lov.png" />
					</div>
					</common:xsubgroup>
					</common:xgroup >
				</div>
				<div class="ic-input ic-split-21 marginT10" >
				<logic:equal name="MaintainCustomerRegistrationForm" property="globalCustomer" value="on">
						<input type="checkbox" property="globalCustomer" title="Global Customer " name="globalCustomer" checked/>
					</logic:equal>
					<logic:notEqual name="MaintainCustomerRegistrationForm" property="globalCustomer" value="on">
						<input type="checkbox" property="globalCustomer" title="Global Customer " name="globalCustomer" />
					</logic:notEqual>
					<label class="ic-inline">
						<common:message key="customermanagement.defaults.customerregistration.lbl.global"/>
					</label>
				</div>
				<!--div class="ic-input ic-split-11" >
				</div-->			
			</div>
			<div class="ic-row">
				<div class="ic-input ic-split-40">
					<div class="ic-row">
						<div class="ic-input ic-mandatory ic-split-50" >
							<label class="ic-label-45">
								<common:message key="customermanagement.defaults.customerregistration.lbl.iacccsf.validfrom"/>&nbsp;
							</label>
							<ibusiness:calendar componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FromDate" property="fromDate" 
							type="image" id="validFrom" />
						</div>
						<div class="ic-input ic-mandatory ic-split-50" >
							<label class="ic-label-30">
								<common:message key="customermanagement.defaults.customerregistration.lbl.to"/>&nbsp;
							</label>
							<ibusiness:calendar componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ToDate" property="toDate" 
							type="image" id="validTo" />
						</div>
					</div>
					<div class="ic-row marginT5">
						<div class="ic-mandatory ic-split-100">
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.customertype" />&nbsp;
							</label>
							<logic:present name="customerVO" property="customerType">
								<bean:define id="customerType" name="customerVO"  property="customerType"  toScope="page"/>
								<ihtml:select property="customerType"
									 componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE" value="<%=(String)customerType%>">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="OneTimeValues">
								<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
								<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
										<logic:equal name="parameterCode" value="shared.customer.customertype" >
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
											</logic:present>
										</logic:equal >
									</logic:iterate >
								</logic:iterate>
							</logic:present>
								</ihtml:select>
							</logic:present>
							<logic:notPresent name="customerVO" property="customerType">
								<ihtml:select property="customerType"
									 componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE">
									 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									 <logic:present name="OneTimeValues">
										<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
										<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
												<logic:equal name="parameterCode" value="shared.customer.customertype" >
													<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
													</logic:present>
												</logic:equal >
											</logic:iterate >
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</logic:notPresent>
						</div>
					</div>
				</div>
				<div class="ic-input ic-split-38">
					
					    <div class="ic-row ">
						<label class="ic-label-30 ">	<common:message key="customermanagement.defaults.customerregistration.lbl.remarks" scope="request"/></label>
                        </div>
						<div class="ic-row">
						<!--Modified by A-7359 for ICRD-294843-->
						<ihtml:textarea property="remarks" style="width:505px" rows="2" cols="30" onkeypress="return validateMaxLengthofRemarks(this,500)" />
				</div>			
			</div>
			<div class="ic-input ic-split-21">
				<label ><common:message key="customermanagement.defaults.customerregistration.lbl.internalAccHld" scope="request"/></label>
				<div class="ic-row ">
					<logic:present name="customerVO" property="internalAccountHolder">
						<bean:define id="internalAccountHolder" name="customerVO" property="internalAccountHolder"/>
						<ihtml:text property="internalAccountHolder" value="<%=String.valueOf(internalAccountHolder)%>"           
						 componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INTACCHOLDER"/>
					</logic:present>
					<logic:notPresent name="customerVO" property="internalAccountHolder">
						<ihtml:text property="internalAccountHolder" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INTACCHOLDER"/>
					</logic:notPresent>
					<div class="lovImg">
						<img height="22" width="22" id="intaccholderlov" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.internalAccountHolder.value,'internalAccountHolder','1','internalAccountHolder','',0)"/>
		</div>
				</div>
				</div>
<div class="ic-input ic-split-30 marginT10">
				<logic:equal name="MaintainCustomerRegistrationForm" property="clearingAgentFlag" value="true">
					<input type="checkbox" property="clearingAgentFlag" title="Clearing Agent " name="clearingAgentFlag" checked/>
				</logic:equal>
				<logic:notEqual name="MaintainCustomerRegistrationForm" property="clearingAgentFlag" value="true">
					<input type="checkbox" property="clearingAgentFlag" title="Clearing Agent " name="clearingAgentFlag" />
				</logic:notEqual>
				<label class="ic-inline">
						<common:message key="customermanagement.defaults.customerregistration.lbl.clearingAgentFlag"/>
					</label>
				</div>			
			</div>
		</div>
	</div>
	<div class="ic-section">
		<div class="ic-row">
			<h3><common:message key="customermanagement.defaults.customerregistration.lbl.address" scope="request"/></h3>
		</div>
		<div class="ic-row">
		<div class="ic-input-container ic-input-round-border">
			<div class="ic-row">	
				<div class="ic-input ic-split-25" >
					<label>
						<common:message key="customermanagement.defaults.customerregistration.lbl.adr1" scope="request"/>
					</label>
					<ihtml:text property="address1" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ADR1" maxlength="100"/>
				</div>
				<div class="ic-input ic-split-23" >
					<label class="ic-label-25">
						<common:message key="customermanagement.defaults.customerregistration.lbl.adr2" scope="request"/>
					</label>
					<ihtml:text property="address2" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ADR2" maxlength="100"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-16" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.city" scope="request"/>
					</label>
					 <ihtml:text property="city" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CITY"/>
				</div>
				<div class="ic-input ic-split-16" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.area" scope="request"/>
					</label>				
					<ihtml:text property="area" maxlength="12" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AREA"/>
				</div>
				<div class="ic-input ic-split-13" >
					<label class="ic-label-30">
						 <common:message key="customermanagement.defaults.customerregistration.lbl.customsLocationNo" scope="request"/>
					</label>	
					<ihtml:text property="customsLocationNo" maxlength="4"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTLOCNO"/>
			</div>
			</div>
			<div class="ic-row">	
				<div class="ic-input ic-split-25 " >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.state" scope="request"/>
					</label>
					 <ihtml:text property="state" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_STATE" maxlength="25"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-23" >
					<label class="ic-label-30">
						<common:message key="customermanagement.defaults.customerregistration.lbl.country" scope="request"/>
					</label>
					<ihtml:text property="country" maxlength="12"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_COUNTRY"/>
					<div class="lovImg">
					<img height="22" id="countrylov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" />
				    </div>
				</div>
				<div class="ic-input ic-split-16" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.zip" scope="request"/>
					</label>
					<ihtml:text property="zipCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ZIP" maxlength="10" />
				</div>
				<div class="ic-input ic-split-16" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.tel" scope="request"/>
					</label>
					<ihtml:text property="telephone" maxlength="25" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TEL"/>
				</div>
				<div class="ic-input ic-split-13" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.eorino" scope="request"/>
					</label>
					 <ihtml:text property="eoriNo" maxlength="17" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_EORINO"/>
			</div>
			</div>
			<div class="ic-row">	
				<div class="ic-input ic-split-25" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.mob" scope="request"/>
					</label>
					<ihtml:text property="mobile" maxlength="25" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_MOB"/>
				</div>
				<div class="ic-input ic-split-23" >
					<label class="ic-label-30">
						<common:message key="customermanagement.defaults.customerregistration.lbl.fax" scope="request"/>
					</label>
					<ihtml:text property="fax" maxlength="25" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FAX"/>	
				</div>
				<div class="ic-input ic-split-16" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.sita" scope="request"/>
					</label>
					<ihtml:text property="sita" maxlength="30" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SITA"/>
				</div>
				<div class="ic-input ic-split-18" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.email" scope="request"/>
					</label>
					<ihtml:text property="email" maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_EMAIL" />
				</div>
				
			</div>
		</div>
		</div>
	</div>
	<div class="ic-section">
		<div class="ic-row">
			<h3><common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress" scope="request"/></h3>
		</div>
		<div class="ic-row">
		<div class="ic-input-container ic-input-round-border">
			<div class="ic-row">	
				<div class="ic-input ic-split-25" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.street" scope="request"/>
					</label>
					<ihtml:text property="billingStreet" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_STREET" maxlength="100"/>
				</div>
                <div class="ic-input ic-split-23" >
					<label class="ic-label-30">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.location" scope="request"/>
					</label>
					<ihtml:text property="billingLocation" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_LOCATION" maxlength="100"/>
				</div>
			
				<div class="ic-input ic-mandatory ic-split-16" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.city" scope="request"/>
					</label>
					<ihtml:text property="billingCityCode" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_CITY"/> 
				</div>
				<div class="ic-input ic-split-15" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.state" scope="request"/>
					</label>				
					<ihtml:text property="billingState" maxlength="20" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_STATE"/>
				</div>
				
			</div>
			<div class="ic-row">	
				<div class="ic-input ic-split-25" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.country" scope="request"/>
					</label>
					<ihtml:text property="billingCountry" maxlength="2"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_COUNTRY"/>
					<div class="lovImg">
					<img height="22" id="billingcountrylov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" />
				</div>
				</div>
				<div class="ic-input ic-split-23" >
					<label class="ic-label-30">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.zipcode" scope="request"/>
					</label>
					<ihtml:text property="billingZipcode"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_ZIPCODE" maxlength="10"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-16" >
					<label class="ic-label-33">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.telephone" scope="request"/>
					</label>
					<ihtml:text property="billingTelephone" 
					maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_TELEPHONE"/> 
				</div>
				<div class="ic-input ic-split-15" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.fax" scope="request"/>
					</label>				
					<ihtml:text property="billingFax" maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_FAX"/>
				</div>
			</div>
			<!--Added by A-7905 as part of ICRD-228463 starts-->
			<div class="ic-row">
			<div class="ic-input ic-mandatory ic-split-25" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.primaryemail" scope="request"/>
					</label>
					<ihtml:text property="billingEmail" maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_PRIMARYEMAIL"/>
			</div>
			<div class="ic-input ic-split-23" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.secondaryemail1" scope="request"/>
					</label>
					<ihtml:text property="billingEmailOne" maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_EMAILONE"/>
			</div>
			
			<div class="ic-input ic-split-25" >
					<label class="ic-label-15">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billingaddress.secondaryemail2" scope="request"/>
					</label>
					<ihtml:text property="billingEmailTwo" maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLADD_EMAILTWO"/>
			</div>
			</div>
			<!--Added by A-7905 as part of ICRD-228463 ends-->
		</div>
		</div>
	</div>
	<%if(jtoflg){%>
		<div class="ic-section">
			<div class="ic-row">
				<h3><common:message key="customermanagement.defaults.customerregistration.lbl.jto" scope="request"/></h3>
			</div>
			<div class="ic-row">
			<div class="ic-border">
				<div class="ic-row">	
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.customerCompanyCode"/>
						</label>
						<ihtml:text property="customerCompanyCode" 
						maxlength="5" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_COMPANYCODE"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							 <common:message key="customermanagement.defaults.customerregistration.lbl.branch"/>
						</label>
						<ihtml:text property="branch" maxlength="5" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BRANCH"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.naacsbbAgentCode"/>
						</label>
						<ihtml:text property="naacsbbAgentCode" 
						maxlength="5" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NACCSBBAGENTCODE"/>
					</div>
				</div>
				<div class="ic-row">	
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.naccsInvoiceCode"/>
						</label>
						<ihtml:text property="naccsInvoiceCode" 
						maxlength="12" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NACCSINVOICECODE"/>
					</div>
					<div class="ic-input ic-split-30" >
						<logic:equal name="MaintainCustomerRegistrationForm" property="spotForwarder" value="Y">
							<input type="checkbox" property="spotForwarder" title="Spot Forwarder " name="spotForwarder" checked/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="spotForwarder" value="Y">
							<input type="checkbox" property="spotForwarder" title="Spot Forwarder " name="spotForwarder" />
						</logic:notEqual>
						<label class="ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.spotForwarder" />
						</label>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.defaultHawbLength" />
						</label>
						<ihtml:text property="defaultHawbLength" 
						maxlength="3" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_DEFAULTHAWBLENGTH"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.billingPeriod" />
						</label>
						<logic:present name="customerVO" property="billPeriod">
							<bean:define id="billing" name="customerVO" property="billPeriod" type="java.lang.String"/>
							<ihtml:select property="billingPeriod" 
							value="<%=(String)billing%>" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLINGPERIOD">
								<logic:present name="billingPeriods">
									<bean:define id="billingPeriod" name="billingPeriods"/>
									<ihtml:options collection="billingPeriod" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="customerVO" property="billPeriod">
							<ihtml:select property="billPeriod" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLINGPERIOD">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="billingPeriods">
									<bean:define id="billingPeriod" name="billingPeriods"/>
									<ihtml:options collection="billingPeriod" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
							</ihtml:select>
						</logic:notPresent>	
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-30" >
						<label>
						<logic:equal name="MaintainCustomerRegistrationForm" property="handledCustomerImport" value="Y">
							<input type="checkbox" property="handledCustomerImport" title="Handled Customer For Import" 
							name="handledCustomerImport" checked/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="handledCustomerImport" value="Y">
							<input type="checkbox" property="handledCustomerImport" title="Handled Customer For Import" 
							name="handledCustomerImport" />
						</logic:notEqual>
						
							 <common:message key="customermanagement.defaults.customerregistration.lbl.handledCustomerImport" />
						</label>						
					</div>
					<div class="ic-input ic-split-30" >
					<label>
						<logic:equal name="MaintainCustomerRegistrationForm" property="poa" value="Y">
							<input type="checkbox" property="poa" title="POA " name="poa" checked/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="poa" value="Y">
							<input type="checkbox" property="poa" title="POA " name="poa" />
						</logic:notEqual>
						
							POA
						</label>						
					</div>
					<div class="ic-input ic-split-30" >
					<label>
						<logic:equal name="MaintainCustomerRegistrationForm" property="consolidator" value="Y">
							<input type="checkbox" property="consolidator" title="Consolidator " name="consolidator" checked/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="consolidator" value="Y">
							<input type="checkbox" property="consolidator" title="Consolidator " name="consolidator" />
						</logic:notEqual>
						
							<common:message key="customermanagement.defaults.customerregistration.lbl.consolidator" />
						</label>						
					</div>
					</div>
					<div class="ic-row marginT10">
					<div class="ic-input ic-split-30" >
					<label>
						<logic:equal name="MaintainCustomerRegistrationForm" property="handledCustomerForwarder" value="Y">
							<input type="checkbox" property="handledCustomerForwarder" title="Handled Customer For Forwarder" 
							name="handledCustomerForwarder" checked/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="handledCustomerForwarder" value="Y">
							<input type="checkbox" property="handledCustomerForwarder" title="Handled Customer For Forwarder" 
							name="handledCustomerForwarder" />
						</logic:notEqual>
						
							<common:message key="customermanagement.defaults.customerregistration.lbl.handledCustomerForwarder" />
						</label>						
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.forwarderType" />
						</label>
						<logic:present name="customerVO" property="forwarderType">
							<bean:define id="forwarder" name="customerVO" property="forwarderType" type="java.lang.String"/>
							<ihtml:select property="forwarderType" style="width:160" 
							value="<%=(String)forwarder%>" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FORWARDERTYPE">
								<html:option value =""/>
								<logic:present name="forwarderTypes">
									<bean:define id="types" name="forwarderTypes"/>
									<ihtml:options collection="types" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="customerVO" property="forwarderType">
							<ihtml:select property="forwarderType" 
							style="width:160" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FORWARDERTYPE">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="forwarderTypes">
									<bean:define id="types" name="forwarderTypes"/>
									<ihtml:options collection="types" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
							</ihtml:select>
						</logic:notPresent>
					</div>
				</div>
				<div class="ic-row marginT10">
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.naccsdeclarationcode"/>
						</label>
						<ihtml:text property="naccsDeclarationCode" 
						maxlength="7" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NACCSDECLARATIONCODE"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.naccsaircargoagentcode"/>
						</label>
						<ihtml:text property="naccsAircargoAgentCode" 
						maxlength="6" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NACCSAIRCARGOAGENTCODE"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.branchname"/>
						</label>
						<ihtml:text property="branchName" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NACCSBRANCHNAME"/>
					</div>
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.validity"/>
						</label>
						<ibusiness:calendar id="customerPOAValidity" type="image" 
						property="customerPOAValidity" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_VALIDITY"/>
					</div>
				</div>
			</div>
			</div>
		</div>
	<%}%>
	<div id="container2" class="tab1-container">
		<ul class="tabs">
            <li>
				<button type="button" class="tab" accesskey="c" onClick="return showPane(event,'pane1', this)" id="tab.customer">
					<common:message key="customermanagement.defaults.customerregistration.lbl.custdetails" scope="request"/>
				</button>
            </li>
            <li>
				<button type="button" class="tab" accesskey="o" id="tab.agent" onClick="return showPane(event,'pane2', this)" >
                    <common:message key="customermanagement.defaults.customerregistration.lbl.agent" scope="request"/></a>
                </button>
            </li>
			<li>
				<button type="button" class="tab" accesskey="o" id="tab.certifications" onClick="return showPane(event,'pane3', this)" >
                    <common:message key="customermanagement.defaults.customerregistration.lbl.certifications" scope="request"/></a>
                </button>
            </li>
			<li>
				<button type="button" class="tab" accesskey="o" id="tab.customerpreference" onClick="return showPane(event,'pane7', this)" >
                    <common:message key="customermanagement.defaults.customerregistration.lbl.customerpreferences" scope="request"/></a>
                </button>
            </li>
        </ul>
		<div class="tab-panes" style="width:100%">
				<jsp:include page="MaintainCustomerRegistration_CustomerDetailsTab_Details.jsp" />	
				<div id="pane3" class="content" >
					<jsp:include page="MaintainCustomerRegistration_IACCCSFDetails.jsp" />	
				</div>
				<div id="pane7" class="content" >
					<jsp:include page="MaintainCustomerRegistration_CustomerPreference.jsp" />	
				</div>

		</div>
	</div>
				
</div>
		