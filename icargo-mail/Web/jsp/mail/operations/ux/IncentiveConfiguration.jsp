<!--IncentiveConfiguration!-->
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
<div class="card-header d-flex left-content-end" id="incentiveSection">
	<div class="form-check form-check-inline">
		<input type="radio" name="incFlag" tabindex="17" class="form-check-input" id="disIncentiveRadioBtn" value="disincentive"
		onclick="toggleResponsivePanel('disincentive')" checked="true"  >
		<label class="form-check-label" for="disIncentiveRadioBtn"> <common:message  key="mailtracking.defaults.ux.mailperformance.lbl.disincentive" /> </label>
	</div>&nbsp;&nbsp;
	<div class="form-check form-check-inline">
		<input type="radio" name="incFlag" tabindex="17" class="form-check-input" id="incentRadioBtn" value="incentive"
		onclick="toggleResponsivePanel('incentive')"  >
		<label class="form-check-label" for="incentRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.incentive" /></label>
	</div>
 </div>	  
 <div class="card-header d-flex left-content-end" id="serviceResponsiveSection">
	 <div class="form-check form-check-inline">
		<input type="radio" name="serviceResponsiveFlag" tabindex="17" class="form-check-input" id="srvRespRadioBtn" value="Y"
		onclick="toggleServiceResponsivePanel('ServiceResponsive')" >
		<label class="form-check-label" for="srvRespRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.serviceresponsivelanes" /> </label>
	</div> &nbsp;&nbsp;
	<div class="form-check form-check-inline">
		<input type="radio" name="serviceResponsiveFlag" tabindex="17" class="form-check-input" id="nonSrvRespRadioBtn" value="N"
		onclick="toggleServiceResponsivePanel('NonServiceResponsive')"  >
		<label class="form-check-label" for="nonSrvRespRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.nonserviceresponsivelanes" /></label>
	</div>&nbsp;&nbsp;
	<div class="form-check form-check-inline" >
		<input type="radio" name="serviceResponsiveFlag" tabindex="17" class="form-check-input" id="bothRadioBtn" value="B"
		onclick="toggleServiceResponsivePanel('BothServiceResponsive')"  >
		<label class="form-check-label" for="bothRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.both" /> </label>
	</div>
 </div>	  
<div class="card-header d-flex justify-content-end">
	<div class="tool-bar align-items-center  pad-y-2sm">
		  <ihtml:nbutton id="btnIncentiveAdd" styleClass="btn btn-primary mar-r-2xs"  property="btnIncentiveAdd" 	componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
			<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
			</ihtml:nbutton>
		 <ihtml:nbutton property="btnDelete" id="btnIncentiveDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
		<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
		</ihtml:nbutton>
	 </div>
 </div>	       

	<!-- DisIncentiveTable -->
	 <jsp:include page="DisIncentiveSection.jsp" /> 
					
	<!-- IncentiveTable Starts -->
							
							
						
				
						
							
							
						
					
