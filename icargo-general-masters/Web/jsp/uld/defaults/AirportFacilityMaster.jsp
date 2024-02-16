<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: AirportFacilityMaster.jsp
* Date				: 14-Aug-2006
* Author(s)			: A-2052
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm" %>

		
<html:html>
<head>
	

<title>
<common:message bundle="airportfacilitymaster" key="uld.defaults.airportfacilitymaster" />
</title>
	<logic:equal name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
	<meta name="decorator" content="mainpanelrestyledui">
	</logic:equal>
	<logic:notEqual name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
		 <bean:define id="popup" value="true" />
      	 <meta name="decorator" content="popuppanelrestyledui"/>
	</logic:notEqual>
	<common:include type="script" src="/js/uld/defaults/AirportFacilityMaster_Script.jsp"/>
</head>

<body >
<business:sessionBean id="KEY_DETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="ULDAirportLocationVOs"/>
<business:sessionBean id="airportCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="AirportCode"/>
<business:sessionBean id="facilityCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityCode"/>
<business:sessionBean id="facilityTypeValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityTypeValue"/>
<business:sessionBean id="facilityTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityType"/>
<business:sessionBean id="contentValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="ContentValue"/>
<business:sessionBean id="contentCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="Content"/>

<bean:define id="form"
		name="AirportFacilityMasterForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm"
		toScope="page" />

<logic:present name="KEY_DETAILS">
<bean:define id="KEY_DETAILS" name="KEY_DETAILS" />
</logic:present>

<logic:equal name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
<div class="iCargoContent ic-masterbg" style="overflow:auto;height:100%;width:100%">
</logic:equal>

<logic:notEqual name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
 <div class="iCargoPopUpContent">
</logic:notEqual>

<ihtml:form action="/uld.defaults.airportfacilitymaster.screenloadcommand.do" styleclass="ic-main-form">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="afterList"/>
<ihtml:hidden property="wareHouseFlag"/>
<ihtml:hidden property="screenName"/>
<% String chkBoxFlag="false";%>
<ihtml:hidden property="chkBoxFlag"/>
 <div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message key="uld.defaults.airportfacilitymaster" />
	</span>
	
		<jsp:include page="AirportFacilityMaster_Details.jsp" />
		
		<div class="ic-main-container">
			<div class="ic-row ">
				<div class="ic-button-container paddR5">
					<a class="iCargoLink" href="#" id="addRowLink" onclick="addRow();">Add</a>
					|
					<a class="iCargoLink" href="#" id="delRowLink" onclick="delRow();">Delete</a>
				</div>
			</div>
			<div class="ic-row">
				<div class="tableContainer" id="div1" style="height:600px;">
				<table id="div1_table" class="fixed-header-table" >
					<thead>
						<tr>
							<td width="3%">
								<input type="checkbox" name="selectedrowsAll" id="selectedrowsAll" value="checkbox"/>
							</td>
							<td width="20%">
								<common:message key="uld.defaults.lbl.facilitytype" />
							</td>
							<td width="30%" class="ic-mandatory">
								<label><common:message key="uld.defaults.lbl.facilitycode" /></label> 
							</td>
							<td width="25%" class="ic-mandatory">
								<label><common:message key="uld.defaults.lbl.description" /></label> 
							</td>
							<td width="15%">
								<common:message key="uld.defaults.lbl.content" />
							</td>
							<td width="7%">
								<common:message key="uld.defaults.lbl.defaultflag" />
							</td>
						</tr>
					</thead>
					<tbody id="facilityTableBody">
							<jsp:include page="AirportFacilityMaster_Table.jsp"/>	

													

													 
														
							<jsp:include page="AirportFacilityMaster_TemplateRow.jsp" />
					</tbody>
				</table>
				</div>
			</div>
		</div>
		
				<jsp:include page="AirportFacilityMaster_Button.jsp" />
	
 </div>
</ihtml:form>
</div>	
	</body>
</html:html>

