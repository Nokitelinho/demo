<%@ include file="/jsp/includes/tlds.jsp" %>
<bean:define id="form"
				name="CarditEnquiryForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm"
				toScope="page"
				scope="request"/>
<business:sessionBean id="oneTimeValues"
						  moduleName="mail.operations"
						  screenID="mailtracking.defaults.carditenquiry"
						  method="get"
						  attribute="oneTimeVOs" />
<div class="ic-row ic-border">
								<div class="ic-input ic-split-15 ">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.originoe" /></label>
									<ihtml:text property="ooe" componentID="CMP_MailTracking_Defaults_CarditEnquiry_originOE" maxlength="6"/>
									<div class="lovImg"><img id="originOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="org"/></div>
								</div>
								<div class="ic-input ic-split-15 ">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.destinationoe" /></label>
									<ihtml:text property="doe" componentID="CMP_MailTracking_Defaults_CarditEnquiry_destnOE" maxlength="6"/>
									<div class="lovImg"><img id="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/></div>
								</div>
								<div class="ic-input ic-split-18 ">
									<label>	<common:message key="mailtracking.defaults.carditenquiry.lbl.cat" /></label>
									<ihtml:select property="mailCategoryCode" componentID="CMP_MailTracking_Defaults_CarditEnquiry_category" style="width:150px">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>">
																<%=(String)fieldDescription%>
															</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
									   </logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-18 ">
									<label>
									<common:message key="mailtracking.defaults.carditenquiry.lbl.mailClass" />
									<label>
									<ihtml:select property="mailSubclass" componentID="CMP_MailTracking_Defaults_CarditEnquiry_mailClass" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									  <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailclass">
											<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									   </logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-10 ">
								<label>
									<common:message key="mailtracking.defaults.carditenquiry.lbl.year" />
									<label>
									<ihtml:text property="year" componentID="CMP_MailTracking_Defaults_CarditEnquiry_Year" maxlength="1"/>
								</div>
								<div class="ic-input ic-split-10 ">
								<label>
									<common:message key="mailtracking.defaults.carditenquiry.lbl.dsn" />
									<label>
									<ihtml:text property="despatchSerialNumber" componentID="CMP_MailTracking_Defaults_CarditEnquiry_Dsn" maxlength="4"/>
								</div>
								<div class="ic-input ic-split-10 ">
								<label>
									<common:message key="mailtracking.defaults.carditenquiry.lbl.rsn" />
									<label>
									<ihtml:text property="receptacleSerialNumber" componentID="CMP_MailTracking_Defaults_CarditEnquiry_Rsn" maxlength="3"/>
								</div>	
							</div>