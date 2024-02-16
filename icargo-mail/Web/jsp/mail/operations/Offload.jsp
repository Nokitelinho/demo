<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: Offload.jsp
* Date				: 27-Jun-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm"%>

	
	
<html:html>

<head>
	
	<title><common:message bundle="offloadResources" key="mailtracking.defaults.offload.lbl.pagetitle" /></title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/Offload_Script.jsp" />
</head>

<body>
	
<div id="pageDiv" class="iCargoContent" style="width:100%;height:100%;overflow:auto;" >

<ihtml:form action="/mailtracking.defaults.offload.screenload.do" >

<bean:define id="form"
	name="OffloadForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm"
    toScope="page"
    scope="request"/>

<business:sessionBean id="offloadType"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="offloadType" />

<business:sessionBean id="mailCategory"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="mailCategory" />

<business:sessionBean id="containerTypes"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="containerTypes" />

<business:sessionBean id="offloadReasonCode"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="offloadReasonCode" />

<business:sessionBean id="mailClass"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="mailClass" />

<business:sessionBean id="offloadVO"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="offloadVO" />

<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="status" />
<ihtml:hidden property="mode" />
<ihtml:hidden property="flightStatus" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="closeflight" />
<ihtml:hidden property="clearFlag" />
<ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="displayPageNum" />
<ihtml:hidden property="warningFlag" />

<div class="ic-content-main">

 <span class="ic-page-title ic-display-none">
 <common:message key="mailtracking.defaults.offload.lbl.offload" />
  </span>
