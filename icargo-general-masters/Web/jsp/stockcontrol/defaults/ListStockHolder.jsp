
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  STK - Stock Control
* File Name				:  ListStockHolder.jsp
* Date					:  11-Jun-2015
* Author(s)				:  Ramesh Chandra Pradhan

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO" %>
<%@ page import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO"%>

<html:html>
<head>
		
	

	<bean:define id="form" name="ListStockHolderForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm"
		toScope="page" />
<title>
	<common:message bundle="<%=form.getBundle()%>" key="liststockholder.liststockholdertitle"/>
</title>

<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/stockcontrol/defaults/ListStockHolder_Script.jsp" />

</head>

<body id="bodyStyle" >

	


	<div class="iCargoContent ic-masterbg" id="pageDiv">

		<ihtml:form action="/stockcontrol.defaults.screenloadliststockholder.do">
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="fromStockHolderList" />
		<html:hidden property="lastPageNum" />
		<html:hidden property="displayPage"  />

		<html:hidden property="checkList" />
		<html:hidden property="listFlag" />
		<html:hidden property="disableButn" />
		<input type="text" name="tempHidden" style="display:none"/>
		<input type="hidden" name="mySearchEnabled" />

		<business:sessionBean id="stockHoldersFromSession"
				moduleName="stockcontrol.defaults"
				screenID="stockcontrol.defaults.liststockholder" method="get"
				attribute="prioritizedStockHolders" />

		<business:sessionBean id="docTypeFromSession"
				moduleName="stockcontrol.defaults"
				screenID="stockcontrol.defaults.liststockholder" method="get"
				attribute="docTypeMap" />

		<business:sessionBean id="stockholderFromSession"
				moduleName="stockcontrol.defaults"
				screenID="stockcontrol.defaults.liststockholder" method="get"
				attribute="stockHolderDetails" />

		<business:sessionBean id="partnerAirlines"
				moduleName="stockcontrol.defaults"
				screenID="stockcontrol.defaults.liststockholder" method="get"
				attribute="partnerAirlines"/>
	
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="liststockholder.liststockholdertitle" />
			</span>
			<div class="ic-head-container">
				<!-- <div class="ic-row" style="height:10px">
					<h3><common:message key="liststockholder.search" /></h3>
				</div> -->
				<div class="ic-filter-panel">
                    <div class="ic-row" style="height:10px"><h3><common:message key="liststockholder.search" /></h3></div>
					<div class="ic-row">
						<!--<div class="ic-col-100">	-->
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-mandatory ic-split-20 ic-label-35">
										<label>
											<common:message key="liststockrequest.StockHolderType" />
										</label>
										<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_STOCKHOLDERTYPE" >
											<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
											<logic:present name="stockHoldersFromSession">
												<bean:define id="stockHolderList" name="stockHoldersFromSession" />
												<logic:iterate id="priorityVO" name="stockHolderList" >
														<html:option
															value="<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
												  <%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
												  </html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-30 ic-label-20">
										<label>
											<common:message key="liststockrequest.Code" />
										</label>
										<ihtml:text property="stockHolderCode"
													componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_STOCKHOLDERCODE"
													maxlength="12" /> 
											<div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" 
													onclick="displayLov('stockcontrol.defaults.screenloadstockholderlov.do');" /></div>
									</div>
									<div class="ic-input ic-split-30">
										<logic:present name="docTypeFromSession">
											<bean:define id="docTypeMap" name="docTypeFromSession" type="java.util.HashMap" />
											<ibusiness:dynamicoptionlist collection="docTypeMap" id="docType"
													firstlistname="docType"
													componentID="TXT_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_DYNAMICOPTIONLIST"
													lastlistname="docSubType" 
													firstoptionlabel="Doc. Type"
													lastoptionlabel="Sub Type"
													optionstyleclass="iCargoMediumComboBox"
													labelstyleclass="iCargoLabelRightAligned" 
													firstselectedvalue="<%=form.getDocType()%>" 
													lastselectedvalue="<%=form.getDocSubType()%>" 
													subDocTypeMandatory="true" 
													docTypeTitle="doctype.tooltip" 
													subDocTypeTitle="subdoctype.tooltip"/>
												
										</logic:present>
								</div>
									<div class="ic-input ic-split-20 marginT10">
										<ihtml:checkbox
											property="partnerAirline"
											componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
											<common:message key="liststockholder.partnerAirline.lbl" />						
									</div>
								</div>
								<div class="ic-row">
									<div class="ic-input ic-split-20 ic-label-30">
										<label>
											<common:message key="liststockholder.awbprefix.lbl" />
										</label>
										<ihtml:select property="awbPrefix"
													componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB"
													disabled='true'>
											<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
											<logic:present name="partnerAirlines">					
												<logic:iterate id="airlineLovVO" name="partnerAirlines"
																type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
													<logic:present name="airlineLovVO" property="airlineNumber">
														<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
														<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-30 ic-label-20">
										<label>
											<common:message key="liststockholder.partnerAirline.lbl" />
										</label>
										<ihtml:text property="airlineName"
											componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL"
											readonly="true" />
									</div>
                                    <div class="ic-button-container paddr%">
										<ihtml:nbutton property="btList" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_LIST" accesskey="L">
											<common:message key="liststockholder.list"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btClear" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_CLEAR" accesskey="L">
											<common:message key="liststockholder.clear"/>
										</ihtml:nbutton>
									</div>
								</div>
								<div class="ic-row">
									
								</div>
							<!--</div> -->
						</div>
					</div>
				</div>
			</div>
			
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="liststockholder.stockhldrdetails" /></h4>
				</div>
				<div class="ic-row" id="listTable">
					<div class="ic-col-100">
						<div class="ic-row">
							<div class="ic-col-50">
								<logic:present name="stockholderFromSession">
									<bean:define id="pageObj" name="stockholderFromSession"  />
									<common:paginationTag
											pageURL="stockcontrol.defaults.liststockholder.do"
											name="pageObj" display="label"
											labelStyleClass="iCargoResultsLabel"
											lastPageNum="<%=form.getLastPageNum() %>" />
								</logic:present>
							</div>
							<div class="ic-col-50 ic-button-container paddR5">
								<logic:present name="stockholderFromSession">
									<bean:define id="pageObj1" name="stockholderFromSession"  />
									<common:paginationTag linkStyleClass="iCargoLink"
											disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitPage('lastPageNum','displayPage')"
											name="pageObj1" display="pages" 
											lastPageNum="<%=form.getLastPageNum()%>" exportToExcel="true" 
											exportTableId="listStockHolderTable" 
											exportAction="stockcontrol.defaults.liststockholder.do"/>
								</logic:present>
							</div>
						</div>
						<div class="ic-row">
				
		<!-- Data table section Start-->		
		<div class="tableContainer" id="div1" style="height:600px; width: 100%">
			<table class="fixed-header-table" id="listStockHolderTable">
                <thead>
                    <tr class="iCargoTableHeadingCenter">
						<td width="2%" rowspan="2" class="iCargoTableHeaderLabel">&nbsp;<span></span></td>
						<td colspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.stockholder" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.doctype" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.subtype" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><!--common:message key="liststockholder.awbprefix.lbl"/-->
						AWB Prefix<span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.reorderlevel" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.reorderqty" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.reorderalert" /><span></span></td>
						<td rowspan="2" class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.autostockrequest" /><span></span></td>
                    </tr>
					<tr class="iCargoTableHeadingCenter">
						<td class="iCargoTableHeaderLabel"
							class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.type" /></td>
						<td class="iCargoTableHeaderLabel"
							class="iCargoTableHeaderLabel"><common:message
							key="liststockholder.code" /></td>
					</tr>
				</thead>
				<tbody>

                    <logic:present name="stockholderFromSession">
						<logic:iterate name="stockholderFromSession" id="vo" indexId="nIndex">

			  		 <%
			        String parentCode=((StockHolderDetailsVO)vo).getStockHolderCode()+ ((StockHolderDetailsVO)vo).getDocType() + ((StockHolderDetailsVO)vo).getDocSubType();
			        String checkCode = ((StockHolderDetailsVO)vo).getStockHolderCode() + "-"  +
			        		   ((StockHolderDetailsVO)vo).getDocType() + "-" + 
			        		   ((StockHolderDetailsVO)vo).getDocSubType()+"-"+
							   ((StockHolderDetailsVO)vo).getAwbPrefix();

			   		 %>
						<tr id="<%= parentCode %>" >
							<td class="iCargoTableTd ic-center" align="center">
								<html:checkbox property="checkStockHolder" value="<%=checkCode%>" 
									onclick="singleSelect(this,'checkStockHolder');" />
							</td>
							<td class="iCargoTableTd"><bean:write name="vo"
								property="stockHolderType" /></td>
							<td class="iCargoTableTd"><bean:write name="vo"
								property="stockHolderCode" /></td>
							<td class="iCargoTableTd"><bean:write name="vo"
								property="docType" /></td>
							<td class="iCargoTableTd"><bean:write name="vo"
								property="docSubType" /></td>
							<td class="iCargoTableTd"><bean:write name="vo"
								property="awbPrefix" /></td>
							<td class="iCargoTableTd" style="text-align: right;"><bean:write
								name="vo" property="reorderLevel" format="####" /></td>
							<td class="iCargoTableTd" style="text-align: right;"><bean:write
								name="vo" property="reorderQuantity" format="####" /></td>

							<logic:equal name="vo" property="reorderAlert" value="true">
								<td class="iCargoTableTd" style="text-align: center;" ><input type="checkbox"
									name="reorderAlert" checked="checked"
									value="<%=((StockHolderDetailsVO)vo).isReorderAlert()%>"
									disabled="true" /></td>
							</logic:equal>
							<logic:equal name="vo" property="reorderAlert" value="false">
								<td class="iCargoTableTd" style="text-align: center;"><input type="checkbox"
									name="reorderAlert"
									value="<%=((StockHolderDetailsVO)vo).isReorderAlert()%>"
									disabled="true" /></td>
							</logic:equal>
							<logic:equal name="vo" property="autoStockRequest" value="true">
								<td class="iCargoTableTd" style="text-align: center;">
									<input type="checkbox"
											name="autoStockRequest" checked="checked"
											value="<%=((StockHolderDetailsVO)vo).isAutoStockRequest()%>"
											disabled="true" />
								</td>
							</logic:equal>
							<logic:equal name="vo" property="autoStockRequest" value="false">
								<td class="iCargoTableTd" style="text-align: center;">
									<input type="checkbox"
										name="autoStockRequest"
										value="<%=((StockHolderDetailsVO)vo).isAutoStockRequest()%>"
										disabled="true" />
								</td>
							</logic:equal>

						</tr>

						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
		</div>
		<!-- Data table section End-->
						</div>
					</div>
				</div>
			</div>
			
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btMonitor" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_MONITOR" accesskey="M">
							<common:message key="liststockholder.Monitor"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btCreate" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_CREATE" accesskey="C">
							<common:message key="liststockholder.create"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btDetails" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_DETAILS" accesskey="D">
							<common:message key="liststockholder.details"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btCancel" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_CANCEL" accesskey="N">
							<common:message key="liststockholder.cancel"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_STOCKCONTROL_DEFAULTS_LISTSTOCKHOLDER_CLOSE" accesskey="O">
							<common:message key="liststockholder.close"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			
		</div>

</ihtml:form>
</div>
	
	
	
	</body>
</html:html>

