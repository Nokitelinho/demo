<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MaintainCCA.jsp
* Date					:  14-June-2006,14-July-08,29-AUG-2008
* Author(s)				:  A-2391,A-3447,A-3227
*************************************************************************/
 --%>

 

 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 
 
 <ihtml:form action="/mailtracking.mra.defaults.maintaincca.screenload.do">
 
 <bean:define id="form"
		 name="mraMaintainCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"
		 toScope="page" />


	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.maintaincca"
	 	  method="get"
	  attribute="OneTimeVOs" />

	<business:sessionBean id="ccaFilterVO"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="maintainCCAFilterVO" />

	<business:sessionBean id="cCAdetailsVO"
	   	moduleName="mailtracking.mra.gpabilling"
	   	screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="cCAdetailsVO" />
 
	<ihtml:hidden property="comboFlag"/>
	<ihtml:hidden property="showpopUP"/>
	<ihtml:hidden property="selectedRow"/>
	<ihtml:hidden property="popupon"/>
	<ihtml:hidden property="count"/>
	<ihtml:hidden property="closeFlag"/>
	<input type="hidden" name="currentDialogOption" />
	<input type="hidden" name="currentDialogId" />
	<ihtml:hidden property="dsnPopupFlag" />
	<ihtml:hidden property="createCCAFlg" />
	<ihtml:hidden property="fromScreen" />
	<ihtml:hidden property="usrCCANumFlg" /> 
 <ihtml:hidden  property="revisedChargeGrossWeignt"  />
 
 	<div id="_gpanameDiv">
			
			<ihtml:text property="revGpaName" readonly="true" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVGPANAME" />																			    
	</div>	

  </ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>