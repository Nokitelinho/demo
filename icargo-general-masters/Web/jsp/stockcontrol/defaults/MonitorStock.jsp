<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name						:  MonitorStockDetails.jsp
* Date								:  12 Jul 2007
* Author(s)						:  A-1944

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO" %>

<html>
<head>
		
	
	
<bean:define id="form"
	name="MonitorStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="monitorstock.title"/></title>
<common:include type="script" src="/js/stockcontrol/defaults/MonitorStock_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">
</head>

<body>
	
	
	   <%@include file="/jsp/includes/reports/printFrame.jsp" %>

					<business:sessionBean id="stockHoldersFromSession"
						moduleName="stockcontrol.defaults"
						screenID="stockcontrol.defaults.monitorstock"
						method="get"
						attribute="prioritizedStockHolders" />
<business:sessionBean id="options"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.monitorstock" method="get"
			attribute="dynamicDocType"/>
<business:sessionBean id="monitorStockDetails"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.monitorstock"
			method="get"
			attribute="monitorStockDetails"/>

<business:sessionBean id="monitorStockHolderVO"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.monitorstock"
			method="get"
			attribute="monitorStockHolderVO"/>

<business:sessionBean id="partnerAirlines"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.monitorstock"
	method="get"
	attribute="partnerAirlines"/>

<logic:present name="monitorStockHolderVO">
	<bean:define id="monitorStockHolderVO" name="monitorStockHolderVO"/>
</logic:present>

<div class="iCargoContent ic-masterbg" id="pageDiv"  style="height:100%;" >
<ihtml:form action="stockcontrol.defaults.screenloadmonitorstock.do ">

<ihtml:hidden property="flag" />
<ihtml:hidden property="code" />
<ihtml:hidden property="buttonStatusFlag" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />

