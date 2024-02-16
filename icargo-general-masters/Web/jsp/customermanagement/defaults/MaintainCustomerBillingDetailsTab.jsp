<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  TAB implemetation
* File Name				:  MaintainCustomerBillingDetailsTab.jsp
* Date					:  26-FEB-2013	
* Author(s)				:  A-5183
*************************************************************************/
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>

<bean:define id="form"
		name="MaintainCustomerRegistrationForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
		toScope="page" />

<business:sessionBean id="OneTimeValues"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="OneTimeValues" />
			
	<div id ="generalDiv">
		<div class="ic-row">
			<div class="ic-input-container ic-input-round-border">
				<div class="ic-row">
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40">
							<common:message key="customermanagement.defaults.customerregistration.lbl.agentType"/>
						</label>
						<ihtml:select property="agentInformation" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTTYPE" >
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
							<logic:present name="OneTimeValues">
								<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
								<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues">
										<logic:equal name="parameterCode" value="shared.agent.forwardertype" >
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
											</logic:present>
										</logic:equal >
									</logic:iterate >
								</logic:iterate>
							</logic:present>							
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40">
							<common:message key="customermanagement.defaults.customerregistration.lbl.cass"/>
						</label>
						<ihtml:select property="cassIdentifier" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CASSIDENTIFIER" onchange="disableRecipientCode();">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
							<logic:present name="OneTimeValues">
								<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
								<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues">
										<logic:equal name="parameterCode" value="cra.agentbilling.cass.cassidentifier" >
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
											</logic:present>
										</logic:equal >
									</logic:iterate >
								</logic:iterate>
							</logic:present>						
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40 ">
							<common:message key="customermanagement.defaults.customerregistration.lbl.cassimport"/>
						</label>
						<ihtml:select property="cassImportIdentifier" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CASSIMPORTIDENTIFIER" onchange="disableRecipientCode();" >
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
							<logic:present name="OneTimeValues">
								<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
								<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues">
										<logic:equal name="parameterCode" value="cra.agentbilling.cass.cassidentifier" >
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
											</logic:present>
										</logic:equal >
									</logic:iterate >
								</logic:iterate>
							</logic:present>						
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-20" >
						<label class="ic-label-30">
							<common:message key="customermanagement.defaults.customerregistration.lbl.salesId"/>
						</label>
						<ihtml:text property="salesId" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SALESID" maxlength="10"  />
					</div>
					<div class="ic-input ic-split-20 ic-center margint20" >
						<ihtml:checkbox  property="ownSalesFlag" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_OWNSALESFLAG"  />
						
							<common:message key="customermanagement.defaults.customerregistration.lbl.ownsales"/>
						
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40 ">
							<common:message key="customermanagement.defaults.customerregistration.lbl.holdingCompany"/>
						</label>
						<ihtml:text property="holdingCompany" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_HOLDCMP" maxlength="11" />
						<div class="lovImg">
							<img height="22" id="holdingCompanylov" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
						</div>
					</div>
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40 ">
							<common:message key="customermanagement.defaults.customerregistration.lbl.ownsales.normalcomm"/>
						</label>
						<ihtml:text property="normalComm"  
						style="text-align:right" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NORMALCOMM" maxlength="5" />
					</div>
					<div class="ic-input ic-split-20" >
						<label class="ic-label-40 ">
							<common:message key="customermanagement.defaults.customerregistration.lbl.fixedvalue" />
						</label>
						<ihtml:text property="normalCommFixed" 
						style="text-align:right" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FIXED" maxlength="12"  />
					</div>
					<common:xgroup>
						<common:xsubgroup id="TURKISH_SPECIFIC">
							<div class="ic-input ic-split-40" >
								<ihtml:checkbox  
								property="excludeRounding" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_EXCLUDERNDFLG"  />
								<label>
									<common:message key="customermanagement.defaults.customerregistration.lbl.excluderoundingflag"/>
								</label>
							</div>
						</common:xsubgroup>
					</common:xgroup >
				</div>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-input-container ic-input-round-border">
				<div class="ic-row">
					<common:xgroup>
						<common:xsubgroup id="CUSTOMER_OTHERS">
							<div class="ic-input ic-split-25" >
								<ihtml:checkbox 
								property="gibCustomerFlag" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GIBCUSTOMERFLAG" />
								<label class="ic-label-30">
									<common:message key="customermanagement.defaults.customerregistration.lbl.gibCustomerFlag"/>
								</label>
							</div>
						</common:xsubgroup>
					</common:xgroup >
					<div class="ic-input ic-split-25" >
						<ihtml:checkbox property="salesReportAgent" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SALESREPORTAGENT" />
						<label class="ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.salesreportagent"/>
						</label>
					</div>
					<div class="ic-input ic-split-25" >
						<ihtml:checkbox 
						property="controllingLocation"  componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTROLLINGLOCATION" />
						<label  class="ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.controllinglocation" />
						</label>
					</div>
					<div class="ic-input ic-split-25" >
						<ihtml:checkbox property="sellingLocation" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SELLINGLOCATION" />
						<label  class="ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.sellinglocation"/>
						</label>
					</div>
					<div class="ic-input ic-split-25" >
						<ihtml:checkbox property="proformaInv" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PROFORMAINV" />
						<label  class="ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.performainvoice"/>
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-row">
		<fieldset class="ic-field-set">
			<legend class="iCargoLegend">
				<common:message key="customermanagement.defaults.customerregistration.lbl.cldetails"/>
			</legend>
			<div class="ic-row">
				<div class="ic-input ic-mandatory ic-split-20" >
					<label class="ic-label-40">
						<common:message key="customermanagement.defaults.customerregistration.lbl.clagentcode"/>
					</label>
					<ihtml:text property="iataCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_IATAAGENTCODE" maxlength="11"  />
					<div class="lovImg">
					<img id="customerlovBilling" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />		
				    </div>
				</div>
				<div class="ic-input ic-mandatory ic-split-20" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.clname" />
					</label>
					<ihtml:text property="clName" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CLNAME" maxlength="25" />
				</div>
				<div class="ic-input ic-split-20" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.billto" />
					</label>
					<ihtml:select property="billingTo" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_BILLINGTO" >
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>										
						<logic:present name="OneTimeValues">
							<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues">
									<logic:equal name="parameterCode" value="shared.agent.billedto" >
										<logic:present name="parameterValue" property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
										</logic:present>
									</logic:equal >
								</logic:iterate >
							</logic:iterate>
						</logic:present>										
					</ihtml:select>
				</div>
				<%-- by A-7567 for ICRD-305684 --%>
				<div class="ic-input ic-split-20" >
					<label class="ic-label-35">
						<common:message key="customermanagement.defaults.customerregistration.lbl.applicableto" />
					</label>
					<ihtml:select property="cntLocBillingApplicableTo" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_APPLICABLETO" >
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>										
						<logic:present name="OneTimeValues">
							<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues">
									<logic:equal name="parameterCode" value="shared.customer.controlinglocationbillingapplicableto" >
										<logic:present name="parameterValue" property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
										</logic:present>
									</logic:equal >
								</logic:iterate >
							</logic:iterate>
						</logic:present>										
					</ihtml:select>
				</div>
				<div class="ic-input ic-split-20 margint20" >
					<logic:present name="MaintainCustomerRegistrationForm" property="invoiceClubbingIndicator">
						<logic:equal name="MaintainCustomerRegistrationForm" property="invoiceClubbingIndicator" value="on">
							<ihtml:checkbox 
							property="invoiceClubbingIndicator" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INVOICEBILLING" value="on"/>
						</logic:equal>
						<logic:notEqual name="MaintainCustomerRegistrationForm" property="invoiceClubbingIndicator" value="on">
							<ihtml:checkbox 
							property="invoiceClubbingIndicator" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INVOICEBILLING"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="MaintainCustomerRegistrationForm" property="invoiceClubbingIndicator">
						<ihtml:checkbox 
						property="invoiceClubbingIndicator" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INVOICEBILLING"/>
					</logic:notPresent>
					<label class="ic-label-40 ic-inline">
						<common:message key="customermanagement.defaults.customerregistration.lbl.invoicebilling" />
					</label>					
				</div>
			</div>
		</fieldset>
		</div>
		<div class="ic-row">
			<div class="ic-input-container ic-input-round-border">
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-20" >
						<label class="ic-label-40">
							<common:message key="customermanagement.defaults.customerregistration.lbl.airportcode"/>
						</label>
						<ihtml:text property="airportCode" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AirportCode"
						maxlength="3" readonly="false" />
						<div class="lovImg">
						<img id="airportCodelov" src="<%=request.getContextPath()%>/images/lov.png" alt = "<common:message key="customermanagement.defaults.customerregistration.lbl.airportcode"/>" width="22" height="22" />
					    </div>
					</div>
					<div class="ic-input ic-mandatory ic-split-55" >
						<label class="ic-label-18 ic-inline">
							<common:message key="customermanagement.defaults.customerregistration.lbl.handledaircrafttype"/>
						</label>
						<logic:present name="OneTimeValues">
							<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues">
									<logic:equal name="parameterCode" value="shared.aircraft.flighttypes" >
										<logic:present name="parameterValue" property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>&nbsp;
											<ihtml:checkbox 
											property="aircraftTypeHandled" 
											componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TRUCKS" value="<%=(String)fieldValue%>" />												<%=(String)fieldDescription%>														
										</logic:present>
									</logic:equal >
								</logic:iterate >
							</logic:iterate>
						</logic:present>
					</div>
					<div class="ic-input ic-mandatory ic-split-25" >
						<label class="ic-label-35">
							<common:message key="customermanagement.defaults.customerregistration.lbl.dateofexchange"/>
						</label>
						<ihtml:select property="dateOfExchange" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_DateofExchange" >							   <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>		
							<logic:present name="OneTimeValues">
								<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
								<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues">
										<logic:equal name="parameterCode" value="cra.defaults.dateofexchange" >
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
											</logic:present>
										</logic:equal >
									</logic:iterate >
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-20 margint20" >
						<ihtml:checkbox property="cassBillingIndicator" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CASSIMPORT"/>
						
							<common:message key="customermanagement.defaults.customerregistration.lbl.cassbilling"/>
												
					</div>
					<div class="ic-input ic-split-30 margint10" >						
						<label class="ic-label-35">
							<common:message key="customermanagement.defaults.customerregistration.lbl.importcasscode"/>
						</label>
						<ihtml:text property="cassCode" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_IMPORTCASSCODE"
							maxlength="15" readonly="false" />
					</div>
					<div class="ic-input ic-split-25 margint20" >
					   <ihtml:checkbox property="ccFeeDueGHA" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CCFEEDUEGHA"/>
						
						    <common:message key="customermanagement.defaults.customerregistration.lbl.ccfeeduegha"/>
						
					</div>
					<div class="ic-input ic-split-25 margint20" >
						<ihtml:checkbox 
