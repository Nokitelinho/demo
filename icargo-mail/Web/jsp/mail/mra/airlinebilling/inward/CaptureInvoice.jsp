<%--

* Project	 		: iCargo
* Module Code & Name: mra-airlinebilling
* File Name			: AirlineExceptions.jsp
* Date				: 11-June-2008
* Author(s)			: A-3434,A-3447


 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO" %>



		
	
<html:html locale="true">
	<head>
	
	
		
		<title><bean:message bundle="captureInvoicebundle" key="mra.inwardbilling.airlineexceptions.title.captureinvoice" /></title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/mail/mra/airlinebilling/inward/CaptureInvoice_Script.jsp" />

	</head>
	<body>
	
	
		
		
		<bean:define id="form" name="CaptureMailInvoiceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm"
		toScope="page" />

		<business:sessionBean id="airlineCN51SummaryVO"
				moduleName="mailtracking.mra.airlinebilling"
				screenID="mailtracking.mra.airlinebilling.inward.captureinvoice"
				method="get" attribute="airlineCN51SummaryVO" />

		<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
				  moduleName="mailtracking.mra.airlinebilling"
				  screenID="mailtracking.mra.airlinebilling.inward.captureinvoice"
				  method="get"
				  attribute="weightRoundingVO" />

		<div id="pageDiv" class="iCargoContent ic-masterbg">
			<ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureinvoice.onScreenLoad.do">
				
				<ihtml:hidden property="buttonFlag"/>
				<ihtml:hidden property="invSatusCheckFlag"/>
				<ihtml:hidden property="invForm1SatusCheckFlag"/>
				<ihtml:hidden name="form" property="invokingScreen" />
				<ihtml:hidden name="form" property="ichFlag" />
				<ihtml:hidden name="form" property="noFormOneCaptured" />

				<div class="ic-content-main">
					<div class="ic-head-container">
						<span class="ic-page-title ic-display-none"><common:message key="mra.inwardbilling.captureinvoice.pagetitle" scope="request"/></span>
						<div class="ic-filter-panel">
							<div class="ic-row marginT5">
								<div class="ic-input ic-split-30 ic-mandatory">
									<label><common:message key="mra.inwardbilling.captureinvoice.clearanceperiod" scope="request"/></label>
									<ihtml:text property="clearancePeriod" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEARANCEPERIOD" maxlength="10"/>
									<div class="lovImg">
									<img name="clearancePeriodlov" id="clearancePeriodlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
									</div>
								</div>	
								<div class="ic-input ic-split-30 ic-mandatory">
									<label><common:message key="mra.inwardbilling.captureinvoice.airlinecode" scope="request"/></label>
									<ihtml:text property="airlineCode" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINECODE" maxlength="3"/>
									<div class="lovImg">
									<img name="airlinecodelov" id="airlinecodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
								</div>
								<div class="ic-input ic-split-20 ic-mandatory" id="captureInvoiceParent">
									<label><common:message key="mra.inwardbilling.captureinvoice.airlineno" scope="request"/></label>
									<ihtml:text property="airlineNo" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINENO" maxlength="4"/>
									<div class="lovImg">
									<img name="airlinecodelov" id="airlinecodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
									</div>
								</div>
								<div class="ic-input ic-split-30 ic-mandatory">
									<label><common:message key="mra.inwardbilling.captureinvoice.invoiceno" scope="request"/></label>
									<ihtml:text property="invoiceRefNo" componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVNUMBER" maxlength="18"/>
									<div class="lovImg">
									<img src="<%=request.getContextPath()%>/images/lov.png" id="invnumberlov" height="22" width="22" />
								</div></div>
								<div class="ic-input ic-split-50 ic-mandatory">
									<label><common:message key="mra.inwardbilling.captureinvoice.invoicedate" scope="request"/></label>
									<ibusiness:calendar
										property="invoiceDate"
										componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVDATE"
										type="image"
										id="invoiceDate"
										value="<%=form.getInvoiceDate()%>"/>
								</div>
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LIST" accesskey="L" >
										<common:message key="mra.inwardbilling.captureinvoice.button.list"/>
									</ihtml:nbutton>

									<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEAR" accesskey="C" >
										<common:message key="mra.inwardbilling.captureinvoice.button.clear"/>
									</ihtml:nbutton>
								</div>	
							</div>	
						</div>
					</div>
					<div class="ic-main-container">
						<%String currency;
							boolean readonlyStatus = false;%>
						<div class="ic-row">	
							<div class="ic-section ic-border">
						<div class="ic-row ">
							<div>
								<h4><common:message key="mra.inwardbilling.captureinvoice.billingdetails" scope="request"/></h4>
							</div>
							<div class="ic-row "></div>
							<div class="ic-input ic-split-25 ic-mandatory">
								<label><common:message key="mra.inwardbilling.captureinvoice.listingcurrency" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="listingCurrency">
									<bean:define id="listingCurrency" name="airlineCN51SummaryVO" property="listingCurrency"  type="String" />
										<%currency=form.getListingCurrency();%>
									<ihtml:text  property="listingCurrency"  componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTINGCURRENCY" value=  "<%=listingCurrency%>" maxlength="3">
									</ihtml:text>
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="listingCurrency">
									<%currency=form.getListingCurrency();%>
									<ihtml:text property="listingCurrency" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTINGCURRENCY" value=""  maxlength="3" >
									</ihtml:text>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message key="mra.inwardbilling.captureinvoice.netamount" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="netAmount" >
									<%currency=form.getListingCurrency();%>
									<ibusiness:moneyEntry  formatMoney="true"    componentID="CMP_MRA_AIRLINEBILLING_INWARD_NETAMOUNT" id="netAmount" name="airlineCN51SummaryVO" moneyproperty="netAmount" property="netAmount" onmoneychange="sumOfFields"  maxlength="14" readonly="<%=readonlyStatus%>"/>
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="netAmount" >
									<%currency=form.getListingCurrency();%>
									<ibusiness:moneyEntry  formatMoney="true"  currencyCode="<%=currency%>" value="0.0"   componentID="CMP_MRA_AIRLINEBILLING_INWARD_NETAMOUNT" id="netAmount" name="KEY_INTERLINEINVOICEVO"  moneyproperty="netAmount" property="netAmount" onmoneychange="sumOfFields"  maxlength="14" readonly="<%=readonlyStatus%>"/>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message key="mra.inwardbilling.captureinvoice.exchangerate" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="exgRate">
									<bean:define id="exgRate" name="airlineCN51SummaryVO" property="exgRate" />
									<ihtml:text property="exgRate" value="<%=String.valueOf(exgRate)%>"  componentID="CMP_MRA_AIRLINEBILLING_INWARD_EXCHANGERATE" maxlength="16" />
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="exgRate">
									<ihtml:text property="exgRate"  value=""  componentID="CMP_MRA_AIRLINEBILLING_INWARD_EXCHANGERATE" />
								</logic:notPresent>
							</div>	
							<div class="ic-input ic-split-25 " id="captureInvoiceParent2">
								<label><common:message key="mra.inwardbilling.captureinvoice.amountinusd" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="amountInusd" >
									<%currency=form.getListingCurrency();%>
									<ibusiness:moneyEntry  formatMoney="true"    componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNTINUSD" id="amountInusd" name="airlineCN51SummaryVO" moneyproperty="amountInusd" property="amountInusd" onmoneychange="sumOfFields"  maxlength="16" readonly="<%=readonlyStatus%>"/>
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="amountInusd" >
									<%currency=form.getListingCurrency();%>
									<ibusiness:moneyEntry  formatMoney="true"  currencyCode="<%=currency%>" value="0.0"   componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNTINUSD" id="exchangeRate" name="KEY_INTERLINEINVOICEVO"  moneyproperty="amountInusd" property="amountInusd" onmoneychange="sumOfFields"  maxlength="16" readonly="<%=readonlyStatus%>"/>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message key="mra.inwardbilling.captureinvoice.invoicereceiptdate" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="invRcvdate">
									<bean:define id="invRcvdate" name="airlineCN51SummaryVO" property="invRcvdate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
										<%String invRcvdateStr=invRcvdate.toDisplayDateOnlyFormat();%>
											<ibusiness:calendar id="invoiceReceiptDate"
											 property="invoiceReceiptDate"
									componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVRECEIPTDATE"
									type="image"
									value="<%=invRcvdateStr%>"
									maxlength="11"/>
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="invRcvdate">
									<ibusiness:calendar id="invoiceReceiptDate"
									 property="invoiceReceiptDate"
									componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVRECEIPTDATE"
									type="image"
									maxlength="11"
									readonly="false"/>
				              	</logic:notPresent>	
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message key="mra.inwardbilling.captureinvoice.totalweight" scope="request"/></label>
								<logic:present name="airlineCN51SummaryVO" property="totWt">
									<bean:define id="totWt" name="airlineCN51SummaryVO" property="totWt" toScope="page"/>
									<logic:present name="KEY_WEIGHTROUNDINGVO">
										<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
										<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
										<ibusiness:unitdef id="totalWeight" unitTxtName="totalWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_TOTALWEIGHT" label=""  unitReq = "false" dataName="sampleStdWt"
										 title="Stated Weight"
										unitValue="<%=totWt.toString()%>" style="text-align:right"
										indexId="index" styleId="weight" />

									</logic:present>
								</logic:present>
								<logic:notPresent name="airlineCN51SummaryVO" property="totWt">
									<logic:present name="KEY_WEIGHTROUNDINGVO">
										<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
										<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
										<ibusiness:unitdef id="totalWeight" unitTxtName="totalWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_TOTALWEIGHT" label=""  unitReq = "false" dataName="sampleStdWt"
										title="Stated Weight"
										unitValue="" style="text-align:right"
										indexId="index" styleId="weight"  />
									</logic:present>
								</logic:notPresent>
							</div>	
						</div>
						</div>
						</div>
					</div>
					<div class="ic-foot-container paddR5">
					<div class="ic-row">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnRejectInvoice" componentID="CMP_MRA_AIRLINEBILLING_INWARD_REJECTINVOICE" accesskey="J" >
								<common:message key="mra.inwardbilling.captureinvoice.button.rejectinvoice" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnDelete" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DELETE" accesskey="E" >
								<common:message key="mra.inwardbilling.captureinvoice.button.delete" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnSave" componentID="CMP_MRA_AIRLINEBILLING_INWARD_SAVE" accesskey="S" >
								<common:message key="mra.inwardbilling.captureinvoice.button.save" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLOSE" accesskey="O" >
								<common:message key="mra.inwardbilling.captureinvoice.button.close" />
							</ihtml:nbutton>
						</div>		
					</div>
				</div>		
				</div>		
			</ihtml:form>
		</div>		
				
		

	</body>
</html:html>
