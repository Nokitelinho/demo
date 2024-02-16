<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.GPAContractFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO"%>
<%@ page info="lite" %>
<html:html>
<head>

	<title>.: iCargo Lite :. <common:message bundle="MailPerformanceResources" key="mailtracking.defaults.ux.mailperformance.pagetitle"/></title>
	<meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
<body >

<bean:define id="form"
		name="MailPerformanceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm"
		toScope="page" />
<business:sessionBean id="resditModeSession"
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="resditModes" />

		 <business:sessionBean id="serviceLevelSession"
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="serviceLevels" />

		 <business:sessionBean id="uSPSPostalCalendarVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="uSPSPostalCalendarVOs"/>
		 <logic:present name="uSPSPostalCalendarVOs">
		<bean:define id="uSPSPostalCalendarVOs"
			name="uSPSPostalCalendarVOs" toScope="page" />
		</logic:present>
		 <business:sessionBean id="calendarTypeSession"
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="calendarTypes" />
		 <business:sessionBean id="coTerminusVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="coTerminusVOs"/>
<business:sessionBean id="mailHandoverVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailHandoverVOs"/>
<business:sessionBean id="handOverTimeServiceLevel"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="serviceLevel"/>

		 <business:sessionBean id="handOverTimeMailClass"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailClasses"/>
		  <business:sessionBean id="mailServiceStndVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailServiceStndVOs"/>

		  <business:sessionBean id="mailServiceStandardVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailServiceStandardVOs"/>
<business:sessionBean id="gPAContractVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="gPAContractVOs"/>
  <business:sessionBean id="mailRdtMasterVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailRdtMasterVOs"/>

