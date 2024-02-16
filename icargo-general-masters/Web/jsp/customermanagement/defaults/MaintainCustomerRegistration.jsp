<!--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - CUSTOMER MANAGEMENT
* File Name				:  CustomerRegistration.jsp
* Date					:  12-Apr-2006
* Author(s)				:  A-2046
*************************************************************************/
 -->
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@page import ="java.util.ArrayList"%>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate"%>


<html:html locale="true">
<head>
		
	
		
<title>iCargo : Customer Registration </title>
<bean:define id="form"
	 name="MaintainCustomerRegistrationForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	 toScope="page" />
<common:include type="script" src="/js/customermanagement/defaults/MaintainCustomerRegistration_Script.jsp"/>
<%if(!form.getScreenStatus().equals("maintainReservation")){%>
	<meta name="decorator" content="mainpanelrestyledui" >
<%}else{%>
	<bean:define id="popup" value="true" />
	<meta name="decorator" content="popuppanelrestyledui">
<%}%>
</head>
<body id="bodyStyle" >

  <business:sessionBean id="currencies"
	 moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer" method="get"
	attribute="currency"/>


	<business:sessionBean id="statusValues"
	  moduleName="customermanagement.defaults"
	  screenID="customermanagement.defaults.maintainregcustomer"
	  method="get" attribute="customerStatus"/>

	  <business:sessionBean id="creditPeriods"
			  moduleName="customermanagement.defaults"
			  screenID="customermanagement.defaults.maintainregcustomer"
		method="get" attribute="creditPeriod"/>


		  <business:sessionBean id="customerVO"
						  moduleName="customermanagement.defaults"
						  screenID="customermanagement.defaults.maintainregcustomer"
		method="get" attribute="customerVO"/>


				<business:sessionBean id="customerCodesFromListing"
						  moduleName="customermanagement.defaults"
						  screenID="customermanagement.defaults.maintainregcustomer"
			method="get" attribute="customerCodesFromListing"/>

		<business:sessionBean id="defaultNotifyModes"
						  moduleName="customermanagement.defaults"
						  screenID="customermanagement.defaults.maintainregcustomer"
			method="get" attribute="defaultNotifyModes"/>

		<business:sessionBean id="forwarderTypes"
					  moduleName="customermanagement.defaults"
					  screenID="customermanagement.defaults.maintainregcustomer"
		    method="get" attribute="forwarderTypes"/>
			
        <business:sessionBean id="billingPeriods"
						  moduleName="customermanagement.defaults"
						  screenID="customermanagement.defaults.maintainregcustomer"
			method="get" attribute="billingPeriods"/>

		<business:sessionBean id="OneTimeValues"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="OneTimeValues" />

		<business:sessionBean id="customerIDGenerationRequired"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="customerIDGenerationRequired" />

<div id="pageDiv" class="iCargoContent ic-masterbg" style="width:100%;height:100%;overflow:auto;">
<ihtml:form  action="customermanagement.defaults.screenloadcustomerregistration.do" styleClass="ic-main-form">

		
	

	 <input type="hidden" name="currentDialogId" />
	 <input type="hidden" name="currentDialogOption" />
	 <ihtml:hidden property="listStatus"/>
	 <ihtml:hidden property="checkedStatus"/>
	 <ihtml:hidden property="primaryContact"/>
	 <ihtml:hidden property="checkedExport"/>
	 <ihtml:hidden property="checkedImport"/>
	 <ihtml:hidden property="checkedSales"/>
	  <ihtml:hidden property="companyCode"/>
	 <ihtml:hidden property="pageURL"/>
	 <ihtml:hidden property="fromCustListing"/>
	<ihtml:hidden property="frmCusLisCreate"/>
	<ihtml:hidden property="screenStatus"/>
	<ihtml:hidden property="customerTypecheckFlag"/>
	<ihtml:hidden property="fromReservation"/> <!--Added by A-2390 to check whether call is from reservation screen -->
	<ihtml:hidden property="fromPermanentBooking"/><!--Added by A-3254 to check whether call is from permanent booking screen -->
	<ihtml:hidden property="knownShipper"/>
	<ihtml:hidden property="stopCredit"/>
	<ihtml:hidden property="invoiceToCustomer"/>
	<ihtml:hidden property="vendorFlag"/>
	<ihtml:hidden property="adminPrivilege"/>
	<ihtml:hidden property="aircraftTypeHandledList"/>
	 <ihtml:hidden property="displayPopupPage"/>
	 <ihtml:hidden property="totalViewRecords"/>
	 <ihtml:hidden property="cusVersionNumber"/>
	 <ihtml:hidden property="isHistoryPresent"/>
	 <ihtml:hidden property="isHistoryPopulated"/>
	 <ihtml:hidden property="displayPage"/>
	 <ihtml:hidden property="isLatestVersion"/>	 
	 <ihtml:hidden property="statusFlag"/>
	 <ihtml:hidden property="sourcePage"/>
	<!--Added by A-5791 for ICRD-59602 -->
	<ihtml:hidden property="customerAgentCode" />
	<ihtml:hidden property="customerAgentName" />
	<!--Added as part of CR ICRD-253447 by A-8154 starts-->
	<ihtml:hidden property="stockAutomationRequired"/>
	<ihtml:hidden property="stockAutomationUserConfirmed"/>
	<ihtml:hidden property="screenFlag" />
	<ihtml:hidden property="duplicateStockHolder" />
	<ihtml:hidden property="duplicateStockHolderOverride" />
	<!--Added as part of CR ICRD-253447 by A-8154 ends-->
