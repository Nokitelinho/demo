<%--/***********************************************************************
* Project	     	     : iCargo
* Module Code & Name 	 :mail
* File Name          	 :MailtagPrintPopup.jsp
* Date                 	 :05-Feb-2018
* Author(s)              :Lakshmi Sasidharan(A-7871)
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

		
	
<html:html>
<head> 			
<title>
			<common:message bundle="consignmentResources" key="mailtracking.defaults.consignment.title" scope="request"/>
		</title>
		<meta name="decorator" content="popuppanelrestyledui" />
    <common:include type="script" src="/js/mail/operations/MailtagPrintPopup_Script.jsp" />
</head>

<body>
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>


	<div class="iCargoPopUpContent" style="overflow-y:auto;height:100% ">


	<ihtml:form action="mailtracking.defaults.consignment.printmailtag.do" styleclass="ic-main-form">

	

		<div class="ic-head-container">
		<div class="ic-row ic-border" style="width:495px;">
		</div>
		</div>
		<div class="ic-main-container"  >								
		<div class="ic-border" >
		  <div class="ic-col-50 marginT5 paddL20" style="height:150px;">
		      <logic:notEqual name="ConsignmentForm" property="selectedIndexes" value = "ALL">
				<ihtml:radio property="printMailTag"  componentID="CMP_MAILTRACKING_DEFAULTS_SELECTEDMAILTAGCHECKBOX"  value="SELECTED" />
						<common:message key="mailtracking.defaults.consignment.lbl.selectedmailtag" />
							 </logic:notEqual> 
			<br><br>
			<ihtml:radio property="printMailTag" componentID="CMP_MAILTRACKING_DEFAULTS_ALLMAILTAGCHECKBOX" value="ALLMAILBAGS"/>
						<common:message key="mailtracking.defaults.consignment.lbl.allmailtag" />
		  </div>
		 
		 </div>
		
		</div>
		<div class="ic-foot-container">	
			<div  class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnPrint" componentID="BTN_MAILTRACKING_DEFAULTS_PRINTBUTTON" accesskey="P">
								<common:message key="mailtracking.defaults.consignment.btn.print" />
							</ihtml:nbutton>	
				</div>
			</div>
		</div>

	</ihtml:form>
</div>
	</body>
</html:html>
