<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"%>


<html:html>
	<head>
		
	
	
	
		<title>
			<common:message bundle="embargorulesresources" key="reco.defaults.titleListEmbargo" scope="request"/>
		</title>
			<meta name="decorator" content="mainpanelrestyledui">
			<common:include type="script" src="/js/reco/defaults/ListEmbargo_Script.jsp"/>

			<bean:define id="form" name="ListEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"/>
	
<business:sessionBean id="levelCode"	moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="levelCode" />
<business:sessionBean id="globalParameters"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="globalParameters" />
<business:sessionBean id="status"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="status" />
<business:sessionBean id="embargoDetails"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="EmabrgoDetailVOs" />

<business:sessionBean id="ruleType"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="ruleType" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="categoryTypes" />
<business:sessionBean id="complianceTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="complianceTypes" />
<business:sessionBean id="applicableTransactionsList" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="uldPos" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="uldPos" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="subRisk" />

	</head>
	<body>
	
	
	     <div class="iCargoContent ic-masterbg" style="width:100%">
			<ihtml:form action="/reco.defaults.screenload.do" styleClass="ic-main-form">
				<ihtml:hidden name="ListEmbargoRulesForm" property="paraValue" />
				<ihtml:hidden name="ListEmbargoRulesForm" property="isButtonClicked" />
				<ihtml:hidden name="form" property="isScreenload" />
				<ihtml:hidden name="form" property="isDisplayDetails" />
				<ihtml:hidden  property="lastPageNum" />
				<ihtml:hidden  property="displayPage" />
				<ihtml:hidden property="isPrivilegedUser" name="form" />
				<ihtml:hidden  property="groupName" />
				<ihtml:hidden  property="groupType" />
				<ihtml:hidden property="autoCollapse" />
                <ihtml:hidden property="filterSummaryDetails" />
				<input type="hidden" name="currentDialogId" />
				<input type="hidden" name="currentDialogOption" />
				<input type="hidden" name="mySearchEnabled" />

	<div class="ic-content-main">
  					<!--DWLayoutTable-->
				<span class="ic-page-title"><common:message  key="listembargo.listembargo"/></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">	<div class="ic-row">&nbsp;</div>
						<div class="ic-row">
						<div class="ic-input ic-split-18">
										
							<div class="ic-row">
								<fieldset class="ic-field-set">
									<legend><label><common:message  key="listembargo.origin"/></label></legend>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.origintype"/></label>
										<ihtml:select property="originType" componentID="CMP_Reco_Defaults_ListEmbargo_OriginType" >
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="geographicLevelType">
													<logic:iterate id="originType" name="geographicLevelType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="originType" property="fieldValue">
															<bean:define id="fieldValue" name="originType" property="fieldValue"/>
															<bean:define id="fieldDescription" name="originType" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:present>
													</logic:iterate>
												</logic:present>
							   			</ihtml:select>
										</div>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.origin"/></label>
											<ihtml:text property="origin" componentID="CMP_Reco_Defaults_ListEmbargo_Origin"/>
											<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="originLov" width="22" height="22"/></div>
										</div>
										
								</fieldset>
							</div>
						
						</div>
						<div class="ic-input ic-split-19">
										
							<div class="ic-row">
								<fieldset class="ic-field-set">
									<legend><label>	<common:message  key="listembargo.destination"/></label></legend>
									<div class="ic-input ic-split-50"><label><common:message  key="reco.defaults.listembargo.lbl.destinationtype"/></label>
										<ihtml:select property="destinationType" componentID="CMP_Reco_Defaults_ListEmbargo_DestinationType">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="geographicLevelType">
													<logic:iterate id="originType" name="geographicLevelType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="originType" property="fieldValue">
															<bean:define id="fieldValue" name="originType" property="fieldValue"/>
															<bean:define id="fieldDescription" name="originType" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:present>
													</logic:iterate>
												</logic:present>
							   				</ihtml:select>
									</div>
									<div class="ic-input ic-split-50"><label><common:message  key="reco.defaults.listembargo.lbl.destination"/></label>
										<ihtml:text property="destination" componentID="CMP_Reco_Defaults_ListEmbargo_DestinationStation" />
  	    								<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="destinationLov" width="22" height="22"/></div>
									</div>
								</fieldset>
							</div>
						</div>
						
						<div class="ic-input ic-split-18">
											
							<div class="ic-row">
								<fieldset class="ic-field-set">
									<legend><label><common:message  key="listembargo.viaPoint"/></label></legend>
								   <div class="ic-input ic-split-51"><label><common:message  key="reco.defaults.listembargo.lbl.viaPointType"/></label>
										<ihtml:select property="viaPointType" componentID="CMP_Reco_Defaults_ListEmbargo_ViaPointType">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="geographicLevelType">
													<logic:iterate id="viaPointType" name="geographicLevelType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="viaPointType" property="fieldValue">
															<bean:define id="fieldValue" name="viaPointType" property="fieldValue"/>
															<bean:define id="fieldDescription" name="viaPointType" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:present>
													</logic:iterate>
												</logic:present>
										</ihtml:select>
								</div>
								 <div class="ic-input ic-split-44"><label><common:message  key="reco.defaults.listembargo.lbl.viaPoint"/></label>
								 <ihtml:text property="viaPoint" componentID="CMP_Reco_Defaults_ListEmbargo_ViaPoint" maxlength="3"/>
  	    						 <div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="viaPointLov" width="22" height="22"/></div>
								 </div>
								</fieldset>
								
						</div>
					
						</div>
						<!--Added by A-7924 as part of ICRD-313966 starts-->
						<div class="ic-input ic-split-20">
										
							<div class="ic-row">
								<fieldset class="ic-field-set">
									<legend><label><common:message  key="listembargo.segmentorigin"/></label></legend>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.segmentorigintype"/></label>
										<ihtml:select id="segmentOriginType" property="segmentOriginType" componentID="CMP_Reco_Defaults_ListEmbargo_SegmentOriginType" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="geographicLevelType">
													<logic:iterate id="segmentOriginType" name="geographicLevelType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="segmentOriginType" property="fieldValue">
															<bean:define id="fieldValue" name="segmentOriginType" property="fieldValue"/>
															<bean:define id="fieldDescription" name="segmentOriginType" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:present>
													</logic:iterate>
												</logic:present>
							   			</ihtml:select>
										</div>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.segmentorigin"/></label>
											<ihtml:text id="segmentOrigin" property="segmentOrigin" componentID="CMP_Reco_Defaults_ListEmbargo_SegmentOrigin"/>
											<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="segmentOriginLov" width="22" height="22"/></div>
										</div>
										
								</fieldset>
							</div>
						</div>
						
						<div class="ic-input ic-split-25">
										
							<div class="ic-row">
								<fieldset class="ic-field-set">
									<legend><label><common:message  key="listembargo.segmentdestination"/></label></legend>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.segmentdestinationtype"/></label>
										<ihtml:select id="segmentDestinationType" property="segmentDestinationType" componentID="CMP_Reco_Defaults_ListEmbargo_SegmentDestinationType" >
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="geographicLevelType">
													<logic:iterate id="segmentDestinationType" name="geographicLevelType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="segmentDestinationType" property="fieldValue">
															<bean:define id="fieldValue" name="segmentDestinationType" property="fieldValue"/>
															<bean:define id="fieldDescription" name="segmentDestinationType" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:present>
													</logic:iterate>
												</logic:present>
							   			</ihtml:select>
										</div>
										<div class="ic-input ic-split-50">
										<label><common:message  key="reco.defaults.listembargo.lbl.segmentdestination"/></label>
											<ihtml:text id="segmentDestination" property="segmentDestination" componentID="CMP_Reco_Defaults_ListEmbargo_SegmentDestination"/>
											<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="segmentDestinationLov" width="22" height="22"/></div>
										</div>
										
								</fieldset>
							</div>
						</div>
					<!--Added by A-7924 as part of ICRD-313966 ends-->
						
						
					
					</div>
					<div class="ic-row">
					<div class="ic-input ic-split-15">
					<label>	<common:message  key="listembargo.ruletype"/></label>
					<ihtml:select property="ruleType" componentID="CMP_Reco_Defaults_ListEmbargo_RuleType">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="ruleType" >
														<logic:iterate id="rule" name="ruleType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<bean:define id="fieldValue" name="rule" property="fieldValue"/>
															<bean:define id="fieldDescription" name="rule" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:iterate>
													</logic:present>
					</ihtml:select>
					</div>
					<div class="ic-input ic-split-15">
					<label><common:message key="listembargo.refnumber"/></label>
					<ihtml:text property="refNumber" componentID="CMP_Reco_Defaults_ListEmbargo_ReferenceNumber" style="text-transform:uppercase"/>
					</div>
					<div class="ic-input ic-split-15"><label><common:message key="listembargo.level"/></label>
					<ihtml:select property="level" componentID="CMP_Reco_Defaults_ListEmbargo_EmbargoLevel">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="levelCode">
												<logic:iterate id="levelCode" name="levelCode" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="levelCode" property="fieldValue">
														<bean:define id="fieldValue" name="levelCode" property="fieldValue"/>
														<bean:define id="fieldDescription" name="levelCode" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
												</logic:present>
												</logic:iterate>
											</logic:present>
					</ihtml:select>
					</div><div class="ic-split-25 ic-input ">
					<label><common:message  key="listembargo.daysofweek"/></label><div class="ic-border ic-content ic_inline_chcekbox and ic-serialcheck-wrapper">
					<ibusiness:frequency id="daysOfWeek" property="daysOfOperation"  componentID="CMP_Reco_Defaults_ListEmbargo_DaysOfWeek"  name="form" startDay="Mon" classForText="iCargoLabelRightAligned" />
					</div></div>
					<div class="ic-input ic-split-15"><label><common:message  key="listembargo.status"/></label>
						<ihtml:select property="status" componentID="CMP_Reco_Defaults_ListEmbargo_EmbargoStatus">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="status" >
														<logic:iterate id="status" name="status" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<bean:define id="fieldValue" name="status" property="fieldValue"/>
															<bean:define id="fieldDescription" name="status" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
														</logic:iterate>
													</logic:present>
						</ihtml:select>
					</div>
					</div>
					<jsp:include page="/jsp/reco/defaults/ListEmbargoParameters.jsp"/>
						<div class="ic-row">							
							<div class="ic-button-container">
						
									<ihtml:nbutton property="btnList" autoCollapse="true"  componentID="CMP_Reco_Defaults_ListEmbargo_BtnList" accesskey="L" value = "List"> <common:message key="listembargo.list"/></ihtml:nbutton>

									<ihtml:nbutton property="btnClear" componentID="CMP_Reco_Defaults_ListEmbargo_BtnClear" accesskey="C" value = "Clear"><common:message  key="listembargo.clear"/>
									</ihtml:nbutton>
							</div>
						</div>
						</div>
					</div>
		<div class="ic-main-container">
				
					
					<div class="ic-row">
					     <a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun=""  href="#"></a>
						<h3><common:message  key="reco.defaults.tablehead" scope="request"/></h3>
					</div>
					
					 <div  id="container"  class="ic-row">
					<jsp:include page="ListEmbargo_Table.jsp"/>	
					</div>
				
			</div>
		
	
				<div class="ic-foot-container">
					<div class="ic-row paddR5">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnCreate" componentID="CMP_Reco_Defaults_ListEmbargo_BtnCreate" accesskey="R"><common:message  key="listembargo.create"/></ihtml:nbutton>
							<common:xgroup >
								  <common:xsubgroup id="COMPLIANCE">
							<ihtml:nbutton property="btnCreateCompInfo" componentID="CMP_Reco_Defaults_ListEmbargo_BtnCreateCompInfo" ><common:message  key="listembargo.createcompinfo"/></ihtml:nbutton>
							</common:xsubgroup>
							  </common:xgroup >
							 <ihtml:nbutton property="btnDetails" componentID="CMP_Reco_Defaults_ListEmbargo_BtnDetails" accesskey="D"><common:message  key="listembargo.details"/></ihtml:nbutton>
							 <logic:present name="form" property="isPrivilegedUser">
									<bean:define id="privilegedUser" name="form" property="isPrivilegedUser"/>
										<logic:equal name="privilegedUser" value="Y">				
							<common:xgroup >
								  <common:xsubgroup id="COMPLIANCE">
							 <ihtml:nbutton property="btnApprove" componentID="CMP_Reco_Defaults_ListEmbargo_BtnApprove" ><common:message  key="listembargo.approve"/></ihtml:nbutton>
							 <ihtml:nbutton property="btnReject" componentID="CMP_Reco_Defaults_ListEmbargo_BtnReject" ><common:message  key="listembargo.reject"/></ihtml:nbutton>
							 </common:xsubgroup>
							  </common:xgroup >
							<ihtml:nbutton property="btnCancel" componentID="CMP_Reco_Defaults_ListEmbargo_BtnCancel"  accesskey="A"><common:message  key="listembargo.cancel"/></ihtml:nbutton>
							</logic:equal>
							</logic:present>
							<ihtml:nbutton property="btnClose" componentID="CMP_Reco_Defaults_ListEmbargo_BtnClose" accesskey="O"><common:message  key="listembargo.close"/></ihtml:nbutton>
						</div>
					</div>
				</div>	
			
				
							
</div>

</ihtml:form>
 </div>
			
	
	</body>
</html:html>

