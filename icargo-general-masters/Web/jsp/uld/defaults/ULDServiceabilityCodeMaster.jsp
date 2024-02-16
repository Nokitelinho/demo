<%--
* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDServiceabilityCodeMaster.jsp
* Date				: 26-Aug-2010
* Author(s)			: A-2052
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm" %>

<html:html locale="true">
<head>
		
	
			
<title>
<common:message bundle="uldserviceability" key="uld.defaults.uldserviceabilitycodemaster" />
</title>

	<logic:equal name="ULDServiceabilityForm" property="screenName" value="screenLoad">
		<meta name="decorator" content="mainpanelrestyledui">
	</logic:equal>
	
	<logic:notEqual name="ULDServiceabilityForm" property="screenName" value="screenLoad">
		 <bean:define id="popup" value="true" />
      		<meta name="decorator" content="popuppanelrestyledui"/>
	</logic:notEqual>
	
	<common:include type="script" src="/js/uld/defaults/ULDServiceabilityCodeMaster_Script.jsp"/>
</head>

<body>
	
	
	
	
<business:sessionBean id="KEY_DETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="ULDServiceabilityVOs"/>
		   
<business:sessionBean id="partyTypeValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="PartyTypeValue"/>

<business:sessionBean id="partyTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="PartyType"/>
		   

<bean:define id="form"
		name="ULDServiceabilityForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm"
		toScope="page" />

<logic:present name="KEY_DETAILS">
<bean:define id="KEY_DETAILS" name="KEY_DETAILS" />
</logic:present>

<logic:equal name="ULDServiceabilityForm" property="screenName" value="screenLoad">
<div id="div3" class="iCargoContent" align="center" style="width:99%;height:100%;overflow:auto;">
</logic:equal>

<logic:notEqual name="ULDServiceabilityForm" property="screenName" value="screenLoad">
 <div class="iCargoPopupContent"  style="width:99%;height:100%;overflow:auto;">
</logic:notEqual>

<ihtml:form action="/uld.defaults.uldserviceability.screenloadcommand.do">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="afterList"/>
<ihtml:hidden property="screenName"/>
<% String chkBoxFlag="false";%>
<ihtml:hidden property="chkBoxFlag"/>

<logic:equal name="ULDServiceabilityForm" property="screenName" value="screenLoad">
 <div class="ic-content-main" style="width:70%;height:95%;">

</logic:equal>
<logic:notEqual name="ULDServiceabilityForm" property="screenName" value="screenLoad">
<div class="ic-content-main" style="width:100%;height:95%;">
 
