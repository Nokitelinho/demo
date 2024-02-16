<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamage_DamageDetailsPart1.jsp
* Date				: 24-Sep-2018
* Author(s)			: A-7955
 --%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"%>
<%@ page
	import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page
	import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page
	import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@ page
	import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO"%>

<%@ include file="/jsp/includes/tlds.jsp"%>


<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="oneTimeValues" />
	
	<business:sessionBean id="KEY_ULDDMGCHKLST"
							moduleName="uld.defaults"
							screenID="uld.defaults.ux.maintaindamagereport"
							method="get"
				attribute="ULDDamageChecklistVO" /> 
	<business:sessionBean id="KEY_ULDDMGCHKLSTMAP"
							moduleName="uld.defaults"
							screenID="uld.defaults.ux.maintaindamagereport"
							method="get"
				attribute="damageChecklistMap" />
	
<%
int index=(Integer)request.getAttribute("index");
ULDDamageVO uLDDamageDtlVO = (ULDDamageVO)request.getAttribute("uLDDamageDtlVO");
   String opFlag = (String)request.getAttribute("oprtnFlg");
   %>

<tr>
		<%if("I".equalsIgnoreCase(opFlag)) { %>
				<html:hidden property="tempOperationFlag" value="I"/>
				
		<% } else {%>
				<ihtml:hidden property="tempOperationFlag" value="U"/>
		<%}%>
				<td class="text-center" >
					 <logic:present name="uLDDamageDtlVO" property="damageReferenceNumber">
						<bean:define id="damageReferenceNumber" name="uLDDamageDtlVO" property="damageReferenceNumber" />
						<ihtml:text property="dmgRefNo" value="<%=String.valueOf(damageReferenceNumber)%>" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REFNO" style="text-align:right" readonly="true"/>											
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="damageReferenceNumber">
						<ihtml:text property="dmgRefNo" value="" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REFNO" readonly="true"/>											
					</logic:notPresent>
				</td>
				<td class="text-center">						
				<logic:present name="uLDDamageDtlVO" property="section">
				<%String cmpId= "CMB_ULD_DEFAULTS_MAINTAINDMG_SECTION"+String.valueOf(index);%>
					<bean:define id="section" name="uLDDamageDtlVO" property="section" />
					<ihtml:select name="uLDDamageDtlVO" id="<%=cmpId%>" property="section" value="<%=(String)section%>" onchange="populateOnChange(this);" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SECTION">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="uld.defaults.section">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:present>
				</td>
				<td class="text-center">
					 <div id="damageType<%=index%>">
					 <logic:present	name="uLDDamageDtlVO" >
						<logic:present name="uLDDamageDtlVO" property="damageDescription">
						 <bean:define id="damageDescription" name="uLDDamageDtlVO" property="damageDescription"/> 						
						 <ihtml:select property="description" style="width:30vh;" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DESCRIPTION" indexId="index" value="<%=(String)damageDescription%>"> 
							<html:option value=""><common:message key="combo.select"/></html:option>
						  <logic:present name="KEY_ULDDMGCHKLSTMAP">							 
								<logic:iterate id="dmgchklst" name="KEY_ULDDMGCHKLSTMAP" >
									<bean:define id="section" name="uLDDamageDtlVO" property="section"/>
									<logic:equal name="dmgchklst" property="key" value="<%=(String)section%>">
										<bean:define id="damageDescs" name="dmgchklst" property="value" type="java.util.Collection"/>
											<logic:iterate id="damageDes" name="damageDescs" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO">
												<html:option value="<%=damageDes.getDescription()%>"><%=damageDes.getDescription()%></html:option>
											</logic:iterate>
									</logic:equal>
								</logic:iterate>
						  </logic:present> 
						</ihtml:select>	
					</logic:present>
						</logic:present>
					</div>
				</td>
				<td class="text-center">							
				<logic:present name="uLDDamageDtlVO" property="severity">
				<bean:define id="severity" name="uLDDamageDtlVO" property="severity" />
					<ihtml:select name="uLDDamageDtlVO" property="severity"  value="<%=(String)severity%>" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY">
						<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
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
				</logic:present>
				<logic:notPresent name="uLDDamageDtlVO" property="severity">
					<ihtml:select name="uLDDamageDtlVO" property="severity"  componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY">
						<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.damageseverity">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:notPresent>
				</td>
				<td class="text-center">
					<span class="col col-12">
						<logic:present name="uLDDamageDtlVO" property="reportedStation">
							<bean:define id="reportedStation" name="uLDDamageDtlVO"	property="reportedStation" />
							<ihtml:text property="repStn" id="repStn_<%=index%>" value="<%=String.valueOf(reportedStation)%>" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" styleClass="span6 all-caps" maxlength="3" />											
						</logic:present> 
						<logic:notPresent name="uLDDamageDtlVO" property="reportedStation">
							<ihtml:text property="repStn" id="repStn_<%=index%>" value=""	componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" styleClass="span6 all-caps" maxlength="3" />
						</logic:notPresent> 
						<span class="ico-btn"><i class="icon list-item" id="repStnlov_<%=index%>" name="repStnlov" onclick ="showReportedAirport(this)"></i></span>
					</span>
				</td>
				<td class="text-center">
				<span class="col col-12">
					<logic:present	name="uLDDamageDtlVO" property="reportedDate">
						<bean:define id="reportedDate" name="uLDDamageDtlVO" property="reportedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
						<%
							String repDate = TimeConvertor.toStringFormat(((LocalDate) reportedDate).toCalendar(),"dd-MMM-yyyy");
							String dateReported = "reportedDate"+index;
						%>
						<ibusiness:litecalendar id="<%=dateReported%>" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_REPORTEDDATE" property="reportedDate" value="<%=repDate%>"	
							indexId="index" calendarTextStyleClass="iCargoLiteDatePicker span8" />
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="reportedDate">
						<ibusiness:litecalendar id="reportedDate" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_REPORTEDDATE" property="reportedDate" value=""
							indexId="index" calendarTextStyleClass="iCargoLiteDatePicker span8" />
					</logic:notPresent> 
				</span>
				</td>

				<!-- Added by A-8368 as part of user story- IASCB-35533 starts-->
				<td class="text-center">
				<%String damageNoticePointValue = "";%>
					<logic:present	name="uLDDamageDtlVO" property="damageNoticePoint">
						<bean:define id="damageNoticePoint" name="uLDDamageDtlVO" property="damageNoticePoint"/> 
						<% damageNoticePointValue= (String)damageNoticePoint; %>
					</logic:present> 
						<ihtml:select property="damageNoticePoint"	 componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_POINTOFNOTICE" value="<%=damageNoticePointValue%>">									
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="operations.shipment.pointofnotice">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						</ihtml:select>					
				</td>
				<!-- Added by A-8368 as part of user story- IASCB-35533 ends-->
				<td class="text-center">
				<%String facilityTypeValue = "";%>
					<logic:present	name="uLDDamageDtlVO" property="facilityType">
						<bean:define id="facilityType" name="uLDDamageDtlVO" property="facilityType"/> 
						<% facilityTypeValue= (String)facilityType; %>
					</logic:present> 
						<ihtml:select property="facilityType"	 componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_FACILITYTYPE" value="<%=facilityTypeValue%>">									
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						</ihtml:select>					
				</td>
				<td class="text-center">
					<span class="col col-12"> 
						<logic:present name="uLDDamageDtlVO" property="location">
							<bean:define id="location" name="uLDDamageDtlVO"	property="location" />
							<ihtml:text property="location" id="location_<%=index%>" styleClass="span6 all-caps" value="<%=String.valueOf(location)%>" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_LOCATION" maxlength="15" />
						</logic:present>
						<logic:notPresent name="uLDDamageDtlVO" property="location">
							<ihtml:text property="location" id="location_<%=index%>" styleClass="span6 all-caps" value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_LOCATION" maxlength="15" />
						</logic:notPresent> 							
						<span class="ico-btn"><i class="icon list-item" id="locationlov_<%=index%>" name="locationlov" onclick ="showFacilityCode(this)"></i></span>						
					</span>
				</td>
				<td class="text-center">
				 <%String partyTypeValue = "";%>
					<logic:present	name="uLDDamageDtlVO" property="partyType">
						<bean:define id="partyType" name="uLDDamageDtlVO" property="partyType"/>  
						<% partyTypeValue= (String)partyType; %>
					</logic:present> 
					 <ihtml:select property="partyType"	componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTYTYPE" value="<%=partyTypeValue%>">							
                     <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>					 
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.PartyType">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>							
				</td>
				<td class="text-center">
					<span class="col col-12">
						<%String id = "party_"+String.valueOf(index); %>
						<logic:present	name="uLDDamageDtlVO" property="party">
							<bean:define id="party" name="uLDDamageDtlVO" property="party" />
							<ihtml:text property="party" id="<%=id%>" styleClass="span6 all-caps" value="<%=String.valueOf(party)%>" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTY"	maxlength="15" indexId="index"/>
						</logic:present> 
						<logic:notPresent name="uLDDamageDtlVO" property="party">
							<ihtml:text property="party" id="<%=id%>" styleClass="span6 all-caps" value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTY" maxlength="15" indexId="index"/>
						</logic:notPresent>
						<span class="ico-btn"><i class="icon list-item" id="partylov_<%=index%>" name="partylov" onclick ="showPartyCode(this)"></i></span>
					</span>
				</td>
				<td class="text-center">
					 <logic:present name="uLDDamageDtlVO" property="remarks">
						<bean:define id="remarks" name="uLDDamageDtlVO" property="remarks" />
						<ihtml:text property="remarks" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_REMARKS" value="<%=(String) remarks%>" />										
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="remarks">
						<ihtml:text property="remarks" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_REMARKS" value="" />
					</logic:notPresent>								
				</td>
				<td> 
                   <logic:equal name="uLDDamageDtlVO" property="closed" value="true">
					<!--bean:write name="uLDDamageDtlVO" property="isClosed"/-->
					<input type="checkbox" name="closed"  style="text-align:center" value="Y" checked="checked"/>		
					</logic:equal>
                 <logic:equal  name="uLDDamageDtlVO" property="closed" value="false">
                <input type="checkbox" name="closed"  style="text-align:center" value="N"  />						 
                </logic:equal>	  
				</td>				
				<td>
				   <%-- <logic:present name="uLDDamageDtlVO" property="picturePresent">
							<bean:define id="picturePresent" name="uLDDamageDtlVO" property="picturePresent" />
							<logic:equal name="picturePresent" value="true">
								<logic:present name="uLDDamageDtlVO" property="sequenceNumber">
										<bean:define id="sequenceNumber" name="uLDDamageDtlVO" property="sequenceNumber" />
								<img src="<%=request.getContextPath()%>/images/images-restyled/clip.png" width="18" height="18" id="imagelov" onclick="openImgUploadPopUp()" alt="Picture LOV" indexId="tIndex"/>
								<img src="<%=request.getContextPath()%>/images/img-active.png" width="18" height="18" id="imagelov" onclick="viewPic(<%=sequenceNumber%>);" alt="Picture LOV"/>
								</logic:present>
							</logic:equal>	
							<logic:equal name="picturePresent" value="false">
							<img src="<%=request.getContextPath()%>/images/images-restyled/clip.png" id="dmgImage<%=index%>"  width="18" height="18" id="imagelov" onclick="openImgUploadPopUp()" alt="Picture LOV" indexId="tIndex"/>
							 <img src="<%=request.getContextPath()%>/images/img-inactive.png" id="inactive_<%=index%>" width="18" height="18" id="imagelov" onclick="picFocus(this)" alt="Picture LOV" /> 	
							</logic:equal>														
				    </logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="picturePresent">
					<img src="<%=request.getContextPath()%>/images/images-restyled/clip.png" id="dmgImage<%=index%>" width="18" height="18" id="imagelov" onclick="openImgUploadPopUp()" alt="Picture LOV" indexId="tIndex"/>
					 <img src="<%=request.getContextPath()%>/images/img-inactive.png" id="inactive_<%=index%>" width="18" height="18" id="imagelov" onclick="picFocus(this)" alt="Picture LOV" /> 							
					</logic:notPresent> 	--%>
					
					<logic:present name="uLDDamageDtlVO" property="imageCount">
					<%if(Integer.parseInt(uLDDamageDtlVO.getImageCount()) > 0){%>
						<bean:define id="dmgIndex1" name="index" />
						<% String dmgIndex = Integer.toString((int)dmgIndex1);%> 
						<% 
							String source = new StringBuilder()
							.append("uld.defaults.ux.damageImageDownload.img?dmgIndex=")
							.append(dmgIndex)
							.append("&imageIndex=0")
							.toString();
						%>	
						<!-- First span-->
						<common:resourceURL  src="<%=source%>" id="finalImageUrl"/>
						<logic:present name="finalImageUrl"> 
							<bean:define id="imgUrl" name="finalImageUrl" />
								<span href="<%=imgUrl%>" rel="<%=dmgIndex%>" class="badge md attach-count" onClick="colorBox()">
								<bean:write name="uLDDamageDtlVO" property="imageCount"/>
								</span>		
						</logic:present> 
						<!-- if more than one image uploaded-->
						<%if(Integer.parseInt(uLDDamageDtlVO.getImageCount()) > 1){%>
							<% for(int i = 1 ; i < Integer.parseInt(uLDDamageDtlVO.getImageCount()) ; i++){%>
								<%
									String source1 = new StringBuilder()
											.append("uld.defaults.ux.damageImageDownload.img?dmgIndex=")
											.append(dmgIndex)
											.append("&imageIndex=")
											.append(i)
											.toString();
								%>
								<common:resourceURL  src="<%=source1%>" id="finalImageUrl1"/>
								<logic:present name="finalImageUrl1"> 
									<bean:define id="imgUrl1" name="finalImageUrl1" />
										<span href="<%=imgUrl1%>" rel="<%=dmgIndex%>" class="badge md attach-count" onClick="colorBox()" style="display:none">
										</span>		
								</logic:present> 
							<%}%><!-- end of for-->
						<%}%><!-- end of if-->
					<%}%><!-- end of if-->
					</logic:present>
					<img src="<%=request.getContextPath()%>/images/images-restyled/clip.png" id="dmgImage_<%=index%>" width="18" height="18" id="imagelov" onclick="openImgUploadPopUp(this)" alt="Picture LOV" indexId="tIndex"/>	
				<i class="icon delete" title="Delete" id="delete_<%=index%>" 
					onclick="inlineDelete({deleteIcon:this,cloneElementType : 'tr' ,operationFlagName:  'tempOperationFlag'}); checkDamageCount(this);" />					
				</td>				
			</tr>