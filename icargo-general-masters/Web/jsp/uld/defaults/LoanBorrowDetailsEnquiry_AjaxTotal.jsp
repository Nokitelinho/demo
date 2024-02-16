<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  LoanBorrowDetailsEnquiry_AjaxTotal.jsp
* Date					:  24-Jul-2008
* Author(s)				:  Sowmya K
*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import = "java.math.BigDecimal" %>
<%@ page import = "java.math.RoundingMode" %>

<ihtml:form action="/uld.defaults.transaction.screenloadloanborrowdetailsenquiry.do">
<business:sessionBean id="totDemmurage"
	moduleName="uld.defaults"
	screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get" attribute="totalDemmurage" />
		
<business:sessionBean id="baseCurrency"
	moduleName="uld.defaults"
	screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get" attribute="baseCurrency" />

	<div id="totalDemmurageAjaxdiv">
	  <logic:present name="totDemmurage">
		<bean:define id="totalDemmurage" name="totDemmurage" toScope="page"/>
		<ihtml:text componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TOTDEMMURAGE" name="totDemmurage" property="totalDemmurage" value="<%=((new BigDecimal(totalDemmurage.toString())).setScale(2,RoundingMode.UP)).toString() %>" readonly="true"/>
	  </logic:present>
	  <logic:present name="baseCurrency">
		<bean:define id="baseCurrency" name="baseCurrency" toScope="page"/>
		<ihtml:text componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_BASECURRENCY" name="baseCurrency" property="baseCurrency" value="<%=(String)baseCurrency%>" readonly="true"/>
	  </logic:present>
	</div>

</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

