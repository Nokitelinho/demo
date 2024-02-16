<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MaintainCCA.jsp
* Date					:  14-June-2006,14-July-08,29-AUG-2008,2012
* Author(s)				:  A-2391,A-3447,A-3227,A-5125
*************************************************************************/
 --%>



 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"%>





<html:html>
 <head>






 	<title><common:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/defaults/MaintainCCA_Script.jsp"/>
 </head>

 <body>




<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	<bean:define id="form"
		 name="mraMaintainCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"
		 toScope="page" />


	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.maintaincca"
	 	  method="get"
	  attribute="OneTimeVOs" />

	<business:sessionBean id="ccaFilterVO"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="maintainCCAFilterVO" />

	<business:sessionBean id="cCAdetailsVO"
	   	moduleName="mailtracking.mra.gpabilling"
	   	screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="cCAdetailsVO" />

	<logic:present name="cCAdetailsVO">
        <bean:define id="cCAdetailsVO" name="cCAdetailsVO" toScope="page" />
	</logic:present>

	<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.maintaincca"
		  method="get"
		  attribute="weightRoundingVO" />

	<business:sessionBean id="KEY_VOLUMEROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.maintaincca"
		  method="get"
		  attribute="volumeRoundingVO" />

    <business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.maintaincca"
	method="get" attribute="systemparametres" />

   <business:sessionBean id="KEY_CRAPARAMETERVOS"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintaincca"
		method="get"
		attribute="cRAParameterVOs" />

 	<div  class="iCargoContent ic-masterbg" style="overflow:auto;width:100%;height:100%;">
 		<ihtml:form action="/mailtracking.mra.defaults.maintaincca.screenload.do">

<ihtml:hidden property="comboFlag"/>
<ihtml:hidden property="showpopUP"/>
<ihtml:hidden property="selectedRow"/>
<ihtml:hidden property="popupon"/>
<ihtml:hidden property="count"/>
<ihtml:hidden property="closeFlag"/>
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="currentDialogId" />
<ihtml:hidden property="dsnPopupFlag" />
<ihtml:hidden property="createCCAFlg" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="usrCCANumFlg" />
<ihtml:hidden property="autoratedFlag" />
<ihtml:hidden property="rateAuditedFlag" />
<ihtml:hidden property="showDsnPopUp" />
<ihtml:hidden property="disableFlag" />
<ihtml:hidden property="dsnDate" />
<ihtml:hidden property="privilegeFlag" />
<ihtml:hidden property="ccaPresent" />
<ihtml:hidden property="overrideRounding" value="N" />
<ihtml:hidden property="rate" />
<ihtml:hidden property="billingStatus" />
<ihtml:hidden  property="revisedChargeGrossWeignt"  />
<%boolean roundingReq=false;%>


<common:xgroup>
			<common:xsubgroup id="RESTRICT_AA_SPECIFIC">
				<ihtml:hidden property="reasonCodeRestrictionFlag" value="N"/>
			</common:xsubgroup>
    	</common:xgroup>
 <common:xgroup>
		 <common:xsubgroup id="AA_SPECIFIC">
			<ihtml:hidden property="reasonCodeRestrictionFlag" value="Y"/>

				</common:xsubgroup>
 </common:xgroup >



