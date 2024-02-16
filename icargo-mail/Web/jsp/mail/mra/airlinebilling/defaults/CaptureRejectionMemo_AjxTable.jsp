<%--
* Project	 		: iCargo
* Module Code & Name		: mailtracking.mra.airlinebilling.defaults
* File Name			: CaptureRejectionMemo_AjxTable.jsp
* Date				: 16-Feb-2007
* Author(s)			: A-2122
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm" %>
<%@ page import="java.util.Formatter" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<% 
   String FORMAT_STRING = "%1$-16.2f";  
%>



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
	

<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.screenload.do">

<ihtml:hidden name="form" property="linkStatusFlag" />



				<div class="ic-row">
					<div id="div1" class="tableContainer" style="height:750px">	
						<table class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingLeft">
									<td width="4%"><input name="checkboxRow" type="checkbox" value=""/></td>
									<td width="12%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.rejectionMemoNo"/></td>
									<td width="12%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.invNo"/></td>
									<td width="12%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.dsn"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.clearancePeriod"/></td>
									<td width="10%"><common:message   key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.lbl.rejectionDate"/></td>							   									
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
								

									<tr class="iCargoTableDataRow1">
										 <%boolean updateStatus=false;%>
										 <%boolean readonlyStatus=true;%>
										 <logic:equal name="memoInInvoiceVO" property="operationalFlag" value="I">
										 <%readonlyStatus=false;%>
										</logic:equal>

										 <logic:equal name="form" property="interlineBillingType" value="O">
										 <%updateStatus=true;%>
										</logic:equal>
											 <td width="4%">
												 
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
	
</ihtml:form>

							   