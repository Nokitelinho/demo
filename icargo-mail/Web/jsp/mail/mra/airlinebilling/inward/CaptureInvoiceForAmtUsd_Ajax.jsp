 
 <%--
 
 * Project	 		: iCargo
 * Module Code & Name:		: mra-airlinebilling
 * File Name			: CaptureInvoice.jsp
 * Date				: 11-June-2008
 * Author(s)			: A-3447
  
  
  --%>
 
 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm" %>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
 <%@ page import="java.util.HashMap"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO" %>
 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 
 <bean:define id="form" name="CaptureMailInvoiceForm"
 		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm"
 		toScope="page" />
 
 
 
 <ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureinvoice.onScreenLoad.do">
 
  
  <div id = "Childdiv2" >
  <%String currency;
			boolean readonlyStatus = false;%>
 	
									<logic:present name="airlineCN51SummaryVO" property="amountInusd" >
												<%currency=form.getListingCurrency();%>
										<ibusiness:moneyEntry  formatMoney="true"    componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNTINUSD" id="amountInusd" name="airlineCN51SummaryVO" moneyproperty="amountInusd" property="amountInusd" onmoneychange="sumOfFields"  maxlength="16" readonly="<%=readonlyStatus%>"/>
									</logic:present>
									<logic:notPresent name="airlineCN51SummaryVO" property="amountInusd" >
									
									<%currency=form.getListingCurrency().toUpperCase();%>
									<%System.out.println("11-----1"+form.getListingCurrency());%>
									
									<ibusiness:moneyEntry  formatMoney="true"  currencyCode="<%=currency%>" value="<%=form.getAmountInusd()%>"   componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNTINUSD" id="exchangeRate" name="KEY_INTERLINEINVOICEVO"  moneyproperty="amountInusd" property="amountInusd" onmoneychange="sumOfFields"  maxlength="16" readonly="<%=readonlyStatus%>"/>
 	
 						</logic:notPresent>
 	</div>		
 		   	
 	    	
   </ihtml:form >
   	
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
 
 
 
 
 