<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: UnaccountedDispatches.jsp
* Date				: 14-AUG-2007
* Author(s)			: A-2107
 ******************************************************************************************--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>

		

			
	
<html:html>

<head>

	
		
	<title>
		<common:message bundle="unaccountedDispatches" key="mailtracking.mra.defaults.unaccountedDispatches" />
	</title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/defaults/UnaccountedDispatches_Script.jsp" />
</head>
<body>
	
	
	
	
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>


<!--CONTENT STARTS-->

	<bean:define id="form"
		name="UnaccountedDispatchesForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm"
		toScope="page"
		scope="request"/>


	<business:sessionBean
			  id="KEY_ONETIMES"
			  moduleName="mailtracking.mra.unaccounteddispatches"
			  screenID="mailtracking.mra.defaults.unaccounteddispatches"
			  method="get"
			attribute="oneTimeVOs" />

	<business:sessionBean
			  id="KEY_ONETIMEMAP"
			  moduleName="mailtracking.mra.unaccounteddispatches"
			  screenID="mailtracking.mra.defaults.unaccounteddispatches"
			  method="get"
			attribute="oneTimeMap" />

	<business:sessionBean
			id="unaccountedDispatchesVO"
			moduleName="mailtracking.mra.unaccounteddispatches"
			screenID="mailtracking.mra.defaults.unaccounteddispatches"
			method="get"
			attribute="unaccountedDispatchesVO" />
	<logic:present name="unaccountedDispatchesVO">
		<bean:define id="unaccountedDispatchesPage" name="unaccountedDispatchesVO" property="unaccountedDispatchesDetails" toScope="page"/>
	</logic:present>


	<business:sessionBean
			id="unaccountedDispatchesFilterVO"
			moduleName="mailtracking.mra.unaccounteddispatches"
			screenID="mailtracking.mra.defaults.unaccounteddispatches"
			method="get"
			attribute="unaccountedDispatchesFilterVO" />
