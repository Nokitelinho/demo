<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: FacilityLocationLov.jsp
* Date				: 19 March 2008, 21-Oct-2015
* Author(s)			: A-2667, A-6770
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>
	<head>
		<title>Facility Codes</title>
		<meta name="decorator" content="popup_panel" >
		<common:include type="script" src="/js/uld/defaults/FacilityLocationLov_Script.jsp"/>
	</head>

	<body>
		<business:sessionBean id="pageLocationLov"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.listulddiscrepancies"
		   method="get"
		   attribute="facilityTypes"/>
		<div class="iCargoPopUpContent" style="width:100%;">
			<ihtml:form action="/uld.defaults.listulddiscrepancies.screenloadlocationlov.do" styleClass="ic-main-form">
			
			<ihtml:hidden property="lastPageNum"/>
			<ihtml:hidden property="defaultComboValue"/>
			<ihtml:hidden property="displayPage"/>
			<ihtml:hidden property="index" />
			<ihtml:hidden property="locationName" />
			<div class="ic-content-main">
			<div class="ic-main-container" >
				<div class="tableContainer" style="height:390px;width:100%;">
					<table class="fixed-header-table" >
						<thead>
							<tr>
								<td>
								</td>
								<td>
									Facility Code
								</td>
								<td>
									Description
								</td>
							</tr>
						</thead>
						<tbody>
							  <logic:present name="pageLocationLov">
								<bean:define id="ULDDiscrepancyVO" name="pageLocationLov"/>
									<logic:iterate id="uLDDiscrepancyVO" name="ULDDiscrepancyVO" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO">
					
							<tr>
								<td>
									 <bean:define id="facCode" name="uLDDiscrepancyVO" property="facilityType"/>
									<ihtml:checkbox property="numChecked" value="<%=String.valueOf(facCode)%>" onclick="singleSelect(this)"/>
								</td>
								<td>
									<bean:write name="uLDDiscrepancyVO" property="facilityType"/>
								</td>
								<td>
									<bean:write name="uLDDiscrepancyVO" property="facilityDescription"/>
								</td>
							</tr>
								     </logic:iterate>
								</logic:present>
						</tbody>
					</table>
				</div>
			</div>
			<div class="ic-foot-container" >
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton property="btok" styleClass="iCargoButtonSmall" value="OK" title = "Ok" accesskey="O"></ihtml:nbutton>

						<ihtml:nbutton property="btclose" styleClass="iCargoButtonSmall" value="Close" title = "Close" accesskey="C"></ihtml:nbutton>
					</div>
				</div>
			</div>
			</div>
			</ihtml:form>
		</div>
	</body>
</html:html>
