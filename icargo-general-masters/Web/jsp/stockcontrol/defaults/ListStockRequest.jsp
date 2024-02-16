
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  STK - Stock Control
* File Name				:  ListStockRequest.jsp
* Date					:  16-Sep-2015
* Author(s)				:  Ramesh Chandra Pradhan

*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.ibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>

<html:html>
<head>

	
	<bean:define id="form"
		name="ListStockRequestForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm"
		toScope="page" />
	<title><common:message bundle="<%=form.getBundle()%>" key="liststockrequest.title" scope="request"/></title>
	<common:include type="script" src="/js/stockcontrol/defaults/ListStockRequest_Script.jsp" />
	<meta name="decorator" content="mainpanelrestyledui">
</head>
<body id="bodyStyle">
	

	<business:sessionBean id="oneTimeStatus"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.liststockrequest" method="get"
			attribute="oneTimeStatus"/>
      
	<business:sessionBean id="partnerAirlines"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.liststockrequest"
			method="get"
			attribute="partnerAirlines"/>
			
	<business:sessionBean id="options"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.liststockrequest" method="get"
			attribute="dynamicOptionList"/>
			
	<business:sessionBean id="stockHoldersFromSession"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.liststockrequest"
			method="get"
			attribute="prioritizedStockHolders" />
	<business:sessionBean id="pageStockRequestVO"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.liststockrequest" method="get"
			attribute="pageStockRequestVO"/>

	<logic:present name="pageStockRequestVO">
		<bean:define id="pageList" name="pageStockRequestVO" toScope="page"/>
	</logic:present>
	
	
