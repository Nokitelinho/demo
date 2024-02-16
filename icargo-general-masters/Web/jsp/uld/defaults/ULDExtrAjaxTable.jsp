<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDExtrAjaxTable.jsp
* Date				: 14-July-2008
* Author(s)			: A-3093

****************************************************** --%>

			<%@ include file="/jsp/includes/tlds.jsp"%>
			<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>

			<%@ page import="java.util.Calendar"%>
			<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
			<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
			<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

			<ihtml:form	action="/uld.defaults.misc.listuldmovement.do">


			<business:sessionBean id="KEY_ULDMOVEMENTDTLS" moduleName="uld.defaults"
			screenID="uld.defaults.misc.listuldmovement" method="get"
			attribute="uldMovementDetails" />

			<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
				screenID="uld.defaults.misc.listuldmovement" method="get"
				attribute="oneTimeValues" />

			<bean:define id="form" name="listULDMovementForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
			toScope="page" />

		
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="transactionFlag" />

	<div class="ic-row">				
		
		<div id="_exter">
		<div class="ic-row">
		<div class="ic-input ic-split-50">
			<logic:present name="KEY_ULDMOVEMENTDTLS">				 
				 <common:paginationTag
				pageURL="uld.defaults.misc.listuldmovement.do"
				name="KEY_ULDMOVEMENTDTLS"
				display="label"
				labelStyleClass="iCargoLink"
				 lastPageNum="<%=form.getLastPageNum() %>" />
				</logic:present>
		</div>
		<div class="ic-input ic-split-50">
			<div class="ic-button-container">
				 
				<logic:present name="KEY_ULDMOVEMENTDTLS">
					<common:paginationTag
					pageURL="javascript:submitPage('lastPageNum','displayPage')"
					name="KEY_ULDMOVEMENTDTLS"
					display="pages"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					lastPageNum="<%=form.getLastPageNum()%>"
					exportToExcel="true"
					exportTableId="listuldtable"
					exportAction="uld.defaults.misc.listuldmovement.do"/>
					</logic:present>
				</div>
			</div>
		</div>
		
	
	<div id="div1" class="tableContainer" style="height:600px">
        <table data-ic-filter-table="true" id="listuldtable" class="fixed-header-table" >
	
		<thead>
			<tr class="ic-th-all">
							<!-- <th style="width:8%"/> --> 
						  <th style="width:12%" />
						  <th style="width:12%" />
						  <th style="width:12%" />
						  <th style="width:12%"/>
						  <th style="width:12%"/>
						  <th style="width:12%" />
						  <th style="width:12%"/>
						  <th style="width:16%"/>
						  
						</tr>
			<tr class="iCargoTableHeadingLeft">
				<td colspan="1" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter8"><common:message
					key="uld.defaults.misc.uldbld.lbl.FlightNo"
					scope="request" /> <span></span>
				</td>	
				<td colspan="1" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter9"><common:message
					key="uld.defaults.misc.uldbld.lbl.FlightDate"
					scope="request" /> <span></span>
				</td>										
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter1">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.Contents"
					scope="request" /><span></span>
				</td>
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter2">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.FromAirport"
					scope="request" /> <span></span>
				</td>
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter3">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.ToAirport"
					scope="request" /><span></span>
				</td>
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter4">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.MovementType"
					scope="request" /><span></span>
				</td>
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter5">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.MovementDate"
					scope="request" /><span></span>
				</td>									
				<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter6">
				<common:message
					key="uld.defaults.misc.listUldMovement.lbl.remarks"
					scope="request" /><span></span>
				</td>
			</tr>
			<!-- <tr class="iCargoTableHeaderLabel">
				<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter7"><common:message
					key="uld.defaults.misc.listUldMovement.lbl.CarrierCode"
					scope="request" /></td>
				<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter8"><common:message
					key="uld.defaults.misc.listUldMovement.lbl.No"
					scope="request" /></td>
				
				<td class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryParameter9"><common:message
					key="uld.defaults.misc.listUldMovement.lbl.FltDate"
					scope="request" /></td>
			</tr> --> 
		</thead>
		<tbody>
			<% int rowNum =0;%>
			<logic:present name="KEY_ULDMOVEMENTDTLS">
				<logic:iterate id="iterator" name="KEY_ULDMOVEMENTDTLS"
								type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO" indexId="index">

				<tr class="iCargoTableDataRow1">
					
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter8"><center>
						<logic:present	name="iterator" property="carrierCode">
						<logic:present	name="iterator" property="flightNumber">
								<%=iterator.getCarrierCode().concat(" ").concat(iterator.getFlightNumber())%>
									<!--bean:write name="iterator" property="flightCarrierCode"/-->
							</logic:present>
						</logic:present>
						</center>
					</td>
					 <!-- <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter8"><center>
						<logic:present	name="iterator" property="flightNumber">
						<bean:write name="iterator" property="flightNumber"/>
						</logic:present>
						</center>
					</td> -->
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter9"><center>
					    <logic:present	name="iterator" property="flightATA">
						<%
							String fltATA ="";
							if(iterator.getFlightATA() != null) {
							fltATA = TimeConvertor.toStringFormat(
										iterator.getFlightATA().toCalendar(),"dd-MMM-yyyy");
							}
						%>
						<%=fltATA%>
						</logic:present>
						 <logic:notPresent	name="iterator" property="flightATA">
						<logic:present	name="iterator" property="flightDate">
						<%
							String fltDate ="";
							if(iterator.getFlightDate() != null) {
							fltDate = TimeConvertor.toStringFormat(
										iterator.getFlightDate().toCalendar(),"dd-MMM-yyyy");
							}
						%>
						<%=fltDate%>
						</logic:present>
						</logic:notPresent>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter1"><center>
						<logic:present	name="iterator" property="content">
						 <%System.out.println("insideeeeeee");%>
						<bean:define id="content" name="iterator" property="content" />
						<logic:present name="oneTimeValues">
						 <%System.out.println("onetimeee");%>
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.contentcodes">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<%if(fieldValue.equals((String)content)){%>
											<%=(String)fieldDescription%>
												  <%}%>
									</logic:present>
								</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						</logic:present>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter2"><center>
						<logic:present	name="iterator" property="pointOfLading">
						<bean:write name="iterator" property="pointOfLading"/>
						</logic:present>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter3"><center>
						<logic:present	name="iterator" property="pointOfUnLading">
						<bean:write name="iterator" property="pointOfUnLading"/>
						</logic:present>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter4"><center>
						<logic:present	name="iterator" property="isDummyMovement">
						<logic:equal name="iterator" property="isDummyMovement" value="true" >
						<common:message key="uld.defaults.misc.listUldMovement.lbl.DummyMovement"/>
						</logic:equal>
						<logic:notEqual name="iterator" property="isDummyMovement" value="true" >
						<common:message key="uld.defaults.misc.listUldMovement.lbl.NormalMovement"/>
						</logic:notEqual>
						</logic:present>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter5"><center>
						<logic:present	name="iterator" property="lastUpdatedTime">
							<%
								String movtDate ="";
								if(iterator.getLastUpdatedTime() != null) {
								movtDate = TimeConvertor.toStringFormat(
											iterator.getLastUpdatedTime().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
								}
												%>
												<%=movtDate%>
						</logic:present>
						</center>
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryParameter6"><center>
						<logic:present	name="iterator" property="remark">
							<bean:write name="iterator" property="remark"/>
						</logic:present>
						</center>
					</td>
				</tr>
				<% rowNum++; %>
				</logic:iterate>
			</logic:present>
		</tbody>		
	</table>	
</div>
</div>
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
				
				
				