<div class="ic-head-container">
	<div class="ic-filter-panel">
		<div class="ic-row">
			<div class="ic-input-container ic-border">	  <!-- modified. A-8164 for ICRD-257601  -->
					<div class="ic-row">
							<div class="ic-input ic-col-16 ic-mandatory">
								<label><common:message key="mailtracking.defaults.offload.lbl.flight" /></label>
								
								<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight"
								 flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>"
								 flightcodevalue="<%=form.getFlightNumber()%>"
								 carrierCodeMaxlength="3" flightCodeMaxlength="5"
								 componentID="CMP_MailTracking_Defaults_Offload_Flight"/>
							</div>
							<div class="ic-input ic-col-16 ic-mandatory">
								<label><common:message key="mailtracking.defaults.offload.lbl.date" /></label>
								<ibusiness:calendar property="date" type="image" id="incalender"
								value="<%=form.getDate()%>" componentID="CMP_MailTracking_Defaults_Offload_Date" onblur="autoFillDate(this)"/>
							</div>
							<div class="ic-input ic-col-13">
								<label><common:message key="mailtracking.defaults.offload.lbl.depport" /></label>
								<ihtml:text property="departurePort"  readonly="true"  disabled="true" componentID="CMP_MailTracking_Defaults_Offload_DepPort" />
							</div>
							<div class="ic-input ic-col-16 ic-mandatory">
								<label><common:message key="mailtracking.defaults.offload.lbl.containertype" /></label>
								<ihtml:select property="containerType" componentID="CMP_MailTracking_Defaults_Offload_Conttype" value="<%=form.getContainerType()%>" >
									<logic:present name="containerTypes">
									<bean:define id="containertypes" name="containerTypes" toScope="page"/>
										<html:option value="ALL">All</html:option>
										<logic:iterate id="onetmvo" name="containertypes">
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

											<html:option value="<%= value.toString() %>"><%= desc %></html:option>

										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-col-15">
							 <label><common:message key="mailtracking.defaults.offload.lbl.contNo" /></label>
								<div id="contType_U">
								<ibusiness:uld id="containerNumber" uldProperty="containerNumber" 
								style="text-transform:uppercase;" componentID="CMP_MailTracking_Defaults_Offload_containerNumber" uldValue="<%=form.getContainerNumber()%>" maxlength="13"/>
							</div>
								<div id="contType_B">
								<ihtml:text id="containerNumber" property="containerNumber" 
								style="text-transform:uppercase;" componentID="CMP_MailTracking_Defaults_Offload_containerNumber" value="<%=form.getContainerNumber()%>" maxlength="13"/>
								</div>
							</div>
							<div class="ic-input ic-col-16">
							    <label><common:message key="mailtracking.defaults.offload.lbl.type" /></label>
									<ihtml:select property="type" onchange="selectDiv();"  componentID="CMP_MailTracking_Defaults_Offload_Type">
										<logic:present name="offloadType">
											<bean:define id="type" name="offloadType" toScope="page"/>
											<logic:iterate id="onetmvo" name="type">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
												<html:option value="<%= value.toString() %>"><%= desc %></html:option>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
							</div>
					</div>
					<div class="ic-row">
							<div class="ic-button-container">
							<ihtml:nbutton property="btList" componentID="CMP_MailTracking_Defaults_Offload_btList" accesskey="L">
								<common:message key="mailtracking.defaults.offload.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear"  componentID="CMP_MailTracking_Defaults_Offload_btClear" accesskey="E">
								<common:message key="mailtracking.defaults.offload.btn.clear" />
							</ihtml:nbutton>
							</div>
					</div>
			</div>
		</div>
		<div class="ic-row">
				<h4><common:message key="mailtracking.defaults.offload.lbl.DSNDetals" /></h4>
		</div>
        <div class="ic-row"> 
			<div class="ic-input-container ic-border">	   <!-- modified. A-8164 for ICRD-257601  -->
					<div class="ic-row">
							<div class="ic-input ic-col-15  ic-label-45">
								<label><common:message key="mailtracking.defaults.offload.lbl.dsn"/></label>
								<ihtml:text property="despatchSn" maxlength="4"  componentID="CMP_MailTracking_Defaults_Offload_Dsn" style="text-align:right"/>
							</div>
							<div class="ic-input ic-col-15  ic-label-45">
								<label><common:message key="mailtracking.defaults.offload.lbl.ooe" /></label>
								<ihtml:text property="originOE" maxlength="6"  componentID="CMP_MailTracking_Defaults_Offload_originOE" />
								<div class="lovImg"><img id="originOELov" value="originOELov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22"></div>
							</div>
							<div class="ic-input ic-col-20  ic-label-45">
								<label><common:message key="mailtracking.defaults.offload.lbl.doe" /></label>
								<ihtml:text property="destnOE" maxlength="6"  componentID="CMP_MailTracking_Defaults_Offload_destnOE" />
								<div class="lovImg"><img id="destnOELov" value="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
							</div>
							<div class="ic-input ic-col-30  ic-label-45">
								<label><common:message key="mailtracking.defaults.offload.lbl.mailclass" /></label>
								<ihtml:select property="mailClass"  componentID="CMP_MailTracking_Defaults_Offload_mailClass">
									<logic:present name="mailClass">
											<bean:define id="mailclass" name="mailClass" toScope="page"/>
											<ihtml:option value="">
												<common:message key="combo.select"/>
											</ihtml:option>
											<logic:iterate id="onetmvo" name="mailclass">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
												<html:option value="<%= value.toString() %>"><%= desc %></html:option>
											</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-col-20  ic-label-45">
								<label><common:message key="mailtracking.defaults.offload.lbl.year" /></label>
								<ihtml:text property="year" maxlength="1"  componentID="CMP_MailTracking_Defaults_Offload_Dsn_Year" style="text-align:right"/>
							</div>
					</div> 
			</div>	
		</div>	   
        <div class="ic-row">
           <h4><common:message key="mailtracking.defaults.offload.lbl.mailBagDetals" /></h4>
        </div>
        <div class="ic-row">
			<div class="ic-input-container ic-border">	   <!-- modified. A-8164 for ICRD-257601  -->
					<div class="ic-row">
							<div class="ic-input ic-col-23">
								<label><common:message key="mailtracking.defaults.offload.lbl.mailBagId" /></label>
								<ihtml:text property="mailbagId" maxlength="29"  componentID="CMP_MailTracking_Defaults_Offload_MailbagId" style="text-align:right;width:210px"/> <!--modified. A-8164 for ICRD 257609-->
							</div>					
							<div class="ic-input ic-col-9">
								<label><common:message key="mailtracking.defaults.offload.lbl.rsn" /></label>
								<ihtml:text property="mailbagRsn" maxlength="3"  componentID="CMP_MailTracking_Defaults_Offload_Rsn" style="text-align:right"/>
							</div>
							<div class="ic-input ic-col-10">
								<label><common:message key="mailtracking.defaults.offload.lbl.ooe" /></label>
								<ihtml:text property="mailbagOriginOE" maxlength="6"  componentID="CMP_MailTracking_Defaults_Offload_Mail_originOE" />
								<div class="lovImg"><img id="mailbagOriginOELov" value="mailbagOriginOELov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22"></div>
							</div>
							<div class="ic-input ic-col-10">
								<label><common:message key="mailtracking.defaults.offload.lbl.doe" /></label>
								<ihtml:text property="mailbagDestnOE" maxlength="6"  componentID="CMP_MailTracking_Defaults_Offload_Mail_destnOE" />
								<div class="lovImg"><img id="mailbagDestnOELov" value="mailbagDestnOELov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22"></div>
							</div>
							<div class="ic-input ic-col-18">
								<label><common:message key="mailtracking.defaults.offload.lbl.category" /></label>
									<ihtml:select property="mailbagCategory"  componentID="CMP_MailTracking_Defaults_Offload_category" style="width:140px">
										<logic:present name="mailCategory">
											<bean:define id="category" name="mailCategory" toScope="page"/>
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:iterate id="onetmvo" name="category">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
												<html:option value="<%= value.toString() %>"><%= desc %></html:option>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
							</div>
							<div class="ic-input ic-col-13">
								<label><common:message key="mailtracking.defaults.offload.lbl.subclass" /></label>
								<ihtml:text property="mailbagSubClass" maxlength="2"  componentID="CMP_MailTracking_Defaults_Offload_Subclass" />
								<div class="lovImg"><img id="mailbagSubClassLov" value="mailbagSubClassLov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22"></div>
							</div>
							<div class="ic-input ic-col-8">
								<label><common:message key="mailtracking.defaults.offload.lbl.year" /></label>
								<ihtml:text property="mailbagYear" maxlength="1"  componentID="CMP_MailTracking_Defaults_Offload_Mailbag_Year" style="text-align:right;width:30px"/>
							</div>
							<div class="ic-input ic-col-9">
								<label><common:message key="mailtracking.defaults.offload.lbl.dsn" /></label>
								<ihtml:text property="mailbagDsn" maxlength="4"  componentID="CMP_MailTracking_Defaults_Offload_Mail_Dsn" style="text-align:right"/>
							</div>
					</div>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton property="btMailBagList"  componentID="CMP_MailTracking_Defaults_Offload_btMailBagList" accesskey="L">
					<common:message key="mailtracking.defaults.offload.btn.list" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btMailBagClear"  componentID="CMP_MailTracking_Defaults_Offload_btMailBagClear" accesskey="E">
					<common:message key="mailtracking.defaults.offload.btn.clear" />
				</ihtml:nbutton>
            </div>
        </div>

   
	
	 
	</div>
