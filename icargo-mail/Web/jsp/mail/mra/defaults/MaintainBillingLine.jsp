<%-- 
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  Mailtracking MRA
* File Name				:  MaintainBillingLine.jsp
* Date					:  28-Feb-2007
* Author(s)				:  A-2398  ,A-4823

*************************************************************************/
 --%>
<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"%>

		
	
<html:html>
<head> 
		
	

			
	
<title><common:message
	key="mailtacking.mra.defaults.maintainbillingline.title"
	bundle="billingline" scope="request" /></title>
<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/mail/mra/defaults/MaintainBillingLine_Script.jsp" />
	
</head>
<body>
<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.maintainbillingmatrix"
	method="get" attribute="systemparametres" />
<bean:define id="form" name="BillingLineForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"
	toScope="page" />
<business:sessionBean id="KEY_ONETIMES"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="oneTimeVOs" />
<!--CONTENT STARTS-->
<div class="iCargoPopUpContent">
<ihtml:form action="mailtracking.mra.defaults.maintainbillingline.screenload.do" styleClass="ic-main-form">
	<html:hidden name="BillingLineForm" property="selectedIndex" />
	<html:hidden name="BillingLineForm" property="canClose" />
	<html:hidden name="BillingLineForm" property="actionType" />
	<html:hidden name="BillingLineForm" property="isModified" />
	<html:hidden name="BillingLineForm" property="blgPtyType" />
	<html:hidden name="BillingLineForm" property="frmDate" />
	<html:hidden name="BillingLineForm" property="toDate" />
	<html:hidden name="BillingLineForm" property="reFlag" />
	<html:hidden name="BillingLineForm" property="disableButton" />
	<ihtml:hidden name="BillingLineForm" property="overrideRounding" value="N" />
	<ihtml:hidden name="BillingLineForm" property="roundingValue" />

