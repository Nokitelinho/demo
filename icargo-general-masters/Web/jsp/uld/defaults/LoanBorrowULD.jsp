<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: LoanBorrowULD.jsp
* Date				: 08-Feb-2006
* Author(s)			: Roopak V.S.

****************************************************** --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html:html locale="true">
<head>
<title><common:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.lbl.loanborrowtitle"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/uld/defaults/LoanBorrowULD_Script.jsp"/>
</head>

<body id="bodyStyle">
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowuld"
		method="get"
		attribute="oneTimeValues" />

			<business:sessionBean
	  			id="uldNature"
	  			moduleName="uld.defaults"
	  			screenID="uld.defaults.loanborrowuld"
	  			method="get"
				attribute="uldNature" />

				<business:sessionBean
	  			id="uldCondition"
	  			moduleName="uld.defaults"
	  			screenID="uld.defaults.loanborrowuld"
	  			method="get"
				attribute="conditionCodes" />



<business:sessionBean id="transactionVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="transactionVO" />
	<logic:present name="transactionVOSession">
		<bean:define id="transactionVOSession" name="transactionVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="txnTypes" />
	<logic:present name="txnTypesSession">
		<bean:define id="txnTypesSession" name="txnTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnNaturesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="txnNatures" />
	<logic:present name="txnNaturesSession">
		<bean:define id="txnNaturesSession" name="txnNaturesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="partyTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="partyTypes" />
	<logic:present name="partyTypesSession">
		<bean:define id="partyTypesSession" name="partyTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="accCodesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="accessoryCodes" />
	<logic:present name="accCodesSession">
		<bean:define id="accCodesSession" name="accCodesSession" toScope="page"/>
	</logic:present>


<bean:define id="form"
      name="maintainULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm"
      toScope="page" />


 <div class="iCargoContent ic-masterbg" style="overflow:auto;height:100%" >

<ihtml:form action="/uld.defaults.transaction.screenloadloanborrowuld.do">
<input type="hidden" name="txnTypeBasis" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="txnTypeDisable" />
<ihtml:hidden property="damageULD" />
<ihtml:hidden property="agreementFlag" />
<ihtml:hidden property="closeStatus" />
<ihtml:hidden property="forDamage" />
<ihtml:hidden property="lucEnable" />
<ihtml:hidden property="ispopup" />
<ihtml:hidden property="comboFlag"/>
<ihtml:hidden property="pageurl"/>
<ihtml:hidden property="disableStatus"/>
<ihtml:hidden property="saveStatus"/>
<ihtml:hidden property="lucPopup"/>
<ihtml:hidden property="airlineCode"/>
<ihtml:hidden property="airlineName"/>
<ihtml:hidden property="printUCR"/>
<ihtml:hidden property="msgFlag"/>
<ihtml:hidden property="errorStatusFlag"/>
<ihtml:hidden property="partyCode"/>
<ihtml:hidden property="partyName"/>
<ihtml:hidden property="showDamage"/>
<ihtml:hidden property="uldNumbersSelected"/>
<ihtml:hidden property="agreementFound"/>
<ihtml:hidden property="dummyTxnAirport"/>
<ihtml:hidden property="stationCode"/>
 <ihtml:hidden property="isInvalidUldsPresent"/>
<ihtml:hidden property="screenInfo"/>
<ihtml:hidden property="warningMsgStatus"/>
<ihtml:hidden property="fromPopup" />


