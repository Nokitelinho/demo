<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  ArriveMailDetails_WithoutDisplayLabel.jsp
* Date          	 :  13-JUL-2017

*************************************************************************/
 --%>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<bean:define id="MailArrivalForm" name="MailArrivalForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm" toScope="page" scope="request"/>
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
<% 
  com.ibsplc.icargo.business.mail.operations.vo.MailbagVO mailDetailsVO =
   (com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)request.getAttribute("mailDetailsVO");
%>	

			<td class="iCargoTableDataTd" >
				<bean:write name="mailDetailsVO" property="mailbagId"/>
				<bean:define id="mailId" name="mailDetailsVO" property="mailbagId" toScope="page"/>
				<ihtml:hidden property="mailbagId" value="<%=(String)mailId%>"/>
			</td>
				<td class="iCargoTableDataTd" >
				<bean:write name="mailDetailsVO" property="ooe"/>
				<bean:define id="ooe" name="mailDetailsVO" property="ooe" toScope="page"/>
				<ihtml:hidden property="mailOOE" value="<%=(String)ooe%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="doe"/>
				<bean:define id="doe" name="mailDetailsVO" property="doe" toScope="page"/>
				<ihtml:hidden property="mailDOE" value="<%=(String)doe%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="mailCategoryCode"/>
				<bean:define id="mailCategoryCode" name="mailDetailsVO" property="mailCategoryCode" toScope="page"/>
				<ihtml:hidden property="mailCat" value="<%=(String)mailCategoryCode%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="mailSubclass"/>
				<bean:define id="mailSubclass" name="mailDetailsVO" property="mailSubclass" toScope="page"/>
				<ihtml:hidden property="mailSC" value="<%=(String)mailSubclass%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="year"/>
				<bean:define id="year" name="mailDetailsVO" property="year" toScope="page"/>
				<ihtml:hidden property="mailYr" value="<%=String.valueOf(year)%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="despatchSerialNumber"/>
				<bean:define id="despatchSerialNumber" name="mailDetailsVO" property="despatchSerialNumber" toScope="page"/>
				<ihtml:hidden property="mailDSN" value="<%=(String)despatchSerialNumber%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="receptacleSerialNumber"/>
				<bean:define id="receptacleSerialNumber" name="mailDetailsVO" property="receptacleSerialNumber" toScope="page"/>
				<ihtml:hidden property="mailRSN" value="<%=(String)receptacleSerialNumber%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="highestNumberedReceptacle"/>
				<bean:define id="highestNumberedReceptacle" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
				<ihtml:hidden property="mailHNI" value="<%=(String)highestNumberedReceptacle%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<bean:write name="mailDetailsVO" property="registeredOrInsuredIndicator"/>
				<bean:define id="registeredOrInsuredIndicator" name="mailDetailsVO" property="registeredOrInsuredIndicator" toScope="page"/>
				<ihtml:hidden property="mailRI" value="<%=(String)registeredOrInsuredIndicator%>"/>
			</td>
			<td class="iCargoTableDataTd">
				<logic:present name="mailDetailsVO" property="strWeight">
				<bean:define id="revGrossWeightID" name="mailDetailsVO" property="weight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>																	 
				<% request.setAttribute("mailWt",revGrossWeightID); %>
																		
				<ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight"
				unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>"
				dataName="mailWt" indexId="index" styleId="mailWt" readonly="true" unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitListValue="<%=(String)revGrossWeightID.getDisplayUnit()%>"  unitTypePassed="MWT"/>
																	       
				</logic:present>
			</td>
			<td class="iCargoTableDataTd">
				<common:write name="mailDetailsVO" property="volume" unitFormatting="true"/>
				<bean:define id="volume" name="mailDetailsVO" property="volume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
				<ihtml:hidden property="mailVolume" value="<%=String.valueOf(volume.getDisplayValue())%>"/>
			</td>