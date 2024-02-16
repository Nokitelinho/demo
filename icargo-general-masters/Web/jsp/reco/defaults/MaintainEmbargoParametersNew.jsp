

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>

<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="levelCode" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="levelCode" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults"	screenID="reco.defaults.maintainembargo"	method="get" attribute="embargoParameterVos" />
<business:sessionBean id="globalParameters"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="globalParameters" />
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailSubClassGrp" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>
<business:sessionBean id="weightsApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="weightsApplicableOn" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="subRisk" />

		<% 
			String embargoRefNo="";
			String embargoLevel="";
			String embargoStatusField="";
			String embargoStartDate="";
			String embargoEndDate="";
			boolean embargoSuspend=false;
			String  embargoOrigin="";
			String  embargoOriginType="";
			String  embargoDestination="";
			String  embargoDestinationType="";
			String  embargoDescription="";
			String  embargoRemarks="";

			String  embargoParameterApplicable="";
			String  embargoParameterValues="";

			String opFlagUpdate = "";
			String paraColl = "";
		%>

		<div class="tableContainer" style="height:155px;width:100%;">
			<table class="fixed-header-table" id="embargo_parameter_table">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<th class="iCargoTableHeader" style="width:5%">
							
								<input type="checkbox" name="allCheck" onclick="updateHeaderCheckBox(this.form,this.form.allCheck,this.form.check)"/>
							
						</th>
						<th class="iCargoTableHeader" style="width:20%">
							<common:message  key="maintainembargo.parameter"/><span class="iCargoMandatoryFieldIcon">*</span>
						</th>
						<th class="iCargoTableHeader" style="width:20%">
							<common:message  key="maintainembargo.applicable"/>
						</th>
						<th class="iCargoTableHeader" style="width:30%">
							<common:message  key="maintainembargo.values"/><span class="iCargoMandatoryFieldIcon">*</span>
						</th>
						<th class="iCargoTableHeader" style="width:25%">
							&nbsp;
						</th>
					</tr>
				</thead>
				
				<tbody id="listParameterData">
					<jsp:include page="MaintainEmbargoParametersNew_Table.jsp"/>
					<jsp:include page="MaintainEmbargoParametersNew_TempTable.jsp"/>
					
					<tr template="true" id="listParameterDataTemplateRow" style="display:none">
						<td>
							<html:hidden property="paramOperationalFlag" value="NOOP"/>
							<div class="ic-center"><ihtml:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.allCheck)"/></div>	
						</td>
						<td>
							<ihtml:select property="parameterCode" 
										indexId="rowCount" 
										value="" 
										styleClass="parameterCode" 
										componentID="CMP_Reco_Defaults_MaintainEmbargo_ParameterCode" 
										onchange="javasciript:changeParameterValues(this)">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							
								<logic:present name="embargoParameters">
									<logic:iterate id="embargoParameter" name="embargoParameters">
										<bean:define id="embargoParameterCode" name="embargoParameter" property="fieldValue" />
										<html:option value="<%=(String)embargoParameterCode %>"><bean:write name="embargoParameter" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present> 
							</ihtml:select>
						</td>

						<td></td>
							
						<td>
							<div class="column4_control_div ic-input">
																											
							</div>																							
							<ihtml:hidden property="values" />
						</td>
						<td>
						</td>
						
					</tr>							       
				</tbody>
			</table>
		</div>
      	

