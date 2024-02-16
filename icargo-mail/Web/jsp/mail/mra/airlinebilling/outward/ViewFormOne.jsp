<%--/***********************************************************************
* Project	     	 	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ViewFormOne.jsp
* Date                 	 : 19-Jun-2008
* Author(s)              : A-3456
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO"%>

			


			
	
<html:html>
<head>
	
	
		
	
	
	<title>
		<common:message bundle="viewForm1" key="mailtracking.mra.airlinebilling.outward.viewform1.title"  scope="request"/>
	</title>
	<meta name="decorator" content="mainpanel">

	<common:include type="script" src="/js/mail/mra/airlinebilling/outward/ViewFormOne_Script.jsp"/>

</head>

<body>
	
	
	
		<bean:define id="form"
     			 name="ViewForm1Form"
     			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm"
  		         toScope="page" />

				 
	<div id="pageDiv" class="iCargoContent" style="overflow:auto;">
		<ihtml:form action="/mailtracking.mra.airlinebilling.outward.screenloadviewform1.do">

			<business:sessionBean id="FORM_ONE_VO"
	   	   	moduleName="mailtracking.mra.airlinebilling"
	   	   	screenID="mailtracking.mra.airlinebilling.outward.viewform1"
	   	   	method="get" attribute="formOneVO" />

	   		<business:sessionBean id="INVOICEFORM_ONE_VOS"
	   		moduleName="mailtracking.mra.airlinebilling"
	   		screenID="mailtracking.mra.airlinebilling.outward.viewform1"
			method="get" attribute="InvoiceFormOneDetailsVOs" />
			
			<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.airlinebilling.outward.viewform1.title" /></span>
					<div class="ic-filter-panel">
						<div class="ic-row ic-border">
							<div>
								<div class="ic-input ic-split-40 ">
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.clearanceperiod" />
									<ihtml:text property="clearancePeriod"
									  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_CLEARANCEPERIOD"
									  maxlength="10"/>
									<img src="<%=request.getContextPath()%>/images/lov.gif" id="clearancePeriodLOV" name="clearancePeriodlov" height="16" width="16" />
								</div>
								<div class="ic-input ic-split-30 ">
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.airlinecode" />
									<ihtml:text property="airlineCode"
									  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_AIRLINECODE"
									  maxlength="2"/>
									<img src="<%=request.getContextPath()%>/images/lov.gif" id="airlineCodeLov" height="16" width="16" />
								</div>
								<div class="ic-input ic-split-30 " id="viewFormoneParent">
									<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.airlinenumber" />
									<ihtml:text property="airlineNumber"
										  componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_AIRLINENO"
										  maxlength="3"/>
									<img src="<%=request.getContextPath()%>/images/lov.gif" id="airlineNumberLov" height="16" width="16" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineNumber.value,'Airline','1','airlineNumber','',0)" /> 
								</div>
								<div class = "ic-row">
									<div class="ic-button-container">
										<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_LIST" >
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.list" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_CLEAR" >
											<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.clear" />
										</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row ic-border">
						<div class="ic-input ic-split-25 ">
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.listingcurrency" />
							<common:display property="listingCurrency" name="form" />
						</div>
						<div class="ic-input ic-split-25 ">
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.exchangerate" />
							<logic:present name="FORM_ONE_VO" property="exchangeRateBillingCurrency">
								<bean:write name="FORM_ONE_VO" property="exchangeRateBillingCurrency" format="####.00000"/>
							</logic:present>        							      							 			 
						</div>
						<div class="ic-input ic-split-25 ">
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.billingcurrency" />
							<common:display property="billingCurrency" name="form" />
						</div>
						<div class="ic-input ic-split-25 ">
							<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.class" />
							<common:display property="tableClass" name="form" />
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-section ic-border">
							<div class="tableContainer" align="center" id="div1"  style="width:100%; height:300px">
								<table class="icargotable1" cellspacing="1px" width="100%">
									<thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="10%" class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.InvoiceNo" />
											</td>
											<td  width="10%" class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.InvoiceDate" />
											</td>
											<td  width="10%" class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.amtlistngcurrency" />
											</td>
											<td  width="19%" class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.amtbasecurrency" />
											</td>
											<td  width="20%" class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.amtbillingcurrency" />
											</td>
										</tr>
									</thead>
									<tbody>
										<logic:present name="INVOICEFORM_ONE_VOS">
											<logic:iterate id="iterator" name="INVOICEFORM_ONE_VOS" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO" indexId="rowId">
												<common:rowColorTag index="rowId">
													<tr bgcolor="<%=color%>">
														<td class="iCargoTableDataTd">
															<center>
																<logic:present  name="iterator" property="invoiceNumber">
																	<bean:write name="iterator" property="invoiceNumber"/>
																	<ihtml:hidden property="invoiceNumber" value="<%=iterator.getInvoiceNumber()%>"/>
																</logic:present>
																<logic:notPresent   name="iterator" property="invoiceNumber">
																	<ihtml:hidden property="invoiceNumber" value=""/>
																</logic:notPresent>
														</td>
														<td class="iCargoTableDataTd">
															<center>
																<logic:present  name="iterator" property="invoiceDate">
																	<bean:define id="invoiceDate" name="iterator" property="invoiceDate" />
																	<%
																		String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)invoiceDate).toCalendar(),"dd-MMM-yyyy");
																	%>
																	<%=assignedLocalDate%>
																</logic:present>
														<td class="iCargoTableDataTd">
															<center>
																<logic:present  name="iterator" property="listingTotAmount">
																	<bean:define id ="listingTotAmount" name="iterator" property="listingTotAmount" />
																	<%System.out.println("inside listingTotAmount"+listingTotAmount);%>																	
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  property="listingTotAmount"/>
																</logic:present>
																<logic:notPresent   name="iterator" property="listingTotalAmt">
																	<ihtml:hidden property="listingTotalAmt" value=""/>
																</logic:notPresent>
														</td>
														<td class="iCargoTableDataTd">
															<center>													 
																<logic:present  name="iterator" property="totalBaseAmount">																
																	<bean:define id ="totalBaseAmount" name="iterator" property="totalBaseAmount" />
																	<%System.out.println("inside totalBaseAmount"+totalBaseAmount);%>																	
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  property="totalBaseAmount"/>
																</logic:present>
																<logic:notPresent   name="iterator" property="totalBaseAmount">
																	<ihtml:hidden property="totalBaseAmount" value=""/>
																</logic:notPresent>				 
														</td>
														<td class="iCargoTableDataTd">
															<center>
																<logic:present  name="iterator" property="billingTotalAmt">																
																	<bean:define id ="billingTotalAmt" name="iterator" property="billingTotalAmt" />
																	<%System.out.println("inside billingTotAmt"+billingTotalAmt);%>																	
																	<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator"  property="billingTotalAmt"/>
																</logic:present>
																<logic:notPresent   name="iterator" property="billingTotalAmt">
																	<ihtml:hidden property="billingTotalAmt" value=""/>
																</logic:notPresent>
															</center>	
														</td>
													</tr>
												</common:rowColorTag>
											</logic:iterate>
										</logic:present>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="ic-row">					
						<div class="ic-button-container">
							<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_FORM1_CLOSE" >
								<common:message key="mailtracking.mra.airlinebilling.outward.viewform1.close" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
		</ihtml:form>
	</div>		


   
	</body>
</html:html>
