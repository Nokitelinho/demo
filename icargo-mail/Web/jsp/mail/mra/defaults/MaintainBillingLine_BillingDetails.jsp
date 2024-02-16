
<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"%>

<bean:define id="form" name="BillingLineForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"
	toScope="page" />
<business:sessionBean id="KEY_ONETIMES"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="oneTimeVOs" />
<business:sessionBean id="chargeRatingBasis"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.maintainbillingmatrix"
			method="get"
			attribute="ratingBasis" />

<business:sessionBean id="billingLineSurchargeChargeVO"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="billingLineSurWeightBreakDetails" />		



<div>
								<fieldset class="ic-field-set" style="height:680px;">
								<legend class="iCargoLegend"><common:message key="mailtacking.mra.defaults.maintainbillingline.legend.blgdtls" scope="request" /></legend>
								<div class="ic-row ic-border"><% int flag = 0;%>
								<logic:present name="BillingLineForm" property="reFlag">
										<logic:equal name="BillingLineForm" property="reFlag" value="R">
									<div class="ic-row">
										<% flag = 1;%>
										<div class="ic-input ic-split-30 ic_inline_chcekbox marginT15 ic-mandatory">
										<input type="radio" name="revExp" onclick="setBillByBillTo('R');" value="R" checked="true" tabindex="33"/> 
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.rec" scope="request" /></label>						
										</div>
										<div class="ic-input ic-split-40 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billingparty" scope="request" /></label>
										<ihtml:select
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillingParty"
										property="billingParty"
										tabindex="34"
										style="width:95px;">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="KEY_ONETIMES">
												<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
												<bean:define id="parameterCode" name="oneTimeValue"
												property="key" />
													<logic:equal name="parameterCode"
													value="mailtracking.mra.billingparty">
													<bean:define id="parameterValues" name="oneTimeValue"
													property="value" />
													<logic:iterate id="parameterValue"
													name="parameterValues"
													type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue"
														property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue"
														property="fieldValue" />
														<bean:define id="fieldDescription"
														name="parameterValue" property="fieldDescription" />
														<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
														<%=String.valueOf(fieldDescription)%>
														</ihtml:option>
														</logic:present>
													</logic:iterate>
													</logic:equal>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
										</div>
										<div class="ic-split-40 ic-mandatory marginT15">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billto" scope="request" /></label>
										<ihtml:text property="billTo" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillTo" 
										maxlength="100" readonly="false" tabindex="35" /><div class="lovImg">
										<img src="<%=request.getContextPath()%>/images/lov.png" 
										id="billToLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-input ic-split-30  ic_inline_chcekbox ic-mandatory marginT15">
										<input type="radio" name="revExp" onclick="setBillByBillTo('E');" value="E"
												tabindex="36"/>
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.pay" scope="request" /></label>
										</div>
										<div class="ic-split-70 ic-mandatory marginT15">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billby" scope="request" /></label>
										<ihtml:text property="billBy" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillBy"
													maxlength="100" readonly="false" tabindex="37" /> 
											<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png"
														id="billByLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
									</logic:equal>
									<logic:equal name="BillingLineForm" property="reFlag" value="E">
									<div class="ic-row">
										<% flag = 1;%>
										<div class="ic-input ic_inline_chcekbox ic-split-30 marginT15 ic-mandatory">
										<input
										type="radio" name="revExp" onclick="setBillByBillTo('R');"
										value="R"
										tabindex="38"/> 
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.rec" scope="request" /></label>									
										</div>
										<div class="ic-input ic-split-40 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billingparty" scope="request" /></label>
										<ihtml:select
													componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillingParty"
													property="billingParty"
													tabindex="39"
													style="width:95px;">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="KEY_ONETIMES">
														<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
															<bean:define id="parameterCode" name="oneTimeValue"
																property="key" />
															<logic:equal name="parameterCode"
																value="mailtracking.mra.billingparty">
																<bean:define id="parameterValues" name="oneTimeValue"
																	property="value" />
																<logic:iterate id="parameterValue"
																	name="parameterValues"
																	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue"
																		property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue"
																			property="fieldValue" />
																		<bean:define id="fieldDescription"
																			name="parameterValue" property="fieldDescription" />
																		<ihtml:option
																			value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																			<%=String.valueOf(fieldDescription)%>
																		</ihtml:option>
																	</logic:present>
																</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>
												</ihtml:select>
										</div>
										<div class="ic-input ic-split-30 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billto" scope="request" /></label>
										<ihtml:text property="billTo"
													componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillTo"
													maxlength="100" readonly="false" tabindex="40" /> <div class="lovImg"> <img
													src="<%=request.getContextPath()%>/images/lov.png"
													id="billToLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-input ic_inline_chcekbox ic-split-30 marginT15 ic-mandatory">
										<input
										type="radio" name="revExp" onclick="setBillByBillTo('E');"
										value="E"
										checked="true" tabindex="41"/>
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.pay" scope="request" /></label>
										</div>
										<div class="ic-input ic-split-70 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billby" scope="request" /></label>
										<ihtml:text property="billBy"
												componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillBy"
												maxlength="100" readonly="false" tabindex="42" /><div class="lovImg"> <img
												src="<%=request.getContextPath()%>/images/lov.png"
												id="billByLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
									</logic:equal>
									</logic:present>
									<%if (flag == 0) {%>
									<div class="ic-row">
										<div class="ic-input ic_inline_chcekbox ic-split-30 marginT15 ic-mandatory">
										<input
										type="radio" name="revExp" onclick="setBillByBillTo('R');"
										value="R"
										tabindex="43"/>
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.rec" scope="request" /></label>									
										</div>
										<div class="ic-input ic-split-40 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billingparty" scope="request" /></label>
										<ihtml:select
												componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillingParty"
												property="billingParty"
												tabindex="44"
												style="width:95px;">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="KEY_ONETIMES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
														<bean:define id="parameterCode" name="oneTimeValue"
															property="key" />
														<logic:equal name="parameterCode"
															value="mailtracking.mra.billingparty">
															<bean:define id="parameterValues" name="oneTimeValue"
																property="value" />
															<logic:iterate id="parameterValue" name="parameterValues"
																type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue"
																	property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue"
																		property="fieldValue" />
																	<bean:define id="fieldDescription"
																		name="parameterValue" property="fieldDescription" />
																	<ihtml:option
																		value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																		<%=String.valueOf(fieldDescription)%>
																	</ihtml:option>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</div>
										<div class="ic-input ic-split-30 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billto" scope="request" /></label>
										<ihtml:text property="billTo"
												componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillTo"
												maxlength="100" readonly="false" tabindex="45" /><div class="lovImg"> <img
												src="<%=request.getContextPath()%>/images/lov.png"
												id="billToLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-input ic_inline_chcekbox ic-split-30 marginT15 ic-mandatory">
										<input
										type="radio" name="revExp" onclick="setBillByBillTo('E');"
										value="E"
										tabindex="46"/>
										<label><common:message key="mailtracking.defaults.maintainbillingmatrix.tooltip.pay" scope="request" /></label>
										</div>
										<div class="ic-input ic-split-70 ic-mandatory">
										<label><common:message key="mailtacking.mra.defaults.maintainbillingline.billby" scope="request" /></label>
										<ihtml:text property="billBy"
												componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillBy"
												maxlength="100" readonly="false" tabindex="47" /> <div class="lovImg"><img
												src="<%=request.getContextPath()%>/images/lov.png"
												id="billByLov" height="22" width="22" alt="" /></div>
										</div>
									</div>
										<%}%>
								</div>
								<div class="ic-col-100">
								<div class="ic-input ic-split-50 ic-mandatory">
								<label><common:message key="mailtacking.mra.defaults.maintainbillingline.bldsec" scope="request" /></label>
								<ihtml:select
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillingSector"
								property="billedSector"
								tabindex="48">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode"
											value="mailtracking.mra.billingSector">
											<bean:define id="parameterValues" name="oneTimeValue"
											property="value" />
												<logic:iterate id="parameterValue" name="parameterValues"
												type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue"
													property="fieldValue" />
													<bean:define id="fieldDescription" name="parameterValue"
													property="fieldDescription" />
													<ihtml:option
													value="<%=String.valueOf(fieldValue).toUpperCase() %>">
													<%=String.valueOf(fieldDescription)%>
													</ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
								</div>
								<div class="ic-input ic-split-50 ic-mandatory">
								<label><common:message key="mailtacking.mra.defaults.maintainbillingline.blgbasis" scope="request" /></label>
								<ihtml:select
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BillingBasis"
								property="billingBasis"
								tabindex="49">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode"
											value="mailtracking.mra.ratingBasis">
											<bean:define id="parameterValues" name="oneTimeValue"
											property="value" />
											<logic:iterate id="parameterValue" name="parameterValues"
											type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue"
												property="fieldValue" />
												<bean:define id="fieldDescription" name="parameterValue"
												property="fieldDescription" />
												<ihtml:option
												value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												<%=String.valueOf(fieldDescription)%>
												</ihtml:option>
												</logic:present>
											</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
								</div>
								<div class="ic-input ic-split-50">
								<label><common:message key="mailtacking.mra.defaults.maintainbillingline.unit" scope="request" /></label>
								<ihtml:select	componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Unit" 
								property="unitCode"
								tabindex="49">
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode"
											value="mail.mra.gpabilling.untcod">
											<bean:define id="parameterValues" name="oneTimeValue"
											property="value" />
											<logic:iterate id="parameterValue" name="parameterValues"
											type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue"
												property="fieldValue" />
												<bean:define id="fieldDescription" name="parameterValue"
												property="fieldDescription" />
												<ihtml:option
												value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												<%=String.valueOf(fieldDescription)%>
												</ihtml:option>
												</logic:present>
											</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
								</div>
								<div class="ic-input ic-split-50 marginT10 ic_inline_chcekbox">
								<%if("Y".equals(form.getIsTaxIncludedInRateFlag())){ %>
								<input type="checkbox" name ="isTaxIncludedInRateFlag" indexId="rowCount" checked >
								<%}else{%>
								<input type="checkbox" name ="isTaxIncludedInRateFlag" indexId="rowCount" >
								<%}%>
								<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.rateincstax" scope="request" /></label>
								</div>
								<div class="ic-input ic-split-100 ic-mandatory ">
								<label><common:message key="mailtacking.mra.defaults.maintainbillingline.currency" scope="request" /></label>
								<ihtml:text property="currency"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Currency"
								maxlength="100" readonly="false" tabindex="51" /><div class="lovImg"> <img
								src="<%=request.getContextPath()%>/images/lov.png" id="currencyLov"
								height="22" width="22" alt="" /></div>
								</div>
								</div>
								
								<!--Pane Section starts-->
								<div id="container1" class="tab1-container" style="width:99%;overflow:auto;">
									<ul class="tabs marginT10">
										<li>
											<button type="button" class="tab" accesskey="g" onclick="return showPane1(event,'pane1', this)" id="tab.mailCharges">
												<b>Mail Charges</b>
											</button>
										</li>
										<li>
											<button type="button" class="tab" accesskey="g" onclick="return showPane2(event,'pane2', this)" id="tab.surCharges">
												<b>Surcharge</b>
											</button>
										</li>
									</ul>
								<div class="tab-panes">

								<!-- CONTENTS OF TAB1 STARTS -->
								<jsp:include page="/jsp/mail/mra/defaults/MaintainBillingLine_MailChgTab.jsp"/>							
								<!-- CONTENTS OF TAB1 ENDS -->

								<!-- CONTENTS OF TAB2 STARTS -->
								<jsp:include page="/jsp/mail/mra/defaults/MaintainBillingLine_SurChgTab.jsp"/>		
								<!-- CONTENTS OF TAB2 ENDS -->
								</div>
								</div>
								<div class="ic-row">
									<div class="ic-input ic-split-50 ic-mandatory ">
									<label><common:message key="mailtacking.mra.defaults.maintainbillingline.from" scope="request" /></label>
									<ibusiness:calendar
									id="blgLineValidFrom" property="blgLineValidFrom"
									componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BlgLineValidFrom"
									type="image" maxlength="11" tabindex="52" />
									</div>
									<div class="ic-input ic-split-50 ic-mandatory ">
									<label><common:message key="mailtacking.mra.defaults.maintainbillingline.to" scope="request" /></label>
									<ibusiness:calendar id="blgLineValidTo" property="blgLineValidTo" 
									componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BlgLineValidTo" type="image" 
									maxlength="11" tabindex="53" />
									</div>
								</div>	
								</fieldset>
							</div>