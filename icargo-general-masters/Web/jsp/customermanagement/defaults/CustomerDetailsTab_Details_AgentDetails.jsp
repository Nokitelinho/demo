<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@page import ="java.util.ArrayList"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

		<business:sessionBean id="customerVO"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get" attribute="customerVO"/>

		<business:sessionBean id="OneTimeValues"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="OneTimeValues" />
	
		
		<div class="ic-row">
			<h3><common:message key="customermanagement.defaults.customerregistration.lbl.agntdet" scope="request"/></h3>
			<div class="ic-button-container paddR5">
				<a href="#" id="addclearingagent" class="iCargoLink">
					<common:message key="customermanagement.defaults.customerregistration.lbl.add" scope="request"/>
				</a> |
				<a href="#" id="deleteclearingagent" class="iCargoLink">
					<common:message key="customermanagement.defaults.customerregistration.lbl.delete" scope="request"/>
				</a> 
			</div>					

		</div>
		<div class="ic-row">
			<div id="div2" class="tableContainer"  style="height:180px;">
				<table id="AgentDetailsTable" class="fixed-header-table">
					<thead>
						<tr>
							<td class="ic-center" width="3%"><input type="checkbox" name="masterRowId1" 
							onclick="updateHeaderCheckBox(this.form,this.form.masterRowId1,this.form.selectedClearingAgentDetails)"/>
							</td>
							<td width="13%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.station" scope="request"/>
							</td>
							<td width="5%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.export" scope="request"/>
							</td>
							<td width="5%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.import" scope="request"/>
							</td>
							<td width="5%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.sales" scope="request"/>
							</td>
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.carrier" scope="request"/>
							</td>
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.agentscc" scope="request"/>
							</td>
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.agentcode" scope="request"/>
							</td>
							<td width="11%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.agentname" scope="request"/>
							</td>
							<td width="13%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.remark" scope="request"/>
							</td>
							
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.agentReferenceId" scope="request"/>
							</td>
							<td width="15%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.origin" scope="request"/>
							</td>
							<logic:present name="OneTimeValues">
										<bean:define id="OneTimeValuesMap" name="OneTimeValues"
										type="java.util.HashMap" />
										<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<logic:equal name="oneTimeValue" property="key"
											value="customermanagement.defaults.agenttype">
							<td width="13%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.Agenttype" scope="request"/>
							</td>
							</logic:equal>
							</logic:iterate>
						    </logic:present>
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.validFrom" scope="request"/>
							</td>
							<td width="14%">
								<common:message key="customermanagement.defaults.customerregistration.lbl.validTo" scope="request"/>
							</td>
						</tr>
					</thead>
					<tbody id="custRegAgentTableBody">
						<logic:present name="customerVO" property="customerAgentVOs" >
							<bean:define id="customerClearingAgentVOs" name="customerVO"  property="customerAgentVOs"/>
							<logic:iterate id="customerClearingAgentVO" name="customerClearingAgentVOs" indexId="clearingIndex" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO">

								<logic:present name="customerClearingAgentVO" property="operationFlag">
									<bean:define id="opFlag" name="customerClearingAgentVO" property="operationFlag"/>
									<ihtml:hidden property="opFlag" value="<%=(String)opFlag%>"/>
								</logic:present>
								<logic:notPresent name="customerClearingAgentVO" property="operationFlag">
									<bean:define id="opFlag"  value="NA"/>
									<ihtml:hidden property="opFlag" value="NA"/>
								</logic:notPresent>

								<logic:notMatch name="opFlag" value="D">

									<logic:match name="opFlag" value="I">
										<ihtml:hidden property="hiddenOpFlagForAgent" value="I"/>
									</logic:match>
									<logic:notMatch name="opFlag" value="I">
										<ihtml:hidden property="hiddenOpFlagForAgent" value="U"/>
									</logic:notMatch>


									<tr>
										<logic:present name="customerClearingAgentVO" property="operationFlag">
										<!-- <ihtml:hidden property="hiddenOpFlagForAgent" value="I"/> -->

											<bean:define id="opFlag" name="customerClearingAgentVO" property="operationFlag"/>
											<ihtml:hidden property="operationAgentFlag" value="<%=(String)opFlag%>"/>
										</logic:present>
										<logic:notPresent name="customerClearingAgentVO" property="operationFlag">
											<bean:define id="opFlag"  value="NA"/>
											<ihtml:hidden property="operationAgentFlag" value="NA"/>
										</logic:notPresent>

										<td class="iCargoTableDataTd ic-center">
											<ihtml:checkbox property="selectedClearingAgentDetails" onclick="toggleTableHeaderCheckbox('selectedClearingAgentDetails',this.form.masterRowId1)" value="<%=String.valueOf(clearingIndex)%>" />
										</td>
										<td   class="iCargoTableDataTd">

											<ihtml:text property="agentStation" styleClass="iCargoEditableTextFieldRowColor1" value="<%=String.valueOf(customerClearingAgentVO.getStationCode())%>" style="width:90px" maxlength="3"/>
                                            <div class="lovImgTbl valignT">
											<img src="<%= request.getContextPath()%>/images/lov.png" width="16"
											height="16" id="airportlov<%=clearingIndex.toString()%>"
											onclick="displayAirport('airportlov',this)" />
                                            </div>
										</td>
										<td   class="iCargoTableDataTd">

											<logic:present name="customerClearingAgentVO" property="exportFlag">
												<bean:define id="exportFlag" name="customerClearingAgentVO" property="exportFlag" />
												<logic:equal name="exportFlag" value="Y">
													<input type="checkbox" name="exported" value="<%=String.valueOf(clearingIndex)%>"  checked />
												</logic:equal>
												<logic:equal name="exportFlag" value="N">
													<input type="checkbox" name="exported" value="<%=String.valueOf(clearingIndex)%>"  />
												</logic:equal>
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="exportFlag">
												<input type="checkbox" name="exported" value="<%=String.valueOf(clearingIndex)%>"  />
											</logic:notPresent>
										</td>
										<td   class="iCargoTableDataTd">


											<logic:present name="customerClearingAgentVO" property="importFlag">
												<bean:define id="importFlag" name="customerClearingAgentVO" property="importFlag" />
												<logic:equal name="importFlag" value="Y">
													<input type="checkbox" name="imported" value="<%=String.valueOf(clearingIndex)%>"  checked />
												</logic:equal>
												<logic:equal name="importFlag" value="N">
													<input type="checkbox" name="imported" value="<%=String.valueOf(clearingIndex)%>"  />
												</logic:equal>
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="importFlag">
												<input type="checkbox" name="imported" value="<%=String.valueOf(clearingIndex)%>"  />
											</logic:notPresent>
										</td>
										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="salesFlag">
												<bean:define id="salesFlag" name="customerClearingAgentVO" property="salesFlag" />
												<logic:equal name="salesFlag" value="Y">
													<input type="checkbox" name="sales" value="<%=String.valueOf(clearingIndex)%>"  checked />
												</logic:equal>

												<logic:equal name="salesFlag" value="N">
													<input type="checkbox" name="sales" value="<%=String.valueOf(clearingIndex)%>"  />
												</logic:equal>

											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="salesFlag">
												<input type="checkbox" name="sales" value="<%=String.valueOf(clearingIndex)%>"  />
											</logic:notPresent>
										</td>

										<!-- Added by A-4826 for IASCB-4841 beg -->
										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="carrier">
												<ihtml:text property="agentCarrier" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CARRIER" value="<%=customerClearingAgentVO.getCarrier()%>" maxlength="25" />
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16"
												id="agentCarrierLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentCarrier('agentCarrierLOV',this)" alt="Carrier LOV"/>
											    </div>
											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="carrier">
												<ihtml:text property="agentCarrier" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CARRIER" value="" maxlength="25"/>
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16" 
												id="agentCarrierLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentCarrier('agentCarrierLOV',this)" alt="Carrier LOV"/>
											    </div>
											</logic:notPresent>
										</td>
									<!-- Added by A-4826 for IASCB-4841 end -->
									<!-- Added by A-4826 for  -->
										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="scc">
												<ihtml:text property="agentScc" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTSCC" value="<%=customerClearingAgentVO.getScc()%>" maxlength="25" />
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16"
												id="agentsccLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentSCC('agentsccLOV',this)" alt="Agent SCC LOV"/>
											    </div>
											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="scc">
												<ihtml:text property="agentScc" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTSCC" value="" maxlength="25"/>
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16" 
												id="agentsccLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentSCC('agentsccLOV',this)" alt="Agent SCC LOV"/>
											    </div>
											</logic:notPresent>
										</td>


										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="agentCode">
												<ihtml:text property="agentCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTCODE" styleClass="iCargoEditableTextFieldRowColor1" style="width:90px" value="<%=customerClearingAgentVO.getAgentCode()%>" maxlength="12" indexId="clearingIndex"/>
											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="agentCode">
												<ihtml:text property="agentCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTCODE" styleClass="iCargoEditableTextFieldRowColor1" style="width:90px" value="" maxlength="12" indexId="clearingIndex" />
											</logic:notPresent>
											 <div class="lovImgTbl valignT">
											<img id="agentcodelov<%=clearingIndex%>"
											src="<%=request.getContextPath()%>/images/lov.png" onclick="lovScreen(this)"
											width="16" height="16" />
										    </div>
										</td>



									<!--added by A-5791 for ICRD-59602-->
										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="agentName">
												<ihtml:text property="agentName" styleClass="iCargoEditableTextFieldRowColor1" style="width:90px" indexId="clearingIndex" value="<%=customerClearingAgentVO.getAgentName()%>" maxlength="150" readOnly="true" />
											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="agentName">
												<ihtml:text property="agentName" styleClass="iCargoEditableTextFieldRowColor1" 
												style="width:90px" indexId="clearingIndex"  maxlength="150" readOnly="true" value=""/>
											</logic:notPresent>
										</td>


										<td   class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="remarks">
												<ihtml:text property="agentRemarks" value="<%=customerClearingAgentVO.getRemarks()%>" styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" />
											</logic:present>

											<logic:notPresent name="customerClearingAgentVO" property="remarks">
												<ihtml:text property="agentRemarks" value="" 
												styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" />
											</logic:notPresent>
										</td>
											<td class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="agentReferenceId">
												<ihtml:text property="agentReferenceId" value="<%=customerClearingAgentVO.getAgentReferenceId()%>" styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" readOnly="true" />
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="agentReferenceId">
												<ihtml:text property="agentReferenceId" value="" 
												styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" readOnly="true" />
											</logic:notPresent>
										</td>
										<td   class="iCargoTableDataTd">
											 <logic:present name="customerClearingAgentVO" property="origin">
												<ihtml:text property="origin" value="<%=customerClearingAgentVO.getOrigin()%>"       componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTORIGIN" styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" />
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16"
												id="agentoriginLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentOrigin('agentoriginLOV',this)" alt="Agent Origin LOV"/>
											    </div>
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="origin">
												<ihtml:text property="origin" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTORIGIN" value=""
														styleClass="iCargoEditableTextFieldRowColor1" style="width:100px"	/>
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16"
												id="agentoriginLOV<%=clearingIndex.toString()%>" 
												onclick="displayAgentOrigin('agentoriginLOV',this)" alt="Agent Origin LOV"/>
											    </div>
											</logic:notPresent>
										</td>
										<logic:present name="OneTimeValues">
										<bean:define id="OneTimeValuesMap" name="OneTimeValues"
										type="java.util.HashMap" />
										<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<logic:equal name="oneTimeValue" property="key"
											value="customermanagement.defaults.agenttype">
											<td class="iCargoTableDataTd">
											<ihtml:select name="customerClearingAgentVO" property="agentType" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TYPE" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<bean:define id="customermanagement" name="oneTimeValue"
												property="value" />
										<logic:iterate id="customermanagement" name="customermanagement"
												>
										<logic:present name="customermanagement" property="fieldValue">
										<bean:define id="fieldValue" name="customermanagement"
														property="fieldValue" />
										<bean:define id="fieldDescription" name="customermanagement"
														property="fieldDescription" />					
										<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>
											</logic:present>
											</logic:iterate>
											</ihtml:select>
										</td>
											</logic:equal>
											</logic:iterate>
											</logic:present>
									<td class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="validityStartDate">
												<bean:define id="validFrom" name="customerClearingAgentVO" 
													property="validityStartDate" type="LocalDate"/>
												<%String formattedValidFrom = validFrom.toDisplayFormat("dd-MMM-yyyy");%>
												<ibusiness:calendar id="agnetValidFrom" property="agnetValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" indexId="clearingIndex" value="<%=formattedValidFrom%>" type="image"/>
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="validityStartDate">
												<ibusiness:calendar id="agnetValidFrom" property="agnetValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" indexId="clearingIndex" value="" type="image"/>
											</logic:notPresent>
										</td>	
											<td class="iCargoTableDataTd">
											<logic:present name="customerClearingAgentVO" property="validityEndDate">
												<bean:define id="validTo" name="customerClearingAgentVO" 
													property="validityEndDate" type="LocalDate"/>
												<%String formattedValidFrom = validTo.toDisplayFormat("dd-MMM-yyyy");%>
												<ibusiness:calendar id="agnetValidTo" property="agnetValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" indexId="clearingIndex" value="<%=formattedValidFrom%>" type="image"/>
											</logic:present>
											<logic:notPresent name="customerClearingAgentVO" property="validityEndDate">
												<ibusiness:calendar id="agnetValidTo" property="agnetValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" indexId="clearingIndex" value="" type="image"/>
											</logic:notPresent>
										</td>

											<logic:notPresent name="customerClearingAgentVO" property="agentReferenceId">
												<ihtml:text property="agentReferenceId" value="" 
												styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" readOnly="true" />
											</logic:notPresent>
										</td>

									</tr>
								</logic:notMatch>

								<logic:match  name="opFlag" value="D">
									<ihtml:hidden property="hiddenOpFlagForAgent" value="D"/>
									<ihtml:hidden property="airportlov"  value=" " />
									<ihtml:hidden property="agentcodelov"  value=" " />
									<ihtml:hidden property="exported"  value=" " />
									<ihtml:hidden property="imported"  value=" " />
									<ihtml:hidden property="sales"  value=" " />

									<logic:present name="customerClearingAgentVO" property="operationFlag">
										<bean:define id="opFlag" name="customerClearingAgentVO" property="operationFlag"/>
										<ihtml:hidden property="operationAgentFlag" value="<%=(String)opFlag%>"/>
									</logic:present>


									<logic:present name="customerClearingAgentVO" property="agentCode">
										<bean:define id="agntCode" name="customerClearingAgentVO" property="agentCode"/>
										<ihtml:hidden property="agentCode" value="<%=(String)agntCode%>"/>
									</logic:present>
									<logic:notPresent name="customerClearingAgentVO" property="agentCode">
										<ihtml:hidden property="agentCode" value=""/>
									</logic:notPresent>

									<logic:present name="customerClearingAgentVO" property="stationCode">
										<bean:define id="agntStation" name="customerClearingAgentVO" property="stationCode"/>
										<ihtml:hidden property="agentStation" value="<%=(String)agntStation %>"/>
									</logic:present>
									<logic:notPresent name="customerClearingAgentVO" property="stationCode">
										<ihtml:hidden property="agentStation" value=""/>
									</logic:notPresent>

									<logic:present name="customerClearingAgentVO" property="remarks">
										<bean:define id="agntRemarks" name="customerClearingAgentVO" property="remarks"/>
										<ihtml:hidden property="agentRemarks" value="<%=(String)agntRemarks %>"/>
									</logic:present>
									<logic:notPresent name="customerClearingAgentVO" property="remarks">
										<ihtml:hidden property="agentRemarks" value=""/>
									</logic:notPresent>
								</logic:match>
							</logic:iterate>

						</logic:present>

						<tr template="true" id="custRegAgentTemplateRow" style="display:none">
							<ihtml:hidden property="hiddenOpFlagForAgent" value="NOOP"/>
							<ihtml:hidden property="operationAgentFlag" value=""/>


							<td  style="text-align:center"   class="iCargoTableDataTd">
								<ihtml:checkbox property="selectedClearingAgentDetails" onclick="" value="" />
							</td>

							<td   class="iCargoTableDataTd">
								<ihtml:text property="agentStation" styleClass="iCargoEditableTextFieldRowColor1" value="" style="width:90px"/>
								 <div class="lovImgTbl valignT">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="16"
								height="16" id="airportlov" onclick="airportlov('airportlov',this)"/>
							     </div>
							</td>

							<td   class="iCargoTableDataTd">
								<input type="checkbox" name="exported" value=""  checked />

							</td>

							<td   class="iCargoTableDataTd">
								<input type="checkbox" name="imported" value=""  checked />
							</td>

							<td   class="iCargoTableDataTd">
								<input type="checkbox" name="sales" value=""  checked />
							</td>
							<!-- Added by A-4826 for IASCB-4841 beg -->
							<td   class="iCargoTableDataTd">
								<ihtml:text property="agentCarrier" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CARRIER" 
								value="" maxlength="25"/>
								 <div class="lovImgTbl valignT">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16" id="agentCarrierLOV" onclick="agentCarrierLOV('agentCarrierLOV',this)" alt="Carrier LOV"/>
							    </div>
							</td>
							<!-- Added by A-4826 for IASCB-4841 beg end -->
						<!-- Added by A-4826 for  -->
							<td   class="iCargoTableDataTd">
								<ihtml:text property="agentScc" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTSCC" 
								value="" maxlength="25"/>
								 <div class="lovImgTbl valignT">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16" id="agentsccLOV" onclick="sccLOV('agentsccLOV',this)" alt="Agent SCC LOV"/>
							    </div>
							</td>

							<td class="iCargoTableDataTd ">
								<ihtml:text property="agentCode" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTCODE" 	styleClass="iCargoEditableTextFieldRowColor1"  value="" maxlength="12" style="width:90px"/>
								 <div class="lovImgTbl valignT">
								<img id="agentcodelov"
								src="<%=request.getContextPath()%>/images/lov.png"
								width="16" height="16"/>
                                </div>
							</td>

						<!--Added by A-5791  for ICRD-59602-->

							<td   class="iCargoTableDataTd">
								<ihtml:text property="agentName"  
								value="" maxlength="150" style="width:90px" readOnly="true" />
							</td>

							<td   class="iCargoTableDataTd">
								<ihtml:text property="agentRemarks" value="" style="width:100px" />
							</td>
							 <td   class="iCargoTableDataTd">
								<ihtml:text property="agentReferenceId"  
								value=""  style="width:90px" readOnly="true" />
							</td>
							<td>
							<ihtml:text property="origin" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENTORIGIN" value="" styleClass="iCargoEditableTextFieldRowColor1" style="width:100px" />
												 <div class="lovImgTbl valignT">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16"
												id="agentoriginLOV" 
												onclick="displayAgentOrigin('agentoriginLOV',this)" alt="Agent Origin LOV"/>
											    </div>
												</td>
						 <logic:present name="OneTimeValues">
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:equal name="parameterCode" value="customermanagement.defaults.agenttype" >
						 <td class="iCargoTableDataTd" >
						  <ihtml:select property="agentType" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_TYPE" indexId="index">
						  <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:iterate id="parameterValue" name="parameterValues">
													<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>
													</logic:present>
									</logic:iterate>
						 </ihtml:select>
						 </td>
									</logic:equal >
									</logic:iterate>
								</logic:present>	
											<td>
											<ibusiness:calendar id="agnetValidFrom" property="agnetValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" indexId="" value="" type="image"/>
											</td>
											<td>
											<ibusiness:calendar id="agnetValidTo" property="agnetValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" indexId="" value="" type="image"/>
							</td>

						
						</tr>
					</tbody>
				</table>
			</div>
		</div>