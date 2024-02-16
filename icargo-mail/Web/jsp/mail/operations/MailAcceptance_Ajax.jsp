<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  MailAcceptance_Ajax.jsp
* Date          	 :  11-Feb-2008
* Author(s)     	 :  Paulson Ouseph A

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"%>
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

<%@ include file="/jsp/includes/customcss.jsp"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>



<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
   	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   	toScope="page" scope="request"/>

<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>

<business:sessionBean id="mailAcceptanceVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="mailAcceptanceVO" />
	<logic:present name="mailAcceptanceVOSession">
		<bean:define id="mailAcceptanceVOSession" name="mailAcceptanceVOSession" toScope="page"/>
	</logic:present>


<ihtml:form action="mailtracking.defaults.mailacceptance.screenloadmailacceptance.do">

<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="disableDestnFlag" />
<ihtml:hidden property="disableSaveFlag" />
<ihtml:hidden property="uldsSelectedFlag" />
<ihtml:hidden property="preAdviceFlag" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="closeflight" />
<ihtml:hidden property="closeFlag" />
<ihtml:hidden property="uldsPopupCloseFlag" />
<ihtml:hidden property="operationalStatus" />
<ihtml:hidden property="preassignFlag" />
<ihtml:hidden property="warningFlag" />
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="reassignScreenFlag" />
<ihtml:hidden property="selCont" />
<ihtml:hidden property="captureULDDamageFlag" />

 <div id= "_acceptance">
	      <div class="tableContainer" id="div1" style="height: 720px">
	      <table class="fixed-header-table">
		<thead>
		  <tr >
		    <td width="8%"  class="iCargoTableHeader"><input type="checkbox" name="masterContainer" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);"/></td>
		    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.uld" /></td>
		    <td width="6%"  class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.pou" /></td>
		    <td width="6%"  class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.destn" /></td>
		    <td width="16%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.onwardflights" /></td>
		    <td width="8%"  class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.numbags" /></td>
		    <td width="8%"  class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.wt" /></td>
		    <td width="8%"  class="iCargoTableHeader"> <common:message key="mailtracking.defaults.mailacceptance.lbl.warehouse" /></td>
		    <td width="8%"  class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.loc" /></td>
		    <td width="18%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.remarks" /></td>
		  </tr>
		</thead>
		<tbody>
		<% int i = 0;%>
		<logic:present name="mailAcceptanceVOSession" property="containerDetails">
		<bean:define id="containerDetailsVOsColl" name="mailAcceptanceVOSession" property="containerDetails" scope="page" toScope="page"/>

		 <% Collection<String> selectedrows = new ArrayList<String>(); %>
		 <logic:present name="MailAcceptanceForm" property="selectMail" >
		 <%
			String[] selectedRows = MailAcceptanceForm.getSelectMail();
			for (int j = 0; j < selectedRows.length; j++) {
				selectedrows.add(selectedRows[j]);
			}
		%>
		</logic:present>
		<logic:iterate id="containerDetailsVO" name="containerDetailsVOsColl" indexId="rowCount">
	        <% i++;%>
	        <!--Parent Rows -->
		  <tr id="container<%=i%>" class="ic-table-row-main">
		    <td class="iCargoTableDataTd" >
			  <a href="#" onClick="toggleRows(this);event.cancelBubble=true" class="ic-tree-table-expand tier1"></a>
			  <bean:define id="compcode" name="containerDetailsVO" property="companyCode" toScope="page"/>
			  <% String primaryKey=(String)compcode+(String.valueOf(i));%>
	                  <%
				if(selectedrows.contains(primaryKey)){
			  %>

				<input type="checkbox" name="selectMail" value="<%=primaryKey%>" checked="true">
			  <%
				}
				else{
			  %>
				<input type="checkbox" name="selectMail" value="<%=primaryKey%>" />

			  <%
				}
			  %>
		    </td>
		    <td class="iCargoTableDataTd" >
				<logic:present name="containerDetailsVO" property="paBuiltFlag">
					<logic:equal name="containerDetailsVO" property="paBuiltFlag" value="Y">
						<bean:write name="containerDetailsVO" property="containerNumber"/>
						<common:message key="mailtracking.defaults.mailacceptance.lbl.shipperBuild" />
					</logic:equal>
					<logic:equal name="containerDetailsVO" property="paBuiltFlag" value="N">				  			
						<bean:write name="containerDetailsVO" property="containerNumber"/>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="containerDetailsVO" property="paBuiltFlag">
					<bean:write name="containerDetailsVO" property="containerNumber"/>
				</logic:notPresent>
				
				<logic:present name="containerDetailsVO" property="containerNumber">
					<bean:define id="uld" name="containerDetailsVO" property="containerNumber"/> 
					<bean:define id="typ" name="containerDetailsVO" property="containerType"/>
					<html:hidden property="uldType"	value="<%=(String) typ%>" /> <!--added by A-8149 for ICRD-270524-->
					<% String uldnum = (String)typ +"-"+ (String)uld;%>
					<ihtml:hidden property="uldnos" value="<%=uldnum%>" />
				</logic:present>	
		    </td>
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="pou"/></td>
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="destination"/></td>
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="route"/></td>
		    <td class="iCargoTableDataTd" style="text-align:right"><bean:write name="containerDetailsVO" property="totalBags" format="####"/></td>
		    <td class="iCargoTableDataTd" style="text-align:right"><common:write name="containerDetailsVO" property="totalWeight" unitFormatting="true"/></td><!-- modified by A-7371-->
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="wareHouse"/></td>
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="location"/></td>
		    <td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="remarks" splitLength="25"/></td>
		  </tr>
		 <!--Child Rows -->
		  <tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
		    <td colspan="10"><div class="tier4"><a href="#" ></a></div>
		      <table style="width:100%;">
		       <tr>
			<td>
			   <table>
			      <thead>
				  <tr >
				    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.dsn" /></td>
				    <td width="12%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.origin" /></td>
				    <td width="12%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.destnoe" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.class" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.cat" /></td>
				    <td width="8%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.subclass" /></td>
				    <td width="11%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.year" /></td>
				    <td width="14%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.numbags" /></td>
				    <td width="15%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.wt" /></td>
				    <td width="10%" class="iCargoTableHeader"><common:message key="mailtracking.defaults.mailacceptance.lbl.plt" /></td>
				  </tr>
				</thead>
				<tbody>
				 <logic:present name="containerDetailsVO" property="dsnVOs">
				 <bean:define id="dsnVOsColl" name="containerDetailsVO" property="dsnVOs" scope="page" toScope="page"/>
				 <logic:iterate id="dsnVO" name="dsnVOsColl" indexId="rowCount">
				 <common:rowColorTag index="rowCount">
				  <tr  bgcolor="<%=color%>" >
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="dsn"/></td>
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="originExchangeOffice"/></td>
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="destinationExchangeOffice"/></td>
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="mailClass"/></td>
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="mailCategoryCode"/></td>
					<td class="iCargoTableDataTd" >

					 <% String subclassValue = ""; %>
					  <logic:notPresent name="dsnVO" property="mailSubclass">
						<bean:write name="dsnVO" property="mailSubclass"/>
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
					<td class="iCargoTableDataTd" ><bean:write name="dsnVO" property="year"/></td>
					<td class="iCargoTableDataTd" style="text-align:right"><bean:write name="dsnVO" property="bags" format="####"/></td>
					<td class="iCargoTableDataTd" style="text-align:right"><common:write name="dsnVO" property="weight" unitFormatting="true"/></td><!-- modified by A7371-->
					<td class="iCargoTableDataTd" >
						<div align="center">
							 <!--<logic:notPresent name="dsnVO" property="pltEnableFlag">
								<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>
							 </logic:notPresent>
							 <logic:present name="dsnVO" property="pltEnableFlag">
								<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
								       <input type="checkbox" name="isPrecarrAwb" value="true" checked disabled="true"/>
								</logic:equal>
								<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
								     <input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>
								</logic:equal>
							 </logic:present>-->
							 <logic:notPresent name="dsnVO" property="pltEnableFlag">
								<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
							 </logic:notPresent>
							 <logic:present name="dsnVO" property="pltEnableFlag">
								<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
									<img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
								</logic:equal>
								<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
									<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
								</logic:equal>
							 </logic:present>
						 </div>
				      </td>
				   </tr>
				   </common:rowColorTag>
				 </logic:iterate>
				 </logic:present>
				</tbody>
			   </table>
			 </td>
			</tr>
		       </table>
		      </td>
		     </tr>
	  	    </logic:iterate>
                    </logic:present>
                    <tr></tr>
                    <tbody>
                    </table>
	 	</div>
	      
 </div>    
   </ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
