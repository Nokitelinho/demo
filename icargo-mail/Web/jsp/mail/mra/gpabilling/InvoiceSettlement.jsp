<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : InvoiceSettlement.jsp
* Date                 	 : 23-Mar-2007
* Author(s)              : A-2408,A-4823
*********************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="java.util.Formatter" %>
<%
	   String FORMAT_STRING = "%1$-16.2f";
	%>
<bean:define id="form"
		 name="InvoiceSettlementForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm"
		 toScope="page" />


			
	
<html:html>
<head>

	

<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpabilling.invoicesettlement.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/gpabilling/InvoiceSettlement_Script.jsp" />
</head>

<body>
	
	
	
	
<%@ include file="/jsp/includes/reports/printFrame.jsp"%>
<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="oneTimeVOs" />
<business:sessionBean id="INV_SETTLEMENT_VOS"
	  	moduleName="mailtracking.mra.gpabilling"
	  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="invoiceSettlementVOs" />
<business:sessionBean id="LIST_INV_COLLECTION" moduleName="mailtracking.mra.gpabilling" screenID="mailtracking.mra.gpabilling.invoicesettlement" method="get" attribute="invoiceSettlementDetailVOs" />
	<business:sessionBean id="GPA_SETTLEMENT_VOS"
	  	moduleName="mailtracking.mra.gpabilling"
	  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="GPASettlementVO" />
	<logic:present name="GPA_SETTLEMENT_VOS">
        <bean:define id="GPA_SETTLEMENT_VOS" name="GPA_SETTLEMENT_VOS" toScope="page" />
	</logic:present>
	<business:sessionBean id="INV_SETTLEMENT_HISVOS"
	 moduleName="mailtracking.mra.gpabilling"
	 screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="invoiceSettlementHistoryVOs" />
	<business:sessionBean id="INV_SETTLEMENT_FILTERVO"
	 moduleName="mailtracking.mra.gpabilling"
	 screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="InvoiceSettlementFilterVO" />
	<logic:present name="INV_SETTLEMENT_FILTERVO">
        <bean:define id="INV_SETTLEMENT_FILTERVO" name="INV_SETTLEMENT_FILTERVO" toScope="page" />
	</logic:present>
	<logic:present name="INV_SETTLEMENT_HISVOS">
        <bean:define id="INV_SETTLEMENT_HISVOS" name="INV_SETTLEMENT_HISVOS" toScope="page" />
	</logic:present>
  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="pageDiv" >

  <ihtml:form action="/mailtracking.mra.gpabilling.invoicesettlement.onScreenLoad.do">
  
 <ihtml:hidden property="screenStatus"/>
 <ihtml:hidden property="availableSettlement"/>
 <ihtml:hidden property = "lastPageNum" />
 <ihtml:hidden property = "frmPopUp" />
<ihtml:hidden property = "displayPage" />
 <ihtml:hidden property = "deleteArray" />
 <ihtml:hidden property = "listInvoiceFlag" />
  <ihtml:hidden property = "listAccountingFlag" />
  <ihtml:hidden property = "selectedInvoice" />
  <ihtml:hidden property = "createFlag" />
	<ihtml:hidden property="opFlag" value="<%=form.getOpFlag()%>" />
	<%--Added by A-4810 for CR ICRD-13639 starts --%>
	<%boolean indigoflg = false;%>
	<common:xgroup>
		<common:xsubgroup id="INDIGO_SPECIFIC">
		 <% indigoflg = true;%>
		</common:xsubgroup>
	</common:xgroup >
	<div class="ic-content-main">
