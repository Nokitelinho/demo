<%--/***********************************************************************
* Project	     	     : iCargo
* Module Code & Name 	 : CRA
* File Name          	 : ListCN51CN66.jsp
* Date                 	 : 16-Jan-2006
* Author(s)              : A-2270
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form"%>

<%@ page import = "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Formatter" %>
<%
   String FORMAT_STRING = "%1$-16.2f";
%>


<html>
<head>


	<common:include	type="script" src="/js/mail/mra/gpabilling/ListCN51CN66_Script.jsp" />
	<title>
		<common:message bundle="listcn51cn66" key="cra.defaults.listcn51cn66.title" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
</head>

<body>




	<business:sessionBean id="cN51CN66VO"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.listcn51cn66" method="get"
		attribute="cN51CN66VO" />
	<business:sessionBean id="cN66VOs"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.listcn51cn66" method="get"
		attribute="cN66VOs" />
		<business:sessionBean id="cN51CN66FilterVO"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.listcn51cn66" method="get"
		attribute="cN51CN66FilterVO" />
	<business:sessionBean id="cN51DetailsVOs"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.listcn51cn66" method="get"
		attribute="cN51DetailsVOs" />
	<business:sessionBean id="oneTimeVOs"
				moduleName="mailtracking.mra.gpabilling"
				screenID="mailtracking.mra.gpabilling.listcn51cn66"
				method="get"
		attribute="oneTimeVOs" />
	<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.listcn51cn66"
	method="get" attribute="systemparametres" />

<%@include file="/jsp/includes/reports/printFrame.jsp" %>

<div id="contentdiv" class="iCargoContent ic-masterbg">
<ihtml:form action="/mailtracking.mra.gpabilling.onscreenloadlistcn51cn66.do">
<jsp:include page="/jsp/includes/tab_support.jsp" />

<html:hidden property="btnStatus" />
<html:hidden property="saveBtnStatus" />
<html:hidden property="invokingScreen"/>
<html:hidden property="accEntryFlag"/>
<html:hidden property="displayPage" />
  <html:hidden property="lastPageNum" />
  <html:hidden property="displayPageCN66" />
  <html:hidden property="cn66LastPageNumber" />
  <ihtml:hidden property="checkButton" />
  <ihtml:hidden property="invoiceStatus" />
  <ihtml:hidden property="fileName" />
  <ihtml:hidden property="overrideRounding" value="N" />

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="mySearchEnabled" />
<bean:define id="form" name="ListCN51CN66Form" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form" toScope="page"/>
<ihtml:hidden property="restrictionFlag" value="N" />
<ihtml:hidden property="reportSpecificFlag" value="N" />
<ihtml:hidden property="reportRestrictionFlag" value="N" />
	<common:xgroup>
		<common:xsubgroup id="INDIGO_SPECIFIC">
			 <%form.setRestrictionFlag("Y");%>
		</common:xsubgroup>
		<common:xsubgroup id="TURKISH_SPECIFIC">
			 <%form.setReportSpecificFlag("Y");%>
			  <%form.setReportRestrictionFlag("TK");%>
		</common:xsubgroup>
		<common:xsubgroup id="SINGAPORE_SPECIFIC">
			 <%form.setReportSpecificFlag("Y");%>
			 <%form.setReportRestrictionFlag("SQ");%>
		</common:xsubgroup>
		<common:xsubgroup id="TAP_SPECIFIC">
			 <%form.setReportSpecificFlag("Y");%>
			 <%form.setReportRestrictionFlag("TP");%>
		</common:xsubgroup>
		<common:xsubgroup id="KE_SPECIFIC">
			<%form.setReportSpecificFlag("Y");%>
			<%form.setReportRestrictionFlag("KE");%>
        </common:xsubgroup>
	</common:xgroup >
		<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>
        <div class="ic-content-main">
	        <span class="ic-page-title ic-display-none">
			    <common:message key="mailtracking.mra.gpabilling.listcn51cn66.tabletitle" />
			</span>
			<div class="ic-head-container">
			    <div class="ic-filter-panel">
				    <div class="ic-input ic-mandatory ic-split-15 ic-marg-top-7">
					<label>
					    <common:message key="mailtracking.mra.gpabilling.listcn51cn66.invoicenumber"/>
					</label>
					    <ihtml:text property="invoiceNumber" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_INVOICENUMBER"
						maxlength="20" />
						<div class="lovImg">
						<img name="invoicenumberlov" id="invoicenumberlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" /></div>
					</div>
					  <div class="ic-input ic-mandatory ic-split-15 ic-marg-top-7">
					<label>
					    <common:message key="mailtracking.mra.gpabilling.listcn51cn66.gpacode"/>
					</label>
					    <ihtml:text property="gpaCode" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_GPACODE" maxlength="5"/>
						<div class="lovImg">
					    <img name="gpacodelov" id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
						</div>
					</div>
					  <div class="ic-input ic-split-10 ic-marg-top-7">
					<label>
					    <common:message key="mailtracking.mra.gpabilling.listcn51cn66.airline"/>
					</label>
					    <ihtml:text property="airlineCode" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_ARLCOD"
							 readonly="true" maxlength="3"/>
					</div>
					<div class="ic-input ic-split-40 ic-marg-top-20 ">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.filename"/>:
						<common:write name="form" property="fileName" />
					</div>
					 <div class="ic-input ic-split-10 ic-marg-top-20 ">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.filestatus"/>:
						<common:write name="form" property="invStatusDesc" />
					</div>
					<div class="ic-input  ic-split-10">
					<div class="ic-button-container marginT10">
					    <ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_BTNDETAILS">
			    		    <common:message key="mailtracking.mra.gpabilling.btn.details" />
			    		</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_BTNCLEAR">
						    <common:message key="mailtracking.mra.gpabilling.btn.clear" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			</div>

			<div class="ic-main-container">
			    <div class="tab-container ic-left" id="container1">
					<ul class="tabs">
						<li>
						<button type="button" onClick="updateCurrentPane('pane1');return showPane(event,'pane1', this);"
							accesskey="5" class="tab" id="tab.Gen">
							<b><common:message key="mailtracking.mra.gpabilling.listcn51cn66.cn51"/></b>
						</button>
						</li>
						<li>
						<button type="button" onClick="updateCurrentPane('pane2');return showPane(event,'pane2', this);"
						accesskey="6" class="tab" id="tab.Main">
						<b><common:message key="mailtracking.mra.gpabilling.listcn51cn66.cn66"/></b>
						</button>
						</li>
					</ul>
			    <div class="tab-panes">
			    <div id="pane1">
			    <logic:present name="cN51DetailsVOs">
					<div class="ic-col-30">
					    <logic:present name="cN51DetailsVOs">
						   <bean:define id="unlabelledPage" name="cN51DetailsVOs"/>
						   <common:paginationTag pageURL="mailtracking.mra.gpabilling.onlistcn51cn66.do"
							name="unlabelledPage"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>"/>
						</logic:present>
						 <logic:notPresent name="unlabelledPage">
								 &nbsp;
						 </logic:notPresent>
                    </div>
					<div class="ic-right">
					    <logic:present name="unlabelledPage">
								<common:paginationTag pageURL="javascript:submitCN51Page('lastPageNum','displayPage')"
								name="unlabelledPage"
								display="pages"
								linkStyleClass="iCargoLink"
								disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getLastPageNum()%>"
								exportToExcel="true"
								exportTableId="CN51table"
								exportAction = "mailtracking.mra.gpabilling.onlistcn51cn66.do"/>
						</logic:present>
						<logic:notPresent name="unlabelledPage">
							&nbsp;
						</logic:notPresent>
					</div>
				</logic:present>
                    <div class="ic-row">
					    <div id="div1" class="tableContainer"  style=" width:100%;height:650px;">
							<table class="fixed-header-table" style="width:100%;" id="CN51table">
							    <thead>
								 <tr>
								
									<td class="iCargoTableHeaderLabel" rowspan="2" width="8%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.sector"/><span></span></td>
									<td class="iCargoTableHeaderLabel"  rowspan="2" width="8%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.mailsubclass"/><span></span></td>
									<td class="iCargoTableHeaderLabel"  rowspan="2" width="8%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.mailcategorycode"/><span></span></td>
									<td class="iCargoTableHeaderLabel" rowspan="2" width="8%" ><common:message key="mailtracking.mra.gpabilling.listcn51cn66.Weight(K.g)"/><span></span></td>
									<!--modified width by A-8149 for ICRD-259059 --><td class="iCargoTableHeaderLabel" colspan="2"  width="13%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.mailchg"/><span></span></td>
									<td class="iCargoTableHeaderLabel" rowspan="2" width="6%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.surchg"/><span></span></td>
									<td class="iCargoTableHeaderLabel" rowspan="2" width="9%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.grossamt"/><span></span></td>
									<logic:equal name="form" property="reportSpecificFlag" value="Y">
										<td class="iCargoTableHeaderLabel" rowspan="2" width="9%">Val Chgs<span></span></td>
									</logic:equal>
									<td class="iCargoTableHeaderLabel" rowspan="2" width="9%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.stax"/><span></span></td>
									<logic:notEqual name="form" property="reportSpecificFlag" value="Y">
									<td class="iCargoTableHeaderLabel" rowspan="2" width="9%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.tds"/><span></span></td>
									</logic:notEqual>
									<td class="iCargoTableHeaderLabel" rowspan="2" width="9%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.netamt"/><span></span></td>
									<!--modified width by A-8149 for ICRD-259059 --><td class="iCargoTableHeaderLabel" rowspan="2"  width="5%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.curr"/><span></span></td>
								</tr>
								<tr>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.rate"/><span></span></td>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.amount"/><span></span></td>
								</tr>
								</thead>
								<tbody>
							        <logic:present name="cN51DetailsVOs">
										<logic:iterate id="cn51vo" name="cN51DetailsVOs"  indexId="rowCount">
                                            <tr>
												<td>
													<logic:present name="cn51vo" property="sector">
															<common:write name="cn51vo" property="sector"/>
													</logic:present>
													<logic:notPresent name="cn51vo" property="sector">
														&nbsp;
													</logic:notPresent>
												</td>
												<td>
													<logic:present name="cn51vo" property="mailSubclass">
														   <common:write name="cn51vo" property="mailSubclass"/>
													</logic:present>
													<logic:notPresent name="cn51vo" property="mailSubclass">
														   &nbsp;
													</logic:notPresent>
												</td>
												<td>
													<logic:present name="cn51vo" property="mailCategoryCodedisp">
														   <common:write name="cn51vo" property="mailCategoryCodedisp"/>
													</logic:present>
													<logic:notPresent name="cn51vo" property="mailCategoryCodedisp">
														   &nbsp;
													</logic:notPresent>
												</td>
												<td class="ic-right">

													<logic:present name="cn51vo" property="totalWeight">
													<logic:present name="cn51vo" property="unitCode">
													<bean:define id="totalWeightcn51" name="cn51vo" property="totalWeight"/>
													<bean:define id="unitCodecn51" name="cn51vo" property="unitCode"/>
													<%String wgtAndUntcn51 = totalWeightcn51+" "+unitCodecn51;%>
													<%=wgtAndUntcn51%>
													<!--<common:write name="cn51vo" property="totalWeight" unitFormatting="true" />
													<common:write name="cn51vo" property="unitCode" />   &nbsp;-->
													</logic:present>
													</logic:present>
													<logic:notPresent name="cn51vo" property="totalWeight">
														   &nbsp;
													</logic:notPresent>
												</td>
												<td class="ic-right">
													<logic:present name="cn51vo" property="applicableRate">
													<logic:notEqual name="cn51vo" property="applicableRate" value = "0">
																<!--<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="applicableRate" property="applicableRate" />-->
														<common:write name="cn51vo" property="applicableRate" format="####.#####"/> <!-- Modified for ICRD-267463 -->
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn51vo" property="applicableRate">
													   &nbsp;
													</logic:notPresent>
												</td>
												<td class="ic-right">
													<logic:present name="cn51vo" property="mailCharge">
													<logic:equal name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="mailCharge" property="mailCharge" overrideRounding = "true"/>
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="mailCharge" property="mailCharge" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn51vo" property="mailCharge">
														&nbsp;
													</logic:notPresent>
												</td>
												<td class="ic-right">
													<logic:present name="cn51vo" property="surCharge">
													<logic:equal name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="surCharge" property="surCharge" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="surCharge" property="surCharge" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn51vo" property="surCharge">
														&nbsp;
													</logic:notPresent>
												</td>
												<td class="ic-right">
													<logic:present name="cn51vo" property="grossAmount">
													<logic:equal name="form" property="overrideRounding" value = "Y">
															  <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="grossAmount" property="grossAmount" overrideRounding = "true"/>
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
															  <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="grossAmount" property="grossAmount" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn51vo" property="grossAmount">
														&nbsp;
													</logic:notPresent>
												</td>
												<logic:equal name="form" property="reportSpecificFlag" value="Y">
													<td class="ic-right">
															<logic:present name="cn51vo" property="valCharges">
															<logic:equal name="form" property="overrideRounding" value = "Y">
																 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="valCharges" property="valCharges" overrideRounding = "true" />
															</logic:equal>
															<logic:notEqual name="form" property="overrideRounding" value = "Y">
																 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="valCharges" property="valCharges" />
															</logic:notEqual>
															</logic:present>
															<logic:notPresent name="cn51vo" property="valCharges">
																   &nbsp;
															</logic:notPresent>
													</td>
												</logic:equal>
												<td class="ic-right">
													<!--localeformat="true" removed from commom write by A-4809 -->
														<logic:present name="cn51vo" property="serviceTax">
														<logic:equal name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="serviceTax" property="serviceTax" overrideRounding = "true" />
														</logic:equal>
														<logic:notEqual name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="serviceTax" property="serviceTax" />
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="cn51vo" property="serviceTax">
															   &nbsp;
														</logic:notPresent>
												</td>
												<logic:notEqual name="form" property="reportSpecificFlag" value="Y">
												<td class="ic-right">
														<logic:present name="cn51vo" property="tds">
														<logic:equal name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="tds" property="tds" overrideRounding = "true" />
														</logic:equal>
														<logic:notEqual name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="tds" property="tds" />
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="cn51vo" property="tds">
															   &nbsp;
														</logic:notPresent>
												</td>
												</logic:notEqual>
												<td class="ic-right">

														<logic:present name="cn51vo" property="netAmount">
														<logic:equal name="form" property="overrideRounding" value = "Y">
															  <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="netAmount" property="netAmount" overrideRounding = "true" />
														</logic:equal>
														<logic:notEqual name="form" property="overrideRounding" value = "Y">
															  <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="netAmount" property="netAmount" />
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="cn51vo" property="netAmount">
															   &nbsp;
														</logic:notPresent>
												</td>
												<td>

														<logic:present name="cn51vo" property="billingCurrencyCode">
															   <common:write name="cn51vo" property="billingCurrencyCode" />
														</logic:present>
														<logic:notPresent name="cn51vo" property="billingCurrencyCode">
															   &nbsp;
														</logic:notPresent>
												</td>
					   				        </tr>
										</logic:iterate>
								    </logic:present>
							    </tbody>
							</table>
						</div>
						<div class="tableContainer" style="height:23px;">
							<table class="fixed-header-table" style="height:23px;">
								<tfoot>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td  id="netCargo" class="ic-right">
											<logic:present name="cn51vo" property="totalBilledAmount">
												<logic:equal name="form" property="overrideRounding" value = "Y">
													<logic:equal name="cn51vo" property="billingCurrencyCode" value = "KRW">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="totalBilledAmount" property="totalBilledAmount" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="cn51vo" property="billingCurrencyCode" value = "KRW">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="totalBilledAmount" property="totalBilledAmount" overrideRounding = "true" />
													</logic:notEqual>
												</logic:equal>
												<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn51vo" moneyproperty="totalBilledAmount" property="totalBilledAmount" />
												</logic:notEqual>
											</logic:present>
											<logic:notPresent name="cn51vo" property="totalBilledAmount">
												&nbsp;
											</logic:notPresent>
										</td>
										<td>&nbsp;</td>
							        </tr>
							    </tfoot>
							</table>
                    </div>

                    </div>
				</div>

				<div id="pane2">
				    <div class="ic-row ic-round-border">
						<div class="ic-input ic-split-20 ic-label-30 ic-marg-top-7">
							<label>
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.category"/>
							</label>
								 <%HashMap hashExps = new HashMap();%>
										<ihtml:select componentID="CMP_MRA_GPABILLING_LISTCN51CN66_CATCOD" property="category">
											<logic:notPresent name="cN51CN66FilterVO" property="category">
												<ihtml:option value=""></ihtml:option>
											</logic:notPresent>
											<logic:present name="oneTimeVOs">
											<ihtml:option value=""></ihtml:option>
												<logic:iterate id="oneTimeValue" name="oneTimeVOs">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue" property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<%= hashExps.put(fieldValue,fieldDescription)%>
																<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase()%>">
																<bean:write name="parameterValue" property="fieldDescription"/></ihtml:option>
															</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
						</div>
						<div class="ic-input ic-split-20 ic-label-30 ic-marg-top-7">
							<label>
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.origin"/>
							</label>
								<logic:present name="cN51CN66FilterVO" property="orgin">
									<bean:define id="orgin" name="cN51CN66FilterVO" property="orgin" />
									 <ihtml:text property="origin" componentID="CMP_MRA_gpabilling_listcn51cn66_origin" value="<%=String.valueOf(orgin)%>"
									  maxlength="4" />
								</logic:present>
								<logic:notPresent name="cN51CN66FilterVO" property="orgin">
									 <ihtml:text property="origin" componentID="CMP_MRA_gpabilling_listcn51cn66_origin"
									  maxlength="4" />
								</logic:notPresent>
								<div class="lovImg">
									<img name="stationlov" id="stationlov"
									 src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
								</div>
						</div>
						<div class="ic-input ic-split-20 ic-label-30 ic-marg-top-7">
							<label>
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.Destination"/>
							</label>
								<logic:present name="cN51CN66FilterVO" property="destination">
									<bean:define id="destination" name="cN51CN66FilterVO" property="destination" />
									<ihtml:text property="destination" componentID="CMP_MRA_gpabilling_listcn51cn66_Destination" value="<%=String.valueOf(destination)%>"
										maxlength="4" />
								</logic:present>
								<logic:notPresent name="cN51CN66FilterVO" property="destination">
									 <ihtml:text property="destination" componentID="CMP_MRA_gpabilling_listcn51cn66_Destination"
										maxlength="4" />
								</logic:notPresent>
								<div class="lovImg">
								<img name="stationCodelov" id="stationCodelov"
									 src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
								</div>
						</div>
						<div class="ic-input ic-split-20 ic-label-30 ic-marg-top-7">
							<label>
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.DsnNumber"/>
							</label>
								<logic:present name="cN51CN66FilterVO" property="dsnNumber">
									<bean:define id="dsnNumber" name="cN51CN66FilterVO" property="dsnNumber" />
									<ihtml:text property="dsnNumber" componentID="CMP_MRA_gpabilling_listcn51cn66_DsnNumber" value="<%=String.valueOf(dsnNumber)%>"
										maxlength="4" />
								</logic:present>
								<logic:notPresent name="cN51CN66FilterVO" property="dsnNumber">
									 <ihtml:text property="dsnNumber" componentID="CMP_MRA_gpabilling_listcn51cn66_DsnNumber"
										maxlength="4" />
								</logic:notPresent>
						</div>
						<div class="ic-input ic-split-20">
						<div class="ic-button-container marginT10">
							<ihtml:nbutton property="btnTabList" componentID="CMP_MRA_gpabilling_listcn51cn66_LISTBUTTON" >
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.list"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btnTabClear" componentID="CMP_MRA_gpabilling_listcn51cn66_CLEARBUTTON" >
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.clear"/>
							</ihtml:nbutton>
						</div>
					</div>
					</div>
					<div class="ic-row">
					    <logic:present name="cN66VOs">
					    <div class="ic-col-30">
						    <logic:present name="cN66VOs">
								<bean:define id="CN66Tab" name="cN66VOs"/>
								<common:paginationTag pageURL="mailtracking.mra.gpabilling.onlistcn51cn66.do"
										name="CN66Tab"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getCn66LastPageNumber() %>"/>
							</logic:present>
							<logic:notPresent name="CN66Tab">
									&nbsp;
							</logic:notPresent>
						</div>
						<div class="ic-right">
					        <logic:present name="CN66Tab">
								<common:paginationTag pageURL="javascript:submitCN66Page('lastPageNum','displayPage')"
								name="CN66Tab"
								display="pages"
								linkStyleClass="iCargoLink"
								disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getCn66LastPageNumber()%>"
								exportToExcel="true"
								exportTableId="listcn51cn66"
								exportAction = "mailtracking.mra.gpabilling.onCN66TabListcn51cn66.do"/>
							</logic:present>
							<logic:notPresent name="CN66Tab">
								&nbsp;
							</logic:notPresent>
						</div>
						</logic:present>
				    </div>
					<div class="ic-row">
					    <div id="div2" class="tableContainer"   style="height:600px">
							<table class="fixed-header-table" id="listcn51cn66">
							    <thead>
									<tr class="ic-th-all">
												<th style="width:2%"/>
												<th style="width:5%"/>

												<th style="width:5%"/>
												<th style="width:3%"/>

												<th style="width:3%"/>
												<th style="width:2%"/>
												<th style="width:2%"/>
												<!--modified width by A-8149 for ICRD-259059 --><th style="width:5%"/>
												<th style="width:4%"/>
												<!-- modified width by A-8149 for ICRD-259059--><th style="width:5%"/>
												<th style="width:4%"/>
												<th style="width:4%"/>
												<th style="width:3%"/>
												<th style="width:3%"/>
												<th style="width:3%"/>
												<th style="width:3%"/>
												<th style="width:5%"/>
												<!-- modified width by A-8149 for ICRD-259059--><th style="width:7%"/>
												<!--modified width by A-8149 for ICRD-259059 --><th style="width:6%"/>
												<!-- modified width by A-8149 for ICRD-259059--><th style="width:7%"/>
												<th style="width:4%"/>
												<th style="width:4%"/>
												<!--modified width by A-8149 for ICRD-259059 --><th style="width:6%"/>
												<!--modified width by A-8149 for ICRD-259059 --><th style="width:3%"/>

									</tr>
									<tr>
										<td class="iCargoTableHeaderLabel" rowspan="2"> &nbsp; </td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.category"/><span></span></td>
										<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.date"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2" ><common:message key="mailtracking.mra.gpabilling.listcn51cn66.dsn"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.rsn"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.hni"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.ri"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.msnno"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.origin"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.Destination"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.flownsector"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.fltnum"/><span></span></td>
										<td class="iCargoTableHeaderLabel"  colspan="4"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.weight"/><span></span></td>
										<td class="iCargoTableHeaderLabel"  colspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.mailchg"/><span></span></td>
										<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.surchg"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.grossamt"/><span></span></td>
										<logic:equal name="form" property="reportSpecificFlag" value="Y">
											<td class="iCargoTableHeaderLabel" rowspan="2">Val Chgs<span></span></td>
											<td class="iCargoTableHeaderLabel" rowspan="2" width="7%"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.declaredValue"/><span></span></td>
										</logic:equal>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.stax"/><span></span></td>
										<logic:notEqual name="form" property="reportSpecificFlag" value="Y">
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.tds"/><span></span></td>
										</logic:notEqual>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.netamt"/><span></span></td>
										<td class="iCargoTableHeaderLabel" rowspan="2"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.curr"/><span></span></td>
									</tr>
									<tr>
										<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.lc/ao"/><span></span></td>
										<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.gpabilling.listcn51cn66.cp"/><span></span></td>
										<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.sv"/><span></span></td>
										<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.EMS"/><span></span></td>
										<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.gpabilling.listcn51cn66.rate"/><span></span></td>
										<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.gpabilling.listcn51cn66.amount"/><span></span></td>
						            </tr>
								</thead>
									<tbody>
									       <logic:present name="cN66VOs">
									         <%int index=0;%>
									      	<logic:iterate id="cn66vo" name="cN66VOs" indexId="rowIndex" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO">
                                        <tr>
                                            <td>
			                                  <input type="checkbox" name="selectContainer" value="<%=rowIndex.toString()%>" onclick="onMultiSelect('selectContainer')" />
		                                    </td>
											<td>
												<logic:present name="cn66vo" property="mailCategoryCode">
														<bean:define id="expCode" name="cn66vo" property="mailCategoryCode"/>
														<%=hashExps.get(expCode)%>
												</logic:present>
										    </td>
										    <td>
											   	<logic:present name="cn66vo" property="receivedDate">
											   		<bean:define id="date" name="cn66vo" property="receivedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											   		<%
											   		String receivedDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
											   		<%=receivedDate%>
											   	</logic:present>
											</td>
											<td class="ic-right">
												<logic:present name="cn66vo" property="dsn">
													<common:write name="cn66vo" property="dsn"/>
													</logic:present>
													<logic:notPresent name="cn66vo" property="dsn">
													&nbsp;
												</logic:notPresent>
											</td>
											<td class="ic-right">
												<logic:present name="cn66vo" property="rsn">
													<common:write name="cn66vo" property="rsn"/>
													</logic:present>
													<logic:notPresent name="cn66vo" property="rsn">
													&nbsp;
												</logic:notPresent>
											</td>
											<td class="ic-right">
												<logic:present name="cn66vo" property="hsn">
													<common:write name="cn66vo" property="hsn"/>
													</logic:present>
													<logic:notPresent name="cn66vo" property="hsn">
													&nbsp;
												</logic:notPresent>
											</td>
											<td class="ic-right">
												<logic:present name="cn66vo" property="regInd">
													<common:write name="cn66vo" property="regInd"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="regInd">
													&nbsp;
												</logic:notPresent>
											</td>
											<td>
												<logic:present name="cn66vo" property="ccaRefNo">
													<input name="ccaRefNumber" type ="hidden" value="<%=String.valueOf(cn66vo.getCcaRefNo())%>"/>
													<common:write name="cn66vo" property="ccaRefNo"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="ccaRefNo">
													<input name="ccaRefNumber" type ="hidden" value=""/>
													&nbsp;
												</logic:notPresent>
											</td>
											<td>
												<logic:present name="cn66vo" property="origin">
														<common:write name="cn66vo" property="origin"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="origin">
														&nbsp;
												</logic:notPresent>
											</td>
											<td>
												<logic:present name="cn66vo" property="destination">
													<common:write name="cn66vo" property="destination"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="destination">
														&nbsp;
												</logic:notPresent>
											</td>
											<td>
												<logic:present name="cn66vo" property="sector">
														  <common:write name="cn66vo" property="sector"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="sector">
														   &nbsp;
												 </logic:notPresent>
											</td>
											<td>
											
											    <logic:present name="cn66vo" property="flightNumber">
													   <common:write name="cn66vo" property="flightNumber"/>
												</logic:present>
												<logic:notPresent name="cn66vo" property="flightNumber">
													   &nbsp;
											    </logic:notPresent>
											</td>
											<td >
											
											    <logic:equal property="mailSubclass" name="cn66vo"  value="LC">
													<logic:present name="cn66vo" property="totalWeight">
													<logic:present name="cn66vo" property="unitcode">
														<bean:define id="totalWeightcn66" name="cn66vo" property="totalWeight"/>
														<bean:define id="unitCodecn66" name="cn66vo" property="unitcode"/>
														<%String wgtAndUntcn66 = totalWeightcn66+" "+unitCodecn66;%>
														<%=wgtAndUntcn66%>
														<!--<common:write name="cn66vo" property="totalWeight" unitFormatting="true" />
														<common:write name="cn66vo" property="unitcode" />-->
													</logic:present></logic:present>
													<logic:notPresent name="cn66vo" property="totalWeight">
														&nbsp;
													</logic:notPresent>
											    </logic:equal>
											</td>
											<td class="ic-right">
											
												<logic:equal property="mailSubclass" name="cn66vo"  value="CP">
													<logic:present name="cn66vo" property="totalWeight">
													<logic:present name="cn66vo" property="unitcode">
														<bean:define id="totalWeightcn66" name="cn66vo" property="totalWeight"/>
														<bean:define id="unitCodecn66" name="cn66vo" property="unitcode"/>
														<%String wgtAndUntcn66 = totalWeightcn66+" "+unitCodecn66;%>
														<%=wgtAndUntcn66%>
													    <!--<common:write name="cn66vo" property="totalWeight" unitFormatting="true" />
														<common:write name="cn66vo" property="unitcode" />-->
                                                    </logic:present>
													 </logic:present>
													<logic:notPresent name="cn66vo" property="totalWeight">
													    &nbsp;
													</logic:notPresent>
												</logic:equal>
											</td>
											<td class="ic-right">
											
												<logic:equal property="mailSubclass" name="cn66vo"  value="SV">
													<logic:present name="cn66vo" property="totalWeight">
													<logic:present name="cn66vo" property="unitcode">
														<bean:define id="totalWeightcn66" name="cn66vo" property="totalWeight"/>
														<bean:define id="unitCodecn66" name="cn66vo" property="unitcode"/>
														<%String wgtAndUntcn66 = totalWeightcn66+" "+unitCodecn66;%>
														<%=wgtAndUntcn66%>
													    <!--<common:write name="cn66vo" property="totalWeight" unitFormatting="true" />
														<common:write name="cn66vo" property="unitcode" />-->
                                                    </logic:present></logic:present>
													<logic:notPresent name="cn66vo" property="totalWeight">
													&nbsp;
													</logic:notPresent>
												</logic:equal>
											</td>
											<td>
											
												<logic:equal property="mailSubclass" name="cn66vo"  value="EMS">
											        <logic:present name="cn66vo" property="totalWeight">
													<logic:present name="cn66vo" property="unitcode">
														<bean:define id="totalWeightcn66" name="cn66vo" property="totalWeight"/>
														<bean:define id="unitCodecn66" name="cn66vo" property="unitcode"/>
														<%String wgtAndUntcn66 = totalWeightcn66+" "+unitCodecn66;%>
														<%=wgtAndUntcn66%>
														<!--<common:write name="cn66vo" property="totalWeight" />
														<common:write name="cn66vo" property="unitcode" />-->
											        </logic:present></logic:present>
													<logic:notPresent name="cn66vo" property="totalWeight">
													    &nbsp;
													</logic:notPresent>
											    </logic:equal>
											</td>
											<td class="ic-right">
											
												<logic:present name="cn66vo" property="applicableRate">
												<logic:notEqual name="cn66vo" property="applicableRate" value = "0">
														<bean:write name="cn66vo" property="applicableRate" format="####.#####"/> <!-- Modified for ICRD-267463 -->
												</logic:notEqual>
												</logic:present>
												<logic:notPresent name="cn66vo" property="applicableRate">
														   &nbsp;
												 </logic:notPresent>
											</td>
											<td class="ic-right">
											
												<logic:present name="cn66vo" property="mailCharge">
												<logic:equal name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="mailCharge" property="mailCharge" overrideRounding = "true" />
												</logic:equal>
												<logic:notEqual name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="mailCharge" property="mailCharge" />
												</logic:notEqual>
												</logic:present>
												<logic:notPresent name="cn66vo" property="mailCharge">
														   &nbsp;
												 </logic:notPresent>
											</td>
											<td class="ic-right">
											
                                                    <logic:present	name="cn66vo" property="surCharge">
														<logic:equal name="form" property="overrideRounding" value = "Y">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="surCharge" property="surCharge" overrideRounding = "true" />
														</logic:equal>
														<logic:notEqual name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="surCharge" property="surCharge" />
														</logic:notEqual>
														<bean:define id="surCharge" name="cn66vo" property="surCharge"/>
														<%String surChrg=surCharge.toString();%>
														<ihtml:hidden name="cn66vo" property="surCharge"/>
														<ihtml:hidden property="surChg" value="<%=surChrg%>"/>
													</logic:present>
													<logic:notPresent name="cn66vo" property="surCharge">
														   &nbsp;
													</logic:notPresent>

</td>
											<td class="ic-right">
											
												<logic:present name="cn66vo" property="actualAmount">
												<logic:equal name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="actualAmount" property="actualAmount" overrideRounding = "true" />
												</logic:equal>
												<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="actualAmount" property="actualAmount" />
												</logic:notEqual>
												</logic:present>
												<logic:notPresent name="cn66vo" property="actualAmount">
														   &nbsp;
												</logic:notPresent>
											</td>
											<logic:equal name="form" property="reportSpecificFlag" value="Y">
											<td class="ic-right">
											
												<logic:present name="cn66vo" property="valCharges">
													<logic:equal name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="valCharges" property="valCharges" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="valCharges" property="valCharges" />
													</logic:notEqual>
												</logic:present>
												<logic:notPresent name="cn66vo" property="valCharges">
														&nbsp;
												</logic:notPresent>
											</td>
											<td class="ic-right">
												<logic:present name="cn66vo" property="declaredValue">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="declaredValue" property="declaredValue" overrideRounding = "true"/>
												</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="declaredValue" property="declaredValue" />
													</logic:notEqual>
												</logic:present>

												<logic:notPresent name="cn66vo" property="declaredValue">
														0
												</logic:notPresent>
											</td>
											</logic:equal>
											<td class="ic-right">
												  <logic:present name="cn66vo" property="serviceTax">
													<logic:equal name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="serviceTax" property="serviceTax" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="serviceTax" property="serviceTax" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn66vo" property="serviceTax">
														   &nbsp;
												   </logic:notPresent>
											</td>
											<logic:notEqual name="form" property="reportSpecificFlag" value="Y">
											<td class="ic-right">
												  <logic:present name="cn66vo" property="tds">
													<logic:equal name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="tds" property="tds" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="tds" property="tds" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn66vo" property="tds">
														   &nbsp;
												   </logic:notPresent>
											</td>
											</logic:notEqual>

											<td class="ic-right">
												  <logic:present name="cn66vo" property="netAmount">
													<logic:equal name="form" property="overrideRounding" value = "Y">
													 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="netAmount" property="netAmount" overrideRounding = "true" />
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
														 <ibusiness:moneyDisplay showCurrencySymbol="false" name="cn66vo" moneyproperty="netAmount" property="netAmount" />
													</logic:notEqual>
													</logic:present>
													<logic:notPresent name="cn66vo" property="netAmount">
														   &nbsp;
												   </logic:notPresent>
											</td>
											<td>
												  <logic:present name="cn66vo" property="currencyCode">
														  <common:write name="cn66vo" property="currencyCode"/>
													</logic:present>
													<logic:notPresent name="cn66vo" property="currencyCode">
														   &nbsp;
												   </logic:notPresent>
											</td>
										</tr>

									       <%index++;%>
									    </logic:iterate>
							 	 </logic:present>
						</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="ic-left">
					<label>
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.invoicestatus"/>
							</label>
				   <logic:present name="cN51CN66VO" property="invoiceStatus">
				     <common:write name="cN51CN66VO" property="invoiceStatus"/>
				   </logic:present>
					<logic:notPresent name="cN51CN66VO" property="invoiceStatus">
						&nbsp;
					</logic:notPresent>
			</div>
		    </div>
			<div class="ic-foot-container paddR5">
			    <div class="ic-button-container">
				    <ihtml:nbutton property="btnFinalizeInv" accesskey="F" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_BTNFINALIZEINV">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.finalizeinvoice"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnWithdraw" accesskey="W" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_BTNWITHDRAW">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.withdraw"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnSurcharge" accesskey="S" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_BTNSURCHG">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.surcharge"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnViewAccount" accesskey="A" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNACC">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.viewaccentry"/>
					</ihtml:nbutton>
					
					<logic:notEqual name="form" property="reportSpecificFlag" value = "Y">
						<logic:notEqual name="form" property="restrictionFlag" value = "Y">
								<ihtml:nbutton property="btn51Print" accesskey="P" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btn66Print" accesskey="T" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
								</ihtml:nbutton>
								<logic:notEqual name="form" property="reportRestrictionFlag" value = "Y">
								<ihtml:nbutton property="btnInvPrint" accesskey="N" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNINV">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printInv"/>
								</ihtml:nbutton>
						</logic:notEqual>
						</logic:notEqual>
						<logic:equal name="form" property="restrictionFlag" value = "Y">
								<ihtml:nbutton property="btn51Print1" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btn66Print1" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
								</ihtml:nbutton>
						</logic:equal>
					</logic:notEqual>
					<ihtml:nbutton property="btnMca" accesskey="M" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_MCA_BTN">
								<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.mca"/>
					</ihtml:nbutton>
					<logic:equal name="form" property="reportSpecificFlag" value = "Y">
					   <logic:equal name="form" property="reportRestrictionFlag" value = "KE">
						<ihtml:nbutton property="btn51PrintKE" accesskey="P" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btn66PrintKE" accesskey="T" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
							</ihtml:nbutton>

						<ihtml:nbutton property="btnInvPrint" accesskey="N" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNINV">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printInv"/>
						</ihtml:nbutton>


						</logic:equal>
						<logic:equal name="form" property="reportRestrictionFlag" value = "SQ">
						<ihtml:nbutton property="btn51PrintSQ" accesskey="P" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btn66PrintSQ" accesskey="T" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnInvPrintSQ" accesskey="N" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNINV">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printInv"/>
						</ihtml:nbutton>
						

						</logic:equal>
						<logic:equal name="form" property="reportRestrictionFlag" value = "TK">
						<ihtml:nbutton property="btn51PrintTK" accesskey="P" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btn66Print" accesskey="T" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnInvPrintTK" accesskey="N" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNINV">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printInv"/>
						</ihtml:nbutton>

					</logic:equal>
						<logic:equal name="form" property="reportRestrictionFlag" value = "TP">
						<ihtml:nbutton property="btn51Print" accesskey="P" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN51">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn51"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btn66Print" accesskey="T" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNCN66">
							<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printcn66"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnInvPrint" accesskey="N" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_PRINT_BTNINV">
									<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.printInv"/>
						</ihtml:nbutton>

						</logic:equal>
					</logic:equal>
					
					
					
						<!--END FOR SQ SPECIFIC-->
					<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_GPABILLING_LISTCN51CN66_CLOSE_BTN">
						<common:message key="mailtracking.mra.gpabilling.listcn51cn66.btn.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
    </ihtml:form>
</div>
	</body>
</html>
