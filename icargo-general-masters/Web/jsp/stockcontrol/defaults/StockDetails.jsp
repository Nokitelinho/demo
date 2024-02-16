<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  STK - Stock Control
* File Name				:  StockDetails.jsp
* Date					:  01-Oct-2015
* Author(s)				:  Ramesh Chandra Pradhan
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate"%>


<html>
	<head>
		


<bean:define id="form"
	name="StockDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm"
	toScope="page" />
		<title>
			<common:message bundle="<%=form.getBundle()%>" key="stockdetails.title"/>
		</title>
		<common:include type="script" src="/js/stockcontrol/defaults/StockDetails_Script.jsp" />
		<meta name="decorator" content="mainpanelrestyledui">
	</head>
	
<body>
	
	

	   <%@include file="/jsp/includes/reports/printFrame.jsp" %>
	 <!-- add session beans and hidden properties
	 -->
	<business:sessionBean id="options"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.stockdetails" method="get"
			attribute="dynamicDocType"/>
	<business:sessionBean id="stockHoldersFromSession"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.stockdetails"
			method="get"
			attribute="prioritizedStockHolders" />
	<business:sessionBean id="StockDetails"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.stockdetails"
			method="get"
			attribute="stockDetails"/>

	<business:sessionBean id="stockDetailsVO"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.stockdetails"
			method="get"
			attribute="stockDetailsVO"/>
			
