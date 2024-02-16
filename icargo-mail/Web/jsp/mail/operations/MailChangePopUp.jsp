<%--*********************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: MailChangePopUp.jsp
* Date				: 14-Jan-2016
* Author(s)			: A-6245
 **********************************************--%>


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
   <title><common:message bundle="mailArrivalResources" key="mailtracking.defaults.mailchange.lbl.pagetitle" /></title>
   <meta name="decorator" content="popup_panel">
   <common:include type="script" src="/js/mail/operations/MailChangePopUp_Script.jsp" />
</head>
<body>
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<div id="divmain" class="iCargoPopUpContent ic-masterbg">
<ihtml:form action="/mailtracking.defaults.mailchange.screenload.do" styleClass="ic-main-form" >

<bean:define id="form"
             name="MailArrivalForm"
             type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
             toScope="page" scope="request"/>
			 
<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="changeFlightValidationVO" />
		<logic:present name="flightValidationVOSession">
			<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
		</logic:present>
<business:sessionBean id="containerVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="containerVOs" />
		<logic:present name="containerVOsSession">
			<bean:define id="containerVOsSession" name="containerVOsSession" toScope="page"/>
		</logic:present>

<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="addLinkFlag" />
<ihtml:hidden property="childCont" />
<ihtml:hidden property="popupCloseFlag" />
<ihtml:hidden property="newChildCont" />

  <div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.defaults.mailchange.lbl.pagetitle" />
			</span>
			<div class="ic-filter-panel">
			<div class="ic-row ic-border">
			<div class="ic-row">
					<div class="ic-input ic-split-30 ic-mandatory">
					<label><common:message key="mailtracking.defaults.mailarrival.lbl.flightno" /></label>
					<ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrierCode"  
				   flightCodeProperty="fltNumber" carriercodevalue="<%=form.getFltCarrierCode()%>" flightcodevalue="<%=form.getFltNumber()%>" 
				   carrierCodeMaxlength="3" flightCodeMaxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_FLIGHTNUMBER"/>
					</div>
					<div class="ic-input ic-split-70 ic-mandatory multi-input">
					<label><common:message key="mailtracking.defaults.mailarrival.lbl.flightdate" /></label>
					<ibusiness:calendar property="arrDate" id="flightDate"
				   type="image" value="<%=form.getArrDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_DATE" />
					</div>
			</div>
			<div class="ic-row">
				<div id="FLIGHTDETAILS" class="ic-input ic-split-60">
					<logic:present name="flightValidationVOSession" property="flightRoute">
					<common:displayFlightStatus flightStatusDetails="flightValidationVOSession" />
					</logic:present>
					<logic:notPresent name="flightValidationVOSession" property="flightRoute">
					&nbsp;
					</logic:notPresent>
				</div>
				<div class="ic-input ic-split-40">
					<div class="ic-button-container">
					<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_LIST" accesskey="L" tabindex="3">
					<common:message key="mailtracking.defaults.mailarrival.btn.list" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CLEAR" accesskey="C" tabindex="4">
					<common:message key="mailtracking.defaults.mailarrival.btn.clear" />
					</ihtml:nbutton>
					</div>
				</div>
			</div>
			</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
					<h4><common:message key="mailtracking.defaults.mailarrival.lbl.containers" /></h4>
					<div class="ic-button-container">
						<a href="#" id="addLink" value="add" name="add" class="iCargoLink">
						<common:message key="mailtracking.defaults.mailarrival.lbl.addcontainer" /></a>
					</div>
			</div>
			<div class="ic-row">
			 <div  id="div1" class="tableContainer" style="height:55px">
			               <table class="fixed-header-table">
			                 <thead>
			                   <tr>
			                     <td width="5%" align="center" class="iCargoTableHeaderLabel"></td>
			                     <td width="35%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailarrival.lbl.uld"/></td>
			                     <td width="15%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.mailarrival.lbl.pou"/></td>
			                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailarrival.lbl.destination"/></td>
			                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailarrival.lbl.noofbags"/></td>
			                     <td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailarrival.lbl.weight"/></td>
			                   </tr>
			                </thead>
							<tbody>
			                  <logic:present name="containerVOsSession">
			                  <bean:define id="containerVOs" name="containerVOsSession" toScope="page"/>
			               	      <logic:iterate id="containerVO" name="containerVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.ContainerVO">
							<common:rowColorTag index="rowid">
							<tr>
							  <td><div class="ic-center">
							  <input type="checkbox" name="selectMail" value="<%= rowid.toString() %>" onclick="singleSelectCb(this.form,'<%= rowid.toString() %>','selectMail');">
							  </div></td>
							  <td>
						  		<logic:present name="containerVO" property="paBuiltFlag">
									<logic:equal name="containerVO" property="paBuiltFlag" value="Y">
										<bean:write name="containerVO" property="containerNumber"/>
										<common:message key="mailtracking.defaults.mailchange.lbl.shipperBuild" />
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
							  <td><bean:write name="containerVO" property="bags"/></td>
							  <td><common:write name="containerVO" property="weight" unitFormatting="true"/></td><!-- modified by A-7371-->
							</tr>
							</common:rowColorTag>
						   </logic:iterate>
			                    </logic:present>
			                  </tbody>
			               </table>
			             </div>
						 <div class="ic-row">
							<label><common:message key="mailtracking.defaults.mailarrival.lbl.scandatetime" /></label>
							<div style="width:35%"><ibusiness:calendar property="flightScanDate" id="flightScanDate" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE" value="<%=form.getFlightScanDate()%>" />
							<ibusiness:releasetimer property="flightScanTime" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="flightScanTime"  type="asTimeComponent" value="<%=form.getFlightScanTime()%>" /></div>
						 </div>
			</div>
		</div>
				<div class="ic-foot-container">
			<div class="ic-button-container">
			        <ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_OK" accesskey="O" tabindex="7">
			<common:message key="mailtracking.defaults.arrivemail.btn.ok" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CANCEL" accesskey="S" tabindex="8">
			<common:message key="mailtracking.defaults.arrivemail.btn.cancel" />
			</ihtml:nbutton>
			</div>
		</div>

 </div>
</ihtml:form>
</div>
		
	</body>
</html:html>