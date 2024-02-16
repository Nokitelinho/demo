<%-------------------------------------------------------------------------------
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: CarditEnquiry.jsp
* Date				: 29-May-2006
* Author(s)			: A-1861
 ---------------------------------------------------------------------------------%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">

	<head>
			
	
		<title>
			<common:message bundle="carditenquiryResources" key="mailtracking.defaults.carditenquiry.lbl.pagetitle" />
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/mail/operations/CarditEnquiry_Script.jsp" />

	</head>
	<body>
		
	
	<div id="pageDiv" class="iCargoContent ic-masterbg">
		<ihtml:form action="/mailtracking.defaults.carditenquiry.screenloadcarditenquiry.do" >
			<ihtml:hidden property="duplicateFlightStatus"/>
			<input type=hidden name="mySearchEnabled" />
			<bean:define id="form"
				name="CarditEnquiryForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm"
				toScope="page"
				scope="request"/>
			<business:sessionBean id="oneTimeValues"
						  moduleName="mail.operations"
						  screenID="mailtracking.defaults.carditenquiry"
						  method="get"
						  attribute="oneTimeVOs" />
		  <div class="ic-content-main">
				<div class="iCargoHeadingLabel">
				  <common:message key="mailtracking.defaults.carditenquiry.lbl.carditenquiry" />
			  	</div>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-row">
							<h4><common:message key="mailtracking.defaults.carditenquiry.lbl.search" /></h4>
						</div>
						<div class="ic-row" style="width:99%">
							<div class="ic-row ic-border">
								<div class="ic-input ic-split-25 ic-mandatory">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.flightNumber" /></label>
									<ibusiness:flightnumber
									carrierCodeProperty="carrierCode"
									id="flight"
									flightCodeProperty="flightNumber"
									carriercodevalue="<%=form.getCarrierCode()%>"
									flightcodevalue="<%=form.getFlightNumber()%>"
									carrierCodeMaxlength="3"
									flightCodeMaxlength="5"
									componentID="CMP_MailTracking_Defaults_CarditEnquiry_FltNo"/>
								</div>
								<div class="ic-input ic-split-25 ic-mandatory">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.flightdate" /></label>
									<ibusiness:calendar property="flightDate" type="image" id="flightDate"
											value="<%=form.getFlightDate()%>"
											componentID="CMP_MailTracking_Defaults_CarditEnquiry_FlightDate"/>
								</div>
								<div class="ic-input ic-split-25 ic-mandatory">
									<label><common:message key="mailtracking.defaults.carditenquiry.tooltip.departureport" /></label>
									<ihtml:text property="departurePort" componentID="CMP_MailTracking_Defaults_CarditEnquiry_DeparturePort" maxlength="4"/>
									<div class="lovImg"><img id="portLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="port"/></div>	
								</div>
								<div class="ic-input ic-split-25 ">
									<label><common:message key="mailtracking.defaults.carditenquiry.tooltip.flightType" /></label>
									<logic:present name="form" property="flightType">
										<logic:notEmpty name="form" property="flightType">
											<ihtml:select property="flightType" componentID="MailTracking_Defaults_CarditEnquiry_FlightType" >
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.flighttype">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
										<logic:empty name="form" property="flightType">
											<ihtml:select property="flightType" componentID="MailTracking_Defaults_CarditEnquiry_FlightType" value="C">
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.flighttype">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:present>
									<logic:notPresent name="form" property="flightType">
										<ihtml:select property="flightType" componentID="MailTracking_Defaults_CarditEnquiry_FlightType" value="C">
										  <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.flighttype">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:notPresent>
								</div>	
							</div>
							<div class="ic-row ic-border">
								<div class="ic-input ic-split-14">
									<logic:present name="form" property="searchMode">
										<logic:notEmpty name="form" property="searchMode">
											<ihtml:select property="searchMode" componentID="MailTracking_Defaults_CarditEnquiry_CarditType" >
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.searchmode">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
										<logic:empty name="form" property="searchMode">
											<ihtml:select property="searchMode" componentID="MailTracking_Defaults_CarditEnquiry_CarditType" value="C">
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.searchmode">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:present>
									<logic:notPresent name="form" property="searchMode">
										<ihtml:select property="searchMode" componentID="MailTracking_Defaults_CarditEnquiry_CarditType" value="C">
										  <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.searchmode">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:notPresent>	
								</div>
								<div class="ic-input ic-split-10">
									<logic:notPresent name="CarditEnquiryForm" property="notAccepted">
										<ihtml:checkbox property="notAccepted" componentID="MailTracking_Defaults_CarditEnquiry_NotAccepted" />
									</logic:notPresent>
									<logic:present name="CarditEnquiryForm" property="notAccepted">
										<ihtml:checkbox property="notAccepted" value="Y" componentID="MailTracking_Defaults_CarditEnquiry_NotAccepted" />
									</logic:present>
									<common:message key="mailtracking.defaults.carditenquiry.lbl.notAcceptedCheck" />
								</div>
								<div class="ic-input ic-split-32">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.unsendResditEvent" /><label>
									<logic:present name="form" property="resdit">
										<logic:notEmpty name="form" property="resdit">
											<ihtml:select property="resdit" componentID="MailTracking_Defaults_CarditEnquiry_UnsendResditEvent" style="width:130px" >
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.resditevent">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
											   <html:option value="">&nbsp;</html:option>
											</ihtml:select>
										</logic:notEmpty>
										<logic:empty name="form" property="resdit">
											<ihtml:select property="resdit" componentID="MailTracking_Defaults_CarditEnquiry_UnsendResditEvent" value="" style="width:130px">
											  <html:option value="">&nbsp;</html:option>
											  <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.resditevent">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:present>
									<logic:notPresent name="form" property="resdit">
										<ihtml:select property="resdit" componentID="MailTracking_Defaults_CarditEnquiry_UnsendResditEvent" value="" style="width:130px">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										  <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.resditevent">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
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
									</logic:notPresent>
								</div>
								<div class="ic-input ic-split-19">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentNo" /></label>
									<ihtml:text property="consignmentDocument" componentID="CMP_MailTracking_Defaults_CarditEnquiry_ConsignmentNo" maxlength="13"/>
								</div>
								<div class="ic-input ic-split-17">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.fromdate" /></label>
									<ibusiness:calendar property="fromDate"
										type="image"
										id="fromDate"
										value="<%=form.getFromDate()%>"
										componentID="CMP_MailTracking_Defaults_CarditEnquiry_FromDate"
										/>
								</div>
								<div class="ic-input ic-split-17">
									<label><common:message key="mailtracking.defaults.carditenquiry.lbl.todate" /></label>
									<ibusiness:calendar property="toDate"
														type="image"
														id="toDate"
														value="<%=form.getToDate()%>"
														componentID="CMP_MailTracking_Defaults_CarditEnquiry_ToDate"
														/>
								</div>
							</div>
							<div class="ic-row">
								<h4><common:message key="mailtracking.defaults.carditenquiry.lbl.mailtag" /></h4>
							</div>
							<jsp:include page="CarditEnquiry_MailTag.jsp" /> 
							<div class="ic-button-container">
								<ihtml:nbutton property="btList"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btList" accesskey="L">
									<common:message key="mailtracking.defaults.carditenquiry.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClear" accesskey="C">
									<common:message key="mailtracking.defaults.carditenquiry.btn.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<div>
							<h4>
								<common:message key="mailtracking.defaults.carditenquiry.lbl.mailbagdetails" />
							</h4>
						</div>
					</div>	
						<jsp:include page="CarditEnquiry_Table.jsp" /> 
				 	<jsp:include page="CarditEnquiry_DespatchDetails.jsp" /> 
				  <jsp:include page="CarditEnquiry_ConsignmentTable.jsp" /> 
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container ic-pad-5">
						<ihtml:nbutton property="btSendResdit"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btSendResdit" accesskey="R">
							<common:message key="mailtracking.defaults.carditenquiry.btn.sendResdit" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClose" accesskey="O">
							<common:message key="mailtracking.defaults.carditenquiry.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>		
			</div>		
	</ihtml:form>
	</div>

				
		
	</body>
</html:html>
