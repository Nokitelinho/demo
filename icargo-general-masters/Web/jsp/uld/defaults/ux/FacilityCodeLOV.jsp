<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  FacilityCodeLOV.jsp
* Date					:
* Author(s)				:  A-1862,A-6806
*************************************************************************/
 --%>

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>



<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.FacilityCodeLOVForm" %>
<%@ page info="lite" %>	

		
		<!DOCTYPE html>			
	
<html:html>
<head> 

        <title><common:message bundle="maintainuldResources" key="icargo.uld.defaults.pagetitle" scope="request"/></title>
		<meta name="decorator" content="popup_panel">
		<common:include type="script" src="/js/uld/defaults/ux/FacilityCodeLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
	
<business:sessionBean id="FacilityTypes"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.ux.maintainuld"
		   method="get"
		   attribute="facilityTypes"/>

	<bean:define id="form" name="FacilityCodeLOVFormUX" type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.FacilityCodeLOVForm" 
				toScope="page" />
	
	<div id="divmain" class="iCargoPopUpContent ic-lov-container" style="position:static; width:100%; height:96%; z-index:1;" >
    <ihtml:form action="/uld.defaults.ux.lov.screenloadfacilitycodelov.do" styleclass="ic-main-form">
		<ihtml:hidden property="companyCode" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="rowCount" />
		<ihtml:hidden property="formNumber"/>
		<ihtml:hidden property="textfiledObj" />
		<ihtml:hidden property="textfiledDesc" />
		<ihtml:hidden property="facilityTypeForLov" />
		<ihtml:hidden property="currentStationForLov" />
		<ihtml:hidden name="form" id="lovTxtFieldName" property="lovTxtFieldName" />
        <ihtml:hidden name="form" id="lovDescriptionTxtFieldName" property="lovDescriptionTxtFieldName" />
        <ihtml:hidden name="form" id="index" property="index" />
        
        <bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
        <bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
        <bean:define id="arrayIndex" name="form" property="index" />

		<div class="ic-content-main">
			<div class="ic-row">
				<span class="ic-page-title ic-display-none"> 
					<common:message bundle="maintainuldResourcesUX" key="uld.defaults.ux.facilitycodelov" />
				</span>	
			</div>
			<div title="Select Facility Code" class="poppane" id="facilityCode">
				<div class="popcontent row" >
					<div class="ic-main-container"> 
					<div class="datagrid m-t-5 row" style="height:97%" >
						<div id="div1" style="height:150px" class="tableContainer">
							<table class="fixed-header-table" id="table1" style="width:100%">
								<thead>
									<tr class="iCargoTableHeadingCenter">
										<td width="5%" class="iCargoTableHeader">&nbsp;</td>
										<td width="95%" class="iCargoTableHeader">
											<common:message key="uld.defaults.ux.lbl.facilitycode" />
										</td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="FacilityTypes">
										<%int index = 0;%>
										<logic:iterate id="FacilityTypes" name="FacilityTypes" indexId="nIndex">
											<bean:define id="facilityCode" name="FacilityTypes" property="facilityCode" />
											<% String strvalue=(String)facilityCode;%>
											<tr ondblclick="setOnDoubleClick('<%=strvalue%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" >

												<td>
													<input type="checkbox" name="suChecked" value="<%=strvalue%>" onclick="singleCheckBoxSelect('<%=strvalue%>');"/>
												</td>
												<td>
													<bean:write name="FacilityTypes" property="facilityCode"/>
												</td >
											</tr>
											<%index++;%>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>
					</div>
					</div>
			<div class="ic-foot-container" >
						<span class="btn-row">
							  <ihtml:button property="btOk"	styleClass="btn primary" componentID="CMP_ULD_DEFAULTS_UX_FCPOPUP_OK_BTN" accesskey="O">
									<common:message key="uld.defaults.ux.fclovbtn.btok" />
							  </ihtml:button>
							  <ihtml:button property="btClose" styleClass="btn default" componentID="CMP_ULD_DEFAULTS_UX_FCPOPUP_CLOSE_BTN" accesskey="C" >
								 <common:message key="uld.defaults.ux.fclovbtn.btclose" />
							  </ihtml:button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</ihtml:form>
	</div>

		

	</body>
</html:html>

