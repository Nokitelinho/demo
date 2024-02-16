<%--/***********************************************************************
* Project	     	 	  : iCargo
* Module Code & Name 	 : MRA
* File Name          	 : AvailableSettlement.jsp
* Date                 	 : 30-Mar-2012
* Author(s)              : A-4823
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm" %>
<bean:define id="form"
		 name="InvoiceSettlementForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm"
		 toScope="page" />
<html:html>
<head>
<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpabilling.invoicesettlement.availablesettlement" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/gpabilling/AvailableSettlementPopUp_Scipt.jsp" />
</head>
<body>
<business:sessionBean id="GPA_SETTLEMENT_VOS"
	  	moduleName="mailtracking.mra.gpabilling"
	  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="GPASettlementVOs" />
<div class="iCargoPopUpContent"style="overflow:auto;height:530px;">
  <ihtml:form action="/mailtracking.mra.gpabilling.invoicesettlement.history.do" styleClass="ic-main-form">
  <ihtml:hidden property="select"/>
  <ihtml:hidden property="availableSettlement"/>
  <ihtml:hidden property="frmPopUp"/>
  <ihtml:hidden property = "lastPageNum" />
<ihtml:hidden property = "displayPage" />
  <ihtml:hidden property = "createFlag" />
  <div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.mra.gpabilling.invoicesettlement.availablesettlement" />
			</span>
		</div>
		<div class="ic-main-container">
		<div class="ic-row">
		<div id="div1" class="tableContainer" style="height:300px" >
				<table class="fixed-header-table">
				<thead>
				<tr>
				<td class="iCargoTableHeader" width="2%"><input type="checkbox" name="masterCheckbox"  value="checkbox" onclick="updateHeaderCheckBox(targetFormName,targetFormName.masterCheckbox,targetFormName.check)" /></td>
				<td class="iCargoTableHeader" width="8%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlementid"/></td>			 					                        
				<td class="iCargoTableHeader" width="8%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlementdate"/></td>
				<td class="iCargoTableHeader" width="8%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeno"/></td>
				<td class="iCargoTableHeader" width="8%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequedate"/></td>
				<td class="iCargoTableHeader" width="8%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeamount"/></td>
				<td class="iCargoTableHeader" width="7%"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.currency"/></td>
				</tr>
				</thead>
				<tbody>
				<% int index=0;  %>
				<logic:present name="GPA_SETTLEMENT_VOS">
				<logic:iterate id="iterator" name="GPA_SETTLEMENT_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO" indexId="rowCount">
				
				<logic:present name="iterator" property="settlementDetailsVOs">
				<bean:define id="settlementDetailsVOs" name="iterator" property="settlementDetailsVOs" />
				<logic:iterate id ="settlementDetailsVO" name="settlementDetailsVOs" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO" indexId="rowCount">
				<tr>
				<td >
				<div>
				<input type="checkbox"  name="check"  onclick="toggleTableHeaderCheckbox('check',this.form.masterCheckbox)" />	
				</div>
				</td>
				<td >
				<div>
				<logic:present	name="settlementDetailsVO" property="settlementId">
				<bean:write name="settlementDetailsVO" property="settlementId"/>
				<ihtml:hidden property="settlementId" value="<%=settlementDetailsVO.getSettlementId()%>"/>
				</logic:present>
				<logic:notPresent	name="settlementDetailsVO" property="settlementId">
				<ihtml:hidden property="settlementId" value=""/>
				</logic:notPresent>
				</div>
				</td>
				<td >
				<div>
				<logic:present	name="settlementDetailsVO" property="settlementDate">
				<bean:define id="settlementDate" property="settlementDate" name="settlementDetailsVO" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
				<%=settlementDate.toDisplayDateOnlyFormat()%>
				</logic:present>
				<logic:notPresent	name="settlementDetailsVO" property="settlementDate">
				<ihtml:hidden property="settlementDate" value=""/>
				</logic:notPresent>
				</div>
				</td>
				
				<!--cheque details-->
				<td>
				<logic:present	name="settlementDetailsVO" property="chequeNumber">
				<bean:write name="settlementDetailsVO" property="chequeNumber"/>
				<ihtml:hidden property="chequeNumber" value="<%=settlementDetailsVO.getChequeNumber()%>"/>
				</logic:present>
				<logic:notPresent	name="settlementDetailsVO" property="chequeNumber">
				<ihtml:hidden property="chequeNumber" value=""/>
				</logic:notPresent>
				</td>
				<td>
				<div>
				<logic:present	name="settlementDetailsVO" property="chequeDate">
				<bean:define id="chequeDate" property="chequeDate" name="settlementDetailsVO" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
				<%=chequeDate.toDisplayDateOnlyFormat()%>
				</logic:present>
				<logic:notPresent	name="settlementDetailsVO" property="chequeDate">
				<ihtml:hidden property="chequeDate" value=""/>
				</logic:notPresent>
				</div>
				</td>
				<td>
				<div>
				<logic:present	name="settlementDetailsVO" property="chequeAmount">
				<ibusiness:moneyDisplay showCurrencySymbol="false" name="settlementDetailsVO" moneyproperty="chequeAmount"  property="chequeAmount" />
				</logic:present>
				</div>
				</td>
				<td>
				<div>
				<logic:present	name="settlementDetailsVO" property="chequeCurrency">
				<bean:write name="settlementDetailsVO" property="chequeCurrency"/>
				<ihtml:hidden property="chequeCurrency" value="<%=settlementDetailsVO.getChequeCurrency()%>"/>
				</logic:present>
				<logic:notPresent	name="settlementDetailsVO" property="chequeCurrency">
				<ihtml:hidden property="chequeCurrency" value=""/>
				</logic:notPresent>
				</div>
				</td>
				<!--cheque details-->
				</tr>
				</logic:iterate>
				</logic:present>
				
				</logic:iterate>
				</logic:present>		
				</tbody>
				</table>
				</div>
		</div>
		</div>
		<div class="ic-foot-container">
		<div class="ic-button-container">
			<ihtml:nbutton property="btOK" componentID="CMP_MRA_GPABILLING_SETTLEAVAILPOPUP_BTNOK" >
					<common:message key="mailtracking.mra.gpabilling.invoicesettlement.ok" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btCreate" componentID="CMP_MRA_GPABILLING_SETTLEAVAILPOPUP_BTNCREATE" >
					<common:message key="mailtracking.mra.gpabilling.invoicesettlement.create" />					
					</ihtml:nbutton>
					<ihtml:nbutton property="btCancel" componentID="CMP_MRA_GPABILLING_SETTLEAVAILPOPUP_BTNCLOSE" >
					<common:message key="mailtracking.mra.gpabilling.invoicesettlement.close" />
					</ihtml:nbutton>
		</div>
		</div>
</div>
</ihtml:form>
</div>
	</body>
</html:html>




										
		
		
