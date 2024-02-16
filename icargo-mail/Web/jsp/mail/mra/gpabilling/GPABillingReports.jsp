<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:   MRA-GPABilling
* File Name				:  GPABillingReports.jsp
* Date					:  13-Mar-2007
* Author(s)				:  A-2408

*************************************************************************
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Calendar" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingReportsForm" %>

<bean:define id="form"
		 name="GPABillingReportsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingReportsForm"
		 toScope="page" />


			
	
<html:html>
<head>
		
		
	
		
<title><common:message key="mailtracking.mra.gpabilling.report.pagetitle" bundle="gpabillingreports" scope="request"/></title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/mail/mra/gpabilling/GPABillingReports_Script.jsp"/>
		<%@ include file="/jsp/includes/customcss.jsp" %> 

</head>
<body class="ic-center" style="width:64%;">
	
	
	

<%@include file="/jsp/includes/reports/printFrame.jsp" %>


<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.gpabillingreports"
	method="get" attribute="oneTimeVOs" />
  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" >
  <ihtml:form action="mailtracking.mra.gpabilling.reports.screenload.do">
  <ihtml:hidden property="specificFlag" value="N" />
	<common:xgroup>
		<common:xsubgroup id="TURKISH_SPECIFIC">
			 <%form.setSpecificFlag("Y");%>
		</common:xsubgroup>
	</common:xgroup >
	<div class="ic-content-main">
		<span class="ic-page-title"><common:message  key="mailtracking.mra.gpabilling.report.title" scope="request"/></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container ic-label-50">
						<div class="ic-row">
							<div class="ic-input ic-split-100 ic-mandatory">
								<label>
									<common:message  key="mailtracking.mra.gpabilling.report.reportid" scope="request"/>
								</label>
								<ihtml:select property="reportIdentifiers" componentID="MRA_GPABILLING_REPORTS_REPORTID" >
										<logic:present name="KEY_ONETIMES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<logic:equal name="parameterCode" value="mail.mra.gpabilling.gpabillingreports">
															<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
										</logic:present>
								</ihtml:select>
								<html:hidden property="selectedReport"/>
							</div>
						</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-section ic-border ic-filter-panel">
					<div class="ic-row">
						
						<div class="ic-row">
							<div class="ic-row" id="RPTMRA033" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.periodwisesmy" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDatePeriodBillSmy" id="fromDatePeriodBillSmy" type="image"
												value="<%=form.getFromDatePeriodBillSmy()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_PCNSMY"/>
											</div>
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDatePeriodBillSmy" id="toDatePeriodBillSmy" type="image"
												value="<%=form.getToDatePeriodBillSmy()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_PCNSMY"/>
											</div>
											<div class="ic-input ic-split-30">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.country" scope="request"/>
												</label>
												<ihtml:text property="country" componentID="MRA_GPABILLING_REPORTS_COUNTRY_PCNSMY" readonly="false" maxlength="3"/>
				          						<div class= "lovImg">
												<img src="<%=request.getContextPath()%>/images/lov.png" id="countrylov" height="22" width="22" alt=""/>
												</div>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodePeriodBillSmy" componentID="MRA_GPABILLING_REPORTS_GPACODE_PCNSMY"/>
				          						<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov1" height="22" width="22" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname1" >
					   								<ihtml:text property="gpaNamePeriodBillSmy" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCNSMY" readonly="true"/>
					   							</div>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						
							<div class ="ic-row" id="RPTMRA034" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.gpawisesmy" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodeGpaBillSmy" componentID="MRA_GPABILLING_REPORTS_GPACODE_GCNSMY"/>
				          						<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov2" height="22" width="22" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname2" >
					   								<ihtml:text property="gpaNameGpaBillSmy" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCNSMY" readonly="true"/>
					   							</div>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDateGpaBillSmy" id="fromDateGpaBillSmy" type="image"
												value="<%=form.getFromDateGpaBillSmy()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_GCNSMY"/>
											</div>
											<div class="ic-input ic-split-35">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDateGpaBillSmy" id="toDateGpaBillSmy" type="image"
												value="<%=form.getToDateGpaBillSmy()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_GCNSMY"/>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class ="ic-row" id="RPTMRA035" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.periodwise51" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDatePeriod51" id="fromDatePeriod51" type="image"
												value="<%=form.getFromDatePeriod51()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_PCN51"/>
											</div>
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDatePeriod51" id="toDatePeriod51" type="image"
												value="<%=form.getToDatePeriod51()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_PCN51"/>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodePeriod51" componentID="MRA_GPABILLING_REPORTS_GPACODE_PCN51"/>
				          						<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="gpaCodelov3" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65 ">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname3" >
					   								<ihtml:text property="gpaNamePeriod51" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCN51" readonly="true"/>
					   							</div>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class ="ic-row" id="RPTMRA036" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.gpawise51" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodeGpa51" componentID="MRA_GPABILLING_REPORTS_GPACODE_GCN51"/>
				          						<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="gpaCodelov4" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65 ">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname4" >
					   								<ihtml:text property="gpaNameGpa51" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCN51" readonly="true"/>
					   							</div>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDateGpa51" id="fromDateGpa51" type="image"
												value="<%=form.getFromDateGpa51()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_GCN51"/>
											</div>
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDateGpa51" id="toDateGpa51" type="image"
												value="<%=form.getToDateGpa51()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_GCN51"/>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class ="ic-row" id="RPTMRA037" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.periodwise66" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDatePeriod66" id="fromDatePeriod66" type="image"
												value="<%=form.getFromDatePeriod66()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_PCN66"/>
											</div>
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDatePeriod66" id="toDatePeriod66" type="image"
												value="<%=form.getToDatePeriod66()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_PCN66"/>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodePeriod66" componentID="MRA_GPABILLING_REPORTS_GPACODE_PCN66"/>
												<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="gpaCodelov5" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65 ">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname5" >
					   								<ihtml:text property="gpaNamePeriod66" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCN66" readonly="true"/>
					   							</div>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class ="ic-row" id="RPTMRA038" style="display:none">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.gpawise66" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCodeGpa66" componentID="MRA_GPABILLING_REPORTS_GPACODE_GCN66"/>
												<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="gpaCodelov6" alt="" />
												</div>
											</div>
											<div class="ic-input ic-split-65 ">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname6" >
					   								<ihtml:text property="gpaNameGpa66" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCN66" readonly="true"/>
					   							</div>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDateGpa66" id="fromDateGpa66" type="image"
												value="<%=form.getFromDateGpa66()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_GCN66"/>
											</div>
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDateGpa66" id="toDateGpa66" type="image"
												value="<%=form.getToDateGpa66()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_GCN66"/>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
							<div class ="ic-row" id="RPTMRA039">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message  key="mailtracking.mra.gpabilling.report.gpabillareport" scope="request"/></legend>
									<div class="ic-input-container ic-label-40">
										<div class="ic-row">
											<div class="ic-input ic-split-35 ic-mandatory">
												<label>
													 <common:message  key="mailtracking.mra.gpabilling.report.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCode" componentID="MRA_GPABILLING_REPORTS_GPACODE_GPABILL"/>
												<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="gpaCodelov7" alt="" />
												</div>
											</div>
											
											<div class="ic-input ic-split-65 ">
												<label class="ic-label-20">
													<common:message  key="mailtracking.mra.gpabilling.report.gpaname" scope="request"/>
												</label>
												<div id="gpaname" >
					   								<ihtml:text property="gpaName" componentID="MRA_GPABILLING_REPORTS_GPANAME_GPABILL" readonly="true"/>
					   							</div>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.billstatus" scope="request"/>
												</label>
												<ihtml:select componentID="MRA_GPABILLING_REPORTS_BILLINGSTATUS_GPABILL" property="billingStatus">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="KEY_ONETIMES">
														<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
															<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.gpabillingstatus">
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																		<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
																	</logic:present>
																</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>
												</ihtml:select>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDateBlbRpt" id="fromDateBlbRpt" type="image"
												value="<%=form.getFromDateBlbRpt()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_FROMDATE_BLBRPT"/>
											</div>
											<div class="ic-input ic-split-35 ">
												<label>
													<common:message  key="mailtracking.mra.gpabilling.report.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDateBlbRpt" id="toDateBlbRpt" type="image"
												value="<%=form.getToDateBlbRpt()%>" maxlength="11" componentID="MRA_GPABILLING_REPORTS_TODATE_BLBRPT"/>
											</div>
										</div>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="ic-col-15">
							&nbsp;
						</div>
					</div>
				<div class="ic-button-container">
					<logic:notEqual name="form" property="specificFlag" value = "Y">	
						<ihtml:nbutton property="btnPrint" componentID="MRA_GPABILLING_REPORTS_BTN_PRINT" accesskey="P">
							<common:message key="mailtracking.mra.gpabilling.report.btn.print" />
						</ihtml:nbutton>
					</logic:notEqual>	
					<logic:equal name="form" property="specificFlag" value = "Y">
						<ihtml:nbutton property="btnPrintTK" componentID="MRA_GPABILLING_REPORTS_BTN_PRINT" accesskey="P">
							<common:message key="mailtracking.mra.gpabilling.report.btn.print" />
						</ihtml:nbutton>
					</logic:equal>	
					<ihtml:nbutton property="btnClose" componentID="MRA_GPABILLING_REPORTS_BTN_CLOSE" accesskey="O">
						<common:message key="mailtracking.mra.gpabilling.report.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
	  
	</div>
	</ihtml:form>
</div>
<!---CONTENT ENDS-->

	</body>
</html:html>

