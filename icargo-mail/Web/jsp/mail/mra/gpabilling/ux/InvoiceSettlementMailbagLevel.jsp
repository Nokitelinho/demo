
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Collection" %>	
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>	
<%@ page import="com.ibsplc.icargo.framework.util.currency.Money"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO"%>







<html>

<head>

		
		
		 <title>.:iCargo Lite:. <common:message  bundle="invoicesettlementMailbagLevel" key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.title"/></title>
    <meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/mra/gpabilling/ux/InvoiceSettlementMailbagLevel_Script.jsp" />
   

    
</head>

<body>
	
 
		
 
		   <bean:define id="form"
						 name="InvoiceSettlementMailbagForm"
						 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm"
						 toScope="page" />				 

<business:sessionBean id="KEY_ONETIME_VOS"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel"
	method="get" attribute="oneTimeVOs" />
	
    <business:sessionBean id="KEY_SYSTEM_PAR"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel"
	method="get" attribute="systemparametres" />
	
	<business:sessionBean id="KEY_SELECTED_GPA_SETTLE_VOS" 
	moduleName="mailtracking.mra.gpabilling" 
	screenID="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel" 
	method="get" 
	attribute="selectedGPASettlementVO" />
	
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
	<logic:present name="KEY_SETTLEMENT_HISTOPRYVO">
        <bean:define id="KEY_SETTLEMENT_HISTOPRYVOs" name="KEY_SETTLEMENT_HISTOPRYVO" toScope="page" />
	</logic:present>
		
	<ihtml:form action="/mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.screenload.do" >
	   <ihtml:hidden property="popupFlag"/>
	<ihtml:hidden property="actionFlag" />
	<ihtml:hidden property="lastPageNum" />
	<ihtml:hidden property="displayPage"/>
	<ihtml:hidden property="chequeFlag"/>
	<ihtml:hidden property = "caseClosedArray" /> <!--Added by A-8399 as part of ICRD-305647-->
	<ihtml:hidden property = "deleteArray" />
    <ihtml:hidden property="flagFilter"/>
	<ihtml:hidden property="currentDialogId"/>
	<ihtml:hidden property="currentDialogOption"/>
	<ihtml:hidden property="saveBtnFlg"/>
	<ihtml:hidden property="selectedMailbag"/>
	<ihtml:hidden property="overrideRounding" value="N" />
	<ihtml:hidden property="createButtonFlag"/>
	
	 
	<logic:present name="KEY_SYSTEM_PAR">
		<logic:iterate id="sysPar" name="KEY_SYSTEM_PAR">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
                   		
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
						
						<!--<ihtml:hidden property="roundingValue"  value="<!--%=String.valueOf(parameterValue).toUpperCase() %>" /><!--added by a-7871 for ICRD-214766-->
						<!--<<!--%form.setRoundingValue(String.valueOf(parameterValue).toUpperCase());%> <!--added by a-7871 for ICRD-263559 -->
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>
	
	<div class="main-container footer-height-scroll">
        <header>
		
            <div class="flippane animated fadeInDown" id="header" >
                <div class="pad-md pad-b-3xs">
                    <div class="row">
                        <div class="col-3">
                            <div class="form-group">
                                <label class="form-control-label">
								<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.invoicenumber"/>
								</label>
                                <div	 class="input-group">
							  <ihtml:text tabindex="2"  property="invoiceNumber"
							  styleClass="form-control" maxlength="50" 
							  componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_INVNUM"/>
										
                          <div class="input-group-append">
						
                                        <ihtml:nbutton  id="invnumberlov" property="invoiceNumber" styleClass="btn btn-icon poptrigger">
										<i class="icon ico-expand"></i>
					         </ihtml:nbutton>
                           		  
						  </div>
						 </div>
                        </div>
					   </div>
                       <div class="col">
                            <div class="mar-t-md">
							<ihtml:nbutton styleClass="btn btn-primary poptrigger" id= "btnList" property="btnList" accesskey ="L" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNLIST" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.list" />
					         </ihtml:nbutton>
                            <ihtml:nbutton styleClass="btn btn-default" id= "btnClear" property="btnClear" accesskey ="L" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLEAR" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.clear" />
					                        		</ihtml:nbutton>    
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
			   <div class="col pad-x-md flip-two animated fadeInDown" id="mainDiv"  style="display:none">
			   <div class="row">
			   <div class="col-4 col-md-5 col-sm-6 border-right">
                        <div class="w-100 pad-t-md invoice-edit-sec">
                            <div class="d-flex">
							<logic:present name="KEY_INV_SETTLE_FILTERVO" property="invoiceNumber">
								 <h3><bean:write name="KEY_INV_SETTLE_FILTERVO" property="invoiceNumber"/></h3>
								 </logic:present>
                                <span class="d-block w-10 position-relative mar-l-xs">
                                    <!--i class="icon ico-pencil-rounded-orange mar-t-xs flip-to-one" id="edittab"></i-->
								<a href="#" class="flipper" id="edittab"  flipper="headerForm"><i onClick="showOverViewTab();" class="icon ico-pencil-rounded-orange"></i></a>	
                                </span>
                            </div>
                        </div>
                       
							<logic:present name="KEY_INV_SETTLE_FILTERVO" property="settlementStatus">
							<bean:define id="status" name="KEY_INV_SETTLE_FILTERVO" property="settlementStatus"/>							
							<logic:present name="KEY_ONETIME_VOS">
							<logic:iterate id="oneTimeVO" name="KEY_ONETIME_VOS">
							<bean:define id="parameterCode" name="oneTimeVO" property="key"/>
							<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.paymentstatus">
							 <bean:define id="parameterValues" name="oneTimeVO" property="value"/>
							 <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								 <logic:present name="parameterValue" property="fieldDescription">
								 <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								 <logic:present name="parameterValue" property="fieldValue">
								 <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								 <logic:equal name="fieldValue" value="<%=(String)status%>">
								  <div class="w-100 pad-b-md pad-t-2sm">
                                                    <logic:equal name="fieldValue" value="D">
                            <span class="badge badge-pill light badge-alert fs12 pad-x-xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								   <logic:equal name="fieldValue" value="S">
                            <span class="badge badge-pill light badge-info pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								  <logic:equal name="fieldValue" value="F">
                            <span class="badge badge-pill light badge-active pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								  <logic:equal name="fieldValue" value="O">
                            <span class="badge badge-pill light badge-error pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 
								 </logic:equal> 
								<logic:equal name="fieldValue" value="C">
                            <span class="badge badge-pill light badge-active pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								
								
								
								
								
								 </div>
								 </logic:equal>
								 </logic:present>
								 </logic:present>
								 </logic:iterate>
								 </logic:equal>
								</logic:iterate>
								 </logic:present>
								 </logic:present>
										
                        
                    </div>
                      <div class="col-4 col-md-5 col-sm-5 border-right">
					   <div class="w-100 pad-x-xs pad-t-md">
					    <div class="row">
						   <div class="col-12">
                                    <div class="form-group">
                                      <label class="form-control-data">
                                                                                                        <common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.gpacode" />
                                                                                                    </label>
                                        <logic:present name="KEY_INV_SETTLE_FILTERVO" property="gpaCode">
										<div class="mar-y-xs form-control-label">
										
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="gpaCode"/>
                                                                                                        </div>
                                                                                                    </logic:present>
								 <logic:present name="KEY_INV_SETTLE_FILTERVO" property="gpaName">
                                                                                                        <div class="form-control-label">
										
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="gpaName"/>
								 
								 </div>
								 </logic:present>
                                    </div>
                                </div>
								 <div class="col-12">
                                    <div class="form-group text-center">
                                        <label class="form-control-data mar-b-xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.billingperiod"/></label>
                                         <div class="text-center">
										 <div class="date-range-display">
										<logic:present name="KEY_INV_SETTLE_FILTERVO" property="billingPeriodfrom">
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="billingPeriodfrom"/>
								 </logic:present> </div>
								  <div class="mar-t-2md"><logic:present name="KEY_INV_SETTLE_FILTERVO" property="billingPeriodto">
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="billingPeriodto"/>
								 </logic:present>
								 </div>
								
								 </div>
                                     
                                 </div>
                                </div>
						</div>
						</div>
					  </div>
					  <div class="col-4 col-md-6 col-sm-6 border-right">
                        <div class="w-100 pad-x-xs pad-t-md">
                            <div class="row">
                                <div class="col-15">
                                    <div class="form-group">
                                        <label class="form-control-data"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementrefno"/></label>
                                       <logic:present name="KEY_INV_SETTLE_FILTERVO" property="settlementReferenceNumber">
									   <div class="form-control-label mar-y-xs">
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="settlementReferenceNumber"/>
								 </div>
								 </logic:present>
								 
                                        <span>
										
										<logic:present name="KEY_INV_SETTLE_FILTERVO" property="settlementCurrency">
										
                                            <strong><bean:write name="KEY_INV_SETTLE_FILTERVO" property="settlementCurrency" /></strong>
											
											</logic:present>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-9">
                                    <div class="form-group">
                                        <label class="form-control-data mar-b-xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementDate"/></label>
                                        <div class="form-control-label"><logic:present name="KEY_INV_SETTLE_FILTERVO" property="settlementDate">
								 <bean:write name="KEY_INV_SETTLE_FILTERVO" property="settlementDate"/>
								 </logic:present></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
					 <div class="col" >
                        <div class="w-100 pad-x-xs pad-t-md">
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
									<logic:present name="KEY_INV_SETTLE_FILTERVO" property="totalBlgamt">
                                        <label class="form-control-data mar-b-xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.totalbilledamt"/></label>
                                          
										  <h3><bean:write name="KEY_INV_SETTLE_FILTERVO" property="totalBlgamt"/></h3>
											</logic:present>
										
                                    </div>
                                </div>
                                <div class="col-10">
                                    <div class="form-group">
									 <logic:present name="KEY_INV_SETTLE_FILTERVO" property="totalSettledAmt">
                                        <label class="form-control-data mar-b-xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.totalsettldamt"/></label>
                                           
											<h3>
											<bean:write name="KEY_INV_SETTLE_FILTERVO" property="totalSettledAmt" />
											</h3>
											</logic:present>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
			   </div>
		</div>
        </header>
        <section class="mar-t-md" >
            <div class="card">
                    <div class="card-header card-header-action">
                            <div class="col"> <h4><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.lbl" /></h4></div>
                            <logic:present name="KEY_SELECTED_GPA_SETTLE_VOS" >
							<logic:iterate id="iterator" name="KEY_SELECTED_GPA_SETTLE_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
                               <logic:present name="iterator" property="invoiceSettlementVO">
							   <bean:define id="invoiceSettlementVO" name="iterator" property="invoiceSettlementVO" />
							<div class="mega-pagination">
                      
                 
						<common:enhancedPaginationTag
									pageURL='javascript:submitPage(lastPageNum,displayPage)'
							name="invoiceSettlementVO" id="invoiceSettlementVO"
							exportToExcel="true" exportTableId="div1" exportAction="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do"
							lastPageNum="<%=form.getLastPageNum() %>"
							renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							defaultSelectOption="<%=form.getDefaultPageSize() %>"  
							lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
							/>
					   </div>
					 <div class="card-header-icons">
                        <a href="#" class="btn-icon popover-text filter-sec" data-popover-text="Filter" onclick="showfilterSection();"><i class="icon ico-filter"></i></a>
                       
                        <div style="display:none;" class="filter-panel-wrap"  id="filterSection">
                                            
                                            <div class="w-100 pad-md border-bottom pad-b-2xs">
                                                <div class="row">
                                                    <div class="col-24">
                                                          <div class="form-group text-left">
                                                                    <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.mailbagid"/></label>
																	  <div class="input-group">
                                                                     <ihtml:text id="mailbagId"  property="mailbagId" name="form"
							  styleClass="form-control" maxlength="50" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_MAILBAGID" /></div>
                                                                </div>
                                                    </div>
                                                    <div class="col-24">
                                                            <div class="form-group text-left">
                                                                    <label class="form-control-label"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementstatus"/></label>
																						
								<ihtml:select componentID="CMP_MRA_GPABILLING_SETTLEINV_SETTLESTATUS" id="status" property="invoiceStatus" name="form" styleClass="form-control">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="KEY_ONETIME_VOS">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIME_VOS">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.paymentstatus">
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
                                                </div>
                                            </div>
                                          <div class="w-100 pad-xs pad-x-2md text-right">
                                                       <ihtml:nbutton styleClass="btn btn-link" id="btnClearFilter" property="btnClearFilter" accesskey ="F" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLEARFILTER" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.clearfilter" />
					                        		</ihtml:nbutton>
													<ihtml:nbutton styleClass="btn btn-primary" id="btnApplyFilter" property="btnApplyFilter" accesskey ="A" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNAPPLYFILTER" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.applyfilter" />
					                        		</ihtml:nbutton>
                                                    </div>
                                            
                                    </div>
                    </div>
					</logic:present>
					</logic:iterate>
						</logic:present>
							
                    </div>
						
						<%
						Double totBilledAmount=0.0;
						Double totSettledAmount=0.0;
						Double totDueAmount=0.0;
						Double totCurrSettlementAmount=0.0;
						%>
						
                <div class="card-body p-0">
				
                    <div class="w-100" >
					     <div class="dataTableContainer tablePanel tableonly-container w-100" id="dataTableContainer">
                        <table class="table table-x-md mb-0 w-100" id="div1">
                            <thead>
                                <tr>
                                    <th class="text-center check-box-cell">
                                        <input type="checkbox" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.check)">
                                    </th>
                                    <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.mailbagid"/></th>
                                    <th class="text-center"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.billingIndicator"/></th>
                                    <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.billledAmount"/></th>
                                    <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.internalmca"/></th>
                                    <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.actualbillled"/></th>
                                    <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.totalsettled"/></th>
                                    <th><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.dueamount"/></th>
                                    <th class="w-10 text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.currentsettlement"/></th>
                                    <th class="text-center"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.paymentstatus"/></th>
                                    <th class="text-center"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.caseclosed"/></th>
                                    <th class="toggle-cell">&nbsp;</th>
                                </tr>
                            </thead>
							
                        <tbody>
						
								
							<logic:present name="KEY_SELECTED_GPA_SETTLE_VOS" >
							<logic:iterate id="iterator" name="KEY_SELECTED_GPA_SETTLE_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
                               <logic:present name="iterator" property="invoiceSettlementVO">
							   <bean:define id="invoiceSettlementVO" name="iterator" property="invoiceSettlementVO" />
							    <logic:iterate id ="invoiceSettlementVOs" name="invoiceSettlementVO" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO" indexId="rowCount">
							  
							   <%String actionFlag=form.getActionFlag();%>
							   <%if(("CREATE").equals(actionFlag)||("SEARCH").equals(actionFlag)||("OK").equals(actionFlag)||("SHOWCREATRE").equals(actionFlag)||("SHOWOK").equals(actionFlag)){%>
                                     <tr>
									 <%Boolean chequeFlag=form.isChequeFlag();%>
									<td class="text-center check-box-cell">
                                        <input type="checkbox"  name="check" value="<%=String.valueOf(rowCount)%>">
                                    </td>
                                    <td>
                                        <span class="d-inline-block align-middle">
										<logic:present	name="invoiceSettlementVOs" property="mailbagID">
																					<bean:write name="invoiceSettlementVOs" property="mailbagID"/>
							<%String mailbagid=invoiceSettlementVOs.getMailbagID();%>
							  <ihtml:hidden   property="mailbagIdtable" name="form" value="<%=mailbagid%>"/>
																			</logic:present>
																	
																			</span>

                                    </td>
                                    <td class="text-center">
								<logic:equal  name="invoiceSettlementVOs" property="mcaNumber" value="0">
									<span class="badge badge-pill high-light badge-info pad-x-xs pad-y-2xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.lbl.mailbag" />
																					
																					
																			
									</span></logic:equal>
										<logic:notEqual  name="invoiceSettlementVOs" property="mcaNumber" value="0">
									<span class="badge badge-pill high-light badge-info pad-x-xs pad-y-2xs"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.lbl.mca" />
																					
																					
																			
									</span></logic:notEqual>
									
									</td>
									<td>
									
									<logic:present	name="invoiceSettlementVOs" property="amountInSettlementCurrency">
									 <bean:define id="amountInSettlementCurrencyTmp" type="Money" name="invoiceSettlementVOs" property="amountInSettlementCurrency"/>
									 <%
									 totBilledAmount=totBilledAmount+(Double)amountInSettlementCurrencyTmp.getAmount();
									 %>
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="amountInSettlementCurrency" overrideRounding = "true" />
									</logic:equal>	
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
									
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="amountInSettlementCurrency"  />
									</logic:notEqual>	
								    </logic:present>
									<logic:notPresent name="invoiceSettlementVOs" property="amountInSettlementCurrency">
														&nbsp;
									</logic:notPresent>
									</td>
									
									
									
									
									
                                    <td>
									<logic:present	name="invoiceSettlementVOs" property="mcaNumber">
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="mcaNumber" overrideRounding = "true"  />
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="mcaNumber"   />
									</logic:notEqual>
									</logic:present>
									</td>
                                    <td><logic:present	name="invoiceSettlementVOs" property="actualBilled">
									
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="actualBilled" overrideRounding = "true" />
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="actualBilled" />
									</logic:notEqual>
																
																					
									</logic:present>
									</td>
                                    <td>
									<logic:present	name="invoiceSettlementVOs" property="amountAlreadySettled">
									
									<bean:define id="amountAlreadySettledTmp" type="Money" name="invoiceSettlementVOs" property="amountAlreadySettled"/>
									 <%
									 totSettledAmount=totSettledAmount+(Double)amountAlreadySettledTmp.getAmount();
									 %>
									 
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="amountAlreadySettled" overrideRounding = "true"/>
									</logic:equal>
                                     <logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="amountAlreadySettled" />
									</logic:notEqual>									
									</logic:present>
									</td>
                                    <td>
									<logic:present	name="invoiceSettlementVOs" property="dueAmount">
									<bean:define id="dueAmountTmp" type="Money" name="invoiceSettlementVOs" property="dueAmount"/>
									 <%
									 totDueAmount=totDueAmount+(Double)dueAmountTmp.getAmount();
									 %>
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="dueAmount" overrideRounding = "true" />
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoiceSettlementVOs"  property="dueAmount"  />
									</logic:notEqual>
									</logic:present>
									</td>
                                     <td>
									
									<logic:present	name="invoiceSettlementVOs" property="currentSettlingAmount">
									<bean:define id="currentSettlingAmountTmp" type="Money" name="invoiceSettlementVOs" property="currentSettlingAmount"/>
									 <%
									 totCurrSettlementAmount=totCurrSettlementAmount+(Double)currentSettlingAmountTmp.getAmount();
									 %>
									 </logic:present>
									 
									 
									  <%if(chequeFlag){%>
									 <logic:present	name="invoiceSettlementVOs" property="currentSettlingAmount">
									
									 <logic:equal name="form" property="overrideRounding" value = "Y"> 
								    <logic:notEqual  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
								    <ibusiness:moneyEntry   formatMoney="true" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
					              name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount"  property="currentSettlingAmount" overrideRounding = "true" labelStyleClass="form-control-label" styleClass="form-control" disabled="true" />	
					              </logic:notEqual>
								 </logic:equal>
								 <logic:notEqual name="form" property="overrideRounding" value = "Y">
								
								<logic:notEqual  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
								    <ibusiness:moneyEntry   formatMoney="true" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
					              name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount"  property="currentSettlingAmount"  labelStyleClass="form-control-label" styleClass="form-control" disabled="true" />	
					              </logic:notEqual>
								  
								 </logic:notEqual>
								
					             <logic:equal  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
								 
					              <ibusiness:moneyEntry  formatMoney="false" value="0.0" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
					            name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount" labelStyleClass="form-control-label" styleClass="form-control"   property="currentSettlingAmount" disabled="true" />
					             </logic:equal>
					             
							</logic:present>

							<%}else{%>
							
							 <logic:present	name="invoiceSettlementVOs" property="currentSettlingAmount">
							
								<logic:notEqual  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
								<logic:equal name="form" property="overrideRounding" value = "Y">
								
						<ibusiness:moneyEntry   formatMoney="true" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
					     name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount" overrideRounding = "true"  property="currentSettlingAmount" labelStyleClass="form-control-label" styleClass="form-control" />	
					  </logic:equal>
					  <logic:notEqual name="form" property="overrideRounding" value = "Y">
								
							<logic:notEqual  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
								<ibusiness:moneyEntry   formatMoney="true" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
							  name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount"  property="currentSettlingAmount"  labelStyleClass="form-control-label" styleClass="form-control" disabled="true" />	
							</logic:notEqual>
								  
						 </logic:notEqual>
					  </logic:notEqual>
					   
					   
					   
					   
					    <logic:equal  name="invoiceSettlementVOs" property="currentSettlingAmount" value="0.0">
						 <logic:equal name="form" property="overrideRounding" value = "Y">
						
					    <ibusiness:moneyEntry  formatMoney="false" value="0.0" componentID="CMP_MRA_SETTLEINVMAILBAG_CURRENTSETTLEMNT" id="currentSettlingAmount"
					   name="invoiceSettlementVOs" moneyproperty="currentSettlingAmount" labelStyleClass="form-control-label" styleClass="form-control"  property="currentSettlingAmount" overrideRounding = "true"/>
					   </logic:equal>
					</logic:equal>
																			</logic:present>
																			<%}%>
																			</td>
                                    <td class="text-center">
                                     <logic:present name="invoiceSettlementVOs" property="settlementStatus">
							<bean:define id="status" name="invoiceSettlementVOs" property="settlementStatus"/>							
							<logic:present name="KEY_ONETIME_VOS">
							<logic:iterate id="oneTimeVO" name="KEY_ONETIME_VOS">
							<bean:define id="parameterCode" name="oneTimeVO" property="key"/>
							<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.paymentstatus">
							 <bean:define id="parameterValues" name="oneTimeVO" property="value"/>
							 <logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								 <logic:present name="parameterValue" property="fieldDescription">
								 <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								 <logic:present name="parameterValue" property="fieldValue">
								 <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								 <logic:equal name="fieldValue" value="<%=(String)status%>">
								  <div class="w-100 pad-b-md pad-t-2sm">
                         <logic:equal name="fieldValue" value="D">
                            <span class="badge badge-pill light badge-alert fs12 pad-x-xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								   <logic:equal name="fieldValue" value="S">
                            <span class="badge badge-pill light badge-info pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								  <logic:equal name="fieldValue" value="F">
                            <span class="badge badge-pill light badge-active pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								  <logic:equal name="fieldValue" value="O">
                            <span class="badge badge-pill light badge-error pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								  <logic:equal name="fieldValue" value="C">
                            <span class="badge badge-pill light badge-error pad-x-2xs pad-y-2xs">
								<%= (String)fieldDescription%>
								 </span>
								 </logic:equal> 
								 </div>
								 </logic:equal>
								 </logic:present>
								 </logic:present>
								 </logic:iterate>
								 </logic:equal>
								</logic:iterate>
								 </logic:present>
								 </logic:present>
                                    </td>
                                   <%if  ("Y".equals(((InvoiceSettlementVO)invoiceSettlementVOs).getCaseClosed()) ) {%>
								 <td class="text-center">
                                    <input type="checkbox" name="caseClosed" indexId="rowCount" checked disabled="true">
									<ihtml:hidden property="caseClosedArray" value="true"/>
                                </td>
																							
																							<%}else{%>
																							<td class="text-center">
																							
																							 <input type="checkbox" name="caseClosed" indexId="rowCount" onclick="enablebasecheckbox('<%=rowCount%>')" >
																							<ihtml:hidden property="caseClosedArray" value="false"/>
																							</td>
																							
																<%}%>					
                                    <td class="toggle-cell">
                                            <div class="dropdown" id="el">
                                            <a class="dropdown-toggle" href="#" ><i class="icon ico-v-ellipsis align-middle"></i></a>
											 <div class="dropdown-box">
                                                    <div class="dropdown-menu">
                                                        <a class="dropdown-item poptrigger" href="#"  id="close-webui" onclick="remarksFn(<%=rowCount%>)" >Remarks</a>
                                                        <a class="dropdown-item poptrigger" href="#" onclick="viewHistoryFn(<%=rowCount%>)" >View History</a>
                                                    </div>
													</div>
                                                </div>
                                     </td>
									 
									 </tr>
									
									 <%}%>
                               
                               
                                
                               
                                   </logic:iterate>
                                </logic:present>  
                               </logic:iterate>
                              </logic:present>  
							<%if(totBilledAmount>0){%>
							<tr>
								<td></td><td></td><td></td>
								<td>
								<strong>
								<logic:equal name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay overrideRounding = "true"  showCurrencySymbol="false" value="<%=totBilledAmount.toString()%>" />
								</logic:equal>
								<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay showCurrencySymbol="false" value="<%=totBilledAmount.toString()%>" />
								</logic:notEqual>
								</strong>
								</td>
								<td></td><td></td>
								<td>
								<strong>
								<logic:equal name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay overrideRounding = "true" showCurrencySymbol="false" value="<%=totSettledAmount.toString()%>" />
								</logic:equal>
								<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay showCurrencySymbol="false" value="<%=totSettledAmount.toString()%>" />
								</logic:notEqual>
								</strong>
								</td>
								<td>
								<strong>
								<logic:equal name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay overrideRounding = "true" showCurrencySymbol="false" value="<%=totDueAmount.toString()%>" />
								</logic:equal>
								<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay  showCurrencySymbol="false" value="<%=totDueAmount.toString()%>" />
								</logic:notEqual>
								</strong>
								</td>
								<td>
								<strong>
								<logic:equal name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay showCurrencySymbol="false" overrideRounding = "true" value="<%=totCurrSettlementAmount.toString()%>" />
								</logic:equal>
								<logic:notEqual name="form" property="overrideRounding" value = "Y">
								<ibusiness:moneyDisplay showCurrencySymbol="false" value="<%=totCurrSettlementAmount.toString()%>" />
								</logic:notEqual>
								</strong>
								</td>
								<td></td><td></td><td></td>
							
							
							</tr>
							  <%}%>
                            </tbody>
							
                        </table>
						</div>
						
                    </div>

                </div>
			
			
			
            </div>
			
			
            <div class="card mar-t-md">
                <div class="card-header card-header-action">
                    <div class="col">
                        <h4><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.settlementdetails" /></h4>
                    </div>
                    <div class="card-header-btns">
					 <ihtml:nbutton styleClass="btn btn-primary" id="addLink" property="addLink" accesskey ="F" onclick="addFn()" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNADD" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.add" />
					                        		</ihtml:nbutton>
					<ihtml:nbutton styleClass="btn btn-default" id="deleteLink" property="deleteLink" accesskey ="F" onclick="deleteFn()" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNDELETE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.delete" />
					                        		</ihtml:nbutton>
                        
                    </div>

                </div>
                <div class="card-body p-0">
				
				      <div class="dataTableContainer tablePanel tableonly-container w-100" id="dataTableContainer">
                    <table class="table mb-0 table-hover w-100" id="settlementTable" style="width:100%;">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell">
                                    <input type="checkbox" name="billChkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.rowId)"/>
                                </th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.batchno"/></th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.batchdate"/></th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.bank"/></th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.branch"/></th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.amount"/></th>
                                <th class="text-center"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.delete"/></th>
                                <th class="text-left"><common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.remarks"/></th>
                            </tr>
                        </thead>
						
                        <tbody class="row-line" id="settlementBody">
						<logic:present name="KEY_SELECTED_GPA_SETTLE_VOS" >
							<logic:iterate id="iterator" name="KEY_SELECTED_GPA_SETTLE_VOS" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO">
                               <logic:present name="iterator" property="settlementDetailsVOs">
							   <bean:define id="settlementDetailsVOs" name="iterator" property="settlementDetailsVOs" />
							    <logic:iterate id ="settlementDetailsVO" name="settlementDetailsVOs" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO" indexId="rowCount">
							<logic:notEqual name="settlementDetailsVO" property="operationFlag" value="D">
							
															<ihtml:text name="form" id="stlflag" property="stlOpFlag" value="N"  style="display:none"/>
																	</logic:notEqual>	
						
						 <%String actionFlag=form.getActionFlag();%>
							   <%if(("OK").equals(actionFlag)||("SHOWOK").equals(actionFlag)||("SHOWCREATRE").equals(actionFlag)){%>
							   <logic:notEqual	name="settlementDetailsVO" property="chequeNumber" value="0000">
						   <tr>
						  
						  <td class="text-center">
                                    <input type="checkbox" name="rowId" id="rowId_<%=rowCount%>" value="<%=String.valueOf(rowCount)%>" property="rowId" onclick="toggleTableHeaderCheckbox('rowId',this.form.billChkAll)"/>
                                <ihtml:hidden property="addButton" id="button" value="Y"/>
                                </td>
								
                                <td><logic:present	name="settlementDetailsVO" property="chequeNumber">
								<bean:write name="settlementDetailsVO" property="chequeNumber" />
								
								</logic:present></td>
								 <td><logic:present	name="settlementDetailsVO" property="chequeDate">
							
																			
																					<bean:define id="chequeDate" name="settlementDetailsVO" property="chequeDate" />
																					<%
																										  String cheqDate = TimeConvertor.toStringFormat(((LocalDate)chequeDate).toCalendar(),"dd-MMM-yyyy");
																										%>
																										
																				<%=String.valueOf(cheqDate)%>						
																			
																			</logic:present></td>
								<td><logic:present	name="settlementDetailsVO" property="chequeBank">
																					<bean:write name="settlementDetailsVO" property="chequeBank"/>
																					
																			</logic:present></td>
								<td><logic:present	name="settlementDetailsVO" property="chequeBranch">
																				<bean:write name="settlementDetailsVO" property="chequeBranch"/>
																					
																			</logic:present></td>
																			<% Double TOT = 0.0 ;%>
                               <td><logic:present	name="settlementDetailsVO" property="chequeAmount">
								
								
									 <bean:define id="chequeAmounttMP" type="Money" name="settlementDetailsVO" property="chequeAmount"/>
									 <%
									 TOT = TOT+(Double)chequeAmounttMP.getAmount();
									 %>
									<logic:equal name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay showCurrencySymbol="false" overrideRounding = "true" value="<%=TOT.toString()%>" />
									</logic:equal>	
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
									
									<ibusiness:moneyDisplay showCurrencySymbol="false" value="<%=TOT.toString()%>" />
									</logic:notEqual>	
								   
																			 </logic:present>
																			 
										<logic:notPresent	name="settlementDetailsVO" property="chequeAmount">									 
											0.0					
																				</logic:notPresent>								 
																			 
																			 
																			 
																			 </td>
                               
								<%if  ("Y".equals(((SettlementDetailsVO)settlementDetailsVO).getIsDeleted()) ) {%>
								 <td class="text-center">
                                    <input type="checkbox" name="isDelete" indexId="rowCount" checked disabled="true">
									<ihtml:hidden property="deleteFlag" value="true"/>
                                </td>
																							
																							<%}else if("ADVPY".equals(
								((SettlementDetailsVO)settlementDetailsVO).getLastUpdatedUser())){%>}
								 <td class="text-center">
                                    <input type="checkbox" name="isDelete" indexId="rowCount"  disabled="true">
									<ihtml:hidden property="deleteFlag" value="true"/>
                                </td>
																							<%}else{%>
																							<td  class="text-center">
																							
																							 <input type="checkbox" name="isDelete" indexId="rowCount">
																							 <ihtml:hidden property="deleteFlag" value="false"/>
																							
																							</td>
																							
																							<%}%>
                                     <td><logic:present	name="settlementDetailsVO" property="remarks">
																					<bean:write name="settlementDetailsVO" property="remarks"/>
																		</logic:present></td>	
																		
  </tr>	