<div class="iCargoContent ic-masterbg" id="pageDiv" style="width:100%;overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.screenloadliststockrequest.do ">
		<%
		 ListStockRequestForm listStockRequestForm = (ListStockRequestForm)request.getAttribute("ListStockRequestForm");
		%>
		
		<html:hidden property="canCancel" />
		<html:hidden property="displayPage" />
		<html:hidden property="lastPageNumber" />
		<html:hidden property="checkList" />
		<html:hidden property="oneRow" />
		<html:hidden property="partnerPrefix" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="mySearchEnabled" />
		<ihtml:hidden property="fromStockRequestList" />

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="liststockrequest.ListStockRequests" />
		</span>
		<div class="ic-head-container">
			<!--<div class="ic-row" style="height:10px">
				<h3><common:message key="liststockrequest.SearchCriteria" /></h3>
			</div> -->
			<div class="ic-filter-panel">
                <div class="ic-row" style="height:10px"><h3><common:message key="liststockrequest.SearchCriteria" /></h3></div>
				<div class="ic-row">
					<div class="ic-col-100">
						<div class="ic-input-container">
							<div class="ic-row">	
								<div class="ic-input ic-split-15 ic-label-22">
									<label>
										<common:message key="liststockrequest.ReqRefNo" />
									</label>
									<ihtml:text property="reqRefNo"
											componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_REQUESTREFERENCENUMBER"
											maxlength="20"	/>
								</div>
								<div class="ic-input ic-split-15 ic-label-25">
									<label>
										<common:message key="liststockrequest.Status" />
									</label>
									<ihtml:select property="status" componentID="CMB_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_STATUS" >
										<html:option value=""><common:message key="combo.select"/></html:option>
										<logic:present  name="oneTimeStatus">
										<logic:iterate id="oneTimeStatus" name="oneTimeStatus" >
											<html:option value= "<%= ((OneTimeVO)oneTimeStatus).getFieldValue() %>">
											<%= ((OneTimeVO)oneTimeStatus).getFieldDescription() %>
											</html:option>
										</logic:iterate>
										</logic:present>
									</ihtml:select>
								</div>
									<div class="ic-input  ic-split-20 "><div class="ic-center marginT4 marginB5">
									<logic:present name="options">
										<bean:define name="options" id="list" type="java.util.HashMap"/>
									<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
									<ibusiness:dynamicoptionlist collection="list"
												id="docType"
												firstlistname="docType"
												componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_DYNAMICOPTIONLIST"
												lastlistname="subType" firstoptionlabel="Doc. Type"
												lastoptionlabel="Sub Type"
												optionstyleclass="iCargoMediumComboBox"
												labelstyleclass="iCargoLabelRightAligned"
												firstselectedvalue="<%=listStockRequestForm.getDocType()%>"
												lastselectedvalue="<%=listStockRequestForm.getSubType()%>"
												docTypeTitle="doctype.tooltip"
												subDocTypeTitle="subdoctype.tooltip"/>
									<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix ends-->
									</logic:present>
								</div></div>
                                 <div class="ic-input ic-split-15 ic-label-25" >
									<label>
										<common:message key="liststockrequest.StockHolderType" />
									</label>
									<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_STOCKHOLDERTYPE" >
										<html:option value=""><common:message key="combo.select"/></html:option>
										<logic:present name="stockHoldersFromSession">
											<bean:define id="stockHolderList" name="stockHoldersFromSession" />
											<logic:iterate id="priorityVO" name="stockHolderList" >

											<html:option value="<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
											<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
											</html:option>

											</logic:iterate>
										</logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-20 ic-label-25">
									<label>
										<common:message key="liststockrequest.Code" />
									</label>
									<ihtml:text property="code"
												componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_STOCKHOLDERCODE"
												maxlength="12"/>
									<div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLov('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>
								</div>
							</div>
							<div class="ic-row">	
								<div class="ic-input ic-split-15 ic-label-25 ic-mandatory">
									<label>
										<common:message key="liststockrequest.FromDate" />
									</label>
									<ibusiness:calendar property="fromDate"
												type="image" textStyleClass="iCargoTextFieldMedium"
												id="FromDate"
												componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_FROMDATE"
												value="<%=listStockRequestForm.getFromDate()%>"
												title="From Date"
												maxlength="11"/>
								</div>
								<div class="ic-input ic-split-15 ic-label-25 ic-mandatory">
									<label>
										<common:message key="liststockrequest.ToDate" />
									</label>
									<ibusiness:calendar property="toDate"
												id="ToDate"
												componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_FROMDATE"
												type="image" textStyleClass="iCargoTextFieldMedium"
												value="<%=listStockRequestForm.getToDate()%>"
												title="To Date"
												maxlength="11"/>
								</div>
                                <div class="ic-input ic-split-8 ic_inline_chcekbox marginT15">
									<label>
										<common:message key="liststockrequest.Manual" />
									</label>
									<ihtml:checkbox property="manual" componentID="CHK_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_MANUAL" />
								</div>
								<div class="ic-input ic-split-12 ic_inline_chcekbox marginT15">
									<label>
										<common:message key="stockcontrol.defaults.liststockrequest.partnerAirlines.lbl" />
									</label>
									<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
								</div>
                                <div class="ic-input ic-split-15 ic-label-25 ">
									<label>
										<common:message key="stockcontrol.defaults.liststockrequest.awbPrefix.lbl" />
									</label>
									<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" disabled='true'>
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="partnerAirlines">					
											<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
												<logic:present name="airlineLovVO" property="airlineNumber">
													<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
													<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
												</logic:present>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
								</div>
								
								<div class="ic-input ic-split-20 ic-label-25">
									<label>
										<common:message key="stockcontrol.defaults.liststockrequest.partnerAirlines.lbl"/>
									</label>
									<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
								</div>
							
							<!--<div class="ic-row">	
							</div> -->
							<div class="ic-button-container paddR4 marginT15">
									<ihtml:nbutton property="btList" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_LIST" accesskey="L">
										<common:message key="liststockrequest.List"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_CLEAR" accesskey="C" >
										<common:message key="liststockrequest.Clear"/>
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="ic-main-container">
			<div class="ic-row">
				<h4><common:message key="liststockrequest.stkreqdtls"/></h4>
			</div>
			<div class="ic-row" id="listTable">
				<div class="ic-col-100">
					<div class="ic-row">
						<div class="ic-col-75">
							<logic:present name="pageList">
								<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
										name="pageList"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=listStockRequestForm.getLastPageNumber() %>" />
							</logic:present>
							
							<logic:notPresent name="pageList">
								&nbsp;
							</logic:notPresent>
						</div>
						<div class="ic-col-25">
							<div class="ic-button-container">
								<logic:present name="pageList">
									<common:paginationTag
											linkStyleClass="iCargoLink" 
											disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitList('lastPageNum','displayPage')"
											name="pageList"
											display="pages"
											lastPageNum="<%=listStockRequestForm.getLastPageNumber()%>" 
											exportToExcel="true"
											exportTableId="stkRequestTable"
											exportAction="stockcontrol.defaults.liststockrequest.do"/>
								</logic:present>
								
								<logic:notPresent name="pageList">
									&nbsp;
								</logic:notPresent>
							</div>
						</div>
					</div>
					<div class="ic-row">
					
	<div class="tableContainer" id="div1" style="height:500px;overflow:auto;">
		<table class="fixed-header-table" id="stkRequestTable">
			<thead>
				<tr class="iCargoTableHeadingLeft">
					<td width="3%" class="iCargoTableHeaderLabel">
						
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Request Reference Number">
						<common:message key="liststockrequest.ReqRefNo" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Requested By" >
						<common:message key="liststockrequest.RequestedBy" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Requested Created By" >
						<common:message key="liststockrequest.requestCreatedBy" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Date of Request" style="text-transform:none;">
						<common:message key="liststockrequest.DateofReq" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Document Type">
						<common:message key="liststockrequest.DocType" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Document Sub Type">
						<common:message key="liststockrequest.SubType" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Number Of Documents" style="text-transform:none;">
						<common:message key="liststockrequest.No.of.Docs" />
						<span></span>
					</td>
					<td width="10%" class="iCargoTableHeaderLabel" title="Status">
						<common:message key="liststockrequest.Status" />
						<span></span>
					</td>
				</tr>
			</thead>
			<tbody>


			<logic:present  name="pageList">
				<logic:iterate id="collectionStockRequestVO" name="pageList" indexId="nIndex">
			
				<tr>
					<td class="iCargoTableDataTd" style="text-align:center">
					<ihtml:checkbox property="checkbox"
						value="<%= ((StockRequestVO)collectionStockRequestVO).getRequestRefNumber() %>"
						componentID="CHK_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_CHECKBOX"/></td>
					<td class="iCargoTableDataTd" >
							<bean:write name="collectionStockRequestVO" property="requestRefNumber" /></td>
					<td class="iCargoTableDataTd" >
							<bean:write name="collectionStockRequestVO" property="stockHolderCode" /></td>
					<td class="iCargoTableDataTd" >
							<bean:write name="collectionStockRequestVO" property="requestCreatedBy" /></td>		
					<bean:define id="requestDate" name="collectionStockRequestVO" property="requestDate" />
					<%
					String requestDateString=(((LocalDate)requestDate).toDisplayDateOnlyFormat());
					%>
					<td class="iCargoTableDataTd" ><%=requestDateString%></td>
					<td class="iCargoTableDataTd" >
						<bean:write name="collectionStockRequestVO" property="documentType" /></td>
					<td class="iCargoTableDataTd" >
						<bean:write name="collectionStockRequestVO" property="documentSubType" /></td>
					<td class="iCargoTableDataTd" style="text-align:right;" >
						<bean:write name="collectionStockRequestVO" property="requestedStock" format="####"/></td>
					<td class="iCargoTableDataTd" >
						<bean:write name="collectionStockRequestVO" property="status" /></td>
				</tr>

				</logic:iterate>
			</logic:present>

			</tbody>
		</table>
	</div>
					
					</div>
				</div>
			</div>
		</div>
		
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btCreate" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_CREATE" accesskey="R">
						<common:message key="liststockrequest.Create"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btDetails" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_DETAILS" accesskey="D">
						<common:message key="liststockrequest.Details"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btCancel" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_CANCEL" accesskey="N">
						<common:message key="liststockrequest.Cancel"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKREQUEST_CLOSE" accesskey="O">
						<common:message key="liststockrequest.Close"/>
					</ihtml:nbutton>		 
				</div>
			</div>
		</div>
	</div>

	

	</ihtml:form>
</div>

<script language="javascript">
if(document.forms[1].canCancel.value=="Y"){
	document.forms[1].action="stockcontrol.defaults.cancelliststockrequest.do";
	document.forms[1].submit();
}

</script>


	</body>
</html:html>

