<%--
* Project	 	: iCargo
* Module Code & Name	: Mail Tracking
* File Name		: TransferContainer.jsp
* Date			: 05-Oct-2006
* Author(s)		: A-1876
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html:html>

<head> 
			
	 <%@ include file="/jsp/includes/customcss.jsp" %>  <!-- 	Added by A-8164 for ICRD-260621 -->
	<title><common:message bundle="transferContainerResources" key="mailtracking.defaults.transfercontainer.lbl.pagetitle" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/TransferContainer_Script.jsp" />
</head> 

<body>
	
	

<business:sessionBean id="containerVO"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.transfercontainer"
		  method="get"
		  attribute="containerVO" />
		  <%@include file="/jsp/includes/reports/printFrame.jsp" %>
<div id="divmain" class="iCargoPopUpContent" >
<ihtml:form action="/mailtracking.defaults.transfercontainer.screenload.do" styleClass="ic-main-form" >

<bean:define id="form"
	name="TransferContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm"
    toScope="page"
    scope="request"/>

<ihtml:hidden property="status" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="assignedto" />
<ihtml:hidden property="hideRadio" />
<ihtml:hidden property="similarCarrier" />
<ihtml:hidden property="isReportNeeded" />
<ihtml:hidden property="embargoFlag" />
<input type="hidden" name="printTransferManifestFlag" />
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="currentDialogId" />
<ihtml:hidden property="isScreenLoad" />
<div class="ic-content-main">
				<div class="ic-head-container">	
					<span class="ic-page-title ic-display-none">
						Transfer Container
					</span>
					<div class="ic-row ic-input-round-border">
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input ic-split-15">
								<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.reassignto" /></label>
							</div>
							<div class="ic-input ic-split-15">
								<ihtml:radio property="reassignedto" onclick="reassign();" value="FLIGHT" componentID="CMP_MailTracking_Defaults_TransferContainer_radioFlight"/>
								<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.flight" /></label>
							</div>
							<div class="ic-input ic-split-70">
							<ihtml:radio property="reassignedto" onclick="reassign();" value="DESTINATION" componentID="CMP_MailTracking_Defaults_TransferContainer_radioDestination"/>
							<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.carrier" /></label>
							</div>
						</div>
						<div class="ic-row">
						 <div id="FLIGHT" >
							<div class="ic-input ic-split-30 ic-mandatory">
								<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.fltno" /></label>
								<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight"
							 flightCodeProperty="flightNumber"
							 carriercodevalue="<%=form.getFlightCarrierCode()%>"
							 flightcodevalue="<%=form.getFlightNumber()%>"  tabindex="1"
							 carrierCodeMaxlength="3"
							 flightCodeMaxlength="5"
							 componentID="CMP_MailTracking_Defaults_TransferContainer_FlightNo"/>
							</div>
							<div class="ic-input ic-split-40 ic-mandatory">
								<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.fltdate" /></label>
								<ibusiness:calendar property="flightDate" type="image" id="incalender"
								value="<%=form.getFlightDate()%>"  tabindex="1"
								componentID="CMP_MailTracking_Defaults_TransferContainer_FlightDate" onblur="autoFillDate(this)"/>
							</div>
							<div class="ic-input ic-split-30 ic-mandatory">
							<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.pou" /></label>
							    <ihtml:text property="flightPou" maxlength="4" tabindex="1" componentID="CMP_MailTracking_Defaults_TransferContainer_pou" />
								  <img id="flightPouLOV" value="flightPouLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" />
							</div>

						</div>
						<div id="DESTINATION" >
						<div class="ic-input ic-split-40 ic-mandatory">
						<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.carrier" /></label>
						<ihtml:text property="carrier" maxlength="3" tabindex="1" componentID="CMP_MailTracking_Defaults_TransferContainer_Carrier"/>
						<img id="carrierLOV" value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
						</div>
						<div class="ic-input ic-split-60 ic-mandatory">
						<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.destn" /></label>
						 <ihtml:text property="destination" maxlength="3" tabindex="1" readonly="false" componentID="CMP_MailTracking_Defaults_TransferContainer_Destination"/>
						<img id="destinationLOV" value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
						</div>
						</div>
						</div>
					</div>
				</div>
				</div>
				<div class="ic-main-container">
				<div class="ic-row">
				<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.onwardRouting" /></label>
				<div class="ic-button-container">
                	<logic:equal name="form" property="reassignedto" value="FLIGHT">
						<a id="addLink" name="formlink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.addlink" /></a> |
						<a id="deleteLink" name="formlink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.deletelink" /></a>
					</logic:equal>
					<logic:notEqual name="form" property="reassignedto" value="FLIGHT">
						<a class="iCargoLink" href="#" disabled="true" value="add"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.addlink" /></a> |
						<a class="iCargoLink" href="#" disabled="true" value="delete"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.deletelink" /></a>
					</logic:notEqual>
				</div>
				</div>
				<div class="ic-row">
				  <div  id="div1" class="tableContainer" style="height:160px">
               <table class="fixed-header-table">
                 <thead>
                   <tr class="iCargoTableHeadingLeft">
                     <td width="9%"class="iCargoTableHeaderLabel"><div><input type="checkbox" name="reassignCheckAll" value="checkbox" /></div></td>
                     <td  colspan="28" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.fltno" /></td>
                     <td width="37%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.depDate" /></td>
                     <td width="26%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.pou" /></td>
                   </tr>
                </thead>

				<tbody>

                 <logic:equal name="form" property="reassignedto" value="FLIGHT">
                 <logic:present name="containerVO">
                 	<logic:present name="containerVO" property="onwardRoutings">
                 	<bean:define id="onwardRoutingVOs" name="containerVO" property="onwardRoutings" toScope="page"/>

                 	<logic:iterate id="routingvo" name="onwardRoutingVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO">
                 		<tr>
						  <td class="ic-center"><div>
							<input type="checkbox" name="reassignSubCheck" value="<%= rowid.toString() %>"></div></td>
						  <td>

							  <logic:present name="routingvo" property="onwardCarrierCode">
									<ihtml:text property="fltCarrier" maxlength="3" componentID="CMP_MailTracking_Defaults_TransferContainer_fltCarrier"
											value="<%= routingvo.getOnwardCarrierCode() %>" style = "background :'<%=color%>'"/>
							  </logic:present>
							  <logic:notPresent name="routingvo" property="onwardCarrierCode">
									<ihtml:text property="fltCarrier" maxlength="3" componentID="CMP_MailTracking_Defaults_TransferContainer_fltCarrier"
										value="" style = "background :'<%=color%>'"/>
							  </logic:notPresent>

						  </td>
						  <td>

							<logic:present name="routingvo" property="onwardFlightNumber">
								<ihtml:text property="fltNo" maxlength="5" componentID="CMP_MailTracking_Defaults_TransferContainer_fltNo"
									value="<%= routingvo.getOnwardFlightNumber() %>" style = "background :'<%=color%>'"/>
							</logic:present>
							<logic:notPresent name="routingvo" property="onwardFlightNumber">
								<ihtml:text property="fltNo" maxlength="5" componentID="CMP_MailTracking_Defaults_TransferContainer_fltNo"
									value="" style = "background :'<%=color%>'"/>
							</logic:notPresent>

						  </td>
						  <td>

						  <logic:present name="routingvo" property="onwardFlightDate">
							<bean:define id="fltDate" name="routingvo" property="onwardFlightDate" toScope="page"/>
								<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
										componentID="CMP_MailTracking_Defaults_TransferContainer_depDate" onblur="autoFillDate(this)"
										value="<%= fltDate.toString().substring(0,11) %>"/>
						  </logic:present>
						  <logic:notPresent name="routingvo" property="onwardFlightDate">
								<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
										componentID="CMP_MailTracking_Defaults_TransferContainer_depDate" onblur="autoFillDate(this)"
										value=""/>
						  </logic:notPresent>

						 </td>
						  <td>

							<logic:present name="routingvo" property="pou">
								<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_TransferContainer_pointOfUnlading"
									value="<%= routingvo.getPou() %>" style = "background :'<%=color%>'"/>
							</logic:present>
							<logic:notPresent name="routingvo" property="pou">
								<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_TransferContainer_pointOfUnlading"
									value="" style = "background :'<%=color%>'"/>
							</logic:notPresent>

							<img id="poulov" value="poulov" src="<%= request.getContextPath()%>/images/lov.gif" width="16" height="16" border="0"
									index="<%=String.valueOf(rowid)%>" onclick="displayLOV('showAirport.do','N','N','showAirport.do',this.value,'pointOfUnlading','0','pointOfUnlading','',<%= rowid %>);" />

						</td>
						</tr>
						</logic:iterate>

                  	</logic:present>
                  </logic:present>
                  </logic:equal>

                  </tbody>
               </table>
             </div>
				</div>
				<div class="ic-row ic-mandatory">
				<label><common:message key="mailtracking.defaults.transfercontainer.lbl.scandate" /></label>
				 <ibusiness:calendar property="scanDate" type="image" id="scanDate"
				value="<%=form.getScanDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_TRANSFERCONTAINER_SCANDATE" onblur="autoFillDate(this)" tabindex="5"/>
				<ibusiness:releasetimer property="mailScanTime" indexId="index" tabindex="6" componentID="TXT_MAILTRACKING_DEFAULTS_TRANSFERCONTAINER_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=form.getMailScanTime()%>"/>
				</div>
				<div class="ic-row">
				<label><common:message key="mailtracking.defaults.transfercontainer.popup.lbl.remarks" /></label>
				 <ihtml:textarea property="remarks" cols="50" rows="2" componentID="CMP_MailTracking_Defaults_TransferContainer_Remarks" />
				</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<ihtml:nbutton property="btSave" componentID="CMP_MailTracking_Defaults_TransferContainer_btSave">
						<common:message key="mailtracking.defaults.transfercontainer.popup.btn.save" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btClose" componentID="CMP_MailTracking_Defaults_TransferContainer_btClose">
						<common:message key="mailtracking.defaults.transfercontainer.popup.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
				
</div>



</ihtml:form>
</div>
			
	</body>

</html:html>
