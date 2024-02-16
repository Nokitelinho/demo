
<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: AssignContainer_Containers.jsp
* Date				: 30-Aug-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ihtml:form action="/mailtracking.defaults.assigncontainer.screenloadAssignContainer.do" >

<bean:define id="form"
	name="AssignContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm"
    toScope="page"
    scope="request"/>

<business:sessionBean id="containerVOs"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.assignContainer"
					  method="get"
					  attribute="containerVOs" />

<ihtml:hidden property="status" />
<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="flightStatus" />
<ihtml:hidden  property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="fromScreen" />

<div id="_data">
<table class="fixed-header-table">
  <thead>
    <tr class="iCargoTableHeadingLeft">
	<td width="3%"><div class="ic-center"><input type="checkbox" name="checkAll" value="checkbox"></div></td>
	<td width="8%"><common:message key="mailtracking.defaults.assigncontainer.lbl.contNo" /></td>
	<td width="7%"><common:message key="mailtracking.defaults.assigncontainer.lbl.pou" /></td>
	<td width="10%"><common:message key="mailtracking.defaults.assigncontainer.lbl.assignedOn" /></td>
	<td width="10%"><common:message key="mailtracking.defaults.assigncontainer.lbl.assignedBy" /></td>
	<td width="14%"><common:message key="mailtracking.defaults.assigncontainer.lbl.onwardFlights" /></td>
	<td width="11%"><common:message key="mailtracking.defaults.assigncontainer.lbl.noOfBags" /></td>
	<td width="9%"><common:message key="mailtracking.defaults.assigncontainer.lbl.wt" /></td>
	<td width="17%"><common:message key="mailtracking.defaults.assigncontainer.lbl.remarks" /></td>
    </tr>
  </thead>
  <tbody>

	<logic:present name="containerVOs">
	<bean:define id="containervos" name="containerVOs" toScope="page"/>

	<% Collection<String> selectedrows = new ArrayList<String>(); %>

	 <logic:present name="form" property="subCheck" >

		<%
		String[] selectedRows = form.getSubCheck();
		for (int j = 0; j < selectedRows.length; j++) {
			selectedrows.add(selectedRows[j]);
		}
		%>

	 </logic:present>

	<logic:iterate id="containervo" name="containervos" indexId="rowid">

	  <logic:present name="containervo" property="operationFlag">
		<bean:define id="operFlag" name="containervo" property="operationFlag" toScope="page"/>

		<logic:notEqual name="containervo" property="operationFlag" value="D">

		<tr>
		  <td>
			
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

			
		  </td>
		  <td><bean:write name="containervo" property="containerNumber"/></td>
		  <td><bean:write name="containervo" property="pou"/></td>
		  <td>
			  <logic:present name="containervo" property="assignedDate">
			  <bean:define id="assignedDate" name="containervo" property="assignedDate" toScope="page"/>
			  <%= assignedDate.toString().substring(0,11) %>
			  </logic:present>
			  <logic:notPresent name="containervo" property="assignedDate">
				&nbsp;
			  </logic:notPresent>
		  </td>
		  <td><bean:write name="containervo" property="assignedUser"/></td>
		  <td><bean:write name="containervo" property="onwardFlights"/></td>
		  <td><bean:write name="containervo" property="bags"/></td>
		  <td>  <common:write name="containervo" property="weight" unitFormatting="true" />
		 </td>
		  <td><bean:write name="containervo" property="remarks"/></td>
		</tr>

		</logic:notEqual>

	  </logic:present>
	  <logic:notPresent name="containervo" property="operationFlag">

		<tr>
		  <td>
			
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

		  </td>
		  <td><bean:write name="containervo" property="containerNumber"/></td>
		  <td><bean:write name="containervo" property="pou"/></td>
		  <td>
			  <logic:present name="containervo" property="assignedDate">
			  <bean:define id="assignedDate" name="containervo" property="assignedDate" toScope="page"/>
			  <%= assignedDate.toString().substring(0,11) %>
			  </logic:present>
			  <logic:notPresent name="containervo" property="assignedDate">
				&nbsp;
			  </logic:notPresent>
		  </td>
		  <td><bean:write name="containervo" property="assignedUser"/></td>
		  <td><bean:write name="containervo" property="onwardFlights"/></td>
		  <td><bean:write name="containervo" property="bags"/></td>
		  <td>  <common:write name="containervo" property="weight" unitFormatting="true" />
		  </td>
		  <td><bean:write name="containervo" property="remarks"/></td>
		</tr>

	  </logic:notPresent>

		</logic:iterate>
	</logic:present>

  </tbody>
</table>
</div>

</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>