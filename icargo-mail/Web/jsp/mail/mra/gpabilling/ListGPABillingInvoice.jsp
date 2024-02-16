<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ListGPABillingInvoice.jsp
* Date                 	 : 4-June-2008
* Author(s)              : A-3434
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm" %>

<bean:define id="form"
		 name="ListGPABillingInvoiceForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm"
		 toScope="page" />



<html:html>
<head>

		<%@ include file="/jsp/includes/customcss.jsp" %>


	<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpabilling.ListGPABillingInvoice.title" /></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/gpabilling/ListGPABillingInvoice_Script.jsp" />
</head>

	<body>


		<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<business:sessionBean id="onetimemap"
		  	moduleName="mailtracking.mra.gpabilling"
		  	screenID="mailtracking.mra.gpabilling.listgpabillinginvoice"
			method="get" attribute="OneTimeMap" />
				<business:sessionBean id="KEY_SYSPARAMETERS"
			moduleName="mailtracking.mra.gpabilling"
			screenID="mailtracking.mra.gpabilling.listgpabillinginvoice"
			method="get" attribute="systemparametres" />
	<div id="mainDiv" class="iCargoContent ic-masterbg" style="overflow:auto;width:100%;height:100%">
			<ihtml:form action="/mailtracking.mra.gpabilling.listgpabillingInvoice.onScreenLoad.do">
				<ihtml:hidden property="selectedRow" />
				<ihtml:hidden property="failureFlag" />
				<ihtml:hidden property="gpaHKGPostConfirmFinalize" />
				<html:hidden property="displayPage" />
                <html:hidden property="lastPageNumber" />
				<input type="hidden" name="currentDialogOption" />
				<input type="hidden" name="currentDialogId" />
				<ihtml:hidden property="invoiceFinalizedStatus" />
				<input type="hidden" name="mySearchEnabled" />
				<ihtml:hidden property="overrideRounding" value="N" />

				<%--Added by A-4810 for CR ICRD-13639 starts --%>
				<%boolean indigoflg = false;%>
				<common:xgroup>
					<common:xsubgroup id="INDIGO_SPECIFIC">
					 <% indigoflg = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<!--Added by A-4809 for CR ICRD-42106 turkish specific starts-->
				<%boolean turkishFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="TURKISH_SPECIFIC">
					 <% turkishFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<%boolean singaporeFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="SINGAPORE_SPECIFIC">
						<% singaporeFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<logic:present name="KEY_SYSPARAMETERS">
					<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
						<bean:define id="parameterCode" name="sysPar" property="key"/>
						<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
							<bean:define id="parameterValue" name="sysPar" property="value"/>
						<logic:notEqual name="parameterValue" value="N">
							<%form.setOverrideRounding("Y");%>
						</logic:notEqual>
						</logic:equal>
					</logic:iterate>
				</logic:present>
				<!--Added by A-4809 for CR ICRD-42106 turkish specific ends-->
				<business:sessionBean id="vocollection" moduleName="mailtracking.mra.gpabilling" screenID="mailtracking.mra.gpabilling.listgpabillinginvoice" method="get" attribute="cN51SummaryVOs" />

				<div class="ic-content-main">
					<span class="ic-page-title ic-display-none">
     						<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.pagetitle" /> </td>
  					</span>
						<div class="ic-head-container">
							<div class="ic-filter-panel">
						<div class="ic-row">
							<h4><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.heading" /> </h4>
						</div>
								<div class="ic-input-container">
									<div class="ic-row">

										</div>
											<div class="ic-row">
											<div class="ic-row">
												<div class="ic-input ic-split-18 ic-label-35 ">
													<label>
										<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.gpacode"/>
                	    							</label>
                							<ihtml:text property="gpacode" componentID="CMP_MRA_LISTGPABILLING_GPACODE"
                							readonly="false" maxlength="6" tabindex="1"/>
				          				<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" height="22" width="22" alt="" /></div>
												</div>
												<div class="ic-input ic-split-18 ic-label-35 ic-mandatory">
													<label>
											<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.from" />
													</label>

                							<ibusiness:calendar
												property="fromDate"
												componentID="CMP_MRA_LISTGPABILLING_FROMDATE"
												type="image"
												id="fromDate"
												maxlength="11"
												tabindex="2"
												value="<%=form.getFromDate()%>"/>
												</div>
												<div class="ic-input ic-split-18 ic-label-35 ic-mandatory">
													<label>
											<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.to" />
													</label>


                							<ibusiness:calendar componentID="CMP_MRA_LISTGPABILLING_TODATE"
								    		property="toDate" type="image" id="toDate"
								    		maxlength="11"
											tabindex="3"
						         	   	 	value="<%=form.getToDate()%>"/>
												</div>
												<div class="ic-input ic-split-18 ic-label-35 ">
													<label>
								<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.invoiceno" />
													</label>
                							<ihtml:text property="invoiceNo" componentID="CMP_MRA_LISTGPABILLING_INVOICENO"
                							readonly="false" maxlength="14" tabindex="4" /></td>
											<div class= "lovImg">
														<img src="<%=request.getContextPath()%>/images/lov.png" id="invnumberlov" height="22" width="22" /></td>
												</div>
											</div>
												<div class="ic-input ic-split-18 ic-label-35 ">
													<label>
										<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.invoicestatus" />
													</label>

												<ihtml:select componentID="CMP_MRA_LISTGPABILLING_INVOICESTATUS" property="invoiceStatus" tabindex="5">

													<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>

													<logic:present name="onetimemap">
														<logic:iterate id="oneTimeValue" name="onetimemap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<logic:equal name="parameterCode" value="mra.gpabilling.invoicestatus">
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
											<div class="ic-row">
												<div class="ic-input ic-split-18 ic-label-35 ">
													<label>
														<common:message key="mail.mra.gpabilling.listgpabillinginvoice.lbl.passfilename"/>
													</label>
													<ihtml:text property="passFileName" componentID="CMP_MRA_LISTGPABILLING_PASSFILENAME"
														readonly="false" tabindex="6"/>
														<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="passFileNameLov" height="22" width="22" alt="" /></div>														
												</div>	
												<div class="ic-input ic-split-18 ic-label-35">
													<label>
														<common:message key="mail.mra.gpabilling.listgpabillinginvoice.lbl.periodno"/>
													</label>
													<ihtml:text property="periodNumber" componentID="CMP_MRA_LISTGPABILLING_PERIODNO"
														readonly="false" tabindex="7"/>
												</div>	
												<div class="ic-input inline_filedset marginT20">
														<ihtml:checkbox name="form" property="checkPASS" componentID="CMP_MRA_LISTGPABILLING_PASS" tabindex="8"/>
														<label>			
															<common:message key="mail.mra.gpabilling.listgpabillinginvoice.lbl.pass" />
														</label>
												</div>													
												<div class="ic-button-container">

													<ihtml:nbutton property="btnList" tabindex="9" componentID="CMP_MRA_GPABILLING_LIST" accesskey="L" >
														<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.list" />
													</ihtml:nbutton>

													<ihtml:nbutton property="btnClear" tabindex="10" componentID="CMP_MRA_GPABILLING_CLEAR" accesskey="C" >
														<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.clear" />
													</ihtml:nbutton>


	    										</div>
											</div>
										</div>
								</div>
							</div>
						</div>

								<div class="ic-main-container">
									<div class="ic-row">
										<div class="ic-bold-label paddL10">
											<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.heading1" /></td>
										</div>
									</div>


											<logic:present name="vocollection">
									<div class="ic-row">
										<div class="ic-col-60 paddL5">

										  <logic:present name="vocollection">
									   <bean:define id="billingInvoicePage" name="vocollection"/>
									   <common:paginationTag pageURL="mailtracking.mra.gpabilling.listgpabillingInvoice.list.do"
										name="billingInvoicePage"
										display="label"
										linkStyleClass="iCargoLink"
                                        disabledLinkStyleClass="iCargoLink"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNumber() %>"/>
								  </logic:present>
								 <logic:notPresent name="billingInvoicePage">
										 &nbsp;
								 </logic:notPresent>
										</div>
										<div class="ic-button-container paddR5">
							<logic:present name="billingInvoicePage">
								<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
								name="billingInvoicePage"
								display="pages"
								linkStyleClass="iCargoLink"
                                disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getLastPageNumber()%>"
								exportToExcel="true"
							    exportTableId="listGPABillInvoice"
							    exportAction = "mailtracking.mra.gpabilling.listgpabillingInvoice.list.do"/>
							</logic:present>
							<logic:notPresent name="billingInvoicePage">
								&nbsp;
							</logic:notPresent>
										</div>
									</div>
					   </logic:present>
									 <div class="ic-row">
									 <!-- Modified by A-8236 for ICRD-251677-->
										<div  class="tableContainer table-border-solid" id="div1" style="height:680px;">
											<table class="fixed-header-table" id="listGPABillInvoice" style="height:45px">

															<thead>
													            <tr>
														<td width="3%" rowspan="2"> </td>
														<td width="2%" rowspan="2">
																	  <input type="checkbox" name="check"><span></span></td>

														 <td   rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.invoiceno" /> <span></span></td>
														  <td width="9%"  rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.invoicedate" /><span></span></td>
																	  <td colspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.billingperiod"/><span></span></td>
														  <td  width="5%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.gpacode"/><span></span></td>
														  <td  rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.gpaname"/><span></span></td>
														   <td  width="5%" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.currency"/><span></span></td>

														  <td  rowspan="2"> <common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.billamount"/><span></span></td>

														  <td  rowspan="2"><common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.invoicestatus" /><span></span></td>
														  <td  width="14%" rowspan="2"><common:message key="mail.mra.gpabilling.listgpabillinginvoice.lbl.passfilename" /><span></span></td>


																</tr>
															         <tr>

																	   <td width="9%"> <common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.from" /> </td>
																	   <td width="9%"> <common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.to" /> </td>
																	 </tr>
																</thead>
																<tbody>
																<% int rowNum=0;%>
																	<logic:present name="vocollection">
																	<logic:iterate id="cn51summary" name="vocollection" indexId="rowId">
																	 
																	 <% int subindex=0;%>
																	<tr class="iCargoTableDataRow1">
																	<!--Added by A-8527 for ICRD-234294 Starts--> 
																	<td id="p1" class="iCargoTableDataTd">
																	<a href="#" onclick="toggleRows(this)" class="ic-tree-table-expand  tier1"></a>
																</td>	
																	<!--Added by A-8527 for ICRD-234294 Ends--> 
																	<td >
														<div>
																	<input type="checkbox" name="rowCount" value="<%=String.valueOf(rowId)%>" onclick="checkBoxValidate('rowCount',<%=rowId%>)"/>
																	</div></td>
														<td > <div > <common:write name="cn51summary" property="invoiceNumber"/> </div> </td>
														<td width="5%"><div>

																	<logic:present  name="cn51summary" property="billedDate">

																	<bean:define id="billedDate" name="cn51summary" property="billedDate" />
																	<%

																	String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)billedDate).toCalendar(),"dd-MMM-yyyy");
																	%>
																	<%=assignedLocalDate%>

																	</logic:present>

																	</div> </td>
																	<td > &nbsp;<common:write name="cn51summary" property="billingPeriodFrom"/> </td>
																	<td > &nbsp;<common:write name="cn51summary" property="billingPeriodTo"/>  </td>
														<td ><div><common:write name="cn51summary" property="gpaCode"/> </div> </td>
																	<td ><div align = "center"><common:write name="cn51summary" property="gpaName"/> </div> </td>
																	<td class="iCargoTableDataTd">
								    									 <center>
																	<common:write name="cn51summary" property="billingCurrencyCode"/> </td>
																	<td style="text-align:right">
																	<logic:equal name="form" property="overrideRounding" value = "Y">
																		<logic:equal name="cn51summary" property="billingCurrencyCode" value = "KRW">
																			<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51summary" moneyproperty="totalAmountInBillingCurrency" property="totalAmountInContractCurrency" overrideRounding = "true" />
																		</logic:equal>
																		<logic:notEqual name="cn51summary" property="billingCurrencyCode" value = "KRW">
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51summary" moneyproperty="totalAmountInBillingCurrency" property="totalAmountInContractCurrency" overrideRounding = "true" />
																		</logic:notEqual>
																	</logic:equal>
																	<logic:notEqual name="form" property="overrideRounding" value = "Y">
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51summary" moneyproperty="totalAmountInBillingCurrency" property="totalAmountInContractCurrency" />
																	</logic:notEqual>
																	</td>
																	<td ><div align = "center">
																	<logic:present name="cn51summary" property="invoiceStatusDisplay">
																	<common:write name="cn51summary" property="invoiceStatusDisplay"/>
																	<bean:define id="invStatus" name="cn51summary" property="invoiceStatusDisplay"/>
																	<ihtml:hidden property="invStatus" value="<%=String.valueOf(invStatus)%>" />
																	</logic:present>
																	</div> </td>
																		<td style="text-align:center">
																			<logic:present name="cn51summary" property="passFileName">
																				<common:write name="cn51summary" property="passFileName"/>
																			</logic:present>
																		</td>	
																	</tr>
																	<!--Added by A-8527 for ICRD-234294 Starts-->
															<div class="tier1">
															<logic:present name="cn51summary" property="rebillInvoiceDetails">		
																<logic:iterate id="rebillInvoiceDetailsId" name="cn51summary" property="rebillInvoiceDetails" indexId="IndexId">
																<tr id="<%=String.valueOf(rowId)%>-<%=String.valueOf(IndexId)%>" class="ic-table-row-sub">
															<td class="iCargoTableDataTd">
																	</td>
																	
															<td class="iCargoTableDataTd">
																
																<input type="checkbox" name="rowId" style="width:25px;" value="<%=String.valueOf(rowNum)%>~<%=String.valueOf(subindex)%>" onclick="enablebasecheckbox('<%=rowNum%>','<%=subindex%>')" />
																</td>
														<td > <div > <common:write name="rebillInvoiceDetailsId" property="rebillInvoiceNumber"/> </div> </td>
														<td width="5%"><div>
																	<logic:present  name="rebillInvoiceDetailsId" property="billedDate">
																	<bean:define id="billedDate" name="rebillInvoiceDetailsId" property="billedDate" />
																	<%
																	String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)billedDate).toCalendar(),"dd-MMM-yyyy");
																	%>
																	<%=assignedLocalDate%>
																	</logic:present>
																	</div> </td>
																	<td > &nbsp;<common:write name="rebillInvoiceDetailsId" property="billingPeriodFrom"/> </td>
																	<td > &nbsp;<common:write name="rebillInvoiceDetailsId" property="billingPeriodTo"/>  </td>
														<td ><div><common:write name="rebillInvoiceDetailsId" property="gpaCode"/> </div> </td>
																	<td ><div align = "center"><common:write name="rebillInvoiceDetailsId" property="gpaName"/> </div> </td>
																	<td class="iCargoTableDataTd">
								    									 <center>
																	<common:write name="rebillInvoiceDetailsId" property="billingCurrencyCode"/> </td>
																	<td style="text-align:right">
																	<logic:equal name="form" property="overrideRounding" value = "Y">
																		<logic:equal name="rebillInvoiceDetailsId" property="billingCurrencyCode" value = "KRW">
																			<ibusiness:moneyDisplay showCurrencySymbol="false" name="rebillInvoiceDetailsId" moneyproperty="totalAmountInContractCurrency" property="totalAmountInContractCurrency" overrideRounding = "true" />
																		</logic:equal>
																		<logic:notEqual name="rebillInvoiceDetailsId" property="billingCurrencyCode" value = "KRW">
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="rebillInvoiceDetailsId" moneyproperty="totalAmountInContractCurrency" property="totalAmountInContractCurrency" overrideRounding = "true" />
																		</logic:notEqual>
																	</logic:equal>
																	<logic:notEqual name="form" property="overrideRounding" value = "Y">
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="rebillInvoiceDetailsId" moneyproperty="totalAmountInContractCurrency" property="totalAmountInContractCurrency" />
																	</logic:notEqual>																	
																	</td>
																	<td ><div align = "center">
																	<logic:present name="cn51summary" property="invoiceStatusDisplay">
																	<common:write name="rebillInvoiceDetailsId" property="invoiceStatusDisplay"/> 
																	<bean:define id="invStatus" name="cn51summary" property="invoiceStatusDisplay"/>
																	<ihtml:hidden property="invStatus" value="<%=String.valueOf(invStatus)%>" />
																	</logic:present>
																	</div> </td>
																	</tr>
																	<% subindex++;%>
																	</logic:iterate>
																</logic:present> 
                                                                 <!--Added by A-8527 for ICRD-234294 Starts Ends--> 
															</div>
															<% rowNum++;%>
																	</logic:iterate>
																	</logic:present>

																	</tbody>

													          </table>
													       </div>
				                    </div>
				            </div>

							<div class="ic-foot-container">
								<div class="ic-row">
									<div class="ic-button-container paddR5">
																				<ihtml:nbutton property="btnDownloadPASS" tabindex="11" componentID="CMP_MRA_GPABILLING_DOWNLOAD_PASSFILE" accesskey="D" >
																				<common:message key="mail.mra.gpabilling.listgpabillinginvoice.lbl.downloadpassfile" />
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnEmailInvoice" tabindex="12" componentID="CMP_MRA_GPABILLING_EMAILINVOICE" accesskey="E" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.emailinvoice" />
																				</ihtml:nbutton>
																				<% if(turkishFlag){%>
																				<ihtml:nbutton property="btnPrintAllTK" tabindex="12" componentID="CMP_MRA_GPABILLING_PRINTALL" accesskey="A" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.printall" />
																				</ihtml:nbutton>
																				<%}%>
																				<% if(singaporeFlag){%>
																				<ihtml:nbutton property="btnPrintAllSQ" tabindex="12" componentID="CMP_MRA_GPABILLING_PRINTALL" accesskey="A" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.printall" />
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnPrintSQ" tabindex="12" componentID="CMP_MRA_GPABILLING_PRINT" accesskey="P" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.print" />
																				</ihtml:nbutton>
																				<%}%>
																				<%if(!turkishFlag && !singaporeFlag ){%>
																				<ihtml:nbutton property="btnPrintAll" tabindex="12" componentID="CMP_MRA_GPABILLING_PRINTALL" accesskey="A" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.printall" />
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnPrint" tabindex="12" componentID="CMP_MRA_GPABILLING_PRINT" accesskey="P" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.print" />
																				</ihtml:nbutton>
																				<%}%>
																				
																				<ihtml:nbutton property="btnView" tabindex="13" componentID="CMP_MRA_GPABLG_LISTCN51_VIEW" accesskey="N" >
																				<common:message key="mailtracking.mra.gpabilling.listcn51.lbl.view"/>
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnViewOutstndRecvbles" tabindex="14" componentID="CMP_MRA_GPABLG_OUTSTND_RECVBLES" accesskey="R" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.lbl.outstadingreceivebles"/>
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnListAccDetails" tabindex="15" componentID="CMP_MRA_GPABILLING_LISTACCDETAILS" accesskey="I">
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.listaccdetails" />
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnWithdrawInv" tabindex="16" componentID="CMP_MRA_GPABILLING_WITHDRAWINV" accesskey="W" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.withdrawinv" />
																				</ihtml:nbutton>
																				<ihtml:nbutton property="btnFinalizeInv" tabindex="17" componentID="CMP_MRA_GPABILLING_FINALIZEINV" accesskey="F" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.finalizeinv" />
																				</ihtml:nbutton>
                                                                             <!-- <% if(!indigoflg){%>
																				<ihtml:nbutton property="btnInvoiceDetails" tabindex="12" componentID="CMP_MRA_GPABILLING_INVOICEDETAILS" accesskey="V" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.invoicedetails" />
																				</ihtml:nbutton>
                                                                               <%}%> -->
																				<ihtml:nbutton property="btnClose" tabindex="18" componentID="CMP_MRA_GPABILLING_CLOSE" accesskey="O" >
																				<common:message key="mailtracking.mra.gpabilling.listgpabillinginvoice.button.close" />

																				</ihtml:nbutton>


																</div>
								</div>
							</div>
						</div>


		</ihtml:form>
	</div>

	</body>
		</html:html>
