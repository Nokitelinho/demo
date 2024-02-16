<%--
* Project	 	: iCargo
* Module Code & Name	: Mail
* File Name		: ConsignmentMailPopUp.jsp
* Date			: 28-Jun-2017
* Author(s)		: ArunimaUnnikrishnan
 --%>
 
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm" %>

		
			
	
<html:html>

<head> 
		
	
	<title><common:message bundle="consignmentResources" key="mailtracking.defaults.consignment.lbl.popup.pagetitle" /></title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/mail/operations/ConsignmentMailPopUp_Script.jsp" />
</head>

<body>
	<bean:define id="ConsignmentForm"
             name="ConsignmentForm"
             type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm"
             toScope="page" scope="request"/>
	
    
<business:sessionBean id="consignmentMailPopup" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="consignmentMailPopUpVO" />
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeType" />

<div  class="iCargoPopUpContent">
<ihtml:form action="/mailtracking.defaults.consignment.addmultiplescreenload.do" styleClass="ic-main-form">
<ihtml:hidden property="actionName" />
<ihtml:hidden property="mailOpFlag" value="N"/>
<ihtml:hidden  property="lastPopupPageNum" />
<ihtml:hidden property="displayPopupPage" />
<ihtml:hidden property="totalViewRecords"/>

<div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.defaults.consignment.lbl.popup.pagetitle" />
			</span>
			</div>
	<div class="ic-main-container" >
	<div class="ic-row">
		<div class="ic-col-100 ic-right paddR5">
 
		<common:popuppaginationtag
						pageURL="javascript:displayNextConsignment('lastPageNum','displayPage')"
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						displayPage="<%=ConsignmentForm.getDisplayPopupPage()%>"
						totalRecords="<%=ConsignmentForm.getTotalViewRecords()%>" />
		
		</div>
		
		</div> 
	 <div class="ic-row ic-border" style="width:500px;height:250px;">
	 <div style="margin-bottom:20px;margin-left:9px;">
	 <div  style="margin-left:4px;width:500px">
			<div class="ic-row" >
			<div class="ic-input ic-mandatory ic-split-25">
			<label> <common:message key="mailtracking.defaults.consignment.lbl.ooe" /></label>
		</div>
			<div class="ic-input ic-mandatory ic-split-22">
			<label> <common:message key="mailtracking.defaults.consignment.lbl.doe" /></label> 
			 </div>
			 	<div class="ic-input ic-mandatory ic-split-18">
				<label> <!--Added by A-7938 for ICRD-243958-->
			<common:message key="mailtracking.defaults.consignment.lbl.cat"/></label> 
			</div><!--Added by A-7938 for ICRD-243958-->
			<div class="ic-input ic-mandatory ic-split-15">
				<label> 
						<common:message key="mailtracking.defaults.consignment.lbl.class"/>
			</label> 
			</div><!--Added by A-7938 for ICRD-243958-->
			<div class="ic-input ic-mandatory ic-split-20">
				<label> 
						<common:message key="mailtracking.defaults.consignment.lbl.sc"/>
			</label> 
			</div>
			</div>
			<div class="ic-row">
			<div class="ic-input ic-mandatory ic-split-25 ">
					 <logic:notPresent name="consignmentMailPopup" property="orginOfficeOfExchange">
																	<ihtml:text property="orginOfficeOfExchange"  componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" tabindex="1" value="" maxlength="6" style="text-transform : uppercase;"/>
																</logic:notPresent>
					 <logic:present name="consignmentMailPopup" property="orginOfficeOfExchange">
					 <bean:define id="orginOfficeOfExchange" name="consignmentMailPopup" property="orginOfficeOfExchange" toScope="page"/>
		                  <ihtml:text property="orginOfficeOfExchange"  value="<%=(String)orginOfficeOfExchange%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE"  tabindex="1" maxlength="6" style="text-transform : uppercase;"/>
		             </logic:present>
					 <!--Added by A-7938 for ICRD-243958-->
			   <div class= "lovImg">		 
		<img  id="mailOOELov"src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
		    </div>
		   </div>
            <div class="ic-input ic-mandatory ic-split-22">			
		            <logic:notPresent name="consignmentMailPopup" property="destOfficeOfExchange">
																	<ihtml:text property="destOfficeOfExchange" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" tabindex="2" value="" maxlength="6" style="text-transform : uppercase;" />
																</logic:notPresent>
																<logic:present name="consignmentMailPopup" property="destOfficeOfExchange">
																	<bean:define id="destOfficeOfExchange" name="consignmentMailPopup" property="destOfficeOfExchange" toScope="page"/>
			<!--Added by A-7938 for ICRD-243958-->														<ihtml:text property="destOfficeOfExchange" value="<%=(String)destOfficeOfExchange%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" tabindex="2" maxlength="6"  style="text-transform : uppercase;" />
																</logic:present>
														<div class= "lovImg">
		<img  id="mailDOELov"src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">	</div>
		
		</div>
			<div class="ic-input ic-mandatory ic-split-18">
			
			
			<% String catValue = ""; %>
																<logic:present name="consignmentMailPopup" property="mailCategory">
																	<bean:define id="mailCategory" name="consignmentMailPopup" property="mailCategory" toScope="page"/>
																	<% catValue = (String) mailCategory; %>
																</logic:present>
																<ihtml:select property="mailCategory" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CAT" tabindex="3" value="<%=catValue%>" style="width:35px">
																	<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
																	<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
																		<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeCatVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
																</ihtml:select>
																
			</div>
			<div class="ic-input ic-mandatory ic-split-15">
	
			
			<% String classValue = ""; %>
																<logic:present name="consignmentMailPopup" property="mailClassType">
																	<bean:define id="mailClassType" name="consignmentMailPopup" property="mailClassType" toScope="page"/>
																	<% classValue = (String) mailClassType; %>
																</logic:present>
																<ihtml:select property="mailClassType" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLASS" tabindex="4" value="<%=classValue%>" style="width:35px" >
																	<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
																	<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
																		<bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeMailClassVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
														</ihtml:select>
															</div>	
			
			<div class="ic-input ic-mandatory ic-split-20">
			
			
			<logic:notPresent name="consignmentMailPopup" property="mailSubClass">
			<ihtml:text property="mailSubClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" tabindex="5" maxlength="2" style="text-transform : uppercase;"/>
			</logic:notPresent>
  
             <logic:present name="consignmentMailPopup" property="mailSubClass">
			 <bean:define id="mailSubClass" name="consignmentMailPopup" property="mailSubClass" toScope="page"/>
			 <ihtml:text property="mailSubClass" value="<%=(String)mailSubClass%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" tabindex="5" maxlength="2" style="text-transform : uppercase;"/>
			              </logic:present>
						  	<!--Added by A-7938 for ICRD-243958-->
						  <div class= "lovImg">	
			<img  id="SCLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
			</div>
			
			</div>
			</div>
	
			
			
			<div class="ic-row">
			<div class="ic-input ic-mandatory ic-split-25">
			<label><common:message key="mailtracking.defaults.consignment.lbl.yr"/></label>
			<div class="ic-row">
			<logic:notPresent name="consignmentMailPopup" property="mailYear">
			<ihtml:text property="mailYear" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" maxlength="1" tabindex="6" style="width:30px;text-align:right"  />
			</logic:notPresent>
				<logic:present name="consignmentMailPopup" property="mailYear">
																	<bean:define id="mailYear" name="consignmentMailPopup" property="mailYear" toScope="page"/>
																	<ihtml:text property="mailYear" value="<%=mailYear.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" tabindex="6" maxlength="1" style="width:20px;text-align:right" />
																</logic:present>
			</div>
			</div>
			<div class="ic-input ic-mandatory ic-split-22">
			<label><common:message key="mailtracking.defaults.consignment.lbl.dsn"/></label>
			<div class="ic-row">
				<logic:notPresent name="consignmentMailPopup" property="mailDsn">
																	<ihtml:text property="mailDsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" value="" maxlength="4"  tabindex="7"  style = "text-align:right"/>
																</logic:notPresent>
							<logic:present name="consignmentMailPopup" property="mailDsn">
																	<bean:define id="mailDsn" name="consignmentMailPopup" property="mailDsn" toScope="page"/>
																	<ihtml:text property="mailDsn" value="<%=mailDsn.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN"  tabindex="7" maxlength="4"  style = "text-align:right"/>
																</logic:present>
			
			</div>
		    </div>
			<div class="ic-input ic-mandatory ic-split-18 ">
			<label><common:message key="mailtracking.defaults.consignment.lbl.hni"/></label>
			<div class="ic-row">
			<% String hniValue = ""; %>
																<logic:present name="consignmentMailPopup" property="highestNumberIndicator">
																	<bean:define id="highestNumberIndicator" name="consignmentMailPopup" property="highestNumberIndicator" toScope="page"/>
																	<% hniValue = (String) highestNumberIndicator; %>
																</logic:present>
																<ihtml:select property="highestNumberIndicator" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_HNI" tabindex="8" value="<%=hniValue%>" style="width:35px">
																	<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
																	<ihtml:option value="">
																		<common:message key="combo.select"/>
																	</ihtml:option>
																	<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
																		<bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeHNIVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
												    			</ihtml:select>
																</div>
			</div>
			<div class="ic-input ic-mandatory ic-split-35">
			<label><common:message key="mailtracking.defaults.consignment.lbl.ri"/></label>
			<div class="ic-row">
			<% String riValue = ""; %>
															    <logic:present name="consignmentMailPopup" property="registeredIndicator">
																	<bean:define id="registeredIndicator" name="consignmentMailPopup" property="registeredIndicator" toScope="page"/>
																	<% riValue = (String) registeredIndicator; %>
															    </logic:present>
															    <ihtml:select property="registeredIndicator" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RI"  tabindex="9" value="<%=riValue%>" style="width:35px">
																	<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
																	<ihtml:option value="">
																		<common:message key="combo.select"/>
																	</ihtml:option>
																	<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
																		<bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeRIVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
																</ihtml:select>
																</div>
			</div>
			</div>
			</div>
				<div  class="ic-row" >
				<!--modified by A-7938 for ICRD-243958-->
							<fieldset class="ic-field-set" style="height:100px;margin-left:5px;margin-right:12px;">
								<legend><common:message key="mailtracking.defaults.consignment.lbl.rsnrange" scope="request"/></legend>
								<div class="ic-row"style="margin-left:20px;">
									
									<div class="ic-input ic-split-35">
												<logic:notPresent name="consignmentMailPopup" property="rsnRangeFrom">
												 <ihtml:text property="rsnRangeFrom" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSNFROM" maxlength="3" tabindex="10" style = "text-align:right"/>
												 </logic:notPresent>
												 <logic:present name="consignmentMailPopup" property="rsnRangeFrom">
																	<bean:define id="rsnRangeFrom" name="consignmentMailPopup" property="rsnRangeFrom" toScope="page"/>
																	<ihtml:text property="rsnRangeFrom" value="<%=(String)rsnRangeFrom%>" tabindex="10" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSNFROM" maxlength="3"   style = "text-align:right"/>
																</logic:present>
											</div>
											<h4>To</h4>
									<div class="ic-input ic-split-35">
									<logic:notPresent name="consignmentMailPopup" property="rsnRangeTo">
												 <ihtml:text property="rsnRangeTo" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSNTO" maxlength="3" tabindex="11" style = "text-align:right"/>
												 </logic:notPresent>
												 <logic:present name="consignmentMailPopup" property="rsnRangeTo">
																	<bean:define id="rsnRangeTo" name="consignmentMailPopup" property="rsnRangeTo" toScope="page"/>
																	<ihtml:text property="rsnRangeTo" value="<%=(String)rsnRangeTo%>" tabindex="11" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSNTO" maxlength="3"   style = " text-align:right"/>
																</logic:present>
										
				
									</div>
									</div>
									
									<div style="margin-top:20px;float:right;margin-right:10px;">

									
									<h4><common:message  key="mailtracking.defaults.consignment.lbl.receptacles" scope="request"/></h4>
									</div>
									<div style="margin-top:24.5px;float:right;">
									<span  id="Count"><b><bean:write name="ConsignmentForm" property="totalReceptacles"/> </b></span>
									</div>
							</fieldset>
						</div>	
					
						</div>
		</div>	
	</div>		
				<div class="ic-foot-container">
				
			<div class="ic-button-container paddR5">
			        <ihtml:nbutton property="btnClose"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLOSE"  tabindex="12" accesskey="C">
						<common:message key="mailtracking.defaults.consignment.btn.close" />
					</ihtml:nbutton>
					
			        <ihtml:nbutton property="btnNew"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_NEW"  tabindex="13" accesskey="N">
					<common:message key="mailtracking.defaults.consignment.btn.new" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnAdd"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_ADD"  tabindex="14" accesskey="A">
						<common:message key="mailtracking.defaults.consignment.btn.add" />
					</ihtml:nbutton>
					
			</div>
		</div>
		</div>


</ihtml:form>
</div>
		
				
		   
	</body>
</html:html>