<%@ page language="java" %>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

		<% String iCargoContentClass="iCargoContent ic-masterbg";%>
		<logic:present parameter="isPopUp">
			<!DOCTYPE html>
			<% iCargoContentClass="iCargoPopUpContent";%>
		</logic:present>		
	



 <bean:define id="form" name="ViewBillingLineForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm"
 	toScope="page" />
  <business:sessionBean id="billingLineDetailsPage"
 		moduleName="mailtracking.mra.defaults"
 		screenID="mailtracking.mra.defaults.viewbillingline"
 		method="get"
		attribute="billingLineDetails"/>

  <business:sessionBean id="KEY_ONETIMES"
 	  moduleName="mailtracking.mra.defaults"
 	  screenID="mailtracking.mra.defaults.viewbillingline"
 	  method="get" attribute="oneTimeVOs" />
	  
	  <div class="ic-input ic-split-25">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.billingmatrixid" scope="request"/>
							</label>
							<ihtml:text property="billingMatrixID"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_BlgMatrixID"
							maxlength="100"
							readonly="false"
							tabindex="1"
							/>
							<div class="lovImg">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="blgMatrixIDLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.pacode" scope="request"/>
							</label>
								<ihtml:text property="postalAdmin"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_PostalAdmin"
								maxlength="100"
								readonly="false" tabindex="2"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="postalAdminLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.airline" scope="request"/>
							</label>
								<ihtml:text property="airline"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Airline"
								maxlength="3"
								readonly="false" tabindex="3"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="airlineLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-15">
							<label class="ic-label-60">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.origin" scope="request"/>
							</label>
								<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_originlevel" property="originLevel">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="REG">Region</ihtml:option>
								</ihtml:select>
								<ihtml:text property="origin"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Origin"
								maxlength="100"
								readonly="false" tabindex="4"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="originLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-15">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.destination" scope="request"/>
							</label>
							<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_destinationlevel" property="destinationLevel">
								 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="REG">Region</ihtml:option>
								</ihtml:select>
								<ihtml:text property="destination"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Destination"
								maxlength="100"
								readonly="false" tabindex="5"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="destinationLov" height="22" width="22" alt="" /></div>
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-25">
							<label class="ic-label-60">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.uplift" scope="request"/>
							</label>
								<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_upliftlevel" property="upliftLevel">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
								</ihtml:select>
								<ihtml:text property="uplift"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_uplift"
								maxlength="100"
								readonly="false" tabindex="4"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="upliftLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.discharge" scope="request"/>
							</label>
							<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_dischargelevel" property="dischargeLevel">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
							</ihtml:select>
								<ihtml:text property="discharge"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_discharge"
								maxlength="100"
								readonly="false" tabindex="5"
								/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="dischargeLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.class" scope="request"/>
							</label>
							<ihtml:text property="billingClass"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Class"
							maxlength="100"
							readonly="false" tabindex="7"
							/>
							<div class="lovImg">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="classLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-15">
							<label class="ic-label-40">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.subclass" scope="request"/>
							</label>
							<ihtml:text property="subClass"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_SubClass"
							maxlength="100"
							readonly="false" tabindex="8"
							/><div class="lovImg">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="subClassLov" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input ic-split-15">


							<label class="ic-label-40">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.uldtype" scope="request"/>
							</label>
							<ihtml:text property="uldType"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_UldType"
							maxlength="100"
							readonly="false" tabindex="9"
							/><div class="lovImg">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="uldTypeLov" height="22" width="22" alt="" /></div>
						</div>
						</div>
					<div class="ic-row">
					<div class="ic-input ic-split-25">
							<label class="ic-label-45">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.category" scope="request"/>
							</label>
							<ihtml:text property="category"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Category"
							maxlength="100"
							readonly="false" tabindex="10"
							/><div class="lovImg">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="categoryLov" height="22" width="22" alt="" /></div>
						</div>
					
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.billingLineID" scope="request"/>
							</label>
							<ihtml:text property="billingLineID"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_RatLineID"
							readonly="false"
							maxlength="9"
							tabindex="11"
							onchange="checkZero(this)"/>
						</div>
						<div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.validfrom" scope="request"/>
							</label>
							<ibusiness:calendar id="validFrom"
							property="validFrom"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_ValidFrom"
							type="image"
							maxlength="11"
							tabindex="12"/>
						</div>
						<div class="ic-input ic-split-15">
							<label class="ic-label-35">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.validto" scope="request"/>
							</label>
							<ibusiness:calendar id="validTo"
							property="validTo"
							componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_ValidTo"
							type="image"
							maxlength="11"
							tabindex="13"/>
						</div>
						  <div class="ic-input ic-split-15">
							<label class="ic-label-10">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.status" scope="request"/>
							</label>
							<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Status" property="status" style="width:76px;height:20px" tabindex="14">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="KEY_ONETIMES">
												<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
												<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
												<logic:equal name="parameterCode" value="mra.gpabilling.ratestatus">
												<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
												</logic:present>
												</logic:iterate>
												</logic:equal>
												</logic:iterate>
											</logic:present>
							</ihtml:select>
						</div>
	  
</div>
<div class="ic-row">
	  <div class="ic-input ic-split-25">
								<label class="ic-label-10">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.unit" scope="request" />
								</label>
								<ihtml:select	componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_Unit" 
								property="unitCode"
								style="width:76px;height:20px" tabindex="15">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									
									
									
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
	  <div class="ic-input ic-split-20">
							<label class="ic-label-50">
								<common:message key="mailtacking.mra.defaults.viewbillingline.label.blgSector" scope="request"/>
							</label>
							<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_BilledSector" property="billedSector"  tabindex="6">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="KEY_ONETIMES">
									<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="mailtracking.mra.billingSector">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue" property="fieldValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
									</logic:present>
									</logic:iterate>
									</logic:equal>
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</div>
	  		

								
								