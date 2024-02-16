<%--
* Project	 		: iCargo
* Module Code & Name		: mailtracking.mra-defaults
* File Name			: ListCN51s.jsp
* Date				: 14-Mar-2007
* Author(s)			: A-2049
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page
    import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm" %>
<%@ page
    import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO" %>
<%@ page
    import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
		

	
	
<html:html locale="true">

<head>


	

	<title>
		<bean:message bundle="listCN51ScreenBundle" key="mailtracking.mra.defauts.listCN51.header.listCN51" />
	</title>

	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/ListCN51s_Script.jsp" />

</head>

<body style="overflow:auto;" onload=" stripedTable(); addIEonScroll();">
	
	
	
	

<business:sessionBean
  		id="KEY_INTBLGTYPE"
  		moduleName="mailtracking.mra"
  		screenID="mailtracking.mra.airlinebilling.defaults.listCN51s"
  		method="get"
  		attribute="oneTimeValues" />

<business:sessionBean
  		id="KEY_ARLCN51VOS"
  		moduleName="mailtracking.mra"
  		screenID="mailtracking.mra.airlinebilling.defaults.listCN51s"
  		method="get"
  		attribute="airlineCN51SummaryVOs" />

<!--PAGE CONTENT STARTS-->

<bean:define id="form" name="ListCN51ScreenForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm"
	toScope="page" />

<div class="iCargoContent ic-masterbg"  id="contentdiv">

<ihtml:form
       action="/mailtracking.mra.airlinebilling.defaults.listCN51s.screenload.do">

      <ihtml:hidden name="form" property="closeFrmCN51Flag" />
      <ihtml:hidden name="form" property="closeFrmCN66Flag" />

 <ihtml:hidden name="form" property="screenStatus" />
<ihtml:hidden name="form" property="accEntryFlag" />
 <html:hidden property="displayPage" />
  <html:hidden property="lastPageNumber" />  
