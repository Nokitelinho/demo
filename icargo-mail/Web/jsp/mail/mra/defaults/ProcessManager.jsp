<%--
* Project	 			: iCargo
* Module Code & Name			: MailTracking.mra.defaults
* File Name				: ProcessManager.jsp
* Date					: 20-Jun-2007
* Author(s)				: A-2270
--%>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm" %>



	
<html:html locale="true">

	<head>
		
	

	
		
	
		<title>
			<common:message bundle="mraprocessmanager" key="mailtracking.mra.defaults.processmanager.lbl.processmanagerfiletitle" />
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="css" src="../css/icargo.css" />
		<common:include type="script" src="/js/mail/mra/defaults/ProcessManager_Script.jsp" />
	</head>

	<body style="overflow:auto;">
	
	<style>
#fullProgress {
  width: 100%;
  background-color: #ddd;
}#myProgress{
  width: 100%;
  background-color: #ddd;
}#unProcessed {
  width: 100%;
  background-color: #ddd;
}   
#awbProgress {
  width: 0%;
  height: 10px;
  background-color: #ff9900;
}#fullProgressBar {
  width: 0%;
  height: 10px;
  background-color: #4CAF50;
}
#myBarUnprocessed {
  width: 0%;
  height: 10px;
  background-color: #ff5c33;
}  
</style> 
	
	
	

	

		<business:sessionBean id="KEY_ONETIMEVALUES"
					  moduleName="mailtracking.mra.defaults"
					  screenID="mailtracking.mra.defaults.processmanager"
					  method="get"
					  attribute="processes" />

		<business:sessionBean id="KEY_ONETIME_FILTERMODE"
					  moduleName="mailtracking.mra.defaults"
					  screenID="mailtracking.mra.defaults.processmanager"
					  method="get"
					  attribute="filterMode" />

        <business:sessionBean id="ONETIME_MAILCATEGORY"
					  moduleName="mailtracking.mra.defaults"
					  screenID="mailtracking.mra.defaults.processmanager"
					  method="get"
					  attribute="mailCategory" />
					  
					  

        <business:sessionBean id="ONETIME_SUBCLASS"
					  moduleName="mailtracking.mra.defaults"
					  screenID="mailtracking.mra.defaults.processmanager"
					  method="get"
					  attribute="mailSubclass" />

	    <business:sessionBean id="ONETIME_PRORATIONEXCEPTION"
					  moduleName="mailtracking.mra.defaults"
					  screenID="mailtracking.mra.defaults.processmanager"
					  method="get"
					  attribute="exception" />

		<bean:define id="MRAProcessManagerForm"
			     name="MRAProcessManagerForm"
			     type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm"
			     toScope="page" />

			 
		<div class="iCargoContent ic-masterbg" style="overflow:auto;height:705px;">

			<ihtml:form action="/mailtracking.mra.defaults.processmanager.onScreenLoad.do">

			 <ihtml:hidden property="totalInvoiceCount" />
			 <ihtml:hidden property="nextFetchValue" />
			 <ihtml:hidden property="completionFlag" />
			 <ihtml:hidden property="finalizedInvoiceCount" />

				<div class="ic-content-main bg-white">
					<span class="ic-page-title ic-display-none">
						<common:message key="mra.defaults.lbl.processmanagerpagetitle" />
					</span>	
					<div class="ic-head-container">
						<div class="ic-filter-panel">
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-13">
										<label>
											<common:message key="mra.defaults.lbl.process"/>
										</label>
										<ihtml:select property="processOneTime" componentID="CMP_MRA_Defaults_ProcessManager_Process"  onchange="disablefilter();"  >
											<logic:present name="KEY_ONETIMEVALUES">
											  <logic:iterate id="processOneTime" name="KEY_ONETIMEVALUES">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
								<div id="top">
								<div class="ic-input ic-split-10">
									<label>
										<common:message key="mra.defaults.processmanager.lbl.flightNo" />
									</label>
									<%String carCode="";%>
									<%String fltNum="";%>
									<ibusiness:flightnumber carrierCodeProperty="carrierCode"
									  id="flightNumber"
									  flightCodeProperty="flightNumber"
									  carriercodevalue="<%=(String)MRAProcessManagerForm.getCarrierCode()%>"
									  flightcodevalue="<%=(String)MRAProcessManagerForm.getFlightNumber()%>"
									  componentID="CMP_MRA_Defaults_ProcessManager_ViewFlownMail_FLIGHTNO"
									  carrierCodeStyleClass="iCargoTextFieldVerySmall"
									  flightCodeStyleClass="iCargoTextFieldSmall"
									  />
								</div>
								<div class="ic-input ic-split-10">
									<label>
										<common:message key="mra.defaults.processmanager.lbl.flightDate" />
									</label>
									<ibusiness:calendar property="flightDate" id="flightDate" componentID="CMP_MRA_Defaults_ProcessManager_FLIGHTDATE" type="image" />
								</div>
								</div>
								
								<div id="FP"  style="display:none">
								
										<div class="ic-input ic-mandatory ic-split-10">
												<label>
													 <common:message  key="mra.defaults.processmanager.periodno" scope="request"/>
												</label>
												<ihtml:text property="periodNumber" id="periodNumber" componentID="CMP_MRA_Defaults_ProcessManager_PeriodNumber"  />
										</div>
											
											
										<div class="ic-input ic-mandatory ic-col-15" >
												<label>
													<common:message key="mra.defaults.processmanager.passfrom" />
												</label>
												<ibusiness:calendar property="passBillingPeriodFrom" id="passBillingPeriodFrom" type="image"
												value="<%=MRAProcessManagerForm.getPassBillingPeriodFrom()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_FROMDATE_ForPass" onblur="autoFillDate(this)"/>
										</div>
										
										<div class="ic-input ic-mandatory ic-col-15 ">
												<label>
												<common:message key="mra.defaults.processmanager.passto" />
												</label>
												<ibusiness:calendar property="passBillingPeriodTo" id="passBillingPeriodTo" type="image"
												value="<%=MRAProcessManagerForm.getPassBillingPeriodTo()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_TODATE_ForPass" onblur="autoFillDate(this)"/>
										</div>
										
										
								<div class="ic-input ic-split-20">	
								<div class="ic-row"><div  style="height:45px;" >                                                    
								 <div > </div>
								</div></div>
								<div class="ic-row"><div id="fullProgress" style="height:10px;" >                                                    
								 <div id="fullProgressBar"></div>
								</div></div>
								<div class="ic-row"><div  style="height:20px;" >                                                    
								 <div > </div>
								</div></div>
								<div  class="ic-row"><div class="ic-input ic-split-60"><span id="intialProgress">0</span> Invoices finalised</div></div>
								</div>
								
								<div class="ic-input ic-split-10">
								<div class="ic-row"><div  style="height:40px;" >                                                    
								 <div > </div>
								</div></div>
								<div class="ic-row"><div id="myBarValue" class="ic-input ic-split-75" >0</div>
								</div>
								</div>
	
								</div>
								
								<div id="PRCSMGRMAL"  style="display:none">
								<div class="ic-input ic-split-20 "> 
		                         <label><common:message key="mra.defaults.processmanager.mailbag" /></label>
											<ihtml:text property="mailbag" componentID="CMP_MRA_Defaults_ProcessManager_MAILBAG" readonly="false" maxlength="29" style="text-transform : uppercase; width:200px" />												
											<div class="lovImg">											
											<img name="mailBaglov" id="mailBaglov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" alt="" />
											</div>
								</div>
								</div>
								
								
								<div id="PRCSMGR1"  style="display:none">

								<div class="ic-col-37">
									<fieldset class="ic-field-set">
									
										<legend class="iCargoLegend">
										<common:message key="mra.defaults.processmanager.date" />
										</legend>
										
										<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.defaults.processmanager.filtermode" />
										</label>
										
										<ihtml:select property="filterMode" componentID="CMP_MRA_Defaults_ProcessManager_Process"  >
											<logic:present name="KEY_ONETIME_FILTERMODE">
											  <logic:iterate id="filterModeOnetime" name="KEY_ONETIME_FILTERMODE">
												<bean:define id="filterModeOnetimeValue" name="filterModeOnetime" property="fieldValue" />
													<html:option value="<%=(String)filterModeOnetimeValue %>">
														<bean:write name="filterModeOnetime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
										
										
									</div>
									
										<div class="ic-input ic-mandatory ic-col-35 ic-label-35">
												<label>
													<common:message key="mra.defaults.processmanager.from" />
												</label>
												<ibusiness:calendar property="fromDateImportmail" id="fromDateImportmail" type="image"
												value="<%=MRAProcessManagerForm.getFromDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_FROMDATE_ForProrate" onblur="autoFillDate(this)"/>
										</div>
										
										<div class="ic-input ic-mandatory ic-col-35 ic-label-35">
												<label>
												<common:message key="mra.defaults.processmanager.to" />
												</label>
												<ibusiness:calendar property="toDateImportmail" id="toDateImportmail" type="image"
												value="<%=MRAProcessManagerForm.getToDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManage_TODATE_ForProrate" onblur="autoFillDate(this)"/>
										</div>	
										
									</fieldset>	 
								</div>
									
								
								
								</div>
								
								<div id="PRCSMGR5"  style="display:none">
							 <div class="ic-input ic-split-20">	
                                <label><common:message key="mra.defaults.processmanager.origin" /></label>
								<ihtml:select componentID="CMP_MRA_Defaults_ProcessManager_origin" property="origin">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								</ihtml:select>
					            <ihtml:text property="originOfficeOfExchange" componentID="CMP_MRA_Defaults_ProcessManager_OOE" readonly="false" maxlength="6" style="text-transform : uppercase;" />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="mailOOELov" id="mailOOELov"src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                                </div>								
						</div>	
						  
                        <div class="ic-input ic-split-20">	
		                         <label><common:message key="mra.defaults.processmanager.destination" /></label>
								<ihtml:select componentID="CMP_MRA_Defaults_ProcessManager_destination" property="destination">
								 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<ihtml:option value="CNT">Country</ihtml:option>
								<ihtml:option value="CTY">City</ihtml:option>
								<ihtml:option value="ARP">Airport</ihtml:option>
								</ihtml:select>
					            <ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MRA_Defaults_ProcessManager_DOE" readonly="false" maxlength="6" style="text-transform : uppercase;"  />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                        </div>
                        </div>
							
						<div class="ic-input ic-split-15">	
		                         <label><common:message key="mra.defaults.processmanager.uplift" /></label>								
					            <ihtml:text property="upliftAirportReRate" componentID="CMP_MRA_Defaults_ProcessManager_Uplift" readonly="false" maxlength="4" style="text-transform : uppercase;"  />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="upliftAirportLov" id="upliftAirportLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                        </div>
                        </div>
						<div class="ic-input ic-split-15">	
		                         <label><common:message key="mra.defaults.processmanager.discharge" /></label>								
					            <ihtml:text property="dischargeAirportReRate" componentID="CMP_MRA_Defaults_ProcessManager_Discharge" readonly="false" maxlength="4" style="text-transform : uppercase;"  />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="dischargeAirportLov" id="dischargeAirportLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                        </div>
                        </div>
						
							
								<div class="ic-row">
								<div class="ic-input ic-split-25">
							<label><common:message key="mra.defaults.processmanager.category" /></label>
							<ihtml:select property="mailCategory" componentID="CMP_MRA_Defaults_ProcessManager_category"   >
											<logic:present name="ONETIME_MAILCATEGORY">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											  <logic:iterate id="processOneTime" name="ONETIME_MAILCATEGORY">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>					
						</div>
						<div class="ic-input ic-split-25 ">
							<label><common:message key="mra.defaults.processmanager.subclass" /></label>
							<ihtml:select componentID="CMP_MRA_Defaults_ProcessManager_subclass" property="mailSubclass">
							                <logic:present name="ONETIME_SUBCLASS">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											  <logic:iterate id="processOneTime" name="ONETIME_SUBCLASS">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>	
						</div>
				
					<div class="ic-input ic-split-25 ">
												<label>
													 <common:message  key="mra.defaults.processmanager.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDate" id="fromDate" type="image"
												value="<%=MRAProcessManagerForm.getFromDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_FROMDATE"/>
											</div>
											<div class="ic-input ic-split-25 ">
												<label>
													<common:message  key="mra.defaults.processmanager.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDate" id="toDate" type="image"
												value="<%=MRAProcessManagerForm.getToDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManage_TODATE"/>
										</div>	
								</div>	
									<div  class="ic-row" >
									<div class="ic-col-35">
							<fieldset class="ic-field-set">
								<legend><common:message key="mra.defaults.processmanager.billngParty" scope="request"/></legend>
								
									
									<div class="ic-input ic-split-30">
												<label>
													 <common:message  key="mra.defaults.processmanager.gpacode" scope="request"/>
												</label>
												<ihtml:text property="gpaCode" componentID="CMP_MRA_Defaults_ProcessManager_GPACODE" style="text-transform : uppercase;" />&nbsp;&nbsp;
												<div class= "lovImg">
												<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" width="22" height="22" alt="" />
												</div>
											</div>
									<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.defaults.processmanager.toairlinecode" scope="request"/>
										</label>
										<ihtml:text property="toAirlineCode"  componentID="CMP_MRA_Defaults_ProcessManager_TOARLCOD"  maxlength="3" style="text-transform : uppercase;" />&nbsp;&nbsp;
										<div class= "lovImg">
										<img id="rerateAirlineCodeLov1" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										</div>
									</div>
									
									<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.defaults.processmanager.fromairlinecode" scope="request"/>
										</label>
										<ihtml:text property="fromAirlineCode"  componentID="CMP_MRA_Defaults_ProcessManager_FROMARLCOD"  maxlength="3" style="text-transform : uppercase;" />&nbsp;&nbsp;
										<div class= "lovImg">
										<img id="rerateAirlineCodeLov2" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										</div>
									</div>
									
							</fieldset>
							</div>
							<div class="ic-col-45">
							<fieldset class="ic-field-set">
								<legend><common:message key="mra.defaults.processmanager.carditparty" scope="request"/></legend>
								
									
									<div class="ic-input ic-split-30">
												<label>
													 <common:message  key="mra.defaults.processmanager.transferairline" scope="request"/>
												</label>
												<ihtml:text property="transferAirlineReRate" componentID="CMP_MRA_Defaults_ProcessManager_TRFARL" maxlength="3" style="text-transform : uppercase;" />&nbsp;&nbsp;
												<div class= "lovImg">
												<img src="<%=request.getContextPath()%>/images/lov.png" id="transferAirlineLov" name="transferAirlineLov" width="22" height="22" alt="" />
												</div>
											</div>
									<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.defaults.processmanager.transferpa" scope="request"/>
										</label>
										<ihtml:text property="transferPAReRate"  componentID="CMP_MRA_Defaults_ProcessManager_TRFPA"  maxlength="5" style="text-transform : uppercase;" />&nbsp;&nbsp;
										<div class= "lovImg">
										<img id="transferPALov"  name="transferPALov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										</div>
									</div>
									
		
									
							</fieldset>
							</div>
							
							 <div class="ic-input" style="margin-left:20px;">	
                                <label><common:message key="mra.defaults.processmanager.orginOOE" /></label>
					            <ihtml:text property="originOEReRate" componentID="CMP_MRA_Defaults_ProcessManager_OOEE" readonly="false" maxlength="6" style="text-transform : uppercase;" />
				              
								<div class="lovImg"><img name="mailOOERRLov" id="mailOOERRLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>								
						</div>	
						  
                        <div class="ic-input ic-split-20 ">	
		                         <label><common:message key="mra.defaults.processmanager.destinationOOE" /></label>
					            <ihtml:text property="destinationOEReRate" componentID="CMP_MRA_Defaults_ProcessManager_DOEE" readonly="false" maxlength="6" style="text-transform : uppercase;"  />
								
				               <div class="lovImg"> <img name="mailDOERRLov" id="mailDOERRLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
							   </div>
                        </div>
							
						</div>
							</div>
					
							<!--added by a-7531 for icrd-132487-->
								<div id="PRCSMGR6"  style="display:none">
								<div class="ic-row">
								<div class="ic-input ic-split-20">
										<label><common:message key="mra.defaults.processmanager.exception" /></label>
							<ihtml:select componentID="CMP_MRA_Defaults_ProcessManager_Exception" property="prorateException">
							                <logic:present name="ONETIME_PRORATIONEXCEPTION">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											  <logic:iterate id="processOneTime" name="ONETIME_PRORATIONEXCEPTION">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
 <div class="ic-col-35">
