<%@ include file="/jsp/includes/tlds.jsp" %>

<business:sessionBean id="transactionFilterVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="transactionFilterVO" />
	<logic:present name="transactionFilterVOSession">
		<bean:define id="transactionFilterVOSession" name="transactionFilterVOSession" toScope="page"/>
	</logic:present>



<business:sessionBean id="mucStatusSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowdetailsenquiry" method="get" attribute="mUCStatus" />
	<logic:present name="mucStatusSession">
		<bean:define id="mucStatusSession" name="mucStatusSession" toScope="page"/>
	</logic:present>


			<div class="ic-input ic-split-25 ic-label-35">
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
                <div class="ic-input ic-split-50">
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
				