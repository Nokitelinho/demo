<%--
* Project	 		: iCargo
* Module Code & Name		: mra.flown
* File Name			: ViewFlownMail.jsp
* Date				: 12/02/2007
* Author(s)			: Santha Kumar.P.M
 --%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm" %>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>




			
	
<html>
<head>

	
	<title>
		<common:message bundle="flownmailresources" key="mra.flown.viewflownmail.title.viewFlownMail" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include	type="script" src="/js/mail/mra/flown/ViewFlownMail_Script.jsp" />
	<common:include type="script" src="/js/tabbedpane.js"/>
	<common:include type="script" src="/js/utils.js"/>
	<common:include type="script" src="/js/tabs.js"/>

</head>


<body>

	

<bean:define id="form"
		 name="ViewFlownMailForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm"
		 toScope="page" />



<business:sessionBean id="KEY_FLOWNMAILSEGMENT"
	     moduleName="mailtracking.mra.flown"
	     screenID="mra.flown.viewflownmail"
	     method="get"
	     attribute="flownMailSegmentVO"/>

<business:sessionBean id="KEY_SEGMENTDETAILS"
	     moduleName="mailtracking.mra.flown"
	     screenID="mra.flown.viewflownmail"
	     method="get"
	     attribute="segmentDetails"/>
<business:sessionBean id="KEY_ONETIMES"
		 moduleName="mailtracking.mra.flown"
		 screenID="mra.flown.viewflownmail"
		 method="get"
	     attribute="oneTimeVOs"/>


  <!--CONTENT STARTS-->
  <div class="iCargoContent ic-masterbg" id="contentdiv">
<ihtml:form action="/mra.flown.screenloadviewflownmail.do">

<ihtml:hidden property="listSegmentsFlag"/>
<ihtml:hidden property="listFlag" />
<ihtml:hidden property="oneSegmentFlag"/>
<ihtml:hidden property="processFlag" />
<ihtml:hidden name="form" property="duplicateFlightFlag"/>
<ihtml:hidden name="form" property="fromScreen"/>
<ihtml:hidden name="form" property="accEntryFlag"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

<jsp:include page="/jsp/includes/tab_support.jsp" />

