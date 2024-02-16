<%--
 /************************************************************************
 
	* Project	 			:  iCargo
	* Module Code & Name	:  MRA - Defaults
	* File Name				:  MailExceptionReports.jsp
	* Date					:  15-Sep-2010
	* Author(s)				:  A-2414

  *************************************************************************/
 --%>

 <%@ page language="java" %>

 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="org.apache.struts.Globals"%>


	
	
<html:html>
<head>
	
	
		
	
<title><common:message key="mailtracking.mra.defaults.report.title" bundle="mailexceptionreports" scope="request"/> </title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/MailExceptionReports_Script.jsp"/>
</head>

 <body class="ic-center" style="width:85%;">
	
	
	
<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.mailexceptionreports"
	method="get" attribute="oneTimeVOs" />   
 


<div class="iCargoContent ic-masterbg" style="overflow:auto;height:350px;">
<ihtml:form action="mailtracking.mra.defaults.mailexceptionreports.screenload.do">
	
	   <div class="ic-content-main bg-white">
			<span class="ic-page-title"><common:message key="mailtracking.mra.defaults.report.heading" scope="request"/></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-section">
							<div class="ic-row">
								<fieldset class="ic-field-set">
								<legend><common:message key="mailtracking.mra.defaults.report.heading" scope="request"/></legend>
						
									    <div class="ic-input ic-mandatory ic-split-25">
											<label><common:message  key="mailtracking.mra.defaults.report.fromdate" scope="request"/></label>
						
											<ibusiness:calendar id="fromDate"
											property="fromDate"
											componentID="CMP_MRA_DEFAULTS_REPORT_FROMDATE"
											type="image"
											maxlength="11"
											/>
										</div>
										<div class="ic-input ic-mandatory ic-split-25">
											<label><common:message  key="mailtracking.mra.defaults.report.todate" scope="request"/></label>							
											<ibusiness:calendar id="toDate"
											property="toDate"
											componentID="CMP_MRA_DEFAULTS_REPORT_TODATE"
											type="image"
											maxlength="11"
											/>
										</div>
										<div class="ic-input ic-split-50">
											<label><common:message  key="mailtracking.mra.defaults.report.reporttype" scope="request"/></label>
								
											<ihtml:select componentID="CMP_MRA_DEFAULTS_REPORT_RPTID" property="reportType" style="width:305px;">
												<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>
												<logic:present name="KEY_ONETIMES"> 									
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES"> 
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/> 
														<logic:equal name="parameterCode" value="mail.mra.defaults.exceptionreports">
															<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<ihtml:option value="<%=String.valueOf(fieldValue)%>"></ihtml:option>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
												</logic:present>													
											</ihtml:select>
										</div>
									
								</fieldset>
			
			
								<div class="ic-button-container">	
										<ihtml:nbutton property="btnGenerate" componentID="CMP_MRA_DEFAULTS_REPORT_GENERATEBTN" accesskey="G" >
											  <common:message key="mailtracking.mra.defaults.report.btn.generate" />
										</ihtml:nbutton>

										<ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_REPORT_CLOSEBTN" accesskey="O">
											<common:message key="mailtracking.mra.defaults.report.btn.close" />
										</ihtml:nbutton>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
</ihtml:form>
	</div>
		

	</body>
</html:html>
