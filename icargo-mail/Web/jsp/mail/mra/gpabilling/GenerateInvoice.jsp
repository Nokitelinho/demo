<%--
* Project	 		: iCargo
* Module Code & Name		: MRA 
* File Name			: GenerateInvoice.jsp
* Date				: 
* Author(s)			: a-3108(added billing period)

--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GenerateGPABillingInvoiceForm" %>
<bean:define id="form"
		 name="GenerateGPABillingInvoiceForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GenerateGPABillingInvoiceForm"
		 toScope="page" />




		
	
<html:html>
	<head>
	
	
		<title>

		<common:message  key="mailtracking.mra.gpabilling.generateinvoice.title" bundle="geninvresources" scope="page"/>

		</title>
		<meta name="decorator" content="mainpanelrestyledui">

		<common:include type="script" src="/js/mail/mra/gpabilling/GenerateInvoice_Script.jsp"/>

	</head>

	<body class="ic-center" style="width:90%;">
	
	<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.generateinvoice"
	method="get" attribute="oneTimeVOs" />
	
	
	
		<div class="iCargoContent ic-masterbg" style="overflow:hidden">
		<ihtml:form action="/mailtracking.mra.gpabilling.generateinvoice.onScreenLoad.do" >

 	<html:hidden property="screenSuccessFlag"/>
 	<html:hidden property="screenStatusFlag"/>
	<ihtml:hidden property="turkishSpecificFlag" value="N" />
	<common:xgroup>
		<common:xsubgroup id="TURKISH_SPECIFIC">
			 <%form.setTurkishSpecificFlag("Y");%>
		</common:xsubgroup>
	</common:xgroup >
  	<div class="ic-content-main bg-white">
			<span class="ic-page-title ic-display-none">

				<common:message key="mailtracking.mra.gpabilling.generateinvoice.heading" />
			</span>
		<div class="ic-head-container">	
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row">
						<div class="ic-col-35 ic-center">
						<div>
						&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<div class="ic-input ic-split-50 ic-label-42">
						    <label>
								<common:message key="mailtracking.mra.gpabilling.generateinvoice.invoicetype" />
							</label>
							<ihtml:select componentID="CMP_MRA_GPABILLING_GENERATEINV_INVTYPE" property="invoiceType" id="invoiceType">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="KEY_ONETIMES">
									<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.invoiceType">
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
							</ihtml:select>
						</div>
						<div class="ic-input ic-split-50 ic-label-51">
							
								<label>
					    <common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.gpacode" />
							</label>
					        <ihtml:text property="gpacode" componentID="CMP_MRA_GPABLG_GENINV_GPACODE"
					        	    tabindex="1" maxlength="5" />
							<div class="lovImg">		
					        <img id="gpacodelov"
					             src="<%=request.getContextPath()%>/images/lov.png"
					             width="22" height="22" tabindex="2" alt="" />
					        </div>
						</div>
						</div>
						<div class="ic-col-45 ic-center">		
						<fieldset class="ic-field-set" >
						<legend  class="iCargoLegend">Billing Period</legend>
							<div class="ic-input ic-split-50 ic-label-50 ">
								<label>
						<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.billingperiodFrom" scope="request"/>
								</label>

						<ibusiness:calendar
						property="blgPeriodFrom"
						componentID="CMP_MRA_GPABLG_GENINV_BLGPRDFRM"
						type="image"
						id="blgPeriodFrom"
						tabindex="3"

						value="<%=form.getBlgPeriodFrom()%>"/>
							</div>
							<div class="ic-input ic-split-50 ic-label-50">
								<label>





						<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.billingperiodTo" scope="request"/>
								</label>



						<td width="36%" >
						<ibusiness:calendar
						property="blgPeriodTo"
						componentID="CMP_MRA_GPABLG_GENINV_BLGPRDTO"
						type="image"
						id="blgPeriodTo"
						tabindex="4"

						value="<%=form.getBlgPeriodTo()%>"/>
							</div>



						</fieldset>
						
						</div>
						<div class="ic-col-20 ic-center">
						<div>
						&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<div class="ic-input">
						   <label>
					

				            <common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.country" />
						   </label>

					        <ihtml:text property="country" componentID="CMP_MRA_GPABLG_GENINV_GPACTY" tabindex="6" />
							<div class="lovImg">
					        <img id="countrylov"
								 src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" tabindex="7" alt="" />
							</div>	 
						</div>
					
					
						<div class="ic-input inline_filedset marginT20">
								<ihtml:checkbox name="form" property="addNew" componentID="CMP_MRA_GPABLG_GENINV_ADDNEW"/>
								<label>			
									<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.addnew" />
								</label>
						</div>
						</div>
					</div>
						</div>
						<div class="ic-row">
							<div class="ic-button-container">

					<!--	<logic:equal name="form" property="turkishSpecificFlag" value = "Y">

					

					

					



							<ihtml:nbutton property="btnGenInvTK" tabindex="19" componentID="CMP_MRA_GPABLG_GENINV_GNINV" >
								<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.generateinvoice" />
							</ihtml:nbutton>
						</logic:equal> -->
						
						<ihtml:nbutton property="btnGenInv" tabindex="19" componentID="CMP_MRA_GPABLG_GENINV_GNINV" >
							<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.generateinvoice" />
						</ihtml:nbutton>
						
						<ihtml:nbutton property="btnClose" tabindex="19" componentID="CMP_MRA_GPABLG_GENINV_CLOSE" >
							<common:message key="mailtracking.mra.gpabilling.generateinvoice.lbl.close" />
						</ihtml:nbutton>
							</div>
						</div>
					</div>
		</div>	
	</div>


		</ihtml:form>
		</div>
 	<span style="display:none" id="tmpSpan"></span>

	
	</body>
	</html:html>
