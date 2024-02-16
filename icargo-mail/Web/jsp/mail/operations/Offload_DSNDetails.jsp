<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: Offload_DSNDetails.jsp
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
<div id="D" style="height:100%;">
     	<logic:present name="offloadVO" property="offloadDSNs">
     		<bean:define id="offloadDSNPage" name="offloadVO" property="offloadDSNs"/>
     	</logic:present>
     	<div class="ic-row">
					<div class="ic-col-50">
     				 <logic:present name="offloadVO" property="offloadDSNs">
     					<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
     					name="offloadDSNPage"
     					display="label"
     					labelStyleClass="iCargoResultsLabel"
     					lastPageNum="<%=((OffloadForm)form).getLastPageNumber() %>" />
     				 </logic:present>

     				 <logic:notPresent name="offloadVO" property="offloadDSNs">
     					&nbsp;
     				 </logic:notPresent>


     			</div>
     			<div class="iCargoLink ic-col-50 ic-right" >
     				<logic:present name="offloadVO" property="offloadDSNs">
     					  <common:paginationTag
     					  pageURL="javascript:submitList('lastPageNum','displayPage')"
     					  name="offloadDSNPage"
     					  display="pages"
     					  linkStyleClass="iCargoLink"
     					  disabledLinkStyleClass="iCargoLink"
     					  lastPageNum="<%=((OffloadForm)form).getLastPageNumber() %>" />
     				</logic:present>

     				<logic:notPresent name="offloadVO" property="offloadDSNs">
     					&nbsp;
     				</logic:notPresent>
     			</div>

     	</div>
		<div class="tableContainer" id="div2" style="height:550px;">
	       	<table id="offloadDSN" class="fixed-header-table">
            <thead>
			<tr>
			  <td width="2%" class="iCargoTableHeaderLabel">
				  <input type="checkbox" name="dsnCheckAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.dsnSubCheck);"><span></span>
				</td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.dsn" /><span></span></td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.ooe" /><span></span></td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.doe" /><span></span></td>
			  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.mailclass" /><span></span></td>
			  <td width="6%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.SC" /><span></span></td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.year" /><span></span></td>
			  <td width="9%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.contno" /><span></span></td>
			  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.consignmentno" /><span></span></td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.pou" /><span></span></td>
			  <td width="4%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.destn" /><span></span></td>
			  <td width="8%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.acceptedBags" /><span></span></td>
			  <td width="8%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.acceptedWt" /><span></span></td>
			  <td width="18%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadreason" /></td>
			  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.offload.lbl.offloadremarks" /><span></span></td>
			</tr>
            </thead>
            <tbody>

              <logic:equal name="form" property="type" value="D">

              <logic:present name="offloadVO">
			    <logic:present name="offloadVO" property="offloadDSNs">
         		<bean:define id="dsns" name="offloadVO" property="offloadDSNs" scope="page" toScope="page"/>

         		<% Collection<String> selectedrows = new ArrayList<String>(); %>

				 <logic:present name="form" property="dsnSubCheck" >

					<%
					String[] selectedRows = form.getDsnSubCheck();
					for (int j = 0; j < selectedRows.length; j++) {
						selectedrows.add(selectedRows[j]);
					}
					%>

				 </logic:present>

         		<logic:iterate id="dsnVO" name="dsns" indexId="rowid">
                         <tr>


					  <td class="iCargoTableDataTd" ><div class="ic-center">

						<%
							if(selectedrows.contains(String.valueOf(rowid))){
						%>

							<input type="checkbox" name="dsnSubCheck" value="<%= rowid.toString() %>" checked>
						<%
							}
							else{
						%>
							<input type="checkbox" name="dsnSubCheck" value="<%= rowid.toString() %>">

						<%
							}
						%>

						</div></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="dsn"/></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="originOfficeOfExchange"/></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="destinationOfficeOfExchange"/></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="mailClass"/></td>
					   <td class="iCargoTableDataTd" >
					  <% String subclassValue = ""; %>
						<logic:notPresent name="dsnVO" property="mailSubclass">
						&nbsp;
						</logic:notPresent>
						<logic:present name="dsnVO" property="mailSubclass">
						<bean:define id="despatchSubclass" name="dsnVO" property="mailSubclass" toScope="page"/>
						<% subclassValue = (String) despatchSubclass;
						int arrays=subclassValue.indexOf("_");
						if(arrays==-1){%>

						<bean:write name="dsnVO" property="mailSubclass"/>
						<%}else{%>
						&nbsp;
						<%}%>
						</logic:present>

					  </td>
					  <td style="text-align:right" class="iCargoTableDataTd" ><bean:write name="dsnVO" property="year"/></td>
					  <td class="iCargoTableDataTd" >
					  <logic:present name="dsnVO" property="paBuiltFlag">
				 		<logic:equal name="dsnVO" property="paBuiltFlag" value="Y">
              				<bean:write name="dsnVO" property="containerNumber"/>
              				<common:message key="mailtracking.defaults.offload.lbl.sb"/>
		      	 		</logic:equal>
		      	 		<logic:equal name="dsnVO" property="paBuiltFlag" value="N">
              				<bean:write name="dsnVO" property="containerNumber"/>
		      	 		</logic:equal>
		      	 	  </logic:present>
		      	 	  <logic:notPresent name="dsnVO" property="paBuiltFlag">
		      	 	  		<bean:write name="dsnVO" property="containerNumber"/>
		      	 	  </logic:notPresent>
		      	 	  </td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="consignmentNumber"/></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="pou"/></td>
					  <td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="destination"/></td>
					  <td style="text-align:right" class="iCargoTableDataTd" ><bean:write name="dsnVO" property="acceptedBags"/></td>
					  <td style="text-align:right" class="iCargoTableDataTd" >
					   <common:write name="dsnVO" property="acceptedWeight" unitFormatting="true" />
					  </td>
					  <td class="iCargoTableDataTd" >

					  	<logic:present name="dsnVO" property="offloadedDescription">
						<bean:define id="reasoncode" name="dsnVO" property="offloadedReason" toScope="page"/>

							<ihtml:select property="dsnOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_dsnOffloadReason" value="<%= String.valueOf(reasoncode) %>" style="width:230px">
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
						<logic:notPresent name="dsnVO" property="offloadedDescription">

							<ihtml:select property="dsnOffloadReason"  componentID="CMP_MailTracking_Defaults_Offload_dsnOffloadReason" value="" style="width:230px">
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
					  <td class="iCargoTableDataTd" >

						<logic:present name="dsnVO" property="offloadedRemarks">
						<bean:define id="remarks" name="dsnVO" property="offloadedRemarks" />
							<ihtml:text property="dsnOffloadRemarks" maxlength="50"  style="width:150px" value="<%= String.valueOf(remarks) %>"
								componentID="CMP_MailTracking_Defaults_Offload_dsnOffloadRemarks" />
						</logic:present>
						<logic:notPresent name="dsnVO" property="offloadedRemarks">
							<ihtml:text property="dsnOffloadRemarks" maxlength="50"  value=""
								componentID="CMP_MailTracking_Defaults_Offload_dsnOffloadRemarks" style="width:150px" />
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