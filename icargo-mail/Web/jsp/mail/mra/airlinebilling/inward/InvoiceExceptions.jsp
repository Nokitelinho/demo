<%--/***********************************************************************
* Project	     	 	 : iCargo
* Module Code & Name 	 : MailTracking-MRA-AirlineBilling-Inward
* File Name          	 : InvoiceExceptions.jsp
* Date                 	 : 20-Feb-2006
* Author(s)              : Sreekanth V G(For R3 development)
****************************************************************************/
--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm" %>
<%@ page import=  "com.ibsplc.xibase.util.time.TimeConvertor"%>


<business:sessionBean id="OneTimeVOs"
		moduleName="mailtracking.mra"
		screenID="mailtracking.mra.airlinebilling.inward.invoiceexceptions"
		method="get"
		attribute="OneTimeVOs" />

<business:sessionBean id="ExceptionInInvoiceVOs"
		moduleName="mailtracking.mra"
		screenID="mailtracking.mra.airlinebilling.inward.invoiceexceptions"
		method="get"
		attribute="ExceptionInInvoiceVOs" />
<bean:define id="form"
		name="InvoiceExceptionsForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm"
		toScope="page" />


	
	
<html:html>
<head>



	<title>
		<common:message bundle="invoiceexceptionsresources" key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.title" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/InvoiceExceptions_Script.jsp"  />
</head>
<body>
	
	


	<%@include file="/jsp/includes/reports/printFrame.jsp" %>

  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="contentdiv" >
	<ihtml:form action="/mailtracking.mra.airlinebilling.inward.invoiceexceptionsscreenload.do">
	<ihtml:hidden property="contractCurrency" />
	<ihtml:hidden property="fromScreenFlag"/>
	<ihtml:hidden property="cn66CloseFlag"/>
