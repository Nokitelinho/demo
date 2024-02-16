<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: LoanBorrowDetailsEnquiry.jsp
* Date				: 14-Feb-2006
* Author(s)			: Roopak V.S.

****************************************************** --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>


<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<bean:define id="form"
      name="listULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm"
      toScope="request" />
<business:sessionBean id="transactionFilterVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionFilterVO" />
	<logic:present name="transactionFilterVOSession">
		<bean:define id="transactionFilterVOSession" name="transactionFilterVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="transactionListVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionListVO" />
	<logic:present name="transactionListVOSession">
		<bean:define id="transactionListVOSession" name="transactionListVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="txnTypes" />
	<logic:present name="txnTypesSession">
		<bean:define id="txnTypesSession" name="txnTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnNaturesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="txnNatures" />
	<logic:present name="txnNaturesSession">
		<bean:define id="txnNaturesSession" name="txnNaturesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="partyTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="partyTypes" />
	<logic:present name="partyTypesSession">
		<bean:define id="partyTypesSession" name="partyTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="accCodesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="accessoryCodes" />
	<logic:present name="accCodesSession">
		<bean:define id="accCodesSession" name="accCodesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnStatusSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="txnStatus" />
	<logic:present name="txnStatusSession">
		<bean:define id="txnStatusSession" name="txnStatusSession" toScope="page"/>
	</logic:present>





