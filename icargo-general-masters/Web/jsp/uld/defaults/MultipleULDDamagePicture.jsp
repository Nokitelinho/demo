<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  MultipleULDDamagePicture.jsp
* Date					:
* Author(s)				:  A-8176
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
<common:message bundle="updatemultipledamageuldresources" key="uld.defaults.updatemultipleuld.lbl.damagePicTitle"/>
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/MultipleULDDamagePicture_Script.jsp"/>
</head>

<body id="bodyStyle">
	
<bean:define id="form"
		 name="UpdateMultipleULDDetailsForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"
		 toScope="page" />

<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<div class="iCargoPopUpContent" style="height:470px;">
<ihtml:form action="/uld.defaults.viewPicScreenLoadCommand.do" styleClass="ic-main-form">
  <div class="ic-content-main">    
			
		<div class="ic-main-container">		
		  <div class="ic-row">
           <div id="div1" class="tableContainer" style="height:395px;width:100%;">
           <table class="fixed-header-table">
		   <tbody>
	        <tr>
			 <td width="100%">
                <img src="<%=request.getContextPath()%>/uld.defaults.misc.showimage.img" alt="Picture LOV"/>
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
	    	<common:message  key="uld.defaults.updatemultipleuld.btn.picclose" />
	      </ihtml:button>
         </div>
		</div>
	</div>

        
</div>
</ihtml:form>
</div>
			
</body>
</html:html>

