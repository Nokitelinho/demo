
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	

<ihtml:form action="/mailtracking.defaults.assignflightauditscreenload.do">

 <ihtml:hidden property="screenStatusFlag"/>
	
  <div id="_filter">
  
  	<div class="ic-section" style="height:80%;">
		<div class="ic-input-round-border" style="height:100%;">
			<div class="ic-col-35">
				<label>
					<common:message key="mailtracking.defaults.assignflightaudit.lbl.flightnumber" />
				</label>
				<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" tabindex="1" componentID="CMP_MAILTRACKING_DEFAULTS_ASSIGNFLIGHTAUDIT_FLIGHTNUMBER"/>
			</div>
			<div class="ic-col-35">
				<label>
					<common:message key="mailtracking.defaults.assignflightaudit.lbl.flightdate" />
				</label>
				<ibusiness:calendar property="flightDate" id="flightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_ASSIGNFLIGHTAUDIT_FLIGHTDATE"/>
			</div>
			<!--<div class="ic-col-30">
				<label>
					<common:message key="mailtracking.defaults.assignflightaudit.lbl.asgport" />
				</label>
				<ihtml:text property="assignPort" maxlength="4" componentID="CMP_MAILTRACKING_DEFAULTS_ASSIGNFLIGHTAUDIT_ASGPORT"/>
				<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.assignPort.value,'Airport','0','assignPort','',0)">
			</div>-->

		</div>
	</div>

		
	<div class="ic-button-container">
			<ihtml:nbutton property="btList" componentID="BTN_MAILTRACKING_DEFAULTS_ASSIGNFLIGHTAUDIT_LIST" accesskey="l">
				<common:message key="mailtracking.defaults.assignflightaudit.btn.list" />
			</ihtml:nbutton>

			<ihtml:nbutton property="btClear" componentID="BTN_MAILTRACKING_DEFAULTS_ASSIGNFLIGHTAUDIT_CLEAR" accesskey="c">
				<common:message key="mailtracking.defaults.assignflightaudit.btn.clear" />
			</ihtml:nbutton>
	</div>
	  
  </div>
  
</ihtml:form>
	
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
