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

<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="embargoVo" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="dayOfOperationApplicableOn" />
<bean:define id="form" name="MaintainComplianceInfoForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm"/>

<!--Geographic Level Section-->

<% String geographicLevel=""; %>

<div style="height:130px;overflow:auto;" >
	<table class="fixed-header-table" id="tableContainer">
		<thead>
			<tr class="iCargoTableHeadingLeft">
				<th class="iCargoTableHeader" width="10%">
				<ihtml:checkbox property="masterCheckbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.index)"/>
				</th>
				<th class="iCargoTableHeader" width="25%">
				<common:message  key="maintainembargo.geographiclevel"/>
				</th>
				<th class="iCargoTableHeader" width="25%">
				<common:message  key="maintainembargo.geographicleveltype"/>
				</th>
				<th class="iCargoTableHeader" width="20%">
				<common:message  key="maintainembargo.applicable"/>
				</th>
				<th class="iCargoTableHeader" width="20%">
				<common:message  key="maintainembargo.values"/><span class="iCargoMandatoryFieldIcon">*</span>
				</th>
			</tr>
		</thead>
		<tbody id="listSelectedData">
			<jsp:include page="MaintainComplianceGeographicLevelTable.jsp"/>
		
			
									
										
										
											
											
									
										
									
		
			
					
		</tbody>
	</table>
</div>
			
		


