<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  MailExportList.jsp
* Date          	 :  28-MAR-2008
* Author(s)     	 :  RENO K ABRAHAM

*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm"%>
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
		
	

<title><common:message bundle="mailexportListResources" key="mailtracking.defaults.mailexportlist.lbl.title" /></title>

<meta name="decorator" content="mainpanelrestyledui">

<common:include type="script" src="/js/mail/operations/MailExportList_Script.jsp" />


</head>

<body id="bodyStyle">
	
	


<div id="pagDiv" class="iCargoContent" style="overflow:auto; height:100%;" >

<ihtml:form action="mailtracking.defaults.mailexportlist.screenloadCommand.do">


<bean:define id="ExportListForm" name="MailExportListForm"
   	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm"
   	toScope="page" scope="request"/>

<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailexportlist" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>

<business:sessionBean id="mailAcceptanceVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailexportlist" method="get" attribute="mailAcceptanceVO" />
	<logic:present name="mailAcceptanceVOSession">
		<bean:define id="mailAcceptanceVOSession" name="mailAcceptanceVOSession" toScope="page"/>
	</logic:present>

<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="disableDestnFlag" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="closeFlag" />
<ihtml:hidden property="warningFlag" />
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="selCont" />
<ihtml:hidden property="selDSN" />
<ihtml:hidden property="status" />
<ihtml:hidden property="assignedto" />
<ihtml:hidden property="reCON" />
<ihtml:hidden property="reDSN" />
<ihtml:hidden property="warningOveride" />
<ihtml:hidden property="disableButtonsForTBA" />
<ihtml:hidden property="duplicateAndTbaTbc" />
<ihtml:hidden property="disableButtonsForAirport" />
<ihtml:hidden property="transferContainerFlag"/>
<ihtml:hidden property="selectedContainer"/><!-- Added by A-7371 for ICRD-133987-->

