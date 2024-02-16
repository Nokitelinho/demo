<%--
* Project	 		: iCargo
* Module Code & Name            : mailtracking.mra.defaults
* File Name			: ListRateAudit
* Date				: 20 june 2008
* Author(s)			: a-3108
--%>


<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm"%>
<%@ page import=  "com.ibsplc.xibase.util.time.TimeConvertor"%>

<bean:define id="form"
			 name="ListRateAuditForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm"
			 toScope="page" />


	
	
<html:html>

<head>
	
	
	<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.rateauditdetails.title"/></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/defaults/ListRateAudit_Script.jsp" />

</head>

<body id="bodyStyle">
	
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>

<business:sessionBean id="OneTimeValues"
	 	 moduleName="mail.operations"
		 screenID="mailtracking.mra.defaults.listrateaudit"
	 	  method="get"
	  attribute="OneTimeValues" />
<business:sessionBean id="RateAuditVOs"
		moduleName="mailtracking.mra"
		screenID="mailtracking.mra.defaults.listrateaudit"
		method="get"
		attribute="RateAuditVOs" />
<business:sessionBean id="RateAuditFilterVO"
moduleName="mailtracking.mra"
screenID="mailtracking.mra.defaults.listrateaudit"
method="get"
attribute="RateAuditFilterVO" />
<!-- The content div -->
<div  class="iCargoContent" style="overflow:auto;width:100%;height:100%;">

	<ihtml:form action="/mailtracking.mra.defaults.listrateauditscreenload.do" >
	<ihtml:hidden property="selectedRowIndex"/>
		<ihtml:hidden property="selectedRows" />
		<ihtml:hidden property="rateauditFlag"/>
		<ihtml:hidden property="lastPageNumber" />
	    <ihtml:hidden property="displayPageNum" />
		<div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.mra.defaults.listRateAudit.pagetitle.listRateAudit" />
			</span>
			<div class="ic-filter-panel">
			<div class="ic-row">
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsn" />
						</label>
						<logic:present name="RateAuditFilterVO" property="dsn">
						<ihtml:text property="dsn"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSN" value="<%= form.getDsn() %>" maxlength="6"  />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="dsn">
						<ihtml:text property="dsn" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSN" maxlength="6" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-40">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsndate" />
						</label>
						<logic:present name="RateAuditFilterVO" property="dsnDate">
						<ibusiness:calendar type="image" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSNDATE" value="<%= form.getDsnDate() %>" id="dsnDate" property="dsnDate"  maxlength="11" />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="dsnDate">
						<ibusiness:calendar type="image" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSNDATE" id="dsnDate" property="dsnDate"  maxlength="11"  />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.gpacode" />
						</label>
						<logic:present name="RateAuditFilterVO" property="gpaCode">
							<ihtml:text property="gpaCode"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_GPACODE" value="<%= form.getGpaCode() %>" maxlength="6"   />
							<img  name="paLOV" id="paLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" />	
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="gpaCode">
							<ihtml:text property="gpaCode" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_GPACODE" maxlength="6"   />
							<img  name="paLOV" id="paLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" />
						</logic:notPresent>
					</div>
			</div>
			<div class="ic-row">
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsnstatus" />
						</label>
						<ihtml:select  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_DSNSTATUS" property="dsnStatus"   style="width:40%;">
						<ihtml:option value=""></ihtml:option>
						<logic:present name="OneTimeValues">
							<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mailtracking.mra.defaults.rateaudit.status">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue" property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
										</logic:present>
									</logic:iterate>

								</logic:equal>
							</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-40">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.flightno" />
						</label>
						<logic:present name="RateAuditFilterVO" property="carrierCode">
						<ibusiness:flightnumber carrierCodeProperty="carrierCode"
						id="flightNumber"
						flightCodeProperty="flightNo"
						componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FLIGHTNO"
						carrierCodeStyleClass="iCargoTextFieldVerySmall"
						flightCodeStyleClass="iCargoTextFieldSmall" />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="carrierCode">
							<ibusiness:flightnumber carrierCodeProperty="carrierCode"
							id="flightNumber"
							flightCodeProperty="flightNo"
							componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FLIGHTNO"

							carrierCodeStyleClass="iCargoTextFieldVerySmall"
							flightCodeStyleClass="iCargoTextFieldSmall" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.flightDate" />
						</label>
						<logic:present name="RateAuditFilterVO" property="flightDate">
						<ibusiness:calendar type="image" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FLIGHTDATE" value="<%= form.getFlightDate() %>" id="flightDate" property="flightDate" maxlength="11"  />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="flightDate">
							<ibusiness:calendar type="image" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FLIGHTDATE" id="flightDate" property="flightDate" maxlength="11"  />
						</logic:notPresent>
					</div>			
			</div>
			<div class="ic-row">
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.subclass" />
						</label>
						<logic:present name="RateAuditFilterVO" property="subClass">
						<ihtml:text property="subClass" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_SUBCLASS" value="<%= form.getSubClass() %>" maxlength="3"   />
						<img  name="subClassLOV" id="subClassLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="subClass">
						<ihtml:text property="subClass" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_SUBCLASS" maxlength="3"   />
						<img  name="subClassLOV" id="subClassLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-40">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.fromDate" />
						</label>
						<logic:present name="RateAuditFilterVO" property="fromDate">
						<ibusiness:calendar type="image" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FROMDATE" value="<%= form.getFromDate() %>" id="fromDate" property="fromDate" maxlength="11"  />
						</logic:present>
						<logic:notPresent name="RateAuditFilterVO" property="fromDate">
							<ibusiness:calendar type="image" value="" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_FROMDATE" id="fromDate" property="fromDate"  maxlength="11" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-30">
						<label>
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.todate" />
						</label>
						<ibusiness:calendar type="image" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_TODATE" id="toDate" property="toDate" maxlength="11" />
					</div>			
			</div>
			<div class="ic-row">
				<div class="ic-button-container">
						<ihtml:button property="btList" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_BTN_LIST" >
						<common:message key="mailtracking.mra.defaults.listRateAudit.btn.list" />
						</ihtml:button>
						<ihtml:button property="btClear" componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT_BTN_CLEAR" >
						<common:message key="mailtracking.mra.defaults.listRateAudit.btn.clear" />
						</ihtml:button>
				</div>
			</div>
		</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
			<logic:present name="RateAuditVOs">
			<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
			name="RateAuditVOs"
			display="label"
			labelStyleClass="iCargoResultsLabel"
			lastPageNum="<%=((ListRateAuditForm)form).getLastPageNumber() %>" />
			</logic:present>

			<logic:notPresent name="RateAuditVOs">
			&nbsp;
			</logic:notPresent>
			<div class="ic-button-container">
				<logic:present name="RateAuditVOs">
				<common:paginationTag
				pageURL="javascript:submitPage1('lastPageNum','displayPage')"
				name="RateAuditVOs"
				display="pages"
				linkStyleClass="iCargoResultsLabel"
				disabledLinkStyleClass="iCargoResultsLabel"
				lastPageNum="<%=((ListRateAuditForm)form).getLastPageNumber() %>" 
				exportToExcel="true"
				exportTableId="listRateAuditTable"
				exportAction="mailtracking.mra.defaults.listrateaudit.listrateauditdetails.do"/>
				</logic:present>

				<logic:notPresent name="RateAuditVOs">
				&nbsp;
				</logic:notPresent>
			</div>
			</div>
			<div class="ic-row">
			
			<div id="div1" class="tableContainer"  style="height:680px">
					<table  class="fixed-header-table" id="listRateAuditTable">
					  <thead>
					  	<tr >
						  <td   rowspan="2" width="1%"><input type="checkbox" tabindex="-1" class="iCargoTableHeader"  name="checkAll" value="checkbox"/><span></span></td>
						  <td class="iCargoTableHeader" rowspan="2">
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.applyautd" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2" >
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsnstatus" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2" >
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsn" />

						  </td>

						  <td class="iCargoTableHeader" rowspan="2">
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsndate" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.origin" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
						  		<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dest" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.fltno" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.fltdate" />

						  </td>
						  <td class="iCargoTableHeader" colspan="5" >
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.dsn" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2" >
						  	<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.auditedwtcharge" />

						  </td>

						  <td class="iCargoTableHeader" rowspan="2" >
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.discrepancy" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.gpacode" />
						  </td>
						  <td class="iCargoTableHeader" rowspan="2" >
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.category" />
						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.class" />

						  </td>
						  <td class="iCargoTableHeader" rowspan="2">
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.subclass" />

						  </td>

						  </td>

						  <td class="iCargoTableHeader" rowspan="2" >
							<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.billto" />

						  </td>


						</tr>
						<tr>
							<td>    <input type="text" tabindex="-1" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.pcs" />" style="width:40px; padding-top:3px;" readonly="true" /><span></span></td>
							<td>	<input type="text" tabindex="-1" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.grosswt" />" style="width:60px; padding-top:3px;" readonly="true" /><span></span></td>
							<td>	<input type="text" tabindex="-1" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.rate" />" style="width:55px; padding-top:3px;" readonly="true" /><span></span></td>
							<td>	<input type="text" tabindex="-1" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.currency" />" style="width:55px; padding-top:3px;" readonly="true" /><span></span></td>
							<td>	<input type="text" tabindex="-1" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.listRateAudit.lbl.presentwtcharge" />" style="width:108px; padding-top:3px;" readonly="true" /><span></span></td>

						</tr>

					  </thead>
					  <tbody>
						<logic:present name="RateAuditVOs">

						<logic:iterate id="RateAuditVO" name="RateAuditVOs"  indexId="rowCount"  >
						<tr>
							<td class="iCargoTableDataTd"><input type="checkbox" tabindex="-1" property="rowId" name="rowId" value="<%=String.valueOf(rowCount)%>" onclick="checkBoxValidate('rowCount','rowId')"/>
							</td>
							<td class="iCargoTableDataTd ic-center" >
							     <center>
							     <logic:present name="RateAuditVO" property="applyAutd">
							     <bean:define id="applyAutd" name="RateAuditVO" property="applyAutd"/>
							     <logic:equal name="applyAutd" value="Y">
							       <logic:equal name="RateAuditVO" property="dsnStatus" value="F">
							    	<input type="checkbox"  tabindex="-1" name="applyAudit"  property="applyAudit" checked="true" onClick="javascript:event.cancelBubble=true" disabled/>
							    	</logic:equal>
							    	<logic:notEqual name="RateAuditVO" property="dsnStatus" value="F">
							    	<input type="checkbox" tabindex="-1" name="applyAudit"  property="applyAudit" checked="true" onClick="javascript:event.cancelBubble=true" />
							    	</logic:notEqual>
							     </logic:equal>
							     <logic:notEqual name="applyAutd" value="Y">
							     	<input type="checkbox" tabindex="-1" property="applyAudit" name="applyAudit" onClick="javascript:event.cancelBubble=true" />
							     </logic:notEqual>
							     </logic:present>
							     <logic:notPresent name="RateAuditVO" property="applyAutd">
							    	<input type="checkbox"tabindex="-1" property="applyAudit" name="applyAudit" onClick="javascript:event.cancelBubble=true"  />
							     </logic:notPresent>
									<ihtml:hidden property="applyAutdits" />
							</td>
							<td  class="ic-center">
							<logic:present  name="RateAuditVO" property="dsnStatus">
							<logic:present name="OneTimeValues">
							<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
							<logic:equal name="parameterCode" value="mailtracking.mra.defaults.rateaudit.status">
							<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<logic:present name="parameterValue" property="fieldValue">
							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
							<bean:define id="dsnStatus" name="RateAuditVO" property="dsnStatus"/>
							<%String field=dsnStatus.toString();%>
							<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
							<bean:write name="parameterValue" property="fieldDescription" />
							<ihtml:hidden property="saveDsnStatus" value="<%=field%>"/>
							</logic:equal>
							</logic:present>
							</logic:iterate>
							</logic:equal>
							</logic:iterate>
							</logic:present>
							</logic:present>
							<logic:notPresent name="RateAuditVO" property="dsnStatus">
							<ihtml:hidden property="saveDsnStatus" value=""/>
							</logic:notPresent>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="dsn">
									<bean:define id="dsn" name="RateAuditVO" property="dsn" />
									<%=dsn%>
								</logic:present>							
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="dsnDate">
									<bean:define id="dsnDateId" name="RateAuditVO" property="dsnDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
									<% String dsnDate=TimeConvertor.toStringFormat(dsnDateId.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
									<%=dsnDate%>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="origin">
							<bean:define id="origin" name="RateAuditVO" property="origin" />
							<%=origin.toString().substring(2,5)%>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">							
							<logic:present name="RateAuditVO" property="destination">
							<bean:define id="destination" name="RateAuditVO" property="destination" />
							<%=destination.toString().substring(2,5)%>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="carrierCode">
							<bean:define id="carrierCode" name="RateAuditVO" property="carrierCode" />
							<%=carrierCode%>
							</logic:present>

							<logic:present name="RateAuditVO" property="flightNumber">
							<bean:define id="flightNumber" name="RateAuditVO" property="flightNumber" />
							<%=flightNumber%>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="flightDate">
							<bean:define id="flightDateId" name="RateAuditVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
							<% String flightDate=TimeConvertor.toStringFormat(flightDateId.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
							<%=flightDate%>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="pcs">
							<bean:define id="pcs" name="RateAuditVO" property="pcs" />
							<%=pcs%>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="grossWt">
							<bean:write name="RateAuditVO" property="grossWt" format="####.00"/>
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="rate">
								<logic:lessEqual name="rate" value="0">
								<bean:write name="RateAuditVO" property="rate" format="0.0000"/>
								</logic:lessEqual>

								<logic:greaterThan name="rate"  value="0">
								<bean:write name="RateAuditVO" property="rate" format="####.0000"/>
								</logic:greaterThan>
							</logic:present>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="currency">
							<bean:write name="RateAuditVO" property="currency" />
							</logic:present>
							</td>
							</td>
							
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="presentWtCharge">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="RateAuditVO"  moneyproperty="presentWtCharge" property="presentWtCharge" />
							</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
							<logic:present name="RateAuditVO" property="auditedWtCharge">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="RateAuditVO"  moneyproperty="auditedWtCharge" property="auditedWtCharge" />
							</logic:present>
							</td>
							<td class="ic-center">
									<logic:present name="RateAuditVO" property="discrepancyNo">
								 		<logic:equal name="RateAuditVO" property="discrepancyNo" value="Y">
								  			N
								 		</logic:equal>
								  		<logic:notEqual name="RateAuditVO" property="discrepancyNo" value="Y">
								   			<ibusiness:moneyDisplay showCurrencySymbol="false" name="RateAuditVO"  moneyproperty="discrepancyYes" property="discrepancyYes" />
								  		</logic:notEqual>
								 	</logic:present>
								 	<logic:notPresent name="RateAuditVO" property="discrepancyNo">

								 	</logic:notPresent>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="gpaCode">
									<bean:define id="gpaCode" name="RateAuditVO" property="gpaCode" />
									<%=gpaCode%>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="category">
									<bean:define id="category" name="RateAuditVO" property="category" />
									<%=category%>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="malClass">
									<bean:define id="malClass" name="RateAuditVO" property="malClass" />
									<%=malClass%>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="subClass">
									<bean:define id="subClass" name="RateAuditVO" property="subClass" />
									<%=subClass%>
								</logic:present>
							</td>
							<td class="iCargoTableDataTd ic-center">
								<logic:present name="RateAuditVO" property="billTo">
									<bean:define id="billTo" name="RateAuditVO" property="billTo" />
									<%=billTo%>
									<ihtml:hidden property="excepFlg" value="N"/>
								</logic:present>
								<logic:notPresent name="RateAuditVO" property="billTo">
									<ihtml:hidden property="excepFlg" value="Y"/>
								</logic:notPresent>
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
			<ihtml:button property="btRateAudit"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_RATEAUDIT" >
				<common:message key="mailtracking.mra.defaults.listRateAudit.btn.rateaudit" />
			</ihtml:button>
			<ihtml:button property="btRateAuditDetails"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_RATEAUDITDETAILS" >
				<common:message key="mailtracking.mra.defaults.listRateAudit.btn.rateauditdetails" />
			</ihtml:button>
			<ihtml:button property="btListprorationException"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_LISTPRORATIONEXCEPTION" >
				<common:message key="mailtracking.mra.defaults.listRateAudit.btn.listprorationexception" />
			</ihtml:button>
			<ihtml:button property="btPrint"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_PRINT" >
				<common:message key="mailtracking.mra.defaults.listRateAudit.btn.print" />
			</ihtml:button>
			<ihtml:button property="btClose"  componentID="CMP_MRA_DEFAULTS_LISTRATEAUDIT__BTN_CLOSE" >
				<common:message key="mailtracking.mra.defaults.listRateAudit.btn.close" />
			</ihtml:button>
		</div>
		</div>
		</div>
		
		
	</ihtml:form>


</div>

	
	</body>
</html:html>
