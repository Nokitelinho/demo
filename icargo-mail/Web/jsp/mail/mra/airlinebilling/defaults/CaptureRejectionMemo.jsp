<%--
* Project	 		: iCargo
* Module Code & Name		: mailtracking.mra.airlinebilling.defaults
* File Name			: CaptureRejectionMemo.jsp
* Date				: 16-Feb-2007
* Author(s)			: A-2122
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm" %>
<%@ page import="java.util.Formatter" %>

<%
   String FORMAT_STRING = "%1$-16.2f";
%>


<html:html locale="true">

<head>
		

<title>

<bean:message bundle="capturerejectionmemoresources" key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.title.capturerejectionmemo" />

</title>

<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/CaptureRejectionMemo_Script.jsp" />
 <%@ include file="/jsp/includes/customcss.jsp" %> 
</head>

<body style="overflow:auto;">



<!--CONTENT STARTS-->


<business:sessionBean id="KEY_BILLINGTYPE"
    	     moduleName="mailtracking.mra.airlinebilling"
    	     screenID="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo"
    	     method="get"
	     attribute="interlineBillingType"/>


<business:sessionBean id="KEY_MEMOININVOICEVOS"
  	     moduleName="mailtracking.mra.airlinebilling"
  	     screenID="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo"
  	     method="get"
	     attribute="memoInInvoiceVOs"/>



 <bean:define id="form" name="CaptureRejectionMemoForm"
    	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm"
	toScope="page" />

 <div class="iCargoContent ic-masterbg" id="contentdiv" style="overflow:auto;width:100%;height:100%">

   <ihtml:form action="/mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.screenload.do">

   <ihtml:hidden name="form" property="linkStatusFlag" />

	    <div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.pagetitle.capturerejectionmemo"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-20 ic-label-30">
								<label>
									<common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.airlineCode"/>
								</label>
								<ihtml:text property="airlineCode" name="CaptureRejectionMemoForm" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLINGDEFAULTS_CAPTUREREJECTIONMEMO_AIRLINECODE"  maxlength="3" />
					 			<div class= "lovImg"><img id="airlinelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</div>
							<div class="ic-input ic-split-30 ic-label-30">
								<label>
									<common:message   key="mailtracking.mra.airlinebilling.defaults.capturecn51.lbl.billingType"/>
								</label>
								<ihtml:select property="interlineBillingType" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLINGDEFAULTS_CAPTUREREJECTIONMEMO_BILLINGTYPE" >
								  <logic:present name="KEY_BILLINGTYPE">
									<logic:iterate id="interlineBillingType" name="KEY_BILLINGTYPE">
									 <bean:define id="interlineBillingTypeValue" name="interlineBillingType" property="fieldValue" />
									 <logic:notEqual name="interlineBillingType" property="fieldValue"  value="O">
									 <html:option value="<%=(String)interlineBillingTypeValue %>">
									   <bean:write name="interlineBillingType" property="fieldDescription" />
									 </html:option>
									  </logic:notEqual>
									</logic:iterate>
								  </logic:present>
								</ihtml:select>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLINGDEFAULTS_CAPTUREREJECTIONMEMO_LIST" accesskey="L">
								  <common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.btn.lbl.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLINGDEFAULTS_CAPTUREREJECTIONMEMO_CLEAR" accesskey="C">
								  <common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.btn.lbl.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row ic-label-60">
					<h4>Capture Rejection Memo Details
					</h4>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer" style="height:680px">
						<table class="fixed-header-table" style="height:35px;">
							<thead>
								<tr class="iCargoTableHeadingLeft">
									<td width="4%"><input name="checkboxRow" type="checkbox" value=""/></td>
									<td width="12%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.rejectionMemoNo"/></td>
									<td width="11%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.invNo"/></td>
									<td width="17%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.dsn"/></td>
									<td width="8%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.clearancePeriod"/></td>
									<td width="8%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.rejectionDate"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.provisionalAmount"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.reportedAmount"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.rejectedAmount"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.remarks"/></td>
								</tr>
							</thead>
							<tbody id="targetWeightTableBody">
								<% int rowNum =0;%>
								<logic:present name="KEY_MEMOININVOICEVOS" >
								<logic:iterate id="memoInInvoiceVO" name="KEY_MEMOININVOICEVOS"
										type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO" indexId="index">
								<ihtml:hidden property="operationalFlag" name="memoInInvoiceVO" />
								<common:rowColorTag index="index">
								<logic:notEqual name="memoInInvoiceVO" property="operationalFlag" value="D">
								<%System.out.println("opflag-->not D");%>

									<tr class="iCargoTableDataRow1">
										 <%boolean updateStatus=false;%>
										 <%boolean readonlyStatus=true;%>
										 <logic:equal name="memoInInvoiceVO" property="operationalFlag" value="I">
										 <%readonlyStatus=false;%>
										</logic:equal>

										 <logic:equal name="form" property="interlineBillingType" value="O">
										 <%updateStatus=true;%>
										</logic:equal>
											 <td width="4%" style="text-align:center">

												 <ihtml:checkbox property="select" value="<%=index.toString()%>" />
											 </td>
											 <td width="12%">
												<center>
												<logic:present name="memoInInvoiceVO" property="memoCode">
												   <bean:write name="memoInInvoiceVO" property="memoCode"/>
												</logic:present>
												</center>
											 </td>
											<td width="12%">
											<center>
												<logic:present name="memoInInvoiceVO" property="invoiceNumber">
												<bean:write name="memoInInvoiceVO" property="invoiceNumber"/>
												</logic:present>
											</center>
											</td>
											<td width="12%">
											<center>
												<logic:present name="memoInInvoiceVO" property="dsn">
													<bean:write name="memoInInvoiceVO" property="dsn"/>
												</logic:present>
											</center>
											</td>
											<td width="10%">
											<center>
												<logic:present name="memoInInvoiceVO" property="clearancePeriod">
													<bean:write name="memoInInvoiceVO" property="clearancePeriod"/>
												</logic:present>
											</center>
											</td>
											<td width="10%">
											<center>
												<logic:present name="memoInInvoiceVO" property="memoDate">
													<%
														String memoDate ="";
														if(memoInInvoiceVO.getMemoDate() != null) {
														memoDate = memoInInvoiceVO.getMemoDate().toDisplayDateOnlyFormat();
														}
													%>
													 <%= memoDate %>
												</logic:present>
											 </center>
											 </td>
											 <td width="10%" style="text-align:right">

												<logic:present name="memoInInvoiceVO" property="provisionalAmount">
													<bean:write name="memoInInvoiceVO" property="provisionalAmount" localeformat="true"/>
												</logic:present>

											 </td>
											 <td width="10%" style="text-align:right">

												<logic:present name="memoInInvoiceVO" property="reportedAmount">
												   <bean:write name="memoInInvoiceVO" property="reportedAmount" localeformat="true"/>
												</logic:present>

											</td>
											 <td width="10%" style="text-align:right">
												<logic:present name="memoInInvoiceVO" property="differenceAmount">
													<bean:write name="memoInInvoiceVO" property="differenceAmount" localeformat="true"/>
												</logic:present>
											</td>
											<logic:present name="memoInInvoiceVO" property="previousDifferenceAmount">
												<bean:define id="prevAmt" name="memoInInvoiceVO" property="previousDifferenceAmount"/>
												<ihtml:hidden property="previousDifferenceAmount" value="<%=prevAmt.toString()%>" />
											</logic:present>
											<logic:notPresent name="memoInInvoiceVO" property="previousDifferenceAmount">
												<ihtml:hidden property="previousDifferenceAmount"  value="" />
											</logic:notPresent>
											 <td width="10%">
											 <center>
												<logic:present name="memoInInvoiceVO" property="remarks">
													<bean:write name="memoInInvoiceVO" property="remarks"/>
												 </logic:present>
											 </center>
											</td>
									</tr>
								 </logic:notEqual>
								<% rowNum++; %>
								</common:rowColorTag>
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
						<ihtml:nbutton property="btClose" componentID="CMP_MAILTRACKING_MRA_AIRLINEBILLINGDEFAULTS_CAPTUREREJECTIONMEMO_CLOSE" accesskey="O">
						  <common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.btn.lbl.close" />
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
