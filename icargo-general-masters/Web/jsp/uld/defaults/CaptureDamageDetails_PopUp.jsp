<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: CaptureDamageDetails_PopUp.jsp
* Date				: 21/3/2018
* Author(s)			: A-8176
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import ="com.ibsplc.icargo.framework.session.ApplicationSessionImpl"%>
<%@ page import ="com.ibsplc.icargo.framework.util.time.Location"%>
<%@ page import ="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Calendar" %>
<html:html>
<head> 
	
	<title><common:message bundle="updatemultipledamageuldresources" key="uld.defaults.updatemultipleuld.popuplbl.heading" /></title>
	<meta name="decorator" content="popuppanelrestyledui">
</head>

<body>
	
	
			
	<common:include type="script" src="/js/uld/defaults/CaptureDamageDetails_PopUp_Script.jsp" />
	<div class="iCargoPopUpContent" style="width:100%;">
		<ihtml:form action="/uld.defaults.misc.showDamageDetails.do"  enctype="multipart/form-data" styleClass="ic-main-form">
			<ihtml:hidden property="selectedRow" />
			<ihtml:hidden property="selectedDmgRow" />
			<ihtml:hidden property="uldNumbers" />
			<ihtml:hidden property="operationalStatus" />
			<ihtml:hidden property="damagedStatus" />
			<ihtml:hidden property="statusFlag"/>
			 <ihtml:hidden property="seqNum"/>
			<bean:define id="form"
					 name="UpdateMultipleULDDetailsForm"
					 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"
					 toScope="page" />
		<business:sessionBean
		id="OneTimeVOs"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="oneTimeValues" />
		<business:sessionBean
		id="SelectedDamageRepairDetailsVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="selectedDamageRepairDetailsVO" />
		<business:sessionBean
		id="ReportedDate"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="reportedDate" />
		<business:sessionBean
		id="reportedAirport"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="reportedAirport" />
		<business:sessionBean id="dmgDescCodes" moduleName="uld.defaults"
		  screenID="uld.defaults.updatemultipleulddetails" method="get"
		  attribute="ULDDamageChecklistVO" />
            <logic:present name="SelectedDamageRepairDetailsVO">
				<bean:define id="SelectedDamageRepairDetailsVO" name="SelectedDamageRepairDetailsVO" />
			</logic:present>
		
	
		<bean:define id="reportedAirport" name="reportedAirport" type="java.lang.String"/>
		
		<bean:define id="date" name="ReportedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
		

		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				
			</span>
			<div class="ic-main-container">
				
				<div class="ic-row">						
					<div class="ic-col-60">
						<h4>
							
						</h4>
					</div>
					<div class="ic-col-40">
						<div class="ic-button-container">
							<ul class="ic-list-link">			 					
								<li>
									<a name="AddULD" id="AddULD" href="#" class="iCargoLink"  >Add</a>
								</li>&nbsp;|&nbsp
								<li>
									<a name="DeleteULD" id="DeleteULD" href="#" class="iCargoLink">Delete</a>	
								</li>										
							</ul>
						</div>							
					 </div>					
				</div>
				<div class="ic-row">
					<div class="tableContainer ic-center" id="div1"  style="width:100%;height:240px">
						<table id="uldDetailsTable" style="width:100%;" class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingLeft" >
									<td width="3%" class="iCargoTableHeaderLabel ic-center">
										<input type="checkbox" name="selectAllULDs" value="checkbox" onclick="updateHeaderCheckBox(this.form, this, this.form.selectedRowId);">
									</td>
									<td width="10%" class="iCargoTableHeaderLabel ic-center">
										Section
									</td>
									<td width="8%" class="iCargoTableHeaderLabel ic-center">
										Description
									</td>
									<td width="6%" class="iCargoTableHeaderLabel ic-center">
										Severity
									</td>

									<td width="6%" class="iCargoTableHeaderLabel ic-center">
										Reported Airport
									</td>
									<td width="8%" class="iCargoTableHeaderLabel ic-center">
										Reported Date
									</td>
									<td width="10%" class="iCargoTableHeaderLabel ic-center">
										Point of Notice
									</td>
									<td width="8%" class="iCargoTableHeaderLabel ic-center">
										Facility Type
									</td>
									<td width="8%" class="iCargoTableHeaderLabel ic-center">
										Location
									</td>
									<td width="6%" class="iCargoTableHeaderLabel ic-center">
										Picture
									</td>
									<td width="7%" class="iCargoTableHeaderLabel ic-center">
										Party Type
									</td>
									<td width="7%" class="iCargoTableHeaderLabel ic-center">
										Party
									</td>
									<td width="12%" class="iCargoTableHeaderLabel ic-center">
										Remarks
									</td>
								</tr>
							</thead>
									   
							<tbody id="uldTableBody">
							
								<jsp:include page="CaptureDamagePopup_TemplateRow.jsp"/>		
		
		                       




									<!-- templateRow -->
									<bean:define id="templateRowCount" value="0"/>
									<tr template="true" id="uldTemplateRow" style="display:none">
										<ihtml:hidden property="ratingUldOperationFlag" value="NOOP" indexId="rowCount"/>
										<td class="iCargoTableDataTd ic-center" width="3%">
											<input type="checkbox" name="selectedDmgRowId" id="selectedDmgRowId">
										</td>

										<td width="10%" style="width:100%">
									<ihtml:select  property="section" indexId="rowCount" id="section" value="" style="width:100%">
			                                 <ihtml:option value="">Select</ihtml:option>
											 <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="uld.defaults.section">
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
								            </ihtml:select>
										</td>
										<td class="iCargoTableDataTd ic-center" width="8%">
											<ihtml:select property="description"  tabindex="2"  indexId="rowCount" id="description" styleClass="iCargoMediumComboBox" value="">
										 <ihtml:option value="">Select</ihtml:option>
										   <logic:present name="dmgDescCodes">
											<logic:iterate id="dmgchecklistvo" name="dmgDescCodes">
												<bean:define id="dmgchecklistvo" name="dmgchecklistvo" type="ULDDamageChecklistVO" />
												<bean:define id="value" name="dmgchecklistvo" property="description"/>
												<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
											</logic:iterate>
										 </logic:present>
									</ihtml:select>
										</td>
										<td class="iCargoTableDataTd ic-center" width="6%">
											<ihtml:select  property="severity" indexId="rowCount" id="severity">
			                                <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="uld.defaults.damageseverity">
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
								            </ihtml:select>
										</td>
										
											<td class="iCargoTableDataTd ic-center" width="6%" class="lovImgTbl valignT">
										
										 <ihtml:text property="repStn" value="<%=reportedAirport%>" indexId="rowCount" id="repStn" style="width:60px;" />
				                          <div class="lovImgTbl valignT" >
					                      <img src="<%= request.getContextPath()%>/images/lov.png" width="18" height="18" name="airportLovImg"  id="airportLovImg" alt="Airport LOV"/>
				                         </div>
									</td>	
									<td class="iCargoTableDataTd ic-center" width="8%">
									<%String reptdDate="";%>
									<%  reptdDate=TimeConvertor.toStringFormat(date.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
									<ibusiness:calendar property="reportedDate" 
												type="image" 
												value="<%=reptdDate%>"
												id="reportedDate" 
												indexId="rowCount"  style="width:77px;"/>
									</td>	

									<!-- Added by A-8368 as part of user story - IASCB-35533 starts-->	
										<td class="iCargoTableDataTd ic-center" width="10%">
											<ihtml:select  property="damageNoticePoint"  indexId="rowCount" id="damageNoticePoint" styleClass="width_100">
			                                 <ihtml:option value="">Select</ihtml:option>
											 <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="operations.shipment.pointofnotice">
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
								            </ihtml:select>
										</td>
									<!-- Added by A-8368 as part of user story - IASCB-35533 ends-->									
										<td class="iCargoTableDataTd ic-center" width="8%">
											<ihtml:select  property="facilityType"  indexId="rowCount" id="facilityType">
			                                 <ihtml:option value="">Select</ihtml:option>
											 <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
								            </ihtml:select>
										</td>
									<td width="8%">
									 <ihtml:text property="location" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_LOCATION"  maxlength="15" indexId="rowCount" value="" id="location" style="width:71px;"/>
	                                <button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"></button>
									<!-- <img src="<%= request.getContextPath()%>/images/lov.gif" name="facilitycodelov"  id="facilitycodelov" alt="Party LOV"/> -->
									</td>
									<td width="6%">
									 </td>
									 <td width="7%">
									<ihtml:select  property="partyType"  indexId="rowCount" id="partyType">
			                                 <ihtml:option value="">Select</ihtml:option>
											 <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="uld.defaults.PartyType">
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
								            </ihtml:select>
									 </td>
									 <td width="7%">
									 <ihtml:text property="party" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" indexId="rowCount" value="" id="party" style="width:60px;"/>			
		                             <div class="lovImgTbl valignT">			
			                         <img src="<%= request.getContextPath()%>/images/lov.png"  width="18" height="18" name="partycodelov"  id="partycodelov" alt="Party LOV" />
		                             </div>
									 </td>
									  <td width="12%" styleClass="width_100">
									 <ihtml:text property="remarks" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="14" indexId="rowCount" value="" id="remarks"/>			
									 </td>
									</tr>
									<!--template row ends-->
	       
							</tbody>
						</table>
					</div>
			
				
            
					 	
					  </div>
			    <div class="ic-row">
				<div class="ic-col-80">
      		  	      <div class="ic-input ic-split-50 ic-mandatory"> 
					   <label>	    
					  <common:message  key="uld.defaults.updatemultipleuld.lbl.Upload" />
					   </label>
						<ihtml:file property="dmgPicture" styleClass="iCargoTextFieldExtraLong" title="Select an damage picture" maxlength="75" style="width:180px"/>
					  </div>
					   <div> 
					   <ihtml:button property="btnAddPic" componentID="BTN_ULD_DEFAULTS_UPDATEMULTUPLEULD_ADDPIC">
						 <common:message key="uld.defaults.updatemultipleuld.popup.btn.addpic" />
						</ihtml:button>
					   </div>
				</div>
				<div class="ic-col-20">
				<logic:present name="SelectedDamageRepairDetailsVO">
				<logic:present name="SelectedDamageRepairDetailsVO" property="damagePoints">
				<bean:define id="points" name="SelectedDamageRepairDetailsVO" property="damagePoints" />
				<div class="ic-row" >
				 <div >		
				<label>		
		      <common:message key="uld.defaults.updatemultipleuld.popup.totalpoints" />
	            </label>
		        <ihtml:text property="totalPoints" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTALPOINTS"  value="<%=points.toString()%>" />
			   </div>	
			   </div>
			   </logic:present>
			   <logic:notPresent name="SelectedDamageRepairDetailsVO" property="damagePoints">
				<div class="ic-row" >
				 <div >		
				<label>		
		         <common:message key="uld.defaults.updatemultipleuld.popup.totalpoints" />
	            </label>
		        <ihtml:text property="totalPoints" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTALPOINTS" style="text-align:right" maxlength="15"  id="totalPoints"/>
			   </div>	
			   </div>						
				</logic:notPresent>
				</logic:present>
				</div>
				
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row" >
					<div class="ic-button-container">
						<ihtml:button id="btnOk" property="btnOk" componentID="BTN_ULD_DEFAULTS_UPDATEMULTUPLEULD_POPUP_DMGSAVE">
							<common:message key="uld.defaults.updatemultipleuld.popup.btn.ok" />
						</ihtml:button>

						<ihtml:button id="btnClose" property="btnClose" componentID="BTN_ULD_DEFAULTS_UPDATEMULTUPLEULD_POPUP_DMGCLOSE">
							<common:message key="uld.defaults.updatemultipleuld.popup.btn.close" />
						</ihtml:button>
					</div>
				</div>
			</div>
		</ihtml:form>
	</div>
		
				
		  
	</body>
</html:html>
