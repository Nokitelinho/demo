<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: CaptureDamagePopup_TemplateRow.jsp
* Date				: 13/03/2018
* Author(s)			: A-8176
--%>
	 
	 <%@ include file="/jsp/includes/tlds.jsp" %>
	 <%@ page import = "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO" %>
	 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
     <%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
     <%@ page import = "java.util.Calendar" %>
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
		id="ReportedAirport"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="reportedAirport" />
		
		<business:sessionBean id="dmgDescCodes" moduleName="uld.defaults"
		  screenID="uld.defaults.updatemultipleulddetails" method="get"
		  attribute="ULDDamageChecklistVO" />
        <bean:define id="reportedAirport" name="ReportedAirport" type="java.lang.String"/>
		<bean:define id="date" name="ReportedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
			
                      <logic:present name="SelectedDamageRepairDetailsVO">
									<logic:present name="SelectedDamageRepairDetailsVO" property="uldDamageVOs">
									<% int uldIndex=0; %>
										<bean:define id="uldDamageVOs" name="SelectedDamageRepairDetailsVO" property="uldDamageVOs" />
											<logic:iterate id="uldDamageVO" name="uldDamageVOs" indexId="rowCount">
												<% request.setAttribute("rowCount",rowCount); %>
												<bean:define id="_uldDamageVO" name="uldDamageVO" toScope="request" />
												<logic:notEqual name="uldDamageVO" property="operationFlag" value="D">
												<tr class="iCargoTableCellsLeftRowColor1">
													<td class="iCargoTableDataTd ic-center" width="3%">
														<logic:notEqual name="uldDamageVO" property="operationFlag" value="">
															<bean:define id="operationFlag" name="uldDamageVO" property="operationFlag" />
															<input type="hidden" name="ratingUldOperationFlag" value="<%=operationFlag%>" />
														</logic:notEqual>
														<logic:equal name="uldDamageVO" property="operationFlag" value="">
															<input type="hidden" name="ratingUldOperationFlag" value="N" />
														</logic:equal>
														<input type="checkbox" name="selectedDmgRowId" id="selectedDmgRowId<%=rowCount%>" onclick="toggleTableHeaderCheckbox('selectedDmgRowId', this.form.selectAllULDs);" indexId="rowCount">
													</td>

												<logic:present name="uldDamageVO" property="section">
												<bean:define id="section1" name="uldDamageVO" property="section" />
													<td class="iCargoTableDataTd ic-center" width="10%">
												
														  <ihtml:select  property="section"  value="<%=(String)section1%>"  indexId="rowCount" id="section"  style="width:100%">
			                                              <ihtml:option value="">Select</ihtml:option>
			                                              <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.section">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
			                                             </ihtml:select>
													</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="section">
													<td class="iCargoTableDataTd ic-center" width="10%" style="width:100%">
													
														  <ihtml:select  property="section"  indexId="rowCount" id="section">
			                                              <ihtml:option value=""  style="width:100%">Select</ihtml:option>
			                                              <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.section">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
			                                             </ihtml:select>
													</td>
													</logic:notPresent>
												<logic:present name="uldDamageVO" property="damageDescription">
												
												  <bean:define id="damagedescription" name="uldDamageVO" property="damageDescription" />
													<td class="iCargoTableDataTd ic-center" width="8%">
												
												<ihtml:select property="description"  tabindex="2"   styleClass="iCargoMediumComboBox"  indexId="rowCount" id="description">
										        <html:option value="<%=(String)damagedescription%>"><%= (String)damagedescription %></html:option>
											    </ihtml:select>
													</td>
													</logic:present>
												<logic:notPresent name="uldDamageVO" property="damageDescription">
													<td class="iCargoTableDataTd ic-center" width="8%">
													
												<ihtml:select property="description"  tabindex="2"   styleClass="iCargoMediumComboBox"  indexId="rowCount" id="description">
										          <logic:present name="dmgDescCodes">
											   <logic:iterate id="damagechecklistvo" name="dmgDescCodes">
												<bean:define id="damagechecklistvo" name="damagechecklistvo" type="ULDDamageChecklistVO" />
												<bean:define id="value" name="damagechecklistvo" property="sectionDescription"/>
												<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
											</logic:iterate>
										 </logic:present>
									</ihtml:select>
													</td>
													</logic:notPresent>
													<logic:present name="uldDamageVO" property="severity">
													<bean:define id="severity" name="uldDamageVO" property="severity" />
													<td  class="iCargoTableDataTd ic-center" width="6%">
													<ihtml:select  property="severity" indexId="rowCount" value="<%=(String)severity%>"  id="severity">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.damageseverity">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
								                   </ihtml:select>
														</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="severity">
													<td class="iCargoTableDataTd ic-center" width="6%">
													<ihtml:select  property="severity" indexId="rowCount"  id="severity">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.damageseverity">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
								                   </ihtml:select>
													</td>
													</logic:notPresent>
													
													<logic:present name="uldDamageVO" property="reportedStation">
													
														<bean:define id="reportedStation" name="uldDamageVO" property="reportedStation" />
													
														<td  class="iCargoTableDataTd ic-center" width="6%" class="lovImgTbl valignT">
															<ihtml:text id="repStn" style="width:60px;" styleId="repStn" property="repStn" componentID="CMP_Operations_Shipment_RatingDetails_slacPieces" value="<%=String.valueOf(reportedStation)%>"  maxlength="4" indexId="rowCount"/>
															<div class="lovImgTbl valignT" style="display:inline-block">
					                                         <img src="<%= request.getContextPath()%>/images/lov.png" width="18" height="18" name="airportLovImg" id ="airportLovImg<%=uldIndex%>" alt="Airport LOV"/ style="width: 60px;">
				                                             </div>
														</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="reportedStation">
														<td class="iCargoTableDataTd ic-center" width="6%" class="lovImgTbl valignT">
														<div class="ic-input">
															<ihtml:text property="repStn" style="width:60px;" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN"  maxlength="3" indexId="rowCount" 
															value="<%=reportedAirport%>"  id="repStn"/>
				                                            <div class="lovImgTbl valignT">
					                                         <img src="<%= request.getContextPath()%>/images/lov.png" width="18" height="18" name="airportLovImg" id ="airportLovImg<%=uldIndex%>" alt="Airport LOV"/>
				                                             </div>
															 </div>
														</td>
													</logic:notPresent>
													<logic:present name="uldDamageVO" property="reportedDate">
													<%String reptdDate="";%>
													<bean:define id="repDate" name="uldDamageVO" property ="reportedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
														<%  reptdDate=TimeConvertor.toStringFormat(repDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<td  class="iCargoTableDataTd ic-center" width="8%">
															<ibusiness:calendar property="reportedDate" type="image" id="reportedDate" value="<%=reptdDate%>" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY_REPORTEDDATE" indexId="rowCount" style="width:77px;"/>
														</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="reportedDate">
														<td class="iCargoTableDataTd ic-center" width="8%">
														<%String reptdDate="";%>
									                    <%  reptdDate=TimeConvertor.toStringFormat(date.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
									                    <ibusiness:calendar id="reportedDate" property="reportedDate" type="image" value="<%=reptdDate%>" indexId="rowCount" style="width:77px;"/>
														</td>
													</logic:notPresent>
													
													<!-- Added by A-8368 as part of user story - IASCB-35533 starts-->
													<logic:present name="uldDamageVO" property="damageNoticePoint">
													<bean:define id="damageNoticePoint" name="uldDamageVO" property="damageNoticePoint" />
													<%=(String)damageNoticePoint%>
													<td  class="iCargoTableDataTd ic-center" width="10%">
													<ihtml:select  property="damageNoticePoint" indexId="rowCount" value="<%=(String)damageNoticePoint%>" id="damageNoticePoint" styleClass="width_100">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="operations.shipment.pointofnotice">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
								                   </ihtml:select>
														</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="damageNoticePoint">
													<td class="iCargoTableDataTd ic-center" width="10%">
													<ihtml:select  property="damageNoticePoint" indexId="rowCount"  id="damageNoticePoint" styleClass="width_100">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="operations.shipment.pointofnotice">
					                                       <bean:define id="parameterValues" name="oneTimeValue" property="value" />
						                                   <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							                                <logic:present name="parameterValue">
							                                <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								                            <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								                            <ihtml:option value="<%=(String)fieldValue%>">
									                         <%=(String)fieldDescription%>
								                            </ihtml:option>
							                                </logic:present>
						                                     </logic:iterate>
					                                       </logic:equal>
				                                          </logic:iterate>
			                                             </logic:present>
								                   </ihtml:select>
													</td>
													</logic:notPresent>
													<!-- Added by A-8368 as part of user story - IASCB-35533 ends-->
													
													
															
							
											 
															
							
											 
										<jsp:include page="CaptureDamagePopup_TemplateRowDetails.jsp"/>		
										
												</tr>
												</logic:notEqual>
													
											<% uldIndex++; %>
											</logic:iterate>

										</logic:present>
									</logic:present>