<div class="ic-content-main">
	<div class="ic-head-container">
		<div class="ic-filter-panel">
						
			<div class="ic-input-container">
				
				<fieldset class="ic-field-set">
					<legend>
						<common:message key="uld.defaults.loanBorrowULD.lbl.txndetails"/>
					</legend>
				<div class="ic-row ">
					<div class="ic-input ic-split-20 ic-mandatory ic-label-30">
						<label>
						<common:message key="uld.defaults.loanBorrowULD.lbl.txntype"/>
						</label>
						<logic:present name="transactionVOSession"  property="transactionType">
							<bean:define id="transactionType" name="transactionVOSession"  property="transactionType"  toScope="page"/>
							<ihtml:select  property="transactionType"
								  componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_TXNTYPE" value="<%=(String)transactionType%>">
								  <%
									String txnTyp = "";
									String txnTypVal = "";
								  %>
										 <logic:present name="transactionVOSession"  property="transactionType">
										  <bean:define id="transactionTypeInVO" name="transactionVOSession"  property="transactionType"  toScope="page"/>
										  <bean:define id="transactionTypeInSess" name="txnTypesSession" toScope="page" />
									<logic:iterate id="txnSessVO" name="transactionTypeInSess" >
										  <bean:define id="fieldValue" name="txnSessVO" property="fieldValue" />
										  <%if(fieldValue.equals(transactionTypeInVO)){%>
									  <bean:define id="fieldDesc" name="txnSessVO" property="fieldDescription" />
									  <%
									  txnTyp = (String)fieldDesc ;
									  txnTypVal = (String)fieldValue ;
									  }%>
									</logic:iterate>
										  <html:option value="<%=(String)txnTypVal%>"><%=(String)txnTyp%></html:option>
										 </logic:present>
										 <logic:notPresent name="transactionVOSession"  property="transactionType">
										 </logic:notPresent>
										  <bean:define id="txnTypeColln" name="txnTypesSession" toScope="page" />
											  <logic:iterate id="txnTypeVO" name="txnTypeColln" >

														<bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
										<%if(!txnTypVal.equals(fieldValue)){%>
														<html:option value="<%=(String)fieldValue %>"><bean:write name="txnTypeVO" property="fieldDescription" /></html:option>
												<%}%>
											 </logic:iterate>
								 </ihtml:select>
							</logic:present>
							<logic:notPresent name="transactionVOSession"  property="transactionType">
								<ihtml:select  property="transactionType" 
									  componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_TXNTYPE"  >
									  <%
										String txnTyp = "";
										String txnTypVal = "";
									  %>
											 <logic:present name="transactionVOSession"  property="transactionType">
											  <bean:define id="transactionTypeInVO" name="transactionVOSession"  property="transactionType"  toScope="page"/>
											  <bean:define id="transactionTypeInSess" name="txnTypesSession" toScope="page" />
										<logic:iterate id="txnSessVO" name="transactionTypeInSess" >
											  <bean:define id="fieldValue" name="txnSessVO" property="fieldValue" />
											  <%if(fieldValue.equals(transactionTypeInVO)){%>
										  <bean:define id="fieldDesc" name="txnSessVO" property="fieldDescription" />
										  <%
										  txnTyp = (String)fieldDesc ;
										  txnTypVal = (String)fieldValue ;
										  }%>
										</logic:iterate>
											  <html:option value="<%=(String)txnTypVal%>"><%=(String)txnTyp%></html:option>
											 </logic:present>
											 <logic:notPresent name="transactionVOSession"  property="transactionType">
											 </logic:notPresent>
											  <bean:define id="txnTypeColln" name="txnTypesSession" toScope="page" />
												  <logic:iterate id="txnTypeVO" name="txnTypeColln" >

															<bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
											<%if(!txnTypVal.equals(fieldValue)){%>
															<html:option value="<%=(String)fieldValue %>"><bean:write name="txnTypeVO" property="fieldDescription" /></html:option>
													<%}%>
												 </logic:iterate>
								 </ihtml:select>
							</logic:notPresent>
						</div>
					
					<div class="ic-input ic-mandatory ic-split-15 ic-label-30">
						<label>
						<common:message key="uld.defaults.loanBorrowULD.lbl.txnnature"/>
						</label>
						<logic:present name="transactionVOSession"  property="transactionNature">
				<bean:define id="transactionNature" name="transactionVOSession"  property="transactionNature"  toScope="page"/>
				<ihtml:select property="transactionNature"  componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_TXNNATURE"  value="<%=(String)transactionNature%>">
				  <%
					String txnNtr = "";
					String txnNtrVal = "";
				  %>
						 <logic:present name="transactionVOSession"  property="transactionNature">
						  <bean:define id="transactionNatureInVO" name="transactionVOSession"  property="transactionNature" toScope="page"/>
						  <bean:define id="transactionNatureInSess" name="txnNaturesSession" toScope="page" />
					<logic:iterate id="txnNtrSessVO" name="transactionNatureInSess" >
						  <bean:define id="fieldValue" name="txnNtrSessVO" property="fieldValue" />
						  <%if(fieldValue.equals(transactionNatureInVO)){%>
					  <bean:define id="fieldDesc" name="txnNtrSessVO" property="fieldDescription" />
					  <%
					  txnNtr = (String)fieldDesc ;
					  txnNtrVal = (String)fieldValue ;
					  }%>
					</logic:iterate>
						  <html:option value="<%=(String)txnNtrVal%>"><%=(String)txnNtr%></html:option>
						 </logic:present>
						 <logic:notPresent name="transactionVOSession"  property="transactionNature">
						 </logic:notPresent>
						  <bean:define id="txnNatureColln" name="txnNaturesSession" toScope="page" />
							  <logic:iterate id="txnNatureVO" name="txnNatureColln" >

										<bean:define id="fieldValue" name="txnNatureVO" property="fieldValue" toScope="page" />
						<%if(!txnNtrVal.equals(fieldValue)){%>
										<html:option value="<%=(String)fieldValue %>"><bean:write name="txnNatureVO" property="fieldDescription" /></html:option>
								<%}%>
							 </logic:iterate>
				 </ihtml:select>
				 </logic:present>

				  <logic:notPresent name="transactionVOSession"  property="transactionNature">
					  <ihtml:select property="transactionNature" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_TXNNATURE" >
						  <%
							String txnNtr = "";
							String txnNtrVal = "";
						  %>
								 <logic:present name="transactionVOSession"  property="transactionNature">
								  <bean:define id="transactionNatureInVO" name="transactionVOSession"  property="transactionNature" toScope="page"/>
								  <bean:define id="transactionNatureInSess" name="txnNaturesSession" toScope="page" />
							<logic:iterate id="txnNtrSessVO" name="transactionNatureInSess" >
								  <bean:define id="fieldValue" name="txnNtrSessVO" property="fieldValue" />
								  <%if(fieldValue.equals(transactionNatureInVO)){%>
							  <bean:define id="fieldDesc" name="txnNtrSessVO" property="fieldDescription" />
							  <%
							  txnNtr = (String)fieldDesc ;
							  txnNtrVal = (String)fieldValue ;
							  }%>
							</logic:iterate>
								  <html:option value="<%=(String)txnNtrVal%>"><%=(String)txnNtr%></html:option>
								 </logic:present>
								 <logic:notPresent name="transactionVOSession"  property="transactionNature">
								 </logic:notPresent>
								  <bean:define id="txnNatureColln" name="txnNaturesSession" toScope="page" />
									  <logic:iterate id="txnNatureVO" name="txnNatureColln" >

												<bean:define id="fieldValue" name="txnNatureVO" property="fieldValue" toScope="page" />
								<%if(!txnNtrVal.equals(fieldValue)){%>
												<html:option value="<%=(String)fieldValue %>"><bean:write name="txnNatureVO" property="fieldDescription" /></html:option>
										<%}%>
									 </logic:iterate>
						 </ihtml:select>
				 </logic:notPresent>
							</div>	
							<div class="ic-input ic-mandatory ic-split-15">
								<label>
								<common:message key="uld.defaults.loanBorrowULD.lbl.txnstation"/>
								</label>
							
								<logic:notPresent name="transactionVOSession" property="transactionStation">
								 <ihtml:text property="transactionStation"  maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TXNSTATION" value="" />
							  </logic:notPresent>
							  <logic:present name="transactionVOSession" property="transactionStation">
								 <bean:define id="txnStation" name="transactionVOSession" property="transactionStation" toScope="page"/>
								 <ihtml:text property="transactionStation"  maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TXNSTATION" value="<%=(String)txnStation%>"  />
							  </logic:present>
							  <div class="lovImg">
							  <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" id="transactionStationImg" 
							  name="transactionStationImg" 
							  onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].transactionStation.value,'transactionStation','1','transactionStation','',0)" alt="Airport LOV"/>
							</div>	
							</div>	
					<div class="ic-input ic-mandatory ic-split-15">
						<label>
						<common:message key="uld.defaults.loanBorrowULD.lbl.txndate"/>
						</label>
						<logic:notPresent name="transactionVOSession" property="strTransactionDate" >
						  <ibusiness:calendar id="transactionDate"  property="transactionDate"  componentID="ULD_DEFAULTS_LOANBORROWULD_CALENDAR1" type="image" maxlength="11" value=""/>
						  </logic:notPresent>
						  <logic:present name="transactionVOSession" property="strTransactionDate">
							  <bean:define id="txnDate" name="transactionVOSession" property="strTransactionDate" toScope="page"/>
							  <ibusiness:calendar id="transactionDate"  property="transactionDate"  componentID="ULD_DEFAULTS_LOANBORROWULD_CALENDAR1" type="image" maxlength="11" value="<%=(String)txnDate%>"/>
						  </logic:present>
					</div>	
							
					<div class="ic-input ic-mandatory ic-split-15">
						<label>
						<common:message key="uld.defaults.loanBorrowULD.lbl.txntime"/>
						</label>
					 <logic:notPresent name="transactionVOSession" property="transactionTime" >
						  <ibusiness:releasetimer  property="transactionTime" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TXNTIME" title="Transaction Time"  type="asTimeComponent" value=""  id="transactionTime"  />
					  </logic:notPresent>
					  <logic:present name="transactionVOSession" property="transactionTime">
						  <bean:define id="transactionTime" name="transactionVOSession" property="transactionTime" toScope="page"/>
						   <ibusiness:releasetimer  property="transactionTime" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TXNTIME" title="Transaction Time"  type="asTimeComponent" id="transactionTime"  value="<%=(String)transactionTime%>"  />
					  </logic:present>
					</div>	
					<div class="ic-input ic-split-15">
						<label>
						<common:message key="uld.defaults.loanBorrowULD.lbl.leaseenddate"/>
						</label>
						<logic:notPresent name="transactionVOSession" property="strLeaseEndDate" >
						  <ibusiness:calendar id="leaseEndDate" property="leaseEndDate"  componentID="ULD_DEFAULTS_LOANBORROWULD_LSEENDDAT" type="image" maxlength="11" value=""/>
						</logic:notPresent>
						<logic:present name="transactionVOSession" property="strLeaseEndDate">
							  <bean:define id="lseEndDate" name="transactionVOSession" property="strLeaseEndDate" toScope="page"/>
							  <ibusiness:calendar id="leaseEndDate"  property="leaseEndDate"  componentID="ULD_DEFAULTS_LOANBORROWULD_LSEENDDAT" type="image" maxlength="11" value="<%=(String)lseEndDate%>"/>
						</logic:present>
				</div>	
				</div>	
				
				<div class="ic-row">
					<div class="ic-input  ic-label-30 ic-mandatory ic-split-20">
						<label>
							<common:message key="uld.defaults.loanBorrowULD.lbl.partytype"/>
						</label>
						
						<ihtml:select property="partyType"  componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_PARTYTYPE"  >
							<%
							String ptyTyp = "";
							String ptyTypVal = "";
							%>
							<logic:present name="transactionVOSession"  property="partyType">
							<bean:define id="partyTypeInVO" name="transactionVOSession"  property="partyType" toScope="page"/>
							<bean:define id="partyTypeInSess" name="partyTypesSession" toScope="page" />
							<logic:iterate id="partySessVO" name="partyTypeInSess" >
							<bean:define id="fieldValue" name="partySessVO" property="fieldValue" />
							<%if(fieldValue.equals(partyTypeInVO)){%>
							<bean:define id="fieldDesc" name="partySessVO" property="fieldDescription" />
							<%
							ptyTyp = (String)fieldDesc ;
							ptyTypVal = (String)fieldValue ;
							}%>
							</logic:iterate>
							<html:option value="<%=(String)ptyTypVal%>"><%=(String)ptyTyp%></html:option>
							</logic:present>
							<logic:notPresent name="transactionVOSession"  property="partyType">
							</logic:notPresent>
							<bean:define id="partyTypeColln" name="partyTypesSession" toScope="page" />
							<logic:iterate id="partyTypeVO" name="partyTypeColln" >

							<bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
							<%if(!ptyTypVal.equals(fieldValue)){%>
							<html:option value="<%=(String)fieldValue %>"><bean:write name="partyTypeVO" property="fieldDescription" /></html:option>
							<%}%>
							</logic:iterate>
						</ihtml:select>
					</div>	
					<div class="ic-input ic-split-60 ">
						<label>
							<common:message key="uld.defaults.loanBorrowULD.lbl.txnremarks"/>
						</label>
						 <logic:notPresent name="transactionVOSession" property="transactionRemark">
							<ihtml:textarea property="transactionRemarks" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_REMARKS" value="" cols="80" rows="2" onblur="validateMaxLength(this,65)" ></ihtml:textarea>

							</logic:notPresent>
							 <logic:present name="transactionVOSession">	     
							 <logic:present name="transactionVOSession" property="transactionRemark">
								 <bean:define id="txnRemark" name="transactionVOSession" property="transactionRemark" toScope="page"/>
								 <ihtml:textarea property="transactionRemarks"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_REMARKS" value="<%=(String)txnRemark%>" cols="80" rows="2" onblur="validateMaxLength(this,65)" ></ihtml:textarea>
							 </logic:present>
							</logic:present>
					</div>
					
						
					
					<div class="ic-input ic-split-20">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_CLEAR" >
								<common:message key="uld.defaults.loanBorrowULD.btn.clear" />
							</ihtml:nbutton>
						</div>
					</div>	
				</div>	
					
					
				</fieldset>
			</div>
						</div>
	</div>
	
	<div class="ic-main-container">
		<div class="ic-row">
			<h4><common:message key="uld.defaults.loanBorrowULD.lbl.partydetails"/></h4>
		</div>
		<div class="ic-row ">
			<div class="ic-border">
			<div class="ic-col ic-center" style="width:50%">
				<fieldset class="ic-field-set">
					<legend class="iCargoLegend">
						From Party
					</legend>
					<div class="ic-row">
					<div class="ic-input ic-split-30" id="fromshortcodeLable">
						<label>
							<common:message key="uld.defaults.loanBorrowULD.tooltip.shortcode" />
						</label>
						<logic:present name="transactionVOSession" property="fromShortCode">
						<bean:define id="fromShortCode" name="transactionVOSession" property="fromShortCode" toScope="page"/>
							<ihtml:text property="fromShortCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_FROM_SHORTCODE" value="" maxlength="10"/>
						</logic:present>
						<logic:notPresent name="transactionVOSession" property="toShortCode">
							<ihtml:text property="fromShortCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_FROM_SHORTCODE" value="" maxlength="10"/>
						</logic:notPresent>
					</div>	
					<div class="ic-input ic-mandatory ic-split-30">
						<label>
							Code
						</label>
						<logic:notPresent name="transactionVOSession" property="fromPartyCode">
						<ihtml:text property="fromPartyCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_PARTYCODE" value="" />
						<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="fromairlinelov" id="fromairlinelov" alt="Airline LOV" />
						 </div>
						</logic:notPresent>
						<logic:present name="transactionVOSession" property="fromPartyCode">
						<bean:define id="ptyCode" name="transactionVOSession" property="fromPartyCode" toScope="page"/>
						<ihtml:text property="fromPartyCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_PARTYCODE" value="<%=(String)ptyCode%>" />
						 <div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="fromairlinelov" id="fromairlinelov" alt="Airline LOV" />
						</div>
						</logic:present>
					</div>
					
					<div class="ic-input ic-split-40">
						<label>
							Name
						</label>
						<logic:notPresent name="transactionVOSession" property="fromPartyName">
							<ihtml:text property="fromPartyName"   maxlength="25" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_PARTYNAME" value="" style="width:185px" />
						</logic:notPresent>
						<logic:present name="transactionVOSession" property="fromPartyName">
							<bean:define id="ptyName" name="transactionVOSession" property="fromPartyName" toScope="page"/>
							<ihtml:text property="fromPartyName"  maxlength="25" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_PARTYNAME" value="<%=(String)ptyName%>" style="width:185px" />
						</logic:present>
					</div>	
					</div>	
				</fieldset>	
			</div>
			<div class="ic-col ic-center" style="width:49%">
				<fieldset class="ic-field-set">
					<legend class="iCargoLegend">
						To Party
					</legend>
					<div class="ic-row">
					<div class="ic-input ic-split-30" id="shortcodeLable">
						<label>
							<common:message key="uld.defaults.loanBorrowULD.tooltip.shortcode" />
						</label>
						<logic:present name="transactionVOSession" property="toShortCode">
						<bean:define id="toShrtCode" name="transactionVOSession" property="toShortCode" toScope="page"/>
							<ihtml:text property="toShortCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TO_SHORTCODE" value=""  maxlength="10"/>
						</logic:present>
						<logic:notPresent name="transactionVOSession" property="toShortCode">
							<ihtml:text property="toShortCode"   componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TO_SHORTCODE" value=""  maxlength="10"/>
						</logic:notPresent>
					</div>	
					<div class="ic-input ic-mandatory ic-split-30">
						<label>
							Code
						</label>
						<logic:notPresent name="transactionVOSession" property="toPartyCode">
						<ihtml:text property="toPartyCode" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TOPARTYCODE" value="" maxlength="15" />
						<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"  name="toairlinelov" id="toairlinelov" alt="Airline LOV" />
						 </div>
						</logic:notPresent>
						<logic:present name="transactionVOSession" property="toPartyCode">
						<bean:define id="ptyCode" name="transactionVOSession" property="toPartyCode" toScope="page"/>
						<ihtml:text property="toPartyCode" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TOPARTYCODE" value="<%=(String)ptyCode%>" maxlength="15"/>
						<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"  name="toairlinelov" id="toairlinelov" alt="Airline LOV" />
						 </div>
						</logic:present>
					</div>
					
					<div class="ic-input ic-split-40">
						<label>
							Name
						</label>
						<logic:notPresent name="transactionVOSession" property="toPartyName">
						<ihtml:text property="toPartyName"   maxlength="25" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TOPARTYNAME" value="" style="width:185px" />
						</logic:notPresent>
						<logic:present name="transactionVOSession" property="toPartyName">
						<bean:define id="ptyName" name="transactionVOSession" property="toPartyName" toScope="page"/>
						<ihtml:text property="toPartyName"   maxlength="25" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_TOPARTYNAME" style="width:185px"/>
						</logic:present>
					</div>	
					</div>
				</fieldset>		
			</div>
		</div>
		</div>
	
	<div class="ic-row">
		<div class="ic-col" style="width:70%">
			<jsp:include page="LoanBorrowULD_ULDDetails.jsp" />
		</div>
		
		<div class="ic-col" style="width:30%">
			<h4><common:message key="uld.defaults.loanBorrowULD.lbl.accessorydetails"/></h4>
			<div class="ic-row "><div class="ic-border">
			<div class="ic-row ic-right">
                <a href="javascript:void(0)" id="addLinkAcc" class="iCargoLink" onclick="addAccDetails1()" ><common:message key="uld.defaults.loanBorrowULD.lnk.add"/></a>
 			    |
 			    <a href="javascript:void(0)" id="deleteLinkAcc" class="iCargoLink" onclick="delAccDetails1()" ><common:message key="uld.defaults.loanBorrowULD.lnk.delete"/></a>
 			</div>
			<div class="ic-row">
			<div id="parentaccessorydiv"  class="tableContainer" style="height:200px">					
				<table class="fixed-header-table">
				<thead>
					<tr class="iCargoTableHeadingLeft">
 		                <td width="5%" class="iCargoTableHeader" > <input type="checkbox" name="masterAcc"  onclick="updateHeaderCheckBox(this.form,this.form.masterAcc,this.form.childAcc)" style="text-align:left" /></td>
						<td width="45%"><common:message key="uld.defaults.loanBorrowULD.lbl.accessorycode"/></td>
						<td width="50%"><common:message key="uld.defaults.loanBorrowULD.lbl.quantity"/><span class="iCargoMandatoryFieldIcon">*</span></td>
                 	</tr>
				</thead>
				<tbody id="AccessoryTableBody">
					<logic:present name="transactionVOSession" property="accessoryTransactionVOs">
						<bean:define id="accessoryTransactionVOs" name="transactionVOSession" property="accessoryTransactionVOs" toScope="page"/>
						<logic:iterate id="accessoryTransactionVO" name="accessoryTransactionVOs" indexId="index">
						<tr>
							<td class="iCargoTableDataTd">
							<input type="checkbox" name="childAcc" id="childAcc" value="<%=index%>" onclick="toggleTableHeaderCheckbox('childAcc',this.form.masterAcc)"/>

							</td>
						<td class="iCargoTableDataTd">
							<logic:present name="accessoryTransactionVO" property="accessoryCode">
								<bean:define id="contents" name="accessoryTransactionVO" property="accessoryCode"/>
								<ihtml:select property="acessoryCode" value="<%=(String)contents%>"  indexId="index" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_ACCESSCODE">
									<bean:define id="accSessionColl" name="accCodesSession" toScope="page" />
									<ihtml:options collection="accSessionColl" property="fieldValue" labelProperty="fieldDescription" />
								</ihtml:select>
							</logic:present>
							<logic:notPresent name="accessoryTransactionVO" property="accessoryCode">
								<ihtml:select property="acessoryCode" indexId="index" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_ACCESSCODE">
								<bean:define id="accSessionColl" name="accCodesSession" toScope="page" />
								<ihtml:options collection="accSessionColl" property="fieldValue" labelProperty="fieldDescription" />
							</ihtml:select>
							</logic:notPresent>
						</td>
							<td class="iCargoTableDataTd">
								<logic:notPresent name="accessoryTransactionVO" property="quantity">
									<ihtml:text property="accessoryQuantity" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_QTY" maxlength="3" style="text-align:right" value="" styleClass="iCargoEditableTextFieldSmallRowColor1" />
								</logic:notPresent>
								<logic:present name="accessoryTransactionVO" property="quantity">
									<ihtml:hidden property="accOperationFlag"  value="U"/>
									<bean:define id="qty" name="accessoryTransactionVO" property="quantity" toScope="page" />
									<ihtml:text property="accessoryQuantity" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_QTY" maxlength="3" value="<%=String.valueOf(qty) %>" indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1"/>
								</logic:present>
							</td>
						</tr>
						
						</logic:iterate>
					</logic:present>

					<!-- Template row started -->
				<bean:define id="templateRowCount" value="0"/>
				<tr template="true" id="accTemplateRow" style="display:none">
					<ihtml:hidden property="accOperationFlag" value="NOOP" />
					<td  class="iCargoTableDataTd">
						<input type="checkbox" name="childAcc" id="childAcc"  onclick="toggleTableHeaderCheckbox('childAcc',this.form.masterAcc)"/>
					</td>
					<td class="iCargoTableDataTd" >
						<ihtml:select property="acessoryCode" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_ACCESSCODE">
						<bean:define id="accSessionColl" name="accCodesSession" toScope="page" />
						<ihtml:options collection="accSessionColl" property="fieldValue" labelProperty="fieldDescription" />
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd" >
						<ihtml:text property="accessoryQuantity" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ACCDTL_QTY"  style="text-align:right" maxlength="3" value="" styleClass="iCargoEditableTextFieldSmallRowColor1"/>
					</td>
				</tr>
			 <!-- template row ended -->
				</tbody>
				
				</table>
			</div>
			</div></div></div>
		</div>
	</div>
	<div class="ic-row">
		<div class="ic-input ic-split-50 ic-label-30" >
			<label>
				 <common:message key="uld.defaults.loanBorrowULD.lbl.orginator" />
			</label>
			<logic:notPresent name="transactionVOSession" property="originatorName">
			  <ihtml:text property="originatorName"   maxlength="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ORIGINATORNAME" value="" style="width:185px" />
			</logic:notPresent>
			<logic:present name="transactionVOSession" property="originatorName">
			  <bean:define id="originatorName" name="transactionVOSession" property="originatorName" toScope="page"/>
				 <ihtml:text property="originatorName"   maxlength="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ORIGINATORNAME" value="<%=(String)originatorName%>" style="width:185px" />
			 </logic:present>
		</div>	
	</div>
	<div class="ic-row">
		<div class="ic-input ic-input ic-split-50 ic-label-30" id="shortcodeLable">
			<label>
				 <common:message key="uld.defaults.loanBorrowULD.lbl.supportinginfo" />
			</label>
			 <logic:notPresent name="transactionVOSession" property="awbNumber">
			  <ihtml:textarea property="awbNumber"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_AWBNUM" value="" cols="50" rows="2" onblur="validateMaxLength(this,100)" ></ihtml:textarea>
			 </logic:notPresent>
			 <logic:present name="transactionVOSession" property="awbNumber">
			  <bean:define id="awbNumber" name="transactionVOSession" property="awbNumber" toScope="page"/>
				 <ihtml:textarea property="awbNumber"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_AWBNUM" value="<%=(String)awbNumber%>" cols="50" rows="2" onblur="validateMaxLength(this,100)" ></ihtml:textarea>
			 </logic:present>
		</div>	
	</div>
