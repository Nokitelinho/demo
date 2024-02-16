<%--
* Project	 	: iCargo
* Module Code & Name	: Mail
* File Name		: ConsignmentMailPopUp.jsp
* Date			: 28-Jun-2017
* Author(s)		: ArunimaUnnikrishnan
 --%>
 
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm"%>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO"%>



		
		<!DOCTYPE html>			
	
<html:html>

<head> 
		
	 
	
	<title><common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.bookingflights.lbl.pagetitle" /></title>
	<meta name="decorator" content="popuppanelrestyledui"> <!-- Updated by A-8236 for ICRD-256183 -->
	<common:include type="script" src="/js/mail/operations/BookedFlightsPopup_Script.jsp" />
</head>

<body>
	<bean:define id="form"
             name="MailbookingPopupForm"
             type="com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm"
             toScope="page" scope="request"/>
	
    
<business:sessionBean id="oneTimeValues" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="oneTimeVOs" />
<business:sessionBean id="mailClassCollection" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="mailbagVOsCollection" />
<business:sessionBean id="BOOKING_MAL_COLLECTION" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="mailBookingDetailsVO" />
<div id="divmain" class="iCargoPopUpContent ic-masterbg" > <!-- Updated by A-8236 for ICRD-256183 -->
<ihtml:form action="/mailtracking.defaults.listconsignment.bookedflightspopup.screenload.do" styleClass="ic-main-form">

<ihtml:hidden property="bookingFlightNumber"/><!--added by A-8061 as part of ICRD-229572-->
<ihtml:hidden property="bookingCarrierCode"/><!--added by A-8061 as part of ICRD-229572-->
<ihtml:hidden property="bookingFlightSequenceNumber"/>

<ihtml:hidden property="popUpFlag"/><!--added by A-8061 as part of ICRD-229572-->

<div class="ic-content-main"style="width:100%">
<div class="ic-head-container"> <!-- Updated by A-8236 for ICRD-256183 -->
		<div class="ic-filter-panel" >
		<div class="ic-row">
		<div class="ic-input">                                
								  <ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" isCheckDigitMod="false"   componentID="CMP_MailTracking_Defaults_CarditEnquiry_AWB_No" />
								</div>
			</div>
		
		
		
               </div>	
  
 </div>
<div class="ic-main-container">  <!-- Updated by A-8236 for ICRD-256183 -->
   <div class="ic-row">
   <h4>Flight Details</h4>
   </div>
	<div id="pane1" class="content" >
            <div class="tableContainer" id="div1"   style="height:250px"> <!-- Updated by A-8236 for ICRD-256183 -->
	    <table  class="fixed-header-table" style="width:100%">
              <thead>
                <tr >
                  <td width="5%" class="iCargoTableHeaderLabel"><div align="center"><input type="checkbox" name="masterDespatch" onclick="updateHeaderCheckBox(this.form,this,this.form.selectedMailbagId);"/></div></td>
                  <td  width="5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.flightNumber"/><span class="iCargoMandatoryFieldIcon">*</span></td>
				  <td  width="5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.carditenquiry.lbl.flightdate"/><span class="iCargoMandatoryFieldIcon">*</span></td>				  
				  <td width="5%"><common:message key="mailtracking.defaults.carditenquiry.lbl.pol"/></td> 
				  <td width="5%"><common:message key="mailtracking.defaults.carditenquiry.lbl.pou"/></td>
				  <td width="5%"><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingPcs"/></td>
				  <td width="5%"><common:message key="mailtracking.defaults.carditenquiry.lbl.bookingWgt"/></td>
				  <td width="5%"><common:message key="mailtracking.defaults.carditenquiry.lbl.attachedPcs"/></td><!-- Modified by A-7794 for ICRD-229394-->
                      
                  </tr>   
              </thead>
			   <tbody>
			  <logic:present name="BOOKING_MAL_COLLECTION" >
			  <logic:iterate id="mailBookingDetailsVO" name="BOOKING_MAL_COLLECTION" indexId="index" type="MailBookingDetailVO">
			  
			  <tr>
			    <td class="iCargoTableDataTd ic-center">
						  				 	<input type="checkbox" name="selectedMailbagId" value="<%=index%>" onclick="singleSelectCb(this.form,'<%=index%>','primaryKey');" />
			  </td>
			 
								
			     <td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVO" property="bookingCarrierCode">
									<bean:define id="bookingCarrierCode" name="mailBookingDetailsVO"
										property="bookingCarrierCode" toScope="page" />
									<%=bookingCarrierCode%>	
									<logic:present name="mailBookingDetailsVO" property="bookingFlightNumber">
									<bean:define id="bookingFlightNumber" name="mailBookingDetailsVO"
										property="bookingFlightNumber" toScope="page" />
										<%
											if("-1".equals(bookingFlightNumber))
												bookingFlightNumber = "";
										%>
									<%=bookingFlightNumber%>
									</logic:present>
									
									<!--added by A-8061 as part of ICRD-229572-->
									<ihtml:hidden property="bookingFlightNumber" value="<%=mailBookingDetailsVO.getBookingFlightNumber()%>"/>
									<ihtml:hidden property="bookingCarrierCode" value="<%=mailBookingDetailsVO.getBookingCarrierCode()%>"/>
									<%String bookedFlightSeqNum=mailBookingDetailsVO.getBookingFlightSequenceNumber()+"";%>
									<ihtml:hidden property="bookingFlightSequenceNumber" value="<%=bookedFlightSeqNum%>"/>
									</logic:present>
									<logic:notPresent name="mailBookingDetailsVO" property="bookingCarrierCode">
										&nbsp;
									</logic:notPresent>
								</td>	
								<td class="iCargoTableDataTd">
									<logic:present name="mailBookingDetailsVO" property="bookingFlightDate">
										<bean:define id="bookingFlightDate" name="mailBookingDetailsVO" property="bookingFlightDate" toScope="page" />
										<%=bookingFlightDate.toString().substring(0, 11)%>
									</logic:present>
									<logic:notPresent name="mailBookingDetailsVO" property="bookingFlightDate">
										&nbsp;
									</logic:notPresent>
								</td>	
			    <td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVO" property="origin" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVO" property="destination" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVO" property="bookedPieces" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVO" property="bookedWeight" /></td>
				<td class="iCargoTableDataTd" ><bean:write name="mailBookingDetailsVO" property="attachedMailBagCount" /></td>
				
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
				
				<ihtml:nbutton property="btnOk"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_OK" accesskey="O">
						<common:message key="mailtracking.defaults.carditenquiry.btn.ok" />
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