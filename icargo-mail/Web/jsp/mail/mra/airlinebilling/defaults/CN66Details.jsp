<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mailtracking
* File Name          	 : CN66Details.jsp
* Date                 	 : 12-Feb-2007,11-AUG-2008
* Author(s)              : A-2408,A-2391
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm" %>
<bean:define id="form"
		 name="CN66DetailsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm"
		 toScope="page" />

<html:html>
<head> 
	
	


<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.airlinebilling.defaults.cn66details.title"/></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/CN66Details_Script.jsp" />
</head>
<body>
	

<business:sessionBean id="KEY_ONETIMES"
   	moduleName="mailtracking.mra.airlinebilling.defaults"
   	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
 	method="get" attribute="oneTimeVOs" />
<business:sessionBean id="CN66DETAILS"
    	moduleName="mailtracking.mra.airlinebilling.defaults"
    	screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
 	method="get" attribute="cn66Details" />
<business:sessionBean id="oneTimeRISession"
		moduleName="mailtracking.mra.airlinebilling.defaults"
		screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
		method="get"
		attribute="oneTimeRI" />
<business:sessionBean
		id="oneTimeHNISession"
		moduleName="mailtracking.mra.airlinebilling.defaults"
		screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
		method="get"
		attribute="oneTimeHNI" />
