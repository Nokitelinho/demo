
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  IN - ULD
* File Name				:  MaintainDiscrepancy.jsp
* Date					:  12-May-2006, 20-Oct-2015
* Author(s)				:  A-2052, a-6770
*************************************************************************/
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.ArrayList"%>

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<html:html>
	<head>
		<title>
			<common:message bundle="maintainulddiscrepancies" key="uld.defaults.listulddiscrepancies.maintaindiscrepancy" />
		</title>
		
		<meta name="decorator" content="mainpanelrestyledui">
			<common:include type="script" src="/js/uld/defaults/MaintainDiscrepancy_Script.jsp" />
	</head>
	<body class="ic-center" style="width:980px;">
		<bean:define id="form"
 		name="MaintainUldDiscrepanciesForm"
 		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm"
 		toScope="page"/>

		
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainulddiscrepancies"
		method="get"
		attribute="oneTimeValues" />
		
<business:sessionBean id="KEY_VALUES"
 		   moduleName="uld.defaults"
 		   screenID="uld.defaults.maintainulddiscrepancies"
 		   method="get"
 		   attribute="ULDDiscrepancyVODetails"/>
<business:sessionBean id="discrepancyCodeFromSession"
 		   moduleName="uld.defaults"
 		   screenID="uld.defaults.maintainulddiscrepancies"
 		   method="get"
 		   attribute="discrepancyCode"/>
<business:sessionBean id="KEY_NAVIGATE"
 		   moduleName="uld.defaults"
 		   screenID="uld.defaults.maintainulddiscrepancies"
 		   method="get"
 		   attribute="ULDDiscrepancyVOs"/>


<business:sessionBean id="KEY_LIST"
	   moduleName="uld.defaults"
	   screenID="uld.defaults.maintainulddiscrepancies"
	   method="get"
	   attribute="ULDDiscrepancyFilterVODetails"/>

 <logic:present name="KEY_VALUES">
