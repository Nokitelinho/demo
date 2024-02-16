<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mail
* File Name				:  BasisUxLov.jsp
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
<%@ page import = "java.lang.*" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.BasisUxLovForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>


			
	
		
		<!DOCTYPE html>			
	
<html:html>
<head> 	
	 		

		<title><common:message  bundle="basisUxLovResources"  key="mailtracking.defaults.basislov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/operations/ux/BasisUxLov_Script.jsp" />
</head>
<business:sessionBean id="rateBreakDownValues"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="rateBreakDownValues"/>
<body id="bodyStyle">
	
<business:sessionBean id="sessionbasis"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="basis"/>	
	<logic:present name="BasisUxLovForm">
	<bean:define id="form"
		name="BasisUxLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.BasisUxLovForm"/>
	</logic:present>
	
	
		 <ihtml:form action="/mail.operations.ux.mailperformance.incentiveconfiguration.basis.screenload.do" styleClass="ic-main-form">

	
		
		<ihtml:hidden name="form" property="multiSelect" />
		<ihtml:hidden name="form" property="index" />
		<ihtml:hidden name="form" property="serviceResponseFlag" />
		<ihtml:hidden name="form" property="code" />
	
	
	<bean:define id="arrayIndex" name="form" property="index"/>	
	<bean:define id="code" name="form" property="code"/>
	
	<div title="" class="poppane md" id="select_basis">
		 
	

        <div class="card">
           
            <div class="card-body p-0" id="pa" style="max-height:250px; overflow-y:auto">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
					<table id="basisTable" class="table table-x-md m-0" style="width:100%" >
						<!-- <thead>
							<tr>
							<th width="35"> </th>	
								<th><common:message  key="mailtracking.defaults.palov.lbl.code" scope="request"/></th>
								<th><common:message  key="mailtracking.defaults.palov.lbl.description" scope="request"/></th>
						</tr>
						</thead> -->
						<tbody id="basisTableBody">
							<% 	String[] basisValue = null; %>
								<logic:present name="sessionbasis" >
									<bean:define id="values" name="sessionbasis" type="String"/>
									<%  basisValue = values.split(","); %>
									
								
								<logic:present name="rateBreakDownValues"  >
									<bean:define id="formulaBasisValues" name="rateBreakDownValues" toScope="page"/>
										<logic:iterate id="onetmvo" name="formulaBasisValues" indexId="rowCount" >
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

											<tr >
											
												<td class="text-center ">
												
												<% boolean valueSelected=false;
												if(basisValue.length>0){
													for(int i=0;i<basisValue.length;i++){
														if(basisValue[i].equals(onetimevo.getFieldValue()))
															valueSelected = true;
													}
													if(valueSelected == true){%>
													&nbsp;&nbsp; <input type="checkbox" id="selectCheckBox<%=String.valueOf(rowCount)%>" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" onclick="populateExpression(this)" tabindex="1" checked="checked"/>
												<% }else{ %>
													&nbsp;&nbsp; <input type="checkbox" id="selectCheckBox<%=String.valueOf(rowCount)%>" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" onclick="populateExpression(this)" tabindex="1"/>
													<%}
												}else{%>
													&nbsp;&nbsp; <input type="checkbox" id="selectCheckBox<%=String.valueOf(rowCount)%>" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" onclick="populateExpression(this)" tabindex="1"/>
												<% }%>
												</td>
												<td width="80px";>
												<% String addSelected = "";
												   String subSelected = "";
												   String mulSelected = "";
												   String divSelected = "";
													if(basisValue.length >0){
														for(int i=0;i<basisValue.length;i++){
															if(i>0 && basisValue[i].equals(onetimevo.getFieldValue())){
																if(("ADD").equals(basisValue[i-1])){
																	addSelected = "selected";
																	break;
																}else if(("-").equals(basisValue[i-1])){
																	subSelected = "selected";
																	break;
																}else if(("*").equals(basisValue[i-1])){
																	mulSelected = "selected";
																	break;
																}else if(("/").equals(basisValue[i-1])){
																	divSelected = "selected";
																	break;
																}
															}
														}
													}
												%>
													<ihtml:select property="operator" id="operator" onchange="onChangeBasis()" styleClass="form-control"  style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_OPERATOR"  >
														<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
														<option value=""><common:message key="combo.select"/></option>
														<option value="ADD" <%=addSelected%> >+</option>
														<option value="-" <%=subSelected%> >-</option>
														<option value="/" <%=divSelected%> >/</option>
														<option value="*" <%=mulSelected%> >*</option>
													</ihtml:select>
												
												</td>
												<td>
													<ihtml:text id="formula" property="formula" name="formula" value="<%=value.toString() %>" readonly="true"/>
												</td>
												
											</tr>	
										</logic:iterate>
									
							</logic:present>
							</logic:present>
							<logic:notPresent name="sessionbasis"> 
								<logic:present name="rateBreakDownValues"  >
									<bean:define id="formulaBasisValues" name="rateBreakDownValues" toScope="page"/>
									    <% int x=0; %>
										<logic:iterate id="onetmvo" name="formulaBasisValues" indexId="rowCount" >
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
											<tr>
											     <% String methodForIselOnChnge = "onChangeBasis("+x+")"; %>
												<td class="text-center ">
													&nbsp;&nbsp; <input type="checkbox" id="selectCheckBox<%=String.valueOf(rowCount)%>" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" onclick="populateExpression(this)" tabindex="1"/>
												</td>
												<td width="80px";>
													<ihtml:select property="operator" id="operator" onchange="<%=methodForIselOnChnge%>" styleClass="form-control"  style="width:85px;" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_OPERATOR"  >
														<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
														<option value=""><common:message key="combo.select"/></option>
														<option value="ADD">+</option>
														<option value="-">-</option>
														<option value="/">/</option>
														<option value="*">*</option>
													</ihtml:select>
												</td>
												<td>
													<ihtml:text id="formula" property="formula" name="formula" value="<%=value.toString() %>" readonly="true"/>
												</td>
											</tr>	
											<% x++; %>
										</logic:iterate>
								</logic:present>
							</logic:notPresent>
						</tbody>
					</table>
				</div>
			</div>
			
			
		</div>
		
		<footer class="mar-t-2sm">
        <% String dispCode="";
		   String hidCode="";
			if(String.valueOf(code)!= "" || String.valueOf(code)!= null ){
				hidCode = String.valueOf(code);
				if( String.valueOf(code).contains("ADD"))
					dispCode = String.valueOf(code).replaceAll("ADD","+");
				if(dispCode!= ""){
					
					dispCode = String.valueOf(dispCode).replaceAll(",","");
				}else{
					
					dispCode = String.valueOf(code).replace(",","");
				}%>
		 <ihtml:hidden id="selectedValues" property="selectedValues" name="form"  value="<%=String.valueOf(hidCode)%>" />
		 <ihtml:text id="formulaPanel" property="formulaPanel" name="form" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_FORMULA" value="<%=String.valueOf(dispCode)%>" />
		<% }else{%>
		<ihtml:hidden id="selectedValues" property="selectedValues" name="form"  value="" />
		<ihtml:text id="formulaPanel" property="formulaPanel" name="form" value="" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_FORMULA"  />
       	<% }%>					
            <div class="btn-row col">	

				<ihtml:nbutton property="btnOk" id="btnOk"  accesskey="K" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_OK"> <common:message  key="mailtracking.defaults.basislov.btn.btok"/> </ihtml:nbutton> 
				
				<ihtml:nbutton property="btnClear" id="btnClear"  accesskey="C" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_CLEAR" > <common:message  key="mailtracking.defaults.basislov.btn.btclear"/> </ihtml:nbutton> 
				
				<ihtml:nbutton property="btnClose" id="btnClose"  accesskey="C" styleClass="btn btn-primary" tabindex="5" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_BASIS_CLOSE" > <common:message  key="mailtracking.defaults.basislov.btn.btclose"/> </ihtml:nbutton> 
									
					
					
			</div>
        
		</footer>
	</div>
	</ihtml:form>
	
				
		   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>
		   <logic:present name="icargo.uilayout">
		       <logic:equal name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
		       </logic:equal>

		       <logic:notEqual name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new.jsp" />
		       </logic:notEqual>
		   </logic:present>
		   <common:registerCharts/>
		   <common:registerEvent />
	</body>
</html:html>
