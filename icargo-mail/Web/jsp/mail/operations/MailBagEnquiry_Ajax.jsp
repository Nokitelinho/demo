<%--****************************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: MailBagEnquiry_Ajax.jsp
* Date				: 11-FEB-2008
* Author(s)			: Reno K Abraham
 ***************************************************--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="/mailtracking.defaults.mailbagenquiry.screenload.do" >
<bean:define id="form"
	name="MailBagEnquiryForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm"
    toScope="page"
    scope="request"/>

<business:sessionBean id="currentStatus"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailBagEnquiry"
		  method="get"
		  attribute="currentStatus" />

<business:sessionBean id="operationTypes"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailBagEnquiry"
		  method="get"
		  attribute="operationTypes" />

<business:sessionBean id="mailCategory"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailBagEnquiry"
		  method="get"
		  attribute="mailCategory" />

<business:sessionBean id="mailbagVOPage"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailBagEnquiry"
		  method="get"
		  attribute="mailbagVOs" />

<logic:present name="mailbagVOPage">
	<bean:define id="mailbagsPage" name="mailbagVOPage" toScope="page"/>
</logic:present>

<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="status" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="assignToFlight" />
<ihtml:hidden property="destination" />
<ihtml:hidden property="destinationCity" />
<ihtml:hidden property="carrierInv" />
<ihtml:hidden property="selCont" />
<ihtml:hidden property="reList" />
<ihtml:hidden property="successMailFlag" />

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<div class="ic-content-main">
<div class="ic-main-container">
<div id = "_mailBagEnquiryTable" >
<div class="tableContainer" id="div1"  style="height:565px">
   <table   id="mailbagenquiry" class="fixed-header-table" >
		<thead>
				<tr class="ic-th-all">
												<th style="width: 2%" />
										<th style="width: 9%" />
										<th style="width: 8%" />
										<th style="width: 6%" />

										<th style="width: 16%" />
										<th style="width: 8%" />
										<th style="width: 5%" />
										<th style="width: 7%" />
										<th style="width: 8%" />
										<th style="width: 8%" />
										<th style="width: 8%" />
										<th style="width: 6%" />
										<th style="width: 6%" />
										<th style="width: 6%" />
												</tr>
                                  <tr >
		    <td class="iCargoTableHeaderLabel"data-ic-csid="td0">
		      <input type="checkbox" name="checkAll" value="checkbox">
		    </td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td1"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.latestOprn" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td2"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.dateOfOpertn" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td3"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.airport" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td4"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.mailbagId" /><span></span></td>
			<td class="iCargoTableHeaderLabel" data-ic-csid="td5"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.rdt" /><span></span></td>			
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td6"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.inout" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td7"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.fltDestn" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td8"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.deparrDate" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td9"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.contNo" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td10"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.consignmentNo" /><span></span></td>
			<td class="iCargoTableHeaderLabel" data-ic-csid="td11"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.awbnumber" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td12"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.userid" /><span></span></td>
		    <td class="iCargoTableHeaderLabel" data-ic-csid="td13"><common:message key="mailtracking.defaults.mailbagenquiry.lbl.weight" /><span></span></td>
		  </tr>
		</thead>
		<tbody>

		<logic:present name="mailbagsPage" >
		<bean:define id="mailBagVOs" name="mailbagsPage" scope="page" toScope="page"/>

		<% Collection<String> selectedrows = new ArrayList<String>(); %>

		<logic:present name="form" property="subCheck" >

			<%
			String[] selectedRows = form.getSubCheck();
			for (int j = 0; j < selectedRows.length; j++) {
				selectedrows.add(selectedRows[j]);
			}
			%>

		</logic:present>

		<logic:iterate id="mailBagVO" name="mailBagVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO">

                <tr >

			<td  class="iCargoTableDataTd" style="text-align:center" id="td0">

			  <div >

				<%
					if(selectedrows.contains(String.valueOf(rowid))){
				%>

					<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" checked="true">
				<%
					}
					else{
				%>
					<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>">

				<%
					}
				%>

			  </div>

			</td>
			<td  class="iCargoTableDataTd" id="td1">
				<logic:present name="mailBagVO" property="latestStatus">
				<bean:define id="lateststatus" name="mailBagVO" property="latestStatus" toScope="page"/>

					<logic:present name="currentStatus">
					<bean:define id="currentstatus" name="currentStatus" toScope="page"/>

						<logic:iterate id="onetmvo" name="currentstatus">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>

							<logic:equal name="onetimevo" property="fieldValue" value="<%= lateststatus.toString() %>">
								<%= onetimedesc %>
							</logic:equal>

						</logic:iterate>
					</logic:present>
				</logic:present>

			</td>
			<td  class="iCargoTableDataTd" id="td2">

				<logic:present name="mailBagVO" property="scannedDate">
				  <bean:define id="scannedDate" name="mailBagVO" property="scannedDate" toScope="page"/>
				  <%= scannedDate.toString().substring(0,11) %>
				</logic:present>
				<logic:notPresent name="mailBagVO" property="scannedDate">
					&nbsp;
				</logic:notPresent>

			</td>
			<td class="iCargoTableDataTd" id="td3"><bean:write name="mailBagVO" property="scannedPort"/></td>
			<td   class="iCargoTableDataTd" id="td4" ><bean:write name="mailBagVO" property="mailbagId"/></td>
			<td  class="iCargoTableDataTd" id="td5">
				<logic:present name="mailBagVO" property="reqDeliveryTime">
				<%=mailBagVO.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm")%>
				</logic:present>
			</td>	
			<td   class="iCargoTableDataTd"id="td6" >
			<logic:present name="operationTypes">
			<bean:define id="status" name="currentStatus" toScope="page"/>
				<logic:iterate id="onetmvo" name="operationTypes">
					<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
					<bean:define id="value" name="onetimevo" property="fieldValue"/>
					<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
					<logic:equal name="mailBagVO" property="operationalStatus" value="<%=(String)value%>">
					<%= desc %>
					</logic:equal>
				</logic:iterate>

			</logic:present>

			</td>
			<td   class="iCargoTableDataTd"id="td7" >

				<logic:present name="mailBagVO" property="flightDate">
				  <logic:present name="mailBagVO" property="carrierCode">

					<bean:define id="carrierCode" name="mailBagVO" property="carrierCode" toScope="page"/>
					<bean:define id="flightNumber" name="mailBagVO" property="flightNumber" toScope="page"/>

					<%= carrierCode %><%= flightNumber %>

				  </logic:present>
				</logic:present>
				<logic:notPresent name="mailBagVO" property="flightDate">
					<logic:present name="mailBagVO" property="carrierCode">
						<bean:define id="carrierCode" name="mailBagVO" property="carrierCode" toScope="page"/>
						<%= carrierCode %>
					</logic:present>
				</logic:notPresent>

			</td>
			<td   class="iCargoTableDataTd" id="td8" >

				<logic:present name="mailBagVO" property="flightDate">
					<bean:define id="flightDate" name="mailBagVO" property="flightDate" toScope="page"/>
					<%= flightDate.toString().substring(0,11) %>
				</logic:present>
				<logic:present name="mailBagVO" property="flightDate">
					&nbsp;
				</logic:present>

			</td>
			<td   class="iCargoTableDataTd" id="td9">
				<logic:present name="mailBagVO" property="paBuiltFlag">
					<logic:equal name="mailBagVO" property="paBuiltFlag" value="Y">
						<bean:write name="mailBagVO" property="containerNumber"/>
						<common:message key="mailtracking.defaults.mailbagenquiry.lbl.shipperBuild" />
					</logic:equal>
					<logic:equal name="mailBagVO" property="paBuiltFlag" value="N">
						<bean:write name="mailBagVO" property="containerNumber"/>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="mailBagVO" property="paBuiltFlag">
					<bean:write name="mailBagVO" property="containerNumber"/>
				</logic:notPresent>
		    </td>
			<td class="iCargoTableDataTd" id="td10"><!--<bean:write name="mailBagVO" property="consignmentNumber"/>-->
				<logic:present name="mailBagVO" property="consignmentNumber">
					<ihtml:text  componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ConsigmentNumber1" indexId="rowid" readonly="true" property="consignmentNumber" name="mailBagVO" />
				</logic:present>
				<logic:notPresent name="mailBagVO" property="consignmentNumber">
					<ihtml:text  componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ConsigmentNumber1" indexId="rowid"  readonly="true" property="consignmentNumber" maxlength="35" value="" />
				</logic:notPresent>
			</td>
			<td  class="iCargoTableDataTd" id="td11">
			<logic:present name="mailBagVO" property="documentNumber">
			<bean:write name="mailBagVO" property="shipmentPrefix" />-
					<bean:write name="mailBagVO" property="documentNumber" />
			</logic:present>
			<logic:notPresent name="mailBagVO" property="documentNumber">
					&nbsp;
			</logic:notPresent>													
					</td>				
			<td  class="iCargoTableDataTd" id="td12"><bean:write name="mailBagVO" property="scannedUser"/></td>
					<td   class="iCargoTableDataTd" id="td13" style="text-align:right">
					  <common:write name="mailBagVO" property="weight" unitFormatting="true" />
					</td>
		  </tr>

		</logic:iterate>
		</logic:present>

	     </tbody>
	   </table>
	   <jsp:include page="/jsp/includes/columnchooser/columnchooser.jsp"/>
</div>
</div>
</div>
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>