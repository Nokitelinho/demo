<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  DespatchEnquiry.jsp
* Date					:  24-June-2008
* Author(s)				:  A-2391,A-3108
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm"%>
  <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

<html:html>


 <head>
	
	

 	<title><common:message bundle="despatchEnquiry" key="mailtracking.mra.defaults.despatchequiry.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/defaults/DespatchEnquiry_Script.jsp"/>
 </head>

 <body>
	
	

		<bean:define id="form"
					name="DespatchEnquiryForm"
					type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm"
					toScope="page" />
 	<div id="mainDiv" class="<%=iCargoContentClass%> " style="overflow:auto;width:100%;height:100%">
 		<ihtml:form action="/mailtracking.mra.defaults.despatchenquiry.screenload.do" styleClass="ic-main-form">
 		<ihtml:hidden property="lovClicked" />
		<ihtml:hidden property="listed" />
		<ihtml:hidden property="blgBasis" />
		<ihtml:hidden property="closeFlag" />
		<ihtml:hidden property="fromScreen" />
  		<ihtml:hidden property="lastPageNumber" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="absIndex" />
		<ihtml:hidden property="pageNum" />
		<ihtml:hidden property="showDsnPopUp" />
        <input type="hidden" name="mySearchEnabled" />
	 	<business:sessionBean id="OneTimeValues"
			 	 	moduleName="mailtracking.mra.defaults"
				 	screenID="mailtracking.mra.defaults.despatchenquiry"
			 	  	method="get"
			  		attribute="OneTimeVOs" />
		<business:sessionBean id="GPABillingDtlVO"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
	     			 attribute="gPABillingDtls"/>
	    <business:sessionBean id="AirlineBillingDetailVO"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
     				 attribute="airlineBillingDetailsVO"/>
	     <business:sessionBean id="SectorRevenueDetailsVO"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
	     			 attribute="FlownDetails"/>
	     <business:sessionBean id="despatchEnquiryVOSession"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
	     			 attribute="despatchEnquiryVO"/>
		<business:sessionBean id="AccountingDetailVOs"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
	     			 attribute="AccountingDetails"/>
		 <business:sessionBean id="USPSReportingVOs"
	  	     	     moduleName="mailtracking.mra.defaults"
	  	     	     screenID="mailtracking.mra.defaults.despatchenquiry"
	  	     	     method="get"
	     			 attribute="USPSReportingDetails"/>
	     <business:sessionBean id="OutStandingBalances"
		 			moduleName="mailtracking.mra.defaults"
		 			screenID="mailtracking.mra.defaults.despatchenquiry"
		 			method="get"
	     			attribute="OutStandingBalances"/>
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">

					<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.heading" />
			</span>	
				<div class="ic-head-container">	
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row ">
								<div class="ic-row">
									<div class="ic-input ic-split-35 ic-label-40 ic-mandatory">
										<label>
								<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.despatchno" />
										</label>
										<ihtml:text property="despatchNum"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_DESPATCHNUM" maxlength="29" style="width:200px" />
                                    <div class="lovImg">
										<img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt=""/>
									</div>
									</div>
									<div class="ic-input ic-split-25 ic-label-35">
										<label> 
											<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.dsptchdate" />
										</label>
										<ibusiness:calendar
											 property="dsnFilterDate"
											 componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_DESPATCHDATE_FILTER"
											 type="image"
											 id="dsnFilterDate"/>
									</div>
									<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_LSTBTN" accesskey="L" >
										<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.button.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_CLEARBTN" accesskey="C" >
										<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.button.clear" />
									</ihtml:nbutton>
								</div>
								</div>
							</div>	
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row ic-border " style="width:99%;">
						<logic:present name="despatchEnquiryVOSession">
							<bean:define id="despatchEnqVO" name="despatchEnquiryVOSession" toScope="page"/>
						<div class="ic-row ic-inline-label"> <!-- Modified by A-8236 for ICRD-252668-->
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.condocno" />
								</label>
								<label class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="csgDocNo"/>
								</label>
							</div>
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.origin" />
								</label>	
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="origin"/>
								</label>
							</div>
							<div class="ic-input ic-split-25 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.dstn" />
								</label>
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="destn"/>
								</label>
							</div>
							<div class="ic-input ic-split-15 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.category" />
								</label>
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="category"/>
								</label>
							</div>
							<div class="ic-input ic-split-20 ic-label-35">
								<label>	
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.subclass" />
								</label>
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="subClass"/>
								</label>
							</div>
						</div>
						<div class="ic-row ic-inline-label">  						
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.stdpcs" />
								</label>
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="pieces"/>
								</label>
							</div>
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.stdwt" />
								</label>
								<label  class="ic-bold-label ic-left">
								<common:write name="despatchEnqVO" property="weight" unitFormatting="true" />
								</label>
							</div>
							<div class="ic-input ic-split-25 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.dsptchdate" />
								</label>
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="strDespatchDate"/>
								</label>
							</div>
							<div class="ic-input ic-split-35 ic-label-14">
								<label>	
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.route" />
								</label>	
								<label  class="ic-bold-label ic-left">
									<bean:write name="despatchEnqVO" property="route"/>
								</label>
							</div>
						</div>	
						</logic:present>
					</div>
					<div class="ic-row ic-border marginT10" style="width:99%;">
						<div class="ic-row">
							<div class="ic-input ic-split-30 ic-label-35">
							   <ihtml:select
									styleClass="iCargoVeryBigComboBox"
									componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_DESPTCHENQTYP"
									property="despatchEnqTyp"
									tabindex="30">
									<ihtml:option value=""/>
									<logic:present name="OneTimeValues">
										<logic:iterate id="oneTimeValue" name="OneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue"
												property="key" />
											<logic:equal name="parameterCode"
												value="mra.defaults.despatchenqtype">
												<bean:define id="parameterValues" name="oneTimeValue"
													property="value" />
												<logic:iterate id="parameterValue"
													name="parameterValues"
													type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue"
														property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue"
															property="fieldValue" />
														<bean:define id="fieldDescription"
															name="parameterValue" property="fieldDescription" />
														<ihtml:option
															value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
														</ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
							    </ihtml:select>
							</div>
						</div>
					</div>
					
					
					
					
					
					
					
					
					
					
						<logic:equal name="form" property="despatchEnqTyp" value="G">
					<div class="ic-row marginT10">
						<div class="ic-bold-label paddL5">
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.gpabillingheading" />
						</div>
					</div>	
					<div class="ic-row ic-border marginT10" style="width:99%;">
						<div class="ic-row">
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
												<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.gpacode" />
								</label>						
												<ihtml:text property="gpaCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_GPACODE" readonly="true"/>
							</div>
							<div class="ic-input ic-split-30 ic-label-35">
								<label>
												<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.gpaname" />
								</label>						
												<ihtml:text property="gpaName" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_GPANAME" readonly="true"/>
							</div>								
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
												<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.currency" />
								</label>	
												<ihtml:text property="currency" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_CURRENCY" readonly="true"/>
							</div>	
							<div class="ic-input ic-split-20 ic-label-35">
								<label>
												<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.finyr" />
								</label>	
												<ihtml:text property="finYear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_FINYEAR" readonly="true"/>
							</div>	
						</div>
					</div>	
					<div class="ic-row marginT10">
						<div  class="tableContainer table-border-solid" id="div1" style="height:425px;overflow:auto;"><!--modified by A-7371 for ICRD-249083-->
							<table class="fixed-header-table" id="despatchequiry">	
											<thead>
											<tr class="ic-th-all">
												<th style="width:16%;"/>
												<th  style="width:8%;"/>
												<th  style="width:7%;"/>
												<th style="width:6%;"/>
												<th style="width:6%;"/>
												<th style="width:8%;"/>
												<th style="width:5%;"/>
												<th style="width:6%;"/>
												<th  style="width:6%;"/>
												<th  style="width:7%;"/>
												<th  style="width:6%;"/>
												<th style="width:6%;"/>
												<th  style="width:4%;"/>
												<th  style="width:9%;"/>
											</tr>
												<tr>
													<td class="Icargotableheaderlabel ic-middle"  rowspan="2" >
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.despatchnum" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle"  rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.ccanum" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.billingstatus" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle"  colspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.bilprd" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.invoicenum" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.invoicedate" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.mailchg" />
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.surchg" />
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.grossamt" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.tax" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.netamt" />
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.curr" />
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.invoicestatus" /><span></span>
													</td>
												<!--	<td class="iCargoTableHeader" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.rate" /><span></span>
													</td>
													<td class="iCargoTableHeader" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.wtcharges" /><span></span>
													</td> -->
												</tr>
												<tr>
													<td class="iCargoTableHeader">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.frm" />
													</td>
													<td class="iCargoTableHeader" >
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.to" />
													</td>
												</tr>
											</thead>
											<tbody>
												<logic:present name="GPABillingDtlVO">
													<logic:iterate id="iterator" name="GPABillingDtlVO" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO" indexId="rowCount">
									
										<tr>
																<td>
																	<logic:present	name="iterator" property="despatchId">
																		<bean:write name="iterator" property="despatchId" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="ccaNo">
																		<bean:write name="iterator" property="ccaNo" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="billingStatus">
																		<bean:write name="iterator" property="billingStatus" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="blgPrdFrm">
																		<bean:write name="iterator" property="blgPrdFrm" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="blgPrdTo">
																		<bean:write name="iterator" property="blgPrdTo" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="invoiceNumber">
																		<bean:write name="iterator" property="invoiceNumber" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="invDate">
																		<bean:write name="iterator" property="invDate" />
																	</logic:present>
																</td>
											<td >
															
																<logic:present	name="iterator" property="amountBillable">
																		<bean:write name="iterator" property="amountBillable" />
																	</logic:present>
																</td>
											<td>
																	
																	<logic:present	name="iterator" property="surchargeAmtBillable">
																		<bean:write name="iterator" property="surchargeAmtBillable" />
																	</logic:present>
																</td>
											<td>
																	<logic:present	name="iterator" property="grossAmount">
																		<bean:write name="iterator" property="grossAmount" />
																	</logic:present>
																	
																</td>
											<td>
																	<logic:present	name="iterator" property="taxAmount">
																		<bean:write name="iterator" property="taxAmount" />
																	</logic:present>
																	
																</td>
											<td>
																<logic:present	name="iterator" property="netAmount">
																		<bean:write name="iterator" property="netAmount" />
																	</logic:present>
																	
																</td>
											<td>
												<logic:present	name="iterator" property="currencyCode">
																		<bean:write name="iterator" property="currencyCode" />
																	</logic:present>
																</td>
																<td>
																	<logic:present	name="iterator" property="invStatus">
																		<bean:write name="iterator" property="invStatus" />
																	</logic:present>
																</td>
														<!--		<td>
																	<logic:present	name="iterator" property="applicableRate">

								<logic:lessEqual name="applicableRate" value="0">
								<bean:write name="iterator" property="applicableRate" format="0.0000"/>
								</logic:lessEqual>

								<logic:greaterThan name="applicableRate"  value="0">
								<bean:write name="iterator" property="applicableRate" format="####.0000"/>
								</logic:greaterThan>



																	</logic:present>
																</td>
											<td>
																	<logic:present	name="iterator" property="amountBillable">
																		<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="amountBillable" property="amountBillable" />
																	</logic:present>
																</td> -->
															</tr>

													</logic:iterate>
												</logic:present>
											</tbody>
										</table>
									</div>
			</div>					
						</logic:equal>
						
						
						
						
						
						
						
					     <logic:equal name="form" property="despatchEnqTyp" value="A">
			<div class="ic-row">
				<div class="ic-bold-label">				    
									<common:message  key="mailtracking.mra.defaults.despatchequiry.lbl.accountingheading"/>
				</div>			       
			</div>					  
			<div class="ic-row">
				<div class="ic-col-60 ">

									  <logic:present name="AccountingDetailVOs">
								      <bean:define id="voFromSession" name="AccountingDetailVOs"/>
										 <logic:present name="voFromSession">
											<common:paginationTag pageURL="mailtracking.mra.defaults.despatchenquiry.accountingdetails.do"
											name="voFromSession"
											display="label"
											labelStyleClass="iCargoResultsLabel"
											lastPageNum="<%=((DespatchEnquiryForm)form).getLastPageNumber() %>"/>
										 </logic:present>
				</div>
				 <div class="ic-button-container" >
									<logic:present name="voFromSession">
									<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
									name="voFromSession"
									display="pages"
									linkStyleClass="iCargoResultsLabel"
									disabledLinkStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNumber()%>"
									exportToExcel="true"
									exportTableId="despatchEnquiryTable"
									exportAction="mailtracking.mra.defaults.despatchenquiry.accountingdetails.do"/>

									</logic:present>
									<logic:notPresent name="voFromSession">
									&nbsp;
									</logic:notPresent>
									</logic:present>
				</div>
			</div>					
			<div class="ic-row">
				<div  class="tableContainer" id="div2" style="height:500px;overflow:auto;">
					<table class="fixed-header-table" id="despatchEnquiryTable">									
						   					<thead>
						   						<tr>
						   							<td class="iCargoTableHeader"  rowspan="2" width="10%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.accid" /><span></span>
						   							</td>
						   							<td class="iCargoTableHeader"  rowspan="2" width="5%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fnpoint" /><span></span>
													</td>
						   							<td class="iCargoTableHeader" rowspan="2" width="7%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.accmonth" /><span></span>
													</td>
						   							<td class="iCargoTableHeader"  rowspan="2" width="18%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.accstring" /><span></span>
													</td>
						   							<td class="iCargoTableHeader" rowspan="2" width="8%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.accountname" /><span></span>
													</td>
						   							<td class="iCargoTableHeader" rowspan="2" width="5%">
													<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.debit" />
													<logic:present name="form" property="currency">
													<bean:define id="currency" name="form" property="currency"/>
													(<%= currency%> )
													</logic:present>
													<span></span>
													</td>
						   							<td class="iCargoTableHeader" rowspan="2" width="5%">
													<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.credit" />
													<logic:present name="form" property="currency">
													<bean:define id="currency" name="form" property="currency"/>
													(<%= currency%> )
													</logic:present>
													<span></span>
													</td>
						   							<td class="iCargoTableHeader" rowspan="2" width="20%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.details" /><span></span>
													</td>
						   						</tr>
						   					</thead>
											<tbody>
											<logic:present name="AccountingDetailVOs">
													<logic:iterate id="vo" name="AccountingDetailVOs" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO" indexId="rowCount">
										
										<tr>
															<td>
																<logic:present	name="vo" property="accEntryId">
																	<bean:write name="vo" property="accEntryId" />
																</logic:present>
																<logic:notPresent name="vo" property="accEntryId">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="vo" property="functionPoint">
																	<bean:write name="vo" property="functionPoint" />
																</logic:present>
																<logic:notPresent name="vo" property="functionPoint">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="vo" property="accountingMonth">
																	<bean:write name="vo" property="accountingMonth" />
																</logic:present>
																<logic:notPresent name="iterator" property="accountingMonth">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="vo" property="accountingString">
																	<bean:write name="vo" property="accountingString" />
																</logic:present>
																<logic:notPresent name="vo" property="accountingString">
																</logic:notPresent>
															</td>

															<td>
																<logic:present	name="vo" property="accountName">
																	<bean:write name="vo" property="accountName" />
																</logic:present>
																<logic:notPresent name="vo" property="accountName">
																</logic:notPresent>
															</td>
											<td>
																<logic:present	name="vo" property="debitAmount">
																	<ibusiness:moneyDisplay  name="vo" property="debitAmount" showCurrencySymbol="false"/>
																</logic:present>
																<logic:notPresent name="vo" property="debitAmount">
																</logic:notPresent>
															</td>
											<td>
																<logic:present	name="vo" property="creditAmount">
																	<ibusiness:moneyDisplay  name="vo" property="creditAmount" showCurrencySymbol="false"/>
																</logic:present>
																<logic:notPresent name="vo" property="creditAmount">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="vo" property="details">
																	<bean:write name="vo" property="details" />
																</logic:present>
																<logic:notPresent name="vo" property="details">
																</logic:notPresent>
															</td>
														</tr>
										
													</logic:iterate>
												</logic:present>
											</tbody>
										</table>
					   				</div>
							</div>
						</logic:equal>
					
					
					
					
					
					
					
					
					
					
						<logic:equal name="form" property="despatchEnqTyp" value="F">
				<div class="ic-row">
					<div class="ic-bold-label">
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.flownheading" />
					</div>
				</div>
				<div class="ic-row">
					<div  class="tableContainer" id="div3" style="height:500px;">
						<table class="fixed-header-table" >
											<thead>
												<tr>
													<td class="Icargotableheaderlabel ic-middle"  rowspan="2" >
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.sector" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle"  colspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.carried" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fltno" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle"  rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fltdate" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2" width="10%">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fltsecrev" />
													<logic:present name="form" property="currency">
													<bean:define id="currency" name="form" property="currency"/>
													(<%= currency%> )
													</logic:present>
														<span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fltsecsta" /><span></span>
													</td>
													<td class="Icargotableheaderlabel ic-middle" rowspan="2">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.fltstat" /><span></span>
													</td>
												</tr>
												 <tr>
													<td class="Icargotableheaderlabel ic-middle">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.wt" />
													</td>
													<td class="Icargotableheaderlabel ic-middle">
														<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.pcs" />
													</td>
												</tr>
											</thead>
											<tbody>
												<logic:present name="SectorRevenueDetailsVO">
													<logic:iterate id="iterator" name="SectorRevenueDetailsVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO" indexId="rowCount">
											
											<tr>
															<td>
																<logic:present	name="iterator" property="sector">
																	<bean:write name="iterator" property="sector" />
																</logic:present>
																<logic:notPresent name="iterator" property="sector">
																</logic:notPresent>
															</td>
												<td>
																<logic:present	name="iterator" property="grossWeight">
																<common:write name="iterator" property="grossWeight" unitFormatting="true" />
																	
																</logic:present>
																<logic:notPresent name="iterator" property="grossWeight">
																</logic:notPresent>
															</td>
												<td>
																<logic:present	name="iterator" property="pieces">
																	<bean:write name="iterator" property="pieces" />
																</logic:present>
																<logic:notPresent name="iterator" property="pieces">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="iterator" property="flightNumber">
																	<bean:write name="iterator" property="flightNumber" />
																</logic:present>
																<logic:notPresent name="iterator" property="flightNumber">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="iterator" property="flightDate">
																	<bean:define id="flightDate" name="iterator" property="flightDate" />
																	<% String fltDate = TimeConvertor.toStringFormat(((LocalDate)flightDate).toCalendar(),"dd-MMM-yyyy"); %>
																				    <%=fltDate%>
																</logic:present>
																<logic:notPresent name="iterator" property="flightDate">&nbsp;
																</logic:notPresent>
															</td>
												<td>
																<logic:present	name="iterator" property="netRevenue">
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="netRevenue" property="netRevenue" />
																</logic:present>
																<logic:notPresent name="iterator" property="netRevenue">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="iterator" property="flightSegmentStatus">
																	<bean:write name="iterator" property="flightSegmentStatus" />
																</logic:present>
																<logic:notPresent name="iterator" property="flightSegmentStatus">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="iterator" property="flightStatus">
																	<bean:write name="iterator" property="flightStatus" />
																</logic:present>
																<logic:notPresent name="iterator" property="flightStatus">
																</logic:notPresent>
															</td>
														</tr>
											
													</logic:iterate>
												</logic:present>
											</tbody>
										</table>
									</div>
					</div>
						</logic:equal>
						
						
						
						
						
						
						
						
						
						
						
						
						
					    <logic:equal name="form" property="despatchEnqTyp" value="I">
			<div class="ic-row">
				<div class="ic-bold-label">
					<common:message key="mailtracking.mra.defaults.interlinebilling.title" /></td>
				</div>
			</div>
			<div class="ic-row">
				<div  class="tableContainer" id="div4" style="height:680px;overflow:auto;">
					<table class="fixed-header-table">
											<thead>
												<tr class="iCargoTableHeadingLeft">
													<td colspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.carriage" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%" rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.carrier" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%" rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.iblorobl" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%" rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.clearanceperiod" />" style="width:100px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%" rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.invoiceno" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.invoicedate" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td colspan="4">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.wt" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.rate" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%" rowspan="2">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.amount" />" style="width:100px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%" rowspan="2">
													  	<input type="text" class="iCargoTableHeader" value="Dispatch Status" style="width:100px; padding-top:3px;" readonly="true" /><span></span>
													</td>
												</tr>
												<tr>
													<td >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.from" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%" >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.to" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													 </td>
													<td >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.lcorao" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%" >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.cp" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													 </td>
													<td width="10%" >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.uld" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													 </td>
													 <td >
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.interlinebilling.sv" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
												</tr>
											</thead>
											<tbody>
												<logic:present name="AirlineBillingDetailVO">
												    <logic:iterate id="iterator" name="AirlineBillingDetailVO" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO" indexId="rowCount">
									
										<tr>
																<td>
																	<logic:present	name="iterator" property="originOfficeOfExchange">
																		<bean:write name="iterator" property="originOfficeOfExchange"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="originOfficeOfExchange">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="destinationOfficeOfExchange">
																		<bean:write name="iterator" property="destinationOfficeOfExchange"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="destinationOfficeOfExchange">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="carrierCode">
																		<bean:write name="iterator" property="carrierCode"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="carrierCode">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="interLineBillingType">
																	<logic:equal name="iterator" property="interLineBillingType" value="I">
																		IBL
																	</logic:equal>
																	<logic:equal name="iterator" property="interLineBillingType" value="O">
																		OBL
																	</logic:equal>
																	</logic:present>
																	<logic:notPresent name="iterator" property="interLineBillingType">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="clearancePeriod">
																		<bean:write name="iterator" property="clearancePeriod"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="clearancePeriod">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="invoicenumber">
																		<bean:write name="iterator" property="invoicenumber"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="invoicenumber">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="invoiceDate">
																	<bean:define id="invoiceDate" name="iterator" property="invoiceDate" />
																	<%  String invdate = TimeConvertor.toStringFormat(((LocalDate)invoiceDate).toCalendar(),"dd-MMM-yyyy"); %>
																					    <%=invdate%>
																	</logic:present>
																	<logic:notPresent name="iterator" property="invoiceDate">&nbsp;
																	</logic:notPresent>
																</td>
																<logic:present	name="iterator" property="mailSubclass">
																	<logic:equal name="iterator" property="mailSubclass" value="LC">
													<td><common:write name="iterator" property="receivedWeight" unitFormatting="true" />
																		</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																	</logic:equal>
																	<logic:equal name="iterator" property="mailSubclass" value="AO">
													<td><common:write name="iterator" property="receivedWeight" unitFormatting="true" />
																		</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																	</logic:equal>
																	<logic:equal name="iterator" property="mailSubclass" value="CP">
																		<td>&nbsp;</td>
													<td><common:write name="iterator" property="receivedWeight" unitFormatting="true" />
																		</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																	</logic:equal>
																	<logic:equal name="iterator" property="mailSubclass" value="ULD">
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
													<td><common:write name="iterator" property="receivedWeight" unitFormatting="true" />
																		</td>
																		<td>&nbsp;</td>
																	</logic:equal>
																	<logic:equal name="iterator" property="mailSubclass" value="SV">
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
													<td><common:write name="iterator" property="receivedWeight" unitFormatting="true" />
																		</td>
																	</logic:equal>
																</logic:present>
																<logic:notPresent name="iterator" property="mailSubclass">
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																</logic:notPresent>
											<td>
																	<logic:present	name="iterator" property="applicablerate">
																		<bean:write name="iterator" property="applicablerate" format="####.0000"/>
																	</logic:present>
																	<logic:notPresent name="iterator" property="applicablerate">
																	</logic:notPresent>
																</td>
											<td>
																	<logic:present	name="iterator" property="billableAmount">
																		<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  moneyproperty="billableAmount" property="billableAmount" />
																	</logic:present>
																	<logic:notPresent name="iterator" property="billableAmount">
																	</logic:notPresent>
																</td>
																<td>
																	<logic:present	name="iterator" property="billingStatus">
																		<logic:present name="OneTimeValues">
																			<logic:iterate id="oneTimeValue" name="OneTimeValues">
																				<bean:define id="parameterCode" name="oneTimeValue" property="key" />
																				<logic:equal name="parameterCode" value="mra.airlinebilling.billingstatus">
																					<bean:define id="parameterValues" name="oneTimeValue" property="value" />
																					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																						<logic:present name="parameterValue">
																							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																							<logic:equal name="iterator" property="billingStatus" value="<%=(String)fieldValue%>">
																								<%=(String)fieldDescription%>
																							</logic:equal>
																						</logic:present>
																					</logic:iterate>
																				</logic:equal>
																			</logic:iterate>
																		</logic:present>
																	</logic:present>
																	<logic:notPresent name="iterator" property="billingStatus">
																	</logic:notPresent>
																</td>
															</tr>
									
													</logic:iterate>
												</logic:present>
											</tbody>
										</table>
									</div>
			</div>
					    </logic:equal>
					
					
					
					
					
					
					   	<logic:equal name="form" property="despatchEnqTyp" value="U">
			<div class="ic-row">
				<div class="ic-bold-label">
					<common:message key="mailtracking.mra.defaults.uspsreporting.title" /></td>
				</div>
			</div>
			<div class="ic-row">
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:message key="mailtracking.mra.defaults.uspsreporting.pacode" />
							</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:display property="paCode" name="form" />
							</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:message key="mailtracking.mra.defaults.uspsreporting.paname" />
							</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:display property="paName" name="form" />
							</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
							
												<common:display property="country" name="form" />
							</label>
							</div>
						</div>
						<div class="ic-row">
						<div>&nbsp</div>
						</div>
						<div class="ic-row">
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:message key="mailtracking.mra.defaults.uspsreporting.financialyear" />
						</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:display property="financialYear" name="form" />
						</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:message key="mailtracking.mra.defaults.uspsreporting.billingcurrency" />
						</label>
						</div>
						<div class="ic-input ic-split-20 ic-label-35">
							<label>
												<common:display property="billingCurrency" name="form" />
						</label>
						</div>
					</div>
					<div class="ic-row">
						<div  class="tableContainer" id="div5" style="height:680px;overflow:auto;">
							<table class="fixed-header-table">
											<thead>
												<tr class="iCargoTableHeadingLeft">
													<td width="10%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.dispatchno" />" style="width:105px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.secorg" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.secdest" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.gcm" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="2%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.basetotamt" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.lhdollarrte" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.lhdollarrte" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.invoiceno" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.invoicedate" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td width="10%">
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.uspsreporting.reconciliationstatus" />" style="width:120px; padding-top:3px;" readonly="true" /><span></span>
													</td>
												</tr>
											</thead>
											<tbody>

											<logic:present name="USPSReportingVOs">
													<logic:iterate id="uspsVO" name="USPSReportingVOs" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO" indexId="rowCount">
												
												<tr>
															<td>
																<logic:present	name="uspsVO" property="dsnNumber">
																	<bean:write name="uspsVO" property="dsnNumber" />
																</logic:present>
																<logic:notPresent name="uspsVO" property="dsnNumber">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="uspsVO" property="sectorOrigin">
																	<bean:write name="uspsVO" property="sectorOrigin" />
																</logic:present>
																<logic:notPresent name="uspsVO" property="sectorOrigin">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="uspsVO" property="sectorDestiantion">
																	<bean:write name="uspsVO" property="sectorDestiantion" />
																</logic:present>
																<logic:notPresent name="iterator" property="sectorDestiantion">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="uspsVO" property="GCM">
																	<bean:write name="uspsVO" property="GCM" />
																</logic:present>
																<logic:notPresent name="uspsVO" property="GCM">
																</logic:notPresent>
															</td>

													<td>
																<logic:present	name="uspsVO" property="baseTotalAmt">
																     <ibusiness:moneyDisplay  name="uspsVO" property="baseTotalAmt"/>
																</logic:present>
																<logic:notPresent name="uspsVO" property="baseTotalAmt">
																</logic:notPresent>
															</td>
													<td>
																<logic:present	name="uspsVO" property="lhDollarRate">
																   <bean:write name="uspsVO" property="lhDollarRate"/>
																</logic:present>
																<logic:notPresent name="uspsVO" property="lhDollarRate">
																</logic:notPresent>
															</td>
													<td>
																<logic:present	name="uspsVO" property="thDollarRate">
																    <bean:write name="uspsVO" property="thDollarRate"/>
																</logic:present>
																<logic:notPresent name="uspsVO" property="thDollarRate">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="uspsVO" property="invoiceNumber">
																	<bean:write name="uspsVO" property="invoiceNumber" />
																</logic:present>
																<logic:notPresent name="uspsVO" property="invoiceNumber">
																</logic:notPresent>
															</td>
															<td>
																<logic:present	name="uspsVO" property="invoiceDate">
																	<bean:define name="uspsVO" property="invoiceDate" id="invoiceDate"/>
																<% String date = TimeConvertor.toStringFormat(((LocalDate)invoiceDate).toCalendar(),"dd-MM-yyyy "); %>
																<%=date%>
																</logic:present>
																<logic:notPresent name="uspsVO" property="invoiceDate">
																</logic:notPresent>
															</td>

															<td>
																<logic:present	name="uspsVO" property="recStatus">
																	<bean:write name="uspsVO" property="recStatus" />
																</logic:present>
																<logic:notPresent name="uspsVO" property="recStatus">
																</logic:notPresent>
															</td>
														</tr>
												
													</logic:iterate>
												</logic:present>

											</tbody>
										</table>
					     			</div>
						</div>
						</logic:equal>
					
					
					
						<logic:equal name="form" property="despatchEnqTyp" value="R">
						<div class="ic-row">
							<div class="ic-bold-label">
								<common:message key="mailtracking.mra.defaults.remarks.title" /></td>
							</div>
						</div>
							<div class="ic-row">
								<div  class="tableContainer" id="div6" style="height:680px;overflow:auto;">
									<table class="fixed-header-table">
											<thead>
												<tr class="iCargoTableHeadingLeft">
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.remarks.functionpoint" />" style="width:90px; padding-top:3px;" readonly="true" /><span></span>
													</td>
													<td>
														<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.remarks.remarks" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
													</td>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
							</div>
						</logic:equal>
					
					
					
					
					
					<logic:equal name="form" property="despatchEnqTyp" value="O">
								<div class="ic-row">
										<div class="ic-bold-label">
											<common:message key="mailtracking.mra.defaults.outstandingbalances.title" /></td>
										</div>
								</div>
								<div class="ic-row">
									<div  class="tableContainer" id="div7" style="height:680px;overflow:auto;">
										<table class="fixed-header-table">
						<thead>
						<tr>
						<td>
						<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.outstandingbalances.accountname" />" style="width:100px; padding-top:3px;" readonly="true" /><span></span>
						</td>
						<td>
						<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.outstandingbalances.accountstring" />" style="width:100px; padding-top:3px;" readonly="true" /><span></span>
						</td>
						<td>
						<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.outstandingbalances.debit" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
						</td>
						<td>
						<input type="text" class="iCargoTableHeader" value="<common:message key="mailtracking.mra.defaults.outstandingbalances.credit" />" style="width:75px; padding-top:3px;" readonly="true" /><span></span>
						</td>
						</tr>
						</thead>
						<tbody>

						<logic:present name="OutStandingBalances">
						<logic:iterate id="outStandingBalanceVO" name="OutStandingBalances" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO" indexId="rowCount">
											
											<tr>
						<td>
						<logic:present  name="outStandingBalanceVO" property="accountName">
						<bean:write name="outStandingBalanceVO" property="accountName" />
						</logic:present>
						<logic:notPresent name="outStandingBalanceVO" property="accountName">
						</logic:notPresent>
						</td>
						<td>
						<logic:present  name="outStandingBalanceVO" property="accountString">
						<bean:write name="outStandingBalanceVO" property="accountString" />
						</logic:present>
						<logic:notPresent name="outStandingBalanceVO" property="accountString">
						</logic:notPresent>
						</td>
						<td>
						<logic:present  name="outStandingBalanceVO" property="debit">
						<bean:write name="outStandingBalanceVO" property="debit" />
						</logic:present>
						<logic:notPresent name="outStandingBalanceVO" property="debit">
						</logic:notPresent>
						</td>
						<td>
						<logic:present  name="outStandingBalanceVO" property="credit">
						<bean:write name="outStandingBalanceVO" property="credit" />
						</logic:present>
						<logic:notPresent name="outStandingBalanceVO" property="credit">
						</logic:notPresent>
						</td>
						</tr>
											
						</logic:iterate>
						</logic:present>



						</tbody>
						</table>
						</div>
								</div>
					</logic:equal>
					
					
					
						<logic:notEqual name="form" property="despatchEnqTyp" value="R">
									<div class="ic-row">
										<div class="ic-input ic-split-25 ic-label-35">	
											<label>
												<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.remarks" />
													
											</label>					
												<ihtml:textarea property="remarks" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_REMARKS"  cols="60" rows="3" readonly="true">
												</ihtml:textarea>
										</div>
									</div>
						</logic:notEqual>
					
					 
					
					
						<logic:equal name="form" property="despatchEnqTyp" value="R">
								<ihtml:hidden property="remarks"/>
							</logic:equal>
				</div>
				<div class="ic-foot-container">						
					<div class="ic-row">
						<div class="ic-button-container">
									<ihtml:nbutton property="btnViewProration" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_VIEWPRORATIONBTN" accesskey="V" >
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.button.viewproraton" />
									</ihtml:nbutton>

									<ihtml:nbutton property="btnActions" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_ACTIONSBTN" accesskey="A" >
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.button.actions" />
									</ihtml:nbutton>

									<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHENQ_CLOSEBTN" accesskey="O" >
									<common:message key="mailtracking.mra.defaults.despatchequiry.lbl.button.close" />
									</ihtml:nbutton>
								</div>
					</div>
				</div>
</div>			
	</ihtml:form>
	</div>

	
	</body>

 </html:html>
