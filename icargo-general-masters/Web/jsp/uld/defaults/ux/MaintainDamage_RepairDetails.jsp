<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamage_RepairDetails.jsp
* Date				: 23-Oct-2017
* Author(s)			: A-7627
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp"%>


<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="oneTimeValues" />
<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="uLDDamageVO" />

<business:sessionBean id="uldNumbers" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="uldNumbers" />

<business:sessionBean id="dmgRefNo" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="dmgRefNo" />

<business:sessionBean id="currencies" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="currencies" />
<business:sessionBean id="refNo" moduleName="uld.defaults"
	screenID="uld.defaults.ux.maintaindamagereport" method="get"
	attribute="refNo" />

<business:sessionBean id="defaultCurrency" moduleName="uld.defaults"
		screenID="uld.defaults.ux.maintaindamagereport" method="get"
		attribute="defaultCurrency" />
<logic:present name="uLDDamageVO">
	<bean:define id="uLDDamageVO" name="uLDDamageVO" />
</logic:present>

<table>
	<thead>
		<tr>
			<th>Repair Head<b>*</b></th>
			<th>Repair Airport<b>*</b></th>
			<th>Repair Date<b>*</b></th>
			<th>Damage Ref. No.<b>*</b></th>
			<th>Amount<b>*</b></th>
			<th>Currency<b>*</b></th>
			<th>Remarks</th>
			<th></th>
		</tr>
	</thead>
	<tbody id="repairDetailsTableBody">
	  <logic:present name="uLDDamageVO" property="uldRepairVOs">
			<bean:define id="uldRepairVOs" name="uLDDamageVO" property="uldRepairVOs" />
			<logic:iterate id="uldRepairVO" name="uldRepairVOs"	indexId="repindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO">
				<logic:present name="uldRepairVO" property="operationFlag">
					<bean:define id="oprFlag" name="uldRepairVO" property="operationFlag"/>
				</logic:present>
				<logic:notPresent name="uldRepairVO" property="operationFlag">
					<bean:define id="oprFlag"  value="NA"/>
				</logic:notPresent>
				<logic:notMatch name="oprFlag" value="D">
				<tr>
					<logic:match name="oprFlag" value="I">
						<html:hidden property="tempRepairOpFlag" value="I"/>
					</logic:match>
					<logic:notMatch name="oprFlag" value="I">
						<ihtml:hidden property="tempRepairOpFlag" value="U"/>
					</logic:notMatch>
					<td class="text-center">
						<logic:present name="uldRepairVO" property="repairHead">
						<bean:define id="repairHead" name="uldRepairVO"	property="repairHead" />						
							 <ihtml:select property="repHead" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_RPRSTA" value="<%=(String)repairHead%>" >
								<logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.repairhead">
											<bean:define id="parameterValues" name="oneTimeValue"	property="value" />
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
						</logic:present>
					</td>
					<td class="text-center">
					<span class="col col-12"> 
						<logic:present name="uldRepairVO" property="repairStation">
						<bean:define id="repairStation" name="uldRepairVO"	property="repairStation" />
							<ihtml:text property="repairStn" id="repairStn_<%=repindex%>" styleClass="span8 all-caps"	value="<%=String.valueOf(repairStation)%>" componentID="CMP_ULD_DEFAULTS_MAINTAINDMG_REPAIRPORT" />
						</logic:present>
						<logic:notPresent name="uldRepairVO" property="repairStation">
							<ihtml:text property="repairStn" id="repairStn_<%=repindex%>" styleClass="span8 all-caps"	value="" componentID="CMP_ULD_DEFAULTS_MAINTAINDMG_REPAIRPORT" />
						</logic:notPresent> 
						<span class="ico-btn"><i class="icon list-item" id="repairStnlov_<%=repindex%>" name="repairStnlov" onclick ="showRepairAirport(this)"></i></span>
					</span>
					</td>
					<td class="text-center">
						<span class="col col-12"> 
						<logic:present name="uldRepairVO" property="repairDate">
							<bean:define id="repairDate" name="uldRepairVO"	property="repairDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
							<%	String repDateStr = TimeConvertor.toStringFormat(((LocalDate) repairDate).toCalendar(),"dd-MMM-yyyy");	%>
							<ibusiness:litecalendar id="repairDate"	indexId="repindex" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_SEVERITY_REPAIRDATE" property="repairDate" value="<%=repDateStr%>"
								calendarTextStyleClass="iCargoLiteDatePicker span8" />
						</logic:present>
						<logic:notPresent name="uldRepairVO" property="repairDate">
							<ibusiness:litecalendar id="repairDate"	indexId="repindex" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_SEVERITY_REPAIRDATE" property="repairDate" value=""
								calendarTextStyleClass="iCargoLiteDatePicker span8" />
						</logic:notPresent>
						</span>
					</td>
					<td class="text-center">
						<span class="col col-12"> 
							<%long damageReferenceNo=0;%>
							<logic:present name="uldRepairVO" property="damageReferenceNumber">
							<bean:define id="damageReferenceNumber" name="uldRepairVO"	property="damageReferenceNumber" />
						  <%damageReferenceNo=(long)damageReferenceNumber;%>                  
							</logic:present>
							<logic:present name="uldRepairVO" property="invoiceReferenceNumber">
								<bean:define id="invoiceReferenceNumber" name="uldRepairVO"	property="invoiceReferenceNumber" />
						<ihtml:select property="dmgRepairRefNo" id="dmgRepairRefNo_<%=repindex%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGREFNO" indexId="repindex" onchange="closeSatus(this);" value="<%=String.valueOf(invoiceReferenceNumber)%>" style="text-align:right">
							<logic:present name="dmgRefNo">
								<logic:iterate id="damageRefNo" name="dmgRefNo">
									<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
								</logic:iterate>
							</logic:present>
							<logic:notPresent  name="dmgRefNo">
							 <logic:present name="dmgRefNo">
								<logic:iterate id="damageRefNo" name="dmgRefNo">
									<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
								</logic:iterate>
							</logic:present>
							</logic:notPresent>
						</ihtml:select>
							</logic:present>
							<logic:notPresent name="uldRepairVO" property="invoiceReferenceNumber">
						<ihtml:select property="dmgRepairRefNo" id="dmgRepairRefNo_<%=repindex%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGREFNO" indexId="repindex" onchange="closeSatus(this);" value="<%=String.valueOf(damageReferenceNo)%>" style="text-align:right" >
						<!--Added as a part of Bug ICRD-318900 by A-8154 -->
						<html:option value=""><common:message key="combo.select"/></html:option>
							<logic:present name="dmgRefNo">
								<logic:iterate id="damageRefNo" name="dmgRefNo">
									<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
								</logic:iterate>
							</logic:present>
							<logic:notPresent  name="dmgRefNo">
							 <logic:present name="dmgRefNo">
								<logic:iterate id="damageRefNo" name="dmgRefNo">
									<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
								</logic:iterate>
							</logic:present>
							</logic:notPresent> 
						</ihtml:select>
					</logic:notPresent>
					</span>
					</td>
					<td class="text-center">
						 <logic:present	name="uldRepairVO" property="displayAmount">
							<bean:define id="displayAmount" name="uldRepairVO"	property="displayAmount" />
							<ihtml:text property="amount" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTAMT"	style="text-align:right" maxlength="10" value="<%=String.valueOf(displayAmount)%>" />							
						</logic:present>
					</td>
					<td class="text-center">
						<%String currencyValue="";%>
							<logic:present name="uldRepairVO" property="currency">
							<bean:define id="currency" name="uldRepairVO"	property="currency" />
						  <%currencyValue=String.valueOf(currency);%>                  
							</logic:present>
						<ihtml:select property="currency" value="<%=currencyValue%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_CURRENCY">
							<logic:present name="currencies">
								<ihtml:options collection="currencies" property="currencyCode" labelProperty="currencyCode" />
							</logic:present>
						</ihtml:select>		
				    </td>
					<td class="text-center">
						 <logic:present	name="uldRepairVO" property="remarks">
							<bean:define id="remarks" name="uldRepairVO" property="remarks" />
							<ihtml:text property="repRemarks" id="remarks_<%=repindex%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_REPREMARKS" value="<%=(String) remarks%>"  
								/>	
						</logic:present>
						<logic:notPresent name="uldRepairVO" property="remarks">
							<ihtml:text property="repRemarks" id="remarks_<%=repindex%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_REPREMARKS" value=""  
								/>	
						</logic:notPresent>
					</td>
					<td>
						<i class="icon delete" title="Delete" id="delete_<%=repindex%>" onclick="inlineDelete({deleteIcon:this,cloneElementType : 'tr' ,operationFlagName:  'tempRepairOpFlag'});" />
					</td>
				</tr>
				</logic:notMatch>
				<logic:match name="oprFlag" value="D" >
					<ihtml:hidden property="tempRepairOpFlag" value="D"/>
					<logic:present name="uldRepairVO" property="repairHead">
						<bean:define id="repairHead" name="uldRepairVO"	property="repairHead" />
						<ihtml:hidden property="repHead" value="<%=String.valueOf(repairHead)%>" />											
					</logic:present>
					<logic:notPresent name="uldRepairVO" property="repairHead">
						<ihtml:hidden property="repHead" value="" />											
					</logic:notPresent>
					<logic:present name="uldRepairVO" property="repairStation">
						<bean:define id="repairStation" name="uldRepairVO" property="repairStation" />
						<ihtml:hidden property="repairStn" styleClass="span8" value="<%=(String) repairStation%>"  />
					</logic:present> 
					<logic:notPresent name="uldRepairVO" property="repairStation">
						<ihtml:hidden property="repairStn"  value=""  />
					</logic:notPresent>
		
					<logic:notPresent name="uldRepairVO" property="repairDate">
						<ihtml:hidden property="repairDate"  value="" />
					</logic:notPresent>
					<logic:present name="uldRepairVO" property="repairDate">
						<bean:define id="repairDate" name="uldRepairVO" property="repairDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String str = String.valueOf(repairDate.toDisplayFormat("dd-MMM-yyyy"));%>
						<ihtml:hidden property="repairDate"  value="<%=str%>" />
					</logic:present>
					<logic:present name="uldRepairVO" property="damageReferenceNumber">
						<bean:define id="damageReferenceNumber" name="uldRepairVO" property="damageReferenceNumber" />
						<ihtml:hidden property="dmgRepairRefNo"  value="<%=String.valueOf(damageReferenceNumber)%>" />
					</logic:present> 
					<logic:notPresent name="uldRepairVO" property="damageReferenceNumber">
						<ihtml:hidden property="dmgRepairRefNo" value=""  />
					</logic:notPresent>
					<logic:present	name="uldRepairVO" property="displayAmount">
						<bean:define id="displayAmount" name="uldRepairVO" property="displayAmount" />
						<ihtml:hidden property="amount" value="<%=String.valueOf(displayAmount)%>" />
					</logic:present> 
					<logic:notPresent name="uldRepairVO" property="displayAmount">
						<ihtml:hidden property="amount"  value=""/>
					</logic:notPresent>
					<logic:present name="uldRepairVO" property="currency">
						<bean:define id="currency" name="uldRepairVO" property="currency" />
						<ihtml:hidden property="currency" value="<%=(String) currency%>" />
					</logic:present> 
					<logic:notPresent name="uldRepairVO" property="currency">
						<ihtml:hidden property="currency"  value=""  />
					</logic:notPresent>
					<logic:present	name="uldRepairVO" property="remarks">
						<bean:define id="remarks" name="uldRepairVO" property="remarks" />
						<ihtml:hidden property="repRemarks" value="<%=(String) remarks%>"  />
					</logic:present> 
					<logic:notPresent name="uldRepairVO" property="remarks">
						<ihtml:hidden property="repRemarks"  value="" />
					</logic:notPresent>
				</logic:match>
			</logic:iterate>
		</logic:present>
		<bean:define id="tempRowCount" value=""/> 
		<tr template="true" id="addRepairDetailTemplateRow"	style="display: none">
			<ihtml:hidden property="tempRepairOpFlag" value="NOOP" />
			<td class="text-center">
				<ihtml:select property="repHead" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_RPRSTA">
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.repairhead">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
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
					<ihtml:text property="repairStn" id="repairStn_" styleClass="span8 all-caps"	value="" componentID="CMP_ULD_DEFAULTS_MAINTAINDMG_REPAIRPORT" />
					<span class="ico-btn"><i class="icon list-item" id="repairStnlov_" name="repairStnlov" onclick ="showRepairAirport(this)"></i></span>
				</span>
			</td>
			<td class="text-center">
				<span class="col col-12"> 
				<ibusiness:litecalendar id="repairDate"	indexId="tempRowCount" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_SEVERITY_REPAIRDATE" property="repairDate" value=""
						calendarTextStyleClass="iCargoLiteDatePicker span8" />
				</span>
			</td>
			<td class="text-center">
			<ihtml:select property="dmgRepairRefNo" 
					multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" liteMode="true"  multiple="multiple" 
				  indexId="tempRowCount" id="damageRefNo" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGREFNO" templateRow="true" value="" onchange="closeSatus(this);" style="text-align:right" >
						<logic:present name="dmgRefNo">
							<logic:iterate id="damageRefNo" name="dmgRefNo">
								<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
							</logic:iterate>
						</logic:present>
					<logic:notPresent  name="dmgRefNo">
							 <logic:present name="dmgRefNo">
								<logic:iterate id="damageRefNo" name="dmgRefNo">
									<html:option value="<%= String.valueOf(damageRefNo)%>"><%=String.valueOf(damageRefNo)%></html:option>
								</logic:iterate>
							</logic:present>
					</logic:notPresent>
					</ihtml:select>
			
			</td>
			<td class="text-center">
				<ihtml:text property="amount" indexId="tempRowCount" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTAMT" maxlength="10" value="" style="text-align:right" />								
			</td>
			<td class="text-center">
			<logic:present name="defaultCurrency">
			<bean:define id="dCurrency" name="defaultCurrency" />
				<ihtml:select property="currency" value="<%=String.valueOf(dCurrency)%>" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_CURRENCY">
					<logic:present name="currencies">
						<ihtml:options collection="currencies" property="currencyCode"	labelProperty="currencyCode" />
					</logic:present>
				</ihtml:select>	
			</logic:present>
			<logic:notPresent name="defaultCurrency">
				<ihtml:select property="currency" value=""	componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_CURRENCY">
					<logic:present name="currencies">
						<ihtml:options collection="currencies" property="currencyCode"	labelProperty="currencyCode" />
					</logic:present>
				</ihtml:select>	
			</logic:notPresent>
			</td>
			<td class="text-center">
				<ihtml:text property="repRemarks" indexId="tempRowCount" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_REPREMARKS" value="" />				
				
			</td>
			<td>
			   <i class="icon delete" title="Delete" id="delete_" onclick="inlineDelete({deleteIcon:this,cloneElementType : 'tr' ,operationFlagName:  'tempRepairOpFlag'});checkForSurveyorFieldEnabling();" />
			</td>
		</tr>
	</tbody>
</table>
