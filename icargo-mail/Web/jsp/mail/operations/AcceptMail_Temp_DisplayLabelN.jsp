<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  ArriveMail_Temp_DisplayLabelN.jsp
* Date          	 :  13-JUL-2017

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
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
	<business:sessionBean id="warehouseSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="warehouse" />
	<logic:present name="warehouseSession">
		<bean:define id="warehouseSession" name="warehouseSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeMailClass" />
<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCompanyCode" />
<%}%>
<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailacceptance"
		  method="get"
		  attribute="weightRoundingVO" />
<business:sessionBean id="KEY_VOLUMEROUNDINGVO"
  moduleName="mail.operations"
  screenID="mailtracking.defaults.mailacceptance"
  method="get"
  attribute="volumeRoundingVO" />



<% 
  String index = request.getAttribute("index").toString();
  String isAjax = request.getAttribute("isAjax").toString();
  pageContext.setAttribute ("index",index);
  com.ibsplc.icargo.business.mail.operations.vo.MailbagVO mailDetailsVO =
   (com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)request.getAttribute("mailDetailsVO");
%>	
<%boolean toDisable = false;%>

			 <logic:notEqual name="mailDetailsVO" property="operationalFlag" value="I">
				 <% toDisable = true;%>
			 </logic:notEqual>
					 <td class="iCargoTableDataTd"><!--Modified by a-7871 for ICRD-218762-->
						<% String mailId= ""; %>
						<logic:present name="mailDetailsVO" property="mailbagId">
							<bean:write name="mailDetailsVO" property="mailbagId"/>
							<bean:define id="mailbagId" name="mailDetailsVO" property="mailbagId" toScope="page"/>
							<% mailId = String.valueOf(mailbagId); %>
						</logic:present>
						<ihtml:hidden property="mailbagId" value="<%=mailId%>"/>
					</td>			 
