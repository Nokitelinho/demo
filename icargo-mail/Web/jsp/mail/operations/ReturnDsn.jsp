<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: ReturnDsn.jsp
* Date				: 21-July-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>



<html:html>

<head> 
		
			
	
	<title><common:message bundle="returnDsnResources" key="mailtracking.defaults.returndsn.lbl.title" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/ReturnDsn_Script.jsp" />
</head>

<body id="bodyStyle">
	
	
	<div class="iCargoPopUpContent" >
		<ihtml:form action="/mailtracking.defaults.returndsn.screenload.do" styleClass="ic-main-form">

			<bean:define id="form"
				name="ReturnDsnForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm"
				toScope="page"
				scope="request"/>

			<business:sessionBean id="damageCodes"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.returndsn"
					  method="get"
					  attribute="oneTimeDamageCodes" />

			<business:sessionBean id="damagedDsnVOs"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.returndsn"
					  method="get"
					  attribute="damagedDsnVOs" />

			<business:sessionBean id="postalAdministrationVOs"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.returndsn"
					  method="get"
					  attribute="postalAdministrationVOs" />
					  
			<ihtml:hidden property="actionStatusFlag" />
			<ihtml:hidden property="screenStatusFlag" />
			<ihtml:hidden property="fromScreen" />
			<ihtml:hidden property="selectedDsns" />
			<ihtml:hidden property="currentPage" />
			<ihtml:hidden property="displayPage" />
			<ihtml:hidden property="lastPage" />
			<ihtml:hidden property="dmgWeight" />
			<ihtml:hidden property="dmgNOB" />

			<div class="ic-content-main">
				<div class="ic-head-container">
					<div class="ic-row">
						<span class="ic-page-title ic-display-none"><common:message key="mailtracking.defaults.returndsn.lbl.pagetitle" /></span>
					</div>
					<div class="ic-button-container ">
						<common:popuppaginationtag pageURL="javascript:submitDamagePage('totalRecords','displayPage')" linkStyleClass="iCargoResultsLabel"
							disabledLinkStyleClass="iCargoResultsLabel" displayPage="<%=String.valueOf(form.getDisplayPage())%>" totalRecords="<%=String.valueOf(form.getLastPage())%>"/>
					</div>	
					<div class="ic-row ic-border">
						<div class="ic-input ic-split-33">
							<label><common:message key="mailtracking.defaults.returndsn.lbl.dsn" /></label>
							<ihtml:text property="dsn" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DSN" maxlength="4" tabindex="1" readonly="true"/>
						</div>
						<div class="ic-input ic-split-33">
							<label><common:message key="mailtracking.defaults.returndsn.lbl.ooe" /></label>
							<ihtml:text property="originOE" readonly="true" maxlength="6" tabindex="1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_ORIGINOE" />					
						</div>
						<div class="ic-input ic-split-33">
							<label><common:message key="mailtracking.defaults.returndsn.lbl.doe" /></label>
							<ihtml:text property="destnOE" readonly="true" maxlength="6" tabindex="1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DESTNOE" />
						</div>
						<div class="ic-input ic-split-33">
							<label><common:message key="mailtracking.defaults.returndsn.lbl.mailclass" /></label>
							<ihtml:text property="mailClass" readonly="true" maxlength="2" tabindex="1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_SC" />
						</div>
						<div class="ic-input ic-split-33">
							<label><common:message key="mailtracking.defaults.returndsn.lbl.year" /></label>
							<ihtml:text property="year" readonly="true" maxlength="1" tabindex="1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_YEAR" />	
						</div>
					</div>
					</div>
					<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-button-container">
							<a id="addLink" name="addLink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.returndsn.lbl.addlink" /></a> |
							<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.returndsn.lbl.deletelink" /></a>
						</div>
					</div>
					<div class="ic-row ic-border">
						<div class="tableContainer" id="div1"  style="height:137px;">
							<table class="fixed-header-table">
								<thead>
									<tr >
									  <td width="4%"  class="iCargoTableHeaderLabel"><input type="checkbox" name="returnCheckAll" value="checkbox"></td>
									  <td width="21%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.damagecode" /></td>
									  <td width="11%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.damagedbags" /></td>
									  <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.damagedwt" /></td>
									  <td width="11%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.returnedbags" /></td>
									  <td width="14%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.returnedwt" /></td>
									  <td width="24%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returndsn.lbl.remarks" /></td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="damagedDsnVOs">
										<bean:define id="dsnVOs" name="damagedDsnVOs" toScope="page"/>
										<% int rowid = 1; %>
										<logic:iterate id="dsnvo" name="dsnVOs" type="com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO">
											<logic:equal name="form" property="currentPage" value="<%= String.valueOf(rowid) %>">
												<logic:present name="dsnvo" property="damagedDsnDetailVOs">
													<bean:define id="dsnDetailVOs" name="dsnvo" property="damagedDsnDetailVOs" toScope="page"/>
														<logic:iterate id="dsndetailvo" name="dsnDetailVOs" indexId="index" type="com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO">
															<logic:present name="dsndetailvo" property="operationFlag">
																<bean:define id="operFlag" name="dsndetailvo" property="operationFlag" toScope="page"/>
																	<logic:notEqual name="dsndetailvo" property="operationFlag" value="D">
																		<tr>
																			<td class="iCargoTableDataTd"><div><input type="checkbox" name="returnSubCheck" value="<%= index.toString() %>"></div></td>
																			  <td class="iCargoTableDataTd">
																					<logic:present name="dsndetailvo" property="damageCode">
																						<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGECODE" tabindex="1" value="<%= dsndetailvo.getDamageCode() %>">
																							<logic:present name="damageCodes">
																								<bean:define id="damagecodes" name="damageCodes" toScope="page"/>
																								<logic:iterate id="onetmvo" name="damagecodes">
																									<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																									<bean:define id="value" name="onetimevo" property="fieldValue"/>
																									<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																									<html:option value="<%= value.toString() %>"><%= desc %></html:option>
																								</logic:iterate>
																							</logic:present>
																						</ihtml:select>
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="damageCode">
																						<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGECODE" tabindex="1" value="">
																							<logic:present name="damageCodes">
																							<bean:define id="damagecodes" name="damageCodes" toScope="page"/>

																								<logic:iterate id="onetmvo" name="damagecodes">
																									<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																									<bean:define id="value" name="onetimevo" property="fieldValue"/>
																									<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

																									<html:option value="<%= value.toString() %>"><%= desc %></html:option>

																								</logic:iterate>
																							</logic:present>
																						</ihtml:select>
																					</logic:notPresent>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="dsndetailvo" property="latestDamagedBags">
																						<ihtml:text property="damagedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_BAGS" maxlength="10" tabindex="1" value="<%= String.valueOf(dsndetailvo.getLatestDamagedBags()) %>"
																								onblur="doCheck(this);" />
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="latestDamagedBags">
																						<ihtml:text property="damagedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_BAGS" maxlength="10" tabindex="1" value=""
																								onblur="doCheck(this);" />
																					</logic:notPresent>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="dsndetailvo" property="latestDamagedWeight">
																						<bean:define id="latestDamagedWeight" name="dsndetailvo" property="latestDamagedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																							<% request.setAttribute("sampleStdWt",latestDamagedWeight); %>
																							<ibusiness:unitdef id="damagedWeight" unitTxtName="damagedWeight" label=""  unitReq = "false" dataName="sampleStdWt" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																							unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																							unitValue="<%=String.valueOf(latestDamagedWeight.getDisplayValue())%>" style="width:70px" 
																							indexId="index" styleId="damagedWeight" tabindex="1" />
																								
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="latestDamagedWeight">
																						<bean:define id="latestDamagedWeight" name="dsndetailvo" property="latestDamagedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																							<% request.setAttribute("sampleStdWt",latestDamagedWeight); %>
																							<ibusiness:unitdef id="damagedWeight" unitTxtName="damagedWeight" label=""  unitReq = "false" dataName="sampleStdWt" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																								unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																								unitValue="0.0" style="width:70px" 
																								indexId="index" styleId="damagedWeight" tabindex="1"/>
																						
																					</logic:notPresent>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="dsndetailvo" property="latestReturnedBags">
																						<ihtml:text property="returnedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNBAGS" maxlength="10" tabindex="1" value="<%= String.valueOf(dsndetailvo.getLatestReturnedBags()) %>"
																								onblur="doCheck(this);"/>
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="latestReturnedBags">
																						<ihtml:text property="returnedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNBAGS" maxlength="10" tabindex="1" value=""
																								onblur="doCheck(this);" />
																					</logic:notPresent>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="dsndetailvo" property="latestReturnedWeight">
																						<bean:define id="latestReturnedWeight" name="dsndetailvo" property="latestReturnedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																						
																							<% request.setAttribute("sampleStdWt",latestReturnedWeight); %>
																							<ibusiness:unitdef id="returnedWeight" unitTxtName="returnedWeight" label=""  unitReq = "false" dataName="sampleStdWt" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNWT"
																								unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																								unitValue="<%=String.valueOf(latestReturnedWeight.getDisplayValue())%>" style="width:70px" 
																								indexId="index" styleId="returnedWeight" tabindex="1"/>
																						
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="latestReturnedWeight">
																						<bean:define id="latestReturnedWeight" name="dsndetailvo" property="latestReturnedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																							<% request.setAttribute("sampleStdWt",latestReturnedWeight); %>
																							<ibusiness:unitdef 
																							id="returnedWeight"  
																							unitTxtName="returnedWeight" 
																							label=""  unitReq = "false" dataName="sampleStdWt" 									componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNWT"  
																								unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																								unitValue="0.0" style="width:70px" 
																								indexId="index"  styleId="returnedWeight" 
																								tabindex="1"/>
																							
																					</logic:notPresent>
																				</td>
																				<td class="iCargoTableDataTd"> 
																					<logic:present name="dsndetailvo" property="remarks">
																						<ihtml:text property="damageRemarks"    componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGEREMARKS"  style="width:135px"  maxlength="50"  tabindex="1"   value="<%= dsndetailvo.getRemarks()  %>"/>
																					</logic:present>
																					<logic:notPresent name="dsndetailvo" property="remarks">
																						<ihtml:text 
																						property="damageRemarks"    componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGEREMARKS"     
																						style="width:135px"   
																						maxlength="50"   
																						tabindex="1"   value=""/>
																					</logic:notPresent>
																				</td>
																			</tr>
																		</logic:notEqual>
																		<logic:equal name="dsndetailvo" property="operationFlag" value="D">

																			<ihtml:hidden property="damageCode" value="<%= String.valueOf(dsndetailvo.getDamageCode()) %>"/>
																			<ihtml:hidden property="damagedBags" value="<%= String.valueOf(dsndetailvo.getDamagedBags()) %>"/>
																			<bean:define id="damagedWeight" name="dsndetailvo" property="damagedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																			<ihtml:hidden property="damagedWeight" value="<%= String.valueOf(damagedWeight.getDisplayValue()) %>"/>
																			<ihtml:hidden property="returnedBags" value="<%= String.valueOf(dsndetailvo.getReturnedBags()) %>"/>
																			<bean:define id="returnedWeight" name="dsndetailvo" property="returnedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																			<ihtml:hidden property="returnedWeight" value="<%= String.valueOf(returnedWeight.getDisplayValue()) %>"/>
																			<ihtml:hidden property="damageRemarks" value="<%= String.valueOf(dsndetailvo.getRemarks()) %>"/>
																		</logic:equal>
																		<ihtml:hidden property="operationFlag" value="<%= String.valueOf(operFlag) %>"/>
																	</logic:present>
																	<logic:notPresent name="dsndetailvo" property="operationFlag">
																		<tr>
																		  <td class="iCargoTableDataTd"><div><input type="checkbox" name="returnSubCheck" value="<%= index.toString() %>"></div></td>
																		  <td class="iCargoTableDataTd">
																			<logic:present name="dsndetailvo" property="damageCode">
																				<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGECODE" tabindex="1" value="<%= dsndetailvo.getDamageCode() %>">
																					<logic:present name="damageCodes">
																					<bean:define id="damagecodes" name="damageCodes" toScope="page"/>
																						<logic:iterate id="onetmvo" name="damagecodes">
																							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																							<bean:define id="value" name="onetimevo" property="fieldValue"/>
																							<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																							<html:option value="<%= value.toString() %>"><%= desc %></html:option>
																						</logic:iterate>
																					</logic:present>
																				</ihtml:select>
																			</logic:present>
																			<logic:notPresent name="dsndetailvo" property="damageCode">
																				<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGECODE" tabindex="1" value="">
																					<logic:present name="damageCodes">
																						<bean:define id="damagecodes" name="damageCodes" toScope="page"/>
																							<logic:iterate id="onetmvo" name="damagecodes">
																								<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																								<bean:define id="value" name="onetimevo" property="fieldValue"/>
																								<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																							<html:option value="<%= value.toString() %>"><%= desc %></html:option>
																						</logic:iterate>
																					</logic:present>
																				</ihtml:select>
																			</logic:notPresent>
																		  </td>
																		  <td class="iCargoTableDataTd">
																				<logic:present name="dsndetailvo" property="latestDamagedBags">
																					<ihtml:text property="damagedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_BAGS" maxlength="10" tabindex="1" value="<%= String.valueOf(dsndetailvo.getLatestDamagedBags()) %>"
																						onblur="doCheck(this);" style="text-align:right"/>
																				</logic:present>
																				<logic:notPresent name="dsndetailvo" property="latestDamagedBags">
																					<ihtml:text property="damagedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_BAGS" maxlength="10" tabindex="1" value=""
																							onblur="doCheck(this);" style="text-align:right"/>
																				</logic:notPresent>
																		  </td>
																		  <td class="iCargoTableDataTd">
																				<logic:present name="dsndetailvo" property="latestDamagedWeight">
																					<bean:define id="latestDamagedWeight" name="dsndetailvo" property="latestDamagedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																						<% request.setAttribute("sampleStdWt",latestDamagedWeight); %>
																						<ibusiness:unitdef id="damagedWeight" unitTxtName="damagedWeight" label=""  unitReq = "false" dataName="sampleStdWt" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																							unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																							unitValue="<%=String.valueOf(latestDamagedWeight.getDisplayValue())%>" style="text-align:right;background :'<%=color%>'"
																							indexId="index" styleId="damagedWeight" tabindex="1"/>
																					
																				</logic:present>
																				<logic:notPresent name="dsndetailvo" property="latestDamagedWeight">
																				<bean:define id="latestDamagedWeight" name="dsndetailvo" property="latestDamagedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																						<% request.setAttribute("sampleStdWt",latestDamagedWeight); %>
																					<ibusiness:unitdef id="damagedWeight" unitTxtName="damagedWeight" label=""  unitReq = "false" dataName="sampleStdWt" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" 
																						unitValue="0.0" style="text-align:right;background :'<%=color%>'"
																						indexId="index" styleId="damagedWeight" tabindex="1"/>
																						
																			</logic:notPresent>
																		  </td>
																		<td class="iCargoTableDataTd">
																				<logic:present name="dsndetailvo" property="latestReturnedBags">
																					<ihtml:text property="returnedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNBAGS" maxlength="10" tabindex="1" value="<%= String.valueOf(dsndetailvo.getLatestReturnedBags()) %>"
																						onblur="doCheck(this);" style="text-align:right"/>
																				</logic:present>
																				<logic:notPresent name="dsndetailvo" property="latestReturnedBags">
																					<ihtml:text property="returnedBags" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_RETURNBAGS" maxlength="10" tabindex="1" value=""
																							onblur="doCheck(this);" style="text-align:right"/>
																				</logic:notPresent>
																		</td>
																		<td class="iCargoTableDataTd">
																			<logic:present name="dsndetailvo" property="latestReturnedWeight">
																				<bean:define id="latestReturnedWeight" name="dsndetailvo" property="latestReturnedWeight" toScope="page"
																				type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																					
																						<% request.setAttribute("sampleStdWt",latestReturnedWeight); %>
																						<ibusiness:unitdef id="returnedWeight" unitTxtName="returnedWeight" label=""  unitReq = "false" dataName="sampleStdWt"
																							unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																							unitValue="<%=String.valueOf(latestReturnedWeight.getDisplayValue())%>" style="text-align:right;background :'<%=color%>'"
																							indexId="index" styleId="returnedWeight" tabindex="1"/>
																						
																				</logic:present>
																				<logic:notPresent name="dsndetailvo" property="latestReturnedWeight">
																				<bean:define id="latestReturnedWeight" name="dsndetailvo" property="latestReturnedWeight" toScope="page"
																				type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
																						<% request.setAttribute("sampleStdWt",latestReturnedWeight); %>
																						<ibusiness:unitdef id="returnedWeight" unitTxtName="returnedWeight" label=""  unitReq = "false" dataName="sampleStdWt"
																							unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_WT"
																							unitValue="0.0" style="text-align:right;background :'<%=color%>'"
																							indexId="index" styleId="returnedWeight" tabindex="1"/>
																				</logic:notPresent>
																			</td>
																			<td class="iCargoTableDataTd"><center>
																				<logic:present name="dsndetailvo" property="remarks">
																					<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGEREMARKS" maxlength="50" tabindex="1" value="<%= dsndetailvo.getRemarks() %>"/>
																				</logic:present>
																				<logic:notPresent name="dsndetailvo" property="remarks">
																					<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGEREMARKS" maxlength="50" tabindex="1" value=""/>
																				</logic:notPresent>
																				</center>
																			</td>
																		</tr>
																	<ihtml:hidden property="operationFlag" value="N"/>
																</logic:notPresent>
															</logic:iterate>
														</logic:present>
													</logic:equal>
												<% rowid++; %>
											</logic:iterate>
										</logic:present>
									</tbody>
								</table>
							</div>
						</div>
						<div class="ic-row">
							<common:message key="mailtracking.defaults.returndsn.lbl.pa" />
							<ihtml:select property="postalAdmin" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNDSN_DAMAGECODE" tabindex="1">
								<logic:present name="postalAdministrationVOs">
									<bean:define id="postalAdministrationVOs" name="postalAdministrationVOs" type="java.util.Collection" toScope="page"/>
									<logic:iterate id="postalAdminVO" name="postalAdministrationVOs">
										<bean:define id="value" name="postalAdminVO" property="paCode"/>
										<bean:define id="desc" name="postalAdminVO" property="paName"/>
										<html:option value="<%= value.toString() %>"><%= desc %></html:option>
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</div>
						</div>
						<div class="ic-foot-container">
							<div class="ic-row  ic-round-border">
								<div class="ic-button-container">
									   <ihtml:nbutton property="btSave" tabindex="1" componentID="BUT_MAILTRACKING_DEFAULTS_RETURNDSN_SAVE">
										<common:message key="mailtracking.defaults.returndsn.btn.save" />
									  </ihtml:nbutton>

									  <ihtml:nbutton property="btClose" tabindex="1" componentID="BUT_MAILTRACKING_DEFAULTS_RETURNDSN_CLOSE">
										<common:message key="mailtracking.defaults.returndsn.btn.close" />
									  </ihtml:nbutton>
								</div>
							</div>
						</div>	
					</div>
			</ihtml:form>		
		</div>	
   			
		  
	</body>

</html:html>
