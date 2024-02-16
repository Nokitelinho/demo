<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  AcceptMail.jsp
* Date          	 :  20-June-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>
 

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
	
<title><common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.lbl.title" /></title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/mail/operations/AcceptMail_Script.jsp"/>
	<common:include type="css" src="/css/progressbar.css" />
	<%@ include file="/jsp/includes/progressbar.jsp" %>
	<common:include type="script" src="/js/progressbar.js"/>

</head>
<body id="bodyStyle">
<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   toScope="page" scope="request"/>
<business:sessionBean id="ContainerDetailsVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVOs" />
	<logic:present name="ContainerDetailsVOsSession">
		<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVO" />
	<logic:present name="ContainerDetailsVOSession">
		<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="warehouseSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="warehouse" />
	<logic:present name="warehouseSession">
		<bean:define id="warehouseSession" name="warehouseSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeContainerTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeContainerType" />
<business:sessionBean id="pousSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="pous" />

  <div id="divmain" class="iCargoPopUpContent ic-masterbg" >
  <ihtml:form action="/mailtracking.defaults.mailacceptance.addmailacceptance.do" styleClass="ic-main-form">
  <jsp:include page="/jsp/includes/tab_support.jsp" />
<ihtml:hidden property="popupCloseFlag" />
<ihtml:hidden property="assignToFlight" />
<ihtml:hidden property="suggestValue" />
<ihtml:hidden property="preassignFlag" />
<ihtml:hidden property="disableFlag" />
<ihtml:hidden property="deleteAgreeFlag" />
<ihtml:hidden property="warningOveride" />
<ihtml:hidden property="overrideUMSFlag" />
<input type="hidden" name="prevPou"/>
<ihtml:hidden property="hiddenScanDate" />
<ihtml:hidden property="hiddenScanTime" />
<ihtml:hidden property="density" />
<ihtml:hidden property="consignmentDocNum" />
<ihtml:hidden property="embargoFlag" />
<ihtml:hidden property="containerType" />
<ihtml:hidden property="modify" />
<ihtml:hidden property="inValidId" />
<ihtml:hidden property="addRowEnableFlag" /> <!-- added by A-7371 as part of ICRD-271301-->
<ihtml:hidden property="canDiscardLATValidation" />
<ihtml:hidden property="canDiscardCoterminus" /> <!-- added by A-7371 as part of ICRD-273840-->
<ihtml:hidden property="canDiscardUldValidation" /> <!-- added by A-8149 for ICRD-276070-->
<ihtml:hidden property="warningStatus" />




