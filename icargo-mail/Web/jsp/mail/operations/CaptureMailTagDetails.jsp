<%--/***********************************************************************
* Project	     	 	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : CaptureMailTagDetails.jsp
* Date                 	 : 08-APR-2009
* Author(s)              : A-2391
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailbagVO" %>


<html:html>
	<head>


<%@ include file="/jsp/includes/customcss.jsp" %>  <!-- 	Added by A-8164 for ICRD-259765 -->



		<title><common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.title.capturemailtagdetails"/></title>
		<meta name="decorator" content="popup_panel">
		<common:include type="script" src="/js/mail/operations/CaptureMailTagDetails_Script.jsp" />
	</head>
	<body>

		<business:sessionBean
			id="oneTimeCatSession"
			moduleName="mail.operations"
			screenID="mailtracking.defaults.mailacceptance"
			method="get" attribute="oneTimeCat" />
		<business:sessionBean
			id="KEY_MAILDETAILVO"
			moduleName="mail.operations"
			screenID="mailtracking.defaults.mailacceptance"
			method="get"
			attribute="currentMailDetail" />

		<business:sessionBean
			id="KEY_SELECTED_MAILDETAIL_VOS"
			moduleName="mail.operations"
			screenID="mailtracking.defaults.mailacceptance"
			method="get"
			attribute="selectedMailDetailsVOs" />

		 <bean:define id="MailAcceptanceForm"
		 	name="MailAcceptanceForm"
		    	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   			toScope="page" scope="request"/>

   	         <bean:define
			id="mailDetailsVO"
			name="KEY_MAILDETAILVO"
			type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO"
			toScope="page" />
		<business:sessionBean
			id="oneTimeRISession"
			moduleName="mail.operations"
			screenID="mailtracking.defaults.mailacceptance"
			method="get"
			attribute="oneTimeRSN" />
		<business:sessionBean
			id="oneTimeHNISession"
			moduleName="mail.operations"
			screenID="mailtracking.defaults.mailacceptance"
			method="get"
			attribute="oneTimeHNI" />
		  	<div class="iCargoPopUpContent" id="div_main" style="height:100%" >
			<ihtml:form action="/mailtracking.defaults.mailacceptance.screenloadcapturemailtagdetails.do" styleClass="ic-main-form">
			<ihtml:hidden property="lastPageNum"/>
			<ihtml:hidden property="currentPageNum"/>
			<ihtml:hidden property="totalRecords"/>
			<ihtml:hidden property="displayPage"/>
			<ihtml:hidden property="popupCloseFlag"/>
			<ihtml:hidden property="modify"/>
			<ihtml:hidden property="density"/>
			<div class="ic-content-main">
			<div class="ic-head-container">
					<div class="ic-row">
						<div class="iCargoHeadingLabel">MailTag Details</div>
					</div>
					<div class="ic-row">
					<div class="ic-button-container">
						<logic:present name="KEY_SELECTED_MAILDETAIL_VOS">
							<common:popuppaginationtag
								pageURL="javascript:selectNextMailTagDetails('lastPageNum','displayPage');"
								linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								displayPage="<%=MailAcceptanceForm.getDisplayPage()%>"
								totalRecords="<%=MailAcceptanceForm.getTotalRecords()%>" />
						</logic:present>
					</div>
					</div>
				</div>
				<div class="ic-main-container">
				<div class="ic-row ic-border">
					<div class="ic-row">
							<logic:present name="mailDetailsVO" property="operationalFlag">
								<bean:define id="operationFlag" name="mailDetailsVO"
									property="operationalFlag" toScope="request" />
								<ihtml:hidden property="mailOpFlag"
									value="<%=((String) operationFlag)%>" />
							</logic:present>
							<logic:notPresent name="mailDetailsVO" property="operationalFlag">
								<ihtml:hidden property="mailOpFlag" value="N" />
							</logic:notPresent>
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.origin" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="ooe">
									<ihtml:text property="mailOOE"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN"
										value="" maxlength="6" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO" property="ooe">
									<bean:define id="ooe" name="mailDetailsVO" property="ooe"
										toScope="page" />
									<ihtml:text property="mailOOE" value="<%=(String) ooe%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN"
										maxlength="6" />
								</logic:present>
								<img name="mailOOELov" id="mailOOELov"
									src="<%=request.getContextPath()%>/images/lov.gif" width="16"
									height="16">
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.destination" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="doe">
									<ihtml:text property="mailDOE"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN"
										value="" maxlength="6" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO" property="doe">
									<bean:define id="doe" name="mailDetailsVO" property="doe"
										toScope="page" />
									<ihtml:text property="mailDOE" value="<%=(String) doe%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN"
										maxlength="6" />
								</logic:present>
								<img name="mailDOELov" id="mailDOELov"
									src="<%=request.getContextPath()%>/images/lov.gif" width="16"
									height="16">
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.cat" />
								</label>
								<%
									String category = "";
								%>
								<logic:present name="mailDetailsVO" property="mailCategoryCode">
									<bean:define id="mailCategoryCode" name="mailDetailsVO"
										property="mailCategoryCode" toScope="page" />
									<%
										category = String.valueOf(mailCategoryCode);
									%>
								</logic:present>
								<ihtml:select property="mailCat"
									componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAT"
									value="<%=category%>" style="width:35px">
									<bean:define id="oneTimeCatSess" name="oneTimeCatSession"
										toScope="page" />
									<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess">
										<bean:define id="fieldValue" name="oneTimeCatVO"
											property="fieldValue" toScope="page" />
										<html:option value="<%=(String) fieldValue%>">
											<bean:write name="oneTimeCatVO" property="fieldValue" />
										</html:option>
									</logic:iterate>
								</ihtml:select>
							</div>
						</div>
									<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.sc" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="mailSubclass">
									<ihtml:text property="mailSC"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC"
										value="" maxlength="2" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO" property="mailSubclass">
									<bean:define id="mailSubclass" name="mailDetailsVO"
										property="mailSubclass" toScope="page" />
									<ihtml:text property="mailSC" value="<%=(String) mailSubclass%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC"
										maxlength="2" />
								</logic:present>
								<img name="mailSCLov" id="mailSCLov"
									src="<%=request.getContextPath()%>/images/lov.gif" width="16"
									height="16">
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.yr" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="year">
									<ihtml:text property="mailYr"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR"
										value="" maxlength="1" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO" property="year">
									<bean:define id="year" name="mailDetailsVO" property="year"
										toScope="page" />
									<ihtml:text property="mailYr" value="<%=year.toString()%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR"
										maxlength="1" />
								</logic:present>
							</div>
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.dsn" />
								</label>
								<logic:notPresent name="mailDetailsVO"
									property="despatchSerialNumber">
									<ihtml:text property="mailDSN"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN"
										value="" maxlength="4" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO"
									property="despatchSerialNumber">
									<bean:define id="despatchSerialNumber" name="mailDetailsVO"
										property="despatchSerialNumber" toScope="page" />
									<ihtml:text property="mailDSN"
										value="<%=(String) despatchSerialNumber%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN"
										maxlength="4" />
								</logic:present>
							</div>
						</div>
								<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.acceptmail.lbl.rsn" />
								</label>

								<logic:notPresent name="mailDetailsVO"
									property="receptacleSerialNumber">
									<ihtml:text property="mailRSN"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN"
										value="" maxlength="3" />
								</logic:notPresent>
								<logic:present name="mailDetailsVO"
									property="receptacleSerialNumber">
									<bean:define id="rsn" name="mailDetailsVO"
										property="receptacleSerialNumber" toScope="page" />
									<ihtml:text property="mailRSN" value="<%=(String) rsn%>"
										componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN"
										maxlength="3" />
								</logic:present>

							</div>
							<div class="ic-input  ic-split-30">
								<label> <common:message key="mailtracking.defaults.acceptmail.lbl.hni" />
								</label>
								<%
													String hni = "" ;
												%>
												<logic:present name="mailDetailsVO" property="highestNumberedReceptacle">
													<bean:define id="highestNumberedReceptacle" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
													<%
														hni = String.valueOf(highestNumberedReceptacle);
													%>
												</logic:present>
											<ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_HNI" value="<%=hni%>" style="width:35px">
														<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
															<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
															  <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
																 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
															</logic:iterate>
											</ihtml:select>
							</div>
							<div class="ic-input  ic-split-30">
								<label><common:message key="mailtracking.defaults.acceptmail.lbl.ri" />
								</label>
								<%
													String ri = "" ;
												%>
												<logic:present name="mailDetailsVO" property="registeredOrInsuredIndicator">
													<bean:define id="registeredOrInsuredIndicator" name="mailDetailsVO" property="registeredOrInsuredIndicator" toScope="page"/>
													<%
														ri = String.valueOf(registeredOrInsuredIndicator);
													%>
												</logic:present>
											<ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_RI" value="<%=ri%>" style="width:35px">
												<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
													<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
													  <bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
														 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
													</logic:iterate>
											</ihtml:select>
							</div>
						</div>
								<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-35">
								<label><common:message key="mailtracking.defaults.acceptmail.lbl.wt" />
								</label>
			        <logic:present name="mailDetailsVO" property="weight">
																	<bean:define id="revGrossWeightID" name="mailDetailsVO" property="weight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
																	 
																
																 <% request.setAttribute("mailWt",revGrossWeightID); %>
															<ibusiness:unitdef id="mailWt" unitTxtName="mailWt"  style="text-align:right" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT" title="Shipment Gross Display Weight"  unitValueStyle="iCargoTextFieldSmall"
																	dataName="" unitTypePassed="WGT"	unitValue="0"  />
																		
														
																       <ibusiness:unitCombo  unitTxtName="mailWt" style="background :'<%=color%>';text-align:right"  label="" title="Revised Gross Weight"
																	   unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>"
																	   dataName="mailWt"
																	   indexId="index"
																	   styleId="mailWt"
                                        unitValueStyle="iCargoEditableTextFieldRowColor1"			unitListName="weightUnit"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitListValue="<%=(String)revGrossWeightID.getDisplayUnit()%>"  unitTypePassed="MWT"/>					
							</logic:present><!--Added by A-8353 for ICRD-274933-->
											</div>
							<div class="ic-input  ic-split-35">
								<label> <common:message key="mailtracking.defaults.acceptmail.lbl.volume" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="volume">
													<ibusiness:unitdef id="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" unitTxtName="mailVolume" label=""  unitReq = "false"  
														 unitValueStyle="iCargoTextFieldSmall" title="Stated Volume" dataName=""
														 unitValue="0" unitTypePassed="WGT" style="text-align:right"  />
									

											<%--<ihtml:text property="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" value=""   maxlength="6"  />
											--%>
					
										</logic:notPresent>
										<logic:present name="mailDetailsVO" property="volume">
												<bean:define id="volume" name="mailDetailsVO" property="volume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure" />
														<% request.setAttribute("sampleStdVol",volume); %>
													<ibusiness:unitdef id="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" unitTxtName="mailVolume" label=""  unitReq = "false" dataName="sampleStdVol"
														 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
														unitValue="<%=String.valueOf(volume.getDisplayValue())%>" style="background :'<%=color%>';text-align:right"
														indexId="index" styleId="stdVolume"  />
												</logic:present>

											<%--<ihtml:text property="mailVolume" value="<%=volume.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  maxlength="6" />
											--%>
										
							</div>
							<div class="ic-input  ic-split-35">
								<label> <common:message key="mailtracking.defaults.acceptmail.lbl.mailcarrier" />
								</label>
								 <logic:notPresent name="mailDetailsVO" property="transferFromCarrier">
											<ihtml:text property="mailCarrier" indexId="1" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" value="" maxlength="2" style="width:40px"/>
										   </logic:notPresent>
										   <logic:present name="mailDetailsVO" property="transferFromCarrier">
											<bean:define id="transferCarrier" name="mailDetailsVO" property="transferFromCarrier" toScope="page"/>
											<ihtml:text property="mailCarrier"  value="<%=(String)transferCarrier%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" maxlength="2" style="width:40px"/>
										   </logic:present>
										   <img name="mailCarrierLov" id="mailCarrierLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">

							</div>
						</div>
								<div class="ic-row">
							<div class="ic-input  ic-split-45">
								<label> <common:message key="mailtracking.defaults.acceptmail.lbl.sealno" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="sealNumber">
													<ihtml:text property="sealNo" style="width:120px;" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNO" value=""  maxlength="15"  />
												</logic:notPresent>
												<logic:present name="mailDetailsVO" property="sealNumber">
													<bean:define id="seal" name="mailDetailsVO" property="sealNumber" toScope="page" />
													<ihtml:text property="sealNo" value="<%=(String)seal%>" style="width:120px;" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNO"  maxlength="15"  />
												</logic:present>
							</div>
							<!--Added as a part of ICRD-197419 by A-7540-->
							<div class="ic-input  ic-split-55">
							      <label> <common:message key="mailtracking.defaults.acceptmail.lbl.mailbagremarks" />
								  </label>
								
								  <logic:notPresent name="mailDetailsVO" property="mailRemarks">
		                            <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" />
	                              </logic:notPresent>
	                             <logic:present name="mailDetailsVO" property="mailRemarks">
	                                 <bean:define id="mailRemarks" name="mailDetailsVO" property="mailRemarks" toScope="page" />
		                              <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="<%=(String)mailRemarks%>" />
	                             </logic:present>
						</div>
							</div>	 
								<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-45">
								<label> <common:message key="mailtracking.defaults.acceptmail.lbl.scandate" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="scannedDate">
												<ibusiness:calendar property="mailScanDate" id="mailScanDate"  type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE" value="" />
											</logic:notPresent>
											<logic:present name="mailDetailsVO" property="scannedDate">
											<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
											<%String scanDate=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
												<ibusiness:calendar property="mailScanDate"   id="mailScanDate" value="<%=(String)scanDate%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE"/>
											</logic:present>
											 <logic:notPresent name="mailDetailsVO" property="scannedDate">
												<ibusiness:releasetimer property="mailScanTime"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value=""/>
											   </logic:notPresent>
											   <logic:present name="mailDetailsVO" property="scannedDate">
											      <bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
											      <%String scanTime=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),"HH:mm");%>
											      <ibusiness:releasetimer property="mailScanTime"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=(String)scanTime%>"/>
										   </logic:present>
							</div>
								<div class="ic-input ic-split-55">
								<label><common:message key="mailtracking.defaults.acceptmail.lbl.bellycardit" />
								</label>
								<logic:notPresent name="mailDetailsVO" property="bellyCartId">
												<ihtml:text property="mailCartId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT" value=""   maxlength="20" style="width:220px" />
										  </logic:notPresent>
										  <logic:present name="mailDetailsVO" property="bellyCartId">
												<bean:define id="carditId" name="mailDetailsVO" property="bellyCartId" toScope="page" />
												<ihtml:text property="mailCartId" value="<%=(String)carditId%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT"  maxlength="20" style="width:220px"/>
										  </logic:present>
												</div>
												</div>
				</div>
				</div>
				<div class="ic-foot-container">
				<div class="ic-button-container">
				<ihtml:nbutton property="btnNew"  componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_NEW"  >
													<common:message  key="mailtracking.defaults.acceptmail.btn.new"/>
												</ihtml:nbutton>
												<ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_OK" >
													<common:message key="mailtracking.defaults.acceptmail.btn.ok" />
					      							</ihtml:nbutton>
													 <ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CLEAR">
													<common:message key="mailtracking.defaults.mailacceptance.btn.clear" />
												  </ihtml:nbutton>
												 <ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_CLOSE">
													<common:message key="mailtracking.defaults.mailacceptance.btn.close" />
												  </ihtml:nbutton>
				</div>
				</div>
			</div>
			</ihtml:form>
			</div>
	</body>

</html:html>