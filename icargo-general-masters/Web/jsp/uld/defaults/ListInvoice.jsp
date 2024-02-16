<%--
**************************************************************************************
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: ListInvoice.jsp
* Date				: 8-Feb-2006
* Author(s)			: A-2001
**************************************************************************************
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
		
	
<title>
	<common:message bundle="listinvoice" key="uld.defaults.listinvoice.icargoListinvoice" />
</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include src="/js/uld/defaults/ListInvoice_Script.jsp" type="script"/>

</head>
<body style="width:85%;" class="ic-center">
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>

	<business:sessionBean
			id="oneTimeValues"
			moduleName="uld.defaults"
			screenID="uld.defaults.listinvoice"
			method="get"
			attribute="oneTimeValues" />

	<business:sessionBean
			id="LIST_INVOICEDISPLAYVOS"
			moduleName="uld.defaults"
			screenID="uld.defaults.listinvoice"
			method="get"
			attribute="uLDChargingInvoiceVO" />

	<business:sessionBean
			id="LIST_INVOICEFILTERVO"
			moduleName="uld.defaults"
			screenID="uld.defaults.listinvoice"
			method="get"
			attribute="chargingInvoiceFilterVO" />



	<bean:define id="form"
			 name="listInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm"
			 toScope="page" />


	<div class="iCargoContent">
	<ihtml:form action="/uld.defaults.screenloaduldinvoice.do">
		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="airlineBaseCurrency"/>
		<ihtml:hidden property="comboFlag"/>
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="mySearchEnabled" />
		
		
	<div class="ic-content-main">	

	
			<span class="ic-page-title ic-display-none">
				<common:message key="uld.defaults.listinvoice.listinvoice" />
		    </span>
			<div class="ic-head-container">	
				
			<div class="ic-filter-panel">
				<div class="ic-row">
					<h3><common:message key="uld.defaults.listinvoice.searchcriteria" /></h3>
				</div>
				<div class="ic-input-container">
					<div class="ic-row">
						<div class="ic-input ic-split-20">
							<label>	<common:message key="uld.defaults.listinvoice.Invoicerefno" /></label>
							<logic:present name="LIST_INVOICEFILTERVO" property="invoiceRefNumber">
									<ihtml:text name="LIST_INVOICEFILTERVO" property="invoiceRefNumber" componentID="TXT_ULD_DEFAULTS_LISTINVOICE_INVOICEREFNO" maxlength="12"  />

							</logic:present>
							<logic:notPresent name="LIST_INVOICEFILTERVO" property="invoiceRefNumber">
									<ihtml:text property="invoiceRefNumber" componentID="TXT_ULD_DEFAULTS_LISTINVOICE_INVOICEREFNO" value="" maxlength="12"  />
							</logic:notPresent>
							<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png"  height="22" width="22" onclick="displayInvoiceRefNoLov();" alt="Invoice Ref No.LOV"/>
							</div>
						</div>
						
						<div class="ic-input ic-split-40">
							<div class="ic-row">
							 <fieldset class="ic-field-set">
								 <legend>
									 <common:message key="uld.defaults.listinvoice.Invoiceto" />
								 </legend>
								<div class="ic-row">
								<div class="ic-input ic-split-50">
								<label><common:message key="uld.defaults.listinvoice.partytype" /></label>
								
				<logic:present name="LIST_INVOICEFILTERVO" property="partyType">
				<ihtml:select name="LIST_INVOICEFILTERVO" property="partyType" componentID="CMB_ULD_DEFAULTS_LISTINVOICE_PTYTYPE">
                <ihtml:option value="ALL"><common:message key="combo.select"/></ihtml:option>
				<logic:present name="oneTimeValues">
					<logic:iterate id="oneTimeValue" name="oneTimeValues">
					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
						<logic:equal name="parameterCode" value="uld.defaults.PartyType">
						<bean:define id="parameterValues" name="oneTimeValue" property="value" />
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<html:option value="<%=(String)fieldValue%>">
										<%=(String)fieldDescription%>
									</html:option>
								</logic:present>
							</logic:iterate>
						</logic:equal>
					</logic:iterate>
				 </logic:present>
				</ihtml:select>
				</logic:present>
				<logic:notPresent name="LIST_INVOICEFILTERVO" property="partyType">
				<ihtml:select property="partyType" componentID="CMB_ULD_DEFAULTS_LISTINVOICE_PTYTYPE"  value="">
					<ihtml:option value="ALL"><common:message key="combo.select"/></ihtml:option>
					  <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.PartyType">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<html:option value="<%=(String)fieldValue%>">
											<%=(String)fieldDescription%>
										</html:option>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
					  </logic:present>
				</ihtml:select>
				</logic:notPresent>
				</div>
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.listinvoice.partycode" /></label>
				<logic:present name="LIST_INVOICEFILTERVO" property="invoicedToCode">
						<ihtml:text name="LIST_INVOICEFILTERVO" property="invoicedToCode" componentID="TXT_ULD_DEFAULTS_LISTINVOICE_INVOICETO" maxlength="3"  />
				</logic:present>
				<logic:notPresent name="LIST_INVOICEFILTERVO" property="invoicedToCode">
						<ihtml:text property="invoicedToCode" componentID="TXT_ULD_DEFAULTS_LISTINVOICE_INVOICETO" value="" maxlength="30"  />
				</logic:notPresent>
				<div class="lovImg">
					<img src="<%= request.getContextPath()%>/images/lov.png"  tabindex="9"  height="22" width="22" name="partycodeairlinelov" id="partycodeairlinelov" alt="Airline LOV"/>
				</div>
				</div>
					</fieldset>
					</div>
					
							
						</div>
						<div class="ic-input ic-split-5">
						</div>
						<div class="ic-input ic-split-35">
							<label><common:message key="uld.defaults.listinvoice.Txntype" /></label>
							<logic:present name="LIST_INVOICEFILTERVO" property="transactionType">
							<ihtml:select name="LIST_INVOICEFILTERVO" property="transactionType" componentID="CMB_ULD_DEFAULTS_LISTINVOICE_TXNTYPE">
                    		<html:option value="ALL">ALL</html:option>
				  <logic:present name="oneTimeValues">
					<logic:iterate id="oneTimeValue" name="oneTimeValues">
					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
						<logic:equal name="parameterCode" value="uld.defaults.invoicetxntype">
						<bean:define id="parameterValues" name="oneTimeValue" property="value" />
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<html:option value="<%=(String)fieldValue%>">
										<%=(String)fieldDescription%>
									</html:option>
								</logic:present>
							</logic:iterate>
						</logic:equal>
					</logic:iterate>
				  </logic:present>
				</ihtml:select>
				</logic:present>
				<logic:notPresent name="LIST_INVOICEFILTERVO" property="transactionType">
				<ihtml:select property="transactionType" componentID="CMB_ULD_DEFAULTS_LISTINVOICE_TXNTYPE"  value="">
					<html:option value="ALL">ALL</html:option>
					  <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.invoicetxntype">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<html:option value="<%=(String)fieldValue%>">
											<%=(String)fieldDescription%>
										</html:option>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
					  </logic:present>
				</ihtml:select>
				</logic:notPresent>

						</div>
						
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-20 ">
							<label>  <common:message key="uld.defaults.listinvoice.fromdate" /></label>
							<logic:present name="LIST_INVOICEFILTERVO" property="invoicedDateFrom">
				      		<bean:define id="listFilter"
							 name="LIST_INVOICEFILTERVO"  type="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO"
								toScope="page" />
						<%
							String invoicedDateFrom = "";
						   if(listFilter.getInvoicedDateFrom() != null) {
								invoicedDateFrom = TimeConvertor.toStringFormat(
											listFilter.getInvoicedDateFrom().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
							}
						%>
                    				<ibusiness:calendar componentID="TXT_ULD_DEFAULTS_LISTINVOICE_FRMDATE" id="invoicefromdate" type="image" property="invoicedDateFrom" value="<%=invoicedDateFrom%>" />
							</logic:present>
							<logic:notPresent name="LIST_INVOICEFILTERVO" property="invoicedDateFrom">
				      		<ibusiness:calendar componentID="TXT_ULD_DEFAULTS_LISTINVOICE_FRMDATE"
				      			type="image" id="invoicefromdate" property="invoicedDateFrom" value="<%= form.getInvoicedDateFrom() %>" maxlength="11" />

							</logic:notPresent>
						</div>
						
						<div class="ic-input ic-split-25 ">
							<label>   <common:message key="uld.defaults.listinvoice.todate" /></label>
							<logic:present name="LIST_INVOICEFILTERVO" property="invoicedDateTo">
				      	<bean:define id="listFilter"
							 name="LIST_INVOICEFILTERVO"  type="com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO"
								toScope="page" />
						 <%
							String invoicedDateTo = "";
						   if(listFilter.getInvoicedDateTo() != null) {
								invoicedDateTo = TimeConvertor.toStringFormat(
											listFilter.getInvoicedDateTo().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
							}
						 %>
                    				<ibusiness:calendar componentID="TXT_ULD_DEFAULTS_LISTINVOICE_TODATE" id="invoicetodate" type="image" property="invoicedDateTo" value="<%=invoicedDateTo%>" />

							</logic:present>
							<logic:notPresent name="LIST_INVOICEFILTERVO" property="invoicedDateTo">
				      		<ibusiness:calendar componentID="TXT_ULD_DEFAULTS_LISTINVOICE_TODATE"
				      			type="image" id="invoicetodate" property="invoicedDateTo" value="<%= form.getInvoicedDateTo() %>" maxlength="11" />

							</logic:notPresent>
						</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" accesskey="L" componentID="BTN_ULD_DEFAULTS_LISTINVOICE_LIST">
									<common:message key="List" />
								</ihtml:nbutton>
                              	<ihtml:nbutton property="btClear" accesskey="C" componentID="BTN_ULD_DEFAULTS_LISTINVOICE_CLEAR">
								<common:message key="Clear" />
								</ihtml:nbutton>
							</div>
					
					</div>
				
				</div>
			</div>

				
			</div>
				<div class="ic-main-container">
			
		
					<div class="ic-row ">
						<h3><common:message  key="ULD.DEFAULTS.listinvoice" scope="request"/></h3>
					</div>
				
				<div class="ic-row ">
				<logic:present name="LIST_INVOICEDISPLAYVOS">
				<common:paginationTag
					pageURL="uld.defaults.listuldinvoice.do"
					name="LIST_INVOICEDISPLAYVOS"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNum() %>" />
				</logic:present>
				<div class="ic-button-container paddR5">
				<logic:present name="LIST_INVOICEDISPLAYVOS">
				<%

				%>
				<common:paginationTag
					pageURL="javascript:submitPage('lastPageNum','displayPage')"
					name="LIST_INVOICEDISPLAYVOS"
					display="pages"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					lastPageNum="<%=form.getLastPageNum()%>"
					exportToExcel="true"
					exportTableId="invoicedetails"
					exportAction="uld.defaults.listuldinvoice.do"/>
				</logic:present>
				<logic:notPresent name="LIST_INVOICEDISPLAYVOS">
				<%

				%>
					&nbsp;
				</logic:notPresent>
				</div>
				</div>
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:460px">
						<table class="fixed-header-table" id="invoicedetails" >
							 <thead>
                            <tr class="iCargoTableHeadingLeft">
                                <td width="5%" align="center">
								<input type="checkbox" name="masterCheckbox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.selectedRows)"/>
								<span></span></td>

								<td width="10%">
								<common:message key="uld.defaults.listinvoice.Invoicerefno" />
									<span></span></td>

                              	<td width="8%">
                              	<common:message key="uld.defaults.listinvoice.Txntype" />
								<span></span></td>

                              	<td width="12%">
                              	<common:message key="uld.defaults.listinvoice.invoicedate" />
								<span></span></td>

								<td width="12%">
                              	<common:message key="uld.defaults.listinvoice.partytype" />
								<span></span></td>

                             	<td width="15%">
                             	<common:message key="uld.defaults.listinvoice.Invoiceto" />
								<span></span></td>

								<td width="12%">
                             	<common:message key="uld.defaults.listinvoice.waiveramount" />(<bean:write  name="listInvoiceForm" property="airlineBaseCurrency"/>)
								<span></span></td>

								<td width="13%">
                             	<common:message key="uld.defaults.listinvoice.totalamount" />(<bean:write  name="listInvoiceForm" property="airlineBaseCurrency"/>)
								<span></span></td>


                              	<td width="13%">
                              	<common:message key="uld.defaults.listinvoice.netamount" />(<bean:write  name="listInvoiceForm" property="airlineBaseCurrency"/>)
								<span></span></td>

							</tr>
						</thead>		
							<tbody>
						<logic:present name="LIST_INVOICEDISPLAYVOS">
						<logic:iterate id="invoiceListVO" name="LIST_INVOICEDISPLAYVOS"  type="ULDChargingInvoiceVO" indexId="index">
						
						<tr>
							<td class="iCargoTableDataTd ic-center">
							<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)"/>
							</td>
							<td  class="iCargoTableDataTd">
							<logic:present name="invoiceListVO" property="invoiceRefNumber">
					    	<bean:write name="invoiceListVO" property="invoiceRefNumber" />
					    	<input type="hidden" name="invoiceRefNumbers" value="<%=invoiceListVO.getInvoiceRefNumber()%>"/>
							</logic:present>
							</td>
							<td  class="iCargoTableDataTd">
							<logic:present name="invoiceListVO" property="transactionType">
							<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.invoicetxntype">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="invoiceListVO" property="transactionType" value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
												</logic:equal>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
							</logic:present>
							<input type="hidden" name="transactionTypes" value="<%=invoiceListVO.getTransactionType()%>"/>
							</logic:present>
							</td>

							<td  class="iCargoTableDataTd">
							<logic:present name="invoiceListVO" property="invoicedDate">
					    	   <%
								String invoicedDate = "";
								if(invoiceListVO.getInvoicedDate() != null) {
									invoicedDate = TimeConvertor.toStringFormat(
											invoiceListVO.getInvoicedDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
								}
								%>
								<%=invoicedDate%>
								<input type="hidden" name="invoicedDates" value="<%=invoicedDate%>"/>
							</logic:present>
							</td>

							<td  class="iCargoTableDataTd">
							<logic:present name="invoiceListVO" property="partyType">
							<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.PartyType">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="invoiceListVO" property="partyType" value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
												</logic:equal>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
							</logic:present>
							
							</logic:present>
							</td>
                           	<td  class="iCargoTableDataTd">
						    <logic:present name="invoiceListVO" property="invoicedToCode">
							<bean:write name="invoiceListVO" property="invoicedToCode" />
							<input type="hidden" name="invoicedToCodes" value="<%=invoiceListVO.getInvoicedToCode()%>"/>
							</logic:present>
							</td>
							<td  class="iCargoTableDataTd" style="text-align:right">
							<logic:present name="invoiceListVO" property="waiverAmount">
								<bean:write name="invoiceListVO" property="waiverAmount" localeformat="true" />
							</logic:present>
							</td>


							<td  class="iCargoTableDataTd" style="text-align:right">
							<logic:present name="invoiceListVO" property="totalAmount">
								<bean:write name="invoiceListVO" property="totalAmount" localeformat="true"/>
							</logic:present>
							</td>

							<td  class="iCargoTableDataTd" style="text-align:right">
							<logic:present name="invoiceListVO" property="netAmount">
								<bean:write name="invoiceListVO" property="netAmount" localeformat="true"/>
							</logic:present>
							</td>
							<logic:present name="invoiceListVO" property="invoicedToCodeName">
								<input type="hidden" name="invoicedToNames" value="<%=invoiceListVO.getInvoicedToCodeName()%>"/>
							</logic:present>

							<logic:notPresent name="invoiceListVO" property="invoicedToCodeName">
								<input type="hidden" name="invoicedToNames" value="  "/>
							</logic:notPresent>
						</tr>
					
					 </logic:iterate>
					 </logic:present>
						</tbody>
						</table>
					</div>
				</div>
			
			</div>
			<div class="ic-foot-container" >
				<div class="ic-button-container paddR5">
					 <ihtml:nbutton property="btPrint" accesskey="P" componentID="BTN_ULD_DEFAULTS_LISTULD_PRINT">
							<common:message key="Print" />
                     </ihtml:nbutton>
					<ihtml:nbutton property="btView" accesskey="V" componentID="BTN_ULD_DEFAULTS_LISTULD_VIEW">
							<common:message key="View" />
                        	</ihtml:nbutton>
						<ihtml:nbutton property="btClose" accesskey="O" componentID="BTN_ULD_DEFAULTS_LISTINVOICE_CLOSE">
								<common:message key="Close" />
                        	</ihtml:nbutton>
				</div>
			</div>
			</div>
	
		</div>
	</ihtml:form>
	</div>



				
		
	</body>
</html:html>

