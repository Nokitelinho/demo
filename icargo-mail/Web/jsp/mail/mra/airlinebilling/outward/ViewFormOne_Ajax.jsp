<%--/***********************************************************************
* Project	     	  : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ViewFormOne_Ajax.jsp
* Date                 	 : 22-Sep-2008
* Author(s)              : A-3447
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
     	name="ViewForm1Form"
     	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm"
  	toScope="page" />

  <ihtml:form action="/mailtracking.mra.airlinebilling.outward.ajaxscreenloadviewform1.do">
  <table width="100%" border="0" class="iCargoBorderedTable">
       	      		  		<tr>
       					         <td colspan="2"  class="iCargoLabelRightAligned">
       							 	<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.clearanceperiod" />
       							 </td>
       							 <td ><ihtml:text property="clearancePeriod"
								  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_CLEARANCEPERIOD"
								  maxlength="10"/>
								 <img src="<%=request.getContextPath()%>/images/lov.gif" id="clearancePeriodLOV" height="16" width="16" /></td >


       							 <td colspan="2"  class="iCargoLabelRightAligned">
       							  	<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.airlinecode" />
       							  </td>
							     <td ><ihtml:text property="airlineCode"
								  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_AIRLINECODE"
								  maxlength="2"/>
								<img src="<%=request.getContextPath()%>/images/lov.gif" id="airlineCodeLov" height="16" width="16" /></td>


       							 <td colspan="2" class="iCargoLabelRightAligned">
       							 	<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.airlinenumber" />
       							 </td>
       							  <td ><ihtml:text property="airlineNumber"
								  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_AIRLINENO"
								  maxlength="3"/>
								  <img src="<%=request.getContextPath()%>/images/lov.gif" id="airlineNumberLov" height="16" width="16"  /></td>


							</tr>


							<tr>
       							<td  colspan="25" class="iCargoLabelRightAligned">

       							 <div align="right" >
								 <ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_LIST" >
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.list" />
								</ihtml:nbutton>

		       						  <ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_CLEAR" >
										<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.clear" />
								  </ihtml:nbutton>

       							  </div>
       						     </td>
       	      			     </tr>

       	      		   </table>
       	      		   
       	      		   
       	      		   
	 </ihtml:form >
			     	
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>