<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamage_DamageDetails.jsp
* Date				: 23-Oct-2017
* Author(s)			: A-7627
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
<%@ include file="/jsp/includes/tlds.jsp"%>


<bean:define id="form" name="maintainDamageReportRevampForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
	toScope="page" />

<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="oneTimeValues" />
<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="uLDDamageVO" />

<business:sessionBean id="uldNumbers" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="uldNumbers" />
<ihtml:hidden property="selectedDamageRowId" />

<logic:present name="uLDDamageVO">
	<bean:define id="uLDDamageVO" name="uLDDamageVO" />
</logic:present>



<business:sessionBean id="KEY_ULDDMGCHKLST"
							moduleName="uld.defaults"
							screenID="uld.defaults.ux.maintaindamagereport"
							method="get"
				attribute="ULDDamageChecklistVO" />

<table>
	<thead>
		<tr>
			<th>Damage <br> Ref. No.</th>			
			<th style="width: 8%">Section<b>*</b></th>
			<th>Damage<b>*</b></th>
			<th>Severity<b>*</b></th>
			<th>Reported Airport<b>*</b></th>
			<th>Reported Date<b>*</b></th>
			<th style="width: 9%">Point of Notice</th>
			<th style="width: 7%">Facility Type</th>
			<th>Location</th>
			<th style="width: 7%">Party Type</th>
			<th>Party</th>
			<th>Remarks</th>
			<th>Closed</th>
			<th style="width: 5%"></th>
		</tr>
	</thead>
	<tbody id="damageDetailsTableBody">
	  <div id="damageDetailsTable">
		<logic:present name="uLDDamageVO" property="uldDamageVOs">
		<bean:define id="uldDamageVOs" name="uLDDamageVO" property="uldDamageVOs" />
		<%	int count = 0;	%>
		<logic:iterate id ="uLDDamageDtlVO" name="uldDamageVOs" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO" indexId="index">		
		<logic:present name="uLDDamageDtlVO" property="operationFlag">
			<bean:define id="opFlag" name="uLDDamageDtlVO" property="operationFlag"/>
			<%request.setAttribute("oprtnFlg",opFlag);%>
		</logic:present>
		<% request.setAttribute("uLDDamageDtlVO",uLDDamageDtlVO); %>
		<logic:notPresent name="uLDDamageDtlVO" property="operationFlag">
			<bean:define id="opFlag"  value="NA"/>
			<%request.setAttribute("oprtnFlg",opFlag);%>
		</logic:notPresent>
			<% request.setAttribute("index",index); %>					
		<logic:notEqual name="opFlag" value="D">
			<jsp:include page="MaintainDamage_DamageDetailsPart1.jsp" />
			</logic:notEqual>
			<logic:equal name="opFlag" value="D" >
				<jsp:include page="MaintainDamage_DamageDetailsPart2.jsp" />
							</logic:equal>
		<% count++;%>

		</logic:iterate>
	</logic:present>

	 </div>
	<bean:define id="templateRowCount" value=""/> 
	 <tr template="true" id="addDamageDetailTemplateRow" style="display:none">
		<ihtml:hidden property="tempOperationFlag" value="NOOP" />
		<td class="text-center">                						
			<ihtml:text property="dmgRefNo" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REFNO" value="" style="text-align:right" readonly="true" indexId="tIndex"/>
		</td>
		<td class="text-center">
			<ihtml:select property="section" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_SECTION" value="" onchange="populateOnChange(this);" indexId="tIndex">
				<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
				<logic:present name="oneTimeValues">
					<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
						<logic:equal name="parameterCode" value="uld.defaults.section">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
									<ihtml:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></ihtml:option>
								</logic:present>
							</logic:iterate>
						</logic:equal>
					</logic:iterate>
				</logic:present>
			</ihtml:select>
		</td>

		<td class="text-center">
		 <div id="damageType">
						<ihtml:select property="description"	componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DESCRIPTION"> 
							<html:option value=""><common:message key="combo.select"/></html:option>						  
						</ihtml:select>				
					</div>
		</td>
		<td class="text-center">
			<ihtml:select property="severity" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY" value="" indexId="tIndex">
				<logic:present name="oneTimeValues">
					<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
						<logic:equal name="parameterCode" value="uld.defaults.damageseverity">
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
				<logic:present name="form" property="currentStation">
					<bean:define id="currentStation" name="form" property="currentStation" />		
					<ihtml:text property="repStn" id="repStn_" value="<%=(String) currentStation%>"	componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" styleClass="span6 all-caps" maxlength="3" indexId="tIndex"/>
					<span class="ico-btn"><i class="icon list-item" id="repStnlov_" name="repStnlov" onclick ="showReportedAirport(this)"></i></span>
				</logic:present>
				<logic:notPresent name="form" property="currentStation">
				<ihtml:text property="repStn" id="repStn_" value=""	componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" styleClass="span6 all-caps" maxlength="3" indexId="tIndex"/>
				 <span class="ico-btn"><i class="icon list-item" id="repStnlov_" name="repStnlov" onclick ="showReportedAirport(this)"></i></span>
				</logic:notPresent>
			</span>
		</td>
		<td class="text-center">
			<span class="col col-12"> 
				<% String dateString = TimeConvertor.toStringFormat((TimeConvertor.getCurrentDate()),"dd-MMM-yyyy");%>
				<ibusiness:litecalendar  id="reportedDate" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_REPORTEDDATE" property="reportedDate" value="<%=dateString%>" 
						indexId="tIndex" calendarTextStyleClass="iCargoLiteDatePicker span8" style="width:75%"/>
			
			</span>
		</td>

		    <!-- Added by A-8368 as part of user story- IASCB-35533 starts-->
				<td class="text-center">
						<ihtml:select property="damageNoticePoint"	value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_POINTOFNOTICE" indexId="tIndex">									
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
			 <ihtml:select property="facilityType"	value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_FACILITYTYPE" indexId="tIndex">									
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
			<td class="text-center"><span class="col col-12">                               
				<ihtml:text property="location" styleClass="span6 all-caps" value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_LOCATION" maxlength="15" indexId="tIndex"/>
				<span class="ico-btn"><i class="icon list-item" id="locationlov_" name="locationlov" onclick ="showFacilityCode(this)"></i></span>
			</span>
			</td>
			<td class="text-center">
				 <ihtml:select property="partyType"	value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTYTYPE" indexId="tIndex">							
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
					<ihtml:text property="party" id="party_" name="party_" styleClass="span6 all-caps" value="" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTY"/>
					<span class="ico-btn"><i class="icon list-item" id="partylov_" name="partylov" onclick ="showPartyCode(this)" indexId="tIndex"></i></span>
				</span>
			</td>
			<td class="text-center">								
				<ihtml:text property="remarks" value="" indexId="tIndex" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_REMARKS" />																
			</td>							
			<td>
				<input type="checkbox" name="closed" indexId="tIndex" onchange="updateCheckboxValue(this)" style="text-align:center"/>
			</td>							
			<td style="display: none">												 
			</td>		
			<td>
			
			<img src="<%=request.getContextPath()%>/images/images-restyled/clip.png" width="18" height="18" id="imagelov_" onclick="openImgUploadPopUp(this)" alt="Picture LOV" indexId="tIndex"/>
			<%--<img src="<%=request.getContextPath()%>/images/img-inactive.png" id="inactive_" width="18" height="18" id="imagelov" onclick="picFocus(this)" alt="Picture LOV" indexId="tIndex"/> --%>
				<i class="icon delete" title="Delete" id="delete_" onclick="inlineDelete({deleteIcon:this,cloneElementType : 'tr' ,operationFlagName:  'tempOperationFlag'}); checkDamageCount(this);" indexId="tIndex"/>
			</td>
		</tr>		
		
	
	</tbody>
</table>
