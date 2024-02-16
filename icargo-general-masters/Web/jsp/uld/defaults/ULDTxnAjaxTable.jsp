<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDTxnAjaxTable.jsp
* Date				: 14-July-2008
* Author(s)			: A-3093

****************************************************** --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ihtml:form action="/uld.defaults.misc.listuldtrns.do">
	
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="transactionFlag" />
	<business:sessionBean id="transactionFilterVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionFilterVO" />
	<logic:present name="transactionFilterVOSession">
		<bean:define id="transactionFilterVOSession" name="transactionFilterVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="transactionListVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionListVO" />
	<logic:present name="transactionListVOSession">
		<bean:define id="transactionListVOSession" name="transactionListVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnStatusSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="txnStatus" />
	<logic:present name="txnStatusSession">
		<bean:define id="txnStatusSession" name="txnStatusSession" toScope="page"/>
	</logic:present>
	
	<bean:define id="form" name="listULDMovementForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
			toScope="page"/>
	<div class="ic-row">				
		
		<div id="_transaction">
		<div class="ic-row">
			<div class="ic-row">
			<div class="ic-input ic-split-50">
			<logic:present name="ULDDamageRepairDetailsVOs">			 
			     <common:paginationTag
				pageURL="uld.defaults.listdamagerepairdetails.do"
				name="ULDDamageRepairDetailsVOs"
				display="label"
				labelStyleClass="iCargoLink"
			     lastPageNum="<%=form.getLastPageNum() %>" />
			    </logic:present>
			</div>
			<div class="ic-input ic-split-50">
			<div class="ic-button-container">		
  			     <logic:present name="transactionListVOSession" property="transactionDetailsPage">
				 <%System.out.println("ddd");%>
			     <common:paginationTag
				pageURL="uld.defaults.misc.listuldtrns.do"
				name="transactionListVOSession" property="transactionDetailsPage"
				display="label"
				labelStyleClass="iCargoLink"
			     lastPageNum="<%=form.getLastPageNum() %>" />
			    </logic:present>
			   <logic:present name="transactionListVOSession" property="transactionDetailsPage">
			    <common:paginationTag
				pageURL="javascript:submitPage('lastPageNum','displayPage')"
				name="transactionListVOSession" property="transactionDetailsPage"
				display="pages"
				linkStyleClass="iCargoResultsLabel"
			    disabledLinkStyleClass="iCargoResultsLabel"
				lastPageNum="<%=form.getLastPageNum()%>"
				exportToExcel="true"
				exportTableId="Txn"
				exportAction="uld.defaults.misc.listuldtrns.do"/>
				</logic:present>
			</div>
			</div>
		</div>
		<div id="div1" class="tableContainer" style="height:610px">
        <table data-ic-filter-table="true" id="Txn" class="fixed-header-table">
				<thead>
				
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter1" width="10%">
							<common:message key="uld.defaults.misc.uldTrndtl.lbl.Date" scope="request"/><span></span>
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter2" width="10%">
							<common:message key="uld.defaults.misc.uldTrndtl.lbl.Status" scope="request"/><span></span>
							
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter3" width="10%">
							<common:message key="uld.defaults.misc.uldTrndtl.lbl.FrmPrty" scope="request"/><span></span>
							
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter4" width="8%" >
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.ToPrty" scope="request"/><span></span>
								
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter5" width="12%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.ControlReceiptNo" scope="request"/><span></span>
														
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter6" width="10%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.TxnDate" scope="request"/><span></span>
								
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter7" width="8%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.FromAirport" scope="request"/><span></span>
								
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter8" width="8%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.ToAirport" scope="request"/><span></span>
								
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter9" width="10%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.ReturnDate" scope="request"/><span></span>
								
						</td>
						<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryTxnParameter10" width="14%">
								<common:message key="uld.defaults.misc.uldTrndtl.lbl.Remarks" scope="request"/><span></span>
								
						</td>
						
							   
				</thead>
				<tbody>
				<logic:present name="transactionListVOSession" property="transactionDetailsPage">
				<bean:define id="uldTransactionsDetailVOs" name="transactionListVOSession" property="transactionDetailsPage" toScope="page"/>
				<%int i=0;%>
				<logic:iterate id="uldTransactionsDetailsVO" name="uldTransactionsDetailVOs" type="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" indexId="index">
				<%i++;%>
				<tr>
						<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter1">
						<bean:define id ="lstUpddate" name="uldTransactionsDetailsVO" property="lastUpdateTime" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String lastUpatedate=lstUpddate.toDisplayDateOnlyFormat();%>						
						<%=lastUpatedate%>
						</td>
						
					
						 <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter2">													
							<%String txnStatusDesc = "";%>
							<%String txnStatusValue = "";%>
							<logic:present name="uldTransactionsDetailsVO" property="transactionStatus">
								<bean:define id="transStatus" name="uldTransactionsDetailsVO" property="transactionStatus" toScope="page"/>
								<bean:define id="txnStatusSessionColl" name="txnStatusSession" toScope="page" />
								<logic:iterate id="txnStatusVO" name="txnStatusSessionColl" >
									<bean:define id="fieldValue" name="txnStatusVO" property="fieldValue" toScope="page" />
										<%if(transStatus.equals(fieldValue)){%>
											<bean:define id="fieldDesc" name="txnStatusVO" property="fieldDescription" />
												<%txnStatusDesc=(String)fieldDesc;
												txnStatusValue=(String)fieldValue;}%>
								</logic:iterate>
							</logic:present>
							<input type="hidden" name="hiddenTxnStatus" value="<%=txnStatusValue%>"/>
							<%=(String)txnStatusDesc %>				
						</td>			
			<%System.out.println("-txnStatusDesc---------->"+txnStatusDesc);%>
						
						  
						   								  
						    <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter3">
						    <%System.out.println("33333333");%>
						    <%String frompartCd = "";%>
							 <logic:present name="uldTransactionsDetailsVO" property="fromPartyCode">
							   <bean:define id="ptyCode" name="uldTransactionsDetailsVO" property="fromPartyCode" toScope="page"/>
							   <%frompartCd = (String)ptyCode;%>
							  </logic:present>
							  <input type="hidden" name="hiddenFromPartyCode" value="<%=(String)frompartCd%>"/>
							  <bean:write  name="uldTransactionsDetailsVO" property="fromPartyCode"/>
						  </td>		
						  
						  <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter4">
						  <%System.out.println("44444444444");%>
						   <%String topartCd = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="toPartyCode">
								   <bean:define id="ptyCode" name="uldTransactionsDetailsVO" property="toPartyCode" toScope="page"/>
								   <%topartCd = (String)ptyCode;%>
							  </logic:present>
							  <input type="hidden" name="hiddenPartyCode" value="<%=(String)topartCd%>"/>
							  <bean:write  name="uldTransactionsDetailsVO" property="toPartyCode"/>
						   </td>
						   					  
						  
						  
						  <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter5">
						  <%String crn = "";%>						  
						  <logic:present name="uldTransactionsDetailsVO" property="controlReceiptNumber">
							 <bean:write  name="uldTransactionsDetailsVO" property="controlReceiptNumber"/>
							 <bean:define id="controlRcptNo" name="uldTransactionsDetailsVO" property="controlReceiptNumber" toScope="page"/>
							 <%crn = (String)controlRcptNo;%>
						  </logic:present>				 		 
						  <input type="hidden" name="hiddenCRN" value="<%=(String)crn%>"/>
						  <bean:write  name="uldTransactionsDetailsVO" property="returnCRN"/>
						  </td>
						  
						  <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter6">
							<logic:present name="uldTransactionsDetailsVO" property="transactionDate">
											<%
														String txnDate ="";
														if(uldTransactionsDetailsVO.getTransactionDate() != null) {
														txnDate = TimeConvertor.toStringFormat(
																	uldTransactionsDetailsVO.getTransactionDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
														}
													%>
													<%=txnDate%>

								</logic:present>
						 </td>
						 <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter7"><bean:write  name="uldTransactionsDetailsVO" property="transactionStationCode"/></td>
						 <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter8"><bean:write  name="uldTransactionsDetailsVO" property="txStationCode"/></td>
						  <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter9">
								  <%String retDate = "";%>
								  <logic:present name="uldTransactionsDetailsVO" property="strRetDate">
									<bean:define id="stRetDate" name="uldTransactionsDetailsVO" property="strRetDate" toScope="page"/>
									<%retDate = (String)stRetDate;%>
								  </logic:present>
								    <logic:present name="uldTransactionsDetailsVO" property="returnDate">
												<%
															String rtnDate ="";
															if(uldTransactionsDetailsVO.getReturnDate() != null) {
															rtnDate = TimeConvertor.toStringFormat(
																		uldTransactionsDetailsVO.getReturnDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
															}
														%>
														<%=rtnDate%>
														<input type="hidden" name="hiddenReturnDate" value="<%=(String)rtnDate%>"/>


										</logic:present>
										<logic:notPresent name="uldTransactionsDetailsVO" property="returnDate">
										<%
											String rtnDate =" ";
										%>
										<input type="hidden" name="hiddenReturnDate" value="<%=(String)rtnDate%>"/>

										</logic:notPresent>
						 	</td>
							<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryTxnParameter10">
									 <logic:present name="uldTransactionsDetailsVO" property="transactionStatus">									
									   <logic:equal name="uldTransactionsDetailsVO" property="transactionStatus" value="T">									 
									   <logic:present name="uldTransactionsDetailsVO" property="transactionRemark">									   
									        <bean:write  name="uldTransactionsDetailsVO" property="transactionRemark"/>
										</logic:present>
									   </logic:equal>
									   <logic:notEqual name="uldTransactionsDetailsVO" property="transactionStatus" value="T">									  
										<logic:present name="uldTransactionsDetailsVO" property="returnRemark">								
									        <bean:write  name="uldTransactionsDetailsVO" property="returnRemark"/>
										</logic:present>
									  </logic:notEqual>
									 </logic:present>
					        </td>
			</tr>
			
			
				</logic:iterate>
				</logic:present>
                     </tbody>
			</table>
			</div>
		</div>
	</div>	
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

