<%--
/***********************************************************************
* Project	     	       : iCargo
* Module Code & Name 	   :
* File Name          	   : MaintainEmbargoGeographicLevel.jsp
* Date                     : 27-MAR-2014
* Author(s)           	   : Anusha
*************************************************************************/
--%>



<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>


<business:sessionBean id="levelCode" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="levelCode" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults"	screenID="reco.defaults.maintainembargo"	method="get" attribute="embargoParameterVos" />
<business:sessionBean id="globalParameters"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="globalParameters" />
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="geographicLevelOnetimeValues" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevel" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>

	<!--Geographic Level Section-->

	<% String geographicLevel=""; %>
	
	<div id="tableContainer" class="tableContainer" style="height:140px;width:100%;">
		<table class="fixed-header-table" id="geographicLevelTab">
			<thead>
				<tr class="iCargoTableHeadingLeft">
					<th class="iCargoTableHeader" style="width:5%">
						
							<input type="checkbox" name="masterCheckbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.index)"/>
						
					</th>
					<th class="iCargoTableHeader" style="width:20%">
						<common:message key="maintainembargo.geographiclevel"/>
					</th>
					<th class="iCargoTableHeader" style="width:20%">
						<common:message key="maintainembargo.geographicleveltype"/>
					</th>
					<th class="iCargoTableHeader" style="width:20%">
						<common:message key="maintainembargo.applicable"/>
					</th>
					<th class="iCargoTableHeader" style="width:35%">
						<common:message key="maintainembargo.values"/><span class="iCargoMandatoryFieldIcon">*</span>
					</th>
				</tr>
			</thead>
			<tbody id="listSelectedData">
				<jsp:include page="MaintainEmbargoGeographicLevelTable.jsp"/>
			
				
					
										
							
											
				
										
			
			
				
			
			
				
			</tbody>
		</table>
	</div>
			
		


