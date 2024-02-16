<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: GPAReports.jsp
* Date				: 7-March-2007
* Author(s)			: A-2245
 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>




<bean:define id="form" name="GPAReportForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.GPAReportForm" toScope="page" />

	
	
<html:html>
<head>

	


<title>
<common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpareporting.gpareports.title" />
</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/gpareporting/GPAReports_Script.jsp"/>
</head>
<body class="ic-center" style="width:80%;">

	
	



  <!--CONTENT STARTS-->
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<div id="pageDiv" class="iCargoContent ic-masterbg">
	<ihtml:form action="/mailtracking.mra.gpareporting.screenloadgpareport.do" >
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.gpareporting.gpareports.pagetitle" /></span>
			<div class="ic-head-container">
			    <div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input ic-split-50 ic-mandatory">
							<label>
							<common:message key="mailtracking.mra.gpareporting.gpareports.reportid" />
							</label>
							<ihtml:select property="reportId" style="width:230px;" componentID="CMP_MRA_GPAReports_ReportId">
								 <!-- <ihtml:option value="RPTMRA004">List of GPA Reports with Status</ihtml:option>
								  <ihtml:option value="RPTMRA005">GPA Report Summary</ihtml:option> -->
								  <ihtml:option value="RPTMRA006">Exceptions Report Summary</ihtml:option>
								  <ihtml:option value="RPTMRA007">Exceptions Report Details</ihtml:option>
								  <ihtml:option value="RPTMRA008">Exceptions Report by Assignee-Summary</ihtml:option>
								  <ihtml:option value="RPTMRA009">Exceptions Report by Assignee-Details</ihtml:option>
							</ihtml:select>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container" >
				<div class="ic-filter-panel">
				<div class="ic-section ic-border ic-filter-panel">
				<div class="ic-col-100">
					<div id="RPTMRA006" class="ic-row">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-25 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpacode" />
								<ihtml:text property="exceptionSummaryGPACode" componentID="CMP_MRA_GPAReports_GPACode" maxlength="5" />
								 <div class="lovImg">
								
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodeSummLov" width="22" height="22" />
								</div>
							</div>
							<div class="ic-input ic-split-25 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpaname" />
								<ihtml:text property="exceptionSummaryGPAName" componentID="CMP_MRA_GPAReports_GPAName" style="text-transform:none;" maxlength="25" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaNameSummLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-40">
								 <fieldset class="iCargoFieldSet">
									<legend class="iCargoLegend">
										<common:message key="mailtracking.mra.gpareporting.gpareports.repprd" />
									</legend>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.from" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_FromDate"
											property="exceptionSummaryFromDate"
											value="<%=form.getExceptionSummaryFromDate()%>"
											type="image"
											id="frmDate_Summ" />
									</div>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.to" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_ToDate"
											property="exceptionSummaryToDate"
											value="<%=form.getExceptionSummaryToDate()%>"
											type="image"
											id="toDate_Summ" />
									</div>
								</fieldset>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.exceptioncode" />
								<business:sessionBean id="oneTimeValues"
								  moduleName="mailtracking.mra"
								  screenID="mailtracking.mra.gpareporting.gpareport"
								  method="get"
								  attribute="oneTimeVOs" />

							   <ihtml:select name="form" property="exceptionSummaryExceptionCode" style="width:150px;" componentID="CMP_MRA_GPAReports_ExceptionCode" >
								<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="oneTimeValues">
									<bean:define id="oneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
									<logic:equal name="oneTimeValues" property="key" value="mailtracking.mra.gpareporting.exceptioncodes" >
									<bean:define id="ExceptionCodes" name="oneTimeValues" />
									<logic:iterate id="ExceptionCode" name="ExceptionCodes" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<html:option value="<%=ExceptionCode.getFieldValue()%>"><%=ExceptionCode.getFieldDescription()%></html:option>
									</logic:iterate>
									</logic:equal>
									</logic:iterate>
									</logic:present>
							   </ihtml:select>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.country" />
								<ihtml:text property="exceptionSummaryCountryCode" maxlength="3" componentID="CMP_MRA_GPAReports_Country" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="countrySummLov" width="22" height="22" />
								
								</div>
							</div>
						</div>
					</div>

					<div id="RPTMRA007">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-25 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpacode" />
								<ihtml:text property="exceptionDetailsGPACode" componentID="CMP_MRA_GPAReports_GPACode" maxlength="5" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodeDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-25 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpaname" />
								<ihtml:text property="exceptionDetailsGPAName" componentID="CMP_MRA_GPAReports_GPAName" style="text-transform:none;" maxlength="25" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaNameDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-40">
								 <fieldset class="iCargoFieldSet">
									<legend class="iCargoLegend">
										<common:message key="mailtracking.mra.gpareporting.gpareports.repprd" />
									</legend>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.from" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_FromDate"
											property="exceptionDetailsFromDate"
											value="<%=form.getExceptionDetailsFromDate()%>"
											type="image"
											id="frmDate_Dtls" />
									</div>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.to" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_ToDate"
											property="exceptionDetailsToDate"
											value="<%=form.getExceptionDetailsToDate()%>"
											type="image"
											id="toDate_Dtls" />
									</div>
								</fieldset>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.exceptioncode" />
								<ihtml:select name="form" property="exceptionDetailsExceptionCode" style="width:150px;" componentID="CMP_MRA_GPAReports_ExceptionCode" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="ExceptionCodes">
										<logic:iterate id="ExceptionCode" name="ExceptionCodes" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<html:option value="<%=ExceptionCode.getFieldValue()%>"><%=ExceptionCode.getFieldDescription()%></html:option>
										</logic:iterate>
									</logic:present>
							   </ihtml:select>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.country" />
								<ihtml:text property="exceptionDetailsCountryCode" maxlength="3" componentID="CMP_MRA_GPAReports_Country" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="countryDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.assignee" />
								<ihtml:text property="exceptionDetailsAssignee" componentID="CMP_MRA_GPAReports_Assigneenonman" maxlength="10" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="assigneeDtlsLov" width="22" height="22" />
								
								</div>
							</div>
						</div>
					</div>
					<div id="RPTMRA008">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-20 ic-mandatory">
								<label>
								<common:message key="mailtracking.mra.gpareporting.gpareports.assignee" />
								</label>
								<ihtml:text property="assigneeSummaryAssignee" componentID="CMP_MRA_GPAReports_Assignee" maxlength="10" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="assigneeAsgnSummLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-20 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpacode" />
								<ihtml:text property="assigneeSummaryGPACode" componentID="CMP_MRA_GPAReports_GPACode" maxlength="5" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodeAsgnSummLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-20 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpaname" />
								<ihtml:text property="assigneeSummaryGPAName" componentID="CMP_MRA_GPAReports_GPAName" style="text-transform:none;" maxlength="25" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaNameAsgnSummLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-40">
								 <fieldset class="iCargoFieldSet">
									<legend class="iCargoLegend">
										<common:message key="mailtracking.mra.gpareporting.gpareports.repprd" />
									</legend>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.from" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_FromDate"
											property="assigneeSummaryFromDate"
											value="<%=form.getAssigneeSummaryFromDate()%>"
											type="image"
											id="frmDate_AsgnSumm" />
									</div>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.to" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_ToDate"
											property="assigneeSummaryToDate"
											value="<%=form.getAssigneeSummaryToDate()%>"
											type="image"
											id="toDate_AsgnSumm" />
									</div>
								</fieldset>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.exceptioncode" />
								<ihtml:select name="form" property="assigneeSummaryExceptionCode" style="width:150px;" componentID="CMP_MRA_GPAReports_ExceptionCode" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="ExceptionCodes">
										<logic:iterate id="ExceptionCode" name="ExceptionCodes" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<html:option value="<%=ExceptionCode.getFieldValue()%>"><%=ExceptionCode.getFieldDescription()%></html:option>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.country" />
								<ihtml:text property="assigneeSummaryCountryCode" maxlength="3" componentID="CMP_MRA_GPAReports_Country" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="countryAsgnSummLov" width="22" height="22" />
								
								</div>
							</div>
						</div>
					</div>
					<div id="RPTMRA009">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-20 ic-mandatory">
								<label>
								<common:message key="mailtracking.mra.gpareporting.gpareports.assignee" />
								</label>
								<ihtml:text property="assigneeDetailsAssignee" componentID="CMP_MRA_GPAReports_Assignee" maxlength="10" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="assigneeAsgnDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-20 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpacode" />
								<ihtml:text property="assigneeDetailsGPACode" componentID="CMP_MRA_GPAReports_GPACode" maxlength="5" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodeAsgnDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-20 ">
								<common:message key="mailtracking.mra.gpareporting.gpareports.gpaname" />
								<ihtml:text property="assigneeDetailsGPAName" componentID="CMP_MRA_GPAReports_GPAName" style="text-transform:none;" maxlength="25"/>
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaNameAsgnDtlsLov" width="22" height="22" />
								
								</div>
							</div>
							<div class="ic-input ic-split-40">
								 <fieldset class="iCargoFieldSet">
									<legend class="iCargoLegend">
										<common:message key="mailtracking.mra.gpareporting.gpareports.repprd" />
									</legend>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.from" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_FromDate"
											property="assigneeDetailsFromDate"
											value="<%=form.getAssigneeDetailsFromDate()%>"
											type="image"
											id="frmDate_AsgnDtls" />
									</div>
									<div class="ic-input ic-split-50 ">
										<common:message key="mailtracking.mra.gpareporting.gpareports.to" />
										<ibusiness:calendar
											componentID="CMP_MRA_GPAReports_ToDate"
											property="assigneeDetailsToDate"
											value="<%=form.getAssigneeDetailsToDate()%>"
											type="image"
											id="toDate_AsgnDtls" />
									</div>
								</fieldset>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.exceptioncode" />
								<ihtml:select name="form" property="assigneeDetailsExceptionCode" style="width:150px;" componentID="CMP_MRA_GPAReports_ExceptionCode" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="ExceptionCodes">
										<logic:iterate id="ExceptionCode" name="ExceptionCodes" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<html:option value="<%=ExceptionCode.getFieldValue()%>"><%=ExceptionCode.getFieldDescription()%></html:option>
										</logic:iterate>
									</logic:present>
							   </ihtml:select>
							</div>
							<div class="ic-input ic-split-25">
								<common:message key="mailtracking.mra.gpareporting.gpareports.country" />
								<ihtml:text property="assigneeDetailsCountryCode" maxlength="3" componentID="CMP_MRA_GPAReports_Country" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="countryAsgnDtlsLov" width="22" height="22" />
								
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="ic-button-container">
					<ihtml:nbutton property="btnPrint" componentID="CMP_MRA_GPAReports_BTNPRINT" accesskey="P">
			    		<common:message key="mailtracking.mra.gpareporting.gpareports.btn.lbl.print" />
				   </ihtml:nbutton>
			    	   <ihtml:nbutton property="btnClose" componentID="CMP_MRA_GPAReports_BTNCLOSE" accesskey="O" styleClass="btn-inline btn-secondary">
			    		<common:message key="mailtracking.mra.gpareporting.gpareports.btn.lbl.close" />
				   </ihtml:nbutton>
				</div>
            </div>
            </div>
            </div>
		</ihtml:form>
	</div>
<!---CONTENT ENDS-->



	</body>
</html:html>
