<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  MailAcceptance.jsp
* Date          	 :  20-June-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"%>
<%@ page
	import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp"%>



<html:html>
<head>
<%@ include file="/jsp/includes/customcss.jsp" %>
<title><common:message bundle="mailAcceptanceResources"
		key="mailtracking.defaults.mailacceptance.lbl.title" /></title>
<meta name="decorator" content="mainpanel">
<common:include type="script"
	src="/js/mail/operations/MailAcceptance_Script.jsp" />
</head>
<body>
	<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
		toScope="page" scope="request" />

	<business:sessionBean id="flightValidationVOSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailacceptance" method="get"
		attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession"
			name="flightValidationVOSession" toScope="page" />
	</logic:present>

	<business:sessionBean id="mailAcceptanceVOSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailacceptance" method="get"
		attribute="mailAcceptanceVO" />
	<logic:present name="mailAcceptanceVOSession">
		<bean:define id="mailAcceptanceVOSession"
			name="mailAcceptanceVOSession" toScope="page" />
	</logic:present>
	<div id="contentdiv" class="iCargoContent ic-masterbg">
		<ihtml:form
			action="mailtracking.defaults.mailacceptance.screenloadmailacceptance.do">

			<ihtml:hidden property="initialFocus" />
			<ihtml:hidden property="duplicateFlightStatus" />
			<ihtml:hidden property="disableDestnFlag" />
			<ihtml:hidden property="disableSaveFlag" />
			<ihtml:hidden property="uldsSelectedFlag" />
			<ihtml:hidden property="preAdviceFlag" />
			<ihtml:hidden property="fromScreen" />
			<ihtml:hidden property="closeflight" />
			<ihtml:hidden property="closeFlag" />
			<ihtml:hidden property="uldsPopupCloseFlag" />
			<ihtml:hidden property="operationalStatus" />
			<ihtml:hidden property="preassignFlag" />
			<ihtml:hidden property="warningFlag" />
			<ihtml:hidden property="currentDialogOption" />
			<ihtml:hidden property="currentDialogId" />
			<ihtml:hidden property="reassignScreenFlag" />
			<ihtml:hidden property="selCont" />
			<ihtml:hidden property="captureULDDamageFlag" />
			<ihtml:hidden property="existingMailbagFlag" />
			<ihtml:hidden property="displayPageForCardit" />
			<input type="hidden" name="ignoreHiddenCheck" />
			<ihtml:hidden property="disableButtons" />
			<ihtml:hidden property="disableAddModifyDeleteLinks" />
			<ihtml:hidden property="warningOveride" />
			<ihtml:hidden property="tbaTbcWarningFlag" />
			<ihtml:hidden property="duplicateAndTbaTbc" />
			<ihtml:hidden property="saveSuccessFlag" />
			<ihtml:hidden property="disableButtonsForAirport" />
			<ihtml:hidden property="selectedContainer"/><!-- Added by A-7371 for ICRD-133987-->
            <ihtml:hidden property="transferContainerFlag"/>
			

			<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"> <common:message
							key="mailtracking.defaults.mailacceptance.lbl.pagetitle" />
					</span>
					<div class="ic-filter-panel">
					<!--Modified by A-7938 for ICRD-243745-->
						<div class="ic-row ic-border" style="width:1370px;">
							<div class="ic-input ic-split-5 "></div>
							<div class="ic-input ic-split-10 ">
								<label> <common:message
										key="mailtracking.defaults.mailacceptance.lbl.acceptto" />
								</label>
							</div>
							<div class="ic-input ic-split-10 ic_inline_chcekbox">
								<ihtml:radio property="assignToFlight" onclick="selectDiv();"
									value="FLIGHT"
									componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_RADIOFLIGHT" />
								<label> <common:message
										key="mailtracking.defaults.mailacceptance.lbl.flight" />
								</label>
							</div>
							<div class="ic-input ic-split-10 ic_inline_chcekbox">
								<ihtml:radio property="assignToFlight" onclick="selectDiv();"
									value="CARRIER"
									componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_RADIODESTINATION" />
								<label> <common:message
										key="mailtracking.defaults.mailacceptance.lbl.carrier" />
								</label>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input-container ic-round-border">
								<div class="ic-row">
									<div id="FLIGHT">
                                        <div class="ic-col-5"></div>
										<div class="ic-col-10 ic-mandatory ic-label-90 ic-marg-top-5">
											<label><common:message
													key="mailtracking.defaults.mailacceptance.lbl.flightno" /></label>
											<logic:notPresent name="mailAcceptanceVOSession"
												property="flightNumber">
												<ibusiness:flightnumber id="fltNo"
													carrierCodeProperty="flightCarrierCode"
													flightCodeProperty="flightNumber" carriercodevalue=""
													flightcodevalue="" tabindex="1"
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_FLIGHTNO" />
											</logic:notPresent>

											<logic:present name="mailAcceptanceVOSession"
												property="flightCarrierCode">
												<logic:present name="mailAcceptanceVOSession"
													property="flightNumber">
													<bean:define id="carrierCode"
														name="mailAcceptanceVOSession"
														property="flightCarrierCode" toScope="page" />
													<bean:define id="flightNo" name="mailAcceptanceVOSession"
														property="flightNumber" toScope="page" />
													<ibusiness:flightnumber id="fltNo"
														carrierCodeProperty="flightCarrierCode"
														flightCodeProperty="flightNumber"
														carriercodevalue="<%=(String) carrierCode%>"
														flightcodevalue="<%=(String) flightNo%>" tabindex="1"
														componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_FLIGHTNO" />
												</logic:present>
											</logic:present>
										</div>
										<div class="ic-col-20 ic-mandatory ic-label-90 ic-marg-top-5">
											<label><common:message
													key="mailtracking.defaults.mailacceptance.lbl.depdate" /></label>
											<logic:notPresent name="mailAcceptanceVOSession"
												property="strFlightDate">
											<ibusiness:calendar property="depDate" id="flightDate" type="image" value="" tabindex="2" componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_FLIGHTDATE" />
											</logic:notPresent>
											<logic:present name="mailAcceptanceVOSession"
												property="strFlightDate">
												<bean:define id="depDate" name="mailAcceptanceVOSession"
													property="strFlightDate" toScope="page" />
												<!--ibusiness:calendar property="depDate" id="flightDate"
													type="image" value="<%=(String) depDate%>"
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_FLIGHTDATE"
													tabindex="3" /-->
													 <ibusiness:calendar id="flightDate" property="depDate" type="image"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_FLIGHTDATE" value="<%=(String) depDate%>"/>
											</logic:present>
										</div>
									</div>
									<div id="CARRIER">
										<div class="ic-col-5"></div>
										<div class="ic-col-20 ic-mandatory ic-input ic-label-90 ic-marg-top-5">
										
											<label><common:message
													key="mailtracking.defaults.mailacceptance.lbl.carrier" /></label>
											<logic:notPresent name="mailAcceptanceVOSession"
												property="flightCarrierCode">
												<ihtml:text property="carrierCode" maxlength="3" value=""
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CARRIERCODE"
													tabindex="1" />
											</logic:notPresent>
											<logic:present name="mailAcceptanceVOSession"
												property="flightCarrierCode">
												<bean:define id="carCode" name="mailAcceptanceVOSession"
													property="flightCarrierCode" toScope="page" />
												<ihtml:text property="carrierCode" maxlength="3"
													value="<%=(String) carCode%>"
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CARRIERCODE"
													tabindex="1" />
											</logic:present>
											<div class= "lovImg">
											<img id="carLov"
												src="<%=request.getContextPath()%>/images/lov.png"
												width="22" height="22"
												onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','1','carrierCode','',0)">

										</div>
										</div>
										<div class="ic-col-20 ic-input ic-mandatory ic-label-30 ic-marg-top-5">
											<label><common:message
													key="mailtracking.defaults.mailacceptance.lbl.destination" /></label>
											<logic:notPresent name="mailAcceptanceVOSession"
												property="destination">
												<ihtml:text property="destination" maxlength="4" value=""
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_DESTINATION"
													tabindex="2" />
											</logic:notPresent>
											<logic:present name="mailAcceptanceVOSession"
												property="destination">
												<bean:define id="finalDest" name="mailAcceptanceVOSession"
													property="destination" toScope="page" />
												<ihtml:text property="destination" maxlength="4"
													value="<%=(String) finalDest%>"
													componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_DESTINATION"
													tabindex="2" />
											</logic:present>
											<div class= "lovImg">
											<img id="desLov"
												src="<%=request.getContextPath()%>/images/lov.png"
												width="22" height="22"
												onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','1','destination','',0)">

										</div>
									</div>
									</div>
									<div class="ic-col-15 ic-label-15 marginL10 marginT15">
										<ihtml:nbutton property="btnList"
											componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_LIST"
											tabindex="3" accesskey="L">
											<common:message
												key="mailtracking.defaults.mailacceptance.btn.list" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear"
											componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CLEAR"
											tabindex="4" accesskey="E">
											<common:message
												key="mailtracking.defaults.mailacceptance.btn.clear" />
										</ihtml:nbutton>
									</div>
									<div class="ic-col-10 ic-label-80 marginT5">
										<label><common:message
											key="mailtracking.defaults.mailacceptance.lbl.depport" /></label>
										<div class="multi-input">
										<ihtml:text property="departurePort" maxlength="4"
											value="<%=MailAcceptanceForm.getDeparturePort()%>"
											componentID="CMP_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_DEPPORT" />
									</div>
									</div>
									 <logic:notPresent name="flightValidationVOSession" property="flightRoute">
		   <logic:present name="flightValidationVOSession" property="operationalStatus">
									<div class="ic-col-20 ic-label-60">
 <%int infoCount=0;%>
		   <img id="polpouInfo_<%=infoCount%>" src="<%=request.getContextPath()%>/images/info.gif"  width="16" height="16"  onclick="prepareAttributes(this,'polpouInfotab_<%=infoCount%>','polpouInfo')" />
						<div style="display:none;width:100%;height:100%;" id="polpouInfotab_<%=infoCount%>" name="polpouInfo">	
														<table class="iCargoBorderLessTable">
															<thead>
									<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailacceptance.infopol" /></th>
									<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailacceptance.infopoU" /></th>
															</thead>
															<logic:present name="mailAcceptanceVOSession">
								<logic:present name="mailAcceptanceVOSession" property="polPouMap">
									<logic:iterate id="polPouMap" name="mailAcceptanceVOSession" property="polPouMap">
									<bean:define id="values" name="polPouMap" property="value" type="java.util.Collection"/>
										<logic:iterate id="val" name="values" type="java.lang.String" >
																			<tr height="25%">
											<td width= "35%" class="iCargoLabelCenterAligned"><bean:write name ="polPouMap" property="key"/></td>
											<td width= "35%" class="iCargoLabelCenterAligned"><%=String.valueOf(val)%></td>
																			</tr>
																		</logic:iterate>
																	</logic:iterate>
																</logic:present>
															</logic:present>
														</table>
													</div>

												</div>	</logic:present>
										</logic:notPresent>
		
		<div class="ic-col-30 ic-label-30 marginT15">
											<div id="FLIGHTDETAILS">

		<logic:present name="flightValidationVOSession" property="operationalStatus">
				 <bean:define id="oprstat" name="flightValidationVOSession" property="operationalStatus" toScope="page"/>
													</logic:present>
		   <logic:present name="flightValidationVOSession" property="flightRoute">
		 	<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />

														</logic:present>
		   <logic:notPresent name="flightValidationVOSession" property="flightRoute">
				<logic:present name="flightValidationVOSession" property="statusDescription">
				 <common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
												</logic:present>
							  </logic:notPresent>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
				
						
						<div class="ic-row">
							
								<div >
								<h4>
								<common:message
									key="mailtracking.defaults.mailacceptance.lbl.ulddetails" />
									</h4>
							</div>
								<div class="ic-button-container">
									<a href="#" id="addLink" value="add" name="add"
										tabindex="5" class="iCargoLink"><common:message
											key="mailtracking.defaults.mailacceptance.lnk.add" /></a>
									<logic:equal name="MailAcceptanceForm" property="preassignFlag"
										value="N">

