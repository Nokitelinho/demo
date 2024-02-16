<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  AcceptDespatch.jsp
* Date          	 :  22-NOV-2009

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

<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   toScope="page" scope="request"/>
   				<%boolean turkishFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="TURKISH_SPECIFIC">
					 <% turkishFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
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
<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCompanyCode" />
<%}%>
		  
<div id="pane2" ">
            <div class="tableContainer" id="div2"  style="height:250px; ">
	      <table   class="fixed-header-table"  >
              <thead>
                <tr >
                  <td width="2%" class="iCargoTableHeaderLabel"><div ><input type="checkbox" name="masterMailTag" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMailTag);"/></div></td>
                  <td width="20.5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.mailbagid" /></td>  <!-- A8164 for 257594-->
				  <td width="8%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.acceptmail.lbl.origin" />  </td>
                  <td width="8%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.acceptmail.lbl.destination" />  </td>
                  <td width="4.5%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.acceptmail.lbl.cat" />  </td>
                  <td width="7%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.acceptmail.lbl.sc" /> </td>
                  <td width="2%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.yr" /> </td>
                  <td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.dsn" />  </td>
                  <td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.rsn" />  </td>
                  <td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.hni" />  </td>
                  <td width="4%" class="iCargoTableHeaderLabel"  ><common:message key="mailtracking.defaults.acceptmail.lbl.ri" /> </td>
                  <td width="16%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.wt" />  </td>
				  <!-- <td width="4%" class="iCargoTableHeaderLabel" >Wt unit</td>-->
                  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.acceptmail.lbl.volume" /> </td>
                  <td width="11%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.scandate" /> </td>
                  <td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.scantime" /> </td>
                  <td width="5.5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.mailcarrier" />  </td>
				 <% if(turkishFlag){%>
				  <td width="5%"class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.mailcompanycode" />  </td>
				 <%}%>
		  <td width="5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.sealno" /> </td>
                  <td width="3.5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.damaged" />  </td>
				   <td width="5.5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.mailbagremarks" />  </td>
                  <td width="4%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.bellycardit" />  </td>
              </tr>
              </thead>
              <tbody id="mailTableBody">
              <% int mail = 0;%>

			  <logic:present name="ContainerDetailsVOSession" property="mailDetails">
				<bean:define id="mailDetailsColln" name="ContainerDetailsVOSession" property="mailDetails" scope="page" toScope="page"/>

					<% int row = ((Collection)mailDetailsColln).size();%>
					<% Collection<String> selectedrows = new ArrayList<String>(); %>

				 <logic:present name="MailAcceptanceForm" property="selectMailTag" >

					<%
					String[] selectedRows = MailAcceptanceForm.getSelectMailTag();
					for (int j = 0; j < selectedRows.length; j++) {
						selectedrows.add(selectedRows[j]);
					}
					%>

	 			</logic:present>

	       <logic:iterate id="mailDetailsVO" name="mailDetailsColln" indexId="index">
	       
               

	         
	         <% mail++;%>
			   <logic:notEqual name="mailDetailsVO" property="operationalFlag" value="D"> 

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
                <td  class="iCargoTableDataTd">
                    <% String mailBagKey=(String.valueOf(mail));%>

					<%
						if(selectedrows.contains(mailBagKey)){
					%>

						<input type="checkbox" name="selectMailTag" value="<%=mailBagKey%>" checked="true">
					<%
						}
						else{
					%>
						<input type="checkbox" name="selectMailTag" value="<%=mailBagKey%>"/>

					<%
						}
					%>

		</td>
			<logic:present name="mailDetailsVO" property="displayLabel">
				<%
				request.setAttribute("mailDetailsVO",mailDetailsVO);
				request.setAttribute ("toDisable",toDisable);
				request.setAttribute ("index",index);
				%>
				<% boolean displayLabelNeeded = false;%>
				<logic:equal name="mailDetailsVO" property="displayLabel" value="Y">
					<%displayLabelNeeded = true;%>
				</logic:equal>
				<logic:notEqual name="mailDetailsVO" property="displayLabel" value="Y">
					<%displayLabelNeeded = false;%>
				</logic:notEqual>
				<%if(displayLabelNeeded){%>
					<jsp:include page="AcceptMailDetails_DisplayLabelY.jsp" />
				<%}else{%>

					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="mailbagId">
							<ihtml:text property="mailbagId"   componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILBAGID" value="" maxlength="29" readonly="<%=toDisable%>" style="width:200px" indexId="index"/> <!-- modified. A8164 for ICRD 257594-->
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="mailbagId">
							<bean:define id="mailbagId" name="mailDetailsVO" property="mailbagId" toScope="page"/>
							<ihtml:text property="mailbagId"  value="<%=(String)mailbagId%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILBAGID" maxlength="29"  style="width:200px" indexId="index" /> <!-- modified. A8164 for ICRD 257594-->
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="ooe">
							<ihtml:text property="mailOOE"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" value="" maxlength="6" readonly="<%=toDisable%>" style="width:54px" indexId="index" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="ooe">
							<bean:define id="ooe" name="mailDetailsVO" property="ooe" toScope="page"/>
							<ihtml:text property="mailOOE"  value="<%=(String)ooe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" maxlength="6"  style="width:54px" indexId="index"/>
						</logic:present>
						<%if(toDisable){%>
                            <div class= "lovImgTbl valignT"><img name="mailOOELov" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled></div>
						<%}else{%>
                            <div class= "lovImgTbl valignT"><img name="mailOOELov" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="doe">
							<ihtml:text property="mailDOE"   componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" value="" maxlength="6" readonly="<%=toDisable%>" style="width:54px" indexId="index" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="doe">
							<bean:define id="doe" name="mailDetailsVO" property="doe" toScope="page"/>
							<ihtml:text property="mailDOE"  value="<%=(String)doe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" maxlength="6"  style="width:55px" indexId="index" />
						</logic:present>
						<%if(toDisable){%>
                            <div class= "lovImgTbl valignT"><img name="mailDOELov" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled></div>
						<%}else{%>
                            <div class= "lovImgTbl valignT"><img name="mailDOELov" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<% String catValue = ""; %>
						<logic:present name="mailDetailsVO" property="mailCategoryCode">
							<bean:define id="mailCtgyCode" name="mailDetailsVO" property="mailCategoryCode" toScope="page"/>
							<% catValue = (String) mailCtgyCode; %>
						</logic:present>
						<ihtml:select property="mailCat"  componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAT" value="<%=catValue%>" style="width:35px" indexId="index">
							<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
							<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
								<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
								<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="mailSubclass">
							<ihtml:text property="mailSC"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" value="" maxlength="2" readonly="<%=toDisable%>" style="width:10px"  indexId="index" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="mailSubclass">
							<bean:define id="mailSubclass" name="mailDetailsVO" property="mailSubclass" toScope="page"/>
							<ihtml:text property="mailSC"  value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" maxlength="2" style="width:10px" indexId="index"/>
						</logic:present>
						<%if(toDisable){%>
                            <div class= "lovImgTbl valignT"><img name="mailSCLov" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled></div>
						<%}else{%>
                            <div class= "lovImgTbl valignT"><img name="mailSCLov" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="year">
							<ihtml:text property="mailYr"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" value="" maxlength="1" readonly="<%=toDisable%>" style="width:13px" indexId="index" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="year">
							<bean:define id="year" name="mailDetailsVO" property="year" toScope="page"/>
							<ihtml:text property="mailYr"  value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" maxlength="1" style="width:13px" indexId="index"  />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="despatchSerialNumber">
							<ihtml:text property="mailDSN"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" value="" maxlength="4" readonly="<%=toDisable%>" indexId="index" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="despatchSerialNumber">
							<bean:define id="despatchSerialNumber" name="mailDetailsVO" property="despatchSerialNumber" toScope="page"/>
							<ihtml:text property="mailDSN"  value="<%=(String)despatchSerialNumber%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" maxlength="4"  indexId="index" />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="receptacleSerialNumber">
							<ihtml:text property="mailRSN"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" value="" maxlength="3" readonly="<%=toDisable%>" style="width:23px" indexId="index"  />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="receptacleSerialNumber">
							<bean:define id="rsn" name="mailDetailsVO" property="receptacleSerialNumber" toScope="page"/>
							<ihtml:text property="mailRSN"  value="<%=(String)rsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" maxlength="3"  style="width:23px" indexId="index" />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<% String hniValue = ""; %>
						<logic:present name="mailDetailsVO" property="highestNumberedReceptacle">
							<bean:define id="hni" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
							<% hniValue = (String) hni;%>
						</logic:present>
						<ihtml:select property="mailHNI"  componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_HNI" value="<%=hniValue%>" style="width:35px" indexId="index" >
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
						<ihtml:select property="mailRI"  componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_RI" value="<%=riValue%>" style="width:35px" indexId="index" >
							<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
							<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
								  <bean:define id="fldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
								 <html:option value="<%=(String)fldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
					
					
	<logic:present name="mailDetailsVO" property="strWeight">
																	<bean:define id="revGrossWeightID" name="mailDetailsVO" property="weight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
																	 
																	
																 <% request.setAttribute("mailWt",revGrossWeightID); %>
											
																       <ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight"
																	   unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>"
																	   dataName="mailWt"
																	   unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitListValue="<%=(String)revGrossWeightID.getDisplayUnit()%>"    indexId="index" 
																	   unitTypePassed="MWT"/>
																	       
													</logic:present><!--modified by A-8353 for ICRD-274933-->
						
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="volume">
								<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" label=""  unitReq = "false" dataName="mailVolume"
									unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
									unitValue="0.0" style="width:35px"
									indexId="index" styleId="stdVolume" readonly="true" unitTypePassed="VOL" />
							<%--
							<ihtml:text property="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" value=""   maxlength="6"  />
							--%>
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="volume">
							<bean:define id="sampleStdVolVo" name="mailDetailsVO" property="volume" type="com.ibsplc.icargo.framework.util.unit.Measure" />
									<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
									<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  unitReq = "false" dataName="sampleStdVol"
										unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										style="width:35px"
										indexId="index" styleId="stdVolume"  unitTypePassed="VOL" />
							<%--
							<ihtml:text property="mailVolume" value="<%=volume.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  maxlength="6" />
							--%>
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="scannedDate">
							<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE" readonly="true" value="" style="width:75px" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="scannedDate">
						<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
						<%String scanDate=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
							<ibusiness:calendar property="mailScanDate"  indexId="index" id="mailScanDate" value="<%=(String)scanDate%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE"  style="width:75px"/>
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
					   	<logic:notPresent name="mailDetailsVO" property="scannedDate">
							<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" readonly="true" value="" style="width:40px"/>
					   	</logic:notPresent>
					   	<logic:present name="mailDetailsVO" property="scannedDate">
					    		<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
					      		<%String scanTime=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),"HH:mm");%>
					      		<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent"  value="<%=(String)scanTime%>" style="width:40px"/>
					   	</logic:present>
					</td>
					<td class="iCargoTableDataTd">
					   	<logic:notPresent name="mailDetailsVO" property="transferFromCarrier">
							<ihtml:text property="mailCarrier" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" value="" maxlength="3" readonly="true" style="width:27px"/>
					   	</logic:notPresent>
					   	<logic:present name="mailDetailsVO" property="transferFromCarrier">
							<bean:define id="transferCarrier" name="mailDetailsVO" property="transferFromCarrier" toScope="page"/>
							<ihtml:text property="mailCarrier" indexId="index" value="<%=(String)transferCarrier%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" maxlength="3"  style="width:27px"/>
					   	</logic:present>
                        <div class= "lovImgTbl"><img name="mailCarrierLov" id="mailCarrierLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
					</td>
				<%}%>
			</logic:present>
			<% if(turkishFlag){%>
			 <td>
        
			 <% String cmpcod = ""; %>
			   <logic:present name="mailDetailsVO" property="mailCompanyCode">
			   <bean:define id="hni" name="mailDetailsVO"  property="mailCompanyCode"  toScope="page"/>
				
				<% cmpcod = (String) hni;%>
			   </logic:present>
			   <ihtml:select property="mailCompanyCode" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CMPCOD" value="<%=cmpcod%>"  readonly="true"  style="width:70px">
			   <logic:notPresent name="form"  property="mailCompanyCode">
		               		<html:option value=""><common:message key="combo.select"/></html:option>
		               </logic:notPresent>
				<bean:define id="oneTimeCmpCodeSess" name="oneTimeMailCompanyCodeSession" toScope="page" />
				<logic:iterate id="oneTimeCmpCodeVO" name="oneTimeCmpCodeSess" >
					 <bean:define id="fieldValue" name="oneTimeCmpCodeVO" property="fieldValue" toScope="page" />
					 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCmpCodeVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
          </td>
		<%}%>
					<td class="iCargoTableDataTd">
					 	<logic:notPresent name="mailDetailsVO" property="sealNumber">
							<ihtml:text property="sealNo" style="width:50px;" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNUM" value="" readonly="true"  maxlength="15"  />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="sealNumber">
							<bean:define id="seal" name="mailDetailsVO" property="sealNumber" toScope="page" />
							<ihtml:text property="sealNo" style="width:50px;" value="<%=(String)seal%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNUM" />
						</logic:present> 
					</td>
					<td class="iCargoTableDataTd">
					  	<logic:notPresent name="mailDetailsVO" property="damageFlag">
							<input type="checkbox" name="mailDamaged" indexId="index"  />
					  	</logic:notPresent>
					  	<logic:present name="mailDetailsVO" property="damageFlag">
							<logic:equal name="mailDetailsVO" property="damageFlag" value="Y" >
								<input type="checkbox" name="mailDamaged" value="true" checked indexId="index"  />
							</logic:equal>
							<logic:equal name="mailDetailsVO" property="damageFlag" value="N">
								<input type="checkbox" name="mailDamaged" value="false" indexId="index"/>
							</logic:equal>
					  	</logic:present>
					</td>
					
					<!--Added remarks field as a part of ICRD-197419 by A-7540-->		
					<td class="iCargoTableDataTd">
						  <logic:notPresent name="mailDetailsVO" property="mailRemarks">
		     <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" readonly="true"/>
	       </logic:notPresent>
	       <logic:present name="mailDetailsVO" property="mailRemarks">
	       <bean:define id="mailRemarks" name="mailDetailsVO" property="mailRemarks" toScope="page" />
		     <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="<%=(String)mailRemarks%>"  />
	       </logic:present>
					</td>
					
			<logic:present name="mailDetailsVO" property="displayLabel">
				<logic:equal name="mailDetailsVO" property="displayLabel" value="Y">
					<td class="iCargoTableDataTd">
						<% String bellyCart = ""; %>
						<logic:present name="mailDetailsVO" property="bellyCartId">
							<bean:write name="mailDetailsVO" property="bellyCartId"/>
							<bean:define id="bellyCartId" name="mailDetailsVO" property="bellyCartId" toScope="page"/>
							<% bellyCart = String.valueOf(bellyCartId); %>
						 </logic:present>
						<ihtml:hidden property="mailCartId" value="<%=bellyCart%>" />
					</td>
				</logic:equal>
				<logic:equal name="mailDetailsVO" property="displayLabel" value="N">
					<td  class="iCargoTableDataTd">
					  	<logic:notPresent name="mailDetailsVO" property="bellyCartId">
							<ihtml:text property="mailCartId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT" value=""  readonly="true" maxlength="20" style="width:50px" />
					  	</logic:notPresent>
						<logic:present name="mailDetailsVO" property="bellyCartId">
					  		<bean:define id="carditId" name="mailDetailsVO" property="bellyCartId" toScope="page" />
							<ihtml:text property="mailCartId" value="<%=(String)carditId%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT"  maxlength="20" style="width:50px"/>
					  	</logic:present>
					</td>
				</logic:equal>
			</logic:present>


				  </tr>
				  </logic:notEqual>
				
				 </logic:iterate>
	       		</logic:present>



	       	     <!-- templateRow -->
					<jsp:include page="AcceptMailDetails_Template.jsp"/>
		     <!--template row ends-->

              </tbody>
            </table>
	   </div>
 </div>		  