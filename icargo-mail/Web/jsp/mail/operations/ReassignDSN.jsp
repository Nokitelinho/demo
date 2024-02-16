<%--*********************************************
* Project	 		 : iCargo
* Module Code & Name : Mail Tracking
* File Name			 : ReassignDSN.jsp
* Date				 : 01-APR-2008
* Author(s)			 : A-3227 RENO K ABRAHAM
 **********************************************--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm"%>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head> 
		
			
	
   <title><common:message bundle="reassignDSNResources" key="mailtracking.defaults.reassigndsn.lbl.title" /></title>
   <meta name="decorator" content="popup_panel">
   <common:include type="script" src="/js/mail/operations/ReassignDSN_Script.jsp" />
</head>

<body id="bodyStyle">
	
	
<div id="divmain" class="iCargoPopUpContent"  style="position:static;height:0px; z-index:1;">	
<ihtml:form action="/mailtracking.defaults.reassigndsn.screenloadreassigndsn.do" styleClass="ic-main-form" >
<bean:define id="form" name="ReassignDSNForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm"
    toScope="page" scope="request"/>
    
<business:sessionBean id="flightValidationVOSession" 
	moduleName="mail.operations" 
	screenID="mailtracking.defaults.reassigndsn" 
	method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>
	
<business:sessionBean id="containerVOsSession" 
	moduleName="mail.operations" 
	screenID="mailtracking.defaults.reassigndsn" 
	method="get" attribute="containerVOs" />
	<logic:present name="containerVOsSession">
		<bean:define id="containerVOsSession" name="containerVOsSession" toScope="page"/>
	</logic:present>
	
<business:sessionBean id="despatchDetailsVOsSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.reassigndsn"
		method="get"
		attribute="despatchDetailsVOs" />
	<logic:present name="despatchDetailsVOsSession">
			<bean:define id="despatchDetailsVOsSession" name="despatchDetailsVOsSession" toScope="page"/>
	</logic:present>
	
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="closeFlag" />
<ihtml:hidden property="container" />
<ihtml:hidden property="carrierIdInv" />
<ihtml:hidden property="carrierCodeInv" />
<ihtml:hidden property="hideRadio" />
<ihtml:hidden property="selectMode" />
<ihtml:hidden property="reassignFocus" />
<ihtml:hidden property="screenStatus" />
<ihtml:hidden property="selectedConsignment" />