<div class="ic-content-main" >	
		  <div class="ic-head-container">
					<span class="ic-page-title ic-display-none">
						<common:message key="mra.flown.viewflownmail.pagetitle.viewFlownMail" />
					</span>
			<div class="ic-filter-panel" >
				<div class="ic-row">
					<div class="ic-input ic-split-25 ic-mandatory">
					<label><common:message key="mra.flown.viewflownmail.lbl.flightNo" /></label>
							<%String carCode="";%>
							<%String fltNum="";%>
							<ibusiness:flightnumber carrierCodeProperty="carrierCode"
							  id="flightNumber"
							  flightCodeProperty="flightNumber"
							  carriercodevalue="<%=(String)form.getCarrierCode()%>"
							  flightcodevalue="<%=(String)form.getFlightNumber()%>"
							  componentID="CMP_MRA_Flown_ViewFlownMail_FLIGHTNO"
							  carrierCodeStyleClass="iCargoTextFieldVerySmall"
							  flightCodeStyleClass="iCargoTextFieldSmall"
							  />
					</div>
					<div class="ic-input ic-split-25 ic-mandatory">
					<label><common:message key="mra.flown.viewFlownMail.lbl.flightDate" /></label>
						<ibusiness:calendar property="flightDate" id="flightDate" componentID="CMP_MRA_Flown_ViewFlownMail_FLIGHTDATE" type="image" />
					</div>
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnList" accesskey = "I" componentID="CMP_MRA_Flown_ViewFlownMail_BT_LISTSEGMENTS" >
							<common:message key="mra.flown.viewflownmail.btn.listSegments"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" accesskey = "C" componentID="CMP_MRA_Flown_ViewFlownMail_BT_CLEAR" >
							<common:message key="mra.flown.viewflownmail.btn.clear"/>
						</ihtml:nbutton>
					</div>
				</div>
				<hr>
				<div class="ic-row">
					<div class="ic-input ic-split-25 ic-mandatory">
						<label><common:message key="mra.flown.viewFlownMail.lbl.segment" /></label>
						<ihtml:select property="segment" componentID="CMP_MRA_Flown_ViewFlownMail_SEGMENT">
							<logic:present name="KEY_SEGMENTDETAILS">
								<logic:iterate id="segment" name="KEY_SEGMENTDETAILS">
									<logic:present name="segment" property="segmentOrigin">
										<logic:present name="segment" property="segmentDestination">
											<bean:define id="segmentSerialNumber" name="segment" property="segmentSerialNumber"/>
												<html:option value="<%=String.valueOf(segmentSerialNumber) %>">
													<bean:write name="segment" property="segmentOrigin"/>
														-
													<bean:write name="segment" property="segmentDestination"/>
												</html:option>
										</logic:present>
									</logic:present>
								</logic:iterate>
							</logic:present>
							<!--<logic:notEqual name="ViewFlownMailForm"  property="listSegmentsFlag" value="Y">
								<html:option value="">
								</html:option>
							</logic:notEqual>--> <!--modified by a-7871 for ICRD-240098-->
						</ihtml:select>
					</div>
					<div class="ic-input marginT10 ic-split-25">
						<common:message key="mra.flown.viewflownmail.lbl.flightSegmentStatus" /> <!-- Modified by A-8236 for ICRD-252679-->
						<%String localstatus="";%>
							<logic:present name="KEY_FLOWNMAILSEGMENT" property="segmentStatus">
							 <bean:define id="segmentStatus" name="KEY_FLOWNMAILSEGMENT" property="segmentStatus"/>
							<ihtml:hidden property="segmentStatus" value="<%=String.valueOf(segmentStatus)%>"/>
								 <logic:present name="KEY_ONETIMES">
									<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.mra.flownSegmentStatus">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<%localstatus=segmentStatus.toString();%>
													<logic:equal name="parameterValue" property="fieldValue" value="<%=localstatus%>">
													<common:display name="parameterValue" property="fieldDescription" />
													</logic:equal>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
							</logic:present>
					</div>
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnListMails" componentID="CMP_MRA_Flown_ViewFlownMail_BT_LIST" accesskey="L" >
							<common:message key="mra.flown.viewflownmail.btn.list"/>
						</ihtml:nbutton>
					</div>
				</div></hr>
			</div>
		 </div>
		 <div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-row">
					<h3><common:message key="mra.flown.viewflownmail.lbl.MailsForFlightSegment" /></h3>
				</div>
				<div class="tab-container" id="container1">
	<ul class="tabs">
		<li>
			<button id="Despatch" type="button" accesskey="g" class="tab" onclick="return showPane(event,'pane1', this)">
			<common:message key="mra.flown.viewflownmail.tab.despatch"/>
			</button>
		</li>
		<li>
			<button type="button" onclick="return showPane(event,'pane2', this)" accesskey="m" class="tab" id="MailBag">
			<common:message key="mra.flown.viewflownmail.tab.mailBag"/>
			</button>
		</li>
	</ul>

	<div class="tab-panes">


	<!---DIV Tab Start--->
	<!-- CONTENTS OF TAB1 STARTS -->


		<div id="pane1" class="content" >
						<div id="div2" class="tableContainer" style="height:680px;">
							<table class="fixed-header-table" id="tab1Table">
								<thead>
								<tr>
								<td rowspan="2" class="iCargoTableHeader" width="1%" ></td>
								<td rowspan="2" class="iCargoTableHeader" width="6%"><common:message   key="mra.flown.viewflownmail.lbl.consignmentDocumentNumber"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.date"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.originOE"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.destinationOE"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.category"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.class"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.despatchSeialNumber"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.year"/></td>
								<td rowspan="2" style="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.actualNumberOfBags"/></td>
								<td rowspan="2" class="iCargoTableHeader" width="3%"><common:message   key="mra.flown.viewflownmail.lbl.weight"/></td>
								</tr>
								</thead>

								<tbody>
									<logic:present name="KEY_FLOWNMAILSEGMENT" property="segmentDSNs">
										<bean:define id="DSN" name="KEY_FLOWNMAILSEGMENT" property="segmentDSNs"/>

										<logic:iterate id="iterator" name="DSN"
										type="com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO" indexId="rowCount">

										<ihtml:hidden property="dsnSegmentStatus"  value="<%=iterator.getDsnSegmentStatus()%>" />
											<tr>

												<td>
													
														<ihtml:checkbox property="rowCount"  value="<%=String.valueOf(rowCount)%>"/>
													
												</td>

												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="consignmentDocumentNumber">
													<bean:write name="iterator" property="consignmentDocumentNumber"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="despatchDate">
													<%=iterator.getDespatchDate().toDisplayDateOnlyFormat()%>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="originOfficeOfExchange">
													<bean:write name="iterator" property="originOfficeOfExchange"/>
													</logic:present>
												</td>
													<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="destinationOfficeOfExchange">
													<bean:write name="iterator" property="destinationOfficeOfExchange"/>
													</logic:present>
												</td>
													<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="mailCategoryCode">
													<bean:write name="iterator" property="mailCategoryCode"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="mailSubclass">
													<bean:write name="iterator" property="mailSubclass"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="dsnNumber">
													<bean:write name="iterator" property="dsnNumber"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="year">
													<bean:write name="iterator" property="year"/>
													</logic:present>
												</td>
													<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="acceptedMailBagCount">
													<bean:write name="iterator" property="acceptedMailBagCount"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="acceptedMailBagWeight">
													<common:write name="iterator" property="acceptedMailBagWeight" unitFormatting="true" />
													</logic:present>
												</td>
											</tr>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>
		</div>	

	<!--contents of Tab1 ends-->

	<!--contents of Tab2 starts-->

		<div id="pane2" class="content">
							<div id="div3" class="tableContainer" style="height:680px;">
								<table class="fixed-header-table" id="tab2Table">
									<thead>
									<tr>
									<td rowspan="2" class="iCargoTableHeader" width="1%" ></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.originOE"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.destinationOE"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.category"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.subClass"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.year"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.despatchSeialNumber"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.receptacleSeialNumber"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.highNumberIndicator"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="5%"><common:message   key="mra.flown.viewflownmail.lbl.registeredIndicator"/></td>
									<td rowspan="2" class="iCargoTableHeader" width="4%"><common:message   key="mra.flown.viewflownmail.lbl.weight"/></td>
									</tr>
									</thead>
									<tbody>
										<logic:present name="KEY_FLOWNMAILSEGMENT" property="segmentMailBags">
											<bean:define id="mailBag" name="KEY_FLOWNMAILSEGMENT" property="segmentMailBags"/>
											<logic:iterate id="iterator" name="mailBag"
											type="com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO" indexId="rowCount">
											<ihtml:hidden property="dsnSegmentStatus"  value="<%=iterator.getMailBagSegmentStatus() %>" />
												<tr>
												<td >
											
														<ihtml:checkbox property="selectedRow"  value="<%=String.valueOf(rowCount)%>"/>
												
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="originOfficeOfExchange">
													<bean:write name="iterator" property="originOfficeOfExchange"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="destinationOfficeOfExchange">
													<bean:write name="iterator" property="destinationOfficeOfExchange"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="mailCategoryCode">
													<bean:write name="iterator" property="mailCategoryCode"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="mailSubclass">
													<bean:write name="iterator" property="mailSubclass"/>
													</logic:present>
												</td class="iCargoTableDataTd">
												<td>
													<logic:present	name="iterator" property="year">
													<bean:write name="iterator" property="year"/>
													</logic:present>
												</td class="iCargoTableDataTd">
												<td>
													<logic:present	name="iterator" property="dsnNumber">
													<bean:write name="iterator" property="dsnNumber"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="receptacleSerialNumber">
													<bean:write name="iterator" property="receptacleSerialNumber"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="highestReceptacleNumber">
													<bean:write name="iterator" property="highestReceptacleNumber"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="registeredIndicator">
													<bean:write name="iterator" property="registeredIndicator"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present	name="iterator" property="mailBagWeight">
													<common:write name="iterator" property="mailBagWeight" unitFormatting="true" />
																										
													<common:write name="iterator" property="displayWgtUnit" />
													</logic:present>
												</td>
												</tr>
											</logic:iterate>
										</logic:present>
									</tbody>
								</table>
							</div>
		</div>
	<!--content of Tab2 ends-->
		</div>
	</div>
</div>
</div>

<div class="ic-foot-container paddR5">
			<div class="ic-row">
				<div class="ic-button-container">
					<ihtml:nbutton property="btnViewAccount" accesskey = "A" componentID="CMP_MRA_Flown_ViewFlownMail_VIEWACCOUNT">
						<common:message key="mailtracking.mra.flown.viewflownmail.btn.viewaccentry"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" accesskey = "O" componentID="CMP_MRA_Flown_ViewFlownMail_CLOSE" >
						<common:message key="mra.flown.viewflownmail.btn.close"/>
					</ihtml:nbutton>
				</div>
			</div>
</div>
</div>
</ihtml:form>
  </div>




<!---CONTENT ENDS--->
<!--Footer Begins-->

 <!--Footer Ends-->	

	</body>
</html>
