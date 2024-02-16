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
<%@ page import = "java.math.BigDecimal" %>
<%@ page import = "java.math.RoundingMode" %>
<html:html locale="true">
<head>
	
<title><common:message bundle="loanBorrowDetailsEnquiryResources" key="uld.defaults.loanBorrowDetailsEnquiry.lbl.loanborrowtitle"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/uld/defaults/LoanBorrowDetailsEnquiry_Script.jsp"/>
</head>

<body id="bodyStyle">
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
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
<business:sessionBean id="mucStatusSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="mUCStatus" />
	<logic:present name="mucStatusSession">
		<bean:define id="mucStatusSession" name="mucStatusSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="totDemmurage"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get" attribute="totalDemmurage" />
<business:sessionBean id="baseCurrency"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowdetailsenquiry"
	method="get" attribute="baseCurrency" />

<bean:define id="form"
      name="listULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm"
      toScope="request" />
<div class="iCargoContent ic-masterbg" style="width:100%;height:100%;overflow:auto;" >
<ihtml:form action="/uld.defaults.transaction.screenloadloanborrowdetailsenquiry.do">
<jsp:include page="/jsp/includes/tab_support.jsp" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="mySearchEnabled" />

<html:hidden property="mode" />
<html:hidden property="popupflag" />
<ihtml:hidden property="comboFlag"/>
<ihtml:hidden property="enquiryDisableStatus"/>
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="listMode" />
<ihtml:hidden property="showTab" />
<ihtml:hidden property="listStatus" />
<ihtml:hidden property="msgFlag" />
<ihtml:hidden property="pageURL" />
<html:hidden property="modDisplayPage" />
<html:hidden property="modCurrentPage" />
<html:hidden property="modLastPageNum" />
<html:hidden property="modTotalRecords" />
<!-- Added by A-7359 for ICRD-255844 starts here-->
<ihtml:hidden property="fromPopup" />
<!-- Added by A-7359 for ICRD-255844 ends here-->
<div class="ic-content-main">
	<span class="ic-page-title ic-display-none"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.loanborrowpagetitle"/>
	</span>
	
   <div class="ic-head-container">			 <!--Added by A-7359 for ICRD - 224586 starts here -->

		<div class="ic-filter-panel">
			<div class="ic-input-container ic-left">
			<b style="text-transform : uppercase"><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.searchcriteria"/></b>
				<div class="ic-row">
			<div class="ic-input ic-split-15 ic-label-35">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldno"/></label>
				
			  <logic:notPresent name="transactionFilterVOSession" property="uldNumber">

				<ibusiness:uld id="uldno" uldProperty="uldNum" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_ULDNO" style="text-transform: uppercase"/>
			  </logic:notPresent>
			  <logic:present name="transactionFilterVOSession" property="uldNumber">
			     <bean:define id="uldNum" name="transactionFilterVOSession" property="uldNumber" toScope="page"/>

				<ibusiness:uld id="uldno" uldProperty="uldNum" uldValue="<%=(String)uldNum%>" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_ULDNO" style="text-transform: uppercase"/>
	    		  </logic:present>
			</div>

			<div class="ic-input ic-split-15 ic-label-45">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.uldtypecode"/>
					</label>			
			  <logic:notPresent name="transactionFilterVOSession" property="uldTypeCode">
			     <ihtml:text property="uldTypeCode" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_ULDCODE" value=""/>
				 <div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'uld','1','uldTypeCode','')"  alt="ULD Type LOV"/>
				 </div>
			  </logic:notPresent>
			  <logic:present name="transactionFilterVOSession" property="uldTypeCode">
			     <bean:define id="uldCode" name="transactionFilterVOSession" property="uldTypeCode" toScope="page"/>
			     <ihtml:text property="uldTypeCode" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_ULDCODE" value="<%=(String)uldCode%>"/>
				 <div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'uld','1','uldTypeCode','')"  alt="ULD Type LOV"/>
				 </div>
	    		  </logic:present>
			</div>
			<div class="ic-input ic-split-15 ic-label-25">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txntype"/></label>
							  <logic:present name="transactionFilterVOSession"  property="transactionType">
						  <bean:define id="transactionTypeInVO" name="transactionFilterVOSession"  property="transactionType"  toScope="page"/>
						   <ihtml:select property="txnType" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_TXNTYPE" value="<%=(String)transactionTypeInVO%>">
						     	  <%
						     	    String txnTyp = "";
						     	    String txnTypVal = "";
						     	  %>
								  		<html:option value="ALL"><common:message key="combo.select"/></html:option>
						     	         <logic:present name="transactionFilterVOSession"  property="transactionType">
						     	          <bean:define id="transactionTypeInVO" name="transactionFilterVOSession"  property="transactionType"  toScope="page"/>
						     	          <bean:define id="transactionTypeInSess" name="txnTypesSession" toScope="page" />
						     	    

						     	         </logic:present>
						     	        

						     	          <bean:define id="txnTypeColln" name="txnTypesSession" toScope="page" />
						     	              <logic:iterate id="txnTypeVO" name="txnTypeColln" >

						     	                        <bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
						     	       
						     	                        <html:option value="<%=(String)fieldValue %>"><bean:write name="txnTypeVO" property="fieldDescription" /></html:option>
						     	               

						     	             </logic:iterate>
						     	         

			  	     </ihtml:select>
			  	     </logic:present>
			  	     <logic:notPresent name="transactionFilterVOSession"  property="transactionType">

						   <ihtml:select property="txnType" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_TXNTYPE" value="ALL">
								  <%
									String txnTyp = "";
									String txnTypVal = "";
								  %>
										 <logic:present name="transactionFilterVOSession"  property="transactionType">
										  <bean:define id="transactionTypeInVO" name="transactionFilterVOSession"  property="transactionType"  toScope="page"/>
										  <bean:define id="transactionTypeInSess" name="txnTypesSession" toScope="page" />
									<logic:iterate id="txnSessVO" name="transactionTypeInSess" >
										  <bean:define id="fieldValue" name="txnSessVO" property="fieldValue" />
										  <%if(fieldValue.equals(transactionTypeInVO)){%>
									  <bean:define id="fieldDesc" name="txnSessVO" property="fieldDescription" />
									  <%
									  txnTyp = (String)fieldDesc ;
									  txnTypVal = (String)fieldValue ;
									  }%>
									</logic:iterate>
									<%if("ALL".equals(transactionTypeInVO)){
									txnTyp = "" ;
									txnTypVal = "ALL" ;%>
								 <html:option value="ALL"><common:message key="combo.select"/></html:option>
								   <%}else{%>
										 <html:option value="<%=(String)txnTypVal%>"><%=(String)txnTyp%></html:option>
									<%}%>

										 </logic:present>
										 <logic:notPresent name="transactionFilterVOSession"  property="transactionType">
										 <%if(!"ALL".equals(txnTypVal)){%>
									<html:option value="ALL"><common:message key="combo.select"/></html:option>
								<%}%>
										 </logic:notPresent>

										  <bean:define id="txnTypeColln" name="txnTypesSession" toScope="page" />
											  <logic:iterate id="txnTypeVO" name="txnTypeColln" >

														<bean:define id="fieldValue" name="txnTypeVO" property="fieldValue" toScope="page" />
										<%if(!txnTypVal.equals(fieldValue)){%>
														<html:option value="<%=(String)fieldValue %>"><bean:write name="txnTypeVO" property="fieldDescription" /></html:option>
												<%}%>

											 </logic:iterate>
										 <logic:present name="transactionFilterVOSession"  property="transactionType">
										 <%if(!"ALL".equals(txnTypVal)){%>
										<html:option value="ALL"><common:message key="combo.select"/></html:option>
										 <%}%>
										 </logic:present>

					 </ihtml:select>
			  	     </logic:notPresent>
						</div>
			<div class="ic-input ic-split-15">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.partytype"/></label>
				                 
				                    <logic:present name="transactionFilterVOSession"  property="partyType">
						    	    <bean:define id="partyTypeInVO" name="transactionFilterVOSession"  property="partyType" toScope="page"/>
				                    <ihtml:select property="partyType" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_PARTYTYPE" value="<%=(String)partyTypeInVO%>">
						    	    	  <%
						    	    	    String ptyTyp = "";
						    	    	    String ptyTypVal = "";
						    	    	  %><html:option value="ALL"><common:message key="combo.select"/></html:option>
						    	    	         <logic:present name="transactionFilterVOSession"  property="partyType">
						    	    	          <bean:define id="partyTypeInVO" name="transactionFilterVOSession"  property="partyType" toScope="page"/>
						    	    	          <bean:define id="partyTypeInSess" name="partyTypesSession" toScope="page" />
						    	    	    <logic:iterate id="partySessVO" name="partyTypeInSess" >
						    	    	          <bean:define id="fieldValue" name="partySessVO" property="fieldValue" />
						    	    	          <%if(fieldValue.equals(partyTypeInVO)){%>
						    	    	      <bean:define id="fieldDesc" name="partySessVO" property="fieldDescription" />
						    	    	      <%
						    	    	      ptyTyp = (String)fieldDesc ;
						    	    	      ptyTypVal = (String)fieldValue ;
						    	    	      }%>
						    	    	    </logic:iterate>

						    	    	         </logic:present>
						    	    	        
						    	    	          <bean:define id="partyTypeColln" name="partyTypesSession" toScope="page" />
						    	    	              <logic:iterate id="partyTypeVO" name="partyTypeColln" >

						    	    	                        <bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
						    	    	      
						    	    	                        <html:option value="<%=(String)fieldValue %>"><bean:write name="partyTypeVO" property="fieldDescription" /></html:option>
						    	    	               
						    	    	             </logic:iterate>
					    		 </ihtml:select>
					    		  </logic:present>
					    		   <logic:notPresent name="transactionFilterVOSession"  property="partyType">

									<ihtml:select property="partyType" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_PARTYTYPE" value="ALL">
										  <%
											String ptyTyp = "";
											String ptyTypVal = "";
										  %>
												 <logic:present name="transactionFilterVOSession"  property="partyType">
												  <bean:define id="partyTypeInVO" name="transactionFilterVOSession"  property="partyType" toScope="page"/>
												  <bean:define id="partyTypeInSess" name="partyTypesSession" toScope="page" />
											<logic:iterate id="partySessVO" name="partyTypeInSess" >
												  <bean:define id="fieldValue" name="partySessVO" property="fieldValue" />
												  <%if(fieldValue.equals(partyTypeInVO)){%>
											  <bean:define id="fieldDesc" name="partySessVO" property="fieldDescription" />
											  <%
											  ptyTyp = (String)fieldDesc ;
											  ptyTypVal = (String)fieldValue ;
											  }%>
											</logic:iterate>
												  <html:option value="<%=(String)ptyTypVal%>"><%=(String)ptyTyp%></html:option>

												  <html:option value=""><common:message key="combo.select"/></html:option>
												 </logic:present>
												 <logic:notPresent name="transactionFilterVOSession"  property="partyType">
													 <html:option value=""><common:message key="combo.select"/></html:option>
												 </logic:notPresent>
												  <bean:define id="partyTypeColln" name="partyTypesSession" toScope="page" />
													  <logic:iterate id="partyTypeVO" name="partyTypeColln" >

																<bean:define id="fieldValue" name="partyTypeVO" property="fieldValue" toScope="page" />
												<%if(!ptyTypVal.equals(fieldValue)){%>
																<html:option value="<%=(String)fieldValue %>"><bean:write name="partyTypeVO" property="fieldDescription" /></html:option>
														<%}%>
													 </logic:iterate>
								 </ihtml:select>
					    		  </logic:notPresent>
				                    </div>

			<div class="ic-input ic-split-15">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnstatus"/></label>
			<logic:present name="transactionFilterVOSession"  property="transactionStatus">
				  <bean:define id="transactionStatusInVO" name="transactionFilterVOSession"  property="transactionStatus"  toScope="page"/>
		         <ihtml:select property="txnStatus" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATUS" value="<%=(String)transactionStatusInVO%>">
			  <%
			    String txnStatus = "";
			    String txnStatusVal = "";
			  %><html:option value="ALL"><common:message key="combo.select"/></html:option>
				 <logic:present name="transactionFilterVOSession"  property="transactionStatus">
				  <bean:define id="transactionStatusInVO" name="transactionFilterVOSession"  property="transactionStatus"  toScope="page"/>
				  <bean:define id="transactionStatusInSess" name="txnStatusSession" toScope="page" />
			    <logic:iterate id="txnStatusSessVO" name="transactionStatusInSess" >
				  <bean:define id="fieldValue" name="txnStatusSessVO" property="fieldValue" />
				  <%if(fieldValue.equals(transactionStatusInVO)){%>
			      <bean:define id="fieldDesc" name="txnStatusSessVO" property="fieldDescription" />
			      <%
			      	txnStatus = (String)fieldDesc ;
			      	txnStatusVal = (String)fieldValue ;
			      }%>

			    </logic:iterate>
			 
			     
				 </logic:present>
				 

				  <bean:define id="txnStatusColln" name="txnStatusSession" toScope="page" />
				      <logic:iterate id="txnStatusVO" name="txnStatusColln" >

						<bean:define id="fieldValue" name="txnStatusVO" property="fieldValue" toScope="page" />
				
						<html:option value="<%=(String)fieldValue %>"><bean:write name="txnStatusVO" property="fieldDescription" /></html:option>
					

				     </logic:iterate>
				    
  	     		</ihtml:select>
  	     		</logic:present>
  	     		<logic:notPresent name="transactionFilterVOSession"  property="transactionStatus">

				 <ihtml:select property="txnStatus" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATUS" value="ALL">
			  <%
				String txnStatus = "";
				String txnStatusVal = "";
			  %>
				 <logic:present name="transactionFilterVOSession"  property="transactionStatus">
				  <bean:define id="transactionStatusInVO" name="transactionFilterVOSession"  property="transactionStatus"  toScope="page"/>
				  <bean:define id="transactionStatusInSess" name="txnStatusSession" toScope="page" />
				<logic:iterate id="txnStatusSessVO" name="transactionStatusInSess" >
				  <bean:define id="fieldValue" name="txnStatusSessVO" property="fieldValue" />
				  <%if(fieldValue.equals(transactionStatusInVO)){%>
				  <bean:define id="fieldDesc" name="txnStatusSessVO" property="fieldDescription" />
				  <%
					txnStatus = (String)fieldDesc ;
					txnStatusVal = (String)fieldValue ;
				  }%>

				</logic:iterate>
				<%if("ALL".equals(transactionStatusInVO)){
					txnStatus = "" ;
					txnStatusVal = "ALL" ;%>
					 <html:option value="ALL"><common:message key="combo.select"/></html:option>
				 <%}else{%>
				  <html:option value="<%=(String)txnStatusVal%>"><%=(String)txnStatus%></html:option>
				 <%}%>
				 </logic:present>
				 <logic:notPresent name="transactionFilterVOSession"  property="transactionStatus">
				  <%if(!"ALL".equals(txnStatusVal)){%>
					   <html:option value="ALL"><common:message key="combo.select"/></html:option>
				<%}%>
				 </logic:notPresent>

				  <bean:define id="txnStatusColln" name="txnStatusSession" toScope="page" />
					  <logic:iterate id="txnStatusVO" name="txnStatusColln" >

						<bean:define id="fieldValue" name="txnStatusVO" property="fieldValue" toScope="page" />
				<%if(!txnStatusVal.equals(fieldValue)){%>
						<html:option value="<%=(String)fieldValue %>"><bean:write name="txnStatusVO" property="fieldDescription" /></html:option>
					<%}%>

					 </logic:iterate>
					<logic:present name="transactionFilterVOSession"  property="transactionStatus">
					  <%if(!"ALL".equals(txnStatusVal)){%>
						<html:option value="ALL"><common:message key="combo.select"/></html:option>
					  <%}%>
					</logic:present>
				</ihtml:select>
  	     		</logic:notPresent>
                    </div>
			<div class="ic-input ic-split-12 ic-label-35">
					<label>From Party Code</label>
				 					  <logic:notPresent name="transactionFilterVOSession" property="fromPartyCode">
				             <ihtml:text property="fromPartyCode" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_PARTYCODE" value="" />
			  </logic:notPresent>
			  <logic:present name="transactionFilterVOSession" property="fromPartyCode">
				 <bean:define id="ptyCode" name="transactionFilterVOSession" property="fromPartyCode" toScope="page"/>
				 <ihtml:text property="fromPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_PARTYCODE" value="<%=(String)ptyCode%>"/>
				  </logic:present>
				  <div class="lovImg">
					<img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" id="fromairlinelov"  alt="Airline LOV"/>
                  </div>
	    	    </div>
	    	<div class="ic-input ic-split-13 ic-label-45">
					<label>To Party Code</label>
							  <logic:notPresent name="transactionFilterVOSession" property="toPartyCode">
					 <ihtml:text property="toPartyCode" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_PARTYCODE" value=""/>
				  </logic:notPresent>
				  <logic:present name="transactionFilterVOSession" property="toPartyCode">
					 <bean:define id="ptyCode" name="transactionFilterVOSession" property="toPartyCode" toScope="page"/>
					 <ihtml:text property="toPartyCode"  componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_PARTYCODE" value="<%=(String)ptyCode%>"/>
					  </logic:present>
					<div class="lovImg">
					  <img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" id="toairlinelov"  alt="Airline LOV"/>
					</div> 
	    	    </div>
        </div>
           <div class="ic-row">
				<jsp:include page="LoanBorrowDetailsEnquiry_Filter.jsp" />
		  </div>
		 
			<!--jsp:include page="LoanBorrowDetailsEnquiry_Row.jsp"/-->
				











                
            <div class="ic-row">
					<jsp:include page="LoanBorrowDetailsEnquirySplit.jsp"/>
                          
			     
  					



			     
			      

					

				




                          

			     
					

			     
				

			   
					

			   
                        
				</div>
		<div class="ic-row">
			<div class="ic-button-container paddR10">
					 <ihtml:nbutton property="btnList" accesskey="L" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_LIST">
						 <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.list"/>
			 			</ihtml:nbutton>
				 <ihtml:nbutton property="btnClear" accesskey="C"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_CLEAR">
			 			<common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.clear"/>
			 		</ihtml:nbutton>
				</div>
				</div>
 		    </div>
                </div>
               </div>
          <div class="ic-main-container">
		   <a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackListULDTransaction"  href="#"></a> <!--Added by A-7359 for ICRD - 224586 starts here -->
    <jsp:include page="LoanBorrowEnquiry_Details.jsp" />

  </div>