<ihtml:hidden property="frmFlightDate" />
<ihtml:hidden property="fromFlightNumber" />
<ihtml:hidden property="fromFlightCarrierCode" />
<ihtml:hidden property="frmassignTo" />
<ihtml:hidden property="fromdestination" />

	
	  <div class="ic-content-main">
 
 


	<div id="divt1" class="ic-main-container">
	<div class="ic-row">
	<div class="ic-input ic-col-50">
		<span class="ic-page-title ic-display-none">
						<common:message key="mailtracking.defaults.reassigndsn.lbl.pagetitle" />
		</span>
		
		 <div class="ic-row">
			 <div class="tableContainer" id="div1" style="height:150px;">
				<table  class="fixed-header-table" id="tab1">
					<thead>
						<tr class="iCargoTableHeadingCenter" >
												<td class="iCargoTableHeaderLabel ic-center" width="3%">
													<ihtml:checkbox property="checkAll"  componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_CHECKALL"/>
												</td>					  
												<td class="iCargoTableHeaderLabel ic-center" width="25%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.consignmentno"/>
												</td>
												<td class="iCargoTableHeaderLabel ic-center" width="18%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.consignmentdate"/>
												</td>
												<td class="iCargoTableHeaderLabel ic-center" width="12%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.pa"/>
												</td>
												<td class="iCargoTableHeaderLabel ic-center" width="10%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.avlpcs"/>
												</td>	
												<td class="iCargoTableHeaderLabel ic-center" width="11%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.avlwt"/>
												</td>
												<td class="iCargoTableHeaderLabel ic-center" width="10%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.reassignpcs"/>
												</td>
												<td class="iCargoTableHeaderLabel ic-center" width="11%">
													<common:message key="mailtracking.defaults.reassigndsn.thead.reassignwt"/>
												</td>
						</tr>
					</thead>
					<tbody>
											<logic:present name="despatchDetailsVOsSession">
												<bean:define id="despatchDetailsVOs" name="despatchDetailsVOsSession" toScope="page"/>
												<% Collection<String> selectrows = new ArrayList<String>(); %>

												 <logic:present name="form" property="selectedRows" >								
													<%
													String[] selRows = form.getSelectedRows();
													for (int j = 0; j < selRows.length; j++) {
														selectrows.add(selRows[j]);
													}
													%>								
									 			</logic:present>
												<logic:iterate id="despatchDetailsVO" name="despatchDetailsVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO">
																		
														<tr>	 
															<td class="iCargoTableDataTd" >
																<div>
												                    <% String Key=(String.valueOf(rowid));%>
												
																	<%
																		if(selectrows.contains(Key)){
																	%>
												
																		<input type="checkbox" name="selectedRows" value="<%=Key%>" checked/>
																	<%
																		}
																		else{
																	%>
																		<input type="checkbox" name="selectedRows" value="<%=Key%>" />
												
																	<%
																		}
																	%>																	
																</div>
															</td>					  
															<td class="iCargoTableDataTd" >
																<bean:write name="despatchDetailsVO" property="consignmentNumber"/>
															</td>					  
															<td class="iCargoTableDataTd" >
																<bean:define id="consignmentDate" name="despatchDetailsVO" property="consignmentDate" toScope="page"/>
																<%= consignmentDate.toString().substring(0,11) %>
															</td>
															<td class="iCargoTableDataTd" >
																<bean:write name="despatchDetailsVO" property="paCode"/>
															</td>
															<td class="iCargoTableDataTd" >
																<bean:write name="despatchDetailsVO" property="acceptedPcsToDisplay"/>
															</td>
															<td class="iCargoTableDataTd" >																												
																<bean:write name="despatchDetailsVO" property="acceptedWgtToDisplay" format="####.00"/>
															</td>
															<td class="iCargoTableDataTd" >
																<logic:present name="despatchDetailsVO" property="acceptedBags">
																	<bean:define id="acceptedBags" name="despatchDetailsVO" property="acceptedBags" toScope="page"/>
																<ihtml:text property="reAssignedPcs" value="<%=acceptedBags.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_REASSIGN_PCS" maxlength="5"/>
																</logic:present>
																<logic:notPresent name="despatchDetailsVO" property="acceptedBags">
																	<ihtml:text property="reAssignedPcs" value="" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_REASSIGN_PCS" maxlength="5"/>
																</logic:notPresent>
															</td>
															<td class="iCargoTableDataTd" >
																<logic:present name="despatchDetailsVO" property="acceptedWeight">
																	<bean:define id="sampleStdWtVo" name="despatchDetailsVO" property="acceptedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!--modified by A-7371-->
																	

																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="reAssignedWt" unitTxtName="reAssignedWt" label=""  unitReq = "false" dataName="sampleStdWt"
																			unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Reassign Wt"
																			unitValue="<%=String.valueOf(sampleStdWtVo.getDisplayValue())%>" style="background :'<%=color%>'"
																			indexId="index" styleId="statedWt"/>
																	
																	<%--																	
																	<ihtml:text property="reAssignedWt"  value="<%=String.valueOf(sampleStdWtVo.getDisplayValue())%>" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_REASSIGN_WT" maxlength="8"/><!-- modified by A-7371-->
																	--%>
																</logic:present>
																<logic:notPresent name="despatchDetailsVO" property="acceptedWeight">
																	
																		<bean:define id="sampleStdWtVo" name="despatchDetailsVO" property="acceptedWeight" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!--modified by A-7371-->
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="reAssignedWt" unitTxtName="reAssignedWt" label=""  unitReq = "false" dataName="sampleStdWt"
																			unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Reassign Wt"
																			unitValue="0.0" style="background :'<%=color%>'"
																			indexId="index" styleId="statedWt"/>
														   			 	
																<%--
																	<ihtml:text property="reAssignedWt"  value="" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_REASSIGN_WT" maxlength="8"/>
																--%>	
																</logic:notPresent>
															</td>	
														</tr>												
																										
												</logic:iterate>
											</logic:present>
											<logic:notPresent name="despatchDetailsVOsSession">
												&nbsp;
											</logic:notPresent>
										</tbody>
				</table>
			 </div>
		 </div>
	</div>

 
  <div class=" ic-input ic-col-50">
	<div class="ic-row">
			<div class="ic-filter-panel">
				<div class="ic-row">
										<logic:present name="ReassignDSNForm" property="hideRadio">
											    <logic:equal name="ReassignDSNForm" property="hideRadio" value="CARRIER">
												    <ihtml:hidden property="assignToFlight" value="FLIGHT" />
												</logic:equal>
												<logic:equal name="ReassignDSNForm" property="hideRadio" value="FLIGHT">
												    <ihtml:hidden property="assignToFlight" value="DESTN" />
												</logic:equal>
											 </logic:present>	
											<label>
												<common:message key="mailtracking.defaults.reassigndsn.lbl.reassignto" />
											</label>
											<ihtml:radio property="assignToFlight"  onclick="selectDiv();" value="FLIGHT" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_RADIOFLIGHT"/>
											<label>
												<common:message key="mailtracking.defaults.reassigndsn.lbl.radioflight" />
											</label>
											<ihtml:radio property="assignToFlight"  onclick="selectDiv();" value="DESTN" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_RADIODESTINATION"/>
											<label>
												<common:message key="mailtracking.defaults.reassigndsn.lbl.radiodestn" />
											</label>
											
				</div>
				<div class="ic-row">
				<div id="FLIGHT" >    					  			  
																<div class="ic-input ic-split-30 ic-label-20">
																	<label>
																		<common:message key="mailtracking.defaults.reassigndsn.lbl.flightno" />
																	</label>
																	 <ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight" flightCodeProperty="flightNumber"
																			carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>"  tabindex="1"
																			carrierCodeMaxlength="3" flightCodeMaxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_FLIGHTNO"/>
																</div>
																
																<div class="ic-input ic-split-30 ic-label-20">
																	<label>
																		<common:message key="mailtracking.defaults.reassigndsn.lbl.flightdate" />
																	</label>
																	<ibusiness:calendar property="depDate" type="image" id="incalender"
																		value="<%=form.getDepDate()%>"  tabindex="2"
																		 componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_FLIGHTDATE" onblur="autoFillDate(this)"/>
																</div>
				</div>
				
				<div id="DESTN" >
															<div class="ic-input ic-split-25 ic-label-30">
																	<label>
																		<common:message key="mailtracking.defaults.reassigndsn.lbl.carriercode" />
																	</label>
																	<ihtml:text property="carrierCode" maxlength="3" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_CARRIERCODE"/>
																		<img value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','0','carrierCode','',0)">
																</div>
																
																<div class="ic-input ic-split-70 ic-label-20">
																	<label>
																		<common:message key="mailtracking.defaults.reassigndsn.lbl.destination" />
																	</label>
																	<ihtml:text property="destination" maxlength="4" tabindex="2" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_DESTINATION" />
																		<img value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','0','destination','',0)">
																</div>
				</div>
				
												<div>
													<div class="ic-input ic-split-50 ic-label-20">
														<label>
															<common:message key="mailtracking.defaults.reassigndsn.lbl.depport" />
														</label>
														<ihtml:text property="departurePort" maxlength="4" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNDSN_DEPPORT" />
													</div>
													
													
														<div id="FLIGHTDETAILS" >
															<logic:present name="flightValidationVOSession" property="flightRoute">
																<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
															</logic:present>
															<logic:notPresent name="flightValidationVOSession" property="flightRoute">
																&nbsp;
															</logic:notPresent>
														</div>
																				         													 
												</div>
												
				<div class="ic-button-container">
														<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNDSN_LIST" tabindex="3">
															<common:message key="mailtracking.defaults.reassigndsn.btn.list" />
														</ihtml:nbutton>
														<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNDSN_CLEAR" tabindex="4">
															<common:message key="mailtracking.defaults.reassigndsn.btn.clear" />
														</ihtml:nbutton>
				</div>
				</div>
			</div>
	</div>
		<div class="ic-row">
			<div class="ic-row">
				<h3>
				<common:message key="mailtracking.defaults.reassigndsn.lbl.condetails" />
				</h3>
			</div>
			
			<div class="ic-row">
				<div  id="div2" class="tableContainer" style="height:150px;">
					<table  class="fixed-header-table" id="icargotable2">
						<thead>
																	<tr class="iCargoTableHeadingLeft">
																		<td width="10%" class="iCargoTableHeaderLabel ic-center">
																		</td>
																		<td width="30%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassigndsn.lbl.containerno" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel" >
																			<common:message key="mailtracking.defaults.reassigndsn.lbl.pou" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassigndsn.lbl.finaldest" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassigndsn.lbl.numbags" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassigndsn.lbl.weight" />
																		</td>
																	</tr>								
						</thead>	
						<tbody>
																	<logic:present name="containerVOsSession">
																		<bean:define id="containerVOs" name="containerVOsSession" toScope="page"/>
																	<logic:iterate id="containerVO" name="containerVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.ContainerVO">
																			
																				<tr>
																					<td >
																						<div>
																							<input type="checkbox" name="selectContainer" value="<%= rowid.toString() %>" onclick="singleSelectCb(this.form,'<%= rowid.toString() %>','selectContainer');">
																						</div>
																					</td>
																					<td >
																						<logic:present name="containerVO" property="paBuiltFlag">
																							<logic:equal name="containerVO" property="paBuiltFlag" value="Y">
																								<bean:write name="containerVO" property="containerNumber"/>
																								<common:message key="mailtracking.defaults.reassigndsn.lbl.shipperBuild" />
																							</logic:equal>
																							<logic:equal name="containerVO" property="paBuiltFlag" value="N">				  			
																								<bean:write name="containerVO" property="containerNumber"/>
																							</logic:equal>
																						</logic:present>
																						<logic:notPresent name="containerVO" property="paBuiltFlag">
																							<bean:write name="containerVO" property="containerNumber"/>
																						</logic:notPresent>
																					</td>
																					<td><bean:write name="containerVO" property="pou"/></td>
																					<td><bean:write name="containerVO" property="finalDestination"/></td>
																					<td><bean:write name="containerVO" property="bags"/></td>
																					<td>
																						<common:write name="containerVO" property="weight" unitFormatting="true"/><!--modified by A-7371-->																						
																					</td>
																				</tr>
																			
																		</logic:iterate>
																	</logic:present>	        
																</tbody>
					</table>
				</div>
			</div>
			
											   	  
											
		</div>
		

 </div>
 </div>
  </div>
 <div class="ic-foot-container">
											
			<div class="ic-button-container">
											<ihtml:nbutton property="btnSave" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNDSN_OK" tabindex="5">
												<common:message key="mailtracking.defaults.reassigndsn.btn.ok" />
											</ihtml:nbutton>
											<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNDSN_CLOSE" tabindex="6">
												<common:message key="mailtracking.defaults.reassigndsn.btn.close" />
											</ihtml:nbutton>

				
				</div>
		</div>
 

</div>
</ihtml:form>
</div>

			
		  
	</body>
</html:html>
