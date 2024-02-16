	<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name            : Uld
* File Name			: ListEstimatedULDStock.jsp
* Date				: 22-Oct-2012
* Author(s)			: A-2934
********************************************************************
--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
		


	<title>
	<common:message bundle="listestimateduldstock" key="uld.defaults.stock.estimateduldstock.lbl.listEstimatedULDStockTitle" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui" >
	<common:include type="script" src="/js/uld/defaults/EstimatedULDStock_Script.jsp"/>

</head>
<body>
	
	

	
	
	
	<bean:define id="form"
		name="ListEstimatedULDStockForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm"
		toScope="page" />

	<business:sessionBean
		id="KEY_LISTESTIMATEDULDSTOCK"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listestimateduldstock"
		method="get"
		attribute="estimatedULDStockVOs" />

	<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listestimateduldstock"
		method="get"
		attribute="estimatedULDStockFilterVO" />


	<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.listestimateduldstock"
		method="get"
		attribute="oneTimeValues" />

	<logic:present name="KEY_LISTESTIMATEDULDSTOCK">
	  		<bean:define id="estimatedULDStockVOs" name="KEY_LISTESTIMATEDULDSTOCK" toScope="page"/>
	  		<logic:present name="estimatedULDStockVOs">
	  		<logic:iterate id="estimatedULDStockVO" name="estimatedULDStockVOs" type="EstimatedULDStockVO" indexId="index">
	  		<logic:present name="estimatedULDStockVO" property="uldTypeCode">
				<bean:define id="accessoryCode1" name="estimatedULDStockVO" property="uldTypeCode" />
			</logic:present>
	  		</logic:iterate>
	  	</logic:present>
	</logic:present>

	<div class="iCargoContent ic-masterbg">
	<ihtml:form action="/uld.defaults.stock.screenloadlistestimateduldstock.do"  >

		<ihtml:hidden property="stockLastPageNum"/>
		<ihtml:hidden property="stockdisplayPage"/>
		<ihtml:hidden property="listStatus"/>
		
		

		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />

		<div class="ic-content-main bg-white">
				<span class="ic-page-title ic-display-none">
					<common:message  key="uld.defaults.stock.estimateduldstock.lbl.listEstimatedULDStockHeading" />
				</span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row">
							<h4>
								<common:message  key="uld.defaults.stock.estimateduldstock.lbl.listEstimatedULDStockLabel" />
							</h4>
							
							</div>
							<div class="ic-row">
								<div class="ic-input ic-split-25  ic-mandatory">
									<label>
										<common:message  key="uld.defaults.stock.estimateduldstock.lbl.airport" />
									</label>
									<logic:present name="LIST_FILTERVO" property="airport">
										<bean:define id="stationCod" name="LIST_FILTERVO" property="airport"/>
										<ihtml:text property="airport" tabindex="2" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_STATION"  value ="<%=stationCod.toString()%>" maxlength="3"  />
									</logic:present>
									<logic:notPresent name="LIST_FILTERVO" property="airport">
										<ihtml:text property="airport" tabindex="2" componentID="TXT_ULD_DEFAULTS_LISTACCESSORYSTOCK_STATION" value ="<%=form.getAirport().toString()%>" maxlength="3"  />
									</logic:notPresent>
										<div class="lovImg">
										<img src="<%= request.getContextPath()%>/images/lov.png" tabindex="5" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].airport.value,'airport','1','airport','',0)" alt="Airportlov" />		
								</div>
								</div>
								<div class="ic-input ic-split-25 ">
									<label>
										<common:message  key="uld.defaults.stock.estimateduldstock.lbl.uldTypeCode" />
									</label>
												<logic:present name="LIST_FILTERVO" property="uldType">
									<ihtml:text name="LIST_FILTERVO" property="uldType" tabindex="4" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE"  maxlength="3" />
									</logic:present>
									<logic:notPresent name="LIST_FILTERVO" property="uldType">
									<ihtml:text property="uldType" tabindex="4" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE" value="" maxlength="3" />
									</logic:notPresent>
								 <div class="lovImg">
								 <img src="<%= request.getContextPath()%>/images/lov.png" tabindex="5" width="22" height="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldType.value,'uldType','1','uldType','')" alt="ULD TypeCode LOV" />
							  </div>
							  </div>
								<div class="ic-input ic-split-25  ic-mandatory">
									<label>
										<common:message  key="uld.defaults.stock.estimateduldstock.lbl.operatingAirlineIdentifier" />
									</label>
												<logic:present name="LIST_FILTERVO" property="airlineCode">
													<bean:define id="airlineCode" name="LIST_FILTERVO" property="airlineCode"/>
									<ihtml:text property="airlinecode" tabindex="6" componentID="TXT_ULD_DEFAULTS_LISTULD_OPERATINGAIRLINE" value ="<%=airlineCode.toString()%>" maxlength="3" />
									</logic:present>
									<logic:notPresent name="LIST_FILTERVO" property="airlineCode">
									<ihtml:text property="airlinecode" tabindex="6" componentID="TXT_ULD_DEFAULTS_LISTULD_OPERATINGAIRLINE" maxlength="3" />
									</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" tabindex="7" width="22" height="22"  onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlinecode.value,'Airline','1','airlinecode','',0)" alt="Airline LOV"/>
							  </div>
							  </div>
							  <div class="ic-button-container paddR5">
								<ihtml:nbutton property="btList" tabindex="8" componentID="BTN_ULD_DEFAULTS_LISTESTIMATEDULDSTOCK_LIST" >
								<common:message key="uld.defaults.stock.estimateduldstock.btn.btList"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" tabindex="9" componentID="BTN_ULD_DEFAULTS_LISESTIMATEDULDSTOCK_CLEAR" >
								<common:message key="uld.defaults.stock.estimateduldstock.btn.btClear"/>
								</ihtml:nbutton>
							  </div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="ic-main-container">
					<div class="ic-row">
					
						<h3>
							<common:message  key="uld.defaults.stock.estimateduldstock.lbl.tableName" />
						</h3>
					</div>
					<div class="ic-row">
					<div class="ic-col-96 paddR10">
					<div class="ic-button-container">
						<logic:present name="estimatedULDStockVOs">
				<common:paginationTag
				pageURL="javascript:submitPage('lastPageNum','displayPage')"
				name="KEY_LISTESTIMATEDULDSTOCK"
				display="pages"
				linkStyleClass="iCargoLink"
			    disabledLinkStyleClass="iCargoLink"
				lastPageNum="<%=form.getStockLastPageNum()%>"
				exportToExcel="true"
				exportTableId="listestimateduldtable"
				exportAction="uld.defaults.stock.findlistestimateduldstockcount.do"/>
			</logic:present>
			<logic:notPresent name="estimatedULDStockVOs">
			</logic:notPresent>
					</div>
					</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1"  style="width:100%;height:550px;" >
							<table  class="fixed-header-table" id="listestimateduldtable" style="width:100%;" >
								<thead>
								<tr>
								<td width="3%">&nbsp;</td>
								<td width="10%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.uldTypeCode"/>
								 </td>
								<td width="9%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.currentAvailability"/>
								</td>
								<td width="10%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.UcmUldIn"/>
								</td>
								<td width="9%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.UcmUldOut"/>
								 </td>
								<td width="9%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.projectedULDCount"/>
								 </td>
								<td width="10%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.maximumQuantity"/>
								 </td>
								<td width="10%" style="font-size:13px;">
								<common:message key="uld.defaults.stock.estimateduldstock.lbl.stockDeviation"/>
								 </td>
								 <td width="10%" style="font-size:13px;">
									<common:message key="uld.defaults.stock.estimateduldstock.lbl.minimumQuantity"/>
								 </td>
								 <td width="10%" style="font-size:13px;">
									<common:message key="uld.defaults.stock.estimateduldstock.lbl.minimumStockDeviation"/>
								 </td>
								</tr>
								</thead>
								<tbody>
			
				<logic:present name="estimatedULDStockVOs">
					<logic:iterate id="estimatedULDStockVO" name="estimatedULDStockVOs" type="EstimatedULDStockVO"
					indexId="rowCount" >

					
					 <tr>
					
					<td class="iCargoTableDataTd ic-center">
						<bean:define id="uldTypeCode" name="estimatedULDStockVO" property="uldTypeCode" />
						<html:checkbox property="select" value="<%=String.valueOf(rowCount)%>" onclick="singleSelectCb(this.form,value,'select');"/>
					</td>
					
					<logic:notPresent name="estimatedULDStockVO" property="uldTypeCode">
						<td class="iCargoTableDataTd ic-center">
							<html:checkbox  property="select" value="" />

						</td>

						<td class="iCargoTableDataTd">
							<html:hidden property="accCodesSelected"  value=""/>
						</td>
					</logic:notPresent>
					
					<logic:present name="estimatedULDStockVO" property="uldTypeCode">
					<bean:define id="uldTypeCode" name="estimatedULDStockVO" property="uldTypeCode" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="uldTypeCode"  value="<%=(String)uldTypeCode%>"/>
							<bean:write name="estimatedULDStockVO" property="uldTypeCode" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="uldTypeCode">
						<td class="iCargoTableDataTd">
							<html:hidden property="uldTypeCode"  value=""/>
						</td>
					</logic:notPresent>
					


					<logic:present name="estimatedULDStockVO" property="currentAvailability">
					<bean:define id="currentAvailability" name="estimatedULDStockVO" property="currentAvailability" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="currentAvailability"  value="<%=(String)currentAvailability%>"/>
							<bean:write name="estimatedULDStockVO" property="currentAvailability" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="currentAvailability">
						<td class="iCargoTableDataTd">
							<html:hidden property="currentAvailability"  value=""/>
						</td>
					</logic:notPresent>



					<logic:present name="estimatedULDStockVO" property="ucmUldIn">
					<bean:define id="ucmUldIn" name="estimatedULDStockVO" property="ucmUldIn" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="ucmUldIn"  value="<%=(String)ucmUldIn%>"/>
							<bean:write name="estimatedULDStockVO" property="ucmUldIn" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="ucmUldIn">
						<td class="iCargoTableDataTd">
							<html:hidden property="ucmUldIn"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="estimatedULDStockVO" property="ucmUldOut">
					<bean:define id="ucmUldOut" name="estimatedULDStockVO" property="ucmUldOut" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="ucmUldOut"  value="<%=(String)ucmUldOut%>"/>
							<bean:write name="estimatedULDStockVO" property="ucmUldOut" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="ucmUldOut">
						<td class="iCargoTableDataTd">
							<html:hidden property="ucmUldOut"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="estimatedULDStockVO" property="projectedULDCount">
					<bean:define id="projectedULDCount" name="estimatedULDStockVO" property="projectedULDCount" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="projectedULDCount"  value="<%=(String)projectedULDCount%>"/>
							<bean:write name="estimatedULDStockVO" property="projectedULDCount" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="projectedULDCount">
						<td class="iCargoTableDataTd">
							<html:hidden property="projectedULDCount"  value=""/>
						</td>
					</logic:notPresent>



					<logic:present name="estimatedULDStockVO" property="maximumQuantity">
					<bean:define id="maximumQuantity" name="estimatedULDStockVO" property="maximumQuantity" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="maximumQuantity"  value="<%=(String)maximumQuantity%>"/>
							<bean:write name="estimatedULDStockVO" property="maximumQuantity" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="maximumQuantity">
						<td class="iCargoTableDataTd">
							<html:hidden property="maximumQuantity"  value=""/>
						</td>
					</logic:notPresent>


					<logic:present name="estimatedULDStockVO" property="stockDeviation">
					<bean:define id="stockDeviation" name="estimatedULDStockVO" property="stockDeviation" />
						<td class="iCargoTableDataTd ic-center">
							<html:hidden property="stockDeviation"  value="<%=(String)stockDeviation%>"/>
							<bean:write name="estimatedULDStockVO" property="stockDeviation" />
						
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="stockDeviation">
						<td class="iCargoTableDataTd">
							<html:hidden property="stockDeviation"  value=""/>
						</td>
					</logic:notPresent>
					<!-- Added by A-6770 for ICRD-152501 starts here -->
						<logic:present name="estimatedULDStockVO" property="minimumQuantity">
					<bean:define id="minimumQuantity" name="estimatedULDStockVO" property="minimumQuantity" />
						<td class="iCargoTableDataTd"><center>
							<html:hidden property="minimumQuantity"  value="<%=(String)minimumQuantity%>"/>
							<bean:write name="estimatedULDStockVO" property="minimumQuantity" />
						</center>
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="minimumQuantity">
						<td class="iCargoTableDataTd">
							<html:hidden property="minimumQuantity"  value=""/>
						</td>
					</logic:notPresent>
					<logic:present name="estimatedULDStockVO" property="minimumStockDeviation">
					<bean:define id="minimumStockDeviation" name="estimatedULDStockVO" property="minimumStockDeviation" />
						<td class="iCargoTableDataTd"><center>
							<html:hidden property="minimumStockDeviation"  value="<%=(String)minimumStockDeviation%>"/>
							<bean:write name="estimatedULDStockVO" property="minimumStockDeviation" />
						</center>
						</td>
					</logic:present>
					<logic:notPresent name="estimatedULDStockVO" property="minimumStockDeviation">
						<td class="iCargoTableDataTd">
							<html:hidden property="minimumStockDeviation"  value=""/>
						</td>
					</logic:notPresent>
					<!-- Added by A-6770 for ICRD-152501 starts here -->
					</tr>
				
				</logic:iterate>
				</logic:present>
				</tbody>
							</table>
						</div>
					</div>
					</div>
					<div class="ic-foot-container">
						<div class="ic-button-container paddR5">
							<ihtml:nbutton property="btDetails" componentID="BTN_ULD_DEFAULTS_LISTEXCESSSTOCK_DETAILS" >
						<common:message key="uld.defaults.stock.estimateduldstock.btn.btExcessStock"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" tabindex="5" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_CLOSE" >
						<common:message key="uld.defaults.stock.estimateduldstock.btn.btClose"/>
						</ihtml:nbutton>
						</div>
					</div>
					</div>
	</ihtml:form>
	</div>

	


	
				
		
	</body>
</html:html>

