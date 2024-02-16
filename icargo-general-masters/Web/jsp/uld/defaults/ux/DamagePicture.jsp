<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  RotationalReferenceNoLOV.jsp
* Date					:
* Author(s)				:  A-2135
*************************************************************************/
 --%>

<%@page import ="java.util.ArrayList"%>
<%@page import ="java.util.Date"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>


	
<html:html>
<head> 
		<!-- 	</%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 		
		<jsp:include page="/jsp/includes/includes.jsp">		
			<jsp:param name="pageInfo" value="</%= ((Servlet)page).getServletInfo()%>"/>
		</jsp:include>	  -->	
	
		
			
	
<title>
<common:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.damagePicTitle" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/ux/DamagePicture_Script.jsp"/>
</head>

<body id="bodyStyle">
	
<bean:define id="form"
		 name="maintainDamageReportRevampForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
		 toScope="page" />

<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.ux.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<div class="iCargoPopUpContent" style="height:470px;">
<ihtml:form action="/uld.defaults.ux.viewPicScreenLoadCommand.do" styleClass="ic-main-form">
  <div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
					 <common:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.damagePicTitle" />
				</span>			
		<div class="ic-main-container">		
		  <div class="ic-row">
           <div id="div1" class="tableContainer" style="height:395px;width:100%;">
           <table class="fixed-header-table">
		   <tbody>
	        <tr>
			 <td width="100%">
                <img src="<%=request.getContextPath()%>/uld.defaults.ux.showimage.img" alt="Picture LOV"/>
			 </td>
	       </tr>
		   </tbody>
          </table>
        </div>
       </div>
	  </div>
      <div class="ic-foot-container">						
	    <div class="ic-row">
	     <div class="ic-button-container">	
          <ihtml:button property="btnPicClose" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_PICCLOSE">
	    	<bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindmgrep.btn.picclose" />
	      </ihtml:button>
         </div>
		</div>
	</div>

        
</div>
</ihtml:form>
</div>
			
		   
				
		   <!--   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>
		   <logic:present name="icargo.uilayout">
		       <logic:equal name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
		       </logic:equal>

		       <logic:notEqual name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new.jsp" />
		       </logic:notEqual>
		   </logic:present>
		   <common:registerCharts/>
		   <common:registerEvent />	   -->
	</body>
</html:html>