<html:hidden property="displayPage" />
  <html:hidden property="lastPageNumber" />

		<div class="ic-content-main">
			<span class="ic-page-title"><common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.invoiceexceptions" /></span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row ic-label-45">
									<div class="ic-input ic-mandatory ic-split-25">
									<label><common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.airlinecode" /></label>
									<ihtml:text property="airlineCode" componentID="TXT_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_AirlineCode" maxlength="3" />
									<div class="lovImg">
									<img height="22" id="airlinelov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" alt=""  />
									</div>
								</div>
								<div class="ic-input ic-split-25">
									<label><common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.invoicenumber" /></label>
									<ihtml:text property="invoiceNumber" componentID="TXT_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_InvoiceNumber" maxlength="11" />
									<div class="lovImg">
									<img height="22" id="invoiceNumberlov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" alt=""  />
								</div>
								</div>
								<div class="ic-input ic-split-25">
									<label><common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.clearenceperiod" /></label>
									<ihtml:text property="clearencePeriod" componentID="TXT_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_ClearencePeriod" maxlength="11" />
									<div class="lovImg">
									<img height="22" id="clearencePeriodlov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" alt=""  />
									</div>
								</div>
								<div class="ic-input ic-split-25">
									<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_List" accesskey="L">
											<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.list" />
									</ihtml:nbutton>

									<ihtml:nbutton property="btnClear" componentID="Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_Clear" accesskey="C">
											<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.clear" />
									</ihtml:nbutton>
									</div>
								</div>

							</div>
						</div>

					</div>
					</div>



           <div class="ic-main-container">
				<div class="ic-row">
			<h4><common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.exceptiondetails"/></hr>
				</div>
					<div class="ic-row">
			<logic:present name="ExceptionInInvoiceVOs">

										  <logic:present name="ExceptionInInvoiceVOs">
									   <bean:define id="invoiceExceptionPage" name="ExceptionInInvoiceVOs"/>
									   <common:paginationTag pageURL="mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do"
										name="invoiceExceptionPage"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNumber() %>"/>
								  </logic:present>
								 <logic:notPresent name="invoiceExceptionPage">
										 &nbsp;
								 </logic:notPresent>
						   <div class="ic-button-container paddR5">
							<logic:present name="invoiceExceptionPage">
								<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
								name="invoiceExceptionPage"
								display="pages"
								linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getLastPageNumber()%>"
								exportToExcel="true"
							    exportTableId="ExceptionDetails"
							    exportAction = "mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do"/>
							</logic:present>
							<logic:notPresent name="invoiceExceptionPage">
								&nbsp;
							</logic:notPresent>
						   </div>


					   </logic:present>
				</div>




			<div class="ic-row">
				<div class="tableContainer" id="div1" style="height:601px;">
					  <table class="fixed-header-table" id="ExceptionDetails" >
					    <thead>
							<tr>
								<td width="2%" align="left" class="iCargoTableHeader">
									<input type="checkbox"  name="headCheck"  value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].headCheck,document.forms[1].rowId)" />
								</td>
								<td  width="12%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.invoicerefno"/>
								</td>
								<td width="12%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.clearenceperiod"/>
								</td>
								<td  width="7%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.provisionalamount"/>
								</td>
								<td width="7%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.reportedamount"/>
								</td>
								<td  width="8%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.differenceamount"/>

								<td  width="8%" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.contractcurrency"/>
								</td>
							</tr>
					</thead>
			   <tbody>
					<logic:present name="ExceptionInInvoiceVOs">
						<logic:iterate id="ExceptionInInvoiceVO" name="ExceptionInInvoiceVOs"  indexId="rowCount"  >

								<tr>
										<td style="text-align:center">
											<html:checkbox name="form" property="rowId" value="<%= String.valueOf(rowCount) %>" onclick="toggleTableHeaderCheckbox('rowId',document.forms[1].headCheck)"  />
										</td>
										<td class="iCargoTableDataTd">

												<common:write name="ExceptionInInvoiceVO" property="invoiceNumber"/>
												<logic:present name="ExceptionInInvoiceVO" property="invoiceNumber">
													<bean:define id="invnum" name="ExceptionInInvoiceVO" property="invoiceNumber"/>
													<ihtml:hidden  property="invNumber" value="<%=String.valueOf(invnum)%>"/>
												</logic:present>


										</td>
										<td class="iCargoTableDataTd">

												<common:write name="ExceptionInInvoiceVO" property="clearancePeriod"/>

										</td>
										<td class="iCargoTableDataTd" >

												<logic:present name="ExceptionInInvoiceVO" property="provAmt">
													 <ibusiness:moneyDisplay showCurrencySymbol="false" name="ExceptionInInvoiceVO" moneyproperty="provAmt" property="provAmt" />
												</logic:present>
												<logic:notPresent name="ExceptionInInvoiceVO" property="provAmt">
													  &nbsp;
												</logic:notPresent>

										</td>
										<td class="iCargoTableDataTd" >

												<logic:present name="ExceptionInInvoiceVO" property="reportedAmt">
													 <ibusiness:moneyDisplay showCurrencySymbol="false" name="ExceptionInInvoiceVO" moneyproperty="reportedAmt" property="reportedAmt" />
												</logic:present>
												<logic:notPresent name="ExceptionInInvoiceVO" property="reportedAmt">
													  &nbsp;
												</logic:notPresent>

										</td>
										<td class="iCargoTableDataTd" >

												<logic:present name="ExceptionInInvoiceVO" property="diffAmt">
													 <ibusiness:moneyDisplay showCurrencySymbol="false" name="ExceptionInInvoiceVO" moneyproperty="diffAmt" property="diffAmt" />
												</logic:present>
												<logic:notPresent name="ExceptionInInvoiceVO" property="diffAmt">
													  &nbsp;
												</logic:notPresent>

										</td>

										<td class="iCargoTableDataTd">

												<common:write name="ExceptionInInvoiceVO" property="contractCurrency"/>

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
						<div class="ic-row">
							<div class="ic-button-container paddR5">


							<ihtml:nbutton property="btnPrint" componentID="Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_PrintExceptionInvoice" accesskey="P">
									<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.print" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btnViewExcDetails" componentID="Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_View" accesskey="X">
								<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.viewexcedetails" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btnClose" componentID="Btn_MailTracking_MRA_AirlineBilling_Inward_InvoiceExceptions_Close" accesskey="O">
								<common:message key="mailtracking.mra.airlinebilling.inward.invoiceexceptions.close" />
							</ihtml:nbutton>

						</div>
					</div>
				</div>

			</div>
		</ihtml:form>
	</div>
       <!---CONTENT ENDS-->





	</body>
</html:html>