<div class="iCargoContent ic-masterbg" id="pageDiv" style="width:100%;overflow:auto;">
	<ihtml:form action="stockcontrol.defaults.screenloadstockdetails.do ">
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="lastPageNum" />
		<input type="text" name="tempHidden" style="display:none"/>

	
		<%
		   StockDetailsForm stockDetailsForm = (StockDetailsForm)request.getAttribute("StockDetailsForm");
		%>
	
	<div class="ic-content-main">

		<span class="ic-page-title ic-display-none">
			<common:message key="stockdetails.StockDetails" />
		</span>
			
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-row">
					<h3><common:message key="stockdetails.StockDetails" /></h3>
				</div>
				<div class="ic-row">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-15 ic-mandatory ic-label-35">
								<label>
									<common:message key="stockdetails.StockHolderType" />
								</label>
								<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_STOCKDETAILS_STOCKHOLDERTYPE" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
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
							<div class="ic-input ic-split-15 ic-label-35">
								<label>
									<common:message key="stockdetails.StockHolderCode" />									
								</label>
								<ihtml:text property="stockHolderCode" componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKDETAILS_STOCKHOLDERCODE" maxlength="12"/>
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" id="stockHolderCodeLov" onclick="displayStockHolderLov('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>
							</div>
                                <div class="ic-input ic-split-23" >
								<logic:present name="options">
									<bean:define name="options" id="list" type="java.util.HashMap"/>
									<ibusiness:dynamicoptionlist 
											collection="list"
											id="docType"
											firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKDETAILS_DYNAMICOPTIONLIST"
											lastlistname="subType" firstoptionlabel="Doc. Type"
											lastoptionlabel="Sub Type"
											optionstyleclass="iCargoMediumComboBox"
											labelstyleclass="iCargoLabelRightAligned"
											subDocTypeMandatory="true"
											docTypeMandatory="true"
											firstselectedvalue="<%=form.getDocType()%>" 
											lastselectedvalue="<%=form.getSubType()%>" 
											docTypeTitle="stockcontrol.defaults.stockdetails.tooltip.docTypeTitle"
											subDocTypeTitle="stockcontrol.defaults.stockdetails.tooltip.subDocTypeTitle"/>
								</logic:present>								
							</div>
						<!--</div>
						<div class="ic-row"> -->
							<div class="ic-input ic-split-15 ic-mandatory ic-label-35 paddL10">
								<label>
									<common:message key="stockdetails.FromDate" />
								</label>
								<ibusiness:calendar property="fromDate"
										type="image" 
										textStyleClass="iCargoTextFieldMedium"
										id="FromDate"
										componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKDETAILS_FROMDATE"
										value="<%=form.getFromDate()%>"
										title="From Date"
										maxlength="11"/>
							</div>
							<div class="ic-input ic-split-12 ic-mandatory ic-label-35 paddL10">
								<label>
									<common:message key="stockdetails.ToDate" />									
								</label>
								<ibusiness:calendar property="toDate" 
										id="ToDate" 
										componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKDETAILS_TODATE" 
										type="image" textStyleClass="iCargoTextFieldMedium" 
										value="<%=form.getToDate()%>" 
										title="To Date"
										maxlength="11"/>
							</div>
							<div class="ic-input ic-split-20">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" accesskey="L" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKDETAILS_LIST" >
										<common:message key="stockdetails.List"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" accesskey="C" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKDETAILS_CLEAR" >
										<common:message key="stockdetails.Clear"/>
									</ihtml:nbutton>
								</div>	
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="ic-main-container">
			<div class="ic-input-container">
				<div class="ic-row">
					<h4><common:message key="stockdetails.StockDetails"/></h4>
				</div>
				<div class="ic-row">
					<div class="ic-col-45">
						<logic:present name="StockDetails">
							<common:paginationTag
									pageURL="javascript:submitList('lastPageNum','displayPage')"
									name="StockDetails"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-col-55">
						<div class="ic-button-container paddR5">
							<logic:present name="StockDetails">
								<common:paginationTag
										linkStyleClass="iCargoLink"  disabledLinkStyleClass="iCargoLink"
										pageURL="javascript:submitList('lastPageNum','displayPage')" 
										name="StockDetails" 
										display="pages" 
										lastPageNum="<%=form.getLastPageNum()%>" 
										exportToExcel="true" 
										exportTableId="stockDetailsTable" 
										exportAction="stockcontrol.defaults.liststockdetails.do"/>
							</logic:present>
						</div>
					</div>
				</div>
				<div class="ic-row">

	<div id="div2" class="tableContainer" style="width:100%; height:615px; overflow:auto;">
		<table class="fixed-header-table" id="stockDetailsTable">
			<thead>
				<tr class="iCargoTableHeadingLeft">
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.date"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.StockHolderCode"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.openingbalance"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.receivedstock"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.distributedstock"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel" style="text-transform:none;"><common:message key="stockdetails.returnedtostock"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.utilizedstock"/></td>
					<td style="width:11%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.blacklistedstock"/></td>
					<td style="width:12%" class="iCargoTableHeaderLabel"><common:message key="stockdetails.closingbalance"/></td>
				</tr>
			</thead>
				<%
				   long receivedStock=0;
				   long distributedStock=0;
				   long returnedToStock=0;
				   long utilizedStock=0;
				  long blackListedStock=0;
				%>
			<tbody>
			
			<logic:present name="StockDetails">

				<logic:iterate id="childVO" name="StockDetails"
					type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO" indexId="rowCount">
					
				<tr>
					<logic:present name = "childVO" property="transactionDate" >
						<td class="iCargoTableDataTd">
							<%String txnDate="";%>

							<bean:define id ="transactionDate" name = "childVO" property="transactionDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
							<% txnDate=TimeConvertor.toStringFormat(transactionDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>

							<%=txnDate%>
						</td>
					</logic:present>
					<logic:notPresent name = "childVO" property="transactionDate">
						<td class="iCargoTableDataTd">&nbsp;</td>
					</logic:notPresent>
						<td class="iCargoTableDataTd" >
							<logic:present name = "childVO" property="stockHolderCode" >
								<bean:write name="childVO" property="stockHolderCode"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;"  >
							<logic:present name = "childVO" property="openingBalance" >
								<bean:write name="childVO" property="openingBalance"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;" >
							<logic:present name = "childVO" property="receivedStock" >
								<bean:write name="childVO" property="receivedStock" />
								<bean:define id ="received" name = "childVO" property="receivedStock" type="java.lang.Long"/>
								<% receivedStock+=(Long)received;%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;" >
							<bean:define id ="allocated" name = "childVO" property="allocatedStock" type="java.lang.Long"/>
							<bean:define id ="returned" name = "childVO" property="returnedStock" type="java.lang.Long"/>
							<bean:define id ="transferred" name = "childVO" property="transferredStock" type="java.lang.Long"/>
							<% 
								long distributed=allocated+returned+transferred;
							%>
							<%=distributed%>
							<% distributedStock+=(Long)distributed;%>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;">
							<logic:present name = "childVO" property="returnedUtilizedStock" >
								<bean:write name="childVO" property="returnedUtilizedStock" />
								<bean:define id ="returned" name = "childVO" property="returnedUtilizedStock" type="java.lang.Long"/>
								<% returnedToStock+=(Long)returned;%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;"  >
							<logic:present name = "childVO" property="utilizedStock" >
								<bean:write name="childVO" property="utilizedStock" />
								<bean:define id ="utilized" name = "childVO" property="utilizedStock" type="java.lang.Long"/>
								<% utilizedStock+=(Long)utilized;%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;"  >
							<logic:present name = "childVO" property="blacklistedStock" >
								<bean:write name="childVO" property="blacklistedStock" />
								<bean:define id ="blacklisted" name = "childVO" property="blacklistedStock" type="java.lang.Long"/>
								<% blackListedStock+=(Long)blacklisted;%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right;"  >
						<logic:present name = "childVO" property="availableStock" >
							<bean:write name="childVO" property="availableStock" />
							<%--availableStockTotal+=availableStock--%>
							</logic:present>
						</td>

					</tr>
						
					</logic:iterate>
				</logic:present>

			</tbody>
	
			<tfoot>
				<tr>
					<td colspan="3">Totals</td>
					<td align="left"><%=receivedStock%></td>
					<td align="left"><%=distributedStock%></td>
					<td align="left"><%=returnedToStock%></td>
					<td align="left"><%=utilizedStock%></td>
					<td align="left"><%=blackListedStock%></td>
					<td align="left"></td>
				</tr>
			</tfoot>
		</table>
	</div>



	
				
				
			</div>
		</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row paddR5">
				<div class="ic-button-container">
					<ihtml:nbutton property="btnClose" accesskey="O" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKDETAILS_CLOSE" >
						<common:message key="stockdetails.Close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>	
	
					
	</ihtml:form>
</div>


	</body>
</html>