<td class="iCargoTableDataTd">
	   						<% String originOE = ""; %>
					<logic:present name="mailDetailsVO" property="ooe">
	   							<bean:write name="mailDetailsVO" property="ooe"/>
					<bean:define id="ooe" name="mailDetailsVO" property="ooe" toScope="page"/>
	   							<% originOE = String.valueOf(ooe); %>
					</logic:present>
	   						<ihtml:hidden property="mailOOE" value="<%=originOE%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String destnOE = ""; %>
					<logic:present name="mailDetailsVO" property="doe">
	   							<bean:write name="mailDetailsVO" property="doe"/>
					<bean:define id="doe" name="mailDetailsVO" property="doe" toScope="page"/>
	   							<% destnOE = String.valueOf(doe); %>
					</logic:present>
	   						<ihtml:hidden property="mailDOE" value="<%=destnOE%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String mailCatCode = ""; %>
					<logic:present name="mailDetailsVO" property="mailCategoryCode">
	   							<bean:write name="mailDetailsVO" property="mailCategoryCode"/>
	   							<bean:define id="mailCategoryCode" name="mailDetailsVO" property="mailCategoryCode" toScope="page"/>
	   							<% mailCatCode = String.valueOf(mailCategoryCode); %>
					</logic:present>
	   						<ihtml:hidden property="mailCat" value="<%=mailCatCode%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String mailSubclss = ""; %>
					<logic:present name="mailDetailsVO" property="mailSubclass">
	   							<bean:write name="mailDetailsVO" property="mailSubclass"/>
					<bean:define id="mailSubclass" name="mailDetailsVO" property="mailSubclass" toScope="page"/>
	   							<% mailSubclss = String.valueOf(mailSubclass); %>
					</logic:present>
	   						<ihtml:hidden property="mailSC" value="<%=mailSubclss%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String yr = ""; %>
					<logic:present name="mailDetailsVO" property="year">
	   							<bean:write name="mailDetailsVO" property="year"/>
	   							<bean:define id="yer" name="mailDetailsVO" property="year" toScope="page"/>
	   							<% yr = String.valueOf(yer); %>
					</logic:present>
	   						<ihtml:hidden property="mailYr" value="<%=yr%>"/>
					</td>
					<td class="iCargoTableDataTd">
	   						<% String dsn = ""; %>
					<logic:present name="mailDetailsVO" property="despatchSerialNumber">
	   							<bean:write name="mailDetailsVO" property="despatchSerialNumber"/>
					<bean:define id="despatchSerialNumber" name="mailDetailsVO" property="despatchSerialNumber" toScope="page"/>
	   							<% dsn = String.valueOf(despatchSerialNumber); %>
					</logic:present>
	   						<ihtml:hidden property="mailDSN" value="<%=dsn%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String rsn = ""; %>
					<logic:present name="mailDetailsVO" property="receptacleSerialNumber">
	   							<bean:write name="mailDetailsVO" property="receptacleSerialNumber"/>
	   							<bean:define id="receptacleSerialNumber" name="mailDetailsVO" property="receptacleSerialNumber" toScope="page"/>
	   							<% rsn = String.valueOf(receptacleSerialNumber); %>
					</logic:present>
	   						<ihtml:hidden property="mailRSN" value="<%=rsn%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String hni = ""; %>
					<logic:present name="mailDetailsVO" property="highestNumberedReceptacle">
	   							<bean:write name="mailDetailsVO" property="highestNumberedReceptacle"/>
	   							<bean:define id="highestNumberedReceptacle" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
	   							<% hni = String.valueOf(highestNumberedReceptacle); %>
					</logic:present>
	   						<ihtml:hidden property="mailHNI" value="<%=hni%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String ri = ""; %>
					<logic:present name="mailDetailsVO" property="registeredOrInsuredIndicator">
	   							<bean:write name="mailDetailsVO" property="registeredOrInsuredIndicator"/>
	   							<bean:define id="registeredOrInsuredIndicator" name="mailDetailsVO" property="registeredOrInsuredIndicator" toScope="page"/>
	   							<% ri = String.valueOf(registeredOrInsuredIndicator); %>
					</logic:present>
	   						<ihtml:hidden property="mailRI" value="<%=ri%>"/>
			   </td>
				<td class="iCargoTableDataTd">
				 <logic:present name="mailDetailsVO" property="strWeight">
																	<bean:define id="revGrossWeightID" name="mailDetailsVO" property="weight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
																	 
																	 
																 <% request.setAttribute("mailWt",revGrossWeightID); %>
																		
																       <ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight"
																	   unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>"
																	   dataName="mailWt"
																	   indexId="index"
																	   styleId="mailWt"
																	   readonly="true" unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitListValue="<%=(String)revGrossWeightID.getDisplayUnit()%>"  unitTypePassed="MWT"/>
																	       
													</logic:present><!--modified by A-8353 for ICRD-274933-->
				
				
	   					<!--	<% String srtWgt = ""; %>
					<logic:present name="mailDetailsVO" property="strWeight">
						<bean:define id="revGrossWeightID" name="mailDetailsVO" property="strWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
							<% request.setAttribute("mailWt",revGrossWeightID); %>
							<ibusiness:unitdef id="mailWt" unitTxtName="mailWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitReq = "false" dataName="mailWt"
							title="Revised Gross Weight"
							style="width:35px"
							indexId="index" styleId="mailWt"  unitTypePassed="MWT"/>
	   							<% srtWgt = String.valueOf(revGrossWeightID.getDisplayValue()); %>-->
					</logic:present>
					</td>
					<td class="iCargoTableDataTd">
	   						<% String volum = ""; %>
	   						<logic:present name="mailDetailsVO" property="volume">
							<bean:define id="sampleStdVolVo" name="mailDetailsVO" property="volume" type="com.ibsplc.icargo.framework.util.unit.Measure" />
	   									<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
									<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  unitReq = "false" dataName="sampleStdVol"
	   										unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										style="width:35px"
										indexId="index" styleId="stdVolume" readonly="<%=toDisable%>" unitTypePassed="VOL" />
	   							<% volum = String.valueOf(sampleStdVolVo.getDisplayValue()); %>
	   							</logic:present>
	   						<ihtml:hidden property="mailVolume" value="<%=volum%>"/>
	   					</td>
	   					<td class="iCargoTableDataTd">
	   						<% String scnDat = ""; %>
	   						<logic:present name="mailDetailsVO" property="strWeight">
					<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
	   							<%scnDat=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
	   							<%= scnDat %>
					</logic:present>
	   						<ihtml:hidden property="mailScanDate" value="<%=scnDat%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String scnTim = ""; %>
				   <logic:present name="mailDetailsVO" property="scannedDate">
				      <bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
	   							<%scnTim=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),"HH:mm");%>
	   							<%= scnTim %>
				   </logic:present>
	   						<ihtml:hidden property="mailScanTime" value="<%=scnTim%>"/>
				</td>
				<td class="iCargoTableDataTd">
	   						<% String trnsfCarrier = ""; %>
				   <logic:present name="mailDetailsVO" property="transferFromCarrier">
	   							<bean:write name="mailDetailsVO" property="transferFromCarrier"/>
	   							<bean:define id="transferFromCarrier" name="mailDetailsVO" property="transferFromCarrier" toScope="page"/>
	   							<% trnsfCarrier = String.valueOf(transferFromCarrier); %>
				   </logic:present>
	   						<ihtml:hidden property="mailCarrier" value="<%=trnsfCarrier%>"/>
				</td>