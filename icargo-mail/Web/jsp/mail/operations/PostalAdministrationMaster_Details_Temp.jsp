<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>	
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.HashMap"%>

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
					
					<bean:define id="templateRowCount" value="0"/>
						<tr template="true" id="otherRow" style="display:none">
							<td  class="iCargoTableDataTd" >
								<input type="checkbox" name="invCheck">
								<ihtml:hidden property="invOpFlag" value="NOOP"/>
							</td>
							<td class="iCargoTableDataTd">																		
								<ihtml:select id="combo" indexId="templateRowCount" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE" name="form" style="width:140px" property="parCode" onchange="changeValue(this,value)" >	  <!-- Modified by A-8164 for ICRD-249119   -->
									<logic:present name="OneTimeValues">
									<html:option value=""><common:message key="combo.select"/></html:option>		<!-- Added by A-8164 for ICRD-249119   -->
									<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.parcod">
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
							<td>
							
															<ihtml:text id="parValue" property="parameterValue" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VALUE" maxlength="40" value="" style="text-transform:uppercase;width:100px;"/><span name="dash" style="visibility:hidden">-</span>
																																																														<!-- modified by A-8164 for ICRD-249119   -->
															<!-- modified by A-8236 for ICRD-262883   -->																																															
															<ihtml:text id="partyIdr" property="partyIdentifier" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PARTYID" style="visibility:hidden;width: 0px" maxlength="10" />	
															<ihtml:select id="combo" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_CODE"   property="handoverMailTypValue" style="visibility:hidden;width: 0px">	  <!-- Modified by A-8164 for ICRD-249119   -->
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
																																															
							</td>
							<td class="iCargoTableDataTd" >
								
									<ibusiness:calendar
											indexId="templateRowCount"
											property="validInvFrom"
											componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDFROM"
											type="image"
											id="validInvFrom"
											value=""/>
								
							</td>
							<td class="iCargoTableDataTd" >
								
									<ibusiness:calendar
											indexId="templateRowCount"
											property="validInvTo"
											componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_VALIDTO"
											type="image"
											id="validInvTo"
											value=""/>
								
							</td>
							<td><ihtml:text property="detailedRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_REMARKS"  value="" maxlength="50" /></td>
							
						</tr>