<%--
**************************************************************************************
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: InvoiceRefNumLov.jsp
* Date				: 12-Feb-2006
* Author(s)			: A-2001
**************************************************************************************
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		
			
	

	<meta name="decorator" content="popup_panel">
	<common:include src="/js/uld/defaults/ListInvoiceLov_Script.jsp" type="script"/>
</head>
<body >
	
	
	<business:sessionBean
			id="INVOICEREFLOVS"
			moduleName="uld.defaults"
			screenID="uld.defaults.listinvoice"
			method="get"
			attribute="lovVO" />

	<bean:define id="form"
			 name="listInvoiceForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm"
			 toScope="page" />

			 
	<div class="iCargoPopUpContent">	
	<ihtml:form action="/uld.defaults.okinvoicerfnolov.do" styleclass="ic-main-form">
	<ihtml:hidden property="lastLovPageNum"/>
	<ihtml:hidden property="displayLovPage"/>
	<ihtml:hidden property="lovStatusFlag"/>
	<ihtml:hidden property="invoiceRefNumber"/>
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
			
	<div class="ic-content-main"> 
	
			<div class="ic-page-title ic-display-none">
				<common:message  key="uld.defaults.listinvoice.invoiceRefLov" />
		    </div>
	
	<div class="ic-head-container"> 
		
		<div class="ic-input-round-border">
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-input ic-split-50 ic-right">
				<label><common:message key="uld.defaults.listinvoice.Invoicerefno" scope="request" /></label>
				<ihtml:text property="invoiceRefNum"
								componentID="TXT_ULD_DEFAULTS_LISTINVOICE_INVOICEREFNO" maxlength="12"/>
				</div>				
				<div class="ic-button-container">	
							<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_INVOICEREFNO_LIST" >
								<common:message key="uld.defaults.invoicelov.btn.btlist" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_INVOICEREFNO_CLEAR"  >
								<common:message key="uld.defaults.invoicelov.btn.btclear" />
							</ihtml:nbutton>
				</div>				
			</div>
		</div>
		</div>
	</div>
	<div class="ic-main-container"> 
	
		
			<div class="ic-row">
			<logic:present name="INVOICEREFLOVS">
					<common:paginationTag
						pageURL="uld.defaults.invoicerefnolovscreenload.do"
						name="INVOICEREFLOVS"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastLovPageNum() %>" />
			</logic:present>
			<div class="ic-button-container">
			<logic:present name="INVOICEREFLOVS">

							<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								pageURL="javascript:submitLovPage('lastPageNum','displayPage')"
								name="INVOICEREFLOVS"
								display="pages"
								lastPageNum="<%=form.getLastLovPageNum()%>"/>
			</logic:present>
</div>
			
		</div>
		<div class="ic-row">
				<div class="tableContainer" id="div1" style="width:100%;height:100%;">
					<table class="fixed-header-table" id="tabl">
						<thead>
							<tr class="iCargoTableHeading" >
								
								<td width="10%" class="iCargoTableHeader"> <common:message  key="uld.defaults.listinvoice.Invoicerefno" /></td>
								
								
							</tr>
						</thead>
						<tbody>
							
							<logic:present name="INVOICEREFLOVS">
							<logic:iterate id="invoiceListData" name="INVOICEREFLOVS"  type="java.lang.String" indexId="index">
							
							<tr>
								<td  width="6%" align="center"  class="iCargoTableDataTd">
									<input type="checkbox" name="selectedRowsInLov" value="<%=index%>" onclick="checkBoxValidate('selectedRowsInLov',<%=index%>)"/>
								</td>
								<td  class="iCargoTableDataTd" style="width:210px;">
									<logic:present name="invoiceListData">
										<bean:write name="invoiceListData"/>
										<input type="hidden" name="invoiceRefNumbers" value="<%=(String)invoiceListData%>"/>
									</logic:present>
								</td>
						 </tr>
						
						 </logic:iterate>
						 </logic:present>
						
						
						</tbody>
						</table>
					</div>
				</div>
		</div>
		
		
		

	
	
	
	<div class="ic-foot-container" style="height:9%">
		
			<div class="ic-button-container">
						<ihtml:nbutton property="btOk" componentID="BTN_ULD_DEFAULTS_INVOICEREFLOV_OK">
							<common:message  key="Ok" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_INVOICEREFNO_CLOSE">
							<common:message  key="Close" />
						</ihtml:nbutton>
			</div>
	
	</div>
	</div>	
	
</ihtml:form>	
</div>
		
	</body>
</html:html>

