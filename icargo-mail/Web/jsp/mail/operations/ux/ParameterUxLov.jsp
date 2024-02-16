<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mail
* File Name				:  ParameterUxLov.jsp
* Date					:  27-Sep-2018
* Author(s)				:  A-6986
*************************************************************************/
--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ParameterUxLovForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" %>
			
	
		
		
<html:html>
<head> 
		

		<title><common:message  bundle="parameterUxLovResources"  key="mailtracking.defaults.parameterlov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/operations/ux/ParameterUxLov_Script.jsp" />
</head>
<business:sessionBean id="mailServiceLevels"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailServiceLevels"/>
<business:sessionBean id="mailAmot"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailAmot"/>
<business:sessionBean id="incentiveParameters"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="incentiveParameters"/>
<business:sessionBean id="mailCategory"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailCategory"/>
	 
<business:sessionBean id="parameterVO"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="parameterVO"/>			 
<body id="bodyStyle">
	
	<logic:present name="ParameterUxLovForm">
	<bean:define id="form"
		name="ParameterUxLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ParameterUxLovForm"/>
	</logic:present>
	
		 <ihtml:form action="/mail.operations.ux.mailperformance.incentiveconfiguration.parameter.screenload.do" styleClass="ic-main-form">
	<!--  Hidden to store the selections in case of pagination-->
		
		<ihtml:hidden name="form" property="selectedValues"  />
		
		<ihtml:hidden name="form" property="index" />
		<ihtml:hidden name="form" property="parameter" />
		<ihtml:hidden name="form" property="displayParameter" />
		<ihtml:hidden name="form" property="txtFieldName" />
		<ihtml:hidden name="form" property="dispTxtFieldName" />
	<ihtml:hidden name="form" property="mailSubClass" />
		<ihtml:hidden name="form" property="excludeIncludeFlag" />
		<ihtml:hidden name="form" property="serviceResponseFlag" />
		<ihtml:hidden name="form" property="excludeTxtFieldName" />
		
		
		<bean:define id="strSelectedValues" name="form" property="selectedValues" />
		<bean:define id="arrayIndex" name="form" property="index"/>	

		<!--  END -->
	
	<div title="" class="poppane md" id="select_parameter">
	

        <div class="card">
           
            <div class="card-body p-0" id="pa" style="max-height:250px; overflow-y:auto">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
					<table id="parameterTable" class="table table-x-md m-0" style="width:100%" >
						<thead>
							<tr>
								<th> </th>
								<th><common:message  key="mailtracking.defaults.parameterlov.lbl.parameter" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.parameterlov.lbl.value" scope="request"/></th>
								<th> </th>
						</tr>
						</thead>
						<tbody id="parameterTableBody">
							<%
							String categry = null;
								String subcls = null;
								String srvlvl = null;
								String amot = null;
								String catExclFlag = null;
								String subExclFlag = null;
								String srvExclFlag = null;
								String amotExclFlag = null;
								int i=0;		
							%>
							<logic:present name="parameterVO" >
							<logic:iterate id="parameters" name="parameterVO" indexId="rowCountDetail" type="com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO" >
								<logic:equal name="parameters" property="disIncParameterCode" value="CAT">	
									<%
									categry  = parameters.getDisIncParameterValue();
									if("Y".equals(parameters.getExcludeFlag())){
										catExclFlag="Y";
									}else{
										catExclFlag="N";
									}
									%>
								</logic:equal>
								<logic:equal name="parameters" property="disIncParameterCode" value="PRD">
									<%
									srvlvl  = parameters.getDisIncParameterValue();
									if("Y".equals(parameters.getExcludeFlag())){
										srvExclFlag="Y";
									}else{
										srvExclFlag="N";
									}
									%>
								</logic:equal>
								<logic:equal name="parameters" property="disIncParameterCode" value="SCL">
									<%
									subcls  = parameters.getDisIncParameterValue();
									if("Y".equals(parameters.getExcludeFlag())){
										subExclFlag="Y";
									}else{
										subExclFlag="N";
									}
									%>
								</logic:equal>
								<logic:equal name="parameters" property="disIncParameterCode" value="AMT">
									<%
									amot  = parameters.getDisIncParameterValue();
									if("Y".equals(parameters.getExcludeFlag())){
										amotExclFlag="Y";
									}else{
										amotExclFlag="N";
									}
									%>
								</logic:equal>
							</logic:iterate>
							</logic:present>
								
								<logic:present name="incentiveParameters" >
									<bean:define id="incentiveParameter" name="incentiveParameters" toScope="page"/>
									    <% int x=0; %> 
										<logic:iterate id="onetmvo" name="incentiveParameter" indexId="indexId">
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

											<tr>
												
													 <% String methodForIselOnChnge = "selctPramValues("+x+")";
													String methodForLovOnChnge = "selctPramValuesLov("+x+")";%> 													
													<logic:equal name="onetimevo" property="fieldValue" value="CAT">
													<% if(categry != null && categry.length() >0){%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" checked="checked"  tabindex="1"/></td>
													<% }else{%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" tabindex="1"/></td>
													<% }%>
													</logic:equal>
													<logic:equal name="onetimevo" property="fieldValue" value="PRD">
													<% if(srvlvl != null && srvlvl.length() >0){%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" checked="checked"  tabindex="1"/></td>
													<% }else{%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" tabindex="1"/></td>
													<% }%>
													</logic:equal>
													<logic:equal name="onetimevo" property="fieldValue" value="SCL">
													<% if(subcls != null && subcls.length() >0){%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" checked="checked"  tabindex="1"/></td>
													<% }else{%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" tabindex="1"/></td>
													<% }%>
													</logic:equal>
													<logic:equal name="onetimevo" property="fieldValue" value="AMT">
													<% if(amot != null && amot.length() >0){%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" checked="checked"  tabindex="1"/></td>
													<% }else{%>
														<td><input type="checkbox" id="selectCheckBox<%=String.valueOf(indexId)%>" name="selectCheckBox" value="<%=String.valueOf(indexId)%>" tabindex="1"/></td>
													<% }%>
													</logic:equal>
										<td style="width: 120px;">
													<ihtml:text readonly="true" property="parameterDisplayCode" styleClass="form-control" tabindex="16" value="<%=desc.toString() %>" />
													<ihtml:hidden  disabled="true" property="parameterCode" styleClass="form-control" value="<%=value.toString() %>" />
												</td>
													<logic:equal name="onetimevo" property="fieldValue" value="CAT">
											<td>
														<ihtml:select property="parameterValue"  id="parameterValue" styleClass="form-control" tabindex="16"  style="" onchange="<%=methodForIselOnChnge%>" >
														<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="mailCategory" >
																<bean:define id="category" name="mailCategory" toScope="page"/>
																<logic:iterate id="onetmvo" name="category">
																	<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																	<bean:define id="value" name="onetimevo" property="fieldValue"/>
																	<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																	
																	<%
																	if(categry != null && categry.equals(value)){%>
																	<option value="<%=(String)value%>" selected ><%=desc%></option>
																	<%									}else{
																	%>
																	<option value="<%=(String)value%>"  ><%=desc%></option>
																	<%}%>
																</logic:iterate>
															</logic:present>
													</ihtml:select>
											</td>
											<td style="width:181px;">
												<div class="form-check form-check-inline">
													<% if(("N").equals(catExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" checked="true" value="N" >
													<% }else{ %>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" >
													<% }%>
														<label class="form-check-label" for="excludeRadioBtn">
															<common:message  key="mailtracking.defaults.parameterlov.lbl.exclude" /> 
														</label>
												</div>&nbsp;&nbsp;
												<div class="form-check form-check-inline">
													<% if(("Y").equals(catExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else if(catExclFlag == null){ %>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else{%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" >
													<% }%>
														<label class="form-check-label" for="includeRadioBtn"><common:message  key="mailtracking.defaults.parameterlov.lbl.include" /></label>
												</div>
											</td>
													</logic:equal>
													<logic:equal name="onetimevo" property="fieldValue" value="SCL">
														  
											<td>
													<div class="input-group">
														<%
														if(subcls != null){
														%>
														<ihtml:text property="parameterValue" id="parameterValue2" styleClass="form-control" tabindex="16" value="<%=subcls%>"  />
														<%}else{%>
												<ihtml:text property="parameterValue" value="" id="parameterValue2" styleClass="form-control" tabindex="16" maxlength="2" onblur="<%=methodForLovOnChnge%>" />
														<%}%>
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="mailSubClassLOV" property="mailSubClassLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
											</td>
											<td style="width:181px;">
												<div class="form-check form-check-inline">
												<% if(("N").equals(subExclFlag)){%>
													<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" checked="true" value="N" >
												<% }else{ %>
													<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" >
												<% } %>
													<label class="form-check-label" for="excludeRadioBtn">
														<common:message  key="mailtracking.defaults.parameterlov.lbl.exclude" /> 
													</label>
												</div>&nbsp;&nbsp;
												<div class="form-check form-check-inline">
												<% if(("Y").equals(subExclFlag)){%>
													<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
												<% }else if(subExclFlag == null){%>
													<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
												<% }else{%>
													<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" >
												<% }%>
													<label class="form-check-label" for="includeRadioBtn"><common:message  key="mailtracking.defaults.parameterlov.lbl.include" /></label>
												</div>
											</td>
													</logic:equal>
													<logic:equal name="onetimevo" property="fieldValue" value="PRD">
											<td>
													<ihtml:select property="parameterValue" id="parameterValue" styleClass="form-control" tabindex="16"  onchange="<%=methodForIselOnChnge%>">
														<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="mailServiceLevels" >
																<bean:define id="mailServiceLevel" name="mailServiceLevels" toScope="page"/>
																<logic:iterate id="onetmvo" name="mailServiceLevel">
																	<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																	<bean:define id="value" name="onetimevo" property="fieldValue"/>
																	<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																	
																	<%
																		if(srvlvl != null && srvlvl.equals(value)){%>
																	<option value="<%=(String)value %>" selected><%=desc%></option>
																	<%}else{%>
																		<option value="<%=(String)value %>" ><%=desc%></option>
																	<%}%>
																</logic:iterate>
															</logic:present>
													</ihtml:select>
										
											</td>
										<td style="width:181px;">
													<div class="form-check form-check-inline">
													<% if(("N").equals(srvExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" checked="true" >
													<% }else{%>
												<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" >
													<% } %>
														<label class="form-check-label" for="excludeRadioBtn"> 
													<common:message  key="mailtracking.defaults.parameterlov.lbl.exclude" /> 
												</label>
													</div>&nbsp;&nbsp;
													<div class="form-check form-check-inline">
													<% if(("Y").equals(srvExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else if(srvExclFlag == null) {%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else{%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" >
													<% }%>
														<label class="form-check-label" for="includeRadioBtn"><common:message  key="mailtracking.defaults.parameterlov.lbl.include" /></label>
													</div>
												</td>
										</logic:equal>
										<logic:equal name="onetimevo" property="fieldValue" value="AMT">
											<td>
													<ihtml:select property="parameterValue" id="parameterValue" styleClass="form-control" tabindex="16"  onchange="<%=methodForIselOnChnge%>">
														<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="mailAmot" >
																<bean:define id="mailamot" name="mailAmot" toScope="page"/>
																<logic:iterate id="onetmvo" name="mailamot">
																	<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
																	<bean:define id="value" name="onetimevo" property="fieldValue"/>
																	<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
																	<%
																		if( amot!= null && amot.equals(value)){%>
																	<option value="<%=(String)value %>" selected><%=desc%></option>
																	<%}else{%>
																		<option value="<%=(String)value %>" ><%=desc%></option>
																	<%}%>
																</logic:iterate>
															</logic:present>
													</ihtml:select>
											</td>
										<td style="width:181px;">
													<div class="form-check form-check-inline">
													<% if(("N").equals(amotExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" checked="true" >
													<% }else{%>
												<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="excludeRadioBtn<%=String.valueOf(indexId)%>" value="N" >
													<% } %>
														<label class="form-check-label" for="excludeRadioBtn"> 
													<common:message  key="mailtracking.defaults.parameterlov.lbl.exclude" /> 
												</label>
													</div>&nbsp;&nbsp;
													<div class="form-check form-check-inline">
													<% if(("Y").equals(amotExclFlag)){%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else if(amotExclFlag == null) {%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" checked="true">
													<% }else{%>
														<input type="radio" name="excludeFlag<%=String.valueOf(indexId)%>" tabindex="17" class="form-check-input" id="includeRadioBtn<%=String.valueOf(indexId)%>" value="Y" >
													<% }%>
														<label class="form-check-label" for="includeRadioBtn"><common:message  key="mailtracking.defaults.parameterlov.lbl.include" /></label>
													</div>
												</td>
										</logic:equal>
											</tr>	
											 <% x++; %>  
										</logic:iterate>
									
							</logic:present>
								
									
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<footer>
        
				
				
					
					
				<ihtml:nbutton property="btnOk" id="btnOk"  accesskey="K" styleClass="btn btn-primary" tabindex="5"  > <common:message  key="mailtracking.defaults.parameterlov.btn.btok"/> </ihtml:nbutton> 
				<ihtml:nbutton property="btnClear" id="btnClear"  accesskey="C" styleClass="btn btn-primary" tabindex="5"> <common:message  key="mailtracking.defaults.parameterlov.btn.btclear"/> </ihtml:nbutton> 
				<ihtml:nbutton property="btnClose" id="btnClose"  accesskey="O" styleClass="btn btn-primary" tabindex="5"  > <common:message  key="mailtracking.defaults.parameterlov.btn.btclose"/> </ihtml:nbutton> 
		
        
		</footer>
	</div>
	</ihtml:form>
	
		
	</body>
</html:html>
