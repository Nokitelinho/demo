<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Stock Control
* File Name				:  ConfirmStock.jsp
* Date					:  01-Oct-2015
* Author(s)				:  Ramesh Chandra Pradhan
*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>



<html:html>
	<head>
		
	
	
		<title>Confirm Stock</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/stockcontrol/defaults/ConfirmStock_Script.jsp" />

</head>
<body id="bodyStyle">
	

		<bean:define id="form"
			name="ConfirmStockForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm"
			toScope="page" />
	
		<business:sessionBean
			id="statusVO" 
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="status" />	
		<business:sessionBean
			id="operationVO"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="operation" />	
		<business:sessionBean
			id="prioritizedStockHolders"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="prioritizedStockHolders" />
		<business:sessionBean
			id="stockHolderFor"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="stockHolderFor" />

		<business:sessionBean id="partnerAirlines"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="partnerAirlines"/>
		<business:sessionBean
			id="map"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.confirmstock"
			method="get"
			attribute="map" />
		<business:sessionBean 
			id="transitStockVOs" 
			moduleName="stockcontrol.defaults" 
			screenID="stockcontrol.defaults.confirmstock" 
			method="get" attribute="transitStockVOs" />
	
	
		<%
			int transitStockCount=0;
		%>
		<logic:present name="transitStockVOs">
			<bean:define id="transitStockVOs" name="transitStockVOs" type="java.util.Collection" />
			<%transitStockCount=transitStockVOs.size();%>
		</logic:present>
		<logic:present name="prioritizedStockHolders" >
			<bean:define id="stockHoldersFromSession" name="prioritizedStockHolders"  />
		</logic:present>
		<logic:present name="stockHolderFor" >
			<bean:define id="stockHolderFors" name="stockHolderFor"  />
		</logic:present>
		<logic:present name="statusVO" >
			<bean:define id="statusOnetime" name="statusVO"  />
		</logic:present>
		<logic:present name="operationVO" >
			<bean:define id="operationOnetime" name="operationVO"  />
		</logic:present>

