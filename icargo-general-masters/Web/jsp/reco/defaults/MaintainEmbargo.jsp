<%--
/***********************************************************************
* Project	     	       : iCargo
* Module Code & Name 	   :
* File Name          	   : MaintainEmbargo.jsp
* Date                     : 17-AUG-2015
* Author(s)           	   : Ramesh Chandra Pradhan
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"%>


		
	
<html:html>


<head>
		

	
	
	<title>
		<common:message bundle="embargorulesresources" key="reco.defaults.titleMaintainEmbargo" scope="request"/>
	</title>
	
		<meta name="decorator" content="mainpanelrestyledui">

		<common:include type="script" src="/js/reco/defaults/MaintainEmbargo_Script.jsp"/>


</head>

<body id="bodyStyle">
	
	
	
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="uldPos" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="uldPos" />
<business:sessionBean id="levelCode" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="levelCode" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults"	screenID="reco.defaults.maintainembargo"	method="get" attribute="embargoParameterVos" />
<business:sessionBean id="globalParameters"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="globalParameters" />
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="applicableLevelsForParameters" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableLevelsForParameters" /><!--Added by A-8374 for IASCB-78839-->
<business:sessionBean id="weightsApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="weightsApplicableOn" />
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="categoryTypes" />
<business:sessionBean id="complianceTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="complianceTypes" />
<business:sessionBean id="applicableTransactionsList" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="userLanguages" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="adminUserlanguages" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="subRisk" />
	
	<% 
		String embargoRefNo="";
		boolean embargoCool=false;
		boolean embargoFrozen=false;
		boolean embargoCC=false;
		String  embargoOrigin="";
		String  embargoOriginType="";
		String  embargoDestination="";
		String  embargoDestinationType="";
		String  embargoDescription="";
		String  embargoRemarks="";
		String  embargoParameterCode="";
		String  embargoParameterApplicable="";
		String  embargoParameterValues="";
		String applicableOn = "";

	%>
	
	<bean:define id="form" 
			name="MaintainEmbargoRulesForm" 
			toScope="page" 
			type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>


<div class="iCargoContent ic-masterbg" style="overflow:auto;">
	<ihtml:form action="/reco.defaults.maintainscreenload.do">
	
		<ihtml:hidden name="form" property="isScreenload" />
		<ihtml:hidden name="form" property="isDisplayDetails" />
		<ihtml:hidden name="form" property="isSaved" />
		<ihtml:hidden name="form" property="isButtonClicked" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="fromList" name="form"/>

		<ihtml:hidden property="refNumberFlag" name="form" />
		<ihtml:hidden property="isPrivilegedUser" name="form" />
		<ihtml:hidden property="isDuplicatePresent" name="form" />

		<ihtml:hidden property="prevEmbargoLang" name="form" />
		<ihtml:hidden property="currentEmbargoLang" name="form" />
		<ihtml:hidden property="prevEmbargoDesc" name="form" />
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="maintainembargo.maintainembargo"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-row">
								<div class="ic-input ic-split-50 ic-mandatory ic-label-30">
									<label>
										<common:message key="maintainembargo.refnumber"/>
									</label>
									<ihtml:text property="refNumber" maxlength="15" componentID="CMP_Reco_Defaults_MaintainEmbargo_ReferenceNumber" style="text-transform:uppercase"/>
								</div>
								<div class="ic-input ic-split-50">
									<div class="ic-button-container">
										<ihtml:nbutton property="btnDisplay" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnDisplay" accesskey="L" >
											<common:message  key="maintainembargo.displaydetails" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnClear" accesskey="C" >
											<common:message key="maintainembargo.clear"/>
										</ihtml:nbutton>
									</div>	
								</div>
							</div>	
						</div>
					</div>	
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">							
					<jsp:include page="MaintainEmbargoGeneralParameters.jsp"/>
				</div>
			

				<div class="ic-row">
					<h4><common:message key="maintainembargo.geographicleveltab"/></h4>
					<div class="ic-button-container paddR5">
						<ul class="ic-list-link">								
							
								<a href="#" class="iCargoLink" id="linkAddTemplateRow">
									<common:message key="maintainembargo.addembargo" />
								</a>|
							
							
								<a href="#" class="iCargoLink" id="linkDeleteTemplateRow" >
									<common:message key="maintainembargo.deleteembargo" />
								</a>
																
						</ul>
					</div>
				</div>
				<div class="ic-row paddL5">
					<div class="ic-border"><jsp:include page="MaintainEmbargoGeographicLevel.jsp"/></div>
				</div>
				<div class="ic-row">
					<div class="ic-input-container">
						<fieldset class="ic-field-set inline_fieldset marginLR10" >
							<legend class="iCargoLegend" >
								<common:message  key="maintainembargo.daysofweek"/>
							</legend>
                            <div class="ic-row">
                                <div class=" ic-col-65">
                                    <div class="ic-input ic_inline_chcekbox ic-serialcheck-wrapper marginT10">
								<label>
									<common:message key="maintainembargo.daysofweek"/>
								</label>
								<ibusiness:frequency id="daysOfOperation" property="daysOfOperation" componentID="CMP_Reco_Defaults_MaintainEmbargo_DaysOfWeek"  name="form" startDay="Mon" classForText="iCargoLabelRightAligned" />
							</div>
                                </div>
                                <div class="ic-input ic-col-30 marginL5 ic-label-90" >
								<label>
									<common:message key="maintainembargo.applicableon"/>
								</label>
								<logic:present name="embargoVo" property="daysOfOperationApplicableOn">
									<bean:define id="applicableOnField" name ="embargoVo" property="daysOfOperationApplicableOn" type="String" />
									<ihtml:select property="daysOfOperationApplicableOn"  componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn" value="<%=(String)applicableOnField%>" >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="dayOfOperationApplicableOn">
											<logic:iterate id="appon" name="dayOfOperationApplicableOn">
												<bean:define id="apponvalue" name="appon" property="fieldValue" />
												<html:option value="<%=(String)apponvalue %>"><bean:write name="appon" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present>
									</ihtml:select>			  
								</logic:present>
								<logic:notPresent name="embargoVo" property="daysOfOperationApplicableOn">
									<ihtml:select property="daysOfOperationApplicableOn"  componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="dayOfOperationApplicableOn">
											<logic:iterate id="appon" name="dayOfOperationApplicableOn">
												<bean:define id="apponvalue" name="appon" property="fieldValue" />
												<html:option value="<%=(String)apponvalue %>"><bean:write name="appon" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
								</logic:notPresent>
							</div>
                            </div>
						</fieldset>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="paddL5"><div class="ic-input ic-mandatory ic-col-65">
								<label>
									<common:message key="maintainembargo.embargodesc"/>
								</label>
								<logic:present name="embargoVo" property="embargoDescription">
									<bean:define id="embargoDescriptionField" name="embargoVo" property="embargoDescription"/>
									<% embargoDescription=(String)embargoDescriptionField; %>
								</logic:present>
								<div style="display:none;width:100%;height:100%;" title="Description" id="moreDescdiv" name="moreDescdiv">
								<div  align="center"  class="ic-mandatory">
									<label>
										&nbsp
									</label>
									<logic:present name="userLanguages">
									  <bean:define id="languages" name="userLanguages" toScope="page"/>
										<ihtml:select property="languages"  onchange="handleLangChange()" id="languages" 
										 value="en_US">
											 <logic:iterate id="language" name="languages">
											   <logic:present name="language" property="fieldDescription">
												<bean:define id="languageDesc" name="language" property="fieldDescription" />
											   <logic:present name="language" property="fieldValue">
												<bean:define id="languageName" name="language" property="fieldValue" />
												   <html:option value="<%=(String)languageName%>">
													 <bean:write name="languageDesc" />
												   </html:option>
											   </logic:present>
											   </logic:present>
											</logic:iterate>
										</ihtml:select>
									</logic:present>
								</div>
								<br>
								<div class="ic-row">
								<label>
										&nbsp &nbsp &nbsp &nbsp &nbsp 
									</label>
								<!-- MODIFIED BY A-7908 cols="90" to cols="73" and rows from 16 to 14-->
									<textarea   style="white-space: pre-wrap;word-wrap: break-word;" name="embargoDescPanel" id="embargoDescPanel" value="<%=embargoDescription %>" cols="73" rows="14" maxlength="4000"></textarea>
								</div>
								
								<!-- MODIFIED BY A-7908 width: from 95% to 98.5% -->
								<div style="width: 98.5% !important">
									<div class="ic-row">
										<div class="ic-button-container">	
										<ihtml:button  property="btnmoreDescOk" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnOK" onclick="handleLangOk()"  >Ok</ihtml:button>
										<ihtml:button  property="btnmoreDescDelete" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnDELETE" onclick="handleLangDelete()">Delete</ihtml:button>
										</div>
									</div>
									</div>
								</div>							

								<div id="descriptionTable">
									<ihtml:textarea property="embargoDesc" value="<%=embargoDescription %>" maxlength="4000" style="width:250px;height:70px;" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoDescription">
									</ihtml:textarea>
									<a href="#" class="iCargoLink" id="moreLink" onclick="prepareAttributesForDesc(event,this,'moreDescdiv','moreDescdiv')"><common:message key="maintainembargo.moredescription" /></a>
								</div>
							</div></div>
							<div class="ic-input ic-col-30">
								<label>
									<common:message key="maintainembargo.remarks"/>
								</label>
								<logic:present name="embargoVo" property="remarks">
									<bean:define id="embargoRemarksField" name="embargoVo" property="remarks"/>
									<% embargoRemarks=(String)embargoRemarksField; %>
								</logic:present>
								<ihtml:textarea property="remarks" value="<%=embargoRemarks%>" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoRemarks" style="width:200px;height:35px;">
								</ihtml:textarea>
							</div>
						</div>	
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input-container paddR5">
						<div class="ic-row">
							<h4><common:message key="maintainembargo.embargodetails"/></h4>
							<div class="ic-button-container paddR5">
								<ul class="ic-list-link">								
									
										<a href="#" class="iCargoLink" name="linkAddParameterTemplateRow" id="linkAddParameterTemplateRow">
											<common:message key="maintainembargo.addembargo"/>
										</a>|
									
									
										<a href="#" class="iCargoLink" name="linkDeleteParameterTemplateRow" id="linkDeleteParameterTemplateRow">
											<common:message  key="maintainembargo.deleteembargo" />
										</a>
																			
								</ul>
							</div>
						</div>
						<div class="ic-row paddL5" style="height:200px;width:100%;">
							<div class="ic-border"><jsp:include page="MaintainEmbargoParametersNew.jsp"/></div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container padd5">
						<common:xgroup >
							<common:xsubgroup id="COMPLIANCE">
								<ihtml:nbutton property="btnConstruct" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnConstruct"  >
									<common:message key="maintainembargo.construct"/>
								</ihtml:nbutton>

								<ihtml:nbutton property="btnDuplicate" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnDuplicate"  >
									<common:message key="maintainembargo.duplicate"/>
								</ihtml:nbutton>

								<logic:present name="form" property="isPrivilegedUser">
									<bean:define id="privilegedUser" name="form" property="isPrivilegedUser"/>
									<logic:equal name="privilegedUser" value="Y">				
										<ihtml:nbutton property="btnApprove" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnApprove" >
											<common:message key="maintainembargo.approve"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnReject" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnReject"  >
											<common:message key="maintainembargo.reject"/>
										</ihtml:nbutton>
									</logic:equal>
								</logic:present>
							</common:xsubgroup>
						</common:xgroup >
						<ihtml:nbutton property="btnSave" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnSave" accesskey="S" >
							<common:message key="maintainembargo.save"/>
						</ihtml:nbutton>

						<common:xgroup>
							<common:xsubgroup id="COMPLIANCE">
								<logic:present name="form" property="isPrivilegedUser">
									<bean:define id="privilegedUser" name="form" property="isPrivilegedUser"/>
									<logic:equal name="privilegedUser" value="Y">			
										<ihtml:nbutton property="btnCancel" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnCancel" accesskey="C" >
											<common:message key="maintainembargo.cancel"/>
										</ihtml:nbutton>
									</logic:equal>
								</logic:present>
							</common:xsubgroup>
						</common:xgroup >

						<ihtml:nbutton property="btnClose" componentID="CMP_Reco_Defaults_MaintainEmbargo_BtnClose" accesskey="O">
							<common:message key="maintainembargo.close"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>

		<div id="controls_div" style="display:none;">
			<!-- column 3 controls-->
			<div id="column3_control_in">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
				<html:option value="IN">Include</html:option>				
				</ihtml:select>
			</div>
			<div id="column3_control_in_ex">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
				<html:option value="IN">Include</html:option>
				<html:option value="EX">Exclude</html:option>		
				</ihtml:select>
			</div>
			<div id="column3_control_in_fltnum">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
				<html:option value="IN">Include</html:option>
				<html:option value="EX">Exclude</html:option>	
				<html:option value="LTEQ">Less Than or Equal To</html:option>
				<html:option value="GTEQ">Greater Than or Equal To</html:option>				
				</ihtml:select>
			</div>
			<div id="column3_control_in_eq">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">	<html:option value="EQ">Equals</html:option>	
				</ihtml:select>
			</div>
			<div id="column3_control_in_ex_oth">
				<ihtml:select indexId="rowCount" property="isIncluded" value=""  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
				<html:option value="EQ">Equals</html:option>
				<html:option value="LT">Less Than</html:option>
				<html:option value="GT">Greater Than</html:option>
				<html:option value="LTEQ">Less Than or Equal To</html:option>
				<html:option value="GTEQ">Greater Than or Equal To</html:option>
			
				</ihtml:select>
			</div>
			<div id="column3_control_in_exif">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
				<html:option value="IN">Include</html:option>
				<html:option value="EX">Exclude</html:option>		
				<html:option value="EXIF">Exclude If</html:option>		
				</ihtml:select>
			</div>
			<div id="column3_control_in_exUnWgt">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">								
				<html:option value="LTEQ">Less Than or Equal To</html:option>
				<html:option value="GTEQ">Greater Than or Equal To</html:option>
				</ihtml:select>
			</div>
			<div id="column3_control_in_exRate">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">								
				<html:option value="LTEQ">Less Than or Equal To</html:option>
				<html:option value="GTEQ">Greater Than or Equal To</html:option>
				</ihtml:select>
			</div>
			<div id="column3_control_in_unid">
				<ihtml:select indexId="rowCount" property="isIncluded" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">									
				<html:option value="IN">Include</html:option>
				<html:option value="EX">Exclude</html:option>			
				</ihtml:select>
			</div>
			<!-- column 4 controls-->
			<div id="column4_control_unid_pi">
				<ihtml:text property="packingInstruction" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_PI" style="width:50%" onkeyup="validateNumber(this);" />
			</div>
			<div id="column4_control_text">
				<ihtml:text property="defaultText" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Default_Text" onblur="restrictValues(this);" style="width:50%" />
			</div>
			<div id="column4_control_unnumber">
			<ihtml:text property="unNumber" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UNNumber" onkeyup="validateNumber(this);" onblur="restrictValues(this);" style="width:50%" />
			</div>
			<div id="column4_control_date">
			<ihtml:text property="dates" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Dates" onchange="dateCheck(this);" onblur="restrictValues(this);" style="width:50%" /><common:message  key="maintainembargo.dateformat"/>
			</div>
			<div id="column4_control_awbprefix">
			<ihtml:text property="awbPrefix" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AWBPrefix" onkeyup="validateNumber(this);restrictAndAppendTokken(this,'4')" onblur="restrictValues(this);restrictLength(this);" style="width:50%" />
			</div>
			<div id="column4_control_weight">
			<ihtml:text property="weight" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Weight" style="width:50%" onkeyup="restrictToSingleNumber(this);" />
			</div>
			<div id="column4_control_numstp">
			<ihtml:text property="numberOfStops" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_NoOfStops" style="width:50%" onkeyup="restrictToSingleNumber(this);" />
			</div>
			<div id="column4_control_volume">
			<ihtml:text property="volume" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Weight" style="width:50%" onkeyup="restrictToSingleNumber(this);" />
			</div>
			<div id="column4_control_time">
			
			<ihtml:text property="time" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Time" onchange="validateTime(this);" style="width:50%"/><common:message  key="maintainembargo.timeformat"/>
			</div>
			<div id="column4_control_length">
			<ihtml:text property="parameterLength" value=""  onkeyup="validateNumber(this);restrictToSingleNumber(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Length"  />
			</div>
			<div id="column4_control_width">
			<ihtml:text property="width" value=""  onkeyup="validateNumber(this);restrictToSingleNumber(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Width" />
			</div>
			<div id="column4_control_height">
			<ihtml:text property="height" value=""  onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Height"  />
			</div>
			<div id="column4_control_flight_number">
				<ihtml:text property="carrierCode" value="" style="width:12%" maxlength="3" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightCarrierCode" />&nbsp
				<ihtml:text property="flightNumber" value="" style="width:20%" maxlength="5" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);checkFlightNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightNumber" />
			</div>
			<div id="column4_unid_number">
				<ihtml:text property="unIds" value="" style="width:100%" maxlength="28" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UNIDs" />
			</div>
		
			<div id="column4_control_flight_type">	
				<ihtml:select property="flightType" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightType" >
					<logic:present name="flightTypes">
						<logic:iterate id="type" name="flightTypes">
							<bean:define id="typevalue" name="type" property="fieldValue" />
							<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present>  		
				</ihtml:select>
			</div>
			<div id="column4_control_split_indicator">	
				<ihtml:select property="splitIndicator"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SplitIndicator" >
				<html:option value="Y">Y</html:option>
				</ihtml:select>
			</div>
			<div id="column4_control_flight_owner">	
				<ihtml:text property="flightOwner" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightOwner" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" height="20" id="flghtOwnr"  class="flghtOwnr"  onclick="displayLOV('showAirline.do','Y','Y','showAirline.do',flightOwner,'Airline','1','flightOwner','',0,null,null,null,null,10);"/></div>
			</div>
			<div id="column4_control_carrier">
				<ihtml:text property="carrier" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Carrier" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showAirline.do','Y','Y','showAirline.do',carrier,'carrier','1','carrier','',0,null,null,null,null,10);" height="20" id="carrier"/></div>
			</div>
			<div id="column4_control_commodity">	
				<ihtml:text property="commodity" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Commodity" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showCommodity.do','Y','Y','showCommodity.do',commodity,'commodity','1','commodity','',getElementIndex(this,'commodity'),null,null,null,null,10);" height="20" id="commodity"/></div>
			</div>
			<div id="column4_control_uldType">	
				<ihtml:text property="uldType" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Commodity" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('shared.uld.findUldTypeAndGroup.do','N','Y','shared.uld.findUldTypeAndGroup.do',uldType,'uldType','1','uldType','',0,null,null,null,null,10);" height="20" id="uldType"/></div>
			</div>
			<div id="column4_control_uldpos">	
				<ihtml:select property="uldPos" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UldPos" >
					<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
					<logic:present name="uldPos">
						<logic:iterate id="type" name="uldPos">
							<bean:define id="typevalue" name="type" property="fieldValue" />
							<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present>  		
				</ihtml:select>
			</div>
			<div id="column4_control_uldtyp">	
			<ihtml:text property="uldTyp" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UldTypeCode"  onblur="restrictValues(this);" style="width:86%" />
				<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22"  onclick="displayLOV('showUld.do','N','Y','showUld.do',uldTyp,'uldTyp','1','uldTyp','',0,null,null,null,null,10)" height="20" id="uldTyp"/>
				</div>				
			</div>
			<div id="column4_control_paymenttype">	
				<ihtml:text property="paymentType" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_PaymentType" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Payment Type','1','paymentType','paymentType',0,'reco.defaults.paymenttype','');" height="20" id="paymentType"/></div>
			</div>
			<div id="column4_control_product">	
				<ihtml:text property="productCode" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_product" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayProductLov('products.defaults.screenloadProductLov.do',productCode[0].value,'productName','','','0','productCode','Y',0);" height="20" id="productCode"/></div>
			</div>
			<div id="column4_control_agentCode">	
				<ihtml:text property="agentCode" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_agent" onblur="restrictValues(this);" style="width:86%" />
               <div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showAgentLOV(this)" id="agentLOV" width="20" height="20" /></div>
			</div>
			<div id="column4_control_agent_group">	
				<ihtml:text property="agentGroup" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_agentGroup" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',agentGroup,'AGTGRP','EMB','agentGroup','1',0);" height="20" id="agentGroup"/></div>
			</div>
			<div id="column4_control_scc">	
				<ihtml:text property="scc" value="" name="scc"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SCC"  onblur="restrictValues(this);" style="width:86%"/>
				<!--img src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="showLov(this,targetFormName)" height="16" id="sccvalues"/-->
				<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showScc.do','Y','Y','showScc.do',scc,'scc','1','scc','',getElementIndex(this,'scc'),null,null,null,null,10);" height="20" id="scc"/></div>
				
			</div>
			<div id="column4_control_scc_group">	
				<ihtml:text property="sccGroup" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SccGroup" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',sccGroup,'SCCGRP','EMB','sccGroup','1',getElementIndex(this,'sccGroup'));" height="20" id="sccGroup"/></div>
			</div>	
			<div id="column4_control_airline_group">	
				<ihtml:text property="airlineGroup" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AirlineGroup" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',airlineGroup,'ARLGRP','EMB','airlineGroup','1',0);" height="20" id="airlineGroup"/></div>
			</div>	
			<div id="column4_control_aircraftType_group">	
				<ihtml:text property="aircraftGroupType" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftGroupType" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',aircraftGroupType,'ACRTYPGRP','EMB','aircraftGroupType','1',0);" height="20" id="aircraftGroupType"/></div>
			</div>
			<div id="column4_control_aircraftType">	
				<ihtml:text property="aircraftType" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftType" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showAircraft.do','Y','Y','showAircraft.do',aircraftType,'aircraftType','1','aircraftType','',0,null,null,null,null,10);" height="20"  id="aircraftType"/></div>
			</div>
			<div id="column4_control_unWgt_input">	
				<ihtml:text property="unWeight" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_UnWeight_input" onkeyup="restrictToSingleNumber(this);" style="width:86%" />
			</div>
			<div id="column4_control_dvcst_input">	
				<ihtml:text property="dvForCustoms" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_UnWeight_input" onblur="restrictSpecialChar(this);" style="width:86%" />
			</div>
			<div id="column4_control_dvcrg_input">	
				<ihtml:text property="dvForCarriage" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_UnWeight_input" onblur="restrictSpecialChar(this);" style="width:86%" />
			</div>
			<div id="column4_control_mailcat">	
				<ihtml:select   property="mailCategory" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailCategory" multiple="multiple" onblur="restrictValues(this);"  >
					<logic:present name="mailCategory">
						<logic:iterate id="mailCat" name="mailCategory">
							<bean:define id="malcat" name="mailCat" property="fieldValue" />
							<html:option value="<%=(String)malcat %>"><bean:write name="mailCat" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present> 													
				</ihtml:select>
			</div>
			<div id="column4_control_mailcls">	
				<ihtml:select property="mailClass"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailClass" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="mailClass">
						<logic:iterate id="mailClas" name="mailClass">
							<bean:define id="malcls" name="mailClas" property="fieldValue" />
							<html:option value="<%=(String)malcls %>"><bean:write name="mailClas" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present> 													
				</ihtml:select>
			</div>
			<div id="column4_control_mailsubclsgrp">	
				<ihtml:select property="mailSubClassGrp"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailSubClsGrp" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="mailSubClassGrp">
						<logic:iterate id="mailsubclsgrp" name="mailSubClassGrp">
							<bean:define id="malsbclsg" name="mailsubclsgrp" property="fieldValue" />
							<html:option value="<%=(String)malsbclsg %>"><bean:write name="mailsubclsgrp" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present> 													
				</ihtml:select>                                                              
			</div>
			<div id="column4_control_mailsubcls">											
				<ihtml:text property="mailSubClass"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Mail_SubClass" onblur="restrictValues(this);" value="" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',mailSubClass,'mailSubClass','1','mailSubClass','',0,null,null,null,null,10);" height="20" id="mailSubClass"/></div>
			</div>	
			<div id="column4_control_servicecargoclass">	
				<ihtml:select property="serviceCargoClass"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceCargoClass" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="serviceCargoClass">
						<logic:iterate id="srvClas" name="serviceCargoClass">
							<bean:define id="srvcls" name="srvClas" property="fieldValue" />
							<html:option value="<%=(String)srvcls %>"><bean:write name="srvClas" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present> 						
				</ihtml:select>
			</div>
			<div id="column4_control_aircraftclassification">	
			 <ihtml:hidden property="aircraftClassification" name="form" value="" />
				<ihtml:text property="aircraftClassificationDesc" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftClassification" onblur="restrictValues(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Aircraft Classification','1','aircraftClassification','aircraftClassificationDesc',0,'shared.aircraft.aircraftClassification','');" height="20" id="aircraftClassification"/></div>
			</div>
			<div id="column4_control_shipperCode">	
				<ihtml:text property="shipperCode" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Shipper" onblur="restrictValues(this);" style="width:86%" />
               <div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showShipperLOV(this)" id="shipperLOV" width="20" height="20" /></div>
			</div>
			<div id="column4_control_shipper_group">	
				<ihtml:text property="shipperGroup" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_ShipperGroup" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',shipperGroup,'SHPCODGRP','EMB','shipperGroup','1',0);" height="20" id="shipperGroup"/></div>
			</div>
			<div id="column4_control_consigneeCode">	
				<ihtml:text property="consigneeCode" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Consignee" onblur="restrictValues(this);" style="width:86%" />
               <div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showConsigneeLOV(this)" id="consigneeLOV" width="20" height="20" /></div>
			</div>
			<div id="column4_control_consignee_group">	
				<ihtml:text property="consigneeGroup" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_ConsigneeGroup" onblur="restrictSpecialChar(this);" style="width:86%" />
                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',consigneeGroup,'CNSGRP','EMB','consigneeGroup','1',0);" height="20" id="consigneeGroup"/></div>
			</div>
			<div id="column4_control_shipmentType">	
				<ihtml:select property="shipmentType"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ShipmentType" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="shipmentType">
						<logic:iterate id="shipmntTyp" name="shipmentType">
							<bean:define id="shptyp1" name="shipmntTyp" property="fieldValue" />
							<html:option value="<%=(String)shptyp1%>"><bean:write name="shipmntTyp" property="fieldDescription" /></html:option>
						</logic:iterate>
					</logic:present> 						
				</ihtml:select>
			</div>
			<div id="column4_control_serviceType">	
                <ihtml:select property="serviceType"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceType" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="serviceType">
						<logic:iterate id="srvcTyp" name="serviceType">
							<bean:define id="srvcTyp1" name="srvcTyp" property="fieldValue" />
							<html:option value="<%=(String)srvcTyp1%>"><bean:write name="srvcTyp" property="fieldValue" /></html:option>
						</logic:iterate>
					</logic:present> 						
				</ihtml:select>
			</div>
			<div id="column4_control_serviceTypeForTechnicalStop">	
                <ihtml:select property="serviceTypeForTechnicalStop"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceTypeForTechnicalStop" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="serviceTypeForTechnicalStop">
						<logic:iterate id="srvcTypForTechnicalStop" name="serviceTypeForTechnicalStop">
							<bean:define id="srvcTypForTechnicalStop1" name="srvcTypForTechnicalStop" property="fieldValue" />
							<html:option value="<%=(String)srvcTypForTechnicalStop1%>"><bean:write name="srvcTypForTechnicalStop" property="fieldValue" /></html:option>
						</logic:iterate>
					</logic:present> 						
				</ihtml:select>
			</div>
			<div id="column4_control_unpg">	
				<ihtml:select property="unPg" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UnPg" onblur="restrictValues(this);" multiple="multiple">
					<logic:present name="unPg">
							<logic:iterate id="pkggrp" name="unPg">
								<bean:define id="pkggrp1" name="pkggrp" property="fieldValue" />
								<html:option value="<%=(String)pkggrp1%>"><bean:write name="pkggrp" property="fieldValue" /></html:option>
							</logic:iterate>
					</logic:present>  		
				</ihtml:select>
			</div>
			<div id="column4_control_consol">	
				<ihtml:select property="consol"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Consol" >
				<html:option value="Y">Yes</html:option>
				<html:option value="N">No</html:option>
				</ihtml:select>
			</div>	
			<div id="column4_control_unid_subRisk">
				<ihtml:text property="subRisk" value="" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SubRisk" style="width:50%" onkeyup="validateNumber(this);"/>
			</div>
			<!-- Added by 202766 for IASCB-159851 -->	
			<div id="column4_control_unknownShipper">	
				<ihtml:select property="unknownShipper"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UnknownShipper" >
				<html:option value="Y">Yes</html:option>
				<html:option value="N">No</html:option>
				</ihtml:select>
			</div>
			<div id="column5_control_div">
				<div id="column5_control_div_inline">
				<common:message  key="maintainembargo.applicableon"/>
				<ihtml:select property="applicableOn" styleClass="applicableOn" componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
				
				<logic:present name="applicableLevelsForParameters">
					<logic:iterate id="appl" name="applicableLevelsForParameters">
						<bean:define id="applOn" name="appl" property="fieldValue" />
						<html:option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></html:option>
					</logic:iterate>
				</logic:present>
				</ihtml:select>
			</div> 
			</div> 
							
			<div id="column6_control_div">
			<div id="column6_control_div_inline">
			<common:message  key="maintainembargo.typeofWeight"/>
				<select property="applicableOnParameter" name="applicableOnParameter" styleClass="applicableOn">
					<logic:present name="weightsApplicableOn">
						<logic:iterate id="appl" name="weightsApplicableOn">
							<bean:define id="applOn" name="appl" property="fieldValue" />
							<%if("T".equals(applOn)){%>
								<option selected="true" value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></option>
							<%}else{%>
								<option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></option>
							<%}%>
						</logic:iterate>
					</logic:present>
				</select>
			</div>
			</div>
		</div>
		
		
	</ihtml:form>
</div>


		
	</body>
</html:html>

