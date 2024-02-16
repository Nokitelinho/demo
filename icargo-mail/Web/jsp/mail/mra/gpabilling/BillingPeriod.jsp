
<%--
* Project	 		: iCargo
* Module Code & Name		: MRA Defaults
* File Name			: BillingPeriod.jsp
* Date				: 
* Author(s)			: a-3108
--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>




<ihtml:form action="/mailtracking.mra.gpabilling.generateinvoice.findBillingPeriod.do">
 <business:sessionBean
		id="KEY_BILLINGPERIODS"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.generateinvoice"
		method="get"
        attribute="generateInvoiceVOs" />


<div id="_ajBillingPeriods" > 
	<div class="ic-row">
		<%String fromDate="";%>
	<%String toDate="";%>
	<logic:present name="KEY_BILLINGPERIODS" >
	
		<logic:iterate id="generateInvoiceVO" name="KEY_BILLINGPERIODS" indexId="index"> 
		<bean:define name="generateInvoiceVO" id="billingFromDate" property="billingPeriodFrom"/>
		<bean:define name="generateInvoiceVO" id="billingToDate" property="billingPeriodTo"/>
		<% fromDate = TimeConvertor.toStringFormat(((LocalDate)billingFromDate).toCalendar(),"dd-MMM-yyyy").toUpperCase(); %>
		<% toDate = TimeConvertor.toStringFormat(((LocalDate)billingToDate).toCalendar(),"dd-MMM-yyyy").toUpperCase(); %>		
		
		<div blgPeriodFrom='<%=String.valueOf(fromDate)%>' blgPeriodTo='<%=String.valueOf(toDate)%>'></div>
 
		 </logic:iterate>
	 </logic:present>

	</div>
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>












