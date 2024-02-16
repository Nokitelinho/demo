<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="transactionFilterVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionFilterVO" />
	<logic:present name="transactionFilterVOSession">
		<bean:define id="transactionFilterVOSession" name="transactionFilterVOSession" toScope="page"/>
	</logic:present>
	
			<div class="ic-input ic-split-50">
				<fieldset class="ic-field-set">
					<legend><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.txndate"/></legend>
                       <div class="ic-input ic-split-50">
					<label>
                           <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.from"/></label>
                          
                 <logic:notPresent name="transactionFilterVOSession" property="strTxnFromDate">
			     <ibusiness:calendar id="txnFromDate"  property="txnFromDate" componentID="ULD_DEFAULTS_LOANBORROWENQY_CALENDAR1" type="image" maxlength="11" value=""/>
			     
				   	<ibusiness:releasetimer  property="txnFrmTime" value="" title="Txn From Time"  type="asTimeComponent" id="txnFrmTime"/>
  					


			   </logic:notPresent>
			   <logic:present name="transactionFilterVOSession" property="strTxnFromDate">
			     <bean:define id="strTxnFromDt" name="transactionFilterVOSession" property="strTxnFromDate" toScope="page"/>
			     <ibusiness:calendar id="txnFromDate"  property="txnFromDate" componentID="ULD_DEFAULTS_LOANBORROWENQY_CALENDAR1" type="image" maxlength="11" value="<%=(String)strTxnFromDt%>"/>

			     <!--Modified by Joji on 19-Feb for NCA Bug fix starts-->
			     
			     <logic:present name="transactionFilterVOSession" property="strTxnFrmTime">
			     <bean:define id="strTxnFrmTime" name="transactionFilterVOSession" property="strTxnFrmTime" />
					<ibusiness:releasetimer  property="txnFrmTime" value="<%=(String)strTxnFrmTime%>" title="Txn From Time"  type="asTimeComponent" id="txnFrmTime"/>
				</logic:present>
				<logic:notPresent name="transactionFilterVOSession" property="strTxnFrmTime">
					<ibusiness:releasetimer  property="txnFrmTime" value="" title="Txn From Time"  type="asTimeComponent" id="txnFrmTime"/>
				</logic:notPresent>
			      
				<!--Modification ends-->

			   </logic:present>
                           <iframe name="gToday:normal:txtfitdte.js" id="gToday:normal:txtfitdte.js" src="../../js/calendar/ipopeng.jsp" scrolling="No" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
                           </div>
                           <div class="ic-input ic-split-40">
					<label>
                           <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.to"/></label>
                                                      <logic:notPresent name="transactionFilterVOSession" property="strTxnToDate">
			     <ibusiness:calendar id="txnToDate" property="txnToDate" componentID="ULD_DEFAULTS_LOANBORROWENQY_TXNTODATE" type="image" maxlength="11" value=""/>
			    	<ibusiness:releasetimer  property="txnToTime"  value="" title="Txn To Time"  type="asTimeComponent" id="txnToTime"/>
					
			   </logic:notPresent>
			   <logic:present name="transactionFilterVOSession" property="strTxnToDate">
			     <bean:define id="strTxnToDt" name="transactionFilterVOSession" property="strTxnToDate" toScope="page"/>
			     <ibusiness:calendar id="txnToDate" property="txnToDate" componentID="ULD_DEFAULTS_LOANBORROWENQY_TXNTODATE"  type="image" maxlength="11" value="<%=(String)strTxnToDt%>"/>

			    <!--Modified by Joji on 19-Feb for NCA Bug fix starts-->
			       <logic:present name="transactionFilterVOSession" property="strTxnToTime">
				 			     <bean:define id="strTxnToTime" name="transactionFilterVOSession" property="strTxnToTime" />
						<ibusiness:releasetimer  property="txnToTime" value="<%=(String)strTxnToTime%>" title="Txn To Time"  type="asTimeComponent" id="txnToTime"/>
					</logic:present>
				<logic:notPresent name="transactionFilterVOSession" property="strTxnToTime">
					<ibusiness:releasetimer  property="txnToTime" value="" title="Txn To Time"  type="asTimeComponent" id="txnToTime"/>
				</logic:notPresent>
				
				<!--Modification ends-->

			   </logic:present>

				</div>


                          
                      </fieldset>
                    </div>
                    <div class="ic-input ic-split-50">
				<fieldset class="ic-field-set">
					<legend><common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.returndetails"/></legend>
					<div class="ic-row">
						<div class="ic-input ic-split-15" >
							<ihtml:radio property="leaseOrReturnFlg" value="R"/>
							<label  class="ic-inline">		
								<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.return"/>
							</label>
						</div>
						<div class="ic-input ic-split-15" >
							<ihtml:radio property="leaseOrReturnFlg"  value="L" />
							<label  class="ic-inline">
								<common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.lease"/>
							</label>
						</div>
					</div>
                        <div class="ic-input ic-split-50">
					<label>
                          <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.from"/></label>
                          <logic:notPresent name="transactionFilterVOSession" property="strReturnFromDate">
			     <ibusiness:calendar id="returnFromDate" property="returnFromDate" type="image" maxlength="11" value="" componentID="ULD_DEFAULTS_LOANBORROWENQY_RTNDTLSFROMDATE"/>

			     
				 	<ibusiness:releasetimer  property="returnFrmTime" value="" title="Return From Time"  type="asTimeComponent" id="returnFrmTime"/>
					
			   </logic:notPresent>
			   <logic:present name="transactionFilterVOSession" property="strReturnFromDate">
			     <bean:define id="strReturnFromDt" name="transactionFilterVOSession" property="strReturnFromDate" toScope="page"/>
			     <ibusiness:calendar id="returnFromDate" property="returnFromDate" type="image" maxlength="11" value="<%=(String)strReturnFromDt%>" componentID="ULD_DEFAULTS_LOANBORROWENQY_RTNDTLSFROMDATE"/>

			     <!--Modified by Joji on 19-Feb for NCA Bug fix starts-->
			     
			      <logic:present name="transactionFilterVOSession" property="strRetFrmTime">
					<bean:define id="strRetFrmTime" name="transactionFilterVOSession" property="strRetFrmTime" />
					<ibusiness:releasetimer  property="returnFrmTime" value="<%=(String)strRetFrmTime%>" title="Return From Time"  type="asTimeComponent" id="returnFrmTime"/>
					</logic:present>
				<logic:notPresent name="transactionFilterVOSession" property="strRetFrmTime">
					<ibusiness:releasetimer  property="returnFrmTime" value="" title="Return From Time"  type="asTimeComponent" id="returnFrmTime"/>
				</logic:notPresent>
				
			      <!--Modification ends-->

			   </logic:present>
                           <iframe name="gToday:normal:txtfitdte3.js" id="gToday:normal:txtfitdte3.js" src="../../js/calendar/ipopeng.jsp" scrolling="No" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
                          </div>
                         <div class="ic-input ic-split-40">
					<label>
                          <common:message key="uld.defaults.loanBorrowDetailsEnquiry.lbl.to"/></label>
                          <logic:notPresent name="transactionFilterVOSession" property="strReturnToDate">
			     <ibusiness:calendar property="returnToDate" id="returnToDate" type="image" maxlength="11" value="" componentID="ULD_DEFAULTS_LOANBORROWENQY_RTNDTLSTODATE"/>
			   
					 <ibusiness:releasetimer  property="returnToTime" value="" title="Return  To Time"  type="asTimeComponent" id="returnToTime"/>
					
			   </logic:notPresent>
			   <logic:present name="transactionFilterVOSession" property="strReturnToDate">
			     <bean:define id="strReturnToDt" name="transactionFilterVOSession" property="strReturnToDate" toScope="page"/>
			     <ibusiness:calendar property="returnToDate" id="returnToDate" type="image" maxlength="11" value="<%=(String)strReturnToDt%>"/>

			     <logic:present name="transactionFilterVOSession" property="strRetToTime">
			     <bean:define id="strRetToTime" name="transactionFilterVOSession" property="strRetToTime" />
					<ibusiness:releasetimer  property="returnToTime" value="<%=(String)strRetToTime%>" title="Return  To Time"  type="asTimeComponent" id="returnToTime"/>
			   </logic:present>
			   	<logic:notPresent name="transactionFilterVOSession" property="strRetToTime">
				     <ibusiness:releasetimer  property="returnToTime" value="" title="Return  To Time"  type="asTimeComponent" id="returnToTime"/>
			   	</logic:notPresent>
			   
			   	<!--Modification ends-->
			   </logic:present>
                            <iframe width="174" name="gToday:normal:txtfltdate4.js" id="gToday:normal:txtfltdate4.js" src="../../js/calendar/ipopeng.jsp" scrolling="No" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
                          </div>
                        
                      </fieldset>
                    </div>
				