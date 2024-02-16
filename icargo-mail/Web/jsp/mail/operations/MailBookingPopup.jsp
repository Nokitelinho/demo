<%--
* Project	 	: iCargo
* Module Code & Name	: Mail
* File Name		: MailBookingPopUp.jsp
* Date			: 28-Jun-2017
* Author(s)		: ArunimaUnnikrishnan
 --%>
 
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO"%>


		
		<!DOCTYPE html>			
	
<html:html>

<head> 
		
	 
	
	<title><common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.mailbooking.lbl.pagetitle" /></title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/mail/operations/MailbookingPopup_Script.jsp" />
</head>

<body>
	<bean:define id="form"
             name="MailbookingPopupForm"
             type="com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm"
             toScope="page" scope="request"/>
	
    
<business:sessionBean id="oneTimeValues" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="oneTimeVOs" />
<business:sessionBean id="mailClassCollection" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="mailbagVOsCollection" />
<business:sessionBean id="ONETIME_SHIPMENT_STATUS" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="bookingStatus" />
<business:sessionBean id="LIST_MAL_COLLECTION" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="mailBookingDetailsVOs" />
<div  class="iCargoPopUpContent ic-masterbg">
<ihtml:form action="/mailtracking.defaults.listconsignment.mailbookingpopup.screenload.do" styleClass="ic-main-form">
<ihtml:hidden  property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="totalViewRecords"/>
<ihtml:hidden property="selectedMailbagId"/>
<ihtml:hidden property="popUpFlag"/>
<ihtml:hidden property="actionName"/><!--added by A-7371 as part of ICRD-228233-->
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="consignmentDocument" />
<ihtml:hidden property="destMismatchFlag" />
<ihtml:hidden property="selectedAwbIndex" />

