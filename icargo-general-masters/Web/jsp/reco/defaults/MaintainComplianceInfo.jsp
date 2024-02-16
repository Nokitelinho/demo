<%--
/***********************************************************************
* Project	     	       : iCargo
* Module Code & Name 	   :
* File Name          	   : MaintainEmbargo.jsp
* Date                     : 29-SEP-2005
* Author(s)           	   : K.Santhi
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm"%>

<html:html>
	<head>

		
		<title>
			<common:message bundle="maintainonetimemaster" key="shared.defaults.maintainonetime.lbl.pageTitle" scope="request"/>
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/reco/defaults/MaintainCompliance_Script.jsp"/>
	</head>
<body id="bodyStyle">

	<% 	
		String embargoRefNo="";
		String embargoLevel="";
		String embargoStatusField="";
		String embargoStartDate="";
		String embargoEndDate="";
		boolean embargoSuspend=false;
		boolean embargoCool=false;
		boolean embargoFrozen=false;
		boolean embargoCC=false;
		String  embargoOrigin="";
		String  embargoOriginType="";
		String  embargoDestination="";
		String  embargoDestinationType="";
		String  embargoDescription="";
		String  embargoRemarks="";
		String  embargoParameterCode="";
		String  embargoParameterApplicable="";
		String  embargoParameterValues="";
		String applicableOn = "";
	%>
<bean:define id="form" name="MaintainComplianceInfoForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm"/>

<div class="iCargoContent ic-center ic-masterbg" id="pageDiv" style="width:80%">
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="embargoVo" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="embargoParameters" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="categoryTypes" />

<ihtml:form action="/reco.defaults.maintaincomplianceinfoscreenload.do">
<ihtml:hidden name="form" property="isScreenload" />
<ihtml:hidden name="form" property="isDisplayDetails" />
<ihtml:hidden name="form" property="isSaved" />
<ihtml:hidden name="form" property="isButtonClicked" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="fromList" name="form"/>
<ihtml:hidden property="isPrivilegedUser" name="form" />
<ihtml:hidden property="refNumberFlag" name="form" />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message  key="maintainembargo.maintainembargo"/></span>
		<!-- head-container starts -->
		<div class="ic-head-container">
			
			<div class="ic-filter-panel">
				<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-input ic-mandatory ic-split-30">
								<label>
									<common:message key="maintainembargo.refnumber"/>
								</label>
								<ihtml:text property="refNumber" maxlength="15" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_ReferenceNumber" style="text-transform:uppercase"/>
							
							
							</div>
							
							<div class="ic-input ic-split-70">
								<div class="ic-button-container">
									<ihtml:nbutton   property="btnDisplay" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnDisplay" accesskey="L" ><common:message  key="maintainembargo.displaydetails" /></ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnClear" accesskey="C" ><common:message  key="maintainembargo.clear"/></ihtml:nbutton>
								</div>
							</div>																				
						</div>						
				</div>
			</div>
		</div>
		<!-- head-container end -->
		
		<!-- main-container starts -->
		<div class="ic-main-container">
			<div  class="ic-row">
				<div  class="ic-input-container ic-input-round-border">
					<div  class="ic-row">
						<div class="ic-input ic-mandatory ic-split-30">
								<label>
									<common:message key="maintainembargo.category" />
								</label>
								<logic:present name="embargoVo" property="category">
									<bean:define id="category" name ="embargoVo" property="category" type="String" />
									<ihtml:select property="category"  componentID="CMP_Reco_Defaults_MaintainComplianceInfo_Category" value="<%=(String)category%>" >
									 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										  <logic:present name="categories">
											<logic:iterate id="catgry" name="categories">
												<bean:define id="catgryvalue" name="catgry" property="fieldValue" />
												<html:option value="<%=(String)catgryvalue %>"><bean:write name="catgry" property="fieldDescription" /></html:option>
											</logic:iterate>
										  </logic:present>                 
									</ihtml:select>
								</logic:present>
								<logic:notPresent name="embargoVo" property="category">
									<ihtml:select property="category"  componentID="CMP_Reco_Defaults_MaintainComplianceInfo_Category">
									 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										  <logic:present name="categories">
											<logic:iterate id="catgry" name="categories">
												<bean:define id="catgryvalue" name="catgry" property="fieldValue" />
												<html:option value="<%=(String)catgryvalue %>"><bean:write name="catgry" property="fieldDescription" /></html:option>
											</logic:iterate>
										  </logic:present>                 
									</ihtml:select>
								</logic:notPresent>
						</div>
						<div class="ic-input ic-split-30">
								<label>
									<common:message  key="maintainembargo.status"/>
								</label>
								<logic:present name="embargoVo" property="status">
								  <logic:present name="embargoStatus">
											<logic:iterate id="statusVo" name="embargoStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="statusVo" property="fieldValue">
														<bean:define id="fieldValue" name="statusVo" property="fieldValue"/>
														<bean:define id="fieldDescription" name="statusVo" property="fieldDescription"/>
															  <logic:equal name="embargoVo" property="status" value="<%=(String)fieldValue%>">
																  <%embargoStatusField=(String)fieldDescription;%>
														  </logic:equal>
													</logic:present>
											 </logic:iterate>
									</logic:present>
								</logic:present>
								
								<ihtml:text property="status" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_EmbargoStatus" value="<%=embargoStatusField%>" maxlength="8" readonly="true" />
						</div>
					</div>
					<div  class="ic-row">
						<div class="ic-input ic-mandatory ic-split-30">
							<label>
								<common:message key="maintainembargo.startdate"/>
							</label>
							<logic:present name="embargoVo" property="startDate">
							  <bean:define id="embargoLocalStartDate" name="embargoVo" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
									<% embargoStartDate=TimeConvertor.toStringFormat(embargoLocalStartDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
							</logic:present>
							<ibusiness:calendar id="startDate" property="startDate" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_EmbargoStartDate" type="image" value="<%=embargoStartDate %>" />				    
						</div>
						<div class="ic-input ic-mandatory ic-split-30">
								<label>
									<common:message  key="maintainembargo.enddate"/>
								</label>
								 <logic:present name="embargoVo" property="endDate">
								      <bean:define id="embargoLocalEndDate" name="embargoVo" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
										<% embargoEndDate=TimeConvertor.toStringFormat(embargoLocalEndDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
						    </logic:present>
						   <ibusiness:calendar id="endDate" property="endDate" value="<%=embargoEndDate %>" type="image" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_EmbargoEndDate" />
						    
						</div>
						<div class="ic-input ic-split-30 inline_filedset marginT15">
						 <logic:present name="embargoVo" property="isSuspended">
							      <bean:define id="embargoIsSuspended" name="embargoVo" property="isSuspended"/>
										<% embargoSuspend=(Boolean)embargoIsSuspended; %>
					    </logic:present>				
							<% if(embargoSuspend){ %>
								 <input type="checkbox" name="isSuspended" style="width:15px;border: 0px;" title="Suspend Embargo"  checked="checked"/>
								<% }
								 else {%>
							<input type="checkbox" name="isSuspended" style="width:15px;border: 0px;" title="Suspend Embargo"  />
							<% } %>
								<label>
									 <common:message  key="maintainembargo.suspend"/>
								</label>
								
						</div>
					</div>
				</div>
			</div>
		
			<div class="ic-row">
				<div class="ic-input ic-split-75">	
					<h4>
						<common:message  key="maintainembargo.geographicleveltab"/>
					</h4>
				</div>		
				<div class="ic-input ic-split-25">
					<div class="ic-button-container">
					<a href="#"  class="iCargoLink"  id="linkAddTemplateRow">
						<common:message  key="maintainembargo.addembargo" /></a>
					| <a href="#"  class="iCargoLink"  id="linkDeleteTemplateRow" >
						<common:message  key="maintainembargo.deleteembargo" /></a>
					</div>
				</div>
			</div>	
			<div class="ic-row">	
				<jsp:include page="MaintainComplianceGeographicLevel.jsp"/>
			</div>

			<div class="ic-row">	
				<div class="ic-input-container  ic-input-round-border">
				<div class="ic-row">
							<div class="ic-input ic-split-50">
								<label>
									<common:message  key="listembargo.parametercode"/>
								</label>
								<logic:present name="embargoParameters" >
									<logic:iterate id="parameter" name="embargoParameters" >
										<bean:define id="paramCode" name="parameter" property="parameterCode"/>
										<bean:define id="opFlag" name="parameter" property="operationalFlag"/>	
											<logic:notEqual name="opFlag" value="D">
												<ihtml:select  property="parameterCode" value="<%=(String)paramCode%>" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_ParameterCode" styleClass="parameterCode" onchange="javasciript:clearParamValues(this,targetFormName)">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>									
													<ihtml:option value="SCC">SCC</ihtml:option>									
													<ihtml:option value="SCCGRP">SCC Group</ihtml:option>									
													<ihtml:option value="PRD">Product</ihtml:option>																		
												</ihtml:select>		
											</logic:notEqual>
									</logic:iterate>
								</logic:present>
								<logic:notPresent name="embargoParameters">
									<ihtml:select  property="parameterCode"  styleClass="parameterCode" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_ParameterCode" onchange="javasciript:clearParamValues(this,targetFormName)">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>									
													<ihtml:option value="SCC">SCC</ihtml:option>									
													<ihtml:option value="SCCGRP">SCC Group</ihtml:option>									
													<ihtml:option value="PRD">Product</ihtml:option>																		
									</ihtml:select>		
								</logic:notPresent>
							</div>
							
							<div class="ic-input ic-split-50">
								<label>
									<common:message  key="listembargo.parametervalue"/>
								</label>
								<logic:present name="embargoParameters">
									<logic:iterate id="param" name="embargoParameters">
										<bean:define id="paramValue" name="param" property="parameterValues"/>
										<bean:define id="opFlag" name="param" property="operationalFlag"/>	
											<logic:notEqual name="opFlag" value="D">
											<ihtml:text property="parameterValue" value="<%=(String)paramValue%>" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_ParameterValue" />
										<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="parameterLov" onclick="showParameterLov()" width="22" height="22" /></div>
										</logic:notEqual>
									</logic:iterate>	
								</logic:present>
									<logic:notPresent name="embargoParameters">
										<ihtml:text property="parameterValue" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_ParameterValue"/>
										<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="parameterLov" onclick="showParameterLov()" width="22" height="22" /></div>
									</logic:notPresent>
							</div>																				
						</div>
					
					<div class="ic-row marginT5">
						<!--<div class="ic-input ic-mandatory ic-split-10">
							<label>
									<common:message  key="maintainembargo.embargodesc"/>
								</label>
						</div>-->
						<div class="ic-input ic-split-50">		
								<label>
									<common:message  key="maintainembargo.embargodesc"/>
								</label>						
								 <logic:present name="embargoVo" property="embargoDescription">
									 <bean:define id="embargoDescriptionField" name="embargoVo" property="embargoDescription"/>
											<% embargoDescription=(String)embargoDescriptionField; %>
								</logic:present>
									<ihtml:textarea property="embargoDesc" value="<%=embargoDescription %>"  cols="80" rows="20" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_EmbargoDescription" maxlength="4000">
									</ihtml:textarea>
							</div>
							<!--<div class="ic-input ic-split-7">
								<label>
									<common:message  key="maintainembargo.remarks"/>
								</label>
							</div>-->
							<div class="ic-input ic-split-50">
								<label>
									<common:message  key="maintainembargo.remarks"/>
								</label>
								<logic:present name="embargoVo" property="remarks">
									<bean:define id="embargoRemarksField" name="embargoVo" property="remarks"/>
									<% embargoRemarks=(String)embargoRemarksField; %>
								</logic:present>					  				    
									<ihtml:textarea property="remarks" value="<%=embargoRemarks%>" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_EmbargoRemarks" cols="20" rows="10" >
									</ihtml:textarea>
							</div>	
					</div>
				</div>
			</div>
						
		</div>
		<!-- main-container end -->
		
		<!-- foot-container starts -->
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<logic:present name="form" property="isPrivilegedUser">
							<bean:define id="privilegedUser" name="form" property="isPrivilegedUser"/>
								<logic:equal name="privilegedUser" value="Y">			
						<ihtml:nbutton property="btnApprove" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnApprove" >
						<common:message  key="maintainembargo.approve"/></ihtml:nbutton>

						<ihtml:nbutton property="btnReject" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnReject"  >
						<common:message  key="maintainembargo.reject"/></ihtml:nbutton>
						
						</logic:equal>
					</logic:present>

						<ihtml:nbutton property="btnSave" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnSave" accesskey="S" >
						<common:message  key="maintainembargo.save"/></ihtml:nbutton>
						<logic:present name="form" property="isPrivilegedUser">
							<bean:define id="privilegedUser" name="form" property="isPrivilegedUser"/>
								<logic:equal name="privilegedUser" value="Y">		
						<ihtml:nbutton property="btnCancel" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnCancel" accesskey="C" >
						<common:message  key="maintainembargo.cancel"/></ihtml:nbutton>
						</logic:equal>
						</logic:present>
						<ihtml:nbutton   property="btnClose" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_BtnClose" accesskey="O">
						<common:message  key="maintainembargo.close"/></ihtml:nbutton>
				</div>
			</div>
		</div>
		<!-- foot-container end -->
	</div>
	</ihtml:form>
</div>

	
</body>
</html:html>

