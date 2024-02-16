<%--*********************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: ReassignMail.jsp
* Date				: 15-Jun-2006
* Author(s)			: A-1876
 **********************************************--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
	
		<html:html locale="true">
		
		<head> 
		
			
		<%@ include file="/jsp/includes/customcss.jsp" %>	
	

   <logic:equal name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
	   <title><common:message bundle="reassignMailResources" key="mailtracking.defaults.assignmail.lbl.title" /></title>
	</logic:equal>

	<logic:notEqual name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
       	  <title><common:message bundle="reassignMailResources" key="mailtracking.defaults.reassignmail.lbl.title" /></title>
  	</logic:notEqual>
   <meta name="decorator" content="popup_panel">
   <common:include type="script" src="/js/mail/operations/ReassignMail_Script.jsp" />
</head>
<body id="bodyStyle">
<div id="divmain" class="iCargoPopUpContent"  >	
<ihtml:form action="/mailtracking.defaults.reassignmail.screenloadreassignmail.do" styleClass="ic-main-form">
<bean:define id="form" name="ReassignMailForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm"
    toScope="page" scope="request"/>
<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.reassignmail" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="containerVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.reassignmail" method="get" attribute="containerVOs" />
	<logic:present name="containerVOsSession">
		<bean:define id="containerVOsSession" name="containerVOsSession" toScope="page"/>
	</logic:present>

<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="closeFlag" />
<ihtml:hidden property="container" />
<ihtml:hidden property="carrierIdInv" />
<ihtml:hidden property="carrierCodeInv" />
<ihtml:hidden property="hideRadio" />
<ihtml:hidden property="selectMode" />
<ihtml:hidden property="reassignFocus" />
<ihtml:hidden property="existingMailbagFlag" />
<ihtml:hidden property="preassignFlag" />
<ihtml:hidden property="duplicateFlag" />

<div class="ic-content-main">

<div class="ic-head-container">
<div class="ic-row">
      <logic:equal name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
	  <span class="ic-page-title ">
 <common:message key="mailtracking.defaults.assignmail.lbl.title" />
	</span>
	  
	  </logic:equal>

	  <logic:notEqual name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
	  	<span class="ic-page-title ">
<common:message key="mailtracking.defaults.reassignmail.lbl.pagetitle" />
</span>
       	  
  	  </logic:notEqual>

</div>
<div class="ic-row">
							<div class="ic-input-container ic_inline_chcekbox ic-round-border">
								<div class="ic-row">
 <logic:present name="ReassignMailForm" property="hideRadio">
		        <logic:equal name="ReassignMailForm" property="hideRadio" value="CARRIER">
			    <ihtml:hidden property="assignToFlight" value="FLIGHT" />
			</logic:equal>
			<logic:equal name="ReassignMailForm" property="hideRadio" value="FLIGHT">
			    <ihtml:hidden property="assignToFlight" value="DESTINATION" />
			</logic:equal>
		    </logic:present>
			<logic:present name="ReassignMailForm" property="hideRadio">

		<logic:equal name="ReassignMailForm" property="hideRadio" value="NONE">
									<div class="ic-input ic_inline_chcekbox ic-split-30">
		<logic:equal name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
			 		<common:message key="mailtracking.defaults.reassignmail.lbl.assignto" />
		  	</logic:equal>

		  	<logic:notEqual name="ReassignMailForm" property="fromScreen" value="CONSIGNMENT">
			 		<common:message key="mailtracking.defaults.reassignmail.lbl.reassignto" />
	  	  	</logic:notEqual>
		</div>
									<div class="ic-input  ic-split-30">
 <ihtml:radio property="assignToFlight" onclick="selectDiv();" value="FLIGHT" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_RADIOFLIGHT"/>
 <label>