<div class="ic-content-main" >
<div class="ic-head-container">
		<div class="ic-filter-panel" >
		<div class="ic-row" style="margin-top:15px;">
		<div class="ic-input ic-col-22 ">
                                  
								  <ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" isCheckDigitMod="false"   componentID="CMP_MailTracking_Defaults_CarditEnquiry_AWB_No" />
								</div>
								
		<div class="ic-input ic-col-20 ">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.lbl.bookingFrom" />
										</label>
										 <ibusiness:calendar property="bookingFrom" type="image" id="incalender1"
							 value="<%=form.getBookingFrom()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_BookingFrom" onblur="autoFillDate(this)"/>
									</div>	

									
		<div class="ic-input ic-col-20">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.lbl.bookingTo" />
										</label>
										 <ibusiness:calendar property="bookingTo" type="image" id="incalender2"
							 value="<%=form.getBookingTo()%>" componentID="CMP_MailTracking_Defaults_CarditEnquiry_BookingTo" onblur="autoFillDate(this)"/>
									</div>	

        <div class="ic-input  ic-col-17 ">
							<label>
								<common:message  key="mailtracking.defaults.carditenquiry.lbl.scc" />
							</label>
							<ihtml:text property="mailScc" componentID="CMP_MailTracking_Defaults_CarditEnquiry_SCC" styleClass="iCargoTextFieldMediumCaps" maxlength="60"/>
							 <div class= "lovImg">
							<img id="sccLOV" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" title="<common:message key="mailtracking.defaults.carditenquiry.tooltip.scclov" />"/>
							</div> 	
						</div> 		

        <div class="ic-input  ic-split-37">
							<label>
								<common:message  key="mailtracking.defaults.carditenquiry.lbl.product" />
							</label>
						<ihtml:text property="mailProduct" maxlength="25" componentID="CMP_MailTracking_Defaults_CarditEnquiry_Product" />
                         <div class= "lovImg">
						<img id="productLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
						</div>	
              </div>
              </div>
			<div class="ic-row">
        <div class="ic-input ic-col-22 ">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.origin" />
										</label>
										<ihtml:text property="orginOfBooking" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_origin" />
										<div class= "lovImg">
										<img id="originOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="org"/>
										</div>	
									</div>		

        <div class="ic-input  ic-col-20 ">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.viaPoint" />
										</label>
											<ihtml:text property="viaPointOfBooking" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_ViaPoint" />
											 <div class= "lovImg">
											<img id="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/>
											</div>	
									</div>	

        <div class="ic-input ic-col-20 ">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.destination" />
										</label>
											<ihtml:text property="destinationOfBooking" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_destn" />
											 <div class= "lovImg">
											<img id="destnLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/>
											</div>	
									</div>	
									
		 <div class="ic-input ic-col-17 ">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.station" />
										</label>
											<ihtml:text property="stationOfBooking" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_StationOfBooking" />
											 <div class= "lovImg">
											<img id="stnlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="stn"/>
									</div>	
									</div>	

        							
		 <div class="ic-input ic-col-21 ">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.shippingDate" />
										</label>
										 <ibusiness:calendar property="shipmentDate" type="image" id="incalender3"
							 value="<%=form.getShipmentDate()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_ShipmentDate" onblur="autoFillDate(this)"/>
									</div>										
                      </div>	

     	<div class="ic-row" >
		 <div class="ic-input ic-col-22 ">
		<label>
											 <common:message key="mailtracking.defaults.carditenquiry.tooltip.flightNumber" />
										</label>
										 <ibusiness:flightnumber
												 carrierCodeProperty="bookingCarrierCode"
												 id="flight"
												 flightCodeProperty="bookingFlightNumber"
												 carriercodevalue="<%=form.getBookingCarrierCode()%>"
												 flightcodevalue="<%=form.getBookingFlightNumber()%>"
												 carrierCodeMaxlength="3"
												 flightCodeMaxlength="5"
												 componentID="CMP_MailTracking_Defaults_SearchCosignment_FltNo"/>
											
									</div>
									
		<div class="ic-input  ic-col-20">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.lbl.flightFrom" />
										</label>
										 <ibusiness:calendar property="bookingFlightFrom" type="image" id="incalender4"
							 value="<%=form.getBookingFlightFrom()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_BookingFlightFrom" onblur="autoFillDate(this)"/>
									</div>	

        <div class="ic-input ic-col-20">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.lbl.flightTo" />
										</label>
										 <ibusiness:calendar property="bookingFlightTo" type="image" id="incalender5"
							 value="<%=form.getBookingFlightTo()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_BookingFlightTo" onblur="autoFillDate(this)"/>
									</div>		

        <div class="ic-input ic-col-17 ">
						<label><common:message key="mailtracking.defaults.carditenquiry.lbl.agentCode" /></label>
					<ihtml:text property="agentCode"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_AGENTCODE" tabindex="6"  style="text-transform : uppercase;" maxlength="15"/>
                                 <div class= "lovImg">
								<img name="agentlov" id="agentlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
					</div>	
					</div>	
					
					
        <div class="ic-input ic-col-21 ">
		<label><common:message key="mailtracking.defaults.carditenquiry.lbl.customerCode" /></label>
        	<ihtml:text  property="customerCode"  tabindex="14" componentID="CMP_MailTracking_Defaults_CarditEnquiry_CUSTCODE"
							maxlength="15"/>
							  <div class= "lovImg">
							<img id="custCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
							</div>	
			</div>		
		</div>
		
		<div class="ic-row" >
		<div class="ic-input ic-col-22">
		<label><common:message key="mailtracking.defaults.carditenquiry.lbl.userid" /></label>
        	<ihtml:text  property="bookingUserId"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_BookingUserId"/>
							
			</div>	
		
           <div class="ic-input ic-col-20">
		         <label>
		           <common:message  key="mailtracking.defaults.carditenquiry.lbl.shipmentStatus" />
		        </label>
				  <logic:present name="ONETIME_SHIPMENT_STATUS">
						  <bean:define id="bookingStatus" name="ONETIME_SHIPMENT_STATUS" toScope="page"/>
							<ihtml:select property="bookingStatus"  tabindex="16" componentID="CMP_MailTracking_Defaults_CarditEnquiry_ShipmentStatus"
							title="Shipment Status">
							    <html:option value=""><common:message key="combo.select"/></html:option>
							     <logic:iterate id="shpStatus" name="bookingStatus">
							       <logic:present name="shpStatus" property="fieldDescription">
									<bean:define id="bkngStatus" name="shpStatus" property="fieldDescription" />
								   <logic:present name="shpStatus" property="fieldValue">
									<bean:define id="status" name="shpStatus" property="fieldValue" />
								       <html:option value="<%=(String)status%>">
									     <bean:write name="bkngStatus" />
									   </html:option>
								   </logic:present>
								   </logic:present>
						      </logic:iterate>
							     </ihtml:select>
				     </logic:present>
					 
					
									 
		       </div>
		      </div>
		
		
	    <div class="ic-row">
							<div class="ic-button-container">
              <ihtml:nbutton property="btList"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btList" accesskey="L">
						<common:message key="mailtracking.defaults.carditenquiry.btn.list" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClear"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClear" accesskey="C">
						<common:message key="mailtracking.defaults.carditenquiry.btn.clear" />
					</ihtml:nbutton>
						</div>
						</div>	
		
               </div>	
  <div class="ic-row">
  
  </div>
   </div>
   <div class="ic-main-container">
  <div class="ic-row">
	
							<%String lstPgNo = "";%>

							<div class="ic-col-80">
								<logic:present name="MailbookingPopupForm" property="lastPageNum">
								<bean:define id="lastPg" name="MailbookingPopupForm" property="lastPageNum" scope="request"  toScope="page" />
								<%
								lstPgNo = (String) lastPg;
								%>
								</logic:present>
								<logic:present name="LIST_MAL_COLLECTION" >
								<bean:define id="pageObj" name="LIST_MAL_COLLECTION"  />
								<common:paginationTag pageURL="mailtracking.defaults.listconsignment.mailbookingpopup.list.do"
								name="pageObj"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=lstPgNo%>" />
								</logic:present>
								<logic:notPresent name="LIST_MAL_COLLECTION" >
								&nbsp;
								</logic:notPresent>
							</div>
							<div class="ic-col-20">
								<logic:present name="LIST_MAL_COLLECTION" >
								<bean:define id="pageObj1" name="LIST_MAL_COLLECTION"  />
								<common:paginationTag
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
								pageURL="javascript:performPagination('lastPageNum','displayPage')"
								name="pageObj1"
								display="pages"
								lastPageNum="<%=lstPgNo%>"/>
								</logic:present>
								<logic:notPresent name="LIST_MAL_COLLECTION" >
									&nbsp;
								</logic:notPresent>
		</div>
		
		</div> 

	<div id="pane1" class="content" >
            <div class="tableContainer" id="div1"   style="height:280px">
	    <table  class="fixed-header-table" style="width:100%">
              <thead>
                <tr >
               <!--   <td  rowspan="2" class="iCargoTableHeaderLabel"><div align="center"><input type="checkbox" name="selectedMailbagId" onclick="updateHeaderCheckBox(this.form,this,this.form.selectedMailbagId);"/></div></td>-->
			   <td  rowspan="2" class="iCargoTableHeaderLabel" width="3%">
								<input type="checkbox" name="parentCheckBox" onclick="updateHeaderCheckBox(this.form,this,this.form.selectedMailbagId);" />
							</td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.awbnumber"/></td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.flightNumber"/></td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.agentCode"/></td>
				   <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.scc"/>  </td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.shippingDate"/></td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.awborigin"/></td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.awbdestination"/> </td>
				  <td colspan="3"><common:message key="mailtracking.defaults.carditenquiry.lbl.booked"/> <span></span></td>
               	  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.tooltip.status"/></td>
                  <td rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.shipmentStatus"/>  </td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingStn"/></td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingDate"/> </td>
                  <td  rowspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.remarks"/> </td>       
                  </tr>
				  
				  <tr>
				  <td width="3%"><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingPcs"/></td>
                  <td width="3%"><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingWgt"/></td>
                  <td width="3%"><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingVol"/></td>
				  </tr>
              </thead>
			  <tbody>
			  <logic:present name="LIST_MAL_COLLECTION" >
			  <logic:iterate id="mailBookingDetailsVOs" name="LIST_MAL_COLLECTION" indexId="index" type="MailBookingDetailVO">
			  
			  <tr>
			    <td class="iCargoTableDataTd ic-center">
						  				 	<input type="checkbox" name="selectedMailbagId" value="<%=index%>" onclick="singleSelectCb(this.form,'<%=index%>','primaryKey');" />
			  </td>
			  <td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVOs" property="masterDocumentNumber">
									<bean:define id="shipmentPrefix" name="mailBookingDetailsVOs"
										property="shipmentPrefix" toScope="page" />
									<bean:define id="documentNumber" name="mailBookingDetailsVOs"
										property="masterDocumentNumber" toScope="page" />
										<%String sp=shipmentPrefix.toString();%>
										<%String dn=documentNumber.toString();%>
									<%=sp%>-<%=dn%>

									</logic:present>
									<logic:notPresent name="mailBookingDetailsVOs" property="masterDocumentNumber">
										&nbsp;
									</logic:notPresent>
								</td>
								
			     <td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVOs" property="bookingCarrierCode">
									<bean:define id="bookingCarrierCode" name="mailBookingDetailsVOs"
										property="bookingCarrierCode" toScope="page" />
									<bean:define id="selectedFlightNumber" name="mailBookingDetailsVOs"
										property="selectedFlightNumber" toScope="page" />
										<%
											if("-1".equals(selectedFlightNumber))
												selectedFlightNumber = "";
												StringBuilder selectedFlightNumberToDisplay=null;
												if(selectedFlightNumber!=null && !selectedFlightNumber.toString().equals(""))
												{
												String[] selectedFlightDetails = selectedFlightNumber.toString().split(",");												
													if(selectedFlightDetails!=null)
													{
														for(int i=0;i<selectedFlightDetails.length;i++)
														{
														String[] flightDetails=selectedFlightDetails[i].split(" ");													
														if(selectedFlightNumberToDisplay==null){
														selectedFlightNumberToDisplay=new StringBuilder().append(flightDetails[2]).append(flightDetails[0]).append(" ").append(flightDetails[1]);
														}
														else{
														selectedFlightNumberToDisplay.append(",").append(flightDetails[2]).append(flightDetails[0]).append(" ").append(flightDetails[1]);
														}
														}	
													}
												}
												
												
										%>
									<%-- <%=bookingCarrierCode%> --%><%=selectedFlightNumberToDisplay%>
									</logic:present>
									<logic:notPresent name="mailBookingDetailsVOs" property="bookingCarrierCode">
										&nbsp;
									</logic:notPresent>
								</td>	
                <td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="agentCode" /></td>	
                <td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="mailScc" /></td>	

                <td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVOs" property="shipmentDate">
										<bean:define id="shipmentDate" name="mailBookingDetailsVOs" property="shipmentDate" toScope="page" />
										<%=shipmentDate.toString().substring(0, 11)%>
									</logic:present>
									<logic:notPresent name="mailBookingDetailsVOs" property="shipmentDate">
										&nbsp;
									</logic:notPresent>
								</td>				
			    <td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="awbOrgin" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="awbDestination" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="bookedPieces" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="bookedWeight" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="bookedVolume" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="bookingStatus" /></td>
				<td class="iCargoTableDataTd" >

				 <logic:present name="mailBookingDetailsVOs" property="shipmentStatus">
					<bean:define id="mailBookingDetailsVOs" name="mailBookingDetailsVOs"  type="MailBookingDetailVO"/>
					<%String shipmentStatus=mailBookingDetailsVOs.getShipmentStatus();%>												
								<logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
															<bean:define id="parameterCode" name="oneTimeValue"
																property="key" />
														<logic:equal name="parameterCode"
																	value="operations.shipment.shipmentstatus">
																<bean:define id="parameterValues" name="oneTimeValue"
																	property="value" />
																<logic:iterate id="parameterValue"
																	name="parameterValues"
																	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue"
																		property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue"
																			property="fieldValue" />
																		<bean:define id="fieldDescription"
																			name="parameterValue" property="fieldDescription" />
																			<%if(shipmentStatus.equals(String.valueOf(fieldValue).toUpperCase())){%>
																			<%String shipmentStatusToDisp=String.valueOf(fieldDescription);%>
																			<%=shipmentStatusToDisp%>																	
																			<%}%>
																	</logic:present>
																</logic:iterate>
													</logic:equal>
									</logic:iterate>
								</logic:present>
				 </logic:present>
				</td>

				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="bookingStation" /></td>
				
				<td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVOs" property="bookingDate">
										<bean:define id="bookingDate" name="mailBookingDetailsVOs" property="bookingDate" toScope="page" />
										<%=bookingDate.toString().substring(0, 11)%>
									</logic:present>
									<logic:notPresent name="mailBookingDetailsVOs" property="bookingDate">
										&nbsp;
									</logic:notPresent>
								</td>	
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVOs" property="remarks" /></td>
				
				
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
				
				<ihtml:nbutton property="btnAttach"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_Attach" accesskey="O">
						<common:message key="mailtracking.defaults.carditenquiry.btn.attach" />
						</ihtml:nbutton>
				
				<ihtml:nbutton property="btClose"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClose" accesskey="O">
						<common:message key="mailtracking.defaults.carditenquiry.btn.close" />
						</ihtml:nbutton>
				</div>
				</div>
									
		</div>
		</div>


</ihtml:form>
</div>
		
			
	</body>
</html:html>