<a href="#" id="deleteLink" value="delete" name="delete"
											tabindex="6" class="iCargoLink"><common:message
												key="mailtracking.defaults.mailacceptance.lnk.delete" /></a>
									</logic:equal>
								</div>
							
					</div>
					
					<div id="acceptanceTable" >
						<div class="tableContainer" id="div1" style="height:720px">
							<table class="fixed-header-table">
								<thead>
									<tr>
										<td width="8%" class="iCargoTableHeader"><input type="checkbox"
											name="masterContainer"
											onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);" /></td>
										<td width="14%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.uld" /></td>
										<td width="6%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.pou" /></td>
										<td width="6%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.destn" /></td>
										<td width="16%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.onwardflights" /></td>
										<td width="8%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.numbags" /></td>
										<td width="8%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.wt" /></td>
										<td width="8%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.warehouse" /></td>
										<td width="8%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.loc" /></td>
										<td width="18%" class="iCargoTableHeader"><common:message
												key="mailtracking.defaults.mailacceptance.lbl.remarks" /></td>
									</tr>
								</thead>
								<tbody>
									<%
										int i = 0;
									%>
									<logic:present name="mailAcceptanceVOSession"
										property="containerDetails">
										<bean:define id="containerDetailsVOsColl"
											name="mailAcceptanceVOSession" property="containerDetails"
											scope="page" toScope="page" />

										<%
											Collection<String> selectedrows = new ArrayList<String>();
										%>
										<logic:present name="MailAcceptanceForm" property="selectMail">
											<%
												String[] selectedRows = MailAcceptanceForm
																		.getSelectMail();
																for (int j = 0; j < selectedRows.length; j++) {
																	selectedrows.add(selectedRows[j]);
																}
											%>
										</logic:present>
										<logic:iterate id="containerDetailsVO"
											name="containerDetailsVOsColl" indexId="rowCount">
											
												<%
													i++;
												%>
												<!--Parent Rows -->
												<tr id="container<%=i%>" class="ic-table-row-main">
													<td class="iCargoTableDataTd">
														
															
																<a href="#" onClick="toggleRows(this);event.cancelBubble=true" class="ic-tree-table-expand tier1"></a>
															<bean:define id="compcode" name="containerDetailsVO"
																property="companyCode" toScope="page" />
															<%
																String primaryKey = (String) compcode
																							+ (String.valueOf(i));
															%>
															<%
																if (selectedrows.contains(primaryKey)) {
															%>

															<input type="checkbox" name="selectMail"
																value="<%=primaryKey%>" checked="true">
															<%
																} else {
															%>
															<input type="checkbox" name="selectMail"
																value="<%=primaryKey%>" />

															<%
																}
															%>
														
													</td>
													<td class="iCargoTableDataTd"><logic:present
															name="containerDetailsVO" property="paBuiltFlag">
															<logic:equal name="containerDetailsVO"
																property="paBuiltFlag" value="Y">
																<logic:present name="containerDetailsVO"
																	property="containerNumber">
																	<bean:write name="containerDetailsVO"
																		property="containerNumber" />
																</logic:present>
																<common:message
																	key="mailtracking.defaults.mailacceptance.lbl.shipperBuild" />
															</logic:equal>
															<logic:equal name="containerDetailsVO"
																property="paBuiltFlag" value="N">
																<logic:present name="containerDetailsVO"
																	property="containerNumber">
																	<bean:write name="containerDetailsVO"
																		property="containerNumber" />
																</logic:present>
															</logic:equal>
														</logic:present> <logic:notPresent name="containerDetailsVO"
															property="paBuiltFlag">
															<logic:present name="containerDetailsVO"
																property="containerNumber">
																<bean:write name="containerDetailsVO"
																	property="containerNumber" />
															</logic:present>
														</logic:notPresent> <logic:present name="containerDetailsVO"
															property="containerNumber">
															<bean:define id="uld" name="containerDetailsVO"
																property="containerNumber" />
															<bean:define id="typ" name="containerDetailsVO"
																property="containerType" />
																<html:hidden property="uldType"
														value="<%=(String) typ%>" /> <!--added by A-8149 for ICRD-270524-->
															<%
																String uldnum = (String) typ + "-"
																								+ (String) uld;
															%>
															<ihtml:hidden property="uldnos" value="<%=uldnum%>" />
														</logic:present></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="pou" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="destination" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="route" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="totalBags"
															format="####" /></td>
													<td class="iCargoTableDataTd"><common:write
															name="containerDetailsVO" property="totalWeight"
															unitFormatting="true" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="wareHouse" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="containerDetailsVO" property="location" /></td>
													<td class="iCargoTableDataTd"><logic:notPresent
															name="containerDetailsVO" property="remarks">
					&nbsp;
				</logic:notPresent> <logic:present name="containerDetailsVO" property="remarks">
															<bean:define id="rem" name="containerDetailsVO"
																property="remarks" />
															<common:write name="rem" splitLength="25" />
														</logic:present></td>
												</tr>
												<!--Child Rows -->
												<tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
													<td colspan="10"><div class="tier4">
															<a href="#"></a>
														</div>
														<table>
															<tr>
																<td>
																	<table>
																		<thead>
																			<tr>
																				<td width="14%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.dsn" /></td>
																				<td width="12%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.origin" /></td>
																				<td width="12%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.destnoe" /></td>
																				<td width="8%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.class" /></td>
																				<td width="8%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.cat" /></td>
																				<td width="8%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.subclass" /></td>
																				<td width="11%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.year" /></td>
																				<td width="14%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.numbags" /></td>
																				<td width="15%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.wt" /></td>
																				<td width="10%" class="iCargoTableHeader"><common:message
																						key="mailtracking.defaults.mailacceptance.lbl.plt" /></td>
																			</tr>
																		</thead>
																		<tbody>
																			<logic:present name="containerDetailsVO"
																				property="dsnVOs">
																				<bean:define id="dsnVOsColl"
																					name="containerDetailsVO" property="dsnVOs"
																					scope="page" toScope="page" />
																				<logic:iterate id="dsnVO" name="dsnVOsColl"
																					indexId="rowCount">
																				
																						<tr>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="dsn" /></td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="originExchangeOffice" /></td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO"
																									property="destinationExchangeOffice" /></td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="mailClass" /></td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="mailCategoryCode" /></td>
																							<td class="iCargoTableDataTd">
																								<%
																									String subclassValue = "";
																								%> <logic:notPresent
																									name="dsnVO" property="mailSubclass">
																									<bean:write name="dsnVO"
																										property="mailSubclass" />
																								</logic:notPresent> <logic:present name="dsnVO"
																									property="mailSubclass">
																									<bean:define id="despatchSubclass" name="dsnVO"
																										property="mailSubclass" toScope="page" />
																									<%
																										subclassValue = (String) despatchSubclass;
																																			int arrays = subclassValue
																																					.indexOf("_");
																																			if (arrays == -1) {
																									%>

																									<bean:write name="dsnVO"
																										property="mailSubclass" />
																									<%
																										} else {
																									%>
						&nbsp;
						<%
							}
						%>
																								</logic:present>

																							</td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="year" /></td>
																							<td class="iCargoTableDataTd"><bean:write
																									name="dsnVO" property="bags" format="####" /></td>
																							<td class="iCargoTableDataTd"><common:write
																									name="dsnVO" property="weight"
																									unitFormatting="true" /></td>
																							<td class="iCargoTableDataTd">
																								<div>
																									<!--<logic:notPresent name="dsnVO" property="pltEnableFlag">
								<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>
							 </logic:notPresent>
							 <logic:present name="dsnVO" property="pltEnableFlag">
								<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
								       <input type="checkbox" name="isPrecarrAwb" value="true" checked disabled="true"/>
								</logic:equal>
								<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
								     <input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>
								</logic:equal>
							 </logic:present>-->
																									<logic:notPresent name="dsnVO"
																										property="pltEnableFlag">
																										<img id="isnotPltEnabled"
																											src="<%=request.getContextPath()%>/images/icon_off.gif" />
																									</logic:notPresent>
																									<logic:present name="dsnVO"
																										property="pltEnableFlag">
																										<logic:equal name="dsnVO"
																											property="pltEnableFlag" value="Y">
																											<img id="isPltEnabled"
																												src="<%=request.getContextPath()%>/images/icon_on.gif" />
																										</logic:equal>
																										<logic:equal name="dsnVO"
																											property="pltEnableFlag" value="N">
																											<img id="isnotPltEnabled"
																												src="<%=request.getContextPath()%>/images/icon_off.gif" />
																										</logic:equal>
																									</logic:present>
																								</div>
																							</td>
																						</tr>
																					
																				</logic:iterate>
																			</logic:present>
																		</tbody>
																	</table>
																</td>
															</tr>
														</table></td>
												</tr>
											
										</logic:iterate>
									</logic:present>
									<tr></tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<!-- Commented by Gopinath M (A-3217) for the Bug Id: MTK708 @ TRV on 31-Aug-2009 -->
						<!--<ihtml:nbutton property="btnCaptureULDDamage" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CAPTUREULDDAMAGE" tabindex="6">
						<common:message key="mailtracking.defaults.mailacceptance.btn.captureulddamage" />
						 </ihtml:nbutton>-->
						 <!-- Added by A-7371 for ICRD-133987 starts-->
						 <ihtml:nbutton property="btTransfer"
							componentID="BTN_MAIL_OPERATIONS_MAILACCEPTANCE_TRANSFER"
							accesskey="F">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.transfer" />
						</ihtml:nbutton>
						<!-- Added by A-7371 for ICRD-133987 ends-->
						<ihtml:nbutton property="btnReassignContainer"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_REASSIGNCONTAINER"
							tabindex="6" accesskey="N">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.reassigncontainer" />
						</ihtml:nbutton>
						<!--  <ihtml:nbutton property="btnAssignContainer" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_ASSIGNCONTAINER" tabindex="6">
						<common:message key="mailtracking.defaults.mailacceptance.btn.assigncontainer" />
					  </ihtml:nbutton>-->
						<ihtml:nbutton property="btnReopenFlight"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_REOPENFLIGHT"
							tabindex="7" accesskey="H">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.reopenflight" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnCloseFlight"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CLOSEFLIGHT"
							tabindex="8" accesskey="G">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.closeflight" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnPreAdvice"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_PREADVICE"
							tabindex="11" accesskey="V">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.preadvice" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnDelete"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_DELETE"
							 accesskey="T">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.delete" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnSave"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_SAVE"
							tabindex="13" accesskey="S">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CLOSE"
							tabindex="14" accesskey="O">
							<common:message
								key="mailtracking.defaults.mailacceptance.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</ihtml:form>
	</div>
	
	</body>
</html:html>