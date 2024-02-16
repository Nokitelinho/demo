<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mail
* File Name				:  FormulaUxLov.jsp
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
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.FormulaUxLovForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO"%>

			
	

	
	
<html:html>
<head> 
	

		<title><common:message  bundle="formulaUxLovResources"  key="mailtracking.defaults.formulalov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/operations/ux/FormulaUxLov_Script.jsp" />
</head>
<business:sessionBean id="formulaBasis"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="formulaBasis"/>
<business:sessionBean id="formulaReference"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="formulaReference"/>
<business:sessionBean id="formula"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="formula"/>
<body id="bodyStyle">
	
	<logic:present name="FormulaUxLovForm">
	<bean:define id="form"
		name="FormulaUxLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.FormulaUxLovForm"/>
	</logic:present>
	
	
		 <ihtml:form action="/mail.operations.ux.mailperformance.incentiveconfiguration.formula.screenload.do"  styleClass="ic-main-form">
	
		
		<ihtml:hidden name="form" property="selectedValues"  />
		
		<ihtml:hidden name="form" property="index" />
		<ihtml:hidden name="form" property="code" />
		<ihtml:hidden name="form" property="serviceResponseFlag" />
		
		
		<bean:define id="arrayIndex" name="form" property="index"/>	

		<!--  END -->
	
	<div title="" class="poppane md" id="select_formula">
		
		<!-- <div class="card-header card-header-action">
	
		</div> -->	  
		<div class="card-header d-flex justify-content-end">
			<ihtml:nbutton id="btnformulaExpAdd" styleClass="btn btn-primary mar-r-2xs"  property="btnFormulaAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_ADD" accesskey="A">
				<common:message key="mailtracking.defaults.formulalov.btn.btadd" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btnDelete" id="btnFormulaAdd" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_DELETE" accesskey="D">
				<common:message key="mailtracking.defaults.formulalov.btn.btdelete" />
			</ihtml:nbutton>
		</div>	  

        <div class="card">
           
            <div class="card-body p-0" id="pa" style="max-height:250px; overflow-y:auto">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
					<table id="formulaTable" class="table table-x-md m-0" style="width:100%" >
						<thead>
							<tr>
								<th width="35"> </th>	
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.basis" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.condition" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.components" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.operator" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.offset" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.formulalov.lbl.operator" scope="request"/></th>
							</tr>
						</thead> 
						<tbody id="formulaTableBody">
								
							<logic:present name="formula" >
								<bean:define id="formula" name="formula" toScope="page"/>

							<logic:iterate id="formulaexp" name="formula" >
								<bean:define id="sessionFormula" name="formulaexp" type="String" />
							 <%
							 String[] splitFormula = sessionFormula.split("~");
							 %>
							<tr>
							<ihtml:hidden property="operationFlags" value="I" />	
								<td>
								&nbsp;&nbsp; <ihtml:checkbox id="selectCheckBox" property="selectCheckBox" value=""/>
								</td>
								<td> <!-- Basis -->
									<ihtml:select property="basis" styleClass="form-control" tabindex="16"   style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_BASIS">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaBasis"  >
												<bean:define id="formulaBasisValues" name="formulaBasis" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaBasisValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
													<% if(splitFormula[0].equals(value.toString())){%>
														<option value="<%= value.toString() %>" selected ><%= desc %></option>
													<%}else{%>
														<option value="<%= value.toString() %>" ><%= desc %></option>
													<% }%>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- Condition -->
									<ihtml:select property="condition" styleClass="form-control" tabindex="1"  style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_CONDITION" >
									<% String GTSelected = "";
									   String LTSelected = "";
									   String EQSelected = "";
										if(("GT").equals(splitFormula[1]))
											GTSelected = "selected";
										else if(("LT").equals(splitFormula[1]))
											LTSelected = "selected";
										else
											EQSelected = "selected";
									%>
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										
											<option value="&gt;" <%=GTSelected%> ><common:message key="combo.greaterthan"/></option>
											<option value="&lt;" <%=LTSelected%> ><common:message key="combo.lessthan"/></option>
											<option value="=" <%=EQSelected%> ><common:message key="combo.equal"/></option>
									</ihtml:select>
								</td>
								<td> <!-- Component Reference -->
									<ihtml:select property="component" styleClass="form-control" tabindex="16" style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_REFERENCE"  >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaReference" >
												<bean:define id="formulaReferenceValues" name="formulaReference" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaReferenceValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
														
														<% if(splitFormula[2].equals(value.toString())){%>
														<option value="<%= value.toString() %>" selected ><%= desc %></option>
														<%}else{%>
														<option value="<%= value.toString() %>" ><%= desc %></option>
														<% }%>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- operator -->
									<% String operatorAddSelected = "";
									   String operatorSubSelected = "";
									   String operatorMulSelected = "";
									   String operatorDivSelected = "";
									   String operatorGTEQSelected = "";
									   String operatorLTEQSelected = "";
										if(("ADD").equals(splitFormula[3]))
											operatorAddSelected = "selected";
										else if(("-").equals(splitFormula[3]))
											operatorSubSelected = "selected";
										else if(("*").equals(splitFormula[3]))
											operatorMulSelected = "selected";
										else if(("/").equals(splitFormula[3]))
											operatorDivSelected = "selected";
										else if(("GT=").equals(splitFormula[3]))
											operatorGTEQSelected = "selected";
										else if(("LT=").equals(splitFormula[3]))
											operatorLTEQSelected = "selected";
									%>
									<ihtml:select property="operator" styleClass="form-control" tabindex="1" style="width:82px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR"  >
										<option value=""><common:message key="combo.select"/></option>
										<option value="ADD" <%=operatorAddSelected%> >+</option>
										<option value="-" <%=operatorSubSelected%> >-</option>
										<option value="/" <%=operatorDivSelected%> >/</option>
										<option value="*" <%=operatorMulSelected%> >*</option>
										<option value="&gt;=" <%=operatorGTEQSelected%> >&gt;=</option> 
										<option value="&lt;=" <%=operatorLTEQSelected%> >&lt;=</option>
									</ihtml:select>
								</td>
								<td> <!-- offset -->
									 <ihtml:text property="offset" maxlength="4" id="offset" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OFFSET" value="<%=splitFormula[4]%>"/>
								</td>
								<td> <!-- operator -->
									<% String AndSelected = "";
									   String OrSelected = "";
									   String NoSelected = "";
										if((" AND ").equals(splitFormula[5]))
											AndSelected = "selected";
										else if((" OR ").equals(splitFormula[5]))
											OrSelected = "selected";
										else
											NoSelected = "selected";
									%>
									<ihtml:select property="logicOperator" styleClass="form-control" tabindex="1"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR" >
										<option value="" <%=NoSelected%> ><common:message key="combo.select"/></option>
										<option value="AND" <%=AndSelected%> ><common:message key="combo.and"/></option>
										<option value="OR" <%=OrSelected%> ><common:message key="combo.or"/></option>
									</ihtml:select>
								</td>
								
							</tr>	
							</logic:iterate>
						</logic:present>
						<logic:notPresent name="formula" >
							<tr>
							<ihtml:hidden property="operationFlags" value="I" />	
								<td>
								&nbsp;&nbsp; <ihtml:checkbox id="selectCheckBox" property="selectCheckBox" value=""/>
								</td>
								<td> <!-- Basis -->
									<ihtml:select property="basis" styleClass="form-control" tabindex="16" style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_BASIS">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaBasis"  >
												<bean:define id="formulaBasisValues" name="formulaBasis" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaBasisValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
														<option value="<%= value.toString() %>" ><%= desc %></option>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- Condition -->
									<ihtml:select property="condition" styleClass="form-control" tabindex="1"  style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_CONDITION" >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<option value="&gt;" ><common:message key="combo.greaterthan"/></option>
											<option value="&lt;" ><common:message key="combo.lessthan"/></option>
											<option value="=" ><common:message key="combo.equal"/></option>
									</ihtml:select>
								</td>
								<td> <!-- Component Reference -->
									<ihtml:select property="component" styleClass="form-control" tabindex="16" style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_REFERENCE"  >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaReference"  >
												<bean:define id="formulaReferenceValues" name="formulaReference" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaReferenceValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
														<option value="<%= value.toString() %>" ><%= desc %></option>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- operator -->
									<ihtml:select property="operator" styleClass="form-control" tabindex="1" style="width:82px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR"  >
										<option value=""><common:message key="combo.select"/></option>
										<option value="+" >+</option>
										<option value="-" >-</option>
										<option value="/" >/</option>
										<option value="*" >*</option>
										<option value="&gt;=" >&gt;=</option> 
										<option value="&lt;=" >&lt;=</option>
									</ihtml:select>
								</td>
								<td> <!-- offset -->
								
									 <ihtml:text property="offset" id="offset" maxlength="4" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OFFSET" value=""/>
                       
								</td>
								<td> <!-- operator -->
									<ihtml:select property="logicOperator" styleClass="form-control" tabindex="1"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR" >
										<option value="" ><common:message key="combo.select"/></option>
										<option value="AND" ><common:message key="combo.and"/></option>
										<option value="OR" ><common:message key="combo.or"/></option>
									</ihtml:select>
								</td>
							<!--commented as part of ICRD-341449	<ihtml:hidden property="operationFlags" value="D" />	-->
							</tr>	
					</logic:notPresent >
							
							<tr template="true" id="formulaTemplateRow" style="display:none">
									<ihtml:hidden property="operationFlags" value="NOOP" />							
									
								<td> 
									&nbsp;&nbsp; <input type="checkbox" name="selectCheckBox" id="selectCheckBox" tabindex="1" value="" checked="checked"  />	</td>	
									<td> <!-- Basis -->
									<ihtml:select property="basis" styleClass="form-control" tabindex="16" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_BASIS" >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaBasis"  >
												<bean:define id="formulaBasisValues" name="formulaBasis" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaBasisValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
														<html:option value="<%= value.toString() %>"><%= desc %></html:option>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- Condition -->
									<ihtml:select property="condition" styleClass="form-control" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_CONDITION"   >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<ihtml:option value="&gt;"><common:message key="combo.greaterthan"/></ihtml:option>
											<ihtml:option value="&lt;"><common:message key="combo.lessthan"/></ihtml:option>
											<ihtml:option value="="><common:message key="combo.equal"/></ihtml:option>
									</ihtml:select>
								</td>
								<td> <!-- Component Reference -->
									<ihtml:select property="component" styleClass="form-control" tabindex="16" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_REFERENCE" >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="formulaReference" >
												<bean:define id="formulaReferenceValues" name="formulaReference" toScope="page"/>
												<logic:iterate id="onetmvo" name="formulaReferenceValues">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo" property="fieldValue"/>
													<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
														<html:option value="<%= value.toString() %>"><%= desc %></html:option>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</td>
								<td> <!-- operator -->
									<ihtml:select property="operator" styleClass="form-control" tabindex="1"
									componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<ihtml:option value="+">+</ihtml:option>
										<ihtml:option value="-">-</ihtml:option>
										<ihtml:option value="/">/</ihtml:option>
										<ihtml:option value="*">*</ihtml:option>
										<ihtml:option value="&gt;=">&gt;=</ihtml:option>
										<ihtml:option value="&lt;=">&lt;=</ihtml:option>
									</ihtml:select>
								</td>
								<td> <!-- offset -->
									
									  <ihtml:text property="offset" maxlength="4" id="offset" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OFFSET"/>
                                        
								</td>
								<td> <!-- operator -->
									<ihtml:select property="logicOperator" styleClass="form-control" tabindex="1"
                                     componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OPERATOR">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<html:option value="AND"><common:message key="combo.and"/></html:option>
										<html:option value="OR"><common:message key="combo.or"/></html:option>
									</ihtml:select>
								</td>
							</tr>		
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<footer >
        
				
				<ihtml:nbutton property="btnOk" id="btnOk"  accesskey="K" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_OK" > <common:message  key="mailtracking.defaults.formulalov.btn.btok"/> </ihtml:nbutton> 
				
				<ihtml:nbutton property="btnClear" id="btnClear"  accesskey="K" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_CLEAR" > <common:message  key="mailtracking.defaults.formulalov.btn.btclear"/> </ihtml:nbutton> 
				
				<ihtml:nbutton property="btnClose" id="btnClose"  accesskey="O" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_FORMULA_CLOSE" > <common:message  key="mailtracking.defaults.formulalov.btn.btclose"/> </ihtml:nbutton> 
				
			
					
        
		</footer>
	</div>
	</ihtml:form>
	
	</body>
</html:html>
