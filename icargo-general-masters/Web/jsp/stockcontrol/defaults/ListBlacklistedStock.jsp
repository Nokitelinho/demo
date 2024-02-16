<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  SKCM - stock Control
* File Name				:  ListBlackListedStock.jsp
* Date					:  20-Sep-2005
* Author(s)				:  Kirupakaran
*************************************************************************/
 --%>

<%@ page language="java" %>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO" %>

<html:html>
<head>
			
<bean:define id="form"
	name="ListBlackListedStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="listblaklistedstock.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/ListBlackListedStock_Script.jsp" />
</head>

<body id="bodyStyle">
	

<business:sessionBean id="options"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.listblacklistedstock"
	method="get"
	attribute="documentTypes"/>
<business:sessionBean id="partnerAirlines"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.listblacklistedstock"
	method="get"
	attribute="partnerAirlines"/>	

<%
 	ListBlackListedStockForm listBlackListedStockForm = (ListBlackListedStockForm)request.getAttribute("ListBlackListedStockForm");
 %>

<div class="iCargoContent ic-masterbg" id="pageDiv" style="overflow:auto;height:100%;width:100%;">
<ihtml:form action="stockcontrol.defaults.screenloadlistblacklistedstock.do">
<html:hidden property="lastPageNumber" />
<html:hidden property="displayPage"  />
<html:hidden property="listButton"/>
<html:hidden property="documentRange"/>
<html:hidden property="partnerPrefix" />
<input type="hidden" name="mySearchEnabled" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
    	<common:message key="stockholder.ListBlackListedStock"/>
	</span>
	<div class="ic-head-container">
		<!--<div class="ic-row">
			<h4><common:message key="stockholder.search" /></h4>
		</div> -->
		<div class="ic-filter-panel">
            <div class="ic-row"><h4><common:message key="stockholder.search" /></h4></div>
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-20 ">	
								<logic:present name="options"> <bean:define name="options" id="list" type="java.util.HashMap"/>
									<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
									<ibusiness:dynamicoptionlist  collection="list"
									id="docType"
									firstlistname="docType"
									componentID="TXT_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_DYNAMICOPTIONLIST"
									lastlistname="subType" firstoptionlabel="Doc. Type"
									lastoptionlabel="Sub Type"
									optionstyleclass="iCargoMediumComboBox"
									labelstyleclass="iCargoLabelRightAligned"
									firstselectedvalue="<%=listBlackListedStockForm.getDocType()%>"
									lastselectedvalue="<%=listBlackListedStockForm.getSubType()%>"
									docTypeTitle="doctype.tooltip"
									subDocTypeTitle="subdoctype.tooltip"/>
									<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix ends-->
									<%--<business:dynamicoptionlist collection="list"
									firstlistname="docType" lastlistname="subType"
									firstoptionlabel="Doc Type"
									lastoptionlabel="Sub Type" optionstyleclass="iCargoMediumComboBox"
									labelstyleclass="iCargoLabelRightAligned"
									firstselectedvalue="<%=listBlackListedStockForm.getDocType()%>"
									lastselectedvalue="<%=listBlackListedStockForm.getSubType()%>"/>--%>
								</logic:present>
							</div>
							<div class="ic-input ic-mandatory ic-split-13">						
								<label class="ic-label-10">
									<common:message key="stockholder.FromDate"/>
								</label>
								<ibusiness:calendar property="fromDate"
								id="fromDate"
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_FROMDATE"
								type="image"
								textStyleClass="iCargoTextFieldMedium"
								value="<%=listBlackListedStockForm.getFromDate()%>"
								title="From Date"
								maxlength="11"/>
							</div>
							<div class="ic-input ic-split-15 ic-mandatory">						
								<label>
									<common:message key="stockholder.ToDate"/>
								</label>					
								<ibusiness:calendar property="toDate"
								id="toDate"
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_TODATE"
								type="image"
								textStyleClass="iCargoTextFieldMedium"
								value="<%=listBlackListedStockForm.getToDate()%>"
								title="To Date"
								maxlength="11"/>
							</div>			
							
					
							<div class="ic-input ic-split-12 ic_inline_chcekbox marginT15 ">		
								
								<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
								
								<label class="ic-label-40">							
								<common:message key="stockcontrol.defaults.listblacklist.partnerAirlines.lbl" />
								</label>
							</div>
						
						
							<div class="ic-input ic-split-13 PaddL0">			
								<label class="ic-label-33">	
								<common:message key="stockcontrol.defaults.listblacklist.awbPrefix.lbl" />
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
							<div class="ic-input ic-split-25" >	
								<label>
								<common:message key="stockcontrol.defaults.listblacklist.partnerAirlines.lbl"/>
								</label>
								<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
							</div>
		</div>	
