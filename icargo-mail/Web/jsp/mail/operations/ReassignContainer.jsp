<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: ReassignContainer.jsp
* Date				: 15-Jun-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html:html>

<head> 
		
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
	<title><common:message bundle="reassignContainerResources" key="mailtracking.defaults.reassigncontainer.lbl.pagetitle" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/ReassignContainer_Script.jsp" />
</head>

<body>
	
	

<business:sessionBean id="containerVO"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.reassignContainer"
					  method="get"
					  attribute="containerVO" />
<div id="divmain" class="iCargoPopUpContent ic-masterbg"  style="position:static; width:100%; height:0px;">
<ihtml:form action="/mailtracking.defaults.reassigncontainer.screenload.do" styleclass="ic-main-form">

<bean:define id="form"
	name="ReassignContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignContainerForm"
    toScope="page"
    scope="request"/>

<ihtml:hidden property="status" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="assignedto" />
<ihtml:hidden property="hideRadio" />
<ihtml:hidden property="frmFlightDate" />
<ihtml:hidden property="fromFlightNumber" />
<ihtml:hidden property="fromFlightCarrierCode" />
<ihtml:hidden property="fromdestination" />
<!--modified by A-7938 for ICRD-243745-->
<div class="ic-content-main bg-white">
				<span class="ic-page-title ic-display-none">
					Reassign Container
				</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<!--modified by A-7938 for ICRD-243745-->
					<div class="ic-row ic-border marginT20" style="width:550px;">
					 <logic:present name="ReassignContainerForm" property="hideRadio">
						<logic:equal name="ReassignContainerForm" property="hideRadio" value="CARRIER">
						<ihtml:hidden property="reassignedto" value="FLIGHT" />
						</logic:equal>
						<logic:equal name="ReassignContainerForm" property="hideRadio" value="FLIGHT">
						<ihtml:hidden property="reassignedto" value="DESTINATION" />
						</logic:equal>
						</logic:present>
						<logic:present name="ReassignContainerForm" property="hideRadio">
						<logic:equal name="ReassignContainerForm" property="hideRadio" value="NONE">
						<div class="ic-input ic-split-40 ic-label-40">
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.reassignto" />
									</label>
									
						</div>
						
						<div class="ic-input ic-split-30 ic-label-30 ic_inline_chcekbox">
									<ihtml:radio property="reassignedto" onclick="selectDiv();" value="FLIGHT" componentID="CMP_MailTracking_Defaults_ReassignContainer_radioFlight"/>
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.flight" />
									</label>
									
						</div>
						
						<div class="ic-input ic-split-25 ic-label-25 ic_inline_chcekbox">
									 <ihtml:radio property="reassignedto" onclick="selectDiv();" value="DESTINATION" componentID="CMP_MailTracking_Defaults_ReassignContainer_radioDestination"/>
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.carrier" />
									</label>
									
						</div>
						
						</logic:equal>
						</logic:present>
					</div>
					</fieldset>
					<div class="ic-row marginT5">
					 <div id="FLIGHT">
						<div class="ic-input ic-mandatory ic-split-30 ">
									 
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.fltno" />
									</label>
									<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight"
							 flightCodeProperty="flightNumber"
							 carriercodevalue="<%=form.getFlightCarrierCode()%>"
							 flightcodevalue="<%=form.getFlightNumber()%>"  tabindex="1"
							 carrierCodeMaxlength="3"
							 flightCodeMaxlength="5"
							 componentID="CMP_MailTracking_Defaults_ReassignContainer_FlightNo"/>

									
						</div>
						
						<div class="ic-input ic-mandatory ic-split-40 ">
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.fltdate" />
									</label>
									  <ibusiness:calendar property="flightDate" type="image" id="incalender"
				   		value="<%=form.getFlightDate()%>"  tabindex="1"
					     componentID="CMP_MailTracking_Defaults_ReassignContainer_FlightDate" onblur="autoFillDate(this)"/>
						</div>
						
						<div class="ic-input ic-mandatory ic-split-25 ">
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.pou" />
									</label>
									<ihtml:text property="flightPou" maxlength="4" tabindex="1" componentID="CMP_MailTracking_Defaults_ReassignContainer_pou" />
									<div class= "lovImg">
									<img id="flightPouLOV" value="flightPouLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
									</div>
						</div>
						
						
					
					</div>
					
					  <div id="DESTINATION" >
						<div class="ic-input ic-mandatory ic-split-30">
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.carrier" />
									</label>
									<ihtml:text property="carrier" maxlength="3" tabindex="1" componentID="CMP_MailTracking_Defaults_ReassignContainer_Carrier"/>
									<div class= "lovImg">
									<img id="carrierLOV" value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
									</div>
						</div>
						
						<div class="ic-input ic-mandatory ic-split-60">
									<label>
										<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.destn" />
									</label>
									<logic:notEqual name="form" property="reassignedto" value="">
										<ihtml:text property="destination" maxlength="4" tabindex="1" componentID="CMP_MailTracking_Defaults_ReassignContainer_Destination" disabled="true"/>
									</logic:notEqual>
									<logic:equal name="form" property="reassignedto" value="">
										<ihtml:text property="destination" maxlength="4" tabindex="1" componentID="CMP_MailTracking_Defaults_ReassignContainer_Destination" />
									</logic:equal>
						</div>
					  </div>
				</div>
			</div>
		
		</div>
		<div class="ic-main-container" style="top:53px; height:0px;">
		<div class="ic-row">
			<h3>
				<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.onwardRouting" />
			</h3>
			<div class="ic-button-container paddR5">
			<logic:equal name="form" property="reassignedto" value="FLIGHT">
			
						<a id="addLink" name="addLink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.addlink" /></a> 
						<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.deletelink" /></a>
					</logic:equal>
					<logic:notEqual name="form" property="reassignedto" value="FLIGHT">
						<a class="iCargoLink" href="#" disabled="true" value="add"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.addlink" /></a> |
						<a class="iCargoLink" href="#" disabled="true" value="delete"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.deletelink" /></a>
					</logic:notEqual>
					
			</div>
			</div>
		
		
				<div  id="div1" class="tableContainer" style="height:90px;width:100% position:static;" >
					<table  class="fixed-header-table multi-input" id="table1" style="width:100%;">
					 <thead>
                   <tr class="iCargoTableHeadingLeft">
                     <td width="9%"  class="iCargoTableHeaderLabel"><div><input type="checkbox" name="reassignCheckAll" value="checkbox" /></div></td>
                     <td width="28%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.fltno" /></td>
                     <td width="37%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.depDate" /></td>
                     <td width="26%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.pou" /></td>
                   </tr>
                </thead>
				<tbody>

                 <logic:equal name="form" property="reassignedto" value="FLIGHT">
                 <logic:present name="containerVO">
                 	<logic:present name="containerVO" property="onwardRoutings">
                 	<bean:define id="onwardRoutingVOs" name="containerVO" property="onwardRoutings" toScope="page"/>

                 		<logic:iterate id="routingvo" name="onwardRoutingVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO">
                 	

                 		<tr>
						  <td><center>
							<input type="checkbox" name="reassignSubCheck" value="<%= rowid.toString() %>"></center></td>
						  <td>

							<logic:notPresent name="routingvo" property="onwardCarrierCode">
							      <logic:notPresent name="routingvo" property="onwardFlightNumber">
								   <ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="" flightcodevalue="" />
							      </logic:notPresent>
							</logic:notPresent>
							<logic:present name="routingvo" property="onwardCarrierCode">
							      <logic:present name="routingvo" property="onwardFlightNumber">
								<bean:define id="carrierCode" name="routingvo" property="onwardCarrierCode" toScope="page"/>
								<bean:define id="flightNo" name="routingvo" property="onwardFlightNumber" toScope="page"/>
								     <ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>" />
							      </logic:present>
							</logic:present>

						  </td>
						  <td>

						  <logic:present name="routingvo" property="onwardFlightDate">
							<bean:define id="fltDate" name="routingvo" property="onwardFlightDate" toScope="page"/>
								<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
										componentID="CMP_MailTracking_Defaults_ReassignContainer_depDate" onblur="autoFillDate(this)"
										value="<%= fltDate.toString().substring(0,11) %>"/>
						  </logic:present>
						  <logic:notPresent name="routingvo" property="onwardFlightDate">
								<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
										componentID="CMP_MailTracking_Defaults_ReassignContainer_depDate" onblur="autoFillDate(this)"
										value=""/>
						  </logic:notPresent>

						  </td>
						  <td>

							<logic:present name="routingvo" property="pou">
								<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_ReassignContainer_pointOfUnlading"
									value="<%= routingvo.getPou() %>" style = "background :'<%=color%>'"/>
							</logic:present>
							<logic:notPresent name="routingvo" property="pou">
								<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_ReassignContainer_pointOfUnlading"
									value="" style = "background :'<%=color%>'"/>
							</logic:notPresent>
							<div class= "lovImgTbl">
							<img id="poulov" value="poulov" src="<%= request.getContextPath()%>/images/lov_sm.png" width="16" height="16" border="0"
									index="<%=String.valueOf(rowid)%>" onclick="displayLOV('showAirport.do','N','N','showAirport.do',this.value,'pointOfUnlading','0','pointOfUnlading','',<%= rowid %>);" />
									</div>

						  </td>
						</tr>

						
						</logic:iterate>

                  	</logic:present>
                  </logic:present>
                  </logic:equal>

                  </tbody>
					</table>
					
				</div>
		
		<div class="ic-row ic-mandatory marginL10">
			<label>
			<common:message key="mailtracking.defaults.reassigncontainer.lbl.scandate" />
			</label>
			<div class="multi-input ic-split-45">
			<ibusiness:calendar property="scanDate" type="image" id="scanDate"
    			value="<%=form.getScanDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNCONTAINER_SCANDATE" onblur="autoFillDate(this)" tabindex="5"/>
    	  <ibusiness:releasetimer property="mailScanTime" indexId="index" tabindex="6" componentID="TXT_MAILTRACKING_DEFAULTS_REASSIGNCONTAINER_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=form.getMailScanTime()%>"/>
		  </div>
    	 
		</div>
		<div class="ic-row marginL10">
		<div class="ic-col-10">
		</div>
		<div class="ic-row ic-input ic-split-100">
		</div>
		<div class="ic-row ic-col-80">
		<label>
			<common:message key="mailtracking.defaults.reassigncontainer.popup.lbl.remarks" />
		</label>
		<!--modified by a-7938 for ICRD-236984-->
		<div class="multi-input" style="height:90px;width:100% position:static;"><ihtml:textarea property="remarks" cols="50" rows="3" componentID="CMP_MailTracking_Defaults_ReassignContainer_Remarks" style="resize:none" /></div>
		</div>
		</div>
		<!--Modified by A-7938 for ICRD-243745-->
		<div class="ic-row">
							&nbsp;
		</div>
		</div>
		
		</div>
		<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btSave" componentID="CMP_MailTracking_Defaults_ReassignContainer_btSave">
						<common:message key="mailtracking.defaults.reassigncontainer.popup.btn.save" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btClose" componentID="CMP_MailTracking_Defaults_ReassignContainer_btClose">
						<common:message key="mailtracking.defaults.reassigncontainer.popup.btn.close" />
					</ihtml:nbutton>

				
				</div>
			</div>


</div>
</ihtml:form>
</div>
			
		   
	</body>

</html:html>