</logic:notEqual>  
<%}%>															
						
						  	
						  </logic:iterate>
						 </logic:present>
						 	</logic:iterate>
						</logic:present>
						
							<!--template row-->
							<bean:define id="templateRowCount" value="0"/>
                            <tr template="true"  id="captureRow" style="display:none">
                                <td class="text-center">
                                    <input type="checkbox" name="rowId">
									<ihtml:hidden property="stlOpFlag" value="NOOP"/>
<ihtml:hidden property="addButton" id="button" value="N"/>
                                </td>
                                <td>
                                   <ihtml:text  property="chequeNumber" name="form" indexId="templateRowCount" value="" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_CHEQUENO" styleClass="form-control"/>
                                </td>
                                <td>
                                    
                                     <ibusiness:litecalendar
											labelStyleClass="form-control-label" name="form" styleClass="form-control hasDatepicker" indexId="templateRowCount" id="chequeDate" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_CHQDATE" 
											property="chequeDate" value=""  />  
								
										
								
                                   
                                </td>
                                <td>
                                   <ihtml:text  property="bankName" indexId="templateRowCount" value="" name="form" componentID="CMP_MRA_SETTLEINVMAILBAG_BANK" styleClass="form-control"/>
                                </td>
                                <td>
                                   <ihtml:text  property="branchName" indexId="templateRowCount" value="" name="form" componentID="CMP_MRA_SETTLEINVMAILBAG_BRANCH" styleClass="form-control"/>
                                </td>
                                <td>
								
								<logic:equal name="form" property="overrideRounding" value = "Y">
                                  <ibusiness:moneyEntry indexId="templateRowCount" name="form" componentID="CMP_MRA_SETTLEINVMAILBAG_CHEQUEAMT" id="chequeAmount" value=""
					              moneyproperty="chequeAmount" labelStyleClass="form-control-label" styleClass="form-control" property="chequeAmount" roundMoney="false" />	
                               </logic:equal>
							   <logic:notEqual name="form" property="overrideRounding" value = "Y">
									
									<ibusiness:moneyEntry indexId="templateRowCount" name="form" componentID="CMP_MRA_SETTLEINVMAILBAG_CHEQUEAMT" id="chequeAmount" value=""
					              moneyproperty="chequeAmount" labelStyleClass="form-control-label" styleClass="form-control" property="chequeAmount" roundMoney="false" />	
                              
									</logic:notEqual>
									
							   </td>
                                <td class="text-center">
                                    <input type="checkbox" name="isDelete" indexId="templateRowCount" value="" >
                                </td>
                                <td>
								
								 <ihtml:textarea  property="chequeRemarks" componentID="CMP_MRA_GPABILLING_SETTLEINVMAILBAG_REMARKS" indexId="templateRowCount" name="form" value=""  styleClass="form-control"></ihtml:textarea>
                                   <!-- <textarea class="textarea w-100">Type your Remarks here...</textarea>-->
                                </td>
                            </tr>
							<!--template row-->
                        </tbody>
						
                    </table>
</div>
                </div>
            </div>
        </section>
       <footer>
	   <ihtml:nbutton styleClass="btn btn-primary" id="btnAccDetails" property="btnAccDetails" accesskey ="A" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNACC" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbag.btn.accounting" />
					                        		</ihtml:nbutton>
		<ihtml:nbutton styleClass="btn btn-primary" id="btnSave" property="btnSave" accesskey ="S" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNSAVE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.save" />
					                        		</ihtml:nbutton>
		<ihtml:nbutton styleClass="btn btn-default" id ="btnClose" property="btnClose" accesskey ="X" componentID="CMP_MRA_GPABILLING_SETTLEINV_MAILBAG_BTNCLOSE" >
										<common:message key="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.btn.close" />
					                        		</ihtml:nbutton>											
        
    </footer>
   </div>
  

    </ihtml:form>
	
</body>
</html>