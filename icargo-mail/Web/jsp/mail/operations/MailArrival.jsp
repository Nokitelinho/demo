<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  MailArrival.jsp
* Date          	 :  04-August-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"%>
<%@ page
	import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page
	import="com.ibsplc.icargo.business.mail.operations.vo.DSNVO"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<%@ include file="/jsp/includes/tlds.jsp"%>

<html:html locale="true">
<head>
<%@ include file="/jsp/includes/customcss.jsp" %>
<title><common:message bundle="mailArrivalResources"
		key="mailtracking.defaults.mailarrival.lbl.title" /></title>

<meta name="decorator" content="mainpanel">

<common:include type="script"
	src="/js/mail/operations/MailArrival_Script.jsp" />


</head>
<body>
	<%@include file="/jsp/includes/reports/printFrame.jsp"%>
	<bean:define id="MailArrivalForm" name="MailArrivalForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
		toScope="page" scope="request" />

	<business:sessionBean id="flightValidationVOSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailarrival" method="get"
		attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession"
			name="flightValidationVOSession" toScope="page" />
	</logic:present>

	<business:sessionBean id="mailArrivalVOSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailarrival" method="get"
		attribute="mailArrivalVO" />
	<logic:present name="mailArrivalVOSession">
		<bean:define id="mailArrivalVOSession" name="mailArrivalVOSession"
			toScope="page" />
	</logic:present>
	<business:sessionBean id="mailArrivalStatusSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailarrival" method="get"
		attribute="oneTimeMailStatus" />

	<div id="pageDiv" class="iCargoContent ic-masterbg">

		<ihtml:form
			action="mailtracking.defaults.mailarrival.screenloadmailarrival.do">

			<ihtml:hidden property="initialFocus" />
			<ihtml:hidden property="duplicateFlightStatus" />
			<ihtml:hidden property="checkFlight" />
			<ihtml:hidden property="listFlag" />
			<ihtml:hidden property="operationalStatus" />
			<ihtml:hidden property="chkFlag" />
			<ihtml:hidden property="container" />
			<input type="hidden" name="currentDialogId" />
			<input type="hidden" name="currentDialogOption" />

			<ihtml:hidden property="flightSequenceNumber" />
			<ihtml:hidden property="carrierId" />
			<ihtml:hidden property="legSerialNumber" />
			<ihtml:hidden property="selectMainContainer" />
			<ihtml:hidden property="selectCont" />
			<ihtml:hidden property="childCont" />
			<ihtml:hidden property="selectMainCont" />
			<ihtml:hidden property="selectMode" />
			<ihtml:hidden property="attachRouting" />
			<ihtml:hidden property="csgDocNumForRouting" />
			<ihtml:hidden property="paCodeForRouting" />
			<ihtml:hidden property="parentContainer" />
			<ihtml:hidden property="selectChild" />
			<ihtml:hidden property="unsavedDataFlag" />
			<ihtml:hidden property="arrivedStatus" />
			<ihtml:hidden property="warningFlag" />
			<ihtml:hidden property="disableButtonsForTBA" />
			<ihtml:hidden property="saveSuccessFlag" />
			<ihtml:hidden property="changePopUpFlag" />
			<ihtml:hidden property="disableButtonsForAirport" />

			<ihtml:hidden property="embargoFlag" />

			<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"> <common:message
							key="mailtracking.defaults.mailarrival.lbl.pagetitle" />
					</span>
					<div class="ic-filter-panel">
						<div class="ic-row ">
							
							<div class="ic-input ic-split-20 ic-mandatory">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.flightno" />
								</label>
								<logic:notPresent name="mailArrivalVOSession"
									property="flightCarrierCode">
									<logic:notPresent name="mailArrivalVOSession"
										property="flightNumber">
										<ibusiness:flightnumber id="fltNo"
											carrierCodeProperty="flightCarrierCode"
											flightCodeProperty="flightNumber" carriercodevalue=""
											flightcodevalue=""
											componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_FLIGHTNUMBER" />
									</logic:notPresent>
								</logic:notPresent>

								<logic:present name="mailArrivalVOSession"
									property="flightCarrierCode">
									<logic:present name="mailArrivalVOSession"
										property="flightNumber">
										<bean:define id="carrierCode" name="mailArrivalVOSession"
											property="flightCarrierCode" toScope="page" />
										<bean:define id="flightNo" name="mailArrivalVOSession"
											property="flightNumber" toScope="page" />
										<ibusiness:flightnumber id="fltNo"
											carrierCodeProperty="flightCarrierCode"
											flightCodeProperty="flightNumber"
											carriercodevalue="<%=(String) carrierCode%>"
											flightcodevalue="<%=(String) flightNo%>"
											componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_FLIGHTNUMBER" />
									</logic:present>
								</logic:present>
							</div>
							<div class="ic-input ic-split-20 ic-mandatory">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.date" />
								</label>
								<logic:notPresent name="mailArrivalVOSession"
									property="arrivalDate">
									<ibusiness:calendar property="arrivalDate" id="flightDate"
										type="image" value=""
										componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_DATE" />
								</logic:notPresent>
								<logic:present name="mailArrivalVOSession"
									property="arrivalDate">
									<bean:define id="depDate" name="mailArrivalVOSession"
										property="arrivalDate" toScope="page" />
									<%
										String conDt = TimeConvertor.toStringFormat(
															((LocalDate) depDate).toCalendar(),
															TimeConvertor.CALENDAR_DATE_FORMAT);
									%>
									<ibusiness:calendar property="arrivalDate" id="flightDate"
										type="image" value="<%=(String) conDt%>"
										componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_DATE" />
								</logic:present>
							</div>
							<div class="ic-input ic-split-20 ">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.port" />
								</label>
								<ihtml:text property="arrivalPort" maxlength="4"
									value="<%=MailArrivalForm.getArrivalPort()%>"
									componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_PORT" />
							</div>
							<div class="ic-input ic-split-30 ">
								<logic:present name="flightValidationVOSession"
									property="flightRoute">
									<common:displayFlightStatus
										flightStatusDetails="flightValidationVOSession" />
								</logic:present>
								<logic:notPresent name="flightValidationVOSession"
									property="flightRoute">
									&nbsp;
								</logic:notPresent>
							</div>

						</div>
						<div class="ic-row ">
							
							<div class="ic-input ic-split-20 ">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.mailstatus" />
								</label>
								<ihtml:select property="mailStatus"
									componentID="CMB_MAILTRACKING_DEFAULTS_MAILARRIVAL_MAILSTATUS">
									<html:option value="ALL">
										<common:message key="combo.select" />
									</html:option>
									<bean:define id="mailArrivalStatus"
										name="mailArrivalStatusSession" toScope="page" />
									<logic:iterate id="mailArrivalStatusVO"
										name="mailArrivalStatus">
										<bean:define id="fieldValue" name="mailArrivalStatusVO"
											property="fieldValue" toScope="page" />
										<html:option value="<%=(String) fieldValue%>">
											<bean:write name="mailArrivalStatusVO"
												property="fieldDescription" />
										</html:option>
									</logic:iterate>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-20 ">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.transfercarrier" />
								</label>
								<ihtml:text property="transferCarrier"
									componentID="CMB_MAILTRACKING_DEFAULTS_MAILARRIVAL_TRANSFERCARRIER"
									maxlength="2" />
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png"
									width="22" height="22"
									onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.transferCarrier.value,'Airline','1','transferCarrier','',0)">
							</div>
							</div>
							<div class="ic-input ic-split-20 ">
								<label> <common:message
										key="mailtracking.defaults.mailarrival.lbl.pa" />
								</label>
								<ihtml:text property="arrivalPA"
									componentID="CMB_MAILTRACKING_DEFAULTS_MAILARRIVAL_PA"
									maxlength="5" />
								<div class= "lovImg"><img id="despatchPALov"
									src="<%=request.getContextPath()%>/images/lov.png" width="22"
									height="22"
									onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.arrivalPA.value,'PA','1','arrivalPA','',0)">
							</div>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList"
									componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_LIST"
									accesskey="L">
									<common:message
										key="mailtracking.defaults.mailarrival.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear"
									componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CLEAR"
									accesskey="E">
									<common:message
										key="mailtracking.defaults.mailarrival.btn.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-button-container">
							<div>
								<a href="#" id="addLink" value="add" name="add"
									class="iCargoLink" onclick="checkCloseFlight();"><common:message
										key="mailtracking.defaults.mailarrival.lnk.add" /></a>
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div2" style="height: 650px">
							<table id="mailarrival" class="fixed-header-table">
								<thead>
									<tr>
										<td class="iCargoTableHeaderLabel"><input type="checkbox"
											name="masterContainer"
											onclick="updateHeaderCheckBox(this.form,this.form.masterContainer,this.form.selectContainer);" /><span></span>
										</td>
										<td width="18%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.uld" /><span></span></td>
										<td width="6%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.pol" /><span></span></td>
										<td width="6%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.destination" /><span></span></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.onwardflights" /><span></span></td>
										<td width="6%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.mftdbags" /><span></span></td>
										<td width="7%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.mftdwt" /><span></span></td>
										<td width="6%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.recvdbags" /><span></span></td>
										<td width="7%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.recvdwt" /><span></span></td>
										<td width="3%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.intact" /><span></span></td>
										<td width="19%" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mailarrival.lbl.remarks" /><span></span></td>
									</tr>
								</thead>
								<tbody>
									<%
										int i = 0;
									%>
									<logic:present name="mailArrivalVOSession"
										property="containerDetails">
										<bean:define id="containerDetailsVOsColl"
											name="mailArrivalVOSession" property="containerDetails"
											scope="page" toScope="page" />


										<%
											Collection<String> selectedrows = new ArrayList<String>();
										%>
										<logic:present name="MailArrivalForm"
											property="selectContainer">
											<%
												String[] selectedRows = MailArrivalForm
																		.getSelectContainer();
																for (int j = 0; j < selectedRows.length; j++) {
																	selectedrows.add(selectedRows[j]);
																}
											%>
										</logic:present>
										<logic:iterate id="containerDetailsVO"
											name="containerDetailsVOsColl" indexId="rowCount">
											<tr id="container<%=i%>" class="ic-table-row-main">
												<td>

													 <a href="#" onClick="toggleRows(this)" class="ic-tree-table-expand tier1"></a>
														<%
															String primaryKey = String.valueOf(i);
														%>
														<%
															if (selectedrows.contains(primaryKey)) {
														%>

														<input type="checkbox" name="selectContainer"
															value="<%=primaryKey%>" checked
															onclick="toggleTableHeaderCheckbox('selectContainer',this.form.masterContainer);" />
														<%
															} else {
														%>
														<input type="checkbox" name="selectContainer"
															value="<%=primaryKey%>"
															onclick="toggleTableHeaderCheckbox('selectContainer',this.form.masterContainer);" />

														<%
															}
														%>

												</td>
												<logic:present name="containerDetailsVO"
													property="containerType">
													<bean:define id="containerType" name="containerDetailsVO"
														property="containerType" />
													<html:hidden property="uldType"
														value="<%=(String) containerType%>" />
												</logic:present>
												<logic:present name="containerDetailsVO"
													property="operationFlag">
													<bean:define id="operationFlag" name="containerDetailsVO"
														property="operationFlag" />
													<html:hidden property="contOpFlag"
														value="<%=(String) operationFlag%>" />
												</logic:present>
												<logic:notPresent name="containerDetailsVO"
													property="operationFlag">
													<html:hidden property="contOpFlag" value="NA" />
												</logic:notPresent>
												<logic:present name="containerDetailsVO"
													property="transferFlag">
													<bean:define id="transferFlag" name="containerDetailsVO"
														property="transferFlag" />
													<html:hidden property="contTransferFlag"
														value="<%=(String) transferFlag%>" />
												</logic:present>
												<logic:notPresent name="containerDetailsVO"
													property="transferFlag">
													<html:hidden property="contTransferFlag" value="NA" />
												</logic:notPresent>
												<logic:present name="containerDetailsVO"
													property="releasedFlag">
													<bean:define id="releasedFlag" name="containerDetailsVO"
														property="releasedFlag" />
													<html:hidden property="contReleasedFlag"
														value="<%=(String) releasedFlag%>" />
												</logic:present>
												<logic:notPresent name="containerDetailsVO"
													property="releasedFlag">
													<html:hidden property="contReleasedFlag" value="N" />
												</logic:notPresent>
												<td><logic:present name="containerDetailsVO"
														property="paBuiltFlag">
														<logic:equal name="containerDetailsVO"
															property="paBuiltFlag" value="Y">
															<bean:write name="containerDetailsVO"
																property="containerNumber" />
															<common:message
																key="mailtracking.defaults.mailarrival.lbl.shipperBuild" />
															<ihtml:hidden property="paBuiltFlag" value="Y" />
														</logic:equal>
														<logic:equal name="containerDetailsVO"
															property="paBuiltFlag" value="N">
															<bean:write name="containerDetailsVO"
																property="containerNumber" />
															<ihtml:hidden property="paBuiltFlag" value="N" />
														</logic:equal>
													</logic:present> <logic:notPresent name="containerDetailsVO"
														property="paBuiltFlag">
														<bean:write name="containerDetailsVO"
															property="containerNumber" />
														<ihtml:hidden property="paBuiltFlag" value="N" />
													</logic:notPresent></td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="pol" /></td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="destination" /></td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="onwardFlights" /></td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="totalBags"
														format="####" /></td>
												<td class="iCargoTableDataTd"><common:write
														name="containerDetailsVO" property="totalWeight"
														unitFormatting="true" /></td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="receivedBags"
														format="####" /></td>
												<td class="iCargoTableDataTd"><common:write
														name="containerDetailsVO" property="receivedWeight"
														unitFormatting="true" /></td>
												<td class="iCargoTableDataTd">
													<!--<logic:present name="containerDetailsVO" property="intact">
					<logic:equal name="containerDetailsVO" property="intact" value="Y">
			    			<input type="checkbox" name="intact"  property="intact" value="" checked disabled/>
			    		</logic:equal>
					<logic:notEqual name="containerDetailsVO" property="intact" value="Y">
			    			<input type="checkbox" name="intact"  property="intact" value="" disabled/>
			    		</logic:notEqual>
			    	</logic:present>
		    	<logic:notPresent name="containerDetailsVO" property="intact">
		    		<input type="checkbox" name="intact"  property="intact" value="" disabled/>
		    	</logic:notPresent>--> <logic:present name="containerDetailsVO"
														property="intact">
														<logic:equal name="containerDetailsVO" property="intact"
															value="Y">
															<img id="isintact"
																src="<%=request.getContextPath()%>/images/icon_on.gif" />
														</logic:equal>
														<logic:notEqual name="containerDetailsVO"
															property="intact" value="Y">
															<img id="isnotintact"
																src="<%=request.getContextPath()%>/images/icon_off.gif" />
														</logic:notEqual>
													</logic:present> <logic:notPresent name="containerDetailsVO"
														property="intact">
														<img id="isnotintact"
															src="<%=request.getContextPath()%>/images/icon_off.gif" />
													</logic:notPresent>
												</td>
												<td class="iCargoTableDataTd"><bean:write
														name="containerDetailsVO" property="remarks" /></td>
											</tr>
											<!--Child Rows -->
											<%
												int k = 0;
											%>
											<tr id="container<%=i%>-<%=i%>"  class="ic-table-row-sub">
												<td colspan="11"><div>
														<a href="#"></a>
													</div>
													<table >
															<tr>
																<td>
																	<table class="ic-table-sub">
																		<thead>
																<tr>
																	<td width="2%" class="iCargoTableHeader">
																		<!-- <input type="checkbox" name="masterDSN" value="<%=primaryKey%>" onclick="updateHeaderCheckBoxForTreeTable(this);"/> -->&nbsp;
																	</td>
																	<td width="6%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.dsn" /></td>
																	<td width="8%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.ooe" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.doe" /></td>
																	<td width="7%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.class" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.cat" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.subclass" /></td>

																	<td width="3%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.year" /></td>
																	<td width="7%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.mftdbags" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.mftdwt" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.recvdbags" /></td>
																	<td width="7%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.recvdwt" /></td>
																	<td width="10%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.awbno" /></td>																			
																	<td width="10%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.csgdocnum" /></td>
																	<td width="5%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.pacode" /></td>
																	<td width="3%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.plt" /></td>
																	<td width="7%" class="iCargoTableHeader"><common:message
																			key="mailtracking.defaults.mailarrival.lbl.rtgavl" /></td>
																</tr>
															</thead>
															<tbody>
																<logic:present name="containerDetailsVO"
																	property="dsnVOs">
																	<bean:define id="dsnVOsColl" name="containerDetailsVO"
																		property="dsnVOs" scope="page" toScope="page" />

																	<%
																		Collection<String> selecteddsns = new ArrayList<String>();
																	%>
																	<logic:present name="MailArrivalForm"
																		property="childContainer">
																		<%
																			String[] selectedDsns = MailArrivalForm
																											.getChildContainer();
																									for (int j = 0; j < selectedDsns.length; j++) {
																										selecteddsns.add(selectedDsns[j]);
																									}
																		%>
																	</logic:present>
																	<logic:iterate id="dsnVO" name="dsnVOsColl"
																		indexId="index">
																		<tr>
																			<logic:present name="dsnVO" property="transferFlag">
																				<bean:define id="transferFlag" name="dsnVO"
																					property="transferFlag" />
																				<html:hidden property="dsnTransferFlag"
																					value="<%=(String) transferFlag%>" />
																			</logic:present>
																			<logic:notPresent name="dsnVO"
																				property="transferFlag">
																				<html:hidden property="dsnTransferFlag" value="NA" />
																			</logic:notPresent>

																			<td class="iCargoTableDataTd">
																				<%
																					String subKey = primaryKey + "~" + k;
																				%> <%
 	if (selecteddsns.contains(subKey)) {
 %> <input type="checkbox" name="childContainer"
																				value="<%=subKey%>" checked /> <%
 	} else {
 %> <input type="checkbox" name="childContainer"
																				value="<%=subKey%>" /> <%
 	}
 %>

																			</td>
																				<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="dsn"/></td>
					<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="originExchangeOffice"/></td>
					<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="destinationExchangeOffice"/></td>
					<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="mailClass"/></td>

					<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="mailCategoryCode"/></td>
																			<td class="iCargoTableDataTd">
																				<%
																					String subclassValue = "";
																				%> <logic:notPresent
																					name="dsnVO" property="mailSubclass">
																					<bean:write name="dsnVO" property="mailSubclass" />
																				</logic:notPresent> <logic:present name="dsnVO" property="mailSubclass">
																					<bean:define id="despatchSubclass" name="dsnVO"
																						property="mailSubclass" toScope="page" />
																					<%
																						subclassValue = (String) despatchSubclass;
																													int arrays = subclassValue.indexOf("_");
																													if (arrays == -1) {
																					%>
																					<bean:write name="dsnVO" property="mailSubclass" />

																					<%
																						} else {
																					%>
							&nbsp;
						   <%
						   	}
						   %>
																				</logic:present>

																			</td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="year" /></td>
																			<td class="iCargoTableDataTd"><bean:write
																					name="dsnVO" property="bags" format="####" /></td>
																			<td class="iCargoTableDataTd"><common:write
																					name="dsnVO" property="weight"
																					unitFormatting="true" /></td>
																			<td class="iCargoTableDataTd"><bean:write
																					name="dsnVO" property="receivedBags" format="####" /></td>
																			<td class="iCargoTableDataTd"><common:write
																					name="dsnVO" property="receivedWeight"
																					unitFormatting="true" /></td>
																			<td class="iCargoTableDataTd"><logic:present name="dsnVO"
																				property="masterDocumentNumber"><common:write
																				name="dsnVO" property="documentOwnerCode" />
																				<label>-</label><common:write
																				name="dsnVO" property="masterDocumentNumber" />
																				</logic:present></td>																					
																			<td class="iCargoTableDataTd"><logic:present name="dsnVO"
																					property="csgDocNum">
																					<logic:present name="dsnVO"
																						property="paCode">
																					<ihtml:text
																						componentID="CMP_Mailtracking_Defaults_MailArrival_CSGDocNum"
																						indexId="index" readonly="true"
																						property="csgDocNum" name="dsnVO" />
																					<ihtml:hidden property="operationalFlag" value="N" />
																					</logic:present>
																					<logic:notPresent name="dsnVO"
																						property="paCode">
																					<ihtml:text
																						componentID="CMP_Mailtracking_Defaults_MailArrival_CSGDocNum"
																						indexId="index" readonly="false"
																						property="csgDocNum" name="dsnVO" />
																					<ihtml:hidden property="operationalFlag" value="U" />
																					</logic:notPresent>
																				</logic:present> <logic:notPresent name="dsnVO" property="csgDocNum">
																					<ihtml:text
																						componentID="CMP_Mailtracking_Defaults_MailArrival_CSGDocNum"
																						indexId="index" property="csgDocNum"
																						maxlength="13" value="" />
																					<ihtml:hidden property="operationalFlag" value="U" />
																				</logic:notPresent></td>
																			<td class="iCargoTableDataTd"><logic:present name="dsnVO"
																					property="paCode">
																					<bean:define id="paCode" name="dsnVO"
																						property="paCode" />
																					<%
																						String field = paCode.toString();
																					%>

																					<ihtml:hidden property="paCod" value="<%=field%>" />
																				</logic:present> <logic:notPresent name="dsnVO" property="paCode">
																					<ihtml:hidden property="paCod" value="N" />
																				</logic:notPresent> <bean:write name="dsnVO" property="paCode" /></td>
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
																						<logic:equal name="dsnVO" property="pltEnableFlag"
																							value="Y">
																							<img id="isPltEnabled"
																								src="<%=request.getContextPath()%>/images/icon_on.gif" />
																						</logic:equal>
																						<logic:equal name="dsnVO" property="pltEnableFlag"
																							value="N">
																							<img id="isnotPltEnabled"
																								src="<%=request.getContextPath()%>/images/icon_off.gif" />
																						</logic:equal>
																					</logic:present>
																				</div>
																			</td>
																			<td class="iCargoTableDataTd">
																				<div>
																					<logic:notPresent name="dsnVO"
																						property="routingAvl">
																						<img id="isnotRoutingAvl"
																							src="<%=request.getContextPath()%>/images/icon_off.gif" />
																						<ihtml:hidden property="hiddenRoutingAvl"
																							value="N" />
																					</logic:notPresent>
																					<logic:present name="dsnVO" property="routingAvl">
																						<logic:equal name="dsnVO" property="routingAvl"
																							value="Y">
																							<img id="isRoutingAvl"
																								src="<%=request.getContextPath()%>/images/icon_on.gif" />
																							<ihtml:hidden property="hiddenRoutingAvl"
																								value="Y" />
																						</logic:equal>
																						<logic:equal name="dsnVO" property="routingAvl"
																							value="N">
																							<img id="isnotRoutingAvl"
																								src="<%=request.getContextPath()%>/images/icon_off.gif" />
																							<ihtml:hidden property="hiddenRoutingAvl"
																								value="N" />
																						</logic:equal>
																					</logic:present>
																				</div>
																			</td>
																		</tr>
																		<%
																			k++;
																		%>
																	</logic:iterate>
																</logic:present>
															</tbody>
														</table>
														</td>
														</tr>
														</table>
													</td>
											</tr>
											<%
																			i++;
																		%>
										</logic:iterate>
									</logic:present>
								</tbody>

							</table>
						</div>
					</div>
				</div>

				<div class="ic-foot-container">
					<div class="ic-button-container">
						<div class="ic-row">
						<ihtml:nbutton property="btnAttachAWB"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_ATTACHAWB" accesskey="N">
							<common:message key="mailtracking.defaults.mailarrival.tooltip.attachawb" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnAutoAttachAWB"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_AUTOATTACHAWB" accesskey="W">
							<common:message key="mailtracking.defaults.mailarrival.tooltip.autoattachawb" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnDetachAWB"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_DETACHAWB" accesskey="B">
							<common:message key="mailtracking.defaults.mailarrival.tooltip.detachawb" />
						</ihtml:nbutton>						
						<ihtml:nbutton property="btnChangeFlight"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CHANGEFLIGHT" accesskey="C">
							<common:message key="mailtracking.defaults.mailarrival.tooltip.changeflight" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnUndoArrival"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_UNDOARRIVAL"
							accesskey="A">
							<common:message
								key="mailtracking.defaults.mailarrival.tooltip.undoarrival" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnAttachRouting"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_ATTACHROUTING"
							accesskey="R">
							<common:message
								key="mailtracking.defaults.mailarrival.tooltip.attachrouting" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnAcquit"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_ACQUIT"
							accesskey="U">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.acquit" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnArriveMail"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_ARRIVEMAIL"
							accesskey="M">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.arrivemail" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnDeliverMail"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_DELIVERMAIL"
							accesskey="V">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.delivermail" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnDiscrepancy"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_DISCREPANCY"
							accesskey="D">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.discrepancy" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnTransferMail"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_TRANSFER"
							accesskey="T">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.transfermail" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnCloseFlight"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CLOSEFLIGHT"
							accesskey="G">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.closeflight" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnReopenFlight"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_REOPENFLIGHT"
							accesskey="H">
							<common:message
								key="mailtracking.defaults.mailarrival.btn.reopenflight" />
						</ihtml:nbutton>
						</div>
						<div class="ic-row ic-button-container">
						<ihtml:nbutton property="btnPrint"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_PRINT"
							accesskey="P">
							<common:message key="mailtracking.defaults.mailarrival.btn.print" />
						</ihtml:nbutton>						
						<ihtml:nbutton property="btnSave"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_SAVE"
							accesskey="S">
							<common:message key="mailtracking.defaults.mailarrival.btn.save" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnClose"
							componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CLOSE"
							accesskey="O">
							<common:message key="mailtracking.defaults.mailarrival.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			</div>


		</ihtml:form>
	</div>
	</body>

</html:html>