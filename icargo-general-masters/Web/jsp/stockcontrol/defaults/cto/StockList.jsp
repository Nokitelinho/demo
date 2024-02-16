<%--
* Project	 		: iCargo
* Module Code & Name: STK & StockControl
* File Name			: StockList.jsp
* Date				: 30-09-2015
* Author(s)			: A-5153
 --%>
 
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm"%>

<html:html>
	<head>
		
		
		<bean:define id="form" name="StockListForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm"/>
		<title><common:message bundle="stocklistresources" key="stocklist.page.title"/></title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/stockcontrol/defaults/cto/StockList_Script.jsp"/>
	</head>
<body>
	
	
<ibusiness:sessionBean id="documentValues" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.stocklist" method="get" attribute="documentValues" />
<ibusiness:sessionBean id="stockVOs" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.stocklist" method="get" attribute="stockVOs" />

<div class="iCargoContent ic-masterbg" >
	<%@ include file="/jsp/includes/reports/printFrame.jsp"%>
	<ihtml:form action="/stockcontrol.defaults.cto.screenloadstocklist.do">

		<ihtml:hidden name="StockListForm" property="isButtonClicked" />
		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="afterReload" />
		<ihtml:hidden property="forMessage" />
		<ihtml:hidden property="fromStockList" />
		<ihtml:hidden property="preview"/>
		<input type="hidden" name="mySearchEnabled" />
		
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
		</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-row">
					<div class="ic-input-container">
						<!--<fieldset class="iCargoFieldSet" >
							<legend class="iCargoLegend"><bean:message bundle="stocklistresources" key="stockcontrol.defaults.stocklist.search"/></legend>-->
						<div class="ic-row">
							<div class="ic-input ic-split-25 ic-label-35" style="margin-left:15px;">
								<label>
									<common:message  key="stockcontrol.defaults.stocklist.airline"/>
								</label>
								<ihtml:text property="airline" componentID="CMP_Stock_Defaults_StockList_Airline" />
							</div>
							<div class=" ic-input ic-split-35 ">
                                <div class=" ic-center  marginB5" >
								<logic:present name="documentValues">
									<bean:define id="map" name="documentValues" type="java.util.HashMap" toScope="page"/>
									<ibusiness:dynamicoptionlist 
											id="documentType" 
											collection="map" 
											firstlistname="documentType" 
											lastlistname="docSubType" 
											componentID= "CMP_StockControl_Defaults_StockList_DynamicOptionList" 
											firstoptionlabel="Doc. Type" 
											lastoptionlabel="Doc. SubType" 
											labelstyleclass="iCargoLabelRightAligned" 
											firstselectedvalue="<%=form.getDocumentType()%>" 
											lastselectedvalue="<%=form.getDocSubType()%>" 
											optionstyleclass="iCargoMediumComboBox" 
											docTypeTitle="stockcontrol.defaults.stocklist.tooltip.docTypeTitle" 
											subDocTypeTitle="stockcontrol.defaults.stocklist.tooltip.subDocTypeTitle"/>
								</logic:present>
                                </div>
                            </div>
							
								<div class="ic-button-container" style="margin-top:35px;">
									<ihtml:nbutton property="btList" componentID="CMP_Stock_Defaults_StockList_List" accesskey="L">
										<common:message key="stockcontrol.defaults.stocklist.list"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="CMP_Stock_Defaults_StockList_Clear" accesskey="C" >
										<common:message key="stockcontrol.defaults.stocklist.clear"/>
									</ihtml:nbutton>

								</div>
							
						</div>
						<!--</fieldset>-->	
					</div>
				</div>
			</div>
		</div>

		<div class="ic-main-container">
			<div class="ic-input-container">
				<div class="ic-row">
					<h4><bean:message bundle="stocklistresources" key="stockcontrol.defaults.stocklist.stock"/></h4>
				</div>
				<div class="ic-row">
					<div class="ic-col-45">
						<logic:present name="stockVOs">
							<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
								name="stockVOs"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-col-55">
						<div class="ic-button-container paddR5">
							<logic:present name="stockVOs">
								<common:paginationTag 
									linkStyleClass="iCargoLink" 
									pageURL="javascript:submitPage('lastPageNum','displayPage')" 
									name="stockVOs" 
									display="pages" 
									lastPageNum="<%=form.getLastPageNum()%>" 
									exportToExcel="true" 
									exportTableId="stocklisttable" 
									exportAction="stockcontrol.defaults.liststocks.do"/>
							</logic:present>
							<logic:notPresent name="stockVOs">
								&nbsp;
							</logic:notPresent>
						</div>
					</div>
				</div>
				<div class="ic-row">

	<div class="tableContainer" id="div1" style="width:100%; height:500px;">
		<table class="fixed-header-table" id="stocklisttable">
			<thead>
				<tr class="iCargoTableHeadingLeft">
					<td style="width:5%" class="iCargoTableHeaderLabel"><html:checkbox property="airlineValue" /><span></span></td>
					<td style="width:18%" class="iCargoTableHeaderLabel"><common:message key="stockcontrol.defaults.stocklist.airline"/><span></span></td>
					<td style="width:20%" class="iCargoTableHeaderLabel"><common:message key="stockcontrol.defaults.stocklist.doctype"/><span></span></td>
					<td style="width:20%" class="iCargoTableHeaderLabel"><common:message key="stockcontrol.defaults.stocklist.docsubtype"/><span></span></td>
					<td style="width:18%" class="iCargoTableHeaderLabel"><common:message key="stockcontrol.defaults.stocklist.totalstock"/><span></span></td>
					<td style="width:19%" class="iCargoTableHeaderLabel"><common:message key="stockcontrol.defaults.stocklist.reorderlevel"/><span></span></td>
				</tr>
			</thead>
			<tbody>
				<logic:present name="stockVOs">
					<logic:iterate id="stockVO" name="stockVOs" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO" indexId="nIndex">
					<tr>
						<bean:define id="airline" name="stockVO" property="airline" />
						<bean:define id="documentType" name="stockVO" property="documentType" />
						<bean:define id="documentSubType" name="stockVO" property="documentSubType" />
						<%String rowVal = String.valueOf(nIndex);%>

						<%String primKey = (String)airline+"-"+(String)documentType+"-"+(String)documentSubType;%>

						<td class="iCargoTableDataTd" ><div class="ic-center"><html:checkbox property="rowId" value="<%=(String)primKey%>" /></div></td>
						<td class="iCargoTableDataTd"><bean:write name="stockVO" property="airline" /></td>
						<td class="iCargoTableDataTd"><bean:write name="stockVO" property="documentType" /></td>
						<td class="iCargoTableDataTd"><bean:write name="stockVO" property="documentSubType" /></td>
						<td class="iCargoTableDataTd" style="text-align:right;"><bean:write name="stockVO" property="totalStock" format="#.##"/></td>
						<td class="iCargoTableDataTd" style="text-align:right;"><bean:write name="stockVO" property="reorderLevel" format="#.##"/></td>
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
			<div class="ic-row paddR5">
				<div class="ic-button-container">
					<ihtml:nbutton property="btViewRequest" componentID="CMP_Stock_Defaults_StockList_ViewRequest" accesskey ="W">
						<common:message key="stockcontrol.defaults.stocklist.viewrequest"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btCreateRequest" componentID="CMP_Stock_Defaults_StockList_CreateRequest" accesskey ="T">
						<common:message key="stockcontrol.defaults.stocklist.createrequest"/>
					</ihtml:nbutton>
					<!--<ihtml:nbutton property="btStockManager" componentID="CMP_Stock_Defaults_StockList_StockManager"><common:message  key="stockcontrol.defaults.stocklist.stockmanager"/></ihtml:nbutton>
					<ihtml:nbutton property="btView" componentID="CMP_Stock_Defaults_StockList_View"><common:message  key="stockcontrol.defaults.stocklist.view"/></ihtml:nbutton>-->
					<ihtml:nbutton property="btPrint" componentID="CMP_Stock_Defaults_StockList_Print" accesskey="P" >
						<common:message key="stockcontrol.defaults.stocklist.print"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="CMP_Stock_Defaults_StockList_Close" accesskey="O">
						<common:message key="stockcontrol.defaults.stocklist.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
	
			
	</ihtml:form>
</div>

	
		
	</body>
</html:html>


