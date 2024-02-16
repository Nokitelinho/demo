<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Collection" %>	
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.currency.Money"%>





<html:html>

<head>
		
	 
		
		
		 <title>.:iCargo Lite:. <common:message  bundle="invoicesettlementMailbagLevel" key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.title"/></title>
    <meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/mra/gpabilling/ux/ViewHistoyPopup_Script.jsp" />
   

    
</head>

<body>
	
 
		   <bean:define id="form"
						 name="InvoiceSettlementMailbagForm"
						 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm"
						 toScope="page" />				 

<business:sessionBean id="KEY_SETTLEMENT_HISTOPRYVO" 
	moduleName="mailtracking.mra.gpabilling" 
	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel" 
	method="get" 
	attribute="invoiceSettlementHistoryVO" />
	
	<business:sessionBean id="KEY_INV_SETTLE_FILTERVO" 
	moduleName="mailtracking.mra.gpabilling" 
	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel" 
	method="get" 
	attribute="invoiceSettlementFilterVO" />
	<logic:present name="KEY_INV_SETTLE_FILTERVO">
        <bean:define id="KEY_INV_SETTLE_FILTERVO" name="KEY_INV_SETTLE_FILTERVO" toScope="page" />
	</logic:present>
	

	   <ihtml:form action="/mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.viewhistory.do">
	   <ihtml:hidden property="actionFlag" />
    <ihtml:hidden property="selectedIndex"/>
    <input type="hidden" name="selectedRow" value="<%=form.getSelectedIndex()[0]%>"/>
    <!--...POPUP - View History...-->
	<%String actionFlag=form.getActionFlag();%>
							   <%if(("VIEW").equals(actionFlag)){%>
    <div title="View History" class="poppane lg container-fluid" id="view_history" style="display:none" >
        <div class="w-100 mar-t-2sm pad-x-2md">
            <div class="row">
                <div class="col-10">
                    <div class="form-group">
					
				
                        <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.mailbagid"/></label>
						<logic:present name="KEY_INV_SETTLE_FILTERVO" property="mailbagID">
						<div class="form-control-data">
								 <strong ><bean:write name="KEY_INV_SETTLE_FILTERVO" property="mailbagID"/></strong></div>
								 </logic:present>
                     
						
                    </div>
                </div>
                <div class="col">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.mcanumber"/></label>
						<logic:present name="KEY_INV_SETTLE_FILTERVO" property="mcaRefnumber">
						<div class="form-control-data">
                        <strong ><bean:write name="KEY_INV_SETTLE_FILTERVO" property="mcaRefnumber"/></strong>
						 </logic:present></div>
						<logic:notPresent name="KEY_INV_SETTLE_FILTERVO" property="mcaRefnumber">
						<div class="form-control-data">
                        <strong >&nbsp;</strong>
						 </logic:notPresent></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="border-top col-24 overflow-hidden" >
 <div class="dataTableContainer tablePanel tableonly-container w-100" id="dataTableContainer">
            <table class="table mb-0 w-100" id="viewhistory">
                <thead>
                    <tr>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementrefno"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.batchno"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.batchdate"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.amount"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementcurrency"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementDate"/></th>
                        <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.remarks"/></th>
                    </tr>
                </thead>
                <tbody>
				<logic:present name="KEY_SETTLEMENT_HISTOPRYVO" >
							<logic:iterate id="iterator" name="KEY_SETTLEMENT_HISTOPRYVO" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO" indexId="rowCount">
                              
                    <tr>
                        <td>
						<logic:present	name="iterator" property="settlementReferenceNumber">
																					<bean:write name="iterator" property="settlementReferenceNumber"/>
																					
																			</logic:present></td>
                        <td><logic:present	name="iterator" property="chequeNumber">
																					<bean:write name="iterator" property="chequeNumber"/>
																					
																			</logic:present></td>
                        <td><logic:present	name="iterator" property="chequeDate">
																					<bean:define id="chequeDate" property="chequeDate" name="iterator" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
				<%=chequeDate.toDisplayDateOnlyFormat()%>
																					
																			</logic:present></td>
                        <td><logic:present	name="iterator" property="chequeAmount">
																				<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  property="chequeAmount" />
																					
																			</logic:present></td>
                        <td><logic:present	name="iterator" property="chequeCurrency">
																					<bean:write name="iterator" property="chequeCurrency"/>
																					
																			</logic:present></td>
                        <td><logic:present	name="iterator" property="settlementDate" >
																						<bean:define id="settlementDate" property="settlementDate" name="iterator" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
				<%=settlementDate.toDisplayDateOnlyFormat()%>
																					
																			</logic:present></td>                        
                        <td><logic:present	name="iterator" property="remarks">
																					<bean:write name="iterator" property="remarks"/>
																					
																			</logic:present></td>
                    </tr>
                      </logic:iterate>
                                </logic:present>  
                               
                </tbody>
				
            </table>
			</div>
        </div>
        <div class="w-100">
            <div class="btn-row">
                <ihtml:nbutton styleClass="btn btn-primary" id="btOK" property="btOK" accesskey ="O" componentID="CMP_MRA_GPABILLING_MAILBAG_SETTLEAVAILPOPUP_BTNOK">
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.ok" />
					         </ihtml:nbutton>
            </div>
        </div>
    </div>
	<%}%>
	 <%if(("REMARK").equals(actionFlag)){%>
	 <div title="Remarks" class="poppane sm"  id="remarks" style="display:none">
        <div class="w-100 pad-md">
          
							<ihtml:textarea  property="remarks"  rows="10" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_REMARKS" styleClass="form-control w-100"
						value="<%=form.getRemarks()[0]%>"></ihtml:textarea>
						 
						 
        </div>
        <div class="w-100">
            <div class="btn-row">
                <ihtml:nbutton styleClass="btn btn-primary" id="btnOK" property="btnOK" accesskey ="O" componentID="CMP_MRA_GPABILLING_MAILBAG_SETTLEAVAILPOPUP_BTNOK">
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.ok" />
					         </ihtml:nbutton>
               <ihtml:nbutton styleClass="btn btn-default" id="btClose" property="btClose" accesskey ="X" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLOSE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.close" />
					                        		</ihtml:nbutton>
            </div>
        </div>
    </div>
   <%}%>
    </ihtml:form>
</body>
			

</html:html>