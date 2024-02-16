<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
		 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
		 toScope="page" />

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="oneTimeValues" />
<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<business:sessionBean id="uldNumbers"
moduleName="uld.defaults"
screenID="uld.defaults.maintaindamagereport"
method="get" attribute="uldNumbers"/>


 <logic:present name="uLDDamageVO">
	<bean:define id="uLDDamageVO" name="uLDDamageVO" />
 </logic:present>




<table  style="width:120%" class="fixed-header-table">
	     <thead >


	       <tr >
	       <td width="3%"><input type="checkbox" name="masterCheckbox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.selectedDmgRowId)" tabindex="35"/></td>
	       <td width="5%" class="iCargoTableHeader">Damage Ref. No</td>
	       <td width="10%" class="iCargoTableHeader">Section</td>
	       <td width="10%" class="iCargoTableHeader">Damage</td>
	       <!-- <td class="iCargoTableHeader" style="width:80px;">Position</td> -->
	       <td width="5%" class="iCargoTableHeader">Severity</td>
	       <td width="10%" class="iCargoTableHeader">Reported Airport</td>
	       <td width="10%" class="iCargoTableHeader">Reported Date</td>
		   <!--commented as part of bug 109606 by A-3767 on 31Mar11-->
	       <!--td class="iCargoTableHeader" style="width:50px;">Closed</td-->
	       <td width="5%" class="iCargoTableHeader">Picture</td>
	       <td width="10%" class="iCargoTableHeader">Faciltiy Type</td>
	       <td width="7%" class="iCargoTableHeader">Location</td>
	       <td width="10%"  class="iCargoTableHeader">Party Type</td>
		   <td width="5%" class="iCargoTableHeader">Party</td>
	       <td width="10%" class="iCargoTableHeader">Remarks</td>
	       </tr>
		</thead>
	    <tbody>
					<logic:present name="uLDDamageVO" property="uldDamageVOs">
					  <bean:define id="uldDamageVOs" name="uLDDamageVO" property="uldDamageVOs" />
					  <logic:iterate id="uldDamageVO" name="uldDamageVOs" indexId="dmgindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" >
					  <common:rowColorTag index="dmgindex">
					  <logic:notEqual name="uldDamageVO" property="operationFlag" value="D">
					  
	                  <tr>
	                    <td  class="iCargoTableTd ic-center">
	                                         <!-- <html:checkbox property="selectedDmgRowId" value="<%=String.valueOf(dmgindex)%>" onclick="validateCheckboxItem(this,this.form,'<%=String.valueOf(dmgindex)%>','selectedDmgRowId');"/> -->
	                       <input type = "checkbox" name="selectedDmgRowId" value="<%=String.valueOf(dmgindex)%>" id="chkCheckAll_1" onclick="toggleTableHeaderCheckbox('selectedDmgRowId',this.form.masterCheckbox)" />
	                    </td>
	                    <td  class="iCargoTableDataTd">
	                    <logic:present name="uldDamageVO" property="damageReferenceNumber">
						<bean:define id="damageReferenceNumber" name="uldDamageVO" property="damageReferenceNumber" />
					    <logic:notEqual name="uldDamageVO" property="damageReferenceNumber" value="0">
	                           <bean:write name="uldDamageVO" property="damageReferenceNumber"/>
						</logic:notEqual>			
	                    </logic:present>
	                    </td>
						<td  class="iCargoTableDataTd" align="center">
						       <logic:present name="uldDamageVO" property="section">
							 <logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="uld.defaults.section">
									 <% String sectionvalue[] = uldDamageVO.getSection().split(",");
									 for(int i=0 ; i< sectionvalue.length;i++) {%>
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														
																<%if( ((String)fieldValue).equals(sectionvalue[i]) ){%>
																<%=(String)fieldDescription%>
																<%}%>
															</logic:present>
														</logic:iterate>
														<% } %>
													</logic:equal>
													</logic:iterate>
													</logic:present>
							</logic:present>
	                      </td>
	                      <td  class="iCargoTableDataTd" align="center">
					       <logic:present name="uldDamageVO" property="damageDescription">
						   <bean:define id="damageDescription" name="uldDamageVO" property="damageDescription" />
						
						   <% String damages[] = uldDamageVO.getDamageDescription().split(",");
						
						   for(int i=0 ; i< damages.length;i++) {%>
							<%=damages[i]%>
							<br>
						
						   <% } %>
						<!--<ihtml:text property="DamageDescriptionss" value ="<%=(String)damageDescription%>"  componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_DAMAGEDESCRIPTION"  readonly="true"/>     -->
						<!-- <bean:write name="uldDamageVO" property="damageDescription"/> -->
						  </logic:present>
	                     </td>
	                                       
	                                      <!--  <td  class="iCargoTableDataTd">
	                                       <logic:present name="uldDamageVO" property="position">
						 <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
						<logic:equal name="parameterCode" value="uld.defaults.position">
							<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<logic:equal name="uldDamageVO" property="position" value="<%=(String)fieldValue%>">
									<%=(String)fieldDescription%>
									</logic:equal>
								</logic:present>
							</logic:iterate>
						</logic:equal>
						</logic:iterate>
						</logic:present>
	                                       </logic:present>
	                                       </td> -->
	                     <td  class="iCargoTableDataTd">
					       <logic:present name="uldDamageVO" property="severity">
							 <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
							<logic:equal name="parameterCode" value="uld.defaults.damageseverity">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue" property="fieldValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<logic:equal name="uldDamageVO" property="severity" value="<%=(String)fieldValue%>">
										<%=(String)fieldDescription%>
										</logic:equal>
									</logic:present>
								</logic:iterate>
							</logic:equal>
							</logic:iterate>
							</logic:present>
					       </logic:present>
	                      </td>
	                      <td align="right" class="iCargoTableDataTd">
	                                       <logic:present name="uldDamageVO" property="reportedStation">
					                         <bean:define id="reportedStation" name="uldDamageVO" property="reportedStation" />
	                                         <bean:write name="uldDamageVO" property="reportedStation"/>
	                                       </logic:present>
	                      </td>
	                      <td class="iCargoTableTd">
					       		           <logic:present name="uldDamageVO" property="reportedDate">
					       					      		 <%=uldDamageVO.getReportedDate().toDisplayDateOnlyFormat()%>
					       				   </logic:present>
	                      </td>
	                                       
	                                       <!--td  class="iCargoTableTd">
					       						<div align="center">
					       	     						<logic:present name="uldDamageVO" property="closed">
					       									<bean:define id="closed" name="uldDamageVO" property="closed" />
					       									<logic:equal name="closed" value="true">
					       										<input type="checkbox" name="closed" checked  disabled="true"/>
					       									</logic:equal>
					       									<logic:equal name="closed" value="false">
					       										<input type="checkbox" name="closed"  disabled="true"/>
					       									</logic:equal>
					       									</logic:present>
					       									<logic:notPresent name="uldDamageVO" property="closed">
					       										<input type="checkbox" name="closed"  disabled="true"/>
					       									</logic:notPresent>
					       	                    </div>
						</td-->
						
						<td  class="iCargoTableDataTd" align="center">
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
						<td class="iCargoTableDataTd" align="center">
						<logic:present name="uldDamageVO" property="facilityType">
							<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>												
												<logic:equal name="uldDamageVO" property="facilityType" value="<%=(String)fieldValue%>">
													<%=(String)fieldDescription%>
												</logic:equal>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
							</logic:present>
					    </logic:present>
						</td>
						<td align="right" class="iCargoTableDataTd">
							<logic:present name="uldDamageVO" property="location">
								<bean:write name="uldDamageVO" property="location"/>
							</logic:present>
	                    </td>
						<td>
							<logic:present name="uldDamageVO" property="partyType">
								<logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="uld.defaults.PartyType">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<logic:equal name="uldDamageVO" property="partyType" value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
									</logic:equal>
												</logic:present>
											</logic:iterate>
									</logic:equal>
									</logic:iterate>
									</logic:present>
							</logic:present>
						</td>
					    <td  class="iCargoTableDataTd">
							<logic:present name="uldDamageVO" property="party">
								<bean:write name="uldDamageVO" property="party"/>
							</logic:present>
	                    </td>

					    <td align="right" class="iCargoTableDataTd">
						    <logic:present name="uldDamageVO" property="remarks">
					        <bean:define id="remarks" name="uldDamageVO" property="remarks" />
					        <ihtml:text property="DamageRemarks" value ="<%=(String)remarks%>"   readonly="true"/>     
	     					<!-- <bean:write name="uldDamageVO" property="remarks"/> -->
	     					</logic:present>
	                    </td>
	                    </tr>
					</logic:notEqual>
					</common:rowColorTag>
				    </logic:iterate>
		  		    </logic:present>
	                </tbody>
	                </table>