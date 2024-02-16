
<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : uld.defaults
* File Name          	 : MUCActions.jsp
* Date                 	 : 15-Mar-2018
* Author(s)              : A-7359
*************************************************************************/
--%>
 
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page language="java" %>

	 <html:html>
	<head>
	
		<title>
			<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.actions" />
		</title>
		
		<meta name="decorator" content="popuppanelrestyledui" >
		<common:include type="script" src="/js/uld/defaults/MUCActions_Script.jsp"/>
	</head>
	<body >
	
	<bean:define id="form"
		 name="MUCTrackingForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm"
		 toScope="request" />
	 
	 <business:sessionBean
		id="configAuditVOs"
		moduleName="uld.defaults"
		screenID="uld.defaults.messaging.muctracking"
		method="get"
		attribute="configAuditColl" />	
		
	<div  class="iCargoPopUpContent"  >	
	<ihtml:form action="/uld.defaults.messaging.muctracking.actions.screenload.do" styleClass="ic-main-form">
	
	<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.actions"/>
	</span>
	<div class="ic-main-container" >
	<div class="ic-row">
				<div class="ic-input ic-split-50 ic-label-30">
				<label><common:message key="uld.defaults.muctracking.crnnumber"/></label>
				<ihtml:text property="crnNumber" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CRNNO" readonly="true"  />
				</div>
	</div>
	<div class="ic-row">
	
	<div id="div1" class="tableContainer" style="width:100%; height:180px;">
	<table class="fixed-header-table">
	<thead>
							<tr >								
									<td class="iCargoTableHeader">
										<common:message key="uld.defaults.muctracking.action"/>
									</td>
									<td class="iCargoTableHeader">
										<common:message key="uld.defaults.muctracking.details"/>
									</td class="iCargoTableHeader">
									<td class="iCargoTableHeader">
										<common:message key="uld.defaults.muctracking.time"/>
									</td>
								</tr>
	</thead>
	<tbody>
	<logic:present name="configAuditVOs">
								<bean:define id="vos" name="configAuditVOs"  toScope="page"/>
								<logic:iterate id="mucAuditData" name="vos"  type="com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO" indexId="index">
								<common:rowColorTag index="index">
								<%System.out.println(mucAuditData.getActionCode());%>
								<tr  bgcolor="<%=color%>" >
									<td  class="iCargoTableDataTd" style="width:150px;">									
										<logic:present name="mucAuditData" property="actionCode">												
											<bean:write name="mucAuditData" property="actionCode" />									
										</logic:present>																			
									</td>
									<td  class="iCargoTableDataTd" style="width:330px;">
										<logic:present name="mucAuditData" property="additionalInformation">

												<bean:write name="mucAuditData" property="additionalInformation" />

										</logic:present>
									</td>
									<td  class="iCargoTableDataTd" style="width:150px;">
										<logic:present name="mucAuditData" property="txnTime">										
											<%
											String txnTime = "";
											if(mucAuditData.getTxnTime() != null) {
												txnTime = TimeConvertor.toStringFormat(
														mucAuditData.getTxnTime(),TimeConvertor.ADVANCED_DATE_FORMAT);
											}
											%>
											<%=txnTime%>							
										</logic:present>
									</td>
							 </tr>
							 </common:rowColorTag>
							 </logic:iterate>
							 </logic:present>
	</tbody>
		
	</table>
	</div>
	
	</div>
	</div>
	<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
				<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_CLOSE">
										<common:message key="uld.defaults.muctracking.close" scope="request"/>
									</ihtml:nbutton>
				</div>
			</div>
		</div>
		</div>
	
	
	
	</ihtml:form>
	</div>
	
			

	</body>
</html:html>
