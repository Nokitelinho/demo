<%--
***************************************************************
* Project	 			: iCargo
* Module Code & Name	: ReserveAWB
* File Name				: icargo_stockcontrol_reservation.jsp
* Date					: 28-09-2015
* Author(s)				: A-5153
***************************************************************
--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>
	<head>
		
	
		
		 <meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/stockcontrol/defaults/cto/Reservation_Script.jsp"/>
		<bean:define id="form" name="ReserveAWBForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm"/>
		<title>
			<common:message bundle="requeststockresources" key="reservation.page.title"/>
		</title>
	</head>
	
<body>

	<ibusiness:sessionBean id="aWBTypes" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.reservestock" method="get" attribute="aWBTypes" />
	<ibusiness:sessionBean id="reserveAWBVO" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.reservestock" method="get" attribute="reserveAWBVO" />
	
	
<div class="iCargoContent ic-masterbg-white ic-center " style="height:500px; width:50%; overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.cto.reservestockscreenload.do">
		<ihtml:hidden property="isGeneral" />
		<ihtml:hidden property="afterReserve" />
		<ihtml:hidden property="shpPrefix" />
		<logic:present name="shpPrefix">
			<bean:define id="shpPrefix" name="form" property="shpPrefix"/>
		</logic:present>
		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="stockcontrol.defaults.reservation.title"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-row">	
								<div class="ic-input ic-split-50 ic-mandatory ic-label-25">
									<label>
										<common:message key="stockcontrol.defaults.reservation.lbl.ariline"/>
									</label>
									<ihtml:text property="airline" componentID="CMP_Stock_Defaults_Reserve_Airline" />
								</div>
								<div class="ic-input ic-split-50 ic-mandatory ic-label-25">
									<label>
										<common:message key="stockcontrol.defaults.reservation.lbl.awbtyp"/>
									</label>
									<html:select  property="awbType" styleClass="iCargoComboBox">
										<logic:present name="aWBTypes">
											<logic:iterate id="aWBTypes" name="aWBTypes">
												<html:option value="<%=(String)aWBTypes%>"><%=(String)aWBTypes%></html:option>
											</logic:iterate>
										</logic:present>
									</html:select>
								</div>	
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="ic-main-container">
				<div class="ic-row">
					<div class="ic-input-container ic-input-round-border" style="margin:6px;">
						<div class="ic-row">	
							<div class="ic-input ic-split-35 ic-mandatory ic-label-40">
								<label>
									<common:message key="stockcontrol.defaults.reservation.lbl.custcode"/>
								</label>
								<ihtml:text property="custCode" componentID="CMP_Stock_Defaults_Reserve_CustomerCode" />
								<div class="lovImg valignT"><img src="<%=request.getContextPath()%>/images/lov.png" id="cuslovImage" /></div>
							</div>	
						
							<div class="ic-input ic-split-35 ic-label-40">
								<label>
									<common:message key="stockcontrol.defaults.reservation.lbl.expdate"/>
								</label>
								<ibusiness:calendar id="expirydate" property="expiryDate" type="image" componentID="CMP_Stock_Defaults_Reserve_ExpiryDate" value="<%=form.getExpiryDate()%>"/>
							</div>	
										
							<div class="ic-input ic-split-30 ic-mandatory ic-label-40">
								<label>
									<common:message key="stockcontrol.defaults.reservation.lbl.rmk"/>
								</label>
								<ihtml:text property="remarks" componentID="CMP_Stock_Defaults_Reserve_Remarks" style="width:120px;" />
							</div>
						</div>
					</div>

				<div class="ic-row">
					<div class="ic-input-container ic-border">
						<div class="ic-row ic-inline-label">
							<div class="ic-input ic-label-25">
								<html:checkbox property="general" value="true" />
								<label>
									<common:message key="stockcontrol.defaults.reservation.lbl.general"/>
								</label>
							</div>
						</div>
						<div class="ic-row marginT5">
						<div class="ic-center ic-border" style="width:95%;">
								<div class="ic-input ic-split-85 ic-label-45">
									<label>
										<common:message key="stockcontrol.defaults.reservation.lbl.noofawb"/>
									</label>
									<ihtml:text property="totAwb" componentID="CMP_Stock_Defaults_Reserve_NoofDocs" />
								</div>
							</div>
						</div>
						<div class="ic-row ic-inline-label marginT5">
							<div class="ic-input ic-label-25">
								<html:checkbox property="specific" value="true"/>
								<label>
									<common:message key="stockcontrol.defaults.reservation.lbl.specific"/>
								</label>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input-container">
								<div class="ic-center" style="width:60%;">
									<div class="ic-row">
										<div class="ic-button-container">
											<ul class="ic-list-link">								
												<li>
													<a href="#" class="iCargoLink" onclick="addrow()">
														<common:message key="stockcontrol.defaults.reservation.lbl.lnkadd"/>
													</a>
												</li> |
												<li>
													<a href="#" class="iCargoLink" onclick="deleterow()">
														<common:message key="stockcontrol.defaults.reservation.lbl.lnkdelete"/>
													</a>
												</li>										
											</ul>
										</div>
									</div>
									<div class="ic-row">
									
									<div class="tableContainer" style="height:120px;" id="div1">
										<table class="fixed-header-table" id="specificTab">
											<thead>
												<tr class="iCargoTableHeading">
													<td style="width:10%" class="iCargoTableHeaderLabel">
														<input type="checkbox" name="checkbox2" value="checkbox">
													</td>
													<td style="width:90%" class="iCargoTableHeaderLabel" colspan="2">
														<common:message key="stockcontrol.defaults.reservation.lbl.awb"/>
													</td>
												</tr>
											</thead>
											<tbody id="reservationTableBody">
												<!-- templateRow -->
												<tr template="true" id="reservationTemplateRow" style="display:none">
													<ihtml:hidden property="reservationOperationFlag" value="NOOP" />
													<td class="iCargoTableDataTd">
														<div class="ic-center">
															<html:checkbox property="rowId" />
														</div>
													</td>
													<% String shipmentPfx = form.getShpPrefix();%>
													<td class="iCargoTableDataTd">
														<ihtml:text property="awbPrefix" componentID="CMP_Stock_Defaults_Reserve_AWP" value="<%=(String)shipmentPfx%>" readonly="true"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="awbNumber" componentID="CMP_Stock_Defaults_Reserve_AWB" value=""/>
													</td>
												</tr>
												<!--template row ends-->
											</tbody>
										</table>
									</div>
									
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btReserve" componentID="CMP_Stock_Defaults_Reserve_Reserve" accesskey="R">
							<common:message  key="stockcontrol.defaults.reservation.btn.lbl.reserve"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btClear" componentID="CMP_Stock_Defaults_Reserve_Clear" accesskey="C">
							<common:message  key="stockcontrol.defaults.reservation.btn.lbl.clear"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="CMP_Stock_Defaults_Reserve_Close" accesskey="O">
							<common:message  key="stockcontrol.defaults.reservation.btn.lbl.close"/>
						</ihtml:nbutton>		 
					</div>
				</div>
			</div>
	
		
		
	</ihtml:form>
		<span style="display:none" id="tmpSpan"></span>
</div>
	
		
		
	</body>
</html:html>