<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>	
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
						<ihtml:hidden name="BillingLineForm"  property="roundingValue" id="round" value="<%=String.valueOf(parameterValue).toUpperCase() %>" /><!--added by a-7871 for ICRD-214766-->
					</logic:notEqual>
					<logic:equal name="parameterValue" value="N">						
						<ihtml:hidden name="BillingLineForm"  property="roundingValue" id="round" value="N" /><!--added by a-7871 for ICRD-214766-->
			</logic:equal>
			</logic:equal>
		</logic:iterate>
	</logic:present>
	<ihtml:hidden property="specificFlag" value="N" />
	<common:xgroup>
		<common:xsubgroup id="TURKISH_SPECIFIC">
			 <%form.setSpecificFlag("Y");%>
		</common:xsubgroup>
	</common:xgroup >
	<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				 <common:message key="mailtacking.mra.defaults.maintainbillingline.pageTitle" />
			</span>
		<div class="ic-main-container">
		<div class="ic-row">
		<div class="ic-col-50" id="blgLine">
						<div class="tableContainer ic-input" id="div1" style="height:563px;">
							<table class="fixed-header-table" id="maintanBillingline">
								<thead>
									<tr>
										<td class="iCargoTableHeader" style="width:20%"><common:message
											key="mailtacking.mra.defaults.maintainbillingline.tableheader.parameter"
											scope="request" /></td>
										<td class="iCargoTableHeader" style="width:40%"><common:message
											key="mailtacking.mra.defaults.maintainbillingline.tableheader.include"
											scope="request" /></td>
										<td class="iCargoTableHeader" style="width:40%"><common:message
											key="mailtacking.mra.defaults.maintainbillingline.tableheader.exclude"
											scope="request" /></td>
									</tr>
								</thead>
								<tbody>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.orgreg"
											scope="request" /></td>
										<td><ihtml:text property="orgRegInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgRegInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="1" /> 
											<div class="lovImgTbl">
											<img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgRegIncLovId" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="orgRegExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgRegExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="2" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgRegExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.orgcnt"
											scope="request" /></td>
										<td><ihtml:text property="orgCntInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgCntInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="3" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgCntIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="orgCntExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgCntExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="4" alt="" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgCntExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.orgcty"
											scope="request" /></td>
										<td><ihtml:text property="orgCtyInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgCtyInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="5" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgCtyIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="orgCtyExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgCtyExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="6" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgCtyExcLov" height="16" width="16" alt=""/></div></td>
									</tr>
									<!--Added by A-7540 as part of ICRD-232319-->
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.orgairport"
											scope="request" /></td>
										<td><ihtml:text property="orgAirportInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgAirInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="7" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgAirIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="orgAirportExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_OrgAirExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="8" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="orgAirExcLov" height="16" width="16" alt=""/></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.uplcnt"
											scope="request" /></td>
										<td><ihtml:text property="uplCntInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uplCntInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="9" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplCntIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="uplCntExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UplCntExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="10" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplCntExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.uplcty"
											scope="request" /></td>
										<td><ihtml:text property="uplCtyInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UplCtyInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="11" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplCtyIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="uplCtyExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UplCtyExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="12" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplCtyExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.uplarp"
											scope="request" /></td>
										<td><ihtml:text property="uplArpInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UplArpInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="11" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplArpIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="uplArpExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UplArpExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="12"  /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uplArpExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									
									<tr class="iCargoTableDataRow2"><!---Added by A-7531-->
									<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.agentCode"
											scope="request" /></td>
									<td><ihtml:text property="agentCodeInc"  componentID="CMP_Mail_MRA_Defaults_BillingLine_AGENTCODEINC"  style="text-transform : uppercase;width:128px;" maxlength="100" tabindex="13"/>
	                                     <div class="lovImgTbl"><img name="agentinclov" id="agentinclov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" /></td></div>
	
	                                 <td><ihtml:text property="agentCodeExc"  componentID="CMP_Mail_MRA_Defaults_BillingLine_AGENTCODEEXC"  style="text-transform : uppercase;width:128px;" maxlength="100" tabindex="14"/>
	                                    <div class="lovImgTbl"><img name="agentexclov" id="agentexclov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" /></td></div>
	
									
									</tr>
									<!--Added by A-7540 as part of ICRD-232319-->
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.viapoint"
											scope="request" /></td>
										<td><ihtml:text property="viaPointInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ViaPointInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="15" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="viaPointIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="viaPointExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ViaPointExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="16" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="viaPointExcLov" height="16" width="16" alt=""/></div></td>
									</tr>
																		
									
									
									
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.fltno"
											scope="request" /></td>
										<td><ibusiness:flightnumber id="fltNo" 
											carrierCodeProperty="carrierCodeInc"  
											flightCodeProperty="flightNumberInc" 
											tabindex="17" 
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FltNumInc"/>
										</td>
										
										<td><ibusiness:flightnumber id="fltNo" 
											carrierCodeProperty="carriercodeExc"  
											flightCodeProperty="flightNumberExc" 
											tabindex="18" 
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FltNumExc"/>
										</td>										
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
										key="mailtacking.mra.defaults.maintainbillingline.flowncarrier"
										scope="request" /></td>
										<td><ihtml:text property="flownCarrierInc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlownCarrierInc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="19" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="flownCarrierIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="flownCarrierExc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlownCarrierExc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="20" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="flownCarrierExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									
									
									<tr class="iCargoTableDataRow1">
										<td><common:message
										key="mailtacking.mra.defaults.maintainbillingline.transferedby"
										scope="request" /></td>
										<td><ihtml:text property="transferedByInc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_TransferedByInc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="19" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="transferedByIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="transferedByExc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_TransferedByExc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="20" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="transferedByExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
										key="mailtacking.mra.defaults.maintainbillingline.transferedpa"
										scope="request" /></td>
										<td><ihtml:text property="transferedPAInc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_TransferedPAInc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="21" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="transferedPAIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="transferedPAExc"
										componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_TransferedPAExc"
										maxlength="100" readonly="false" style="width:130px;"
										tabindex="22" /><div class="lovImgTbl"> <img
										src="<%=request.getContextPath()%>/images/lov.png"
										id="transferedPAExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.discnt"
											scope="request" /></td>
										<td><ihtml:text property="disCntInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisCntInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="23" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disCntIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="disCntExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisCntExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="24" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disCntExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.discty"
											scope="request" /></td>
										<td><ihtml:text property="disCtyInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisCtyInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="25" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disCtyIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="disCtyExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisCtyExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="26" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disCtyExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.disarp"
											scope="request" /></td>
										<td><ihtml:text property="disArpInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisArpInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="25"  /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disArpIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="disArpExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DisArpExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="26"  /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="disArpExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.desreg"
											scope="request" /></td>
										<td><ihtml:text property="desRegInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesRegInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="27" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desRegIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="desRegExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesRegExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="28" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desRegExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.descnt"
											scope="request" /></td>
										<td><ihtml:text property="desCntInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesCntInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="29" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desCntIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="desCntExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesCntExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="30" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desCntExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.descty"
											scope="request" /></td>
										<td><ihtml:text property="desCtyInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesCtyInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="31" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desCtyIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="desCtyExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesCtyExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="32" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desCtyExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<!--Added by A-7540 as part of ICRD-232319-->
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.desairport"
											scope="request" /></td>
										<td><ihtml:text property="desAirportInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesAirInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="33" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desAirIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="desAirportExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_DesAirExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="34" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="desAirExcLov" height="16" width="16" alt=""/></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.category"
											scope="request" /></td>
										<td><ihtml:text property="catInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_CatInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="35" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="catIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="catExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_CatExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="36" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="catExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<logic:equal name="form" property="specificFlag" value = "Y">
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.mailCompany"
											scope="request" /></td>
										<td><ihtml:text property="mailCompanyInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_MailCompanyInc"
											maxlength="5" readonly="false" style="width:130px;"
											tabindex="37" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="mailCompanyIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="mailCompanyExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_MailCompanyExc"
											maxlength="5" readonly="false" style="width:130px;"
											tabindex="38" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="mailCompanyExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									</logic:equal>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.class"
											scope="request" /></td>
										<td><ihtml:text property="classInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ClassInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="39" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="classIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="classExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ClassExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="40" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="classExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
										key="mailtacking.mra.defaults.maintainbillingline.subclass"
										scope="request" /></td>
									<td><ihtml:text property="subClassInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SubClassInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="41" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="subClsIncLov" height="16" width="16" alt="" /></div></td>
									<td><ihtml:text property="subClassExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SubClassExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="42" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="subClsExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
									<tr class="iCargoTableDataRow2">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.subclassgroup"
											scope="request" /></td>
										<td>
								<ihtml:select
									componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SubClsInc"
									property="subClsInc">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="KEY_ONETIMES">
								<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
									<bean:define id="parameterCode" name="oneTimeValue"
										property="key" />
									<logic:equal name="parameterCode"
										value="mailtracking.defaults.mailsubclassgroup">
										<bean:define id="parameterValues" name="oneTimeValue"
											property="value" />
										<logic:iterate id="parameterValue"
											name="parameterValues"
											type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue"
												property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue"
													property="fieldValue" />
												<bean:define id="fieldDescription"
													name="parameterValue" property="fieldDescription" />
												<ihtml:option
													value="<%=String.valueOf(fieldValue).toUpperCase() %>">
													<%=String.valueOf(fieldDescription)%>
												</ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
								</logic:present>
								</ihtml:select>

								</td>

								<td>
							<ihtml:select
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SubClsExc"
								property="subClsExc">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="KEY_ONETIMES">
							<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
								<bean:define id="parameterCode" name="oneTimeValue"
									property="key" />
								<logic:equal name="parameterCode"
									value="mailtracking.defaults.mailsubclassgroup">
									<bean:define id="parameterValues" name="oneTimeValue"
										property="value" />
									<logic:iterate id="parameterValue"
										name="parameterValues"
										type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue"
											property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue"
												property="fieldValue" />
											<bean:define id="fieldDescription"
												name="parameterValue" property="fieldDescription" />
											<ihtml:option
												value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												<%=String.valueOf(fieldDescription)%>
											</ihtml:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
							</logic:present>
							</ihtml:select>


						</td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.servicelevel"
											scope="request" /></td>
											
								<td>
							<ihtml:select
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ServiceExc"
								property="mailServiceInc">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="KEY_ONETIMES">
							<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
								<bean:define id="parameterCode" name="oneTimeValue"
									property="key" />
								<logic:equal name="parameterCode"
									value="mail.operations.mailservicelevels">
									<bean:define id="parameterValues" name="oneTimeValue"
										property="value" />
									<logic:iterate id="parameterValue"
										name="parameterValues"
										type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue"
											property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue"
												property="fieldValue" />
											<bean:define id="fieldDescription"
												name="parameterValue" property="fieldDescription" />
											<ihtml:option
												value="<%=String.valueOf(fieldValue).toUpperCase() %>">
												<%=String.valueOf(fieldDescription)%>
											</ihtml:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
							</logic:present>
							</ihtml:select>


						</td>
										<td>
								<ihtml:select
									componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ServiceInc"
									property="mailServiceExc">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="KEY_ONETIMES">
								<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
									<bean:define id="parameterCode" name="oneTimeValue"
										property="key" />
									<logic:equal name="parameterCode"
										value="mail.operations.mailservicelevels">
										<bean:define id="parameterValues" name="oneTimeValue"
											property="value" />
										<logic:iterate id="parameterValue"
											name="parameterValues"
											type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue"
												property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue"
													property="fieldValue" />
												<bean:define id="fieldDescription"
													name="parameterValue" property="fieldDescription" />
												<ihtml:option
													value="<%=String.valueOf(fieldValue).toUpperCase() %>">
													<%=String.valueOf(fieldDescription)%>
												</ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
								</logic:present>
								</ihtml:select>

								</td>
									</tr>
									<tr class="iCargoTableDataRow1">
										<td><common:message
											key="mailtacking.mra.defaults.maintainbillingline.uldtyp"
											scope="request" /></td>
										<td><ihtml:text property="uldTypInc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UldTypInc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="43" /> <div class="lovImgTbl"><img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uldTypIncLov" height="16" width="16" alt="" /></div></td>
										<td><ihtml:text property="uldTypExc"
											componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_UldTypExc"
											maxlength="100" readonly="false" style="width:130px;"
											tabindex="3" /><div class="lovImgTbl"> <img
											src="<%=request.getContextPath()%>/images/lov.png"
											id="uldTypExcLov" height="16" width="16" alt="" /></div></td>
									</tr>
								</tbody>
							</table>
						</div>
                <div class="ic-input ic-split-50 marginT10 ic_inline_chcekbox">				
				<label><common:message key="mailtacking.mra.defaults.maintainbillingline.paBuilt" /></label>		
								
									<logic:equal name="BillingLineForm" property="paBuilt" value="Y">
								<input type="checkbox" name ="paBuilt" indexId="rowCount" checked >
								</logic:equal>
							
							<logic:notEqual name="BillingLineForm" property="paBuilt" value="Y">
								<input type="checkbox" name ="paBuilt" indexId="rowCount">
							</logic:notEqual>
					</div>
					</div>
					<div class="ic-col-50" id="blgDtls">
						<jsp:include page="MaintainBillingLine_BillingDetails.jsp"/>
					</div>
						</div>
						</div>
					<div class="ic-foot-container paddR5">
						<div class="ic-row">
							<div class="ic-button-container">
							<ihtml:nbutton property="btnOK"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgLine_OKBtn">
							<common:message
							key="mailtacking.mra.defaults.maintainbillingline.button.OK"
							scope="request" />
							</ihtml:nbutton> 
							<ihtml:nbutton property="btnClose"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgLine_CloseBtn">
							<common:message
							key="mailtacking.mra.defaults.maintainbillingline.button.Close"
							scope="request" />
							</ihtml:nbutton>
							</div>
						</div>
					</div>
		</div>
	
	

</ihtml:form>
</div>
	</body>
</html:html>
