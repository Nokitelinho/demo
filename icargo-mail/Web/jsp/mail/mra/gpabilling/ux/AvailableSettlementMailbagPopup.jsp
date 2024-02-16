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
	
	 
		
		
                                                    <title>.:iCargo Lite:.
                                                        <common:message bundle="invoicesettlementMailbagLevel" key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.title" />
                                                    </title>
    <meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/mra/gpabilling/ux/AvailableSettlementMalbagPopUp_Scipt.jsp" />
   

    
</head>

<body class="overflow-hidden">
		
 
		   <bean:define id="form"
						 name="InvoiceSettlementMailbagForm"
						 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm"
						 toScope="page" />				 

<business:sessionBean id="KEY_SELECTED_GPA_SETTLE_VOS" 
	moduleName="mailtracking.mra.gpabilling" 
	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel" 
	method="get" 
	attribute="selectedGPASettlementVO" />
	
	<business:sessionBean id="KEY_INV_SETTLE_FILTERVO" 
	moduleName="mailtracking.mra.gpabilling" 
	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel" 
	method="get" 
	attribute="invoiceSettlementFilterVO" />
	<logic:present name="KEY_INV_SETTLE_FILTERVO">
        <bean:define id="KEY_INV_SETTLE_FILTERVO" name="KEY_INV_SETTLE_FILTERVO" toScope="page" />
	</logic:present>
	       	
	   <ihtml:form action="/mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.openPopup.do">
	  <ihtml:hidden property="actionFlag" />
	   <ihtml:hidden property="popupFlag"/>
	    <ihtml:hidden property="flagFilter"/>
		<ihtml:hidden property="createButtonFlag"/>
    <div title="Available Settlements" class="poppane lg" id="available_settlements">
        <div class="w-100 mar-t-2sm pad-x-2md">
            <div class="row">
                <div class="col-7">
                    <div class="form-group">
					<logic:present name="KEY_INV_SETTLE_FILTERVO" property="invoiceNumber">
                                                                            <label class="form-control-label">
                                                                                <common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.invoicenumber" />
                                                                            </label>
                        <strong class="d-block"><bean:write name="KEY_INV_SETTLE_FILTERVO" property="invoiceNumber"/></strong>
						</logic:present>
						
								
                    </div>
                </div>
            </div>
        </div>
        <div class="pop-up-table-wrapper">
		 <div class="card border-bottom-0">
		  <div class="card-body p-0">
		  <div class="tabs" id="tabs" style="display:block">
		 <ul class="nav nav-tabs nav-tabs-large">
                            <li class="nav-item">
                                <a class="nav-link" href="#tabs-1" ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.lbl.newsettlement"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"  href="#tabs-2"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.lbl.availablesettlement"/></a>
                            </li>
                        </ul>
						  <div id="tabs-1" >
                            <div class="pad-md">
                                <div class="row">
                                    <div class="col-4">
                                        <div class="form-group">
                                            <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementrefno"/></label>
                                             <ihtml:text  id="settlementReferenceNumber" property="settlementReferenceNumber" name="form"
							  styleClass="form-control" maxlength="50" 
							  componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_SETLREFNO"/>
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementDate"/></label>
                                            <div class="input-group">
											<ibusiness:litecalendar
											labelStyleClass="form-control-label" styleClass="form-control hasDatepicker" id="settlementDate" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_SETLDATE" 
											property="settlementDate" value=""  />
												
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                    <div class="form-group">
                                        <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.currency" /></label>
                                        <div class="input-group">
                                            <ihtml:text  name="form"  id="settlementCurrency" property="settlementCurrency"
							  styleClass="form-control" maxlength="50" 
							  componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_SETLCURENCY"/>
							     <div class="input-group-append">
						
                                        <ihtml:nbutton styleClass="btn btn-icon poptrigger" id="currencylov" property="settlementCurrency">
										<i class="icon ico-expand"></i>
					         </ihtml:nbutton>
                                           
                                        
                                    
									  
						   </div>
                                           
                                        </div>
                                    </div>
                        </div>
                                </div>
                            
                            </div>
                            <div class="pad-md text-right border-top">
                               	<ihtml:nbutton styleClass="btn btn-primary flipper" id="btCreate" property="btCreate" accesskey ="C" componentID="CMP_MRA_GPABILLING_MAILBAG_SETTLEAVAILPOPUP_BTNCREATE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.create" />
					         </ihtml:nbutton>
                               <ihtml:nbutton styleClass="btn btn-default" id="btClose" property="btClose" accesskey ="X" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLOSE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.close" />
					                        		</ihtml:nbutton>
                            </div>
                        </div>
					
                      <div id="tabs-2" >
					  
					 <div class="dataTableContainer tablePanel tableonly-container w-100" id="dataTableContainer" >
            <table class="table mb-0 w-100" id="container">
			  
                <thead>
                    <tr>
                        <th  class="text-center check-box-cell">&nbsp;</th>
                        <th ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementid"/></th>
						 <th ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementDate"/></th>
                        <th ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.chequeno"/></th>
                        <th ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.amount"/></th>
                        <th ><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.currency"/></th>
                    </tr>
                </thead>
                                           <tbody>
                                                                                        <%String actionFlag=form.getFlagFilter();%>

                                                                                            <%if(("Y").equals(actionFlag)){%>
                                                                                                <logic:present name="KEY_SELECTED_GPA_SETTLE_VOS">
                                                                                                    <logic:iterate id="iterator" name="KEY_SELECTED_GPA_SETTLE_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
                                                                                                        <logic:present name="iterator" property="settlementDetailsVOs">
                                                                                                            <bean:define id="settlementDetailsColl" name="iterator" property="settlementDetailsVOs" />
                                                                                                           <logic:iterate id="settlementDetailsVOs" name="settlementDetailsColl" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO" indexId="rowCount">

                                                                       <logic:notEqual name="settlementDetailsVOs" property="chequeNumber" value="">
                                                                                                                <tr>
                                                                                                                    <td class="text-center check-box-cell">
                                                                                                                        <input type="checkbox" name="check" onclick="toggleTableHeaderCheckbox('check',this.form.masterCheckbox)" />
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                        <logic:present name="settlementDetailsVOs" property="settlementId">
                                                                                                                            <bean:write name="settlementDetailsVOs" property="settlementId" />

                                                                                                                        </logic:present>
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                        <logic:present name="settlementDetailsVOs" property="chequeDate">
                                                                                                                            <bean:define id="chequeDate" property="chequeDate" name="settlementDetailsVOs" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                                                                                                                            <%=chequeDate.toDisplayDateOnlyFormat()%>

                                                                                                                        </logic:present>
                                                                                                                    </td>
                                                                                                                    <td>
																													 
                                                                                                                        <logic:present name="settlementDetailsVOs" property="chequeNumber">
																														<logic:notEqual name="settlementDetailsVOs" property="chequeNumber" value="0000">

                                                                                                                            <bean:write name="settlementDetailsVOs" property="chequeNumber" />
                                                                                                                           </logic:notEqual>
                                                                                                                        </logic:present>

                                                                                                                    </td>
																														
                                                                                                                    <td>
                                                                                                                        <logic:present name="settlementDetailsVOs" property="chequeAmount">
                                                                                                                          
																															<logic:equal name="form" property="overrideRounding" value = "Y">
																															<ibusiness:moneyDisplay showCurrencySymbol="false" name="settlementDetailsVOs" roundMoney="false"   property="chequeAmount" overrideRounding = "true" />
																															</logic:equal>	
																															<logic:notEqual name="form" property="overrideRounding" value = "Y">
																															
																															<ibusiness:moneyDisplay showCurrencySymbol="false" name="settlementDetailsVOs"  property="chequeAmount" roundMoney="false" />
																															</logic:notEqual>	
																															</logic:present>
																															<logic:notPresent name="settlementDetailsVOs" property="chequeAmount">
																																				0.0;
																															</logic:notPresent>
																															
                                                                                                                    </td>
                                                                                                                    <td>
                                                                                                                        <logic:present name="settlementDetailsVOs" property="chequeCurrency">
                                                                                                                            <bean:write name="settlementDetailsVOs" property="chequeCurrency" />

                                                                                                                        </logic:present>
                                                                                                                    </td>

                                                                                                                </tr>

 </logic:notEqual>
                                                                                                            </logic:iterate>
                                                                                                        </logic:present>
                                                                                                    </logic:iterate>
                                                                                                </logic:present>
                                                                                    </tbody>
																					 <%}%>
				
            </table>
		
        </div>
			
            <div class="pad-md text-right border-top">
			<ihtml:nbutton styleClass="btn btn-primary" id="btOK" property="btOK" accesskey ="O" componentID="CMP_MRA_GPABILLING_MAILBAG_SETTLEAVAILPOPUP_BTNOK">
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.ok" />
					         </ihtml:nbutton>
                            <ihtml:nbutton styleClass="btn btn-default" id="btClose" property="btClose" accesskey ="X" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLOSE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.close" />
					                        		</ihtml:nbutton>	 
             </div>
            </div>
			</div>
			</div>
    </div>
  
   </div>
   </div>
   
    </ihtml:form>
	
</body>
			

</html:html>