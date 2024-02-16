
<%--
/***********************************************************************
* Project : iCargo
* Module Code & Name : IN - Inventory Control
* File
Name : MonitorStock.jsp
* Date : 13-Sep-2005
* Author(s) : Smrithi

*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"
%>
<%@
page import = "java.util.HashMap" %>
<%@ page
import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm"
%>

<html:html>
	<head>

		<bean:define id="form" name="BlackListStockForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm"
			toScope="page" />

		<title>
			<common:message bundle="<%=form.getBundle()%>" key="blackliststock.title" />
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
			<common:include type="script"
				src="/js/stockcontrol/defaults/BlacklistStock_Script.jsp" />
	</head>
	<body id="bodyStyle">
		

		<business:sessionBean id="partnerAirlines"
			moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.blackliststock"
			method="get" attribute="partnerAirlines" />

		<div id="pageDiv" class="iCargoContent ic-masterbg-white" style="overflow:auto;height:100%;">
			<ihtml:form action="/stockcontrol.defaults.screenloadblackliststock.do">
				<input type="hidden" name="currentDialogId" />
				<input type="hidden" name="currentDialogOption" />
				<input type="hidden" name="mySearchEnabled" />
				<ihtml:hidden property="documentRange" />
				<ihtml:hidden property="fromScreen" />

				<ihtml:hidden property="voidNeeded" />
				<div class="ic-content-main">



					<span class="ic-page-title ic-display-none">
						<common:message key="blackliststock.BlacklistStock" />
					</span>



					<business:sessionBean id="options"
						moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.blackliststock"
						method="get" attribute="dynamicDocType" />
					<%
					BlackListStockForm blacklistStockForm =
					(BlackListStockForm)request.getAttribute("BlackListStockForm");
					%>
					<div class="ic-main-container">
						<div class="ic-input-container ic-input-round-border">



							<div class="ic-row">
								<div class="ic-input ic-split-25">
									
										<logic:present name="options">
										<bean:define name="options" id="list" type="java.util.HashMap" />
										<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts -->
										<ibusiness:dynamicoptionlist
											collection="list" id="docType" firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_DYNAMICOPTIONLIST"
											lastlistname="subType" firstoptionlabel="Doc. Type"
											lastoptionlabel="Sub Type" optionstyleclass="iCargoMediumComboBox"
											labelstyleclass="iCargoLabelRightAligned" firstselectedvalue="<%=blacklistStockForm.getDocType()%>"
											lastselectedvalue="<%=blacklistStockForm.getSubType()%>"
											docTypeTitle="doctype.tooltip" subDocTypeTitle="subdoctype.tooltip" />

									</logic:present>
								</div>

								<div class="ic-input ic-split-20 ic-label-40">
									<label>
										<common:message key="stockholder.StockHolderCode" />
									</label>
									<ihtml:text property="stockHolderCode"
										componentID="TXT_STOCKCONTROL_DEFAULTS_MONITORSTOCK_STOCKHOLDERCODE"
										maxlength="12" />
									<div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png"
                                                              width="22" id="stockHolderCodeLov" /></div>

								</div>
						<!--	</div>
							<div class="ic-row"> -->


								<div class="ic-input ic-split-15 ic-label-25 ic_inline_chcekbox marginT10">
									<ihtml:checkbox property="partnerAirline"
										componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
									<label>
										<common:message
											key="stockcontrol.defaults.blacklist.partnerAirlines.lbl" />
									</label>
								</div>
								<div class="ic-input ic-split-15 ic-label-40">

									<label>
										<common:message
											key="stockcontrol.defaults.blacklist.awbPrefix.lbl" />
									</label>


									<ihtml:select property="awbPrefix"
										componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB"
										disabled='true'>
										<ihtml:option value="">
											<common:message key="combo.select" />
										</ihtml:option>
										<logic:present name="partnerAirlines">
											<logic:iterate id="airlineLovVO" name="partnerAirlines"
												type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
												<logic:present name="airlineLovVO" property="airlineNumber">
													<% String
													value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier();
													%>
													<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
												</logic:present>
											</logic:iterate>
										</logic:present>
									</ihtml:select>

								</div>
								<div class="ic-input ic-split-20 ic-label-20">
									<label>
										<common:message
											key="stockcontrol.defaults.blacklist.partnerAirlines.lbl" />
									</label>


									<ihtml:text property="airlineName"
										componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL"
										readonly="true" />
								</div>
							</div>
							<div class="ic-row">


								<div class="ic-input ic-split-25 ic-label-25" style=" !important;">
									<label>
										<common:message key="blackliststock.RangeFrom" />
									</label>
									<ihtml:text property="rangeFrom" style="text-align:right"
										componentID="TXT_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_RANGEFROM" />
								</div>
								<div class="ic-input ic-split-20 ic-label-40" >
									<label>
										<common:message key="blackliststock.RangeTo" />
									</label>

									<ihtml:text property="rangeTo" style="text-align:right"
										componentID="TXT_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_RANGETO" />

								</div>
								<div class="ic-input ic-split-50 ic-label-20">
									<label>
										<common:message key="blackliststock.Remarks" />
									</label>



									<ihtml:textarea property="remarks"
										componentID="TXT_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_REMARKS"
										cols="50" rows="3" />

								</div>


							</div>







						</div>



					</div>
					<div class="ic-foot-container">


						<div class="ic-button-container paddR5">

							<div style="display: none">
								<ihtml:nbutton property="btnClear"
									componentID="BTN_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_CLEAR">
									<common:message key="blackliststock.clear" />
								</ihtml:nbutton>
							</div>
							<logic:equal name="form" property="voidNeeded" value="Y">
								<ihtml:nbutton property="btvoid"
									componentID="BTN_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_VOID"
									accesskey="V">
									<common:message key="blackliststock.void" />
								</ihtml:nbutton>
							</logic:equal>
							<ihtml:nbutton property="btblacklist"
								componentID="BTN_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_BLACKLIST"
								accesskey="B">
								<common:message key="blackliststock.blacklist" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btclose"
								componentID="BTN_STOCKCONTROL_DEFAULTS_BLACKLISTSTOCK_CLOSE"
								accesskey="O">
								<common:message key="blackliststock.close" />
							</ihtml:nbutton>

						</div>

					</div>
			</ihtml:form>
		</div>


	
	</body>
</html:html>




