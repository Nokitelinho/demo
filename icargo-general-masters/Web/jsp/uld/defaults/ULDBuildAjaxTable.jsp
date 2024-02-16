<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDBuildAjaxTable.jsp
* Date				: 14-July-2008
* Author(s)			: A-3093

****************************************************** --%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
	
<ihtml:form action="/uld.defaults.listbuildupbreakdowndetails.do">

<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="transactionFlag" />
<ihtml:hidden property="isUldValid"/>
<business:sessionBean id="KEY_BREAKDOWNDEATILS" moduleName="uld.defaults"
			screenID="uld.defaults.misc.listuldmovement" method="get"
			attribute="operationalULDAuditVO" />
			
<bean:define id="form" name="listULDMovementForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
			toScope="page" />			
<div class="ic-row">				
		
		<div id="_build">
		<div class="ic-row">
			<div class="ic-col">
			  <logic:present name="KEY_BREAKDOWNDEATILS">					
				     <common:paginationTag
					pageURL="uld.defaults.listbuildupbreakdowndetails.do"
					name="KEY_BREAKDOWNDEATILS"
					display="label"
					labelStyleClass="iCargoLink"
				     lastPageNum="<%=form.getLastPageNum() %>" />
				    </logic:present>
		</div>
		<div class="ic-right paddR5">
			<div class="ic-button-container">	
			    <logic:present name="KEY_BREAKDOWNDEATILS">
				    <common:paginationTag
					pageURL="javascript:submitPage('lastPageNum','displayPage')"
					name="KEY_BREAKDOWNDEATILS"
					display="pages"
					linkStyleClass="iCargoResultsLabel"
				    disabledLinkStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNum()%>"
					exportToExcel="true"
					exportTableId="break"
					exportAction="uld.defaults.listbuildupbreakdowndetails.do"/>
					</logic:present>			    
			    </div>
			</div>
		</div>
		<div id="div1" class="tableContainer" style="height:610px">
        <table id="break" data-ic-filter-table="true" class="fixed-header-table" >
	
				<thead>
					<tr class="ic-th-all">
							<!-- <th style="width:15%"/> -->
						  <th style="width:20%" />
						  <th style="width:20%" />
							 <th style="width:15%"/>
						  <th style="width:20%"/>
						  <th style="width:25%"/>
						 
						  
						  						  
						</tr>
					<tr class="iCargoTableHeadingLeft">
						
						<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter1">
							<common:message key="uld.defaults.misc.uldbld.lbl.Date" scope="request"/><span></span>
							
						</td>
						<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter2">
							<common:message key="uld.defaults.misc.uldbld.lbl.Status" scope="request"/><span></span>
							
						</td>
						<!-- <td colspan="3" class="iCargoTableHeader">
							<common:message key="uld.defaults.misc.uldbld.lbl.FlightDetails" scope="request"/><span></span>
							
						</td> -->
						<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter4">
								<common:message key="uld.defaults.misc.uldbld.lbl.FlightNo" scope="request"/><span></span>
						</td>
						<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter5" >
								<common:message key="uld.defaults.misc.uldbld.lbl.FlightDate" scope="request"/><span></span>
				        </td>
						<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter3">
								<common:message key="uld.defaults.misc.uldbld.lbl.Remarks" scope="request"/><span></span>
								
						</td>				
					</tr>		
					<!-- <tr class="iCargoTableHeaderLabel">
				        <td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter4" >
				        <common:message key="uld.defaults.misc.uldbld.lbl.CarrierCode" scope="request"/><span></span>
				        </td>
				        <td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter5">
				        <common:message key="uld.defaults.misc.uldbld.lbl.No" scope="request"/><span></span>
				        </td>
						
						<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter6" >
				        <common:message key="uld.defaults.misc.uldbld.lbl.FltDate" scope="request"/><span></span>
				        </td>
						
				  </tr>	-->				
				</thead>
				<tbody>				
				<logic:present name="KEY_BREAKDOWNDEATILS"> 				
					<logic:iterate id="iterator" name="KEY_BREAKDOWNDEATILS"
							type="com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO" indexId="index">
								
							<tr class="iCargoTableDataRow1">
								
								<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter1"><center>
									<logic:present name="iterator" property="lastUpdateTime">
											<%=iterator.getLastUpdateTime().toDisplayFormat()%>	
									</logic:present>
									</center>
								</td>
								<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter2"><center>
									<logic:present name="iterator" property="status">
									<bean:write name="iterator" property="status" />
									</logic:present>
									</center>
								</td>
								<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter4"><center>
									<logic:present	name="iterator" property="flightCarrierCode">
									<logic:present	name="iterator" property="flightNumber">
									<%=iterator.getFlightCarrierCode().concat(" ").concat(iterator.getFlightNumber())%>
									<!--bean:write name="iterator" property="flightCarrierCode"/-->
									</logic:present>
									</logic:present>
									</center>
								</td>										
								<!-- <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter5">
								<center>
									<logic:present	name="iterator" property="flightNumber">
									<bean:write name="iterator" property="flightNumber"/>
									</logic:present>
									</center>
								</td>			-->							
								<!--<td class="iCargoTableDataTd" ><center>
									<logic:present	name="iterator" property="flightTailNumber">
									<bean:write name="iterator" property="flightTailNumber"/>
									</logic:present>
								</center>
								</td>-->										
								<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter5"><center>
									<logic:present	name="iterator" property="flightDate">									
									<%
											String flightDate ="";
											if(iterator.getFlightDate() != null) {
											flightDate = TimeConvertor.toStringFormat(
												iterator.getFlightDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
											}
											%>
											<%=flightDate%>
									</logic:present>
								</center>
								</td>										
								<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter3"><center>
									<logic:present name="iterator" property="remarks">
									<bean:write name="iterator" property="remarks" />
									</logic:present>
									</center>
								</td>
						</tr>
					</logic:iterate>
				</logic:present>
			</tbody>
		</table>
</div>
</div>
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
				