<!--Modified by A-5165 for ICRD-35135-->
	<%--Added   by A-5183 for CR ICRD-18283 starts --%>
	<%boolean othersflag = false;%>			 
				<common:xgroup>
					<common:xsubgroup id="CUSTOMER_OTHERS">

		 <% othersflag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<%boolean nasFlag = false;%>			 
				<common:xgroup>
					<common:xsubgroup id="NAS_SPECIFIC">
						<% nasFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
	<% if(othersflag || nasFlag){%>
	<ihtml:hidden property="restrictionFlag" value="Y" />
	 <%}else{%>
	 <ihtml:hidden property="restrictionFlag" value="N" />
	 <%}%>
	 
	<jsp:include page="/jsp/includes/tab_support.jsp" />
			
<div class="ic-content-main" >
	<div class="ic-head-container">	
		<div class="ic-filter-panel">			  
			<logic:present name="customerCodesFromListing">
				<div class="ic-row">
					<div class="ic-col-100">
			  			<bean:define id="customerCodes" name="customerCodesFromListing"/>
			            <bean:define id="customerCodesSize" value="<%=String.valueOf(((ArrayList)customerCodes).size())%>" />
						<logic:greaterThan name="customerCodesSize" value="1">
							<common:popuppaginationtag
			  					pageURL="/customermanagement.defaults.navigatecustomerdetails.do"
			  					linkStyleClass="iCargoLink"
			  					disabledLinkStyleClass="iCargoLink"
			  					displayPage="<%=((MaintainRegCustomerForm)form).getDisplayPage()%>"
			  					totalRecords="<%=String.valueOf(((ArrayList)customerCodes).size())%>" />
						</logic:greaterThan>
					</div>
				</div>
			</logic:present>
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-20">
								<label class="ic-label-30">
									<common:message key="customermanagement.defaults.customerregistration.lbl.custcode" scope="request"/>
									<logic:notEqual name="customerIDGenerationRequired" value="Y">						
										<span class="iCargoMandatoryFieldIcon">*</span>
									</logic:notEqual>
								</label>
								<logic:equal name="customerIDGenerationRequired" value="Y">
									<ihtml:text property="customerCode" 
									maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_NORMAL_CUSTCODE"/>
								</logic:equal>
								<logic:notEqual name="customerIDGenerationRequired" value="Y">						
									<ihtml:text property="customerCode" 
									maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTCODE"/>
								</logic:notEqual>
								<div class="lovImg">
								<img height="22" id="customerlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
							    </div>
							</div>
							<div class="ic-input ic-split-15">
								<label class="ic-label-25">
									<common:message key="customermanagement.defaults.customerregistration.lbl.status" scope="request"/>
								</label>
								<logic:present name="customerVO" property="status">
									<logic:present name="statusValues">
										<bean:define id="statusFromVO" name="customerVO" property="status"/>
										<logic:iterate id="status" 
										name="statusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="status" property="fieldValue">
												<bean:define id="fieldDescription" name="status" property="fieldDescription"/>
												<bean:define id="fieldValue" name="status" property="fieldValue"/>
													<logic:equal name="status" property="fieldValue" value="<%=(String)statusFromVO%>">
														<ihtml:hidden name="customerVO" property="status"/>
														<ihtml:text property="status" disabled="true" value="<%=(String)fieldDescription%>" 
															componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_STATUS"/>
													</logic:equal>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</logic:present>
								<logic:notPresent name="customerVO" property="status">						
									<ihtml:hidden property="status" value="A"/>								
									<ihtml:text property="status" value="Active" 
									componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_STATUS" disabled="true"/>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-10 margint10">
								<div id ="customerAuditVersions">
								
								<logic:present name="customerVO" property="isHistoryPopulated">
									<bean:define id="historyPopulated" name="customerVO" property="isHistoryPopulated"/>
									<logic:equal name="historyPopulated" value="Y">
										<div class="ic-input ic-split-100">
											<label class="ic-label-50">
												<common:message key="customermanagement.defaults.customerregistration.version"/>
											</label>
											<common:directlinknavigationtag
												pageURL="/customermanagement.defaults.relistcustomerdetailshistory.do?statusFlag=LNKNAV"
												displayPage="<%=form.getDisplayPopupPage()%>"
												totalRecords="<%=form.getTotalViewRecords()%>"/>
										</div>			
									</logic:equal>
								</logic:present>
								</div>
							</div>
							<div class="ic-input ic-split-13 margint10">
								<label class="ic-inline">
									<common:message key="customermanagement.defaults.customerregistration.updatedby"/> : 
								</label>
								<span class="ic-bold-value">
									<logic:present name ="customerVO" property="lastUpdatedUser">
										<bean:write name="customerVO" property="lastUpdatedUser"/>
									</logic:present>
								</span>
							</div>
							<div class="ic-input ic-split-6 margint10">
								<label class="ic-inline">
									<common:message key="customermanagement.defaults.customerregistration.updatedat"/> : 
								</label>
								<span class="ic-bold-value">
									<logic:present name ="customerVO" property="lastUpdatedStation">
										<bean:write name="customerVO" property="lastUpdatedStation"/>
									</logic:present>
								</span>
							</div>
							<div class="ic-input ic-split-17 margint10">
								<label class="ic-inline">
									<common:message key="customermanagement.defaults.customerregistration.updatedon"/> : 
								</label>
								<span class="ic-bold-value">
									<logic:present name ="customerVO" property="lastUpdatedTime">
										<bean:define id ="lastUpdatedTime" name ="customerVO" property="lastUpdatedTime" type="LocalDate"/>
										<%String lastUpdtdTime = lastUpdatedTime.toDisplayFormat("dd-MM-yyyy HH:mm:ss");%>
											<%=lastUpdtdTime%>
									</logic:present>
								</span>
							</div>
						
								<div class="ic-button-container">
									<ihtml:nbutton accesskey="L" property="btnList" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_LIST">
										<common:message key="customermanagement.defaults.customerregistration.btn.list" scope="request"/>
									</ihtml:nbutton>
									<ihtml:nbutton accesskey="C" property="btnClear" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CLEAR">
										<common:message key="customermanagement.defaults.customerregistration.btn.clear" scope="request"/>
									</ihtml:nbutton>
								</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		
	<div class="ic-row">
		<div id="container1" class="tab1-container" align="left">		
			<ul class="tabs">
				<li>
					<button type="button" onclick="return showPane(event,'pane4', this)" accesskey="a" class="tab" id="tab.customerdetails">						
						<common:message key="customermanagement.defaults.customerregistration.btn.customerdetails" scope="request"/>
					</button>
				</li>
				<li>
					<button type="button" onclick="return showPane(event,'pane5', this)" accesskey="b" class="tab" id="tab.billingdetails">
						<common:message key="customermanagement.defaults.customerregistration.btn.billingdetails" scope="request"/>
					</button>
				</li>
				 <!-- % if(othersflag){%>	-->
					<li>
						<button type="button" onclick="return showPane(event,'pane6', this)" accesskey="b" class="tab" id="tab.bankdetails">
							<common:message key="shared.agent.maintainagentmaster.btn.bankdetails" scope="request"/>
						</button>
					</li>
				<!--  % }%> -->
			</ul>
			<div class="tab-panes">
				<div id="pane4" class="content">				
					<!-- Modified by A-5220 for ICRD-55852 -->
					<jsp:include page="MaintainCustomerRegistration_CustomerDetailsTab.jsp" />
				</div>
				<div id="pane5" class="content">
					<jsp:include page="MaintainCustomerBillingDetailsTab.jsp" />
				</div>		
				<div id="pane6" class="content">
					<jsp:include page="MaintainCustomerBankDetailsTab.jsp" />
				</div>		
			</div>
		</div>
	</div>
	</div>
					
					<script>
						var versionNumberArray 	   = new Array();
						var navVersionNumberArray  = new Array();
						<% String[] versionNumbers = (String[])form.getVersionNumbers();
						if(versionNumbers != null){
							for(int i=0; i<versionNumbers.length; i++){ %>									           							           
								versionNumberArray[<%= i %>]='<%= versionNumbers[i] %>'; 
							<%}
						}%>	
						<% 
						String[] navVersionNumbers = (String[])form.getNavVersionNumbers();
						if(navVersionNumbers!=null){
							for(int i=0; i<navVersionNumbers.length; i++){ %>									           							           
								navVersionNumberArray[<%= i %>]='<%= navVersionNumbers[i] %>';
							<%}
						}%>	
					</script>
					

	<div class="ic-foot-container">
		<div class="ic-row paddR5">
			<div class="ic-button-container">					
				<!-- Added for IASCB-130291 -->
				<ihtml:nbutton accesskey ="G" property="btnGroupList" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GROUPLIST">
					<common:message key="customermanagement.defaults.customerregistration.btn.grouplist" scope="request"/>
				</ihtml:nbutton>
				<!-- Added as part of CR ICRD-253447 by A-8154 -->
				<ihtml:nbutton accesskey ="I" property="btnActivate" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_ACTIVATE">
					<common:message key="customermanagement.defaults.customerregistration.btn.activate" scope="request"/>
				</ihtml:nbutton>
			  	<ihtml:nbutton accesskey="S" property="btnSave" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_SAVE">
			  		<common:message key="customermanagement.defaults.customerregistration.btn.save" scope="request"/>
			  	</ihtml:nbutton>
				<ihtml:nbutton accesskey="O" property="btnClose" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CLOSE">
					<common:message key="customermanagement.defaults.customerregistration.btn.close" scope="request"/>
				</ihtml:nbutton>
			</div>
		</div>
	</div>		
</div>
</ihtml:form>
</div>

	</body>
</html:html>
