<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<bean:define id="form"
	name="MaintainCustomerRegistrationForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	toScope="page" />

	
	<business:sessionBean id="oneTimeValues"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="oneTimeValues" />
			
	<business:sessionBean id="CustomerVO"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="customerVO" />
			
<div class="ic-row">
<div class="tableContainer" style="height:200px;">
			<table id="" class="fixed-header-table">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td style="width:50%" class="iCargoTableHeaderLabel ic-center">
							<common:message  key="customermanagement.defaults.customerregistration.description" scope="request"/>	
						</td>
						<td style="width:50%" class="iCargoTableHeaderLabel">
							<common:message  key="customermanagement.defaults.customerregistration.value" scope="request"/>	
						</td>
						</tr>
					</thead>
					<tbody>
					<%int i = 0; 
					int paCoIndex = 0;
					%>
					<logic:present name="oneTimeValues">
						<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:equal name="parameterCode" value="customermanagement.defaults.customerpreferences" >
											<logic:iterate id="parameterValue" name="parameterValues" indexId="nIndex">
												<logic:present name="parameterValue" property="fieldValue">
										<% i++; %>
													<bean:define id="fldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fldDescription" name="parameterValue" property="fieldDescription"/>
						<tr>
													<ihtml:hidden property="preferenceCode" value="<%=(String)fldValue%>"/>
													<%if(("PaCOUp").equals(fldValue) || ("DISPLAYRATE").equals(fldValue) ){
														paCoIndex = i;
													}
													%>
													<td style="width:16%" class="iCargoTableHeaderLabel">
															<%=(String)fldDescription%> 						
						</td>
													<logic:present name="CustomerVO" property="customerPreferences">
														<bean:define id="custPreferences" name="CustomerVO" property="customerPreferences" type="java.util.Collection"/>
													
														<%boolean flg = false;
														int index=0;%>
														<logic:iterate id="custPreference" name="custPreferences" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO">
															<logic:present name="custPreference" property="preferenceCode">
															<logic:notEqual name="custPreference" property="operationFlag" value="D">
																<bean:define id="prefCode" name="custPreference" property="preferenceCode" />
																<logic:equal name="prefCode" value="<%=(String)fldValue%>" >
																<% flg = true;%>
						<td style="width:25%" class="iCargoTableHeaderLabel">
																		<%if(("PaCOUp").equals(prefCode) || ("DISPLAYRATE").equals(fldValue) ){%>
																			<ihtml:select componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE" property="preferenceValue" styleClass="iCargoBigComboBox"  value="<%=(String)custPreference.getPreferenceValue()%>">																				
																				<html:option value="Yes">Yes</html:option>	
																				<html:option value="No">No</html:option>
																			</ihtml:select>
																		<%}
																		else if(("APLPUBRAT").equals(prefCode) || ("APLPUBRAT").equals(fldValue)){ %>
																		<!-- <ihtml:checkbox property="preferenceValue" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE_APPLYPUBLISHRATE"/> -->
																			<logic:present name="custPreference" property="preferenceValue">
																			<logic:equal name="custPreference" property="preferenceValue" value="Y">
																			<ihtml:hidden property="preferenceValue" value="Y"/>
																			<input type="checkbox" id="preferenceValueId" onChange="checkChange(this,<%=nIndex%>)" checked="checked" value="Y"/>
																			<!--<ihtml:checkbox property="preferenceValue" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE_APPLYPUBLISHRATE" value="Yes"/> -->
																			</logic:equal> 
																			<logic:notEqual name="custPreference" property="preferenceValue" value="Y">
																			<ihtml:hidden property="preferenceValue" value="N"/>
																			<input type="checkbox" id="preferenceValueId" onChange="checkChange(this,<%=nIndex%>)" value="N"/>
																		<!--<ihtml:checkbox property="preferenceValue" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE_APPLYPUBLISHRATE"/>-->
																			</logic:notEqual>
																			</logic:present> 
																		<%}
																		else if((("LSEDLVRPRF").equals(prefCode)||("LSEDLVRPRF").equals(fldValue)) || (("INDINVFLG").equals(prefCode) || ("INDINVFLG").equals(fldValue))){%>
                                                                        <logic:present name="custPreference" property="preferenceValue">
																			<logic:equal name="custPreference" property="preferenceValue" value="Y">
																			<ihtml:hidden property="preferenceValue" value="Y"/>
																			<input type="checkbox" id="preferenceValueId" onChange="checkChange(this,<%=nIndex%>)" checked="checked" value="Y"/>
																			<!--<ihtml:checkbox property="preferenceValue" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE_APPLYPUBLISHRATE" value="Yes"/> -->
																			</logic:equal> 
																			<logic:notEqual name="custPreference" property="preferenceValue" value="Y">
																			<ihtml:hidden property="preferenceValue" value="N"/>
																			<input type="checkbox" id="preferenceValueId" onChange="checkChange(this,<%=nIndex%>)" value="N"/>
																		<!--<ihtml:checkbox property="preferenceValue" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE_APPLYPUBLISHRATE"/>-->
																			</logic:notEqual>
																			</logic:present> 
																		<%}
																		else if(("DEFCRT").equals(prefCode) ||("DEFCRT").equals(fldValue)){%>
																		<ihtml:select  property="preferenceValue"   value="<%=(String)custPreference.getPreferenceValue()%>">		
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
																				<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																					<logic:iterate id="parameterValue" name="parameterValues">
																					<logic:equal name="parameterCode" value="system.defaults.certificatetype" >
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
                                                                        <%
                                                                          }
																		  
																		else if(("CUSTYP").equals(fldValue)){%>
																	
							                                           <ihtml:select  property="preferenceValue"   value="<%=(String)custPreference.getPreferenceValue()%>" style="width:240px">		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
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
														<%}		
																  
																		else{ %>
																		<% 
																		if(index==0 && custPreference.getPreferenceValue() != null && 
																		!((String)custPreference.getPreferenceValue()).isEmpty()) {index++;%>
																		<ihtml:text style="width:500px" property="preferenceValue" value="<%=(String)custPreference.getPreferenceValue()%>"
																			componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
																		<%}
																		else if(index==0) { 
																		index++;%>
																		<ihtml:text style="width:500px" property="preferenceValue" value=""  
																			componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
																		<%}
																		%>
																		<%}%>
																	</td>
																</logic:equal>
																</logic:notEqual>
															</logic:present>
														</logic:iterate>
														<%if(!flg) {%>
																<td style="width:25%" class="iCargoTableHeaderLabel">
																<%if(("PaCOUp").equals(fldValue) || ("DISPLAYRATE").equals(fldValue) ){%>
																<ihtml:select componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE" property="preferenceValue" styleClass="iCargoBigComboBox"  value="Yes">
																		<html:option  value="Yes">Yes</html:option>	
																		<html:option  value="No">No</html:option>
																	</ihtml:select>
																<%} else if(("INDINVFLG").equals(fldValue)){%>
																
																	<ihtml:hidden property="preferenceValue" value="N"/>
																			<input type="checkbox" id="preferenceValueId" onChange="checkChange(this,<%=nIndex%>)" value="N"/>
																
																<%} else if(("DEFCRT").equals(fldValue)){%>
																		<ihtml:select  property="preferenceValue" >		
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
																				<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																					<logic:iterate id="parameterValue" name="parameterValues">
																					<logic:equal name="parameterCode" value="system.defaults.certificatetype" >
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
																<%}
																else if(("CUSTYP").equals(fldValue)){%>
																	
							                                            <ihtml:select  property="preferenceValue" style="width:240px">		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
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
														<%}		
															else if(("PRODUCTS").equals(fldValue)){%>
															
															    <ihtml:text property="preferenceValue" value="" style="width:500px" 
																		componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
																<%}			
																
																else{ %>
																	<ihtml:text property="preferenceValue" value=""
																		componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
																<%}%>
																</td>
														<% } %>
													</logic:present>
													<logic:notPresent name="CustomerVO" property="customerPreferences">
													<td style="width:25%" class="iCargoTableHeaderLabel">
														<% if(i == paCoIndex){ %>
															<ihtml:select componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE" property="preferenceValue" styleClass="iCargoBigComboBox"  value="Yes">
																<html:option  value="Yes">Yes</html:option>	
																<html:option  value="No">No</html:option>
															</ihtml:select>
														<% }
														else if(("APLPUBRAT").equals(fldValue)){ %>
																<ihtml:hidden property="preferenceValue" value=""/>
																<input type="checkbox" id="preferenceValueId" value="N" style="width:20px" onChange="checkChange(this,<%=nIndex%>)"/>
														<%}	
                                                        else if(("LSEDLVRPRF").equals(fldValue) || ("LSEDLVRPRF").equals(fldValue) || ("INDINVFLG").equals(fldValue)){ %>
																<ihtml:hidden property="preferenceValue" value="N"/>
																<input type="checkbox" id="preferenceValueId" value="N" style="width:20px" onChange="checkChange(this,<%=nIndex%>)"/>
														<%}
														else if(("DEFCRT").equals(fldValue)){%>
																		<ihtml:select  property="preferenceValue" >		
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
																				<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																					<logic:iterate id="parameterValue" name="parameterValues">
																					<logic:equal name="parameterCode" value="system.defaults.certificatetype" >
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
														<%}					

															else if(("CUSTYP").equals(fldValue)){%>
																	
							                                            <ihtml:select  property="preferenceValue" style="width:240px">		
																		<logic:present name="oneTimeValues">
																			<bean:define id="OneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
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
														<%}		
                                                             else if(("PRODUCTS").equals(fldValue)){%>
															
															    <ihtml:text property="preferenceValue" value="" style="width:500px"  
																		componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
																<%}																	
														

														else{ %>
														<ihtml:text property="preferenceValue" value=""
							componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_PREFERENCEVALUE"/>
														<% } %>
						</td>
													</logic:notPresent>
					</tr>
						</logic:present>
					</logic:iterate>
							</logic:equal>
			</logic:iterate>
		</logic:present>	
					
	</tbody>
	</table>
</div>
</div>