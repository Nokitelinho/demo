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
		
			
	
<title>
<common:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.damagePicTitle" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/DamagePicture_Script.jsp"/>
</head>

<body id="bodyStyle">
<bean:define id="form"
		 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
		 toScope="page" />

<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<div class="iCargoPopUpContent" style="height:470px;">
<ihtml:form action="/uld.defaults.viewPicScreenLoadCommand.do" styleClass="ic-main-form">
  <div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
					 <common:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.damagePicTitle" />
				</span>			
		<div class="ic-main-container">		
		  <div class="ic-row">
           <div id="div1" class="tableContainer" style="height:395px;width:100%;">
           <table class="fixed-header-table">
		   <tbody>
	        <tr>
			 <td width="100%">
			      <% String source = new StringBuilder().append("uld.defaults.showimage.img").toString();%> 
                 <common:resourceURL  src="<%=source%>" id="imageURL"/> 
                  <bean:define id="imgURL" name="imageURL"/>
                <img src="<%=imgURL%>" alt="Picture LOV"/>
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
	    	<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.picclose" />
	      </ihtml:button>
         </div>
		</div>
	</div>

        
</div>
</ihtml:form>
</div>
			
		   
	</body>
</html:html>

