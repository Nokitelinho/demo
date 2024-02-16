<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  MailManifest.jsp
* Date          	 :  28-July-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO"%>
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
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
	
<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.mailmanifest.lbl.title" /></title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/mail/operations/MailManifest_Script.jsp" />

</head>
<body id="bodyStyle">
	
	
	
<bean:define id="MailManifestForm" name="MailManifestForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
   toScope="page" scope="request"/>
<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="mailManifestVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="mailManifestVO" />
	<logic:present name="mailManifestVOSession">
		<bean:define id="mailManifestVOSession" name="mailManifestVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="oneTimeCat" />

<div id="pageDiv" class="iCargoContent ic-masterbg">

<ihtml:form action="mailtracking.defaults.mailmanifest.screenloadmailmanifest.do">

<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="disableSaveFlag" />
<ihtml:hidden property="uldsSelectedFlag" />
<ihtml:hidden property="uldsPopupCloseFlag" />
<ihtml:hidden property="openAttachAWB" />
<ihtml:hidden property="pou" />
<ihtml:hidden property="pol" />
<ihtml:hidden property="operationalStatus" />
<ihtml:hidden property="mailFlightSummary" />
<ihtml:hidden property="type" />
<ihtml:hidden property="parentContainer" />
<ihtml:hidden property="selectChild" />
<ihtml:hidden property="autoAttach" />
<ihtml:hidden property="shipmentDesc" />
<ihtml:hidden property="attachRouting" />
<ihtml:hidden property="csgDocNumForRouting" />
<ihtml:hidden property="paCodeForRouting" />
<ihtml:hidden property="warningFlag" />
<ihtml:hidden property="disableButtonsForTBA" />
<ihtml:hidden property="disableButtonsForAirport" />
<input type="hidden" name="currentDialogId" /> <!-- Added by A-8164 for ICRD-259783 -->
<input type="hidden" name="currentDialogOption" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.mailmanifest.lbl.pagetitle" />
	</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
                <div class="ic-input-container">
					<div class="ic-section ic-border">
						
							<div class="ic-row ">
							
											
							
							
							
							

										<div class="ic-col-10 ic-mandatory ic-label-90">
									<label><common:message key="mailtracking.defaults.mailmanifest.lbl.flightno" /></label>
										<logic:notPresent name="mailManifestVOSession" property="flightCarrierCode">
											<logic:notPresent name="mailManifestVOSession" property="flightNumber">
												<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue="" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_MAILMANIFEST_FLIGHTNUMBER"/>
											</logic:notPresent>
										</logic:notPresent>
										<logic:present name="mailManifestVOSession" property="flightCarrierCode">
											<logic:present name="mailManifestVOSession" property="flightNumber">
														<bean:define id="carrierCode" name="mailManifestVOSession" property="flightCarrierCode" toScope="page"/>
														<bean:define id="flightNo" name="mailManifestVOSession" property="flightNumber" toScope="page"/>
												 <ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_MAILMANIFEST_FLIGHTNUMBER"/>
											</logic:present>
										</logic:present>	   
								</div>			   
							<div class="ic-col-20 ic-mandatory ic-label-90">		   
									<label><common:message key="mailtracking.defaults.mailmanifest.lbl.depdate" /></label>
											<logic:notPresent name="mailManifestVOSession" property="strDepDate">
												<ibusiness:calendar property="depDate" id="flightDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_MAILMANIFEST_DEPDATE" tabindex="2"/>
											</logic:notPresent>
											<logic:present name="mailManifestVOSession" property="strDepDate">
												<bean:define id="depDate" name="mailManifestVOSession" property="strDepDate" toScope="page"/>
												<ibusiness:calendar property="depDate" id="flightDate" type="image" value="<%=(String)depDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILMANIFEST_DEPDATE" tabindex="2"/>
											</logic:present>
								</div>
								
								<div class="ic-col-15 ic-label-15 marginT10">
									
									<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_LIST" tabindex="3" accesskey="L">
									<common:message key="mailtracking.defaults.mailmanifest.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_CLEAR" tabindex="4" accesskey="E">
									<common:message key="mailtracking.defaults.mailmanifest.btn.clear" />
									</ihtml:nbutton>
									
								</div>
									<div class="ic-col-10 ic-label-90 multi-input marginL10">
									<label><common:message key="mailtracking.defaults.mailmanifest.lbl.depport" /></label>	
											 <ihtml:text property="departurePort" maxlength="4" value="<%=MailManifestForm.getDeparturePort()%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILMANIFEST_DEPPORT" tabindex="5"/>
									</div>
								
										   <logic:notPresent name="flightValidationVOSession" property="flightRoute">
											   <logic:present name="flightValidationVOSession" property="operationalStatus">
		  <div class="ic-col-20 ic-label-60">
												 <%int infoCount=0;%>
										   <img id="polpouInfo_<%=infoCount%>" src="<%=request.getContextPath()%>/images/info.gif"  width="16" height="16"  onclick="prepareAttributes(event,this,'polpouInfotab_<%=infoCount%>','polpouInfo')" />
														<div style="display:none;width:100%;height:100%;" id="polpouInfotab_<%=infoCount%>" name="polpouInfo">	
															<table class="iCargoBorderLessTable">
																<thead>
																	<th width= "50%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailmanifest.infopol" /></th>
																	<th width= "50%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailmanifest.infopoU" /></th>
																</thead>
																<logic:present name="mailManifestVOSession">
																<logic:present name="mailManifestVOSession" property="polPouMap">
																	<logic:iterate id="polPouMap" name="mailManifestVOSession" property="polPouMap">
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
							</div>
											</logic:present>
										</logic:notPresent>
										
										<div class="ic-col-30 ic-label-30 marginT10">
											<div id="FLIGHTDETAILS">
	  
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
				<div>
				<div class="ic-input ic-split-2"></div>
					<b><common:message key="mailtracking.defaults.mailmanifest.lbl.ulddetails" /></b>
				</div>	
				
			</div>
			<div class="ic-row" id="mailManifest_table">
				<div  class="tableContainer" id="div2"  style="width:100%;overflow:auto;height:650px;"> 
					<table  id="mailmanifest" class="fixed-header-table">
						<thead>
						  <tr class="iCargoTableHeadingCenter">
							<td width="7%" class="iCargoTableTd">
							<input type="checkbox" name="masterContainer" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);"/></td>
							<td width="30%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.uld" /></td>
							<td width="15%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.pou" /></td>
							<td width="15%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.destination" /></td>
							<td width="17%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.numbags" /></td>
							<td width="16%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.wt" /></td>
						  </tr>
						</thead>
						<tbody>
							<% int i = 0;%>
							<logic:present name="mailManifestVOSession" property="containerDetails">
								<bean:define id="containerDetailsVOsColl" name="mailManifestVOSession" property="containerDetails" scope="page" toScope="page"/>

								 <% Collection<String> selectedrows = new ArrayList<String>(); %>
								 <logic:present name="MailManifestForm" property="selectMail" >
								 <%
									String[] selectedRows = MailManifestForm.getSelectMail();
									for (int j = 0; j < selectedRows.length; j++) {
										selectedrows.add(selectedRows[j]);
									}
								%>
								</logic:present>
								<logic:iterate id="containerDetailsVO" name="containerDetailsVOsColl" indexId="rowCount">
									<!--Parent Rows -->
									<tr class="iCargoTableDataRow1" id="container<%=i%>" class="ic-table-row-main">
									
										<td class="iCargoTableDataTd " width="8%">
												 <div>
												 <a href="#" onClick="toggleRows(this);event.cancelBubble=true" class="ic-tree-table-expand tier1"></a>
											&nbsp;&nbsp;&nbsp;&nbsp;
											
												  <bean:define id="compcode" name="containerDetailsVO" property="companyCode" toScope="page"/>
												  
												  <% String primaryKey = String.valueOf(i);%>
														  <%
													if(selectedrows.contains(primaryKey)){
												  %>

													<input type="checkbox" name="selectMail" value="<%=primaryKey%>" checked />
												  <%
													}
													else{
												  %>
													<input type="checkbox" name="selectMail" value="<%=primaryKey%>" />

												  <%
													}
												  %>
												
											</div>
										</td>
										<td class="iCargoTableDataTd">
										 <logic:present name="containerDetailsVO" property="paBuiltFlag">
											 <logic:equal name="containerDetailsVO" property="paBuiltFlag" value="Y">
												<bean:write name="containerDetailsVO" property="containerNumber"/>
												<common:message key="mailtracking.defaults.mailmanifest.sb"/>
											 </logic:equal>
											 <logic:equal name="containerDetailsVO" property="paBuiltFlag" value="N">
												<bean:write name="containerDetailsVO" property="containerNumber"/>
											 </logic:equal>
										 </logic:present>
										 <logic:notPresent name="containerDetailsVO" property="paBuiltFlag">
											<bean:write name="containerDetailsVO" property="containerNumber"/>	
										 </logic:notPresent>	
										</td>
										<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="pou"/></td>
										<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="destination"/></td>
										<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="totalBags" format="####"/></td>
										<td class="iCargoTableDataTd"> 
										<common:write name="containerDetailsVO" property="totalWeight" unitFormatting="true" /></td> 
									 </tr>
									 <!--Child Rows -->
									<tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
										<td colspan="10"><div class="tier4"><a href="#" ></a></div>
											<table width="100%"><tr><td>
												<table>
															<thead>
																<tr class="iCargoTableHeadingCenter">
																		<td width="3%" class="iCargoTableHeader ic-center"><input type="checkbox" name="masterDSN" value="<%=primaryKey%>" onclick="updateHeaderCheckBoxForTreeTable(this);"/></td>
																		<td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.dsn" /></td>
																		<td width="9%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.origin" /></td>
																		<td width="9%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.destnoe" /></td>
																		<td width="12%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.cat" /></td>
																		<td width="5%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.class" /></td>
																		<td width="6%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.sc" /></td>
																		<td width="5%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.year" /></td>
																		<td width="7%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.numbags" /></td>
																		<td width="7%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.wt" /></td>
																		<td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.awb" /></td>
																		<td width="10%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.csgdocnum" /></td>
																		<td width="5%" class="iCargoTableHeader">PA Code</td>
																		<td width="2%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.plt" /></td>
																		<td width="4%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailmanifest.lbl.route" /></td>
																</tr>
															</thead>
															<tbody>
																<logic:present name="containerDetailsVO" property="dsnVOs">
																	<bean:define id="dsnVOsColl" name="containerDetailsVO" property="dsnVOs" scope="page" toScope="page"/>

																	<% Collection<String> selecteddsns = new ArrayList<String>(); %>
																	<logic:present name="MailManifestForm" property="selectDSN" >
																		 <%
																			String[] selectedDsns = MailManifestForm.getSelectDSN();
																			for (int j = 0; j < selectedDsns.length; j++) {
																				selecteddsns.add(selectedDsns[j]);
																			}
																		%>
																	</logic:present>
																	<logic:iterate id="dsnVO" name="dsnVOsColl" indexId="index">
																		<tr class="ic-table-row-sub">
																			<td class="iCargoTableDataTd">
																			<%
																				String primaryKeyDSN = i + "-" + String.valueOf(index);
																			%>

																			 <%
																				if(selecteddsns.contains(primaryKeyDSN)){
																			 %>

																				<input type="checkbox" name="selectDSN" value="<%=primaryKeyDSN%>" checked onclick="toggleTableHeaderCheckboxForTreeTable(this);event.cancelBubble=true" />
																			 <%
																				}
																				else{
																			 %>
																				<input type="checkbox" name="selectDSN" value="<%=primaryKeyDSN%>" onclick="toggleTableHeaderCheckboxForTreeTable(this);event.cancelBubble=true"/>

																			 <%
																				}
																			 %>
																			</td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="dsn"/></td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="originExchangeOffice"/></td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="destinationExchangeOffice"/></td>
																			<td class="iCargoTableDataTd">
																				<logic:present name="dsnVO" property="mailCategoryCode">
																					
																						<logic:iterate id="oneTimeCatSess" name="oneTimeCatSession" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																							<bean:define id="fieldValue" name="oneTimeCatSess" property="fieldValue"/>
																							<bean:define id="fieldDescription" name="oneTimeCatSess" property="fieldDescription"/>
																							<logic:equal name="dsnVO" property="mailCategoryCode" value="<%=(String)fieldValue%>">
																								<%=(String)fieldDescription%>
																							</logic:equal>
																						</logic:iterate>
																					
																				</logic:present>
																			</td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="mailClass"/></td>
																			<td class="iCargoTableDataTd">
																			 <% String subclassValue = ""; %>
																				<logic:notPresent name="dsnVO" property="mailSubclass">
																				&nbsp;
																				</logic:notPresent>
																				<logic:present name="dsnVO" property="mailSubclass">
																				<bean:define id="despatchSubclass" name="dsnVO" property="mailSubclass" toScope="page"/>
																				<% subclassValue = (String) despatchSubclass;
																				int arrays=subclassValue.indexOf("_");
																				if(arrays==-1){%>

																				<bean:write name="dsnVO" property="mailSubclass"/>
																				<%}else{%>
																				&nbsp;
																				<%}%>
																			</logic:present>
																			</td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="year"/></td>
																			<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="bags" format="####"/></td>
																			<td class="iCargoTableDataTd"><common:write name="dsnVO" property="weight" unitFormatting="true" />
																			</td>
																			<td class="iCargoTableDataTd">
																			<logic:present name="dsnVO" property="documentOwnerCode">
																				 <bean:write name="dsnVO" property="documentOwnerCode"/><label>-</label>
																				 <bean:write name="dsnVO" property="masterDocumentNumber"/>
																			</logic:present>	 
																			</td>
																			<td class="iCargoTableDataTd">
																				<logic:present name="dsnVO" property="csgDocNum">
																					<ihtml:text  componentID="CMP_Mailtracking_Defaults_Mailmanifest_CSGDocNum" indexId="index" readonly="true" property="csgDocNum" name="dsnVO" />
																					<ihtml:hidden property="operationalFlag" value="N"/>
																				<bean:define id="csgDocNum" name="dsnVO" property="csgDocNum"/>
																				<%String field=csgDocNum.toString();%>

																				
																				</logic:present>
																				<logic:notPresent name="dsnVO" property="csgDocNum">
																					<ihtml:text  componentID="CMP_Mailtracking_Defaults_Mailmanifest_CSGDocNum" indexId="index"  property="csgDocNum" maxlength="35" value="" />
																					<ihtml:hidden property="operationalFlag" value="U"/>																							
																				
																				</logic:notPresent>
																			</td>
																			<td class="iCargoTableDataTd">
																				<logic:present name="dsnVO" property="paCode">
																				<bean:define id="paCode" name="dsnVO" property="paCode"/>
																				<%String field=paCode.toString();%>

																				<ihtml:hidden property="paCod" value="<%=field%>"/>
																				</logic:present>
																				<logic:notPresent name="dsnVO" property="paCode" >
																				<ihtml:hidden property="paCod" value="N"/>
																				</logic:notPresent>
																			<bean:write name="dsnVO" property="paCode"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<div class="ic-center">
																					 <logic:notPresent name="dsnVO" property="pltEnableFlag">
																						<!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
																							<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																					 </logic:notPresent>
																					 <logic:present name="dsnVO" property="pltEnableFlag">
																						<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
																							   <!--<input type="checkbox" name="isPrecarrAwb" value="true" checked disabled="true"/>-->
																							   <img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
																						</logic:equal>
																						<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
																							 <!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
																							<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																						</logic:equal>
																					 </logic:present>
																				 </div>
																			</td>
																			<td class="iCargoTableDataTd">
																				<div class="ic-center">
																					 <logic:notPresent name="dsnVO" property="routingAvl">
																						<!--<input type="checkbox" name="routingAvl" value="false" disabled="true"/>-->
																							<img id="routingNotAvl" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																							<ihtml:hidden property="hiddenRoutingAvl" value="N" />
																					 </logic:notPresent>
																					 <logic:present name="dsnVO" property="routingAvl">
																						<logic:equal name="dsnVO" property="routingAvl" value="Y" >
																							<!--<input type="checkbox" name="routingAvl" value="true" checked disabled="true"/>-->
																							   <img id="routingAvl" src="<%=request.getContextPath()%>/images/icon_on.gif" />
																							<ihtml:hidden property="hiddenRoutingAvl" value="Y" />
																						</logic:equal>
																						<logic:equal name="dsnVO" property="routingAvl" value="N">
																							<!--<input type="checkbox" name="routingAvl" value="false" disabled="true"/>-->
																							<img id="routingNotAvl" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																							<ihtml:hidden property="hiddenRoutingAvl" value="N" />
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
													</table>
										</td>
									</tr>
								<% i++;%>
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
				 <ihtml:nbutton property="btnAttachRouting" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_ATTACHROUTING" tabindex="6" accesskey="R">
						<common:message key="mailtracking.defaults.mailmanifest.btn.attachrouting" />
				   </ihtml:nbutton>
				  <ihtml:nbutton property="btnAttachAWB" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_ATTACHAWB" tabindex="11" accesskey="B">
					<common:message key="mailtracking.defaults.mailmanifest.btn.attachawb" />
				  </ihtml:nbutton>				   
					<ihtml:nbutton property="btnAutoAttachAWB" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_AUTOATTACH" tabindex="6" accesskey="W">
						<common:message key="mailtracking.defaults.mailmanifest.btn.autoattach" />
					</ihtml:nbutton>
				  <ihtml:nbutton property="btnDetachAWB" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_DETACHAWB" tabindex="11" accesskey="D">
					<common:message key="mailtracking.defaults.mailmanifest.btn.detachawb" />
				  </ihtml:nbutton>					
				  <ihtml:nbutton property="btnViewMailDetails" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_VIEWMAILDETAILS" tabindex="6" accesskey="V">
					<common:message key="mailtracking.defaults.mailmanifest.btn.viewmaildetails" />
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btnReopenFlight" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_REOPENFLIGHT" tabindex="7" accesskey="H">
					<common:message key="mailtracking.defaults.mailmanifest.btn.reopenflight" />
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btnCloseFlight" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_CLOSEFLIGHT" tabindex="8" accesskey="G">
					<common:message key="mailtracking.defaults.mailmanifest.btn.closeflight" />
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btSave"  componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_SAVE" tabindex="12" accesskey="S">
					<common:message key="mailtracking.defaults.mailmanifest.btnsave" />
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btnGenerateManifest" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_GENERATEMANIFEST" tabindex="13" accesskey="T">
					<common:message key="mailtracking.defaults.mailmanifest.btn.generatemanifest" />
				  </ihtml:nbutton>
				  <ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_CLOSE" tabindex="14" accesskey="O">
					<common:message key="mailtracking.defaults.mailmanifest.btn.close" />
				  </ihtml:nbutton>
			</div>
		</div>
   </div>
</ihtml:form>
</div>

				
		
	</body>
</html:html>
