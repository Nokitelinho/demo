<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: AssignContainer.jsp
* Date				: 01-Jun-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.ContainerVO" %>

<html:html>

<head> 
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
	<title><common:message bundle="assignContainerResources" key="mailtracking.defaults.assigncontainer.popup.lbl.pagetitle" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/Add_UpdateContainer_Script.jsp" />
</head>

<body>
	
	

<business:sessionBean id="containerTypes"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.assignContainer"
					  method="get"
					  attribute="containerTypes" />

<business:sessionBean id="containerVOs"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.assignContainer"
					  method="get"
					  attribute="selectedContainerVOs" />

<business:sessionBean id="pointOfLadings"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.assignContainer"
					  method="get"
					  attribute="pointOfLadings" />
<div class="iCargoPopUpContent ic-masterbg" style="overflow:auto;width:100%;height:180px">
<ihtml:form action="/mailtracking.defaults.assigncontainer.screenloadAddUpdatePopup.do" styleClass="ic-main-form">

<bean:define id="form"
	name="AssignContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm"
    toScope="page"
    scope="request"/>

<ihtml:hidden property="assignedto" />
<ihtml:hidden property="status" />
<ihtml:hidden property="currentAction" />
<ihtml:hidden property="currentIndex" />
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="warningCode" />
<ihtml:hidden property="destn" />
<ihtml:hidden property="fromScreen" />
<input type="hidden" name="prevPou"/>

<ihtml:hidden property="flightCarrierCode" />
<ihtml:hidden property="flightNumber" />
<ihtml:hidden property="flightDate" />
<ihtml:hidden property="carrier" />
<ihtml:hidden property="containerType" />


<div class="ic-content-main">
  <span class="ic-page-title ic-display-none">
  <common:message key="mailtracking.defaults.assigncontainer.popup.lbl.pagetitle" />
  </span>

