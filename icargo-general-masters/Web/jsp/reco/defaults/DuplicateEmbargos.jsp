<%--
/*************************************************************************
* Project	 		: iCargo
* Module Name		: reco.defaults
* File Name			: DuplicateEmbargo.jsp
* Date				: 01-Aug-2014
* Author(s)			: A-5160
**************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"%>
<%@ page import = "java.util.*" %>

<html:html>
<head>
		
			
	
	   <title><common:message bundle="embargorulesresources" key="reco.defaults.titleMaintainEmbargo" scope="request"/></title>
	   <meta name="decorator" content="popup_panel">
	   <common:include type="script" src="/js/reco/defaults/MaintainEmbargo_Script.jsp"/>	
</head>
       <bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>
       <% MaintainEmbargoRulesForm frm = (MaintainEmbargoRulesForm)(request.getAttribute("MaintainEmbargoRulesForm"));%>
<body id="bodyStyle">
		  
          <business:sessionBean id="embargoVos" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVos" />
		  <div class="iCargoPopUpContent">
				<ihtml:form method="post" action="reco.defaults.duplicateembargos.do"  styleClass="ic-main-form">
				
				 <div class="ic-content-main">
				 <span class="ic-page-title ic-display-none">
					<common:message  key="reco.defaults.duplicateembargo"/>
				 </span>
			     <div class="ic-main-container">
			  		   <div class="ic-row">
							<div id="div1" class="tableContainer" style="height:340px;width:100%;">
							<table class="fixed-header-table">
							<thead>
							<tr class="ic-center">
							<td width="20%" class="iCargoTableHeader"><common:message  key="maintainembargo.refnumber"/></td>
							<td width="80%" class="iCargoTableHeader"><common:message  key="maintainembargo.embargodesc"/></td>
							</tr>
							</thead>
							<tbody>
							<logic:present name="embargoVos">
								<logic:iterate id="embargoVo" name="embargoVos">
										<tr class="iCargoTableDataRow1">
							            <td class="iCargoTableDataTd"><bean:write name="embargoVo" property="embargoReferenceNumber"/></td>
										<td class="iCargoTableDataTd"><bean:write name="embargoVo" property="embargoDescription"/></td>
										</tr>					
								</logic:iterate>
							</logic:present>	
							</tbody>
							</table>
							</div>
					 </div>
				 </div>
				<div class="ic-foot-container">
	            	<div class="ic-button-container">			
						<ihtml:button onClick="duplicatePopupClose()"  property="btnClosePopUp" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnClose" >
						<common:message  key="maintainembargo.close"/>
						</ihtml:button>
							
					</div>
				</div>	
            </div>				
			</ihtml:form>
			</div>				
				
		   
	</body>
</html:html>