<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
					<logic:notEqual name="parameterValue" value="N">
							<%form.setOverrideRounding("Y");%>
							<ihtml:hidden name="mraMaintainCCAForm"  property="roundingValue" id="round" value="<%=String.valueOf(parameterValue).toUpperCase() %>" />
					</logic:notEqual>

			<logic:equal name="parameterValue" value="N">
						<ihtml:hidden name="mraMaintainCCAForm"  property="roundingValue" id="round" value="N" />
			</logic:equal>

			</logic:equal>
		</logic:iterate>
	</logic:present>
	<div class="ic-content-main">
		<span class="ic-page-title"><common:message key="mailtracking.mra.defaults.maintaincca.lbl.heading" /></span>
					<div class="ic-head-container">
						<div class="ic-filter-panel">
							<div class="ic-input-container">
								<fieldset class="ic-field-set" height="100%">
									<div class="ic-row">
										<div class="ic-input ic-split-20">
											<label><common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcano" /></label>
											<logic:present name="ccaFilterVO" property="ccaReferenceNumber">
											<%System.out.println("inside logic:present name=ccaFilterVO");%>
											<bean:define id="ccaReferenceNumber" name="ccaFilterVO" property="ccaReferenceNumber"/>
											<ihtml:text property="ccaNum" value="<%=(String)ccaReferenceNumber%>" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CCANUM" maxlength="10"/>
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="mcanolov" height="22" width="22" alt="" disabled ="true"/>
											</div>
											</logic:present>
											<logic:notPresent name="ccaFilterVO" property="ccaReferenceNumber">
											<ihtml:text property="ccaNum" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CCANUM" maxlength="10"/>
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="mcanolov" height="22" width="22" alt="" />
											</div>
											</logic:notPresent>
										</div>
										<div class="ic-input ic-split-30" >
										   <label>
											<common:message key="mailtracking.mra.defaults.maintaincca.lbl.dsn" />
										   </label>
											<logic:present name="ccaFilterVO" property="dsnNumber">
											<%System.out.println("inside logic:present name=ccaFilterVO  dsn number");%>
											<bean:define id="dsnNumber" name="ccaFilterVO" property="dsnNumber"/>
											<ihtml:text property="dsnNumber" value="<%=(String)dsnNumber%>" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DSN" style="width:200px"  maxlength="29"/>
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt="" disabled="true" />
											</div>
											</logic:present>
											<logic:notPresent name="ccaFilterVO" property="dsnNumber">
											<ihtml:text property="dsnNumber" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DSN"  maxlength="29" style="width:200px"/>
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt="" />
											</div>
											</logic:notPresent>
										</div>
										<div class="ic-input ic-split-15" >
										  <label>
										  <common:message key="mailtracking.mra.defaults.maintaincca.lbl.condocno" />
										  </label>
											<logic:present name="ccaFilterVO" property="consignmentDocNum">
											   <bean:define id="consignmentDocNum" name="ccaFilterVO" property="consignmentDocNum"/>
											<ihtml:text property="conDocNo" value="<%=(String)consignmentDocNum%>"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CONDOCNO"  maxlength="15"/>
											</logic:present>
											<logic:notPresent name="ccaFilterVO" property="consignmentDocNum">
											<ihtml:text property="conDocNo" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CONDOCNO"  maxlength="15"/>
											</logic:notPresent>
										</div>
										<div class="ic-input ic-split-15" >
										<!-- Removed label tag by A-8236 as part of ICRD-250603 issue1-->
											<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcastatus" />

										<!--	<% String ccaStatusForDisp = ""; %>
										<logic:present name="ccaFilterVO" property="ccaStatus">
											<bean:define id="ccaStatus" name="ccaFilterVO" property="ccaStatus"/>

												<% ccaStatusForDisp = (String)ccaStatus; %>
												<%System.out.println(ccaStatusForDisp);%>
										</logic:present>
										<logic:notPresent name="ccaFilterVO" property="ccaStatus">

												<% ccaStatusForDisp = "N"; %>
										</logic:notPresent>
										<%System.out.println("-------------------------"+ccaStatusForDisp);%>
										   <ihtml:select

												styleClass="iCargoMediumComboBox"
												componentID="CMP_MAILTRACKING_MRA_DEFAULTS_STATUS"
												property="ccaStatus"
												value="<%=ccaStatusForDisp%>" disabled="true"
											tabindex="30">
											<logic:present name="OneTimeValues">
												<logic:iterate id="oneTimeValue" name="OneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue"
														property="key" />
													<logic:equal name="parameterCode"
															value="mra.defaults.ccastatus">
														<bean:define id="parameterValues" name="oneTimeValue"
															property="value" />
														<logic:iterate id="parameterValue"
															name="parameterValues"
															type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue"
																property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue"
																	property="fieldValue" />
																<bean:define id="fieldDescription"
																	name="parameterValue" property="fieldDescription" />
																<ihtml:option
																	value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																	<%=String.valueOf(fieldDescription)%>
																</ihtml:option>
															</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
											</logic:present>
											</ihtml:select>-->
											<logic:present name="cCAdetailsVO" property="ccaStatus">
											<bean:define id="cCAdetailsVO" name="cCAdetailsVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"/>
											  <%String ccAStatus=cCAdetailsVO.getCcaStatus();%>
											  <logic:present name="OneTimeValues">
												<logic:iterate id="oneTimeValue" name="OneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue"
														property="key" />
													<logic:equal name="parameterCode"
															value="mra.defaults.ccastatus">
														<bean:define id="parameterValues" name="oneTimeValue"
															property="value" />
														<logic:iterate id="parameterValue"
															name="parameterValues"
															type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue"
																property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue"
																	property="fieldValue" />
																<bean:define id="fieldDescription"
																	name="parameterValue" property="fieldDescription" />
																	<%if(ccAStatus.equals(String.valueOf(fieldValue).toUpperCase())){%>
																	<%String ccaStatus=String.valueOf(fieldDescription);%>
																	<%=ccaStatus%>

																		<ihtml:hidden property ="ccaStatus" value = "<%=ccAStatus%>"/>
																    <%}%>



															</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
											 </logic:present>
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="ccaStatus">
											&nbsp;
											<ihtml:hidden property ="ccaStatus" value = 'N'/>
										    </logic:notPresent>
											</div>
											<div class="ic-button-container">
												<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_LSTBTN" accesskey="L" >
												<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.list" />
												</ihtml:nbutton>

												<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLEARBTN" accesskey="C" >
												<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.clear" />
												</ihtml:nbutton>
											</div>
										</div>
								</fieldset>


								<fieldset class="ic-field-set ic-inline-label" height="100%">
								<legend><common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcadetails" /></legend>
								<div class="ic-row ic-label-45">
								<div class="ic-input ic-split-15" >
								<label>
								<common:message key="mailtracking.mra.defaults.maintaincca.lbl.issuedate" />
								</label>
								<logic:present name="cCAdetailsVO" property="issueDate">
									<bean:define id="issueDate" name="cCAdetailsVO" property="issueDate" />
							  			<common:display property="issueDate" name="cCAdetailsVO" />
								</logic:present>
								<logic:notPresent name="cCAdetailsVO" property="issueDate">
							  		<common:display property="issueDate" name="form" />
								</logic:notPresent>
								</div>
							<div class="ic-input ic-split-15" >
							<label>
								<common:message key="mailtracking.mra.defaults.maintaincca.lbl.billingperiodfrom" />
						    </label>
								<logic:present name="cCAdetailsVO" property="billingPeriodFrom">
									<bean:define id="billingPeriodFrom" name="cCAdetailsVO" property="billingPeriodFrom" />
							  			<common:display property="billingPeriodFrom" name="cCAdetailsVO" />
								</logic:present>
								<logic:notPresent name="cCAdetailsVO" property="billingPeriodFrom">
									<common:display property="bilfrmdate" name="form"  />
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-15" >
							  <label>
								<common:message key="mailtracking.mra.defaults.maintaincca.lbl.billingperiodto" />
							  </label>
								<logic:present name="cCAdetailsVO" property="billingPeriodTo">
									<bean:define id="billingPeriodTo" name="cCAdetailsVO" property="billingPeriodTo" />
							  			<common:display property="billingPeriodTo" name="cCAdetailsVO" />
								</logic:present>
								<logic:notPresent name="cCAdetailsVO" property="billingPeriodTo">
									<common:display property="biltodate" name="form" />
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-20" >
							  <label>
								<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcatype" />
							 </label>
					 			<% String ccaTypeValue = ""; %>
								<logic:present name="cCAdetailsVO" property="ccaType">
								<bean:define id="ccaType" name="cCAdetailsVO" property="ccaType" toScope="page"/>
									<% ccaTypeValue = (String)ccaType; %>
								</logic:present>
								<logic:notPresent name="cCAdetailsVO" property="ccaType">
										<% ccaTypeValue = "A"; %>
									<common:xgroup >
										<common:xsubgroup id="AA_ASIANA_SPECIFIC">
											<% ccaTypeValue = "I"; %>
										</common:xsubgroup>
									</common:xgroup>
								</logic:notPresent>
								<ihtml:select
									styleClass="iCargoMediumComboBox"
									componentID="CMP_MAILTRACKING_MRA_DEFAULTS_TYPE"
									property="ccaType" onchange="onBlurCCA()" 
									value="<%=ccaTypeValue%>"
									tabindex="30" disabled="false">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="OneTimeValues">
										<logic:iterate id="oneTimeValue" name="OneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue"
												property="key" />
											<logic:equal name="parameterCode"
												value="mra.defaults.ccatype">
												<bean:define id="parameterValues" name="oneTimeValue"
													property="value" />
												<logic:iterate id="parameterValue"
													name="parameterValues"
													type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue"
														property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue"
															property="fieldValue" />
														<bean:define id="fieldDescription"
															name="parameterValue" property="fieldDescription" />
														<ihtml:option
															value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
														</ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-20" >
							         <label>
											<common:message key="mailtracking.mra.defaults.maintaincca.lbl.billingstatus" />
									</label>
							               <logic:present name="cCAdetailsVO" property="billingStatus">
													<bean:define id="cCAdetailsVO" name="cCAdetailsVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"/>
													  <%String billingStatus=cCAdetailsVO.getBillingStatus();%>
													  <logic:present name="OneTimeValues">
														<logic:iterate id="oneTimeValue" name="OneTimeValues">
															<bean:define id="parameterCode" name="oneTimeValue"
																property="key" />
															<logic:equal name="parameterCode"
																	value="mailtracking.mra.gpabilling.gpabillingstatus">
																<bean:define id="parameterValues" name="oneTimeValue"
																	property="value" />
																<logic:iterate id="parameterValue"
																	name="parameterValues"
																	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue"
																		property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue"
																			property="fieldValue" />
																		<bean:define id="fieldDescription"
																			name="parameterValue" property="fieldDescription" />
																			<%if(billingStatus.equals(String.valueOf(fieldValue).toUpperCase())){%>
																			<%String billingStatusToDisp=String.valueOf(fieldDescription);%>
																			<%=billingStatusToDisp%>

																				<ihtml:hidden property ="billingStatus" value = "<%=billingStatus%>"/>
																			<%}%>



															</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
											 </logic:present>
											</logic:present>
										<logic:notPresent name="cCAdetailsVO" property="billingStatus">
											&nbsp;
								        </logic:notPresent>
								</div>
								<!--Added by a-7540-->
					<div class="ic-input ic-split-15">
							<logic:present name="cCAdetailsVO" property="autoMca">
								<logic:equal name="cCAdetailsVO" property="autoMca" value="Y">
								<input type="checkbox" name="isAutoMca" property="isAutoMca" checked value="Y" />
								</logic:equal>
								<logic:notEqual name="cCAdetailsVO" property="autoMca" value="Y">
								<input type="checkbox" name="isAutoMca" property="isAutoMca" value="N"/>
								</logic:notEqual>
								</logic:present>
								<logic:notPresent name="cCAdetailsVO" property="autoMca">
								<input type="checkbox" name="isAutoMca" property="isAutoMca" value="N"/>
								</logic:notPresent>
						<label>
							<common:message  key="mailtracking.mra.defaults.maintaincca.lbl.autoMca" />
					   </label>
						</div>
						</div>
					</fieldset><!--CCA DTLS LEGEND TABLE  ENDS-->

				   		   		<fieldset class="ic-field-set ic-inline-label" height="100%">
								<legend><common:message key="mailtracking.mra.defaults.maintaincca.lbl.dsndetails" /></legend>
					   			<div class="ic-row ic-label-45">
									<div class="ic-input ic-split-25">
									 <label>
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.origin" />
									</label>
							   				<logic:present name="cCAdetailsVO" property="origin">
												<bean:define id="origin" name="cCAdetailsVO" property="origin" />
										  			<common:display property="origin" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="origin">
							   			  		<common:display property="origin" name="form" />
											</logic:notPresent>
							   			</div>

									<div class="ic-input ic-split-25">
									  <label>
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.destn" />
							   		 </label>
											<logic:present name="cCAdetailsVO" property="destination">
												<bean:define id="destination" name="cCAdetailsVO" property="destination" />
										  			<common:display property="destination" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="destination">
							   			  		<common:display property="destination" name="form" />
											</logic:notPresent>
							   			</div>


							   				<div class="ic-input ic-split-25">
											<label>
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.category" />
							   			   </label>
											<logic:present name="cCAdetailsVO" property="category">
												<bean:define id="category" name="cCAdetailsVO" property="category" />
										  			<common:display property="category" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="category">
							   			  		<common:display property="category" name="form" />
											</logic:notPresent>
							   			</div>
							   				<div class="ic-input ic-split-25">
											<label>
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.subclass" />
							   			   </label>
											<logic:present name="cCAdetailsVO" property="subClass">
												<bean:define id="subClass" name="cCAdetailsVO" property="subClass" />
										  			<common:display property="subClass" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="subClass">
							   			  		<common:display property="subclass" name="form" />
											</logic:notPresent>
							   			</div>
						   			</div>
				   			</fieldset>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
				<fieldset class="ic-field-set">
					<legend><common:message key="mailtracking.mra.defaults.maintaincca.lbl.revision" /></legend>
					<div class="ic-col-50">
					<div class="ic-row">

								<table   class="fixed-header-table" >
				   					<thead>
					   					<tr class="iCargoTableHeadingLeft">
					   						<td class="iCargoTableHeaderLabel"   width="38%">
												&nbsp;
					   						</td>
					   						<td class="iCargoTableHeaderLabel"  width="24%">
												<common:message key="mailtracking.mra.defaults.maintaincca.lbl.revised/corrected" />
					   						</td>
					   						<td class="iCargoTableHeaderLabel"  width="24%">
												<common:message key="mailtracking.mra.defaults.maintaincca.lbl.original/incorrect" />
											</td>
					   					</tr>
				   					</thead>
									<tbody>
					   					<tr  height="100%">
											<td width="100%" colspan="3">
												 <div id="ajaxDiv">
												<table height="50%" style="width:100%;>
													<tr height= "1" class="iCargoFieldSet" >
													<td  colspan="2">
															&nbsp;<common:message key="mailtracking.mra.defaults.maintaincca.lbl.details" />
													</td>
													</tr>
														<tr>
															<td width="40%">
																<table >
																	<thead >
																		<tr class="iCargoTableHeadingLeft" >
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px"  >
																			<div align="left" >
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.currency" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.gpacode" />
																			    </div>
																			</td>
																		</tr>
																		<!--Added by A-7929 as part of ICRD-132548 starts...  -->
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.rate" />
																			    </div>
																			</td>
																		</tr>
																		<!--Added by A-7929 as part of ICRD-132548 ends...  -->
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.weight" />
																				</div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mailchgs" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.surchgs" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px">
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.tax" />
																				</div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px">
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.netvalue" />
																				</div>
																			</td>
																		</tr>
																	</thead>
																</table>
															</td>
															<td width="50%">
									   							<table style="width:100%">
																	<tbody>
																	<!-- Updated column height below, by A-8236 for ICRD-252669 -->
																		<tr style="height:27px">
																			<td width="50%" style="height:27px">
																			<logic:notPresent name="cCAdetailsVO" property="revContCurCode">
										  										<ihtml:text property="revCurCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVCURR" maxlength="3"/>
																				<div class="lovImgTbl valignT">
																				<img name="currLov" id="currLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																			    </div>
																			  </logic:notPresent>
																			<logic:present name="cCAdetailsVO" property="revContCurCode">
																			<ihtml:text property="revCurCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVCURR" maxlength="3"/>
																			<div class="lovImgTbl valignT">
																			<img name="currLov" id="currLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																			</div>
																			  </logic:present>
																			  </td>
																			  <td width="50%" class="iCargoTableDataTd" style="height:27px">
																			<logic:notPresent name="cCAdetailsVO" property="contCurCode">
										  												<bean:write property="curCode" name="form" />
																			  </logic:notPresent>
																			<logic:present name="cCAdetailsVO" property="contCurCode">
																						<bean:define id="contCurCode" name="cCAdetailsVO" property="contCurCode" />
										  												<bean:write property="contCurCode" name="cCAdetailsVO" />
																			  </logic:present>
																			</td>
																			</tr>
																			<tr style="height:27px">
																			<td  width="50%" style="height:27px">
																			<logic:present name="cCAdetailsVO" property="revGpaCode">
																					<ihtml:text property="revGpaCode"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVGPACODE" maxlength="5"/>
																					<div class="lovImgTbl valignT">
																					<img name="gpacodelov" id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																				    </div>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revGpaCode">
										  											<ihtml:text property="revGpaCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVGPACODE" maxlength="5"/>
																					<div class="lovImgTbl valignT">
																					<img name="gpacodelov" id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																				    </div>
																				</logic:notPresent>
																				</td>
																			<td width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="gpaCode">
																					<bean:define id="gpaCode" name="cCAdetailsVO" property="gpaCode" />
										  												<bean:write property="gpaCode" name="cCAdetailsVO"/>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="gpaCode">
										  											<bean:write property="gpaCode" name="form" />
																				</logic:notPresent>
																			</td>
																		</tr>
																		<!-- Added by A-7929 as part of ICRD-132548 starts..  -->
																		<tr style="height:27px">
																		 <td width="50%" style="height:27px">
																			<logic:notPresent name="cCAdetailsVO" property="revisedRate">
                                                                             <ihtml:text property="revisedRate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right" value="0"
