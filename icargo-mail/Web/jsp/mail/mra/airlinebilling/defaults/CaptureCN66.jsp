<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : CaptureCN66.jsp
* Date                 	 : 12-Feb-2007,11-AUG-2008
* Author(s)              : A-2408,A-2391
*********************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form" %>
<bean:define id="form"
		 name="CaptureCN66Form"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form"
		 toScope="page" />
<%@ page import="java.util.Formatter" %>

<%
   String FORMAT_STRING = "%1$-16.2f";
%>



<html:html locale="true">
<head>



<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.airlinebilling.defaults.captureCN66.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/CaptureCN66_Script.jsp" />
</head>

<body style="overflow:auto;">



<%@ include file="/jsp/includes/reports/printFrame.jsp"%>

 <business:sessionBean id="KEY_ONETIMES"
   	moduleName="mailtracking.mra.airlinebilling.defaults"
   	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
 	method="get" attribute="oneTimeVOs" />
 <business:sessionBean id="CN66DETAILS_MAP"
    	moduleName="mailtracking.mra.airlinebilling.defaults"
    	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
  	method="get" attribute="AirlineCN66DetailsVOs" />

<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.airlinebilling.defaults"
  	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
	method="get" attribute="systemparametres" />
  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="contentdiv" style="overflow:auto;height:100%">
  <ihtml:form action="/mailtracking.mra.airlinebilling.defaults.captureCN66.onScreenLoad.do">
	  <ihtml:hidden property="airlineIdentifier"/>
	   <ihtml:hidden property="screenStatus"/>
	   <ihtml:hidden property="lastPageNum"/>
	   <ihtml:hidden property="displayPage"/>
	     <ihtml:hidden name="form" property="rowCounter"/>
		 <ihtml:hidden property="netChargeMoneyDisp" />
		 <ihtml:hidden property="linkStatus" />


		 <div class="ic-content-main" >
		<span class="ic-page-title ic-display-none">
		<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.pagetitle" />
		</span>
			<div class="ic-head-container">
			<div class="ic-filter-panel">
					<div class="ic-input-container">
					
								<jsp:include page="/jsp/mail/mra/airlinebilling/defaults/CaptureCN66_Filter.jsp" />
					</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<fieldset class="ic-field-set">
						<legend><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.lbl.summary" /></legend>
						<div class="ic-row">
							<div class="ic-input ic-split-22 paddL10">
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.lbl.totwt" />
							 <common:display name="form" property="netSummaryWeight"  />
							 </div>
							 <div class="ic-input ic-split-20">
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.lbl.totchg" />
							 <common:display property="netChargeMoneyDisp" name="form" />
							 </div>
							 <div class="ic-input ic-split-22">
							<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.listingCurrency"/>
								 <logic:present name="form" property="blgCurCode">
										<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_INWARD_CURCOD" property="blgCurCode" readonly="true" />
								</logic:present>
							 </div>
						</div>
					</fieldset>
				</div>
				<div class="ic-row iCargoHeadingLabel">
					<h4><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.cn66details"/></h4>
				</div>
				<div class="ic-row">
					<div class="ic-col-55 paddL10">
						<logic:present name="CN66DETAILS_MAP">
						<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
						name="CN66DETAILS_MAP"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=((CaptureCN66Form)form).getLastPageNum() %>" />
						</logic:present>

						<logic:notPresent name="CN66DETAILS_MAP">
						&nbsp;
						</logic:notPresent>
					</div>
					<div class="ic-col-30">
						<div class="ic-button-container">
							<logic:present name="CN66DETAILS_MAP">
							<common:paginationTag
							pageURL="javascript:submitPage1('lastPageNum','displayPage')"
							name="CN66DETAILS_MAP"
							display="pages"
							linkStyleClass="iCargoLink"
							disabledLinkStyleClass="iCargoLink"
							lastPageNum="<%=((CaptureCN66Form)form).getLastPageNum() %>"
							exportToExcel="true"
							exportTableId="captureAgtSettlementMemo"
							exportAction="mailtracking.mra.airlinebilling.defaults.captureCN66.listcn66.do"/>
							</logic:present>

							<logic:notPresent name="CN66DETAILS_MAP">
												&nbsp;
							</logic:notPresent>
						</div>
					</div>
					<div class="ic-col-15">
						<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" id="btnAdd"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.add"/></a>
							 <a href="#" class="iCargoLink" id="btnModify"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.modify"/></a>
								<a href="#" class="iCargoLink" id="btnDelete"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.delete"/></a>
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div class="tableContainer table-border-solid" id="div1" style="height:420px" style="widht:110%"><!-- Modified by A-8236 for ICRD-251814 -->
					<table class="fixed-header-table" id="captureAgtSettlementMemo">
					<thead>
					<tr class="ic-th-all">
						<th style="width: 2%;">
						<th style="width: 6%;">
						<th style="width: 6%;">
						<th style="width: 10%;">

						<th style="width: 4%;">
						<th style="width: 4%;">
						<th style="width: 5%;">
						<th style="width: 5%;">
						<th style="width: 4%;">


						<th style="width: 4%;">
						<th style="width: 5%;">
						<th style="width: 8%;">
						<th style="width: 5%;">
						<th style="width: 5%;">

						<th style="width: 5%;">
						<th style="width: 5%;">
						<th style="width: 7%;">
						<th style="width: 10%;">
						<th style="width: 10%;">
					</tr>
					<tr>
					<td class="iCargoTableHeader ic_inline_chcekbox ic-center" rowspan="2"><input type="checkbox" name="allCheck" /></td>
					<td class="iCargoTableHeader" colspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.carriage"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.category"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.org"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.dest"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.fltno"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.dsn"/></td>
					<logic:present name="KEY_SYSPARAMETERS">
					<logic:iterate id="oneTimeValue" name="KEY_SYSPARAMETERS">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.defaults.DsnLevelImportToMRA">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:equal name="parameterValues" value="N">
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.rsn"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.hni"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.ri"/></td>
					</logic:equal>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.dateofissue"/></td>
					<td class="iCargoTableHeader" colspan="4"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.wt"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.rate"/></td>
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.amount"/></td>
					<logic:present name="KEY_SYSPARAMETERS">
					<logic:iterate id="oneTimeValue" name="KEY_SYSPARAMETERS">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.defaults.DsnLevelImportToMRA">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:equal name="parameterValues" value="N">
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.despatchmailbagstatus"/></td>
					</logic:equal>
					<logic:notEqual name="parameterValues" value="N">
					<td class="iCargoTableHeader" rowspan="2"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.despatchstatus"/></td>
					</logic:notEqual>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</tr>
					<tr>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.from"/></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.to"/></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.lc/ao"/></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.cp"/></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.sv"/></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.ems"/></td>
					</tr>

					</thead>
					<tbody>
					<logic:present name="CN66DETAILS_MAP">
					<%int selectCount=0;%>

					<logic:iterate id="cn66Value" name="CN66DETAILS_MAP" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO" indexId="rowCount">
					<%int deleteCount=0;%>
					<%int checksize=0;%>
					<ihtml:hidden property="outerRowId" value="<%=String.valueOf(rowCount)%>"/>
					<logic:present name="cn66Value" property="operationFlag">
					<ihtml:hidden name="cn66Value" property="operationFlag"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="operationFlag">
					<ihtml:hidden name="form" property="operationFlag" value=""/>
					</logic:notPresent>
					<logic:notEqual name="cn66Value" property="operationFlag" value="D">
					<%checksize++;%>
					</logic:notEqual>

					<bean:define id="count" value="<%=String.valueOf(rowCount)%>"/>
					<bean:define id="deletecount" value="<%=String.valueOf(deleteCount)%>"/>
					<logic:equal name="cn66Value" property="operationFlag" value="D">

					<ihtml:hidden name="form" property="carriageFrom"/>
					<ihtml:hidden name="form" property="carriageTo"/>
					<ihtml:hidden name="form" property="mailCategoryCode"/>
					<ihtml:hidden name="form" property="origin"/>
					<ihtml:hidden name="form" property="destination"/>
					<ihtml:hidden name="form" property="flightNumber"/>
					<ihtml:hidden name="form" property="despatchSerialNo"/>
					<ihtml:hidden name="form" property="despatchDate"/>

					<ihtml:hidden name="form" property="totalWeight"/>
					<ihtml:hidden name="form" property="rate"/>
					<ihtml:hidden name="form" property="amount"/>

					<%selectCount++;%>
					</logic:equal>
					<logic:notEqual name="cn66Value" property="operationFlag" value="D">

					<tr>


					<td class="ic_inline_chcekbox ic-center">
					<ihtml:checkbox property="check" value="<%=String.valueOf(selectCount)%>"/>

					</td>
					<logic:equal name="deletecount" value="0">

					<td rowspan ="<%=checksize%>">

					<logic:present	name="cn66Value" property="carriageFrom">
					<bean:write name="cn66Value" property="carriageFrom"/>
					</logic:present>

					</td>
					<td rowspan ="<%=checksize%>">

					<logic:present	name="cn66Value" property="carriageTo">
					<bean:write name="cn66Value" property="carriageTo"/>
					</logic:present>

					</td>

					</logic:equal>

					<td>


					<logic:present	name="cn66Value" property="mailCategoryCode">
					<logic:present name="KEY_ONETIMES">
					<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="parameterValue" property="fieldValue">

					<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
					<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
					<bean:define id="mailCategoryCode" name="cn66Value" property="mailCategoryCode"/>
					<%String field=mailCategoryCode.toString();%>
					<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
					<bean:write name="parameterValue" property="fieldDescription" />
					<ihtml:hidden property="mailCategoryCode" value="<%=field%>"/>
					</logic:equal>
					</logic:present>
					</logic:iterate>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</logic:present>
					</td>
					<td>

					<logic:present	name="cn66Value" property="origin">
					<bean:write name="cn66Value" property="origin"/>
					</logic:present>

					</td>
					<td>

					<logic:present	name="cn66Value" property="destination">
					<bean:write name="cn66Value" property="destination"/>
					</logic:present>

					</td>
					<td>

					<logic:present	name="cn66Value" property="flightCarrierCode">
					<bean:write name="cn66Value" property="flightCarrierCode"/>
					</logic:present>
					<logic:present	name="cn66Value" property="flightNumber">
					<bean:write name="cn66Value" property="flightNumber"/>
					</logic:present>

					</td>

					<td>
					<logic:present	name="cn66Value" property="despatchSerialNo">
					<bean:write name="cn66Value" property="despatchSerialNo"/>
					</logic:present>
					</td>
					<logic:present name="KEY_SYSPARAMETERS">
					<logic:iterate id="oneTimeValue" name="KEY_SYSPARAMETERS">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.defaults.DsnLevelImportToMRA">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:equal name="parameterValues" value="N">
					<td>
					<logic:present	name="cn66Value" property="receptacleSerialNo">
					<bean:write name="cn66Value" property="receptacleSerialNo"/>
					</logic:present>
					</td>
					<td>
					<logic:present	name="cn66Value" property="hni">
					<bean:write name="cn66Value" property="hni"/>
					</logic:present>
					</td>
					<td>
					<logic:present	name="cn66Value" property="regInd">
					<bean:write name="cn66Value" property="regInd"/>
					</logic:present>
					</td>
					</logic:equal>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					<td>

					<logic:present	name="cn66Value" property="despatchDate">
					<bean:define id="despatchDate" name="cn66Value" property="despatchDate" />
					<%

					String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)despatchDate).toCalendar(),"dd-MMM-yyyy");
					%>
					<%=assignedLocalDate%>
					</logic:present>

					</td>
					<%
					String localMailCategory="";
					%>
					<logic:present	name="cn66Value" property="mailSubClass">
					<bean:define id="mailSubClass" name="cn66Value" property="mailSubClass" />
					<%
					localMailCategory=mailSubClass.toString();

					%>
					<bean:define id="mailcat" value="<%=localMailCategory%>"/>
					</logic:present>
					<td  id="totalWeightLC">
					<div>
					<logic:present	name="cn66Value" property="totalWeight">


					<logic:equal name="mailcat" value="LC">
					<common:write name="cn66Value" property="totalWeight" unitFormatting="true" />


					</logic:equal>
					</logic:present>

					</div>
					</td>
					<td  id="totalWeightCP">
					<div>
					<logic:present	name="cn66Value" property="totalWeight">



					<logic:equal name="mailcat" value="CP">
					<common:write name="cn66Value" property="totalWeight" unitFormatting="true" />

					</logic:equal>
					</logic:present>
					</div>
					</td>
					<td id="totalWeightSV">
					<div>
					<logic:present	name="cn66Value" property="totalWeight">



					<logic:equal name="mailcat" value="SV">
					<common:write name="cn66Value" property="totalWeight" unitFormatting="true" />

					</logic:equal>
					</logic:present>
					</div>
					</td>
					<td id="totalWeightEMS">
					<div>
					<logic:present	name="cn66Value" property="totalWeight">



					<logic:equal name="mailcat" value="EMS">
					<common:write name="cn66Value" property="totalWeight" unitFormatting="true" />

					</logic:equal>
					</logic:present>
					</div>
					</td>
					<td>
					<logic:present	name="cn66Value" property="rate">
					<logic:lessEqual name="rate" value="0">
							<bean:write name="cn66Value" property="rate" format="0.0000"/>
							</logic:lessEqual>

							<logic:greaterThan name="rate"  value="0">
							<bean:write name="cn66Value" property="rate" format="####.0000"/>
					</logic:greaterThan>


					</logic:present>
					</td>
					<td id="amount">
					<logic:present	name="cn66Value" property="amount">
					<ibusiness:moneyDisplay  name="cn66Value" moneyproperty="amount"    showCurrencySymbol="false"
					property="amount"
					/>
					</logic:present>
					</td>

					<td>


					<logic:present	name="cn66Value" property="despatchStatus">
					<logic:present name="KEY_ONETIMES">
					<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.mra.despatchstatus">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="parameterValue" property="fieldValue">

					<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
					<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
					<bean:define id="despatchStatus" name="cn66Value" property="despatchStatus"/>
					<%String field=despatchStatus.toString();%>
					<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
					<bean:write name="parameterValue" property="fieldDescription" />
					<ihtml:hidden property="despatchStatus" value="<%=field%>"/>
					</logic:equal>
					</logic:present>
					</logic:iterate>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</logic:present>
					<logic:notPresent name="cn66Value" property="despatchStatus">
					<ihtml:hidden property="despatchStatus" value="N"/>
					</logic:notPresent>
					</td>
					<%deleteCount++;%>

					<%selectCount++;%>
					</tr>
					</logic:notEqual>
					</logic:iterate>
					</logic:present>
					</tbody>
					<tfoot>
					<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<logic:present name="KEY_SYSPARAMETERS">
					<logic:iterate id="oneTimeValue" name="KEY_SYSPARAMETERS">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.defaults.DsnLevelImportToMRA">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:equal name="parameterValues" value="N">
					<td></td>
					<td></td>
					</logic:equal>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					<td style="text-align:left">
					<common:write name="form" property="netLCWeight" unitFormatting="true"/>
					</td>
					<td style="text-align:left">
					<common:write name="form" property="netCPWeight" unitFormatting="true"/>
					</td>
					<td style="text-align:left">
					<common:write name="form" property="netSVWeight" unitFormatting="true"/>

					</td>
					<td style="text-align:left"><common:write name="form" property="netEMSWeight" unitFormatting="true"/></td>
					<td> </td>

					<td>
					<div class="ic-left">
					<ibusiness:moneyDisplay moneyproperty="netChargeMoney"    showCurrencySymbol="false"
					property="netSummaryCharge" />
					</div>
					</td>
					<td></td>
					</tr>
					</tfoot>
					</table>
					</div>
				</div>

				<div class="ic-row paddL5">
				<div class="ic-input ic-split-15 ">
					<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.status" />
					<%String localstatus="";%>
					<logic:present name="cn66Value" property="cn51Status">
					<logic:present name="KEY_ONETIMES">
					<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
					<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
					<logic:equal name="parameterCode" value="mailtracking.mra.despatchstatus">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="parameterValue" property="fieldValue">

					<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
					<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

					<bean:define id="cn51Status" name="cn66Value" property="cn51Status"/>
					<%localstatus=cn51Status.toString();%>
					<logic:equal name="parameterValue" property="fieldValue" value="<%=localstatus%>">
					<common:display name="parameterValue" property="fieldDescription" />
					<ihtml:hidden property="cn51Status" value="<%=localstatus%>"/>
					</logic:equal>
					</logic:present>
					</logic:iterate>
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</logic:present>
				</div></div>
				</div>
			<div class="ic-foot-container paddR5">
				<div class="ic-button-container">
					<ihtml:nbutton property="btncn66Print" accesskey = "P" componentID="CMP_MRA_AIRLINEBILLING_CN66_PRINT" >
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.cn66Print" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btncn66PrintInv" accesskey = "N" componentID="CMP_MRA_AIRLINEBILLING_CN66_PRINT_INVOICE" >
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.cn66PrintInv" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btncn66ListAccDtls" accesskey ="A" componentID="CMP_MRA_AIRLINEBILLING_CN66_LISTACCDTLS" >
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.listaccdtls" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btncn51enquiry" accesskey ="F" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CN51ENQUIRY" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.cn51enquiry" />
						
						<ihtml:nbutton property="btnWithdraw" accesskey = "W" componentID="CMP_MRA_AIRLINEBILLING_WITHDRAW" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.withdraw" />
					        </ihtml:nbutton>
						
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnProcess" accesskey = "E" componentID="CMP_MRA_AIRLINEBILLING_INWARD_PROCESS" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.process" />
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnAssignException" accesskey = "X" componentID="CMP_MRA_AIRLINEBILLING_INWARD_ASSIGNEXP" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.assignexception" />
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnSave" accesskey = "S" componentID="CMP_MRA_AIRLINEBILLING_INWARD_SAVE" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.save" />
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnClose" accesskey = "O" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLOSE" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn66.button.close" />
					        </ihtml:nbutton>
				</div>
			</div>

		</div>

</ihtml:form>
</div>
<!---CONTENT ENDS-->


	</body>
</html:html>