<ihtml:hidden property="chkTransaction" />
<ihtml:hidden property="uldNumbersSelected" />
<ihtml:hidden property="txnNumbersSelected" />
<ihtml:hidden property="totalCountFlag" />
<div id="container1" class="content" style="height:98%;text-align:left">





		<ul class="tabs">
   		  <li>
		      <button type="button" class="tab" accesskey="s" onclick="return showPane(event,'pane1', this)" id="tab.shipment">
		   	  <b><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldtxndetails"/></b>
		      </button>
		  </li>
		  <li>
		      <button type="button" class="tab" accesskey="u" onclick="return showPane(event,'pane2', this)" id="tab.uld">
		   	  <b><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.accessories"/></b>
		      </button>
	 	  </li>
	       </ul>
  	      <div class="tab-panes">
	<!-- CONTENTS OF TAB1 STARTS -->
	     <div id="pane1">
			<div class="ic-row marginT5">
				<div class="ic-col-50" id="_paginationResultsLabel">
		 			<logic:present name="transactionListVOSession" property="transactionDetailsPage">
		 			<common:paginationTag
		 				pageURL="javascript:submitList('lastPageNum','displayPage')"
		 				name="transactionListVOSession" property="transactionDetailsPage"
		 				display="label"
		 				labelStyleClass="iCargoResultsLabel"
		 				lastPageNum="<%=form.getLastPageNumber() %>" />
		 			</logic:present>
				</div>
				<div class="ic-col-50 paddR5" id="_paginationLink" style="text-align:right;">
		 			<logic:present name="transactionListVOSession" property="transactionDetailsPage">
		 			<common:paginationTag
		 				pageURL="javascript:submitList('lastPageNum','displayPage')"
		 				name="transactionListVOSession" property="transactionDetailsPage"
		 				display="pages"
		 				linkStyleClass="iCargoLink"
			    		disabledLinkStyleClass="iCargoLink"
		 				lastPageNum="<%=form.getLastPageNumber()%>"
						exportToExcel="true"
						exportTableId="transactiontable"
						exportAction="uld.defaults.transaction.listuldtransactions.do"
						columnSelector="true"
						tableId="transactiontable"/>
		 			</logic:present>
		 			<logic:notPresent name="transactionListVOSession" property="transactionDetailsPage">
		 			&nbsp;
					<div align="right">
						<common:columnSelector 
						name="transTable" 
						tableId="transactiontable"           
						id="transactiontable" />
					</div>
		 			</logic:notPresent>
					&nbsp;&nbsp;&nbsp;&nbsp;
		 			</div>
		 		   </div> 	
				
				<div class="ic-row responsive-height">
							<div class="tableContainer" id="div1" style="height:1050px">
								<table class="fixed-header-table" id="transactiontable" style="width:150%;">




                    <thead>
                       <tr class="iCargoTableHeadingLeft">
			 	<td style="width:3%; text-align:center"><input type="checkbox" name="masterUld" data-ic-csid="td1" data-ic-column-key="td1" onclick="updateHeaderCheckBox(this.form,this.form.masterUld,this.form.uldDetails)"/><span></span></td>
				
			    <td style="width:10%;" data-ic-csid="td2" data-ic-column-key="td2"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldno"/><span></span></td>

				<td style="width:10%;" data-ic-csid="td3" data-ic-column-key="td3"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txntype"/><span></span></td>

			    <td  style="width:12%;" data-ic-csid="td4" data-ic-column-key="td4"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnstatus"/><span></span></td>
			    <td style="width:15%;" data-ic-csid="td5" data-ic-column-key="td5"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txndate"/><span></span></td>
			    <td style="width:7%;" data-ic-csid="td6" data-ic-column-key="td6"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.station"/><span></span></td>


			    <td style="width:7%;" data-ic-csid="td7" data-ic-column-key="td7">Loaned To<span></span></td>
			    <td style="width:7%;" data-ic-csid="td8" data-ic-column-key="td8">Loaned From<span></span></td>
				<td  style="width:10%;" data-ic-csid="td9" data-ic-column-key="td9"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.remainingdaystoreturn"/><span></span></td>
				
				<td style="width:10%;" data-ic-csid="td10" data-ic-column-key="td10"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.leaseenddate"/><span></span></td>
			    <td style="width:10%;" data-ic-csid="td11" data-ic-column-key="td11"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.remainingdaystoendlease"/><span></span></td>
				<td  style="width:10%;" data-ic-csid="td12" data-ic-column-key="td12"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.desstation"/><span></span></td>
				
				<td style="width:10%;" data-ic-csid="td13" data-ic-column-key="td13" >Return Control Receipt No.<span></span></td>
				<td style="width:10%;" data-ic-csid="td14" data-ic-column-key="td14">Control Receipt No.<span></span></td>
				<td  style="width:10%;" data-ic-csid="td15" data-ic-column-key="td15"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returnstation"/><span></span></td>
				<td style="width:15%;" data-ic-csid="td16" data-ic-column-key="td16"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returndate"/><span></span></td>
				<td style="width:10%;" data-ic-csid="td17" data-ic-column-key="td17"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldCondition"/><span></span></td>
				<td style="width:10%;" data-ic-csid="td18" data-ic-column-key="td18"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.partytype"/><span></span></td>

				<td style="width:15%;" data-ic-csid="td19" data-ic-column-key="td19">MUC Status<span></span></td>
				<td style="width:10%;" data-ic-csid="td20" data-ic-column-key="td20">MUC Ref No<span></span></td>
				<td style="width:10%;" data-ic-csid="td21" data-ic-column-key="td21">MUC Date<span></span></td>
				<td style="width:10%;" data-ic-csid="td22" data-ic-column-key="td22"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.demurrage"/><span></span></td>
				<td style="width:10%;" data-ic-csid="td23" data-ic-column-key="td23"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.currency"/><span></span></td>
			    <td style="width:10%" data-ic-csid="td24" data-ic-column-key="td24"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.invoicerefno"/><span></span></td>
			    <td style="width:15%" data-ic-csid="td25" data-ic-column-key="td25"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.remarks"/><span></span></td>
			    <td style="width:10%" data-ic-csid="td26" data-ic-column-key="td26"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnrefno"/><span></span></td>

		      </tr>
		    </thead>
		    <tbody>
		    <logic:present name="transactionListVOSession" property="transactionDetailsPage">
		    <bean:define id="uldTransactionsDetailVOs" name="transactionListVOSession" property="transactionDetailsPage" toScope="page"/>
		    <%int i=0;%>
		    <logic:iterate id="uldTransactionsDetailsVO" name="uldTransactionsDetailVOs" type="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO" indexId="index">
		    <%i++;%>
		      <bean:define id="uldNum" name="uldTransactionsDetailsVO" property="uldNumber" toScope="page"/>
		      <bean:define id="txnRefNumber" name="uldTransactionsDetailsVO" property="transactionRefNumber" toScope="page"/>
		       <% String primaryKeyUld=(String)uldNum+txnRefNumber.toString()+i;%>

		       
			 <tr  >
			  <td class="iCargoTableDataTd text-center" ><input type="checkbox" name="uldDetails" onclick="toggleTableHeaderCheckbox('uldDetails',this.form.masterUld)" value="<%=primaryKeyUld%>"/></td>

			  <td class="iCargoTableDataTd">
			  <%String uldno = "";%>
			  <logic:present name="uldTransactionsDetailsVO" property="uldNumber">
				<bean:define id="uldNo" name="uldTransactionsDetailsVO" property="uldNumber" toScope="page"/>
				<%uldno = (String)uldNo;%>
			  </logic:present>
			  <input type="hidden" name="hiddenUldNo" value="<%=(String)uldno%>"/>
			  <bean:write  name="uldTransactionsDetailsVO" property="uldNumber"/></td>
			  <td class="iCargoTableDataTd" >
			  			  <%String txnTypeDesc = "";%>
			  			  <%String txnTypeValue = "";%>
			  			  <logic:present name="uldTransactionsDetailsVO" property="transactionType">
			  			   <bean:define id="txnType" name="uldTransactionsDetailsVO" property="transactionType" toScope="page"/>
			  			       <bean:define id="txnTypesSessionColl" name="txnTypesSession" toScope="page" />
			  				   <logic:iterate id="txnTypeVO" name="txnTypesSessionColl" >
			  					 <bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
			  				     <%if(txnType.equals(fieldValue)){%>
			  					 <bean:define id="fieldDesc" name="txnTypeVO" property="fieldDescription" />
			  				     <%txnTypeDesc=(String)fieldDesc;
			  				       txnTypeValue=(String)fieldValue;}%>
			  				</logic:iterate>
			  			   </logic:present>
			  			    <input type="hidden" name="hiddenTxnType" value="<%=txnTypeValue%>"/>
			  			    <%=(String)txnTypeDesc %>
			  </td>
			  <td class="iCargoTableDataTd">
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
			  <td class="iCargoTableDataTd">
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
			  <td class="iCargoTableDataTd" ><bean:write  name="uldTransactionsDetailsVO" property="transactionStationCode"/></td>
			  <td class="iCargoTableDataTd" >
			  <%String topartCd = "";%>
			  <logic:present name="uldTransactionsDetailsVO" property="toPartyCode">
				   <bean:define id="ptyCode" name="uldTransactionsDetailsVO" property="toPartyCode" toScope="page"/>
				   <%topartCd = (String)ptyCode;%>
			  </logic:present>
			  <input type="hidden" name="hiddenPartyCode" value="<%=(String)topartCd%>"/>
			  <bean:write  name="uldTransactionsDetailsVO" property="toPartyCode"/>
			   <%String topartName = "";%>
			  <logic:present name="uldTransactionsDetailsVO" property="toPartyName">
				   <bean:define id="toPartyName" name="uldTransactionsDetailsVO" property="toPartyName" toScope="page"/>
				   <%topartName = (String)toPartyName;%>
			  </logic:present>
			  <input type="hidden" name="hiddenPartyName" value="<%=(String)topartName%>"/>

			  </td>
			  <td class="iCargoTableDataTd">
			  			  <%String frompartCd = "";%>
			  			  <logic:present name="uldTransactionsDetailsVO" property="fromPartyCode">
			  			       <bean:define id="ptyCode" name="uldTransactionsDetailsVO" property="fromPartyCode" toScope="page"/>
			  			       <%frompartCd = (String)ptyCode;%>
			  			  </logic:present>
			  			  <input type="hidden" name="hiddenFromPartyCode" value="<%=(String)frompartCd%>"/>
			  			  <bean:write  name="uldTransactionsDetailsVO" property="fromPartyCode"/>
			  </td>
		<td class="iCargoTableDataTd">
		 <logic:present name="uldTransactionsDetailsVO" property="transactionStatus">
		 <logic:equal name="uldTransactionsDetailsVO" property="transactionStatus" value="T">
			  			 <% int days = 0;%>
			  			  <logic:present name="uldTransactionsDetailsVO" property="remainingDayToReturn">
			  			    <bean:define id="remainingDays" name="uldTransactionsDetailsVO" property="remainingDayToReturn" toScope="page"/>
							<%days = Integer.parseInt((String)remainingDays);%>
							 <%if(days>0){%>
			  			   <div style="color:green" align="centre"> <bean:write  name="uldTransactionsDetailsVO" property="remainingDayToReturn"/></div>
							<%}else if(days<0){%>
							 <div style="color:red" align="centre"> <bean:write  name="uldTransactionsDetailsVO" property="remainingDayToReturn"/></div>
							 <%}else{%>
							  <div style="color:red" align="centre"> 
							  <bean:write  name="uldTransactionsDetailsVO" property="remainingDayToReturn"/>
							  </div>
							  <%}%>
			  			  </logic:present>
				  </logic:equal>		  
			    </logic:present>			 
			  </td>
			  <td class="iCargoTableDataTd" >
			  <logic:present name="uldTransactionsDetailsVO" property="leaseEndDate">
			  					<%
			  								String lseEndDate ="";
			  								if(uldTransactionsDetailsVO.getLeaseEndDate() != null) {
			  								lseEndDate = TimeConvertor.toStringFormat(
			  											uldTransactionsDetailsVO.getLeaseEndDate().toCalendar(),"dd-MMM-yyyy");
			  								}
			  							%>
			  							<%=lseEndDate%>
					</logic:present>
			  </td>
			  <td class="iCargoTableDataTd">
		 <logic:present name="uldTransactionsDetailsVO" property="transactionStatus">
		 <logic:equal name="uldTransactionsDetailsVO" property="transactionStatus" value="T">
			  			 <% int days = 0;%>
			  			  <logic:present name="uldTransactionsDetailsVO" property="remainingDaysToEndLease">
			  			    <bean:define id="remainingDaysToEndLease" name="uldTransactionsDetailsVO" property="remainingDaysToEndLease" toScope="page"/>
							<%days = Integer.parseInt((String)remainingDaysToEndLease);%>
							 <%if(days>0){%>
			  			   <div style="color:green" align="centre"> <bean:write  name="uldTransactionsDetailsVO" property="remainingDaysToEndLease"/></div>
							<%}else if(days<0){%>
							 <div style="color:red" align="centre"> <bean:write  name="uldTransactionsDetailsVO" property="remainingDaysToEndLease"/></div>
							 <%}else{%>
							  <div style="color:red" align="centre"> 
							  <bean:write  name="uldTransactionsDetailsVO" property="remainingDaysToEndLease"/>
							  </div>
							  <%}%>
			  			  </logic:present>
				  </logic:equal>		  
			    </logic:present>			 
			  </td>
			   <td class="iCargoTableDataTd" ><bean:write  name="uldTransactionsDetailsVO" property="txStationCode"/></td>
			  <td class="iCargoTableDataTd" >
			  <%String retcrn = "";%>
			  	 <logic:present name="uldTransactionsDetailsVO" property="returnCRN">
			  		<bean:write  name="uldTransactionsDetailsVO" property="returnCRN"/>
					<bean:define id="controlRcptNo" name="uldTransactionsDetailsVO" property="returnCRN" toScope="page"/>
					<%retcrn = (String)controlRcptNo;%>
			  	 </logic:present>
			  	 
				  <!--input type="hidden" name="hiddenCrn" value="<%=retcrn%>"/-->
				    <%String poolFlag = "";%>
					 <logic:present name="uldTransactionsDetailsVO" property="poolOwnerFlag">
						<bean:define id="poolOwnerFlag" name="uldTransactionsDetailsVO" property="poolOwnerFlag" toScope="page"/>
						 <%poolFlag = (String)poolOwnerFlag;%>
					 </logic:present>
				  <input type="hidden" name="hiddenPoolFlag" value="<%=poolFlag%>"/>
		      </td>
		      
		      <td class="iCargoTableDataTd" >
			  <%String crn = "";%>
				 <logic:present name="uldTransactionsDetailsVO" property="controlReceiptNumber">
					<bean:write  name="uldTransactionsDetailsVO" property="controlReceiptNumber"/>
					<bean:define id="controlRcptNo" name="uldTransactionsDetailsVO" property="controlReceiptNumber" toScope="page"/>
					<%crn = (String)controlRcptNo;%>
				 </logic:present>
				 
				  <input type="hidden" name="hiddenCrn" value="<%=crn%>"/>
				    <%String poolFlagcrn = "";%>
					 <logic:present name="uldTransactionsDetailsVO" property="poolOwnerFlag">
						<bean:define id="poolOwnerFlag" name="uldTransactionsDetailsVO" property="poolOwnerFlag" toScope="page"/>
						 <%poolFlagcrn = (String)poolOwnerFlag;%>
					 </logic:present>
				  <input type="hidden" name="hiddenPoolFlag" value="<%=poolFlagcrn%>"/>
		      </td>
		      

			  <td class="iCargoTableDataTd" ><bean:write  name="uldTransactionsDetailsVO" property="returnStationCode"/></td>
			  <td class="iCargoTableDataTd" >
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
									<%=rtnDate%>
									<input type="hidden" name="hiddenReturnDate" value="<%=(String)rtnDate%>"/>

									</logic:notPresent>
			 	</td>
			  
		        <td>
				<%String conditionCode = "";%>
			  <logic:present name="uldTransactionsDetailsVO" property="uldConditionCode">
				    <bean:define id="uldConditionCode" name="uldTransactionsDetailsVO" property="uldConditionCode" toScope="page"/>
					<%conditionCode = (String)uldConditionCode;%>
			  </logic:present>
				 <input type="hidden" name="hiddenConditionCode" value="<%=(String)conditionCode%>"/>
				<bean:write  name="uldTransactionsDetailsVO" property="uldConditionCode"/>
		        </td>

		        <td class="iCargoTableDataTd" >
							  <%String ptyTypeDesc = "";%>
							  <%String ptyTypeField = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="partyType">
							  <bean:define id="ptyType" name="uldTransactionsDetailsVO" property="partyType" toScope="page"/>
							      <bean:define id="partyTypesSessionColl" name="partyTypesSession" toScope="page" />
								   <logic:iterate id="partyTypeVO" name="partyTypesSessionColl" >
									 <bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
								     <%if(ptyType.equals(fieldValue)){%>
									 <bean:define id="fieldDesc" name="partyTypeVO" property="fieldDescription" />
								     <%ptyTypeDesc=(String)fieldDesc;
								     ptyTypeField=(String)fieldValue;}%>

								</logic:iterate>
							  </logic:present>

							    <%=(String)ptyTypeDesc %>
							    <input type="hidden" name="hiddenPartyType" value="<%=(String)ptyTypeField%>"/>
			  </td>

			  <td class="iCargoTableDataTd" >
			  			  <%String muciatastatus = "";%>
			  			  <logic:present name="uldTransactionsDetailsVO" property="mucIataStatus">
			  				    <bean:define id="mucIataStatus" name="uldTransactionsDetailsVO" property="mucIataStatus" toScope="page"/>
			  					<%muciatastatus = (String)mucIataStatus;%>
			  			  </logic:present>
			  				 <input type="hidden" name="hiddenMucIataStatus" value="<%=(String)muciatastatus%>"/>
			  			   <bean:write  name="uldTransactionsDetailsVO" property="mucIataStatus"/>
			  </td>

			  <td class="iCargoTableDataTd">
			  			  <logic:present name="uldTransactionsDetailsVO" property="mucReferenceNumber">
			  				     <bean:write  name="uldTransactionsDetailsVO" property="mucReferenceNumber"/>
			  			  </logic:present>
			  </td>

			  <td class="iCargoTableDataTd" >

			  			  <logic:present name="uldTransactionsDetailsVO" property="mucDate">
			  			  					<%
			  			  								String mucDate ="";
			  			  								if(uldTransactionsDetailsVO.getMucDate() != null) {
			  			  								mucDate = TimeConvertor.toStringFormat(
			  			  											uldTransactionsDetailsVO.getMucDate().toCalendar(),"dd-MMM-yyyy");
			  			  								}
			  			  							%>
			  			  							<%=mucDate%>


			  					</logic:present>


			  </td>

				<td class="iCargoTableDataTd"  style="text-align:right">
							  <%String waiverAmount = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="waived">
								<bean:define id="waiverAmt" name="uldTransactionsDetailsVO" property="waived" toScope="page"/>
								<%waiverAmount = String.valueOf(waiverAmt);%>
							  </logic:present>
							   <input type="hidden" name="hiddenWaiver" value="<%=String.valueOf(waiverAmount)%>"/>
							  <%String dmg = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="total">
								<bean:define id="totalAmt" name="uldTransactionsDetailsVO" property="total" toScope="page"/>
								<%dmg = String.valueOf(totalAmt);%>
							  </logic:present>
							   <input type="hidden" name="hiddenDmg" value="<%=String.valueOf(dmg)%>"/>

							   <bean:write  name="uldTransactionsDetailsVO" property="total" localeformat="true" /></td>
				<td class="iCargoTableDataTd" ><bean:write  name="uldTransactionsDetailsVO" property="currency" /></td>

			 	<td class="iCargoTableDataTd" >
							  <%String invoice = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="invoiceRefNumber">
							  	<bean:define id="invoiceRefNo" name="uldTransactionsDetailsVO" property="invoiceRefNumber" toScope="page"/>
							  	<%invoice = (String)invoiceRefNo;%>
							  </logic:present>
							   <input type="hidden" name="hiddenInvoiceRefNo" value="<%=(String)invoice%>"/>
							   <bean:write  name="uldTransactionsDetailsVO" property="invoiceRefNumber"/>
			  	</td>

			  	<td class="iCargoTableDataTd" >
							 <logic:present name="uldTransactionsDetailsVO" property="transactionStatus">
							   <logic:equal name="uldTransactionsDetailsVO" property="transactionStatus" value="T">
							        <bean:write  name="uldTransactionsDetailsVO" property="transactionRemark"/>
							   </logic:equal>
							   <logic:equal name="uldTransactionsDetailsVO" property="transactionStatus" value="R">
							   	<bean:write  name="uldTransactionsDetailsVO" property="transactionRemark"/>
							   </logic:equal>
							 </logic:present>
		        </td>

		        <td class="iCargoTableDataTd" >
							  <%String txnref = "";%>
							  <logic:present name="uldTransactionsDetailsVO" property="transactionRefNumber">
								<bean:define id="txnrefn" name="uldTransactionsDetailsVO" property="transactionRefNumber" toScope="page"/>
								<%txnref = String.valueOf(txnrefn);%>
							  </logic:present>
							  <input type="hidden" name="hiddenTxnRefNo" value="<%=(String)txnref%>"/>
							  <logic:present name="uldTransactionsDetailsVO" property="capturedRefNumber">
							  	<bean:write  name="uldTransactionsDetailsVO" property="capturedRefNumber"/>
							  </logic:present>
							  <logic:notPresent name="uldTransactionsDetailsVO" property="capturedRefNumber">
							  	<bean:write  name="uldTransactionsDetailsVO" property="transactionRefNumber" format="####"/>
							  </logic:notPresent>
			   </td>

		       </tr>
		      
		      </logic:iterate>
 		    </logic:present>
                     </tbody>

                   </table>
		 </div>
                  		</div>

	      </div>
    <!-- CONTENTS OF TAB1 ENDS -->
    <!-- CONTENTS OF TAB2 STARTS -->
	     <div id="pane2">
	       <div class="ic-row">
				<div class="tableContainer" id="div2"   style="height:550px;width:100%;">
	          <table id="accessoriestable" class="fixed-header-table">
		    <thead>
		       <tr class="iCargoTableHeadingLeft">
			  <td width="4%"><input type="checkbox" name="masterAcc" onclick="updateHeaderCheckBox(this.form,this.form.masterAcc,this.form.accessoryDetails)"/><span></span></td>
			  <td width="12%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txntype"/><span></span></td>
			  <td width="12%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.accessorycode"/><span></span></td>
			  <td width="7%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.quantity"/><span></span></td>
			  <td width="11%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txndate"/><span></span></td>
			  <td width="11%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.partytype"/><span></span></td>
			  <td width="12%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.fromparty"/><span></span></td>
			  <td width="12%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.toparty"/><span></span></td>
			  <td width="11%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.station"/><span></span></td>
			  <td width="11%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returnquantity"/><span></span></td>
			  <td width="25%"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.remarks"/><span></span></td>
	               </tr>
		   </thead>
		   <tbody>
		   <logic:present name="transactionListVOSession" property="accessoryTransactions">
		    <bean:define id="accessoryTransactionVOs" name="transactionListVOSession" property="accessoryTransactions" toScope="page"/>
		    <%int j=0;%>
		    <logic:iterate id="accessoryTransactionVO" indexId="accIndex" name="accessoryTransactionVOs">
		    <%j++;%>
		      <bean:define id="accessCode" name="accessoryTransactionVO" property="accessoryCode" toScope="page"/>
		      <bean:define id="txnRefNumber" name="accessoryTransactionVO" property="transactionRefNumber" toScope="page"/>
 		      <% String primaryKeyAcc=(String)accessCode+txnRefNumber.toString()+j;%>

		      <tr>
			  <td class="iCargoTableDataTd"><input type="checkbox" name="accessoryDetails" onclick="toggleTableHeaderCheckbox('accessoryDetails',this.form.masterAcc)" value="<%=primaryKeyAcc%>"/></td>
			  <td class="iCargoTableDataTd">
			  <%String txnTypeDesc = "";
			    String txnTypeVal = "";%>
			  <logic:present name="accessoryTransactionVO" property="transactionType">
			    <bean:define id="txnType" name="accessoryTransactionVO" property="transactionType" toScope="page"/>
			       <bean:define id="txnTypesSessionColl" name="txnTypesSession" toScope="page" />
				   <logic:iterate id="txnTypeVO" name="txnTypesSessionColl" >
					 <bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
				     <%if(txnType.equals(fieldValue)){%>
					 <bean:define id="fieldDesc" name="txnTypeVO" property="fieldDescription" />
				     <%txnTypeDesc=(String)fieldDesc;
				       txnTypeVal=(String)fieldValue;}%>
				</logic:iterate>
			  </logic:present>
			  <input type="hidden" name="hiddenAccTxnType" value="<%=txnTypeVal%>"/>
                          <%=(String)txnTypeDesc %>
			  </td>
			  <td class="iCargoTableDataTd">
			   <%String accDesc = "";%>
			   <logic:present name="accessoryTransactionVO" property="accessoryCode">
			   <bean:define id="accessCode" name="accessoryTransactionVO" property="accessoryCode" toScope="page"/>
			      <bean:define id="accSessionColl" name="accCodesSession" toScope="page" />
				   <logic:iterate id="accessCodeVO" name="accSessionColl" >
					 <bean:define id="fieldValue" name="accessCodeVO" property="fieldValue" toScope="page" />
				     <%if(accessCode.equals(fieldValue)){%>
					 <bean:define id="fieldDesc" name="accessCodeVO" property="fieldDescription" />
				     <%accDesc=(String)fieldDesc;}%>
				</logic:iterate>
			  </logic:present>
                          <%=(String)accDesc %>
			  </td>
			  <td class="iCargoTableDataTd" style="text-align:right"><bean:write  name="accessoryTransactionVO" property="quantity"/></td>
			 <bean:define id="actualQunatity" name="accessoryTransactionVO" property="quantity" toScope="page"/>
				<%actualQunatity = String.valueOf(actualQunatity);%>
			<html:hidden property="returnQuantityCheck" value="<%=(String)actualQunatity%>" />
			  <td class="iCargoTableDataTd"><bean:write  name="accessoryTransactionVO" property="strTxnDate"/></td>
			  <td class="iCargoTableDataTd">
			  <%String ptyTypeDesc = "";%>
			  <logic:present name="accessoryTransactionVO" property="partyType">
			  <bean:define id="ptyType" name="accessoryTransactionVO" property="partyType" toScope="page"/>
			       <bean:define id="partyTypesSessionColl" name="partyTypesSession" toScope="page" />
				   <logic:iterate id="partyTypeVO" name="partyTypesSessionColl" >
					 <bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
				     <%if(ptyType.equals(fieldValue)){%>
					 <bean:define id="fieldDesc" name="partyTypeVO" property="fieldDescription" />
				     <%ptyTypeDesc=(String)fieldDesc;}%>
				</logic:iterate>
			  </logic:present>
                          <%=(String)ptyTypeDesc %>
                          </td>
			  <td class="iCargoTableDataTd"><bean:write  name="accessoryTransactionVO" property="fromPartyCode"/></td>
			  <td class="iCargoTableDataTd"><bean:write  name="accessoryTransactionVO" property="toPartyCode"/></td>
			  <td class="iCargoTableDataTd"><bean:write  name="accessoryTransactionVO" property="transactionStationCode"/></td>
			  <td class="iCargoTableDataTd">
			  <%String retQuntity="";%>
			  <logic:present name="accessoryTransactionVO" property="returnQuntity">
							  	<bean:define  id="returnQuntity" name="accessoryTransactionVO" property="returnQuntity" toScope="page"/>
				<% retQuntity=(String)returnQuntity ;%>				
				</logic:present>
				  <ihtml:text property="returnQuantity" maxlength="3" styleClass="iCargoEditableTextFieldSmallRowColor1" value="<%=retQuntity%>" /></td>
		          <td class="iCargoTableDataTd"><bean:write  name="accessoryTransactionVO" property="transactionRemark"/></td>
		     </tr>
				
		      </logic:iterate>

 		    </logic:present>
	          </tbody>
	        </table>
			<jsp:include page="/jsp/includes/columnchooser/columnchooser.jsp"/>
	      </div>
	    </div>
<!-- CONTENTS OF TAB2 ENDS -->
         </div>
</div>
 </div>


