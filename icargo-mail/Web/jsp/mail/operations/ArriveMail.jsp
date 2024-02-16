<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  ArriveMail.jsp
* Date          	 :  08-August-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="/jsp/includes/tlds.jsp" %>


<html:html locale="true">

<head>
<%@ include file="/jsp/includes/customcss.jsp" %>
	 <title><common:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.lbl.title" /></title>
	 <meta name="decorator" content="popup_panel">
     <common:include type="script" src="/js/mail/operations/ArriveMail_Script.jsp" />
</head>

<body >

		<bean:define id="MailArrivalForm" name="MailArrivalForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm" toScope="page" scope="request"/>
		<business:sessionBean id="ContainerDetailsVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="containerDetailsVOs" />
<logic:present name="ContainerDetailsVOsSession">
	<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
</logic:present>
		<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="containerDetailsVO" />
<logic:present name="ContainerDetailsVOSession">
	<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="polSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="pols" />
<business:sessionBean id="oneTimeContainerTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeContainerType" />
<%boolean turkishFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="TURKISH_SPECIFIC">
					 <% turkishFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeCompanyCode" />
<%}%>
<div id="pageDiv" class="iCargoPopUpContent ic-masterbg">
<ihtml:form action="/mailtracking.defaults.mailarrival.screenloadarrivemailpopup.do"  styleClass="ic-main-form">
<jsp:include page="/jsp/includes/tab_support.jsp" />
<ihtml:hidden property="popupCloseFlag" />
<ihtml:hidden property="suggestContainerValue" />
<ihtml:hidden property="disableFlag" />
<ihtml:hidden property="hiddenScanDate" />
<ihtml:hidden property="hiddenScanTime" />
<ihtml:hidden property="hiddenMailTag" />
<ihtml:hidden property="showAssignContainer" />
<ihtml:hidden property="density" />
<ihtml:hidden property="containerType" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
				<ihtml:hidden property="isContainerValidFlag" />
<input type="hidden" name="operationFlag"/>
<ihtml:hidden property="unsavedDataFlag" />
<ihtml:hidden property="deleteFlag" />
<ihtml:hidden property="inValidId" />
<ihtml:hidden property="embargoFlag" /> <!--added by a-7871-->
<div class="ic-content-main">
<div class="ic-head-container">
<span class="ic-page-title ic-display-none"> <common:message
							key="mailtracking.defaults.arrivemail.lbl.pagetitle" />
					</span>
					<div class="ic-filter-panel">
					<div class="ic-round border">
						<jsp:include page="ArriveMail_Filters.jsp" />
							</div>

					</div>