<div class="ic-row">		
							<div class="ic-input ic-split-10">	
								<label class="ic-label-35">		
								<common:message key="stockholder.rangeFrom"/>
								</label>
								<ihtml:text property="rangeFrom"
	           		componentID="TXT_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_RANGEFROM"
	           		/>
							</div>
							<div class="ic-input ic-split-14 paddR0 paddL0">	
								<label class="ic-label-33">	
								<common:message key="stockholder.RangeTo"/>
								</label>
								<ihtml:text property="rangeTo"
	          		componentID="TXT_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_RANGETO"
	          		/>
							</div>
							<!-- <div class="ic-input ic-split-70"> -->
								<div class="ic-button-container marginR10">
									<ihtml:nbutton property="btnList"
										componentID="BTN_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_LIST" accesskey="L" >
									<common:message key="listblaklistedstock.list"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear"
										componentID="BTN_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_CLEAR" accesskey="C" >
									<common:message key="listblaklistedstock.clear"/>
									</ihtml:nbutton>
								</div>
								</div>
							<!--</div> -->
								
					</div>
				</div>		
			</div>	 
		</div>  
	</div>
	<div class="ic-main-container">
		<business:sessionBean id="listFromSession"
	   					  moduleName="stockcontrol.defaults"
	   				      screenID="stockcontrol.defaults.listblacklistedstock"
	   					  method="get"
						  attribute="blacklistStockVOs" />

		<logic:present name="listFromSession">
		<bean:define id="pageList" name="listFromSession" toScope="page"/>
		<div class="ic-row">
			<h4><common:message key="stockholder.blacklistedstock"/></h4>
		</div>
		</logic:present>
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<logic:present name="listFromSession">
				<bean:define id="pageList" name="listFromSession" toScope="page"/>
				<div class="ic-row">
					<div class="ic-col-65">
						<logic:present name="pageList">
							<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
								name="pageList"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=listBlackListedStockForm.getLastPageNumber() %>" />
						</logic:present>
						<logic:notPresent name="pageList">
								&nbsp;
						</logic:notPresent>						    
					</div>
					<div class="ic-col-35 paddR5">
						<div class="ic-button-container">
							<logic:present name="pageList">
							  <common:paginationTag
							  linkStyleClass="iCargoLink"
							  disabledLinkStyleClass="iCargoLink"
							  pageURL="javascript:submitList('lastPageNum','displayPage')"
							  name="pageList"
							  display="pages"
							  lastPageNum="<%=listBlackListedStockForm.getLastPageNumber()%>" 
							  exportToExcel="true"
							  exportTableId="blklistedStockTable"
							  exportAction="stockcontrol.defaults.listblacklistedstock.do"/>
							</logic:present>
							<logic:notPresent name="pageList">
									&nbsp;
							</logic:notPresent>									
						</div>
					</div>
				</div>
				</logic:present>
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:650px;">
						<table id="blklistedStockTable" class="fixed-header-table" style="width:100%">
							<thead>
							<tr class="iCargoTableHeadingLeft">
							<td width="6%" class="iCargoTableHeadingCenter">
								&nbsp;
						  
							<span></span></td>
							<td width="10%">
								<common:message key="stockholder.DocType"/>
								
							<span></span></td>
							<td height="25px" width="10%">
								<common:message key="stockholder.SubType"/>
								
							<span></span></td>
		<td height="25px" width="10%">
                	<common:message key="stockholder.StockHolder"/>
<span></span></td>
		<td height="25px" width="11%">
                	<common:message key="stockholder.FirstLevelStockHolder"/>
<span></span></td>
                <td height="25px" width="11%">
                	<common:message key="stockholder.SecondLevelStockHolder"/>
<span></span></td>
                <td height="25px" width="10%">
								<common:message key="stockholder.BlackListingDate"/>
							
							<span></span></td>
							<td width="8%">
								<common:message key="stockholder.rangeFrom"/>
								
							<span></span></td>
							<td width="8%">
								<common:message key="stockholder.RangeTo"/>
								
							<span></span></td>
							<td width="16%">
								<common:message key="stockholder.Remarks"/>
								
							<span></span></td>
							</tr>
							</thead>
							<%int count=0;
					String blacklistDate;%>
							<tbody>
								<logic:present name="pageList" >
									<logic:iterate id="listSession" name="pageList" indexId="nIndex">
									
									   <tr>
										  <td style="text-align:center;" class="iCargoTableDataTd">
												<ihtml:checkbox property="blacklistCheck"
													name="ListBlackListedStockForm"
													value="<%=String.valueOf(count) %>"
													componentID="CHK_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_BLACKLISTCHECK"/>
										  </td>
										  <%	blacklistDate=
													TimeConvertor.toStringFormat(((LocalDate)((BlacklistStockVO)listSession).getBlacklistDate()).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);

											%>

										  <td  class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getDocumentType() %></td>
										  <td  class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getDocumentSubType() %></td>
                    		      <td  class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getStockHolderCode()==null ? "-":((BlacklistStockVO)listSession).getStockHolderCode() %></td>
                    		      <td  class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getFirstLevelStockHolder()==null ? "-":((BlacklistStockVO)listSession).getFirstLevelStockHolder() %></td>
                    		      <td  class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getSecondLevelStockHolder()==null ? "-":((BlacklistStockVO)listSession).getSecondLevelStockHolder() %></td>
										  <td  class="iCargoTableDataTd"><%= blacklistDate %></td>
										  <td style="text-align:right;" class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getRangeFrom() %></td>
										  <td style="text-align:right;" class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getRangeTo() %></td>
										  <% if(((BlacklistStockVO)listSession).getRemarks()!=null) {%>
										  <td class="iCargoTableDataTd"><%= ((BlacklistStockVO)listSession).getRemarks() %></td>
											<%}
										else{%>
										<td >&nbsp;</td>
										<%}%>
									   </tr>
									   <%
											count++;
									   %>
									   
									</logic:iterate>
									<% count=0; %>
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
			<div class="ic-button-container paddR15">
				<ihtml:nbutton property="btnRevoke"
					componentID="BTN_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_REVOKE" accesskey="R">
					<common:message key="listblaklistedstock.revoke"/>
			    </ihtml:nbutton>
				<ihtml:nbutton property="btnClose"
					componentID="BTN_STOCKCONTROL_DEFAULTS_LISTBLACKLISTEDSTOCK_CLOSE" accesskey="O">
					<common:message key="listblaklistedstock.close"/>
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>

	
				
		
	</body>
</html:html>