<div class="ic-foot-container paddR5">	

         <div class="ic-row">
		<div class="ic-input ic-split-10 ic-left" id="totalDemmurage">
			<label>
	        <a href="#" class="iCargoLink" id="totalDemmurage">
	      	<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.totalDemurrage" scope="request"/>
			</a>
			</label>
		</div>
		<div class="ic-input ic-split-30 ic-left" id="totalDemmuragediv">
		  <logic:present name="totDemmurage">
			<bean:define id="totalDemmurage" name="totDemmurage" toScope="page"/>
		<ihtml:text componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TOTDEMMURAGE" name="totDemmurage" property="totalDemmurage" value="<%=((new BigDecimal(totalDemmurage.toString())).setScale(2,RoundingMode.UP)).toString() %>" readonly="true" style="text-align:right"/>
		  </logic:present>
		  <logic:present name="baseCurrency">
			<bean:define id="baseCurrency" name="baseCurrency" toScope="page"/>
		<ihtml:text componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_BASECURRENCY" name="baseCurrency" property="baseCurrency" value="<%=(String)baseCurrency%>" readonly="true"/>
		  </logic:present>			  
		</div>
	 </div>
	<div class="ic-row">
		<div class="ic-button-container loan-borrow-btn-area" style="padding-bottom: 10px!important">	
			  <ihtml:nbutton  accesskey="M" property="btnIgnoreReinstateMUC" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_IGNOREREINSTATEMUC">
	      		<common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.ignorereinstatemuc"/>
			  </ihtml:nbutton>
              <ihtml:nbutton  accesskey="I"  property="btnPrintUCR" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_PRINT">
	      		<common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.printucr"/>
	      </ihtml:nbutton>
              <ihtml:nbutton  accesskey="P"  property="btnPrint" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_PRINTUCR">
		   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.print"/>
	      </ihtml:nbutton>
	      <ihtml:nbutton property="btnPrintAll"  accesskey="A"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_PRINTALL">
		  		  <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.printall"/>
	      </ihtml:nbutton>
	      <ihtml:nbutton property="btnCreateTxn"   accesskey="X" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_CREATETXN">
	     	   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.createtxn"/>
	      </ihtml:nbutton>
	      <ihtml:nbutton property="btnModifyTxn"  accesskey="N"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_MODIFYTXN">
		   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.modifytxn"/>
	      </ihtml:nbutton>
              <ihtml:nbutton property="btnReturn"  accesskey="R"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_RETURN">
		   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.return"/>
	      </ihtml:nbutton>
              <ihtml:nbutton property="btnViewUldDetails"  accesskey="V"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_VIEWULDDTLS">
		  <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.viewulddetails"/>
	      </ihtml:nbutton>

	      <!--<ihtml:nbutton property="btnDelete" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_DELETE" tabindex="32">
		  	<common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.delete"/>
	      </ihtml:nbutton>-->
	   
		
		
	       <ihtml:nbutton property="btnGenerateInvoice"  accesskey="G"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_GENINVOICE">
		  		   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.geninvoice"/>
	      </ihtml:nbutton>
	      <ihtml:nbutton property="btnDelete"  accesskey="E" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_DELETE">
		  	<common:message key="uld.defaults.loanBorrowDetailsEnquiry.btn.delete"/>
	      </ihtml:nbutton>
	      <ihtml:nbutton property="btnGenLUC" accesskey="T"  componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_GENLUC">
		   	<common:message key="uld.defaults.loanBorrowDetailsEnquiry.tooltip.genluc"/>
	      </ihtml:nbutton>
		   <ihtml:nbutton property="btnGenMUC" accesskey="U" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_GENMUC">
	      		<common:message key="uld.defaults.loanBorrowDetailsEnquiry.tooltip.genmuc"/>
	      </ihtml:nbutton>
          <ihtml:nbutton property="btnClose"  accesskey="O" componentID="BTN_ULD_DEFAULTS_LOANBORROWENQY_CLOSE">
		   <common:message key="uld.defaults.loanBorrowDetailsEnquiry.tooltip.close"/>
	      </ihtml:nbutton>
		  </div>
              </div>
          </div>
		  </div>
		 </div>

</ihtml:form>
</div>

				
			

		

		
	</body>
</html:html>


