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

	<common:message  key="mra.airlinebilling.outward.reports.title" bundle="outwardreport" scope="request"/>

	</title>
       <meta name="decorator" content="mainpanelrestyledui">

	<common:include type="script" src="/js/mail/mra/airlinebilling/outward/OutwardBillingReport_Script.jsp"/>

</head>



<body class="ic-center" style="width:80%;">
	
	





  <!--CONTENT STARTS-->
<%@include file="/jsp/includes/reports/printFrame.jsp" %>


<div class="iCargoContent" style="overflow:auto;">
  <ihtml:form action="/mailtracking.mra.airlinebilling.outward.reports.onScreenLoad.do">

	<ihtml:hidden property="billingType"/>

	<div class="ic-content-main" style="border:0px;">
			<span class="ic-page-title ic-display-none">
				<common:message key="mra.airlinebilling.outward.reports.pagetitle" scope="request"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel marginT5">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-mandatory ic-split-50 ic-label-30">
								<label>
									<common:message key="mra.airlinebilling.outward.reports.reportID" scope="request"/>
								</label>
								<ihtml:select property="reportID"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTIDS"  style="width:250px">
									<html:option value="RPTMRA010"><common:message key="mra.airlinebilling.outward.reports.RPTMRA010" scope="request"/></html:option>
									<html:option value="RPTMRA011"><common:message key="mra.airlinebilling.outward.reports.RPTMRA011" scope="request"/></html:option>
									<html:option value="RPTMRA012"><common:message key="mra.airlinebilling.outward.reports.RPTMRA012" scope="request"/></html:option>
									<html:option value="RPTMRA013"><common:message key="mra.airlinebilling.outward.reports.RPTMRA013" scope="request"/></html:option>
									<html:option value="RPTMRA014"><common:message key="mra.airlinebilling.outward.reports.RPTMRA014" scope="request"/></html:option>
									<html:option value="RPTMRA040"><common:message key="mra.airlinebilling.outward.reports.RPTMRA040" scope="request"/></html:option>
								</ihtml:select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-section  ic-filter-panel">
					<div class="ic-row">
					<div class="ic-row">
						<div id="RPTMRA010" class="ic-row">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA010" scope="request"/></legend>
								<div class="ic-row">
									<div class="ic-input ic-split-40 ic-mandatory">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA010.clrprd" scope="request"/>
										</label>
										<ihtml:text property="rpt010ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA010_CLRPRD"  maxlength="10" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt010ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									</div>
									</div>
									     
									<div class="ic-input ic-split-30">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA010.airlinecode" scope="request"/>
										</label>
										<ihtml:text property="rpt010AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA010_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt010AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-30">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA010.airlinenum" scope="request"/>
										</label>
										<ihtml:text property="rpt010AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA010_ARLNUM"  maxlength="4" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt010AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
								</div>
							</fieldset>
						</div>
						<div id="RPTMRA011" class="ic-row" style="display:none">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA011" scope="request"/></legend>
								<div class="ic-input ic-split-50">

										<fieldset class="ic-field-set marginT5">
											<legend>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA011.legend.clrprd" />
											</legend>
											<div class="ic-input ic-split-40">
												<div class="ic-input">
													<label><common:message key="mra.airlinebilling.outward.reports.RPTMRA011.clrprdfrm" scope="request"/></label></td>
													<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA011_FROMPRD" property="rpt011FromDate" type="image" id="fromDate" />
												</div>
											</div>
											<div class="ic-input ic-split-40">
												<div class="ic-input">
													<td class="iCargoLabelRightAligned">
													<label><common:message key="mra.airlinebilling.outward.reports.RPTMRA011.clrprdto" scope="request"/></label></td>
													<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA011_TOPRD" property="rpt011ToDate" type="image" id="todate" />
												</div>
											</div>
										</fieldset>
								</div>
										<div class="ic-input ic-split-20  ic-mandatory">

											<label>
												<common:message key="mra.airlinebilling.outward.reports.RPTMRA011.airlinecode" scope="request"/>
											</label>
											<ihtml:text property="rpt011AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA011_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
											<div class="lovImg">
											<img id="rpt011AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										    </div>
										</div>
									<div class="ic-input ic-split-20  ic-mandatory">

											<label>
												<common:message key="mra.airlinebilling.outward.reports.RPTMRA011.airlinenum" scope="request"/>
											</label>
											<ihtml:text property="rpt011AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA011_ARLNUM"  maxlength="4" style="text-transform : uppercase;" />
											<div class="lovImg">
											<img id="rpt011AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
										    </div>
										</div>


							</fieldset>
						</div>
						<div id="RPTMRA012" class="ic-row" style="display:none">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA012" scope="request"/></legend>
								<div class="ic-row">
									<div class="ic-input ic-split-40 ic-mandatory">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA012.clrprd" scope="request"/>
										</label>
										<ihtml:text property="rpt012ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA012_CLRPRD"  maxlength="10" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt012ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-30">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA012.airlinecode" scope="request"/>
										</label>
										<ihtml:text property="rpt012AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA012_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt012AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-30">
										<label>
											<common:message key="mra.airlinebilling.outward.reports.RPTMRA012.airlinenum" scope="request"/>
										</label>
										<ihtml:text property="rpt012AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA012_ARLNUM"  maxlength="4" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt012AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
								</div>
							</fieldset>
						</div>
						<div id="RPTMRA013" class="ic-row" style="display:none">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA013" scope="request"/></legend>
								<div class="ic-input ic-split-50">

										<fieldset class="ic-field-set">
											<legend>
												<common:message key="mra.airlinebilling.outward.reports.RPTMRA013.legend.clrprd" />
											</legend>
										<div class="ic-input ic-split-40">
											<div class="ic-input">
												<label><common:message key="mra.airlinebilling.outward.reports.RPTMRA013.clrprdfrm" scope="request"/></label>
												<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA013_FROMPRD" property="rpt013FromDate" type="image" id="rpt013FromDate" />
											</div>
										</div>
										<div class="ic-input ic-split-40">
											<div class="ic-input">
												<td class="iCargoLabelRightAligned">
												<label><common:message key="mra.airlinebilling.outward.reports.RPTMRA013.clrprdto" scope="request"/></label></td>
												<ibusiness:calendar componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA013_TOPRD" property="rpt013ToDate" type="image" id="rpt013Todate" />
											</div>
										</div>
										</fieldset>
									</div>
									<div class="ic-input ic-split-20  ic-mandatory">
												<label>
													<common:message key="mra.airlinebilling.outward.reports.RPTMRA013.airlinecode" scope="request"/>
												</label>
												<ihtml:text property="rpt013AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA013_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
												<div class="lovImg">
												<img id="rpt013AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
											    </div>
											</div>
									<div class="ic-input ic-split-20  ic-mandatory">
												<label>
													<common:message key="mra.airlinebilling.outward.reports.RPTMRA013.airlinenum" scope="request"/>
												</label>
												<ihtml:text property="rpt013AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA013_ARLNUM"  maxlength="4" style="text-transform : uppercase;" />
												<div class="lovImg">
												<img id="rpt013AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
											    </div>
											</div>
							</fieldset>
						</div>
						<div id="RPTMRA014" class="ic-row" style="display:none">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA014" scope="request"/></legend>
								<div class="ic-row">
									<div class="ic-input ic-split-35 ic-mandatory">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA014.clrprd" scope="request"/>
										</label>
										<ihtml:text property="rpt014ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA014_CLRPRD"  maxlength="10" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt014ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-40">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA014.invnum" scope="request"/>
										</label>
										<ihtml:text property="rpt014InvoiceNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA014_INVNUM"  maxlength="18" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt014InvoiceNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-25">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA014.airlinecode" scope="request"/>
										</label>
										<ihtml:text property="rpt014AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA014_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt014AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									</div>
							</fieldset>
						</div>
						<div id="RPTMRA040" class="ic-row" style="display:none">
							<fieldset class="ic-field-set marginT5">
								<legend><common:message key="mra.airlinebilling.outward.reports.RPTMRA040" scope="request"/></legend>
								<div class="ic-row">
									<div class="ic-input ic-split-40 ic-mandatory">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA040.clrprd" scope="request"/>
										</label>
										<ihtml:text property="rpt040ClrPrd"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA040_CLRPRD"  maxlength="6" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt040ClrPrdLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
                                        </div>
									</div>
									<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA040.airlinecode" scope="request"/>
										</label>
										<ihtml:text property="rpt040AirlineCode"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA040_ARLCOD"  maxlength="3" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt040AirlineCodeLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									<div class="ic-input ic-split-30">
										<label>
										<common:message key="mra.airlinebilling.outward.reports.RPTMRA040.airlinenum" scope="request"/>
										</label>
										 <ihtml:text property="rpt040AirlineNum"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_RPTMRA040_ARLNUM"  maxlength="4" style="text-transform : uppercase;" />
										<div class="lovImg">
										<img id="rpt040AirlineNumLov" src="<%=request.getContextPath()%>/images/lov.png"  width="22" height="22" alt="" />
									    </div>
									</div>
									</div>
							</fieldset>
						</div>
					</div>
				</div>
			
					<div class="ic-button-container">
						<ihtml:nbutton property="btnPrint"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_PRINT" accesskey="P" >
							<common:message key="mra.airlinebilling.outward.reports.btPrint"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose"  componentID="CMP_MRA_AIRLINEBILLING_OUWRPT_CLOSE" accesskey="O" >
							<common:message key="mra.airlinebilling.outward.reports.btClose"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
	</div>
	</ihtml:form>
</div>


	</body>
</html:html>

