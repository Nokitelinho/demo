<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  SearchContainer_Temp.jsp
* Date          	 :  31-August-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ihtml:form action="mailtracking.defaults.searchcontainer.screenloadsearchcontainer.do">

<bean:define id="form"
	name="SearchContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm"
    toScope="page" scope="request"/>

<business:sessionBean
		id="oneTimeValues"
		moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer"
		method="get"
		attribute="oneTimeValues" />

<business:sessionBean id="containerVOcollection" moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer" method="get" attribute="listContainerVOs" />
	<logic:present name="containerVOcollection">
		<bean:define id="containerVOcollection" name="containerVOcollection" toScope="page"/>
	</logic:present>
<business:sessionBean id="searchContainerFilterVO" moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer" method="get" attribute="searchContainerFilterVO" />
	<logic:present name="searchContainerFilterVO">
		<bean:define id="searchContainerFilterVO" name="searchContainerFilterVO" toScope="page"/>
	</logic:present>


<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="reassignFlag" />
<ihtml:hidden property="status" />
<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="warningFlag" />

<div id="_searchContainer">

      <table class="fixed-header-table" id="searchContainerTemp">
           <thead>
		              <tr class="iCargoTableHeadingLeft">
		  		<td width="2%"><input type="checkbox" name="masterContainer" onclick="updateHeaderCheckBox(this.form,this,this.form.selectContainer);"/><span></span></td>
						<td width="2%"><span>&nbsp;</span></td>
						<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.containerno" /><span></span></td>
						<td width="7%"><common:message key="mailtracking.defaults.searchcontainer.lbl.fltcarr" /><span></span></td>
						<td width="10%"><common:message key="mailtracking.defaults.searchcontainer.lbl.flightdate" /><span></span></td>
						<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.currentport" /><span></span></td>
						<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.pol" /><span></span></td>
						<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.pou" /><span></span></td>
						<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.finaldest" /><span></span></td>
						<td width="9%"><common:message key="mailtracking.defaults.searchcontainer.lbl.assignedon" /><span></span></td>
						<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.assignedby" /><span></span></td>
						<td width="9%"><common:message key="mailtracking.defaults.searchcontainer.lbl.onwardroute" /><span></span></td>
						<td width="6%"><common:message key="mailtracking.defaults.searchcontainer.lbl.numbags" /><span></span></td>
						<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.wt" /><span></span></td>
						<common:xgroup>
						<common:xsubgroup id="SINGAPORE_SPECIFIC">
						<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.noofdays" /><span></span></td>
						<td width="7%"><common:message key="mailtracking.defaults.searchcontainer.lbl.subclassgroup" /><span></span></td>
						</common:xsubgroup>
						</common:xgroup >
						<td width="10%"><common:message key="mailtracking.defaults.searchcontainer.lbl.remarks" /><span></span></td>
		              </tr>
          </thead>
            <tbody>
            <logic:present name="containerVOcollection" >
            <% int i = 1;
               int count = 1;
            %>
  	  <bean:define id="containerVOsColl" name="containerVOcollection" scope="page" toScope="page"/>

  	    <% Collection<String> selectedrows = new ArrayList<String>(); %>

  		 <logic:present name="form" property="selectContainer" >

  			<%
  			String[] selectedRows = form.getSelectContainer();
  			for (int j = 0; j < selectedRows.length; j++) {
  				selectedrows.add(selectedRows[j]);
  			}
  			%>

  	    </logic:present>

	    <logic:iterate id="containerVO" name="containerVOsColl" indexId="rowCount">
	    <tr>

              <bean:define id="compcode" name="containerVO" property="companyCode" toScope="page"/>
  	    	<% String primaryKey=(String)compcode+(String.valueOf(count));%>

  	      	  <td class="iCargoTableDataTd ic-center">

  	      	  		<%
  						if(selectedrows.contains(primaryKey)){
  					%>

  						<input type="checkbox" name="selectContainer" value="<%=primaryKey%>" checked="true">
  					<%
  						}
  						else{
  					%>
  						<input type="checkbox" name="selectContainer" value="<%=primaryKey%>" />

  					<%
  						}
  					%>

	      	  </td>
			  <td>
				<bean:define id="uldtype" name="containerVO" property="type" />
				<html:hidden property="uldType"	value="<%=(String) uldtype%>" /> <!--added by A-8149 for ICRD-270524-->
				<logic:equal name="containerVO" property="type" value="U">
					<logic:present name="containerVO" property="offloadedInfo">
						<logic:notEqual name="containerVO" property="offloadCount" value="0">
						<img src="<%=request.getContextPath()%>/images/info.gif" width="16" height="16" 
								name="offload_link"  id="offload_link_<%=String.valueOf(rowCount)%>" />
						</logic:notEqual>
						<logic:equal name="containerVO" property="offloadCount" value="0">
						 &nbsp;
						</logic:equal>
					</logic:present>
				</logic:equal>
				<div style="display:none" id="offload_<%=String.valueOf(rowCount)%>" name="offloadDetails" tabindex="0">				
				<table class="fixed-header-table">
				<thead>
					<tr>
						<td class="iCargoTableDataRow2" colspan="2" style="text-align:left" ><b>
						<common:message key="mailtracking.defaults.searchcontainer.lbl.offloadinfo"/></b></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="iCargoTableDataRow2" style="text-align:left">
						<common:message key="mailtracking.defaults.searchcontainer.lbl.nooftimesoffloaded"/></td>
						<td style="text-align:left;height:5%">
						<logic:present name="containerVO" property="offloadCount">
						<bean:write name="containerVO" property="offloadCount"/>
						</logic:present></td>
					</tr>
					<tr>
					<td  class="iCargoTableHeaderLabel">
					<common:message key="mailtracking.defaults.searchcontainer.lbl.offloadflightinfo"/></td>
					<td class="iCargoTableDataTd" style="text-align:left;height:5%">
					<table>
					<tbody>
					<logic:present name="containerVO" property="offloadedInfo">
					 <bean:define id="offloadedInfoColl" name="containerVO"  property="offloadedInfo" scope="page" toScope="page"/>
					 <logic:iterate id="offloadedInfo" name="offloadedInfoColl" indexId="rowCount">				
					<tr>
					<td class="iCargoTableDataTd" style="text-align:left;height:5%">																		
							<bean:write name="offloadedInfo"/>							
					</td>
					</tr>
					 </logic:iterate>
					</logic:present>
					</tbody>
					</table>
					</td>
					</tr>
				</tbody>					
				</table>
				</div>
				</td>
              <td>			  
			  <bean:write name="containerVO" property="containerNumber"/></td>
	      <td><bean:write name="containerVO" property="carrierCode"/>
			<logic:notEqual name="containerVO" property="flightNumber" value="-1">
				<bean:write name="containerVO" property="flightNumber"/>
			</logic:notEqual>
	      </td>
	      <td>
		  <logic:present name="containerVO" property="flightDate">
			<bean:define id ="fltDate" name = "containerVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
				<%String fltDt=TimeConvertor.toStringFormat(fltDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
				<%=fltDt%>
		  </logic:present>
	      </td>
	      <td>
                  <logic:notEqual name="containerVO" property="arrivedStatus" value="Y">
                      <bean:write name="containerVO" property="assignedPort"/>
		  </logic:notEqual>
		  <logic:equal name="containerVO" property="arrivedStatus" value="Y">
		      <bean:write name="containerVO" property="pou"/>
		  </logic:equal>
	      </td>	   
			<td><bean:write name="containerVO" property="assignedPort"/></td>
              <td><bean:write name="containerVO" property="pou"/></td>

              <td><bean:write name="containerVO" property="finalDestination"/></td>
	      <td>
		  <logic:present name="containerVO" property="assignedDate">
			<bean:define id ="assignDate" name = "containerVO" property="assignedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
			<%String asgnDate=TimeConvertor.toStringFormat(assignDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
				<%=asgnDate%>
		  </logic:present>
	      </td>
              <td><bean:write name="containerVO" property="assignedUser"/></td>
              <td><bean:write name="containerVO" property="onwardRoute"/></td>
              <td><div align="right"><bean:write name="containerVO" property="bags" format="####"/></div></td>
	      	  <td><div align="right"> <common:write name="containerVO" property="weight" unitFormatting="true" />
			 </div></td>
			 <common:xgroup>
			 <common:xsubgroup id="SINGAPORE_SPECIFIC">
			  <td><div align="right"><bean:write name="containerVO" property="noOfDaysInCurrentLoc"/></div></td>
			  <td><bean:write name="containerVO" property="subclassGroup"/></td>
			  </common:xsubgroup>
			</common:xgroup >
              <td><bean:write name="containerVO" property="remarks"/></td>
            </tr>
            <%if(i == 1){
                 i = 2;
              }else{
                 i = 1;
              }
              count++;
            %>
            </logic:iterate>
           </logic:present>
          </tbody>
        </table>
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
