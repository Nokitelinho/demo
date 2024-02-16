<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ModifyLoanDetails.jsp
* Date				: 20-Feb-2006
* Author(s)			: Roopak V.S.

****************************************************** --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		
			
	
<title><common:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.lbl.modifytitle"/></title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/ModifyULDDetails_Script.jsp"/>
</head>

<body>
	
	
	
<business:sessionBean id="ULDTxnDetailsVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="uLDTransactionDetailsVO" />
	<logic:present name="ULDTxnDetailsVOSession">
		<bean:define id="ULDTxnDetailsVOSession" name="ULDTxnDetailsVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="txnTypes" />
	<logic:present name="txnTypesSession">
		<bean:define id="txnTypesSession" name="txnTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="partyTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="partyTypes" />
	<logic:present name="partyTypesSession">
		<bean:define id="partyTypesSession" name="partyTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean
	  id="ULDServiceabilityVOSession"
	  moduleName="uld.defaults"
	  screenID="uld.defaults.loanborrowdetailsenquiry"
	  method="get"
	attribute="ULDServiceabilityVOs" />

<business:sessionBean
	id="ctrlRcptNoPrefix"
	moduleName="uld.defaults"
	screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get"
	attribute="ctrlRcptNoPrefix" />
<business:sessionBean
	id="ctrlRcptNo"
	moduleName="uld.defaults"
	screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get"
	attribute="ctrlRcptNo" />