<common:message key="mailtracking.defaults.reassignmail.lbl.radioflight" />
</label>
</div>
		<div class="ic-input  ic-split-15">
 <ihtml:radio property="assignToFlight" onclick="selectDiv();" value="DESTINATION" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_RADIODESTINATION"/>
<label>
 <common:message key="mailtracking.defaults.reassignmail.lbl.radiodestn" />
 </label>
		</div> </logic:equal>
	      </logic:present>
</div>

</div>
						</div>

<div class="ic-row">
			<div class="ic-input-container ic-round-border">
					<div class="ic-row">
					
						<div id="FLIGHT">
						
						<div class="ic-col-30 ic-input ic-mandatory">
					
								<label><common:message key="mailtracking.defaults.reassignmail.lbl.flightno" /></label>
							<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight" flightCodeProperty="flightNumber"
				carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>"  tabindex="1"
				carrierCodeMaxlength="3" flightCodeMaxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_FLIGHTNO"/>

		   							</div>
							<div class="ic-col-35 ic-mandatory ic-input ic-label-40">
								<label><common:message key="mailtracking.defaults.reassignmail.lbl.flightdate" /></label>
								   <ibusiness:calendar property="depDate" type="image" id="incalender"
				value="<%=form.getDepDate()%>"  tabindex="2"
			     componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_FLIGHTDATE" onblur="autoFillDate(this)"/>
							</div>
							
						</div>
						
						
						<div id="DESTINATION">
							<div class="ic-input ic-split-30 ic-mandatory ">
								<label><common:message key="mailtracking.defaults.reassignmail.lbl.carriercode" /></label>
								    <ihtml:text property="carrierCode" maxlength="3" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_CARRIERCODE"/>
			<div class="lovImg">						
		      <img value="carrierLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','0','carrierCode','',0)">
			</div>  
							</div>
							<div class="ic-input ic-split-30 ic-mandatory ">
								<label><common:message key="mailtracking.defaults.reassignmail.lbl.destination" /></label>
								<ihtml:text property="destination" maxlength="4" tabindex="2" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_DESTINATION" />
				<div class="lovImg">
                    <img value="destinationLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','0','destination','',0)">
				</div>	
                  
							</div>
						</div>
						
						
								<div class="ic-button-container">
								
								<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAIL_LIST" tabindex="3">
			<common:message key="mailtracking.defaults.reassignmail.btn.list" />
		</ihtml:nbutton>
		<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAIL_CLEAR" tabindex="4">
			<common:message key="mailtracking.defaults.reassignmail.btn.clear" />
		</ihtml:nbutton>
						</div>
				
						
						
						</div>	
			<div class="ic-row marginT5">
	<div class="ic-input ic-split-30">
	<label>
	<common:message key="mailtracking.defaults.reassignmail.lbl.depport" />
	</label>
						 <ihtml:text property="departurePort" maxlength="4" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_DEPPORT" />
						</div>
			
			<div class="ic-col-70 ic-label-45">
			  <div id="FLIGHTDETAILS" >
		   		<logic:present name="flightValidationVOSession" property="flightRoute">
		 			<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
		   		</logic:present>
		   		<logic:notPresent name="flightValidationVOSession" property="flightRoute">
		       		 &nbsp;
		   		</logic:notPresent>
		    </div>
			</div>
			</div>
		</div>
		
		</div>
		</div>
		<div class="ic-main-container" style="top:130px; height:173px;">
	
	<div class="ic-row iCargoLink">
											<div class="ic-col-30 ic-bold-label ic-left">
												<label>&nbsp; Container Details</label>
											</div>
	<div class="ic-button-container">
	 <logic:equal name="ReassignMailForm" property="preassignFlag" value="N" >
		     <a href="#" id="addLink" value="add" name="add" class="iCargoLink"><common:message key="mailtracking.defaults.reassignmail.lnk.addcontainer" /></a>
		    </logic:equal>
		    <logic:notEqual name="ReassignMailForm" property="preassignFlag" value="N" >
			&nbsp;
		    </logic:notEqual>
	</div>
	</div>
	
	
	
	<div class="ic-row ">
	 <div  id="div1" class="tableContainer" style="height:80px" >
	
	 <table class="fixed-header-table" >
                 <thead>
                   <tr class="iCargoTableHeadingLeft" >
                     <td width="10%" align="center" class="iCargoTableHeaderLabel"></td>
                     <td width="30%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassignmail.lbl.containerno" /></td>
                     <td width="15%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.reassignmail.lbl.pou" /></td>
                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassignmail.lbl.finaldest" /></td>
                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassignmail.lbl.numbags" /></td>
                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.reassignmail.lbl.weight" /></td>
                   </tr>
                </thead>
				<tbody>
                  <logic:present name="containerVOsSession">
                  <bean:define id="containerVOs" name="containerVOsSession" toScope="page"/>
               	      <logic:iterate id="containerVO" name="containerVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.ContainerVO">
						<common:rowColorTag index="rowid">
						<tr class="iCargoTableCellsLeftRowColor1">
						  <td><div align="center"><input type="checkbox" name="selectContainer" value="<%= rowid.toString() %>" onclick="singleSelectCb(this.form,'<%= rowid.toString() %>','selectContainer');"></div></td>
						  <td><logic:present name="containerVO" property="paBuiltFlag">
								<logic:equal name="containerVO" property="paBuiltFlag" value="Y">
									<bean:write name="containerVO" property="containerNumber"/>
									<common:message key="mailtracking.defaults.reassignmail.lbl.shipperBuild" />
								</logic:equal>
								<logic:equal name="containerVO" property="paBuiltFlag" value="N">
									<bean:write name="containerVO" property="containerNumber"/>
								</logic:equal>
							</logic:present>
							<logic:notPresent name="containerVO" property="paBuiltFlag">
								<bean:write name="containerVO" property="containerNumber"/>
							</logic:notPresent>
						  </td>
						  <td><bean:write name="containerVO" property="pou"/></td>
						  <td><bean:write name="containerVO" property="finalDestination"/></td>
						  <td style="text-align:right"><bean:write name="containerVO" property="bags"/></td>
						  <td style="text-align:right"><common:write name="containerVO" property="weight"  unitFormatting="true"/></td><!--modified  by A-7371-->
						</tr>
						</common:rowColorTag>
					  </logic:iterate>
                    </logic:present>
                  </tbody>
               </table>
	 </div>
	
	
	   <logic:present name="ReassignMailForm" property="hideRadio">
  		        <logic:notEqual name="ReassignMailForm" property="hideRadio" value="FLIGHT">


<div class="ic-row">
<div class="ic-input ic-mandatory ic-split-60">
		  <label>
		  <common:message key="mailtracking.defaults.reassignmail.lbl.scandate" />
    		</label>

    	  <ibusiness:calendar property="scanDate" type="image" id="scanDate"
    			value="<%=form.getScanDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_REASSIGNMAIL_SCANDATE" onblur="autoFillDate(this)" tabindex="5"/>

    	  <ibusiness:releasetimer property="mailScanTime" indexId="index" tabindex="6" componentID="TXT_MAILTRACKING_DEFAULTS_REASSIGNMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=form.getMailScanTime()%>"/>
    	  </div>

 </div>

   </logic:notEqual>

  </logic:present>
	
	
	
	
</div>	
</div>


<div class="ic-foot-container paddR5">
<div class="ic-button-container">
 <ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAIL_OK" tabindex="5">
		<common:message key="mailtracking.defaults.reassignmail.btn.ok" />
	</ihtml:nbutton>
	<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_REASSIGNMAIL_CLOSE" tabindex="6">
		<common:message key="mailtracking.defaults.reassignmail.btn.close" />
	</ihtml:nbutton>
</div>

</div>


</div>
</ihtml:form >
</div>	

		 
	</body>
		</html:html>