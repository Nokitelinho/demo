<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : InvoiceSettlement.jsp
* Date                 	 : 23-Mar-2007
* Author(s)              : A-2408
*********************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO"%>
<bean:define id="form"
		 name="InvoiceSettlementForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm"
		 toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpabilling.invoicesettlement.title"/></title>
<common:include type="script" src="/js/mail/mra/gpabilling/InvoiceSettlement_Script.jsp" />
</head>
<body>	
<%@ include file="/jsp/includes/reports/printFrame.jsp"%>
<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="oneTimeVOs" />
<business:sessionBean id="INV_SETTLEMENT_VOS"
	  	moduleName="mailtracking.mra.gpabilling"
	  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="invoiceSettlementVOs" />
	<business:sessionBean id="GPA_SETTLEMENT_VOS"
	  	moduleName="mailtracking.mra.gpabilling"
	  	screenID="mailtracking.mra.gpabilling.invoicesettlement"
	method="get" attribute="GPASettlementVO" />
	<logic:present name="GPA_SETTLEMENT_VOS">
        <bean:define id="GPA_SETTLEMENT_VOS" name="GPA_SETTLEMENT_VOS" toScope="page" />
	</logic:present>
		

  <!--CONTENT STARTS-->
<div class="iCargoContent" id="contentdiv" style="overflow:auto;">
  <ihtml:form action="/mailtracking.mra.gpabilling.invoicesettlement.populategpaname.do">
 <ihtml:hidden property="screenStatus"/>
 <ihtml:hidden property="availableSettlement"/>
 <ihtml:hidden property = "lastPageNum" />
<ihtml:hidden property = "displayPage" />
	<ihtml:hidden property="opFlag" value="<%=form.getOpFlag()%>" />

 <div class="ic-border">
						<div class="ic-col-35">
							<fieldset class="ic-field-set">
								<legend><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpadetails"/></legend>
									<div class="ic-row ic-label-25">
										<div class="ic-input ic-mandatory ic-split-50">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpacode"/></label>
											<ihtml:text property="gpaCodeFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPACODE" maxlength="5"/>
				          			        <img src="<%=request.getContextPath()%>/images/lov.gif" id="gpacodelov" height="16" width="16" alt="" /></td>
											
										 </div>
												
										<div class="ic-input  ic-split-50">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.gpaname"/></label>	
										
										<ihtml:text property="gpaNameFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_GPANAME" maxlength="10"/>
										 </div>
									</div>
							</fieldset>
						</div>
				
						<div class="ic-col-35">
								<fieldset class="ic-field-set" >
									<legend ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicedetails"/></legend>
									<div class="ic-row ic-label-25">
										<div class="ic-input ic-split-50">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invrefno"/></label>
											<ihtml:text property="invRefNumberFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_INVNUM" maxlength="18" />
											<img src="<%=request.getContextPath()%>/images/lov.gif" id="invnumberlov" height="16" width="16" alt="" /></td>
									
										</div>
										<div class="ic-input ic-split-50">
											<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.invoicesatus"/></label>
								 <ihtml:select componentID="CMP_MRA_GPABILLING_SETTLEINV_SETTLESTATUS" property="invoiceStatusFilter">
												<ihtml:option value=""></ihtml:option>
												<logic:present name="KEY_ONETIMES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<logic:equal name="parameterCode" value="mailtracking.mra.invoicestatus">
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
									</div>
						</fieldset>
					</div>
							<div class="ic-col-30">
								<fieldset class="ic-field-set" >
									<legend ><common:message key="mailtracking.mra.gpabilling.invoicesettlement.billingperiod"/></legend>
										<div class="ic-row ic-label-25">
											<div class="ic-input ic-split-50">
												<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.fromdate"/></label>
										    <ibusiness:calendar
								     	property="fromDate"
								    	componentID="CMP_MRA_GPABILLING_SETTLEINV_FROMDATE"
						         	    type="image"
						         	    id="fromDate"
						                value="<%=form.getFromDate()%>"/>
											</div>
											<div class="ic-input ic-split-50">
												<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.todate"/></label>
													 <ibusiness:calendar
										property="toDate"
										componentID="CMP_MRA_GPABILLING_SETTLEINV_TODATE"
										type="image"
										id="toDate"
						                value="<%=form.getToDate()%>"/>

											</div>
										</div>		
									</fieldset>
								</div>
								<div class="ic-row ic-label-23">
									<div class="ic-input ic-split-20">
										<label><common:message key="mailtracking.mra.gpabilling.invoicesettlement.chequeno"/></label>
										<ihtml:text property="chequeNumberFilter" componentID="CMP_MRA_GPABILLING_SETTLEINV_CHEQUENOFILTER" />
									</div>
								
									<div class="ic-button-container">
										<ihtml:button property="btnList" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNLIST" >
										<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.list" />
					                        		</ihtml:button>

										<ihtml:button property="btnClear" componentID="CMP_MRA_GPABILLING_SETTLEINV_BTNCLEAR" >
										<common:message key="mailtracking.mra.gpabilling.invoicesettlement.btn.clear" />
					                        		</ihtml:button>
									</div>
								</div>
							</div>
	</ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>



