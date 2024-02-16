<%--
* Project	 		: iCargo
* Module Code & Name: CAP
* File Name			: ListCustomerRegistration.jsp
* Date				: 11-Apr-2006
* Author(s)			: A-2135
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO" %>

<%
response.setDateHeader("Expires",0);
response.setHeader("Pragma","no-cache");

if (request.getProtocol().equals("HTTP/1.1")) {
	response.setHeader("Cache-Control","no-cache");
}
%>


<html:html locale="true">
	<head>
		<title>
			<bean:message bundle="listtempcustomerform" key="cm.defaults.scrntitle.listtemporarycustomerregistration" />
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
		<common:include type="script" src="/js/customermanagement/defaults/ListTemporaryCustomerRegistration_Script.jsp"/>
	</head>
<body id="bodyStyle">
	
<business:sessionBean id="KEY_LISTTEMPCUSTOMERREG"
		   moduleName="customermanagement.defaults"
		   screenID="customermanagement.defaults.listtempcustomerform"
		   method="get"
		   attribute="listTempCustomerDetails"/>

<business:sessionBean
			id="KEY_ONETIMEVALUES"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.listtempcustomerform"
			method="get"
			attribute="oneTimeValues" />

<business:sessionBean id="KEY_LIST"
		   moduleName="customermanagement.defaults"
		   screenID="customermanagement.defaults.listtempcustomerform"
		   method="get"
		   attribute="listCustomerRegistration"/>

