<%--

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: LoanBorrowDetailsEnquiry_Filter.jsp
* Date				: 31-jul-08
* Author(s)			: a3353

 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>


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

	<business:sessionBean id="mucStatusSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="mUCStatus" />
	<logic:present name="mucStatusSession">
		<bean:define id="mucStatusSession" name="mucStatusSession" toScope="page"/>
				  </logic:present>


          
		<div class="ic-input ic-split-15">
						

					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txnstation"/></label>
					            <logic:notPresent name="transactionFilterVOSession" property="transactionStationCode">
							     <ihtml:text property="txnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATION" value=""/>
								 <div class="lovImg">
							     <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].txnStation.value,'Airport','1','txnStation','')"  alt="Airport LOV"/>
								  </div>
						      </logic:notPresent>
						      <logic:present name="transactionFilterVOSession" property="transactionStationCode">
						        <bean:define id="txnStationCode" name="transactionFilterVOSession" property="transactionStationCode" toScope="page"/>
							     <ihtml:text property="txnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_TXNSTATION" value="<%=(String)txnStationCode%>"/>
								 <div class="lovImg">
							     <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].txnStation.value,'Airport','1','txnStation','')"  alt="Airport LOV"/>
								 </div> 
							  </logic:present>
                    </div>


		<div class="ic-input ic-split-15">
					<label>Rtn. Airport</label>
							   <logic:notPresent name="transactionFilterVOSession" property="returnedStationCode">
							  								     <ihtml:text property="returnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_RETSTATION" value=""/>
																  <div class="lovImg">
							  								      <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].returnStation.value,'Airport','1','returnStation','')"  alt="Airport LOV"/>
																  </div>
							  								  </logic:notPresent>
							  								  <logic:present name="transactionFilterVOSession" property="returnedStationCode">
							  								     <bean:define id="returnStationCode" name="transactionFilterVOSession" property="returnedStationCode" toScope="page"/>
							  								     <ihtml:text property="returnStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_RETSTATION" value="<%=(String)returnStationCode%>"/>
																 <div class="lovImg">
							  								     <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].returnStation.value,'Airport','1','returnStation','')"  alt="Airport LOV"/>
																 </div>
						    		</logic:present>

							</div>



                <div class="ic-input ic-split-15">
					<label>Acc. Code</label>
                    <logic:present name="transactionFilterVOSession"  property="accessoryCode">
					<bean:define id="accessoryCodeInVO" name="transactionFilterVOSession"  property="accessoryCode"  toScope="page"/>
                    <ihtml:select property="accessoryCode" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_ACCESSCODE" value="<%=(String)accessoryCodeInVO%>">
							  	  <%
							  	    String accessCode = "";
							  	    String accessCodeVal = "";
							  	  %>
							  	         <logic:present name="transactionFilterVOSession"  property="accessoryCode">
							  	          <bean:define id="accessoryCodeInVO" name="transactionFilterVOSession"  property="accessoryCode"  toScope="page"/>
							  	          <bean:define id="accCodesInSess" name="accCodesSession" toScope="page" />
							  	    <logic:iterate id="accCodesVO" name="accCodesInSess" >
							  	          <bean:define id="fieldValue" name="accCodesVO" property="fieldValue" />
							  	          <%if(fieldValue.equals(accessoryCodeInVO)){%>
							  	      <bean:define id="fieldDesc" name="accCodesVO" property="fieldDescription" />
							  	      <%
							  	      accessCode = (String)fieldDesc ;
							  	      accessCodeVal = (String)fieldValue ;
							  	      }%>
							  	    </logic:iterate>
							  	          <html:option value="<%=(String)accessCodeVal%>"><%=(String)accessCode%></html:option>
							  	          <html:option value=""><common:message key="combo.select"/></html:option>
							  	         </logic:present>
							  	         <logic:notPresent name="transactionFilterVOSession"  property="accessoryCode">
							  	         <html:option value=""><common:message key="combo.select"/></html:option>
							  	         </logic:notPresent>
							  	          <bean:define id="accCodesColln" name="accCodesSession" toScope="page" />
							  	              <logic:iterate id="accCodesVO" name="accCodesColln" >

							  	                        <bean:define id="fieldValue" name="accCodesVO" property="fieldValue" toScope="page" />
							  	        <%if(!accessCodeVal.equals(fieldValue)){%>
							  	                        <html:option value="<%=(String)fieldValue %>"><bean:write name="accCodesVO" property="fieldDescription" /></html:option>
							  	                <%}%>
							  	             </logic:iterate>
				  	     		</ihtml:select>
				  	     		</logic:present>
				  	     		<logic:notPresent name="transactionFilterVOSession"  property="accessoryCode">
								<ihtml:select property="accessoryCode" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_ACCESSCODE" value="ALL">
											  <%
												String accessCode = "";
												String accessCodeVal = "";
											  %>
													 <logic:present name="transactionFilterVOSession"  property="accessoryCode">
													  <bean:define id="accessoryCodeInVO" name="transactionFilterVOSession"  property="accessoryCode"  toScope="page"/>
													  <bean:define id="accCodesInSess" name="accCodesSession" toScope="page" />
												<logic:iterate id="accCodesVO" name="accCodesInSess" >
													  <bean:define id="fieldValue" name="accCodesVO" property="fieldValue" />
													  <%if(fieldValue.equals(accessoryCodeInVO)){%>
												  <bean:define id="fieldDesc" name="accCodesVO" property="fieldDescription" />
												  <%
												  accessCode = (String)fieldDesc ;
												  accessCodeVal = (String)fieldValue ;
												  }%>
												</logic:iterate>
													  <html:option value="<%=(String)accessCodeVal%>"><%=(String)accessCode%></html:option>
													  <html:option value=""><common:message key="combo.select"/></html:option>
													 </logic:present>
													 <logic:notPresent name="transactionFilterVOSession"  property="accessoryCode">
													 <html:option value=""><common:message key="combo.select"/></html:option>
													 </logic:notPresent>
													  <bean:define id="accCodesColln" name="accCodesSession" toScope="page" />
														  <logic:iterate id="accCodesVO" name="accCodesColln" >

																	<bean:define id="fieldValue" name="accCodesVO" property="fieldValue" toScope="page" />
													<%if(!accessCodeVal.equals(fieldValue)){%>
																	<html:option value="<%=(String)fieldValue %>"><bean:write name="accCodesVO" property="fieldDescription" /></html:option>
															<%}%>
														 </logic:iterate>
											</ihtml:select>
				  	     		</logic:notPresent>

				  	     		</div>
		<div class="ic-input ic-split-15 ic-label-35">
					<label>MUC Status</label>
				<logic:present name="transactionFilterVOSession"  property="mucStatus">
					<bean:define id="mucStatusInVO" name="transactionFilterVOSession"  property="mucStatus"  toScope="page"/>
					<ihtml:select property="mucStatus" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_MUCSTATUS" value="<%=(String)mucStatusInVO%>">
					<%
						String mucStatus = "";
						String mucStatusVal = "";
					%>
					<logic:present name="transactionFilterVOSession"  property="mucStatus">
						<bean:define id="mucStatusInVO" name="transactionFilterVOSession"  property="mucStatus"  toScope="page"/>
						<bean:define id="mucStatusInSess" name="mucStatusSession" toScope="page" />
						<logic:iterate id="mucStatusSessVO" name="mucStatusInSess" >
							<bean:define id="fieldValue" name="mucStatusSessVO" property="fieldValue" />
							<%if(fieldValue.equals(mucStatusInVO)){%>
							<bean:define id="fieldDesc" name="mucStatusSessVO" property="fieldDescription" />
							<%
								mucStatus = (String)fieldDesc ;
								mucStatusVal = (String)fieldValue ;
							}%>
						</logic:iterate>
						<%if("ALL".equals(mucStatusInVO)){
							mucStatus = "" ;
							mucStatusVal = "ALL" ;%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}else{%>
							<html:option value="<%=(String)mucStatusVal%>"><%=(String)mucStatus%></html:option>
						<%}%>
					</logic:present>
					<logic:notPresent name="transactionFilterVOSession"  property="mucStatus">
						<%if(!"ALL".equals(mucStatusVal)){%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}%>
					</logic:notPresent>
					<bean:define id="mucStatusColln" name="mucStatusSession" toScope="page" />
						<logic:iterate id="mucStatusVO" name="mucStatusColln" >
							<bean:define id="fieldValue" name="mucStatusVO" property="fieldValue" toScope="page" />
							<%if(!mucStatusVal.equals(fieldValue)){%>
								<html:option value="<%=(String)fieldValue %>"><bean:write name="mucStatusVO" property="fieldDescription" /></html:option>
							<%}%>
						</logic:iterate>
				    <logic:present name="transactionFilterVOSession"  property="mucStatus">
						<%if(!"ALL".equals(mucStatusVal)){%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}%>
				    </logic:present>
					</ihtml:select>
				</logic:present>
  	     		<logic:notPresent name="transactionFilterVOSession"  property="mucStatus">
					<ihtml:select property="mucStatus" componentID="CMB_ULD_DEFAULTS_LOANBORROWENQY_MUCSTATUS" value="ALL">
					<%
						String mucStatus = "";
						String mucStatusVal = "";
					%>
					<logic:present name="transactionFilterVOSession"  property="mucStatus">
					<bean:define id="mucStatusInVO" name="transactionFilterVOSession"  property="mucStatus"  toScope="page"/>
					<bean:define id="mucStatusInSess" name="mucStatusSession" toScope="page" />
						<logic:iterate id="mucStatusSessVO" name="mucStatusInSess" >
						<bean:define id="fieldValue" name="mucStatusSessVO" property="fieldValue" />
						<%if(fieldValue.equals(mucStatusInVO)){%>
							<bean:define id="fieldDesc" name="mucStatusSessVO" property="fieldDescription" />
							<%
								mucStatus = (String)fieldDesc ;
								mucStatusVal = (String)fieldValue ;
							}%>
						</logic:iterate>
						<%if("ALL".equals(mucStatusInVO)){
							mucStatus = "" ;
							mucStatusVal = "ALL" ;%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}else{%>
							<html:option value="<%=(String)mucStatusVal%>"><%=(String)mucStatus%></html:option>
						<%}%>
					</logic:present>
					<logic:notPresent name="transactionFilterVOSession"  property="mucStatus">
						<%if(!"ALL".equals(mucStatusVal)){%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}%>
					</logic:notPresent>
					<bean:define id="mucStatusColln" name="mucStatusSession" toScope="page" />
						<logic:iterate id="mucStatusVO" name="mucStatusColln" >
						<bean:define id="fieldValue" name="mucStatusVO" property="fieldValue" toScope="page" />
						<%if(!mucStatusVal.equals(fieldValue)){%>
							<html:option value="<%=(String)fieldValue %>"><bean:write name="mucStatusVO" property="fieldDescription" /></html:option>
						<%}%>
						</logic:iterate>
					<logic:present name="transactionFilterVOSession"  property="mucStatus">
						<%if(!"ALL".equals(mucStatusVal)){%>
							<html:option value="ALL"><common:message key="combo.select"/></html:option>
						<%}%>
					</logic:present>
					</ihtml:select>
  	     		</logic:notPresent>
                </div>
        <div class="ic-input ic-split-15">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.controlreceiptno"/></label>
					<logic:present name="transactionFilterVOSession" property="prefixControlReceiptNo">
						<bean:define id="precrn" name="transactionFilterVOSession" property="prefixControlReceiptNo" toScope="page"/>
						<ihtml:text property="controlReceiptNoPrefix" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOFIRST" value="<%=(String)precrn%>"/>
					</logic:present>
					<logic:notPresent name="transactionFilterVOSession" property="prefixControlReceiptNo">
						<ihtml:text property="controlReceiptNoPrefix" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOFIRST" value=""/>
					</logic:notPresent>
					-
					<logic:present name="transactionFilterVOSession" property="midControlReceiptNo">
						<bean:define id="midcrn" name="transactionFilterVOSession" property="midControlReceiptNo" toScope="page"/>
						<ihtml:text property="controlReceiptNoMid" maxlength="1" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOMIDDLE" value="<%=(String)midcrn%>"/>
					</logic:present>
					<logic:notPresent name="transactionFilterVOSession" property="midControlReceiptNo">
						<ihtml:text property="controlReceiptNoMid" maxlength="1" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOMIDDLE" value=""/>
					</logic:notPresent>
					<logic:present name="transactionFilterVOSession" property="controlReceiptNo">
						<bean:define id="sufcrn" name="transactionFilterVOSession" property="controlReceiptNo" toScope="page"/>
						<ihtml:text property="controlReceiptNo" maxlength="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOLAST" value="<%=(String)sufcrn%>"/>
					</logic:present>
					<logic:notPresent name="transactionFilterVOSession" property="controlReceiptNo">
						<ihtml:text property="controlReceiptNo" maxlength="7" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_CONTROLRECEIPTNOLAST" value=""/>
					</logic:notPresent>
				  	     		</div>
				<div class="ic-input ic-split-15">
					<label><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.desstation"/></label>
					            <logic:notPresent name="transactionFilterVOSession" property="desStation">
							     <ihtml:text property="desStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_DESSTATION" value=""/>
								 <div class="lovImg">
							     <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].desStation.value,'Airport','1','desStation','')"  alt="Airport LOV"/>
								  </div>
						      </logic:notPresent>
						      <logic:present name="transactionFilterVOSession" property="desStation">
						        <bean:define id="desStationCode" name="transactionFilterVOSession" property="desStation" toScope="page"/>
							     <ihtml:text property="desStation" maxlength="3" componentID="TXT_ULD_DEFAULTS_LOANBORROWENQY_DESSTATION" value="<%=(String)desStationCode%>"/>
								 <div class="lovImg">
							     <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].desStation.value,'Airport','1','desStation','')"  alt="Airport LOV"/>
								 </div> 
							  </logic:present>
				  	     		</div>

