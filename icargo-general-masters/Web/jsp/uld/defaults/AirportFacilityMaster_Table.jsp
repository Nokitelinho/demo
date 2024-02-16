	<%@ include file="/jsp/includes/tlds.jsp" %>
	
	<business:sessionBean id="KEY_DETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="ULDAirportLocationVOs"/>
	<business:sessionBean id="facilityCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityCode"/>	 
	<business:sessionBean id="facilityTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityType"/>
	<business:sessionBean id="contentCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="Content"/>
	<bean:define id="form"
		name="AirportFacilityMasterForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm"
		toScope="page" />
		
	<logic:present name="KEY_DETAILS">
		<bean:define id="KEY_DETAILS" name="KEY_DETAILS" />
	</logic:present>	
<% String chkBoxFlag="false";%>

	<logic:present name="KEY_DETAILS">
									<bean:define id="KEY_DETAILS" name="KEY_DETAILS"/>
									<logic:iterate id="iterator" name="KEY_DETAILS" indexId="billingindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO">
										<bean:define id="_iterator" name="iterator" toScope="request"/>	
									<% String value=String.valueOf(billingindex)+"-"+"chk";
										request.setAttribute("billingindex",billingindex);
										request.setAttribute("value",value);
									%>

									  <logic:notPresent name="iterator" property="operationFlag">
										  <ihtml:hidden property="operationFlag" value="null"/>
										  <ihtml:hidden property="sequenceNumber" name="iterator" />
											<logic:notEqual name="form" property="screenName" value="screenLoad">
												<tr>
													<jsp:include page="AirportFacilityMaster_Table_Row.jsp"/>
												</tr>
											</logic:notEqual>
											<logic:equal name="form" property="screenName" value="screenLoad">
												<tr>
													<td class="iCargoTableDataTd ic-center" >
														<input type="checkbox" name="selectedRows" id="selectedRows" value="<%=billingindex%>" />
													</td>
													<td>
														<html:select styleClass="iCargoMediumComboBox" property="facility" title="Facility Type" value="<%=String.valueOf(iterator.getFacilityType())%>" disabled="true">
														<logic:present name="facilityTypeCombo">
															<logic:iterate id="StatusIterator" name="facilityTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="StatusIterator" property="fieldValue">
																	<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																	<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
																</logic:present>
															</logic:iterate>
														</logic:present>
														</html:select>
													</td>
													<td class="iCargoTableDataTd" >
													<logic:equal name="iterator" property="facilityType" value="WHS">
														<ihtml:select property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITYCODE_COMBO"
														  value="<%=String.valueOf(iterator.getFacilityCode())%>" >
															<logic:present name="facilityCodeFromSession">
															<bean:define id="facvalue" name="facilityCodeFromSession"/>
															<ihtml:options collection="facvalue" property="facilityCode" labelProperty="facilityCode"/>
															</logic:present>
														</ihtml:select>
														<ihtml:hidden property="whsFacilityCode" value="<%=(String)iterator.getFacilityCode()%>"/>
													</logic:equal>
													<logic:notEqual name="iterator" property="facilityType" value="WHS">
														<ihtml:text property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="facilityCode"  value="<%=(String)iterator.getFacilityCode()%>" maxlength="10"/>
														<ihtml:hidden property="whsFacilityCode" value="<%=(String)iterator.getFacilityCode()%>"/>
													</logic:notEqual>
													</td>
													<td class="iCargoTableDataTd" >
													<logic:present name= "iterator" property="description">
														<ihtml:text property="description" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="description" value ="<%=iterator.getDescription().toString()%>" maxlength="50" styleClass="iCargoEditableTextFieldRowColor1"/>
													</logic:present>
													</td>
													<td>
													<logic:present name="iterator" property="content">
													<logic:equal name="iterator" property="content" value="RPR">
														<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
														<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
													</logic:equal>

													<logic:notEqual name="iterator" property="content" value="RPR">
														<logic:equal name="iterator" property="content" value="AGT">
														<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
														<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
														</logic:equal>
														<logic:notEqual name="iterator" property="content" value="AGT">
														<logic:equal name="iterator" property="content" value="ULD">
														<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
														<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
														</logic:equal>
														<logic:notEqual name="iterator" property="content" value="ULD">
															<html:select styleClass="iCargoMediumComboBox" property="content" title="Content" value="<%=String.valueOf(iterator.getContent())%>">
															<logic:present name="contentCombo">
																<logic:iterate id="StatusIterator" name="contentCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="StatusIterator" property="fieldValue">
																		<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																		<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
																	</logic:present>
																</logic:iterate>
															</logic:present>
															</html:select>
															<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
														</logic:notEqual>
														</logic:notEqual>
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="iterator" property="content">
													 
														
														<ihtml:hidden property="content" value=""/>
														<ihtml:hidden property="contentVal" value=""/>
														</logic:notPresent>
													</td>
													<td class="iCargoTableDataTd" >
													<logic:present name="iterator" property="defaultFlag">
													 <% chkBoxFlag="N"; %>
														<logic:equal name="iterator" property="defaultFlag" value="Y">
															<input name="defaultFlag" type="checkbox" id="<%=String.valueOf(value)%>" checked="checked"  value="Y" onclick="singleDefaultSelect(this)"/>
															<% chkBoxFlag="Y"; %>
														</logic:equal>
														<logic:notEqual name="iterator" property="defaultFlag" value="Y">
															  <input name="defaultFlag" id="<%=String.valueOf(value)%>" type="checkbox" value="N" onclick="singleDefaultSelect(this)"/>
														</logic:notEqual>
													</logic:present>
													<logic:notPresent name="iterator" property="defaultFlag">
															  <input name="defaultFlag" type="checkbox"id="<%=String.valueOf(value)%>" value="N" onclick="singleDefaultSelect(this)"/>
													</logic:notPresent>
													</td>
												</tr>
											</logic:equal>
								      </logic:notPresent>
									  <logic:present name="iterator" property="operationFlag">
											<logic:notEqual name="iterator" property="operationFlag" value="D">
													<bean:define id="operationFlag" name="iterator" property="operationFlag" />
													<ihtml:hidden property="operationFlag" value="<%=(String)operationFlag%>"/>
													<ihtml:hidden property="sequenceNumber" name="iterator" />
													<tr class="iCargoTableDataRow2">
														<td>
															<input type="checkbox" name="selectedRows" value="<%=billingindex%>" />
														</td>
														<td>
															<html:select styleClass="iCargoMediumComboBox" property="facility" title="Facility Type" value="<%=String.valueOf(iterator.getFacilityType())%>" disabled="true">
															<logic:present name="facilityTypeCombo">
																<logic:iterate id="StatusIterator" name="facilityTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="StatusIterator" property="fieldValue">
																		<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																		<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
																	</logic:present>
																</logic:iterate>
															</logic:present>
															</html:select>
														</td>
														<td >
														<logic:equal name="iterator" property="facilityType" value="WHS">
															<ihtml:select property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITYCODE_COMBO"
															  value="<%=String.valueOf(iterator.getFacilityCode())%>" >
																<logic:present name="facilityCodeFromSession">
																	<bean:define id="facvalue" name="facilityCodeFromSession"/>
																	<ihtml:options collection="facvalue" property="facilityCode" labelProperty="facilityCode"/>
																</logic:present>
															</ihtml:select>
														</logic:equal>
														<logic:notEqual name="iterator" property="facilityType" value="WHS">
															<ihtml:text property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="facilityCode"  value="<%=(String)iterator.getFacilityCode()%>" maxlength="10"/>
															<ihtml:hidden property="whsFacilityCode" value="<%=(String)iterator.getFacilityCode()%>"/>
														</logic:notEqual>
														</td>
														<td >
															<ihtml:text property="description" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="description" value ="<%=iterator.getDescription().toString()%>" maxlength="50"/>
														</td>
														<td>
														<logic:present name="iterator" property="content">
															<logic:equal name="iterator" property="content" value="RPR">
																<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
																<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
															</logic:equal>
														<logic:notEqual name="iterator" property="content" value="RPR">
															<logic:equal name="iterator" property="content" value="AGT">
																<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
																<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
															</logic:equal>
															<logic:notEqual name="iterator" property="content" value="AGT">
																<logic:equal name="iterator" property="content" value="ULD">
																	<ihtml:hidden property="content" value="<%=String.valueOf(iterator.getContent())%>"/>
																	<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
																</logic:equal>
																<logic:notEqual name="iterator" property="content" value="ULD">
																	<html:select styleClass="iCargoMediumComboBox" property="content" title="Content" value="<%=String.valueOf(iterator.getContent())%>">
																	<logic:present name="contentCombo">
																		<logic:iterate id="StatusIterator" name="contentCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																			<logic:present name="StatusIterator" property="fieldValue">
																				<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																				<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
																			</logic:present>
																		</logic:iterate>
																	</logic:present>
																	</html:select>
																	<ihtml:hidden property="contentVal" value="<%=(String)iterator.getContent()%>"/>
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="iterator" property="content">
															<html:select styleClass="iCargoMediumComboBox" property="content" title="Content">
															<logic:present name="contentCombo">
																<logic:iterate id="StatusIterator" name="contentCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="StatusIterator" property="fieldValue">
																		<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																		<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
																	</logic:present>
																</logic:iterate>
															</logic:present>
															</html:select>
															<ihtml:hidden property="contentVal" value=""/>
															</logic:notPresent>
														</td>
														<td>
															<logic:present name="iterator" property="defaultFlag">
																<% chkBoxFlag="N"; %>
																<logic:equal name="iterator" property="defaultFlag" value="Y">
																	<input name="defaultFlag" type="checkbox"  checked="checked" value="Y" id="<%=String.valueOf(value)%>" onclick="singleDefaultSelect(this)"/>
																	<% chkBoxFlag="Y"; %>
																</logic:equal>
																<logic:notEqual name="iterator" property="defaultFlag" value="Y">
																	  <input name="defaultFlag" type="checkbox" value="N" id="<%=String.valueOf(value)%>" onclick="singleDefaultSelect(this)"/>
																</logic:notEqual>
															</logic:present>
															<logic:notPresent name="iterator" property="defaultFlag">
																  <input name="defaultFlag" type="checkbox" value="N"id="<%=String.valueOf(value)%>" onclick="singleDefaultSelect(this)"/>
															</logic:notPresent>
														</td>
													</tr>
											</logic:notEqual>
											<logic:equal name="iterator" property="operationFlag" value="D">
													<ihtml:hidden property="operationFlag" value="D"/>
													<ihtml:hidden property="sequenceNumber" name="iterator" />
													<ihtml:hidden property="facility" value="<%=(String)iterator.getFacilityType()%>"/>
													<ihtml:hidden property="facilityCode" value="<%=(String)iterator.getFacilityCode()%>"/>
													<ihtml:hidden property="whsFacilityCode" name="whsFacilityCode" value="<%=(String)iterator.getFacilityCode()%>"/>
													<ihtml:hidden property="description" value="<%=(String)iterator.getDescription()%>"/>
													<logic:present name="iterator" property="content">
														<ihtml:hidden property="content" value="<%=(String)iterator.getContent()%>"/>
														<ihtml:hidden property="contentVal" name="contentVal" value="<%=(String)iterator.getContent()%>"/>
													</logic:present>
													<logic:notPresent name="iterator" property="content">
														<ihtml:hidden property="content" value="" />
														<ihtml:hidden property="contentVal" value="" />
													</logic:notPresent>
													<logic:present name="iterator" property="defaultFlag">
														<ihtml:hidden property="defaultFlag" value="<%=(String)iterator.getDefaultFlag()%>" />
													</logic:present>
													<logic:notPresent name="iterator" property="defaultFlag">
														<ihtml:hidden property="defaultFlag" value=""/>
													</logic:notPresent>
											</logic:equal>
									   </logic:present>
									</logic:iterate>
							</logic:present>