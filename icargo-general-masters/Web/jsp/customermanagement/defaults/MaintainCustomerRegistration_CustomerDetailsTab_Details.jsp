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
	<div id="pane1" class="content" >
				<div class="ic-row">
					<div class="ic-input ic-split-30" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.defNotifyMode" scope="request"/>
						</label>
						<logic:present name="customerVO" property="defaultNotifyMode">
				            <bean:define id="notifyMode" name="customerVO" property="defaultNotifyMode" type="java.lang.String"/>
							<ihtml:select property="defaultNotifyMode" 
							value="<%=(String)notifyMode%>" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_DEFNOTIFYMODE">
							    <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="defaultNotifyModes">
									<bean:define id="modes" name="defaultNotifyModes"/>
									<ihtml:options collection="modes" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="customerVO" property="defaultNotifyMode">
							<ihtml:select property="defaultNotifyMode" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_DEFNOTIFYMODE">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="defaultNotifyModes">
									<bean:define id="modes" name="defaultNotifyModes"/>
									<ihtml:options collection="modes" property="fieldValue" labelProperty="fieldDescription"/>
								 </logic:present>
							</ihtml:select>
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-30 multi-select-wrap" >
						<label>
							<common:message key="customermanagement.defaults.customerregistration.lbl.restrictedfops" scope="request"/>
						</label>
						<bean:define id="oneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
						<ihtml:select multiSelect="true" 
							multiSelectNoneSelectedText="Select" 
							multiSelectMinWidth="200" 
							multiple="multiple" property="restrictedFOPs" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_RESTRICTEDFOP" >
							<logic:present name="OneTimeValues" >
								<logic:iterate id="oneTimeValue" name="OneTimeValues">
									<bean:define id="paramCode" name="oneTimeValue" property="key"/>
									<logic:equal name="paramCode" value="shared.defaults.paymenttype">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="paramValue" 
										name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="paramValue" property="fieldValue">
												<bean:define id="fieldValue" name="paramValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="paramValue" property="fieldDescription"/>
												<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-40" >
						<div class="ic-button-container">
							
								
									<a href="#" id="addcontact" class="iCargoLink">
										<common:message key="customermanagement.defaults.customerregistration.lbl.add" scope="request"/>
									</a>
									<a name="hyphen"> | </a>
								
									<a href="#" id="deletecontact" class="iCargoLink">
										<common:message key="customermanagement.defaults.customerregistration.lbl.delete" scope="request"/>
									</a> 
								
													
						</div>
					</div>
				</div>				
				<div class="ic-row marginT5 marginB5">
				<div class="tableContainer" id="div1"  style="height:180px;">
					<table id="CustomerDetailsTable" class="fixed-header-table" style="width:115%;">
						<thead>
							<tr>
							    <td class="ic-center" width="3%"><input type="checkbox" name="masterRowId" 
								onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.selectedContactDetails)"/>
								</td>
								
								<td width="8%">
                                    <common:message key="customermanagement.defaults.customerregistration.lbl.code" scope="request"/>
                                </td>
								<td width="8%">
                                    <common:message key="customermanagement.defaults.customerregistration.lbl.name" scope="request"/>
                                </td>
								<td width="8%">
                                    <common:message key="customermanagement.defaults.customerregistration.lbl.desgn" scope="request"/>
                                </td>
							    <td width="12%">
                                    <common:message key="customermanagement.defaults.customerregistration.lbl.type" scope="request"/>
                                </td>
								<td width="7%">
                                    <common:message key="customermanagement.defaults.customerregistration.lbl.notificationpreferences" scope="request"/>
                                </td>
							    <td width="8%">
							        <common:message key="customermanagement.defaults.customerregistration.lbl.tel" scope="request"/>
                                </td>
                                <td width="8%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.mob" scope="request"/>
                                </td>
								<td width="8%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.fax" scope="request"/>
                                </td>
                                <td width="8%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.sita" scope="request"/>
                                </td>
								 <td width="7%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.additionalcontact" scope="request"/>
                                </td>
                                <td width="8%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.email" scope="request"/>
                                </td>
							    <td width="5%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.active" scope="request"/>
                                </td>
                                <td width="6%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.primary" scope="request"/>
								</td>
                                <td width="8%">
									<common:message key="customermanagement.defaults.customerregistration.lbl.remark" scope="request"/>
                                </td>
							</tr>
						</thead>
						
						<tbody id="custRegContactTableBody">
						    <logic:present name="customerVO" property="customerContactVOs" >
							    <bean:define id="customerContactVOs" name="customerVO"  property="customerContactVOs"/>
								<logic:iterate id="customerContactDetailVO" name="customerContactVOs" 
								indexId="index" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO">

										<logic:present name="customerContactDetailVO" property="operationFlag">
											<bean:define id="opFlag" name="customerContactDetailVO" property="operationFlag"/>
										</logic:present>
										<logic:notPresent name="customerContactDetailVO" property="operationFlag">
											<bean:define id="opFlag"  value="NA"/>
										</logic:notPresent>
										<logic:notMatch name="opFlag" value="D">
											<logic:match name="opFlag" value="I">
												<ihtml:hidden property="hiddenOpFlagForCustomer" value="I"/>
											</logic:match>
											<logic:notMatch name="opFlag" value="I">
												<ihtml:hidden property="hiddenOpFlagForCustomer" value="U"/>
											</logic:notMatch>
							   <tr>

										<td class="iCargoTableDataTd ic-center">
										<ihtml:checkbox property="selectedContactDetails"  onclick="toggleTableHeaderCheckbox('selectedContactDetails',this.form.masterRowId)" value="<%=String.valueOf(index)%>" />
										</td>
										 
											 <td   class="iCargoTableDataTd">
											 <logic:present name="customerContactDetailVO" property="operationFlag">
											   <bean:define id="opFlag" name="customerContactDetailVO" property="operationFlag"/>
											   <ihtml:hidden property="operationContactFlag" value="<%=(String)opFlag%>"/>
											   </logic:present>


											   <logic:notPresent name="customerContactDetailVO" property="operationFlag">
											   		<bean:define id="opFlag"  value="NA"/>
											   		<ihtml:hidden property="operationContactFlag" value="<%=(String)opFlag%>"/>
							   						</logic:notPresent>

											 <logic:present name="customerContactDetailVO" property="contactCustomerCode">
											 <ihtml:text property="contactCode" maxlength="50" 
											 value="<%=customerContactDetailVO.getContactCustomerCode()%>" style="width:80px"  />
											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="contactCustomerCode">
											 <ihtml:text property="contactCode" maxlength="50" 
											 value="" style="width:80px"  />
											 </logic:notPresent>
											 </td>
											 <td   class="iCargoTableDataTd">
											 <logic:present name="customerContactDetailVO" property="customerName">
											 <ihtml:text property="contactName" maxlength="75"  
											 value="<%=customerContactDetailVO.getCustomerName()%>"  style="width:80px"  />
											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="customerName">
											 	<ihtml:text property="contactName" maxlength="75"  
												value=""  style="width:80px"  />
											 </logic:notPresent>

											 </td>
											 <td   class="iCargoTableDataTd">
											  <logic:present name="customerContactDetailVO" property="customerDesignation">
											 <ihtml:text property="contactDesignation" 
											 maxlength="25" styleClass="iCargoEditableTextFieldRowColor1"  
											 value="<%=customerContactDetailVO.getCustomerDesignation()%>" style="width:80px"  />
											 </logic:present>
											  <logic:notPresent name="customerContactDetailVO" property="customerDesignation">
											 	<ihtml:text property="contactDesignation" 
												maxlength="25" styleClass="iCargoEditableTextFieldRowColor1"  value="" style="width:80px"  />
											 	</logic:notPresent>

											 </td>
											 <!-- Added by A-5220 for ICRD-55852 -->
											 <td class="iCargoTableDataTd">
											 
											 <logic:notPresent name="customerContactDetailVO" property="contactType">
											 <ihtml:select property="contactTypes" indexId="index" 
											 componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TYPE" value="">
												<ihtml:option value="" ><common:message key="combo.select"/></ihtml:option>
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:equal 
															name="parameterCode" value="customermanagement.defaults.customercontact.contacttype" >
															
															<logic:iterate id="parameterValue" name="parameterValues">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" 
																		name="parameterValue" property="fieldDescription"/>
																		<ihtml:option value="<%=(String)fieldValue%>">
																		<%=fieldDescription%></ihtml:option>
																	</logic:present>
													</logic:iterate>
													</logic:equal>
													</logic:iterate>
													
												</logic:present>
												</ihtml:select>
											</logic:notPresent>
											<logic:present name="customerContactDetailVO" property="contactType">
											<ihtml:select property="contactTypes" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TYPE" indexId="index"
											value="<%=customerContactDetailVO.getContactType()%>">
											<ihtml:option value="" ><common:message key="combo.select"/></ihtml:option>
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:equal 
															name="parameterCode" value="customermanagement.defaults.customercontact.contacttype" >
															<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
																	<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" 
																	name="parameterValue" property="fieldDescription"/>
																	<logic:equal name="customerContactDetailVO" property="contactType" 
																	value="<%=(String)fieldValue%>" >
																		<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%>																			</ihtml:option>
																	</logic:equal>
																	</logic:present>
															</logic:iterate>
															<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
																	<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" 
																	name="parameterValue" property="fieldDescription"/>
																	<logic:notEqual name="customerContactDetailVO" property="contactType" 
																	value="<%=(String)fieldValue%>" >
																		<ihtml:option value="<%=(String)fieldValue%>">
																		<%=fieldDescription%></ihtml:option>
																	</logic:notEqual>
																	</logic:present>
															</logic:iterate>
													</logic:equal >
													</logic:iterate>
												</logic:present>
												</ihtml:select>
											</logic:present>
											
											 </td>
											  <td   class="iCargoTableDataTd ic-center">
											    <div class="lovImgTbl valignT">
												<img height="16" name="notificationPreferences" id="notificationPreferences<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" onclick="displayNotificationPreferences(this)"/>							 
											    </div>
											</td>
											 <td   class="iCargoTableDataTd">
											 <logic:present name="customerContactDetailVO" property="telephone">
											 <ihtml:text property="contactTelephone" maxlength="25" indexId="nIndex" 
											 styleId="contacttelephone" styleClass="iCargoEditableTextFieldRowColor1" 
											 value="<%=customerContactDetailVO.getTelephone()%>" style="width:80px"  />
											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="telephone">
											<ihtml:text property="contactTelephone" 
											maxlength="25" indexId="nIndex" styleId="contacttelephone" 
											styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:80px"  />
											 </logic:notPresent>

											</td>
											 <td   class="iCargoTableDataTd">
											 <logic:present name="customerContactDetailVO" property="mobile">
											 <ihtml:text property="contactMobile"  maxlength="25" 
											 styleClass="iCargoEditableTextFieldRowColor1" 
											 value="<%=customerContactDetailVO.getMobile()%>" style="width:80px"  />
											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="mobile">
											 	<ihtml:text property="contactMobile"  maxlength="25" styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:80px"  />
											 </logic:notPresent>


											 </td>
											 <td   class="iCargoTableDataTd">
											  <logic:present name="customerContactDetailVO" property="fax">
											 <ihtml:text property="contactFax"  maxlength="25" styleClass="iCargoEditableTextFieldRowColor1" value="<%=customerContactDetailVO.getFax()%>" style="width:80px"  />
											 </logic:present>
											  <logic:notPresent name="customerContactDetailVO" property="fax">
												 <ihtml:text property="contactFax"  maxlength="25" styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:80px"  />
												 </logic:notPresent>

											 </td>
											<td   class="iCargoTableDataTd">
											<logic:present name="customerContactDetailVO" property="siteAddress">
											<ihtml:text property="contactSita"  maxlength="30" styleClass="iCargoEditableTextFieldRowColor1"  value="<%=customerContactDetailVO.getSiteAddress()%>" style="width:80px"     />

											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="siteAddress">
												<ihtml:text property="contactSita"  maxlength="30" styleClass="iCargoEditableTextFieldRowColor1"  value="" style="width:80px"     />

												 </logic:notPresent>

											</td>
											<td   class="iCargoTableDataTd ic-center">
											    <div class="lovImgTbl valignT">
												<img height="16" name="additionalContacts" id="additionalContacts<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" onclick="displayAdditionalContacts(this)"/>
											    </div>
											
											</td>
											<td   class="iCargoTableDataTd">

											<logic:present name="customerContactDetailVO" property="emailAddress">
											<ihtml:text property="contactEmail"  maxlength="75" styleClass="iCargoEditableTextFieldRowColor1" value="<%=customerContactDetailVO.getEmailAddress()%>" style="width:80px;text-transform : lowercase"  
										     title="<%=customerContactDetailVO.getEmailAddress()%>"/>
											</logic:present>
											<logic:notPresent name="customerContactDetailVO" property="emailAddress">
											<ihtml:text property="contactEmail"  maxlength="75" styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:80px;text-transform : lowercase"  />
											</logic:notPresent>

											</td>
											<td height="19"  class="iCargoTableTd ic-center">
											   <div>
											   <logic:present name="customerContactDetailVO" property="activeStatus">
											   		<bean:define id="contactstatus" name="customerContactDetailVO" property="activeStatus"/>
											   			<logic:equal name="contactstatus" value="A">

											   				<input type="checkbox" name="active" checked value="<%=String.valueOf(index)%>" />
											   			</logic:equal>
											   			<logic:notEqual name="contactstatus" value="A">
														<input type="checkbox" name="active" value="<%=String.valueOf(index)%>"  />
											   			</logic:notEqual>
											   			</logic:present>
											   			<logic:notPresent name="customerContactDetailVO" property="activeStatus">
											   			<input type="checkbox" name="active" value="<%=String.valueOf(index)%>"  />
											   			</logic:notPresent>
											    </div>


											   </td>


											   <td class="iCargoTableTd ic-center">
											   <div>
											   <logic:present name="customerContactDetailVO" property="primaryUserFlag">
													<bean:define id="contactPrimary" name="customerContactDetailVO" property="primaryUserFlag"/>
														<logic:equal name="contactPrimary" value="Y">

															<input type="checkbox" onclick="singleSelect(this)" id="primaryContacts<%=index%>"
																name="primaryContacts" checked value="<%=String.valueOf(index)%>" />
														</logic:equal>
														<logic:notEqual name="contactPrimary" value="Y">
														<input type="checkbox" name="primaryContacts" onclick="singleSelect(this)"  id="primaryContacts<%=index%>"
															value="<%=String.valueOf(index)%>"  />
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="customerContactDetailVO" property="primaryUserFlag">
														<input type="checkbox" name="primaryContacts" onclick="singleSelect(this)"  id="primaryContacts<%=index%>"
															value="<%=String.valueOf(index)%>"  />
														</logic:notPresent>
												</div>


											   </td>
											 <td   class="iCargoTableDataTd">
											 <logic:present name="customerContactDetailVO" property="remarks">
											 <ihtml:text property="contactRemarks" maxlength="100"  value="<%=customerContactDetailVO.getRemarks()%>"  style="width:80px"  />
											 </logic:present>
											 <logic:notPresent name="customerContactDetailVO" property="remarks">
											 	<ihtml:text property="contactRemarks" 
												maxlength="100" value=""  style="width:80px"  />
											 </logic:notPresent>

											 </td>

								 </tr>

								 </logic:notMatch>

								 <logic:match name="opFlag" value="D" >
								 	<ihtml:hidden property="hiddenOpFlagForCustomer" value="D"/>


								<logic:present name="customerContactDetailVO" property="operationFlag">
								   <bean:define id="opFlag" name="customerContactDetailVO" property="operationFlag"/>
								   <ihtml:hidden property="operationContactFlag" value="<%=(String)opFlag%>"/>
							   </logic:present>

								<logic:present name="customerContactDetailVO" property="contactCustomerCode">
									 <bean:define id="contCode"  name="customerContactDetailVO" property="contactCustomerCode"/>
									 <ihtml:hidden property="contactCode" value="<%=(String)contCode%>"/>
									 </logic:present>
								 <logic:present name="customerContactDetailVO" property="customerName">
										 <bean:define id="contName"  name="customerContactDetailVO" property="customerName"/>
										 <ihtml:hidden property="contactName" value="<%=(String)contName%>"/>
								 </logic:present>
								 <logic:present name="customerContactDetailVO" property="customerDesignation">
									 <bean:define id="contDesg"  name="customerContactDetailVO" property="customerDesignation"/>
									 <ihtml:hidden property="contactDesignation" value="<%=(String)contDesg%>"/>
								 </logic:present>
								 <logic:present name="customerContactDetailVO" property="telephone">
										 <bean:define id="tel"  name="customerContactDetailVO" property="telephone"/>
										 <ihtml:hidden property="contactTelephone" value="<%=(String)tel%>"/>
							 	</logic:present>

							 	<logic:present name="customerContactDetailVO" property="mobile">
										 <bean:define id="mob"  name="customerContactDetailVO" property="mobile"/>
										 <ihtml:hidden property="contactMobile" value="<%=(String)mob%>"/>
							 	</logic:present>

							 	<logic:present name="customerContactDetailVO" property="fax">
										 <bean:define id="fax"  name="customerContactDetailVO" property="fax"/>
										 <ihtml:hidden property="contactFax" value="<%=(String)fax%>"/>
								</logic:present>

								<logic:present name="customerContactDetailVO" property="siteAddress">
									 <bean:define id="sitaAdr"  name="customerContactDetailVO" property="siteAddress"/>
									 <ihtml:hidden property="contactSita" value="<%=(String)sitaAdr%>"/>
							</logic:present>


								<logic:present name="customerContactDetailVO" property="emailAddress">
									 <bean:define id="emailAdr"  name="customerContactDetailVO" property="emailAddress"/>
									 <ihtml:hidden property="contactEmail" value="<%=(String)emailAdr%>"/>
							</logic:present>

							<logic:present name="customerContactDetailVO" property="remarks">
								 <bean:define id="rmks"  name="customerContactDetailVO" property="remarks"/>
								 <ihtml:hidden property="contactRemarks" value="<%=(String)rmks%>"/>
							</logic:present>
						</logic:match>


								   </logic:iterate>


                                </logic:present>
                <bean:define id="templateRowCount" value=""/>
				<tr template="true" id="custRegContactTemplateRow" style="display:none">

					<td class="iCargoTableDataTd ic-center">
					<ihtml:checkbox property="selectedContactDetails"  onclick="" value="" />
						<ihtml:hidden property="hiddenOpFlagForCustomer" value="NOOP"/>
					   </td>
						
					<ihtml:hidden property="operationContactFlag" value=""/>
				

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactCode" maxlength="50" 
							value="" style="width:80px"  />
						  </td>

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactName" maxlength="75"   
							value=""  style="width:80px"  />
						 </td>

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactDesignation" maxlength="25" styleClass="iCargoEditableTextFieldRowColor1"  
							value="" style="width:80px"  />
						 </td>
						<!-- Added by A-5220 for ICRD-55852 -->
						 <td class="iCargoTableDataTd">
						  <ihtml:select property="contactTypes" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TYPE" indexId="index">
						  <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						 <logic:present name="OneTimeValues">
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.contacttype" >
											<logic:iterate id="parameterValue" name="parameterValues">
													<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%></ihtml:option>
													</logic:present>
									</logic:iterate>
									</logic:equal >
									</logic:iterate>
								</logic:present>	
						 </ihtml:select>
						 </td>
						 <td   class="iCargoTableDataTd ic-center">
									 <div class="lovImgTbl valignT">
									<img height="16" name="notificationPreferences" id="notificationPreferences<%=templateRowCount%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" onclick="displayNotificationPreferences(this)"/>			 
									</div>	 
										 </td>
						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactTelephone" maxlength="25" indexId="nIndex" 
							styleId="contacttelephone" styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:80px"  />
						</td>

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactMobile"  maxlength="25" styleClass="iCargoEditableTextFieldRowColor1" 
							value="" style="width:80px"  />
						 </td>

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactFax"  maxlength="25" styleClass="iCargoEditableTextFieldRowColor1" 
							value="" style="width:80px"  />
						 </td>

						<td   class="iCargoTableDataTd">
						<ihtml:text property="contactSita"  maxlength="30" styleClass="iCargoEditableTextFieldRowColor1"  
							value="" style="width:80px"     />
						</td>
						<td   class="iCargoTableDataTd ic-center">
									<div class="lovImgTbl valignT">	 
									<img height="16" name="additionalContacts" id="additionalContacts<%=templateRowCount%>"  src="<%=request.getContextPath()%>/images/lov.png" width="16" onclick="displayAdditionalContacts(this)"/>	 
									</div> 
										 </td>
						<td   class="iCargoTableDataTd">
						<ihtml:text property="contactEmail"  maxlength="75" styleClass="iCargoEditableTextFieldRowColor1" 
							value="" style="width:80px;text-transform : lowercase"     />
						</td>


						<td height="19"  class="iCargoTableTd ic-center">
						   <div>
						<input type="checkbox" name="active" checked value="" />
						</div>
					   </td>


						<td class="iCargoTableTd ic-center">
						   <div>
							<input type="checkbox" onclick="singleSelect(this)" id="primaryContacts<%=templateRowCount%>" value=""  name="primaryContacts" />    
							</div>
					    </td>

						 <td   class="iCargoTableDataTd">
						 <ihtml:text property="contactRemarks" maxlength="100"   
							value=""  style="width:80px"  />
						 </td>

				</tr>
							</tbody>
                              </table>
			            </div>
					</div>						
			</div>		
			
				
	 <div id="pane2" class="content" >
				<jsp:include page="CustomerDetailsTab_Details_AgentDetails.jsp"/>
					
						
							
							
							
						
					
				
				
								
			
			












										




















										
										

										
										








										
										
										




										




















							



							


							





				</div>
				