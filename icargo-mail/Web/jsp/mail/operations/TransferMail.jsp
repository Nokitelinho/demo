<%--*********************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: TransferMail.jsp
* Date				: 04-Oct-2006
* Author(s)			: A-1876
 **********************************************--%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm"%>
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
<html:html locale="true">
<head> 



<%@ include file="/jsp/includes/customcss.jsp" %>
<title><common:message bundle="transferMailResources"
		key="mailtracking.defaults.transfermail.lbl.title" /></title>
<meta name="decorator" content="popup_panel">
<common:include type="script"
	src="/js/mail/operations/TransferMail_Script.jsp" />
</head>
<body>
	<%@include file="/jsp/includes/reports/printFrame.jsp"%>
	<div id="divmain" class="iCargoPopUpContent">
		<ihtml:form action="/mailtracking.defaults.transfermail.screenload.do" styleClass="ic-main-form">
			<bean:define id="form" name="TransferMailForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm"
				toScope="page" scope="request" />
			<business:sessionBean id="flightValidationVOSession"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.transfermail" method="get"
				attribute="flightValidationVO" />
			<logic:present name="flightValidationVOSession">
				<bean:define id="flightValidationVOSession"
					name="flightValidationVOSession" toScope="page" />
			</logic:present>
			<business:sessionBean id="containerVOsSession"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.transfermail" method="get"
				attribute="containerVOs" />
			<logic:present name="containerVOsSession">
				<bean:define id="containerVOsSession" name="containerVOsSession"
					toScope="page" />
			</logic:present>

			<ihtml:hidden property="initialFocus" />
			<ihtml:hidden property="duplicateFlightStatus" />
			<ihtml:hidden property="mailbag" />
			<ihtml:hidden property="fromScreen" />
			<ihtml:hidden property="frmFltNum" />
			<ihtml:hidden property="frmFltDat" />
			<ihtml:hidden property="closeFlag" />
			<ihtml:hidden property="selectMode" />
			<ihtml:hidden property="hideRadio" />
			<ihtml:hidden property="hidScanTime" />
			<ihtml:hidden property="dummyCarCod" />
			<ihtml:hidden property="frmCarCod" />
			<ihtml:hidden property="embargoFlag" /> <!--added by a-7871-->
			<input type="hidden" name="printTransferManifestFlag" />
			<input type="hidden" name="currentDialogOption" />
			<input type="hidden" name="currentDialogId" />
			<ihtml:hidden property="preassignFlag" />
			<div class="ic-content-main">
				<div class="ic-row">
					<span class="ic-page-title "> <common:message
							key="mailtracking.defaults.transfermail.lbl.pagetitle" />
					</span>
				</div>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
					<div class="ic-input-container  ">
						<div class="ic-row">
							<div class="ic-input-container  ic-input-round-border">
								<div class="ic-row ic_inline_chcekbox">
									<div class="ic-input  ic-split-30">
										<ihtml:radio property="assignToFlight" onclick="selectDiv();"
											value="FLIGHT"
											componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_RADIOFLIGHT" />
										<label> <common:message
												key="mailtracking.defaults.transfermail.lbl.radioflight" />
										</label>
									</div>
									<div class="ic-input  ic-split-30">
										<ihtml:radio property="assignToFlight" onclick="selectDiv();"
											value="DESTINATION"
											componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_RADIODESTINATION" />
										<label> <common:message
												key="mailtracking.defaults.transfermail.lbl.radiodestn" />
										</label>
									</div>

								</div>

							</div>
						</div>
						<div class="ic-row">
								<div id="FLIGHT">
									<div class="ic-input-container  ic-round-border">
										<div class="ic-row ic-label-45 ic-label-90" style="padding-left:10px">
											<div class="ic-col-25 ic-mandatory">
												<label><common:message
														key="mailtracking.defaults.transfermail.lbl.flightno" /></label>
												<ibusiness:flightnumber
													carrierCodeProperty="flightCarrierCode" id="flight"
													flightCodeProperty="flightNumber"
													carriercodevalue="<%=form.getFlightCarrierCode()%>"
													flightcodevalue="<%=form.getFlightNumber()%>" tabindex="1"
													carrierCodeMaxlength="3" flightCodeMaxlength="5"
													componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_FLIGHTNO" carrierCodeStyleClass="iCargoTextFieldVerySmall" flightCodeStyleClass="iCargoTextFieldSmall"/>

											</div>
											<div class="ic-col-40 ic-mandatory">
												<label><common:message
														key="mailtracking.defaults.transfermail.lbl.flightdate" /></label>
												<ibusiness:calendar property="flightDate" type="image"
													id="incalender" value="<%=form.getFlightDate()%>"
													tabindex="2"
													componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_FLIGHTDATE"
													onblur="autoFillDate(this)" />
											</div>
											<div class="ic-col-30">

											<div class="ic-button-container">
												<ihtml:nbutton property="btnList"
													componentID="BTN_MAILTRACKING_DEFAULTS_TRANSFERMAIL_LIST"
													tabindex="3">
													<common:message
														key="mailtracking.defaults.transfermail.btn.list" />
												</ihtml:nbutton>
												<ihtml:nbutton property="btnClear"
													componentID="BTN_MAILTRACKING_DEFAULTS_TRANSFERMAIL_CLEAR"
													tabindex="4">
													<common:message
														key="mailtracking.defaults.transfermail.btn.clear" />
												</ihtml:nbutton>
											</div>
										</div>
										</div>
										<div class="ic-row ">
										<div class="ic-col-70">
											<div id="FLIGHTDETAILS">
												<logic:present name="flightValidationVOSession" property="flightRoute">
													<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
												</logic:present>
												<logic:notPresent name="flightValidationVOSession" property="flightRoute">
													&nbsp;
												</logic:notPresent>
											</div>
											</div>
										</div>
										</div>
									</div>
								</div>
							</div>
							</div>
							</div>
							<div class="ic-main-container">
										<div class="ic-row" id="divLinks">
											<div class="ic-col-30 ic-bold-label ic-left">
												<label><common:message
													key="mailtracking.defaults.transfermail.lbl.ulddetails" /></label>
											</div>
											<div class="ic-col-70">
												<logic:equal name="TransferMailForm"
													property="preassignFlag" value="N">
														<div class="ic-button-container">
													<a href="#" id="addLink" value="add" name="add"
														class="iCargoLink"><common:message
															key="mailtracking.defaults.transfermail.lnk.addcontainer" /></a>
														</div>
												</logic:equal>
												<logic:notEqual name="TransferMailForm"
													property="preassignFlag" value="N">
													&nbsp;
												</logic:notEqual>
											</div>
										</div>
										<div class="ic-row ">
											<div id="div1" class="tableContainer" style="height: 63px">
												<table class="fixed-header-table">
													<thead>
														<tr>
															<td width="10%" class="iCargoTableHeaderLabel"></td>
															<td width="30%" class="iCargoTableHeaderLabel"><common:message
																	key="mailtracking.defaults.transfermail.lbl.uld" /></td>
															<td width="15%" class="iCargoTableHeaderLabel"><common:message
																	key="mailtracking.defaults.transfermail.lbl.pou" /></td>
															<td width="15%" class="iCargoTableHeaderLabel"><common:message
																	key="mailtracking.defaults.transfermail.lbl.finaldest" /></td>
															<td width="15%" class="iCargoTableHeaderLabel"><common:message
																	key="mailtracking.defaults.transfermail.lbl.numbags" /></td>
															<td width="15%" class="iCargoTableHeaderLabel"><common:message
																	key="mailtracking.defaults.transfermail.lbl.weight" /></td>
														</tr>
													</thead>
													<tbody>
														<logic:present name="containerVOsSession">
															<bean:define id="containerVOs" name="containerVOsSession"
																toScope="page" />
															<logic:iterate id="containerVO" name="containerVOs"
																indexId="rowid"
																type="com.ibsplc.icargo.business.mail.operations.vo.ContainerVO">

																<tr>
																	<td>
																			<input type="checkbox" name="selectMail"
																				value="<%=rowid.toString()%>"
																				onclick="singleSelectCb(this.form,'<%=rowid.toString()%>','selectMail');">
																		</td>
																	<td><logic:present name="containerVO"
																			property="paBuiltFlag">
																			<logic:equal name="containerVO"
																				property="paBuiltFlag" value="Y">
																				<bean:write name="containerVO"
																					property="containerNumber" />
																				<common:message
																					key="mailtracking.defaults.transfermail.lbl.shipperBuild" />
																			</logic:equal>
																			<logic:equal name="containerVO"
																				property="paBuiltFlag" value="N">
																				<bean:write name="containerVO"
																					property="containerNumber" />
																			</logic:equal>
																		</logic:present> <logic:notPresent name="containerVO"
																			property="paBuiltFlag">
																			<bean:write name="containerVO"
																				property="containerNumber" />
																		</logic:notPresent></td>
																	<td><bean:write name="containerVO" property="pou" /></td>
																	<td><bean:write name="containerVO"
																			property="finalDestination" /></td>
																	<td><bean:write name="containerVO" property="bags" /></td>
																	<td><common:write name="containerVO"
																			property="weight" unitFormatting="true"/></td><!--modified by A-7371-->
																</tr>

															</logic:iterate>
														</logic:present>
													</tbody>
												</table>
											</div>
										</div>


								<div id="DESTINATION">
									<div class="ic-row  ">
									

										<div class="ic-row">
										<div class="ic-input ic-split-20 ic-mandatory "></div>
											<div class="ic-input ic-split-20 ic-mandatory ">
												<label><common:message key="mailtracking.defaults.transfermail.lbl.carriercode" /></label>
											</div>
											
											<div class="ic-split-40 ic-mandatory  ic-date-time-combo">
												<label> <common:message
												key="mailtracking.defaults.transfermail.lbl.scandate" />
												</label>
											</div>
										</div>
												<div class="ic-row">
											
												<div style="padding-left: 127px;"><ihtml:text property="carrierCode" maxlength="3"
													tabindex="1"
													componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_CARRIERCODE" />
												<div class= "lovImg"><img value="carrierLOV"
													src="<%=request.getContextPath()%>/images/lov.png"
													width="22" height="22"
													onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','0','carrierCode','',0)"></div>
													
													

													
												<div style="padding-right: 172px; float: right;">
												<ibusiness:calendar property="scanDate" type="image"
												id="scanDate" value="<%=form.getScanDate()%>"
												componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERMAIL_SCANDATE"
												onblur="autoFillDate(this)" tabindex="5" />
												<ibusiness:releasetimer property="mailScanTime" indexId="index"
												tabindex="6"
												componentID="TXT_MAILTRACKING_DEFAULTS_TRANSFERMAIL_SCANTIME"
												id="scanTime" type="asTimeComponent"
												value="<%=form.getMailScanTime()%>" />
													</div>
											</div>

											
												
											
												
												
										</div>

										
<!--										<div class="ic-row">&nbsp;</div>
										<div class="ic-row">&nbsp;</div>
										<div class="ic-row">&nbsp;</div>
										<div class="ic-row">&nbsp;</div>
										<div class="ic-row">&nbsp;</div>
										<div class="ic-row">&nbsp;</div> -->
									</div>
								</div>




				
					</div>
					<div class="ic-foot-container">
						<div class="ic-row  ic-round-border">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnSave"
									componentID="BTN_MAILTRACKING_DEFAULTS_TRANSFERMAIL_OK"
									tabindex="7">
									<common:message key="mailtracking.defaults.transfermail.btn.ok" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClose"
									componentID="BTN_MAILTRACKING_DEFAULTS_TRANSFERMAIL_CLOSE"
									tabindex="8">
									<common:message
										key="mailtracking.defaults.transfermail.btn.close" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
		</ihtml:form>
	</div>
	
	</body>
</html:html>