<div id="pageDiv" class="iCargoContent ic-masterbg" style="width:100%;overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.screenloadconfirmstock.do" >
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="hiddenOpFlag" />
		<ihtml:hidden property="popUpStatus" />
		
		<div class="ic-content-main bg-white">
			<span class="ic-page-title ic-display-none">
				<common:message bundle="confirmstockresources" key="confirmstock.title" />
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row margonT10">
							<div class="ic-input  ic-split-15">
								<label>
									<common:message bundle="confirmstockresources" key="confirmstock.stockholdertype"/>
								</label>
								<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_STOCKHOLDERTYPE"  title="Stock Holder Type">
									<logic:present name="stockHoldersFromSession">
										<bean:define id="stockHolderList" name="stockHoldersFromSession" />
										<logic:iterate id="priorityVO" name="stockHolderList" >
											<html:option value= "<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
												<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
											</html:option>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-15 ic-mandatory ">
								<label>
									<common:message bundle="confirmstockresources" key="confirmstock.StockHolderCode" />
								</label>
								<ihtml:text property="stockHolderCode" componentID="TXT_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_STOCKHOLDERCODE" maxlength="12" title="Stock Holder Code"/>
								<div class="lovImg">
								<img  src="<%=request.getContextPath()%>/images/lov.png" id="stockHolderCodeLov" /> 
							</div>
							</div>
							<div class="ic-input ic-split-21">
							
							<div class="ic-row  ">
								<logic:present name="map" >
									<bean:define id="maps" name="map" toScope="page" type="java.util.HashMap"/>
									<ibusiness:dynamicoptionlist collection="maps"
											id="docType"
											firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_DYNAMICOPTIONLIST"
											lastlistname="docSubType" firstoptionlabel="Doc. Type"
											lastoptionlabel="Sub Type"
											optionstyleclass="iCargoMediumComboBox"
											labelstyleclass="iCargoLabelRightAligned"
											firstselectedvalue="<%=form.getDocType()%>"
											lastselectedvalue="<%=form.getDocSubType()%>"
											docTypeTitle="doctype.tooltip"
											subDocTypeTitle="subdoctype.tooltip"/>
								</logic:present>
								<logic:notPresent  name="options">

								</logic:notPresent>
							</div>
							
							</div>
							<div class="ic-input ic-split-15 paddR14 ">
								<label>
									<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.operation"/>
								</label>
								<ihtml:select property="operation" componentID="CMB_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_OPERATION" title="Operation">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="operationOnetime" >
										<bean:define id="operationList" name="operationOnetime" />
										<logic:iterate id="operationoneTimeVO" name="operationList">
											<bean:define id="defaultValue" name="operationoneTimeVO" property="fieldValue" />
											<bean:define id="diaplayValue" name="operationoneTimeVO" property="fieldDescription" />
											<html:option value="<%=(String)defaultValue%>"><%=(String)diaplayValue%></html:option>
										</logic:iterate>
									</logic:present>
									<logic:notPresent name="operationOnetime" >
													
									</logic:notPresent>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-15 ">
								<label>
									<common:message bundle="confirmstockresources" key="confirmstock.fromdate" />
								</label>
								<ibusiness:calendar
										id = "fromDate"
										componentID = "TXT_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_FROMDATE"
										property="fromDate"
										type="image"
										textStyleClass="iCargoTextFieldMedium"
										value="<%=form.getFromDate()%>" title="From Date"/>
							</div>
							
							<div class="ic-input ic-split-15 ">
								<label>
									<common:message bundle="confirmstockresources" key="confirmstock.todate" />
								</label>
								<ibusiness:calendar
										id = "toDate"
										componentID = "TXT_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_TODATE"
										property="toDate"
										type="image"
										textStyleClass="iCargoTextFieldMedium"
										value="<%=form.getToDate()%>" title="To Date"/>
							</div>
						</div>
						<div class="ic-row marginT10">
							<div class="ic-input ic-split-15">
								<label>
									<common:message bundle="confirmstockresources" key="confirmstock.status" />
								</label>
								<ihtml:select property="status" componentID="CMB_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_STATUS" title="Status">
									<html:option value="ALL"><common:message bundle="confirmstockresources" key="confirmstock.all"/></html:option>
									<logic:present name="statusOnetime" >
										<bean:define id="statusList" name="statusOnetime" />
										<logic:iterate id="oneTimeVO" name="statusList">
											<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
											<bean:define id="diaplayValue" name="oneTimeVO" property="fieldDescription" />
											<html:option value="<%=(String)defaultValue%>"><%=(String)diaplayValue%></html:option>
										</logic:iterate>
									</logic:present>
									<logic:notPresent name="statusOnetime" >
									
									</logic:notPresent>
								</ihtml:select>
							</div>
						
							<div class="ic-input ic-split-15  ic_inline_chcekbox marginT15">
								<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CCONFIRMSTOCK_PRTARL_CHK" title="Partner Airline"/>
								<label>
									<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.partnerAirline.lbl" />
								</label>
							</div>
							<div class="ic-input ic-split-21  ">
								<label>
									<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.awbPrefix.lbl" />
								</label>
								<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_PRTARL_CMB" disabled='true' title="Awb Prefix">
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
							<div class="ic-input ic-split-33 ">
								<label>
									<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.partnerAirline.lbl"/>
								</label>
								<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_PRTARL" readonly="true" title="Partner Airline"/>
							</div>
								<div class="ic-input ic-split-15 ">
								<div class="ic-button-container">
									<ihtml:nbutton property="btList" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_LIST" accesskey ="L" title="List" >
										<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btclear" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_CLEAR" accesskey ="C" title="Clear" >
										<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-input-container">
					<div class="ic-row">
						<h4>Stock Details</h4>
					</div>
					<div class="ic-row">
					
		<div class="tableContainer" id="div2" style="width:100%; height:550px; overflow:auto;">
			<table class="fixed-header-table" id="detailsTable">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td style="width:5%" class="iCargoTableHeadingLeft"><input type="checkbox" id="checkall" name="chkBox" /></td>
						<td style="width:15%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="confirmstock.fromstockholder"/></td>
						<td style="width:12%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="confirmstock.rangefrom"/></td>
						<td style="width:12%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="confirmstock.rangeto"/></td>
						<td style="width:12%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="confirmstock.status"/></td>
						<td style="width:12%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.confirmstock.missingdate"/></td>
						<td style="width:12%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="confirmstock.operation"/></td>
						<td style="width:20%" class="iCargoTableHeadingLeft"><common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.confirmstock.remarks"/></td>
					</tr>
				</thead>
			<tbody id="confirmStock">
			
		<logic:present name="transitStockVOs">
			<logic:iterate id="transitStockVO" name="transitStockVOs" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO" indexId="checkedRowCount">

					<tr>
						<td class="iCargoTableDataTd">
							<div class="ic-center">
								<logic:present name="row">
									<bean:define id="chkedRow" name="row"/>
									<logic:equal name="chkedRow" value="<%=String.valueOf(checkedRowCount)%>">
										<input type="checkbox" name="checkRow" id="checkRow"  value="<%=String.valueOf(checkedRowCount)%>"  checked="true" />
									</logic:equal>
									<logic:notEqual name="chkedRow" value="<%=String.valueOf(checkedRowCount)%>">
										<input type="checkbox" name="checkRow"  value="<%=String.valueOf(checkedRowCount)%>"  />
									</logic:notEqual>
								</logic:present>
								<logic:notPresent name="row">
									<input type="checkbox" name="checkRow" id="checkRow" value="<%=String.valueOf(checkedRowCount)%>" />
								</logic:notPresent>
							</div>
						</td>
						<!--td><input type="checkbox" name="checkBox" /></td-->
						<td class="iCargoTableDataTd">
							<bean:write name="transitStockVO" property="stockControlFor" />
						</td>
						<td class="iCargoTableDataTd">
							<bean:write name="transitStockVO" property="missingStartRange" />
							<ihtml:hidden  property="startRange" value="<%=transitStockVO.getMissingStartRange()%>" />
						</td>
						<td class="iCargoTableDataTd">
							<bean:write name="transitStockVO" property="missingEndRange" />
							<ihtml:hidden property="endRange" value="<%=transitStockVO.getMissingEndRange()%>" />
						</td>
						<td class="iCargoTableDataTd" name="confirm" id="confirm">
							<logic:equal name="transitStockVO" property ="confirmStatus" value="N">
								<common:message bundle="confirmstockresources" key="stockcontrol.defaults.notconfirmed" />
							</logic:equal>
							<logic:equal name="transitStockVO" property ="confirmStatus" value="M">
								<common:message bundle="confirmstockresources" key="stockcontrol.defaults.missing" />
							</logic:equal>
						</td>
						<td class="iCargoTableDataTd">
							<logic:equal name="transitStockVO" property ="confirmStatus" value="M">
								<logic:present name="transitStockVO" property="txnDate">
									<bean:define id="txnDat" name="transitStockVO" property="txnDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
									<%String txnDate=TimeConvertor.toStringFormat(txnDat.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
									<%=txnDate%>
								</logic:present>
							</logic:equal>
						</td>
						<td class="iCargoTableDataTd">
							<logic:equal name="transitStockVO" property ="txnCode" value="A">
								<common:message bundle="confirmstockresources" key="stockcontrol.defaults.allocation" />
							</logic:equal>
							<logic:equal name="transitStockVO" property ="txnCode" value="R">
								<common:message bundle="confirmstockresources" key="stockcontrol.defaults.return" />
							</logic:equal>
							<logic:equal name="transitStockVO" property ="txnCode" value="T">
								<common:message bundle="confirmstockresources" key="stockcontrol.defaults.transfer" />
							</logic:equal>
						</td>
						<td class="iCargoTableDataTd">
							<bean:write name="transitStockVO" property="missingRemarks" />
						</td>
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
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btConfirm" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_CONFIRM" accesskey ="N" title="Confirm" >
							<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.confirm" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btMissing" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_MISSING" accesskey ="M" title="MissingStock">
							<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.missing" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btBlackList" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_BLACKLIST" accesskey ="B" title="Black Listed/Voided" >
							<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.blacklist" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BTN_STOCKCONTROL_DEFAULTS_CONFIRMSTOCK_CLOSE" accesskey ="O" title="Close" >
							<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.tooltip.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>

	</ihtml:form>
</div>
		
		
	</body>
</html:html>
