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
	<% String chkBoxFlag="false";
		Integer billingindex = (Integer)request.getAttribute("billingindex");
		String value = (String)request.getAttribute("value");
	%>
	<bean:define id="iterator" name="_iterator" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO"/>
												<td class="iCargoTableDataTd" >
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
													<ihtml:text property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="facilityCode" value="<%=(String)iterator.getFacilityCode()%>" maxlength="10" readonly="true"/>
														<ihtml:hidden  property="selectedRowsLOV" name="selectedRowsLOV" value="<%=(String)iterator.getFacilityCode()%>" />
														<ihtml:hidden  property="selectedAirportCode" name="selectedAirportCode" value="<%=(String)iterator.getAirportCode()%>" />
														<logic:present name="facilityTypeCombo">
															<logic:iterate id="StatusIterator" name="facilityTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:equal name="StatusIterator" property="fieldValue" value="<%=iterator.getFacilityType().toString()%>">
																	<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
																	<ihtml:hidden  property="selectedRow_Type" name="selectedRow_Type" value="<%=iterator.getFacilityType().toString()%>" />
																</logic:equal>
															</logic:iterate>
														</logic:present>
												</td>
												<td class="iCargoTableDataTd" >
													<ihtml:text property="description" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="description" value ="<%=iterator.getDescription().toString()%>" maxlength="50" styleClass="iCargoEditableTextFieldRowColor1" readonly="true"/>
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
												<td class="iCargoTableDataTd" >
												<logic:present name="iterator" property="defaultFlag">
													<% chkBoxFlag="N"; %>
													<logic:equal name="iterator" property="defaultFlag" value="Y">
														<input name="defaultFlag" type="checkbox" id="<%=String.valueOf(value)%>" checked="checked"  value="Y" onclick="singleSelect(this)" disabled="true"/>
														<% chkBoxFlag="Y"; %>
													</logic:equal>
													<logic:notEqual name="iterator" property="defaultFlag" value="Y">
														  <input name="defaultFlag" id="<%=String.valueOf(value)%>" type="checkbox" value="N"  onclick="singleSelect(this)" disabled="true"/>
													</logic:notEqual>
												</logic:present>
												<logic:notPresent name="iterator" property="defaultFlag">
													  <input name="defaultFlag" type="checkbox"id="<%=String.valueOf(value)%>" value="N" onclick="singleSelect(this)"/>
												</logic:notPresent>
												</td>
												