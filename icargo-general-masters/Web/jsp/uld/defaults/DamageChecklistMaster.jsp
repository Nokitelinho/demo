 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>

		
 <html:html>

 <head>
		
	
		<title><common:message key="uld.defaults.damagechecklistmaster" bundle="DamageChkMaster" scope="request" /></title>
		<common:include type="script" src="/js/uld/defaults/DamageChecklistMaster_Script.jsp" />
		<meta name="decorator" content="mainpanelrestyledui">


</head> 

	<body class="ic-center" style="width:60%;">
		
		
		<bean:define id="form"
			name="DamageChecklistMasterForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageChecklistMasterForm"
				toScope="page" />


				<business:sessionBean id="oneTimeMode"
				moduleName="uld.defaults"
				screenID="uld.defaults.damagechecklistmaster"
				method="get"
				attribute="section" />

				<business:sessionBean id="KEY_ULDDMGCHKLST"
				moduleName="uld.defaults"
				screenID="uld.defaults.damagechecklistmaster"
				method="get"
				attribute="ULDDamageChecklistVO" />

		<div class="iCargoContent" style="height:100%;overflow:auto;">

			<ihtml:form action="/uld.defaults.screenloaddamagechecklistmaster.do" >
			<input type="hidden" name="mySearchEnabled" />
			<ihtml:hidden property="statusFlag"/>
			<ihtml:hidden property="disableButtons"/>
			<ihtml:hidden property="totalPoints"/>


			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message key="uld.defaults.damagechecklistmaster.damagechecklistmaster" scope="request" />
				</span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row">
								<h4><common:message key="ULD.DEFAULTS.searchcriteria" scope="request"/></h4>
							</div>
							<div class="ic-row ">
								<div class="ic-input">
									<label>
										<common:message key="uld.defaults.damagechecklistmaster.lbl.section" scope="request" />
									</label>
									<ihtml:select componentID="CMB_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_SECTION_COMBO" property="section">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="oneTimeMode">
												<logic:iterate id="frmMode" name="oneTimeMode">
													<bean:define id="parameterType" name="frmMode" property="fieldType"/>
														<logic:equal name="parameterType" value="uld.defaults.section">
															<logic:present name="frmMode" property="fieldValue">
																<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
																<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
																<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
															</logic:present>
														</logic:equal >
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</div>
								<div class="ic-button-container paddR5">
									<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_LIST" accesskey="L">
										<common:message key="List" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_CLEAR" accesskey="C">
										<common:message key="Clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<h4><common:message  key="ULD.DEFAULTS.damageDetails" scope="request"/></h4>
					</div>
					<div class="ic-row">
						<div class="ic-button-container paddR5">
							<a href="#" class="iCargoLink" id="addEvent"><common:message bundle="DamageChkMaster" key="uld.defaults.damagechecklistmaster.link.add"/>
							</a>&nbsp;|&nbsp;

							<a href="#" class="iCargoLink" id="deleteEvent">
							<common:message bundle="DamageChkMaster" key="uld.defaults.damagechecklistmaster.link.delete"/>
							</a>
						</div>
					</div>
					
					<div class="tableContainer"  id="div1"  style="height:600px">
						<table class="fixed-header-table" id="damagechecklistmaster">
							<thead>
								<tr>
									<td width="5%"  class="iCargoTableHeader">
										<input type="checkbox" name="eventHeadCheck" value="Y" onclick="updateHeaderCheckBox(this.form,this.form.eventHeadCheck,this.form.selectedDamageList)" id=""></td>
									<td width="40%" class="iCargoTableHeader" >
										<common:message bundle="DamageChkMaster" key="uld.defaults.damagechecklistmaster.section"/><span class="iCargoMandatoryFieldIcon">*</span>

									<span></span></td>
									<td width="35%"class="iCargoTableHeader" >
										<common:message bundle="DamageChkMaster" key="uld.defaults.damagechecklistmaster.description"/><span class="iCargoMandatoryFieldIcon">*</span>
									<span></span></td>
									<td width="20%"class="iCargoTableHeader" >
										<common:message bundle="DamageChkMaster" key="uld.defaults.damagechecklistmaster.noofpoints"/><span class="iCargoMandatoryFieldIcon">*</span>
									<span></span></td>
								</tr>
							</thead>

							<tbody id="uldTableBody">
								<logic:present name="KEY_ULDDMGCHKLST">
									<bean:define id="key_ulddmgchklst" name="KEY_ULDDMGCHKLST"/>
										<logic:iterate id = "uldDamageChecklistVO" name="key_ulddmgchklst"
											type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO"
											indexId="rowCount" scope="page">

													<logic:present name="uldDamageChecklistVO" property="operationFlag">
														<bean:define id="operationFlag" name="uldDamageChecklistVO" property="operationFlag"/>
															<logic:equal name="uldDamageChecklistVO" property="operationFlag" value="D">
																<ihtml:hidden property="hiddenOperationFlag" value="D"/>

																<logic:present name="uldDamageChecklistVO" property="tableSection">
																	<bean:define id="tableSection" name="uldDamageChecklistVO" property="tableSection"/>
																		<ihtml:hidden property="tableSection"   value ="<%=tableSection.toString()%>" />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="tableSection">
																		<ihtml:hidden property="tableSection" value=""   />
																</logic:notPresent>

																<logic:present name="uldDamageChecklistVO" property="sequenceNumber">
																	<bean:define id="sequenceNumber" name="uldDamageChecklistVO" property="sequenceNumber"/>
																		<ihtml:hidden property="sequenceNumber"   value ="<%=uldDamageChecklistVO.getSequenceNumber()%>" />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="sequenceNumber">
																		<ihtml:hidden property="sequenceNumber" value="" />
																</logic:notPresent>


																<logic:present name="uldDamageChecklistVO" property="description">
																	<bean:define id="description" name="uldDamageChecklistVO" property="description"/>
																		<ihtml:hidden property="description"   value ="<%=description.toString()%>"  />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="description">
																		<ihtml:hidden property="description" value=""   />
																</logic:notPresent>


																<logic:present name="uldDamageChecklistVO" property="noOfPoints">
																	<bean:define id="noOfPoints" name="uldDamageChecklistVO" property="noOfPoints"/>
																		<ihtml:hidden property="noOfPoints"   value ="<%=noOfPoints.toString()%>" />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="noOfPoints">
																		<ihtml:hidden property="noOfPoints" value="" />
																</logic:notPresent>


															</logic:equal>

															<logic:notEqual name="uldDamageChecklistVO" property="operationFlag" value="D">

																<tr>
																<td class="ic-center">
																	<input type="checkbox" name="selectedDamageList" onclick="toggleTableHeaderCheckbox('selectedDamageList',this.form.eventHeadCheck)"  />

																</td>
																<logic:equal name="uldDamageChecklistVO" property="operationFlag" value="I">
																	<ihtml:hidden property="hiddenOperationFlag" value="I"/>

																	<td class="ic-center">
																		<ihtml:select componentID="CMB_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_TABLESECTION_COMBO" property="tableSection"
																		value="<%=uldDamageChecklistVO.getSection()%>"
																		>
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																			<logic:present name="oneTimeMode">
																				<logic:iterate id="frmMode" name="oneTimeMode">
																					<bean:define id="parameterType" name="frmMode" property="fieldType"/>

																						<logic:equal name="parameterType" value="uld.defaults.section">
																							<logic:present name="frmMode" property="fieldValue">
																								<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
																								<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
																									<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
																							</logic:present>
																						</logic:equal >
																				</logic:iterate>
																			</logic:present>
																		</ihtml:select>

																		<logic:present name="uldDamageChecklistVO" property="sequenceNumber">
																				<bean:define id="sequenceNumber" name="uldDamageChecklistVO" property="sequenceNumber"/>
																					<ihtml:hidden property="sequenceNumber"   value ="<%=uldDamageChecklistVO.getSequenceNumber()%>" />
																		</logic:present>
																		<logic:notPresent name="uldDamageChecklistVO" property="sequenceNumber">
																					<ihtml:hidden property="sequenceNumber" value="" />
																		</logic:notPresent>

																	</td>
																</logic:equal>
																<logic:notEqual name="uldDamageChecklistVO" property="operationFlag" value="I">
																	<ihtml:hidden property="hiddenOperationFlag" value="U"/>
																<ihtml:hidden property="sequenceNumber" value="uldDamageChecklistVO.getSequenceNumber()"/>
																<td class="ic-center">
																	<ihtml:select componentID="CMB_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_TABLESECTION_COMBO" property="tableSection"
																	value="<%=uldDamageChecklistVO.getSection()%>"
																	disabled="true">
																	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																		<logic:present name="oneTimeMode">
																			<logic:iterate id="frmMode" name="oneTimeMode">
																				<bean:define id="parameterType" name="frmMode" property="fieldType"/>

																					<logic:equal name="parameterType" value="uld.defaults.section">
																						<logic:present name="frmMode" property="fieldValue">
																							<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
																							<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
																								<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
																						</logic:present>
																					</logic:equal >
																			</logic:iterate>
																		</logic:present>
																	</ihtml:select>

																		<logic:present name="uldDamageChecklistVO" property="sequenceNumber">
																				<bean:define id="sequenceNumber" name="uldDamageChecklistVO" property="sequenceNumber"/>
																					<ihtml:hidden property="sequenceNumber"   value ="<%=uldDamageChecklistVO.getSequenceNumber()%>" />
																		</logic:present>
																		<logic:notPresent name="uldDamageChecklistVO" property="sequenceNumber">
																					<ihtml:hidden property="sequenceNumber" value="" />
																		</logic:notPresent>

																</td>
																</logic:notEqual>
																<td class="ic-center">
																	<logic:present name="uldDamageChecklistVO" property="description">
																		<bean:define id="description" name="uldDamageChecklistVO" property="description"/>
																			<ihtml:text property="description" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION" value ="<%=description.toString()%>"  maxlength="100" />
																	</logic:present>
																	<logic:notPresent name="uldDamageChecklistVO" property="description">
																		<ihtml:text property="description" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION" value =""  maxlength="100" />
																	</logic:notPresent>

																</td>
																<td class="ic-center">
																	<logic:present name="uldDamageChecklistVO" property="noOfPoints">
																		<bean:define id="noOfPoints" name="uldDamageChecklistVO" property="noOfPoints"/>
																			<ihtml:text property="noOfPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_NOOFPOINTS" value ="<%=noOfPoints.toString()%>"  maxlength="5"  />
																	</logic:present>
																	<logic:notPresent name="uldDamageChecklistVO" property="noOfPoints">
																		<ihtml:text property="noOfPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_NOOFPOINTS" value =""  maxlength="5"  />
																	</logic:notPresent>

																</td>
																</tr>
															</logic:notEqual>

													</logic:present>

													<logic:notPresent name="uldDamageChecklistVO" property="operationFlag">
														<tr>
															<td class="ic-center">
																<input type="checkbox" name="selectedDamageList" onclick="toggleTableHeaderCheckbox('selectedDamageList',this.form.eventHeadCheck)"/>
																<ihtml:hidden property="hiddenOperationFlag" value="U"/>
															</td>
															<td class="ic-center">
																<ihtml:select componentID="CMB_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_TABLESECTION_COMBO" property="tableSection"
																value="<%=uldDamageChecklistVO.getSection()%>"
																disabled="true">
																<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																	<logic:present name="oneTimeMode">
																		<logic:iterate id="frmMode" name="oneTimeMode">
																			<bean:define id="parameterType" name="frmMode" property="fieldType"/>

																			<logic:equal name="parameterType" value="uld.defaults.section">
																				<logic:present name="frmMode" property="fieldValue">
																					<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
																					<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
																						<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
																				</logic:present>
																			</logic:equal >
																		</logic:iterate>
																	</logic:present>
																</ihtml:select>

																<logic:present name="uldDamageChecklistVO" property="sequenceNumber">
																		<bean:define id="sequenceNumber" name="uldDamageChecklistVO" property="sequenceNumber"/>
																			<ihtml:hidden property="sequenceNumber"   value ="<%=uldDamageChecklistVO.getSequenceNumber()%>" />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="sequenceNumber">
																			<ihtml:hidden property="sequenceNumber" value="" />
																</logic:notPresent>
															</td>
															<td class="ic-center">
																<logic:present name="uldDamageChecklistVO" property="description">
																	<bean:define id="description" name="uldDamageChecklistVO" property="description"/>
																	<ihtml:text property="description" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION" value ="<%=description.toString()%>"  maxlength="100"  />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="description">
																	<ihtml:text property="description" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION" value =""  maxlength="100"  />
																</logic:notPresent>
															</td>
															<td class="ic-center">
																<logic:present name="uldDamageChecklistVO" property="noOfPoints">
																	<bean:define id="noOfPoints" name="uldDamageChecklistVO" property="noOfPoints"/>
																	<ihtml:text property="noOfPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_NOOFPOINTS" value ="<%=noOfPoints.toString()%>"  maxlength="5"  />
																</logic:present>
																<logic:notPresent name="uldDamageChecklistVO" property="noOfPoints">
																	<ihtml:text property="noOfPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_NOOFPOINTS" value =""  maxlength="5"  />
																</logic:notPresent>
															</td>
														</tr>
													</logic:notPresent>
												
										</logic:iterate>
								</logic:present>


								<tr template="true" height="80px" id="uldTemplateRow" style="display:none">
									<td class="ic-center">
										<input type="checkbox" name="selectedDamageList" onclick="toggleTableHeaderCheckbox('selectedDamageList',targetFormName.eventHeadCheck)"/>
											<ihtml:hidden property="hiddenOperationFlag" value="NOOP"/>
									</td>
									<td class="ic-center">
										<ihtml:select componentID="CMB_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_TABLESECTION_COMBO" property="tableSection">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="oneTimeMode">
												<logic:iterate id="frmMode" name="oneTimeMode">
													<bean:define id="parameterType" name="frmMode" property="fieldType"/>
													<logic:equal name="parameterType" value="uld.defaults.section">
														<logic:present name="frmMode" property="fieldValue">
															<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
															<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
															<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
														</logic:present>
													</logic:equal >
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</td>
									<td class="ic-center">
										<ihtml:text property="description" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION" value =""  maxlength="100"  />
									</td>
									<td class="ic-center">
										<ihtml:text property="noOfPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_NOOFPOINTS" value =""  maxlength="5"  />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="ic-row ic-center marginT5">	
						<label>
							<common:message key="uld.defaults.damagechecklistmaster.totalpoints" />
						</label>
						<ihtml:text  property="totalPoints" componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_TOTALPOINTS" value="<%=form.getTotalPoints()%>" readonly="true"/>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btSave" componentID="BTN_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_SAVE" accesskey="S">
							<common:message key="Save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_CLOSE" accesskey="O">
							<common:message key="Close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			</ihtml:form>
		</div>

					
	
	</body>
</html:html>


