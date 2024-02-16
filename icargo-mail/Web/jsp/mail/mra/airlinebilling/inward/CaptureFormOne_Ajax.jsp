<%--
* Project	 		: iCargo
* Module Code & Name		: mra-airlinebilling
* File Name			: CaptureFormOne.jsp
* Date				: 10-Sep-2008
* Author(s)			: A-3447
--%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm" %>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	

<bean:define id="form" name="CaptureMailFormOneForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm"
	toScope="page" />	

<business:sessionBean id="onetimemap"
  moduleName="mailtracking.mra.defaults"
 screenID="mailtracking.mra.airlinebilling.inward.captureformone"
  method="get"  attribute="OneTimeMap" />  
	     	
	
<ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureformone.ajaxonScreenLoad.do">
	
	
	 <div class="ic-head-container">
		   <div class="ic-filter-panel" id="mainTable">
		   <div class="ic-input-container">
		   <div class="ic-row ">
		   
				
		
						
							<div class="ic-input ic-mandatory ic-split-35 ">
						
					   <label> 	<common:message   key="mra.inwardbilling.captureformone.clearanceperiod" scope="request"/></label>
					
			<ihtml:text property="clearancePeriod" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEARANCEPERIOD"  maxlength="10" />
								<img name="clearancePeriodlov" id="clearancePeriodlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
			
						
					
					</div>
				<div class="ic-input ic-mandatory ic-split-20 ">
						
					   <label> <common:message   key="mra.inwardbilling.captureformone.airlinecode" scope="request"/></label>
					
				<ihtml:text property="airlineCode" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINECODE"  maxlength="2" value="<%=(String)form.getAirlineCode()%>"/>
					  <img name="airlinecodelov" id="airlinelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
				
				</div>
					<div class="ic-input  ic-split-20">
						
					   <label>
						<common:message   key="mra.inwardbilling.captureformone.airlineno" scope="request"/>
					</label>
				
					<ihtml:text property="airlineNo" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINENO"  maxlength="4" value="<%=(String)form.getAirlineNo()%>"/>
								<img name="airlinenumberlov" id="airlinelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
					
					</div>
								<div class="ic-input  ic-split-25 ">
										
					   <label>
					  		<common:message   key="mra.inwardbilling.captureformone.invoicestatus" scope="request"/>
					</label>
			
				<ihtml:select property="invoiceStatus"
								componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICESTATUS" style="width:100px"
								tabindex="4">
								<% System.out.println("*******-------->>no values");%>
								<ihtml:option value=""></ihtml:option>
								<logic:present name="onetimemap">
								<% System.out.println("-------->>no values");%>
								<logic:iterate id="oneTimeValue" name="onetimemap">
								<% System.out.println("*******-------->>no values");%>
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
								<logic:equal name="parameterCode" value="mra.airlinebilling.inward.captureformone.invoicestatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
								</logic:present>
								</logic:iterate>
								</logic:equal>
								</logic:iterate>
								</logic:present>
								<logic:notPresent name="OneTimeValues">
								<% System.out.println("no values");%>
								</logic:notPresent>
		
							</ihtml:select>			
			</div>
			
					
				
			
				 </div>
					<div class="ic-row ">
							<div class="ic-button-container" >
							<ihtml:nbutton property="btList" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LIST">
								  <common:message   key="mra.inwardbilling.captureformone.button.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEAR">
								  <common:message   key="mra.inwardbilling.captureformone.button.clear" />
								</ihtml:nbutton>
					</div>
				
					
		    </div>	        
	      
</div>	
</div>	

	 
	</div>		
	
				</ihtml:form>

	
	 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>