 <%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PostalAdminiMaster_BillingInfoAndSettlmentInfo.jsp
* Date					:  14-Aug-2013
* Author(s)				:  A-5253
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO"%>

 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailEventVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "java.util.Calendar" %>
 <%@ page import="java.util.HashMap"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
 
 <bean:define id="form"
		 name="PostalAdministrationForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"
		 toScope="page" />

	<business:sessionBean id="paVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="paVO"/>
	 <business:sessionBean id="postalAdministrationDetailsVOs"
	 		 moduleName="mail.operations"
	 		 screenID="mailtracking.defaults.masters.postaladministration"
	 		 method="get"
	 	 attribute="postalAdministrationDetailsVOs"/>



	<business:sessionBean id="ONETIME_CATEGORY"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="oneTimeCategory"/>
	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
	 	  method="get"
	  attribute="OneTimeValues" />
	    <div class="ic-row">
		<div class="ic-col-55">
		 <div class="ic-round-border">
			
				<fieldset class="ic-field-set">
					<legend><common:message key="mailtracking.defaults.pamaster.lbl.billinginfo" /></legend>
					  <div class="ic-button-container">
						<div class="ic-row ic-right">
							<a id="addBilLink" name="addBilLink" class="iCargoLink" href="#" value="add">
								<common:message key="mailtracking.defaults.pamaster.lbl.add" />
							</a>						|
							<a id="deleteBilLink" name="deleteBilLink" class="iCargoLink" href="#" value="delete">
								<common:message key="mailtracking.defaults.pamaster.lbl.delete"/>
							</a>
						</div>
					  </div>	
						<div class="ic-row">
							
								<div class="tableContainer" id="billdiv" style="height:120px;">
										<table class="fixed-header-table ic-pad-3">
											<thead>
												<tr>
													<td  class="iCargoTableHeaderLabel" width="3%"><input type="checkbox" name="billChkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.billChkAll,this.form.billCheck)" /> </td>
													<td class="iCargoTableHeaderLabel" width="25%"> <common:message key="mailtracking.defaults.pamaster.lbl.billingsource"/></td>
													<td class="iCargoTableHeaderLabel" width="22%"><common:message key="mailtracking.defaults.pamaster.lbl.billingfrequency" /></td>
													<!--<td class="iCargoTableHeadingCenter"><common:message key="mailtracking.defaults.pamaster.lbl.proformainv"/></td>-->
													<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.defaults.pamaster.lbl.validfrm"/></td>
													<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.defaults.pamaster.lbl.validto"/></td>
												</tr>
											</thead>
											<tbody id="bilTableBody">
														<%String keyParameter=null;%>
														<logic:present name="postalAdministrationDetailsVOs" >
															<bean:define id="postalAdministrationDetailsVOs" name="postalAdministrationDetailsVOs" type="java.util.HashMap" />
															<logic:iterate id="postalAdministrationDetailsVO" name="postalAdministrationDetailsVOs"  indexId="rowCount">
															<logic:present name="postalAdministrationDetailsVO" property="key">
																<bean:define id="keyValue" name="postalAdministrationDetailsVO" property="key" type="java.lang.String" />
																<%
																	keyParameter=keyValue;
																%>
															</logic:present>
															<%
																if(keyParameter.equals("BLGINFO")){
															%>
															<logic:present name="postalAdministrationDetailsVO" property="value">
																<bean:define id="innerPostalAdministrationDetailsVOs" name="postalAdministrationDetailsVO" property="value" type="java.util.Collection" />
																<logic:iterate id="innerPostalAdministrationDetailsVO" name="innerPostalAdministrationDetailsVOs"  indexId="rowCount" >
																	<logic:equal name="innerPostalAdministrationDetailsVO" property="operationFlag" value="D">
																		<ihtml:hidden property="billCheck" name="form"/>
																		<ihtml:hidden property="billingSource" name="form"/>
																		<ihtml:hidden property="billingFrequency" name="form"/>
																		<ihtml:hidden property="bilSerNum" name="form"/>
																		<!--<ihtml:hidden property="profInv"/>-->
																		<ihtml:hidden property="validFrom"/>
																		<ihtml:hidden property="validTo"/>
																	</logic:equal>
																	<logic:notEqual name="innerPostalAdministrationDetailsVO" property="operationFlag" value="D">
																	<ihtml:hidden name="form" property="bilOpFlag" value="N"/>
																		<tr >
																			<td class="iCargoTableDataTd" style="text-align:center">
																				<logic:present  name="innerPostalAdministrationDetailsVO" property="companyCode">
																				<input type="checkbox" name="billCheck" value="<%=String.valueOf(rowCount)%>" onclick="toggleTableHeaderCheckbox('billCheck',this.form.billChkAll)" />
																				</logic:present>
																			</td>
																			<td class="iCargoTableDataTd" >
																				<logic:present  name="innerPostalAdministrationDetailsVO" property="billingSource">
																				<bean:define id="billingSource" name="innerPostalAdministrationDetailsVO" property="billingSource" />													
														<ihtml:select  componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGSOURCE"  property="billingSource"  indexId="templateRowCount"    value="<%=billingSource.toString()%>" style="width:140px" disabled="true">
																					<logic:present name="OneTimeValues">
																						<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																						<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																						<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																						<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																						   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:equal name="parameterCode" value="mailtracking.defaults.billingsource" >
																								<logic:present name="parameterValue" property="fieldValue">
																									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
																								</logic:present>
																					    </logic:equal>
																					  </logic:iterate>
																</logic:iterate>
																					</logic:present>
														</ihtml:select>
																				</logic:present>
																				<logic:notPresent  name="innerPostalAdministrationDetailsVO" property="billingSource">
													
															<ihtml:select componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGSOURCE"  property="billingSource"  indexId="templateRowCount" style="width:140px" disabled="true">
																	<logic:present name="OneTimeValues">
																		<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
																		<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																			<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																			<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																			<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:equal name="parameterCode" value="mailtracking.defaults.parcod" >
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
																			<td class="iCargoTableDataTd" >
																				<logic:present  name="innerPostalAdministrationDetailsVO" property="billingFrequency">
																				<bean:define id="billingFrequency" name="innerPostalAdministrationDetailsVO" property="billingFrequency" />
													<ihtml:text componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGFREQUENCY" 
													 property="billingFrequency" 
													 value="<%=billingFrequency.toString()%>" readonly="true"/>
																				</logic:present>
																				<logic:notPresent  name="innerPostalAdministrationDetailsVO" property="billingFrequency">
											  
											<ihtml:text componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGFREQUENCY" 
											 property="billingFrequency" 
											 value=""  readonly="true"/>
																				</logic:notPresent>
																<div class="lovImgTbl valignT"><img name="billingPeriodLOV" id="billingPeriodLOV" height="16" src="<%=request.getContextPath()%>/images/lov.png" readonly="true" width="16"/></div>				
																			</td>
																			<!--	<td class="iCargoTableDataTd" >
																				
																				 <logic:present name="innerPostalAdministrationDetailsVO" property="profInv">
																					<bean:define id="profInv" name="innerPostalAdministrationDetailsVO" property="profInv"/>
																					<logic:equal name="profInv" value="Y">
																						<input type="checkbox" name="profInv"  checked="true"  disabled="true"/>
																						<ihtml:hidden property="profInv" value="Y"/>
																					</logic:equal>
																					<logic:notEqual name="profInv" value="Y">
																						<input type="checkbox" name="profInv"   disabled="true"/>
																						<ihtml:hidden property="profInv" value="N"/>
																					</logic:notEqual>
																				 </logic:present>
																				 <logic:notPresent name="innerPostalAdministrationDetailsVO" property="profInv">
																					<input type="checkbox" name="profInv"   disabled="true"/>
																					<ihtml:hidden property="profInv" value="N"/>
																				 </logic:notPresent>
																				 
																				</td>-->
																			<td class="iCargoTableDataTd" >
																				
																	
																				
																				
																					<logic:present name="innerPostalAdministrationDetailsVO" property="validFrom">
																						<bean:define id="validFrom" name="innerPostalAdministrationDetailsVO" property="validFrom" />
																						<%
																						  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validFrom).toCalendar(),"dd-MMM-yyyy");
																						%>
																						<ibusiness:calendar
																							property="validFrom"
																							componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																							type="image"
																							id="validFrom"
																							value="<%=assignedLocalDate%>" indexId="rowCount" readonly="true" />
																					</logic:present>
																					<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validFrom">
																						<ibusiness:calendar
																							property="validFrom"
																							componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																							type="image"
																							id="validFrom"
																							value= "" indexId="rowCount"/>
																					</logic:notPresent>
																				
																				
																					
																				
																			</td>
																			<td class="iCargoTableDataTd" >
																				
																					<logic:present name="innerPostalAdministrationDetailsVO" property="validTo">
																						<bean:define id="validTo" name="innerPostalAdministrationDetailsVO" property="validTo" />
																						<%
																						  String assignedLocalToDate = TimeConvertor.toStringFormat(((LocalDate)validTo).toCalendar(),"dd-MMM-yyyy");
																						%>
																						<ibusiness:calendar
																							property="validTo"
																							componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																							type="image"
																							id="validTo"
																							indexId="rowCount"
																							value="<%=assignedLocalToDate%>" readonly="true"/>
																					</logic:present>
																					<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validTo">
																						<ibusiness:calendar
																							property="validTo"
																							componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																							type="image"
																							id="validTo"
																							indexId="rowCount"
																							value= "" />
																					</logic:notPresent>
																					<logic:present name="innerPostalAdministrationDetailsVO" property="sernum">
																						<bean:define id="sernum" name="innerPostalAdministrationDetailsVO" property="sernum" />
																						<% String serialnumber=sernum.toString(); %>
																						<ihtml:hidden property="bilSerNum" value="<%=serialnumber%>"/>
																					</logic:present>
																				
																			</td>
																		</tr>
																	</logic:notEqual>
																</logic:iterate>
															</logic:present>
														<%} %>
													</logic:iterate>
												</logic:present>
											<!-- template row starts-->
												<bean:define id="templateRowCount" value="0"/>
												<tr template="true" id="billingRow" style="display:none">
													<td class="iCargoTableDataTd" style="text-align:center">
														<input type="checkbox" name="billCheck" onclick="toggleTableHeaderCheckbox('billCheck',this.form.billChkAll)" />
														<ihtml:hidden property="bilOpFlag" value="NOOP"/>
													</td>
													<td class="iCargoTableDataTd" >
														<ihtml:select indexId="templateRowCount" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGSOURCE" name="form" property="billingSource">
														<logic:present name="OneTimeValues">
															<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
															<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<logic:equal name="parameterCode" value="mailtracking.defaults.billingsource">
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:present name="parameterValue" property="fieldValue">
																			<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																			<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
																		</logic:present>
																	</logic:iterate>
																</logic:equal>
															</logic:iterate>
														</logic:present>
														</ihtml:select>
													</td>
													<td class="iCargoTableDataTd">																		
														<ihtml:text  indexId="templateRowCount" property="billingFrequency" name="form" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_BILLINGFREQUENCY"  value=""/>
														<div class="lovImgTbl valignT"><img height="16" width="16" id="billingPeriodLOV"
					src="<%=request.getContextPath()%>/images/lov.png"
					onclick="invokeBillingPeriodLOV(this);" /></div>
														
													</td>
													<!--	<td class="iCargoTableDataTd" >
														
														<!-- <input type="checkbox" name="billingDtlVO" property="profInv"/>
														<input type="checkbox" name="profInv"  />
														<ihtml:hidden property="profInv" value=""/>
														
													</td>	-->										
													<td class="iCargoTableDataTd" >
														
															<ibusiness:calendar
																indexId="templateRowCount"
																property="validFrom"
																componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																type="image"
																id="validFrom"
																value=""/>
														
													</td>
													<td class="iCargoTableDataTd" >
														
															<ibusiness:calendar
																indexId="templateRowCount"
																property="validTo"
																componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																type="image"
																id="validTo"
																value=""/>

													</td>
												<!-- template row ends-->
												</tr>
											</tbody>
										</table>
								</div>
							
						</div>
				</fieldset>
			  </div>	
			</div>
			<div class="ic-col-45">
			  <div class="ic-round-border">
				<fieldset class="ic-field-set">
					<legend><common:message key="mailtracking.defaults.pamaster.lbl.settlementinfo" /></legend>
					<div class="ic-button-container">
					<div class="ic-row ic-right">
						<a id="addSetLink" name="addSetLink" class="iCargoLink" href="#" value="add">
							<common:message key="mailtracking.defaults.pamaster.lbl.add" />
						</a>
						|
						<a id="deleteSetLink" name="deleteSetLink" class="iCargoLink" href="#" value="delete">
							<common:message key="mailtracking.defaults.pamaster.lbl.delete"/>
						</a>
					</div>
					</div>
					<div class="ic-row"> 
						<div class="tableContainer" id="setdiv" style="height:120px;">
							<table  class="fixed-header-table ic-pad-3">
								<thead>
									<tr >
										<td class="iCargoTableHeaderLabel" width="4%"><input type="checkbox" name="setChkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.setChkAll,this.form.setCheck)"  /> </td>
										<td class="iCargoTableHeaderLabel" width="30%"> <common:message key="mailtracking.defaults.pamaster.lbl.settlementcurrency"/></td>
										<td class="iCargoTableHeaderLabel" width="30%"><common:message key="mailtracking.defaults.pamaster.lbl.validfrm" /></td>
										<td class="iCargoTableHeaderLabel" width="36%"><common:message key="mailtracking.defaults.pamaster.lbl.validto"/></td>
									</tr>
							    </thead>
								<tbody id="setTableBody">
									<logic:present name="postalAdministrationDetailsVOs" >
										<bean:define id="postalAdministrationDetailsVOs" name="postalAdministrationDetailsVOs" type="java.util.HashMap" />
										<logic:iterate id="postalAdministrationDetailsVO" name="postalAdministrationDetailsVOs"  indexId="rowCount">
										<logic:present name="postalAdministrationDetailsVO" property="key">
											<bean:define id="keyValue" name="postalAdministrationDetailsVO" property="key" type="java.lang.String" />
											<%
												keyParameter=keyValue;
											%>
									</logic:present>
									<%
										if(keyParameter.equals("STLINFO")){
									%>
									<logic:present name="postalAdministrationDetailsVO" property="value">
										<bean:define id="innerPostalAdministrationDetailsVOs" name="postalAdministrationDetailsVO" property="value" type="java.util.Collection" />
										<logic:iterate id="innerPostalAdministrationDetailsVO" name="innerPostalAdministrationDetailsVOs"   >
											<logic:equal name="innerPostalAdministrationDetailsVO" property="operationFlag" value="D">
												<ihtml:hidden property="setCheck" name="form"/>
												<ihtml:hidden property="settlementCurrencyCode" name="form"/>
												<ihtml:hidden property="validSetFrom" name="form"/>
												<ihtml:hidden property="validSetTo" name="form"/>
												<ihtml:hidden property="seSerNum" name="form"/>
											</logic:equal>
											<logic:notEqual name="innerPostalAdministrationDetailsVO" property="operationFlag" value="D">
												
												<tr>
													<td class="iCargoTableDataTd" style="text-align:center">
														<input type="checkbox" name="setCheck" property="setCheck" value="<%=String.valueOf(rowCount)%>" onclick="toggleTableHeaderCheckbox('setCheck',this.form.setChkAll)"/>
													     <ihtml:hidden name="form" property="stlOpFlag" value="N"/>
													</td>
													<td class="iCargoTableDataTd" >
													
													
													
														<logic:present name="innerPostalAdministrationDetailsVO" property="settlementCurrencyCode">
															<ihtml:text indexId="rowCount" property="settlementCurrencyCode" name="innerPostalAdministrationDetailsVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SETTLEMENTCURRENCY"  maxlength="3" readonly="true"/>
														</logic:present>
														<logic:notPresent name="innerPostalAdministrationDetailsVO" property="settlementCurrencyCode">
															<ihtml:text indexId="rowCount" property="settlementCurrencyCode" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SETTLEMENTCURRENCY" maxlength="3" value=""/>
															<!--img src="<%=request.getContextPath()%>/images/lov.gif" id="currencyLOV" height="16" width="16"
															onClick="displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.settlementCurrencyCode.value,'settlementCurrencyCode','1','settlementCurrencyCode','',<%=String.valueOf(rowCount)%>)"/-->
															</logic:notPresent>
															<div class="lovImgTbl valignT"><img name="currencyLOV" id="currencyLOV<%=rowCount%>" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16" /></div>
														
													</td>
													<td class="iCargoTableDataTd">
														
															<logic:present name="innerPostalAdministrationDetailsVO" property="validFrom">
																<bean:define id="validFrom" name="innerPostalAdministrationDetailsVO" property="validFrom" />
																<%
																  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validFrom).toCalendar(),"dd-MMM-yyyy");
																%>
																<ibusiness:calendar
																	property="validSetFrom"
																	componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																	type="image"
																	id="validSetFrom"
																	value="<%=assignedLocalDate%>" indexId="rowCount" readonly="true"/>
															</logic:present>
															<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validFrom">
																<ibusiness:calendar
																	property="validSetFrm"
																	componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																	type="image"
																	id="validSetFrm"
																	value="" indexId="rowCount" disabled="true"/>
															</logic:notPresent>
														
													</td>
													<td class="iCargoTableDataTd">
														
															<logic:present name="innerPostalAdministrationDetailsVO" property="validTo">
																<bean:define id="validTo" name="innerPostalAdministrationDetailsVO" property="validTo" />
																<%
																  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validTo).toCalendar(),"dd-MMM-yyyy");
																%>
																<ibusiness:calendar
																	property="validSetTo"
																	componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																	type="image"
																	id="validSetTo"
																	indexId="rowCount"
																	value="<%=assignedLocalDate%>"/>
															</logic:present>
															<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validTo">
																<ibusiness:calendar
																	property="validSetTo"
																	componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																	type="image"
																	id="validSetTo"
																	indexId="rowCount"
																	value=""/>
															</logic:notPresent>
															<logic:present name="innerPostalAdministrationDetailsVO" property="sernum">
																<bean:define id="serialnum" name="innerPostalAdministrationDetailsVO" property="sernum" />
																<% String serialnumber=serialnum.toString();%>
																<ihtml:hidden property="seSerNum" value="<%=serialnumber%>"/>
															</logic:present>
														
													 </td>
												</tr>
											</logic:notEqual>
										</logic:iterate>
									</logic:present>
									<%} %>
								</logic:iterate>
							</logic:present>
						
					<!-- template row starts-->
						<bean:define id="templateRowCount" value="0"/>
						<tr template="true" id="settlementRow" style="display:none">
							<td class="iCargoTableDataTd" style="text-align:center">
								<input type="checkbox" name="setCheck" onclick="toggleTableHeaderCheckbox('setCheck',this.form.setChkAll)" >
								<ihtml:hidden name="form" property="stlOpFlag" value="NOOP"/>
							</td>
							<td class="iCargoTableDataTd" >
								<ihtml:text  indexId="templateRowCount" property="settlementCurrencyCode" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SETTLEMENTCURRENCY"  maxlength="3" value=""/>
								<div class="lovImgTbl valignT"><img name="currencyLOV" id="currencyLOV" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16" /></div>
							</td>
							<td class="iCargoTableDataTd">
								
									<ibusiness:calendar
										indexId="templateRowCount"
										property="validSetFrom"
										componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
										type="image"
										id="validSetFrom"
										value=""/>
								
							</td>
							<td class="iCargoTableDataTd">
								
									<ibusiness:calendar
										indexId="templateRowCount"
										property="validSetTo"
										componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
										type="image"
										id="validSetTo"
										value=""/>
								
							</td>
						<!-- template row ends-->
					</tr>
					</tbody>
							</table>
						</div>
						<div class="ic-row">
						<logic:present name="paVO" >
						<div class="ic-row  ic-center ">
						<ihtml:radio name="paVO" property="settlementLevel" onclick="selectRadioButton();" value="M" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY"/>
						<common:message key="mailtracking.defaults.pamaster.lbl.mailbag" />
						<ihtml:radio name="paVO" property="settlementLevel" onclick="selectRadioButton();" value="V" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY"/>
						<common:message key="mailtracking.defaults.pamaster.lbl.invoice" />
					</div>
						<div class="ic-row">
						</div>
						<div class="ic-row ">
							<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.tolerance" />
						<ihtml:text  property="tolerancePercent" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"  />
						</div>
							<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.tolerancevalue" />
						<ihtml:text  property="toleranceValue" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"   />
						</div>
							<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.maxvalue" />
						<ihtml:text  property="maxValue" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"   />
						</div>
						</div>
						</logic:present>
						<logic:notPresent name="paVO" >
						<div class="ic-row ic-center">
						<ihtml:radio  property="settlementLevel" onclick="selectRadioButton();" value="M" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY"/>
						<common:message key="mailtracking.defaults.pamaster.lbl.mailbag" />
						
						<ihtml:radio  property="settlementLevel" onclick="selectRadioButton();" value="V" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY"/>
						<common:message key="mailtracking.defaults.pamaster.lbl.invoice" />
						</div>
						<div class="ic-row">
						</div>
						<div class="ic-row ">
						<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.tolerance" />
						<ihtml:text  property="tolerancePercent" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"   value=""/>
						</div>
						<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.tolerancevalue" />
						<ihtml:text  property="toleranceValue" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"   value=""/>
						</div>
						<div class="ic-col-30">
						<common:message key="mailtracking.defaults.pamaster.maxvalue" />
						<ihtml:text  property="maxValue" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS"   value=""/>
						</div>
						</div>
						</logic:notPresent>
						</div>
					</div>
				</fieldset>
			</div>
		  </div>	
		</div>
	