/>
																			 </logic:notPresent>

																			<logic:present name="cCAdetailsVO" property="revisedRate">
																																									<bean:define id="revisedRate" name="cCAdetailsVO" property="revisedRate" toScope="page" type="java.lang.Double"/>

				<logic:equal name="revisedRate" value="0.0">
				<ihtml:text  id="revisedRate"  property="revisedRate" value="0"
				componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right" />
				</logic:equal>
				<logic:notEqual name="revisedRate" value="0.0">
				<ihtml:text id="revisedRate"  property="revisedRate" value="<%=String.valueOf(revisedRate)%>"
				componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right; width:60px" />
				</logic:notEqual>
																			  </logic:present>
																			</td>
																			<td  width="50%" class="iCargoTableDataTd" style="height:27px">
																			<!-- Modified by A-8399 as part of ICRD-305527 -->
																				<logic:present name="cCAdetailsVO" property="rate">
																					<bean:define id="rate" name="cCAdetailsVO" property="rate" />
																					 <common:write name="cCAdetailsVO" property="rate"  />

																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="rate">
																				<common:write name="form" property="rate" />

																				</logic:notPresent>
																			</td>
																		</tr>
																		<!-- Added by A-7929 as part of ICRD-132548 ends..  -->
																		<tr style="height:27px">
																		<td width="50%" style="height:27px">
																			<logic:notPresent name="cCAdetailsVO" property="revGrossWeight">

																				<logic:present name="KEY_WEIGHTROUNDINGVO">

																					<bean:define id="revGrossWeightID" name="KEY_WEIGHTROUNDINGVO" />
																						<% request.setAttribute("grossWeight",revGrossWeightID); %>
																							<ibusiness:unitdef id="statedWt" unitTxtName="revGrossWeight" label="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT"  unitReq = "false" dataName="grossWeight"
																							 title="Revised Weight"
																							unitValue="0.0" style="text-align:right"
																							indexId="index" styleId="grossweight" />
																				</logic:present>
																			  </logic:notPresent>

																			<logic:present name="cCAdetailsVO" property="revGrossWeight">
																						<bean:define id="revGrossWeightID" name="cCAdetailsVO" property="revGrossWeight" toScope="page"/>

																						<logic:present name="KEY_WEIGHTROUNDINGVO">

																							<bean:define id="revGrossWeight" name="KEY_WEIGHTROUNDINGVO" />
																								<% request.setAttribute("grossWeight",revGrossWeight); %>

																									<!-- <ibusiness:unitdef id="statedWt" unitTxtName="revGrossWeight" label="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT"  unitReq = "false" dataName="grossWeight"
																									 title="Revised Weight"
																									unitValue="<%=revGrossWeightID.toString()%>" style="text-align:right"
																									indexId="index" styleId="grossweight" />
																									indexId="index" styleId="grossweight" readonly="true"/>-->

																									<ihtml:text property="revGrossWeight" name="revGrossWeight" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT" value="<%=revGrossWeightID.toString()%>"
																									/>



																						</logic:present>

																			  </logic:present>




																			</td>
																			<td  width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="grossWeight">
																					<bean:define id="grossWeight" name="cCAdetailsVO" property="grossWeight" />
																					 <common:write name="cCAdetailsVO" property="grossWeight" unitFormatting="true" />

																					 <common:write name="cCAdetailsVO" property="displayWgtUnit" />
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="grossWeight">
																				<common:write name="form" property="grossWeight" unitFormatting="true" />

																				</logic:notPresent>
																			</td>

																		</tr>
																		<tr style="height:27px">
																			<td width="50%" style="height:27px"><!--A-6991-->
																				<logic:present name="cCAdetailsVO" property="revChgGrossWeight">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
																				 <ibusiness:moneyEntry name="cCAdetailsVO" id="revChgGrossWe" moneyproperty="revChgGrossWeight"  property="revChgGrossWeight" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" overrideRounding = "true" />
																				  	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
																				 <ibusiness:moneyEntry name="cCAdetailsVO" id="revChgGrossWe" moneyproperty="revChgGrossWeight"  property="revChgGrossWeight" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" />
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revChgGrossWeight">
 																				 <ibusiness:moneyEntry  id="revChgGrossWe"  property="revChgGrossWeight" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" />
																				</logic:notPresent>
																			</td>
																			<td width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="chgGrossWeight">
																				  <logic:equal name="form" property="overrideRounding" value = "Y">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="chgGrossWeight" property="chgGrossWeight" overrideRounding="true"/>
																				</logic:equal>
																		<logic:notEqual name="form" property="overrideRounding" value = "Y">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="chgGrossWeight" property="chgGrossWeight"/>

																					</logic:notEqual>

																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="chgGrossWeight">
																				0.0
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																			<td width="50%" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="otherRevChgGrossWgt">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
																				       <ibusiness:moneyEntry name="cCAdetailsVO" id="otherRevChgGrossWg" moneyproperty="otherRevChgGrossWgt" formatMoney="true"  property="otherRevChgGrossWgt"    componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE"  overrideRounding = "true"/>
																				</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
																				       <ibusiness:moneyEntry name="cCAdetailsVO" id="otherRevChgGrossWg" moneyproperty="otherRevChgGrossWgt" formatMoney="true"  property="otherRevChgGrossWgt"    componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE" />
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="otherRevChgGrossWgt">
 																				 <ibusiness:moneyEntry  id="otherRevChgGrossWg"  property="otherRevChgGrossWgt" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE" />
																				</logic:notPresent>
																			</td>
																			<td width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="otherChgGrossWgt">
																														  <logic:equal name="form" property="overrideRounding" value = "Y">

																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="otherChgGrossWgt" property="otherChgGrossWgt" overrideRounding="true"/>

										  	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="otherChgGrossWgt" property="otherChgGrossWgt"/>

													</logic:notEqual>

																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="otherChgGrossWgt">
																				0.0
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																			<td  width="50%" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="revTax">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
																				    <ibusiness:moneyEntry name="cCAdetailsVO" id="revTax" moneyproperty="revTax"  property="revTax" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE"  overrideRounding = "true"/>
																					</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														   <ibusiness:moneyEntry name="cCAdetailsVO" id="revTax" moneyproperty="revTax"  property="revTax" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" />
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revTax">
 																				 <ibusiness:moneyEntry  id="revTax"  property="revTax" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" />
																				</logic:notPresent>
																			</td>
																			<td  width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="tax">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="tax" property="tax" overrideRounding="true"/>
																					</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="tax" property="tax"/>

													</logic:notEqual>
																							</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="tax">
																					0
																							</logic:notPresent>

																			</td>
																		</tr>

																		<tr style="height:27px">
																			<td  width="50%" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="differenceAmount">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
																				    <ibusiness:moneyEntry name="cCAdetailsVO" id="differenceAmount" moneyproperty="differenceAmount"  property="differenceAmount" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true" overrideRounding = "true"/>
																					</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
