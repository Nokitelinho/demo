<%--
* Project	 		: iCargo
* Module Code & Name: mailtracking.mra.airlinebilling
* File Name			: InwardBillingReport.jsp
* Date				: 14/03/2007
* Author(s)			: A-2521

--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.report.InwardBillingReportForm" %>

	
	
<html:html>
<head>
	
		
	
	<title>

	<common:message  key="mra.airlinebilling.inward.reports.title" bundle="inwardreport" scope="request"/>

	</title>
       <meta name="decorator" content="mainpanelrestyledui">

	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/InwardBillingReport_Script.jsp"/>

</head>



<body class="ic-center" style="width:64%;">
	
	
	
	


  <!--CONTENT STARTS-->
<%@include file="/jsp/includes/reports/printFrame.jsp" %>


<div class="iCargoContent" style="overflow:auto;">
  <ihtml:form action="/mailtracking.mra.airlinebilling.inward.reports.onScreenLoad.do">

	<ihtml:hidden property="billingType"/>

	<div class="ic-content-main" style="border:0px;>
			<span class="ic-page-title ic-display-none">
				<common:message key="mra.airlinebilling.inward.reports.pagetitle" scope="request"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel marginT5">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-50 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.inward.reports.reportID" scope="request"/>&nbsp;
								</label>
								<ihtml:select property="reportID" tabindex="1" componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTIDS"  style="width:250px">
									<html:option value="RPTMRA017"><common:message key="mra.airlinebilling.inward.reports.RPTMRA017" scope="request"/></html:option>
									<html:option value="RPTMRA018"><common:message key="mra.airlinebilling.inward.reports.RPTMRA018" scope="request"/></html:option>
									<html:option value="RPTMRA019"><common:message key="mra.airlinebilling.inward.reports.RPTMRA019" scope="request"/></html:option>
									<html:option value="RPTMRA020"><common:message key="mra.airlinebilling.inward.reports.RPTMRA020" scope="request"/></html:option>
								</ihtml:select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
			<div class="ic-section marginB5 ic-filter-panel">
				<div class="ic-row">
						<div class="ic-row">
						
								<div  class="ic-row" id="RPTMRA017" style="visibility:vissible; position:absolute;  width:100%;">
						<fieldset class="ic-field-set marginT5" style="width:98%";>
									<legend><common:message key="mra.airlinebilling.inward.reports.RPTMRA017" scope="request"/></legend>
							<div class="ic-row">&nbsp;</div>
							<div class="ic-row">
								<div class="ic-input ic-split-40 ic-mandatory">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA017.clrprd" scope="request"/>
									</label>
												<ihtml:text property="rpt017ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA017_CLRPRD" tabindex="4" maxlength="10"  />
									<div class="lovImg">
									<img id="rpt017ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
								</div>
								</div>
											<div class="ic-input ic-split-30 ">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA017.airlinecode" scope="request"/>
									</label>
									<ihtml:text property="rpt017AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA017_ARLCOD" tabindex="4" maxlength="3" style="text-transform : uppercase;" />
				                  	<div class="lovImg">
									<img id="rpt017AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
								</div>
								</div>
											<div class="ic-input ic-split-30 ">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA017.airlinenum" scope="request"/>
									</label>
									<ihtml:text property="rpt017AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA017_ARLNUM" tabindex="4" maxlength="4" style="text-transform : uppercase;" />
									<div class="lovImg">
									<img id="rpt017AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
								</div>
								</div>
							</div>
							<div class="ic-row">&nbsp;</div>
						</fieldset>
					</div>
								<div class="ic-row" id="RPTMRA018" style="visibility:hidden; position:absolute;  width:100%;">
						<fieldset class="ic-field-set marginT5" style="width:98%";>
							<legend><common:message key="mra.airlinebilling.inward.reports.RPTMRA018" scope="request"/>
							</legend>
							<div class="ic-row ic-input ic-split-50">
								
									<fieldset class="ic-field-set">
									
										
														<legend>
															<common:message key="mra.airlinebilling.inward.reports.RPTMRA018.legend.clrprd" />
														</legend>
														<div class="ic-col-50">
															<div class="ic-input">
																<label><common:message key="mra.airlinebilling.inward.reports.RPTMRA018.clrprdfrm" scope="request"/></label></td>
																<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA018_FROMPRD" property="rpt018FromDate" type="image" id="fromDate" />
															</div>
														</div>
														<div class="ic-col-50">
															<div class="ic-input">
																<td class="iCargoLabelRightAligned">
																<label><common:message key="mra.airlinebilling.inward.reports.RPTMRA018.clrprdto" scope="request"/></label></td>
																<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA018_TOPRD" property="rpt018ToDate" type="image" id="todate" />
															</div>
														</div>
													</fieldset>
												</div>
												<div class="ic-row ic-input ic-split-50">
													<div class="ic-col-50">
														<div class="ic-input ic-split-100 ic-mandatory">
															<label>
																<common:message key="mra.airlinebilling.inward.reports.RPTMRA018.airlinecode" scope="request"/>
															</label>
															<ihtml:text property="rpt018AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA018_ARLCOD" tabindex="4" maxlength="3" style="text-transform : uppercase;" />
															<div class="lovImg">
															<img id="rpt018AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
														    </div>
														</div>
													</div>
													<div class="ic-col-50">
														<div class="ic-input ic-split-100 ic-mandatory">
															<label>
															<common:message key="mra.airlinebilling.inward.reports.RPTMRA018.airlinenum" scope="request"/>
															</label>
															<ihtml:text property="rpt018AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA018_ARLNUM" tabindex="4" maxlength="4" style="text-transform : uppercase;" />
															<div class="lovImg">
															<img id="rpt018AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
														</div>
														</div>
														
													</div>
												</div>
										</fieldset>
								</div>
								<div class="ic-row" id="RPTMRA019" style="visibility:hidden; position:absolute;  width:99.8%;">
						<fieldset class="ic-field-set marginT5" style="width:98%";>
							<legend><common:message key="mra.airlinebilling.inward.reports.RPTMRA019" scope="request"/>
							</legend>
							<div class="ic-row">&nbsp;</div>
							<div class="ic-row">
								<div class="ic-input ic-split-40 ic-mandatory">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA019.clrprd" scope="request"/>
									</label>
									<ihtml:text property="rpt019ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA019_CLRPRD" tabindex="4" maxlength="10" style="text-transform : uppercase;" />
									<div class="lovImg">
									<img id="rpt019ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
								    </div>
								</div>
											<div class="ic-input ic-split-30 ">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA019.airlinecode" scope="request"/>
									</label>
									<ihtml:text property="rpt019AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA019_ARLCOD" tabindex="4" maxlength="3" style="text-transform : uppercase;" />
									<div class="lovImg">
									<img id="rpt019AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
							        </div>	
								</div>
											<div class="ic-input ic-split-30 ">
									<label>
									<common:message key="mra.airlinebilling.inward.reports.RPTMRA019.airlinenum" scope="request"/>
									</label>
									<ihtml:text property="rpt019AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA019_ARLNUM" tabindex="4" maxlength="4" style="text-transform : uppercase;" />
									<div class="lovImg">
									<img id="rpt019AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
								    </div>
								</div>
							</div>
							<div class="ic-row">&nbsp;</div>
						</fieldset>
					</div>
								<div class="ic-row" id="RPTMRA020" style="visibility:hidden; position:absolute;  width:100%;">
						<fieldset class="ic-field-set marginT5" style="width:98%";>
							<legend><common:message key="mra.airlinebilling.inward.reports.RPTMRA020" scope="request"/>
							</legend>
							<div class="ic-row ic-input ic-split-50">
								<fieldset class="ic-field-set ic-split-250">
										<legend>
										<common:message key="mra.airlinebilling.inward.reports.RPTMRA020.legend.clrprd" />
										</legend>
										<div class="ic-col-50">
											<div class="ic-input">
										<label><common:message key="mra.airlinebilling.inward.reports.RPTMRA020.clrprdfrm" scope="request"/></label></td>
										<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA020_FROMPRD" property="rpt020FromDate" type="image" id="rpt020FromDate" />
										</div>
										</div>
										<div class="ic-col-50">
											<div class="ic-input">
										<td class="iCargoLabelRightAligned">
										<label><common:message key="mra.airlinebilling.inward.reports.RPTMRA020.clrprdto" scope="request"/></label></td>
										<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA020_TOPRD" property="rpt020ToDate" type="image" id="rpt020ToDate" />
										</div>
										</div>
								</fieldset>
							</div>	
							<div class="ic-row ic-input ic-split-50">
								<div class="ic-col-50">
									<div class="ic-input ic-split-100 ic-mandatory">
										<label>
										 <common:message key="mra.airlinebilling.inward.reports.RPTMRA020.airlinecode" scope="request"/>
										</label>
										<ihtml:text property="rpt020AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA020_ARLCOD" tabindex="4" maxlength="3" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt020AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5"alt="" />
									    </div>
									</div>
								</div>
								<div class="ic-col-50">
									<div class="ic-input ic-split-100 ic-mandatory">
										<label>
											<common:message key="mra.airlinebilling.inward.reports.RPTMRA020.airlinenum" scope="request"/>
										</label>
										<ihtml:text property="rpt020AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_INWRPT_RPTMRA020_ARLNUM" tabindex="4" maxlength="4" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt020AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" tabindex="5" alt="" />
									    </div>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					
							<div class="ic-col-55">
							&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
						<div class="ic-col-55">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
			<div class="ic-col-55">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<div class="ic-col-55">
							&nbsp;
						</div>
						<div class="ic-col-55">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						<div class="ic-col-55">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
						</div>
				<div class="ic-button-container marginT10">
					<ihtml:nbutton property="btnPrint" tabindex="18" componentID="CMP_MRA_AIRLINEBILLING_INWRPT_PRINT" accesskey="P" >
					<common:message key="mra.airlinebilling.inward.reports.btPrint"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" tabindex="19" componentID="CMP_MRA_AIRLINEBILLING_INWRPT_CLOSE" accesskey="O">
					<common:message key="mra.airlinebilling.inward.reports.btClose"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	</ihtml:form>
		</div>

				

	</body>
</html:html>

