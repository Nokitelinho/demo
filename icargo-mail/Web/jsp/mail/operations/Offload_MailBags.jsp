<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: Offload_MailBags.jsp
* Date				: 18-May-2017
* Author(s)			: A-6850
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm"%>


<business:sessionBean id="offloadReasonCode"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="offloadReasonCode" />


<business:sessionBean id="offloadVO"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.offload"
		  method="get"
		  attribute="offloadVO" />
<bean:define id="form"
	name="OffloadForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm"
    toScope="page"
    scope="request"/>

<div id="M" style="height:100%;" >
		<logic:present name="offloadVO" property="offloadMailbags">
			<bean:define id="offloadMailbagPage" name="offloadVO" property="offloadMailbags"/>
		</logic:present>
		<div class="ic-row">
					<div class="ic-col-50">
						 <logic:present name="offloadVO" property="offloadMailbags">
							<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
							name="offloadMailbagPage"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=((OffloadForm)form).getLastPageNumber() %>" />
						 </logic:present>

						 <logic:notPresent name="offloadVO" property="offloadMailbags">
							&nbsp;
						 </logic:notPresent>
					</div>
					<div class="iCargoLink ic-col-50 ic-right" >
						<logic:present name="offloadVO" property="offloadMailbags">
							  <common:paginationTag
							  pageURL="javascript:submitList('lastPageNum','displayPage')"
							  name="offloadMailbagPage"
							  display="pages"
							  linkStyleClass="iCargoLink"
							  disabledLinkStyleClass="iCargoLink"
							  lastPageNum="<%=((OffloadForm)form).getLastPageNumber() %>"
							  exportToExcel="true"
							  exportTableId="offloadMailbag"
							  exportAction="mailtracking.defaults.offload.list.do"/>
						</logic:present>

						<logic:notPresent name="offloadVO" property="offloadMailbags">
							&nbsp;
						</logic:notPresent>
					</div>
		</div>




	<div class="tableContainer" id="div3" style="height:550px;">
	<table id="offloadMailbag" class="fixed-header-table">
      <thead>
		  <tr>
			<td width="4%" class="iCargoTableHeaderLabel">
				<input type="checkbox" name="mailbagCheckAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.mailbagSubCheck);">
			 </td>
			<td width="21%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.mailBagId" /><span></span></td>
			<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.contno" /><span></span></td>
			<td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.pou" /><span></span></td>
			<td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.destn" /><span></span></td>
			<td width="25%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadreason" /></td>
			<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadremarks" /><span></span></td>
		  </tr>
      </thead>
	  <tbody>

	  <logic:equal name="form" property="type" value="M">

	  	<logic:present name="offloadVO">
			<logic:present name="offloadVO" property="offloadMailbags">
			<bean:define id="mailbags" name="offloadVO" property="offloadMailbags" scope="page" toScope="page"/>

         		<% Collection<String> selectedrows = new ArrayList<String>(); %>

				 <logic:present name="form" property="mailbagSubCheck" >

					<%
					String[] selectedRows = form.getMailbagSubCheck();
					for (int j = 0; j < selectedRows.length; j++) {
						selectedrows.add(selectedRows[j]);
					}
					%>

				 </logic:present>

         		<logic:iterate id="mailbagVO" name="mailbags" indexId="rowid">

				  <tr>
					<td>
					<div class="ic-center">
						<%
							if(selectedrows.contains(String.valueOf(rowid))){
						%>

							<input type="checkbox" name="mailbagSubCheck" value="<%= rowid.toString() %>" checked>
						<%
							}
							else{
						%>
							<input type="checkbox" name="mailbagSubCheck" value="<%= rowid.toString() %>">

						<%
							}
						%>
					</div>
					  </td>
					<td><bean:write name="mailbagVO" property="mailbagId"/></td>
					<td>
					<logic:present name="mailbagVO" property="paBuiltFlag">
				 		<logic:equal name="mailbagVO" property="paBuiltFlag" value="Y">
              				<bean:write name="mailbagVO" property="containerNumber"/>
              				<common:message key="mailtracking.defaults.offload.lbl.sb"/>
		      	 		</logic:equal>
		      	 		<logic:equal name="mailbagVO" property="paBuiltFlag" value="N">
              				<bean:write name="mailbagVO" property="containerNumber"/>
		      	 		</logic:equal>
		      	 	  </logic:present>
		      	 	  <logic:notPresent name="mailbagVO" property="paBuiltFlag">
		      	 	  		<bean:write name="mailbagVO" property="containerNumber"/>
		      	 	  </logic:notPresent>
					</td>
					<td><bean:write name="mailbagVO" property="pou"/></td>
					<td><bean:write name="mailbagVO" property="finalDestination"/></td>
					<td>

						<logic:present name="mailbagVO" property="offloadedDescription">
						<bean:define id="reasoncode" name="mailbagVO" property="offloadedReason" toScope="page"/>

							<ihtml:select property="mailbagOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_mailbagOffloadReason" value="<%= String.valueOf(reasoncode) %>" style="width:230px">
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
						<logic:notPresent name="mailbagVO" property="offloadedDescription">

							<ihtml:select property="mailbagOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_mailbagOffloadReason" value="" style="width:230px">
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
					<td>

						<logic:present name="mailbagVO" property="offloadedRemarks">
						<bean:define id="remarks" name="mailbagVO" property="offloadedRemarks" />
							<ihtml:text property="mailbagOffloadRemarks" maxlength="50"  value="<%= String.valueOf(remarks) %>" style="width:150px"
								componentID="CMP_MailTracking_Defaults_Offload_mailbagOffloadRemarks" />
						</logic:present>
						<logic:notPresent name="mailbagVO" property="offloadedRemarks">
							<ihtml:text property="mailbagOffloadRemarks" maxlength="50"  value="" style="width:150px"
								componentID="CMP_MailTracking_Defaults_Offload_mailbagOffloadRemarks" />
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