<input type="hidden" name="mySearchEnabled" />

 <div class="ic-content-main" >
		<span class="ic-page-title ic-display-none">
		<common:message key="mailtracking.mra.airlinebilling.defaults.captureCN66.pagetitle" />
		</span>
			<div class="ic-head-container">
			<div class="ic-filter-panel">
					<div class="ic-input-container ic-round-border">
							<div class="ic-row ic-label-50">
									<div class="ic-input ic-split-25 ic-mandatory">
									<label><common:message  key="mailtracking.mra.defauts.listCN51.label.billedDateFrom"/></label>
									<ibusiness:calendar componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_DATE_BLGFRMDATE" property="blgFromDateStr"  type="image" id="blgFromDateStr" />
									</div>
									<div class="ic-input ic-split-25 ic-mandatory">
									<label><common:message  key="mailtracking.mra.defauts.listCN51.label.billedDateTo"/></label>
									<ibusiness:calendar componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_DATE_BLGTOODATE" property="blgToDateStr"  type="image" id="blgToDateStr" />
									</div>
									<div class="ic-input ic-split-25">
									<label><common:message   key="mailtracking.mra.defauts.listCN51.label.airlineCode"/></label>
									<ihtml:text property="airlineCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_TXT_ARLCOD"  maxlength="4" />
									<div class="lovImg">
									<img name="airlineCodelov" id="airlineCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
									</div>
									</div>
									<div class="ic-input ic-split-25">
									<label><common:message   key="mailtracking.mra.defauts.listCN51.label.intBlgMode"/></label>
									<ihtml:select property="interlineBlgType" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_CMBBOX_INTBLGMOD" value="<%=form.getInterlineBlgType()%>">
									<logic:present name="KEY_INTBLGTYPE">
									<logic:iterate id="oneTimeValue" name="KEY_INTBLGTYPE">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="mailtracking.mra.billingtype">
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
									</div>
									<!-- Added by A-7929 as part of ICRD-265471 -->
									<div class="ic-input ic-split-25">
									<label><common:message   key="mailtracking.mra.defauts.listCN51.label.invoiceno"/></label>
									<ihtml:text property="invoiceNo" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_TXT_INVOICE_NO"  maxlength="18" />
									<div class="lovImg">
				          			 <img src="<%=request.getContextPath()%>/images/lov.png" id="invoiceNumberLov" height="22" width="22" alt="" />
									 </div>
									 </div>
									<div class="ic-input ic-split-20">
									<label><common:message   key="mailtracking.mra.defauts.listCN51.label.intBlgType"/></label>
									<ihtml:select property="billingType" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_CMBBOX_INTBLGTYPE" value="<%=form.getBillingType()%>">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_INTBLGTYPE">
									<logic:iterate id="oneTimeValue" name="KEY_INTBLGTYPE">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="mailtracking.mra.invoicetype">
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
									</div>
									
							
								<div class="ic-input ic-split-20 ic-button-container">
									<ihtml:nbutton property="btList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_LIST" accesskey="L">
										<common:message   key="mailtracking.mra.defauts.listCN51.button.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_CLEAR" accesskey="C">
										<common:message   key="mailtracking.mra.defauts.listCN51.button.clear" />
									</ihtml:nbutton>
								</div>
							</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message   key="mailtracking.mra.defauts.listCN51.tableTitle.listCN51sTable"/></h4>
				</div>
				<div class="ic-row">
				<logic:present name="KEY_ARLCN51VOS">
					<div class="ic-col-30">
					<logic:present name="KEY_ARLCN51VOS">
									   <bean:define id="listCN51Page" name="KEY_ARLCN51VOS"/>
									   <common:paginationTag pageURL="mailtracking.mra.airlinebilling.defaults.listCN51s.listCN51.do"
										name="listCN51Page"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNumber() %>"/>
								  </logic:present>
								 <logic:notPresent name="listCN51Page">
										 &nbsp;
								 </logic:notPresent>
					</div>
					<div class="ic-col-70">
						<div class="ic-button-container paddR5">
							<logic:present name="listCN51Page">
								<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
								name="listCN51Page"
								display="pages"
								lastPageNum="<%=form.getLastPageNumber()%>"  
								exportToExcel="true"
								exportTableId="captureAgtSettlementMemo" 
								exportAction = "mailtracking.mra.airlinebilling.defaults.listCN51s.listCN51.do"/>
							</logic:present>
							<logic:notPresent name="listCN51Page">
								&nbsp;
							</logic:notPresent>
						</div>
					</div>
					</logic:present>
				 <logic:notPresent name="KEY_ARLCN51VOS">
					&nbsp;
				</logic:notPresent>
				</div>
				<div class="ic-row">
					<div class="tableContainer" id="tableDiv1"  style="height:720px">
					<table class="fixed-header-table" id="captureAgtSettlementMemo" >
					<thead>
					<tr >
					<td  class="iCargoTableHeader" width="1%"></td>

					<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.invoiceNumber" /><span></span></td>

					<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.invoiceDate" /><span></span></td>

					<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.clrPeriod" /><span></span></td>

					<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.billingType" /><span></span></td>

					<td  class="iCargoTableHeader" width="6%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.airlineCode" /><span></span></td>

					<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.invoiceAmt" /><span></span></td>

					<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.currency" /><span></span></td>
					
					<td  class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defauts.listCN51.tablecolumn.invoiceStatus" /><span></span></td>
					</tr>
					</thead>
					<tbody>
					<logic:present name="KEY_ARLCN51VOS">
					<logic:iterate id="AirlineCN51VO" name="KEY_ARLCN51VOS" indexId="rowCount">
					<tr>
					<td>
					<input type="checkbox" name="tableRowId" value="<%=rowCount.toString()%>" onclick="singleSelectCb(this.form,'<%= rowCount.toString() %>','tableRowId');"/>
					</td>
					<td ><common:write name="AirlineCN51VO" property="invoicenumber"/>
					<logic:present name="AirlineCN51VO" property="invoicenumber">
					<bean:define id="invoiceNumber" name="AirlineCN51VO" property="invoicenumber"/>
					<ihtml:hidden  property="invoiceNumber" value="<%=String.valueOf(invoiceNumber)%>"/>
					</logic:present>
					<logic:notPresent name="AirlineCN51VO" property="invoicenumber">
					<ihtml:hidden  property="invoiceNumber"/>
					</logic:notPresent>
					</td>
					<!--<td style="text-align:right"><common:write name="AirlineCN51VO" property="invoiceDate"/></td>-->
					<td>
					<!-- Added as part of ICRD-343336-->
					 <logic:present name="AirlineCN51VO" property="invoiceDate">
					<bean:define id="invoiceDate" name="AirlineCN51VO" property="invoiceDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                     <%String invDate=TimeConvertor.toStringFormat(invoiceDate.toCalendar(),"dd-MMM-yyyy");%>
                                                               <%=invDate%>
					</logic:present>										   
                    </td>
					<td style="text-align:right"><common:write name="AirlineCN51VO" property="clearanceperiod"/></td>
                    <td><common:write name="AirlineCN51VO" property="billingType"/></td>
					<td><common:write name="AirlineCN51VO" property="airlinecode"/></td>

					<td style="text-align:right"><bean:write name="AirlineCN51VO" property="totalAmountInContractCurrency" localeformat="true"/></td>

					<td><common:write name="AirlineCN51VO" property="contractCurrencycode"/></td>
					<td><common:write name="AirlineCN51VO" property="invStatus"/></td>
					</tr>
					</logic:iterate>
					</logic:present>
					</tbody>
					</table>
					</div>
				</div>
			</div>
			<div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
				
			<common:xgroup>
				<common:xsubgroup id="AA_SPECIFIC">
					<ihtml:miscprintmultibutton componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_EXTRACT_SIS_DETAILS" liteMode="true" property="printMiscReportButtonId">
						<common:message key="mailtracking.mra.airlinebilling.defauts.listCN51.tooltip.button.extractSisDetails" />
					</ihtml:miscprintmultibutton>
				</common:xsubgroup>
			</common:xgroup>	
			<ihtml:nbutton property="btnAccEntries" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_ACCENTRIES" accesskey="A">
				<common:message key="mailtracking.mra.defauts.listCN51.tooltip.button.accentries"/>
			</ihtml:nbutton>
			
			<ihtml:nbutton property="btnFinalizeInvoice" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_FINALIZE" accesskey="F">
				<common:message key="mailtracking.mra.defauts.listCN51.tooltip.button.finalizeInvoice"/>
			</ihtml:nbutton>
			
			<ihtml:nbutton property="btnCN66Details" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_CN66DTL" accesskey="N">
				<common:message key="mailtracking.mra.defauts.listCN51.tooltip.button.cn66Details"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btnCN51Details" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_CN51DTL" accesskey="T">
				<common:message key="mailtracking.mra.defauts.listCN51.tooltip.button.cn51Details"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTCN51_BTN_CLOSE" accesskey="O">
				<common:message key="mailtracking.mra.defauts.listCN51.tooltip.button.close"/>
			</ihtml:nbutton>
				</div>
			</div>
			</div>
</div>




</ihtml:form>
</div>


	</body>
</html:html>
















