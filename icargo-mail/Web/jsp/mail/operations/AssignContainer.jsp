<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: AssignContainer.jsp
* Date				: 29-May-2006
* Author(s)			: A-1861
 --%>


<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>

<html:html>

<head>
	
	<title><common:message bundle="assignContainerResources" key="mailtracking.defaults.assigncontainer.lbl.pagetitle" /></title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/AssignContainer_Script.jsp" />
</head>

<body>

	
<div id="pageDiv" class="iCargoContent ic-masterbg" style="height:100%; overflow:auto;" >
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<ihtml:form action="/mailtracking.defaults.assigncontainer.screenloadAssignContainer.do" styleClass="ic-main-form" >

<bean:define id="form"
	name="AssignContainerForm"
    	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm"
    	toScope="page" scope="request"/>

<business:sessionBean id="flightValidationVO"
	  moduleName="mail.operations"
	  screenID="mailtracking.defaults.assignContainer"
	  method="get"
	  attribute="flightValidationVO" />

<business:sessionBean id="containerVOs"
	  moduleName="mail.operations"
	  screenID="mailtracking.defaults.assignContainer"
	  method="get"
	  attribute="containerVOs" />

<business:sessionBean id="polPouMap"
	  moduleName="mail.operations"
	  screenID="mailtracking.defaults.assignContainer"
	  method="get"
	  attribute="polPouMap" />

<ihtml:hidden property="status" />
<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="flightStatus" />
<ihtml:hidden  property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="warningFlag" />
<ihtml:hidden property="disableButtonsForTBA" />
<ihtml:hidden property="duplicateAndTbaTbc" />
<div class="ic-content-main">

 <span class="ic-page-title ic-display-none">
  	<common:message key="mailtracking.defaults.assigncontainer.lbl.pagetitle" />
  </span>