<input type="hidden" name="mySearchEnabled" />
<div class="ic-content-main">

	<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.mailexportlist.lbl.pagetitle" />
	</span>
	<div class="ic-head-container">
				<div class="ic-filter-panel">
				<div class="ic-input-container">
				<div class="ic-row ic-border" style="width:1370px;"><!--modified by a-7871 for ICRD-243976-->
						<div class="ic-input ic-split-5 "></div>
							<div class="ic-input ic-split-10 ">
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.acceptto" />
									</label>
									
					</div>
					<div class="ic-input ic-split-10 ic_inline_chcekbox"><!--modified by a-7871 for ICRD-243976-->
									
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.flight" />
									</label>
									<ihtml:radio property="assignToFlight" onclick="selectDiv();" value="FLIGHT" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_RADIOFLIGHT"/>
					</div>
					<div class="ic-input ic-split-10 ic_inline_chcekbox"><!--modified by a-7871 for ICRD-243976-->
					
									 
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.carrier" />
									</label>
									<ihtml:radio property="assignToFlight" onclick="selectDiv();" value="CARRIER" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_RADIODESTINATION"/>
					</div>
				</div>
				<div class="ic-row">
					 <div id="FLIGHT">
						<div class="ic-input ic-split-20 ic-label-30 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.flightno" />
									</label>
									<logic:notPresent name="mailAcceptanceVOSession" property="flightNumber">
										<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue="" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_FLIGHTNO"/>
									</logic:notPresent>
		      
									<logic:present name="mailAcceptanceVOSession" property="flightCarrierCode">
										<logic:present name="mailAcceptanceVOSession" property="flightNumber">
										<bean:define id="carrierCode" name="mailAcceptanceVOSession" property="flightCarrierCode" toScope="page"/>
										<bean:define id="flightNo" name="mailAcceptanceVOSession" property="flightNumber" toScope="page"/>
										<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_FLIGHTNO"/>
										</logic:present>
									</logic:present>
						</div>
						<div class="ic-input ic-split-20 ic-label-30 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.depdate" />
									</label>
									<logic:notPresent name="mailAcceptanceVOSession" property="strFlightDate">
										<ibusiness:calendar property="depDate" id="flightDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_FLIGHTDATE" />
									</logic:notPresent>
									<logic:present name="mailAcceptanceVOSession" property="strFlightDate">
										<bean:define id="depDate" name="mailAcceptanceVOSession" property="strFlightDate" toScope="page"/>
										<ibusiness:calendar property="depDate" id="flightDate" type="image" value="<%=(String)depDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_FLIGHTDATE" />
									</logic:present>
						</div>
					</div>
					<div id="CARRIER">
						<div class="ic-col-22  ic-input ic-mandatory ic-label-25" style="margin-left:30px;" ><!--Modified by A-7531 for icrd-216843-->
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.carrier" />
									</label>
									 <logic:notPresent name="mailAcceptanceVOSession" property="flightCarrierCode">
										<ihtml:text property="carrierCode" maxlength="3" value="" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_CARRIERCODE" />
									</logic:notPresent>
									<logic:present name="mailAcceptanceVOSession" property="flightCarrierCode">
										<bean:define id="carCode" name="mailAcceptanceVOSession" property="flightCarrierCode" toScope="page"/>
										<ihtml:text property="carrierCode" maxlength="3" value="<%=(String)carCode%>" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_CARRIERCODE" />
									</logic:present>
									<div class="lovImg"> <img id="carLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','1','carrierCode','',0)"> </div>
						</div>
						<div class="ic-col-22 ic-input ic-mandatory ic-label-25">
									<label>
										<common:message key="mailtracking.defaults.mailexportlist.lbl.destination" />
									</label>
									<logic:notPresent name="mailAcceptanceVOSession" property="destination">
										<ihtml:text property="destination" maxlength="4" value="" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_DESTINATION" />
									</logic:notPresent>
									<logic:present name="mailAcceptanceVOSession" property="destination">
										<bean:define id="finalDest" name="mailAcceptanceVOSession" property="destination" toScope="page"/>
									<ihtml:text property="destination" maxlength="4" value="<%=(String)finalDest%>" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_DESTINATION" />
									</logic:present>
									 <div class="lovImg"> <img id="desLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','1','destination','',0)"></div>
						</div>
						</div>
						<div class="ic-input ic-split-20 ic-label-30">
							<label>
								<common:message key="mailtracking.defaults.mailexportlist.lbl.depport" />
							</label>
							<ihtml:text property="departurePort" maxlength="4" value="<%=ExportListForm.getDeparturePort()%>" componentID="CMP_MAILTRACKING_DEFAULTS_EXPORTLIST_DEPPORT"/>
						</div>
						
							<logic:notPresent name="flightValidationVOSession" property="flightRoute">
		   <logic:present name="flightValidationVOSession" property="operationalStatus">
		   <div>
		         <%int infoCount=0;%>
		   <img id="polpouInfo_<%=infoCount%>" src="<%=request.getContextPath()%>/images/info.gif"  width="16" height="16"  onclick="prepareAttributes(this,'polpouInfotab_<%=infoCount%>','polpouInfo')" />
						<div style="display:none;width:100%;height:100%;" id="polpouInfotab_<%=infoCount%>" name="polpouInfo">	
							<table class="iCargoBorderLessTable">
								<thead>
									<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailexportlist.infopol" /></th>
									<th width= "35%" class="iCargoHeadingLabel"><common:message key="mailtracking.defaults.mailexportlist.infopoU" /></th>
								</thead>
								<logic:present name="mailAcceptanceVOSession">
								<logic:present name="mailAcceptanceVOSession" property="polPouMap">
									<logic:iterate id="polPouMap" name="mailAcceptanceVOSession" property="polPouMap">
									<bean:define id="values" name="polPouMap" property="value" type="java.util.Collection"/>
										<logic:iterate id="val" name="values" type="java.lang.String" >
										<tr height="25%">
											<td width= "35%" class="iCargoLabelCenterAligned"><bean:write name ="polPouMap" property="key"/></td>
											<td width= "35%" class="iCargoLabelCenterAligned"><%=String.valueOf(val)%></td>
										</tr>
										</logic:iterate>
									</logic:iterate>
								</logic:present>
								</logic:present>
							</table>
						</div>
						</div>
			</logic:present>
		</logic:notPresent>
		
		<div id="FLIGHTDETAILS">
		
			<div class="ic-input ic-split-25">

		<logic:present name="flightValidationVOSession" property="operationalStatus">
				 <bean:define id="oprstat" name="flightValidationVOSession" property="operationalStatus" toScope="page"/>				 
		   </logic:present>
		   <logic:present name="flightValidationVOSession" property="flightRoute">
		 	<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
		   </logic:present>
		   <logic:notPresent name="flightValidationVOSession" property="flightRoute">
		        <logic:present name="flightValidationVOSession" property="statusDescription">
				 <common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
		   
		   
				</logic:present>
		   </logic:notPresent>
			</div>
		</div>
		<div class="ic-button-container">
							<ihtml:nbutton property="btnList"  componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_LIST" accesskey="L">
								<common:message key="mailtracking.defaults.mailexportlist.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClear"  componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_CLEAR" accesskey="C" >
								<common:message key="mailtracking.defaults.mailexportlist.btn.clear" />
							</ihtml:nbutton>
		</div>
	</div>				
		
					
					
			
		</div>
	</div>
	</div>
	<div class="ic-main-container">
	
		<div class="ic-row">
		
			 <h4><common:message key="mailtracking.defaults.mailexportlist.lbl.ulddetails" /></h4>
		
		</div>
	
	
	 <div class="ic-row">
		<div class="tableContainer" id="div1" style="width:100%;overflow:auto;height:650px;">
			<table  class="fixed-header-table" id="mailExportListTable" style="width:100%;">
				<thead>
						<tr class="iCargoTableHeadingCenter" >
							
							<td style="padding-left:30px;width:8%;" class="iCargoTableHeaderLabel"><input type="checkbox" name="masterContainer" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);"/><span></span></td>
							<td width="14%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.uld" /><span></span></td>
							<td width="6%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.pou" /><span></span></td>
							<td width="6%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.destn" /><span></span></td>
							<td width="16%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.onwardflights" /><span></span></td>
							<td width="8%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.numbags" /><span></span></td>
							<td width="8%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.wt" /><span></span></td>
							<td width="8%" class="iCargoTableHeaderLabel"> <common:message key="mailtracking.defaults.mailexportlist.lbl.warehouse" /><span></span></td>
							<td width="8%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.loc" /><span></span></td>
							<td width="18%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailexportlist.lbl.remarks" /><span></span></td>
									  
						</tr>
				</thead>
		<tbody>
		<% int i = 0;%>
		<logic:present name="mailAcceptanceVOSession" property="containerDetails">
		<bean:define id="containerDetailsVOsColl" name="mailAcceptanceVOSession" property="containerDetails" scope="page" toScope="page"/>

		 <% Collection<String> selectedrows = new ArrayList<String>(); %>
		 <logic:present name="ExportListForm" property="selectMail" >
		 <%
			String[] selectedRows = ExportListForm.getSelectMail();
			for (int j = 0; j < selectedRows.length; j++) {
				selectedrows.add(selectedRows[j]);
			}
		%>
		</logic:present>
		<logic:iterate id="containerDetailsVO" name="containerDetailsVOsColl" indexId="rowCount">
		
	        <!--Parent Rows -->
		  <tr id="container<%=i%>" class="ic-table-row-main">
		    <td class="iCargoTableTd">
		       
			<a href="#" onclick="toggleRows(this)" class="ic-tree-table-expand  tier1">
			</a>
			  <bean:define id="compcode" name="containerDetailsVO" property="companyCode" toScope="page"/>
			  <% String primaryKey=(String)compcode+String.valueOf(i);%>
	                  <%
				if(selectedrows.contains(primaryKey)){
			  %>

				<input type="checkbox" name="selectMail" value="<%=primaryKey%>" checked="true">
			  <%
				}
				else{
			  %>
				<input type="checkbox" name="selectMail" value="<%=primaryKey%>" />

			  <%
				}
			  %>
	               
		    </td>
		    <td class="iCargoTableTd">
				<logic:present name="containerDetailsVO" property="paBuiltFlag">
					<logic:equal name="containerDetailsVO" property="paBuiltFlag" value="Y">
						<logic:present name="containerDetailsVO" property="containerNumber">
							<bean:write name="containerDetailsVO" property="containerNumber"/>
						</logic:present>
						<common:message key="mailtracking.defaults.mailexportlist.lbl.shipperBuild" />
					</logic:equal>
					<logic:equal name="containerDetailsVO" property="paBuiltFlag" value="N">				  			
						<logic:present name="containerDetailsVO" property="containerNumber">
							<bean:write name="containerDetailsVO" property="containerNumber"/>
						</logic:present>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="containerDetailsVO" property="paBuiltFlag">
				   <logic:present name="containerDetailsVO" property="containerNumber">
					<bean:write name="containerDetailsVO" property="containerNumber"/>
				   </logic:present>	
				</logic:notPresent>
				
				<logic:present name="containerDetailsVO" property="containerNumber">
					<bean:define id="uld" name="containerDetailsVO" property="containerNumber"/> 
					<bean:define id="typ" name="containerDetailsVO" property="containerType"/>
					<html:hidden property="uldType" value="<%=(String) typ%>" /> <!--added by A-8149 for ICRD-270524-->
					<% String uldnum = (String)typ +"-"+ (String)uld;%>
					<ihtml:hidden property="uldnos" value="<%=uldnum%>" />
				</logic:present>			
		    </td>
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="pou"/></td>
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="destination"/></td>
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="route"/></td>
		    <td class="iCargoTableTd"><div align="right"><bean:write name="containerDetailsVO" property="totalBags" format="####"/></div></td>
		    <td class="iCargoTableTd"><div align="right"><common:write name="containerDetailsVO" property="totalWeight" unitFormatting="true"/></div></td><!--modified by A-7371-->
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="wareHouse"/></td>
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="location"/></td>
		    <td class="iCargoTableTd"><bean:write name="containerDetailsVO" property="remarks"/></td>
		  </tr>
		 <!--Child Rows -->		 
		 <% int k = 0;%>
		  <tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
		    <td colspan="10"><div class="tier4"><a href="#" ></a></div>
		      <table width="100%">
		       <tr>
			<td>
				
			   <table>
			      <thead>
				  <tr>
				    <td width="3%" class="iCargoTableHeader">&nbsp;</td>
				    <td width="10%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.dsn" /></td>
				    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.origin" /></td>
				    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.destnoe" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.class" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.cat" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.subclass" /></td>
				    <td width="11%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.year" /></td>
				    <td width="13%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.numbags" /></td>
				    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.wt" /></td>
				    <td width="9%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailexportlist.lbl.plt" /></td>
				  </tr>
				</thead>
				<tbody>
				 <logic:present name="containerDetailsVO" property="dsnVOs">
				 <bean:define id="dsnVOsColl" name="containerDetailsVO" property="dsnVOs" scope="page" toScope="page"/>				 
				   <% Collection<String> selecteddsnrows = new ArrayList<String>(); %>
				   <logic:present name="ExportListForm" property="selectDSN" >
				   <%
						String[] selecteddsn = ExportListForm.getSelectDSN();
						for (int j = 0; j < selecteddsn.length; j++) {
							selecteddsnrows.add(selecteddsn[j]);
						}
					%>
					</logic:present>
				 <logic:iterate id="dsnVO" name="dsnVOsColl">
				  <tr  >
				    <td class="iCargoTableTd">
				       <div class="tier1">
						  <bean:define id="dsn" name="dsnVO" property="dsn" toScope="page"/>
				  		  <% String subKey = primaryKey+"~"+k+"~"+dsn;%>
				            <%
								if(selecteddsnrows.contains(subKey)){
						  	%>
			
							<input type="checkbox" name="selectDSN" value="<%=subKey%>" checked="true">
						 	<%
								}
								else{
						 	%>
							<input type="checkbox" name="selectDSN" value="<%=subKey%>" />
			
						  	<%
								}
						 	%>
			          </div>
				    </td>
					<td class="iCargoTableTd"><bean:write name="dsnVO" property="dsn"/></td>
					<td class="iCargoTableTd"><bean:write name="dsnVO" property="originExchangeOffice"/></td>
					<td class="iCargoTableTd"><bean:write name="dsnVO" property="destinationExchangeOffice"/></td>
					<td class="iCargoTableTd"><bean:write name="dsnVO" property="mailClass"/></td>
					<td class="iCargoTableTd"><bean:write name="dsnVO" property="mailCategoryCode"/></td>
					<td class="iCargoTableTd">

					 <% String subclassValue = ""; %>
					  <logic:notPresent name="dsnVO" property="mailSubclass">
						<bean:write name="dsnVO" property="mailSubclass"/>
					</logic:notPresent>
					<logic:present name="dsnVO" property="mailSubclass">
					<bean:define id="despatchSubclass" name="dsnVO" property="mailSubclass" toScope="page"/>
					<% subclassValue = (String) despatchSubclass;
								   int arrays=subclassValue.indexOf("_");
								   if(arrays==-1){%>

						<bean:write name="dsnVO" property="mailSubclass"/>
						<%}else{%>
						&nbsp;
						<%}%>
					</logic:present>

					</td>
					<td class="iCargoTableTd" ><div align="right"><bean:write name="dsnVO" property="year"/></div></td>
					<td class="iCargoTableTd"><div align="right"><bean:write name="dsnVO" property="bags" format="####"/></div></td>
					<td class="iCargoTableTd"><div align="right"><common:write name="dsnVO" property="weight" unitFormatting="true"/></div></td><!-- modified by A-7371-->
					<td class="iCargoTableTd">
						<div align="center">
							<logic:notPresent name="dsnVO" property="pltEnableFlag">
								<!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
								<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
							 </logic:notPresent>
							 <logic:present name="dsnVO" property="pltEnableFlag">
								<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
								       <!--<input type="checkbox" name="isPrecarrAwb" value="true" checked disabled="true"/>-->
								       <img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
								</logic:equal>
								<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
								     <!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
								     <img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
								</logic:equal>
							 </logic:present>
						 </div>
				     </td>
				   </tr>
		          <% k++;%>				   
				 </logic:iterate>
				 </logic:present>
				</tbody>
			   </table>
			 </td>
			</tr>
		       </table>
		      </td>
		     </tr>		     
		  <% i++;%>
	  	    </logic:iterate>
                    </logic:present>
                     
                    <tbody>				
			</table>
			
		</div>
	 </div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-button-container paddR5">
		 <!-- Added by A-7371 for ICRD-133987 starts-->
						 <ihtml:nbutton property="btnTransfer"
							componentID="BTN_MAIL_OPERATIONS_MAILEXPORTLIST_TRANSFER"
							accesskey="F">
							<common:message
								key="mailtracking.defaults.mailexportlist.btn.transfer" />
						</ihtml:nbutton>
					
					  <ihtml:nbutton property="btnReassignContainer" componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_REASSIGNCONTAINER" accesskey="T" >
						<common:message key="mailtracking.defaults.mailexportlist.btnreassigncontainer" />
					  </ihtml:nbutton>
				      <ihtml:nbutton property="btnReassignDSN" componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_REASSIGNDSN" accesskey="N">
						<common:message key="mailtracking.defaults.mailexportlist.btnreassigndsn" />
					  </ihtml:nbutton>
				      <ihtml:nbutton property="btnReassignMailbag" componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_REASSIGNMAILBAG" accesskey="B">
						<common:message key="mailtracking.defaults.mailexportlist.btnreassignmailbag" />
					  </ihtml:nbutton>
				      <ihtml:nbutton property="btnViewMailbag" componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_VIEWMAILBAG" accesskey="V" >
						<common:message key="mailtracking.defaults.mailexportlist.btnviewmailbags" />
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_EXPORTLIST_CLOSE" accesskey="O" >
						<common:message key="mailtracking.defaults.mailexportlist.btnclose" />
					  </ihtml:nbutton>
				
				</div>
	</div>
</div>
</ihtml:form>
</div>

				
		
	</body>
</html:html>
