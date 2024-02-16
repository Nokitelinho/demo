<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  AcceptMailDetails_DisplayLabelY.jsp
* Date          	 :  31-MAY-2017

*************************************************************************/
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<% 
  com.ibsplc.icargo.business.mail.operations.vo.MailbagVO mailDetailsVO =
   (com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)request.getAttribute("mailDetailsVO");
%>

					<td class="iCargoTableDataTd">
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
					</td>
					<td class="iCargoTableDataTd">
						<% String volum = ""; %>
						<logic:present name="mailDetailsVO" property="volume">
							<common:write name="mailDetailsVO" property="volume" unitFormatting="true"/><!--modified by A-7371-->
							<bean:define id="vol" name="mailDetailsVO" property="volume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure" />
							<% volum = String.valueOf(vol.getDisplayValue()); %>
							<!--<logic:present name="KEY_VOLUMEROUNDINGVO">
									<bean:define id="sampleStdVolVo" name="KEY_VOLUMEROUNDINGVO" />
									<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
									<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label=""  unitReq = "false" dataName="sampleStdVol"
										unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										unitValue="<%=vol.toString()%>" style="background :'<%=color%>'"
										indexId="index" styleId="stdVolume" />
							</logic:present>-->
						</logic:present>
						<ihtml:hidden property="mailVolume" value="<%=volum%>"/>
					</td>
					<td class="iCargoTableDataTd">
						<% String scnDat = ""; %>
						<logic:present name="mailDetailsVO" property="scannedDate">
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