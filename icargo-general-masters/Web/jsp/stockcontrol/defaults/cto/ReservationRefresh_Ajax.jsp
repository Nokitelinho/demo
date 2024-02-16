<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ibusiness:sessionBean
		id="reserveAWBVO"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.cto.reservestock"
		method="get" attribute="reserveAWBVO" />

<bean:define id="form"
		name="ReserveAWBForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm"/>


<common:include type="script" src="/js/stockcontrol/defaults/cto/Reservation_Script.jsp" />

<ihtml:form action="/stockcontrol.defaults.cto.reservestockscreenload.do">

	<ihtml:hidden property="isGeneral" />
	<ihtml:hidden property="afterReserve" />
	<ihtml:hidden property="shpPrefix" />
	
	<logic:present name="shpPrefix">
		<bean:define id="shpPrefix" name="form" property="shpPrefix"/>
	</logic:present>

	<table class="fixed-header-table" id="specificTab">
		<thead>
			<tr class="iCargoTableHeading">
				<td width="10%" class="iCargoTableHeaderLabel"><input type="checkbox" name="checkbox2" value="checkbox"></td>
				<td width="90%" class="iCargoTableHeaderLabel" colspan="2"><common:message  key="stockcontrol.defaults.reservation.lbl.awb"/></td>
			</tr>
		</thead>
		<tbody id="reservationTableBody">
			<!-- templateRow -->
			<tr template="true" id="reservationTemplateRow" style="display:none">
				<ihtml:hidden property="reservationOperationFlag" value="NOOP" />
				<td class="iCargoTableDataTd" >
					<html:checkbox property="rowId" />
				</td>
				<% String shipmentPfx  = form.getShpPrefix();%>
				<td class="iCargoTableDataTd" width="10%"><ihtml:text property="awbPrefix" componentID="CMP_Stock_Defaults_Reserve_AWP" value="<%=(String)shipmentPfx%>" readonly="true"/></td>
				<td class="iCargoTableDataTd"><ihtml:text property="awbNumber" componentID="CMP_Stock_Defaults_Reserve_AWB" value=""/></td>
			</tr>
			<!--template row ends-->
		</tbody>
	</table>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>


