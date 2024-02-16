<%--*********************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: ReassignMailbag_Ajax.jsp
* Date				: 06-May-2008
* Author(s)			: A-3251
 **********************************************--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm"%>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ihtml:form action="/mailtracking.defaults.reassignmailbag.screenloadreassignmailbag.do" styleclass="ic-main-form">
<bean:define id="form"
	name="ReassignMailbagForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm"
    toScope="page" scope="request"/>   

<business:sessionBean id="currentStatus"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.reassignmailbag"
		  method="get"
		  attribute="currentStatus" />
		  
<business:sessionBean id="containerVOsSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.reassignmailbag"
		method="get"
		attribute="containerVOs" />
	<logic:present name="containerVOsSession">
			<bean:define id="containerVOsSession" name="containerVOsSession" toScope="page"/>
	</logic:present>
	
<business:sessionBean id="mailbagVOsSession"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.reassignmailbag"
		method="get"
		attribute="mailbagVOs" />
	<logic:present name="mailbagVOsSession">
			<bean:define id="mailbagVOsSession" name="mailbagVOsSession" toScope="page"/>
	</logic:present>
	
	<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.reassignmailbag" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>
	


<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="reDSN" />
<ihtml:hidden property="hideRadio" />
<ihtml:hidden property="selmailbags" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="closeFlag" />
<ihtml:hidden property="container" />
<ihtml:hidden property="carrierIdInv" />
<ihtml:hidden property="carrierCodeInv" />
<ihtml:hidden property="selectMode" />
<ihtml:hidden property="reassignFocus" />

<ihtml:hidden property="frmFlightDate" />
<ihtml:hidden property="fromFlightNumber" />
<ihtml:hidden property="fromFlightCarrierCode" />
<ihtml:hidden property="frmassignTo" />
<ihtml:hidden property="fromdestination" />

  
 