<div class="ic-head-container">
  <div class="ic-filter-panel">
		<fieldset><legend ><label class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.containerdetails" /></label></legend>
     
        <div class="ic-row">
			  <div class="ic-input ic-split-50">
		

          	 <logic:equal name="form" property="assignedto" value="DESTINATION">
          	 	<logic:equal name="form" property="status" value="CREATE">
						<ibusiness:uld id="containerNumber" uldProperty="containerNumber" barrowFlag="true" barrowFlagProperty="barrowCheck" style="text-transform:uppercase;"  componentID="CMP_MailTracking_Defaults_AddPopup_containerNumber" maxlength="12" />
			 	</logic:equal>
			 	<logic:equal name="form" property="status" value="MODIFY">
						<ibusiness:uld id="containerNumber" uldProperty="containerNumber" barrowFlag="true" barrowFlagProperty="barrowCheck" 
						style="text-transform:uppercase;" readonly="true" suggestCollection="containerVOs" suggestAttribute="containerNumber"
						isSuggestible="true" componentID="CMP_MailTracking_Defaults_AddPopup_containerNumber" />
			 	</logic:equal>
			 </logic:equal>
			 <logic:equal name="form" property="assignedto" value="FLIGHT">
				<logic:equal name="form" property="status" value="CREATE">
						<ibusiness:uld id="containerNumber" uldProperty="containerNumber" barrowFlag="true" barrowFlagProperty="barrowCheck" style="text-transform:uppercase;"  componentID="CMP_MailTracking_Defaults_AddPopup_containerNumber" maxlength="12" />
				</logic:equal>
				<logic:equal name="form" property="status" value="MODIFY">
						<ibusiness:uld id="containerNumber" uldProperty="containerNumber" barrowFlag="true" barrowFlagProperty="barrowCheck"
						style="text-transform:uppercase;"  suggestCollection="containerVOs"  suggestAttribute="containerNumber" 
						isSuggestible="true" componentID="CMP_MailTracking_Defaults_AddPopup_containerNumber"  readonly="true" />
			 	</logic:equal>
			 </logic:equal>
         </div>
          <div class="ic-col-50 ic-label-30">
          	<ihtml:nbutton property="btPopupList" componentID="CMP_MailTracking_Defaults_AddPopup_btList">
				<common:message key="mailtracking.defaults.assigncontainer.popup.btn.list" />
			</ihtml:nbutton>
          </div>
		 </div>
        <div class="ic-row ">
		<div class="ic-input ic-mandatory ic-split-50">
         <label><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.pou" /></label>

          	<ihtml:select property="pou" componentID="CMP_MailTracking_Defaults_AddPopup_pou">
			
				<logic:present name="pointOfLadings">
				<bean:define id="pointofladings" name="pointOfLadings" toScope="page"/>

					<logic:iterate id="str" name="pointofladings">
						<bean:define id="point" name="str" type="String" />

						<html:option value="<%= point %>"><%= point %></html:option>

					</logic:iterate>
				</logic:present>
			</ihtml:select>

          </div>
         <div class="ic-input ic-split-15 ic_inline_chcekbox marginT20">
           <logic:equal	name="form" property="containerType" value="B">
				<ihtml:checkbox property="paBuilt" disabled="true" />
			</logic:equal>
			<logic:notEqual name="form" property="containerType" value="B">
		<logic:present name="containerVOs">
			<bean:define id="containerVOs" name="containerVOs" toScope="page" />
			<% int flag=0;%>
			<logic:iterate id="containerVO" name="containerVOs">
				<logic:present name="containerVO" property="containerNumber">
					<bean:define id="contNo" name="containerVO"	property="containerNumber" toScope="page" type="String" />

					<%
						if(contNo.equalsIgnoreCase(form.getContainerNumber())){
						flag=1;
					%>
						<logic:equal name="containerVO" property="operationFlag" value="U">
							<logic:present name="containerVO" property="paBuiltFlag">
							     <logic:equal name="containerVO" property="paBuiltFlag" value="Y">
								  <input type="checkbox" name="paBuilt" value="Y" checked >
							     </logic:equal>
							     <logic:equal name="containerVO" property="paBuiltFlag" value="N">
									  <input type="checkbox" name="paBuilt" value="N" >
							     </logic:equal>
						       </logic:present>
						       <logic:notPresent name="containerVO" property="paBuiltFlag">
								<input type="checkbox" name="paBuilt" value="N" >
						       </logic:notPresent>
						</logic:equal>				
						<logic:notEqual name="containerVO" property="operationFlag" value="U">
						 <bean:define id="opFlag" name="containerVO" property="operationFlag" toScope="page" type="String" />
							<logic:present name="containerVO" property="paBuiltFlag">
							     <logic:equal name="containerVO" property="paBuiltFlag" value="Y">
								  <input type="checkbox" name="paBuilt" value="Y" checked >
							     </logic:equal>
							     <logic:equal name="containerVO" property="paBuiltFlag" value="N">
									  <input type="checkbox" name="paBuilt" value="N">
							     </logic:equal>
						       </logic:present>
						       <logic:notPresent name="containerVO" property="paBuiltFlag">
								<input type="checkbox" name="paBuilt" value="N" >
						       </logic:notPresent>
						</logic:notEqual>
	  		 
					<%
				     		}	
					%>
			
			</logic:present>
			</logic:iterate>
			<% if(flag == 0){%>
			       <input type="checkbox" name="paBuilt" value="N">
			
			<% }%>
			
		</logic:present>
		<logic:notPresent name="containerVOs">
			<input type="checkbox" name="paBuilt" value="N">
		</logic:notPresent>
	</logic:notEqual>
	<span class="iCargoLabelLeftAligned">
	 <label>
	<common:message	key="mailtracking.defaults.assigncontainer.popup.lbl.paBuilt" />
	</span>  <label>
	</div>
          <div class="ic-input ic-mandatory ic-split-30">
          
          	<label><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.destn" /></label>
          <ihtml:text property="containerDestination" componentID="CMP_MailTracking_Defaults_AddPopup_Destination" />
          <div class= "lovImg"><img id="destinationLOV" value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>

          </div>
		  </div>
       
        <div class="ic-row">
		<div class="ic-input ic-split-100">
				
				

				<label>
			 <common:message key="mailtracking.defaults.assigncontainer.popup.lbl.remarks" />
				</label>
          <ihtml:textarea property="remarks" cols="50" rows="2" componentID="CMP_MailTracking_Defaults_AddPopup_Remarks" />
          </div>
        </div>
     
      </fieldset>
	 </div>
 
  </div>
 
  <div class="ic-main-container">
   	<fieldset>
		<legend><label class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.onwardRouting" /></label></legend>
		
		  <div class="iCargoLink ic-row ic-right">
			
			<logic:equal name="form" property="assignedto" value="DESTINATION">
				<a class="iCargoLink" href="#" disabled="true" value="add"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.addLink" /></a> 
				<a class="iCargoLink" href="#" disabled="true" value="delete"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.deleteLink" /></a>
			</logic:equal>
			<logic:notEqual name="form" property="assignedto" value="DESTINATION">
					<logic:equal name="form" property="containerType" value="B">
						<a class="iCargoLink" href="#" disabled="true" value="add"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.addLink" /></a> 
						<a class="iCargoLink" href="#" disabled="true" value="delete"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.deleteLink" /></a>
					</logic:equal>
					<logic:notEqual name="form" property="containerType" value="B">
						<a id="addLink" name="addLink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.addLink" /></a> 
						<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.deleteLink" /></a>
					</logic:notEqual>
			</logic:notEqual>
		  </div>
		  <div class="ic-row">
			 <div class="tableContainer ic-center" id="div2"  style="width:100%;height:100px;">
				<table class="fixed-header-table">
				  <thead>
					<tr class="iCargoTableHeadingLeft">
						<td width="4%"><div align="center">
							<input type="checkbox" name="contCheckAll" value="checkbox">
						  </div></td>
						<td class="ic-mandatory" width="28%"><label><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.fltNo" /></label></td>
						<td class="ic-mandatory"width="37%"><label><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.depDate" /></label></td>
						<td class="ic-mandatory"width="26%"><label><common:message key="mailtracking.defaults.assigncontainer.popup.lbl.pou" /></label></td>
					</tr>
				  </thead>
				  <tbody id="assignContainerTableBody">

					<logic:equal name="form" property="assignedto" value="FLIGHT">

					<logic:present name="containerVOs">
				       	<bean:define id="containervos" name="containerVOs" toScope="page"/>
						<logic:iterate id="containervo" name="containervos">

							<logic:present name="containervo" property="containerNumber">
							<bean:define id="contNo" name="containervo" property="containerNumber" toScope="page" type="String"/>

							<%

							if(contNo.equalsIgnoreCase(form.getContainerNumber())){

							%>

								<logic:present name="containervo" property="onwardRoutings">
								<bean:define id="onwardRoutingVOs" name="containervo" property="onwardRoutings" toScope="page"/>

								<logic:iterate id="routingvo" name="onwardRoutingVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO">

									<logic:present name="routingvo" property="operationFlag">
									<bean:define id="operFlag" name="routingvo" property="operationFlag" toScope="page"/>
									<html:hidden property="opFlag" value="<%=(String)operFlag%>"/>

									<logic:notEqual name="routingvo" property="operationFlag" value="D">

										<tr>
										  <td class="ic-center" style="padding-left:14px;"><input type="checkbox" name="contSubCheck" value="<%= rowid.toString() %>"></td>
										  <td width="28%">

											 <logic:notPresent name="routingvo" property="onwardCarrierCode">
											      <logic:notPresent name="routingvo" property="onwardFlightNumber">
												   <ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="" flightcodevalue="" componentID="CMP_MailTracking_Defaults_AddPopup_fltNo" />
											      </logic:notPresent>
											</logic:notPresent>
											<logic:present name="routingvo" property="onwardCarrierCode">
											      <logic:present name="routingvo" property="onwardFlightNumber">
												<bean:define id="carrierCode" name="routingvo" property="onwardCarrierCode" toScope="page"/>
												<bean:define id="flightNo" name="routingvo" property="onwardFlightNumber" toScope="page"/>
												     <ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>" componentID="CMP_MailTracking_Defaults_AddPopup_fltNo" />
											      </logic:present>
              	      									</logic:present>

										  </td>
										  <td>

										  <logic:present name="routingvo" property="onwardFlightDate">
											<bean:define id="fltDate" name="routingvo" property="onwardFlightDate" toScope="page"/>
												<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
														componentID="CMP_MailTracking_Defaults_AddPopup_depDate" onblur="autoFillDate(this)"
														value="<%= fltDate.toString().substring(0,11) %>"/>
										  </logic:present>
										  <logic:notPresent name="routingvo" property="onwardFlightDate">
												<ibusiness:calendar property="depDate" type="image" id="incalender" indexId="rowid"
													componentID="CMP_MailTracking_Defaults_AddPopup_depDate" 
													onblur="autoFillDate(this)" value=""/>
										  </logic:notPresent>

										  </td>
										  <td>

											<logic:present name="routingvo" property="pou">
												<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_AddPopup_pointOfUnlading"
													value="<%= routingvo.getPou() %>"/>
											</logic:present>
											<logic:notPresent name="routingvo" property="pou">
												<ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_AddPopup_pointOfUnlading"
													value=""/>
											</logic:notPresent>

											
											<img id="templatepou" value="templatepou" name="templatepou" src="<%= request.getContextPath()%>/images/lov.gif" height="16"
													index="<%=String.valueOf(rowid)%>"  onclick="displayLOV('showAirport.do','N','N','showAirport.do',targetFormName.pointOfUnlading[index].value,'pointOfUnlading','0','pointOfUnlading','',index);" />
										  </td>
										</tr>

									</logic:notEqual>
									<logic:equal name="routingvo" property="operationFlag" value="D">

									    <logic:present name="routingvo" property="onwardCarrierCode">
										<ihtml:hidden property="fltCarrier" value="<%= routingvo.getOnwardCarrierCode() %>"/>
									    </logic:present>
									    <logic:notPresent name="routingvo" property="onwardCarrierCode">
											<ihtml:hidden property="fltCarrier" />
										</logic:notPresent>

										<logic:present name="routingvo" property="onwardFlightNumber">
											<ihtml:hidden property="fltNo" value="<%= routingvo.getOnwardFlightNumber() %>"/>
										</logic:present>
										<logic:notPresent name="routingvo" property="onwardFlightNumber">
											<ihtml:hidden property="fltNo" />
										</logic:notPresent>

										<logic:present name="routingvo" property="onwardFlightDate">
										<bean:define id="fltDate" name="routingvo" property="onwardFlightDate" toScope="page"/>
											<ihtml:hidden property="depDate" value="<%= fltDate.toString().substring(0,11) %>"/>
									    </logic:present>
									    <logic:notPresent name="routingvo" property="onwardFlightDate">
											<ihtml:hidden property="depDate" />
										 </logic:notPresent>

										 <logic:present name="routingvo" property="pou">
											<ihtml:hidden property="pointOfUnlading" value="<%= routingvo.getPou() %>"/>
										 </logic:present>
										 <logic:notPresent name="routingvo" property="pou">
											<ihtml:hidden property="pointOfUnlading" />
										 </logic:notPresent>

									</logic:equal>

								</logic:present>
								<logic:notPresent name="routingvo" property="operationFlag">
								 <ihtml:hidden property="opFlag" value="N"/>

									<tr>
									  <td style="padding-left:14px;"><input type="checkbox" name="contSubCheck" value="<%= rowid.toString() %>"></td>
									  <td width="28%">

										<logic:notPresent name="routingvo" property="onwardCarrierCode">
										      <logic:notPresent name="routingvo" property="onwardFlightNumber">
											   <ibusiness:flightnumber id="fltNo" indexId="rowCount" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="" flightcodevalue="" componentID="CMP_MailTracking_Defaults_AddPopup_fltNo" />
										      </logic:notPresent>
										</logic:notPresent>
										<logic:present name="routingvo" property="onwardCarrierCode">
										      <logic:present name="routingvo" property="onwardFlightNumber">
											<bean:define id="carrierCode" name="routingvo" property="onwardCarrierCode" toScope="page"/>
											<bean:define id="flightNo" name="routingvo" property="onwardFlightNumber" toScope="page"/>
												<ibusiness:flightnumber id="fltNo"  indexId="rowCount" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>" componentID="CMP_MailTracking_Defaults_AddPopup_fltNo" /> 
										      </logic:present>
              	      								</logic:present>

									  </td>
									  <td>

									  <logic:present name="routingvo" property="onwardFlightDate">
										<bean:define id="fltDate" name="routingvo" property="onwardFlightDate" toScope="page"/>
											<ibusiness:calendar property="depDate" indexId="rowCount" type="image" id="incalender"
													componentID="CMP_MailTracking_Defaults_AddPopup_depDate" onblur="autoFillDate(this)"
													value="<%= fltDate.toString().substring(0,11) %>"/>
									  </logic:present>
									  <logic:notPresent name="routingvo" property="onwardFlightDate">
											<ibusiness:calendar property="depDate" indexId="rowCount" type="image" id="incalender"
													componentID="CMP_MailTracking_Defaults_AddPopup_depDate" onblur="autoFillDate(this)"
													value=""/>
									  </logic:notPresent>

									  </td>
									  <td>

										<logic:present name="routingvo" property="pou">
											<ihtml:text property="pointOfUnlading" indexId="rowCount" componentID="CMP_MailTracking_Defaults_AddPopup_pointOfUnlading"
												value="<%= routingvo.getPou() %>"/>
										</logic:present>
										<logic:notPresent name="routingvo" property="pou">
											<ihtml:text property="pointOfUnlading" indexId="rowCount" componentID="CMP_MailTracking_Defaults_AddPopup_pointOfUnlading"
												value=""/>
										</logic:notPresent>

										<img id="templatepou" value="templatepou" name="templatepou" src="<%= request.getContextPath()%>/images/lov.gif" height="16"
													index="<%=String.valueOf(rowid)%>"  onclick="displayLOV('showAirport.do','N','N','showAirport.do',targetFormName.pointOfUnlading[index].value,'pointOfUnlading','0','pointOfUnlading','',index);" />

									  </td>
									</tr>

								</logic:notPresent>

								</logic:iterate>

								</logic:present>

							<% } %>

							</logic:present>

						</logic:iterate>
          			</logic:present>

          			</logic:equal>
          			
          			<!-- templateRow -->
				<tr template="true" id="assignContainerTemplateRow" style="display:none">
					<ihtml:hidden property="opFlag" value="NOOP" />
					<td class="iCargoTableDataTd ic-center" style="padding-left:14px;">
						<input type="checkbox" name="contSubCheck">
					</td>
					<td class="iCargoTableDataTd">
						<ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrier"  flightCodeProperty="fltNo" carriercodevalue="" flightcodevalue="" componentID="CMP_MailTracking_Defaults_AddPopup_fltNo"/>
					</td>
					<td class="iCargoTableDataTd">
					     <ibusiness:calendar property="depDate" type="image" id="incalender"
							componentID="CMP_MailTracking_Defaults_AddPopup_depDate" 
							onblur="autoFillDate(this)" value=""/>
					</td>
					<td class="iCargoTableDataTd">
					     <ihtml:text property="pointOfUnlading" componentID="CMP_MailTracking_Defaults_AddPopup_pointOfUnlading" value=""/>
						 <div class="lovImgTbl valignT">
					     <img id="poulov" name="poulov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" />
						 </div>
					</td>
				</tr>
				<!--template row ends-->

				  </tbody>
				</table>
			  </div>
		  </div>
		 <div class="ic-row">
			<div class="ic-button-container">
                <ihtml:nbutton property="btAddNew" componentID="CMP_MailTracking_Defaults_AddPopup_btAddNew">
					<common:message key="mailtracking.defaults.assigncontainer.popup.btn.addNew" />
				</ihtml:nbutton>
			</div>
		</div>
		
	</fieldset>

	</div>
 
  
<div class="ic-foot-container">
	<div class="ic-button-container">

    	<ihtml:nbutton property="btOk" componentID="CMP_MailTracking_Defaults_AddPopup_btOk">
			<common:message key="mailtracking.defaults.assigncontainer.popup.btn.ok" />
		</ihtml:nbutton>
		<ihtml:nbutton property="btClose" componentID="CMP_MailTracking_Defaults_AddPopup_btClose">
			<common:message key="mailtracking.defaults.assigncontainer.popup.btn.close" />
		</ihtml:nbutton>

  </div>
  </div>
  
</div>

</ihtml:form>
</div>
	</body>

</html:html>
