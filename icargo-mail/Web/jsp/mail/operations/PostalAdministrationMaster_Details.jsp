<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>	
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
	  <!--Modified by A-7938 for ICRD-243967-->
		<div class="tableContainer" id="bildiv" style="height:100px;">
			<table class="fixed-header-table ic-pad-3">
				<thead>
					<tr>
						<td class="iCargoTableHeaderLabel" width="3%"><input type="checkbox" name="ChkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.ChkAll,this.form.invCheck)"/></td>
						<td class="iCargoTableHeaderLabel" width="19%"><common:message key="mailtracking.defaults.pamaster.lbl.code" /></td>
						<td class="iCargoTableHeaderLabel" width="22%"><common:message key="mailtracking.defaults.pamaster.lbl.value" /></td>
						<td class="iCargoTableHeaderLabel" width="19%"><common:message key="mailtracking.defaults.pamaster.lbl.validfrm" /></td>
						<td class="iCargoTableHeaderLabel" width="19%"><common:message key="mailtracking.defaults.pamaster.lbl.validto" /></td>
						<td class="iCargoTableHeaderLabel" width="17%"><common:message key="mailtracking.defaults.pamaster.lbl.remarks" /></td>
					</tr>
				</thead>
				<tbody id="otherTableBody">
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
										if(keyParameter.equals("INVINFO")){
									%>
								<logic:present name="postalAdministrationDetailsVO" property="value">
									<bean:define id="innerPostalAdministrationDetailsVOs" name="postalAdministrationDetailsVO" property="value" type="java.util.Collection" />
										<logic:iterate id="innerPostalAdministrationDetailsVO" name="innerPostalAdministrationDetailsVOs" type="com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO" indexId="rowCount" >
												<% request.setAttribute("rowCount",rowCount); %>
											<bean:define id="_innerPostalAdministrationDetailsVO" name="innerPostalAdministrationDetailsVO" toScope="request" />
											<logic:equal name="innerPostalAdministrationDetailsVO" property="operationFlag" value="D">
												<ihtml:hidden property="invCheck" name="form"/>
												<ihtml:hidden property="invSerNum" name="form"/>																						
												<ihtml:hidden property="parameterType" name="form"/>
												<ihtml:hidden property="parameterValue" name="form"/>
												<ihtml:hidden property="detailedRemarks" name="form"/>												
												<ihtml:hidden property="parCode" name="form"/>	
												<ihtml:hidden property="validFrom"/>
												<ihtml:hidden property="validTo"/>
											</logic:equal>
								<!--new jsp-->
								  <jsp:include page="PostalAdministrationMaster_Details_Existing.jsp"/>
													

													
														
														
														
														
													
													
													
															
													
													
													

													
														
														
														
														
													
													
													
															
													
													
											<logic:equal name="innerPostalAdministrationDetailsVO" property="operationFlag" value="I">
											<ihtml:hidden name="form" property="invOpFlag" value="I"/>
													<tr class="iCargoTableDataRow1" >
													<td class="iCargoTableDataTd" style="text-align:center">
														<logic:present  name="innerPostalAdministrationDetailsVO" property="companyCode">
															<input type="checkbox" name="invCheck" value="<%=String.valueOf(rowCount)%>" >
														</logic:present>
													</td>
													<td class="iCargoTableDataTd" >
														<logic:present  name="innerPostalAdministrationDetailsVO" property="parCode">
													
															<bean:define id="parCode" name="innerPostalAdministrationDetailsVO" property="parCode" />													
														<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE"  property="parCode"  indexId="templateRowCount"    value="<%=parCode.toString()%>" style="width:140px" disabled="true">
															<logic:present name="OneTimeValues">
															<html:option value=""><common:message key="combo.select"/></html:option>
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
																	</logic:equal>
																			</logic:iterate >
																</logic:iterate>
															</logic:present>
															</ihtml:select>
														</logic:present>
														<logic:notPresent  name="innerPostalAdministrationDetailsVO" property="parCode">
													 <%System.out.println("inside notPresent");%>
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE"  property="parCode"  indexId="templateRowCount" style="width:140px" disabled="true">
																	<logic:present name="OneTimeValues">
																	<html:option value=""><common:message key="combo.select"/></html:option>
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
														<logic:present  name="innerPostalAdministrationDetailsVO" property="parameterValue">
														<logic:notEqual  name="innerPostalAdministrationDetailsVO" property="parCode" value="HANMALTYP">

															<ihtml:text id="parValue" indexId="templateRowCount" property="parameterValue" name="innerPostalAdministrationDetailsVO" value="<%=innerPostalAdministrationDetailsVO.getParameterValue()%>" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VALUE" style="text-transform:uppercase;width:100px;" maxlength="40" />
															<logic:equal  name="innerPostalAdministrationDetailsVO" property="parCode" value="UPUCOD">
															<span name="dash">-</span><ihtml:text id="partyIdr" indexId="templateRowCount" property="partyIdentifier" name="innerPostalAdministrationDetailsVO" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PARTYID" style="text-transform:uppercase;width:50px;" maxlength="10" />
															</logic:equal>
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE" indexId="templateRowCount"  property="handoverMailTypValue" style="text-transform:uppercase;width:0px;visibility:hidden;" value="<%=innerPostalAdministrationDetailsVO.getParameterValue()%>" >	  <!-- Modified by A-8164 for ICRD-249119   -->
									<logic:present name="OneTimeValues">
									<html:option value=""><common:message key="combo.select"/></html:option>	
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.parcod.handlemaltyp">
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
											</logic:notEqual>		
<logic:equal  name="innerPostalAdministrationDetailsVO" property="parCode" value="HANMALTYP">
															<ihtml:text id="parValue" property="parameterValue" indexId="templateRowCount" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VALUE" maxlength="40" value="" style="text-transform:uppercase;width:0px;visibility:hidden;"/><span name="dash" style="visibility:hidden">-</span>
															<ihtml:text id="partyIdr" property="partyIdentifier" indexId="templateRowCount"  value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PARTYID" style="text-transform:uppercase;width:0px;visibility:hidden;" maxlength="10" />	
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE" indexId="templateRowCount"  property="handoverMailTypValue" style="text-transform:uppercase;width:100px;" value="<%=innerPostalAdministrationDetailsVO.getParameterValue()%>" >	  <!-- Modified by A-8164 for ICRD-249119   --> 
									<logic:present name="OneTimeValues">
									<html:option value=""><common:message key="combo.select"/></html:option>		<!-- Added by A-8164 for ICRD-249119   -->
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.parcod.handlemaltyp">
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
								</logic:equal>											
														</logic:present>
														<logic:notPresent  name="innerPostalAdministrationDetailsVO" property="parameterValue">
														<logic:notEqual  name="innerPostalAdministrationDetailsVO" property="parCode" value="HANMALTYP">
															<ihtml:text id="parValue" indexId="templateRowCount" property="parameterValue" name="innerPostalAdministrationDetailsVO" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VALUE" style="text-transform:uppercase;width:100px;" maxlength="40" />
															<logic:equal  name="innerPostalAdministrationDetailsVO" property="parCode" value="UPUCOD">
															<span name="dash">-</span><ihtml:text id="partyIdr" indexId="templateRowCount" property="partyIdentifier" name="innerPostalAdministrationDetailsVO" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PARTYID" style="text-transform:uppercase;width:50px;" maxlength="10" />
															</logic:equal>
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE" indexId="templateRowCount"   property="handoverMailTypValue" style="text-transform:uppercase;width:100px;visibility:hidden;" >	  <!-- Modified by A-8164 for ICRD-249119   -->
									<logic:present name="OneTimeValues">
									<html:option value=""><common:message key="combo.select"/></html:option>	
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.parcod.handlemaltyp">
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
								</logic:notEqual>
								<logic:equal  name="innerPostalAdministrationDetailsVO" property="parCode" value="HANMALTYP">
															<ihtml:text id="parValue" property="parameterValue" indexId="templateRowCount"  componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VALUE" maxlength="40" value="" style="text-transform:uppercase;width:0px;visibility:hidden;"/><span name="dash" style="visibility:hidden">-</span>
															<ihtml:text id="partyIdr" property="partyIdentifier" indexId="templateRowCount"  value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PARTYID" style="text-transform:uppercase;width:0px;visibility:hidden;" maxlength="10" />	
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE" indexId="templateRowCount"  property="handoverMailTypValue" style="text-transform:uppercase;width:100px;" value="<%=innerPostalAdministrationDetailsVO.getParameterValue()%>" >	  <!-- Modified by A-8164 for ICRD-249119   -->
									<logic:present name="OneTimeValues">
									<html:option value=""><common:message key="combo.select"/></html:option>		<!-- Added by A-8164 for ICRD-249119   -->
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.parcod.handlemaltyp">
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
															</logic:equal>
														</logic:notPresent>
													</td>
													<td class="iCargoTableDataTd" >
														
															<logic:present name="innerPostalAdministrationDetailsVO" property="validFrom">
																<bean:define id="validFrom" name="innerPostalAdministrationDetailsVO" property="validFrom" />
																<%
																  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validFrom).toCalendar(),"dd-MMM-yyyy");
																%>
																<ibusiness:calendar
																		property="validInvFrom"
																		componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																		type="image"
																		id="validInvFrom"
																		value="<%=assignedLocalDate%>" indexId="rowCount" />
															</logic:present>
															<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validFrom">
																<ibusiness:calendar
																			property="validInvFrom"
																			componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
																			type="image"
																			id="validInvFrom"
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
																		property="validInvTo"
																		componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																		type="image"
																		id="validInvTo"
																		indexId="rowCount"
																		value="<%=assignedLocalToDate%>" />
															</logic:present>
															<logic:notPresent name="innerPostalAdministrationDetailsVO" property="validTo">
																<ibusiness:calendar
																		property="validInvTo"
																		componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
																		type="image"
																		id="validInvTo"
																		indexId="rowCount"
																		value= "" />
															</logic:notPresent>
															<logic:present name="innerPostalAdministrationDetailsVO" property="sernum">
																<bean:define id="sernum" name="innerPostalAdministrationDetailsVO" property="sernum" />
																<% String serialnumber=sernum.toString(); %>
																<ihtml:hidden property="invSerNum" value="<%=serialnumber%>"/>
															</logic:present>
														
													</td>
													<td class="iCargoTableDataTd" >
														<logic:present  name="innerPostalAdministrationDetailsVO" property="detailedRemarks">
															<ihtml:text indexId="templateRowCount" property="detailedRemarks" name="innerPostalAdministrationDetailsVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_REMARKS" value="<%=innerPostalAdministrationDetailsVO.getDetailedRemarks()%>" maxlength="50" disabled="true"/>
														</logic:present>
														<logic:notPresent  name="innerPostalAdministrationDetailsVO" property="detailedRemarks">
															<ihtml:text indexId="templateRowCount" property="detailedRemarks" name="innerPostalAdministrationDetailsVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_REMARKS" value="" maxlength="50"  disabled="true"/>
														</logic:notPresent>
													</td>
													</tr>
											</logic:equal>
											<!--update-->
											<!--update-->
										</logic:iterate>
									</logic:present>
								<%} %>
							</logic:iterate>
						</logic:present>




					<!--template row strs-->
                       <jsp:include page="PostalAdministrationMaster_Details_Temp.jsp"/>
							<!-- template row ends-->
					</tbody>
				
			</table>
			</fieldset>
		</div>
	  </div>
	  <!--
	  <div class="ic-button-container">
	  <div class="ic-row ic-right">
	    <a id="addLink" name="addLink" class="iCargoLink" href="#" value="add" onclick="addDetail()">
			<common:message key="mailtracking.defaults.pamaster.lbl.add" />
		</a>
		|
		<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete" onclick="deleteDetail()">
			<common:message key="mailtracking.defaults.pamaster.lbl.delete"/>
		</a>
	  </div>
	  </div>
	  <div class="ic-row">
		
	  </div>
	  -->
	  <div class="ic-row" >
							&nbsp;
	  </div>
	  