<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		moduleName="mailtracking.mra.airlinebilling.defaults"
		screenID="mailtracking.mra.airlinebilling.defaults.capturecn66"
		method="get"
		attribute="weightRoundingVO" />
 <div class="iCargoPopUpContent">
    <ihtml:form action="/mailtracking.mra.airlinebilling.defaults.captureCN66.CN66Details.do" focus="carriageFrom" styleClass="ic-main-form">
    <ihtml:hidden property="cn66Key"/>
    <ihtml:hidden property="innerRowSelected"/>
    <ihtml:hidden property="outerRowSelected"/>
    <ihtml:hidden property="screenStatus"/>
    <ihtml:hidden property="blgCurCode"/>
    <ihtml:hidden  property="rowCount"/>
    <ihtml:hidden  property="showDsnPopUp"/>
    <ihtml:hidden  property="count"/>
    <ihtml:hidden  property="tempRowIndicator"/>
    <ihtml:hidden property="focFlag"/>
    <ihtml:hidden property="popupFlag"/>

	 <div class="ic-content-main" >
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.mra.airlinebilling.defaults.cn66details" />
		</span>
			<div class="ic-main-container">
			<div class="ic-row paddL5">
				<div class="ic-button-container paddR5">
					<a href="#" class="iCargoLink" id="btNext">Next</a>
				</div>
				
					<div class="ic-input ic-split-35">
					<label>
					<common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.carriagefrom" />
					</label>
					<ihtml:text property="carriageFrom" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CARRFROM" maxlength="4"/>
					<div class="lovImg">
				        <img src="<%=request.getContextPath()%>/images/lov.png" id="carriagefromLov" height="22" width="22" alt="" /></div>
					</div>
					<div class="ic-input ic-split-35">
					<label>
					<common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.carriageto" />
					</label>
						<ihtml:text property="carriageTo" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CARRTO" maxlength="4"/>
				       <div class="lovImg">  <img src="<%=request.getContextPath()%>/images/lov.png" id="carriagetoLov" height="22" width="22" alt="" /></div>
					</div>
				</div>
				<div class="ic-row paddR5">
					<h4><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.capturedetails" /></h4>
				
					<div class="ic-button-container">
						<a href="#" class="iCargoLink" id="btnAdd" onclick="addRow()"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.button.add"/></a>
										<a href="#" class="iCargoLink" id="btnDelete" onclick="deleteRow()"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.button.delete"/></a>
					</div>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer" style="height:360px">
					<table class="fixed-header-table" style="width:150%">
					<thead>
					<tr class="ic-th-all">
						<th style="width:3%"/>
						<th style="width:14%"/>
						<th style="width:8%"/>
						<th style="width:8%"/>
						<th style="width:13%"/>

						<th style="width:6%"/>
						<th style="width:6%"/>
						<th style="width:6%"/>
						<th style="width:6%"/>
						<th style="width:15%"/>

						<th style="width:7%"/>
						<th style="width:7%"/>
						<th style="width:7%"/>
						<th style="width:7%"/>
						<th style="width:5%"/>

						<th style="width:12%"/>
					</tr>
					<tr>
					<td rowspan="2" class="iCargoTableHeader ic_inline_chcekbox ic-center"><input type="checkbox" name="allCheck" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.category" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.org" /><span class="iCargoMandatoryFieldIcon">*</span></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.dest" /><span class="iCargoMandatoryFieldIcon">*</span></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.fltno" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.dsn" /><span class="iCargoMandatoryFieldIcon">*</span></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.rsn" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.hni" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.ri" /></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.dateofissue" /><span class="iCargoMandatoryFieldIcon">*</span></td>

					<td class="iCargoTableHeader" colspan="4"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.wt" /><span class="iCargoMandatoryFieldIcon">*</span></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.rate" /><span class="iCargoMandatoryFieldIcon">*</span></td>
					<td rowspan="2" class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.amount" /></td>
					</tr>
					<tr>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.lc/ao" /></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.cp" /></td>
					<!-- <td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.sal" /></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.uld" /></td> -->
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.sv" /></td>
					<td class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.ems" /></td>
					</tr>
					</thead>
					<tbody id="cn66DetailsTable">
					<logic:present name="CN66DETAILS">
					<logic:iterate id="cn66Value" name="CN66DETAILS" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO" indexId="rowCount">
					<ihtml:hidden name="cn66Value" property="operationFlag"/>
					<ihtml:hidden name="cn66Value" property="dsnIdr"/>
					<ihtml:hidden name="cn66Value" property="malSeqNum"/>
					
					<ihtml:hidden name="cn66Value" property="sequenceNumber"/>
					<logic:notEqual name="cn66Value" property="operationFlag" value="D">
					<tr>
					<td class="ic-center">
					<ihtml:checkbox property="check" value="<%=String.valueOf(rowCount)%>"/>
					</td>
					<td>
					<ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_CATEGORYDET" name="cn66Value" property="mailCategoryCode">
					<ihtml:option value=""></ihtml:option>
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
					</td>
					<td >
					<ihtml:text name="cn66Value" property="origin" value="<%=cn66Value.getOrigin()%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_ORG"
					maxlength="4" indexId="rowCount"/><div class="lovImgTbl">
					<img src="<%=request.getContextPath()%>/images/lov.png" id="originLov" height="16" width="16"
					onClick="displayLOV('showStation.do','N','Y','showStation.do',targetFormName.origin.value,'Origin','0','origin','',<%=String.valueOf(rowCount)%>)" alt="" /></div>
					</td>
					<td>
					<ihtml:text name="cn66Value" property="destination" value="<%=cn66Value.getDestination()%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DEST"
					maxlength="4" indexId="rowCount"/><div class="lovImgTbl">
					<img src="<%=request.getContextPath()%>/images/lov.png" id="destLov" height="16" width="16"
					onClick="displayLOV('showStation.do','N','Y','showStation.do',targetFormName.destination.value,'Destination','0','destination','',<%=String.valueOf(rowCount)%>)" alt="" /></div>
					</td>
					<td>
					<logic:present	name="cn66Value" property="flightNumber">
					<bean:define id="flightNumber" name="cn66Value" property="flightNumber" />
					<bean:define id="carCode" name="cn66Value" property="carrierCode" />
					<ibusiness:flightnumber carrierCodeProperty="carrierCode"
					id="flightNumber"
					flightCodeProperty="flightNumber"
					carriercodevalue="<%=(String)carCode%>"
					flightcodevalue="<%=(String)flightNumber%>"
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_FLTNUM"
					carrierCodeStyleClass="iCargoTextFieldVerySmall"
					flightCodeStyleClass="iCargoTextFieldSmall"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="flightNumber">
					<ibusiness:flightnumber carrierCodeProperty="carrierCode"
					id="flightNumber"
					flightCodeProperty="flightNumber"
					carriercodevalue=""
					flightcodevalue=""
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_FLTNUM"
					carrierCodeStyleClass="iCargoTextFieldVerySmall"
					flightCodeStyleClass="iCargoTextFieldSmall"/>
					</logic:notPresent>
					</td>
					<td>
					<logic:present	name="cn66Value" property="despatchSerialNo">
					<ihtml:text name="cn66Value" property="despatchNumber" value="<%=cn66Value.getDespatchSerialNo()%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPNUM"
					maxlength="4"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="despatchSerialNo">
					<ihtml:text property="despatchNumber" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPNUM" maxlength="4"/>
					<ihtml:hidden property="despatchNumber"/>
					</logic:notPresent>
					</td>
					<td>
					<logic:present	name="cn66Value" property="receptacleSerialNo">
					<ihtml:text name="cn66Value" property="receptacleSerialNo" value="<%=cn66Value.getReceptacleSerialNo()%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RESCPNUM"
					maxlength="3"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="receptacleSerialNo">
					<ihtml:text property="receptacleSerialNo" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RESCPNUM"  maxlength="3"/>
					<ihtml:hidden property="receptacleSerialNo"/>
					</logic:notPresent>
					</td>
					<td>
					<%
					String hni = "" ;
					%>
					<logic:present name="cn66Value" property="hni">
					<bean:define id="hniMail" name="cn66Value" property="hni" toScope="page"/>
					<%
					hni = String.valueOf(hniMail);
					%>
					</logic:present>
					<ihtml:select property="hni" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_HNI" value="<%=hni%>">
					<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
					<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
					  <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
						 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
					</logic:iterate>
					</ihtml:select>
					</td>
					<td>
					<%
					String ri = "" ;
					%>
					<logic:present name="cn66Value" property="regInd">
					<bean:define id="regInd" name="cn66Value" property="regInd" toScope="page"/>
					<%
					ri = String.valueOf(regInd);
					%>
					</logic:present>
					<ihtml:select property="regInd" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RI" value="<%=ri%>" >
					<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
					<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
					<bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
					<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
					</logic:iterate>
					</ihtml:select>
					</td>
					<td>
					<logic:present	name="cn66Value" property="despatchDate">
					<bean:define id="despatchDate" name="cn66Value" property="despatchDate" />
					<%
					String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)despatchDate).toCalendar(),"dd-MMM-yyyy");
					%>
					<ibusiness:calendar
					property="despatchDate"
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPDATE"
					type="image"
					id="despatchDate"
					value="<%=assignedLocalDate%>"
					indexId="rowCount"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="despatchDate">
					<ibusiness:calendar
					property="despatchDate"
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPDATE"
					type="image"
					id="despatchDate"
					value=""
					indexId="rowCount"/>
					</logic:notPresent>
					</td>

					<%
					String localMailCategory="";
					%>
					<logic:present	name="cn66Value" property="mailSubClass">
					<bean:define id="mailSubClass" name="cn66Value" property="mailSubClass" />
					<%
					localMailCategory=mailSubClass.toString();
					%>
					<bean:define id="mailcat" value="<%=localMailCategory%>"/>
					</logic:present>
					<td>

					<%String locallctotalWeight="0";%>
					<logic:present	name="cn66Value" property="totalWeight">

					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="LC">
					<%
					locallctotalWeight=((Double)totalWeight).toString();
					%>
					</logic:equal>


					</logic:present>
					<!--<ihtml:text name="cn66Value" property="totalLcWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_LC"
					styleId="totalLcWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtLC",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalLcWeight" unitTxtName="totalLcWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_LC_NEW" label=""  unitReq = "false" dataName="sampleStdWtLC"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="LC"
					unitValue="<%=locallctotalWeight%>"
					indexId="rowCount" maxlength="20" styleId="totalLcWeight" />
					</logic:present>



					</td>
					<td>


					<%String localcptotalWeight="0";%>
					<logic:present	name="cn66Value" property="totalWeight">
					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="CP">
					<%
					localcptotalWeight=((Double)totalWeight).toString();
					%>
					</logic:equal>

					</logic:present>
					<!--<ihtml:text name="cn66Value" property="totalCpWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CP"
					styleId="totalCpWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtCP",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalCpWeight" unitTxtName="totalCpWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CP_NEW" label=""  unitReq = "false" dataName="sampleStdWtCP"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="CP"
					unitValue="<%=localcptotalWeight%>"
					indexId="rowCount"  maxlength="20" styleId="totalCpWeight" />
					</logic:present>

					</td>

					<!--<td>


					<logic:present	name="cn66Value" property="totalWeight">
					<%String localsaltotalWeight="0";%>
					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="SAL">
					<%
					localsaltotalWeight=((Double)totalWeight).toString();
					%>

					</logic:equal>
					<ihtml:text name="cn66Value" property="totalSalWeight" value="<%=localsaltotalWeight%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SAL"
					styleId="totalSalWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>
					</logic:present>

					</td>
					<td>


					<logic:present	name="cn66Value" property="totalWeight">
					<%String localuldtotalWeight="0";%>
					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="UL">
					<%
					localuldtotalWeight=((Double)totalWeight).toString();
					%>

					</logic:equal>
					<ihtml:text name="cn66Value" property="totalUldWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_ULD"
					styleId="totalUldWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>
					</logic:present>
					</td> -->

					<td>


					<%String localsvtotalWeight="0";%>
					<logic:present	name="cn66Value" property="totalWeight">
					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="SV">
					<%
					localsvtotalWeight=((Double)totalWeight).toString();
					%>

					</logic:equal>
					</logic:present>
					<!--<ihtml:text name="cn66Value" property="totalSvWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SV"
					styleId="totalSvWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtSV",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalSvWeight" unitTxtName="totalSvWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SV_NEW" label=""  unitReq = "false" dataName="sampleStdWtSV"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="SV"
					unitValue="<%=localsvtotalWeight%>"
					indexId="rowCount"  maxlength="20" styleId="totalSvWeight" />
					</logic:present>
					</td>
					<td>
					<%String localemstotalWeight="0";%>
					<logic:present	name="cn66Value" property="totalWeight">
					<bean:define id="totalWeight" name="cn66Value" property="totalWeight" />
					<logic:equal name="mailcat" value="EMS">
					<%
					localemstotalWeight=((Double)totalWeight).toString();
					%>
					</logic:equal>
					</logic:present>
					<!--<ihtml:text name="cn66Value" property="totalEmsWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_EMS"
					styleId="totalEMSWeight"  maxlength="20" indexId="rowCount" onblur="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtEMS",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalEmsWeight" unitTxtName="totalEmsWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_EMS_NEW" label=""  unitReq = "false" dataName="sampleStdWtEMS"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="EMS"
					unitValue="<%=localemstotalWeight%>"
					indexId="rowCount" maxlength="20" styleId="totalEmsWeight"  />
					</logic:present>
					</td>
					<td>
					<logic:present	name="cn66Value" property="rate">
					<%String rateStr="0";%>
					<bean:define id="rate" name="cn66Value" property="rate" />

					<%
					rateStr=((Double)rate).toString();
					%>
					<ihtml:text  indexId="rowCount"  property="rate" value="<%=rateStr%>"
					onchange="change(this)" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RATE"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="rate" >
					<ihtml:text  indexId="rowCount"  property="rate" value=""
					onchange="change(this)" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RATE"/>
					</logic:notPresent>
					</td>

					<td>
					<div>
					<logic:present name="cn66Value" property="amount" >
					<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNT" id="amount"
					name="cn66Value" moneyproperty="amount"  property="amount"  maxlength="20"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="amount" >
					<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AMOUNT" id="amount"
					name="cn66Value" moneyproperty="amount"  property="amount"  maxlength="20"/>
					</logic:notPresent>
					</div>

					</td>




					</tr>
					</logic:notEqual>
					</logic:iterate>
					</logic:present>
					<!-- template row starts-->
					<logic:present name="cn66Value" property="blgCurCode">
					<bean:define id="currencyCode" name="cn66Value" property="blgCurCode"/>
					</logic:present>
					<logic:notPresent name="cn66Value" property="blgCurCode">
					<bean:define id="currencyCode" value=""/>
					</logic:notPresent>
					<%String currencyCode =(String) pageContext.getAttribute("currencyCode");%>
					<bean:define id="templateRowCount" value="0"/>
					<tr template="true" id="cn66DetailsTableRow" style="display:none">
					<td class="iCargoTableDataTd">
					<html:checkbox property="check"/>
					<ihtml:hidden property="operationFlag" value="NOOP"/>
					<ihtml:hidden property="sequenceNumber" value="0"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:select componentID="CMP_MRA_AIRLINEBILLING_INWARD_CATEGORYDET" property="mailCategoryCode" indexId="templateRowCount">
					<ihtml:option value=""></ihtml:option>
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
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="origin" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_ORG"
					maxlength="4" indexId="templateRowCount"/><div class="lovImgTbl">
					<img src="<%=request.getContextPath()%>/images/lov.png" id="originLov" height="16" width="16"
					onClick="displayLOV('showStation.do','N','Y','showStation.do',targetFormName.origin.value,'Origin','0','origin','',<%=String.valueOf(templateRowCount)%>)" alt="" /></div>

					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="destination" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DEST"
					maxlength="4" indexId="templateRowCount"/><div class="lovImgTbl">
					<img src="<%=request.getContextPath()%>/images/lov.png" id="destLov" height="16" width="16"
					onClick="displayLOV('showStation.do','N','Y','showStation.do',targetFormName.destination.value,'Destination','0','destination','',<%=String.valueOf(templateRowCount)%>)" alt="" /></div>

					</td>
					<td class="iCargoTableDataTd">
					<ibusiness:flightnumber carrierCodeProperty="carrierCode"
					id="flightNumber"
					flightCodeProperty="flightNumber"
					carriercodevalue=""
					flightcodevalue=""
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_FLTNUM"
					carrierCodeStyleClass="iCargoTextFieldVerySmall"
					flightCodeStyleClass="iCargoTextFieldSmall"
					indexId="templateRowCount"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="despatchNumber" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPNUM"
					maxlength="4" indexId="templateRowCount"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="receptacleSerialNo" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RESCPNUM"
					maxlength="3" indexId="templateRowCount"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="hni" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_HNI"
					maxlength="1" indexId="templateRowCount"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="regInd" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RI"
					maxlength="1" indexId="templateRowCount"/>
					</td>
					<td class="iCargoTableDataTd">
					<ibusiness:calendar
					property="despatchDate"
					componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_DESPDATE"
					type="image"
					id="despatchDate"
					value=""
					indexId="0"/>
					</td>

					<td class="iCargoTableDataTd">
					<!--<ihtml:text name="cn66Value" property="totalLcWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_LC"
					styleId="totalLcWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtLC",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalLcWeight" unitTxtName="totalLcWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_LC_NEW" label=""  unitReq = "false" dataName="sampleStdWtLC"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="LC"
					unitValue=""
					indexId="templateRowCount" maxlength="20" styleId="totalLcWeight" />
					</logic:present>



					<!--ibusiness:moneyEntry currencyCode="<%=currencyCode%>" value="0" indexId="templateRowCount"  formatMoney="true"
					name="cn66Value" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_LC" id="totalLcWeight"    property="totalLcWeight"
					onmoneychange="updateCpStatus" maxlength="20" style="text-align : right;border: 0px;background:"  /-->
					</td>


					<td class="iCargoTableDataTd">
					<!--<ihtml:text name="cn66Value" property="totalCpWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CP"
					styleId="totalCpWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtCP",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalCpWeight" unitTxtName="totalCpWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CP_NEW" label=""  unitReq = "false" dataName="sampleStdWtCP"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="CP"
					unitValue=""
					indexId="templateRowCount" maxlength="20" styleId="totalCpWeight"  />
					</logic:present>


					</td>

					<!-- <td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="totalSalWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SAL"
					styleId="totalSalWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>


					</td>

					<td class="iCargoTableDataTd">
					<ihtml:text name="cn66Value" property="totalUldWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_ULD"
					styleId="totalUldWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>


					</td> -->

					<td class="iCargoTableDataTd">
					<!--<ihtml:text name="cn66Value" property="totalSvWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SV"
					styleId="totalSvWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtSV",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalSvWeight" unitTxtName="totalSvWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SV_NEW" label=""  unitReq = "false" dataName="sampleStdWtSV"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="SV"
					unitValue=""
					indexId="templateRowCount" maxlength="20" styleId="totalSvWeight"  />
					</logic:present>


					</td>
					<td class="iCargoTableDataTd">
					<!--<ihtml:text name="cn66Value" property="totalEmsWeight" value="" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_SV"
					styleId="totalEmsWeight"  maxlength="20" indexId="templateRowCount" onchange="updateCpStatus()"/>-->
					<logic:present name="KEY_WEIGHTROUNDINGVO">
					<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
					<% request.setAttribute("sampleStdWtEMS",sampleStdWtVo); %>
					<ibusiness:unitdef id="totalEmsWeight" unitTxtName="totalEmsWeight" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_EMS_NEW" label=""  unitReq = "false" dataName="sampleStdWtEMS"
					unitValueStyle="iCargoEditableTextFieldRowColor1" title="EMS"
					unitValue=""
					indexId="templateRowCount" maxlength="20" styleId="totalEmsWeight" />
					</logic:present>
					</td>
					<td>
					<ihtml:text  indexId="rowCount"  name="cn66Value" property="rate" value="1"
					onchange="change(this)" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_RATE"
					/>
					</td>

					<td class="iCargoTableDataTd">

					<ibusiness:moneyEntry currencyCode="<%=currencyCode%>" value="0" indexId="templateRowCount"  formatMoney="true"
					name="cn66Value" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_AMOUNT" id="amount"    property="amount"
					maxlength="20"  />

					</td>
					</tr>
					<!-- template row ends -->
					</tbody>
					</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container paddR5">
				<div class="ic-button-container">
					<ihtml:nbutton property="btnok" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_OK" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.button.ok" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnclose" componentID="CMP_MRA_AIRLINEBILLING_INWARD_DET_CLOSE" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.cn66details.button.close" />
					</ihtml:nbutton>
				</div>
			</div>
	</div>


</ihtml:form>
</div>


	</body>
</html:html>