</div>
<div class="ic-main-container">
	<div id="U" style="height:100%;">
		<div class="tableContainer"  id="div1"  style="height:500px;">
				<table id="offloadULD" class="fixed-header-table" >
					<thead>
						<tr width="3%" class="iCargoTableHeader">
						  <td class="iCargoTableHeaderLabel">
							  <input type="checkbox" name="uldCheckAll" value="checkbox">
						  </td>
						  <td width="13%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.contno" /><span></span></td>
						  <td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.pou" /><span></span></td>
						  <td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.destn" /><span></span></td>
						  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.acceptedBags" /><span></span></td>
						  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.acceptedWt" /><span></span></td>
						  <td width="20%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadreason" /></td>
						  <td width="20%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadremarks" /><span></span></td>
						</tr>
					</thead>
					<tbody>
						<logic:equal name="form" property="type" value="U">
							<logic:present name="offloadVO">
								<logic:present name="offloadVO" property="offloadContainers">
									<bean:define id="containers" name="offloadVO" property="offloadContainers" type="java.util.Collection" toScope="page"/>
									<% Collection<String> selectedrows = new ArrayList<String>(); %>
										<logic:present name="form" property="uldSubCheck" >
										<%
										String[] selectedRows = form.getUldSubCheck();
										for (int j = 0; j < selectedRows.length; j++) {
											selectedrows.add(selectedRows[j]);
										}
										%>
										</logic:present>
									<logic:iterate id="containervo" name="containers" indexId="rowid">
									<tr>
										<td>
											<div class="ic-center">
											<%
												if(selectedrows.contains(String.valueOf(rowid))){
											%>

												<input type="checkbox" name="uldSubCheck" value="<%= rowid.toString() %>" checked>
											<%
												}
												else{
											%>
												<input type="checkbox" name="uldSubCheck" value="<%= rowid.toString() %>">

											<%
												}
											%>
											</div>
										</td>
										<td class="ic-center">
											  <logic:present name="containervo" property="paBuiltFlag">
												<logic:equal name="containervo" property="paBuiltFlag" value="Y">
													<bean:write name="containervo" property="containerNumber"/>
													<common:message key="mailtracking.defaults.offload.lbl.sb"/>
												</logic:equal>
												<logic:equal name="containervo" property="paBuiltFlag" value="N">
													<bean:write name="containervo" property="containerNumber"/>
												</logic:equal>
											  </logic:present>
											  <logic:notPresent name="containervo" property="paBuiltFlag">
													<bean:write name="containervo" property="containerNumber"/>
											  </logic:notPresent>
										</td>
										<td class="ic-center"><bean:write name="containervo" property="pou"/></td>
										<td class="ic-center"><bean:write name="containervo" property="finalDestination"/></td>
										<td style="text-align:right"><bean:write name="containervo" property="bags"/></td>
										<td style="text-align:right">
									   <common:write name="containervo" property="weight" unitFormatting="true" /></td>
										<td class="ic-center">
											<logic:present name="containervo" property="offloadedDescription">
											<bean:define id="reasoncode" name="containervo" property="offloadedReason" toScope="page"/>

												<ihtml:select property="contOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_contOffloadReason" value="<%= String.valueOf(reasoncode) %>" style="width:230px">
													<logic:present name="offloadReasonCode">
													<bean:define id="reason" name="offloadReasonCode" toScope="page"/>

														<logic:iterate id="onetmvo" name="reason">
															<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
															<bean:define id="value" name="onetimevo" property="fieldValue"/>
															<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

															<html:option value="<%= value.toString() %>"><%= desc %></html:option>

														</logic:iterate>

													</logic:present>
												</ihtml:select>

											</logic:present>
											<logic:notPresent name="containervo" property="offloadedDescription">

												<ihtml:select property="contOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_contOffloadReason" value="" style="width:230px">
													<logic:present name="offloadReasonCode">
													<bean:define id="reason" name="offloadReasonCode" toScope="page"/>

														<logic:iterate id="onetmvo" name="reason">
															<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
															<bean:define id="value" name="onetimevo" property="fieldValue"/>
															<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

															<html:option value="<%= value.toString() %>"><%= desc %></html:option>

														</logic:iterate>

													</logic:present>
												</ihtml:select>

											</logic:notPresent>
									  </td>
									  <td class="ic-center">
										<logic:present name="containervo" property="offloadedRemarks">
										<bean:define id="remarks" name="containervo" property="offloadedRemarks" />
											<ihtml:text property="contOffloadRemarks" maxlength="50" value="<%= String.valueOf(remarks) %>" style="width:150px"
												 componentID="CMP_MailTracking_Defaults_Offload_contOffloadRemarks" />
										</logic:present>
										<logic:notPresent name="containervo" property="offloadedRemarks">
											<ihtml:text property="contOffloadRemarks" maxlength="50" value=""
												 componentID="CMP_MailTracking_Defaults_Offload_contOffloadRemarks" style="width:150px" />
										</logic:notPresent>
									  </td>
									</tr>
									</logic:iterate>
								</logic:present>
							</logic:present>
						</logic:equal>
					</tbody>
				</table>
		</div>
	</div>

	<jsp:include page="Offload_DSNDetails.jsp" /> 

	<jsp:include page="Offload_MailBags.jsp" /> 

   
</div>
<div class="ic-foot-container">
	<div class="ic-button-container paddR5">

      	<ihtml:nbutton property="btOffload"  componentID="CMP_MailTracking_Defaults_Offload_btOffload" accesskey="F">
		<common:message key="mailtracking.defaults.offload.btn.offload" />
	</ihtml:nbutton>

      	<ihtml:nbutton property="btClose" componentID="CMP_MailTracking_Defaults_Offload_btClose" accesskey="O">
	  	<common:message key="mailtracking.defaults.offload.btn.close" />
	</ihtml:nbutton>

    </div>
</div>

</div>

</ihtml:form>

</div>
	</body>

</html:html>

