
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	

<ihtml:form action="/mailtracking.defaults.containerauditscreenload.do">

  <ihtml:hidden property="screenStatusFlag"/>
	
  <div id="_filter">
  
  	<div class="ic-section" style="height:80%;">
		<div class="ic-border" style="height:100%;">
			<div class="ic-col-25 ic-input">
				<label>
					<common:message key="mailtracking.defaults.containeraudit.lbl.flightnumber" />
				</label>
				<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_FLIGHTNUMBER"/>
			</div>
			<div class="ic-col-25 ic-input">
				<label>
					<common:message key="mailtracking.defaults.containeraudit.lbl.flightdate" />
				</label>
				<ibusiness:calendar property="flightDate" id="flightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_FLIGHTDATE"/>
			</div>
			<div class="ic-col-25 ic-input">
				<label>
					 <common:message key="mailtracking.defaults.containeraudit.lbl.asgport" />
				</label>
				<ihtml:text property="assignPort" maxlength="4" componentID="CMP_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_ASGPORT"/>
				<div class="lovImg">
				<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.assignPort.value,'Airport','0','assignPort','',0)"></div>
			</div>
			<div class="ic-col-25 ic-input">
				<label>
					<common:message key="mailtracking.defaults.containeraudit.lbl.containernumber" />
				</label>
				<ihtml:text property="containerNumber"  maxlength="13" value="" componentID="TXT_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_CONTAINERNUMBER"  style="width:110px"/>
			</div>

		</div>
	</div>
		

		<div class="ic-button-container paddR5">
			<ihtml:nbutton accesskey = "l" property="btList" componentID="BTN_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_LIST">
				<common:message key="mailtracking.defaults.containeraudit.btn.list" />
			</ihtml:nbutton>

			<ihtml:nbutton accesskey = "c" property="btClear" componentID="BTN_MAILTRACKING_DEFAULTS_CONTAINERAUDIT_CLEAR" >
				<common:message key="mailtracking.defaults.containeraudit.btn.clear" />
			</ihtml:nbutton>
		</div>

		    
  </div>
  
</ihtml:form>
	
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
