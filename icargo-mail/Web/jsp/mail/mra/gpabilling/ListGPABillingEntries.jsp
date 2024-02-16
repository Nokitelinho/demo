<%--/***********************************************************************
* Project	     	     : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ListGPABillingEntries.jsp
* Date                 	 : 16-Jan-2007
* Author(s)              : A-2408
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm" %>
<%@ page import="com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.GPABillingEntriesSessionImpl" %>


<bean:define id="form"
		 name="GPABillingEntriesForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm"
		 toScope="page" />

			
	
<html:html>
<head>

	
		
	  <%@ include file="/jsp/includes/customcss.jsp" %> 
	
		
	

	

<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpabilling.GPABillingEntries.title" /></title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/mail/mra/gpabilling/ListGPABillingEntries_Script.jsp" />
</head>

<body>
	
	
	
	

	

<business:sessionBean id="GPABillingDetailsVO"
	     moduleName="mailtracking.mra.gpabilling"
	     screenID="mailtracking.mra.gpabilling.billingentries.listgpabillingentries"
	     method="get"
	     attribute="gpaBillingDetails"/>
<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.billingentries.listgpabillingentries"
	method="get" attribute="oneTimeVOs" />

	<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.gpabilling"
  	screenID="mailtracking.mra.gpabilling.billingentries.listgpabillingentries"
	method="get" attribute="systemparametres" />
	
<div id="pageDiv" class="iCargoContent ic-masterbg">
<ihtml:form action="/mailtracking.mra.gpabilling.billingentries.onScreenLoad.do">

<ihtml:hidden property="screenStatus"/>
<ihtml:hidden property="lastPageNum"/>
<ihtml:hidden property="displayPage"/>
<ihtml:hidden property="selectedRow"/>
<input type="hidden" name="mySearchEnabled" />
<ihtml:hidden property="specificFlag" value="N" />
<ihtml:hidden property="overrideRounding" value="N" />
<ihtml:hidden property="roundingValue" />
<ihtml:hidden property="hasPrivilege" />


	<common:xgroup>
		<common:xsubgroup id="TURKISH_SPECIFIC">
			 <%form.setSpecificFlag("Y");%>
		</common:xsubgroup>
	</common:xgroup >
	<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>			
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
						<ihtml:hidden property="roundingValue"  value="<%=String.valueOf(parameterValue).toUpperCase() %>" /><!--added by a-7871 for ICRD-214766-->
						<%form.setRoundingValue(String.valueOf(parameterValue).toUpperCase());%> <!--added by a-7871 for ICRD-263559 -->
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>
    <div class="ic-content-main">
	    <span class="ic-page-title ic-display-none">
		    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.pagetitle" />
		</span>
		    <div class="ic-head-container">
			    <div class="ic-filter-panel">
				<div class="ic-row marginT20">
				    <div class="ic-col-35">
						<fieldset class="ic-field-set">
							<legend class="iCargoLegend">
								<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.daterange" />
							</legend>
								<div class="ic-input ic-mandatory ic-col-50 ic-label-30">
							        <label>
									    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.from" />
									</label>
								        <ibusiness:calendar
								         property="fromDate"
								    	 componentID="CMP_MRA_GPABILLING_FROMDATE"
						         	     type="image" 
						         	     id="fromDate"
						                 value="<%=form.getFromDate()%>"/>
								</div>
								<div class="ic-input ic-mandatory ic-label-30">
							        <label> 
									    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.to" />
									</label>
									      <ibusiness:calendar componentID="CMP_MRA_GPABILLING_TODATE"
								          property="toDate" type="image" id="toDate"
						         	      value="<%=form.getToDate()%>"/>
								</div>
						</fieldset>	  
                   	</div>	
					
                    <div class="ic-col-18 marginT20 marginL10">
					    <div class="ic-input ">
						<label>
						    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.condocno" />
						</label>	
							 <ihtml:text property="consignmentNumber" componentID="CMP_MRA_GPABILLING_CONDOCNO" readonly="false" maxlength="15" />		
						</div>
						</div>
					 <div	 class="ic-col-15 marginT20">
						<div class="ic-input ">
							<label><common:message key="mailtracking.mra.gpabilling.GPABillingEntries.status" /></label>
							<ihtml:select componentID="CMP_MRA_GPABILLING_BILLINGSTATUS" property="status">
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="KEY_ONETIMES">
									<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.gpabillingstatus">
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
					 <div class="ic-col-20 marginT20">
							<div class="ic-input ">
								<label><common:message key="mailtracking.mra.gpabilling.GPABillingEntries.gpacode" /></label>
								 <ihtml:text property="gpaCodeFilter" componentID="CMP_MRA_GPABILLING_GPACODE" readonly="false" maxlength="5" />
								<div class="lovImg"> 
								<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" height="22" width="22" alt="" />
							  </div></div>
					</div>
                    </div>	
					<div class="ic-row">
						
                        <div class="ic-input ic-split-12">	
                           <label> <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.orgOOE" /></label>
					            <ihtml:text property="originOfficeOfExchange" componentID="CMP_MRA_GPABILLING_OOE" readonly="false" maxlength="6" />
								<div class="lovImg">
				                <img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
						</div>	
						</div>	
                        <div class="ic-input ic-split-12">	
						  <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.desOOE" /></label>
					            <ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MRA_GPABILLING_DOE" readonly="false" maxlength="6" />
								<div class="lovImg">
				                <img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
                        </div></div>
						<div class="ic-input ic-split-12">	
						   <label> <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.lbl.category" /></label>
		                        <ihtml:select componentID="CMP_MRA_GPABILLING_category" property="mailCategoryCode" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
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
						<div class="ic-input ic-split-8">
                          <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.lbl.subclass" /></label>
						   <ihtml:text property="mailSubclass" componentID="CMP_MRA_GPABILLING_SUBCLASS" maxlength="2" style="width:35px"/>
						   <div class="lovImg"><img name="subClassFilterLOV" id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />						
                        </div> </div>
						<div class="ic-input ic-split-8">	
						  <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.year" /></label>
							<ihtml:text property="year" componentID="CMP_MRA_GPABILLING_YEAR" readonly="false" maxlength="1" style="width:30px" />	
                        </div>
						<div class="ic-input ic-split-10">	
						 <label>   <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.dsnno" />	</label>				
						    <ihtml:text property="dsn" componentID="CMP_MRA_GPABILLING_GPACODE_DSN"  maxlength="4" style="width:45px" />	
                        </div>
						<div class="ic-input ic-split-7">	
						  <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.rsn" /></label>
					       <ihtml:text property="recepatableSerialNumber" componentID="CMP_MRA_GPABILLING_RSN" readonly="false" maxlength="3" style="width:35px" />	
                        </div>
						<div class="ic-input ic-split-7">	
						 <label>   <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.hni" /></label>
					       <ihtml:text property="highestNumberIndicator" componentID="CMP_MRA_GPABILLING_HNI" readonly="false" maxlength="1" style="width:35px" />
                        </div>
						<div class="ic-input ic-split-7	">
                          <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.ri" /></label>
					        <ihtml:text property="registeredIndicator" componentID="CMP_MRA_GPABILLING_RI" readonly="false" maxlength="1" style="width:35px" />						
                        </div>
						<!-- Added for ICRD-258393 Starts -->
						 <div class="ic-input ic-split-8">	
                           <label>  <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.origin" /></label></td>
					            <ihtml:text property="origin" componentID="CMP_MRA_GPABILLING_ORG" readonly="false" maxlength="3" />
				                 <div class="lovImg"><img name="originLOV" id="originLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>						
						</div>
						<div class="ic-input ic-split-8">	
                            <label><common:message key="mailtracking.mra.gpabilling.GPABillingEntries.destination" /></label></td>
					            <ihtml:text property="destination" componentID="CMP_MRA_GPABILLING_DST" readonly="false" maxlength="3" />
				                 <div class="lovImg"><img name="destLOV" id="destLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>						
						</div>
						<!-- Added for ICRD-258393 ends -->
						</div>
						<div class="ic-row marginT5">
						<div class="ic-input ic-split-25">	
                           <label> <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.mailbagId" /></label>
					            <ihtml:text property="mailbagId" componentID="CMP_MRA_GPABILLING_MAILBAGID" readonly="false" maxlength="29" style="width:210px" /> <!--modified. A-8164 for ICRD 257674-->	
						</div>
<!--Added by A-6991 for ICRD-137019 Starts-->
                         <div class="ic-input ic_inline_chcekbox ic-split-11 marginT15" >
						<logic:equal name="form" property="contractRate" value="Y">
								<input type="checkbox" name="contractRate" checked />
						</logic:equal>

						<logic:notEqual name="form" property="contractRate" value="Y">
								<input type="checkbox" name="contractRate"  />
						</logic:notEqual>
						<label class="ic-split-38">
						<common:message key="mailtracking.mra.gpabilling.billingentries.contractRate"  scope="request"/>
			            </label>
                        </div>		
						 <div class="ic-input ic_inline_chcekbox ic-split-10 marginT15" >
						<logic:equal name="form" property="UPURate" value="Y">
								<input type="checkbox" name="UPURate" checked />
						</logic:equal>

						<logic:notEqual name="form" property="UPURate" value="Y">
								<input type="checkbox" name="UPURate" />
						</logic:notEqual>
						<label class="ic-split-38">
						<common:message key="mailtracking.mra.gpabilling.billingentries.UPURate"  scope="request"/>
			            </label>
                        </div>
						 <div class="ic-input ic-split-12" ><!--Added by A-7871 for ICRD-232381 -->
					   <label> <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.uspsperformed" /></label>
		                        <ihtml:select componentID="CMP_MRA_GPABILLING_USPSPerformancemet" property="isUSPSPerformed" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.uspsperformed">
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


<!--Added by A-6991 for ICRD-137019 Ends-->					
						
						<div class="ic-button-container">
						    <ihtml:nbutton property="btList" accesskey="L" componentID="CMP_MRA_GPABILLING_DETAILS" >
								<common:message key="mailtracking.mra.gpabilling.billingentries.button.details" />
					        </ihtml:nbutton>
                            <ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_GPABILLING_CLEAR" >
								<common:message key="mailtracking.mra.gpabilling.billingentries.button.clear" />
					        </ihtml:nbutton>
						</div>
				</div>
			</div>
	    </div>
		<div class="ic-main-container">
		<div class="ic-row">
			<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackGPABillingEntries"  href="#"> </a> <!--Added by A-7929 for ICRD - 224586  -->
		    </div>
		    <div class="ic-col-30">
				<logic:present name="GPABillingDetailsVO">
					<common:paginationTag pageURL="mailtracking.mra.gpabilling.listbillingentries.do"
					name="GPABillingDetailsVO"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=((GPABillingEntriesForm)form).getLastPageNum()%>" />
				</logic:present>
				<logic:notPresent name="GPABillingDetailsVO">
				   &nbsp;
				</logic:notPresent>
	        </div>
			<div class="ic-right">
				<logic:present name="GPABillingDetailsVO">
					<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
					linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
					name="GPABillingDetailsVO" display="pages" lastPageNum="<%=(String)((GPABillingEntriesForm)form).getLastPageNum()%>"
					exportToExcel="true"
					exportTableId="listGPABillTable"
					exportAction="mailtracking.mra.gpabilling.listbillingentries.do"
					fetchCount="500"/>
				</logic:present>
				<logic:notPresent name="GPABillingDetailsVO">
				   &nbsp;
				</logic:notPresent>
			</div>
			<div class="ic-row">
			   	<div id="div1" class="tableContainer"  style="width:100%;height:710px;">
					<table style="width:140%;" class="fixed-header-table"  id="listGPABillTable">
                        <thead>
						<tr class="ic-th-all">
						<th style="width:2%"/>
						<th style="width:4%"/>
						<th style="width:8%"/>
						<th style="width:8%"/>
						<th style="width:18%"/>
						<th style="width:5%"/>
						<th style="width:5%"/>
						<th style="width:5%"/>
						<th style="width:17%"/>
						<th style="width:3%"/>
						<th style="width:3%"/>
						<th style="width:3%"/>
						<th style="width:2%"/>
						<th style="width:2%"/>
						<th style="width:3%"/>
						<th style="width:6%"/><!--modified by A-7371 for ICRD-220296-->
						<th style="width:4%"/>
						<th style="width:5%"/>	
						<th style="width:6%"/>	
						<th style="width:6%"/>		
						<th style="width:6%"/>
						<th style="width:5%"/>	
						<th style="width:5%"/><!--modified by A-8149 for ICRD-270291-->	
					    <th style="width:9%"/><!--modified by A-8149 for ICRD-270291-->
						<th style="width:4%"/> <!--modified by A-8464 for ICRD-277576-->	
						<th style="width:6%"/>	
						<th style="width:7%"/>
						<th style="width:5%"/> <!--modified by A-8464 for ICRD-277576-->	
						<th style="width:7%"/><!--Added by A-7871 for ICRD-232381--> 
						</tr>
							<tr>
                        <td rowspan="2" class="iCargoTableHeader" ><input type="checkbox" name="allCheck" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
								<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.gpacode" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.invoiceNumber" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.consdocno" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader"  >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.mailbagId" /><span></span></td>									
                            <td rowspan="2" class="iCargoTableHeader"  >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.originoe" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.destoe" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.category" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.subclass" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.year" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.dsn" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader">
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.rsn" /><span></span></td>
							<td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.hni" /><span></span></td>
							<td rowspan="2" class="iCargoTableHeader">
							        <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.ri" /><span></span></td>
                            <td rowspan="2" style="iCargoLabelCenterAligned" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.mcano" /><span></span></td>
                            <td rowspan="2" class="iCargoTableHeader">
                                    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.weight" /><span></span></td>
                            <td colspan="2" class="iCargoTableHeader" >
							        <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.mailchg" /></td>
							<td rowspan="2" class="iCargoTableHeader" >
							        <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.surchg" /></td>
							<td rowspan="2" class="iCargoTableHeader" >
                                   <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.grossamt" /><span></span></td>
							<logic:equal name="form" property="specificFlag" value = "Y">
							<td rowspan="2" class="iCargoTableHeader" >Val Chgs<span></span></td>
							<td rowspan="2" class="iCargoTableHeader" >
							       <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.declaredvalue"/><span></span></td>
							</logic:equal>	
							<td rowspan="2" class="iCargoTableHeader" >
                                    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.stax" /><span></span></td>
							<logic:notEqual name="form" property="specificFlag" value = "Y">	
							<td rowspan="2" class="iCargoTableHeader">TDS<span></span></td>
							</logic:notEqual>
							<td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.netamt" /><span></span></td>
							<td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.curr" /><span></span></td>
							<td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.billingstatus" /><span></span></td>
							<td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.RateIdentifier" />
									<span></span></td>	
                            <td rowspan="2" class="iCargoTableHeader" >
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.RateType" />
									<span></span></td>	
										<td rowspan="2" class="iCargoTableHeader" ><!--Added by A-7871 for ICRD-232381-->
									<common:message key="mailtracking.mra.gpabilling.GPABillingEntries.uspsperformed" />
										<span></span></td>	
		</td>	
						</tr>
                        <tr>
		       				<td class="iCargoTableHeader">
							    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.rate" /></td>
		       				<td class="iCargoTableHeader">
							    <common:message key="mailtracking.mra.gpabilling.GPABillingEntries.amount" /></td>		       								
		       			</tr>
                    </thead>
                	<tbody>
										 <logic:present name="GPABillingDetailsVO">
										<logic:iterate id="iterator" name="GPABillingDetailsVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO" indexId="rowCount">
										 
				      	 					
										<td>
										<ihtml:checkbox property="check" value="<%=String.valueOf(rowCount)%>"/>
										</td>
										<td>
										
										<logic:present	name="iterator" property="gpaCode">
										<bean:write name="iterator" property="gpaCode"/>
										</logic:present>
						      				
										</td>
										<td >
										
										<logic:present	name="iterator" property="invoiceNumber">
										<bean:write name="iterator" property="invoiceNumber"/>
										</logic:present>
						      				
										</td>
										<td >
										
										<logic:present	name="iterator" property="csgDocumentNumber">
										<bean:write name="iterator" property="csgDocumentNumber"/>
										</logic:present>
						      				
										</td>
										<td >
										
										<logic:present	name="iterator" property="billingBasis">
										<bean:write name="iterator" property="billingBasis"/>
										<bean:define id="billingBasis" name="iterator" property="billingBasis"/>
										<%String mailBagId=billingBasis.toString();%>
										<ihtml:hidden property="mailBagId" value="<%=mailBagId%>"/>
										</logic:present>
						      				
										</td>
										<td >
										
										<logic:present	name="iterator" property="origin">
										<bean:write name="iterator" property="origin"/>
										</logic:present>
						      			
										</td>
										<td >
										
										<logic:present	name="iterator" property="destination">
										<bean:write name="iterator" property="destination"/>
										</logic:present>
						      			</div>
										</td>
										<td >
										
										<logic:present	name="iterator" property="category">
										<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue" property="fieldValue">

										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<bean:define id="category" name="iterator" property="category"/>
										<%String mailcat=category.toString();%>
										<logic:equal name="parameterValue" property="fieldValue" value="<%=mailcat%>">
										<bean:write name="parameterValue" property="fieldDescription" />
										<ihtml:hidden property="category" value="<%=mailcat%>"/>
										</logic:equal>
										</logic:present>
										</logic:iterate>
										</logic:equal>
										</logic:iterate>
										</logic:present>
										<!--<bean:write name="iterator" property="category"/>-->
										</logic:present>
						      			
										</td>
										<td >
										
										<logic:present	name="iterator" property="subClass">
										<bean:write name="iterator" property="subClass"/>
										</logic:present>
						      				
										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="year">
										<bean:write name="iterator" property="year"/>
										</logic:present>
						      				
										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="dsn">
										<bean:write name="iterator" property="dsn"/>
										<bean:define id="dsn" name="iterator" property="dsn"/>
										<%String dsnnum=dsn.toString();%>
										<ihtml:hidden name="iterator" property="dsn"/>
										<ihtml:hidden property="despatchNumber" value="<%=dsnnum%>"/>
										</logic:present>
						      			

										</td>
										

										<td style="text-align:right">
										
										<logic:present	name="iterator" property="rsn">
										<bean:write name="iterator" property="rsn"/>
										<ihtml:hidden name="iterator" property="rsn"/>
										</logic:present>
						      			

										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="hni">
										<bean:write name="iterator" property="hni"/>
										<ihtml:hidden name="iterator" property="hni"/>
										</logic:present>
						      			

										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="regInd">
										<bean:write name="iterator" property="regInd"/>
										<ihtml:hidden name="iterator" property="regInd"/>
										</logic:present>
						      			

										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="ccaRefNumber">
											<ihtml:hidden property="ccaReferenceNumber" value="<%=iterator.getCcaRefNumber()%>"/>
										<bean:write name="iterator" property="ccaRefNumber"/>
										</logic:present>
										<logic:notPresent	name="iterator" property="ccaRefNumber">
											<ihtml:hidden property="ccaReferenceNumber" value=""/>
										</logic:notPresent>	
										
										</td>

										<td style="text-align:right">
										
										<logic:present	name="iterator" property="weight">
										<common:write name="iterator" property="weight" unitFormatting="true" />
										
										</logic:present>
						      			
										</td>
										<!--added for cr ICRD7370-->
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="applicableRate">
											<logic:equal name="iterator" property="applicableRate" value="0">
												 <logic:equal name="form" property="roundingValue" value = "5">
													<bean:write name="iterator" property="applicableRate" format="####.#####"/>
												</logic:equal>
												 <logic:notEqual name="form" property="roundingValue" value = "5">
													<bean:write name="iterator" property="applicableRate" format="####.####"/>
												 </logic:notEqual>
											</logic:equal>
											<logic:notEqual name="iterator" property="applicableRate" value="0">
											 <logic:equal name="form" property="roundingValue" value = "5">
											<bean:write name="iterator" property="applicableRate" format="####.#####"/>
												</logic:equal>
												 <logic:notEqual name="form" property="roundingValue" value = "5">
											<bean:write name="iterator" property="applicableRate" format="####.####"/>
												 </logic:notEqual>
											<!--<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="applicableRate" property="applicableRate" />-->
											</logic:notEqual>
										</logic:present>
										<logic:notPresent name="iterator" property="applicableRate">
														&nbsp;
										</logic:notPresent>
										</td>
										<td style="text-align:right">
										<logic:present	name="iterator" property="mailChg">
											<logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="mailChg" property="mailChg" overrideRounding = "true" />
											</logic:equal>
											<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="mailChg" property="mailChg" />
											</logic:notEqual>
										</logic:present>
										<logic:notPresent name="iterator" property="mailChg">
														&nbsp;
										</logic:notPresent>
						      			
										</td>
										<td style="text-align:right">
										<logic:present	name="iterator" property="surChg">
										<logic:equal name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="surChg" property="surChg" overrideRounding = "true"  />
										</logic:equal>
										<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="surChg" property="surChg" />
										</logic:notEqual>
										<bean:define id="surChg" name="iterator" property="surChg"/>
										<%String surChrg=surChg.toString();%>
										<ihtml:hidden name="iterator" property="surChg"/>
										<ihtml:hidden property="surCharge" value="<%=surChrg%>"/>
										</logic:present>
										<logic:notPresent name="iterator" property="surChg">
														&nbsp;
										</logic:notPresent>
						      			
										</td>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="grossAmount">
											<logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="grossAmount" property="grossAmount" overrideRounding = "true"  />
											</logic:equal>
											<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="grossAmount" property="grossAmount" />
											</logic:notEqual>											
										</logic:present>
										<logic:notPresent name="iterator" property="grossAmount">
														&nbsp;
										</logic:notPresent>
						      			
										</td>
										<logic:equal name="form" property="specificFlag" value = "Y">
											<logic:present	name="iterator" property="valCharges">
											<logic:equal name="form" property="overrideRounding" value = "Y">
												<td style="text-align:right"><ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="valCharges" property="valCharges" overrideRounding = "true"  /></td>
											</logic:equal>
											<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<td style="text-align:right"><ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="valCharges" property="valCharges" /></td>
											</logic:notEqual>												
											</logic:present>
											<logic:notPresent	name="iterator" property="valCharges">
												<td style="text-align:right">&nbsp;</td>
											</logic:notPresent>
											
											<logic:present	name="iterator" property="declaredValue">
												<td style="text-align:right"><ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="declaredValue" property="declaredValue"  /></td>									
											</logic:present>
											<logic:notPresent	name="iterator" property="declaredValue">
												<td style="text-align:right">0</td>
											</logic:notPresent>
											
										</logic:equal>
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="serviceTax">
										<logic:equal name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="serviceTax" property="serviceTax" overrideRounding = "true"  />
										</logic:equal>	
										<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="serviceTax" property="serviceTax" />
										</logic:notEqual>											
										</logic:present>
										<logic:notPresent name="iterator" property="serviceTax">
														&nbsp;
										</logic:notPresent>
						      			
										</td>
										<logic:notEqual name="form" property="specificFlag" value = "Y">
										<td style="text-align:right">
										
										<logic:present	name="iterator" property="tds">
										<logic:equal name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="tds" property="tds" overrideRounding = "true"  />
										</logic:equal>
										<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<logic:equal name="iterator" property="tds" value="0">
												0
											</logic:equal>
											<logic:notEqual name="iterator" property="tds" value="0">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="tds" property="tds" />
										</logic:notEqual>
										</logic:notEqual>
										</logic:present>
										<logic:notPresent name="iterator" property="tds">
														&nbsp;
										</logic:notPresent>
						      			
										</td>
										</logic:notEqual>
										<td style="text-align:right">
										<logic:present	name="iterator" property="netAmount">
										<logic:equal name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="netAmount" property="netAmount" overrideRounding = "true"  />
										</logic:equal>
										<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="iterator" moneyproperty="netAmount" property="netAmount" />
										</logic:notEqual>
										</logic:present>
										<logic:notPresent name="iterator" property="netAmount">
														&nbsp;
										</logic:notPresent>
										</td>
										<td>
										<logic:present	name="iterator" property="currency">
										<bean:write name="iterator" property="currency"/>
										</logic:present>
										</td>
										<td style="text-align:center">
										
										<logic:present	name="iterator" property="billingStatus">
										<logic:present name="KEY_ONETIMES">
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.mra.gpabilling.gpabillingstatus">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue" property="fieldValue">

										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<bean:define id="billingStatus" name="iterator" property="billingStatus"/>
										<%String field=billingStatus.toString();%>
										<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
										<bean:write name="parameterValue" property="fieldDescription" />
										<ihtml:hidden property="saveBillingStatus" value="<%=field%>"/>
										</logic:equal>
										</logic:present>
										</logic:iterate>
										</logic:equal>
										</logic:iterate>
										</logic:present>
										</logic:present>
										<logic:notPresent name="iterator" property="billingStatus">
										<ihtml:hidden property="saveBillingStatus" value=""/>
										</logic:notPresent>
						      			
										</td>
										<!--Added by A-6991 for ICRD-137019 Starts-->
										<td>
										<div style="word-wrap:break-word;"> <!--modified by A-8464 for ICRD-277576-->
										<logic:present	name="iterator" property="rateIdentifier">
										<bean:write name="iterator" property="rateIdentifier"/>
										<bean:define id="rateType" name="iterator" property="rateType"/>
										<%if("C".equals(rateType)){%>
										/<bean:write name="iterator" property="rateLineIdentifier"/>
										 <%}%>
										</logic:present>
										</div>
						      			</td>	
										<td>
										<logic:present	name="iterator" property="rateType">
										<bean:write name="iterator" property="rateType"/>
										</logic:present>
						      			</td>	
										<!--Added by A-6991 for ICRD-137019 Ends-->
										
										<td><!--Added by A-8464 for ICRD-232381 Ends-->
										<logic:present	name="iterator" property="isUSPSPerformed">
										<bean:write name="iterator" property="isUSPSPerformed"/>
										</logic:present>
						      			</td>	<!--Added by A-8464 for ICRD-232381 Ends-->
										
										
										<!--<td >
										<center>
										<logic:present  name="iterator" property="ccaRefNumber">
										<ihtml:hidden property="ccaReferenceNumber" value="<%=iterator.getCcaRefNumber()%>"/>
										<bean:write name="iterator" property="ccaRefNumber"/>
										</logic:present>
										<logic:notPresent   name="iterator" property="ccaRefNumber">
										<ihtml:hidden property="ccaReferenceNumber" value=""/>
										</logic:notPresent>
										</center>
										</td>-->

										</tr>
										
										</logic:iterate>
			 							</logic:present>
							</tbody>
						</table>
				
			</div>
			
		</div>
	</div>
		<div class="ic-foot-container">
		    <div class="ic-button-container">
				<ihtml:nbutton property="btnVoid" accesskey="V" componentID="CMP_MRA_GPABILLING_BTNVOID" >
	                <common:message key="mailtracking.mra.gpabilling.billingentries.button.btnvoid" />
	            </ihtml:nbutton>
			   <ihtml:nbutton property="btnAutoMCA" accesskey="A" componentID="CMP_MRA_GPABILLING_BTNAUTOMCA" >
	                <common:message key="mailtracking.mra.gpabilling.billingentries.button.automca" />
	            </ihtml:nbutton>
                <ihtml:nbutton property="btnRouting" accesskey="R" componentID="CMP_MRA_GPABILLING_BTNROUTING" >
	                <common:message key="mailtracking.mra.gpabilling.billingentries.button.routing" />
	            </ihtml:nbutton>
			    <ihtml:nbutton property="btnRerate" accesskey="E" componentID="CMP_MRA_GPABILLING_BTNRERATE" >
	                <common:message key="mailtracking.mra.gpabilling.billingentries.button.rerate" />
	            </ihtml:nbutton>
			    <ihtml:nbutton property="btnSurcharge" accesskey="S" componentID="CMP_MRA_GPABILLING_BTNSURCHG" >
	                <common:message key="mailtracking.mra.gpabilling.billingentries.button.surcharge" />
	            </ihtml:nbutton>
	            <ihtml:nbutton property="btnChangeStatus" accesskey="B" componentID="CMP_MRA_GPABILLING_CHGSTATUS" >
					<common:message key="mailtracking.mra.gpabilling.billingentries.button.chgstatus" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_GPABILLING_CLOSE" >
				   <common:message key="mailtracking.mra.gpabilling.billingentries.button.close" />
				</ihtml:nbutton>
		</div>
	</div>
</ihtml:form>
</div>

	</body>
</html:html>