<div class="ic-content-main">
	<div  id="_divt1" class="ic-main-container">
	<div class="ic-row">
	<div class="ic-input ic-col-50">
		<span class="ic-page-title ic-display-none">
						<common:message key="mailtracking.defaults.reassignmailbag.lbl.pagetitle" />
		</span>
		<div class="ic-row">
		<div class="ic-col-40">
										<%String lstPgNo = "";%>
										
											<logic:present name="ReassignMailForm" property="lastPageNum">
												<bean:define id="lastPg" name="ReassignMailForm" property="lastPageNum" scope="request"  toScope="page" />
												<%
													lstPgNo = (String) lastPg;
												%>
											</logic:present>
											<logic:present name="mailbagVOsSession" >
												<bean:define id="pageObj" name="mailbagVOsSession"  />
													<common:paginationTag pageURL="mailtracking.defaults.reassignmailbag.listmailbag.do"
						   							name="pageObj"
						   							display="label"
						   							labelStyleClass="iCargoResultsLabel"
						   							lastPageNum="<%=lstPgNo%>" />
						   							</logic:present>
											<logic:notPresent name="mailbagVOsSession" >
												&nbsp;
											</logic:notPresent>
				</div>									
		<div class="ic-col-60">						
										  <div class="ic-button-container">
							   		     	<logic:present name="mailbagVOsSession" >
							   		        	<bean:define id="pageObj1" name="mailbagVOsSession"  />
							   		     		<common:paginationTag
							   		     		  	linkStyleClass="iCargoLink"
							   		        	  	disabledLinkStyleClass="iCargoLink"
							   		   		  		pageURL="javascript:submitPage('lastPageNum','displayPage')"
							   		   		  		name="pageObj1"
							   		   		  		display="pages"
							   		   		  		lastPageNum="<%=lstPgNo%>"
													exportToExcel="true"
													exportTableId="reassignMailTable"
													exportAction="mailtracking.defaults.reassignmailbag.listmailbag.do"/>
							   		        </logic:present>
							   		       	<logic:notPresent name="mailbagVOsSession" >
							   		     		&nbsp;
							   		        </logic:notPresent>
							   	   		</div>
		</div>
		</div>
		 <div class="ic-row">
			 <div class="tableContainer" id="div1" style="height:150px;">
				<table  class="fixed-header-table" id="reassignMailTable">
					<thead>
					<tr class="ic-th-all">
						<th style="width:5%"/>
						<th style="width:35%"/>
						<th style="width:15%"/>
						<th style="width:15%"/>
						<th style="width:30%"/>
					</tr>
						<tr class="iCargoTableHeadingCenter" >
												<td class="iCargoTableHeaderLabel" width="10%">
													<ihtml:checkbox property="checkAll"  componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_CHECKALL"/>
												</td>					  
												<td class="iCargoTableHeaderLabel" width="30%">
													<common:message key="mailtracking.defaults.reassignmailbag.thead.Mailid"/>
												</td>
												<td class="iCargoTableHeaderLabel" width="30%" colspan="2">
													<common:message key="mailtracking.defaults.reassignmailbag.thead.Latestop"/>
												</td>
												<td class="iCargoTableHeaderLabel" width="30%">
													<common:message key="mailtracking.defaults.reassignmailbag.thead.Dateop"/>
												</td>
						</tr>
					</thead>
					<tbody>
											<logic:present name="mailbagVOsSession">
												<bean:define id="mailbagVOs" name="mailbagVOsSession" toScope="page"/>
												<% Collection<String> selectrows = new ArrayList<String>(); %>

												 <logic:present name="form" property="selectedRows" >								
													<%
													String[] selRows = form.getSelectedRows();
													for (int j = 0; j < selRows.length; j++) {
														selectrows.add(selRows[j]);
													}
													%>								
									 			</logic:present>
												<logic:iterate id="mailbagVO" name="mailbagVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO">
																			
														<tr>	 
															<td class="iCargoTableDataTd ic-center">
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
															<td class="iCargoTableDataTd ic-center" >
																<bean:write name="mailbagVO" property="mailbagId"/>
															</td>
															<td class="iCargoTableDataTd ic-center" >
																<logic:present name="mailbagVO" property="latestStatus">
																	<bean:define id="latestStatus" name="mailbagVO" property="latestStatus" toScope="page"/>
																	<logic:present name="currentStatus">	
																		<bean:define id="currentstatus" name="currentStatus" toScope="page"/>	
																		<logic:iterate id="onetmvo" name="currentstatus">
																			<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																			<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>										
																			<logic:equal name="onetimevo" property="fieldValue" value="<%= latestStatus.toString() %>">
																				<%= onetimedesc %>
																			</logic:equal>										
																		</logic:iterate>
																	</logic:present>
																</logic:present>
															</td>
															<td class="iCargoTableDataTd ic-center" >
																<bean:write name="mailbagVO" property="scannedPort"/>
															</td>
															<td class="iCargoTableDataTd ic-center" >
																<bean:define id="scannedDate" name="mailbagVO" property="scannedDate" toScope="page"/>
																<%= scannedDate.toString().substring(0,20) %>
															</td>
														</tr>												
													
												</logic:iterate>
											</logic:present>	
										</tbody>
				</table>
			 </div>
		 </div>
	</div>

 
  <div class=" ic-input ic-col-50">
	<div class="ic-row">
			<div class="ic-border"> <!-- Modified by A-8236 for ICRD-251780 -->
				<div class="ic-row paddL5">
				<div class="ic-row marginT5">
										<logic:present name="ReassignMailForm" property="hideRadio">
												<logic:equal name="ReassignMailForm" property="hideRadio" value="CARRIER">
													<ihtml:hidden property="assignToFlight" value="FLIGHT" />
												</logic:equal>
												<logic:equal name="ReassignMailForm" property="hideRadio" value="FLIGHT">
													<ihtml:hidden property="assignToFlight" value="DESTN" />
												</logic:equal>
											</logic:present>
											
											
												<common:message key="mailtracking.defaults.reassignmailbag.lbl.assignto" />
											
											<ihtml:radio property="assignToFlight"  onclick="selectDiv();" value="FLIGHT" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_RADIOFLIGHT"/>
											
												<common:message key="mailtracking.defaults.reassignmailbag.lbl.radioflight" />
											
											<ihtml:radio property="assignToFlight"  onclick="selectDiv();" value="DESTN" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_RADIODESTINATION"/>
											
												<common:message key="mailtracking.defaults.reassignmailbag.lbl.radiodestn" />
											
											
				</div>					
				</div>
				<div class="ic-row">
				<div id="FLIGHT" >    					  			  
																<div class="ic-input ic-split-50 ic-mandatory">
																	<label>
																		<common:message key="mailtracking.defaults.reassignmailbag.lbl.flightno" />
																	</label>
																	<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight" flightCodeProperty="flightNumber"
																	carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>"  tabindex="1"
																	carrierCodeMaxlength="3" flightCodeMaxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_FLIGHTNO"/>
																</div>
																
																<div class="ic-input ic-split-50 ic-mandatory">
																	<label>
																		<common:message key="mailtracking.defaults.reassignmailbag.lbl.flightdate" />
																	</label>
																	<ibusiness:calendar property="depDate" type="image" id="incalender"
																		value="<%=form.getDepDate()%>"  tabindex="2"
																		 componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_FLIGHTDATE" />
																</div>
				</div>
				
				<div id="DESTN" >
															<div class="ic-input ic-split-50 ic-mandatory">
																	<label>
																		<common:message key="mailtracking.defaults.reassignmailbag.lbl.carriercode" />
																	</label>
																	<ihtml:text property="carrierCode" maxlength="3" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_CARRIERCODE"/><!-- Modified by A-8236 for ICRD-251780 -->
																		<div class="lovImg"><img value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carrierCode.value,'Airline','0','carrierCode','',0)">
																		</div>
																	<div class="lovImg">
																		<img value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carrierCode.value,'Airline','0','carrierCode','',0)">
																</div>
																</div>
																<div class="ic-input ic-split-50 ic-mandatory">
																	<label>
																		<common:message key="mailtracking.defaults.reassignmailbag.lbl.destination" />
																	</label>
																	<ihtml:text property="destination" maxlength="4" tabindex="2" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_DESTINATION" /><!-- Modified by A-8236 for ICRD-251780 -->
																		<div class="lovImg"><img value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destination.value,'Airport','0','destination','',0)">
																		</div>
																	<div class="lovImg">
																		<img value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destination.value,'Airport','0','destination','',0)">
																		</div>
																</div>
				</div>
				
												<div class="ic-row">
													<div class="ic-input ic-split-30">
														<label>
															<common:message key="mailtracking.defaults.reassignmail.lbl.depport" />
														</label>
														<ihtml:text property="departurePort" maxlength="4" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_DEPPORT" />
													</div>
													
													<div class="ic-input ic-split-70">
														<div id="FLIGHTDETAILS" >
															<logic:present name="flightValidationVOSession" property="flightRoute">
																<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
															</logic:present>
															<logic:notPresent name="flightValidationVOSession" property="flightRoute">
																&nbsp;
															</logic:notPresent>
														</div>
																				         													 
												</div>
												
												</div>
				<div class="ic-row">								
				<div class="ic-button-container">
														<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_LIST" tabindex="3">
															<common:message key="mailtracking.defaults.reassignmailbag.btn.list" />
														</ihtml:nbutton>
														<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_CLEAR" tabindex="4">
															<common:message key="mailtracking.defaults.reassignmailbag.btn.clear" />
														</ihtml:nbutton>
				</div>
				</div>
			</div>
	</div>
	</div>
		<div class="ic-row">
			
				<h3>
					<common:message key="mailtracking.defaults.reassignmailbag.lbl.condetails" />
				</h3>
			
			
			<div class="ic-row">
				<div  id="div2" class="tableContainer" style="width:100%; height:100px;">
					<table  class="fixed-header-table" id="table2" style="width:100%;">
						<thead>
																	<tr class="iCargoTableHeadingLeft">
																		<td width="10%" class="iCargoTableHeaderLabel ic-center">
																		</td>
																		<td width="30%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassignmail.lbl.containerno" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel" >
																			<common:message key="mailtracking.defaults.reassignmail.lbl.pou" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassignmail.lbl.finaldest" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassignmail.lbl.numbags" />
																		</td>
																		<td width="15%" class="iCargoTableHeaderLabel">
																			<common:message key="mailtracking.defaults.reassignmail.lbl.weight" />
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
																								<common:message key="mailtracking.defaults.reassignmail.lbl.shipperBuild" />
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
																					<td><common:write name="containerVO" property="weight" unitFormatting="true"/></td><!-- modified by A-7371-->
																				</tr>
																			
																		</logic:iterate>
																	</logic:present>	        
																</tbody>
					</table>
				</div>
			</div>
			<div class="ic-row">
											<label>
												<common:message key="mailtracking.defaults.reassignmailbag.lbl.scandate" />
											</label> 
											<ibusiness:calendar property="scanDate" type="image" id="scanDate"
											value="<%=form.getScanDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_SCANDATE" onblur="autoFillDate(this)" tabindex="5"/>      			
											<ibusiness:releasetimer property="mailScanTime" indexId="index" tabindex="6" componentID="TXT_MAILTRACKING_DEFAULTS_REASSIGNMAILBAG_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=form.getMailScanTime()%>"/>
			</div>
											   	  
											
		</div>
		

 </div>
 </div>
  </div>
		
 </div> 
			
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