</div>
<div class="ic-main-container">
<div class="ic-col-100">
<div class="ic-round border">
						<div>
						<fieldset class ="ic-field-set">
						 <legend >
	     	     <common:message key="mailtracking.defaults.arrivemail.lbl.maildtls" />
	     </legend>
		 <div class="ic-button-container">
		 <div >
		  <a href="#" id="addLink" value="add" name="add" class="iCargoLink"> <common:message key="mailtracking.defaults.arrivemail.lnk.add" /></a>
												<a href="#" id="deleteLink" value="delete" name="delete" class="iCargoLink"><common:message key="mailtracking.defaults.arrivemail.lnk.delete" /></a>
	        </div>
		 </div>
		 	<div id="container1" >
			<ul class="tabs">
	  	<button type="button" id="tab2" onClick="return showPane(event,'pane2', this);" accesskey="m" class="tab"><common:message key="mailtracking.defaults.arrivemail.lbl.mailtag" /></button>
             <button type="button" id="tab1" onClick="return showPane(event,'pane1', this);" accesskey="d" class="tab"><common:message key="mailtracking.defaults.arrivemail.lbl.despatch" /></button>
          </ul>
		  <div class="tab-panes">
		     <% int tabNum = 0;%>
	  <jsp:include page="ArriveDespatch.jsp"/>
	   <div id="pane2"  style="width:100%">
	   <div class="tableContainer" id="div2" style="height:190px;width:144%">
	    <table  class="fixed-header-table" style="width:144%">
	    <thead>
                    <tr >
                  	<td  width="3%"class="iCargoTableHeaderLabel" ><input type="checkbox" name="masterMailTag" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMailTag);"/></td>
					<td  width="26%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.mailbagid" /><span class="iCargoMandatoryFieldIcon">*</span></td><!--modified by a-7871--> <!-- A-8164 for 257594-->
                  	<td  width="9%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.origin" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  	<td width="9%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.destination" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
		  	<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.cat" />  </td>
		  	<td width="8%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.sc" /> <span class="iCargoMandatoryFieldIcon">*</span> </td>
		  	<td width="3%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.yr" /> <span class="iCargoMandatoryFieldIcon">*</span> </td>
		  	<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.dsn" />  <span class="iCargoMandatoryFieldIcon">*</span></td> <!--modified by a-7871 for ICRD-244927-->
		  	<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.rsn" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
		  	<td width="4%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.arrivemail.lbl.hni" /></td>
		  	<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.ri" />  </td>
		  	<td width="19%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.wt" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
			<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.volume" /></td>
		  	<td width="17%" colspan="2" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.scandate" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
		  	<td width="4%" class="iCargoTableHeaderLabel" >
		  	   <common:message key="mailtracking.defaults.arrivemail.lbl.recvd" />
		  	   <input type="checkbox" name="recvdMailTag" onclick="updateHeaderCheckBox(this.form,this,this.form.mailReceived);"/>
		  	</td>
		  	<td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.delvd" />
		  	  <input type="checkbox" name="delvdMailTag" onclick="updateHeaderCheckBox(this.form,this,this.form.mailDelivered);"/></td>
			 <td width="12%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.mailremarks" /></td> 
		  	<td width="5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.damaged" />  </td>
			<% if(turkishFlag){%>
			<td  width="11%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.companycode" />  </td>
			<%}%>
                        <td  width="13%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.sealno" /> </td>
			<td width="12%"class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.arrivemail.lbl.arrivalsealno" />  </td>
                    </tr>
					</thead>
								 <tbody id="mailTableBody">
								  <% int mail = 0;%>
 	        <logic:present name="ContainerDetailsVOSession" property="mailDetails">
			  <bean:define id="mailDetailsColln" name="ContainerDetailsVOSession" property="mailDetails" scope="page" toScope="page"/>
	      	    <%boolean toDisableScanFields = false;%>
		    <logic:present name="mailDetailsColln" property="transferFlag">
		       <logic:equal name="mailDetailsColln" property="transferFlag" value="Y">
		         <% toDisableScanFields = true;%>
		       </logic:equal>
		    </logic:present>
			 <% int row = ((Collection)mailDetailsColln).size();%>
		   <% Collection<String> selectedrows = new ArrayList<String>(); %>
		    <logic:present name="MailArrivalForm" property="selectMailTag" >
																<% String[] selectedRows = MailArrivalForm.getSelectMailTag();
			for (int j = 0; j < selectedRows.length; j++) {
				selectedrows.add(selectedRows[j]);
																	}%>
	 	   </logic:present>
		    <logic:iterate id="mailDetailsVO" name="mailDetailsColln" indexId="index">
			  <bean:define id="transferFlag" value=""/>
	         <logic:present name="mailDetailsVO" property="transferFlag">
	         	<bean:define id="transferFlag" name="mailDetailsVO" property="transferFlag" type="String"/>
	         </logic:present>
			  <input type="hidden" name="transferFlag" value="<%=transferFlag%>"/>
	         <% tabNum = tabNum + row * mail; %>
	         <% mail++;%>
	         <%boolean toDisable = false;%>
			 <logic:notEqual name="mailDetailsVO" property="operationalFlag" value="I">
				 <% toDisable = true;%>
			 </logic:notEqual>
		 <logic:present name="mailDetailsVO" property="operationalFlag">
			<bean:define id="operationFlag" name="mailDetailsVO" property="operationalFlag" toScope="request" />
			<ihtml:hidden property="mailOpFlag" value="<%=((String)operationFlag)%>" />
		 </logic:present>
		 <logic:notPresent name="mailDetailsVO" property="operationalFlag">
			<ihtml:hidden property="mailOpFlag" value="N" />
		 </logic:notPresent>
		 <tr>
		                 <td class="iCargoTableDataTd ic-center" >
																		<% String mailBagKey=(String.valueOf(mail));
																		if(selectedrows.contains(mailBagKey)){ %>
			<input type="checkbox" name="selectMailTag" value="<%=mailBagKey%>" checked="true">
																		<% } else{ %>
		    <input type="checkbox" name="selectMailTag" value="<%=mailBagKey%>"/>
																		<% } %>
	        </td>
			<logic:present name="mailDetailsVO" property="displayLabel">
			<logic:equal name="mailDetailsVO" property="displayLabel" value="Y">
						<%				
						request.setAttribute ("mailDetailsVO",mailDetailsVO);
						%>
			<jsp:include page="ArriveMailDetails_WithoutDisplayLabel.jsp" />
			</logic:equal>
			<logic:notEqual name="mailDetailsVO" property="displayLabel" value="Y">
			<td class="iCargoTableDataTd" >
			   <logic:notPresent name="mailDetailsVO" property="mailbagId">
				<ihtml:text property="mailbagId" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILBAGID" value="" maxlength="29" readonly="<%=toDisable%>" style="width:240px" indexId="index"/> <!-- modified. A-8164 for 257594-->
			   </logic:notPresent>
			   <logic:present name="mailDetailsVO" property="mailbagId">
				<bean:define id="mailId" name="mailDetailsVO" property="mailbagId" toScope="page"/>
				<ihtml:text property="mailbagId" value="<%=(String)mailId%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILBAGID" maxlength="29" readonly="<%=toDisable%>" style="width:240px" indexId="index"/> <!-- modified. A-8164 for 257594-->
			   </logic:present>
			</td>			
				<td class="iCargoTableDataTd" >
			   <logic:notPresent name="mailDetailsVO" property="ooe">
				<ihtml:text property="mailOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILOOE" value="" maxlength="6" readonly="<%=toDisable%>"  style="width:45px" indexId="index" />
			   </logic:notPresent>
			   <logic:present name="mailDetailsVO" property="ooe">
				<bean:define id="ooe" name="mailDetailsVO" property="ooe" toScope="page"/>
				<ihtml:text property="mailOOE" value="<%=(String)ooe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILOOE" maxlength="6" readonly="<%=toDisable%>"  style="width:45px" indexId="index" />
			   </logic:present>
			   <%if(toDisable){%>
				<img name="mailOOELov" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
			   <%}else{%>
				<img name="mailOOELov" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			   <%}%>
			</td>
			<td class="iCargoTableDataTd">
			   <logic:notPresent name="mailDetailsVO" property="doe">
				<ihtml:text property="mailDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDOE" value="" maxlength="6" readonly="<%=toDisable%>" style="width:45px" indexId="index"/>
			   </logic:notPresent>
			   <logic:present name="mailDetailsVO" property="doe">
				<bean:define id="doe" name="mailDetailsVO" property="doe" toScope="page"/>
				<ihtml:text property="mailDOE" value="<%=(String)doe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDOE" maxlength="6" readonly="<%=toDisable%>" style="width:45px" indexId="index"/>
			   </logic:present>
			   <%if(toDisable){%>
				 <img name="mailDOELov" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
			   <%}else{%>
				 <img name="mailDOELov" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			   <%}%>
			</td>
			<td class="iCargoTableDataTd">
			  <% String catValue = ""; %>
			  <logic:present name="mailDetailsVO" property="mailCategoryCode">
				<bean:define id="mailCtgyCode" name="mailDetailsVO" property="mailCategoryCode" toScope="page"/>
				<% catValue = (String) mailCtgyCode; %>
			  </logic:present>
			  <ihtml:select property="mailCat" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CAT" value="<%=catValue%>" disabled="<%=toDisable%>"  style="width:35px" indexId="index">
				<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
				<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
					<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
					<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
				</logic:iterate>
			  </ihtml:select>
			</td>
			<td class="iCargoTableDataTd">
			  <logic:notPresent name="mailDetailsVO" property="mailSubclass">
				<ihtml:text property="mailSC" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILSC" value="" maxlength="2" readonly="<%=toDisable%>"  style="width:18px" indexId="index" />
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="mailSubclass">
				<bean:define id="mailSubclass" name="mailDetailsVO" property="mailSubclass" toScope="page"/>
				<ihtml:text property="mailSC" value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILSC" maxlength="2" readonly="<%=toDisable%>"  style="width:18px" indexId="index" />
			  </logic:present>
			  <%if(toDisable){%>
				 <img name="mailSCLov" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
			  <%}else{%>
				 <img name="mailSCLov" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			  <%}%>
			</td>
			<td class="iCargoTableDataTd">
			  <logic:notPresent name="mailDetailsVO" property="year">
				<ihtml:text property="mailYr" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILYR" value="" maxlength="1" readonly="<%=toDisable%>" style="width:13px" indexId="index" />
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="year">
				<bean:define id="year" name="mailDetailsVO" property="year" toScope="page"/>
				<ihtml:text property="mailYr" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILYR" maxlength="1" readonly="<%=toDisable%>" style="width:13px" indexId="index"/>
			  </logic:present>
			</td>
			<td class="iCargoTableDataTd">
			  <logic:notPresent name="mailDetailsVO" property="despatchSerialNumber">
				<ihtml:text property="mailDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDSN" value="" maxlength="4" readonly="<%=toDisable%>" indexId="index"/>
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="despatchSerialNumber">
				<bean:define id="despatchSerialNumber" name="mailDetailsVO" property="despatchSerialNumber" toScope="page"/>
				<ihtml:text property="mailDSN" value="<%=(String)despatchSerialNumber%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDSN" maxlength="4" readonly="<%=toDisable%>" indexId="index"/>
			  </logic:present>
			</td>
			<td class="iCargoTableDataTd">
			   <logic:notPresent name="mailDetailsVO" property="receptacleSerialNumber">
				<ihtml:text property="mailRSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILRSN" value="" maxlength="3" readonly="<%=toDisable%>" style="width:23px" indexId="index"/>
			   </logic:notPresent>
			   <logic:present name="mailDetailsVO" property="receptacleSerialNumber">
				<bean:define id="rsn" name="mailDetailsVO" property="receptacleSerialNumber" toScope="page"/>
				<ihtml:text property="mailRSN" value="<%=(String)rsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILRSN" maxlength="3" readonly="<%=toDisable%>" style="width:23px" indexId="index"/>
			   </logic:present>
			</td>
			<td class="iCargoTableDataTd">
			   <% String hniValue = ""; %>
			   <logic:present name="mailDetailsVO" property="highestNumberedReceptacle">
				<bean:define id="hni" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
				<% hniValue = (String) hni;%>
			   </logic:present>
			   <ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_HNI" value="<%=hniValue%>" disabled="<%=toDisable%>"  style="width:35px" indexId="index">
				<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
				<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
					 <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
					 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
			</td>
			<td class="iCargoTableDataTd">
			   <% String riValue = ""; %>
			   <logic:present name="mailDetailsVO" property="registeredOrInsuredIndicator">
				  <bean:define id="ri" name="mailDetailsVO" property="registeredOrInsuredIndicator" toScope="page"/>
				  <% riValue = (String) ri; %>
			   </logic:present>
			   <ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RI" value="<%=riValue%>" disabled="<%=toDisable%>"  style="width:35px" indexId="index">
				<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
				<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
				      <bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
				      <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
			</td>
			<td class="iCargoTableDataTd">
			
			  <logic:present name="mailDetailsVO" property="strWeight">
                                                                                                                                                                        
                                                                            <bean:define id="revGrossWeightID" name="mailDetailsVO" 
																			property="weight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
                                                                                                                                                                                                                                                     <% request.setAttribute("mailWt",revGrossWeightID); %>
                                                                                                                                                                        
                                                                            <ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight"
                                                                             unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>"
                                                                             dataName="mailWt" indexId="index"  styleId="mailWt" unitListName="weightUnit"  
                                                                             componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILWT"  unitListValue="<%=(String)revGrossWeightID.getDisplayUnit()%>"  unitTypePassed="MWT"/>
                                                                                                                                     
			  </logic:present>
			
			</td>
			<td class="iCargoTableDataTd">
			  <logic:present name="mailDetailsVO" property="volume">
				<bean:define id="volume" name="mailDetailsVO" property="volume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
				<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILVOLUME"  unitReq = "false" dataName="mailVolume" unitValueMaxLength="4"
				style="width:30px"
				indexId="index" styleId="mailVolume"  unitTypePassed="VOL"/>
				<ihtml:hidden property="mailVolume" value="<%=String.valueOf(volume.getDisplayValue())%>"/>
			  </logic:present>
			</td>
			</logic:notEqual>
			</logic:present>
			<td class="iCargoTableDataTd" colspan="2">
		  <logic:notPresent name="mailDetailsVO" property="latestScannedDate">
			<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE"   value="" readonly="<%=toDisableScanFields%>"/>
		  </logic:notPresent>
		  <logic:present name="mailDetailsVO" property="latestScannedDate">
			<logic:present name="mailDetailsVO" property="arrivedFlag">
				<logic:equal name="mailDetailsVO" property="arrivedFlag" value="Y" >
			<bean:define id="latestScannedDate" name="mailDetailsVO" property="latestScannedDate" toScope="page"/>
			<%String scanDate=TimeConvertor.toStringFormat(((LocalDate)latestScannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
		    	<ibusiness:calendar property="mailScanDate" indexId="index" id="mailScanDate" value="<%=(String)scanDate%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE" readonly="<%=toDisableScanFields%>"/>
				</logic:equal>
		  </logic:present>
		 </logic:present>
		 <logic:present name="mailDetailsVO" property="latestScannedDate">
			<logic:present name="mailDetailsVO" property="arrivedFlag">
				<logic:equal name="mailDetailsVO" property="arrivedFlag" value="N" >
			<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE"   value="" readonly="<%=toDisableScanFields%>"/>
				</logic:equal>
			</logic:present>
		 </logic:present>
		   <logic:notPresent name="mailDetailsVO" property="latestScannedDate">
			<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="" readonly="<%=toDisableScanFields%>" style="width:37px"/>
		   </logic:notPresent>
		   <logic:present name="mailDetailsVO" property="latestScannedDate">
			<logic:present name="mailDetailsVO" property="arrivedFlag">
				<logic:equal name="mailDetailsVO" property="arrivedFlag" value="Y" >
		      <bean:define id="latestScannedDate" name="mailDetailsVO" property="latestScannedDate" toScope="page"/>
		      <%String scanTime=TimeConvertor.toStringFormat(((LocalDate)latestScannedDate).toCalendar(),"HH:mm");%>
		      <ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=(String)scanTime%>" readonly="<%=toDisableScanFields%>" style="width:37px"/>
				</logic:equal>
		   </logic:present>
			</logic:present>
			<logic:present name="mailDetailsVO" property="latestScannedDate">
				<logic:present name="mailDetailsVO" property="arrivedFlag">
					<logic:equal name="mailDetailsVO" property="arrivedFlag" value="N" >
						<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="" readonly="<%=toDisableScanFields%>" style="width:37px"/>
					</logic:equal>
				</logic:present>
			</logic:present>
		</td>
		<td class="iCargoTableDataTd ic-center">
		         <logic:notPresent name="mailDetailsVO" property="arrivedFlag">
				<input type="checkbox" name="mailReceived" indexId="index"/>
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="arrivedFlag">
				<logic:equal name="mailDetailsVO" property="arrivedFlag" value="Y" >
				     <logic:notPresent name="mailDetailsVO" property="operationalFlag">
					   <input type="checkbox" name="mailReceived" value="true" checked disabled indexId="index"/>
				     </logic:notPresent>
				     <logic:present name="mailDetailsVO" property="operationalFlag">
				        <logic:equal name="mailDetailsVO" property="operationalFlag" value="I" >
					     <input type="checkbox" name="mailReceived" value="true" checked disabled indexId="index"/>
					</logic:equal>
					<logic:notEqual name="mailDetailsVO" property="operationalFlag" value="I" >
					     <input type="checkbox" name="mailReceived" value="true" checked indexId="index"/>
					</logic:notEqual>
				     </logic:present>
				</logic:equal>
				<logic:equal name="mailDetailsVO" property="arrivedFlag" value="N">
					 <input type="checkbox" name="mailReceived" value="false" indexId="index"/>
				</logic:equal>
			  </logic:present>
		</td>
		<td class="iCargoTableDataTd ic-center">
		   	  <logic:notPresent name="mailDetailsVO" property="deliveredFlag">
				<input type="checkbox" name="mailDelivered" indexId="index"/>
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="deliveredFlag">
				<logic:equal name="mailDetailsVO" property="deliveredFlag" value="Y" >
				     <logic:notPresent name="mailDetailsVO" property="operationalFlag">
					   <input type="checkbox" name="mailDelivered" value="true" checked disabled indexId="index"/>
				     </logic:notPresent>
				     <logic:present name="mailDetailsVO" property="operationalFlag">
					   <input type="checkbox" name="mailDelivered" value="true" checked indexId="index"/>
				     </logic:present>
				</logic:equal>
				<logic:equal name="mailDetailsVO" property="deliveredFlag" value="N">
					 <input type="checkbox" name="mailDelivered" value="false" indexId="index"/>
				</logic:equal>
			  </logic:present>
		</td>
		<!--Added by A-7540 as a part of ICRD-197419-->
		<td class="iCargoTableDataTd ic-center">
		   <logic:notPresent name="mailDetailsVO" property="mailRemarks">
		     <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" />
	       </logic:notPresent>
	       <logic:present name="mailDetailsVO" property="mailRemarks">
	       <bean:define id="mailRemarks" name="mailDetailsVO" property="mailRemarks" toScope="page" />
		     <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="<%=(String)mailRemarks%>" />
	       </logic:present>
		   </td>
	
		<td class="iCargoTableDataTd ic-center">
			  <logic:notPresent name="mailDetailsVO" property="damageFlag">
				<input type="checkbox" name="mailDamaged" indexId="index" />
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="damageFlag">
				<logic:equal name="mailDetailsVO" property="damageFlag" value="Y" >
				     <logic:notPresent name="mailDetailsVO" property="operationalFlag">
					   <input type="checkbox" name="mailDamaged" value="true" checked disabled indexId="index" />
				     </logic:notPresent>
				     <logic:present name="mailDetailsVO" property="operationalFlag">
					   <input type="checkbox" name="mailDamaged" value="true" checked indexId="index" />
				     </logic:present>
				</logic:equal>
				<logic:equal name="mailDetailsVO" property="damageFlag" value="N">
					 <input type="checkbox" name="mailDamaged" value="false" indexId="index" />
				</logic:equal>
			  </logic:present>
		</td>
		<% if(turkishFlag){%>
		   <td>

		    <% String cmpcod = ""; %>
			   <logic:present name="mailDetailsVO" property="mailCompanyCode">
			   <bean:define id="hni" name="mailDetailsVO"  property="mailCompanyCode"  toScope="page"/>

				<% cmpcod = (String) hni;%>
			   </logic:present>
			   <ihtml:select property="mailCompanyCode" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CMPCOD" value="<%=cmpcod%>"   style="width:75px">
			   <logic:notPresent name="form"  property="mailCompanyCode">
		               		<html:option value=""><common:message key="combo.select"/></html:option>
		               </logic:notPresent>
				<bean:define id="oneTimeCmpSess" name="oneTimeMailCompanyCodeSession" toScope="page" />
				<logic:iterate id="oneTimeCMPcodeVO" name="oneTimeCmpSess" >
					 <bean:define id="fieldValue" name="oneTimeCMPcodeVO" property="fieldValue" toScope="page" />
					 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCMPcodeVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>







          </td>
		  <%}%>
		<td class="iCargoTableDataTd">
		 <logic:notPresent name="mailDetailsVO" property="sealNumber">
			<ihtml:text property="sealNo" style="width:120px;" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SEALNUM" value=""  maxlength="15"  />
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="sealNumber">
				<bean:define id="seal" name="mailDetailsVO" property="sealNumber" toScope="page" />
				<ihtml:text property="sealNo" style="width:120px;"  value="<%=(String)seal%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SEALNUM" maxlength="15"  />
			  </logic:present>
		</td>
		<td class="iCargoTableDataTd">
		 <logic:notPresent name="mailDetailsVO" property="arrivalSealNumber">
			<ihtml:text property="arrivalSealNo" style="width:120px;" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_ARRSEALNUM" value=""  maxlength="15"  />
			  </logic:notPresent>
			  <logic:present name="mailDetailsVO" property="arrivalSealNumber">
				<bean:define id="arrivalseal" name="mailDetailsVO" property="arrivalSealNumber" toScope="page" />
				<ihtml:text property="arrivalSealNo" style="width:120px;" value="<%=(String)arrivalseal%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_ARRSEALNUM" maxlength="15" />
			  </logic:present>
		</td>
		 </tr>
			</logic:iterate>
			</logic:present>
			  <!-- templateRow -->
					<jsp:include page="ArriveMail_TemplateRow.jsp" />
		<!--template row ends-->
								 </tbody>
		</table>
	   </div>
	   </div>
		  </div>
			</div>
			<div class= "ic-row">
				<div class="ic-input ic-col-75">
				 <td class="iCargoLabelRightAligned">
	      Remarks
	  </td>
					<logic:notPresent name="ContainerDetailsVOSession" property="remarks">
	 	      <ihtml:textarea property="remarks" cols="80" rows="3" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_REMARKS" value="" ></ihtml:textarea>
	 	</logic:notPresent>
	 	<logic:present name="ContainerDetailsVOSession" property="remarks">
	 	      <bean:define id="remarks" name="ContainerDetailsVOSession" property="remarks" toScope="page" />
	 	      <ihtml:textarea property="remarks" cols="80" rows="3" value="<%=(String)remarks%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_REMARKS" ></ihtml:textarea>
	 	</logic:present>
				</div>
			</div>
						</fieldset>
						</div>
						</div>

</div>
</div>
<div class="ic-foot-container">
					<div class="ic-button-container">
					 <ihtml:nbutton property="btnUndoArrival"   componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_UNDOARRIVAL" >
					<common:message key="mailtracking.defaults.mailarrival.tooltip.undoarrival" />
	   </ihtml:nbutton>
		 <logic:present name="ContainerDetailsVOSession">
			 <logic:equal name="ContainerDetailsVOSession" property="operationFlag" value="I">
         		<ihtml:nbutton property="btnMarkIntact" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_INTACT" disabled="true">
               	 <common:message key="mailtracking.defaults.arrivemail.btn.intact" />
         		</ihtml:nbutton>
         	</logic:equal>
			<logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
         		<ihtml:nbutton property="btnMarkIntact" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_INTACT" >
               	 <common:message key="mailtracking.defaults.arrivemail.btn.intact" />
         		</ihtml:nbutton>
         	</logic:notEqual>
         </logic:present>
         <ihtml:nbutton property="btnScanTime" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVAL_POPUP_CHANGESCANTIME" >
	      	<common:message key="mailtracking.defaults.arrivemail.btn.changescantime" />
         </ihtml:nbutton>
								<ihtml:nbutton property="btnCaptureDamage" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVAL_POPUP_CAPTUREDAMAGE" >
									<common:message key="mailtracking.defaults.arrivemail.btn.captureDamage" />
								</ihtml:nbutton>
         <ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_OK" >
		        <common:message key="mailtracking.defaults.arrivemail.btn.ok" />
         </ihtml:nbutton>
         <ihtml:nbutton property="btnCancel" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CANCEL" styleClass="btn-inline btn-secondary"> <!--modified by a-7871-->
                <common:message key="mailtracking.defaults.arrivemail.btn.cancel" />
         </ihtml:nbutton>
					</div>
					</div>
</div>
</ihtml:form>
</div>
	</body>
</html:html>