<bean:define id="form" name="ListTempCustomerForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm" toScope="page" />
<div class="iCargoContent ic-masterbg" id="mainDiv" style="overflow-y:auto;height:100%;" >
<ihtml:form action="/customermanagement.defaults.screenloadlisttempcustomerregistration.do">
<ihtml:hidden property="displayPageNum" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="fromList" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="mySearchEnabled" /> 
<ihtml:hidden property="detailsFlag" />
<ihtml:hidden property="custCodeForAttach" />
<ihtml:hidden property="flag" />
<ihtml:hidden property="attachFlag" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="listtempcustomerform" key="cm.defaults.pgtitle.listtemporarycustomerregistration" />
	</span>
	<div class="ic-head-container">	
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-input-container ic-label-30">
					<div class="ic-row">
						<h4>
						<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.filterlbl" /></td>
						</h4>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.tempid" />
							</label>
							<logic:present name="KEY_LISTTEMPCUSTOMERREG" property="tempCustCode">
								 <bean:define id="tempCustCode" name="KEY_LISTTEMPCUSTOMERREG" property="tempCustCode"/>
								 <ihtml:text maxlength="12" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_TEMPID" property="listTempId" value="<%=(String)tempCustCode%>" />
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="tempCustCode">
								 <ihtml:text maxlength="12" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_TEMPID" property="listTempId" value="" />
							</logic:notPresent>
							<div class="lovImg">
							<img id="tempidLOV" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" />
						    </div>
						</div>
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.custname" />
							</label>
							<logic:present name="KEY_LISTTEMPCUSTOMERREG" property="tempCustName">
								 <bean:define id="tempCustName" name="KEY_LISTTEMPCUSTOMERREG" property="tempCustName"/>
								 <ihtml:text maxlength="50"  componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CUSTNAME" property="customerName" value="<%=(String)tempCustName%>" />
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="tempCustName">
								 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CUSTNAME" property="customerName" value="" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.station" />
							</label>
							<logic:present name="KEY_LISTTEMPCUSTOMERREG" property="stationCode">
								 <bean:define id="stationCode" name="KEY_LISTTEMPCUSTOMERREG" property="stationCode"/>
								 <ihtml:text  componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_STATION" property="station" value="<%=(String)stationCode%>" />
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="stationCode">
								 <ihtml:text  componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_STATION" property="station" value="" />
							</logic:notPresent>
							<div class="lovImg">
							<img id="stationlov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22"/>
						    </div>
						</div>
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.status" />
							</label>
							<logic:present name="KEY_LISTTEMPCUSTOMERREG" property="activeStatus">
								<bean:define id="activeStatus" name="KEY_LISTTEMPCUSTOMERREG" property="activeStatus"/>
								<ihtml:select property="status" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_ACTIVE" value="<%=(String)activeStatus%>">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									 <logic:present name="KEY_ONETIMEVALUES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="oneTimeValue" property="fieldValue">
												<bean:define id="fieldValue" name="oneTimeValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="oneTimeValue" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="activeStatus">
								<ihtml:select property="status" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_ACTIVE" value="">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									 <logic:present name="KEY_ONETIMEVALUES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="oneTimeValue" property="fieldValue">
												<bean:define id="fieldValue" name="oneTimeValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="oneTimeValue" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</logic:notPresent>
						</div>
						
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.fromdate" />
							</label>
							 <logic:present name="KEY_LISTTEMPCUSTOMERREG" property="fromDate">
								<bean:define id="fromDate" name="KEY_LISTTEMPCUSTOMERREG" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
								<%String toDat="";%>
								<%  toDat=TimeConvertor.toStringFormat(fromDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
								<ibusiness:calendar id="fromDate" type="image" property="validFrom" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_FROMDATE"
								value="<%=(String)toDat%>"	/>									 
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="fromDate">
								<ibusiness:calendar id="fromDate" type="image" property="validFrom" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_FROMDATE"
								value="" />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.todate" />								
							</label>
							 <logic:present name="KEY_LISTTEMPCUSTOMERREG" property="toDate">
								<bean:define id="toDate" name="KEY_LISTTEMPCUSTOMERREG" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
								<%String toDat="";%>
								<%  toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
								<ibusiness:calendar id="toDate" type="image" property="validTo" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_TODATE"
								value="<%=(String)toDat%>"	/>
							 </logic:present>

							 <logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="toDate">
								<ibusiness:calendar id="toDate" type="image" property="validTo" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_TODATE"
								value="" />
							 </logic:notPresent>
						</div>
						<div class="ic-input ic-split-25 ic-label-45">
							<label>
								<common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.lbl.custcode" />								
							</label>
							 <logic:present name="KEY_LISTTEMPCUSTOMERREG" property="customerCode">
								 <bean:define id="customerCode" name="KEY_LISTTEMPCUSTOMERREG" property="customerCode"/>
								 <ihtml:text componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CUSTOMERCODE" property="customerCode" value="<%=(String)customerCode%>" />
							</logic:present>

							<logic:notPresent name="KEY_LISTTEMPCUSTOMERREG" property="customerCode">
								 <ihtml:text componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CUSTOMERCODE" property="customerCode" value="" />
							</logic:notPresent>
                            <div class="lovImg">
							<img id="customercodelov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" />
						    </div>
						</div>
						<div class="ic-input ic-split-25">
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" accesskey="L" componentID="CMP_CM_DEFAULTS_CM_LIST_BTN">
								 <common:message bundle="listtempcustomerform" key="cm.defaults.btn.btlist" />
								</ihtml:nbutton>

								<ihtml:nbutton accesskey="C" property="btClear" componentID="CMP_CM_DEFAULTS_CM_CLEAR_BTN">
								 <common:message bundle="listtempcustomerform" key="cm.defaults.btn.btclear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row">
			<h4><common:message bundle="listtempcustomerform" key="cm.defaults.listtemporarycustomerregistration.tableheader" /></h4>
		</div>
		<div class="ic-row">
			<div class="ic-col-60">
				<logic:present name="KEY_LIST">
					<common:paginationTag
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="KEY_LIST"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
				</logic:present>
			</div>
			<div class="ic-col-40 paddR5">
			<div class="ic-button-container">
				<logic:present name="KEY_LIST">
					<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="KEY_LIST"
						display="pages"
						lastPageNum="<%=form.getLastPageNum()%>"
						exportToExcel="true"
						exportTableId="listtempcustomerreg"
						exportAction="customermanagement.defaults.listtempcustreg.do"/>
				</logic:present>
			</div>
		</div>
		</div>
		<div class="ic-row">
			<div class="tableContainer" id="div1" style="height:550px;">
			<table id="listtempcustomerreg" class="fixed-header-table" >
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td style="width:5%;" class="iCargoTableHeaderLabel ic-center">
							<input type="checkbox" name="masterRowId" value="checkbox" />
						</td>
						<td style="width:9%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column1" /><span></span>
						</td>
						<td style="width:20%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column2" /><span></span>
						</td>
						<td style="width:10%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column7" /><span></span>
						</td>
						<td style="width:10%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column3" /><span></span>
						</td>
						<td style="width:15%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column4" /><span></span>
						</td>
						<td style="width:10%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column5" /><span></span>
						</td>
						<td style="width:20%;" class="iCargoTableHeaderLabel">
							<common:message bundle="listtempcustomerform" key="cm.defaults.tableheader.column6" /><span></span>
						</td>
					</tr>
				</thead>
				<tbody>
					<logic:present name="KEY_LIST">
						<bean:define id="KEY_LIST" name="KEY_LIST" />
						<logic:iterate id="iterator" name="KEY_LIST" indexId="repindex" type="com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO">
							<input type="hidden" name="custcode" value="<%=iterator.getCustomerCode()%>"/>
							<input type="hidden" name="statusvalues" value="<%=iterator.getActiveStatus()%>"/>

							<tr>
								<td class="iCargoTableDataTd ic-center" >
									<html:checkbox property="rowId"  value="<%=String.valueOf(repindex)%>"/>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="tempCustCode">
										<bean:write name="iterator" property="tempCustCode"/>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="tempCustName">
										<bean:write name="iterator" property="tempCustName"/>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="customerCode">
										<bean:write name="iterator" property="customerCode"/>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="station">
										<bean:write name="iterator" property="station"/>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="createdDate">
										<%String Date = TimeConvertor.toStringFormat(iterator.getCreatedDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
										<%=(String)Date %>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="activeStatus">
										<bean:define id="status" name="iterator" property="activeStatus"/>
										<logic:present name="KEY_ONETIMEVALUES">
											<logic:iterate id="OneTimeVo" name="KEY_ONETIMEVALUES">
											   <logic:present name="OneTimeVo" property="fieldDescription">
													<logic:present name="OneTimeVo" property="fieldValue">
														<bean:define id="fdValue" name="OneTimeVo" property="fieldValue"/>
														<logic:equal name="status" value="<%=(String)fdValue%>" >
														 <bean:write name="OneTimeVo" property="fieldDescription"/>
														</logic:equal>
													</logic:present>
												</logic:present>
											</logic:iterate>
										</logic:present>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd" >
										<logic:present name="iterator" property="remarks">
											<bean:write name="iterator" property="remarks"/>
										</logic:present>
								</td>
							</tr>						
						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton accesskey="R" property="btCreate" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CREATE_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btcreate" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="D" property="btDetails" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_DETAILS_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btdetails" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="U" property="btRegisterCust" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_REGISTERCUST_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btregistercust" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="A" property="btAttachCust" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_ATTATCHCUST_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btattatchcust" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="B" property="btBlackListed" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_BLACKLISTED_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btblacklisted" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="V" property="btRevokeBlackListed" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_REVOKEBLACKLISTED_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btrevokeblacklisted" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="E" property="btDelete" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_DELETE_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btdelete" />
				</ihtml:nbutton>
				<ihtml:nbutton accesskey="O" property="btClose" componentID="CMP_CM_DEFAULTS_CM_LISTTEMPORARYCUSTOMERREGISTRATION_CLOSE_BTN">
					<bean:message bundle="listtempcustomerform" key="cm.defaults.btclose" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>	
</div>
</div>

</ihtml:form>
</div>
	
</body>
</html:html>


