<%--
* Project	 		: iCargo
* Module Code & Name		: mailtracking.mra-airlinebilling-defaults
* File Name			: CaptureCN51.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2122
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO" %>
<%@ page import="java.util.Formatter" %>


<html:html locale="true">
<head>

		

<title>
	<bean:message bundle="capturecn51resources" key="mailtracking.mra.defaults.capturecn51.title.capturecn51" />
</title>

	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/CaptureCN51_Script.jsp" />

</head>


<body>

	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<business:sessionBean
  		id="KEY_ONETIMEVALUES"
  		moduleName="mailtracking.mra.airlinebilling.defaults"
  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
  		method="get"
  		attribute="oneTimeValues" />

<business:sessionBean
  		id="KEY_DETAILS"
  		moduleName="mailtracking.mra.airlinebilling.defaults"
  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
  		method="get"
  		attribute="cn51Details" />
		<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		        moduleName="mailtracking.mra.airlinebilling.defaults"
		        screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		        method="get"
		        attribute="weightRoundingVO" />

  <!--CONTENT STARTS-->


  <bean:define id="form" name="CaptureCN51Form"
  	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form"
	toScope="page" />


<div id="pageDiv" class="iCargoContent ic-masterbg">
	<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.capturecn51.screenload.do">

		<ihtml:hidden name="form" property="linkStatusFlag" />
		<ihtml:hidden name="form" property="statusFlag" />
		<ihtml:hidden name="form" property="screenFlag" />
		<ihtml:hidden name="form" property="deleteFlag" />
		<ihtml:hidden property="blgCurCode"/>
		<ihtml:hidden property="netLC"/>
		<ihtml:hidden property="netCP"/>
		<ihtml:hidden property="netSal"/>
		<ihtml:hidden property="netUld"/>
		<ihtml:hidden property="netSV"/>
		<ihtml:hidden property="netEMS"/>
		<ihtml:hidden  property="rowCount"/>
		<ihtml:hidden property="lastPageNumber" />
		<ihtml:hidden property="displayPageNum" />
		<ihtml:hidden property="screenFlg" />
		<ihtml:hidden property="currentDialogOption" />
		<ihtml:hidden property="currentDialogId" />
		<ihtml:hidden property="netChargeMoneyDisp" />
		<ihtml:hidden property="cn51Status"/>
		<bean:define id="netLCID" value="<%=String.valueOf(form.getNetLC())%>"/>
		<bean:define id="netCPID" value="<%=String.valueOf(form.getNetCP())%>"/>
		<bean:define id="netSalID" value="<%=String.valueOf(form.getNetSal())%>"/>
		<bean:define id="netUldID" value="<%=String.valueOf(form.getNetUld())%>"/>
		<bean:define id="netSVID" value="<%=String.valueOf(form.getNetSV())%>"/>
		<bean:define id="netEMSID" value="<%=String.valueOf(form.getNetEMS())%>"/>
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
			<common:message   key="mailtracking.mra.defaults.capturecn51.pagetitle.capturecn51"/>
			</span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row ic-label-50">
							<div class="ic-input ic-mandatory ic-split-25 ">
							
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.invRefNo"/></label>
								<ihtml:text property="invoiceRefNo" name="CaptureCN51Form" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_INVOICENO"  maxlength="18" />
								<div class="lovImg"><img name="invoicenolov" id="invoicenolov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ic-mandatory">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.clearanceperiod"/></label>
								<ihtml:text property="clearancePeriod" name="CaptureCN51Form" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CLEARANCEPERIOD"  maxlength="10" />
								<div class="lovImg"><img name="clearancePeriodlov" id="clearancePeriodlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ic-mandatory">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.airlinecode"/></label>
								<ihtml:text property="airlineCode" name="CaptureCN51Form" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AIRLINECODE"  maxlength="3" />
								<div class="lovImg"><img name="airlinelov" id="airlinelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.category"/></label>
								<ihtml:select property="category" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CATEGORY" value="<%=form.getCategory()%>">
									<logic:present name="KEY_ONETIMEVALUES">
										<html:option value=""></html:option>
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							</div>
							<div class="ic-row marginT5 ic-label-50">
							<div class="ic-input ic-split-25 ">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.carriageFrom"/></label>
								<ihtml:text property="carriageFrom" name="CaptureCN51Form" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGEFROMFILTER"  maxlength="4" />
								<div class="lovImg"><img name="carriageFromlov" id="carriageFromlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.carriageTo"/></label>
								<ihtml:text property="carriageTo" name="CaptureCN51Form" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGETOFILTER"  maxlength="4" />
						    	<div class="lovImg"><img name="carriageTolov" id="carriageTolov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-25 ">
								<label><common:message   key="mailtracking.mra.defaults.capturecn51.lbl.billingtype"/></label>
								<ihtml:select property="billingType" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_BILLINGTYPE" value="<%=form.getBillingType()%>">
									<logic:present name="KEY_ONETIMEVALUES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.mra.billingtype">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>	
							</div>
							
								<div class="ic-button-container paddR5">
									<ihtml:nbutton property="btList" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_LIST" accesskey="L">
										<common:message   key="mailtracking.mra.defaults.capturecn51.btn.lbl.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CLEAR" accesskey="C">
										<common:message   key="mailtracking.mra.defaults.capturecn51.btn.lbl.clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<fieldset class="ic-field-set" style="width:99%;" height="100%">
						<legend>
							<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.lbl.summary" />
						</legend>
						<div class="ic-row">
							<div class="ic-input ic-split-10 ">
								<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.lbl.totwt" />:
							</div>
							<div class="ic-input ic-split-20 " id="netSummaryWeight">	
								<common:write name="form" property="netWeight" unitFormatting="true" />
							</div>
							<div class="ic-input ic-split-10 ">
								<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.lbl.totchg" />
							</div>
							<div class="ic-input ic-split-20 " id="netCharge">	
								<common:display property="netChargeMoneyDisp" name="form"  format="####.00"/>
							</div>	
							<div class="ic-input ic-split-30 ">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.listingCurrency"/>
								<logic:present name="KEY_DETAILS">
									<logic:present name="form" property="blgCurCode">
										<common:display name="form" property="blgCurCode" />
									</logic:present>
								</logic:present>
							</div>
						</div>
					</fieldset>
					<div class="ic-input ic-split-100 "></div>
					<div class="ic-row iCargoHeadingLabel">
						<h4><common:message   key="mailtracking.mra.defaults.capturecn51.headinglabel.details"/></h4>
						<div class="ic-button-container paddR15">
							<a href="#" class="iCargoLink" name="modifyLink" id="modifyLink">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.addmodify"/>
							</a>
							
							<a href="#" class="iCargoLink" name="deleteLink" id="deleteLink">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.delete"/>
							</a>
						</div>
					</div>
					
					<logic:present name="KEY_DETAILS" property="cn51DetailsPageVOs">
						<bean:define id="CN51DETAILVOS" name="KEY_DETAILS" property="cn51DetailsPageVOs"/>
					</logic:present>	
					<div class="ic-col-70 paddL10">
						<logic:present name="KEY_DETAILS" property="cn51DetailsPageVOs">
							<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
								name="CN51DETAILVOS"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=((CaptureCN51Form)form).getLastPageNumber() %>" />
						</logic:present>
					   <logic:notPresent name="KEY_DETAILS" property="cn51DetailsPageVOs">
							&nbsp;
					   </logic:notPresent>
					</div>

					<div class="ic-col-30 ic-button-container paddR10">
						  <logic:present name="KEY_DETAILS" property="cn51DetailsPageVOs">
							  <common:paginationTag
								  pageURL="javascript:submitList('lastPageNum','displayPage')"
								  name="CN51DETAILVOS"
								  display="pages"
								  linkStyleClass="iCargoResultsLabel"
								  disabledLinkStyleClass="iCargoResultsLabel"
								  lastPageNum="<%=((CaptureCN51Form)form).getLastPageNumber() %>" 
								  exportToExcel="true"
								  exportTableId="Form-1Details"
								  exportAction="mailtracking.mra.airlinebilling.defaults.capturecn51.list.do"/>
						   </logic:present>
						  <logic:notPresent name="KEY_DETAILS" property="cn51DetailsPageVOs">
							 &nbsp;
						  </logic:notPresent>
					</div>
					<div class="tablecontainer" id="div1" style="height:505px;">
						<table class="fixed-header-table" id="Form-1Details" >
							<thead>
								<tr>
									<td class="iCargoTableHeader ic-center ic_inline_chcekbox" width="2%" rowspan="2">
										<input type="checkbox" name="selectAll" value="checkbox"
										 onclick="updateHeaderCheckBox(targetFormName, targetFormName.selectAll, targetFormName.select)"/></td>
									<td class="iCargoTableHeader" width="15%" colspan="2" >
										<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.carriage" /></td>
									<td class="iCargoTableHeader" width="8%" rowspan="2" >
										<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.category" /></td>
									<td    class="iCargoTableHeader" width="50%" colspan="4">
										<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.wt"/></td>
									<td   class="iCargoTableHeader" width="10%" rowspan="2">
										<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.rate"/></td>
									<td   class="iCargoTableHeader" width="15%" rowspan="2">
										<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.amount"/></td>
								</tr>
								<tr>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.capturecn51.tablehead.from" /></td>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.capturecn51.tablehead.to" /></td>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.lc/ao"/></td>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.cp"/></td>
							        <td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.sv"/></td>
									<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.ems"/></td>
							  </tr>
						</thead>
						<tbody  id="cn51DetailsTable">
							<logic:present name="KEY_DETAILS">
								<logic:present name="KEY_DETAILS" property="cn51DetailsPageVOs">
								  	<bean:define id="airlineCN51DetailsVOs" name="KEY_DETAILS" property="cn51DetailsPageVOs"/>
	  		  							<logic:iterate id="airlineCN51DetailsVO" name="airlineCN51DetailsVOs"
	  		  								type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO" indexId="rowCount">
											<logic:present name="airlineCN51DetailsVO" property="operationFlag">
												<ihtml:hidden name="airlineCN51DetailsVO" property="operationFlag"/>
											</logic:present>
											<logic:notPresent name="airlineCN51DetailsVO" property="operationFlag">
												<ihtml:hidden name="CaptureCN51Form" property="operationFlag" value=""/>
											</logic:notPresent>
											<ihtml:hidden property="sequenceNumber" name="airlineCN51DetailsVO"/>
											<% String subClassGroup=""; %>
											<logic:present	name="airlineCN51DetailsVO" property="mailsubclass">
												<bean:define id="mailsubclass" name="airlineCN51DetailsVO" property="mailsubclass" />
												<% subClassGroup=mailsubclass.toString(); %>
												<bean:define id="subClass" value="<%=subClassGroup%>"/>
											</logic:present>
											<logic:notEqual name="airlineCN51DetailsVO" property="operationFlag" value="D">
												<common:rowColorTag index="rowCount">
												<tr bgcolor="<%=color%>">
													<td align="center" class="iCargoTableDataTd ic-center ic_inline_chcekbox">
														<ihtml:checkbox property="select" onclick="toggleTableHeaderCheckbox('select',this.form.selectAll)"  value="<%=rowCount.toString()%>" />
													</td>
													<td align="center" >
														<logic:present name="airlineCN51DetailsVO" property="carriagefrom" >
															<bean:write name="airlineCN51DetailsVO" property="carriagefrom"/>
														</logic:present>
														<logic:notPresent name="airlineCN51DetailsVO" property="carriagefrom" >
															&nbsp;
														</logic:notPresent>
													</td>
													<td align="center" >
														<logic:present name="airlineCN51DetailsVO" property="carriageto" >
															<bean:write name="airlineCN51DetailsVO" property="carriageto"/>
														</logic:present>
														<logic:notPresent name="airlineCN51DetailsVO" property="carriageto" >
															&nbsp;
														</logic:notPresent>
													</td>
													<td align="center" >
														<logic:present name="airlineCN51DetailsVO" property="mailcategory" >
															<bean:write name="airlineCN51DetailsVO" property="mailcategory"/>
														</logic:present>
														<logic:notPresent name="airlineCN51DetailsVO" property="mailcategory" >
															&nbsp;
														</logic:notPresent>
													</td>
													<td align="center" >
														<logic:equal name="subClass" value="LC">
															<logic:present name="airlineCN51DetailsVO" property="totalweight" >
															  <common:write name="airlineCN51DetailsVO" property="totalweight" unitFormatting="true" />
																	
															</logic:present>
															<logic:notPresent name="airlineCN51DetailsVO" property="totalweight" >
																	&nbsp;
															</logic:notPresent>

														</logic:equal>
														<logic:notEqual name="subClass" value="LC">
														0.00
														</logic:notEqual>

													</td>
													<td align="center" >
														<logic:equal name="subClass" value="CP">
															<logic:present name="airlineCN51DetailsVO" property="totalweight" >
															  <common:write name="airlineCN51DetailsVO" property="totalweight" unitFormatting="true" />
																	
															</logic:present>
															<logic:notPresent name="airlineCN51DetailsVO" property="totalweight" >


															</logic:notPresent>

														</logic:equal>
														<logic:notEqual name="subClass" value="CP">
															0.00
														</logic:notEqual>
													</td>
													<td align="center" >
														<logic:equal name="subClass" value="SV">
																<logic:present name="airlineCN51DetailsVO" property="totalweight" >
																  <common:write name="airlineCN51DetailsVO" property="totalweight" unitFormatting="true" />
																		
																</logic:present>
																<logic:notPresent name="airlineCN51DetailsVO" property="totalweight" >


																</logic:notPresent>

															</logic:equal>
															<logic:notEqual name="subClass" value="SV">
																0.00
														</logic:notEqual>
													</td>
													<td align="center" >	
														<logic:equal name="subClass" value="EMS">
																<logic:present name="airlineCN51DetailsVO" property="totalweight" >
																		 <common:write name="airlineCN51DetailsVO" property="totalweight" unitFormatting="true" />
																</logic:present>
																<logic:notPresent name="airlineCN51DetailsVO" property="totalweight" >
																</logic:notPresent>
															</logic:equal>
															<logic:notEqual name="subClass" value="EMS">
																0.00
														</logic:notEqual>
													</td>													
													<td align="center" >	
													<%String rateStr="0.00";%>
													<% String FORMAT_STRING = "%1$-16.4f"; %>
													<logic:present	name="airlineCN51DetailsVO" property="applicablerate">
														<bean:define id="rate" name="airlineCN51DetailsVO" property="applicablerate"/>
														<% 
														rateStr =new Formatter().format(FORMAT_STRING,((Double)rate)).toString().trim();
														%>
														</logic:present>
														<%=rateStr%>
													</td>
													<td align="center" >
														<%String totalAmount="0.00";%>
														<logic:present name="airlineCN51DetailsVO" property="totalamountincontractcurrency" >
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="airlineCN51DetailsVO"  moneyproperty="totalamountincontractcurrency" property="totalamountincontractcurrency" />
														</logic:present>
														<logic:notPresent name="airlineCN51DetailsVO" property="totalamountincontractcurrency" >
															<%=totalAmount%>
														</logic:notPresent>
													</td>
												</tr>
											</common:rowColorTag>
										</logic:notEqual>
									</logic:iterate>
								</logic:present>
							</logic:present>
							<!-- template row starts-->
							<logic:present name="form" property="blgCurCode">
								<bean:define id="currencyCode" name="form" property="blgCurCode"/>
							</logic:present>
							<logic:notPresent name="airlineCN51DetailsVO" property="blgCurCode">
								<bean:define id="currencyCode" value=""/>
							</logic:notPresent>
							<%String currencyCode =(String) pageContext.getAttribute("currencyCode");%>
							<tr template="true" id="cn51DetailsTableRow" style="display:none">
								<td class="iCargoTableDataTd ic_inline_chcekbox ic-center">
									<html:checkbox property="select"/>
									<ihtml:hidden property="operationFlag" value="NOOP"/>
									<ihtml:hidden property="sequenceNumber" value="0"/>
								</td>
								<td class="iCargoTableDataTd">
									<ihtml:select property="categories" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CATEGORY">
										<logic:present name="KEY_ONETIMEVALUES">
											<html:option value=""></html:option>
											<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
															<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>">
																<%=(String)fieldDescription%>
															</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
								</td>
								<td class="iCargoTableDataTd">
								<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtLCAO" unitTxtName="wtLCAO" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHT" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="LC/AO"
																			unitValue="" style="background :'<%=color%>';text-align:right"
																			onchange="updateCpStatus()" indexId="rowCount" styleId="weight"  />
																		
																		
																		
																		</logic:present>
								</td>
								<td class="iCargoTableDataTd">
								<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtCP" unitTxtName="wtCP" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTCP" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="CP"
																			unitValue="" style="background :'<%=color%>';text-align:right"
																			onchange="updateCpStatus()" indexId="rowCount" styleId="weight"  />
																		
																		
																		
																		</logic:present>
								</td>
								<td class="iCargoTableDataTd">
								<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtSv" unitTxtName="wtSv" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTSV" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="SV"
																			unitValue="" style="background :'<%=color%>';text-align:right"
																			onchange="updateCpStatus()" indexId="rowCount" styleId="weight"  />
																		
																		
																		
																		</logic:present>
								</td>
											<td class="iCargoTableDataTd">
								<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtEms" unitTxtName="wtEms" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTEMS" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="EMS"
																			unitValue="" style="background :'<%=color%>';text-align:right"
																			onchange="updateCpStatus()" indexId="rowCount" styleId="weight"  />																	
																		</logic:present>
								</td>
								<td class="iCargoTableDataTd">
									<center>
										<ihtml:text  indexId="rowCount"   property="rate" value="1"
										onchange="change(this.rowCount)" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_RATE"/>
									</center>
								</td>
								<td class="iCargoTableDataTd">
									<ibusiness:moneyEntry currencyCode="<%=currencyCode%>" value="0" indexId="rowCount"  formatMoney="true"
										 componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AMOUNT" id="totalAmount"    property="totalAmount"
										 maxlength="20" style="text-align : right;border: 0px;background:"  />
								</td>
						   </tr>
							<!-- template row ends -->
						</tbody>
						<tfoot>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<div id="totlc" class="ic-left">
									<logic:notEqual name="netLCID" value="0.0">
										<common:write name="form" property="netLC" format="####.00"/>		
									</logic:notEqual>
									<logic:equal name="netLCID" value="0.0">
											0.00
									</logic:equal>
									</div>
								</td>
								<td>
									<div id="totcp" class="ic-left">
									<logic:notEqual name="netCPID" value="0.0">
										<common:write name="form" property="netCP" format="####.00"/>
									</logic:notEqual>
									<logic:equal name="netCPID" value="0.0">
											0.00
									</logic:equal>
									</div>
								</td>
								<td>
									<div id="totsv" class="ic-left">
									<logic:notEqual name="netSVID" value="0.0">
										<common:write name="form" property="netSV" format="####.00"/>	
									</logic:notEqual>
									<logic:equal name="netSVID" value="0.0">
											0.00
									</logic:equal>
									</div>
								</td>
								<td>
									<div id="totems" class="ic-left">
									<logic:notEqual name="netEMSID" value="0.0">
										<common:write name="form" property="netEMS" format="####.00"/>
									</logic:notEqual>
									<logic:equal name="netEMSID" value="0.0">
											0.00
									</logic:equal>
									</div>
								</td>								
								<td></td>
								<td id="netamt">
								<div class="ic-left">
									<ibusiness:moneyDisplay moneyproperty="netChargeMoney" showCurrencySymbol="false" property="netCharge" />
								</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-15 paddR5">
						<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.cn51status" scope="request"/>
						<logic:present name="KEY_DETAILS">
							<logic:present name="KEY_DETAILS" property="invStatus">
								<logic:present name="KEY_ONETIMEVALUES">
									<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="mailtracking.mra.despatchstatus">
											<bean:define id="parameterValues" name="oneTimeValue" property="value" />
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<logic:equal name="KEY_DETAILS" property="invStatus" value="<%=fieldValue.toString()%>">
														<ihtml:hidden property="invStatus" value="<%=fieldValue.toString()%>" />
														<common:display name="parameterValue" property="fieldDescription"/>
													</logic:equal>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
						   </logic:present>
						</logic:present>
					</div>
				</div>
			</div>
			<div class="ic-foot-container paddR5">
			<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnPrint"  componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_PRINT"  accesskey="P">
							<common:message  key="mailtracking.mra.defaults.capturecn51.btn.lbl.print"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btEnquiry" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_ENQUIRY" accesskey="N">
							<common:message   key="mailtracking.mra.defaults.capturecn51.btn.lbl.enquiry" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btSave" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_SAVE" accesskey="S">
							<common:message   key="mailtracking.mra.defaults.capturecn51.btn.lbl.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CLOSE" accesskey="O">
							<common:message   key="mailtracking.mra.defaults.capturecn51.btn.lbl.close" />
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