<div id="pageDiv" class="iCargoContent">
	<ihtml:form action="/mailtracking.mra.defaults.unaccounteddispatches.onScreenLoad.do">
		 <ihtml:hidden property="lastPageNum"/>
		 <ihtml:hidden property="displayPage"/>
		 <ihtml:hidden property="screenStatusFlag" />
		 <ihtml:hidden name="form" property="fromScreen" />

		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.defaults.unaccountedDispatches" /></span>
			<div class="ic-head-container">
				<div class="ic-row iCargoHeadingLabel">
					<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.search" />
				</div>
				<div class="ic-filter-panel">
					<div class="ic-row ic-border">
						<div class="ic-input ic-split-30 ">
							<fieldset class="iCargoFieldSet">
								<legend class="iCargoLabelRightAligned"><b><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.fltDate" /></b></legend>
								<div class="ic-input ic-split-50 ic-mandatory">
									<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.fromDate" />
									<logic:present name="unaccountedDispatchesFilterVO">
										<bean:define id="flightFromDate" name="unaccountedDispatchesFilterVO" property="flightFromDate"/>
										<ibusiness:calendar property="fromDate" type="image" id="fromDate" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_FROMDATE" value="<%=String.valueOf(flightFromDate)%>"/>
									</logic:present>
									<logic:notPresent name="unaccountedDispatchesFilterVO">
										<ibusiness:calendar property="fromDate" type="image" id="fromDate" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_FROMDATE" value=""/>
									</logic:notPresent>
								</div>
								<div class="ic-input ic-split-50 ic-mandatory">
									<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.toDate" />
									<logic:present name="unaccountedDispatchesFilterVO">
										<bean:define id="flightToDate" name="unaccountedDispatchesFilterVO" property="flightToDate"/>
										<ibusiness:calendar property="toDate" type="image" id="toDate" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_TODATE" value="<%=String.valueOf(flightToDate)%>"/>
									</logic:present>
									<logic:notPresent name="unaccountedDispatchesFilterVO">
										<ibusiness:calendar property="toDate" type="image" id="toDate" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_TODATE" value=""/>
									</logic:notPresent>
								</div>
							</fieldset>
						</div>	
						<div class="ic-input ic-split-20 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.fltNo" />
							<logic:present name="unaccountedDispatchesFilterVO">
								<bean:define id="fltCode" name="unaccountedDispatchesFilterVO" property="carrierCode"/>
								<bean:define id="fltNumber" name="unaccountedDispatchesFilterVO" property="flightNumber"/>
								<ibusiness:flightnumber id="flightNumber"
								carrierCodeProperty="flightCode"
								flightCodeProperty="flightNo"
								carriercodevalue="<%=(String.valueOf(fltCode))%>"
								flightcodevalue="<%=(String.valueOf(fltNumber))%>"
								componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_FLIGHTNO"
								carrierCodeStyleClass="iCargoTextFieldVerySmall"
								flightCodeStyleClass="iCargoTextFieldSmall" />
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesFilterVO">
								<ibusiness:flightnumber id="flightNumber"
								carrierCodeProperty="flightCode"
								flightCodeProperty="flightNo"
								carriercodevalue=""
								flightcodevalue=""
								componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_FLIGHTNO"
								carrierCodeStyleClass="iCargoTextFieldVerySmall"
								flightCodeStyleClass="iCargoTextFieldSmall" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-15 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.org" />
							<logic:present name="unaccountedDispatchesFilterVO">
								<bean:define id="departurePort" name="unaccountedDispatchesFilterVO" property="departurePort"/>
								<ihtml:text name="form" property="origin"  componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_ORIGIN" maxlength="4" value = "<%=String.valueOf(departurePort)%>"/>
								<img id="originLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"value="org"/>
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesFilterVO">
								<ihtml:text name="form" property="origin"  componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_ORIGIN" maxlength="4" value="" />
								<img id="originLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" value="org" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-15 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.dest" />
							<logic:present name="unaccountedDispatchesFilterVO">
								<bean:define id="finalDestination" name="unaccountedDispatchesFilterVO" property="finalDestination"/>
								<ihtml:text name="form" property="destination" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_DEST" maxlength="4" value = "<%=String.valueOf(finalDestination)%>"/>
								<img id="destinationLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" value="org"/>
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesFilterVO">
								<ihtml:text name="form" property="destination"  componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_DEST" maxlength="4" value="" />
								<img id="destinationLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" value="org" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-20 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.reasonCode" />
							<logic:notPresent name="unaccountedDispatchesFilterVO">
								<logic:present name="KEY_ONETIMES">
									<ihtml:select property="reasonCode" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_RESONCDE" style="width:100px" value="All">
										<ihtml:option value=""> </ihtml:option>
										<bean:define id="oneTimeCatSess" name="KEY_ONETIMES" toScope="page" />
										<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
											<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
											<html:option value="<%=(String)fieldValue %>">
												<bean:write name="oneTimeCatVO" property="fieldValue"/>
											</html:option>
										</logic:iterate>
									</ihtml:select>
								</logic:present>
							</logic:notPresent>
							<logic:present name="unaccountedDispatchesFilterVO">
								<bean:define id="reasonCode" name="unaccountedDispatchesFilterVO" property="reasonCode"/>
								<ihtml:select property="reasonCode" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_RESONCDE" style="width:100px" value = "<%=String.valueOf(reasonCode)%>">
									<ihtml:option value=""></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<bean:define id="oneTimeCatSess" name="KEY_ONETIMES" toScope="page" />
										<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
											<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
											<html:option value="<%=(String)fieldValue %>">
												<bean:write name="oneTimeCatVO" property="fieldValue"/>
											</html:option>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</logic:present>
						</div>
						<div class="ic-button-container">
							<ihtml:nbutton property="btList" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_LIST">
								<common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_CLEAR">
								<common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.clear" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row ic-border">
					<%boolean toDisable = false;%>
					<logic:notPresent  name="unaccountedDispatchesVO" >
						<% toDisable = true;%>
					</logic:notPresent>
					<div class="ic-row iCargoHeadingLabel">
						<div>
							<h4><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.tot" /></h4>
						</div>	
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-30 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.noOfDis" />
							<logic:present name="unaccountedDispatchesVO">
								<common:display name="unaccountedDispatchesVO" property="noOfDispatches"/>
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesVO">
								<ihtml:text property="noOfDispatches" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_DISPNO" value="" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-30 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.protatedAmt" />
							<logic:present name="unaccountedDispatchesVO" property="propratedAmt">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="unaccountedDispatchesVO"  moneyproperty="propratedAmt" property="propratedAmt" />
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesVO">
								<ihtml:text property="proRatedAmt" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_PRORATEDAMT" value="" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-30 ">
							<common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.currency" />
							<logic:present name="unaccountedDispatchesVO">
								<common:display name="unaccountedDispatchesVO" property="currency"/>
							</logic:present>
							<logic:notPresent name="unaccountedDispatchesVO">
								<ihtml:text property="currency" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_CURRENCY" value=""  />
							</logic:notPresent>
						</div>
						<div class="ic-row">
							<div class="ic-button-container">
								<%String lstPgNo = "";%>
								<logic:present name="UnaccountedDispatchesForm" property="lastPageNum">
									<bean:define id="lastPg" name="UnaccountedDispatchesForm" property="lastPageNum" scope="request" toScope="page" />
									<%
										lstPgNo = (String) lastPg;
									%>
								</logic:present>
								<logic:present name="unaccountedDispatchesVO" >
									<bean:define id="pageObj" name="unaccountedDispatchesVO"  property = "unaccountedDispatchesDetails"   toScope="page" />
									<common:paginationTag pageURL="mailtracking.mra.defaults.unaccounteddispatches.listload.do"
										name="pageObj"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=lstPgNo%>" />
								</logic:present>
								<logic:present name="unaccountedDispatchesVO" >
									<bean:define id="pageObj1" name="unaccountedDispatchesVO"  property = "unaccountedDispatchesDetails"   toScope="page" />
								   <common:paginationTag
										linkStyleClass="iCargoResultsLabel"
										disabledLinkStyleClass="iCargoResultsLabel"
										pageURL="javascript:selectNextDetails('lastPageNum','displayPage')"
										name="pageObj1"
										display="pages"
										lastPageNum="<%=lstPgNo%>"
										exportToExcel="true"
										exportTableId="unAccountDispatchTable"
										exportAction="mailtracking.mra.defaults.unaccounteddispatches.listload.do"/>
								</logic:present>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-section ic-border">
						<div id="div1" class="tableContainer" style="width:100%;height:608px;">
							<table  class="fixed-header-table"  id="unAccountDispatchTable">
								<thead>
									<tr class="iCargoTableHeadingCenter" width="100%">

										<td  class="iCargoTableHeader"  rowspan="2"  width="2%"><input type="checkbox" name="headChk"  value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.selectCheckBox)"/>
										</td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.dsn" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.org" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.dest" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.fltNo" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.fltDate" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.billTyp" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.currency" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.grosswt" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.rate" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.amt" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.category" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.class" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.subclass" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.sectorFrm" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.sectorTo" /></td>
										<td colspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.proratedAmt" /></td>
										<td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.reason" /></td>
								  </tr>
								  <tr>
										<td  class="iCargoTableHeaderLabel"> <common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.ctrcurrency" /> </td>
										<td  class="iCargoTableHeaderLabel"> <common:message key="mailtracking.mra.defaults.unaccountedDispatches.lbl.nzd" /> </td>

								  </tr>

								</thead>
								<tbody>
									<logic:present name="unaccountedDispatchesVO" >
										<bean:define id="parameterValues" name="unaccountedDispatchesVO" property="unaccountedDispatchesDetails"/>
											<logic:iterate id="iterator" name="parameterValues" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO" indexId="rowId">
											<tr class="iCargoTableCellsLeftRowColor<%=1%>">
									<td width="3%">
										<input type="checkbox"   name="selectCheckBox" onclick="toggleTableHeaderCheckbox('selectCheckBox',this.form.headChk)" value="<%=iterator.getCsgDocNum()+'-'+iterator.getAcceptedDate().toDisplayDateOnlyFormat()
										+'-'+iterator.getBilBase()+'-'+iterator.getDsnSqnNo()+'-'+iterator.getPostalCde()+'#'+iterator.getReason()%>" />
									</td>
									<td ><bean:write name="iterator" property="dsn" /></td>
										<td ><bean:write name="iterator" property="origin" /></td>
										<td ><bean:write name="iterator" property="destination" /></td>
										<td ><bean:write name="iterator" property="flightNumber" /></td>
										<td ><bean:write name="iterator" property="flightDate" /></td>
										<td ><bean:write name="iterator" property="billType" /></td>
										<td ><bean:write name="iterator" property="currency" /></td>
										<td ><bean:write name="iterator" property="weight" /></td>
										<td ><logic:present name="iterator" property="rate">
												<logic:lessEqual name="rate" value="0">
												<bean:write name="iterator" property="rate" format="0.0000"/>
												</logic:lessEqual>

												<logic:greaterThan name="rate"  value="0">
												<bean:write name="iterator" property="rate" format="####.0000"/>
												</logic:greaterThan>
											</logic:present>


											</td>
										<td >
											<div align="right">
											<logic:present name="iterator" property="amount">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="amount" property="amount" />
											</logic:present>
											<logic:notPresent name="iterator" property="amount">
											&nbsp;
											</logic:notPresent>
											</div>
										</td>

										<td>
											<center>

											<logic:present  name="iterator" property="mailCategory">
											<bean:define id="mailCategory" name="iterator" property="mailCategory" />
											<logic:present name="KEY_ONETIMEMAP">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMEMAP">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">

											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

											<%String field = mailCategory.toString();%>
											<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
											<bean:write name="parameterValue" property="fieldDescription" />

											</logic:equal>
											</logic:present>
											</logic:iterate>
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</logic:present>

											</center>

									</td>


										<td > <center> <bean:write name="iterator" property="mailClass" /> <center> </td>
										<td > <center> <bean:write name="iterator" property="mailSubClass" /> <center> </td>
										<td ><bean:write name="iterator" property="sectorFrom" /></td>
										<td ><bean:write name="iterator" property="sectorTo" /></td>
										<td >
											<div align="right">
											<logic:present name="iterator" property="proratedAmtinCtrcur">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="proratedAmtinCtrcur" property="proratedAmtinCtrcur" />
											</logic:present>
											<logic:notPresent name="iterator" property="proratedAmtinCtrcur">
											&nbsp;
											</logic:notPresent>
											</div>
										</td>
										<td >
										<div align="right">
										<logic:present name="iterator" property="proratedAmt">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="proratedAmt" property="proratedAmt" />
										</logic:present>
										<logic:notPresent name="iterator" property="proratedAmt">
										&nbsp;
										</logic:notPresent>
										</div>
										</td>

										<td>
											<center>

											<logic:present  name="iterator" property="reason">
											<bean:define id="reason" name="iterator" property="reason" />
											<logic:present name="KEY_ONETIMEMAP">
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMEMAP">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.mra.reasoncode">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">

											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

											<%String field = reason.toString();%>
											<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
											<bean:write name="parameterValue" property="fieldDescription" />

											</logic:equal>
											</logic:present>
											</logic:iterate>
											</logic:equal>
											</logic:iterate>
											</logic:present>
											</logic:present>

											</center>

										</td>

								 </tr>
								</logic:iterate>
								 </logic:present>
						</tbody>
						</table>
						</div>	
					</div>
				</div>
				<div class="ic-button-container">
					<ihtml:nbutton property="btPrint" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_PRINT">
					  <common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.print" />
				</ihtml:nbutton>
 				<ihtml:nbutton property="btDispatchEnq" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_DESPENQ" style="width:0px">
					  <common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.dispEnq" />
				</ihtml:nbutton>
 				<ihtml:nbutton property="btManualAccounting" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_MANACCT" style="width:200px">
					  <common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.manAcct" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btAccounting" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_ACCT">
					 <common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.acct" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="MRA_DEFAULTS_UNACCOUNTEDDISPATCH_CLOSE">
					 <common:message   key="mailtracking.mra.defaults.unaccountedDispatches.btn.close" />
				</ihtml:nbutton>
				</div>		
			</div>
		</div>
	</ihtml:form>	
</div>	

	</body>
</html:html>