</logic:notEqual>

				<span class="ic-page-title ic-display-none">
					<common:message key="uld.defaults.uldserviceabilitycodemaster" />
				</span>
				
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row">
								<h3>
								<common:message key="uld.defaults.searchcriteria" />
								</h3>
							</div>
							
							<div class="ic-row">
								<jsp:include page="ULDServiceabilityCodeMaster_Details.jsp" />
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<h3>
						<common:message  key="ULD.DEFAULTS.ULDserviceability" scope="request"/>
						</h3>
					</div>
				<div class="ic-button-container paddR5">
						<a class="iCargoLink" href="#" id="addRowLink" onclick="addRow();">Add</a>
						| <a class="iCargoLink" href="#" id="delRowLink" onclick="delRow();">Delete</a>
					</div>
					<div class="ic-row">
						 <div class="tableContainer" id="div1" style="width:100%;height:550px;">
							<table  class="fixed-header-table" id="tab1" style="width:100%;">
								 <thead>
									<tr class="iCargoTableHeadingLeft ic-mandatory">
										<td width="1%">
											<input type="checkbox" name="selectedrowsAll" id="selectedrowsAll" value="checkbox"  />
										</td>			
										<td width="10%">
											<label><common:message key="uld.defaults.lbl.code" /></label>
										</td>
										<td width="10%">
											<label><common:message key="uld.defaults.lbl.description" /></label>
										</td>
									</tr>
								</thead>
								<tbody id="facilityTableBody">
									 <logic:present name="KEY_DETAILS">
		    <bean:define id="KEY_DETAILS" name="KEY_DETAILS"/>
		    <logic:iterate id="iterator" name="KEY_DETAILS" indexId="billingindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO">
		     
		     <% String value=String.valueOf(billingindex)+"-"+"chk";%>

		      <logic:notPresent name="iterator" property="operationFlag"> 
			      <ihtml:hidden property="operationFlag" value="null"/>
			      <ihtml:hidden property="sequenceNumber" name="iterator" />
				<tr>
				<td class="iCargoTableDataTd" style="text-align:center">
				<input type="checkbox" name="selectedRows" id="selectedRows" value="<%=billingindex%>" />
				</td>							
				
				<td class="iCargoTableDataTd" >
				<logic:present name="iterator" property="code">
				<bean:define id="serviceCode" name="iterator" property="code" />
				<ihtml:text property="serviceCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="serviceCode" value ="<%=serviceCode.toString()%>" maxlength="3"/>
				</logic:present>	
				<logic:notPresent name="iterator" property="code">
				<ihtml:text property="serviceCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="serviceCode" value ="" maxlength="3"/>
				</logic:notPresent>
				</td>
				
				<td class="iCargoTableDataTd" >
				<logic:present name="iterator" property="description">
				<bean:define id="serviceDescription" name="iterator" property="description" />
				<ihtml:text property="serviceDescription" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="serviceDescription" value ="<%=serviceDescription.toString()%>" maxlength="20"/>
				</logic:present>	
				<logic:notPresent name="iterator" property="description">
				<ihtml:text property="serviceDescription" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="serviceDescription" value ="" maxlength="20"/>
				</logic:notPresent>
				</td>
			</tr>
			</logic:notPresent>
			<logic:present name="iterator" property="operationFlag">
			<logic:notEqual name="iterator" property="operationFlag" value="D">
			<bean:define id="operationFlag" name="iterator" property="operationFlag" />
			 <ihtml:hidden property="operationFlag" value="<%=(String)operationFlag%>"/>
 			<ihtml:hidden property="sequenceNumber" name="iterator" />
			<tr class="iCargoTableDataRow2">
			<td>
			<input type="checkbox" name="selectedRows" value="<%=billingindex%>" />
			</td>	
			
			<td >
			<logic:present name="iterator" property="code">
			<bean:define id="serviceCode" name="iterator" property="code" />
			<ihtml:text property="serviceCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="serviceCode" value ="<%=serviceCode.toString()%>" maxlength="3"/>			
			</logic:present>

			<logic:notPresent name="iterator" property="code">
			<ihtml:text property="serviceCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="serviceCode" value ="" maxlength="3"/>
			</logic:notPresent>
			</td>
			
			<td >
			<logic:present name="iterator" property="description">
			<bean:define id="serviceDescription" name="iterator" property="description" />
			<ihtml:text property="serviceDescription" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="serviceDescription" value ="<%=serviceDescription.toString()%>" maxlength="20"/>			
			</logic:present>

			<logic:notPresent name="iterator" property="description">
			<ihtml:text property="serviceDescription" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" name="serviceDescription" value ="" maxlength="20"/>
			</logic:notPresent>
			</td>
			</tr>
			</logic:notEqual>

			<logic:equal name="iterator" property="operationFlag" value="D">
			 <ihtml:hidden property="operationFlag" value="D"/>
 			<ihtml:hidden property="sequenceNumber" name="iterator" />
 				
			<logic:present name="iterator" property="code">
			<bean:define id="serviceCode" name="iterator" property="code" />
			<ihtml:hidden property="serviceCode" value="<%=(String)serviceCode%>"/>
			</logic:present>

			<logic:notPresent name="iterator" property="code">
			<ihtml:hidden property="serviceCode" value="" />
			</logic:notPresent>
			
			<logic:present name="iterator" property="description">
			<bean:define id="serviceDescription" name="iterator" property="description" />
			<ihtml:hidden property="serviceDescription" value="<%=(String)serviceDescription%>"/>
			</logic:present>

			<logic:notPresent name="iterator" property="description">
			<ihtml:hidden property="serviceDescription" value="" />
			</logic:notPresent>
			
			
			</logic:equal>
			</logic:present>
		
			</logic:iterate>
			</logic:present>

			<bean:define id="templateRowCount" value="0"/>
			<tr template="true" id="facilityTemplateRow" style="display:none">
			    <ihtml:hidden property="operationFlag" value="NOOP"/>
				<ihtml:hidden property="sequenceNumber" value="" />
				<td style="text-align:center">
				<input type="checkbox" name="selectedRows" id="selectedRows" value="" />
				</td>				
				
				<td >
				 <ihtml:text property="serviceCode" name="serviceCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" indexId="templateRowCount" 
				   value="" maxlength="3"/>
				</td>
				
				<td >
				 <ihtml:text property="serviceDescription" name="serviceDescription" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" indexId="templateRowCount" 
				 value="" maxlength="20"/>
				</td>
				
			</tr>
								</tbody>
							</table>
						 </div>
					</div>
				</div>
				
				<div class="ic-foot-container">
					
						<jsp:include page="ULDServiceabilityCodeMaster_Button.jsp" />
					
				</div>

</div>
</div>
</ihtml:form>
</div>
</div>


				
		
	</body>
</html:html>