<div class="ic-head-container">
	<div class="ic-filter-panel">
		<div class="ic-row">
			<div class="ic-row ic-border" style="width:1370px;height:50px;">
					<div class="ic-row">
						<div class="ic-input ic-col-15 ic-label-80"><label><common:message key="mailtracking.defaults.assigncontainer.lbl.assignto" /></label>
						</div>
						<div class="ic-input ic-col-10 ic-label-10">
							<ihtml:radio property="assignedto" onclick="selectDiv();" value="FLIGHT" componentID="CMP_MailTracking_Defaults_AssignContainer_radioFlight"/>
							<common:message key="mailtracking.defaults.assigncontainer.lbl.flight" />
						</div>
						<div class="ic-input ic-col-10 ic-label-10">
						<ihtml:radio property="assignedto" onclick="selectDiv();" value="DESTINATION" componentID="CMP_MailTracking_Defaults_AssignContainer_radioDestination"/>
						<common:message key="mailtracking.defaults.assigncontainer.lbl.carrier" />
						</div>
					</div> 
			</div> 
		</div>  
		<div class="ic-row">
			<div class="ic-input-container ic-round-border">
					<div class="ic-row">
						<div id="FLIGHT">
							<div class="ic-col-20 ic-input ic-mandatory ic-label-45">
								<label><common:message key="mailtracking.defaults.assigncontainer.lbl.flightno" /></label>
								<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight"
										 flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>"
										 flightcodevalue="<%=form.getFlightNumber()%>"  carrierCodeMaxlength="3"
										 flightCodeMaxlength="5" componentID="CMP_MailTracking_Defaults_AssignContainer_FlightNo"/>
							</div>
							<div class="ic-col-20 ic-input ic-mandatory ic-label-45">
								<label><common:message key="mailtracking.defaults.assigncontainer.lbl.depdate" /></label>
								<ibusiness:calendar property="flightDate" type="image" id="incalender"
									 value="<%=form.getFlightDate()%>"  componentID="CMP_MailTracking_Defaults_AssignContainer_FlightDate" onblur="autoFillDate(this)"/>
							</div>
						</div>
						<div id="DESTINATION">
							<div class="ic-col-20 ic-input ic-mandatory ic-label-45">
								<label><common:message key="mailtracking.defaults.assigncontainer.lbl.carrier" /></label>
								<ihtml:text property="carrier" maxlength="3"  componentID="CMP_MailTracking_Defaults_AssignContainer_Carrier"/>
								<div class="lovImg">
								<img id="carrierLOV" value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
								</div>
							</div>
							<div class="ic-col-20 ic-input ic-mandatory ic-label-45">
								<label><common:message key="mailtracking.defaults.assigncontainer.lbl.destination" /></label>
								<ihtml:text property="destn" maxlength="4"  componentID="CMP_MailTracking_Defaults_AssignContainer_Destination" />
								<div class="lovImg">
								<img id="destinationLOV" value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								</div>
							</div>
						</div>
						<div class="ic-col-20">
						<div class="ic-button-container">
								<ihtml:nbutton property="btList"  componentID="CMP_MailTracking_Defaults_AssignContainer_btList" accesskey="L">
									<common:message key="mailtracking.defaults.assigncontainer.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="CMP_MailTracking_Defaults_AssignContainer_btClear" accesskey="E">
									<common:message key="mailtracking.defaults.assigncontainer.btn.clear" />
								</ihtml:nbutton>
						</div>
						</div>
						<div class="ic-col-10">
						</div>
						<div class="ic-col-30 ic-input">
						<label><common:message key="mailtracking.defaults.assigncontainer.lbl.depport" /></label>
						<ihtml:text property="departurePort" readonly="true" componentID="CMP_MailTracking_Defaults_AssignContainer_DepPort" />
						
						<logic:notPresent name="flightValidationVO" property="flightRoute">
							<logic:present name="flightValidationVO" property="operationalStatus">
								<td>
									<%int infoCount=0;%>
									<img id="polpouInfo_<%=infoCount%>" src="<%=request.getContextPath()%>/images/info.gif" height="16"  onclick="prepareAttributes(this,'polpouInfotab_<%=infoCount%>','polpouInfo')" />
									<div style="display:none;width:100%;height:100%;" id="polpouInfotab_<%=infoCount%>" name="polpouInfo">	
										<table class="iCargoBorderLessTable">
										<thead>
											<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.assigncontainer.infopol" /></th>
											<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.assigncontainer.infopoU" /></th>
										</thead>
										<logic:present name="polPouMap">							
											<logic:iterate id="polpou" name="polPouMap">
											<bean:define id="values" name="polpou" property="value" type="java.util.Collection"/>
												<logic:iterate id="val" name="values" type="java.lang.String" >
												<tr height="25%">
													<td width= "35%" class="iCargoLabelCenterAligned"><bean:write name ="polpou" property="key"/></td>
													<td width= "35%" class="iCargoLabelCenterAligned"><%=String.valueOf(val)%></td>
												</tr>
												</logic:iterate>
											</logic:iterate>
										</logic:present>
										<tbody></tbody>
										</table>
									</div>
								</td>
							</logic:present>
						</logic:notPresent>
						<div class="iCargoLabelCenterAligned">
							<div id="FLIGHTDETAILS" >
							  <logic:present name="flightValidationVO" >
							  <logic:present name="flightValidationVO" property="flightRoute">
								  <common:displayFlightStatus flightStatusDetails="flightValidationVO" />
							  </logic:present>
							   <logic:notPresent name="flightValidationVO" property="flightRoute">
								<logic:present name="flightValidationVO" property="statusDescription">
								 <common:displayFlightStatus flightStatusDetails="flightValidationVO" />   
						   
							  </logic:present>
							  </logic:notPresent>
							  </logic:present>
							  <logic:notPresent name="flightValidationVO">
								&nbsp;
							  </logic:notPresent>
							</div>
						</div>
						</div>
					</div>	
			</div>
		</div>
	</div>
        </div> 
  
  
  <div class="ic-main-container">
  
			<div class="ic-row iCargoLink">
					<div id="div2">
							<div class="iCargoHeadingLabel" ><common:message key="mailtracking.defaults.assigncontainer.lbl.assigndContainers" /></div>
								<div>
									<div class="ic-button-container paddR5">
										<a href="#" id="createLink" class="iCargoLink" value="create" name="create"><common:message key="mailtracking.defaults.assigncontainer.lbl.createLink" /></a> |
										<a href="#" id="modifyLink" class="iCargoLink" value="modify" name="modify"><common:message key="mailtracking.defaults.assigncontainer.lbl.modifyLink" /></a> |
										<a href="#" id="deleteLink" class="iCargoLink" value="delete" name="delete"><common:message key="mailtracking.defaults.assigncontainer.lbl.deleteLink" /></a>
									</div>
								</div>	
					</div>
			</div>


    
    <div id = "assigncontainerTable" >
   
      <div class="tableContainer" id="div1"  style="height:580px;">
        <table class="fixed-header-table">
          <thead>
            <tr>
                <td class="iCargoTableHeaderLabel" width="3%"><input type="checkbox" name="checkAll" value="checkbox"></td>
				<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.contNo" /></td>
				<td width="7%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.pou" /></td>
				<td width="12%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.assignedOn" /></td>
				<td width="11%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.assignedBy" /></td>
				<td width="14%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.onwardFlights" /></td>
				<td width="11%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.noOfBags" /></td>
				<td width="9%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.wt" /></td>
				<td width="18%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.assigncontainer.lbl.remarks" /></td>
            </tr>
          </thead>
          <tbody>

          	<logic:present name="containerVOs">
          	<bean:define id="containervos" name="containerVOs" toScope="page"/>

			<% Collection<String> selectedrows = new ArrayList<String>(); %>

			 <logic:present name="form" property="subCheck" >

				<%
				String[] selectedRows = form.getSubCheck();
				for (int j = 0; j < selectedRows.length; j++) {
					selectedrows.add(selectedRows[j]);
				}
				%>

			 </logic:present>

			<logic:iterate id="containervo" name="containervos" indexId="rowid">
			
			  <logic:present name="containervo" property="operationFlag">
				<bean:define id="operFlag" name="containervo" property="operationFlag" toScope="page"/>

				<logic:notEqual name="containervo" property="operationFlag" value="D">

				<tr>
				  <td>
					<div class="ic-center">
					<%
						if(selectedrows.contains(String.valueOf(rowid))){
					%>

						<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" checked="true">
					<%
						}
						else{
					%>
						<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>">

					<%
						}
					%>
					</div>
				  </td>
				  <td>
				  	<logic:present name="containervo" property="paBuiltFlag">
				  		<logic:equal name="containervo" property="paBuiltFlag" value="Y">
				  			<bean:define id="containerNumber" name="containervo" property="containerNumber" toScope="page"/>
				  			<bean:write name="containervo" property="containerNumber"/>
					 		<common:message key="mailtracking.defaults.assigncontainer.lbl.shipperBuild" />
				  		</logic:equal>
				  		<logic:equal name="containervo" property="paBuiltFlag" value="N">
				  			<bean:write name="containervo" property="containerNumber"/>
				  		</logic:equal>
				 	</logic:present>
				 	<logic:notPresent name="containervo" property="paBuiltFlag">
				  		<bean:write name="containervo" property="containerNumber"/>
					</logic:notPresent>
				  </td>
				  <td><bean:write name="containervo" property="pou"/></td>
				  <td>
					  <logic:present name="containervo" property="assignedDate">
					  <bean:define id="assignedDate" name="containervo" property="assignedDate" toScope="page"/>
					  <%= assignedDate.toString().substring(0,11) %>
					  </logic:present>
					  <logic:notPresent name="containervo" property="assignedDate">
						&nbsp;
					  </logic:notPresent>
				  </td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="assignedUser"/></td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="onwardFlights"/></td>
				  <td class="iCargoTableDataTd"><div class="ic-right"><bean:write name="containervo" property="bags"/></div></td>
				  <td class="iCargoTableDataTd"><div class="ic-right">
				  <common:write name="containervo" property="weight" unitFormatting="true" />
				  </div></td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="remarks"/></td>
            		    </tr>

            		</logic:notEqual>

            	  </logic:present>
            	  <logic:notPresent name="containervo" property="operationFlag">
            	  	<tr>
				  <td>
					<div class="ic-center">
					<%
						if(selectedrows.contains(String.valueOf(rowid))){
					%>

						<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" checked="true">
					<%
						}
						else{
					%>
						<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>">

					<%
						}
					%>
					</div>
				  </td>
				  <td>
				  	<logic:present name="containervo" property="paBuiltFlag">
				  		<logic:equal name="containervo" property="paBuiltFlag" value="Y">
				  			<bean:write name="containervo" property="containerNumber"/>
					 		<common:message key="mailtracking.defaults.assigncontainer.lbl.shipperBuild" />
				  		</logic:equal>
				  		<logic:equal name="containervo" property="paBuiltFlag" value="N">
				  			<bean:write name="containervo" property="containerNumber"/>
				  		</logic:equal>
				 	</logic:present>
				 	<logic:notPresent name="containervo" property="paBuiltFlag">
				  		<bean:write name="containervo" property="containerNumber"/>
					</logic:notPresent>

				  </td>
				  <td><bean:write name="containervo" property="pou"/></td>
				  <td>
					  <logic:present name="containervo" property="assignedDate">
					  <bean:define id="assignedDate" name="containervo" property="assignedDate" toScope="page"/>
					  <%= assignedDate.toString().substring(0,11) %>
					  </logic:present>
					  <logic:notPresent name="containervo" property="assignedDate">
						&nbsp;
					  </logic:notPresent>
				  </td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="assignedUser"/></td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="onwardFlights"/></td>
				  <td class="iCargoTableDataTd" style="text-align:right"><bean:write name="containervo" property="bags"/></td>
				  <td class="iCargoTableDataTd" style="text-align:right">
				    <common:write name="containervo" property="weight" unitFormatting="true" />
				  </td>
				  <td class="iCargoTableDataTd"><bean:write name="containervo" property="remarks"/></td>
            		  </tr>

            	     </logic:notPresent>
                   
          	  </logic:iterate>
          	</logic:present>

          </tbody>
        </table>
       </div>
       </div>
      


      </div>
  
  <div class="ic-foot-container">
	<div class="ic-button-container paddR5">
    <ihtml:nbutton property="btPrintULDTag" componentID="CMP_MailTracking_Defaults_AssignContainer_btPrintULDTag" accesskey="G">
		<common:message key="mailtracking.defaults.assigncontainer.btn.printuldtag" />
	</ihtml:nbutton>
	<ihtml:nbutton property="btPreAdvice" componentID="CMP_MailTracking_Defaults_AssignContainer_btPreAdvice" accesskey="A">
		<common:message key="mailtracking.defaults.assigncontainer.btn.preadvice" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btAcceptMail" componentID="CMP_MailTracking_Defaults_AssignContainer_btAcceptMail" accesskey="M">
		<common:message key="mailtracking.defaults.assigncontainer.btn.acceptMail" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btViewMailBags" componentID="CMP_MailTracking_Defaults_AssignContainer_btViewMailBags" accesskey="B">
		<common:message key="mailtracking.defaults.assigncontainer.btn.viewMailBags" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btReassign" componentID="CMP_MailTracking_Defaults_AssignContainer_btReassign" accesskey="N">
		<common:message key="mailtracking.defaults.assigncontainer.btn.reassign" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btDeassign" componentID="CMP_MailTracking_Defaults_AssignContainer_btDeassign" accesskey="U">
		<common:message key="mailtracking.defaults.assigncontainer.btn.deassign" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btSave" componentID="CMP_MailTracking_Defaults_AssignContainer_btSave" accesskey="S">
		<common:message key="mailtracking.defaults.assigncontainer.btn.save" />
	</ihtml:nbutton>

	<ihtml:nbutton property="btClose" componentID="CMP_MailTracking_Defaults_AssignContainer_btClose" accesskey="O">
		<common:message key="mailtracking.defaults.assigncontainer.btn.close" />
	</ihtml:nbutton>
  
	</div>
</div>

</div>
</ihtml:form>

<span style="display:none" id="tmpSpan"></span>

</div>

	</body>

</html:html>
