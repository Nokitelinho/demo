	<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name            : Uld
* File Name			: ListExcessStockAirports.jsp
* Date				: 22-Oct-2012
* Author(s)			: A-2934
********************************************************************
--%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListExcessStockAirportsForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO"%>
<%@ page import="com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentForBookingVO"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		
	

	
	<title>
	<common:message bundle="listexcessstockairports" key="uld.defaults.stock.excessstockairport.lbl.listEstimatedULDStockTitle" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui" >
	<common:include type="script" src="/js/uld/defaults/ExcessStockAirport_Script.jsp"/>

</head>
<body>
	
	

	
	<bean:define id="form"
		name="ListExcessStockAirportsForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListExcessStockAirportsForm"
		toScope="page" />

	<business:sessionBean
		id="KEY_LISTESTIMATEDULDSTOCK"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.findairportswithexcessstock"
		method="get"
		attribute="excessStockAirportVOs" />

	<business:sessionBean
		id="KEY_LISTFLIGHTDETAILS"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.findairportswithexcessstock"
		method="get"
		attribute="flightSegmentForBookingVOs" />


	<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.stock.findairportswithexcessstock"
		method="get"
		attribute="oneTimeValues" />

	<logic:present name="KEY_LISTESTIMATEDULDSTOCK">
	  		<bean:define id="excessStockAirportVOs" name="KEY_LISTESTIMATEDULDSTOCK" toScope="page"/>
	  		<logic:present name="excessStockAirportVOs">
	  		<logic:iterate id="excessStockAirportVO" name="excessStockAirportVOs" type="ExcessStockAirportVO" indexId="index">
	  		<logic:present name="excessStockAirportVO" property="uldTypeCode">
				<bean:define id="accessoryCode1" name="excessStockAirportVO" property="uldTypeCode" />
			</logic:present>
	  		</logic:iterate>
	  	</logic:present>
	</logic:present>

	<logic:present name="KEY_LISTFLIGHTDETAILS">
	  		<bean:define id="flightSegmentForBookingVOs" name="KEY_LISTFLIGHTDETAILS" />
	  		<logic:present name="flightSegmentForBookingVOs">
	  		<logic:iterate id="flightSegmentForBookingVO" name="flightSegmentForBookingVOs" type="FlightSegmentForBookingVO" indexId="index">
	  		<logic:present name="flightSegmentForBookingVO" property="flightNumber">
				</logic:present>
	  		</logic:iterate>
	  	</logic:present>
	</logic:present>
	<div class="iCargoContent ic-masterbg" style="overflow:auto;" >
	<ihtml:form action="/uld.defaults.stock.findairportswithexcessstock.do"  >
		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="fltlastPageNum"/>
		<ihtml:hidden property="fltDisplayPage"/>
		
		

		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		
		 <div class="ic-content-main bg-white">
				<span class="ic-page-title ic-display-none">
					<common:message  key="uld.defaults.stock.excessstockairport.lbl.listEstimatedULDStockHeading" />
				</span>
					<div class="ic-main-container">
						<div  class="ic-row">
							
								<h3>
									<common:message  key="uld.defaults.stock.excessstockairport.lbl.listEstimatedULDStockLabel" />
								</h3>
							
						</div>
						
							<fieldset class="ic-field-set">
								<legend class="iCargoLegend">
									<common:message  key="uld.defaults.stock.excessstockairport.lbl.tableName" />
								</legend>
								
									
										<div class="ic-row">
										<div class="ic-input ic-split-30 ">
											<label>
												<common:message  key="uld.defaults.stock.excessstockairport.lbl.uldTypeCode" />
											</label>
											<logic:present name="LIST_FILTERVO" property="uldType">
											<ihtml:text name="LIST_FILTERVO" property="uldType" tabindex="4" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE"  maxlength="3" />
											</logic:present>
											<logic:notPresent name="LIST_FILTERVO" property="uldType">
											<ihtml:text property="uldType" tabindex="4" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE" maxlength="3" />
											</logic:notPresent>
										</div>
										<div class="ic-input ic-split-30 ">
											<label>
											<common:message  key="uld.defaults.stock.excessstockairport.lbl.operatingAirlineIdentifier" />
											</label>
											<logic:present name="LIST_FILTERVO" property="airlineCode">
												<bean:define id="airlineCode" name="LIST_FILTERVO" property="airlineCode"/>
												<ihtml:text property="airlinecode" tabindex="5" componentID="TXT_ULD_DEFAULTS_LISTULD_OPERATINGAIRLINE" value ="<%=airlineCode.toString()%>" maxlength="3" />
											</logic:present>
											<logic:notPresent name="LIST_FILTERVO" property="airlineCode">
												<ihtml:text property="airlinecode" tabindex="5" componentID="TXT_ULD_DEFAULTS_LISTULD_OPERATINGAIRLINE" maxlength="3" />
											</logic:notPresent>
										</div>
											<div class="ic-button-container paddR5">
												<logic:present name="excessStockAirportVOs">
		
													<common:paginationTag
													pageURL="javascript:submitPage('lastPageNum','displayPage')"
													name="KEY_LISTESTIMATEDULDSTOCK"
													display="pages"
													linkStyleClass="iCargoResultsLabel"
													disabledLinkStyleClass="iCargoResultsLabel"
													lastPageNum="<%=form.getLastPageNum()%>"
													exportToExcel="true"
													exportTableId="listexcessairportstocktable"
													exportAction="uld.defaults.stock.findairportswithexcessstock.do"/>
												</logic:present>
												<logic:notPresent name="estimatedULDStockVOs">
													&nbsp;
												</logic:notPresent>
											</div>
										</div>
										<div class="ic-row">
												<div class="tableContainer" id="div1" style="height:210px">
													<table  class="fixed-header-table" id="listexcessairportstocktable" style="width:100%;">
														<thead>
															<tr class="iCargoTableHeadingCenter" >
																<td width="3%" height="23px">&nbsp;</td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.airport"/>
																	 </td>
																	<td width="13%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.currentAvailability"/>
																	</td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.UcmUldIn"/>
																	</td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.UcmUldOut"/>
																	 </td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.projectedULDCount"/>
																	</td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.minimumQuantity"/>
																	 </td>
																	<td width="14%">
																	<common:message key="uld.defaults.stock.excessstockairport.lbl.stockDeviation"/>
																	</td>
															</tr>
														</thead>
																		<tbody>
			
																					<logic:present name="excessStockAirportVOs">
																						<logic:iterate id="excessStockAirportVO" name="excessStockAirportVOs" type="ExcessStockAirportVO"
																						indexId="rowCount" >

																						
																						 <tr>
																						<td><html:checkbox value="<%=String.valueOf(rowCount)%>" property="selectFlag" onclick="singleSelectCb(this.form,value,'selectFlag');"/><span></span></td>
																						<logic:present name="excessStockAirportVO" property="uldTypeCode">
																						<bean:define id="uldTypeCode" name="excessStockAirportVO" property="uldTypeCode" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="uldTypeCode"  value="<%=(String)uldTypeCode%>"/>
																								<bean:write name="excessStockAirportVO" property="uldTypeCode" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="uldTypeCode">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="uldTypeCode"  value=""/>
																							</td>
																						</logic:notPresent>
																						


																						<logic:present name="excessStockAirportVO" property="currentAvailability">
																						<bean:define id="currentAvailability" name="excessStockAirportVO" property="currentAvailability" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="currentAvailability"  value="<%=(String)currentAvailability%>"/>
																								<bean:write name="excessStockAirportVO" property="currentAvailability" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="currentAvailability">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="currentAvailability"  value=""/>
																							</td>
																						</logic:notPresent>



																						<logic:present name="excessStockAirportVO" property="ucmUldIn">
																						<bean:define id="ucmUldIn" name="excessStockAirportVO" property="ucmUldIn" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="ucmUldIn"  value="<%=(String)ucmUldIn%>"/>
																								<bean:write name="excessStockAirportVO" property="ucmUldIn" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="ucmUldIn">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="ucmUldIn"  value=""/>
																							</td>
																						</logic:notPresent>


																						<logic:present name="excessStockAirportVO" property="ucmUldOut">
																						<bean:define id="ucmUldOut" name="excessStockAirportVO" property="ucmUldOut" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="ucmUldOut"  value="<%=(String)ucmUldOut%>"/>
																								<bean:write name="excessStockAirportVO" property="ucmUldOut" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="ucmUldOut">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="ucmUldOut"  value=""/>
																							</td>
																						</logic:notPresent>


																						<logic:present name="excessStockAirportVO" property="projectedULDCount">
																						<bean:define id="projectedULDCount" name="excessStockAirportVO" property="projectedULDCount" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="projectedULDCount"  value="<%=(String)projectedULDCount%>"/>
																								<bean:write name="excessStockAirportVO" property="projectedULDCount" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="projectedULDCount">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="projectedULDCount"  value=""/>
																							</td>
																						</logic:notPresent>



																						<logic:present name="excessStockAirportVO" property="minimumQuantity">
																						<bean:define id="minimumQuantity" name="excessStockAirportVO" property="minimumQuantity" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="minimumQuantity"  value="<%=(String)minimumQuantity%>"/>
																								<bean:write name="excessStockAirportVO" property="minimumQuantity" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="minimumQuantity">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="minimumQuantity"  value=""/>
																							</td>
																						</logic:notPresent>


																						<logic:present name="excessStockAirportVO" property="stockDeviation">
																						<bean:define id="stockDeviation" name="excessStockAirportVO" property="stockDeviation" />
																							<td class="iCargoTableDataTd ic-center">
																								<html:hidden property="stockDeviation"  value="<%=(String)stockDeviation%>"/>
																								<bean:write name="excessStockAirportVO" property="stockDeviation" />
																							
																							</td>
																						</logic:present>
																						<logic:notPresent name="excessStockAirportVO" property="stockDeviation">
																							<td class="iCargoTableDataTd">
																								<html:hidden property="stockDeviation"  value=""/>
																							</td>
																						</logic:notPresent>
																						
																						
																						</tr>
																					
																					</logic:iterate>
																					</logic:present>
																					</div>
																		</tbody>
													</table>
												</div>
												<div class="ic-button-container paddR5">
													<ihtml:nbutton property="btFlightDetails" style="width:100px" componentID="BTN_ULD_DEFAULTS_LISTEXCESSSTOCK_DETAILS" >
														<common:message key="uld.defaults.stock.excessstockairport.btn.btExcessStock"/>
													</ihtml:nbutton>
												</div>
											</fieldset>	
											
											<fieldset class="ic-field-set" style="height:372px;overflow:auto;">
												<legend class="iCargoLegend">
													<common:message  key="uld.defaults.stock.excessstockairport.lbl.flightDetails" />
												</legend>
													<div class="ic-button-container">
														<logic:present name="flightSegmentForBookingVOs">
																<common:paginationTag
																pageURL="javascript:submitFltPage('lastPageNum','displayPage')"
																name="KEY_LISTFLIGHTDETAILS"
																display="pages"
																linkStyleClass="iCargoResultsLabel"
																disabledLinkStyleClass="iCargoResultsLabel"
																lastPageNum="<%=form.getFltlastPageNum()%>"
																exportToExcel="true"
																exportTableId="listshowflightdetailstable"
																exportAction="uld.defaults.stock.showFlightDetails.do?navigationMode=EXPORT"/>
															</logic:present>
															<logic:notPresent name="flightSegmentForBookingVOs">
																&nbsp;
															</logic:notPresent>
													</div>
													<div class="ic-row">
														<div class="table-container" id="div2" style="height:330px">
															<table class="fixed-header-table" id="listshowflightdetailstable">
																<thead>
																<tr class="ic-th-all">
																	<th style="width:6%"/>
																	<th style="width:6%"/>
																	<th style="width:6%"/>
																	<th style="width:9%"/>
																	<th style="width:9%"/>
																	<th style="width:8%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:4%"/>
																	<th style="width:8%"/>
																	
																</tr>
																	<tr class="iCargoTableHeadingCenter">
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.flightNumber"/>
																		<span></span> </td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.org"/>
																		<span></span></td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.destn"/>
																		<span></span></td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.depature"/>
																		<span></span> </td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.arrival"/>
																		<span></span> </td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.aircraftType"/>
																		<span></span> </td>
																		<td colspan="6">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.allotment"/>
																		<span></span> </td>
																		<td colspan="6">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.freesale"/>
																		<span></span> </td>
																		<td rowspan="2">
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.hasRestriction"/>
																		<span></span> </td>
																	</tr>
																	<tr>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.weight"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.volume"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.q7"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.q6"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.l7"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.l3"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.weight"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.volume"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.q7"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.q6"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.l7"/>
																		<span></span> </td>
																		<td>
																		<common:message key="uld.defaults.stock.excessstockairport.lbl.l3"/>
																		<span></span> </td>
																		</tr>
																</thead>
																<tbody>
																	<% String flightDate=""; %>
																	<% String flightArrDate="";%>
																	<logic:present name="flightSegmentForBookingVOs">
																			<logic:iterate id="flightSegmentForBookingVO" name="flightSegmentForBookingVOs" type="FlightSegmentForBookingVO"
																			indexId="rowCount" >
																			<tr>
																		<logic:present name="flightSegmentForBookingVO" property="flightNumber">
																			<bean:define id="flightCarrierCode"  name="flightSegmentForBookingVO" property="flightCarrierCode" />
																			<bean:define id="flightNumber" name="flightSegmentForBookingVO" property="flightNumber" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="flightCarrierCode" />
																					<bean:write name="flightSegmentForBookingVO" property="flightNumber" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="flightNumber">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="flightNumber"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="segmentOrigin">
																			<bean:define id="segmentOrigin" name="flightSegmentForBookingVO" property="segmentOrigin" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="segmentOrigin" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="segmentOrigin">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="segmentOrigin"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="segmentDestination">
																			<bean:define id="segmentDestination" name="flightSegmentForBookingVO" property="segmentDestination" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="segmentDestination" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="segmentDestination">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="segmentDestination"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="departureDateLeg">
																			<bean:define id="departureDateLeg" name="flightSegmentForBookingVO" property="departureDateLeg" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
																				<td class="iCargoTableDataTd ic-center">
																					<% flightDate=departureDateLeg.toDisplayFormat("dd-MMM, HH:mm"); %>
																					<%=flightDate%>
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="departureDateLeg">
																				<logic:present name="flightSegmentForBookingVO" property="estimatedDepartureDateLeg">
																			<bean:define id="estimatedDepartureDateLeg" name="flightSegmentForBookingVO" property="estimatedDepartureDateLeg" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
																				<td class="iCargoTableDataTd ic-center">
																					<% flightDate=estimatedDepartureDateLeg.toDisplayFormat("dd-MMM, HH:mm"); %>
																					<%=flightDate%>
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="estimatedDepartureDateLeg">
																				<td class="iCargoTableDataTd">
																				
																					<html:hidden property="departureDateLeg"  value=""/>
																				</td>
																			</logic:notPresent>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="arrivalDateLeg">
																			<bean:define id="arrivalDateLeg" name="flightSegmentForBookingVO" property="arrivalDateLeg" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
																				<td class="iCargoTableDataTd ic-center">
																					<% flightArrDate=arrivalDateLeg.toDisplayFormat("dd-MMM, HH:mm"); %>
																					<%=flightArrDate%>
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="arrivalDateLeg">
																			<logic:present name="flightSegmentForBookingVO" property="estimatedArrivalDateLeg">
																				<bean:define id="estimatedArrivalDateLeg" name="flightSegmentForBookingVO" property="estimatedArrivalDateLeg" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
																				<td class="iCargoTableDataTd ic-center">
																					<% flightArrDate=estimatedArrivalDateLeg.toDisplayFormat("dd-MMM, HH:mm"); %>
																					<%=flightArrDate%>
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="estimatedArrivalDateLeg">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="arrivalDateLeg"  value=""/>
																				</td>
																			</logic:notPresent>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="aircraftType">
																			<bean:define id="aircraftType" name="flightSegmentForBookingVO" property="aircraftType" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="aircraftType" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="aircraftType">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="aircraftType"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentWeight">
																			<bean:define id="availableAllotmentWeight" name="flightSegmentForBookingVO" property="availableAllotmentWeight" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentWeight" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentWeight">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentWeight"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentVolume">
																			<bean:define id="availableAllotmentVolume" name="flightSegmentForBookingVO" property="availableAllotmentVolume" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentVolume" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentVolume">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentVolume"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckOne">
																			<bean:define id="availableAllotmentLowerDeckOne" name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckOne" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckOne" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckOne">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentLowerDeckOne"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckTwo">
																			<bean:define id="availableAllotmentLowerDeckTwo" name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckTwo" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckTwo" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentLowerDeckTwo">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentLowerDeckTwo"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckOne">
																			<bean:define id="availableAllotmentUpperDeckOne" name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckOne" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckOne" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckOne">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentUpperDeckOne"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckTwo">
																			<bean:define id="availableAllotmentUpperDeckTwo" name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckTwo" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckTwo" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableAllotmentUpperDeckTwo">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableAllotmentUpperDeckTwo"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleWeight">
																			<bean:define id="availableFreesaleWeight" name="flightSegmentForBookingVO" property="availableFreesaleWeight" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleWeight" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleWeight">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleWeight"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleVolume">
																			<bean:define id="availableFreesaleVolume" name="flightSegmentForBookingVO" property="availableFreesaleVolume" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleVolume" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleVolume">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleVolume"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckOne">
																			<bean:define id="availableFreesaleLowerDeckOne" name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckOne" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckOne" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckOne">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleLowerDeckOne"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckTwo">
																			<bean:define id="availableFreesaleLowerDeckTwo" name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckTwo" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckTwo" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleLowerDeckTwo">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleLowerDeckTwo"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne">
																			<bean:define id="availableFreesaleUpperDeckOne" name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleUpperDeckOne"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckTwo">
																			<bean:define id="availableFreesaleUpperDeckOne" name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne" />
																				<td class="iCargoTableDataTd ic-center">
																					<bean:write name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne" />
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="availableFreesaleUpperDeckOne">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="availableFreesaleUpperDeckOne"  value=""/>
																				</td>
																			</logic:notPresent>
																			<logic:present name="flightSegmentForBookingVO" property="hasRestriction">
																			<bean:define id="hasRestriction" name="flightSegmentForBookingVO" property="hasRestriction" />
																				<td class="iCargoTableDataTd ic-center">
																				<logic:equal name="flightSegmentForBookingVO" property="hasRestriction" value="false">
																					<common:message key="uld.defaults.stock.excessstockairport.lbl.hasRestrictionValue.false"/>
																				</logic:equal>
																				<logic:equal name="flightSegmentForBookingVO" property="hasRestriction" value="true">
																						<common:message key="uld.defaults.stock.excessstockairport.lbl.hasRestrictionValue.true"/>
																				</logic:equal>
																				
																				</td>
																			</logic:present>
																			<logic:notPresent name="flightSegmentForBookingVO" property="hasRestriction">
																				<td class="iCargoTableDataTd">
																					<html:hidden property="hasRestriction"  value=""/>
																				</td>
																			</logic:notPresent>
																			</tr>
																			</logic:iterate>
																		</logic:present>
																		</tbody>
																</table>
														</div>
													
												</div>	
											</fieldset>
										</div>
					</div>
		<div class="ic-foot-container">
		<div class="ic-row">
													<div class="ic-button-container paddR5">
													<ihtml:nbutton property="btClose" tabindex="5" componentID="BTN_ULD_DEFAULTS_LISTACCESSORYSTOCK_CLOSE" >
														<common:message key="uld.defaults.stock.excessstockairport.btn.btClose"/>
													</ihtml:nbutton>
													</div>
														
												</div>	
										</div>
									
								
								
							
						
		 </div>
	</ihtml:form>
	</div>

		
				
		
	</body>
</html:html>

