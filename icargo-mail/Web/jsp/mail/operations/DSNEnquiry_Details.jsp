<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import="java.util.Collection"%>
 <%@ page import="java.util.ArrayList"%>

<bean:define id="form"
	name="DsnEnquiryForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm"
	toScope="page"
	scope="request"/>

<ihtml:form action="/mailtracking.defaults.dsnenquiry.screenload.do">

	<business:sessionBean id="despatchDetailsVOPage"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.dsnEnquiry"
			  method="get"
			  attribute="despatchDetailsVOs" />

	<business:sessionBean id="mailClass"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.dsnEnquiry"
			  method="get"
			  attribute="mailClass" />

	<business:sessionBean id="mailCategory"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.dsnEnquiry"
			  method="get"
			  attribute="mailCategory" />


	<logic:present name="despatchDetailsVOPage">
		<bean:define id="despatchDetailsPage" name="despatchDetailsVOPage" toScope="page"/>
	</logic:present>

	<ihtml:hidden property="screenStatusFlag" />
	<ihtml:hidden property="lastPageNum" />
	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="status" />
	<ihtml:hidden  property="currentDialogOption" />
	<ihtml:hidden property="currentDialogId" />
	<ihtml:hidden property="reList" />	


	<div id="_paginationLabels">
	 	  <%String lstPgNo = "";%>
		<logic:present name="DsnEnquiryForm" property="lastPageNum">
		<bean:define id="lastPg" name="DsnEnquiryForm" property="lastPageNum" scope="request" toScope="page" />
				<%
					lstPgNo = (String) lastPg;
				%>
		</logic:present>

		<logic:present name="despatchDetailsPage" >
			  <bean:define id="pageObj" name="despatchDetailsPage"   toScope="page" />

			  <common:paginationTag pageURL="mailtracking.defaults.dsnenquiry.list.do"
						name="pageObj"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=lstPgNo%>" />
		</logic:present>
	 </div>

	 <div id="_paginationLinks">
	 	<logic:present name="despatchDetailsPage" >
			<bean:define id="pageObj1" name="despatchDetailsPage"   toScope="page" />
		   <common:paginationTag
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitPage('lastPageNum','displayPage')"
						name="pageObj1"
						display="pages"
						lastPageNum="<%=lstPgNo%>"
						exportToExcel="true"
						exportTableId="dsnenquiry"
						exportAction="mailtracking.defaults.dsnenquiry.list.do"/>
		</logic:present>

		<logic:notPresent name="despatchDetailsPage">
				&nbsp;
		</logic:notPresent>
 	</div>


	<table  id="dsnenquiry"
								class="fixed-header-table" >

					<thead>
					<tr class="ic-th-all">
										<th style="width: 3%" />
										<th style="width: 6%" />
										<th style="width: 8%" />
										<th style="width: 8%" />
										<th style="width: 12%" />
										<th style="width: 8%" />
										<th style="width: 6%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 8%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
									</tr>
				  <tr >
			   <td class="iCargoTableHeaderLabel"><div align="center">
				 <input name="checkAll" type="checkbox" value="" /><div>
					</td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.dsn" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.ooe" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.doe" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.class" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.SC" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.year" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.category" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.uld" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.consignmentno" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.pacode" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.fltordestn" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.flightdate" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.airport" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.plt" /> </td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.wt" /></td>
					<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.noOfBags" /></td>
				  </tr>
				</thead>
				<tbody>

				  <logic:present name="despatchDetailsPage" >
	<%
		int i = 1;
		int count = 1;
	%>
				  <bean:define id="despatchDetailVOs" name="despatchDetailsPage" scope="page" toScope="page"/>
				   <% Collection<String> selectedrows = new ArrayList<String>(); %>

					 <logic:present name="DsnEnquiryForm" property="subCheck" >

						<%
						String[] selectedRows = form.getSubCheck();
						for (int j = 0; j < selectedRows.length; j++) {

							selectedrows.add(selectedRows[j]);
						}
						%>

					</logic:present>
					<logic:iterate id="despatchDetailVO" name="despatchDetailVOs" indexId="rowid">
			<common:rowColorTag index="rowid">
                        <tr bgcolor="<%=color%>">
						<td width="33"  align="center" class="iCargoTableDataTd">
						<div align="center">
						<%
							if(selectedrows.contains(String.valueOf(rowid))){
						%>

							<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" checked="true">
						<%
							}
							else{
						%>
							<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" />

						<%
							}
						%>
						  </div></td>
						<td width="69" class="iCargoTableDataTd" style="text-align:right"><bean:write name="despatchDetailVO" property="dsn"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="originOfficeOfExchange"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="destinationOfficeOfExchange"/></td>
						<td width="76" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="mailClass">
							<bean:define id="mailsubclass" name="despatchDetailVO" property="mailClass" toScope="page"/>

								<logic:present name="mailClass">
								<bean:define id="mailclass" name="mailClass" toScope="page"/>

									<logic:iterate id="onetmvo" name="mailclass">
										<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
										<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>

										<logic:match name="onetimevo" property="fieldValue" value="<%= mailsubclass.toString() %>">
											<%= onetimedesc %>
										</logic:match>

									</logic:iterate>
								</logic:present>
							</logic:present>

						</td>
						<td width="79" class="iCargoTableDataTd">


																 <% String subclassValue = ""; %>
																  <logic:notPresent name="despatchDetailVO" property="mailSubclass">
																	&nbsp;
																</logic:notPresent>
																<logic:present name="despatchDetailVO" property="mailSubclass">
																<bean:define id="despatchSubclass" name="despatchDetailVO" property="mailSubclass" toScope="page"/>
																<% subclassValue = (String) despatchSubclass;
																			   int arrays=subclassValue.indexOf("_");
																			   if(arrays==-1){%>

																	<bean:write name="despatchDetailVO" property="mailSubclass"/>
																	<%}else{%>
																	&nbsp;
																	<%}%>
									 							</logic:present>
						</td>

						<td width="60" class="iCargoTableDataTd" style="text-align:right">

							<bean:define id="year" name="despatchDetailVO" property="year" toScope="page"/>
							200<%= year %>

						</td>
						<td width="79" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="mailCategoryCode">
							<bean:define id="categorycode" name="despatchDetailVO" property="mailCategoryCode" toScope="page"/>

								<logic:present name="mailCategory">
								<bean:define id="mailcategory" name="mailCategory" toScope="page"/>

									<logic:iterate id="onetmvo" name="mailcategory">
										<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
										<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>

										<logic:equal name="onetimevo" property="fieldValue" value="<%= categorycode.toString() %>">
											<%= onetimedesc %>
										</logic:equal>

									</logic:iterate>
								</logic:present>
							</logic:present>

						</td>
						<td width="120" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="containerNumber"/></td>
						<td width="89" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="consignmentNumber"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="paCode"/></td>
						<td width="90" class="iCargoTableDataTd">
							<bean:write name="despatchDetailVO" property="carrierCode"/>

							<logic:present name="despatchDetailVO" property="flightDate">
								<bean:define id="seqno" name="despatchDetailVO" property="flightSequenceNumber" toScope="page"/>
								<logic:notEqual name="seqno" value="-1">

								<bean:write name="despatchDetailVO" property="flightNumber"/>

								</logic:notEqual>
							</logic:present>

						</td>
						<td width="180" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="flightDate">
								<bean:define id="seqno" name="despatchDetailVO" property="flightSequenceNumber" toScope="page"/>
								<logic:notEqual name="seqno" value="-1">

								<bean:define id="flightDate" name="despatchDetailVO" property="flightDate"/>
								<%=((LocalDate)flightDate).toDisplayFormat("dd-MMM-yyyy")%>

								</logic:notEqual>
							</logic:present>

						</td>
						
						<td width="71" class="iCargoTableDataTd">
							<logic:present name="despatchDetailVO" property="airportCode">
								<bean:define id="airport" name="despatchDetailVO" property="airportCode" toScope="page"/>
								<ihtml:hidden property="dsnPort"  value = "<%=(String)airport%>"/>
								<bean:write name="despatchDetailVO" property="airportCode"/>
							</logic:present>
						</td>
						
						<td width="33"  align="center" class="iCargoTableDataTd">
						  <div align="center">
							<logic:present name="despatchDetailVO" property="pltEnabledFlag">
								<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
									<!--<input name="pltFlag" type="checkbox" checked="true" disabled/>-->
									<ihtml:hidden property="contNumber"  value = "true"/>
									<img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
								</logic:equal>
								<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="false">
									<input name="pltFlag" type="checkbox" disabled/>-->
									<!--<ihtml:hidden property="contNumber"  value = "false"/> -->
									<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
								</logic:equal> 
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="pltEnabledFlag">
							<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
							</logic:notPresent>


						  </div></td>
						<td width="70" class="iCargoTableDataTd" style="text-align:right">

							<logic:present name="despatchDetailVO" property="flightDate">
								<logic:equal name="form" property="operationType" value="I">
									<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
									  <common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />
									
									</logic:equal>
									<logic:notEqual name="despatchDetailVO" property="pltEnabledFlag" value="true">
									<common:write name="despatchDetailVO" property="receivedWeight" unitFormatting="true" />
										
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual name="form" property="operationType" value="I">
									<common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />
								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="flightDate">
								<common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />
							</logic:notPresent>

						</td>
						<td width="94" class="iCargoTableDataTd" style="text-align:right">

							<logic:present name="despatchDetailVO" property="flightDate">
								<logic:equal name="form" property="operationType" value="I">
									<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
										<bean:write name="despatchDetailVO" property="acceptedBags"/>
									</logic:equal>
									<logic:notEqual name="despatchDetailVO" property="pltEnabledFlag" value="true">
										<bean:write name="despatchDetailVO" property="receivedBags"/>
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual name="form" property="operationType" value="I">
									<bean:write name="despatchDetailVO" property="acceptedBags"/>
								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="flightDate">
								<bean:write name="despatchDetailVO" property="acceptedBags"/>
							</logic:notPresent>

						</td>
					  </tr>
                </common:rowColorTag>
				  </logic:iterate>
				 </logic:present>

				</tbody>
		 </table>


</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>