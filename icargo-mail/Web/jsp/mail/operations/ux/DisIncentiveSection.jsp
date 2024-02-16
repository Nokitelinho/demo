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
<ihtml:hidden property="servRespFlag" />
<ihtml:hidden property="disIncFlag" />		

 <jsp:include page="ServiceResponsiveSection.jsp" /> <!-- For ServiceResponsive Lane Records-->
 <jsp:include page="NonServiceResponsiveSection.jsp" /> <!-- For Non-ServiceResponsive Lane Records-->
 <!-- For BOTH Service & NonService-Responsive Lane Records-->
 <div class="card-body p-0" id="bothDisIncentive">
	<div id="dataTableContainer" class="dataTableContainer tablePanel tableonly-container w-100" >
		<table class="table-x-md m-0 table w-100" id="disIncentiveTable" style="display:none;">
			<thead>
				<tr>
					<th class="text-center check-box-cell"><input type="checkbox" name="checkAllBothDisincentive" onclick="updateHeaderCheckBox(this.form,this,this.form.disIncBothSrvRowId);"></th>
					<th class="text-center param"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.parameter" /></th>
					<th class="text-center formul"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.formula" /></th>
					<th class="text-center bas"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.basis" /></th>
					<th class="text-center perc"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.percentage" /></th>
					<th class="text-center valfrm"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validfrom" /></th>
					<th class="text-center valto"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validto" /></th>
					
				</tr>
			</thead>
			<tbody id="disIncentiveTableBody">
				<logic:present name="incentiveConfigurationVOs"  >
				<logic:iterate id="incentiveConfigurationVO" name="incentiveConfigurationVOs" indexId="rowCount" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO" >
				<logic:equal name="incentiveConfigurationVO" property="incentiveFlag" value="N">
				
				
								
										
										
										
									
									
							
							
							
										
						
				
				
								
										
										
											
									
									
										
							
							
							
						
				
				
				<logic:equal name="incentiveConfigurationVO" property="serviceRespFlag" value="B">
				<tr id="disIncentiveBothServiceResp<%=String.valueOf(rowCount)%>" >
					<logic:present name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags"> 
						<bean:define id="disIncBothSrvOperationFlags" name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" toScope="request" />
						<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="D">
							<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncBothSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
							</td>
							<td>
									<%
										StringBuilder parameter = new StringBuilder();
										StringBuilder displayParameter = new StringBuilder();
										StringBuilder excFlag = new StringBuilder();
										String code = null;
									%>
							<logic:present name="incentiveConfigurationVO" property="incentiveConfigurationDetailVOs">
							<bean:define id="incentiveConfigurationDetails" name="incentiveConfigurationVO" type="java.util.Collection" property="incentiveConfigurationDetailVOs"/>
								<logic:iterate id="incentiveConfigurationDetailVO" name="incentiveConfigurationDetails" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
									<ihtml:hidden property="disIncBothSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
									<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncBothSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
								
										<%
										if("CAT".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))
											code = "CATEGORY";
										else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))
											code = "SERVICE LEVEL";
										else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))	
											code = "SUBCLASS";
										else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))	
											code = "AMOT";
										if(parameter == null){
											parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()).append(" : ")
																	.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue());
										}else{
											parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode())
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue());
										}
										if(displayParameter == null){
											displayParameter = displayParameter.append(code).append(" : ")
																.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue())
																.append(" (")
																.append("Y".equals(incentiveConfigurationDetailVO.getBothSrvExcludeFlag()) ? "I" : "E")
																.append(")");
										}else{
											displayParameter = displayParameter.append("\n").append(code)
															.append(" : ").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue())
															.append(" (")
															.append("Y".equals(incentiveConfigurationDetailVO.getBothSrvExcludeFlag()) ? "I" : "E")
															.append(")");
										}
										if(excFlag == null || excFlag.length()== 0){
											excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getBothSrvExcludeFlag());
										}else{
											excFlag = 	excFlag.append(",").append(
														incentiveConfigurationDetailVO.getBothSrvExcludeFlag());
										}
										%>
								</logic:iterate>
							</logic:present >
									<% String bothSrvParamId = "disIncBothSrvParameter";
										String bothSrvDisplayParamId = "bothSrvDisplayParameter";
								       String bothSrvParamIdLOV = "disIncBothSrvParameterLOV";
									   String bothSrvExcFlag = "excBothSrvFlag"; %>
								<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
									
											<div class="input-group" style="width:208px;">
												<ihtml:hidden property="disIncBothSrvParameter" id="<%=bothSrvParamId+String.valueOf(rowCount)%>" style="height:70px;" value="<%=parameter.toString()%>"  styleClass="form-control" />
												
												<ihtml:textarea property="bothSrvDisplayParameter" id="<%=bothSrvDisplayParamId+String.valueOf(rowCount)%>" style="height:70px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>"  styleClass="form-control" />
												<div class="input-group-append">									
													<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="<%=bothSrvParamIdLOV+String.valueOf(rowCount)%>" property="parameterLOV">
													<i class="icon ico-expand" ></i>
													</ihtml:nbutton>
												</div>
											</div>
											<ihtml:hidden property="bothSrvExcFlag" id="<%=bothSrvExcFlag+String.valueOf(rowCount)%>" value="<%=excFlag.toString()%>" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
									<div class="input-group" style="width:208px;">
										<ihtml:hidden property="disIncBothSrvParameter" id="<%=bothSrvParamId+String.valueOf(rowCount)%>" style="height:70px;" value="<%=parameter.toString()%>"  styleClass="form-control" />
										
										<ihtml:textarea property="bothSrvDisplayParameter" id="<%=bothSrvDisplayParamId+String.valueOf(rowCount)%>" style="height:70px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" value="<%=displayParameter.toString()%>" readonly="true" styleClass="form-control" />
										
										
										<ihtml:hidden property="bothSrvExcFlag" id="excBothSrvFlag" value="<%=excFlag.toString()%>" />	
									</div>
								</logic:notEqual>
									<br/>
							</td>
							<td>
									<%
										String formula="";
										String newFormula = "";
										if(incentiveConfigurationVO.getBothSrvFormula()!= null){
											String formulaExp=incentiveConfigurationVO.getBothSrvFormula();
											formula=formulaExp.replaceAll("~","");
											newFormula = incentiveConfigurationVO.getBothSrvFormula();
											if(newFormula.contains(">"))
											newFormula = newFormula.replaceAll(">","GT");
										   if(newFormula.contains("<"))
											newFormula = newFormula.replaceAll("<","LT");
										}
								   %>
								<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<% String bothSrvformulaId  = "bothSrvformula";
										   String bothSrvformulaLOV  = "bothSrvformulaLOV";
										   String bothSrvDisplayformulaId  = "bothSrvDisplayformula";%>
										<div class="input-group">
										<ihtml:hidden property="bothSrvFormula" id="<%=bothSrvformulaId+String.valueOf(rowCount)%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" value="<%=newFormula%>"/>
										
										<ihtml:text value="<%=formula%>" property="bothSrvDisplayFormula" id="<%=bothSrvDisplayformulaId+String.valueOf(rowCount)%>" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
										<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="<%=bothSrvformulaLOV+String.valueOf(rowCount)%>" property="formulaLOV" >
											<i class="icon ico-expand" ></i>
										</ihtml:nbutton>
									</div>	
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
									
										<ihtml:hidden property="bothSrvFormula" value="<%=newFormula%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" />
										<ihtml:text value="<%=formula%>" property="bothSrvDisplayFormula" id="bothSrvDisplayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<% String bothSrvBasisId  = "bothSrvBasis"; %>
										<% String bothSrvBasisLOV  = "bothSrvBasisLOV";
										   String temp = 	"tempBothSrvBasis";%>
										<div class="input-group" style="width:150px;">
										<ihtml:hidden property="tempBothSrvBasis" id="<%=temp+String.valueOf(rowCount)%>" />
											<ihtml:text property="bothSrvBasis" id="<%=bothSrvBasisId+String.valueOf(rowCount)%>" name="incentiveConfigurationVO" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS"   />
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="<%=bothSrvBasisLOV+String.valueOf(rowCount)%>" property="basisLOV">
												<i class="icon ico-expand" ></i>
											</ihtml:nbutton>
										</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<ihtml:text property="bothSrvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<ihtml:text property="disIncBothSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncBothSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" styleClass="form-control" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<ihtml:text property="disIncBothSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncBothSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
								<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar id="disIncBothSrvValidFrom" 
										componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncBothSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncBothSrvValidFrom()%>" styleClass="form-control" />
									</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
										<ihtml:text property="disIncBothSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncBothSrvValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
									</logic:notEqual>
								</td>
								<td>
									<% String validTo  = "disIncBothSrvValidTo"; %>
									<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncBothSrvValidTo"  value="<%=incentiveConfigurationVO.getDisIncBothSrvValidTo()%>" />
									</div>
									</logic:equal>
									<logic:notEqual name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="I">
									<div class="input-group" style="width:122px;">
										<ibusiness:litecalendar tabindex="5"  
										styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
										property="disIncBothSrvValidTo"  value="<%=incentiveConfigurationVO.getDisIncBothSrvValidTo()%>" />
									</div>
								</logic:notEqual>
							</td>
							
						</logic:notEqual>
						<logic:equal name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags" value="D">
							<ihtml:hidden property="disIncBothSrvParameterType" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncBothSrvParameterCode" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="bothSrvExcludeFlag" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="disIncBothSrvParameterValue" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="bothSrvFormula" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="bothSrvBasis" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncBothSrvPercentage" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncBothSrvValidFrom" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="disIncBothSrvValidTo" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="incFlag" name="incentiveConfigurationVO"/>
							<ihtml:hidden property="serviceResponsiveFlag" name="incentiveConfigurationVO"/>
						</logic:equal>
						<ihtml:hidden property="disIncBothSrvOperationFlags" value="<%=((String)disIncBothSrvOperationFlags)%>" />
					</logic:present>
					<logic:notPresent name="incentiveConfigurationVO" property="disIncBothSrvOperationFlags">
						<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="disIncBothSrvRowId" value="<%=String.valueOf(rowCount)%>"/>
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
							<ihtml:hidden property="disIncBothSrvParameterType" name="incentiveConfigurationDetailVO" value = "D"/>
							
							<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="disIncBothSrvDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
							
							<%
							if("CAT".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))
								code = "CATEGORY";
							else if("PRD".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))
								code = "SERVICE LEVEL";
							else if("SCL".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))	
								code = "SUBCLASS";
							else if("AMT".equals(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()))	
								code = "AMOT";
							if(parameter == null){
								parameter = parameter.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode()).append(" : ")
														.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue());
							}else{
								parameter = parameter.append("\n").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterCode())
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue());
							}
							if(displayParameter == null){
								displayParameter = displayParameter.append(code).append(" : ")
													.append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue())
													.append(" (")
													.append("Y".equals(incentiveConfigurationDetailVO.getBothSrvExcludeFlag()) ? "I" : "E")
													.append(")");
							}else{
								displayParameter = displayParameter.append("\n").append(code)
												.append(" : ").append(incentiveConfigurationDetailVO.getDisIncBothSrvParameterValue())
												.append(" (")
												.append("Y".equals(incentiveConfigurationDetailVO.getBothSrvExcludeFlag()) ? "I" : "E")
												.append(")");
							}
							if(excFlag == null || excFlag.length()== 0){
								excFlag = 	excFlag.append(incentiveConfigurationDetailVO.getBothSrvExcludeFlag());
							}else{
								excFlag = 	excFlag.append(",").append(
											incentiveConfigurationDetailVO.getBothSrvExcludeFlag());
							}
							%>
								</logic:iterate>
						</logic:present >
								<div class="input-group" style="width:208px;">
									<ihtml:hidden property="disIncBothSrvParameter" style="height:70px;" value="<%=parameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" disabled="true" styleClass="form-control"  />
									<ihtml:textarea property="bothSrvDisplayParameter" style="height:70px;" value="<%=displayParameter.toString()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN"  readonly="true" styleClass="form-control"  />
									<ihtml:hidden property="bothSrvExcFlag" value="<%=excFlag.toString()%>" />
								</div>
						</td>

						<td>
						
						<%
										String formula="";
								
											String formulaExp=incentiveConfigurationVO.getBothSrvFormula();
											formula=formulaExp.replaceAll("~","");
											String newFormula = incentiveConfigurationVO.getBothSrvFormula();
											if(newFormula.contains(">"))
											newFormula = newFormula.replaceAll(">","GT");
										   if(newFormula.contains("<"))
											newFormula = newFormula.replaceAll("<","LT");
										
								   %>
							<ihtml:hidden property="bothSrvFormula" value="<%=newFormula%>" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"  styleClass="form-control" />
							<ihtml:text value="<%=formula%>" property="bothSrvDisplayFormula" id="displayFormula" styleClass="form-control" readonly="true" style="width:400px"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						</td>
						<td>
						<div class="input-group" style="width:150px;">
							<ihtml:text property="bothSrvBasis" name="incentiveConfigurationVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS" style="width:150px" readonly="true" styleClass="form-control" />
						</div>
						</td>
						<td>
							<ihtml:text property="disIncBothSrvPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getDisIncBothSrvPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px" readonly="true" styleClass="form-control" />
						</td>
						<td>
						<div class="input-group" style="width:122px;">
							<ihtml:text property="disIncBothSrvValidFrom" value="<%=incentiveConfigurationVO.getDisIncBothSrvValidFrom()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" readonly="true" styleClass="form-control" />
						</div>
						</td>
						<td>
						<% String validTo  = "disIncBothSrvValidTo"; %>
						<div class="input-group" style="width:122px;">
							<ibusiness:litecalendar property="disIncBothSrvValidTo"
							styleClass="form-control" id="<%=validTo+String.valueOf(rowCount)%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
							  value="<%=incentiveConfigurationVO.getDisIncBothSrvValidTo()%>" />
						</div>
						</td>
						
						<ihtml:hidden property="disIncBothSrvOperationFlags" value="U" />
					</logic:notPresent>
				</tr>
				</logic:equal>
			
				</logic:equal>
				</logic:iterate>
			</logic:present>
			<tr  template="true" id="disIncentiveTemplateRow" style="display:none">
				<ihtml:hidden property="disIncBothSrvOperationFlags" value="NOOP" />
				<ihtml:hidden property="incFlag" value="N" />
				<ihtml:hidden property="disIncBothSrvParameterType" value="D" />
				
					<td class="text-center">
						<input type="checkbox"  name="disIncBothSrvRowId"/>
					</td>
					<td class="iCargoTableDataTd">
						<div class="input-group" style="width:208px;">
						<ihtml:hidden  value="" property="disIncBothSrvParameter" id="disIncBothSrvParameter" styleClass="form-control" style="height:70px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" />
						<ihtml:textarea  value="" property="bothSrvDisplayParameter" id="bothSrvDisplayParameter" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" style="height:70px;" styleClass="form-control" />
							
							<ihtml:hidden property="bothSrvExcFlag" id="excBothSrvFlag" value="" />
							<ihtml:hidden property="disIncBothSrvParameterType" value="D" />
							
							<div class="input-group-append">									
								 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
								  id="disIncBothSrvParameterLOV" property="parameterLOV">
								  <i class="icon ico-expand" ></i>
								 </ihtml:nbutton>
							</div>
						</div>
					 </td>
					 <td>
					 <div class="input-group">
						<ihtml:hidden value=""  property="bothSrvFormula" id="bothSrvformula" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						<ihtml:text value="" property="bothSrvDisplayFormula" id="bothSrvDisplayformula" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA"/>
						 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
						  id="bothSrvformulaLOV" property="formulaLOV" >
						  <i class="icon ico-expand" ></i>
						 </ihtml:nbutton>
						</div>
						
					 </td>
					<td>
					<div class="input-group" style="width:150px;">
					<ihtml:hidden property="tempBothSrvBasis" id="tempBothSrvBasis" value="" />
				<ihtml:text value="" property="bothSrvBasis" id="bothSrvBasis" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS"/>
				 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary" id="bothSrvBasisLOV" property="basisLOV">
				  
							<i class="icon ico-expand" ></i>
						 </ihtml:nbutton>
						 </div>
					</td>
					<td>
				<ihtml:text  value="" property="disIncBothSrvPercentage" id="disIncBothSrvPercentage" styleClass="form-control"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:60px"/>
					</td>
					<td>
					<div class="input-group" style="width:122px;">
				<ibusiness:litecalendar  property="disIncBothSrvValidFrom" value="" 
						id="disIncBothSrvValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" />
					 </div>
					</td>
					<td>
					<div class="input-group" style="width:122px;">
				<ibusiness:litecalendar id="disIncBothSrvValidTo"  property="disIncBothSrvValidTo"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" value="" />
					 </div>
					</td>
					
			</tr>
		</tbody>
	</table>
	</div>
</div>