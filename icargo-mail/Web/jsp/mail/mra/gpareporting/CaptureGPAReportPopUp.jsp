<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: CaptureGPAReport.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2257

 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>

		
				
	
<html:html locale="true">
<head> 
	
	
		
	<title><common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.capturegpareport.title.capturegpareport" />

	</title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/mra/gpareporting/CaptureGPAReportPopUp_Script.jsp"/>


</head>
<body style="overflow:auto;">
	
	


<business:sessionBean id="GPAREPORTINGFILTERVO"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="gPAReportingFilterVO" />


<business:sessionBean id="GPAREPORTDETAILVO"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="selectedGPAReportingDetailsVO" />

<business:sessionBean id="MODIFIEDGPAVOS"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="modifiedGPAReportingDetailsVOs" />

<business:sessionBean id="MAILCATEGORY_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailCategory" />

<business:sessionBean id="MAILSTATUS_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailStatus" />

<business:sessionBean id="HIGHESTNUM_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="heighestNum" />

<business:sessionBean id="REGINSIND_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="regOrInsInd" />

<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra"
		  screenID="mailtracking.mra.gpareporting.capturegpareport"
		  method="get"
		  attribute="weightRoundingVO" />

  <!--CONTENT STARTS-->

 <div class="iCargoPopUpContent">

 <bean:define id="form" name="CaptureGPAReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" toScope="page" />

 <ihtml:form action="/mailtracking.mra.gpareporting.capturegpareportpopup.screenload.do" styleclass="ic-main-form">


<ihtml:hidden property="screenFlag" />
<ihtml:hidden property="displayPopUpPage" />
<ihtml:hidden property="popUpStatusFlag" />
<ihtml:hidden property="amtForValidation" />
<ihtml:hidden property="selectedRows" />
<ihtml:hidden property="basistype" />
<ihtml:hidden property="currencyCode" />
<ihtml:hidden property="showDsnPopUp"/>
<ihtml:hidden property="dsnFlag"/>

