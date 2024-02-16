<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   toScope="page" scope="request"/>
   <%boolean turkishFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="TURKISH_SPECIFIC">
					 <% turkishFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
<business:sessionBean id="ContainerDetailsVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVOs" />
	<logic:present name="ContainerDetailsVOsSession">
		<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVO" />
	<logic:present name="ContainerDetailsVOSession">
		<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="warehouseSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="warehouse" />
	<logic:present name="warehouseSession">
		<bean:define id="warehouseSession" name="warehouseSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeMailClass" />
<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCompanyCode" />
<%}%>
<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.mailacceptance"
		  method="get"
		  attribute="weightRoundingVO" />
<business:sessionBean id="KEY_VOLUMEROUNDINGVO"
  moduleName="mail.operations"
  screenID="mailtracking.defaults.mailacceptance"
  method="get"
  attribute="volumeRoundingVO" />
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<tr template="true" id="mailTemplateRow" style="display:none">
	   
	   				<ihtml:hidden property="mailOpFlag" value="NOOP" />
	   				<td class="iCargoTableDataTd">
	   
	   					<input type="checkbox" name="selectMailTag" >
	   				</td>
					<td>
							<ihtml:text property="mailbagId"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILBAGID" value="" maxlength="29" style="width:190px" indexId="index"/>
					</td>	   
	   				<td>
	   				    	<ihtml:text property="mailOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" value="" maxlength="6" style="width:52px"/>
	   				    	 <div class= "lovImgTbl valignT"><img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
	   				</td>
	   
	   				<td>
	   				    	<ihtml:text property="mailDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" value="" maxlength="6" style="width:52px"/>
	   				    	 <div class= "lovImgTbl valignT"><img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"> </div>
	   				</td>
	   
	   				<td>
	   					<ihtml:select property="mailCat" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAT" value="" style="width:35px">
	   					<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
	   						<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
	   						<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
	   								<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
	   						</logic:iterate>
	   					</ihtml:select>
	   				</td>
	   
	   				<td>
	   					<ihtml:text property="mailSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" value="" maxlength="2" style="width:15px"/>
	   					 <div class= "lovImgTbl valignT"><img name="mailSCLov" id="mailSCLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
	   				</td>
	   
	   				<td>
	   					<ihtml:text property="mailYr" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" value="" maxlength="1" style="width:36px"/>
	   				</td>
	   
	   				<td>
	   					<ihtml:text property="mailDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" value="" maxlength="4" style="width:32px" />
	   				</td>
	   
	   				<td>
	   				   	<ihtml:text property="mailRSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" value="" maxlength="3"style="width:30px" />
	   				</td>
	   
	   				<td>
	   					<ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_HNI" value="" style="width:35px">
	   					<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
	   						<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
	   						  <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
	   							 <html:option value="<%=(String)fieldValue %>" ><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
	   						</logic:iterate>
	   					</ihtml:select>
	   				</td>
	   
	   				<td>
	   					<ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_RI" value="" style="width:35px">
	   					<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
	   						<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
	   						  <bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
	   							 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
	   						</logic:iterate>
	   					</ihtml:select>
	   			        </td>
	   
	   				<td>
					<bean:define id="defWeightUnit" name="MailAcceptanceForm" property="defWeightUnit" />
	   				  <ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight" unitListName="weightUnit"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitListValue="<%=(String)defWeightUnit%>"  unitTypeStyle="iCargoMediumComboBox" unitTypePassed="MWT"/>
	   				</td><!--modified by A-8353 for ICRD-274933-->
	   				<td>
	   
	   
	   					<logic:present name="KEY_VOLUMEROUNDINGVO">
	   								<bean:define id="sampleStdVolVo" name="KEY_VOLUMEROUNDINGVO" />
	   								<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
	   								<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label=""  unitReq = "false" dataName="sampleStdVol"
	   									unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
	   									unitValue="0.0" style="background :'<%=color%>'"
	   									indexId="index" styleId="stdVolume" style="width:40px"/>
	   					 </logic:present>
	   
	   					<%--
	   					<ihtml:text property="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" value=""  maxlength="6"  />
	   					--%>
	   				</td>
	   				<td>
	   				    	<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image"  componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE" value="" style="width:80px"/>
	   				</td>
	   
	   				<td>
	   					<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value=""style="width:38px"/>
	   				</td>
	   
	   				<td>
	   					<ihtml:text property="mailCarrier" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" value="" maxlength="2" style="width:25px"/>
	   				         <div class= "lovImgTbl valignT"><img name="mailCarrierLov" id="mailCarrierLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"> </div>
	   				</td>
					<td>
						<ihtml:text property="sealNo" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNUM" value="" maxlength="15"  />
					</td>     	 
	   				<td>
	   				    <input type="checkbox" name="mailDamaged" />
	   				</td>
	   
	   				<td>
					   <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" />
	   				</td>
	   
	   				<td>
	   					<ihtml:text property="mailCartId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT" value=""   maxlength="20"  style="width:220px"/>
	   				</td>
	   
	   			</tr>