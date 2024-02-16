<%@ include file="/jsp/includes/tlds.jsp" %>



<html:html>

	<head>
		
				
		
		<title>iCargo : Reservation Listing</title>
		<meta name="decorator" content="popuppanelrestyledui"/>
		<common:include type="script" src="/js/stockcontrol/defaults/cto/CancelReservationListing_Script.jsp"/>

	</head>
	
	<%
		response.setDateHeader("Expires",0);
		response.setHeader("Pragma","no-cache");

		if (request.getProtocol().equals("HTTP/1.1")) {
			response.setHeader("Cache-Control","no-cache");
		}
	%>


<body>
	
	
<bean:define id="form"
		name="ReservationListingForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm"
		toScope="page" />
		
<div class="iCargoPopUpContent" style="width:100%; overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.reservationlisting.CancelCommand.do" styleClass="ic-main-form">
		<ihtml:hidden property="errorStatus" />
		<html:hidden property="displayPage" />
		<html:hidden property="lastPageNum" />
		<html:hidden property="currentPageNum" />
		<html:hidden property="totalRecords" />
	
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				Cancel Reservation
			</span>
			
			<div class="ic-main-container">
				<div class="ic-input-container">
					<div class="ic-row">
						<div class="ic-button-container">
							<common:popuppaginationtag
									pageURL="javascript:fnToNavigate('lastPageNum','displayPage')"
									linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									displayPage="<%=form.getDisplayPage()%>"
									totalRecords="<%=form.getTotalRecords()%>" />
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-row">
								<div class="ic-input-container ic-input-round-border">
									<div class="ic-input ic-split-100 ic-label-20">
										<label>
											AWB
										</label>
										<ihtml:text property="popShipmentPrefix" componentID="CMP_StockControl_Defaults_Reservationlisting_AWB" readonly="true" maxlength="3"/>
										<ihtml:text property="popDocumentnumber" componentID="CMP_StockControl_Defaults_Reservationlisting_AWB" readonly="true" maxlength="6"/>
									</div>
								</div>
							</div>
							<div class="ic-row">
								<div class="ic-input-container ic-input-round-border">
									<div class="ic-input ic-split-100 ic-label-20">
										<label>
											Remarks
										</label>
										<ihtml:text property="cancelRemarks" componentID="CMP_StockControl_Defaults_Reservationlisting_Remarks" maxlength="20"/>
									</div>
								</div>	
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:button property="OkCancel" componentID="CMP_StockControl_Defaults_Reservationlisting_Ok" value = "Ok" >
						</ihtml:button>
						<ihtml:button property="PopupClose" componentID="CMP_StockControl_Defaults_ReservationListing_Close" value = "Close" >
						</ihtml:button>
					</div>
				</div>
			</div>
		</div>

	</ihtml:form>
</div>
		
	<script>
		checkError();
	</script>
			
		  
		   
	</body>
</html:html>