<%
 boolean disableDSN=false;

 %>

 <logic:equal name="form" property="popUpStatusFlag" value="MODIFY">
  <%
  disableDSN=true;

  %>
 </logic:equal>

     <div class="ic-content-main">
               
			<div class="ic-head-container">		
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							
								<label>
								<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.capturegpareport" />
								</label>
						</div>
						<div class="ic-input ic-split-50">
							<div class="ic-row">
								<div class="ic-col-50">
									<label>
										<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.billingbasis" />
									</label>
									<ihtml:text property="gpaselect" suggestCollection="MODIFIEDGPAVOS" suggestAttribute="dsnDateForDisplay,billingBasis" readonly="true" suggestEvent="displayNext" componentID="CMP_MRA_CaptureGPAReportPopUp_Sujjest" maxlength="11" style="text-transform : uppercase;" />
								</div>
								<div class="ic-col">
									<a href="#" class="iCargoLink" id="addLink" >
									  Create
									  </a> |
									  <a href="#" class="iCargoLink"  id="deleteLink" >
									 Delete
									  </a>
								</div>
							</div>
							
						</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-col">
				<label>
					 <common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.date" />
				</label>
				<logic:present name="GPAREPORTDETAILVO" property="dsnDate">
				  <bean:define id="dsnDate" name="GPAREPORTDETAILVO" property="dsnDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />

					  <ibusiness:calendar
						componentID="CMP_MRA_CaptureGPAReportPopUp_Date"
						property="date"
						value="<%=dsnDate.toDisplayDateOnlyFormat() %>"
						type="image"
						id="date" />
				  </logic:present>
				  <logic:notPresent name="GPAREPORTDETAILVO" property="dsnDate">

					  <ibusiness:calendar
						componentID="CMP_MRA_CaptureGPAReportPopUp_Date"
						property="date"
						value=""
						type="image"
						id="date" />
				  </logic:notPresent>
				 </div>
				 <div class="ic-col ic-mandatory">
					<label>
					 <common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.originoe" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="originOfficeExchange">
					<bean:define id="originOfficeExchange" name="GPAREPORTDETAILVO" property="originOfficeExchange" />

					<ihtml:text property="originOE" value="<%=(String)originOfficeExchange%>" maxlength="6" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_OriginOE" style="text-transform : uppercase"  />
					<img name="originOELov" id="originOELov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="originOfficeExchange">

					<ihtml:text property="originOE" value="" readonly="<%=disableDSN%>"  maxlength="6" componentID="CMP_MRA_CaptureGPAReportPopUp_OriginOE" style="text-transform : uppercase"  />
					<img name="originOELov" id="originOELov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
					</logic:notPresent>
				 </div>
				  <div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.destnoe" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="destinationOfficeExchange">
					  <bean:define id="destinationOfficeExchange" name="GPAREPORTDETAILVO" property="destinationOfficeExchange" />
						<ihtml:text property="destinationOE" value="<%=(String)destinationOfficeExchange%>" maxlength="6" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_DestOE" style="text-transform : uppercase"  />
						<img name="destOELov" id="destOELov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
					   </logic:present>
					   <logic:notPresent name="GPAREPORTDETAILVO" property="destinationOfficeExchange">

						<ihtml:text property="destinationOE" value="" readonly="<%=disableDSN%>" maxlength="6" componentID="CMP_MRA_CaptureGPAReportPopUp_DestOE" style="text-transform : uppercase"  />
						<img name="destOELov" id="destOELov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
					   </logic:notPresent>
				  </div>
				  <div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.mailcategory" />
					<label>
					<ihtml:select property="mailCategory"  disabled="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_MailCatg" >
					  <logic:present name="GPAREPORTDETAILVO" property="mailCategory">
					  <bean:define id="mailCategory" name="GPAREPORTDETAILVO" property="mailCategory" />
						 <logic:iterate id="mailcategory_onetime" name="MAILCATEGORY_ONETIME">
						 <bean:define id="mailcategoryvalue" name="mailcategory_onetime" property="fieldValue" />
						 <logic:equal name="GPAREPORTDETAILVO" property="mailCategory" value="<%=(String)mailcategoryvalue %>">
						<html:option value="<%=(String)mailcategoryvalue %>"><bean:write name="mailcategory_onetime" property="fieldDescription" /> </html:option>
						 </logic:equal>
						 </logic:iterate>
						 <logic:iterate id="mailcategory_onetime" name="MAILCATEGORY_ONETIME">
						 <bean:define id="mailcategoryvalue" name="mailcategory_onetime" property="fieldValue" />
						<html:option value="<%=(String)mailcategoryvalue %>"><bean:write name="mailcategory_onetime" property="fieldDescription" /> </html:option>
						 </logic:iterate>

					  </logic:present>
					  <logic:notPresent name="GPAREPORTDETAILVO" property="mailCategory">
						 <logic:iterate id="mailcategory_onetime" name="MAILCATEGORY_ONETIME">
						 <bean:define id="mailcategoryvalue" name="mailcategory_onetime" property="fieldValue" />
						<html:option value="<%=(String)mailcategoryvalue %>"><bean:write name="mailcategory_onetime" property="fieldDescription" /> </html:option>
						 </logic:iterate>
					  </logic:notPresent>
					</ihtml:select>
				  </div>
				  <div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.mailsubclass" />
					<label>
					<logic:present name="GPAREPORTDETAILVO" property="actualMailSubClass">
					<bean:define id="mailSubClass" name="GPAREPORTDETAILVO" property="actualMailSubClass" />
						<ihtml:text property="mailSubClass" value="<%=(String)mailSubClass%>" maxlength="2" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_MailSubClass" style="text-transform : uppercase"  />
					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="actualMailSubClass">

						<ihtml:text property="mailSubClass" value="" readonly="<%=disableDSN%>" maxlength="2" componentID="CMP_MRA_CaptureGPAReportPopUp_MailSubClass" style="text-transform : uppercase"  />
					</logic:notPresent>
				 </div>
			</div>
			<div class="ic-row">
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.year" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="year">
					<bean:define id="year" name="GPAREPORTDETAILVO" property="year" />
						<ihtml:text property="year" value="<%=(String)year%>" readonly="<%=disableDSN%>" maxlength="1" componentID="CMP_MRA_CaptureGPAReportPopUp_Year" style="text-transform : uppercase"  />
					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="year">

						<ihtml:text property="year" value="" readonly="<%=disableDSN%>" maxlength="1" componentID="CMP_MRA_CaptureGPAReportPopUp_Year" style="text-transform : uppercase"  />
					</logic:notPresent>
				</div>
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.dsn" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="dsnNumber">
					<bean:define id="dsnNumber" name="GPAREPORTDETAILVO" property="dsnNumber" />
						<ihtml:text property="dsn" value="<%=(String)dsnNumber%>" maxlength="4" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_DSN" style="text-transform : uppercase"  />
					 </logic:present>
					 <logic:notPresent name="GPAREPORTDETAILVO" property="dsnNumber">

						<ihtml:text property="dsn" value="" readonly="<%=disableDSN%>" maxlength="4" componentID="CMP_MRA_CaptureGPAReportPopUp_DSN" style="text-transform : uppercase"  />
					 </logic:notPresent>
				</div>
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.receptacleSerialNumber" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="receptacleSerialNumber">
					<bean:define id="receptacleSerialNumber" name="GPAREPORTDETAILVO" property="receptacleSerialNumber" />
						  <logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >
							<ihtml:text property="receptacleSerialNum" value="<%=(String)receptacleSerialNumber%>" maxlength="3" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_RSN" style="text-transform : uppercase"  />

						   </logic:equal>
						   <logic:notEqual name="GPAREPORTDETAILVO" property="basistype" value="M" >
							<ihtml:text property="receptacleSerialNum" value="<%=(String)receptacleSerialNumber%>" readonly="true" maxlength="3" componentID="CMP_MRA_CaptureGPAReportPopUp_RSN" style="text-transform : uppercase"  />
						   </logic:notEqual>
					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="receptacleSerialNumber">
						<logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >
							<ihtml:text property="receptacleSerialNum" value="" maxlength="3" readonly="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_RSN" style="text-transform : uppercase"  />

						   </logic:equal>
						   <logic:notEqual name="GPAREPORTDETAILVO" property="basistype" value="M" >
							  <ihtml:text property="receptacleSerialNum" value="" readonly="true" maxlength="3" componentID="CMP_MRA_CaptureGPAReportPopUp_RSN" style="text-transform : uppercase"  />
						   </logic:notEqual>
					</logic:notPresent>
				</div>
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.highestNumberedReceptacle" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="highestNumberedReceptacle">
					  <bean:define id="highestNumberedReceptacle" name="GPAREPORTDETAILVO" property="highestNumberedReceptacle" />
					  <ihtml:select property="highestNumberedRec"  disabled="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_HN" >
						  <logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >
							 <logic:iterate id="highestnum_onetime" name="HIGHESTNUM_ONETIME">
							 <bean:define id="highestnumvalue" name="highestnum_onetime" property="fieldValue" />
							 <logic:equal name="GPAREPORTDETAILVO" property="highestNumberedReceptacle" value="<%=(String)highestnumvalue %>">
							<html:option value="<%=(String)highestnumvalue %>"><bean:write name="highestnum_onetime" property="fieldDescription" /> </html:option>
							 </logic:equal>
							 </logic:iterate>
							 <logic:iterate id="highestnum_onetime" name="HIGHESTNUM_ONETIME">
							 <bean:define id="highestnumvalue" name="highestnum_onetime" property="fieldValue" />
							<html:option value="<%=(String)highestnumvalue %>"><bean:write name="highestnum_onetime" property="fieldDescription" /> </html:option>
							 </logic:iterate>
						  </logic:equal>
					</ihtml:select>
					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="highestNumberedReceptacle">
						  <logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >

						  <ihtml:select property="highestNumberedRec"  disabled="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_HN" >
							 <logic:iterate id="highestnum_onetime" name="HIGHESTNUM_ONETIME">
							 <bean:define id="highestnumvalue" name="highestnum_onetime" property="fieldValue" />
							<html:option value="<%=(String)highestnumvalue %>"><bean:write name="highestnum_onetime" property="fieldDescription" /> </html:option>
							 </logic:iterate>

							</ihtml:select>
						  </logic:equal>
							 <logic:notEqual name="GPAREPORTDETAILVO" property="basistype" value="M" >

							 <ihtml:select property="highestNumberedRec"  disabled="true" componentID="CMP_MRA_CaptureGPAReportPopUp_HN" >
								<html:option value=""></html:option>
							  </ihtml:select>

							 </logic:notEqual>
					</logic:notPresent>
				</div>
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.registeredOrInsuredIndicator" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="registeredOrInsuredIndicator">
								  <bean:define id="registeredOrInsuredIndicator" name="GPAREPORTDETAILVO" property="registeredOrInsuredIndicator" />
								  <ihtml:select property="registeredOrInsuredInd"  disabled="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_RI" >
									  <logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >
									     <logic:iterate id="reginsind_onetime" name="REGINSIND_ONETIME">
									     <bean:define id="reginsindvalue" name="reginsind_onetime" property="fieldValue" />
									     <logic:equal name="GPAREPORTDETAILVO" property="registeredOrInsuredIndicator" value="<%=(String)reginsindvalue %>">
										<html:option value="<%=(String)reginsindvalue %>"><bean:write name="reginsind_onetime" property="fieldDescription" /> </html:option>
									     </logic:equal>
									     </logic:iterate>
									     <logic:iterate id="reginsind_onetime" name="REGINSIND_ONETIME">
									     <bean:define id="reginsindvalue" name="reginsind_onetime" property="fieldValue" />
										<html:option value="<%=(String)reginsindvalue %>"><bean:write name="reginsind_onetime" property="fieldDescription" /> </html:option>
									     </logic:iterate>
									  </logic:equal>
								   </ihtml:select>

								  </logic:present>
								  <logic:notPresent name="GPAREPORTDETAILVO" property="registeredOrInsuredIndicator">
							              	    <logic:equal name="GPAREPORTDETAILVO" property="basistype" value="M" >
										    <ihtml:select property="registeredOrInsuredInd"  disabled="<%=disableDSN%>" componentID="CMP_MRA_CaptureGPAReportPopUp_RI" >
										    <logic:iterate id="reginsind_onetime" name="REGINSIND_ONETIME">
										     <bean:define id="reginsindvalue" name="reginsind_onetime" property="fieldValue" />
											<html:option value="<%=(String)reginsindvalue %>"><bean:write name="reginsind_onetime" property="fieldDescription" /> </html:option>
										     </logic:iterate>
										     </ihtml:select>
									     </logic:equal>
									     <logic:notEqual name="GPAREPORTDETAILVO" property="basistype" value="M" >

										 <ihtml:select property="registeredOrInsuredInd"  disabled="true" componentID="CMP_MRA_CaptureGPAReportPopUp_RI" >
											<html:option value=""></html:option>
										  </ihtml:select>

								             </logic:notEqual>
								  </logic:notPresent>

				</div>
				<div class="ic-col ic-mandatory">
					<label>
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.noofmailbags" />
					</label>
					<logic:present name="GPAREPORTDETAILVO" property="noOfMailBags">
					  <bean:define id="noOfMailBags" name="GPAREPORTDETAILVO" property="noOfMailBags" />

						<ihtml:text property="noOfMailBag" value="<%=noOfMailBags.toString()%>" maxlength="3" componentID="CMP_MRA_CaptureGPAReportPopUp_NoMailBags" style="text-transform : uppercase"  />

					</logic:present>
					<logic:notPresent name="GPAREPORTDETAILVO" property="noOfMailBags">

						<ihtml:text property="noOfMailBag" value=""  maxlength="3" componentID="CMP_MRA_CaptureGPAReportPopUp_NoMailBags" style="text-transform : uppercase"  />
					</logic:notPresent>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-field-set">
					<legend>Charge Details
					</legend>
					<div class="ic-row">
						<div class="ic-col ic-mandatory">
							<label><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.wt" />
							</label>
							<logic:present name="GPAREPORTDETAILVO" property="weight">
								<bean:define id="weight" name="GPAREPORTDETAILVO" property="weight" toScope="page"/>
									<logic:present name="KEY_WEIGHTROUNDINGVO">
									<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
									<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
									<ibusiness:unitdef id="weight" unitTxtName="weight" componentID="CMP_MRA_CaptureGPAReportPopUp_Weight" label=""  unitReq = "false" dataName="sampleStdWt"
										 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
										unitValue="<%=weight.toString()%>" style="background :'<%=color%>';text-align:right"
										indexId="index" styleId="weight" />
									
									</logic:present>

							</logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="weight">
							  <logic:present name="KEY_WEIGHTROUNDINGVO">
								<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
								<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
								<ibusiness:unitdef id="weight" unitTxtName="weight" componentID="CMP_MRA_CaptureGPAReportPopUp_Weight" label=""  unitReq = "false" dataName="sampleStdWt"
									 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
									unitValue="" style="background :'<%=color%>';text-align:right"
									indexId="index" styleId="weight"  />
								
								</logic:present>
							</logic:notPresent>
						</div>
						<div class="ic-col">
							<label>
								<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.rate" />
							</label>
							<logic:present name="GPAREPORTDETAILVO" property="rate">
							<bean:define id="rate" name="GPAREPORTDETAILVO" property="rate" />
								<ihtml:text property="rate" value="<%=rate.toString()%>"  maxlength="20" componentID="CMP_MRA_CaptureGPAReportPopUp_Rate" style="text-transform : uppercase"  />
							</logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="rate">

								<ihtml:text property="rate" value="" maxlength="20" componentID="CMP_MRA_CaptureGPAReportPopUp_Rate" style="text-transform : uppercase"  />
							</logic:notPresent>
						</div>
						<div class="ic-col">
							<label>
								<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.amount" />
							</label>
							<logic:present name="GPAREPORTDETAILVO" property="amount">
							<bean:define id="amount" name="GPAREPORTDETAILVO" property="amount" />
							<ibusiness:moneyEntry  formatMoney="false"   componentID="CMP_MRA_CaptureGPAReportPopUp_Amount" id="amount" styleId="amount"  indexId="rowCount" readonly="true" name="GPAREPORTDETAILVO" moneyproperty="amount" property="amount" />
							</logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="amount">

								<ibusiness:moneyEntry  formatMoney="false"   componentID="CMP_MRA_CaptureGPAReportPopUp_Amount" id="amount" styleId="amount"  indexId="rowCount" readonly="true"  moneyproperty="amount" property="amount" value="0.0" />
							</logic:notPresent>
						</div>
						<div class="ic-col">
							<label><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.tax" />
							</label>
							<logic:present name="GPAREPORTDETAILVO" property="tax">
							<bean:define id="tax" name="GPAREPORTDETAILVO" property="tax" />
								<ihtml:text property="tax" value="<%=tax.toString()%>" maxlength="20" componentID="CMP_MRA_CaptureGPAReportPopUp_Tax" style="text-transform : uppercase"  />
							</logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="tax">

								<ihtml:text property="tax" value="" maxlength="20" componentID="CMP_MRA_CaptureGPAReportPopUp_Tax" style="text-transform : uppercase"  />
							</logic:notPresent>
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-col">
							<label><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.total" />
							</label>
							<logic:present name="GPAREPORTDETAILVO" property="total">
							<ibusiness:moneyEntry  formatMoney="false"   componentID="CMP_MRA_CaptureGPAReportPopUp_Total" id="total" styleId="total"  indexId="rowCount" readonly="true" name="GPAREPORTDETAILVO"  moneyproperty="total" property="total" />
							</logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="total">

								<ibusiness:moneyEntry  formatMoney="false"   componentID="CMP_MRA_CaptureGPAReportPopUp_Total" id="total" styleId="total"  indexId="rowCount" readonly="true"  moneyproperty="total" property="total" value="0.0" />
							</logic:notPresent>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-field-set">
					<legend>Flight Details
					</legend>
					<div class="ic-row">
						 <logic:present name="GPAREPORTDETAILVO" property="gpaReportingFlightDetailsVOs">

							  <a href="#" class="iCargoLink" id="addfltLink" >
							     Create
							   </a> |
							  <a href="#" class="iCargoLink"  id="deletefltLink" >
							     Delete
							  </a>

						    </logic:present>
							<logic:notPresent name="GPAREPORTDETAILVO" property="gpaReportingFlightDetailsVOs">
							 <a href="#" class="iCargoLink" id="addfltLink" value="" >
							     Create
							   </a> |
							   <a href="#" class="iCargoLink" value=""  id="deletefltLink" disabled="true">
							    Delete
							 </a>
						     </logic:notPresent>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="billdiv" style="height:120px;">
							<table class="fixed-header-table ic-pad-3">
								<thead>
									<tr class="ic-th-all">
										<th style="width:1%">
										<th style="width:60%">
										<th style="width:40%">
										<th style="width:30%">
										<th style="width:30%">
									</tr>

									<td class="iCargoTableHeader" rowspan="2"  ><html:checkbox property="headerChk" /></td>
									<td class="iCargoTableHeader" colspan="2" rowspan="1" ><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.carriage" /></td>
									<td class="iCargoTableHeader" rowspan="2" ><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.flightno" /></td>

								 </tr>
								 <tr>

									<td class="iCargoTableHeader"  ><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.from" /></td>
									<td class="iCargoTableHeader" ><common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.to" /></td>
								 </tr>
								</thead>
								<tbody>
									<% int index=0;  %>



									<logic:present name="GPAREPORTDETAILVO" property="gpaReportingFlightDetailsVOs">
									<bean:define id="gpaReportingFlightDetailsVOs" name="GPAREPORTDETAILVO" property="gpaReportingFlightDetailsVOs" type="java.util.Collection"/>

									<logic:iterate id="ITERATOR" name="gpaReportingFlightDetailsVOs" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO" indexId="nIndex">

									<logic:notEqual name="ITERATOR" property="operationFlag" value="D" >

									<common:rowColorTag index="nIndex">
									<tr>
										<td><html:checkbox property="rowchk" value="<%=String.valueOf(index)%>" /></td>
										<td>

											<logic:present name="ITERATOR" property="carriageFrom">
											<bean:define id="carriageFrom" name="ITERATOR" property="carriageFrom" />
												<ihtml:text property="carriageFrom" value="<%=(String)carriageFrom%>" componentID="CMP_MRA_CaptureGPAReportPopUp_CarriageFrom" style="text-transform : uppercase"  />
											</logic:present>
											<logic:notPresent name="ITERATOR" property="carriageFrom">
												<ihtml:text property="carriageFrom" value="" componentID="CMP_MRA_CaptureGPAReportPopUp_CarriageFrom" style="text-transform : uppercase"  />
											</logic:notPresent>
										</td>
										<td>

											<logic:present name="ITERATOR" property="carriageTo">
											<bean:define id="carriageTo" name="ITERATOR" property="carriageTo" />
												<ihtml:text property="carriageTo" value="<%=(String)carriageTo%>" componentID="CMP_MRA_CaptureGPAReportPopUp_CarriageTo" style="text-transform : uppercase"  />
											</logic:present>
											<logic:notPresent name="ITERATOR" property="carriageTo">
												<ihtml:text property="carriageTo" value="" componentID="CMP_MRA_CaptureGPAReportPopUp_CarriageTo" style="text-transform : uppercase"  />
											</logic:notPresent>
										</td>
										<td>
											<logic:present name="ITERATOR" property="flightCarrierCode">
											<logic:present name="ITERATOR" property="flightNumber">
											<bean:define id="flightCarrierCode" name="ITERATOR" property="flightCarrierCode" />
											<bean:define id="flightNumber" name="ITERATOR" property="flightNumber" />
												<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode"
													  id="flightNumber"
													  flightCodeProperty="flightNumber"
													  carriercodevalue="<%=flightCarrierCode.toString()%>"
													  flightcodevalue="<%=flightNumber.toString()%>"
													  componentID="CMP_MRA_CaptureGPAReportPopUp_FlightNo"
													  carrierCodeStyleClass="iCargoTextFieldVerySmall"
													  flightCodeStyleClass="iCargoTextFieldSmall"/>
											</logic:present>
											<logic:notPresent name="ITERATOR" property="flightNumber">
												<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode"
													  id="flightNumber"
													  flightCodeProperty="flightNumber"
													  carriercodevalue=""
													  flightcodevalue=""
													  componentID="CMP_MRA_CaptureGPAReportPopUp_FlightNo"
													  carrierCodeStyleClass="iCargoTextFieldVerySmall"
													  flightCodeStyleClass="iCargoTextFieldSmall"/>
											</logic:notPresent>
											</logic:present>
											<logic:notPresent name="ITERATOR" property="flightCarrierCode">
												<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode"
													  id="flightNumber"
													  flightCodeProperty="flightNumber"
													  carriercodevalue=""
													  flightcodevalue=""
													  componentID="CMP_MRA_CaptureGPAReportPopUp_FlightNo"
													  carrierCodeStyleClass="iCargoTextFieldVerySmall"
													  flightCodeStyleClass="iCargoTextFieldSmall"/>
											</logic:notPresent>
										</td>
									</tr>
								 </common:rowColorTag>
								</logic:notEqual>
								<% index++;  %>
								</logic:iterate>
								</logic:present>
								</tbody>
							</table>
						</div>
					</div>
					</div>
				</div>
			</div>
		
		<div class="ic-foot-container">
			<div class="ic-button-container">
				<div class="ic-row">
					<ihtml:nbutton property="btnOk" componentID="CMP_MRA_CaptureGPAReportPopUp_BTNOK" >
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.ok" />
					 </ihtml:nbutton>
					 <ihtml:nbutton property="btnClose" componentID="CMP_MRA_CaptureGPAReportPopUp_BTNCLOSE" >
						<common:message key="mailtracking.mra.gpareporting.capturegpareportpopup.close" />
				     </ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
 <!---CONTENT ENDS-->
     </ihtml:form>
   </div>
	</body>
</html:html>