property="billingThroughInterline" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CASSIMPORT_BILLINGTHROUGHINTERLINE"/>
					
							<common:message key="customermanagement.defaults.customerregistration.lbl.interlinebilling"/>
												
					</div>
					<div class="ic-input ic-split-25 margint10" >						
						<label class="ic-label-35">
							<common:message key="customermanagement.defaults.customerregistration.lbl.airlinecode"/>
						</label>
						<ihtml:text property="airlineCode" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AirlineCode"
							maxlength="3" readonly="false" />
						<div class="lovImg">
						<img id="airlineCodeLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="
						<common:message key="customermanagement.defaults.customerregistration.tooltip.airlinecode"/>"/>
					</div>
				</div>
			</div>
		</div>
		</div>
		<div class="ic-row">
			<div class="ic-col-35">
				<fieldset class="ic-field-set">
					<legend class="iCargoLegend">
						<common:message key="customermanagement.defaults.customerregistration.lbl.settlementtolerance" />
					</legend>
					<div class="ic-row">
						<div class="ic-input ic-split-50" >
							<ihtml:radio property="enduranceFlag"  value="awb" />
							<label  class="ic-inline">
								<common:message key="customermanagement.defaults.customerregistration.lbl.awb"/>
							</label>
						</div>
						<div class="ic-input ic-split-50" >
							<ihtml:radio property="enduranceFlag" value="invoice"  />
							<label  class="ic-inline">
								<common:message key="customermanagement.defaults.customerregistration.lbl.invoice"/>
							</label>
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-col-50 ic-label-45 margint10" >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.Tolerance" />
							</label>
							<ihtml:text property="endurancePercentage" 
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TOLERANCE" maxlength="12"   />
						</div>
						<div class="ic-input ic-col-50 ic-label-50 margint10" >
							<label >
								<common:message key="customermanagement.defaults.customerregistration.lbl.Tolerancevalue" />
							</label>
							<ihtml:text property="enduranceValue" 
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TOLERANCEVALUE" maxlength="12"  />
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-col-50 ic-label-45 margint10" >
							<label >
								<common:message key="customermanagement.defaults.customerregistration.lbl.maxvalue" />
							</label>
							<ihtml:text property="enduranceMaxValue" 
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_MAXVALUE" maxlength="12"  />
						</div>
						<div class="ic-input ic-col-50 ic-label-50 margint10" >
							<label >
								<common:message key="customermanagement.defaults.customerregistration.lbl.settlementcurrency"/>
							</label>
							<ihtml:text property="settlementCurrency" 
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SEETLCURRENCY" maxlength="12" />
							<div class="lovImg">
							<img height="22" id="settlecurrencyLov" 
							src="<%=request.getContextPath()%>/images/lov.png" width="22" />
						    </div>
						</div>
					</div>
				</fieldset>
			</div>
			<div class="ic-col-65">
				<div class="ic-input-container ic-input-round-border">
					<div class="ic-row">
						<div class="ic-input ic-split-15"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.billingcurrency"/>
							</label>
							<ihtml:text property="billingCode" 
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SEETLCURRENCY" maxlength="3" readonly="false" />
							<div class="lovImg">
							<img id="billingCurrencyLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" 
							alt="<common:message key="customermanagement.defaults.customerregistration.lbl.billingcurrency"/>" />
						</div>
						</div>
						<!-- Added by 201930 for IASCB-131790 start -->
						<div class="ic-input ic-split-15"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.casscountry"/>
							</label>
							<ihtml:text property="cassCountry" maxlength="3"  componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CASSCOUNTRY"/>
							<div class="lovImg">
								<img id="casscountrylov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" 
								alt="<common:message key="customermanagement.defaults.customerregistration.lbl.casscountry"/>" />
							</div>
						</div>
						<!-- Added by 201930 for IASCB-131790 end -->
						<div class="ic-input ic-split-20"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.billingperiod"/>
							</label>
							<ihtml:select property="billingPeriod" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_billingPeriod" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
								<logic:present name="OneTimeValues">
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues">
											<logic:equal name="parameterCode" value="cra.defaults.billingperiod" >
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
												</logic:present>
											</logic:equal >
										</logic:iterate >
									</logic:iterate>
								</logic:present>							
							</ihtml:select>
							<ihtml:hidden property="billingPeriodDescription" />
						</div>
						<div class="ic-input ic-split-20"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.billingduedays"/>
							</label>
							<ihtml:text property="billingDueDays" 
							componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_billingduedays" maxlength="3" />
						</div>
						<!-- Added by A-7555 for ICRD-319714 start-->
						<div class="ic-input ic-split-30"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.duedatebasis"/>
							</label>
							<ihtml:select property="dueDateBasis" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_DUEDATEBASIS" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
								<logic:present name="OneTimeValues">
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues">
											<logic:equal name="parameterCode" value="cra.agentbilling.invoice.duedatebasis" >
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
												</logic:present>
											</logic:equal >
										</logic:iterate >
									</logic:iterate>
								</logic:present>							
							</ihtml:select>
							<ihtml:hidden property="billingPeriodDescription" />
						</div>
						<!-- Added by A-7555 ends -->
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-47 margint10" >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.rmks" />
							</label>
							<ihtml:textarea property="billingremark" style="width:300px"  cols="54" rows="5" maxlength="100"/>
						</div>
						<div class="ic-input ic-split-30 ic-label-40 multi-select-wrap margint10"  >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.lbl.invoicetype" />
							</label>
							<ihtml:select multiSelect="true" multiSelectNoneSelectedText="SELECT" multiSelectMinWidth="100" 
							multiple="multiple" property="invoiceType" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_INVOICETYPE" >
								<logic:present name="OneTimeValues">
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues">
											<logic:equal name="parameterCode" value="shared.agent.invoicetype" >
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
												</logic:present>
											</logic:equal >
										</logic:iterate >
									</logic:iterate>
								</logic:present>										
							</ihtml:select>
						</div>
						<common:xgroup>
							<common:xsubgroup id="CUSTOMER_OTHERS">
								<div class="ic-input ic-split-23 ic-right" >
									<ihtml:checkbox 
									property="publicSectorFlag" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PUBLICSECTOR" />
									<label>
										<common:message key="customermanagement.defaults.customerregistration.lbl.publicSector"/>
									</label>
								</div>
							</common:xsubgroup>
						</common:xgroup>
						<common:xgroup>
							<common:xsubgroup id="TURKISH_SPECIFIC">
								<div class="ic-input ic-split-30 ic-right" >
									<label>
										<common:message key="customermanagement.defaults.customerregistration.lbl.gibRegistrationDate"/>
									</label>
									<ibusiness:calendar componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GIBREGISTRATIONDATE" property="gibRegistrationDate" type="image" id="gibregistrationdate" />
								</div>
							</common:xsubgroup>
						</common:xgroup>
						</div>
					</div>
				</div>
			</div>
		</div>		
		
							