<fieldset class="ic-field-set">
<legend class="iCargoLegend">
								<common:message key="mailtracking.mra.processManager.daterange" />
							</legend>
						<div class="ic-input ic-mandatory ic-col-50 ic-label-35">
												<label>
													 <common:message  key="mra.defaults.processmanager.datefrom" scope="request"/>
												</label>
												<ibusiness:calendar property="fromDateforProrate" id="fromDateforProrate" type="image"
												value="<%=MRAProcessManagerForm.getFromDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_FROMDATE_ForProrate" onblur="autoFillDate(this)"/>
											</div>
						<div class="ic-input ic-mandatory ic-col-50 ic-label-35">
												<label>
													<common:message  key="mra.defaults.processmanager.dateto" scope="request"/>
												</label>
												<ibusiness:calendar property="toDateforProrate" id="toDateforProrate" type="image"
												value="<%=MRAProcessManagerForm.getToDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManage_TODATE_ForProrate" onblur="autoFillDate(this)"/>
										</div>	
										

</fieldset>	 
									</div>
									
									<div class="ic-col-45">
							<fieldset class="ic-field-set">
								<legend><common:message key="mra.defaults.processmanager.carditparty" scope="request"/></legend>
								
									
									<div class="ic-input ic-split-35">
												<label>
													 <common:message  key="mra.defaults.processmanager.transferairline" scope="request"/>
												</label>
												<ihtml:text property="transferAirlineException" maxlength="3" componentID="CMP_MRA_Defaults_ProcessManager_TRFARL" style="text-transform : uppercase;" />&nbsp;&nbsp;
												<div class= "lovImg">
												<img src="<%=request.getContextPath()%>/images/lov.png" id="transferAirlineExpLov" name="transferAirlineExpLov" width="22" height="22" alt="" />
						</div>
											</div>
									<div class="ic-input ic-split-35">
										<label>
										<common:message key="mra.defaults.processmanager.transferpa" scope="request"/>
										</label>
										<ihtml:text property="transferPAException"  componentID="CMP_MRA_Defaults_ProcessManager_TRFPA"  maxlength="5" style="text-transform : uppercase;" />&nbsp;&nbsp;
										<div class= "lovImg">
										<img id="transferPAExpLov"  name="transferPAExpLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										</div>
									</div>
									
		
									
							</fieldset>
							</div>
						</div>


								<div class="ic-row">
								
								 <div class="ic-input ic-split-10" >	
                                <label><common:message key="mra.defaults.processmanager.orginOOE" /></label>
					            <ihtml:text property="originforProrate" componentID="CMP_MRA_Defaults_ProcessManager_OOEE" readonly="false" maxlength="6" style="text-transform : uppercase;" />
				                <!-- Modified by A-8236 for ICRD-255110-->
				                <!--<img name="mailOOELov1" id="mailOOELov1"src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"/>	-->
								<div class="lovImg"><img name="mailOOELov1" id="mailOOELov1" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>								
						</div>	
						  
                        <div class="ic-input ic-split-10 ">	
		                         <label><common:message key="mra.defaults.processmanager.destinationOOE" /></label>
					            <ihtml:text property="destinationforProrate" componentID="CMP_MRA_Defaults_ProcessManager_DOEE" readonly="false" maxlength="6" style="text-transform : uppercase;"  />
								<!-- Modified by A-8236 for ICRD-255110-->
				                <!--<img name="mailDOELov1" id="mailDOELov1" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"/> -->
				               <div class="lovImg"> <img name="mailDOELov1" id="mailDOELov1" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
							   </div>
                        </div>
						
					    <div class="ic-input ic-split-15 ">	
		                         <label><common:message key="mra.defaults.processmanager.originairport" /></label>
					            <ihtml:text property="originAirportException" componentID="CMP_MRA_Defaults_ProcessManager_ORGARP" readonly="false" maxlength="4" style="text-transform : uppercase;"  />
								
				               <div class="lovImg"> <img name="originAirportExceptionLov" id="originAirportExceptionLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
							   </div>
                        </div>	
						
					    <div class="ic-input ic-split-15 ">	
		                         <label><common:message key="mra.defaults.processmanager.destinationairport" /></label>
					            <ihtml:text property="destinationAirportException" componentID="CMP_MRA_Defaults_ProcessManager_DSTARP" readonly="false" maxlength="4" style="text-transform : uppercase;"  />
								
				               <div class="lovImg"> <img name="destinationAirportExceptionLov" id="destinationAirportExceptionLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
							   </div>
                        </div>							
						
						<div class="ic-input ic-split-15">	
		                         <label><common:message key="mra.defaults.processmanager.uplift" /></label>								
					            <ihtml:text property="upliftAirportException" componentID="CMP_MRA_Defaults_ProcessManager_Uplift" readonly="false" maxlength="4" style="text-transform : uppercase;"  />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="upliftAirportExpLov" id="upliftAirportExpLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                        </div>
                        </div>
						<div class="ic-input ic-split-20">	
		                         <label><common:message key="mra.defaults.processmanager.discharge" /></label>								
					            <ihtml:text property="dischargeAirportException" componentID="CMP_MRA_Defaults_ProcessManager_Discharge" readonly="false" maxlength="4" style="text-transform : uppercase;"  />&nbsp;&nbsp;
								<div class= "lovImg">
				                <img name="dischargeAirportExpLov" id="dischargeAirportExpLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"/>
                        </div>
						
						</div>
						
						<div class="ic-input "style="margin-left:-50px;">
							<label><common:message key="mra.defaults.processmanager.subclass" /></label>
							<ihtml:select componentID="CMP_MRA_Defaults_ProcessManager_subclass" property="mailSubclassforProrate">
							                <logic:present name="ONETIME_SUBCLASS">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											  <logic:iterate id="processOneTime" name="ONETIME_SUBCLASS">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>	
						</div>
				        <div class="ic-input">
							<label><common:message key="mra.defaults.processmanager.category" /></label>
							<ihtml:select property="mailCategoryforProrate" componentID="CMP_MRA_Defaults_ProcessManager_category"   >
											<logic:present name="ONETIME_MAILCATEGORY">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											  <logic:iterate id="processOneTime" name="ONETIME_MAILCATEGORY">
												<bean:define id="processOneTimeValue" name="processOneTime" property="fieldValue" />
													<html:option value="<%=(String)processOneTimeValue %>">
														<bean:write name="processOneTime" property="fieldDescription" />
													</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>					
						</div>
						
								</div>	
							
							</div>
							<!-- Added by A-4809 for ICRD-232299 cardit case Starts-->
							<div id="PRCSMGR7"  style="display:none">							
							<div  class="ic-row" >
								<div class="ic-input ic-split-30">
							    <fieldset class="ic-field-set">
								<legend><common:message key="mra.defaults.processmanager.mailbagdate" scope="request"/></legend>
									<div class="ic-input ic-split-50 ">
										<label>
											 <common:message  key="mra.defaults.processmanager.datefrom" scope="request"/>
										</label>
										<ibusiness:calendar property="mailFromDate" id="mailFromDate" type="image"
										value="<%=MRAProcessManagerForm.getMailFromDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManager_FROMDATE"/>
									</div>
									<div class="ic-input ic-split-50 ">
										<label>
											<common:message  key="mra.defaults.processmanager.dateto" scope="request"/>
										</label>
										<ibusiness:calendar property="mailToDate" id="mailToDate" type="image"
										value="<%=MRAProcessManagerForm.getMailToDate()%>" maxlength="11" componentID="CMP_MRA_Defaults_ProcessManage_TODATE"/>
									</div>	
								</fieldset>
								</div>
								<div class="ic-input ic-split-20 ">
                                <label><common:message key="mra.defaults.processmanager.pacode" /></label>
					            <ihtml:text property="paCode" componentID="CMP_MRA_Defaults_ProcessManager_PACODE" readonly="false" maxlength="6" style="text-transform : uppercase;" />
								<div class="lovImg"><img name="paCodelov" id="paCodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>										
								</div>
								
							</div>
							</div>
							<!-- Added by A-4809 for ICRD-232299 cardit case Ends-->
							<div id="SISOWBLG"  style="display:none">
								<div class="ic-input ic-split-25 ic-mandatory">	
												<label class="ic-label"> <common:message key="mailtracking.mra.defaults.processManager.lbl.clearanceperiod"/></label>
												<ihtml:text  property="clearancePeriod" componentID="CMP_MRA_DEFAULTS_PROCESSMANAGER_CLEARANCEPERIOD"  style="text-transform : uppercase;" maxlength="6" id="clearanceperiod"/>
												<img class="lovImg" name="clearanceperiodlov" id="clearanceperiodlov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16" />												
											</div>
								</div>
								<div class="ic-button-container">
							<ihtml:nbutton property="btnExecute"  componentID="CMP_MRA_Defaults_ProcessManager_Execute"  accesskey="E" >
								<common:message key="mra.defaults.processmanager.btn.execute" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClose"  componentID="CMP_MRA_Defaults_ProcessManager_Close" accesskey="O" >
								<common:message key="mra.defaults.processmanager.btn.close" scope="request"/>
							</ihtml:nbutton>
						</div>
							</div>
							
								
							
						</div>
					</div>
					</div>
					
					
			

			</ihtml:form>
		    </div>	
				
	
				
		
	</body>


</html:html>