<bean:define id="KEY_VALUES" name="KEY_VALUES"/>
</logic:present>

		<div class="iCargoContent">
			<ihtml:form action="/uld.defaults.listulddiscrepancies.creatediscrepancy.do">
			
			<ihtml:hidden property="recordUldNumber"/>
			<ihtml:hidden property="recordCode"/>
			<ihtml:hidden property="recordDate"/>
			<ihtml:hidden property="recordPOL"/>
			<ihtml:hidden property="recordPOU"/>
			<ihtml:hidden property="recordCurrentStation"/>
			<ihtml:hidden property="oldFacilityType"/>
			<ihtml:hidden property="uldDisplayPage"/>
			<ihtml:hidden property="uldLastPageNum"/>
			<ihtml:hidden property="uldTotalRecords"/>
			<ihtml:hidden property="uldCurrentPageNum"/>
			<ihtml:hidden property="locationName"/>

			<!-- add by A-5210 -->
			<ihtml:hidden property="listStatus" />

			<ihtml:hidden property="errorStatus"/>
			<ihtml:hidden property="pageURL"/>
			<ihtml:hidden property="filterStatus"/>
			<ihtml:hidden property="closeFlag"/>
			<ihtml:hidden property="flag"/>
			<ihtml:hidden property="detailsFlag"/>
			<html:hidden property="saveOpFlag"/>
			<ihtml:hidden property="buttonStatusFlag"/>
			<ihtml:hidden property="discDisableStat"/>
			<ihtml:hidden property="defaultComboValue"/>
			<ihtml:hidden property="saveStatusFlag"/>
			<ihtml:hidden property="saveStatusPopup"/>
			<input type="hidden" name="currentDialogId" />
			<input type="hidden" name="currentDialogOption"/>
			<ihtml:hidden property="listFlag"/>
			<ihtml:hidden property="discrepancymode"/>
			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message bundle="maintainulddiscrepancies" key="uld.defaults.listulddiscrepancies.maintaindiscrepancy"/>
				</span>
					<div class="ic-main-container">
					  <div class="ic-row ic-input-round-border marginT5" style="width=150px">
							<logic:equal name="MaintainUldDiscrepanciesForm" property="flag" value="modifymode">
							<bean:define id="KEY_NAVIGATE" name="KEY_NAVIGATE"/>
							<div class="ic-row">
								<div class="ic-button-container">
								  <logic:present name="KEY_NAVIGATE">
									<common:popuppaginationtag
										pageURL="javascript:selectNextUld('lastPageNum','displayPage')"
										linkStyleClass="iCargoLink"
										disabledLinkStyleClass="iCargoLink"
										displayPage="<%=form.getUldDisplayPage()%>"
										totalRecords="<%=form.getUldTotalRecords()%>"/>
								</logic:present>
								</div>
							</div>
							</logic:equal>
					  
						<div class="ic-row">
							<div class="ic-input-container">
								<div class="ic-col-35 ic-label-50">
									<div class="ic-input ic-mandatory ic-split-100">
										<label>
											<common:message key="uld.defaults.listulddiscrepancies.lbl.uldno"/>
										</label>
										<logic:present name="KEY_LIST" property="uldNumber">
											<bean:define id="uldNumber" name="KEY_LIST" property="uldNumber"/>
		
											<ibusiness:uld id="uldno" uldProperty="uldNoChild" uldValue="<%=(String)uldNumber%>" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_ULDNO" style="text-transform: uppercase"/>
										</logic:present>

										<logic:notPresent name="KEY_LIST" property="uldNumber">
												<ibusiness:uld id="uldno" uldProperty="uldNoChild" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_ULDNO" style="text-transform: uppercase"/>
										</logic:notPresent>
									</div>
								</div>
								<div class="ic-col-35 ic-label-50">
									<div class="ic-input ic-split-100">
										<label>
											<common:message key="uld.defaults.listulddiscrepancies.lbl.discrepancycode"/>
										</label>
										<logic:notEmpty name="MaintainUldDiscrepanciesForm" property="discrepancyCode">
				<ihtml:select property="discrepancyCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_DISCCODE">
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="uld.defaults.discrepancyCode">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
				</ihtml:select>
		</logic:notEmpty>
		<logic:empty name="MaintainUldDiscrepanciesForm" property="discrepancyCode">
			<ihtml:select property="discrepancyCode" value="F" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_DISCCODE">
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="uld.defaults.discrepancyCode">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
			</ihtml:select>
		</logic:empty>
									</div>
								</div>
								<div class="ic-col-30 ic-label-40">
									<div class="ic-input ic-mandatory ic-split-100">
										<label>
											 <common:message bundle="maintainulddiscrepancies" key="uld.defaults.listulddiscrepancies.lbl.discrepancydate"/>
										</label>
										<ibusiness:calendar type="image"  id="discrepancyDate" property="discrepancyDate" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_DISCDATE"/>
									</div>
								</div>
							</div>
						</div>
						<div class="ic-row marginT5">
							<div class="ic-input-container">
								<div class="ic-col-35 ic-label-50">
									<div class="ic-input ic-mandatory ic-split-100">
										<label>
											<common:message key="uld.defaults.listulddiscrepancies.lbl.reportingstation"/>
										</label>
										<logic:present name="KEY_LIST" property="reportingStation">
											<bean:define id="reportingStation" name="KEY_LIST" property="reportingStation"/>
												<ihtml:text componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_REPORTINGSTATION" property="reportingStationChild" value="<%=(String)reportingStation%>" maxlength="3"/>
										</logic:present>

										<logic:notPresent name="KEY_LIST" property="reportingStation">
											<ihtml:text componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_REPORTINGSTATION" property="reportingStationChild" name="MaintainUldDiscrepanciesForm" maxlength="3"/>
										</logic:notPresent>
										<div class="lovImg">
										<img name="reportingstationlov" id="reportingstationlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"  alt="Airport LOV"/>
										</div>
									</div>
								</div>
								<div class="ic-col-35 ic-label-50">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message bundle="maintainulddiscrepancies" key="uld.defaults.maintainulddiscrepancies.tooltip.facilitycode"/>
										</label>
													<logic:notEmpty name="MaintainUldDiscrepanciesForm" property="facilityType">			
				<logic:equal name="MaintainUldDiscrepanciesForm" property="facilityType" value="NIL">				
				<ihtml:select property="facilityType" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_FACCODE">
					<html:option value="NIL">NIL</html:option>
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
				</ihtml:select>
				</logic:equal>
				<logic:notEqual name="MaintainUldDiscrepanciesForm" property="facilityType" value="NIL">				
				<ihtml:select property="facilityType" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_FACCODE">
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
				</ihtml:select>
				</logic:notEqual>
			</logic:notEmpty>
			<logic:empty name="MaintainUldDiscrepanciesForm" property="facilityType">
			<ihtml:select property="facilityType" componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_FACCODE">
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
			</ihtml:select>
			</logic:empty>

									</div>
								</div>
								<div class="ic-col-30 ic-label-40">
									<div class="ic-input ic-split-100">
										<label>
											<common:message bundle="maintainulddiscrepancies" key="uld.defaults.listulddiscrepancies.tooltip.location"/>
										</label>
										<logic:present name="KEY_LIST" property="location">
											<bean:define id="location" name="KEY_LIST" property="location"/>
												<ihtml:text componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_LOC" property="location" value="<%=(String)location%>" maxlength="15"/>
										</logic:present>

										<logic:notPresent name="KEY_LIST" property="location">
											<ihtml:text componentID="TXT_ULD_DEFAULTS_MAINTAINULDDISCREPANCIES_LOC" property="location" name="MaintainUldDiscrepanciesForm" maxlength="15"/>
										</logic:notPresent>
										<div class="lovImg">
										<img name="locationlov" id="locationlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="Location LOV"/>
									</div>
								</div>
							</div>
						</div>
						</div>
						<div class="ic-row ic-label-17 marginT5">
							<div class="ic-input-container">
								<div class="ic-input ic-split-100">
									<label>
										<common:message key="uld.defaults.listulddiscrepancies.lbl.remarks"/>
									</label>
									<ihtml:textarea property="remarks"  rows="2" cols="45" style="width:760px"
										onblur="validateMaxLength(this,50);" componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_REMARKS"/>
								</div>
							</div>
						</div>
					  </div>
					</div>
					<div class="ic-foot-container">
						<div class="ic-row">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_SAVE" accesskey="S">
										<common:message key="uld.defaults.listulddiscrepancies.btn.btsave"/>
									</ihtml:nbutton>

									<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_CLOSE" accesskey="O">
										<common:message key="uld.defaults.listulddiscrepancies.btn.btclose"/>
									</ihtml:nbutton>
								</div>
						</div>
					</div>
			</div>
			</ihtml:form>
		</div>
	</body>
</html:html>

