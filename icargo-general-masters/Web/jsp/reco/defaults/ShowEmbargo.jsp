<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO" %>
<%@ page import="java.util.Collection" %>



<html>
<head>
		

	<title><common:message bundle="embargorulesresources"
			      key="reco.defaults.titleCheckEmbargo" scope="request"/></title>
		<meta name="decorator" content="popuppanelrestyledui">

	<common:include type="script" src="/js/reco/defaults/ShowEmbargo_Script.jsp"/>
	<style type="text/css">
		.centeredImage {
			text-align:center;
			display:block;
		}
	</style>
	
	
</head>

<% boolean disable=false; %>
<bean:define id="form" name="CheckEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.CheckEmbargoRulesForm"/>
<body>
	
	
<div class="iCargoPopUpContent">
<ihtml:form action="/reco.defaults.showEmbargo.do">
	

<ihtml:hidden property="continueAction" />
<ihtml:hidden property="notocAction" />
<span class="ic-page-title ic-display-none"><common:message  key="reco.defaults.showembargo.embargo"/></span>

<div class="ic-main-container" >
<div class="ic-row">
<div id="div1" class="tableContainer" style="height:240px; width:100%;" align="center">
<table class="fixed-header-table" id="showEmbargo">
<thead>

<tr>
<td width="20%" class="iCargoTableHeader">Reference No.</td>
<td width="20%" class="iCargoTableHeader"><common:message  key="reco.defaults.showembargo.level"/></td>
<td width="20%" class="iCargoTableHeader"><common:message  key="reco.defaults.showembargo.description"/></td>
<td width="20%" class="iCargoTableHeader"><common:message  key="reco.defaults.showembargo.param"/></td>
<td width="20%" class="iCargoTableHeader">AWB/Mailbag</td>
</tr>
</thead>
<tbody>
<logic:present name="CheckEmbargoRulesForm" property="embargoPage" >

<bean:define id="embargos" name="CheckEmbargoRulesForm" property="embargoPage" scope="request" toScope="page"/>

<logic:iterate id="embargoVO" name="embargos" indexId="nIndex">
<logic:present  name = "embargoVO" property="params" >
<bean:define id="parameters" name="embargoVO" property="params" />
</logic:present >

<tr class="iCargoTableDataRow1">
<td class="iCargoTableDataTd">
<bean:write name="embargoVO" property="embargoReferenceNumber"/>
</td>
<td class="iCargoTableDataTd">

<bean:define id="test" name="embargoVO" property="embargoLevel"/>
<logic:equal  name="test"  value="E">
<img src="<%=request.getContextPath()%>/images/error.gif" class="centeredImage" />
<% disable=true; %>
</logic:equal>

<logic:equal  name="test"  value="I">

<img src="<%=request.getContextPath()%>/images/info.gif" class="centeredImage" />
</logic:equal>

<logic:equal  name="test"  value="W">
<img src="<%=request.getContextPath()%>/images/warning.gif" class="centeredImage" width="20" height="18" alt="Warning"/>
</logic:equal>
</td>
<td class="iCargoTableDataTd" style="word-wrap: break-word;">
<bean:write name="embargoVO" property="embargoDescription"/>
</td>
<td class="iCargoTableDataTd">
<logic:present  name = "embargoVO" property="params" >
<logic:iterate id="embargoParameterVO" name="parameters">
<bean:write name="embargoParameterVO" property="parameterCode"/>
<logic:present  name = "embargoParameterVO" property="parameterValues" >
<bean:define id="parameterValues" name="embargoParameterVO" property="parameterValues" />

													
													 :<bean:write name="embargoParameterVO" property="parameterValues"/>
													
 </logic:present >
</br>
</logic:iterate>
</logic:present >
</td>
<td class="iCargoTableDataTd">
<bean:write name="embargoVO" property="shipmentID"/>
</td>
</tr>
</logic:iterate>

</logic:present>
</tbody>
</table>
</div>
</div>

		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:button property="btContinue"  disabled="<%= disable %>" componentID="CMP_Reco_Defaults_ShowEmbargo_continue"><common:message  key="reco.defaults.showembargo.continue"/></ihtml:button>
				<ihtml:button property="btClose"  componentID="CMP_Reco_Defaults_ShowEmbargo_close" ><common:message  key="reco.defaults.showembargo.close"/></ihtml:button>
			</div>
		</div>
	</div>


</ihtml:form>
</div>
			
	</body>
</html>



