<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<business:sessionBean id="ReservationVOs"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.reservationlisting"
			method="get"
			attribute="ReservationVO" />


<ihtml:form action="/stockcontrol.defaults.reservationlistingscreenload.do">
	<div class="tableContainer" id="resTableDiv_Temp" style="width:100%; height:650px;">	
		<table class="fixed-header-table" id="reservationlisttable">
			 <thead>
				<tr class="iCargoTableHeadingLeft">
					<td style="width:5%" class="iCargoTableHeaderLabel">
						<input type="checkbox" name="checkbox2" value="checkbox" />
					</td>

					<td style="width:16%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.awb"/><span></span>
					</td>
					<td style="width:15%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.type"/><span></span>
					</td>
					<td style="width:15%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.cuscode"/><span></span>
					</td>
					<td style="width:16%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.resDate"/><span></span>
					</td>
					<td style="width:16%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.expiryDate"/><span></span>
					</td>
					<td style="width:17%" class="iCargoTableHeaderLabel">
						<common:message key="icargo.stockcontrol.defaults.reservationlisting.lbl.remarks"/><span></span>
					</td>
				</tr>
			</thead>
			
			<tbody>
				<logic:present name="ReservationVOs" >
					<logic:iterate id="vos" name="ReservationVOs" indexId="rowCount" >
						<tr>
							<td class="iCargoTableTd">
								<div class="ic-center">
									<input type="checkbox" name="rowId" value="<%=String.valueOf(rowCount)%>"/>
								</div>
							</td>
							<td class="iCargoTableDataTd">
								<bean:write name="vos" property="shipmentPrefix" />
							</td>
							<td class="iCargoTableDataTd">
								<bean:write name="vos" property="documentNumber" />
							</td>
							<td class="iCargoTableDataTd">
								<bean:write name="vos" property="documentType" />
							</td>
							<td class="iCargoTableDataTd" >
								<bean:write name="vos" property="customerCode" />
							</td>
							<td class="iCargoTableDataTd">
								<bean:define id="cDate" name="vos" property="reservationDate" />
								<% String rTime = TimeConvertor.toStringFormat(((LocalDate)cDate).toCalendar(),"dd-MMM-yyyy"); %>
								<%= rTime%>
							</td>
							<td class="iCargoTableDataTd">
								<bean:define id="cDate" name="vos" property="expiryDate" />
								<% String eTime = TimeConvertor.toStringFormat(((LocalDate)cDate).toCalendar(),"dd-MMM-yyyy"); %>
								<%= eTime%>
							</td>
							<td class="iCargoTableDataTd">
								<bean:write name="vos" property="reservationRemarks" />
							</td>
						</tr>		
					</logic:iterate>
				</logic:present>
			</tbody>
		</table>
	</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>


