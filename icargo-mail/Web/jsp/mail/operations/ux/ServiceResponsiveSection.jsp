<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>	
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO"%>
<%@ page info="lite" %>
<business:sessionBean id="incentiveConfigurationVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="incentiveConfigurationVOs"/>
<business:sessionBean id="incentiveConfigurationDetails"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="incentiveConfigurationDetailVOs"/>
<bean:define id="form"
		name="MailPerformanceForm" 
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm" 
		toScope="page" />	
 <div class="card-body p-0" id="srvDisIncentive">
	<div id="dataTableContainer" class="dataTableContainer tableonly-container tablePanel w-100" >
		<table class="table-x-md m-0 w-100 table" id="disIncentiveSrvTable" >
			<thead>
				<tr>
					<th class="text-center check-box-cell"><input type="checkbox" name="checkAllDisincentive" onclick="updateHeaderCheckBox(this.form,this,this.form.disIncSrvRowId);"></th>
					<th class="text-center param"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.parameter" /></th>
					<th class="text-center formul"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.formula" /></th>
					<th class="text-center bas"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.basis" /></th>
					<th class="text-center perc"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.percentage" /></th>
					<th class="text-center valfrm"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validfrom" /></th>
					<th class="text-center valto"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validto" /></th>
				</tr>
			</thead>
			<tbody id="disIncentiveSrvTableBody">
				<logic:present name="incentiveConfigurationVOs"  >
				<logic:iterate id="incentiveConfigurationVO" name="incentiveConfigurationVOs" indexId="rowCount" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO" >
				<logic:equal name="incentiveConfigurationVO" property="incentiveFlag" value="N">
				<logic:equal name="incentiveConfigurationVO" property="serviceRespFlag" value="Y">
					<tr id="disIncentiveServiceResp<%=String.valueOf(rowCount)%>" >
					<logic:present name="incentiveConfigurationVO" property="disIncSrvOperationFlags"> 
						<bean:define id="disIncSrvOperationFlags" name="incentiveConfigurationVO" property="disIncSrvOperationFlags" toScope="request" />
						<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="D">
							<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
							</td>
								<td>
									<%
										StringBuilder parameter = new StringBuilder();
										StringBuilder excFlag = new StringBuilder();
										StringBuilder displayParameter = new StringBuilder();
										String code = null;
									%>
							<logic:present name="incentiveConfigurationVO" property="incentiveConfigurationDetailVOs">
							<bean:define id="incentiveConfigurationDetails" name="incentiveConfigurationVO" type="java.util.Collection" property="incentiveConfigurationDetailVOs"/>
								<logic:iterate id="incentiveConfigurationDetailVO" name="incentiveConfigurationDetails" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
										<ihtml:hidden property="disIncSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
									<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
										<%
										if("CAT".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
											code = "CATEGORY";
										else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
											code = "SERVICE LEVEL";
										else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))	
											code = "SUBCLASS";
										else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
											code = "AMOT";
										if(parameter == null){
											parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()).append(" : ")
																	.append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue());
										}else{
											parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncSrvParameterCode())
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue());
										}
										if(displayParameter == null){
											displayParameter = displayParameter.append(code).append(" : ")
																.append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue())
																.append(" (")
																.append("Y".equals(incentiveConfigurationDetailVO.getSrvExcludeFlag()) ? "I" : "E")
																.append(")");
										}else{
											displayParameter = displayParameter.append("\n").append(code)
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue())
															.append(" (")
															.append("Y".equals(incentiveConfigurationDetailVO.getSrvExcludeFlag()) ? "I" : "E")
															.append(")");
										}
										if(excFlag == null || excFlag.length()== 0){
											excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getSrvExcludeFlag());
										}else{
											excFlag = 	excFlag.append(",").append(
														incentiveConfigurationDetailVO.getSrvExcludeFlag());
										}
										%>
								</logic:iterate>
							</logic:present >
								<% String srvParamId = "disIncParameter";
									String srvDisplayParamId = "srvDisplayParameter";
								 String srvParamIdLOV = "disIncParameterLOV";
								 String srvExcFlag = "excFlag";%>
								<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
								
											<div class="input-group" style="width:208px;">
											
												<ihtml:hidden property="disIncSrvParameter" id="<%=srvParamId+String.valueOf(rowCount)%>" style="height:70px;" value="<%=parameter.toString()%>" styleClass="form-control" />
												
												<ihtml:textarea property="srvDisplayParameter" id="<%=srvDisplayParamId+String.valueOf(rowCount)%>" style="height:65px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>"  styleClass="form-control" />
												<div class="input-group-append">									
													<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon" property="parameterLOV" id="<%=srvParamIdLOV+String.valueOf(rowCount)%>">
													<i class="icon ico-expand" ></i>
													</ihtml:nbutton>
												</div>
											</div>
											<ihtml:hidden property="srvExcFlag" id="<%=srvExcFlag+String.valueOf(rowCount)%>" value="<%=excFlag.toString()%>" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
											
											<ihtml:hidden property="disIncSrvParameter" id="<%=srvParamId+String.valueOf(rowCount)%>" style="height:70px;" value="<%=parameter.toString()%>" styleClass="form-control" />
											
											<ihtml:textarea property="srvDisplayParameter" id="<%=srvDisplayParamId+String.valueOf(rowCount)%>" style="height:65px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>" readonly="true" styleClass="form-control" />
											
											<ihtml:hidden property="srvExcFlag" id="excFlag" value="<%=excFlag.toString()%>" />
								</logic:notEqual>
									<br/>
							</td>
							<td>
									<%
										String formula="";
										if(incentiveConfigurationVO.getSrvFormula()!=null && incentiveConfigurationVO.getSrvFormula()!=""){
											String formulaExp=incentiveConfigurationVO.getSrvFormula();
											formula=formulaExp.replaceAll("~","");
										}
										String newFormula = null;
										if(incentiveConfigurationVO.getSrvFormula()!=null){
											 newFormula = incentiveConfigurationVO.getSrvFormula();
										if(newFormula.contains(">"))
											newFormula = newFormula.replaceAll(">","GT");
										if(newFormula.contains("<"))
											newFormula = newFormula.replaceAll("<","LT");
										}
								   %>
								<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
									<% String SrvformulaId  = "srvformula";
										String SrvformulaIdLOV = "srvformulaLOV";
									   String SrvDisplayformulaId  = "srvDisplayformula";%>
									<div class="input-group">
										<ihtml:hidden property="srvFormula" id="<%=SrvformulaId+String.valueOf(rowCount)%>" value="<%=newFormula%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA" />
										<ihtml:text value="<%=formula%>" id="<%=SrvDisplayformulaId+String.valueOf(rowCount)%>" property="srvDisplayFormula" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
										<div class="input-group-append">		
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="<%=SrvformulaIdLOV+String.valueOf(rowCount)%>" property="formulaLOV">
											<i class="icon ico-expand" ></i>
										</ihtml:nbutton>
									</div>	
									</div>	
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
										<ihtml:hidden property="srvFormula" value="<%=newFormula%>"  name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" />
										<ihtml:text value="<%=formula%>" property="srvDisplayFormula" id="srvDisplayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
									<% String SrvBasisId  = "srvBasis";
 									   String SrvBasisIdLOV  = "srvBasisLOV";
									   String temp = "tempSrvBasis";%>
									<div class="input-group" style="width:150px;">
									<ihtml:hidden property="tempSrvBasis" id="<%=temp+String.valueOf(rowCount)%>" />
										<ihtml:text property="srvBasis" id="<%=SrvBasisId+String.valueOf(rowCount)%>" name="incentiveConfigurationVO" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS"  />
										<div class="input-group-append">
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" property="basisLOV" id="<%=SrvBasisIdLOV+String.valueOf(rowCount)%>" >
											<i class="icon ico-expand" ></i>
										</ihtml:nbutton>
										</div>
									</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
										<ihtml:text property="srvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
										<ihtml:text property="disIncSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" styleClass="form-control" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
										<ihtml:text property="disIncSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">	
										<ibusiness:litecalendar
										id="disIncSrvValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncSrvValidFrom()%>"  styleClass="form-control" />
									</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ihtml:text property="disIncSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncSrvValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
									</div>
									</logic:notEqual>
								</td>
								<td>
									<% String validTo  = "disIncSrvValidTo"; %>
									<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncSrvValidTo" value="<%=incentiveConfigurationVO.getDisIncSrvValidTo()%>" />
									</div>
									</logic:equal>
									<logic:notEqual name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="I">
										<div class="input-group" style="width:122px;">									
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncSrvValidTo" value="<%=incentiveConfigurationVO.getDisIncSrvValidTo()%>" />
										</div>
								</logic:notEqual>
							</td>
						</logic:notEqual>
						<logic:equal name="incentiveConfigurationVO" property="disIncSrvOperationFlags" value="D">
							<ihtml:hidden property="disIncSrvParameterType" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncSrvParameterCode" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="srvExcludeFlag" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncSrvParameterValue" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="srvFormula" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="srvBasis" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncSrvPercentage" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncSrvValidFrom" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncSrvValidTo" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="incentiveFlag" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="serviceRespFlag" name="incentiveConfigurationVO"/>
						</logic:equal>
						<ihtml:hidden property="disIncSrvOperationFlags" value="<%=((String)disIncSrvOperationFlags)%>" />
					</logic:present>
					<logic:notPresent name="incentiveConfigurationVO" property="disIncSrvOperationFlags">
						<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
						</td>
							<td>
						<%
						StringBuilder parameter = new StringBuilder();
						StringBuilder excFlag = new StringBuilder();
						StringBuilder displayParameter = new StringBuilder();
						String code = null;
						%>
						<logic:present name="incentiveConfigurationVO" property="incentiveConfigurationDetailVOs"  >
							<bean:define id="incentiveConfigurationDetails" name="incentiveConfigurationVO" type="java.util.Collection" property="incentiveConfigurationDetailVOs"/>
								<logic:iterate id="incentiveConfigurationDetailVO" name="incentiveConfigurationDetails" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
							<ihtml:hidden property="disIncSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
							<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
							<%
							if("CAT".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
								code = "CATEGORY";
							else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
								code = "SERVICE LEVEL";
							else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))	
								code = "SUBCLASS";
							else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()))
								code = "AMOT";
							if(parameter == null){
								parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncSrvParameterCode()).append(" : ")
														.append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue());
							}else{
								parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncSrvParameterCode())
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue());
							}
							if(displayParameter == null){
								displayParameter = displayParameter.append(code).append(" : ")
													.append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue())
													.append(" (")
													.append("Y".equals(incentiveConfigurationDetailVO.getSrvExcludeFlag()) ? "I" : "E")
													.append(")");
							}else{
								displayParameter = displayParameter.append("\n").append(code)
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncSrvParameterValue())
												.append(" (")
												.append("Y".equals(incentiveConfigurationDetailVO.getSrvExcludeFlag()) ? "I" : "E")
												.append(")");
							}
							if(excFlag == null || excFlag.length()== 0){
								excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getSrvExcludeFlag());
							}else{
								excFlag = 	excFlag.append(",").append(
											incentiveConfigurationDetailVO.getSrvExcludeFlag());
							}
							%>
								</logic:iterate>
						</logic:present >
						<div class="input-group" style="width:208px;">
										<ihtml:hidden property="disIncSrvParameter" style="height:70px;" value="<%=parameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"  disabled="true" styleClass="form-control" />
										<ihtml:textarea property="srvDisplayParameter" style="height:65px;" value="<%=displayParameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"  readonly="true" styleClass="form-control" />
										<ihtml:hidden property="srvExcFlag" id="excFlag" value="<%=excFlag.toString()%>" />
									</div>
						</td>
						<%
							String formulaExp=incentiveConfigurationVO.getSrvFormula();
							String formula=null;
							formula=formulaExp.replaceAll("~","");
							String newFormula = incentiveConfigurationVO.getSrvFormula();
							if(newFormula.contains(">"))
								newFormula = newFormula.replaceAll(">","GT");
							if(newFormula.contains("<"))
								newFormula = newFormula.replaceAll("<","LT");
					   %>
						<td>
							<ihtml:hidden property="srvFormula" value="<%=newFormula%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" />
							<ihtml:text value="<%=formula%>" property="srvDisplayFormula" id="srvDisplayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						</td>
						<td>
							<div class="input-group" style="width:150px;">
								<ihtml:text property="srvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
							</div>
						</td>
						<td>
								<ihtml:text property="disIncSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
						</td>
						<td>
							<div class="input-group" style="width:122px;">
							<ihtml:text property="disIncSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
						</div>
						</td>
						<td>
						<% String validTo  = "disIncSrvValidTo"; %>
						<div class="input-group" style="width:122px;">
							<ibusiness:litecalendar styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
							property="disIncSrvValidTo" value="<%=incentiveConfigurationVO.getDisIncSrvValidTo()%>" />
						</div>
						</td>
						<ihtml:hidden property="disIncSrvOperationFlags" value="U" />
					</logic:notPresent>
				</tr>
				</logic:equal>
				</logic:equal>
				</logic:iterate>
			</logic:present>
			<tr  template="true" id="disIncentiveSrvTemplateRow" style="display:none">
				<ihtml:hidden property="disIncSrvOperationFlags" value="NOOP" />
				<ihtml:hidden property="incFlag" value="N" />
				<ihtml:hidden property="disIncSrvParameterType" value="D" />
					<td class="text-center">
						<input type="checkbox"  name="disIncSrvRowId"/>
					</td>
					<td class="iCargoTableDataTd">
						<div class="input-group" style="width:208px;">
							<ihtml:hidden  value="" property="disIncSrvParameter" id="disIncParameter" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" style="height:65px;"/>
							<ihtml:textarea  value="" property="srvDisplayParameter" id="srvDisplayParameter" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" style="height:70px;"/>
							<ihtml:hidden property="srvExcFlag" id="excFlag" value="" />
							<ihtml:hidden property="disIncSrvParameterType" value="D" />
							<div class="input-group-append">									
								 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
								  id="disIncParameterLOV" property="parameterLOV">
								  <i class="icon ico-expand" ></i>
								 </ihtml:nbutton>
							</div>
						</div>
					</td>
					<td>
						<div class="input-group">
							<ihtml:hidden value=""  property="srvFormula" id="srvformula" styleClass="form-control" />
							<ihtml:text value="" property="srvDisplayFormula" id="srvDisplayformula" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
							<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
							id="srvformulaLOV" property="formulaLOV" >
								<i class="icon ico-expand" ></i>
							</ihtml:nbutton>
						</div>
					</td>
					<td>
						<div class="input-group" style="width:150px;">
						<ihtml:hidden value="" property="tempSrvBasis" id="tempSrvBasis" />
							<ihtml:text value="" property="srvBasis" id="srvBasis" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS"/>
							<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="srvBasisLOV" property="basisLOV">
								<i class="icon ico-expand" ></i>
							</ihtml:nbutton>
						 </div>
					</td>
					<td>
						<ihtml:text  value="" property="disIncSrvPercentage" id="disIncSrvPercentage" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px"/>
					</td>
					<td>
						<div class="input-group" style="width:122px;">
							<ibusiness:litecalendar  property="disIncSrvValidFrom" value="" 
							id="disIncSrvValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" />
						 </div>
					</td>
					<td>
						<div class="input-group" style="width:122px;">
							<ibusiness:litecalendar id="disIncSrvValidTo"  property="disIncSrvValidTo"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" value="" />
						 </div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>