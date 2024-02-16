<%--
* Project	 		: iCargo
* Module Code & Name:ULD
* File Name			:MUCTracking.jsp
* Date				:07Aug08
* Author(s)			:a-3045
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm" %>
<%@page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<html:html locale="true">
<head>
		
	
<title><bean:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.title" /></title>
<meta name="decorator" content="mainpanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/MUCTracking_Script.jsp"/>
</head>
<body id="bodyStyle" class="ic-center" style="width:80%;">
	
	
	<bean:define id="form"
	 name="MUCTrackingForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm"
	 toScope="page" />

	<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.messaging.muctracking"
		method="get"
		attribute="oneTimeValues" />

	<business:sessionBean
		id="LIST_DISPLAYVOS"
		moduleName="uld.defaults"
		screenID="uld.defaults.messaging.muctracking"
		method="get"
		attribute="listDisplayColl" />

	<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.messaging.muctracking"
		method="get"
		attribute="listFilterVO" />

	<business:sessionBean
		id="uldCondition"
		moduleName="uld.defaults"
		screenID="uld.defaults.messaging.muctracking"
		method="get"
		attribute="conditionCodes" />


<div class="iCargoContent"  style="overflow:auto;height:100%" >

	<ihtml:form action="uld.defaults.messaging.muctracking.screenload.do">
	<ihtml:hidden property="crnCheck"/>
	<ihtml:hidden property="enableFlag"/>
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="uld.defaults.muctracking.pagetitle" scope="request" />
		</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				
					<div class="ic-row">
						<h4><common:message key="uld.defaults.muctracking.searchcriteria" /></h4>
					</div>
					<div class="ic-input-container">
					<div class="ic-row ">
						<div class="ic-split-25 ic-input ">
							<label>
								<common:message key="uld.defaults.muctracking.refNumber" />
								<span class="iCargoMandatoryFieldIcon">*</span>
							</label>
							<logic:present name="LIST_FILTERVO" property="mucReferenceNumber">
								<ihtml:text name="LIST_FILTERVO" property="mucReferenceNumber" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_REFNO" maxlength="3"  />
							</logic:present>
							<logic:notPresent name="LIST_FILTERVO" property="mucReferenceNumber">
								<ihtml:text property="mucReferenceNumber" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_REFNO" value="" maxlength="3"  />
							</logic:notPresent>
							<div class="lovImg">
							<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayMUCRefNoLov();" alt="Ref No.LOV"/>
						</div>
						</div>
						<div class="ic-split-25 ic-input ">
							<label>
								<common:message key="uld.defaults.muctracking.date" />
							</label>
							<logic:present name="LIST_FILTERVO" property="mucDate">
								<ibusiness:calendar property="mucFilterDate" id="mucFilterDate" componentID="CMP_ULD_DEFAULTS_MUCTRACKING_DATE" type="image"/>
							</logic:present>
							<logic:notPresent name="LIST_FILTERVO" property="mucDate">
								<ibusiness:calendar property="mucFilterDate" id="mucFilterDate" componentID="CMP_ULD_DEFAULTS_MUCTRACKING_DATE" type="image" value=""/>
							</logic:notPresent>
						</div>
						<div class="ic-split-25 ic-input ">
							<label>
								<common:message key="uld.defaults.muctracking.iataStatus" />
							</label>
							<logic:present name="LIST_FILTERVO" property="mucIataStatus">
							<ihtml:select  property="iataFilterStatus" componentID="CMB_ULD_DEFAULTS_MUCTRACKING_IATASTATUS_COMBO">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="uld.defaults.muciatastatus">
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
							</logic:present>
							<logic:notPresent name="LIST_FILTERVO" property="mucIataStatus">
							<ihtml:select  property="iataFilterStatus" componentID="CMB_ULD_DEFAULTS_MUCTRACKING_IATASTATUS_COMBO" value="">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="uld.defaults.muciatastatus">
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
						<div class="ic-button-container paddR5">
							<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_LIST" accesskey="L">
								<common:message key="uld.defaults.muctracking.list" scope="request"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_CLEAR" accesskey="C">
								<common:message key="uld.defaults.muctracking.clear" scope="request"/>
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<logic:present name="LIST_DISPLAYVOS" property="uldTransactionsDetails">
					<td class="iCargoResultsLabel"  style="padding-left:10px;">
						<common:message key="uld.defaults.muctracking.records" scope="request"/>
						<logic:present name="form" property="recordSize">
							<bean:write name="form" property="recordSize" />
						</logic:present>
					</td>
				</logic:present>
			</div>
			<div class="ic-row">
				<div class="tableContainer"  id="div1" style="height:600px;">
					<table class="fixed-header-table">
						<thead>
							<tr class="iCargoTableHeadingLeft">
								<td class="iCargoTableHeader" width="5%"> <input type="checkbox" name="masterRowId" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.selectedrow)"/></td>
								<td  class="iCargoTableHeader" width="9%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.uldnumber"/>
								</td>
								<td  class="iCargoTableHeader" width="11%" >
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.date"/><span class="iCargoMandatoryFieldIcon">*</span>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.time"/>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.fromcarrier"/>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.tocarrier"/>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.txntype"/>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.txnairport"/><span class="iCargoMandatoryFieldIcon">*</span>
								</td>
								<td  class="iCargoTableHeader" width="14%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.crn"/><span class="iCargoMandatoryFieldIcon">*</span>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.destinationairport"/><span class="iCargoMandatoryFieldIcon">*</span>
								</td>
								<td  class="iCargoTableHeader" width="7%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.condition"/>
								</td>
								<td  class="iCargoTableHeader" width="12%">
									<common:message bundle="MUCTrackingResources" key="uld.defaults.muctracking.iatastatus"/>
								</td>
							</tr>
						</thead>

						<tbody>
						<logic:present name="LIST_DISPLAYVOS" property="uldTransactionsDetails">
						<bean:define id="uldTransactionsDetailVOs" name="LIST_DISPLAYVOS" property="uldTransactionsDetails" toScope="page"/>
						<%int i=0;%>
						<logic:iterate id="uldTransactionsDetailsVO" name="uldTransactionsDetailVOs" type="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" indexId="index">
						<%i++;%>
						<bean:define id="uldNum" name="uldTransactionsDetailsVO" property="uldNumber" toScope="page"/>
						<bean:define id="txnRefNumber" name="uldTransactionsDetailsVO" property="transactionRefNumber" toScope="page"/>
						<% String primaryKeyUld=(String)uldNum+txnRefNumber.toString()+i;%>
						<tr>
							<td  class="iCargoTableDataTd ic-center">
								<input type="checkbox" name="selectedrow" value="<%=primaryKeyUld%>" onclick="toggleTableHeaderCheckbox('selectedrow',this.form.masterRowId)"/>
							</td>
							<td class="iCargoTableDataTd" >
								<logic:present name="uldTransactionsDetailsVO" property="uldNumber">
									<ibusiness:uld id="uldno" uldProperty="uldNumber" styleClass="iCargoTableTextFieldLeftRow1" uldValue="<%=uldTransactionsDetailsVO.getUldNumber()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_ULDNUMBER" style="text-transform: uppercase" readonly="true"/>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd">
								<logic:present name="uldTransactionsDetailsVO" property="transactionType">
								<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
								<logic:present name="uldTransactionsDetailsVO" property="transactionDate">
											<%
												String txnDate ="";
												if(uldTransactionsDetailsVO.getTransactionDate() != null) {
												txnDate = TimeConvertor.toStringFormat(
															uldTransactionsDetailsVO.getTransactionDate().toCalendar(),"dd-MMM-yyyy");
												}
											%>
								<ibusiness:calendar componentID="" type="image" indexId="index" id="mucDate" property="mucDate" value="<%=txnDate%>" maxlength="11" />
								</logic:present>
								<logic:notPresent name="uldTransactionsDetailsVO" property="transactionDate">
									<ihtml:text property="mucDate"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="CMP_ULD_DEFAULTS_MUCTRACKING_DATE"/>
								</logic:notPresent>
								</logic:equal>
								<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
								<logic:present name="uldTransactionsDetailsVO" property="returnDate">
											<%
												String rtnDate ="";
												if(uldTransactionsDetailsVO.getReturnDate() != null) {
												rtnDate = TimeConvertor.toStringFormat(
															uldTransactionsDetailsVO.getReturnDate().toCalendar(),"dd-MMM-yyyy");
												}
											%>
								<ibusiness:calendar componentID="CMP_ULD_DEFAULTS_MUCTRACKING_DATE" type="image" indexId="index" id="mucDate" property="mucDate" value="<%=rtnDate%>" maxlength="11" />
								</logic:present>
								<logic:notPresent name="uldTransactionsDetailsVO" property="returnDate">
									<ihtml:text property="mucDate"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID=""/>
								</logic:notPresent>
								</logic:notEqual>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="transactionDate">
										<%
											String txnTime ="";
											if(uldTransactionsDetailsVO.getTransactionDate() != null) {
											txnTime = TimeConvertor.toStringFormat(
														uldTransactionsDetailsVO.getTransactionDate().toCalendar(),"HH:mm");
											}
										%>
							<ibusiness:releasetimer  property="mucTime" indexId="index" title="MUC Time"  type="asTimeComponent" id="mucTime" value="<%=txnTime%>" />
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="transactionDate">
								<ihtml:text property="mucTime"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_TIME"/>
							</logic:notPresent>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="returnDate">
										<%
											String rtnTime ="";
											if(uldTransactionsDetailsVO.getReturnDate() != null) {
											rtnTime = TimeConvertor.toStringFormat(
														uldTransactionsDetailsVO.getReturnDate().toCalendar(),"HH:mm");
											}
										%>
							<ibusiness:releasetimer  property="mucTime" indexId="index" title="MUC Time"  type="asTimeComponent" id="mucTime" value="<%=rtnTime%>" />
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="returnDate">
								<ihtml:text property="mucTime"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID=""/>
							</logic:notPresent>
							</logic:notEqual>
							</logic:present>

							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="fromPartyCode">
							<bean:write name="uldTransactionsDetailsVO" property="fromPartyCode" />
							</logic:present>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="toPartyCode">
							<bean:write name="uldTransactionsDetailsVO" property="toPartyCode" />
							</logic:present>
							</logic:notEqual>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="toPartyCode">
							<bean:write name="uldTransactionsDetailsVO" property="toPartyCode" />
							</logic:present>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="fromPartyCode">
							<bean:write name="uldTransactionsDetailsVO" property="fromPartyCode" />
							</logic:present>
							</logic:notEqual>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
								<bean:write name="uldTransactionsDetailsVO" property="transactionType" />
							</logic:present>
							</td>
							<td class="iCargoTableDataTd">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="Loan">
							<logic:present name="uldTransactionsDetailsVO" property="transactionStationCode">
								<ihtml:text property="txnAirport" indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="<%=uldTransactionsDetailsVO.getTransactionStationCode()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_TRANSACTIONAIRPORT" maxlength="3"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="transactionStationCode">
								<ihtml:text property="txnAirport"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_TRANSACTIONAIRPORT" maxlength="3"/>
							</logic:notPresent>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="Loan">
							<logic:present name="uldTransactionsDetailsVO" property="returnStationCode">
								<ihtml:text property="txnAirport" indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="<%=uldTransactionsDetailsVO.getReturnStationCode()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_TRANSACTIONAIRPORT" maxlength="3"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="returnStationCode">
								<ihtml:text property="txnAirport"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_TRANSACTIONAIRPORT" maxlength="3"/>
							</logic:notPresent>
							</logic:notEqual>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="Borrow">
							<logic:present name="uldTransactionsDetailsVO" property="returnCRN">	
								<ihtml:text property="CRN"  indexId="index" styleClass="iCargoEditableTextFieldRowColor1" value="<%=uldTransactionsDetailsVO.getReturnCRN()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CRN" maxlength="12"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="returnCRN">
								<ihtml:text property="CRN"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CRN" maxlength="12"/>
							</logic:notPresent>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="Borrow">
							<logic:present name="uldTransactionsDetailsVO" property="controlReceiptNumber">
								<ihtml:text property="CRN"  indexId="index" styleClass="iCargoEditableTextFieldRowColor1" value="<%=uldTransactionsDetailsVO.getControlReceiptNumber()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CRN" maxlength="12"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="controlReceiptNumber">
								<ihtml:text property="CRN"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CRN" maxlength="12"/>
							</logic:notPresent>
							</logic:notEqual>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd">
							<logic:present name="uldTransactionsDetailsVO" property="transactionType">
							<logic:equal name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="txStationCode">
								<ihtml:text property="destAirport"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="<%=uldTransactionsDetailsVO.getTxStationCode()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_DESTINATIONAIRPORT" maxlength="3"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="txStationCode">
								<ihtml:text property="destAirport"  indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_DESTINATIONAIRPORT" maxlength="3"/>
							</logic:notPresent>
							</logic:equal>
							<logic:notEqual name="uldTransactionsDetailsVO" property="transactionType" value="<%=uldTransactionsDetailsVO.getTransactionType()%>">
							<logic:present name="uldTransactionsDetailsVO" property="returnStationCode">
							<ihtml:text property="destAirport" indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="<%=uldTransactionsDetailsVO.getReturnStationCode()%>" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_DESTINATIONAIRPORT" maxlength="3"/>
							</logic:present>
							<logic:notPresent name="uldTransactionsDetailsVO" property="returnStationCode">
							<ihtml:text property="destAirport" indexId="index" styleClass="iCargoEditableTextFieldSmallRowColor1" value="" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_DESTINATIONAIRPORT" maxlength="3"/>
							</logic:notPresent>
							</logic:notEqual>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd" >
								<logic:present name="uldTransactionsDetailsVO" property="uldConditionCode">
									<bean:define id="uldCond" name="uldTransactionsDetailsVO" property="uldConditionCode" />
									<ihtml:select property="condition" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CONDITION" value="<%=(String)uldCond%>" indexId="index" styleClass="iCargoSmallComboBox" >
									<bean:define id="contents" name="uldCondition"/>
									<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription" />
									</ihtml:select>
								</logic:present>
									<logic:notPresent name="uldTransactionsDetailsVO" property="uldConditionCode">
									<ihtml:select property="condition" componentID="TXT_ULD_DEFAULTS_MUCTRACKING_CONDITION" value=""  indexId="index" styleClass="iCargoSmallComboBox">
									<bean:define id="contents" name="uldCondition"/>
									<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription" />
									</ihtml:select>
								</logic:notPresent>
							</td>

							<td class="iCargoTableDataTd">

							<logic:present name="uldTransactionsDetailsVO" property="mucIataStatus">
								<ihtml:text property="iataStatus"  indexId="index" styleClass="iCargoTableTextFieldLeftRow1" readonly="true" value="<%=uldTransactionsDetailsVO.getMucIataStatus()%>" componentID="CMB_ULD_DEFAULTS_MUCTRACKING_IATASTATUS_COMBO"/>
							</logic:present>
							</td>
						</tr>
						</logic:iterate>
						</logic:present>
						</tbody>
					</table>
				</div>
			</div>
			</div>
		
	<div class="ic-foot-container">
		<div class="ic-button-container">
			<ihtml:nbutton property="btnSend" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_SEND" accesskey="N">
				<common:message key="uld.defaults.muctracking.send" scope="request"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btnDetails" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_DETAILS" accesskey="D">
				<common:message key="uld.defaults.muctracking.details" scope="request"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btnActions" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_ACTIONS" accesskey="A">
				<common:message key="uld.defaults.muctracking.actions" scope="request"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_SAVE" accesskey="S">
				<common:message key="uld.defaults.muctracking.save" scope="request"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_MUCTRACKING_CLOSE" accesskey="O">
				<common:message key="uld.defaults.muctracking.close" scope="request"/>
			</ihtml:nbutton>
		</div>
    </div>
	</div>

</ihtml:form>
</div>

				
		
	</body>
</html:html>