<ibusiness:moneyEntry name="cCAdetailsVO" id="differenceAmount" moneyproperty="differenceAmount"  property="differenceAmount" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true" />
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="differenceAmount">
 																				 <ibusiness:moneyEntry  id="differenceAmount"  property="differenceAmount" formatMoney="false" value="0.00"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_NETVALUE" disabled="true"/>
																				</logic:notPresent>
																			</td>
																			<td width="50%" style="height:27px">
																			<!--Commented for ICRD-141841-->
																			<!--	<logic:present name="cCAdetailsVO" property="netAmount">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="netAmount" property="netAmount" />
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="netAmount">
																				0.0
																				</logic:notPresent>-->
																			</td>
																		</tr>
																		<!--
																		</table>-->



																	</tbody>
																	</table>
																	</td>
																	</tr>
																</table>

													</div>
													</td>
														</tr>
															</tbody>
															</table>

					</div>
					<div class="ic-row">

					<div class="ic-input ">
										<label>
											<common:message key="mailtracking.mra.defaults.maintaincca.lbl.remarks" />
										</label>

											<logic:present name="cCAdetailsVO" property="ccaRemark">
												<bean:define id="ccaRemark" name="cCAdetailsVO" property="ccaRemark" type="String"/>
												<ihtml:textarea property="remarks" value="<%=ccaRemark%>" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REMARKS"  maxlength="200" cols="100" rows="5">
												</ihtml:textarea>
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="ccaRemark">
												<ihtml:textarea property="remarks" value="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REMARKS"  maxlength="200" cols="100" rows="5" >
												</ihtml:textarea>
											</logic:notPresent>

									</div>
					</div>
					</div>
					<div class="ic-col-50" height="100%">
					<div class="ic-row">
							<fieldset class="ic-field-set" height="100%">
												<legend >
													<common:message key="mailtracking.mra.defaults.maintaincca.lbl.reasons" />
												</legend>
												<div class="ic-row">
													<div class="table_Temp_Container" id="setdiv" style="height:100%;width:100%">
													<!--<div class="ic-input-container" id="setdiv" style="height:720px;overflow-y : auto;"> -->
													<div class="ic-input-container" id="setdiv" style="height:420px;width:100%;overflow-y : auto;">
														<table  class="fixed-header-table ic-pad-3">
															<tbody id="setTableBody">
																	<logic:present name="KEY_CRAPARAMETERVOS">
																	<% int i=0;%>
																		<logic:iterate id = "craParameterVO" name="KEY_CRAPARAMETERVOS" scope="page" type="com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO">
																		<bean:define id="index"  value="<%=String.valueOf(i)%>" />
																			<logic:present name="craParameterVO" property="parameter">
											                                   <bean:define id="reasonCode" name="craParameterVO" property="parameter" />

	<tr>


																				<td class="iCargoTableDataTd">
													<div class="ic-input ">


																						<logic:equal name="craParameterVO" property="parameterCode"  value="<%=reasonCode.toString()%>" >
																							<ihtml:hidden property="reasonCheck" value="<%=reasonCode.toString()%>"/>
																								<input type="checkbox"  class="reasonsearchcheckbox" id="reason" name="form" property="reasonCheck" checked="true" onclick="setReasonCheckValue(this,'<%=index%>','<%=reasonCode.toString()%>');"
																								value="<%=reasonCode.toString()%>" style="width:50px;" />
																						 	</logic:equal >
																							<logic:notEqual name="craParameterVO" property="parameterCode"  value="<%=reasonCode.toString()%>">
																							<ihtml:hidden property="reasonCheck" value=""/>
																								<input type="checkbox" class="reasonsearchcheckbox" id="reason<%=index%>" name="form" property="reasonCheck" onclick="setReasonCheckValue(this,'<%=index%>','<%=reasonCode.toString()%>');"
																								value="" style="width:50px;" />
																							</logic:notEqual >


																						<logic:present name="craParameterVO" property="parameterCode">
																								<bean:write property="parameterCode" name="craParameterVO" />
																						</logic:present>

																						&nbsp;<b>:</b><logic:present name="craParameterVO" property="parameterDescription">
																								<bean:write property="parameterDescription" name="craParameterVO"  />
																						</logic:present>

																					</div>

																				</td>









																			</tr>
																			</logic:present>
																			<logic:notPresent name="craParameterVO" property="parameter">
											<tr>


																				<td class="iCargoTableDataTd">
																					<div class="ic-input " >
																					<bean:define id="reasonCode" name="craParameterVO" property="parameterCode" />
																							<ihtml:hidden property="reasonCheck" value=""/>
																								<input type="checkbox" class="reasonsearchcheckbox" id="reason<%=index%>" name="form" property="reasonCheck" onclick="setReasonCheckValue(this,'<%=index%>','<%=reasonCode.toString()%>');"
																								value="" style="width:50px;" />

																						<logic:present name="craParameterVO" property="parameterCode">
																								<bean:write property="parameterCode" name="craParameterVO" />
																						</logic:present>

																						&nbsp;<b>:</b><logic:present name="craParameterVO" property="parameterDescription">
																								<bean:write property="parameterDescription" name="craParameterVO" />
																						</logic:present>

																					</div>

																				</td>






																			</tr>
										</logic:notPresent>
										<% ++i;%>
																		</logic:iterate>
																	</logic:present>
															</tbody>
														</table>
													</div>
													<!--<div class="ic-input ">

																<logic:present name="cCAdetailsVO" property="currChangeInd">
																	<logic:equal name="cCAdetailsVO" property="currChangeInd" value="Y">
																		<input type="checkbox" name="reason1" property="reason1" checked value="Y" />
																	</logic:equal>
																	<logic:notEqual name="cCAdetailsVO" property="currChangeInd" value="Y">
																		<input type="checkbox"  name="reason1" property="reason1" value="N"/>
																	</logic:notEqual>
																</logic:present>
																<logic:notPresent name="cCAdetailsVO" property="currChangeInd">
																		<input type="checkbox"  name="reason1" property="reason1" value="N"/>
																</logic:notPresent>


															<common:message key="mailtracking.mra.defaults.maintaincca.lbl.changeincurr" />
														</div>
															</div>
													<div class="ic-row">
													<div class="ic-input ">
																<logic:present name="cCAdetailsVO" property="gpaChangeInd">
																	<logic:equal name="cCAdetailsVO" property="gpaChangeInd" value="Y">
																		<input type="checkbox" name="reason2" property="reason2" checked value="Y" />
																	</logic:equal>
																	<logic:notEqual name="cCAdetailsVO" property="gpaChangeInd" value="Y">
																		<input type="checkbox" name="reason2" property="reason2" value="N"/>
																	</logic:notEqual>
																</logic:present>
																<logic:notPresent name="cCAdetailsVO" property="gpaChangeInd">
																		<input type="checkbox" name="reason2" property="reason2" value="N"/>
																</logic:notPresent>

															<common:message key="mailtracking.mra.defaults.maintaincca.lbl.changegenpost" />
														</div>
															</div>
														<div class="ic-row">
													<div class="ic-input ">
																<logic:present name="cCAdetailsVO" property="grossWeightChangeInd">
																	<logic:equal name="cCAdetailsVO" property="grossWeightChangeInd" value="Y">
																		<input type="checkbox" name="reason3" property="reason3" checked value="Y" />
																	</logic:equal>
																	<logic:notEqual name="cCAdetailsVO" property="grossWeightChangeInd" value="Y">
																		<input type="checkbox" name="reason3" property="reason3" value="N"/>
																	</logic:notEqual>
																</logic:present>
																<logic:notPresent name="cCAdetailsVO" property="grossWeightChangeInd">
																		<input type="checkbox" name="reason3" property="reason3" value="N"/>
																</logic:notPresent>

															<common:message key="mailtracking.mra.defaults.maintaincca.lbl.changeingroswt" />
														</div>
															</div>
															<div class="ic-row">
													<div class="ic-input ">
																<logic:present name="cCAdetailsVO" property="weightChargeChangeInd">
																	<logic:equal name="cCAdetailsVO" property="weightChargeChangeInd" value="Y">
																		<input type="checkbox" name="reason4" property="reason4" checked value="Y" />
																	</logic:equal>
																	<logic:notEqual name="cCAdetailsVO" property="weightChargeChangeInd" value="Y">
																		<input type="checkbox" name="reason4" property="reason4" value="N"/>
																	</logic:notEqual>
																</logic:present>
																<logic:notPresent name="cCAdetailsVO" property="weightChargeChangeInd">
																		<input type="checkbox" name="reason4" property="reason4" value="N"/>
																</logic:notPresent>

															<common:message key="mailtracking.mra.defaults.maintaincca.lbl.changeinwtchg" />
													</div>-->
															</div>
											</fieldset>
										</div>
				</fieldset>
				</div>
				<div class="ic-row">
				</div>
				</div>

				<div class="ic-foot-container">
					<div class="ic-row">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnSurcharge" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_SURCHGBTN" accesskey="S" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.surchg" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btnPrint" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_PRINTBTN" accesskey="P" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.print" />
							</ihtml:nbutton>
							<%if("Y".equals(form.getPrivilegeFlag())){%>

							<ihtml:nbutton property="btnDelete" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DELETEBTN" accesskey="E" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.delete" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btnAccept" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ACCEPTBTN" accesskey="T" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.accept" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btnReject" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REJECTBTN" accesskey="J" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.reject" />
							</ihtml:nbutton>

							<%}%>

							<ihtml:nbutton property="btnSave" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_SAVEBTN" accesskey="S" >

							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.save" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLOSEBTN" accesskey="O" >
							<common:message key="mailtracking.mra.defaults.maintaincca.lbl.button.close" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
	</ihtml:form>
	</div>

	</body>
</html:html>