</div>
	<div class="ic-foot-container">
		<div class="ic-button-container paddR10">
			<ihtml:nbutton property="btnSendLUC"  componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_SENDLUC"  >
		  	 <common:message key="uld.defaults.loanBorrowULD.btn.sendluc" />
		  </ihtml:nbutton>
		  <ihtml:nbutton property="btnAgreementReport"  componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_AGREEMENTRPT"  >
		  	 <common:message key="uld.defaults.loanBorrowULD.btn.agreementreport" />
		  </ihtml:nbutton>
		  <ihtml:nbutton property="btnGenerateDamageReport"  componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_GENDMGRPT"  >
		  	 <common:message key="uld.defaults.loanBorrowULD.btn.generatedamagereport" />
		  </ihtml:nbutton>
		  <ihtml:nbutton property="btnSave"  componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_SAVE" >
		  	 <common:message key="uld.defaults.loanBorrowULD.btn.save" />
		  </ihtml:nbutton>
		  <ihtml:nbutton property="btnClose"  componentID="BTN_ULD_DEFAULTS_LOANBORROWULD_CLOSE" >
		  	 <common:message key="uld.defaults.loanBorrowULD.btn.close" />
		  </ihtml:nbutton>
		</div>
	</div>
 </ihtml:form>

</div>
</body>
</html:html>

