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
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.FacilityCodeLOVForm" %>


<html:html>
<head>

		
	

<!-- Added By A-5170 For ICRD-32241 starts -->	
<title><common:message bundle="maintainuldResources" key="icargo.uld.defaults.pagetitle" scope="request"/></title>
<!-- Added By A-5170 For ICRD-32241 ends -->
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/uld/defaults/FacilityCodeLOV_Script.jsp"/>
</head>

<body>
	
	
	
<business:sessionBean id="FacilityTypes"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainuld"
		   method="get"
		   attribute="facilityTypes"/>

<bean:define id="form" name="FacilityCodeLOVForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.FacilityCodeLOVForm" toScope="page" />
<div class="iCargoPopUpContent" >
<ihtml:form action="/uld.defaults.lov.screenloadfacilitycodelov.do" styleclass="ic-main-form">


<ihtml:hidden property="companyCode" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDesc" />
<ihtml:hidden property="facilityTypeForLov" />
<ihtml:hidden property="currentStationForLov" />

	<div class="ic-content-main">
		<div class="ic-row">
			<span class="ic-page-title ic-display-none"> 
				<common:message bundle="maintainuldResources" key="uld.defaults.facilitycodelov" />
			</span>	
		</div>		
			<div class="ic-main-container"> 
				<div class="tableContainer" id="div1" style="height:290px">
					<table class="fixed-header-table">
						<thead>
						<tr>
						<td width="5%" class="iCargoTableHeadingCenter">
						&nbsp;
						</td>
						<td width="40%" class="iCargoTableHeadingLeft">
						<common:message key="uld.defaults.lbl.facilitycode" />
						</td>
						</tr>
						</thead>
						 <tbody>
						<logic:present name="FacilityTypes">
						<%int index = 0;%>
						<logic:iterate id="FacilityTypes" name="FacilityTypes" indexId="nIndex">
						<bean:define id="facilityCode" name="FacilityTypes" property="facilityCode" />
						
							<%
							String strvalue=(String)facilityCode;
							%>
						<tr ondblclick="submitClick('<%=strvalue%>');" >
						<td class="iCargoTableTd" >
						<input type="checkbox" name="suChecked" value="<%=strvalue%>" onclick="singleCheckBoxSelect('<%=strvalue%>');"/>
						</td>
						<td class="iCargoTableTd" >
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
			<div class="ic-foot-container" >
				<div class="ic-button-container paddR5">			
              <ihtml:button property="btOk"
                componentID="CMP_ULD_DEFAULTS_FCPOPUP_OK_BTN"  >
                <common:message key="uld.defaults.fclovbtn.btok" />
              </ihtml:button>

              <ihtml:button property="btClose" styleClass="btn-inline btn-secondary"
                componentID="CMP_ULD_DEFAULTS_FCPOPUP_CLOSE_BTN"  >
		 		 <common:message key="uld.defaults.fclovbtn.btclose" />
              </ihtml:button>
				</div>  
			</div>
		</div>
</ihtml:form>
	</div>

		
	</body>
</html:html>

