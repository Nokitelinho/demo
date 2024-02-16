<%--
* Project	 		: iCargo
* Module Code & Name		: cra.flown
* File Name			: FlownReport.jsp
* Date				: 13/12/2006
* Author(s)			: A-2521

--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.GenerateOutwardBillingInvoiceForm" %>


	
	
<html:html>
<head>
	
	

	
	<title>
		<common:message  key="mailtracking.mra.airlinebilling.generateinvoice.title" bundle="generateoutwardbillinginvoice" scope="page"/>
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/airlinebilling/outward/GenerateOutwardBillingInvoice_Script.jsp"/>
</head>

<body class="ic-center" style="width:70%;">
	
	
	
		
	<ihtml:form action="/mailtracking.mra.airlinebilling.outward.generateinvoice.screenload.do">
	<div class="iCargoContent" style="overflow:hidden;">
			<ihtml:hidden  property="hasGenerated"/>
			<ihtml:hidden  property="ownAirline"/>
			<business:sessionBean id="clearanceHouses"
			moduleName="mailtracking.mra.airlinebilling"
			screenID="mailtracking.mra.airlinebilling.outward.generateinvoice"
			method="get"
			attribute="ClearingHouses" />

		<!-- outside bordered table starts -->
		<div class="ic-content-main" style="border:0px;>
			<span class="ic-page-title"><common:message key="mailtracking.mra.airlinebilling.generateinvoice.heading" /></span>
			<div class="ic-head-container">
				<div class="ic-filter-panel marginT5">
					<div class="ic-sectionic-pad-2">
					<div class="ic-input-container ic-label-35">
						<div class="ic-row">
							<div class="ic-input ic-split-33">
								<label>
									<common:message key="mailtracking.mra.airlinebilling.generateinvoice.lbl.clrhouse" />
								</label>
									<ihtml:select  property="clearingHouse" componentID="CMP_MRA_ARLBLG_GENINV_CLRHOU">
										<html:option value=""><common:message key="combo.select"/></html:option>
										<logic:present name="clearanceHouses">
											<logic:iterate name="clearanceHouses" id="clrHouse">
												<logic:present name="clrHouse"  property="fieldDescription">
													<bean:define id="clrDesc" name="clrHouse"  property="fieldDescription"/>

													<logic:present name="clrHouse"  property="fieldValue">
														<bean:define id="clrVal" name="clrHouse"  property="fieldValue"/>
														<html:option value="<%=(String)clrVal%>">
															<bean:write name="clrDesc" />
														</html:option>
													</logic:present>
												</logic:present>
											</logic:iterate>
										</logic:present>

									</ihtml:select>
							</div>
							<div class="ic-input ic-split-33">
								<label>
									<common:message key="mailtracking.mra.airlinebilling.generateinvoice.lbl.clrprd" />
								</label>
								<ihtml:text property="clearancePeriod" componentID="CMP_MRA_ARLBLG_GENINV_CLRPRD" maxlength="10"/>
								<div class="lovImg">
								<img id="clrprdLov" src="<%=request.getContextPath()%>/images/lov.png" alt="" />
							    </div>
							</div>
							<div class="ic-input ic-split-33">
								<label>
									 <common:message key="mailtracking.mra.airlinebilling.generateinvoice.lbl.airlinecode"/>
								</label>
								<ihtml:text property="airlineCode" componentID="CMP_MRA_ARLBLG_GENINV_ARLCODE" maxlength="3" />
								<div class="lovImg">
								<img id="airlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png" alt="" />
							    </div>
							</div>
						</div>
					
				
			
			<div class="ic-row">
				<div class="ic-button-container">
					<ihtml:nbutton property="btnGenInv" componentID="CMP_MRA_ARLBLG_GENINV_GNINV" accesskey="G">
						<common:message key="mailtracking.mra.airlinebilling.generateinvoice.lbl.generateinvoice" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_ARLBLG_GENINV_CLOSE" accesskey="O">
						<common:message key="mailtracking.mra.airlinebilling.generateinvoice.lbl.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
			</div>
			</div>
			</div>
		</div>

		
	</div>	
		</ihtml:form>
		
		


	</body>
	</html:html>
