<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PostalAdministrationMaster.jsp
* Date					:  14-June-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO"%>

 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailEventVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "java.util.Calendar" %>
 <%@ page import="java.util.HashMap"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>

	 <html:html>

 <head>
	<title><common:message bundle="postalAdminResources" key="mailtracking.defaults.pamaster.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/PostalAdministrationMaster_Script.jsp"/>
	<bean:define id="screenload" value="screenload"/>
 </head>

 <body>
	<bean:define id="form"
		 name="PostalAdministrationForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"
		 toScope="page" />

	<business:sessionBean id="paVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="paVO"/>
	 <business:sessionBean id="postalAdministrationDetailsVOs"
	 		 moduleName="mail.operations"
	 		 screenID="mailtracking.defaults.masters.postaladministration"
	 		 method="get"
	 	 attribute="postalAdministrationDetailsVOs"/>



	<business:sessionBean id="ONETIME_CATEGORY"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="oneTimeCategory"/>
	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
	 	  method="get"
	  attribute="OneTimeValues" />


 	<div class="iCargoContent ic-masterbg">
 		<ihtml:form action="/mailtracking.defaults.pamaster.screenload.do">
 			<ihtml:hidden property="opFlag" value="<%=form.getOpFlag()%>" />
 			<ihtml:hidden property="recievedArray"/>
 			<ihtml:hidden property="upliftedArray"/>
 			<ihtml:hidden property="assignedArray"/>
 			<ihtml:hidden property="returnedArray"/>
 			<ihtml:hidden property="handedOverArray"/>
 			<ihtml:hidden property="pendingArray"/>
 			<ihtml:hidden property="deliveredArray"/>
			<ihtml:hidden property="readyForDeliveryArray"/>
			<ihtml:hidden property="transportationCompletedArray"/>
			<ihtml:hidden property="arrivedArray"/>			
 			<ihtml:hidden property="handoverRcvdArray"/>
 			<ihtml:hidden property="loadedArray"/>
 			<ihtml:hidden property="onlineHandoverArray"/>
 			<ihtml:hidden property="screenStatusFlag"/>
 			<ihtml:hidden property="statusActiveOrInactive"/>
			<ihtml:hidden property="msgSelected"/>
			<ihtml:hidden property="parameterType" name="form"/>
			<input type="hidden" name="currentDialogId" />
            <input type="hidden" name="currentDialogOption" />
			<div class="ic-content-main">
               
					<div class="ic-head-container">
						<%--<div class="ic-row ">
							<label class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.pamaster.lbl.search" /></label>
						</div>--%>
						<div class="ic-filter-panel" >						
							<div class="ic-row ">							
								<div class=" ic-col-80">
									<div class="ic-input-container">
										<div class="ic-input ic-mandatory ic-split-100" >
											<label>
											  <common:message key="mailtracking.defaults.pamaster.lbl.gpacode" />
											</label>
											<ihtml:text property="paCode" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_GPACODE" maxlength="5"/>
											 <div class="lovImg"><img name="paLOV" id="paLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="lov1"/></div>
										</div>
									</div>
								</div>
								<div class=" ic-col-20 ">
									<div class="ic-button-container">
										<ihtml:nbutton property="btList" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_LIST" accesskey="L">
										<common:message key="mailtracking.defaults.pamaster.btn.list" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btClear" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_CLEAR" accesskey="C">
										<common:message key="mailtracking.defaults.pamaster.btn.clear" />
										</ihtml:nbutton>					
									</div>
								</div>
							</div>							
						</div>
					</div>
					<div class="ic-main-container">	
					 
						<div class="ic-section ic-round-border ">
							<div class="ic-row">
								<h4><common:message key="mailtracking.defaults.pamaster.lbl.details" /></label></h4>
							</div>
								<div class="ic-section ic-round-border ic-pad-2">
									<div class="ic-row ic-border" style="width:99%">
									  <div class="ic-input-container ic-label-20">
										<div class="ic-row">
											<div class="ic-input ic-mandatory ic-split-25">
												<label>
													<common:message key="mailtracking.defaults.pamaster.lbl.name" />
												</label>
												<logic:present name="paVO">
												<ihtml:text property="paName" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PANAM" maxlength="100" style="text-transform : uppercase;"/>
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="paName" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PANAM" maxlength="100" style="text-transform : uppercase;"/>
												</logic:notPresent>
											</div>
											<div class="ic-input ic-mandatory ic-split-20">
												<label class="ic-label-45">
													<common:message key="mailtracking.defaults.pamaster.lbl.country" />
												</label>				
													<logic:present name="paVO">
													<ihtml:text property="countryCode" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_COUNTRY" maxlength="3"/>
													</logic:present>
													<logic:notPresent name="paVO">
													<ihtml:text property="countryCode" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_COUNTRY" maxlength="3"/>
													</logic:notPresent>
													<logic:notEqual name="form" property="screenStatusFlag" value="<%=screenload%>">
													
													</logic:notEqual>
													 <div class="lovImg"><img name="countryLOV" id="countryLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="lov2"/></div>
											</div>
											<div class="ic-input ic-split-20">
												<label class="ic-label-50">
												   <common:message key="mailtracking.defaults.pamaster.lbl.debInvCode" />
												</label>		
												<logic:present name="paVO">
												<ihtml:text property="debInvCode" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DEBINVCODE" maxlength="3"/>
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="debInvCode" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DEBINVCODE" maxlength="3"/>
												</logic:notPresent>
											</div>
										</div>	
										<div class="ic-row">		
											<div class="ic-input ic-split-25">
												<label class="ic-label-50">
													<common:message key="mailtracking.defaults.pamaster.lbl.status" />
												</label>
												<logic:present name="paVO">
												<ihtml:text property="status" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS" maxlength="100" readonly="true"/>
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="status" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATUS" maxlength="100"/>
												</logic:notPresent>
											</div>
										    <div class="ic-input ic-split-20">
											   <label class="ic-label-70">
													<common:message key="mailtracking.defaults.pamaster.lbl.accnum"/>
												</label>		
												<logic:present name="paVO">
												<ihtml:text name="paVO" property="accNum" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_ACCNO" maxlength="15" />
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="accNum" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_ACCNO" maxlength="15"/>
												</logic:notPresent>
											</div>
											<div class="ic-input ic-split-20">
												<label class="ic-label-50">
													<common:message key="mailtracking.defaults.pamaster.lbl.vatnum" />
												</label>
												<logic:present name="paVO">
												<ihtml:text name="paVO" property="vatNumber" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VATNUM" maxlength="20" />
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="vatNumber" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_VATNUM" maxlength="20"/>
												</logic:notPresent>
											</div>
										    <div class="ic-input ic-split-10">
												
													<common:message key="mailtracking.defaults.pamaster.lbl.emailinvoice" />
												<logic:present name="paVO" property="autoEmailReqd">
												<bean:define id="emailReqd" name="paVO" property="autoEmailReqd"/>
												<logic:equal name="paVO" property="autoEmailReqd" value="Y">
												<%String emailReq=emailReqd.toString();%>
												<ihtml:checkbox name="paVO" property="autoEmailReqd" componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_EMAILREQD" value="<%=emailReq%>"/>
												</logic:equal>
												<logic:notEqual name="paVO" property="autoEmailReqd" value="Y">
												<ihtml:checkbox name="paVO" property="autoEmailReqd" componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_EMAILREQD"/>
												</logic:notEqual>
												</logic:present>
												<logic:notPresent name="paVO" property="autoEmailReqd">
												<ihtml:checkbox property="autoEmailReqd" componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_EMAILREQD" />
												</logic:notPresent>
											</div>
											<div class="ic-input ic-split-10">
												<common:xgroup>
												<common:xsubgroup id="CUSTOMER_OTHERS">
												<ihtml:checkbox property="gibCustomerFlag" componentID="CMP_MAILTRACKING_DEFAULTS_PAMASTER_GIBCUSTOMERFLAG" />
												<label class="ic-label-70"><common:message key="mailtracking.defaults.pamaster.lbl.gibCustomerFlag"/></label>
												</common:xsubgroup>
												</common:xgroup >	
											</div>
											<div class="ic-input ic-split-12">
												<label class="ic-label-60"><common:message key="mailtracking.defaults.pamaster.lbl.dueindays" /></label>
												<logic:present name="paVO">
												<ihtml:text name="paVO" property="dueInDays" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DUEINDAYS" maxlength="3" />
												</logic:present>
												<logic:notPresent name="paVO">
												<ihtml:text property="dueInDays" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DUEINDAYS" maxlength="3"/>
												</logic:notPresent>
											</div>
										</div>
									</div>
								</div>
								</div>
								<div class="ic-row">
									<jsp:include page="PostalAdmin_BillingAndSettlementInfo.jsp" />		
								</div>
							  <div class="ic-row">
							  <!--Added by A-6991 for ICRD-211662 Starts-->

							 <!--Added by A-6991 for ICRD-211662 
								  <div class="ic-input ic-split-14">
									<logic:present name="paVO">
									<ihtml:checkbox name="paVO" property="partialResdit" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISPOSTALRESDIT"/>
									</logic:present>
									<logic:notPresent name="paVO">
									<ihtml:checkbox property="partialResdit" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISPOSTALRESDIT"/>
									</logic:notPresent>
											
										<common:message key="mailtracking.defaults.pamaster.lbl.ispostalresdit" />
									
								  </div>Ends-->
								 <!--  <div class="ic-input ic-split-16">
									<logic:present name="paVO">
									<ihtml:checkbox name="paVO" property="msgEventLocationNeeded" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISMSGEVTLOCNEEDED"/>
									</logic:present>
									<logic:notPresent name="paVO">
									<ihtml:checkbox property="msgEventLocationNeeded" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISMSGEVTLOCNEEDED"/>
									</logic:notPresent>
										
										<common:message key="mailtracking.defaults.pamaster.lbl.ismsgevtlocneeded" />
									
								  </div>-->
							<!--  <div class="ic-input ic-split-16">
							  <label>
							  <common:message key="mailtracking.defaults.pamaster.lbl.resditversion" />
							  </label>
								<logic:present name="paVO">
								<ihtml:select name="paVO" property="residtversion" 
								componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_RESDITVER" style="width:100px">

								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>

								<logic:present name="OneTimeValues">

								<logic:iterate id="oneTimeValue" name="OneTimeValues">

								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.defaults.postaladministration.resditversion">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

								<ihtml:option value="<%=String.valueOf(fieldValue) %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
								</logic:present>

								</ihtml:select>
								</logic:present>
								<logic:notPresent name="paVO">
								<ihtml:select  property="residtversion" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_RESDITVER" style="width:100px">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>

								<logic:present name="OneTimeValues">

								<logic:iterate id="oneTimeValue" name="OneTimeValues">

								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.defaults.postaladministration.resditversion">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

								<ihtml:option value="<%=String.valueOf(fieldValue) %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
								</logic:present>
								</ihtml:select>

								</logic:notPresent>
							  </div>-->
							 <!--  <div class="ic-input ic-split-20"> added by A-7371 for ICRD-212135
								<common:message key="mailtracking.defaults.pamaster.lbl.resditTriggeringPeriod" />
												
                                                <logic:present name="paVO">
												<bean:define id="resditTriggerPeriod" name="paVO" property="resditTriggerPeriod"/>
												<logic:equal name="resditTriggerPeriod" value="0">
												<ihtml:text id="resditTriggerPeriod" name="paVO" property="resditTriggerPeriod" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_RESDITTRIGGERINGPERIOD"  maxlength="6"/>
												</logic:equal>
												<logic:notEqual name="resditTriggerPeriod"  value="0">
												<ihtml:text id="resditTriggerPeriod" name="paVO" property="resditTriggerPeriod" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_RESDITTRIGGERINGPERIOD"  maxlength="6"/>
												</logic:notEqual>
												</logic:present>
												
												<logic:notPresent name="paVO">
												<ihtml:text id="resditTriggerPeriod" name="paVO" property="resditTriggerPeriod" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_RESDITTRIGGERINGPERIOD" maxlength="6" />
								                </logic:notPresent>
							  </div>
							  <div class="ic-input ic-split-20">
								 
									<common:message key="mailtracking.defaults.pamaster.lbl.messaging" />
								 				
									<logic:present name="paVO">
									<ihtml:radio name="paVO" property="messagingEnabled" onclick="selectRadio();" value="Y" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY"/>
									<common:message key="mailtracking.defaults.pamaster.lbl.yes" />
									<ihtml:radio name="paVO" property="messagingEnabled" onclick="selectRadio();" value="N" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEN"/>
									<common:message key="mailtracking.defaults.pamaster.lbl.no" />
									<ihtml:radio name="paVO" property="messagingEnabled" onclick="selectRadio();" value="P" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEP"/>
									<common:message key="mailtracking.defaults.pamaster.lbl.partial" />
									</logic:present>
									<logic:notPresent name="paVO">
									<ihtml:radio property="messagingEnabled" onclick="selectRadio();" value="Y" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEY" />
									<common:message key="mailtracking.defaults.pamaster.lbl.yes" />
									<ihtml:radio property="messagingEnabled" onclick="selectRadio();" value="N" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEN" />
									<common:message key="mailtracking.defaults.pamaster.lbl.no" />
									<ihtml:radio property="messagingEnabled" onclick="selectRadio();" value="P" componentID="RAD_MAILTRACKING_DEFAULTS_PAMASTER_MESSAGEP" />
									<common:message key="mailtracking.defaults.pamaster.lbl.partial" />
									</logic:notPresent>
							  </div>-->
							  </div>
							 <!--Added by A-7540 for ICRD-243365-->
							 <div class="ic-row">
							 
							 <div class="ic-input ic-split-14">
									<logic:present name="paVO">
									<ihtml:checkbox name="paVO" property="proformaInvoiceRequired" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISPROFORMAINVREQ"/>
									</logic:present>
									<logic:notPresent name="paVO">
									<ihtml:checkbox property="proformaInvoiceRequired" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ISPROFORMAINVREQ"/>
									</logic:notPresent>
											
										<common:message key="mailtracking.defaults.pamaster.lbl.isproformaInvoiceRequired" />
									
								 </div>
								 
								 <div class="ic-input ic-split-20">
								 <!--commented  by A-8527 for ICRD-282011-->
							 <!--<label> -->
							  <common:message key="mailtracking.defaults.pamaster.lbl.latValidation" />
							  <!-- </label>  -->
								<logic:present name="paVO">
								<ihtml:select name="paVO" property="latValLevel" 
								componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_LATVALIDATION" style="width:100px">

								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>

								<logic:present name="OneTimeValues">

								<logic:iterate id="oneTimeValue" name="OneTimeValues">

								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.defaults.latvalidationlevel">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

								<ihtml:option value="<%=String.valueOf(fieldValue) %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
								</logic:present>

								</ihtml:select>
								</logic:present>
								<logic:notPresent name="paVO">
								<ihtml:select  property="latValLevel" componentID="CMB_MAILTRACKING_DEFAULTS_PAMASTER_LATVALIDATION" style="width:100px">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>

								<logic:present name="OneTimeValues">

								<logic:iterate id="oneTimeValue" name="OneTimeValues">

								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.defaults.latvalidationlevel">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

								<ihtml:option value="<%=String.valueOf(fieldValue) %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
								</logic:present>
								</ihtml:select>

								</logic:notPresent>
							  </div>
							  <div class="ic-input ic-split-40"><!-- added by A-8353 for ICRD-230449-->
								<common:message key="mailtracking.defaults.pamaster.lbl.dupMailbagPeriod" />
												
                                                <logic:present name="paVO">
												<bean:define id="dupMailbagPeriod" name="paVO" property="dupMailbagPeriod"/>
												<logic:equal name="dupMailbagPeriod" value="0">
												<ihtml:text id="dupMailbagPeriod" name="paVO" property="dupMailbagPeriod" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DUPMAILBAGPERIOD"  maxlength="5"/>
												</logic:equal>
												<logic:notEqual name="dupMailbagPeriod"  value="0">
												<ihtml:text id="dupMailbagPeriod" name="paVO" property="dupMailbagPeriod" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DUPMAILBAGPERIOD"  maxlength="5"/>
												</logic:notEqual>
												</logic:present>
												
												<logic:notPresent name="paVO">
												<ihtml:text id="dupMailbagPeriod" name="paVO" property="dupMailbagPeriod" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_DUPMAILBAGPERIOD" maxlength="5" />
								                </logic:notPresent>
								 </div> 
								 

								 
								 
								 </div> 
							  
							  <div class="ic-row">
							  
								  <fieldset class="ic-field-set">
									<legend ><common:message key="mailtracking.defaults.pamaster.lbl.billingaddress" /></legend>
										<div class="ic-row">
									<div class="ic-input ic-split-25">
												<label class="ic-label-50">
										
															<common:message key="mailtracking.defaults.pamaster.lbl.conperson" />
														</label>				
														<logic:present name="paVO">
															<ihtml:text property="conPerson" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CONPER" maxlength="50" />
														</logic:present>
														<logic:notPresent name="paVO">
															<ihtml:text property="conPerson" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CONPER" maxlength="50" />
														</logic:notPresent>	
														
														</div>
											<div class="ic-input  ic-split-50">
										
											<label class="ic-label-50">
														<common:message key="mailtracking.defaults.pamaster.lbl.address"/>
														</label>		
															<logic:present name="paVO">
															<ihtml:textarea  name="paVO" property="address" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ADDRESS" maxlength="800" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:textarea  property="address" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_ADDRESS" maxlength="800" />
															</logic:notPresent>  														
											
														</div>	
											<div class="ic-input ic-mandatory ic-split-25">
										
											<label class="ic-label-50">
															<common:message key="mailtracking.defaults.pamaster.lbl.city" />
														</label>			
															<logic:present name="paVO">
															<ihtml:text property="city" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CITY" maxlength="25" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:text property="city" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CITY" maxlength="25" />
															</logic:notPresent>	
											
													</div>
										</div>
													<div class="ic-row">
										<div class="ic-input  ic-split-25">
										
											<label class="ic-label-50">
															<common:message key="mailtracking.defaults.pamaster.lbl.state" />
														</label>			
														<logic:present name="paVO">
														<ihtml:text property="state" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATE" maxlength="25" />
														</logic:present>
														<logic:notPresent name="paVO">
														<ihtml:text property="state" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_STATE" maxlength="25" />
														</logic:notPresent>
											
											</div>
											<div class="ic-input  ic-split-25">
										
											<label class="ic-label-50">
															<common:message key="mailtracking.defaults.pamaster.lbl.country" />
														</label>	
															<logic:present name="paVO">
															<ihtml:text property="country" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_COUNTRY_NAME" maxlength="25" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:text property="country" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_COUNTRY_NAME" maxlength="25" />
															</logic:notPresent>														
											
														</div>	
										<div class="ic-input  ic-split-25">
										
											<label class="ic-label-50">
															<common:message key="mailtracking.defaults.pamaster.lbl.mobile" />
														</label>	
															<logic:present name="paVO">
															<ihtml:text property="mobile" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_MOBILE" maxlength="25" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:text property="mobile" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_MOBILE" maxlength="25" />
															</logic:notPresent>														
											
														</div>															
											<div class="ic-input  ic-split-25">
										
											
													<label class="ic-label-50">
														<common:message key="mailtracking.defaults.pamaster.lbl.postcod" />
														</label>
															<logic:present name="paVO">
															<ihtml:text property="postCod" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_POSTCOD" maxlength="7" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:text property="postCod" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_POSTCOD" maxlength="7"/>
															</logic:notPresent>														
														</div>
											
													</div>
										
													<div class="ic-row">
											<div class="ic-input  ic-split-25">
										
									<label class="ic-label-50">
															<common:message key="mailtracking.defaults.pamaster.lbl.phone1" />
														</label>
															<logic:present name="paVO">
															<ihtml:text property="phone1" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PHONE1" maxlength="25" />
															</logic:present>
															<logic:notPresent name="paVO">
															<ihtml:text property="phone1" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PHONE1" maxlength="25" />
															</logic:notPresent>
											
											</div>
												<div class="ic-input  ic-split-10">
										
										<label class="ic-label-50">
																<common:message key="mailtracking.defaults.pamaster.lbl.phone2" />
															</label>
																<logic:present name="paVO">
																<ihtml:text property="phone2" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PHONE2" maxlength="25" />
																</logic:present>
																<logic:notPresent name="paVO">
																<ihtml:text property="phone2" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_PHONE2" maxlength="25" />
																</logic:notPresent>	
											
											</div>
	<div class="ic-input  ic-split-10">											<label class="ic-label-50">
																<common:message key="mailtracking.defaults.pamaster.lbl.fax" />
															</label>
																<logic:present name="paVO">
																	<ihtml:text property="fax" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_FAX" maxlength="25" />
																</logic:present>
																<logic:notPresent name="paVO">
																	<ihtml:text property="fax" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_FAX" maxlength="25" />
																</logic:notPresent>	
											
											</div>
													<div class="ic-input  ic-split-17">
										
										<label class="ic-label-50">
																<common:message key="mailtracking.defaults.pamaster.lbl.email" />
															</label>
																<logic:present name="paVO">
																	<ihtml:text property="email" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_EMAIL" maxlength="200" />
																</logic:present>
																<logic:notPresent name="paVO">
																	<ihtml:text property="email" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_EMAIL" maxlength="200" />
																</logic:notPresent>														
											
														</div>
														
														<!--Added as part of IASCB-853 starts-->
														<div class="ic-input  ic-split-17">
										
										<label class="ic-label-50">
																<common:message key="mailtracking.defaults.pamaster.lbl.SecondaryEmail1" />
															</label>
																<logic:present name="paVO">
																	<ihtml:text property="secondaryEmail1" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SECONDARYEMAIL1" maxlength="200" />
																</logic:present>
																<logic:notPresent name="paVO">
																	<ihtml:text property="secondaryEmail1" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SECONDARYEMAIL1" maxlength="200" />
																</logic:notPresent>														
											
													</div>
														<div class="ic-input  ic-split-17">
														<label class="ic-label-50">
																<common:message key="mailtracking.defaults.pamaster.lbl.SecondaryEmail2" />
															</label>
																<logic:present name="paVO">
																	<ihtml:text property="secondaryEmail2" name="paVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SECONDARYEMAIL2" maxlength="200" />
																</logic:present>
																<logic:notPresent name="paVO">
																	<ihtml:text property="secondaryEmail2" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_SECONDARYEMAIL2" maxlength="200" />
																</logic:notPresent>														
											
														</div>
													<!--	Added as part of IASCB-853 ends-->
													</div>

												</div>		
								  </fieldset>
								  
								  
								  </div>

							 <div class="ic-row">
								 <div class="ic-input ic-col-40">
								 <label>
									<common:message key="mailtracking.defaults.pamaster.lbl.remarks" /></td>
								 </label>				
									<logic:present name="paVO">
										<ihtml:textarea cols="70" rows="5" name="paVO" property="remarks" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_REMARKS"/>
									</logic:present>
									<logic:notPresent name="paVO">
										<ihtml:textarea cols="90" rows="5" property="remarks" componentID="TXT_AREA_MAILTRACKING_DEFAULTS_PAMASTER_REMARKS"/>
									</logic:notPresent>
								
								 </div>
							<div class="ic-col-60">
							  <div class="ic-round-border">
								<fieldset class="ic-field-set">
									<legend>
										<common:message key="mailtracking.defaults.pamaster.lbl.otherdetails" />
									</legend>
									<div class="ic-button-container">
									<div class="ic-row ic-right">
									<a id="addOtherLink" name="addOtherLink" class="iCargoLink" href="#" value="add">
										<common:message key="mailtracking.defaults.pamaster.lbl.add" />
									</a>
									|
									<a id="deleteOtherLink" name="deleteOtherLink" class="iCargoLink" href="#" value="delete">
										<common:message key="mailtracking.defaults.pamaster.lbl.delete"/>
									</a>
									</div>
									</div>
									<div class="ic-row">
										<jsp:include page="PostalAdministrationMaster_Details.jsp" />
									</div>
								
								
							</div>	
							</div>
							</div>
						  </div>  
						</div>    
					
			    
				<div class="ic-foot-container">	
				   <div class="ic-button-container ic-pad-5">
					<div class="ic-row ic-right">
					<ihtml:nbutton property="btActivate" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_ACTIVATE" accesskey="I">
						<common:message key="mailtracking.defaults.pamaster.btn.activate" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btInactivate" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_INACTIVATE" accesskey="V">
						<common:message key="mailtracking.defaults.pamaster.btn.inactivate" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btSave" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_SAVE" accesskey="S">
						<common:message key="mailtracking.defaults.pamaster.btn.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_PAMASTER_CLOSE" accesskey="O">
						<common:message key="mailtracking.defaults.pamaster.btn.close" />
					</ihtml:nbutton>
					</div>
				   </div>
			    </div>

			</div>
		</ihtml:form>	
	</div>	
	    
	</body>

 </html:html>

