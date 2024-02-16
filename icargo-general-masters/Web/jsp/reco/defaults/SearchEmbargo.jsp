<%--
 /***********************************************************************
* Project       	:  iCargo
* Module Code & Name  :  RECO
* File Name       :  SearchEmbargo.jsp
* Date            :
* Author(s)       :
*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"%> 
<html:html>
<head>	

<title><common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.webtitle" scope="request"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/reco/defaults/SearchEmbargo_Script.jsp"/>
<bean:define id="form" name="SearchEmbargoForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"/>

</head>
<body id="bodyStyle">
<%String popupStyle=null;
boolean isPopUpScreen = false;
 %>
 
<logic:present parameter="isPopUp">
	<% popupStyle="overflow-y: scroll;";
		isPopUpScreen=true;
	%>	
</logic:present>
<div class="iCargoContent ic-center" id="pageDiv">
<div class="iCargoContent ic-center ic-masterbg" id="pageDiv" style="<%=popupStyle%>">
<business:sessionBean id="geographicLevelTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="levelCodes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="levelCodes" />
<business:sessionBean id="parameterCodes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="parameterCodes" />
<business:sessionBean id="applicableTransactions" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="categories" />
<business:sessionBean id="originTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="destinationTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="viaPointTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="EmabrgoDetailVOs" />
<business:sessionBean id="ruleTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="RuleTypes" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="embargoSearchVO" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="EmbargoSearchVO" />
<business:sessionBean id="regulatoryComposeMessages" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="regulatoryComposeMessages" />
<business:sessionBean id="regulatoryComplianceRules" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="regulatoryComplianceRules" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="uldPos" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="uldPos" />
<ihtml:form action="reco.defaults.onscreenloadsearchembargo.do">
	<ihtml:hidden  property="simpleSearch" styleId="simpleSearch"/>
	<ihtml:hidden  property="lastPageNum" />
	<ihtml:hidden  property="displayPage" />
	<ihtml:hidden property="leftPanelKey" styleId="leftPanelKey"/>
	<ihtml:hidden property="leftPanelValue" styleId="leftPanelValue"/>
	<ihtml:hidden  property="groupName" />
	<ihtml:hidden  property="groupType" />
	<ihtml:hidden  property="sourceScreen" />
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message  key="reco.defaults.searchembargo.title"/></span>
		<!-- head-container starts -->
		<div class="ic-head-container" style="position:static;">
			<div class="ic-filter-panel">
				<div class="ic-row marginT5">
						<div class="ic-input-container">
							<div class="ic-input ic-split-30">
								<ihtml:select property="geographicLevelType" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_GeographicLevelType" >						
									<logic:present name="geographicLevelTypes">
										<logic:iterate id="geographicLevelType" name="geographicLevelTypes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="geographicLevelType" property="fieldValue">
												<bean:define id="fieldValue" name="geographicLevelType" property="fieldValue"/>
												<bean:define id="fieldDescription" name="geographicLevelType" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
								<ihtml:text property="geographicLevel" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_GeographicLevel"/>
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" width="22" height="22"/></div>
							</div>
							<div class="ic-input ic-split-30">
								<ihtml:select property="dayOfOperationApplicableOn" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ApplicableOn" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="dayOfOperationApplicableOn">
										<logic:iterate id="dayOfOperationApplicableOn" name="dayOfOperationApplicableOn" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="dayOfOperationApplicableOn" property="fieldValue">
												<bean:define id="fieldValue" name="dayOfOperationApplicableOn" property="fieldValue"/>
												<bean:define id="fieldDescription" name="dayOfOperationApplicableOn" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>							
							<div class="ic-input ic-split-40">
								<div class="ic-button-containe paddR5">
										<ihtml:nbutton property="btnSearchSimple" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnSearchSimple" >
											<common:message  key="reco.defaults.searchembargo.search"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClearSimple" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnClearSimple" >
											<common:message  key="reco.defaults.searchembargo.clear"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnSpecificSearch" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnSpecificSearch" >
											<common:message  key="reco.defaults.searchembargo.specificserch"/>
										</ihtml:nbutton>
								</div>
							</div>																				
						</div>					
				</div>
			</div>
			
			
			
			<div class="ic-filter-panel" id="specific_search_div" style="display:none;"> 
				<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-input ic-split-30">
								<label>
									<common:message  key="reco.defaults.searchembargo.applicabletransactions"/>
								</label>
								<ihtml:select property="applicableTransaction" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Applicable_Transaction" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="applicableTransactions">
										<logic:iterate id="applicableTransactions" name="applicableTransactions" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="applicableTransactions" property="fieldValue">
												<bean:define id="fieldValue" name="applicableTransactions" property="fieldValue"/>
												<bean:define id="fieldDescription" name="applicableTransactions" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-35">
								<label>
									<common:message  key="reco.defaults.searchembargo.embargodate"/>
								</label>
								<ibusiness:calendar id="embargoDate" property="embargoDate" type="image" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_EmbargoDate" />
							</div>
							<div class="ic-input ic-split-17">
								<label>
									<common:message  key="reco.defaults.searchembargo.fromdate"/>
								</label>
								<ibusiness:calendar id="fromDate" property="fromDate" type="image" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_FromDate" />
							</div>
							<div class="ic-input ic-split-17">
								<label>
									<common:message  key="reco.defaults.searchembargo.todate"/>
								</label>
								<ibusiness:calendar id="toDate" property="toDate" type="image" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ToDate" />
							</div>																															
					</div>						
				</div>
				
				<div class="ic-row">
					<div class="ic-input-container">
						<div class="ic-input ic-split-30">
							<fieldset class="ic-field-set inline_filedset">
							<legend><common:message  key="reco.defaults.searchembargo.origintitle"/></legend>
							<!--<div class="ic-round-border ic-split-100">-->
								<div class="ic-input ic-split-50">
									<label>
										<common:message  key="reco.defaults.searchembargo.origintype"/>
									</label>
									 <ihtml:select property="originType" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_OriginType" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="originTypes">
												<logic:iterate id="originTypes" name="originTypes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="originTypes" property="fieldValue">
														<bean:define id="fieldValue" name="originTypes" property="fieldValue"/>
														<bean:define id="fieldDescription" name="originTypes" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
													</logic:present>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
								</div>
								<div class="ic-input ic-split-50">
									<label>
										<common:message  key="reco.defaults.searchembargo.origin"/>
									</label>
									<ihtml:text property="origin" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Origin" />
									   <div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="originLov" width="22" height="22"/></div>
								</div>
								<!--</div>-->						
								</fieldset>
						</div>								
						<div class="ic-input ic-split-35">
							<fieldset class="ic-field-set inline_filedset">
							<legend><common:message  key="reco.defaults.searchembargo.destinationtitle"/></legend>
								<div class="ic-input ic-split-60">
									<label>
										<common:message  key="reco.defaults.searchembargo.destinationtype"/>
									</label>
									 <ihtml:select property="destinationType" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_DestinationType" >	
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="destinationTypes">
											<logic:iterate id="destinationTypes" name="destinationTypes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="destinationTypes" property="fieldValue">
													<bean:define id="fieldValue" name="destinationTypes" property="fieldValue"/>
													<bean:define id="fieldDescription" name="destinationTypes" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
												</logic:present>
											</logic:iterate>
											</logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-40">
									<label>
										<common:message  key="reco.defaults.searchembargo.destination"/>
									</label>
									<ihtml:text property="destination" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Destination" />
  	    								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="destinationLov" width="22" height="22"/></div>
								</div>
								</fieldset>
						</div>								
						<div class="ic-input ic-split-30">
							<fieldset class="ic-field-set inline_filedset">
							<legend><common:message  key="reco.defaults.searchembargo.viapointtile"/></legend>
								<div class="ic-input ic-split-50">
									<label>
									<common:message  key="reco.defaults.searchembargo.viapointtype"/>
									</label>
									 <ihtml:select property="viaPointType" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ViaPointType" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="viaPointTypes">
											<logic:iterate id="viaPointTypes" name="viaPointTypes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="viaPointTypes" property="fieldValue">
													<bean:define id="fieldValue" name="viaPointTypes" property="fieldValue"/>
													<bean:define id="fieldDescription" name="viaPointTypes" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
												</logic:present>
											</logic:iterate>
											</logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-50">
									<label>
										<common:message  key="reco.defaults.searchembargo.viapoint"/>
									</label>
									<ihtml:text property="viaPoint" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ViaPoint" />
  	    								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="viaPointLov" width="22" height="22"/></div>
								</div>							
								</fieldset>
						</div>													
				</div>
			</div>	
				
				<div class="ic-row">
					<div class="ic-col-100">
						<div class="ic-input-container">
							<div class="ic-input ic-split-12">
								<label>
									<common:message  key="reco.defaults.searchembargo.ruletype"/>
								</label>
								 <ihtml:select property="ruleType" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_RuleType" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="ruleTypes">
										<logic:iterate id="ruleTypes" name="ruleTypes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="ruleTypes" property="fieldValue">
												<bean:define id="fieldValue" name="ruleTypes" property="fieldValue"/>
												<bean:define id="fieldDescription" name="ruleTypes" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-15">
								<label>
									<common:message  key="reco.defaults.searchembargo.level"/>
								</label>
								<ihtml:select property="levelCode" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Level">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="levelCodes">
											<logic:iterate id="levelCodes" name="levelCodes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="levelCodes" property="fieldValue">
													<bean:define id="fieldValue" name="levelCodes" property="fieldValue"/>
													<bean:define id="fieldDescription" name="levelCodes" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
												</logic:present>
											</logic:iterate>
										</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-18">
								<label>
									<common:message  key="reco.defaults.searchembargo.parametercode"/>
								</label>
								<ihtml:select property="parameterCode" onchange="javasciript:changeParameterValues(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ParameterCode">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="parameterCodes">
										<logic:iterate id="parameterCodes" name="parameterCodes" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterCodes" property="fieldValue">
												<bean:define id="fieldValue" name="parameterCodes" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterCodes" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							
							<div class="ic-input ic-split-20">
								<div class="ic-split-45">
									<label>
										<common:message  key="reco.defaults.searchembargo.parametervalue"/>
									</label>
								</div>
								<div class="ic-split-55">
									<div id="parameter_control_AWBPRE" class="parameter_control" style="display:none;">						
									<ihtml:text property="awbPrefix" onkeyup="validateNumber(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AWBPrefix" />						
									</div>
									<div id="parameter_control_CAR" class="parameter_control" style="display:none;">
									<ihtml:text property="carrier" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Carrier" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',carrier,'carrier','1','carrier','',0);" height="22" id="carrier"/></div>
									</div>
									<div id="parameter_control_COM" class="parameter_control" style="display:none;">
									<ihtml:text property="commodity" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Commodity" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showCommodity.do','N','Y','showCommodity.do',commodity,'commodity','1','commodity','',0);" height="22" id="commodity"/>
									</div>
									</div>
									<div id="parameter_control_DAT" class="parameter_control" style="display:none;">
									<ibusiness:calendar id="dates" property="dates" type="image" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Dates" />
									</div>
									<div id="parameter_control_FLTNUM" class="parameter_control" style="display:none">
									<ibusiness:flightnumber id="flightNumber"   componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_FlightNumber" carrierCodeProperty="carrierCode" flightCodeProperty="flightNumber" 
												  carrierCodeStyleClass="iCargoTextFieldVerySmall" flightCodeStyleClass="iCargoTextFieldSmall"/>
									
									</div>
									<div id="parameter_control_FLTOWR" class="parameter_control" style="display:none">
									<ihtml:text property="flightOwner" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_FlightOwner" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="flghtOwnr"  class="flghtOwnr"  onclick="displayLOV('showAirline.do','N','Y','showAirline.do',flightOwner,'Airline','1','flightOwner','',0);"/>
									</div>
									</div>
									<div id="parameter_control_FLTTYP" class="parameter_control" style="display:none">
									<ihtml:select property="flightType"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_FlightType" >
											<logic:present name="flightTypes">
												<logic:iterate id="type" name="flightTypes">
													<bean:define id="typevalue" name="type" property="fieldValue" />
													<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present>  		
										</ihtml:select>
									</div>
									<div id="parameter_control_ULDPOS" class="parameter_control" style="display:none">	
									<ihtml:select property="uldPos" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_UldPos" >
										<logic:present name="uldPos">
											<logic:iterate id="type" name="uldPos">
												<bean:define id="typevalue" name="type" property="fieldValue" />
												<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present>  		
									</ihtml:select>	
									</div>
									<div id="parameter_control_ULDTYP" class="parameter_control" style="display:none">	
										<ihtml:text property="uldTyp"  onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UldTypeCode" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',uldTyp,'uldTyp','1','uldTyp','',0);" height="22" id="uldTyp"/></div>
									</div>
									<div id="parameter_control_GOODS" class="parameter_control" style="display:none">
									<ihtml:text property="natureOfGoods"  onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_NatureOfGoods" />
									</div>
									<div id="parameter_control_UNCLS" class="parameter_control" style="display:none">
									<ihtml:text property="unIds"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Unids" />
									</div>
									<div id="parameter_control_PKGINS" class="parameter_control" style="display:none">
									<ihtml:text property="packingInstruction"  onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_PI" />
									</div>
									<div id="parameter_control_PAYTYP" class="parameter_control" style="display:none">
									<ihtml:text property="paymentType" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_PaymentType" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Payment Type','1','paymentType','paymentType','0','reco.defaults.paymenttype','')" height="22" id="paymentType"/></div>
									
									</div>
									<div id="parameter_control_PRD" class="parameter_control" style="display:none">
										<ihtml:text property="productCode" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Product"/><div class= "lovImg">						
										<img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayProductLov('products.defaults.screenloadProductLov.do',productCode.value,'productCode','','','0','productCode','Y','0')" height="22" id="productCode"/></div>
									</div>
									<div id="parameter_control_AGT" class="parameter_control" style="display:none">	
									<ihtml:text property="agentCode" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AGT" onblur="validateaplphanum(this);" />
								   <div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showAgentLOV(this)" id="agentLOV" width="20" height="20" /></div>
									</div>
									<div id="parameter_control_AGTGRP" class="parameter_control" style="display:none">
									<ihtml:text property="agentGroup" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AgtGroup" onblur="validateaplphanum(this);"  />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',agentGroup,
									'AGTGRP','EMB','agentGroup','1',0);" height="20" id="agentGroup"/></div>
									</div>
									<div id="parameter_control_SCC" class="parameter_control" style="display:none">
									<ihtml:text property="scc" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_SCC" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showScc.do','N','Y','showScc.do',scc,'scc','1','scc','',0);" height="22" id="scc"/></div>					
									</div>
									<div id="parameter_control_SCCGRP" class="parameter_control" style="display:none">
									<ihtml:text property="sccGroup"  onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_SccGroup" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',sccGroup,'SCCGRP','EMB','sccGroup','1',0);" height="22" id="sccGroup"/></div>
									</div>
									<div id="parameter_control_SPLIT" class="parameter_control" style="display:none">
									<ihtml:select property="splitIndicator" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_SplitIndicator" >
										<html:option value="Y">Y</html:option>
										</ihtml:select>
									</div>
									<div id="parameter_control_TIM" class="parameter_control" style="display:none">
									<ihtml:text property="time" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Time" onchange="validateTime(this);"/>
									</div>
									<div id="parameter_control_UNKSHP" class="parameter_control" style="display:none">
									<!-- Added by 202766 for IASCB-159851 -->	
									<ihtml:select property="unknownShipper" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UnknownShipper" >
										<html:option value="Y">Y</html:option>
										<html:option value="N">N</html:option>
										</ihtml:select>
									</div>
									<div id="parameter_control_UNNUM" class="parameter_control" style="display:none">
									<ihtml:text property="unNumber" onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_UnNumber" />
									</div>
									<div id="parameter_control_WGT" class="parameter_control" style="display:none">
										<ihtml:text property="weight" onkeyup="validateWeight(this);"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Weight" />
										<label>
										
										</label>									
									</div>
									<div id="parameter_control_HGT" class="parameter_control" style="display:none">
										<ihtml:text property="height" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Height" onkeyup="validateNumber(this);" />
										<label>
										
										</label>									
									</div>
									<div id="parameter_control_WID" class="parameter_control" style="display:none">
										<ihtml:text property="width" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Width" onkeyup="validateNumber(this);" />
										<label>
										
										</label>
									</div>
									<div id="parameter_control_LEN" class="parameter_control" style="display:none">
										<ihtml:text property="parameterLength" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Length" onkeyup="validateNumber(this);" />
										<label>
										<common:message  key="reco.defaults.searchembargo.dimensionunit"/>
										</label>
									</div>
									<div id="parameter_control_ARLGRP" class="parameter_control" style="display:none">
									<ihtml:text property="airlineGroup"  onblur="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AirlineGroup" /><div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',airlineGroup,'ARLGRP','EMB','airlineGroup','1',0);" height="22" id="airlineGroup"/></div>
									</div>
									<div id="parameter_control_VOL" class="parameter_control" style="display:none">
									<div class="ic-row">
										<ihtml:text property="volume"  onkeyup="restrictToSingleNumber(this);"   componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Volume" />
									</div>
									</div>
									<div id="parameter_control_ULD" class="parameter_control" style="display:none">	
										<ihtml:text property="uldType"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Uld" />
											<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('shared.uld.findUldTypeAndGroup.do','N','Y','shared.uld.findUldTypeAndGroup.do',uldType,'uldType','1','uldType','',0,null,null,null,null,10);" height="22" id="uldType"/></div>	
									</div>
									<div id="parameter_control_ACRTYPGRP" class="parameter_control" style="display:none">	
										<ihtml:text property="aircraftGroupType"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AircraftGroupType" />
											<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',aircraftGroupType,'ACRTYPGRP','EMB','aircraftGroupType','1',0);" height="22" id="aircraftGroupType"/></div>	
									</div>
									<div id="parameter_control_ACRTYP" class="parameter_control" style="display:none">	
										<ihtml:text property="aircraftType"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AircraftType" />
											<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showAircraft.do','Y','Y','showAircraft.do',aircraftType,'aircraftType','1','aircraftType','',0,null,null,null,null,10);" height="22" id="aircraftType"/></div>	
									</div>		
									<div id="parameter_control_SRVCRGCLS" class="parameter_control" style="display:none">		
										<ihtml:select property="serviceCargoClass"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ServiceCargoClass">
											<logic:present name="serviceCargoClass">
												<logic:iterate id="srvClas" name="serviceCargoClass">
													<bean:define id="srvcls" name="srvClas" property="fieldValue" />
													<html:option value="<%=(String)srvcls %>"><bean:write name="srvClas" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present> 						
										</ihtml:select>
									</div>
									<div id="parameter_control_ACRCLS" class="parameter_control" style="display:none">	
										<ihtml:text property="aircraftClassification"componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_AircraftClassification"/>
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Aircraft Classification','1','aircraftClassification','aircraftClassification',0,'shared.aircraft.aircraftClassification','');" height="20" id="aircraftClassification"/></div>
									</div>
									<div id="parameter_control_SHP" class="parameter_control" style="display:none">		
										<ihtml:text property="shipperCode" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Shipper" />
									   <div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showShipperLOV(this)" id="shipperLOV" width="20" height="20" /></div>
									</div>
									<div id="parameter_control_SHPGRP" class="parameter_control" style="display:none">	
										<ihtml:text property="shipperGroup" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ShipperGroup" onblur="restrictSpecialChar(this);" />
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',shipperGroup,'SHPCODGRP','EMB','shipperGroup','1',0);" height="20" id="shipperGroup"/></div>
									</div>
									<div id="parameter_control_CNS" class="parameter_control" style="display:none">
										<ihtml:text property="consigneeCode"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Consignee" />
									   <div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showConsigneeLOV(this)" id="consigneeLOV" width="20" height="20" /></div>
									</div>
									<div id="parameter_control_CNSGRP" class="parameter_control" style="display:none">		
										<ihtml:text property="consigneeGroup" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ConsigneeGroup" onblur="restrictSpecialChar(this);" />
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',consigneeGroup,'CUSGRP','EMB','consigneeGroup','1',0);" height="20" id="consigneeGroup"/></div>
									</div>
									<div id="parameter_control_SHPTYP" class="parameter_control" style="display:none">
										<ihtml:select property="shipmentType"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_ShipmentType">
											<logic:present name="shipmentType">
												<logic:iterate id="shipmntTyp" name="shipmentType">
													<bean:define id="shptyp1" name="shipmntTyp" property="fieldValue" />
													<html:option value="<%=(String)shptyp1%>"><bean:write name="shipmntTyp" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present> 						
										</ihtml:select>
									</div>
									<div id="parameter_control_CNSL" class="parameter_control" style="display:none">	
										<ihtml:select property="consol"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Filter_Consol" >
										<html:option value="Y">Yes</html:option>
										<html:option value="N">No</html:option>
										</ihtml:select>
									</div>
									<div id="parameter_control_" class="parameter_control" style="display:none">
										<ihtml:text property="defaultText" onblur="showParameterValue();"  componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_DefaultText"/>
									</div>
								</div>
								<ihtml:hidden property="parameterValue" style="text-transform : uppercase;"/>
							</div>
							<div class="ic-input ic-split-15">
								<label>
									<common:message  key="reco.defaults.searchembargo.category"/>
								</label>
								<ihtml:select property="category" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_Category">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="categories">
										<logic:iterate id="categories" name="categories" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="categories" property="fieldValue">
												<bean:define id="fieldValue" name="categories" property="fieldValue"/>
												<bean:define id="fieldDescription" name="categories" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-20">
								<div class="ic-button-container paddR15">
								<ihtml:nbutton property="btnSearch" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnSearch" >
								<common:message  key="reco.defaults.searchembargo.search"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnClear" >
									<common:message  key="reco.defaults.searchembargo.clear"/>
								</ihtml:nbutton>
								</div>
							</div>																									
						</div>
					</div>						
				</div>				
			</div>
					
		</div>
		<!-- head-container end -->
		
		<!-- main-container starts -->
		<div class="ic-main-container" id="maindiv" style="position:static;">
			<jsp:include page="SearchEmbargo_LeftPanel.jsp" />
		</div>
		<!-- main-container end -->
		
		<!-- foot-container starts -->
		<div class="ic-foot-container paddR5"  style="bottom:10px;">
			<div class="ic-row">
				<div class="ic-button-container">
				<!--added by A-8800 for IASCB- starts-->
				<%if(isPopUpScreen){%>
					<ihtml:nbutton property="btnPopUpClose" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnClose" >
						<common:message  key="reco.defaults.searchembargo.close"/>
					</ihtml:nbutton>
				<%}else{%>
					<ihtml:nbutton property="btnClose" componentID="CMP_Reco_Defaults_ListRegulatoryComplianceRules_BtnClose" >
						<common:message  key="reco.defaults.searchembargo.close"/>
					</ihtml:nbutton>	
				<%}%>
				<!--added by A-8800 for IASCB- End-->
				</div>
			</div>
		</div>
		<!-- foot-container end -->
	</div>
	</ihtml:form>
</div>

ss
	</body>
</html:html>