<ihtml:form action="/mail.operations.ux.mailperformance.screenload.do" >
	<ihtml:hidden property="screenFlag" />
	<ihtml:hidden property="statusFlag" />
	<ihtml:hidden name="form" property="lastPageNum" />
	<ihtml:hidden name="form" property="displayPage" />
	<ihtml:hidden name="form" property="pagination" />
	<ihtml:hidden name="form" property="postalCalendarAction" />
	      <div class="main-container">
        <header class="clearfix">
            <!--Filter Panel- Edit Fields-->
            <div class="animated  fadeInDown">
                <div class="pad-md pad-b-3xs col">
                    <div class="row">
                        <div class="col-24">
                            <div class="card">
                                 <div class="card-body smoke-bg pad-xs">
                                    <div class="form-check form-check-inline">
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="serviceStandards" value="serviceStandards" checked
										onclick="togglepanel('pane1','pane2','pane3','pane4','pane5','pane6','pane7','paneb1','paneb2', 'paneb3','paneb4','paneb5','paneb6','paneb7')">
                                        <label class="form-check-label" for="serviceStandards"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.servicestandards" /></label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="hoRadiobtn" value="handoverTime"
										onclick="togglepanel('pane2','pane1','pane3','pane4','pane5','pane6','pane7','paneb2','paneb1','paneb3','paneb4','paneb5','paneb6','paneb7')" >
                                        <label class="form-check-label" for="hoRadiobtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.handovertime" /></label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="rdtRadioBtn" value="RDTOffset"
										onclick="togglepanel('pane3','pane1','pane2','pane4','pane5','pane6','pane7','paneb3','paneb1','paneb2','paneb4','paneb5','paneb6','paneb7')" >
                                        <label class="form-check-label" for="rdtRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.RDToffset" /></label>
                                    </div>                                    <div class="form-check form-check-inline" >
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="ctRadioBtn" value="coTerminus"
										onclick="togglepanel('pane4','pane1','pane2','pane3','pane5','pane6','pane7','paneb4','paneb1','paneb2','paneb3','paneb5','paneb6','paneb7')" >
                                        <label class="form-check-label" for="ctRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.coterminus" /></label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="postCalender" value="postalCalendar"
										onclick="togglepanel('pane5','pane1','pane2','pane3','pane4','pane6','pane7','paneb5','paneb1','paneb2','paneb3','paneb4','paneb6','paneb7')" >
                                        <label class="form-check-label" for="inlineRadio5"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.postalcalender" /></label>
                                    </div>
                                    <div class="form-check form-check-inline">
									<!--Modified by A-8527 for ICRD-298762 -->
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="cidRadiobtn" value="contractID"
										onclick="togglepanel('pane6','pane5','pane3','pane1','pane4','pane2','pane7','paneb6','paneb5','paneb3','paneb1','paneb4','paneb2','paneb7')" >
                                        <label class="form-check-label" for="cidRadiobtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.contractid" /></label>
                                    </div>
									<!-- Added for ICRD-232361 -->
									 <div class="form-check form-check-inline">
                                        <input type="radio" name="mainRadio" tabindex="17" class="form-check-input" id="incentiveRadioBtn" value="incentives"
										onclick="togglepanel('pane7','pane1','pane2','pane3','pane4','pane5','pane6','paneb7','paneb1','paneb2','paneb3','paneb4','paneb5','paneb6')" >
                                        <label class="form-check-label" for="incentiveRadioBtn"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.incentives" /></label>
                                    </div>
									<!-- Added for ICRD-232361 -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--serviceStandards!-->
                    <div class="row mar-t-2md contentDisplay serviceStandards" id="pane1">
                        <div class="col-3 col-md-2 ">
                            <div class=" form-group">
									<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /><span class="mandatory">*</span></label> 		<!--Added by A-8399 as part of ICRD-304438-->
                                <div class="input-group">
                                   <logic:present name="form" property="serviceStandardsPacode">
									<bean:define id="PAcode" name="form" property="serviceStandardsPacode"/>
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsPacode" id="PAcode" styleClass="form-control" maxlength="7"  value="<%=(String)PAcode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_GPA"/>
									</logic:present>
									<logic:notPresent name="form" property="serviceStandardsPacode">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsPacode" id="PAcode" styleClass="form-control" maxlength="7" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_GPA"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-3 col-md-2">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.origin" /></label>
                                    <div class="input-group">
                                    <logic:present name="form" property="serviceStandardsOrigin">
									<bean:define id="airportOrigin" name="form" property="serviceStandardsOrigin"/>
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsOrigin" id="airportOrigin" styleClass="form-control" maxlength="50"  value="<%=(String)airportOrigin%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_ORIGIN"/>
									</logic:present>
									<logic:notPresent name="form" property="serviceStandardsOrigin">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsOrigin" id="airportOrigin" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_ORIGIN"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-3 col-md-2">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.destination" /></label>
                                    <div class="input-group">
                                 <logic:present name="form" property="serviceStandardsDestination">
									<bean:define id="airportDest" name="form" property="serviceStandardsDestination"/>
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsDestination" id="airportDest" styleClass="form-control" maxlength="50"  value="<%=(String)airportDest%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_DESTINATION"/>
									</logic:present>
									<logic:notPresent name="form" property="serviceStandardsDestination">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="serviceStandardsDestination" id="airportDest" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_DESTINATION"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                                        <div class="col-4 col-md-3">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.servicelevel" /></label>
                                 <ihtml:select tabindex="4" property="serviceLevel" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICELEVEL" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="serviceLevelSession">
									<logic:iterate id="serviceLevels" name="serviceLevelSession">
										<bean:define id="fieldValue" name="serviceLevels" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="serviceLevels" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>
                            </div>
                        </div>
                        <div class="col-4 col-md-3">
                            <div class=" form-group">
                                <div class="form-check mar-t-md form-check-inline">
                                    <ihtml:checkbox tabindex="4" id="scanningWaived" styleClass="form-check-input" property="scanningWaived" value="Y"/>
                                    <label class="form-check-label" for="inlineCheckbox1"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.scanningwaived" /></label>
                                </div>
                            </div>
                        </div>
                                <div class="col-2">
                            <div class="form-group">
                                <label class="form-control-label" style="width:160%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.servicestandard" /></label>  <!--Added width by A-8399 as part of ICRD-304438-->
                                <logic:present name="form" property="serviceStandard">
									<bean:define id="ServiceStandard" name="form" property="serviceStandard"/>
										<ihtml:text tabindex="4" property="serviceStandard" id="ServiceStandard" styleClass="form-control" maxlength="5"  value="<%=(String)ServiceStandard%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_SERVICESTD"/>
									</logic:present>
									<logic:notPresent name="form" property="serviceStandard">
										<ihtml:text tabindex="4" property="serviceStandard" id="ServiceStandard" styleClass="form-control" maxlength="5" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_SERVICESTD"/>
									</logic:notPresent>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.contractid" /></label>
                                 <logic:present name="form" property="contractId">
									<bean:define id="ConId" name="form" property="contractId"/>
										<ihtml:text tabindex="4" property="contractId" id="ConId" styleClass="form-control" maxlength="10"  value="<%=(String)ConId%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_CONTRACTID"/>
									</logic:present>
									<logic:notPresent name="form" property="contractId">
										<ihtml:text tabindex="4" property="contractId" id="ConId" styleClass="form-control" maxlength="10" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_CONTRACTID"/>
									</logic:notPresent>
                            </div>
                        </div>
                    </div>
                    <!--handoverTime!-->
                    <div class="row mar-t-2md contentDisplay handoverTime" style="display:none" id="pane2">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /></label>
                                <div class="input-group">

									 <logic:present name="form" property="hoPaCode">
										<bean:define id="hopacode" name="form" property="hoPaCode"/>
											<ihtml:text tabindex="14" inputGroup="true" buttonIconClass="icon ico-expand" property="hoPaCode" id="hopacode" styleClass="form-control" maxlength="7"  value="<%=(String)hopacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:present>
									<logic:notPresent name="form" property="hoPaCode">
										<ihtml:text tabindex="14"  inputGroup="true" buttonIconClass="icon ico-expand" property="hoPaCode" id="hopacode" styleClass="form-control" maxlength="7"   componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:notPresent>
                                    </div>
                                </div>
                            </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcode" /></label>
                                <div class="input-group">
									<logic:present name="form" property="hoAirport">
										<bean:define id="hoairportcode" name="form" property="hoAirport"/>
											<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoAirport" id="hoairportcode" styleClass="form-control" value="<%=(String)hoairportcode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
									</logic:present>

									<logic:notPresent name="form" property="hoAirport">
										<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoAirport" id="hoairportcode" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
						<div class="col-4 col-md-2">
                            <div class=" form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.exchangeOffice" /></label>
                                <div class="input-group">
									<logic:present name="form" property="hoExchangeOffice">
										<bean:define id="hoExchangeOffice" name="form" property="hoExchangeOffice"/>
											<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoExchangeOffice" id="hoExchangeOffice" styleClass="form-control" value="<%=(String)hoExchangeOffice%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC"/>
									</logic:present>
									<logic:notPresent name="form" property="hoExchangeOffice">
										<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoExchangeOffice" id="hoExchangeOffice" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-3">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.mailclass" /></label>
                                           <div class="input-group">
											 <logic:present name="form" property="hoMailClass">
											<bean:define id="mailClass" name="form" property="hoMailClass"/>
												<ihtml:select property="hoMailClass" styleClass="form-control" tabindex="16" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>

													<logic:present name="handOverTimeMailClass">
														<logic:iterate id="malclsVO" name="handOverTimeMailClass">
															<bean:define id="fieldValue" name="malclsVO" property="fieldValue" />
															<%
																String isSelected = "";
																if(mailClass!= null && mailClass.equals(fieldValue)){
																isSelected = "selected";
																}
															%>
															<option value="<%=(String)fieldValue%>" <%=isSelected%> ><bean:write name="malclsVO" property="fieldValue" /></option>
														</logic:iterate>
													</logic:present>
												</ihtml:select>
										</logic:present>
										<logic:notPresent name="form" property="hoMailClass">
											<ihtml:select property="hoMailClass" styleClass="form-control" tabindex="16" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS" >
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="handOverTimeMailClass">
														<logic:iterate id="malclsVO" name="handOverTimeMailClass">
													<bean:define id="fieldValue" name="malclsVO" property="fieldValue" />
													<html:option value="<%=(String)fieldValue %>"><bean:write name="malclsVO" property="fieldValue" /></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
										</logic:notPresent>
                                    </div>
                                </div>
                            </div>
							<div class="col-4 col-md-2">
                            <div class=" form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.mailsubclass" /></label>
                                <div class="input-group">
									<logic:present name="form" property="hoMailSubClass">
										<bean:define id="hoMailSubClass" name="form" property="hoMailSubClass"/>
											<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoMailSubClass" id="hoMailSubClass" styleClass="form-control" value="<%=(String)hoMailSubClass%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS"/>
									</logic:present>
									<logic:notPresent name="form" property="hoMailSubClass">
										<ihtml:text tabindex="15" inputGroup="true" buttonIconClass="icon ico-expand" property="hoMailSubClass" id="hoMailSubClass" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS"/>
										</logic:notPresent>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <!--RDT Off!-->
                     <div class="row mar-t-2md contentDisplay RDTOffset" style="display:none" id="pane3">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /></label>
                                <div class="input-group">
                                     <logic:present name="form" property="rdtPacode">
									<bean:define id="rdtPacode" name="form" property="rdtPacode"/>
										<ihtml:text tabindex="4"   inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="rdtPacode" id="rdtPacode" styleClass="form-control" maxlength="7"  value="<%=(String)rdtPacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
										<!-- //Modified by A-8331 as part of ICRD-301783 -->
									</logic:present>
									<logic:notPresent name="form" property="rdtPacode">
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="rdtPacode" id="rdtPacode" styleClass="form-control" maxlength="7" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
										<!-- //Modified by A-8331 as part of ICRD-301783  -->
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcodes" /></label>
                                <div class="input-group">
                                  <div class="input-group">
                                    <logic:present name="form" property="rdtAirport">
									<bean:define id="rdtAirport" name="form" property="rdtAirport"/>
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="rdtAirport" id="rdtAirport" styleClass="form-control" maxlength="50"  value="<%=(String)rdtAirport%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
										<!-- //Modified by A-8331 as part of ICRD-301783 -->
									</logic:present>
									<logic:notPresent name="form" property="rdtAirport">
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="rdtAirport" id="rdtAirport" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
										<!--  //Modified by A-8331 as part of ICRD-301783  -->
									</logic:notPresent>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Co-Terminus!-->
                    <div class="row mar-t-2md contentDisplay coTerminus" style="display:none" id="pane4">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
							<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /></label>
                                <div class="input-group" style="width:100px">   <!--Modified as part of ICRD-304483-->
                                    <logic:present name="form" property="coPacode">
									<bean:define id="coPacode" name="form" property="coPacode"/>
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="coPacode" id="coPacode" styleClass="form-control" maxlength="7"  value="<%=(String)coPacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:present>
									<logic:notPresent name="form" property="coPacode">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="coPacode" id="coPacode" styleClass="form-control" maxlength="7" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group"> <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcode" /></label>
                                <div class="input-group" style="width:100px">  <!--Modified as part of ICRD-304483-->
                                    <logic:present name="form" property="coAirport">
									<bean:define id="coAirport" name="form" property="coAirport"/>
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="coAirport" id="coAirport"
										 styleClass="form-control" maxlength="50"  value="<%=(String)coAirport%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
									</logic:present>
									<logic:notPresent name="form" property="coAirport">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" styleClass="form-control"
										property="coAirport" id="coAirport"  maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-3">
                            <div class=" form-group" style="width:130px">   <!--Modified as part of ICRD-304483-->
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.resditmode" /></label>
                                <ihtml:select property="filterResdit" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_RESMOD" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="resditModeSession">
									<logic:iterate id="resditmodeVO" name="resditModeSession">
										<bean:define id="fieldValue" name="resditmodeVO" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="resditmodeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>
                            </div>
                        </div>
                        <div class="col-5 col-md-4">
                            <div class=" form-group">
                                <div class="form-check mar-t-md form-check-inline">
                                   <ihtml:checkbox styleClass="form-check-input" property="receivedFromTruck" value="Y"/>
                                    <label for="inlineCheckbox1" class="form-check-label"><common:message key="mailtracking.defaults.ux.mailperformance.lbl.onlyfromtruck" /></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Postal calender!-->
                    <div class="row mar-t-2md contentDisplay postalCalendar" style="display:none" id="pane5">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
									<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /><span class="mandatory">*</span></label>  		<!--Added by A-8399 as part of ICRD-304441-->
                                <div class="input-group">
                                    <logic:present name="form" property="calPacode">
									<bean:define id="calPacode" name="form" property="calPacode"/>
										<ihtml:text tabindex="4"   inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="calPacode" id="calPacode" styleClass="form-control" maxlength="7"  value="<%=(String)calPacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:present>
									<logic:notPresent name="form" property="calPacode">
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="calPacode" id="calPacode" styleClass="form-control" maxlength="7" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-3">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.calendertype" /></label>
									 <ihtml:select property="filterCalender" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CALENDERTYP" >
								<logic:present name="calendarTypeSession">
									<logic:iterate id="calendertypeVO" name="calendarTypeSession">
										<bean:define id="fieldValue" name="calendertypeVO" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="calendertypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>
                            </div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.fromdate" /></label>				<!--Added by A-8399 as part of ICRD-304441-->
								<div class="input-group">
                                <logic:present name="form" property="calValidFrom">
									<bean:define id="calValidFrom" name="form"
													property="calValidFrom" toScope="page" />
									<ibusiness:litecalendar tabindex="5"
									labelStyleClass="form-control-label" id="calValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM"
									property="calValidFrom"  value="<%=(String) calValidFrom%>" />
							   </logic:present>
							    <logic:notPresent name="form" property="calValidFrom">
									<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="calValidFrom" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM"
										property="calValidFrom"  />
							   </logic:notPresent>
								</div>
							</div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class="form-group">
								<label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.todate" /></label>					<!--Added by A-8399 as part of ICRD-304441-->
                                <div class="input-group">
                                <logic:present name="form" property="calValidTo">
									<bean:define id="calValidTo" name="form"
													property="calValidTo" toScope="page" />
									<ibusiness:litecalendar tabindex="5"
									labelStyleClass="form-control-label" id="calValidTo" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
									property="calValidTo"  value="<%=(String) calValidTo%>" />
							   </logic:present>
							    <logic:notPresent name="form" property="calValidTo">
									<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="calValidTo" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
										property="calValidTo"  />
							   </logic:notPresent>
								</div>
                            </div>
                        </div>
                    </div>


				    <div class="row mar-t-2md contentDisplay contractID" style="display:none;" id="pane6">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /><span class="mandatory">*</span></label> 			<!--Added by A-8399 as part of ICRD-304438-->
                                <div class="input-group">


									  <logic:present name="form" property="conPaCode">
									  <bean:define id="conPacode" name="form" property="conPaCode" />
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="conPaCode" id="conPacode" styleClass="form-control" maxlength="7"  value="<%=(String)conPacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									  </logic:present>
									  <logic:notPresent name="form" property="conPaCode">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="conPaCode" id="conPacode" styleClass="form-control" maxlength="7" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									  </logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.origin" /></label>
                                <div class="input-group">

									<logic:present name="form" property="originAirport">
									 <bean:define id="originAirport" name="form" property="originAirport" />
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="originAirport" id="originAirport" styleClass="form-control" maxlength="50"  value="<%=(String)originAirport%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN"/>
									</logic:present>
									<logic:notPresent name="form" property="originAirport">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="originAirport" id="originAirport" styleClass="form-control" maxlength="50" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-4 col-md-2">
                            <div class=" form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.destination" /></label>
                                <div class="input-group">

									<logic:present name="form" property="destinationAirport">
 <bean:define id="destinationAirport" name="form" property="destinationAirport" />
										<ihtml:text tabindex="4"  inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="destinationAirport" id="destinationAirport" styleClass="form-control" maxlength="50"  value="<%=(String)destinationAirport%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION"/>
									</logic:present>
									<logic:notPresent name="form" property="destinationAirport">
										<ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"
										property="destinationAirport" id="destinationAirport" styleClass="form-control" maxlength="50" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION"/>
									</logic:notPresent>
                                </div>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.contractid" /></label>

								<logic:present name="form" property="contractID">
								<bean:define id="contractID" name="form" property="contractID" />
										<ihtml:text tabindex="4" labelStyleClass="form-control-label"
										property="contractID" id="contractID" styleClass="form-control" maxlength="50"  value="<%=(String)contractID%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID"/>
									</logic:present>
									<logic:notPresent name="form" property="contractID">
										<ihtml:text tabindex="4" labelStyleClass="form-control-label"
										property="contractID" id="contractID" styleClass="form-control" maxlength="50" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID"/>
									</logic:notPresent>
                            </div>
                        </div>
						<div class="col-2">
                            <div class="form-group">
                                <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.region" /></label>
								<logic:present name="form" property="region">
								<bean:define id="region" name="form" property="region" />
										<ihtml:text tabindex="5" labelStyleClass="form-control-label"
										property="region" id="region" styleClass="form-control" maxlength="5"  value="<%=(String)region%>" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION"/>
									</logic:present>
									<logic:notPresent name="form" property="region">
										<ihtml:text tabindex="5" labelStyleClass="form-control-label"
										property="region" id="region" styleClass="form-control" maxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION"/>
									</logic:notPresent>
                    </div>
                        </div>
                    </div>
					<!-- Incentives/disincentives -->
					<div class="row mar-t-2md contentDisplay incentive" style="display:none;" id="pane7">
                        <div class="col-4 col-md-2 ">
                            <div class=" form-group">
								 <label class="form-control-label"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.gpacode" /><span class="mandatory">*</span></label>
                                <div class="input-group">
									 <logic:present name="form" property="incPaCode">
										<bean:define id="incPaCode" name="form" property="incPaCode"/>
											<ihtml:text tabindex="14" inputGroup="true" buttonIconClass="icon ico-expand" property="incPaCode" id="incPaCode" styleClass="form-control" maxlength="5"  value="<%=(String)incPaCode%>" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:present>
									<logic:notPresent name="form" property="incPaCode">
										<ihtml:text tabindex="14"  inputGroup="true" buttonIconClass="icon ico-expand" property="incPaCode" id="incPaCode" styleClass="form-control" maxlength="5"   componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_GPA"/>
									</logic:notPresent>
                </div>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="btn-row">
                    <ihtml:nbutton styleClass="btn btn-primary flipper" property="btnList" id="btnList" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_LIST" accesskey="L">
							<common:message key="mailtracking.defaults.ux.mailperformance.btn.list" />
					</ihtml:nbutton>
                    <ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CLEAR" accesskey="C">
							<common:message key="mailtracking.defaults.ux.mailperformance.btn.clear" />
					</ihtml:nbutton>
                </div>
            </div>
            <!--Filter Panel- Edit Fields ENDS-->
        </header>
        <section class="pad-t-md">
            <div class="card contentDisplay serviceStandards" id="paneb1">
               <jsp:include page="ServiceStandards.jsp" />
            </div>
			<!-- HandOver Time List -->
            <div class="card contentDisplay handoverTime" style="display:none;" id="paneb2">
                <jsp:include page="HandoverTime.jsp"/>
                    </div>


            <div class="card contentDisplay RDTOffset" style="display:none;" id="paneb3">
                <div class="card-header d-flex justify-content-end">
                    <div class="tool-bar align-items-center  pad-y-2sm">
                        <ihtml:nbutton id="btnRdtAdd" styleClass="btn btn-primary" property="btnRdtAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
						</ihtml:nbutton>
						<ihtml:nbutton id="btnRdtDelete" property="btnDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
                    </div>
                </div>
                <div class="card-body p-0" id="rdtOffSet">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="rdtOffestTable" class="table table-x-md m-0" style="width:100%">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell"><input type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.rowRowId);"></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcodes" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.RDToffset" /></th>
                                <th style="width:3%"></th>
                            </tr>
                        </thead>
                        <tbody id="rdtOffsetTableBody">
						  <logic:present name="mailRdtMasterVOs">
						   <logic:iterate id ="rdtOffsetVO" name="mailRdtMasterVOs" type="com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO" indexId="rowCount">
                            <!--<tr>
                                <td class="text-center"><input type="checkbox"></td>
                                <td></td>
                                <td></td>
                                <td><a href=""><i class="icon ico-del"></i></a></td>
                            </tr>
                            <tr>
                                <td class="text-center"><input type="checkbox"></td>
                                <td></td>
                                <td></td>
                                <td><a href=""><i class="icon ico-del"></i></a></td>
                            </tr>
                            <tr>
                                <td class="text-center"><input type="checkbox"></td>
                                <td></td>
                                <td></td>
                                <td><a href=""><i class="icon ico-del"></i></a></td>
                            </tr>
                            <tr>
                                <td class="text-center"><input type="checkbox"></td>
                                <td></td>
                                <td></td>
                                <td><a href=""><i class="icon ico-del"></i></a></td>
                            </tr>
                            <tr>
                                <td class="text-center"><input type="checkbox"></td>
                                <td></td>
                                <td></td>
                                <td><a href=""><i class="icon ico-del"></i></a></td>
                            </tr>-->
							<tr>
								<logic:present name="rdtOffsetVO" property="operationFlag">
								<bean:define id="rdtOperationFlag" name="rdtOffsetVO" property="operationFlag" toScope="request" />
								<bean:define id="seqnum" name="rdtOffsetVO" property="seqnum" toScope="request" />
								<logic:notEqual name="rdtOffsetVO" property="operationFlag" value="D">
								<td class="text-center ">
									&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="rdtRowId" value="<%=String.valueOf(rowCount)%>"/>
								</td>
								<td>
									<logic:equal name="rdtOffsetVO" property="operationFlag" value="I">

										<ihtml:text property="airportCodes" name="rdtOffsetVO" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px" />
									</logic:equal>
									<logic:notEqual name="rdtOffsetVO" property="operationFlag" value="I">
										<ihtml:text property="airportCodes" styleClass="form-control" name="rdtOffsetVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px"/>
									</logic:notEqual>
								</td>
								<td>
									<logic:equal name="rdtOffsetVO" property="operationFlag" value="I">
										<ihtml:text property="rdtOffset" name="rdtOffsetVO" styleClass="form-control"  style="width:230px" />
									</logic:equal>
									<logic:notEqual name="rdtOffsetVO" property="operationFlag" value="I">
										<ihtml:text property="rdtOffset" styleClass="form-control" name="rdtOffsetVO" readonly="true"  style="width:230px"/>
									</logic:notEqual>
								</td>

								<td class="text-center">
										 <a  id="delete<%=rowCount%>" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
										 </td>
									</logic:notEqual>
								<logic:equal name="rdtOffsetVO" property="operationFlag" value="D">
									<ihtml:hidden property="airportCodes" name="rdtOffsetVO"/>
								</logic:equal>
								<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
								<ihtml:hidden property="rdtOperationFlag" value="<%=((String)rdtOperationFlag)%>" />
								</logic:present>
								<logic:notPresent name="rdtOffsetVO" property="operationFlag">
								<bean:define id="seqnum" name="rdtOffsetVO" property="seqnum" toScope="request" />
								<td class="text-center">
									&nbsp;&nbsp; <ihtml:checkbox property="rdtRowId" id="<%=String.valueOf(rowCount)%>" value="<%=String.valueOf(rowCount)%>" />
								</td>
								<td>
								<div class="input-group">
															<ihtml:text style="width:100px"  property="airportCodes" name="rdtOffsetVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" styleClass="form-control"/>
															<div class="input-group-append">
																	 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="rdtairLov" property="rdtairLOV">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
																</div>
																</div>
								</td>
							    <td>
									<ihtml:text property="rdtOffset" styleClass="form-control" name="rdtOffsetVO"  style="width:100px" />

								</td>
								<td class="text-center">
										 <a  id="delete<%=rowCount%>" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
										 </td>
								<ihtml:hidden property="rdtOperationFlag" value="N" />
								<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
								</logic:notPresent>
								</tr>
							</logic:iterate>
						 </logic:present>
						 <tr  template="true" id="rdtOffsetTemplateRow" style="display:none">
												<ihtml:hidden property="rdtOperationFlag" value="NOOP" />
												<ihtml:hidden property="seqnum" value="" />

														 <td class="text-center">
															<input type="checkbox"  name="rdtRowId"/>
														 </td>
														 <td>

															<div class="input-group">
															<ihtml:text style="width:100px" value="" property="airportCodes" id="airportCodes" />
															<div class="input-group-append">
																	 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="rdtairLOV" property="rdtairLOV"
																	  >
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
																</div>
																</div>
														 </td>
                                                         <td class="text-center">
														    <ihtml:text style="width:100px" value="" property="rdtOffset" id="rdtOffset" maxlength="5" />
														 </td>
														 <td class="text-center">
														 &nbsp;&nbsp; <a  id="delete" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
														 </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            </div>
            <div class="card contentDisplay coTerminus" style="display:none;" id="paneb4">
               <jsp:include page="CoTerminus.jsp"/>
            </div>
            <div class="card contentDisplay postalCalendar" style="display:none;" id="paneb5">
                <div class="card-header card-header-action">
				<div class="col">
                        <h4>Postal Calendar</h4>
                    </div>
                    <div class="card-header-btns">
                        <ihtml:nbutton styleClass="btn btn-primary" property="btnCalAdd" id="btnCalAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnDelete" id="btnCalDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
                    </div>
                </div>
                <div class="card-body p-0" id="postalCal" name="postalCal">
                   <!-- Ajax call -->
                </div>
            </div>

            <div class="card contentDisplay contractID" style="display:none;" id="paneb6">
                <jsp:include page="ContractId.jsp"/>
            </div>
			 <div class="card contentDisplay incentive" style="display:none;" id="paneb7">
                <jsp:include page="IncentiveConfiguration.jsp"/>
            </div>
        </section>
        <footer>
            <ihtml:nbutton styleClass="btn btn-primary" id="btnSave" property="btnSave" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SAVE" accesskey="S">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.save" />
						</ihtml:nbutton>
            <ihtml:nbutton property="btnClose" id="btnClose" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CLOSE" accesskey="O">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.close" />
						</ihtml:nbutton>
        </footer>
    </div>
	</ihtml:form>
	</body>
</html:html>