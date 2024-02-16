<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name: ULD Management
* File Name			: ListULDAgreement.jsp
* Date				: 26-Oct-2015
* Author(s)			: A-6767
********************************************************************
--%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		
<title>iCargo : List Agreement</title>
<meta name="decorator" content="mainpanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/ListUldAgreement_Script.jsp"/>
</head>
	<body>
		<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<bean:define id="form"
		name="listUldAgreementForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm"
		toScope="page" />
		<business:sessionBean id="agreementVO" moduleName="uld.defaults" screenID="uld.defaults.listuldagreement"
		method="get" attribute="uldAgreements" />
		<business:sessionBean id="filterVO" moduleName="uld.defaults" screenID="uld.defaults.listuldagreement"
		method="get" attribute="uLDAgreementFilterVO" />
		<div class="iCargoContent ic-masterbg" style="width:100%;height:100%;overflow:auto;">
			<ihtml:form action="/uld.defaults.screenloadlistuldagreement.do">
				<div class="ic-content-main">
					<input type="hidden" name="currentDialogId" />
					<input type="hidden" name="currentDialogOption" />
					<input type="hidden" name="mySearchEnabled" />
					 <ihtml:hidden property="lastPageNumber" />
					 <ihtml:hidden property="displayPageNum" />
					 <ihtml:hidden property="listStatus" />
					 <ihtml:hidden property="comboFlag"/>
					<span class="ic-page-title ic-display-none">
						<common:message key="uld.defaults.listuldagreement.lbl.listagr" scope="request"/>
					</span>
					<div class="ic-head-container">
						<div class="ic-filter-panel">
						<div class="ic-row">
						<div class="ic-label"><h3>
							<common:message key="uld.defaults.listuldagreement.lbl.search" scope="request"/></h3>
						</div>
						</div>
							<div class="ic-row">
							<div class="ic-col-100">
							<div class="ic-col-50">
							<div class="ic-col-22">
								<div class="ic-row">
									<div class="ic-input ic-split-95 ">
										<label>
											 <common:message key="uld.defaults.listuldagreement.lbl.agrno" scope="request"/>
										</label>
											<logic:present name="filterVO" property="agreementNumber">
												<bean:define id="agrno" name="filterVO" property="agreementNumber"/>
												<ihtml:text property="agreementNumber" tabindex="1" maxlength="16" value="<%=agrno.toString()%>" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_AGREEMENTNUMBER"   />
												<button type="button" class="iCargoLovButton" id="agrlov"  />
											</logic:present>
											<logic:notPresent name="filterVO" property="agreementNumber" >
												<ihtml:text property="agreementNumber" tabindex="1" value="" maxlength="16" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_AGREEMENTNUMBER"   />
												<button type="button" class="iCargoLovButton" id="agrlov"  />
											</logic:notPresent>
									</div>
								</div>
							<div class="ic-row">
								
									<div class="ic-input ic-split-90 ">
										<label>
											<common:message key="uld.defaults.listuldagreement.lbl.txntype" scope="request"/>
										</label>
											<business:sessionBean id="txnTypes"
											moduleName="uld.defaults"
											screenID="uld.defaults.listuldagreement" method="get"
											attribute="transactionType"/>
											<logic:present name="filterVO" property="txnType" >
												<bean:define id="txnType" name="filterVO" property="txnType"/>
												<!-- Modified by A-5493 for ICRD-48148-->
												<ihtml:select componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_TCTNTYP" property="transactionType" tabindex="5" value="<%=txnType.toString()%>">
													<!-- Modified by A-5493 ends-->
													<ihtml:option value="">ALL</ihtml:option>
													<logic:present name="txnTypes">
														<bean:define id="txnType" name="txnTypes"/>
														<ihtml:options collection="txnType" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:present>
											<logic:notPresent name="filterVO" property="txnType" >
												<ihtml:select property="transactionType" tabindex="5" value="" componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_TCTNTYP">
													<logic:present name="txnTypes" >
														<ihtml:option value="ALL">ALL</ihtml:option>
														<bean:define id="txnType" name="txnTypes"/>
														<ihtml:options collection="txnType" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:notPresent>
									</div>
								</div>
							</div>
							<div class="ic-col-35">
								<business:sessionBean id="partyTypes"
									moduleName="uld.defaults"
									screenID="uld.defaults.listuldagreement" method="get"
									attribute="partyType" />
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend ic-left">
										<common:message key="uld.defaults.listuldagreement.lbl.FromParty" scope="request"/>
									</legend>
								<div class="ic-col-55">
									<div class="ic-input  ic-right">
										<label>
											<common:message key="uld.defaults.listuldagreement.lbl.partytype" scope="request"/>
										</label>
										<logic:present name="filterVO" property="fromPartyType" >
											<bean:define id="partyTyp" name="filterVO" property="fromPartyType"/>
											<ihtml:select componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_FROMPARTYTYP" property="fromPartyType" tabindex="2" value="<%=partyTyp.toString()%>">
												<ihtml:option value="">ALL</ihtml:option>
												<logic:present name="partyTypes">
													 <bean:define id="partyType" name="partyTypes"/>
													 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="filterVO" property="fromPartyType" >
											<ihtml:select property="fromPartyType" tabindex="2" componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_FROMPARTYTYP" value="">
												<logic:present name="partyTypes">
												 <ihtml:option value="ALL">ALL</ihtml:option>
												 <bean:define id="partyType" name="partyTypes"/>
												 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
								</div>
									<div class="ic-input  ic-right">
										<label>
											 <common:message key="uld.defaults.listuldagreement.lbl.partycode" scope="request"/>
										</label>
											<logic:present name="filterVO" property="fromPartyCode">
												<bean:define id="partyCod" name="filterVO" property="fromPartyCode"/>
												<ihtml:text property="fromPartyCode" tabindex="3" value="<%=partyCod.toString()%>" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_FROMPARTYCODE"/>
												<!--<button type="button" class="iCargoLovButton" id="fromAirlineLov"  />-->
												<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="fromAirlineLov" id="fromAirlineLov" alt="Airline LOV" />
						 </div>
											</logic:present>
											<logic:notPresent name="filterVO" property="fromPartyCode" >
												<ihtml:text property="fromPartyCode" tabindex="3" value="" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_FROMPARTYCODE" />
												<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="fromAirlineLov" id="fromAirlineLov" alt="Airline LOV" />
						 </div>
											</logic:notPresent>
									</div>
								</fieldset>
							</div>
							<div class="ic-col-35">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend ic-left">
										<common:message key="uld.defaults.listuldagreement.lbl.ToParty" scope="request"/>
									</legend>
								<div class="ic-col-55">
									<div class="ic-input  ic-right">
										<label>
											<common:message key="uld.defaults.listuldagreement.lbl.partytype" scope="request"/>
										</label>
										<logic:present name="filterVO" property="partyType" >
											<bean:define id="partyTyp" name="filterVO" property="partyType"/>
											<ihtml:select componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_PARTYTYP" property="partyType" tabindex="2" value="<%=partyTyp.toString()%>">
												<ihtml:option value="">ALL</ihtml:option>
												<logic:present name="partyTypes">
													 <bean:define id="partyType" name="partyTypes"/>
													 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="filterVO" property="partyType" >
											<ihtml:select property="partyType" tabindex="2" componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_PARTYTYP" value="">
												<logic:present name="partyTypes">
												 <ihtml:option value="ALL">ALL</ihtml:option>
												 <bean:define id="partyType" name="partyTypes"/>
												 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
								</div>
								<div class="ic-col-45">
										<label>
											 <common:message key="uld.defaults.listuldagreement.lbl.partycode" scope="request"/>
										</label>
											<logic:present name="filterVO" property="partyCode">
												<bean:define id="partyCod" name="filterVO" property="partyCode"/>
												<ihtml:text property="partyCode" tabindex="3" value="<%=partyCod.toString()%>" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_PARTYCODE"/>
												<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airlineLov" id="airlineLov" alt="Airline LOV" />
						 </div>
											</logic:present>
											<logic:notPresent name="filterVO" property="partyCode" >
												<ihtml:text property="partyCode" tabindex="3" value="" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_PARTYCODE" />
												<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airlineLov" id="airlineLov" alt="Airline LOV" />
						 </div>
											</logic:notPresent>
									</div>
								</fieldset>
							</div>
							</div>
							<div class="ic-col-50">
							<div class="ic-row">
								<div class="ic-col-20">
										<label>
											<common:message key="uld.defaults.listuldagreement.lbl.agrstatus" scope="request"/>
										</label>
											<business:sessionBean id="statusValues"
											moduleName="uld.defaults"
											screenID="uld.defaults.listuldagreement" method="get"
											attribute="agreementStatus"/>
											<logic:present name="filterVO" property="agreementStatus" >
												<bean:define id="agrstatus" name="filterVO" property="agreementStatus"/>
												<ihtml:select componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_AGRSTA" property="agreementStatus" tabindex="6" value="<%=agrstatus.toString()%>">
													<ihtml:option value="">ALL</ihtml:option>
													<logic:present name="statusValues">
														<bean:define id="status" name="statusValues"/>
														<ihtml:options collection="status" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:present>
											<logic:notPresent name="filterVO" property="agreementStatus" >
												<ihtml:select property="agreementStatus" tabindex="6" value="" componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_AGRSTA">
													<logic:present name="statusValues">
														<ihtml:option value="ALL">ALL</ihtml:option>
														<bean:define id="status" name="statusValues"/>
														<ihtml:options collection="status" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:notPresent>
									</div>
							<div class="ic-col-80">
								<div class="ic-input ic-split-100 ">
										<label>
											<common:message key="uld.defaults.listuldagreement.lbl.agrdate" scope="request"/>
										</label>
											<%String agrmntDate="";%>
											<%String agrfromDate="";%>
											<%String agrmnttoDate="";%>
											<logic:present name="filterVO" property ="agreementListDate">
												<bean:define id="agrDate" name="filterVO" property ="agreementListDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
												<%  agrmntDate=agrDate.toDisplayDateOnlyFormat();%>
											</logic:present>
											<logic:notPresent name="filterVO" property ="agreementListDate">
												<%agrmntDate="";%>
											</logic:notPresent>
											<ibusiness:calendar property="agreementListDate" componentID="CMB_ULD_DEFAULTS_LISTULDAGRMNT_TCTNTYP" id="agreementListDate" tabindex="4" type="image"
											textStyleClass="iCargoTextFieldMedium"
											value="<%=agrmntDate%>" title = "Agreement Date"  />
								</div>
									
							</div>
							</div>
							<div class="ic-row">
						<div class="ic-col-75">
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend ic-left">
										<common:message key="uld.defaults.listuldagreement.lbl.agrvalidity" scope="request"/>
									</legend>
									<div class="ic-col-50">
										<div class="ic-input  ic-right">
											<label>
												<common:message key="uld.defaults.listuldagreement.lbl.fromdate" scope="request"/>
											</label>
												<logic:present name="filterVO" property ="agreementFromDate">
													<bean:define id="agrfrmDate" name="filterVO" property ="agreementFromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
													<%agrfromDate=agrfrmDate.toDisplayDateOnlyFormat();%>
												</logic:present>
												<logic:notPresent name="filterVO" property ="agreementFromDate">
													<%  agrfromDate="";%>
												</logic:notPresent>
												<ibusiness:calendar componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_AGRMNTFRMDATE" id="agreementFromDate" property="agreementFromDate" tabindex="7" type="image"
												textStyleClass="iCargoTextFieldMedium"
												value="<%=agrfromDate%>" title = "From Date"  />
										</div>
									</div>
									<div class="ic-col-50">
										<div class="ic-input ic-split-100 ">
											<label>
												<common:message key="uld.defaults.listuldagreement.lbl.todate" scope="request"/>
											</label>
											<logic:present name="filterVO" property ="agreementToDate">
												<bean:define id="agrtoDate" name="filterVO" property ="agreementToDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
												<% agrmnttoDate=agrtoDate.toDisplayDateOnlyFormat();%>
											</logic:present>
											<logic:notPresent name="filterVO" property ="agreementToDate">
												<%  agrmnttoDate="";%>
											</logic:notPresent>
											<ibusiness:calendar property="agreementToDate" componentID="TXT_ULD_DEFAULTS_LISTULDAGRMNT_AGRMNTTODATE" id="agreementToDate" tabindex="8" type="image"
											textStyleClass="iCargoTextFieldMedium"
											value="<%=agrmnttoDate%>" title = "To Date"  />
										</div>
									</div>
								</fieldset>
								</div>
								<div class="ic-col-25 right">
									<div class="ic-button-container ic-left">
										<ihtml:nbutton property="btnList" tabindex="9" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_LIST" accesskey="L">
										<common:message key="uld.defaults.listuldagreement.btn.list" scope="request"/></ihtml:nbutton>
										<ihtml:nbutton property="btClear" tabindex="10" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_CLEAR" accesskey="C"><common:message key="uld.defaults.listuldagreement.btn.clear" scope="request"/>
										</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
					</div>
						</div>	
						</div>
						</div>
					<div class="ic-main-container" >
						<fieldset class="iCargoFieldSet ic-left" style="height:90%">
						&nbsp;
							<b>
								<common:message key="uld.defaults.listuldagreement.lbl.agrdetails" scope="request"/></h3>
							</b>
							<div class="ic-row">
								<div class="ic-col-50">
									<logic:present name="agreementVO">
										<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
										name="agreementVO"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=((ListULDAgreementForm)form).getLastPageNumber() %>" />
									</logic:present>
									<logic:notPresent name="agreementVO">
										
									</logic:notPresent>
								</div>
								<div class="ic-col-50 ic-right">
									<logic:present name="agreementVO">
										<common:paginationTag
										pageURL="javascript:submitList('lastPageNum','displayPage')"
										name="agreementVO"
										display="pages"
										linkStyleClass="iCargoLink"
										disabledLinkStyleClass="iCargoLink"
										lastPageNum="<%=((ListULDAgreementForm)form).getLastPageNumber()%>" 
										exportToExcel="true"
										exportTableId="listULDTable"
										exportAction="uld.defaults.listuldagreements.do"/>
									</logic:present>
									<logic:notPresent name="agreementVO">
										
									</logic:notPresent>
								</div>
							</div>
							<div class="ic-row">
								<div class="tableContainer " id="div1" style="width:100%;height:450px;overflow:auto;">
									<table class="fixed-header-table" id="listULDTable" >
									
										<thead>
											<tr class="iCargoTableHeadingLeft">
												<td width="28" rowspan="2" >
													<input type="checkbox" name="masterRowId" tabindex="11" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.check)" /></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.AgreementNo"/>
													<span></span></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.frompartytype"/>
													<span></span></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.frompartycode"/>
													<span></span></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.topartytype"/>
													<span></span></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.topartycode"/>
													<span></span></td>
												<td  rowspan="2" width="15%"  >
													<common:message key="uld.defaults.listuldagreement.lbl.AgreementDate"/>
													<span></span></td>
												<td rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.TxnType"/>
													<span></span></td>
												<td colspan="2" class="iCargoTableHeaderLabel" >
													<common:message key="uld.defaults.listuldagreement.lbl.validity"/>
													<span></span></td>
												<td  rowspan="2" >
													<common:message key="uld.defaults.listuldagreement.lbl.AgreementStatus"/>
													<span></span></td>
											</tr>
											<tr class="iCargoTableHeadingLeft">
												<td width="15%">
													<common:message key="uld.defaults.listuldagreement.lbl.From"/>
												</td>
												<td width="15%">
													<common:message key="uld.defaults.listuldagreement.lbl.To"/>
												</td>
											</tr>
										</thead>
										<tbody>
											<logic:present name="agreementVO" >
												<logic:iterate id="agreementVO" name="agreementVO" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO">
											<tr>
												<td class="iCargoTableDataTd ic-center">
													<input type="checkbox" name="check" property="check"  value="<%=nIndex.toString()%>"  onclick="toggleTableHeaderCheckbox('check',this.form.masterRowId)"/>
												</td>
												<td  class="iCargoTableDataTd">
													<input type="hidden" name="agreementNo" value="<%=agreementVO.getAgreementNumber()%>"/>
													<input type="hidden" name="statusvalues" value="<%=agreementVO.getAgreementStatus()%>"/>
													<bean:write name="agreementVO" property="agreementNumber"/></td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="fromPartyType">
													<% if( "L".equals(agreementVO.getFromPartyType())) {%>
														ALL
													<% } %>
													<bean:define id="partytype" name="agreementVO" property="fromPartyType"/>
													<logic:present name="partyTypes">
													<bean:define id="party" name="partyTypes"/>
													<logic:iterate id="partyType" name="party">
													<bean:define id="onetimevo" name="partyType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
													<%if(onetimevo.getFieldValue().equals(partytype)){%>
													<bean:write name="onetimevo" property="fieldDescription"/>
													<%}%>
													</logic:iterate>
													</logic:present>
													</logic:present>
												</td>
												<td  class="iCargoTableDataTd"><bean:write name="agreementVO" property="fromPartyCode"/>
													</td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="partyType">
													<% if( "L".equals(agreementVO.getPartyType())) {%>
														ALL
													<% } %>
													
													<bean:define id="partytype" name="agreementVO" property="partyType"/>
													<logic:present name="partyTypes">
													<bean:define id="party" name="partyTypes"/>

													<logic:iterate id="partyType" name="party">
													<bean:define id="onetimevo" name="partyType" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>

													<%if(onetimevo.getFieldValue().equals(partytype)){%>
													<bean:write name="onetimevo" property="fieldDescription"/>
													<%}%>
													</logic:iterate>
													</logic:present>
													</logic:present>
												</td>
												<td  class="iCargoTableDataTd"><bean:write name="agreementVO" property="partyCode"/>
													</td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="agreementDate">
													<%=agreementVO.getAgreementDate().toDisplayDateOnlyFormat()%>
													</logic:present></td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="txnType">
														<bean:define id="txn" name="agreementVO" property="txnType"/>
														<logic:present name="txnTypes">
															<bean:define id="txntype" name="txnTypes"/>
															<logic:iterate id="actualtxn" name="txntype">
																<bean:define id="onetimevo" name="actualtxn" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
																<%if(onetimevo.getFieldValue().equals(txn)){%>
																<bean:write name="onetimevo" property="fieldDescription"/>
																<%}%>
															</logic:iterate>
														</logic:present>
													</logic:present>
												</td>
												<td  class="iCargoTableDataTd">
													<%=agreementVO.getAgreementFromDate().toDisplayDateOnlyFormat()%></td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="agreementToDate">
														<%=agreementVO.getAgreementToDate().toDisplayDateOnlyFormat()%>
													</logic:present></td>
												<td  class="iCargoTableDataTd">
													<logic:present name="agreementVO" property="agreementStatus">
														<bean:define id="status" name="agreementVO" property="agreementStatus"/>
														<logic:present name="statusValues">
															<bean:define id="statusType" name="statusValues"/>
															<logic:iterate id="actualStatus" name="statusType">
																<bean:define id="onetimevo" name="actualStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
																<%if(onetimevo.getFieldValue().equals(status)){%>
																<bean:write name="onetimevo" property="fieldDescription"/>
																<%}%>
															</logic:iterate>
														</logic:present>
													</logic:present>
												</td>
											</tr>
											</logic:iterate>
											</logic:present>
										</tbody>
									</table>
								</div>
							</div>		
						</fieldset>
					</div>
					<div class="ic-foot-container">
						<div class="ic-button-container paddR10">
							<ihtml:nbutton property="btnPrint" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_PRINT" accesskey="P">
								<common:message key="uld.defaults.listuldagreement.btn.print" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btCreate" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_CREATE" accesskey="R">
							<common:message key="uld.defaults.listuldagreement.btn.create" scope="request"/></ihtml:nbutton>
							<ihtml:nbutton property="btDelete" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_DELETE" accesskey="E">
							<common:message key="uld.defaults.listuldagreement.btn.delete" scope="request"/></ihtml:nbutton>
							<ihtml:nbutton property="btActivate" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_ACTIVATE" accesskey="I">
							<common:message key="uld.defaults.listuldagreement.btn.activate" scope="request"/></ihtml:nbutton>
							<ihtml:nbutton property="btInactivate" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_INACTIVATE" accesskey="T">
							<common:message key="uld.defaults.listuldagreement.btn.inactivate" scope="request"/></ihtml:nbutton>
							<ihtml:nbutton property="btClose" tabindex="12" componentID="BTN_ULD_DEFAULTS_LISTULDAGRMNT_CLOSE" accesskey="O">
							<common:message key="uld.defaults.listuldagreement.btn.close" scope="request"/></ihtml:nbutton>
						</div>
					</div>
				</div>
			</ihtml:form>
		</div>		
	</body>
</html:html>