<span class="ic-page-title"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.pagetitle" /></span>
<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div id="divChild">
					<div class="ic-border">
						<div class="ic-col-35">
							<fieldset class="ic-field-set">
								<legend><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpadetails"/></legend>
									<div class="ic-row ">
										<div class="ic-input ic-mandatory ic-split-50 ic-label-30 ">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpacode"/></label>
										<logic:present name="INV_SETTLEMENT_FILTERVO" property="gpaCode">
										<bean:define id="gpaCode" name="INV_SETTLEMENT_FILTERVO" property="gpaCode" toScope="page"/>
										<ihtml:text property="gpaCodeFilter" value="<%=String.valueOf(gpaCode)%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPACODE" maxlength="5"/>
										<div class="lovImg">
										 <img src="<%=request.getContextPath()%>/images/lov.png" id="gpacodelov" height="22" width="22" alt="" disabled="true"/>
										 </div>
										 </logic:present>
										 <logic:notPresent name="INV_SETTLEMENT_FILTERVO" property="gpaCode">
										<ihtml:text property="gpaCodeFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPACODE" maxlength="5"/>
										<div class="lovImg">	   
										  <img src="<%=request.getContextPath()%>/images/lov.png" id="gpacodelov" height="22" width="22" alt=""/>
										 </div>
										 </logic:notPresent>
										 </div>
												
										<div class="ic-input  ic-split-50 ic-label-30 ">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpaname"/></label>	
										
										<logic:present name="INV_SETTLEMENT_FILTERVO" property="gpaName">
										<bean:define id="gpaName" name="INV_SETTLEMENT_FILTERVO" property="gpaName" toScope="page"/>
										<ihtml:text property="gpaNameFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPANAME" value="<%=String.valueOf(gpaName)%>" maxlength="10"/>
										 </logic:present>
										 <logic:notPresent name="INV_SETTLEMENT_FILTERVO" property="gpaName">
										<ihtml:text property="gpaNameFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPANAME" maxlength="10"/>
										 </logic:notPresent>
										 </div>
									</div>
							</fieldset>
						</div>
				
						<div class="ic-col-35">
								<fieldset class="ic-field-set" >
									<legend ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicedetails"/></legend>
									<div class="ic-row ">
										<div class="ic-input ic-split-50 ic-label-40">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invrefno"/></label>
											
											<logic:present name="INV_SETTLEMENT_FILTERVO" property="invoiceNumber">
											<bean:define id="invoiceNumber" name="INV_SETTLEMENT_FILTERVO" property="invoiceNumber" toScope="page"/>
											<ihtml:text property="invRefNumberFilter" value="<%=String.valueOf(invoiceNumber)%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_INVNUM" maxlength="18" />
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="invnumberlov" height="22" width="22" alt="" disabled="true"/>
											</div> 
											</logic:present>
											<logic:notPresent name="INV_SETTLEMENT_FILTERVO" property="invoiceNumber">
											<ihtml:text property="invRefNumberFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_INVNUM" maxlength="18" />
											<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" id="invnumberlov" height="22" width="22" alt="" />
											</div>
											</logic:notPresent>
										</div>
										<div class="ic-input ic-split-50 ic-label-40">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicesatus"/></label>
								 
											<ihtml:select componentID="CMP_MRA_GPABILLING_SETTLEINV_SETTLESTATUS" property="invoiceStatusFilter" styleId="invoiceStatusFilter">
												<ihtml:option value=""></ihtml:option>
												<logic:present name="KEY_ONETIMES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="mailtracking.mra.invoicestatus">
													<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue" property="fieldValue">

														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>

													</logic:present>
													</logic:iterate>
													</logic:equal>
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</div>
									</div>
						</fieldset>
					</div>
							<div class="ic-col-30">
								<fieldset class="ic-field-set" >
									<legend ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.billingperiod"/></legend>
										<div class="ic-row ">
											<div class="ic-input ic-split-50 ic-label-35">
												<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.fromdate"/></label>
										   
												<logic:notPresent name="INV_SETTLEMENT_FILTERVO" property="fromDate">
												<ibusiness:calendar property="fromDate" id="fromDate" type="image" value="" componentID="CMP_MRA_GPABILLING_SETTLEINV_FROMDATE" />
												</logic:notPresent>
												<logic:present name="INV_SETTLEMENT_FILTERVO" property="fromDate">
													<bean:define id="fromDate" name="INV_SETTLEMENT_FILTERVO" property="fromDate" toScope="page"/>
													<%String fromDat=TimeConvertor.toStringFormat(((LocalDate)fromDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
													<ibusiness:calendar property="fromDate" id="fromDate" type="image" value="<%=(String)fromDat%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_FROMDATE"  />
												</logic:present>
											</div>
											<div class="ic-input ic-split-50 ic-label-25">
												<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.todate"/></label>
													
														<logic:notPresent name="INV_SETTLEMENT_FILTERVO" property="toDate">
												<ibusiness:calendar property="toDate" id="toDate" type="image" value="" componentID="CMP_MRA_GPABILLING_SETTLEINV_TODATE" />
												</logic:notPresent>
												<logic:present name="INV_SETTLEMENT_FILTERVO" property="toDate">
													<bean:define id="toDate" name="INV_SETTLEMENT_FILTERVO" property="toDate" toScope="page"/>
													<%String toDat=TimeConvertor.toStringFormat(((LocalDate)toDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
													<ibusiness:calendar property="toDate" id="toDate" type="image" value="<%=(String)toDat%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_TODATE" />
												</logic:present>
											</div>
										</div>		
									</fieldset>
								</div>
								<div class="ic-row ">
									<div class="ic-input ic-split-30 ic-label-17">
										<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeno"/></label>
										<ihtml:text property="chequeNumberFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_CHEQUENOFILTER" maxlength="15" value=""/>
									
									</div>
								
									<div class="ic-button-container">
										<ihtml:nbutton property="btnList" accesskey ="L" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNLIST" >
										<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.list" />
					                        		</ihtml:nbutton>

										<ihtml:nbutton property="btnClear" accesskey ="C" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNCLEAR" >
										<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.clear" />
					                        		</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
						</div>
					 </div>
		  </div>
		  <!--FILTER PANEL ENDS-->
		  <!--SETTLEMENT DETAILS PANEL STARTS-->
		<div class="ic-main-container">
						<div class="ic-row">
							<h4><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlementdetails"/></h4>
						</div>
						<div class="ic-border">
						<div class="ic-row ic-label-45">
							<div class="ic-input  ic-split-35">
							<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlerefno"/></label>
									
								<logic:present name="GPA_SETTLEMENT_VOS" property="settlementId">
									<bean:define id="settlementId" name="GPA_SETTLEMENT_VOS" property="settlementId" />
									<ihtml:text property="settlementReferenceNumber" name="GPA_SETTLEMENT_VOS" componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLREFNO"  disabled="true"/>
								</logic:present>
								<logic:notPresent name="GPA_SETTLEMENT_VOS" property="settlementId">
								<ihtml:text property="settlementReferenceNumber" componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLREFNO" maxlength="12" />
								</logic:notPresent>
								</div>
								<div class="ic-input  ic-split-35">
								<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlecurr"/></label>
														
								<logic:present name="GPA_SETTLEMENT_VOS" property="settlementCurrency">
									<bean:define id="settlementCurrency" name="GPA_SETTLEMENT_VOS" property="settlementCurrency" />
									<ihtml:text property="settleCurrency" name="GPA_SETTLEMENT_VOS" componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLREFNO"  disabled="true"/>
								</logic:present>
								<logic:notPresent name="GPA_SETTLEMENT_VOS" property="settlementCurrency">
								<ihtml:text property="settleCurrency" value="<%=form.getSettleCurrency()%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLCURR" maxlength="4" />
								<div class="lovImg">
								<img id="currencyLOV" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt=""/>
								</div>
								</logic:notPresent>
								</div>
								<div class="ic-input  ic-split-30">
								<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settledate"/></label>
								
								<logic:present name="GPA_SETTLEMENT_VOS" property="settlementDate">
								<bean:define id="settlementDate" name="GPA_SETTLEMENT_VOS" property="settlementDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
								        <ibusiness:calendar
								     	property="settlementDate"
								    	componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLDATE"
										disabled="true"
						         	    type="image"
						         	    id="settlementDate"
						                value="<%=settlementDate.toDisplayDateOnlyFormat()%>"/>
								</logic:present>		
								<logic:notPresent name="GPA_SETTLEMENT_VOS" property="settlementDate">
								<ibusiness:calendar
								     	property="settlementDate"
								    	componentID="CMP_MRA_GPABILLING_SETTLEINV_SETLDATE"
						         	    type="image"
						         	    id="settlementDate"
						                value="<%=form.getSettlementDate()%>"/></td>
								</logic:notPresent>
								</div>
							 </div>
						</div>
		   <!--pagination starts-->
		   <div class="ic-border marginT10">
				<div class="ic-row">
				
			
					<logic:present name="LIST_INV_COLLECTION">
						<common:paginationTag
							pageURL="mailtracking.mra.gpabilling.invoicesettlement.list.do"
							name="LIST_INV_COLLECTION"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>" />
					</logic:present>
					<logic:notPresent name="LIST_INV_COLLECTION">
					 &nbsp;
					</logic:notPresent>
					 <div class="ic-button-container">
						<logic:present name="LIST_INV_COLLECTION">
						<common:paginationTag
							pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="LIST_INV_COLLECTION"
							display="pages"
							linkStyleClass="iCargoResultsLabel"
							disabledLinkStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>" />					 
							</logic:present>
							<logic:notPresent name="LIST_INV_COLLECTION">					
							&nbsp;
						   </logic:notPresent>
					</div>
					
				</div>
		<!--pagination ends-->

					<div class="ic-row ic-pad-3 ic-section ">
						   <div class="tableContainer " id="div1" style="height:140px;">
								<table style="width:100%;" class="fixed-header-table " id="captureAgtSettlementMemo" >
									 <thead>
										 <tr >


											<td class="iCargoTableHeaderLabel" width="2%"> <input type="checkbox" name="allCheck" value="checkbox" /></td>

					                        <td  class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpacode"/></td>

					                        <td class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpaname"/></td>

					                        <td   class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invrefno"/></td>

					                        <td    class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.billingperiod"/></td>

					                        <td  class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.billingcurrency"/></td>


											<td    class="iCargoTableHeader" width="11%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.billedamt"/></td>

											<td  class="iCargoTableHeader" width="10%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.totalsettled"/></td>

					                       	<td    class="iCargoTableHeader" width="10%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.dueamt"/></td>

					                        <td    class="iCargoTableHeader" width="10%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicesatus"/></td>


										</tr>
								   </thead>
                                      
                                      
								   <tbody>

										<logic:present name="GPA_SETTLEMENT_VOS" >
										<logic:iterate id="iterator" name="GPA_SETTLEMENT_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
												<logic:present name="iterator" property="invoiceSettlementVOsPage">
												    <bean:define id="invoiceSettlementVOsPage" name="iterator" property="invoiceSettlementVOsPage" />
														<logic:iterate id ="invoiceSettlementVO" name="invoiceSettlementVOsPage" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO" indexId="rowCount">
										
																 <tr>
																		<td>
																			<input type="checkbox"  name="check" value="<%=String.valueOf(rowCount)%>"/>
																		</td>	
																						  
																		<td width="2%">
																			<logic:present	name="invoiceSettlementVO" property="gpaCode">
																					<bean:write name="invoiceSettlementVO" property="gpaCode"/>
																					
																			</logic:present>
																			<logic:notPresent	name="invoiceSettlementVO" property="gpaCode">
																			<ihtml:hidden property="gpaCode" value=""/>
																			</logic:notPresent>
																		</td>
																		<td >

																		<logic:present	name="invoiceSettlementVO" property="gpaName">
																				<bean:write name="invoiceSettlementVO" property="gpaName"/>
																				<ihtml:hidden property="gpaName" value="<%=invoiceSettlementVO.getGpaName()%>"/>
																		</logic:present>
																				<logic:notPresent	name="invoiceSettlementVO" property="gpaName">
																				<ihtml:hidden property="gpaName" value=""/>
																				</logic:notPresent>
																		</td>
																		<td >
																			<logic:present	name="invoiceSettlementVO" property="invoiceNumber">
																			<bean:write name="invoiceSettlementVO" property="invoiceNumber"/>
																			<ihtml:hidden property="invoiceNumber" value="<%=invoiceSettlementVO.getInvoiceNumber()%>"/>
																			</logic:present>
																											<logic:notPresent	name="invoiceSettlementVO" property="invoiceNumber">
																			<ihtml:hidden property="invoiceNumber" value=""/>
																			</logic:notPresent>
																		</td>
																		<td >

																			<logic:present	name="invoiceSettlementVO" property="billingPeriod">
																			<bean:write name="invoiceSettlementVO" property="billingPeriod"/>

																			</logic:present>
																			<logic:notPresent	name="invoiceSettlementVO" property="billingPeriod">
																			<ihtml:hidden property="billingPeriod" value=""/>
																			</logic:notPresent>
																		</td>
																		<td >

																			<logic:present	name="invoiceSettlementVO" property="billingCurrencyCode">
																			<bean:write name="invoiceSettlementVO" property="billingCurrencyCode"/>
																		<!--	<ihtml:hidden property="settlementCurrency" value="<%=invoiceSettlementVO.getSettlementCurrencyCode()%>"/>-->
																		</logic:present>
																			<logic:notPresent	name="invoiceSettlementVO" property="billingCurrencyCode">



																		<ihtml:hidden property="settlementCurrency" value=""/>
																		</logic:notPresent>
																		</td>
																		<td >
										
																		<logic:present	name="invoiceSettlementVO" property="amountInSettlementCurrency">
																		<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVO" moneyproperty="amountInSettlementCurrency"  property="amountInSettlementCurrency" />

																		
																		</logic:present>
																		<logic:notPresent	name="invoiceSettlementVO" property="amountInSettlementCurrency">
																		<ihtml:hidden property="totalBilledAmountSettlementCurr" value=""/>
																		</logic:notPresent>
																		
																		</td>
																		<td >
																		
																		<logic:present	name="invoiceSettlementVO" property="amountAlreadySettled">
																		<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVO" moneyproperty="amountAlreadySettled"  property="amountAlreadySettled" />
																		
																		</logic:present>
																		<logic:notPresent	name="invoiceSettlementVO" property="amountAlreadySettled">
																		<ihtml:hidden property="amountSettled" value=""/>
																		</logic:notPresent>
																		
																		</td>

																		<%boolean flag=false;%>

																		<td >


																		
																		<logic:present	name="invoiceSettlementVO" property="dueAmount">
																		<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVO" moneyproperty="dueAmount"  property="dueAmount" />
																		
																		</logic:present>
																		<logic:notPresent	name="invoiceSettlementVO" property="dueAmount">
																		<ihtml:hidden property="amountSettled" value=""/>
																		</logic:notPresent>
																		
																		</td>
																		<td >
																		<logic:present	name="invoiceSettlementVO" property="settlementStatus">
																										<bean:define id="settlementStatus" name="invoiceSettlementVO" property="settlementStatus" />
																		<logic:present name="KEY_ONETIMES">
																		<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
																		<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																										<logic:equal name="parameterCode" value="mailtracking.mra.invoicestatus">
																		<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																		<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:present name="parameterValue" property="fieldValue">

																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

																		<%String field=settlementStatus.toString();%>
																		<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
																		<bean:write name="parameterValue" property="fieldDescription" />
																		<ihtml:hidden property="settlementStatus" value="<%=field%>"/>
																		</logic:equal>
																		</logic:present>
																		</logic:iterate>
																		</logic:equal>
																		</logic:iterate>
																		</logic:present>



																		<logic:equal name="settlementStatus" value="S">
																		<%
																		flag=true;
																		%>
																		</logic:equal>
																		</logic:present>

																		</td>






																		</tr>
																		
																		</logic:iterate>
																		</logic:present>
																	</logic:iterate>	
																	</logic:present>
																</tbody>
														</table>
												</div>
	        
								      </div>
							       
								   <div class="ic-row">
									 <div class="ic-button-container">
									   <ihtml:nbutton property="btnViewHistory" accesskey ="H" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNVIEWHISTORY" >
										<common:message key="mailtracking.mra.gpabilling.invoicesettlement.viewhistory" />
										</ihtml:nbutton>
									</div>
							  	</div>
									</div>

		
		
		
				 <div class="ic-row">
	    
						        <fieldset class="ic-field-set">
									<legend><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settledetails"/></legend>
									
										
										<div class="ic-row">
															
										<logic:present name="GPA_SETTLEMENT_VOS">
											<common:paginationTag
												pageURL="mailtracking.mra.gpabilling.invoicesettlement.list.do"
												name="GPA_SETTLEMENT_VOS"
												display="label"
												labelStyleClass="iCargoResultsLabel"
												lastPageNum="<%=form.getLastPageNum() %>" />
										</logic:present>
										<logic:notPresent name="GPA_SETTLEMENT_VOS">
										 &nbsp;
										</logic:notPresent>
										 <div class="ic-button-container">
											<logic:present name="GPA_SETTLEMENT_VOS">
											<common:paginationTag
												pageURL="javascript:submitPage('lastPageNum','displayPage')"
												name="GPA_SETTLEMENT_VOS"
												display="pages"
												linkStyleClass="iCargoResultsLabel"
												disabledLinkStyleClass="iCargoResultsLabel"
												lastPageNum="<%=form.getLastPageNum() %>" />					 
												</logic:present>
												<logic:notPresent name="GPA_SETTLEMENT_VOS">					
												&nbsp;
											   </logic:notPresent>
											   <a id="addLink" name="addLink" class="iCargoLink" href="#" value="addLink">
													<common:message key="mailtracking.mra.gpabilling.invoicesettlement.add"/>
												</a>
												|
												<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="deleteLink">
													<common:message key="mailtracking.mra.gpabilling.invoicesettlement.delete"/>
												</a>
											</div>
										</div>
													
										<div class="ic-row ic-pad-3 ic-section">
												   <div class="tableContainer " id="div2" style="height:100px;">
													  <table class="fixed-header-table " id="captureSettlement" >												     
														 <thead>
															<tr>
															<td  class="iCargoTableHeaderLabel ic-center" width="1%" ><input type="checkbox" name="billChkAll" value="checkbox" /></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeno"/><label class="iCargoMandatoryFieldIcon">*</label></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequedate"/><label class="iCargoMandatoryFieldIcon">*</label></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.bank"/></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.branch"/></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.amount"/><label class="iCargoMandatoryFieldIcon">*</label></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.delete"/></td>
															<td  class="iCargoTableHeaderLabel" width="3%" ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.remarks"/></td>
														</tr>
														</thead>
														<tbody id="settlementBody">
												<logic:present name="GPA_SETTLEMENT_VOS" >
												<logic:iterate id="iterator" name="GPA_SETTLEMENT_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
														<logic:present name="iterator" property="settlementDetailsVOs">
															<bean:define id="settlementDetailsVOs" name="iterator" property="settlementDetailsVOs" />
																<logic:iterate id ="settlementDetailsVO" name="settlementDetailsVOs" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO" indexId="rowCount">
																	<logic:notEqual name="settlementDetailsVO" property="operationFlag" value="D">
																						<ihtml:hidden name="form" property="stlOpFlag" value="N"/>
																	</logic:notEqual>					
																	
																		<tr >
																			<td>																			
																			<input type="checkbox"  name="rowId" value="<%=String.valueOf(rowCount)%>" />
																	
																			</td>
																			<td class="iCargoTableDataTd" >
																								<logic:present  name="settlementDetailsVO" property="chequeNumber">
																									<ihtml:text  property="chequeNumber" indexId="rowCount" value="<%=settlementDetailsVO.getChequeNumber()%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_CHEQUENO"  disabled="true"/>
																								</logic:present>
																								<logic:notPresent  name="settlementDetailsVO" property="chequeNumber">
																									<ihtml:hidden property="chequeNumber" indexId="rowCount" value=""/>
																								</logic:notPresent>
																							</td>
																					<td class="iCargoTableDataTd" >
																								
																									<logic:present name="settlementDetailsVO" property="chequeDate">
																										<bean:define id="chequeDate" name="settlementDetailsVO" property="chequeDate" />
																										<%
																										  String cheqDate = TimeConvertor.toStringFormat(((LocalDate)chequeDate).toCalendar(),"dd-MMM-yyyy");
																										%>
																										<ibusiness:calendar
																											    property="chequeDate"
																											    componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																											    type="image"
																											    id="chequeDate"
																											    value="<%=cheqDate%>" indexId="rowCount" disabled="true"/>
																									</logic:present>
																									<logic:notPresent name="settlementDetailsVO" property="chequeDate">
																										<ibusiness:calendar
																													property="chequeDate"
																													componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																													type="image"
																													id="chequeDate"
																													value= "" indexId="rowCount"/>
																									</logic:notPresent>
																								
																							</td>
																							<td class="iCargoTableDataTd" >
																								<logic:present  name="settlementDetailsVO" property="chequeBank">
																									<ihtml:text property="bankName" indexId="rowCount" value="<%=settlementDetailsVO.getChequeBank()%>"  componentID="CMP_MRA_GPABILLING_SETTLEINV_BANK"  disabled="true"/>
																								</logic:present>
																								<logic:notPresent  name="settlementDetailsVO" property="chequeBank">
																									<ihtml:hidden property="bankName" value="" indexId="rowCount"/>
																								</logic:notPresent>
																							</td>
																							<td class="iCargoTableDataTd" >
																								<logic:present  name="settlementDetailsVO" property="chequeBranch">
																									<ihtml:text property="branchName" indexId="rowCount" value="<%=settlementDetailsVO.getChequeBranch()%>" componentID="CMP_MRA_GPABILLING_SETTLEINV_BRANCH"  disabled="true"/>
																								</logic:present>
																								<logic:notPresent  name="settlementDetailsVO" property="chequeBranch">
																									<ihtml:hidden property="branchName" value="" indexId="rowCount"/>
																								</logic:notPresent>
																							</td>
																							<td class="iCargoTableDataTd" >
																								<logic:present  name="settlementDetailsVO" property="chequeAmount">
																								<ibusiness:moneyDisplay showCurrencySymbol="false" name="settlementDetailsVO" moneyproperty="chequeAmount"  property="chequeAmount"/>

																								
																								</logic:present>
																								</td>
																							<%if  ("Y".equals(((SettlementDetailsVO)settlementDetailsVO).getIsDeleted()) ) {%>
																							<td class="iCargoTableDataTd">
																							<input type="checkbox" name ="isDelete" indexId="rowCount" checked disabled="true">
																							</td>
																							
																							<%}else{%>
																							<td class="iCargoTableDataTd">
																							
																							<input type="checkbox" name ="isDelete" indexId="rowCount" >
																							
																							</td>
																							
																							<%}%>
																							<td class="iCargoTableDataTd" >
																								<logic:present  name="settlementDetailsVO" property="remarks">
																									<ihtml:text  property="chequeRemarks" value="<%=settlementDetailsVO.getRemarks()%>" indexId="rowCount" componentID="CMP_MRA_GPABILLING_SETTLEINV_REMARKS"  disabled="true"/>
																								</logic:present>
																								<logic:notPresent  name="settlementDetailsVO" property="remarks">
																									<ihtml:hidden property="chequeRemarks" value="" indexId="rowCount"/>
																								</logic:notPresent>
																							</td>
																		</tr>
																
																</logic:iterate>
														</logic:present>
													</logic:iterate>
													</logic:present>
														<!--template row strs-->
															<bean:define id="templateRowCount" value="0"/>
																<tr template="true" id="captureRow" style="display:none">
																	<td width="1%" class=" ic-center" >
																		<input type="checkbox"  name="rowId" />	
																		<ihtml:hidden property="stlOpFlag" value="NOOP"/>
																	</td>
																	<td>
																		<ihtml:text property="chequeNumber" indexId="templateRowCount" componentID="CMP_MRA_GPABILLING_SETTLEINV_CHEQUENO" maxlength="15" value=""/>
																	</td>
																	<td class="iCargoTableDataTd" >
																		
																			<ibusiness:calendar
																					indexId="templateRowCount" 
																					property="chequeDate"
																					componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																					type="image"
																					id="chequeDate"																					
																					value=""/>
																		
																	</td>
																	
																	<td><ihtml:text property="bankName" indexId="templateRowCount"  componentID="CMP_MRA_GPABILLING_SETTLEINV_BANK" maxlength="20" value=""/></td>
																	<td><ihtml:text property="branchName" indexId="templateRowCount"  componentID="CMP_MRA_GPABILLING_SETTLEINV_BRANCH" maxlength="20" value=""/></td>
																	<td><ihtml:text property="chequeAmount" indexId="templateRowCount"  componentID="CMP_MRA_GPABILLING_SETTLEINV_AMT" maxlength="16" value=""/></td>
																	
																	<td><input type="checkbox" name="isDelete" indexId="templateRowCount"  value=""></td>
																	<td><ihtml:text property="chequeRemarks" indexId="templateRowCount"  componentID="CMP_MRA_GPABILLING_SETTLEINV_REMARKS" maxlength="50" value=""/></td>
																	<!-- template row ends-->
						            	                       	</tr>
														</tbody>
													  </table>
													</div>
												</div>
										
										</fieldset>
									</div>
				
				<div class="ic-row">
						<fieldset class="ic-field-set">
									<legend><common:message key="mailtracking.mra.gpabilling.invoicesettlement.settlehistorydetails"/></legend>
												<div class="ic-row ic-pad-3 ic-section ">
												   <div class="tableContainer " id="div3" style="height:125px;">
													  <table style="width:100%;" class="fixed-header-table " id="historySettlement" >												     
														 <thead>
															<tr>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"></td></tr>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.setmntrefno"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeno"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequedate"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.bank"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.branch"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.amount"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.setmntcurr"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.setmntdate"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.delete"/></td>
															<td  class="iCargoTableHeader" width="3%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.invoicesettlement.remarks"/></td>
														</thead>
															<logic:present name="INV_SETTLEMENT_HISVOS">
														<tfoot>
														<tr>
														<td>&nbsp; Total</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
														<td>&nbsp;</td>
														<td  id="netAmount">
														<bean:define id ="netAmount"  value="<%=new Formatter().format(FORMAT_STRING,form.getTotalAmount()).toString().trim()%>"/>
														<common:write  name="netAmount" />
														</td>
														<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
														</tr>
														</tfoot>
														</logic:present>
														<tbody>
														<logic:present name="INV_SETTLEMENT_HISVOS">
															<logic:iterate id="iterator" name="INV_SETTLEMENT_HISVOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO" indexId="rowCount">
															
																 <tr >
																 <td>
																 </td>
																<td>
																			<logic:present	name="iterator" property="settlementReferenceNumber">
																			<bean:write name="iterator" property="settlementReferenceNumber"/>
																			<ihtml:hidden property="settlementReferenceNumber" value="<%=iterator.getSettlementReferenceNumber()%>"/>
																			</logic:present>
																			<logic:notPresent	name="iterator" property="settlementReferenceNumber">
																			<ihtml:hidden property="settlementReferenceNumber" value=""/>
																			</logic:notPresent>
																</td>
																<td>
																			<logic:present	name="iterator" property="chequeNumber">
																			<bean:write name="iterator" property="chequeNumber"/>
																			<ihtml:hidden property="chequeNumber" value="<%=iterator.getChequeNumber()%>"/>
																			</logic:present>
																			<logic:notPresent	name="iterator" property="chequeNumber">
																			<ihtml:hidden property="chequeNumber" value=""/>
																			</logic:notPresent>
															</td>
															<td class="iCargoTableDataTd" >
																
																	<logic:present name="iterator" property="chequeDate">
																		<bean:define id="chequeDate" name="iterator" property="chequeDate" />
																		<%
																		  String cheqDate = TimeConvertor.toStringFormat(((LocalDate)chequeDate).toCalendar(),"dd-MMM-yyyy");
																		%>
																		<ibusiness:calendar
																			    property="chequeDate"
																			    componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																			    type="image"
																			    id="chequeDate"
																			    value="<%=cheqDate%>" indexId="rowCount" disabled="true"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="chequeDate">
																		<ibusiness:calendar
																					property="chequeDate"
																					componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																					type="image"
																					id="chequeDate"
																					value= "" indexId="rowCount"/>
																	</logic:notPresent>
																								
															</td>
															<td>
																			<logic:present	name="iterator" property="chequeBank">
																			<bean:write name="iterator" property="chequeBank"/>
																			<ihtml:hidden property="chequeBank" value="<%=iterator.getChequeBank()%>"/>
																			</logic:present>
																			<logic:notPresent	name="iterator" property="chequeBank">
																			<ihtml:hidden property="chequeBank" value=""/>
																			</logic:notPresent>
															</td>
															<td>
																			<logic:present	name="iterator" property="chequeBranch">
																			<bean:write name="iterator" property="chequeBranch"/>
																			<ihtml:hidden property="chequeBranch" value="<%=iterator.getChequeBranch()%>"/>
																			</logic:present>
																			<logic:notPresent	name="iterator" property="chequeBranch">
																			<ihtml:hidden property="chequeBranch" value=""/>
																			</logic:notPresent>
															</td>															
															<td >
															
															<logic:present	name="iterator" property="chequeAmount">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="chequeAmount"  property="chequeAmount" />
															
															</logic:present>
															
															</td>
															<td>
																			<logic:present	name="iterator" property="chequeCurrency">
																			<bean:write name="iterator" property="chequeCurrency"/>
																			<ihtml:hidden property="chequeCurrency" value="<%=iterator.getChequeCurrency()%>"/>
																			</logic:present>
																			<logic:notPresent	name="iterator" property="chequeCurrency">
																			<ihtml:hidden property="chequeCurrency" value=""/>
																			</logic:notPresent>
															</td>
															<td class="iCargoTableDataTd" >
																
																	<logic:present name="iterator" property="settlementDate">
																		<bean:define id="settlementDate" name="iterator" property="settlementDate" />
																		<%
																		  String settleDate = TimeConvertor.toStringFormat(((LocalDate)settlementDate).toCalendar(),"dd-MMM-yyyy");
																		%>
																		<ibusiness:calendar
																			    property="settlementDate"
																			    componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																			    type="image"
																			    id="settlementDate"
																			    value="<%=settleDate%>" indexId="rowCount" disabled="true"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="settlementDate">
																		<ibusiness:calendar
																					property="settlementDate"
																					componentID="CMP_MRA_GPABILLING_SETTLEINV_CHQDATE"
																					type="image"
																					id="settlementDate"
																					value= "" indexId="rowCount"/>
																	</logic:notPresent>
																		
																		</td>
																		<td class="iCargoTableDataTd">
																		 <logic:equal  name="iterator" property="isDeleted" value="true">
																		<input type="checkbox" name ="isDeleted" indexId="rowCount" checked disabled="true">
																		<ihtml:hidden property="isDelete" value="true"/>
																		</logic:equal>
																		<logic:notEqual  name="iterator" property="isDeleted" value="true">
																		<input type="checkbox" name ="isDeleted" indexId="rowCount" disabled= "true">
																		<ihtml:hidden property="isDelete" value="false"/>
																		</logic:notEqual>
																		</td>
																		<td class="iCargoTableDataTd" >
																			<logic:present  name="iterator" property="remarks">
																				<ihtml:text  property="chequeRemarks" value="<%=iterator.getRemarks()%>" indexId="rowCount" componentID="CMP_MRA_GPABILLING_SETTLEINV_REMARKS"  disabled="true"/>
																			</logic:present>
																			<logic:notPresent  name="iterator" property="remarks">
																				<ihtml:hidden property="chequeRemarks" value="" indexId="rowCount" disabled="true"/>
																			</logic:notPresent>
																		</td>
																							
															
															</tr>
															
															</logic:iterate>
															</logic:present>
															
														</tbody>
													  </table>
													</div>
													</div>
										 </fieldset>	
									</div>
					</div>
				<div class="ic-foot-container">
					<div class="ic-row">
						<div class="ic-button-container">
	
						<ihtml:nbutton property="btnPrint" accesskey ="P" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNPRINT" >
							<common:message key="mailtracking.mra.gpabilling.invoicesettlement.print" />
						</ihtml:nbutton>
						<!-- Invoice Details button navigates to GPA billing enquiry screen which is removed.Hence button flow is commented -->
						<!--
						 <% if(!indigoflg){%>
						<ihtml:nbutton property="btnInvoiceDetails" accesskey ="D" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNINVOICEDETAILS" >
						<common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicedetails" />
					        </ihtml:nbutton>
					     <%}%> -->
						<ihtml:nbutton property="btnAccDetails" accesskey ="A" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNLISTACCDETAILS" >
						<common:message key="mailtracking.mra.gpabilling.invoicesettlement.listaccdetails" />
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnSave" accesskey ="S" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNSAVE" >
						<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.save" />
					        </ihtml:nbutton>
					        <ihtml:nbutton property="btnClose" accesskey ="O" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNCLOSE" >
						<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.close" />
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
