<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MailPerformance.jsp
* Date					:  26-MAR-2018
* Author(s)				:  A-8251
*************************************************************************/
 --%>
 
 
 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
		
	 <html:html>

 <head>
		
 	<title><common:message bundle="mailPerformanceResources" key="mailtracking.defaults.mailperformance.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/MailPerformance_Script.jsp"/>
 </head>

 <body class="ic-center" style="width:65%;">
	

	<bean:define id="form"
		 name="MailPerformanceForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm"
		 toScope="page" />

	
	<div id="mainDiv"  class="iCargoContent">

		<ihtml:form action="/mailtracking.defaults.mailperformance.screenload.do">

			<ihtml:hidden property="screenFlag" />
			<jsp:include page="/jsp/includes/tab_support.jsp" />

			<div class="ic-content-main" >
				
				<div class="ic-main-container">
				<div class="ic-row ic-inline-label marginT5" style="padding: 10px;">
										<div class="ic-input ic-split-20">
											<input type="radio" id="ssRadioBtn" tabindex="17" name="searchRadioButton"  value="SS" onclick="togglepanel('pane1','pane2','pane3','pane4','pane5')" />
											
												Service Standards
										</div>
										<div class="ic-input ic-split-20">
											<input type="radio" id="htRadioBtn" tabindex="17" name="searchRadioButton"  value="HT" onclick="togglepanel('pane2','pane4','pane3','pane1','pane5')"/>
											
												Handover Time
										</div>
										<div class="ic-input ic-split-20">
											<input type="radio" id="roRadioBtn" tabindex="17" name="searchRadioButton"  value="RO" onclick="togglepanel('pane3','pane2','pane4','pane1','pane5')" />
											
												RDT Offset
										</div>
										<div class="ic-input ic-split-20">
											<input type="radio" id="ctRadioBtn" tabindex="17" name="searchRadioButton"   value="CT" onclick="togglepanel('pane4','pane2','pane3','pane1','pane5')" />											
												<common:message key="mailtracking.defaults.mailperformance.lbl.details" />
										</div>
										<div class="ic-input ic-split-20">
											<input type="radio" id="pcRadioBtn" tabindex="17" name="searchRadioButton"  value="PC" onclick="togglepanel('pane5','pane2','pane3','pane1','pane4')"/> 											
												Postal Calender
										</div>
									</div>
				
									<div id="pane1" ></div>
									<div id="pane2" ></div>
									<div id="pane3"></div>
									<div id="pane4">
										<jsp:include page="CoTerminus.jsp" />
									</div>
									<div id="pane5" ></div>
		</div>
	</div>		
	</ihtml:form>
	
</div>
	</body>
</html:html>