<business:sessionBean id="systemParameters" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="systemParameters"/>
<bean:define id="form"
	 name="listULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm"
	 toScope="page" />
	 <div class="iCargoContent" style="overflow:auto;width:100%;height:100%">
   <ihtml:form action="/uld.defaults.transaction.screenloadmodifyuldtransactions.do">
   
    <html:hidden property="closeModifyFlag" />
	<html:hidden property="modDisplayPage" />
    <html:hidden property="modCurrentPage" />
    <html:hidden property="modLastPageNum" />
    <html:hidden property="modTotalRecords" />
    <ihtml:hidden property="poolOwnerFlag" />
    <ihtml:hidden property="dummyAirport" />
    <ihtml:hidden property="dummyDate" />
    <ihtml:hidden property="dummyTime" />
    <ihtml:hidden property="dummyDemmurage" />  
    <ihtml:hidden property="loginStation" />  
    <ihtml:hidden property="modFlag" /> 
    <ihtml:hidden property="dummyCRN" /> 
    <ihtml:hidden property="dummyCRNPrefix" /> 
    <ihtml:hidden property="modifiedFlag" />
	<!--Added by A-5782 for ICRD-107212 starts-->
    <logic:present name="systemParameters">
	<logic:iterate  name="systemParameters" id="systemParameterMap">
	<logic:equal  name="systemParameterMap" property="key" value="uld.defaults.dummyreturnstation">
	<bean:define id="defaultDummyStation" name="systemParameterMap" property="value"/>
     <input type="hidden" id="dummyStation" name="dummyStation" value="<%=(String)defaultDummyStation%>"/>
	</logic:equal>
	</logic:iterate>											
	</logic:present >
	<logic:notPresent name="systemParameters">
	  <input type="hidden" id="dummyStation" name="dummyStation" value=""/>
	</logic:notPresent>
	  <!--Added by A-5782 for ICRD-107212 starts-->
	<div class="ic-content-main">
	<span class="ic-page-title"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.modifypagetitle"/></span>
	
	
    <div class="ic-head-container">
		<div class="ic-row">
		<common:popuppaginationtag
			pageURL="javascript:navigateULDDetails('lastPageNum','displayPage')"
			linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
			displayPage="<%=form.getModDisplayPage()%>"
			totalRecords="<%=form.getModTotalRecords()%>" />

		</div>
		<div class="ic-filter-panel">
			<div class="ic-input-container" style="width:66%;">
				<div class="ic-section ic-border">
					<div class="ic-row">
						<div class="ic-input ic-split-20">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldno"/></label>
								<logic:notPresent name="ULDTxnDetailsVOSession" property="uldNumber">
								<ihtml:text property="modULDNo"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_ULDNO" readonly="true" value="" />
								</logic:notPresent>
								<logic:present name="ULDTxnDetailsVOSession" property="uldNumber">
								<bean:define id="txnStationCode" name="ULDTxnDetailsVOSession" property="uldNumber" toScope="page"/>
								<ihtml:text property="modULDNo"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_ULDNO" readonly="true" value="<%=(String)txnStationCode%>" />
								</logic:present>
						</div>
						<div class="ic-input ic-split-20">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txntype"/></label>
								<logic:notPresent name="ULDTxnDetailsVOSession" property="transactionType">
								<ihtml:text property="modTxnType"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_TXNTYPE" readonly="true" value="" />
								</logic:notPresent>
								<logic:present name="ULDTxnDetailsVOSession" property="transactionType">
								<%String txnTypeDesc = "";%>
								<bean:define id="txnType" name="ULDTxnDetailsVOSession" property="transactionType" toScope="page"/>
								<bean:define id="txnTypesSessionColl" name="txnTypesSession" toScope="page" />
								<logic:iterate id="txnTypeVO" name="txnTypesSessionColl" >
								<bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
								<%if(txnType.equals(fieldValue)){%>
								<bean:define id="fieldDesc" name="txnTypeVO" property="fieldDescription" />
								<%txnTypeDesc=(String)fieldDesc;}%>
								</logic:iterate>


								<ihtml:text property="modTxnType"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_TXNTYPE" readonly="true" value="<%=(String)txnTypeDesc%>" />
								</logic:present>
						</div>
						<div class="ic-input ic-split-20">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.partytype"/></label>
								<logic:notPresent name="ULDTxnDetailsVOSession" property="partyType">
								<ihtml:text property="modPartyType"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYTYPE" readonly="true" value="" />
								</logic:notPresent>
								<logic:present name="ULDTxnDetailsVOSession" property="partyType">
								<%String ptyTypeDesc = "";%>
								<bean:define id="PtyType" name="ULDTxnDetailsVOSession" property="partyType" toScope="page"/>
								<bean:define id="partyTypesSessionColl" name="partyTypesSession" toScope="page" />
								<logic:iterate id="partyTypeVO" name="partyTypesSessionColl" >
								<bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
								<%if(PtyType.equals(fieldValue)){%>
								<bean:define id="fieldDesc" name="partyTypeVO" property="fieldDescription" />
								<%ptyTypeDesc=(String)fieldDesc;}%>
								</logic:iterate>


								<ihtml:text property="modPartyType"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYTYPE" readonly="true" value="<%=(String)ptyTypeDesc%>" />
								</logic:present>
						</div>
		  
						<div class="ic-input ic-split-15">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.partycode"/></label>
								<logic:present name="ULDTxnDetailsVOSession" property="transactionType">

								<logic:equal name="ULDTxnDetailsVOSession" property="transactionType" value="L">
								<logic:notPresent name="ULDTxnDetailsVOSession" property="toPartyCode">
									<ihtml:text property="modPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYCODE" readonly="true" value="" />
								</logic:notPresent>
								<logic:present name="ULDTxnDetailsVOSession" property="toPartyCode">
								<bean:define id="ptyCode" name="ULDTxnDetailsVOSession" property="toPartyCode" toScope="page"/>
								<ihtml:text property="modPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYCODE" readonly="true" value="<%=(String)ptyCode%>" />
								</logic:present>
								</logic:equal>

								<logic:equal name="ULDTxnDetailsVOSession" property="transactionType" value="B">
								<logic:notPresent name="ULDTxnDetailsVOSession" property="fromPartyCode">
								<ihtml:text property="modPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYCODE" readonly="true" value="" />
								</logic:notPresent>
								<logic:present name="ULDTxnDetailsVOSession" property="fromPartyCode">
								<bean:define id="ptyCode" name="ULDTxnDetailsVOSession" property="fromPartyCode" toScope="page"/>
								<ihtml:text property="modPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_PARTYCODE" readonly="true" value="<%=(String)ptyCode%>" />
								</logic:present>
								</logic:equal>

								</logic:present>
						</div>
						<div class="ic-input ic-split-25">
											<label>
												<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.leaseenddate"/>
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="strLseEndDate">
										   						  				<ibusiness:calendar id="txnToDateBtn" property="modLseEndDate" title="End of Lease Date" type="image" maxlength="11" tabindex="1" />
										   						  			  </logic:notPresent>
										   						  			  <logic:present name="ULDTxnDetailsVOSession" property="strLseEndDate">
										   						  			  <bean:define id="strLsEndDate" name="ULDTxnDetailsVOSession" property="strLseEndDate" toScope="page"/>
										   						  			  <ibusiness:calendar id="txnToDateBtn"  property="modLseEndDate" title="End of Lease Date" type="image" value="<%=(String)strLsEndDate%>" maxlength="11" />
										   						  			  	</logic:present>
						</div>
						</div>
						<fieldset class="ic-field-set">
								<legend class="iCargoLegend">
									 Txn Details
								</legend>
								
									
										<div class="ic-row">
										<div class="ic-input ic-split-30">
											<label>
												<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txndate"/>
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="strTxnDate">
										   						  				<ibusiness:calendar id="txnToDateBtn" property="modTxnDate" title="Transaction Date" type="image" maxlength="11" tabindex="1" />
										   						  			  <ibusiness:releasetimer  property="modTxnTime" value="" title="Transaction Time"  type="asTimeComponent" id="strTxnTimer"/>
										   						  			  </logic:notPresent>
										   						  			  <logic:present name="ULDTxnDetailsVOSession" property="strTxnDate">
										   						  			  <bean:define id="strTnDate" name="ULDTxnDetailsVOSession" property="strTxnDate" toScope="page"/>
										   						  			  <ibusiness:calendar id="txnToDateBtn"  property="modTxnDate" title="Transaction Date" type="image" value="<%=(String)strTnDate%>" maxlength="11" />
										   						  			  	</logic:present>
										   						  			   <logic:present name="ULDTxnDetailsVOSession" property="strTxnTime">
										   						  			  <bean:define id="txnTime" name="ULDTxnDetailsVOSession" property="strTxnTime" toScope="page"/>
										   						  			  <ibusiness:releasetimer  property="modTxnTime" value="<%=(String)txnTime%>" title="Transaction Time"  type="asTimeComponent" id="strTxnTimer"/>
										   						  			  </logic:present>
										</div>
										<div class="ic-input ic-split-20">
											<label>
											 <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnstation"/>
											</label>
											<logic:notPresent name="ULDTxnDetailsVOSession" property="transactionStationCode">
										   			  <ihtml:text property="txnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATION" value=""/>
										   			  </logic:notPresent>
										   			  <logic:present name="ULDTxnDetailsVOSession" property="transactionStationCode">
										   			  <bean:define id="txnStationCode" name="ULDTxnDetailsVOSession" property="transactionStationCode" toScope="page"/>
										   			  <ihtml:text property="txnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATION" value="<%=(String)txnStationCode%>"/>
										   			  </logic:present>
										</div>
										<div class="ic-input ic-split-20">
											<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.desairport"/>
											</label>
											<logic:notPresent name="ULDTxnDetailsVOSession" property="txStationCode">

										   			  <ihtml:text property="desStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_DESAIRPORT" value=""/>
										   			  </logic:notPresent>
										   			  <logic:present name="ULDTxnDetailsVOSession" property="txStationCode">

										   			  <bean:define id="desStationCode" name="ULDTxnDetailsVOSession" property="txStationCode" toScope="page"/>
										   			  <ihtml:text property="desStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_DESAIRPORT" value="<%=(String)desStationCode%>"/>
										   			  </logic:present>
										</div>	
										<div class="ic-input ic-split-30">
											<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnremarks"/>
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="transactionRemark">
										   			                                <html:textarea property="modTxnRemarks" title="Transaction Remarks" value="" cols="15" rows="2" onblur="validateMaxLength(this,100)" tabindex="3" ></html:textarea>
										   			  			   </logic:notPresent>
										   			  			   <logic:present name="ULDTxnDetailsVOSession" property="transactionRemark">
										   			  			     <bean:define id="txnRemark" name="ULDTxnDetailsVOSession" property="transactionRemark" toScope="page"/>
										   			  			     <html:textarea property="modTxnRemarks" title="Transaction Remarks"   cols="15" rows="2" onblur="validateMaxLength(this,100)" value="<%=(String)txnRemark%>" tabindex="3" ></html:textarea>
										   			   </logic:present>
										</div>	
										</div>
										
										
										</fieldset>	

					<div class="ic-row">
						<div class="ic-input ic-split-23">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.crnNumber"/></label>
								 <logic:present name="ctrlRcptNo">
									<bean:define id="ctrlRcptNo" name="ctrlRcptNo" />
									<logic:present name="ctrlRcptNoPrefix">
									<bean:define id="ctrlRcptNoPrefix" name="ctrlRcptNoPrefix" />
									<ihtml:text property="modCrnPrefix" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN_PREFIX" value="<%=(String)ctrlRcptNoPrefix%>" readonly="true"/>
									</logic:present>
								<logic:notPresent name="ctrlRcptNoPrefix">
								<ihtml:text property="modCrnPrefix" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN_PREFIX" value="" readonly="true"/>
							</logic:notPresent>
						
								-<ihtml:text property="modCRN" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN" value="<%=(String)ctrlRcptNo%>" maxlength="8"/>
				   
							</logic:present>
							<logic:notPresent name="ctrlRcptNo">
								<bean:define id="ctrlRcptNo" name="ctrlRcptNo" />
								<logic:present name="ctrlRcptNoPrefix">
							<bean:define id="ctrlRcptNoPrefix" name="ctrlRcptNoPrefix" />
							<ihtml:text property="modCrnPrefix" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN_PREFIX" value="<%=(String)ctrlRcptNoPrefix%>" readonly="true"/>
							</logic:present>
							<logic:notPresent name="ctrlRcptNoPrefix">
							<ihtml:text property="modCrnPrefix" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN_PREFIX" value="" readonly="true"/>
							</logic:notPresent>
							-<ihtml:text property="modCRN" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CRN" value="" maxlength="8"/>
							</logic:notPresent> 
						</div>
						<div class="ic-input ic-split-22">
								<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldcondition"/></label>
								 <logic:present name="ULDServiceabilityVOSession">
							<ihtml:select property="modUldCondition" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_ULDCONDITION">
								<bean:define id="ULDServiceabilityVO" name="ULDServiceabilityVOSession"/>
								<ihtml:options collection="ULDServiceabilityVO" property="code" labelProperty="code" />
							</ihtml:select>
						</logic:present>

						</div>
						<div class="ic-input ic-split-55">
								<label> <common:message key="uld.defaults.loanBorrowenquiry.lbl.awb" /></label>
								<logic:notPresent name="form" property="modAwbNumber">
					  <ihtml:textarea property="modAwbNumber" tabindex="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWEQUIRY_AWBNUM" value="" cols="50" rows="2"></ihtml:textarea>
					 </logic:notPresent>
					 <logic:present name="form" property="modAwbNumber">
					  <bean:define id="modAwbNumber" name="form" property="modAwbNumber" toScope="page"/>
					 	 <ihtml:textarea property="modAwbNumber" tabindex="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWEQUIRY_AWBNUM" value="<%=(String)modAwbNumber%>" cols="50" rows="2"  ></ihtml:textarea>
					 </logic:present>
						</div>
		  
						
						</div>
						<fieldset class="ic-field-set">
								<legend class="iCargoLegend">
									 Rtn Details
								</legend>
								
								<%String txnStatusVal = "";%>
			 				     					 <logic:present name="ULDTxnDetailsVOSession" property="transactionStatus">
												   <bean:define id="txnStatus" name="ULDTxnDetailsVOSession" property="transactionStatus" toScope="page"/>
												   <%txnStatusVal = (String)txnStatus;%>
												 </logic:present>	
												 <input type="hidden" name="hiddenTxnStatus" value="<%=(String)txnStatusVal%>"/>
								<div class="ic-row">
									<div class="ic-input ic-split-16">
										<logic:equal name="form" property="dummy" value="Y">
											<input type="checkbox" name="dummy" title="Dummy" checked="checked" value="on"/>
										</logic:equal>
										<logic:notEqual name="form" property="dummy" value="Y">
											<input type="checkbox" name="dummy" title="Dummy" value="off"/>
										</logic:notEqual>
										<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.dummy"/>
										</label>
									</div>
									<div class="ic-input ic-split-33">
											<label>
											 <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returndate"/>
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="strRetDate">
													  			 			  <ibusiness:calendar property="modRtnDate" title="Return Date" type="image" maxlength="11" value="" id="modRtnDate" />
													  			 			  </logic:notPresent>
													  			 			  <logic:notPresent name="ULDTxnDetailsVOSession" property="strRetTime">
													  			 			  <ibusiness:releasetimer  property="modRtnTime" value="" title="Return Time"  type="asTimeComponent" id="returnTime" />
													  			 			  </logic:notPresent>
													  			 			  <logic:present name="ULDTxnDetailsVOSession" property="strRetDate">
										   						  			  <bean:define id="retDate" name="ULDTxnDetailsVOSession" property="strRetDate" toScope="page"/>
										   						  			  <ibusiness:calendar id="modRtnDate"  property="modRtnDate" title="Return Date" type="image" value="<%=(String)retDate%>" maxlength="11" />
										   						  			  </logic:present>
										   						  			  <logic:present name="ULDTxnDetailsVOSession" property="strRetTime">
										   						  			  <bean:define id="retTime" name="ULDTxnDetailsVOSession" property="strRetTime" toScope="page"/>
										   						  			  <ibusiness:releasetimer  property="modRtnTime" value="<%=(String)retTime%>" title="Return Time"  type="asTimeComponent" id="strRtnTimer"/>
										   						  			  </logic:present>
									</div>
									<div class="ic-input ic-split-22">
											<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returnstation"/>													  			 			  
													  			 			  
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="returnStationCode">
													  			 			  <ihtml:text property="returnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_RETURN_RETSTATION" value=""/>
																			  <!-- Modified by A-7426 as part of ICRD-200751 starts-->
													  			 			 <img id="returnStationLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
													  			 			  </logic:notPresent>
													  			 			  <logic:present name="ULDTxnDetailsVOSession" property="returnStationCode">
													  			 			  <bean:define id="rtnStation" name="ULDTxnDetailsVOSession" property="returnStationCode" toScope="page"/>
													  			 			  <ihtml:text property="returnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_RETURN_RETSTATION" value="<%=(String)rtnStation%>"/>
													  			 			 <img id="returnStationLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
																			 <!-- Modified by A-7426 as part of ICRD-200751 ends-->
													  			 			  </logic:present>
											
									</div>	
									<div class="ic-input ic-split-25">
										<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returnremarks"/>
										</label>
										 <logic:notPresent name="ULDTxnDetailsVOSession" property="returnRemark">
													  			              <html:textarea property="rtnRemarks" title="Return Remarks" value="" cols="15" rows="2" onblur="validateMaxLength(this,100)"></html:textarea>
													  			              </logic:notPresent>
													  			              <logic:present name="ULDTxnDetailsVOSession" property="returnRemark">
													  			              <bean:define id="returnRmk" name="ULDTxnDetailsVOSession" property="returnRemark" toScope="page"/>
													  			              <html:textarea property="rtnRemarks" title="Return Remarks" onblur="validateMaxLength(this,100)" value="<%=(String)returnRmk%>"/>
										   						  			  </logic:present>	
									</div>	
								</div>
								<div class="ic-row">
									<div class="ic-input ic-split-18">
										
										<label>
											 <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returndemurrage"/>
										</label>
										 <logic:notPresent name="ULDTxnDetailsVOSession" property="demurrageAmount">
						                           <ihtml:text property="rtndemurrage" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETDEMURRAGE" value=""/>
						                           </logic:notPresent>
						                           <logic:present name="ULDTxnDetailsVOSession" property="demurrageAmount">
						                           <bean:define id="demurrage" name="ULDTxnDetailsVOSession" property="demurrageAmount" toScope="page"/>
						                           <ihtml:text property="rtndemurrage" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETDEMURRAGE" value="<%=String.valueOf(demurrage)%>"/>
						                           </logic:present>
									</div>
									<div class="ic-input ic-split-35">
											<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returnwaived"/>
											</label>
											<logic:notPresent name="ULDTxnDetailsVOSession" property="waived">
						                           <ihtml:text property="rtnwaived" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETWAIVED" value=""/>
						                           </logic:notPresent>
						                           <logic:present name="ULDTxnDetailsVOSession" property="waived">
						                           <bean:define id="waiver" name="ULDTxnDetailsVOSession" property="waived" toScope="page"/>
						                           <ihtml:text property="rtnwaived" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETWAIVED" value="<%=String.valueOf(waiver)%>"/>
						                           </logic:present>
									</div>
									<div class="ic-input ic-split-21">
											<label>
											<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returntaxes"/>										  			 			  
													  			 			  
											</label>
											 <logic:notPresent name="ULDTxnDetailsVOSession" property="taxes">
						                     <ihtml:text property="rtntaxes" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETTAXES" value=""/>
						                      </logic:notPresent>
						                      <logic:present name="ULDTxnDetailsVOSession" property="taxes">
						                      <bean:define id="tax" name="ULDTxnDetailsVOSession" property="taxes" toScope="page"/>
						                      <ihtml:text property="rtntaxes" maxlength="8" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_RETTAXES" value="<%=String.valueOf(tax)%>"/>
						                    </logic:present>
									</div>	
									<div class="ic-input ic-split-25">
										<label>
										<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.currency"/>
										</label>
										 <logic:notPresent name="ULDTxnDetailsVOSession" property="currency">
										<ihtml:text property="currency" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CURRENCY" readonly="true" value=""/>
										</logic:notPresent>
										<logic:present name="ULDTxnDetailsVOSession" property="currency">
										<bean:define id="currencyCode" name="ULDTxnDetailsVOSession" property="currency" toScope="page"/>
										<ihtml:text property="currency" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CURRENCY" readonly="true" value="<%=String.valueOf(currencyCode)%>"/>
										</logic:present>
									</div>	
								</div>		
										
								</fieldset>	
                   
		      <div class="ic-row">
						<div class="ic-input ic-split-80">
								&nbsp;			
						</div>	
						<div class="ic-input ic-split-18">
                         <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_OK" >
                             <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.ok"/>
                         </ihtml:nbutton>
                         <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_MODIFY_CLOSE">
                             <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.close"/>
                         </ihtml:nbutton>
						 </div>	
                      
                </div> 

  </ihtml:form>
  </div> 
    </div> 
	  </div> 
	    </div> 
 </div> 
			

	</body>
</html:html>