<html:hidden property="listFlagFromListScreen"/>
<html:hidden property="disableButnFromListScreen"/>
<html:hidden property="fromListMonitor"/>
<html:hidden property="fromMonitorStock"/>
<html:hidden property="checkList"/>
<html:hidden property="reportGenerateMode"/>
<html:hidden property="documentRange"/>
<html:hidden property="deleteButtonPrivilege"/>
<html:hidden property="partnerPrefix"/>
<input type="text" name="tempHidden" style="display:none"/>


  <%
	   MonitorStockForm monitorStockForm = (MonitorStockForm)request.getAttribute("MonitorStockForm");
   %>
	<div class="ic-content-main">	
		<div class="ic-head-container">		
			<!--<div class="ic-row">
				<h4><common:message key="monitorstock.MonitorStock" /></h4>
			</div> -->
			<div class="ic-filter-panel">
                <div class="ic-row"><h4><common:message key="monitorstock.MonitorStock" /></h4></div>
				<div class="ic-row">
					<div class="ic-input-container">
						<div class="ic-col-30">
							<div class="ic-input ic-mandatory ic-split-100 ic-label-35">
								<label>
									<common:message key="monitorstock.StockHolderType" />
								</label>
								<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_MONITORSTOCK_STOCKHOLDERTYPE" tabindex="1">
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
							<div class="ic-input ic-split-100 ic-label-55" style="margin-top:10px">
								<label><ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" tabindex="5" />
									<common:message key="monitorstockdetails.partnerAirline.lbl" />
								</label>
							</div>
							<div class="ic-input ic-split-100 ic-label-45" style="margin-top:5px">
								<label><ihtml:checkbox property="manual" componentID="CHK_STOCKCONTROL_DEFAULTS_MONITORSTOCK_MANUAL" tabindex="8" />
									<common:message key="monitorstock.Manual" />
								</label>
							</div>
						</div>
						<div class="ic-col-30">
							<div class="ic-input ic-mandatory ic-split-100 ic-label-35">
								<label>
									<common:message key="monitorstock.StockHolderCode" />
								</label>
								<ihtml:text property="stockHolderCode" componentID="TXT_STOCKCONTROL_DEFAULTS_MONITORSTOCK_STOCKHOLDERCODE" maxlength="12" tabindex="2"/>
                                <div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png"  id="stockHolderCodeLov" /></div> 
							</div>
							<div class="ic-input ic-split-100 ic-label-35">								
								<label>
									<common:message key="monitorstockdetails.partnerAwbPrefix.lbl" />
								</label>
								<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" disabled='true' tabindex="5">
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
							<div class="ic-input ic-split-100 ic-label-25">
								
							</div>
						</div>
						<div class="ic-col-40">
							<div class="ic-input ic-split-100 marginl10" >
								<logic:present  name="options">
									<bean:define name="options" id="list" type="java.util.HashMap"/>
									<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
									<ibusiness:dynamicoptionlist  collection="list"
									id="docType"
									firstlistname="docType"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCK_DYNAMICOPTIONLIST"
									lastlistname="subType" firstoptionlabel="Doc. Type"
									lastoptionlabel="Sub Type"
									optionstyleclass="iCargoMediumComboBox"
									labelstyleclass="iCargoLabelRightAligned"
																subDocTypeMandatory="true"
																docTypeMandatory="true"
									firstselectedvalue="<%=monitorStockForm.getDocType()%>"
									lastselectedvalue="<%=monitorStockForm.getSubType()%>"
									docTypeTitle="stockcontrol.defaults.monitorstock.tooltip.docTypeTitle"
									subDocTypeTitle="stockcontrol.defaults.monitorstock.tooltip.subDocTypeTitle" tabindex="3"/>
								</logic:present>
							</div>
							<div class="ic-split-100 ic-label-100" style="padding-left: 15px;">								
								<label>
									<common:message key="monitorstockdetails.partnerAirline.lbl"/>
								</label>
								<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
							</div>
							<div class="ic-input ic-split-100 ">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList"
										componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_LIST"  accesskey="L" tabindex="9">
										<common:message key="monitorstock.List"/>
										</ihtml:nbutton>
									<ihtml:nbutton property="btnClear"
										componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CLEAR"  accesskey="C" tabindex="10" >
										<common:message key="monitorstock.Clear"/>
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			
			<div class="ic-row" id="listTable">
				
					<div class="ic-row">
						<h4><common:message key="monitorstockdetails.monitorstockdetails"/></h4>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div2" style="height:100px">
							<table class="fixed-header-table" id="stockHolderTable">
								<thead>
									<tr>				
										<td rowspan="2" width="2%" class="iCargoTableHeadingLeft"></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdertype"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdercode"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdername"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.availablestock"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.allocatedstock"/></td>
										<td colspan="2" width="23%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.requests"/></td>
									</tr>
									<tr>
										<td width="50%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.recieved"/></td>
										<td width="50%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.placed"/></td>
									</tr>				 
								</thead>
								<tbody>
								<logic:present name="monitorStockHolderVO">

									<bean:define id="parentVO" name="monitorStockHolderVO" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO" />

												
									<tr>
										<td class="ic-center"> <ihtml:checkbox property="check"
			componentID="CHK_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CHECK"
													value="<%= ((MonitorStockVO)parentVO).getStockHolderCode() %>" /></td>
										<td class="iCargoTableDataTd">
											<logic:present name="parentVO" property="stockHolderType" >
												<bean:define id="stockHolderTypeChild" name="parentVO" property="stockHolderType" type="String"/>

												<logic:present name="stockHoldersFromSession">
														<bean:define id="stockHolderList" name="stockHoldersFromSession" />
														<logic:iterate id="priorityVO" name="stockHolderList" >
														  <logic:equal  name="priorityVO" property="stockHolderType" value="<%=stockHolderTypeChild%>">
																<bean:write name="priorityVO" property="stockHolderDescription"/>
															</logic:equal>
														</logic:iterate>
												</logic:present>
											</logic:present>
										</td>
										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="stockHolderCode"/></td>
										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="stockHolderName"/> </td>

										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="availableStock" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="allocatedStock" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="requestsReceived" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="parentVO" property="requestsPlaced" format="####"/></td>
									</tr>


								</logic:present>
								</tbody>
							</table>
						</div>
					</div>
					<div class="ic-row">
						<h4><common:message key="monitorstockdetails.distributiondetails"/></h4>
					</div>
					<div class="ic-row">
						<div class="ic-col-45">
							<logic:present name="monitorStockDetails" >
								<bean:define id="pageObj" name="monitorStockDetails"  />
								<common:paginationTag pageURL="stockcontrol.defaults.listmonitorstock.do"
										name="pageObj"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=monitorStockForm.getLastPageNum()%>" />
							</logic:present>					    
						</div>
						<div class="ic-col-55">
							<div class="ic-button-container paddR5">
								<logic:present name="monitorStockDetails" >
									<bean:define id="pageObj1" name="monitorStockDetails"  />
									<common:paginationTag
											linkStyleClass="iCargoLink"
											disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitPage('lastPageNum','displayPage')"
											name="pageObj1"
											display="pages"
											lastPageNum="<%=monitorStockForm.getLastPageNum()%>"
											exportToExcel="true"
											exportTableId="monitorStockTable"
											exportAction="stockcontrol.defaults.listmonitorstock.do"/>
								</logic:present>							
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1" style="height:380px">
							<table class="fixed-header-table" id="monitorStockTable">
								<thead>
									<tr >
										<td rowspan="2" width="2%" class="iCargoTableHeadingLeft"></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdertype"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdercode"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.stockholdername"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.availablestock"/></td>
										<td rowspan="2" width="15%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.allocatedstock"/></td>
										<td colspan="2" width="23%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.requests"/></td>
									</tr>
									<tr>
										<td width="50%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.recieved"/></td>
										<td width="50%" class="iCargoTableHeadingLeft"><common:message key="monitorstockdetails.placed"/></td>
									</tr>								  
								</thead>
								<tbody>
									<logic:present name="monitorStockDetails">
									<logic:iterate id="childVO" name="monitorStockDetails"
												type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO" indexId="rowCount" >
											
									<tr >
										<td class="ic-center"> <ihtml:checkbox property="check"
													componentID="CHK_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CHECK"
													value="<%= ((MonitorStockVO)childVO).getStockHolderCode() %>"
													/></td>
										<td class="iCargoTableDataTd">
											<logic:present name="childVO" property="stockHolderType">
											<bean:define id="stockHolderTypeChild" name="childVO" property="stockHolderType" type="String"/>
											<logic:present name="stockHoldersFromSession">
													<bean:define id="stockHolderList" name="stockHoldersFromSession" />
													<logic:iterate id="priorityVO" name="stockHolderList" >
													  <logic:equal  name="priorityVO" property="stockHolderType" value="<%=stockHolderTypeChild%>">
															<bean:write name="priorityVO" property="stockHolderDescription"/>
														</logic:equal>
													</logic:iterate>
											</logic:present>
											</logic:present>
										</td>
										<td class="iCargoTableDataTd"><bean:write name="childVO" property="stockHolderCode"/></td>
										<td class="iCargoTableDataTd"><bean:write name="childVO" property="stockHolderName"/></td>

										<td class="iCargoTableDataTd"><bean:write name="childVO" property="availableStock" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="childVO" property="allocatedStock" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="childVO" property="requestsReceived" format="####"/></td>
										<td class="iCargoTableDataTd"><bean:write name="childVO" property="requestsPlaced" format="####"/></td>
									</tr>					

									</logic:iterate>
									</logic:present>
								</tbody>
							</table>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnViewRange"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_VIEWRANGE"  accesskey="V">
						<common:message key="monitorstock.ViewRange"/>
					</ihtml:nbutton>

					<!--Added by A-3791 for ICRD-110168-->
					<ihtml:nbutton property="btnMonitor"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_MONITOR"  accesskey="M">
						<common:message key="monitorstock.Monitor"/>
					</ihtml:nbutton>	
					<logic:equal name="form" property="deleteButtonPrivilege" value="true">
						<ihtml:nbutton property="delete"
							componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_DELETE"  accesskey="D">
							<common:message key="monitorstock.Delete"/>
							</ihtml:nbutton>
					</logic:equal>
					<ihtml:nbutton property="return"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_RETURN"  accesskey="R">
						<common:message key="monitorstock.Return"/>
					</ihtml:nbutton>

					<ihtml:nbutton property="transfer"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_TRANSFER"   accesskey="T">
						<common:message key="monitorstock.Transfer"/>
					</ihtml:nbutton>

					<ihtml:nbutton property="btnCreateStock"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CREATESTOCK"  accesskey="E" >
						<common:message key="monitorstock.CreateStock"/>
					</ihtml:nbutton>

					<ihtml:nbutton property="btnAllocateStock"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_ALLOCATESTOCK"  accesskey="A" >
						<common:message key="monitorstock.AllocateStock"/>
					</ihtml:nbutton>

					<ihtml:nbutton property="btnCreateRequest"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CREATEREQUEST"  accesskey="Q">
						<common:message key="monitorstock.CreateRequest"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnPrint"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_PRINT"   accesskey="P">
						<common:message key="monitorstock.Print"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MONITORSTOCK_CLOSE"  accesskey="O">
						<common:message key="monitorstock.Close"/>
					</ihtml:nbutton>
				</div>
	
	</div>
	</div>
</ihtml:form>
</div>
	
				
		
	</body>
</html>

