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
<head>
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
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
<div class="card-body p-0" id="nonSrvDisIncentive">
	<div id="dataTableContainer" class="dataTableContainer tablePanel tableonly-container w-100">
		<table class="table-x-md m-0 w-100 table" id="disIncentiveNonSrvTable" style="display:none;">
			<thead>
				<tr>
					<th class="text-center check-box-cell"><input type="checkbox" name="checkAllNonSrvDisincentive" onclick="updateHeaderCheckBox(this.form,this,this.form.disIncNonSrvRowId);"></th>
					<th class="text-center param"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.parameter" /></th>
					<th class="text-center formul"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.formula" /></th>
					<th class="text-center bas"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.basis" /></th>
					<th class="text-center perc"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.percentage" /></th>
					<th class="text-center valfrm"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validfrom" /></th>
					<th class="text-center valto"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validto" /></th>
				</tr>
			</thead>
			<tbody id="disIncentiveNonSrvTableBody">
				<logic:present name="incentiveConfigurationVOs"  >
				<logic:iterate id="incentiveConfigurationVO" name="incentiveConfigurationVOs" indexId="rowCount" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO" >
				<logic:equal name="incentiveConfigurationVO" property="incentiveFlag" value="N">
				<logic:equal name="incentiveConfigurationVO" property="serviceRespFlag" value="N">
				<tr id="disIncentiveNonServiceResp<%=String.valueOf(rowCount)%>" >
					<logic:present name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags"> 
						<bean:define id="disIncNonSrvOperationFlags" name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" toScope="request" />
						<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="D">
							<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncNonSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
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
										<ihtml:hidden property="disIncNonSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
									<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncNonSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
										<%
										if("CAT".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())){
											code = "CATEGORY";
										}else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())){
											code = "SERVICE LEVEL";
										}else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())){	
											code = "SUBCLASS";
										}else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())){
											code = "AMOT";
										}	
										if(parameter == null){
											parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()).append(" : ")
																	.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue());
										}else{
											parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue());
										}
										if(displayParameter == null){
											displayParameter = displayParameter.append(code).append(" : ")
																.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue())
																.append(" (")
																.append("Y".equals(incentiveConfigurationDetailVO.getNonSrvExcludeFlag()) ? "I" : "E")
																.append(")");
										}else{
											displayParameter = displayParameter.append("\n").append(code)
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue())
															.append(" (")
															.append("Y".equals(incentiveConfigurationDetailVO.getNonSrvExcludeFlag()) ? "I" : "E")
															.append(")");
										}
										if(excFlag == null || excFlag.length()== 0){
											excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getNonSrvExcludeFlag());
										}else{
											excFlag = 	excFlag.append(",").append(
														incentiveConfigurationDetailVO.getNonSrvExcludeFlag());
										}
										%>
								</logic:iterate>
							</logic:present >
										<% String NonSrvParamId = "disIncNonSrvParameter";
										   String NonSrvDispParamId = "nonSrvDisplayParameter";
										   String NonSrvParamIdLOV = "disIncNonSrvParameterLOV";
										   String NonSrvExcFlag = "excNonSrvFlag";%>
								<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										
											<div class="input-group" style="width:208px;">
												<ihtml:hidden property="disIncNonSrvParameter" id="<%=NonSrvParamId+String.valueOf(rowCount)%>" style="height:70px;" value="<%=parameter.toString()%>"  styleClass="form-control" />
												
												<ihtml:textarea property="nonSrvDisplayParameter" id="<%=NonSrvDispParamId+String.valueOf(rowCount)%>" style="height:70px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>" styleClass="form-control" />
												<div class="input-group-append">									
													<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"  property="parameterLOV" id="<%=NonSrvParamIdLOV+String.valueOf(rowCount)%>">
													<i class="icon ico-expand" ></i>
													</ihtml:nbutton>
												</div>
											</div>
										<ihtml:hidden property="nonSrvExcFlag" id="<%=NonSrvExcFlag+String.valueOf(rowCount)%>" value="<%=excFlag.toString()%>" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
											<div class="input-group">
												<ihtml:hidden property="disIncNonSrvParameter" id="<%=NonSrvParamId+String.valueOf(rowCount)%>" style="height:65px;" value="<%=parameter.toString()%>"  styleClass="form-control" />
												
												<ihtml:textarea property="nonSrvDisplayParameter" id="<%=NonSrvDispParamId+String.valueOf(rowCount)%>" style="height:70px;" readonly="true"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>" styleClass="form-control" />
												
											</div>
										<ihtml:hidden property="nonSrvExcFlag" id="excNonSrvFlag" value="<%=excFlag.toString()%>" />
								</logic:notEqual>
									<br/>
							</td>
							<td>
								<%
									String formula="";
									String newFormula = "";
									if(incentiveConfigurationVO.getNonSrvFormula() != null){
										String formulaExp=incentiveConfigurationVO.getNonSrvFormula();
										formula=formulaExp.replaceAll("~","");
										newFormula = incentiveConfigurationVO.getNonSrvFormula();
										if(newFormula.contains(">"))
											newFormula = newFormula.replaceAll(">","GT");
										if(newFormula.contains("<"))
											newFormula = newFormula.replaceAll("<","LT");
									}
							   %>
								<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
									<% String NonSrvformulaId  = "nonSrvformula";
									   String NonSrvDisplayformulaId  = "nonSrvDisplayformula";
									   String NonSrvformulaLOV  = "nonSrvformulaLOV";%>
									<div class="input-group">
										<ihtml:hidden property="nonSrvFormula" id="<%=NonSrvformulaId+String.valueOf(rowCount)%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" value="<%=newFormula%>" />
										<ihtml:text value="<%=formula%>" property="nonSrvDisplayFormula" id="<%=NonSrvDisplayformulaId+String.valueOf(rowCount)%>" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
										<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon" id="<%=NonSrvformulaLOV+String.valueOf(rowCount)%>" property="formulaLOV" >
											<i class="icon ico-expand" ></i>
										</ihtml:nbutton>
									</div>	
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<ihtml:hidden property="nonSrvFormula" value="<%=newFormula%>"  name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" />
										<ihtml:text value="<%=formula%>" property="nonSrvDisplayFormula" id="displayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<% String NonSrvBasisId  = "nonSrvBasis"; 
										 String NonSrvBasisIdLOV  = "nonSrvBasisLOV";
										 String temp = "tempNonSrvBasis"; %>
										<div class="input-group" style="width:150px;">
										<ihtml:hidden property="tempNonSrvBasis" id="<%=temp+String.valueOf(rowCount)%>" />
											<ihtml:text property="nonSrvBasis" id="<%=NonSrvBasisId+String.valueOf(rowCount)%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" styleClass="form-control" />
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="<%=NonSrvBasisIdLOV+String.valueOf(rowCount)%>" property="basisLOV">
												<i class="icon ico-expand" ></i>
											</ihtml:nbutton>
										</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<ihtml:text property="nonSrvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<ihtml:text property="disIncNonSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncNonSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" styleClass="form-control" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<ihtml:text property="disIncNonSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncNonSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
								<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar
										id="disIncNonSrvValidFrom" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncNonSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncNonSrvValidFrom()%>"   />
									</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ihtml:text property="disIncNonSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncNonSrvValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
									</div>
									</logic:notEqual>
								</td>
								<td>
									<% String validTo  = "disIncNonSrvValidTo"; %>
									<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncNonSrvValidTo" value="<%=incentiveConfigurationVO.getDisIncNonSrvValidTo()%>" />
									</div>
									</logic:equal>
									<logic:notEqual name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="I">
										<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncNonSrvValidTo" value="<%=incentiveConfigurationVO.getDisIncNonSrvValidTo()%>" />
									</div>	
								</logic:notEqual>
							</td>
						</logic:notEqual>
						<logic:equal name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags" value="D">
							<ihtml:hidden property="disIncNonSrvParameterType" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncNonSrvParameterCode" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="nonSrvExcludeFlag" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncNonSrvParameterValue" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="nonSrvFormula" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="nonSrvBasis" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncNonSrvPercentage" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncNonSrvValidFrom" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncNonSrvValidTo" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="incFlag" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="serviceResponsiveFlag" name="incentiveConfigurationVO"/>
						</logic:equal>
						<ihtml:hidden	property="disIncNonSrvOperationFlags" value="<%=((String)disIncNonSrvOperationFlags)%>" />
					</logic:present>
					<logic:notPresent name="incentiveConfigurationVO" property="disIncNonSrvOperationFlags">
						<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncNonSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
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
							<ihtml:hidden property="disIncNonSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
							<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncNonSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
							<%
							if("CAT".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()))
								code = "CATEGORY";
							else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()))
								code = "SERVICE LEVEL";
							else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()))	
								code = "SUBCLASS";
							else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()))
									code = "AMOT";
							if(parameter == null){
								parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode()).append(" : ")
														.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue());
							}else{
								parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterCode())
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue());
							}
							if(displayParameter == null){
								displayParameter = displayParameter.append(code).append(" : ")
													.append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue())
													.append(" (")
													.append("Y".equals(incentiveConfigurationDetailVO.getNonSrvExcludeFlag()) ? "I" : "E")
													.append(")");
							}else{
								displayParameter = displayParameter.append("\n").append(code)
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncNonSrvParameterValue())
												.append(" (")
												.append("Y".equals(incentiveConfigurationDetailVO.getNonSrvExcludeFlag()) ? "I" : "E")
												.append(")");
							}
							if(excFlag == null || excFlag.length()== 0 ){
								excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getNonSrvExcludeFlag());
							}else{
								excFlag = 	excFlag.append(",").append(
											incentiveConfigurationDetailVO.getNonSrvExcludeFlag());
							}
							%>
								</logic:iterate>
						</logic:present >
								<div class="input-group" style="width:208px;">
									<ihtml:hidden property="disIncNonSrvParameter" id="disIncNonSrvParameter" style="height:70px;" value="<%=parameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" disabled="true" styleClass="form-control"  />
									<ihtml:hidden property="nonSrvExcFlag" value="<%=excFlag.toString()%>" />
									<ihtml:textarea property="nonSrvDisplayParameter" id="disIncNonSrvParameter" style="height:70px;" value="<%=displayParameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"  readonly="true" styleClass="form-control"  />
								</div>
						</td>
						<%
							String formulaExp=incentiveConfigurationVO.getNonSrvFormula();
							String formula="";
							formula=formulaExp.replaceAll("~","");
							String newFormula = incentiveConfigurationVO.getNonSrvFormula();
							if(newFormula.contains(">"))
								newFormula = newFormula.replaceAll(">","GT");
							if(newFormula.contains("<"))
								newFormula = newFormula.replaceAll("<","LT");
					   %>
						<td>
							<ihtml:hidden property="nonSrvFormula" value="<%=newFormula%>"  name="incentiveConfigurationVO"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"   />
							<ihtml:text value="<%=formula%>" property="nonSrvDisplayFormula" id="displayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						</td>
						<td>
							<div class="input-group" style="width:150px;">
							
							<ihtml:text property="nonSrvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
							</div>
						</td>
						<td>
							<ihtml:text property="disIncNonSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncNonSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
						</td>
						<td>
						<div class="input-group" style="width:122px;">
							<ihtml:text property="disIncNonSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncNonSrvValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
						</div>
						</td>
						<td>
						<% String validTo  = "disIncNonSrvValidTo"; %>
						<div class="input-group" style="width:122px;">
								<ibusiness:litecalendar property="disIncNonSrvValidTo"
								styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
								 value="<%=incentiveConfigurationVO.getDisIncNonSrvValidTo()%>" />
							</div>
						</td>
						<ihtml:hidden property="disIncNonSrvOperationFlags" value="U" />
					</logic:notPresent>
				</tr>
				</logic:equal>
				</logic:equal>
				</logic:iterate>
			</logic:present>
			<tr  template="true" id="disIncentiveNonSrvTemplateRow" style="display:none">
				<ihtml:hidden property="disIncNonSrvOperationFlags" value="NOOP" />
				<ihtml:hidden property="incFlag" value="N" />
				<ihtml:hidden property="disIncNonSrvParameterType" value="D" />
					<td class="text-center">
						<input type="checkbox"  name="disIncNonSrvRowId"/>
					</td>
					<td class="iCargoTableDataTd">
						<div class="input-group" style="width:208px;">
						<ihtml:hidden  value="" property="disIncNonSrvParameter" id="disIncNonSrvParameter" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" style="height:70px;"/>
						<ihtml:textarea  value="" property="nonSrvDisplayParameter" id="nonSrvDisplayParameter" styleClass="form-control"  style="height:70px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"/>
							<ihtml:hidden property="nonSrvExcFlag" id="excNonSrvFlag" value="" />
							<ihtml:hidden property="disIncNonSrvParameterType" value="D" />
							<div class="input-group-append">									
								 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
								  id="disIncNonSrvParameterLOV" property="parameterLOV">
								  <i class="icon ico-expand" ></i>
								 </ihtml:nbutton>
							</div>
						</div>
					 </td>
					 <td>
					 <div class="input-group">
						<ihtml:hidden value=""  property="nonSrvFormula" id="nonSrvformula" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						<ihtml:text value="" property="nonSrvDisplayFormula" id="nonSrvDisplayformula" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
						  id="nonSrvformulaLOV" property="formulaLOV" >
						  <i class="icon ico-expand" ></i>
						 </ihtml:nbutton>
						</div>
					 </td>
					<td>
					<div class="input-group" style="width:150px;">
					<ihtml:hidden property="tempNonSrvBasis" id="tempNonSrvBasis" value="" />
				<ihtml:text value="" property="nonSrvBasis" id="nonSrvBasis" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS"/>
				 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="nonSrvBasisLOV" property="basisLOV">
							<i class="icon ico-expand" ></i>
						 </ihtml:nbutton>
						 </div>
					</td>
					<td>
				<ihtml:text  value="" property="disIncNonSrvPercentage" id="disIncNonSrvPercentage" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px"/>
					</td>
					<td>
					<div class="input-group" style="width:122px;">
				<ibusiness:litecalendar  property="disIncNonSrvValidFrom" value="" 
						id="disIncNonSrvValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" />
					 </div>
					</td>
					<td>
					<div class="input-group" style="width:122px;">
				<ibusiness:litecalendar id="disIncNonSrvValidTo"  property="disIncNonSrvValidTo"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" value="" />
					 </div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>