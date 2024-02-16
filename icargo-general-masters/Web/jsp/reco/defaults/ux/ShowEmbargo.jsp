<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO" %>
<%@ page import="java.util.Collection" %>
<%@ page info="lite" %>		
<html>
<head> 

	<title>Show Embargo</title>
	<meta name="decorator" content="popuppanelux">
</head>
<% boolean disable=false; %>
<bean:define id="form" name="CheckEmbargoRulesRevampedForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ux.CheckEmbargoRulesForm"/>
<body>
	<%@ include file="/jsp/includes/reports/printFrame.jsp" %>
<ihtml:form action="/reco.defaults.ux.showEmbargo.do">
<ihtml:hidden property="continueAction" />
<span class="ic-page-title ic-display-none"><common:message  key="reco.defaults.showembargo.embargo"/></span>
<div class="ic-main-container" >
<div class="ic-row">
<div id="div1" class="w-100">
<div class="dataTableContainer tablePanel">
<table class="table m-0" id="showEmbargo">
<thead>
<tr>
<td>Reference No.</td>
<td><common:message  key="reco.defaults.ux.showembargo.level"/></td>
<td><common:message  key="reco.defaults.ux.showembargo.description"/></td>
<td><common:message  key="reco.defaults.ux.showembargo.param"/></td>
<td>AWB/Mailbag</td>
</tr>
</thead>
<tbody>
<logic:present name="CheckEmbargoRulesRevampedForm" property="embargoPage" >
<bean:define id="embargos" name="CheckEmbargoRulesRevampedForm" property="embargoPage" scope="request" toScope="page"/>
<logic:iterate id="embargoVO" name="embargos" indexId="nIndex">
<logic:present  name = "embargoVO" property="params" >
<bean:define id="parameters" name="embargoVO" property="params" />
</logic:present >
<tr>
<td>
<bean:write name="embargoVO" property="embargoReferenceNumber"/>
</td>
<td>
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
<td>
<bean:write name="embargoVO" property="embargoDescription"/>
</td>
<td>
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
<td>
<bean:write name="embargoVO" property="shipmentID"/>
</td>
</tr>
</logic:iterate>
</logic:present>
</tbody>
</table>
</div>
</div>
</div>
		 <div class="w-100 pad-y-sm pad-x-xs text-right border-top">
            <ihtml:nbutton property="btContinue" styleClass="btn btn-primary" disabled="<%= disable %>"  componentID="CMP_Reco_Defaults_UX_ShowEmbargo_btnContinue">Continue
            </ihtml:nbutton>
		<ihtml:nbutton property="btClose" styleClass="btn btn-default" componentID="CMP_Reco_Defaults_UX_ShowEmbargo_btnClose">Close
		</ihtml:nbutton>
        </div>
	</div>
</ihtml:form>
<common:include type="script" src="/js/reco/defaults/ux/ShowEmbargo_Script.jsp"/>
  </body>
</html>