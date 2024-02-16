<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  Consigment_MailDetails_Template.jsp
* Date				 : 18-May-2017
* Author(s)			 : A-6850

*************************************************************************/
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeMailClass" />
<bean:define id="ConsignmentForm" name="ConsignmentForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm" toScope="page" scope="request"/>
<tr template="true" id="mailTemplateRow" style="display:none">
												<ihtml:hidden property="mailOpFlag" value="NOOP" />
												<td class="ic-center">
													<input type="checkbox" name="selectMail" >
												</td>
												<td>
													<ihtml:text property="mailbagId" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGMENT_MAILBAGID" value="" maxlength="29" style="width:210px" /> <!-- modified. A-8164 for ICRD 257671-->
												</td>												
												<td>
													<ihtml:text property="originOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" value="" maxlength="6" style="width:50px" />
													<!-- Modified by A-7938 for ICRD-243958-->
													<div class="lovImgTbl valignT">
													<img name="OOELov" id="OOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
													</div>
												</td>
												<td>
													<ihtml:text property="destinationOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" value="" maxlength="6" style="width:50px" />
													<!-- Modified by A-7938 for ICRD-243958-->
													<div class="lovImgTbl valignT">
													<img name="DOELov" id="DOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
													</div>
												</td>
												<td>
													<ihtml:select property="category" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CAT" value="" style="width:35px" >
														<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
														<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
															<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
															<html:option value="<%=(String)fieldValue %>">
																<bean:write name="oneTimeCatVO" property="fieldValue"/>
															</html:option>
														</logic:iterate>
													</ihtml:select>
												</td>
												<td>
													<ihtml:select property="mailClass" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLASS" value="" style="width:35px">
														<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
														<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
															<bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
															<html:option value="<%=(String)fieldValue %>">
																<bean:write name="oneTimeMailClassVO" property="fieldValue"/>
															</html:option>
														</logic:iterate>
													</ihtml:select>
												</td>
												<td>
													<ihtml:text property="subClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" maxlength="2" style="width:35px"/>
													<!-- Modified by A-7938 for ICRD-243958-->
													<div class="lovImgTbl valignT">
													<img name="SCLov" id="SCLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
													</div>
												</td>
												<td>
												    	<ihtml:text property="year" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" value="" maxlength="1" style="width:20px;text-align:right"/>
												</td>
												<td>
												    	<ihtml:text property="dsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" value="" maxlength="4"  style = "text-align:right"/>
												</td>
												<td>
												   	<ihtml:text property="rsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSN" value="" maxlength="3"  style = "text-align:right"/>
												</td>
												<td>
												        <ihtml:text property="numBags" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_NUMBAG" value="1" maxlength="5"   style = "text-align:right"/>
												</td>
												<td>
												    <ihtml:select property="mailHI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_HNI" value="" style="width:40px" >
														<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
														<ihtml:option value="">
															<common:message key="combo.select"/>
														</ihtml:option>
														<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
															<bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
															<html:option value="<%=(String)fieldValue %>">
																<bean:write name="oneTimeHNIVO" property="fieldValue"/>
															</html:option>
														</logic:iterate>
												    </ihtml:select>
												</td>
												<td>
													<ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RI" value="" style="width:40px" >
														<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
														<ihtml:option value="">
															<common:message key="combo.select"/>
														</ihtml:option>
														<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
															<bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
															<html:option value="<%=(String)fieldValue %>">
																<bean:write name="oneTimeRIVO" property="fieldValue"/>
															</html:option>
														</logic:iterate>
												    </ihtml:select>
												</td>
												<td><bean:define id="defWeightUnit" name="ConsignmentForm" property="defWeightUnit" />

														<ibusiness:unitCombo  unitTxtName="weight" style="background :'<%=color%>';text-align:right"  label="" title="Stated Weight" unitValueStyle="iCargoTextFieldSmall" unitValue="0.0"unitListName="weightUnit"  unitTypeStyle="iCargoMediumComboBox"  componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" unitListValue="<%=(String)defWeightUnit%>"  tabindex="0" unitTypePassed="MWT"/>
												</td><!--modified by A-8353 for ICRD-274933-->
                                                     <common:xgroup>
                                                         <common:xsubgroup id="TURKISH_SPECIFIC">
												<td>
												   	<ibusiness:moneyEntry id="declaredValue" moneyproperty="declaredValue"  property="declaredValue" formatMoney="true"  componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DECLAREDVALUE" value="0" style = "text-align:right" maxlength="16" />
												</td>
												
												<td>
													<ihtml:text property="currencyCode" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_CURCOD" value=""  maxlength="3" />  
								                                         
													<img name="currencyCodeLov" id="currencyCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
												</td>
													     </common:xsubgroup> 
                                                     </common:xgroup >

												<td>
												   	<ibusiness:uld id="uldNum" uldProperty="uldNum" style="text-transform:uppercase;" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_ULDNUM" maxlength="12" uldValue=""/>
												</td>

											</tr>