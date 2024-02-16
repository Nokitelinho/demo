	 <%@ include file="/jsp/includes/tlds.jsp" %>
	 <%@ page import = "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" %>

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
							<%
                            String rowCount = String.valueOf((Integer)request.getAttribute("rowCount"));
                            %>
							<bean:define id="_rowCount" name="rowCount" toScope="page" />
							<bean:define id="uldDamageVO" name="_uldDamageVO" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" />
							<logic:present name="uldDamageVO" property="facilityType">
													<bean:define id="facilityType" name="uldDamageVO" property="facilityType" />
													<%=(String)facilityType%>
													<td  class="iCargoTableDataTd ic-center" width="8%">
													<ihtml:select  property="facilityType" indexId="rowCount" value="<%=(String)facilityType%>" id="facilityType">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
													<logic:notPresent name="uldDamageVO" property="facilityType">
													<td class="iCargoTableDataTd ic-center" width="8%">
													<ihtml:select  property="facilityType" indexId="rowCount"  id="facilityType">
			                                        <ihtml:option value="">Select</ihtml:option>
											         <logic:present name="OneTimeVOs">
				                                           <logic:iterate id="oneTimeValue" name="OneTimeVOs">
					                                       <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                               <logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
							<logic:present name="uldDamageVO" property="location">
														<bean:define id="location" name="uldDamageVO" property="location" />
									<td  width="8%">
									 <ihtml:text property="location" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_LOCATION"  maxlength="15" indexId="rowCount" value="<%=(String)location%>" id="location" style="width:71px;"/>
	                                <button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov<%=rowCount%>" ></button>
									<!-- <img src="<%= request.getContextPath()%>/images/lov.gif" name="facilitycodelov"  id="facilitycodelov" alt="Party LOV"/> -->
														</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="location">
									<td width="8%">
															<ihtml:text property="location" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_LOCATION"  maxlength="15" indexId="rowCount" id="location"/>
	                                                        <button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"></button>
									<!-- <img src="<%= request.getContextPath()%>/images/lov.gif" name="facilitycodelov"  id="facilitycodelov" alt="Party LOV"/> -->
														</td>
													</logic:notPresent>
													<td  class="iCargoTableDataTd" align="center" width="6%">
												   <logic:present name="uldDamageVO" property="picturePresent">
													<bean:define id="picturePresent" name="uldDamageVO" property="picturePresent" />
													<logic:equal name="picturePresent" value="true">
													<logic:present name="uldDamageVO" property="sequenceNumber">
															<bean:define id="sequenceNumber" name="uldDamageVO" property="sequenceNumber" />
													<img src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" id="imagelov" onclick="viewPic(<%=sequenceNumber%>);" alt="Picture LOV"/>
							                               </logic:present>
					                                       </logic:equal>
													<logic:equal name="picturePresent" value="false">
													</logic:equal>
													</logic:present>
														</td>
													<logic:present name="uldDamageVO" property="partyType">
														<bean:define id="partyType" name="uldDamageVO" property="partyType" />
														<td  class="iCargoTableDataTd ic-center" width="7%">
														<ihtml:select  property="partyType"  id="partyType" indexId="_rowCount" value="<%=(String)partyType%>">
			                                              <ihtml:option value="">Select</ihtml:option>
											           <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												     <logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
					                                      <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                              <logic:equal name="parameterCode" value="uld.defaults.PartyType">
					                                      <bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="partyType">
														<td class="iCargoTableDataTd ic-center" width="7%">
													<ihtml:select  property="partyType"  indexId="_rowCount"  id="partyType" >
			                                              <ihtml:option value="">Select</ihtml:option>
											        <bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
												    <logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
					                                      <bean:define id="parameterCode" name="oneTimeValue" property="key" />
							                              <logic:equal name="parameterCode" value="uld.defaults.PartyType">
					                                      <bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
													</logic:notPresent>
												   <logic:present name="uldDamageVO" property="party">
													  <td  class="iCargoTableDataTd ic-center" width="7%">
														<bean:define id="party" name="uldDamageVO" property="party" />
														<ihtml:text property="party" value="<%=(String)party%>" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" id="party" indexId="_rowCount" style="width:60px;"/>			
		                                                <div class="lovImg">			
			                                            <img src="<%= request.getContextPath()%>/images/lov.png" name="partycodelov"  id="partycodelov<%=rowCount%>" alt="Party LOV" indexId="_rowCount"  />
														</div>
													</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="party">
														<td class="iCargoTableDataTd ic-center" width="7%">
														<ihtml:text property="party" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" indexId="rowCount" id="party" style="width:60px;"/>			
		                                                 <div class="lovImg">			
			                                             <img src="<%= request.getContextPath()%>/images/lov.png" name="partycodelov"  id="partycodelov<%=rowCount%>" alt="Party LOV" />
														 </div>
														</td>
													</logic:notPresent>
													<logic:present name="uldDamageVO" property="remarks">
													
													 <td  class="iCargoTableDataTd ic-center" width="12%" styleClass="width_100">
														<bean:define id="remarks" name="uldDamageVO" property="remarks" />
														<ihtml:text property="remarks" value="<%=(String)remarks%>" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" indexId="_rowCount"/>			
		                                                
													</td>
													</logic:present>
													<logic:notPresent name="uldDamageVO" property="remarks">
														<td class="iCargoTableDataTd ic-center" width="12%" styleClass="width_100">
														<ihtml:text property="remarks" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" indexId="_rowCount"/>			
		                                                </td>
													</logic:notPresent>
													