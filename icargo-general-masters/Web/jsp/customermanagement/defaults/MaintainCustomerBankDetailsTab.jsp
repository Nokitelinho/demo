<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  TAB implemetation
* File Name				:  MaintainCustomerBankDetailsTab.jsp
* Date					:  27-FEB-2013
* Author(s)				:  A-5183
*************************************************************************/
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
		
<bean:define id="form"
		name="MaintainCustomerRegistrationForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
		toScope="page" />
  
<business:sessionBean id="OneTimeValues"
		moduleName="customermanagement.defaults"
		screenID="customermanagement.defaults.maintainregcustomer"
		method="get"
		attribute="OneTimeValues" />
			
<business:sessionBean id="customerVO"
		moduleName="customermanagement.defaults"
		screenID="customermanagement.defaults.maintainregcustomer"
		method="get" attribute="customerVO"/>
		
	<ihtml:hidden property="misopcheck"/>
	<ihtml:hidden property="resave"/>

<!--Modified by A-5165 for ICRD-35135-->
	<%boolean indigoFlag = false;%>			 
	<common:xgroup>
		<common:xsubgroup id="INDIGO_SPECIFIC">

		 <% indigoFlag = true;%>
		</common:xsubgroup>
	</common:xgroup >
<!--Modified by A-5165 for ICRD-35135 ends-->
<!-- DIV Tab Start-->
<div id="pane6">
	<fieldset class="iCargoFieldSet">
		<!--Added if condition by A-5165 for ICRD-35135-->
			<% if(indigoFlag){%>
				<div class="ic-row">
					<div class="ic-input-container ic-input-round-border">
						<div class="ic-row">			
							<div class="ic-input ic-split-35" >
								<label>
									<common:message key="shared.agent.maintainagentmaster.lbl.bankname"/>
								</label>
								 <ihtml:text property="bankcode" name="form" 
								 componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_BANKNAME"  maxlength="20" />
							</div>
							<div class="ic-input ic-split-35" >
								<label>
									<common:message key="shared.agent.maintainagentmaster.lbl.branch"/>
								</label>
								<ihtml:text property="bankbranch" name="form" 
								componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_BRANCH"    maxlength="20" />
							</div>
							<div class="ic-input ic-split-30" >
								<label>
									<common:message key="shared.agent.maintainagentmaster.lbl.city"/>
								</label>
								<ihtml:text property="bankcityname" name="form" 
								componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_CITY"   maxlength="20" />
							</div>
						</div>
						<div class="ic-row">			
							<div class="ic-input ic-split-35" >
								<label>
									 <common:message key="shared.agent.maintainagentmaster.lbl.state"/>
								</label>
								<ihtml:text property="bankstatename" name="form" 
								componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_STATE"   maxlength="20" />
							</div>
							<div class="ic-input ic-split-35" >
								<label>
									 <common:message key="shared.agent.maintainagentmaster.lbl.country"/>
								</label>
								<ihtml:text property="bankcountryname" 
								name="form" componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_BANK_COUNTRY"   maxlength="20" />
							</div>
							<div class="ic-input ic-split-30" >
								<label>
									<common:message key="shared.agent.maintainagentmaster.lbl.pincode"/>
								</label>
								<ihtml:text property="bankpincode" name="form" 
								componentID="TXT_SHARED_AGENT_MAINTAINAGENTMST_PINCODE"   maxlength="20" />
							</div>
						</div>
					</div>
				</div>
			<% }%>
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ul class="ic-list-link">
						<li>
							<a class="iCargoLink" href="#" id="AddMiscDetails">
								<common:message key="shared.agent.maintainagentmaster.lnk.add" />
							</a>
						</li> |
						<li>
							<a class="iCargoLink" href="#" id="DeleteMiscDetails">
								<common:message key="shared.agent.maintainagentmaster.lnk.delete" />
							</a>
						</li>
					</ul>					
				</div>
			</div>
			<div id="div2" class="tableContainer" style="width:100%" >
				<table id="bankDetailsTable" class="fixed-header-table">
					<thead>
						<tr class="iCargoTableHeadingLeft">
							<td width="5%" class="iCargoTableHeaderLabel ic-center">
								<input type="checkbox" name="checkAll" value="checkbox" 
								onclick="updateHeaderCheckBox(this.form, this, this.form.rowIdmis);" />
							</td>
							<td width="25%" class="iCargoTableHeaderLabel">
								<common:message key="shared.agent.maintainagentmaster.lbl.code" />
							</td>
							<td width="25%" class="iCargoTableHeaderLabel" >
								<common:message key="shared.agent.maintainagentmaster.lbl.value" />
							</td>
							<td width="10%" class="iCargoTableHeaderLabel">
								<common:message key="shared.agent.maintainagentmaster.lbl.validfrom" />
							</td>
							<td width="10%" class="iCargoTableHeaderLabel">
								<common:message key="shared.agent.maintainagentmaster.lbl.validto" />
							</td>
							<td width="25%" class="iCargoTableHeaderLabel" >
								<common:message key="shared.agent.maintainagentmaster.lbl.remarks" />
							</td>
						</tr>
					</thead>
					<%int count=0;%>
					<tbody id="bankdetailsTableBody">

									<logic:present name="customerVO" >
										<logic:present  name="customerVO" property="customerMiscValDetailsVO">
										<bean:define id="customerMiscValDetails" property="customerMiscValDetailsVO" name="customerVO"/>
										<logic:iterate id="miscDetailsVO" name="customerMiscValDetails" 
											indexId="nIndexMisc" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO">
										
									 <%
										String onetimelov = "loadStateLov"+String.valueOf(nIndexMisc);									
									%>

										<logic:present name="miscDetailsVO" property="operationFlag">


										<logic:equal name="miscDetailsVO" property="operationFlag"  value="U">
										<!--logic:present name="miscDetailsVO" property="miscValidFrom"-->
										<tr id="updateTR<%=nIndexMisc%>" >
											<td class="iCargoTableDataTd" style="text-align=center;" >
												<input type="checkbox" name="rowIdmis"  value="<%=String.valueOf(nIndexMisc)%>" 
													onclick="toggleTableHeaderCheckbox('rowIdmis', this.form.checkAll);" />
												<ihtml:hidden property="miscDetailOpFlag" value="U"/>
											</td>
											<td  class="iCargoTableDataTd">
												<logic:present name="miscDetailsVO" property="miscCode" >
												<bean:define id="miscCode" name="miscDetailsVO"  property="miscCode"/>
													<ihtml:select  componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISCODE"  property="miscDetailCode"  indexId="nIndexMisc"    value="<%=miscCode.toString()%>" style="width:115px" disabled="true">

															<logic:present name="OneTimeValues">
																<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:equal name="parameterCode" value="shared.agent.bankdetails.codes" >
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
												</logic:present>
												<logic:notPresent name="miscDetailsVO"  property="miscCode" >
													<ihtml:select componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISCODE"  property="miscDetailCode"  indexId="nIndexMisc" style="width:115px">

															<logic:present name="OneTimeValues">
																<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:equal name="parameterCode" value="shared.agent.bankdetails.codes" >
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
												</logic:notPresent>
											</td>


										<td  class="iCargoTableDataTd">
										  <logic:present name="miscDetailsVO" property="miscValue" >
											<ihtml:text property="miscDetailValue"
											style="width:70px"
											indexId="nIndexMisc"
											value="<%=String.valueOf((miscDetailsVO).getMiscValue())%>"
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALUE"
												
											/>
										   </logic:present>
										   <logic:notPresent name="miscDetailsVO" property="miscValue" >
												<ihtml:text property="miscDetailValue"
												style="border: 0px;background :;width:70px"
												indexId="nIndexMisc"
												value=""
												componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALUE"
												
												/>
											</logic:notPresent>
											<div class="lovImgTbl valignT">
											<img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="loadStateLov" onclick="showConditionalLov('loadStateLov',this)" id= "loadStateLov<%=String.valueOf(nIndexMisc)%>"/>
											</div>
										</td>

										<td>
										<logic:present name="miscDetailsVO" property="miscValidFrom" >
											<ibusiness:calendar id="miscDetailValidFrom" property="miscDetailValidFrom" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_FROMDATE" indexId="nIndexMisc" value="<%=String.valueOf((miscDetailsVO).getMiscValidFrom().toDisplayDateOnlyFormat())%>" type="image"/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscValidFrom" >
										<ibusiness:calendar id="miscDetailValidFrom" property="miscDetailValidFrom" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_FROMDATE" indexId="nIndexMisc" value="" type="image"/>
										</logic:notPresent>
										</td>
										<td>
										<logic:present name="miscDetailsVO" property="miscValidTo" >
											<ibusiness:calendar id="miscDetailValidTo" property="miscDetailValidTo" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_TODATE" indexId="nIndexMisc" value="<%=String.valueOf((miscDetailsVO).getMiscValidTo().toDisplayDateOnlyFormat())%>" type="image"/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscValidTo" >
											<ibusiness:calendar id="miscDetailValidTo" property="miscDetailValidTo" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_TODATE" indexId="nIndexMisc" value="" type="image"/>
										</logic:notPresent>
										</td>

										<td  class="iCargoTableDataTd">
										<logic:present name="miscDetailsVO" property="miscRemarks" >
											<ihtml:text property="miscDetailRemarks"
											style="width:70px"
											indexId="nIndexMisc"
											value="<%=String.valueOf((miscDetailsVO).getMiscRemarks())%>"
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALIDREMARKS"
											maxlength="50"
											/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscRemarks" >
											<ihtml:text property="miscDetailRemarks"
											style="border: 0px;background :;width:70px"
											indexId="nIndexMisc"
											value=""
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALIDREMARKS"
											maxlength="50"
											/>
										</logic:notPresent>
										</td>

										<ihtml:hidden property="miscSeqNum" value="<%=String.valueOf((miscDetailsVO).getSequenceNumber())%>"/>


									</tr>
									<!--/logic:present -->
									</logic:equal>
									<!-- modified by A-4810 for bug-icrd-11307 -->
									<logic:equal name="miscDetailsVO" property="operationFlag"  value="I">
										<logic:present name="miscDetailsVO" property="miscValidFrom">
										<tr id="updateTR<%=nIndexMisc%>" >
											<td class="iCargoTableDataTd" style="text-align=center;" >
												<input type="checkbox" name="rowIdmis"  value="<%=String.valueOf(nIndexMisc)%>" onclick="toggleTableHeaderCheckbox('rowIdmis', this.form.checkAll);" />
												<ihtml:hidden property="miscDetailOpFlag" value="I"/>
											</td>
											<td  class="iCargoTableDataTd">
												<logic:present name="miscDetailsVO" property="miscCode" >
												<bean:define id="miscCode" name="miscDetailsVO"  property="miscCode"/>
													<ihtml:select  componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISCODE"  property="miscDetailCode"  indexId="nIndexMisc"    value="<%=miscCode.toString()%>" style="width:115px">
															<logic:present name="OneTimeValues">
																<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:equal name="parameterCode" value="shared.agent.bankdetails.codes" >
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
												</logic:present>
												<logic:notPresent name="miscDetailsVO"  property="miscCode" >
													<ihtml:select componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISCODE"  property="miscDetailCode"  indexId="nIndexMisc" style="width:115px">
															<logic:present name="OneTimeValues">
																<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:equal name="parameterCode" value="shared.agent.bankdetails.codes" >
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
												</logic:notPresent>
											</td>
										<td  class="iCargoTableDataTd">
										  <logic:present name="miscDetailsVO" property="miscValue" >
											<ihtml:text property="miscDetailValue"
											style="width:70px"
											indexId="nIndexMisc"
											value="<%=String.valueOf((miscDetailsVO).getMiscValue())%>"
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALUE"
												
											/>
										   </logic:present>
										   <logic:notPresent name="miscDetailsVO" property="miscValue" >
												<ihtml:text property="miscDetailValue"
												style="border: 0px;background :;width:70px"
												indexId="nIndexMisc"
												value=""
												componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALUE"
												
												/>
											</logic:notPresent>
											<div class="lovImgTbl valignT">
											<img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="loadStateLov" onclick="showConditionalLov('loadStateLov',this)" id="loadStateLov<%=String.valueOf(nIndexMisc)%>" />
											</div>
										</td>
										<td>
										<logic:present name="miscDetailsVO" property="miscValidFrom" >
											<ibusiness:calendar id="miscDetailValidFrom" property="miscDetailValidFrom" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_FROMDATE" indexId="nIndexMisc" value="<%=String.valueOf((miscDetailsVO).getMiscValidFrom().toDisplayDateOnlyFormat())%>" type="image"/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscValidFrom" >
										<ibusiness:calendar id="miscDetailValidFrom" property="miscDetailValidFrom" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_FROMDATE" indexId="nIndexMisc" value="" type="image"/>
										</logic:notPresent>
										</td>
										<td>
										<logic:present name="miscDetailsVO" property="miscValidTo" >
											<ibusiness:calendar id="miscDetailValidTo" property="miscDetailValidTo" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_TODATE" indexId="nIndexMisc" value="<%=String.valueOf((miscDetailsVO).getMiscValidTo().toDisplayDateOnlyFormat())%>" type="image"/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscValidTo" >
											<ibusiness:calendar id="miscDetailValidTo" property="miscDetailValidTo" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_TODATE" indexId="nIndexMisc" value="" type="image"/>
										</logic:notPresent>
										</td>
										<td  class="iCargoTableDataTd">
										<logic:present name="miscDetailsVO" property="miscRemarks" >
											<ihtml:text property="miscDetailRemarks"
											style="width:70px"
											indexId="nIndexMisc"
											value="<%=String.valueOf((miscDetailsVO).getMiscRemarks())%>"
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALIDREMARKS"
											maxlength="50"
											/>
										</logic:present>
										<logic:notPresent name="miscDetailsVO" property="miscRemarks" >
											<ihtml:text property="miscDetailRemarks"
											style="border: 0px;background :;width:70px"
											indexId="nIndexMisc"
											value=""
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALIDREMARKS"
											maxlength="50"
											/>
										</logic:notPresent>
										</td>
										<ihtml:hidden property="miscSeqNum" value="<%=String.valueOf((miscDetailsVO).getSequenceNumber())%>"/>
									</tr>
									</logic:present>
									</logic:equal>

									<logic:equal name="miscDetailsVO" property="operationFlag" value="D">
										<ihtml:hidden property="miscDetailCode" name="miscDetailsVO"  value="<%=String.valueOf((miscDetailsVO).getMiscCode())%>" />
										<ihtml:hidden property="miscDetailValue" name="miscDetailsVO" value="<%=String.valueOf((miscDetailsVO).getMiscValue())%>" />
										<ihtml:hidden property="miscDetailValidFrom" name="miscDetailsVO" value="<%=String.valueOf((miscDetailsVO).getMiscValidFrom().toDisplayDateOnlyFormat())%>"/>
										<ihtml:hidden property="miscDetailValidTo" name="miscDetailsVO"  value="<%=String.valueOf((miscDetailsVO).getMiscValidTo().toDisplayDateOnlyFormat())%>"/>
										<ihtml:hidden property="miscDetailRemarks" name="miscDetailsVO" value="<%=String.valueOf((miscDetailsVO).getMiscRemarks())%>"/>
										<ihtml:hidden property="miscDetailOpFlag" value="D"/>
										<ihtml:hidden property="miscSeqNum" name="miscDetailsVO"  value="<%=String.valueOf((miscDetailsVO).getSequenceNumber())%>"/>
									</logic:equal>

									</logic:present>

									<% count++;%>

									</logic:iterate>
									
									
									<% count=0; %>
									</logic:present>
								</logic:present>
								
						 <!--Template row-->
									<tr template="true" id="bankdetailsTemplateRow"  style="display:none" >
										<td class="iCargoTableDataTd ic-center">
											<ihtml:checkbox property="rowIdmis" onclick="toggleTableHeaderCheckbox('rowIdmis', this.form.checkAll);" />
											 <ihtml:hidden property="miscDetailOpFlag" value="NOOP"/>
										</td>
										<td  class="iCargoTableDataTd">
											<ihtml:select property="miscDetailCode" indexId = "bankdetailsTemplateRow"	componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISCODE" style="width:115px" >




														<logic:present name="OneTimeValues">
															<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
															<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:equal name="parameterCode" value="shared.agent.bankdetails.codes" >
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
										</td>
										<td  class="iCargoTableDataTd">
											<ihtml:text property="miscDetailValue"
											style="width:95%;"
											indexId="bankdetailsTemplateRow"
											value=""
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALUE"
											
											/>
											<div class="lovImgTbl valignT">
											<img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="loadStateLov" onclick="showConditionalLov('loadStateLov',this)" id="loadStateLov" />
											</div>
										</td>
										<td>
										   <ibusiness:calendar id="miscDetailValidFrom" property="miscDetailValidFrom" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_FROMDATE" indexId="nIndexMisc" value="" type="image"/>
										</td>
										<td>
											<ibusiness:calendar id="miscDetailValidTo" property="miscDetailValidTo" componentID="CAL_SHARED_AGENT_MAINTAINAGENTMST_TODATE" indexId="nIndexMisc" value="" type="image"/>
										</td>
										<td  class="iCargoTableDataTd">
											<ihtml:text property="miscDetailRemarks"
											style="width:95%;"
											indexId="bankdetailsTemplateRow"
											value=""
											componentID="CMP_SHARED_AGENT_MAINTAINAGENTMST_MISVALIDREMARKS"
											maxlength="50"
											/>
										</td>
										<ihtml:hidden property="miscSeqNum" value="1"/>
									</tr>
						<!--Template Row Ends--->


							  </tbody>


						   <!-- add -->

						</table>

					</td>
				</tr>
			</table>

		</td>
	</tr>
	</table>
	</fieldset>
</div>