<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
 <%boolean toDisableTopRow = true;%>
 <logic:present name="ContainerDetailsVOSession">
  <logic:equal name="ContainerDetailsVOSession" property="containerOperationFlag" value="I">
      <% toDisableTopRow = false;%>
  </logic:equal>
   <logic:equal name="ContainerDetailsVOSession" property="containerOperationFlag" value="N">
       <% toDisableTopRow = false;%>
  </logic:equal>
 </logic:present>
 <div class="ic-content-main">
 <div class="ic-head-container">
 <span class="ic-page-title ic-display-none"> <common:message key="mailtracking.defaults.acceptmail.lbl.pagetitle" />
					</span>
					<div class="ic-filter-panel">
					<div class="ic-round border">
					<div class="ic-row ">
									<fieldset class ="ic-field-set">
						 <legend >
             <common:message key="mailtracking.defaults.acceptmail.lbl.contdtls" />
        </legend>
		<div class="ic-input ic-split-35 borrowUld marginT15">
		<ibusiness:uld id="containerNo" uldProperty="containerNo" barrowFlag="true" barrowFlagProperty="barrowCheck"
		style="text-transform:uppercase;"  suggestCollection="ContainerDetailsVOsSession"  suggestAttribute="containerNumber" 
		isSuggestible="true" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONTAINERNUM" maxlength="20" disabled="<%=toDisableTopRow%>"/>
		</div>
			<div class="ic-input ic-mandatory ic-split-20">
								<label><common:message key="mailtracking.defaults.acceptmail.lbl.pou" /></label>
								
								 <% String pouVal = ""; %>
	    <logic:present name="ContainerDetailsVOSession" property="pou">
		<bean:define id="pou" name="ContainerDetailsVOSession" property="pou" toScope="page"/>
		<% pouVal = (String) pou; %>
	    </logic:present>
	    <ihtml:select property="pou" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_POU" value="<%=pouVal%>" style="width:60px" disabled="<%=toDisableTopRow%>">
	      <logic:notPresent name="pousSession">
	     	 <ihtml:option value="">&nbsp</ihtml:option>
	      </logic:notPresent>
	      <logic:present name="pousSession">
		   <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
			<bean:define id="pousSess" name="pousSession" toScope="page" />
			<logic:iterate id="pouVO" name="pousSess" >
			  <bean:define id="pou" name="pouVO" toScope="page" />
			  <html:option value="<%=(String)pou %>"><%=(String)pou %></html:option>
			</logic:iterate>
	      </logic:present>
        </ihtml:select>
								</div>
								<div class="ic-input ic-mandatory ic-split-20">
								<label><common:message key="mailtracking.defaults.acceptmail.lbl.destn" /></label>
								
								<logic:notPresent name="ContainerDetailsVOSession" property="destination">
		     <ihtml:text property="destn" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESTINATION" style="width:45px" maxlength="4"/>
	       </logic:notPresent>
	       <logic:present name="ContainerDetailsVOSession" property="destination">
	       <bean:define id="destn" name="ContainerDetailsVOSession" property="destination" toScope="page" />
		     <ihtml:text property="destn" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESTINATION" value="<%=(String)destn%>" style="width:45px"  maxlength="4"/>
	       </logic:present>
		   <div class= "lovImg">
	       <img id="destnImage" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destn.value,'Airport','0','destn','',0)">
								</div>
								</div>
											<div class="ic-input ic-split-15 ic_inline_chcekbox marginT20">
								      <logic:present name="ContainerDetailsVOSession">
			<logic:equal name="ContainerDetailsVOSession" property="containerType" value="B">
				<input type="checkbox" name="paBuilt" value="N" disabled>
			</logic:equal>
		       <logic:notEqual name="ContainerDetailsVOSession" property="containerType" value="B">
			       <logic:present name="ContainerDetailsVOSession" property="operationFlag">
				<logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
					       <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
						     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
							  <input type="checkbox" name="paBuilt" value="Y" checked  >
						     </logic:equal>
						     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
								  <input type="checkbox" name="paBuilt" value="N" >
						     </logic:equal>
					       </logic:present>
					       <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
							<input type="checkbox" name="paBuilt" value="N" >
					       </logic:notPresent>
			       </logic:notEqual>
			       <logic:equal name="ContainerDetailsVOSession" property="operationFlag" value="I">
				 <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
					     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
						  <input type="checkbox" name="paBuilt" value="Y" checked >
					     </logic:equal>
					     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
						  <input type="checkbox" name="paBuilt" value="N" disabled>
					     </logic:equal>
					</logic:present>
					<logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
						<input type="checkbox" name="paBuilt" value="N">
					</logic:notPresent>
			       </logic:equal>
			     </logic:present>
			     <logic:notPresent name="ContainerDetailsVOSession" property="operationFlag">
			       <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
					<logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
						  <input type="checkbox" name="paBuilt" value="Y" checked >
					</logic:equal>
					<logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
						  <input type="checkbox" name="paBuilt" value="N" disabled>
					</logic:equal>
				   </logic:present>
				   <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
						<input type="checkbox" name="paBuilt" value="N">
				   </logic:notPresent>
			     </logic:notPresent>
		     </logic:notEqual>
	 </logic:present>
	 <label>  <common:message key="mailtracking.defaults.acceptmail.lbl.pabuilt" />
								</label>
								</div>
								<div class="ic-input ic-split-10">
								<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_ACCEPTMAIL_LIST" >
	       	  <common:message key="mailtracking.defaults.acceptmail.btn.list" />
      	    </ihtml:nbutton>
								</div>
								</div>
									<div class="ic-row ">
		<div class="ic-input ic-split-35">
		<label> <common:message key="mailtracking.defaults.acceptmail.lbl.contjnyid" />
		</label>
		<logic:notPresent name="ContainerDetailsVOSession" property="containerJnyId">
						<ihtml:text property="containerJnyId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONTJNYID" value=""
								maxlength="35" style="width:290px"/>
					 </logic:notPresent>
					<logic:present name="ContainerDetailsVOSession" property="containerJnyId">
						 <bean:define id="containerJny" name="ContainerDetailsVOSession" property="containerJnyId" toScope="page" />
						<ihtml:text property="containerJnyId"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONTJNYID" value="<%=(String)containerJny%>"
								maxlength="35" disabled="<%=toDisableTopRow%>" style="width:290px"/>
					 </logic:present>
		</div>
		<div class="ic-input ic-split-30">
		<label> <common:message key="mailtracking.defaults.acceptmail.lbl.pa" />
		</label>
		<logic:notPresent name="ContainerDetailsVOSession" property="paCode">
						<ihtml:text property="paCode" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_PA" value=""
								maxlength="5" />
								<div class= "lovImg">
								<img name="paLov" id="paLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								 </div>
					
					 </logic:notPresent>
					<logic:present name="ContainerDetailsVOSession" property="paCode">
						 <bean:define id="pavalue" name="ContainerDetailsVOSession" property="paCode" toScope="page" />
						<ihtml:text property="paCode"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_PA" value="<%=(String)pavalue%>"
								maxlength="5" disabled="<%=toDisableTopRow%>" />
								<div class= "lovImg">
							 	<img name="paLov" id="paLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								</div>
					 </logic:present>
		</div>
		</div>
		</fieldset>			
		</div>
		<div class="ic-row">
		    <div class="ic-row">
			<div class="ic-input ic-split-15">
			  <label><common:message key="mailtracking.defaults.acceptmail.lbl.warehouse" /></label>
			   <%
		String warehouseValue = "";
	    %>
	    <logic:present name="ContainerDetailsVOSession" property="wareHouse">
		<bean:define id="wareHse" name="ContainerDetailsVOSession" property="wareHouse" toScope="page"/>
		<%
			warehouseValue = (String) wareHse;
		%>
	    </logic:present>
	    <ihtml:select property="warehouse" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_WAREHOUSE" value="<%=warehouseValue%>" >
		<bean:define id="warehouseSess" name="warehouseSession" toScope="page" />
		<logic:iterate id="warehouseVO" name="warehouseSess" >
		  <bean:define id="fieldValue" name="warehouseVO" property="warehouseCode" toScope="page" />
		     <html:option value="<%=(String)fieldValue %>"><%=(String)fieldValue %></html:option>
		</logic:iterate>
            </ihtml:select>
  	  </div>
	        <div class="ic-input ic-split-15">
			 <label><common:message key="mailtracking.defaults.acceptmail.lbl.location" /></label>
			  
  	   <logic:notPresent name="ContainerDetailsVOSession" property="location">
                   <ihtml:text property="location" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_LOCATION" maxlength="10"/>
           </logic:notPresent>
           <logic:present name="ContainerDetailsVOSession" property="location">
             <bean:define id="location" name="ContainerDetailsVOSession" property="location" toScope="page" />
                  <ihtml:text property="location" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_LOCATION" value="<%=(String)location%>" maxlength="10"/>
           </logic:present>
  	</div>
	<div class="ic-input ic-split-43">
			  <label><common:message key="mailtracking.defaults.acceptmail.lbl.onwardflights" /></label>
			        <logic:notPresent name="ContainerDetailsVOSession" property="route">
		     <ihtml:text property="onwardFlights" styleClass="iCargoTextFieldExtraLong" value="" />
	       </logic:notPresent>
	       <logic:present name="ContainerDetailsVOSession" property="route">
	       <bean:define id="route" name="ContainerDetailsVOSession" property="route" toScope="page" />
		     <ihtml:text property="onwardFlights" styleClass="iCargoTextFieldExtraLong" value="<%=(String)route%>" readonly="true" />
	       </logic:present>
          </div>
		    <div class="ic-input ic-split-10">
			  <label><common:message key="mailtracking.defaults.acceptmail.lbl.carrier" /></label>
			  
	       <logic:notPresent name="ContainerDetailsVOSession" property="transferFromCarrier">
		     <ihtml:text property="carrier" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CARRIER" value="" style="width:45px" maxlength="3"/>
	       </logic:notPresent>
	       <logic:present name="ContainerDetailsVOSession" property="transferFromCarrier">
	       <bean:define id="fromCarrier" name="ContainerDetailsVOSession" property="transferFromCarrier" toScope="page" />
		     <ihtml:text property="carrier" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CARRIER" value="<%=(String)fromCarrier%>" style="width:45px" maxlength="3"/>
	       </logic:present>
		   <div class= "lovImg">
	       <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrier.value,'Airline','0','carrier','',0)">
			</div>
		  </div>
            <div class="ic-input ic-split-30">
		 <label><common:message key="mailtracking.defaults.acceptmail.lbl.bellycardit" /></label>
		<logic:notPresent name="ContainerDetailsVOSession" property="bellyCartId">
				<ihtml:text property="bellyCarditId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT" value=""  style="width:220px" maxlength="20"/>
			</logic:notPresent>
		    <logic:present name="ContainerDetailsVOSession" property="bellyCartId">
		    <bean:define id="cartId" name="ContainerDetailsVOSession" property="bellyCartId" toScope="page" />
			 <ihtml:text property="bellyCarditId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CARRIER" value="<%=(String)cartId%>" style="width:220px" maxlength="20"/>
		    </logic:present>  
		  </div>
            <div class="ic-button-container padd5">
		  	
				<ihtml:nbutton property="btnCarIdList" componentID="BTN_MAILTRACKING_DEFAULTS_BELLYCARTID_LIST" >
						<common:message key="mailtracking.defaults.acceptmail.btn.list" />
				</ihtml:nbutton>
		</div>
	</div>

		</div>
	
					</div>
					</div>
					</div>
					<div class="ic-main-container">

							<div class="ic-row ">
	<fieldset class="ic-field-set"><legend ><common:message key="mailtracking.defaults.acceptmail.lbl.maildtls" /></legend>								
   	<div class="ic-row ">
	
		<div class="ic-button-container">
		
		    
			
			  <a href="#" id="addLink" value="add" name="add" class="iCargoLink"><common:message key="mailtracking.defaults.acceptmail.lnk.add" /></a>
			  <a href="#" id="modifyLink" value="modify" name="modify" class="iCargoLink"><common:message key="mailtracking.defaults.acceptmail.lnk.modify" /></a>
			  |
			  <a href="#" id="deleteLink" value="delete" name="delete" class="iCargoLink"><common:message key="mailtracking.defaults.acceptmail.lnk.delete" /></a>
		    
		   
		 
		</div>
		</div>
		<div id="container1">
	
			<ul class="tabs">
			<button type="button" id="tab2" onClick="selectTab('tab2');return showPane(event,'pane2', this);" accesskey="m" class="tab"><common:message key="mailtracking.defaults.acceptmail.lbl.mailtag" /></button>
			  <button type="button" id="tab1" onClick="selectTab('tab1');return showPane(event,'pane1', this);" accesskey="d" class="tab"><common:message key="mailtracking.defaults.acceptmail.lbl.despatch" /></button>
			</ul>
			<div class="tab-panes">				
				<jsp:include page="AcceptDespatch.jsp"/>
				<jsp:include page="AcceptMailDetails.jsp"/>
		 	 </div>
		</div>
	</fieldset>
			<div class="ic-row">
			<div class="ic-col-70 card_grey">
			 <label>Remarks</label>
				<logic:notPresent name="ContainerDetailsVOSession" property="remarks">
					<ihtml:textarea property="remarks" cols="80" rows="3" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_REMARKS" style="resize:none" value="" ></ihtml:textarea>
				</logic:notPresent>
				<logic:present name="ContainerDetailsVOSession" property="remarks">
					<bean:define id="remarks" name="ContainerDetailsVOSession" property="remarks" toScope="page" />
					<ihtml:textarea property="remarks" cols="80" rows="3" value="<%=(String)remarks%>" style="resize:none" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_REMARKS" ></ihtml:textarea>
				</logic:present>
		</div>
			<div class="ic-col-20">
				<div class="ic-button-container marginT20">
					<ihtml:nbutton property="btnNewContainer" componentID="BTN_MAILTRACKING_DEFAULTS_ACCEPTMAIL_NEWCONTAINER" >
						<common:message key="mailtracking.defaults.acceptmail.btn.newcontainer" />
					</ihtml:nbutton>
			</div>
		</div>
		</div>
		</div>
					</div>
					<div class="ic-foot-container">
					<div class="ic-button-container">
					
	<%-- <ihtml:nbutton property="btnCarditEnquiry" componentID="BTN_MAILTRACKING_DEFAULTS_POPUP_ACCEPTMAIL_LISTCARDITS" >
       	    <common:message key="mailtracking.defaults.acceptmail.btn.listcardits" />
      </ihtml:nbutton> --%>

      <ihtml:nbutton property="btnScanTime" componentID="BTN_MAILTRACKING_DEFAULTS_POPUP_ACCEPTMAIL_CHANGESCANTIME" >
       	    <common:message key="mailtracking.defaults.acceptmail.btn.changescantime" />
      </ihtml:nbutton>

      <ihtml:nbutton property="btnLookup" componentID="BT_MAILTRACKING_DEFAULTS_POPUP_ACCEPTMAIL_LOOKUP" >
            <common:message key="mailtracking.defaults.popup.acceptmail.btn.lookup" />
      </ihtml:nbutton>

      <ihtml:nbutton property="btnCaptureDamage" componentID="BTN_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAPTUREDAMAGE" >
		<common:message key="mailtracking.defaults.acceptmail.btn.captureDamage" />
      </ihtml:nbutton>
         <ihtml:nbutton property="btOk" componentID="BTN_MAILTRACKING_DEFAULTS_CHANGESCANTIME_OK" >
	  			<common:message key="mailtracking.defaults.damagedetails.btn.ok" />
      </ihtml:nbutton>
      <ihtml:nbutton property="btnCancel" componentID="BTN_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CANCEL" >
      	  		<common:message key="mailtracking.defaults.acceptmail.btn.cancel" />
      </ihtml:nbutton>
					</div>
					</div>
 </div>
 </div>
  </ihtml:form>
  </div>
  
	</body>
</html:html>