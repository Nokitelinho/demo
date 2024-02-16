<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  ListCCA_Filter.jsp
* Date					: 16-Aug-2018
* Author(s)				: A-8464
* Comment				: Splitted jsp from ListCCA.jsp for ICRD-278013
*************************************************************************/
 --%>
 
 <%@ include file="/jsp/includes/tlds.jsp"%>
 
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"%>
 
 <bean:define id="form"
		 name="ListCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"
		 toScope="page" />

 <business:sessionBean
			id="LIST_FILTERVO"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.listcca"
			method="get"
		attribute="CCAFilterVO" />
		
 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.listcca"
	 	  method="get"
	  attribute="OneTimeVOs" />


									<div class="ic-col-65">
										<fieldset class="ic-field-set" >
											<legend class="iCargoLegend"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcadetails" /></legend>
												<div class="ic-row ic-label-45">
													<div class="ic-input ic-split-25">
														<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcano" /></label>
																<logic:present name="LIST_FILTERVO" property="ccaRefNumber">
																	<bean:define id="ccaRefNumber" name="LIST_FILTERVO" property="ccaRefNumber"/>
																	<ihtml:text property="ccaNum" value="<%=(String)ccaRefNumber%>" maxlength="11" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCANUM" />
																 <div class="lovImg"> 
																 <img src="<%=request.getContextPath()%>/images/lov.png" id="mcanolov" height="22" width="22" alt="" disabled="true" /></td>
																 </div>
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="ccaRefNumber">
																  <ihtml:text property="ccaNum" maxlength="11" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCANUM" />
																 <div class="lovImg"> 
																 <img src="<%=request.getContextPath()%>/images/lov.png" id="mcanolov" height="22" width="22" alt="" /></td>
																 </div>
																</logic:notPresent>
														</div>

														<div class="ic-input   ic-split-25">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcatype" /></label>
															
															<logic:present name="LIST_FILTERVO" property="ccaType">
															   <ihtml:select name="LIST_FILTERVO"
																styleClass="iCargoMediumComboBox"
																componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCATYPE"
																property="ccaType">
																<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																<logic:present name="OneTimeValues">
																	<logic:iterate id="oneTimeValue" name="OneTimeValues">
																		<bean:define id="parameterCode" name="oneTimeValue"
																			property="key" />
																		<logic:equal name="parameterCode"
																			value="mra.defaults.ccatype">
																			<bean:define id="parameterValues" name="oneTimeValue"
																				property="value" />
																			<logic:iterate id="parameterValue"
																				name="parameterValues"
																				type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:present name="parameterValue"
																					property="fieldValue">
																					<bean:define id="fieldValue" name="parameterValue"
																						property="fieldValue" />
																					<bean:define id="fieldDescription"
																						name="parameterValue" property="fieldDescription" />
																					<ihtml:option
																						value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																						<%=String.valueOf(fieldDescription)%>
																					</ihtml:option>
																				</logic:present>
																			</logic:iterate>
																		</logic:equal>
																	</logic:iterate>
																</logic:present>
																</ihtml:select>
														 </logic:present>
														 <logic:notPresent name="LIST_FILTERVO" property="ccaType">
															   <ihtml:select
																styleClass="iCargoMediumComboBox"
																componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCATYPE"
																property="ccaType">
																<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																<logic:present name="OneTimeValues">
																	<logic:iterate id="oneTimeValue" name="OneTimeValues">
																		<bean:define id="parameterCode" name="oneTimeValue"
																			property="key" />
																		<logic:equal name="parameterCode"
																			value="mra.defaults.ccatype">
																			<bean:define id="parameterValues" name="oneTimeValue"
																				property="value" />
																			<logic:iterate id="parameterValue"
																				name="parameterValues"
																				type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:present name="parameterValue"
																					property="fieldValue">
																					<bean:define id="fieldValue" name="parameterValue"
																						property="fieldValue" />
																					<bean:define id="fieldDescription"
																						name="parameterValue" property="fieldDescription" />
																					<ihtml:option
																						value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																						<%=String.valueOf(fieldDescription)%>
																					</ihtml:option>
																				</logic:present>
																			</logic:iterate>
																		</logic:equal>
																	</logic:iterate>
																</logic:present>
																</ihtml:select>
														  </logic:notPresent>
															</div>
															<div class="ic-input   ic-split-25">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcastatus" /></label>
																	<% String ccaStatusForDisp = ""; %>
																	<logic:present name="LIST_FILTERVO" property="ccaStatus">
																	<bean:define id="ccaStatus" name="LIST_FILTERVO" property="ccaStatus" toScope="page"/>
																		<% ccaStatusForDisp = (String)ccaStatus; %>
																	</logic:present>
																	<logic:notPresent name="LIST_FILTERVO" property="ccaStatus">
																	</logic:notPresent>
																	<ihtml:select
																		styleClass="iCargoMediumComboBox"
																		componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCABILLSTA"
																		property="ccaStatus"
																		value="<%=ccaStatusForDisp%>"
																		tabindex="30">
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																		<logic:present name="OneTimeValues">
																			<logic:iterate id="oneTimeValue" name="OneTimeValues">
																				<bean:define id="parameterCode" name="oneTimeValue"
																					property="key" />
																				<logic:equal name="parameterCode"
																					value="mra.defaults.ccastatus">
																					<bean:define id="parameterValues" name="oneTimeValue"
																						property="value" />
																					<logic:iterate id="parameterValue"
																						name="parameterValues"
																						type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																						<logic:present name="parameterValue"
																							property="fieldValue">
																							<bean:define id="fieldValue" name="parameterValue"
																								property="fieldValue" />
																							<bean:define id="fieldDescription"
																								name="parameterValue" property="fieldDescription" />
																							<ihtml:option
																								value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																								<%=String.valueOf(fieldDescription)%>
																							</ihtml:option>
																						</logic:present>
																					</logic:iterate>
																				</logic:equal>
																			</logic:iterate>
																		</logic:present>
																	</ihtml:select>
																</div>
																<div class="ic-input   ic-split-25">
																<label>
																	<common:message key="mailtracking.mra.gpabilling.listmca.lbl.billingstatus" />
														        </label>
																	<% String billingStatusForDisp = ""; %>
																	<logic:present name="LIST_FILTERVO" property="billingStatus">
																	<bean:define id="billingStatus" name="LIST_FILTERVO" property="billingStatus" toScope="page"/>
																		<% billingStatusForDisp = (String)billingStatus; %>
																	</logic:present>
																	<logic:notPresent name="LIST_FILTERVO" property="billingStatus">
																	</logic:notPresent>
																	<ihtml:select
																		styleClass="iCargoMediumComboBox"
																		componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CCABILLSTA"
																		property="billingStatus"
																		value="<%=billingStatusForDisp%>"
																		tabindex="30">
																		<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																		<logic:present name="OneTimeValues">
																			<logic:iterate id="oneTimeValue" name="OneTimeValues">
																				<bean:define id="parameterCode" name="oneTimeValue"
																					property="key" />
																				<logic:equal name="parameterCode"
																					value="mailtracking.mra.gpabilling.gpabillingstatus">
																					<bean:define id="parameterValues" name="oneTimeValue"
																						property="value" />
																					<logic:iterate id="parameterValue"
																						name="parameterValues"
																						type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																						<logic:present name="parameterValue"
																							property="fieldValue">
																							<bean:define id="fieldValue" name="parameterValue"
																								property="fieldValue" />
																							<bean:define id="fieldDescription"
																								name="parameterValue" property="fieldDescription" />
																							<ihtml:option
																								value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																								<%=String.valueOf(fieldDescription)%>
																							</ihtml:option>
																						</logic:present>
																					</logic:iterate>
																				</logic:equal>
																			</logic:iterate>
																		</logic:present>
																	</ihtml:select>
																</div>
															</div>
										</fieldset>
										</div>
										<div class="ic-col-35">
											<fieldset class="ic-field-set" >
												<legend><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcaissuedate" /></legend>
													<div class="ic-row ic-label-45">
														<div class="ic-input  ic-mandatory ic-split-50">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.from" /></label>

															 <logic:present name="LIST_FILTERVO" property="fromDate">
																<bean:define id="listFilter"
																		 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO"
																			toScope="page" />
																<%
																	String fDate = "";
																   if(listFilter.getFromDate() != null) {
																		fDate = TimeConvertor.toStringFormat(
																					listFilter.getFromDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
																	}
																%>
																<ibusiness:calendar type="image" id="frmDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FROMDATE" property="frmDate"  value="<%=fDate%>" maxlength="11"/>
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="fromDate">
																<ibusiness:calendar id="frmDate" type="image"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FROMDATE"  property="frmDate" value="" maxlength="11"/>
																</logic:notPresent>
														</div>
														<div class="ic-input ic-mandatory ic-split-50">
														<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.to" /></label>
														
														 <logic:present name="LIST_FILTERVO" property="toDate">
															<bean:define id="listFilter"
																	 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO"
																		toScope="page" />
															<%
																String tDate = "";
															   if(listFilter.getToDate() != null) {
																	tDate = TimeConvertor.toStringFormat(
																				listFilter.getToDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
																}
															%>
															<ibusiness:calendar type="image" id="toDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_TODATE" property="toDate"  value="<%=tDate%>" maxlength="11"/>
															</logic:present>
															<logic:notPresent name="LIST_FILTERVO" property="toDate">
															<ibusiness:calendar id="toDate" type="image"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_TODATE"  property="toDate" value="<%= form.getToDate() %>" maxlength="11"/>
															</logic:notPresent>
														</div>
													</div>
											</fieldset>
										</div>