<div class="card-body p-0" id="incentive">
	<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%" >
	<table class="table-x-md m-0 table" id="incentiveTable" style="display:none;width:100%;">
			<thead>
				<tr>
					<th class="text-center check-box-cell"><input type="checkbox" name="checkAllIncentive" onclick="updateHeaderCheckBox(this.form,this,this.form.incRowId);"></th>
					<th class="text-center"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.from" /></th>
					<th class="text-center"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.to" /></th>
					<th class="text-center"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.percentage" /></th>
					<th class="text-center"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validfrom" /></th>
					 <th class="text-center"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.validto" /></th>
					
				</tr>
			</thead>
			<tbody id="incentiveTableBody">
				<logic:present name="incentiveConfigurationVOs"  >
				<logic:iterate id="incentiveConfigurationVO" name="incentiveConfigurationVOs" indexId="rowCount" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO" >
				<logic:equal name="incentiveConfigurationVO" property="incentiveFlag" value="Y">
				
					<logic:present name="incentiveConfigurationVO" property="incOperationFlags"> 
						<bean:define id="incOperationFlags" name="incentiveConfigurationVO" property="incOperationFlags" toScope="request" />
						<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="D">
						<tr>
							<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="incRowId" value="<%=String.valueOf(rowCount)%>"/>
							</td>
						<!-- 	Parameter td  -->
						<logic:present name="incentiveConfigurationVO" property="incentiveConfigurationDetailVOs"  >
							<bean:define id="incentiveConfigurationDetails" name="incentiveConfigurationVO" type="java.util.Collection" property="incentiveConfigurationDetailVOs"/>
							<logic:iterate id="incentiveConfigurationDetailVO" name="incentiveConfigurationDetails" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
								<ihtml:hidden property="incParameterType" name="incentiveConfigurationDetailVO" value = "I"/>
								<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="incDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
								<logic:equal name="incentiveConfigurationDetailVO" property="incParameterCode" value="PERFRM">
									<td>
									<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
										<ihtml:text property="incParameterValue" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" style="width:200px;display:inline;" styleClass="form-control" />
										<ihtml:hidden  value="PERFRM" property="incParameterCode" id="incParameterCode" />
										<ihtml:hidden property="incParameterType" value="I" />
									</logic:equal>
									<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">
										<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"  readonly="true" styleClass="form-control" />
										<ihtml:hidden  value="PERFRM" property="incParameterCode" id="incParameterCode" />
										<ihtml:hidden property="incParameterType" value="I" />
									</logic:notEqual>
									</td>
								</logic:equal>
								<logic:equal name="incentiveConfigurationDetailVO" property="incParameterCode" value="PERTOO">
									<td>
									<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
										<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>"   componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" styleClass="form-control" />
										<ihtml:hidden  value="PERTOO" property="incParameterCode" id="incParameterCode" />
										<ihtml:hidden property="incParameterType" value="I" />
									</logic:equal>
									<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">	
										<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>"   componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN"  readonly="true" styleClass="form-control" />
										<ihtml:hidden  value="PERTOO" property="incParameterCode" id="incParameterCode" />
										<ihtml:hidden property="incParameterType" value="I" />
									</logic:notEqual>
									</td>
								</logic:equal>
							</logic:iterate>
						</logic:present >
						<!--  -->
							<td>
								<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
									<ihtml:text property="incPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getIncPercentage())%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:200px" styleClass="form-control" />
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">
									<ihtml:text property="incPercentage" readonly="true" value="<%=String.valueOf(incentiveConfigurationVO.getIncPercentage())%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:200px" styleClass="form-control"  />
								</logic:notEqual>
							</td>
							<td>
								<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
								<% String validfrom = "incValidFrom";%>
									<div class="input-group" style="width:220px;">
										<ibusiness:litecalendar property="incValidFrom" id="<%=validfrom+String.valueOf(rowCount)%>" value="<%=incentiveConfigurationVO.getIncValidFrom()%>"   styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"/>
									</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">
									<ihtml:text property="incValidFrom" value="<%=incentiveConfigurationVO.getIncValidFrom()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM" style="width:220px" styleClass="form-control" readonly="true"/>
								</logic:notEqual>
							</td>
							<td><% String validTo = "incValidTo";%>
								<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
								
								<div class="input-group" style="width:220px;">
									<ibusiness:litecalendar property="incValidTo" id="<%=validTo+String.valueOf(rowCount)%>" value="<%=incentiveConfigurationVO.getIncValidTo()%>"  styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"  />	
								</div>
								</logic:equal>
								<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">
								<div class="input-group" style="width:220px;">
									<ibusiness:litecalendar property="incValidTo" id="<%=validTo+String.valueOf(rowCount)%>" value="<%=incentiveConfigurationVO.getIncValidTo()%>"  styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"  />
								</div>	
								</logic:notEqual>
							</td>
							<ihtml:hidden property="incOperationFlags" value="<%=((String)incOperationFlags)%>" />
							
						</tr>	
						</logic:notEqual>
						<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="D">
						<tr style="display:none;">
							<td>
							<ihtml:hidden property="incParameterCode" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="incParameterValue" name="incentiveConfigurationDetailVO"/>
							<ihtml:hidden property="incParameterType" name="incentiveConfigurationDetailVO"/>
							</td>
							<td>
								<ihtml:hidden property="incParameterCode" name="incentiveConfigurationDetailVO"/>
								<ihtml:hidden property="incParameterValue" name="incentiveConfigurationDetailVO"/>
								<ihtml:hidden property="incParameterType" name="incentiveConfigurationDetailVO"/>
							</td>
							<td>
							<ihtml:hidden property="incPercentage" name="incentiveConfigurationVO"/>
							</td>
							<td>
							<ihtml:hidden property="incvalidFrom" name="incentiveConfigurationVO"/>
							</td>
							<td>
							<ihtml:hidden property="incvalidTo" name="incentiveConfigurationVO"/>
							</td>
						<ihtml:hidden property="incOperationFlags" value="<%=((String)incOperationFlags)%>" />
						</tr>
						</logic:equal>
					</logic:present>
					<logic:notPresent name="incentiveConfigurationVO" property="incOperationFlags">
					<tr>
						<td class="text-center ">
								&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="incRowId" value="<%=String.valueOf(rowCount)%>"/>
						</td>
							<!-- 	Parameter td  -->
						<logic:present name="incentiveConfigurationVO" property="incentiveConfigurationDetailVOs"  >
							<bean:define id="incentiveConfigurationDetails" name="incentiveConfigurationVO" type="java.util.Collection" property="incentiveConfigurationDetailVOs"/>
							<logic:iterate id="incentiveConfigurationDetailVO" name="incentiveConfigurationDetails" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
								<ihtml:hidden property="incParameterType" name="incentiveConfigurationDetailVO" value = "I"/>
								<ihtml:hidden id="<%=String.valueOf(rowCountDetail)%>" property="incDetailRowId" value="<%=String.valueOf(rowCountDetail)%>"/>
								<logic:equal name="incentiveConfigurationDetailVO" property="incParameterCode" value="PERFRM">
									<td>
										<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
											<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"  readonly="true" styleClass="form-control" />
										</logic:equal>
										<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">
											<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER" readonly="true" styleClass="form-control" />
										</logic:notEqual>
										<ihtml:hidden  value="PERFRM" property="incParameterCode" id="incParameterCode" />
									</td>
								</logic:equal>
								
								<logic:equal name="incentiveConfigurationDetailVO" property="incParameterCode" value="PERTOO">
									<td>
										<logic:equal name="incentiveConfigurationVO" property="incOperationFlags" value="I">
											<ihtml:text property="incParameterValue" style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" readonly="true" styleClass="form-control" />
										</logic:equal>
										<logic:notEqual name="incentiveConfigurationVO" property="incOperationFlags" value="I">	
											<ihtml:text property="incParameterValue"  style="width:200px;display:inline;" value="<%=incentiveConfigurationDetailVO.getIncParameterValue()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN"  readonly="true" styleClass="form-control" />
										</logic:notEqual>
									</td>
									<ihtml:hidden  value="PERTOO" property="incParameterCode" id="incParameterCode" />
								</logic:equal>
							</logic:iterate>
						</logic:present >
						<!--  -->
						<td>
							<ihtml:text property="incPercentage" value="<%=String.valueOf(incentiveConfigurationVO.getIncPercentage())%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE" style="width:200px" readonly="true" styleClass="form-control" />
						</td>
						<td>
							<ihtml:text property="incValidFrom" value="<%=incentiveConfigurationVO.getIncValidFrom()%>"  style="width:220" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM" styleClass="form-control" />
						</td>
						<td>
                              <% String validTo = "incValidTo";%>
								<div class="input-group" style="width:220px;">
									<ibusiness:litecalendar property="incValidTo" id="<%=validTo+String.valueOf(rowCount)%>" value="<%=incentiveConfigurationVO.getIncValidTo()%>"  styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"  />	
								</div>
						</td>
						
						<ihtml:hidden property="incOperationFlags" value="U" />
					</tr>
					</logic:notPresent>
				
				</logic:equal>
				<logic:notEqual name="incentiveConfigurationVO" property="incentiveFlag" value="Y">
				<tr></tr>
				</logic:notEqual>
				</logic:iterate>
			</logic:present >
			<tr  template="true" id="incentiveTemplateRow" style="display:none">
				<ihtml:hidden property="incOperationFlags" value="NOOP" />
				<ihtml:hidden property="incFlag" value="Y" />
				
					<td class="text-center">
						<input type="checkbox"  name="incRowId"/>
					</td>
					 <td>
					 <input type="hidden" name="incDetailRowId" value="NO" />
						<ihtml:text  value="" style="width:200px;" property="incParameterValue" id="incParameterValue" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"/>
						<ihtml:hidden  value="PERFRM" property="incParameterCode" id="incParameterCode" />
						<ihtml:hidden property="incParameterType" value="I" />
					 </td>
					<td>
						<ihtml:text  value="" property="incParameterValue" id="incParameterValue" styleClass="form-control" style="width:200px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PARAMETER"/>
						<ihtml:hidden  value="PERTOO" property="incParameterCode" id="incParameterCode" />
						<ihtml:hidden property="incParameterType" value="I" />
					</td>
					<td>
						<ihtml:text  value="" style="width:200px;" property="incPercentage" id="incPercentage" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_PERCENTAGE"/>
					</td>
					<td>
						<div class="input-group" style="width:220px;">
						<ibusiness:litecalendar property="incValidFrom" value="" 
						styleClass="form-control" id="incValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" 
						/>
						</div>
					</td>
					<td>
					<div class="input-group" style="width:220px;">
						<ibusiness:litecalendar  property="incValidTo" value="" 
						styleClass="form-control" id="incValidTo"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" />
						</div
					</td>
					
			</tr>
		</tbody>
	</table>
	</div>
</div>
<!-- IncentiveTable Ends -->