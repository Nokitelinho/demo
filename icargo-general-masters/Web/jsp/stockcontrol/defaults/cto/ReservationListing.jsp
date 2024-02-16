<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>
	<head>
		
		
		<title>iCargo : Reservation Listing</title>
		<meta name="decorator" content="mainpanelrestyledui"/>
		<common:include type="script" src="/js/stockcontrol/defaults/cto/ReservationListing_Script.jsp"/>
	</head>

<body>
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<bean:define id="form"
	 name="ReservationListingForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm"
	 toScope="page" />

<business:sessionBean id="ReservationVOs"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.reservationlisting"
			method="get"
			attribute="ReservationVO" />
<business:sessionBean id="documentType"
			moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.reservationlisting"
			method="get"
			attribute="DocumentType" />

<div class="iCargoContent ic-masterbg" style="width:100%;overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.reservationlistingscreenload.do">
		<input type="hidden" name="mySearchEnabled" />
		<ihtml:hidden property="afterReload" />
		<ihtml:hidden property="preview" />
		<ihtml:hidden property="enableBtn" />
	
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">Reservation Listing</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-row">
					<div class="ic-input-container">
		                   <div class="ic-col">
							<h3>Search Criteria</h3>
							</div>			
							<div class="ic-row">
								<div class="ic-input ic-split-25">
									<div class="ic-row">
										<fieldset class="ic-field-set" style="width:270px;">
											<legend class="iCargoLegend">Reservation Date: </legend>
											<div class="ic-input ic-split-50 ic-label-30">
												<label>
													From
												</label>
												<ibusiness:calendar id="reservationFilterFromDate" property="reservationFilterFromDate" type="image" componentID="CMP_StockControl_Defaults_Reservationlisting_FromDate" value=""/>
											</div>
											<div class="ic-input ic-split-50 ic-label-30">
												<label>
													To
												</label>
												<ibusiness:calendar id="reservationFilterToDate" property="reservationFilterToDate" type="image" componentID="CMP_StockControl_Defaults_Reservationlisting_ToDate" value=""/>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="ic-input ic-split-15 ic-label-30" style="margin-left:40px;">
									<label>
										Airline
									</label>
									<ihtml:text property="airlineFilterCode" componentID="CMP_StockControl_Defaults_Reservationlisting_Airline" maxlength="2"/>
								</div>
								<div class="ic-input ic-split-15 ic-label-30">
									<label>
										Cust Code
									</label>
									<ihtml:text property="customerFilterCode" componentID="CMP_StockControl_Defaults_Reservationlisting_CusCode" maxlength="6"/>
								</div>
								<div class="ic-input ic-split-15 ic-label-30">
									<label>
										AWB Type
									</label>
									<ihtml:select styleClass="iCargosmallComboBox" property="documentFilterType" componentID="CMP_StockControl_Defaults_Reservationlisting_DocumentFilterType" style="width:90px;" >
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="documentType">
												<logic:iterate id="documentIterator" name="documentType" type="com.ibsplc.icargo.business.shared.document.vo.DocumentVO">
													<logic:present name="documentIterator" property="documentSubTypeDes">
														<bean:define id="fieldValue" name="documentIterator" property="documentSubTypeDes"/>
														<html:option value="<%=(String)fieldValue%>"><%=(String)fieldValue%></html:option>
													</logic:present>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-25">
							<div class="ic-row">
										<fieldset class="ic-field-set" style="width:270px;">
											<legend class="iCargoLegend">Expiry Date:</legend>
											<div class="ic-input ic-split-50 ic-label-30">
												<label>
													From
												</label>
												<ibusiness:calendar id="expiryFilterFromDate" property="expiryFilterFromDate" type="image" componentID="CMP_StockControl_Defaults_Reservationlisting_ExpiryFromDate" value=""/>
											</div>
											<div class="ic-input ic-split-50 ic-label-30">
												<label>
													To
												</label>
												<ibusiness:calendar id="expiryFilterToDate" property="expiryFilterToDate" type="image" componentID="CMP_StockControl_Defaults_Reservationlisting_ExpiryToDate" value=""/>
											</div>
										</fieldset>
									</div>	
								</div>
				
									<div class="ic-button-container">
										<ihtml:nbutton property="btnList" componentID="CMP_StockControl_Defaults_Reservationlisting_List" accesskey ="L" value = " List" >
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="CMP_StockControl_Defaults_Reservationlisting_Clear" accesskey ="C" value = "Clear" >
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
					<h4>AWB Reservation Details:</h4>
				</div>
				<div class="ic-row">
					
	<div class="tableContainer" id="div1" style="width:100%; height:550px;">
		<table class="fixed-header-table" id="reservationlisttable">
			<thead>
				<tr class="iCargoTableHeadingLeft">
					<td style="width:5%" class="iCargoTableHeaderLabel">
						<div class="ic-center">
							<input type="checkbox" name="checkbox2" value="checkbox" />
						</div>
						<span></span>
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
					<%int count=0;%>
					<logic:iterate id="vos" name="ReservationVOs" indexId="rowCount" >
						<tr>
							<td class="iCargoTableTd">
								<div class="ic-center">
									<input type="checkbox" name="rowId" value="<%=String.valueOf(rowCount)%>"/>
								</div>
							</td>

							<td class="iCargoTableDataTd">
								<bean:write name="vos" property="shipmentPrefix" /> &nbsp-&nbsp;
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
					<%count++;%>
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
					<ihtml:nbutton property="btnExtend" accesskey ="T" componentID="CMP_StockControl_Defaults_ReservationListing_Extend" value = "Extend Reservation " >
						</ihtml:nbutton>
					<ihtml:nbutton property="btnCancel" accesskey ="N" componentID="CMP_StockControl_Defaults_ReservationListing_Cancel" value = "Cancel Reservation " >
						</ihtml:nbutton>
					<ihtml:nbutton property="btnView" accesskey ="V" componentID="CMP_StockControl_Defaults_ReservationListing_View" value = "View" >
						</ihtml:nbutton>
					<ihtml:nbutton property="btnPrint" accesskey ="P" componentID="CMP_StockControl_Defaults_ReservationListing_Print" value = "Print" >
						</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" accesskey ="O" componentID="CMP_StockControl_Defaults_ReservationListing_Close" value = "Close" >
						</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>

	</ihtml:form>
</div>
  <span style="display:none" id="tmpSpan"></span>


	</body>
</html:html>


