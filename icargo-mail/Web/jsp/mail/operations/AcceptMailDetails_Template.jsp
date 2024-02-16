<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  AcceptMailDetails_Template.jsp
* Date          	 :  31-MAY-2017

*************************************************************************/
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Set"%>
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

<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCompanyCode" />
<%}%>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeHNI" />
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
		  
<tr template="true" id="mailTemplateRow" style="display:none">

				<ihtml:hidden property="mailOpFlag" value="NOOP" />
				<td  class="iCargoTableDataTd ic-center">

					<input type="checkbox" name="selectMailTag" >
				</td>

				<td>
				    	<ihtml:text property="mailbagId"  componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILBAGID" value="" maxlength="29" style="width:200px" indexId="index"/> <!-- modified. A-8164 for ICRD 257594-->
				</td>
				<td>
				    	<ihtml:text property="mailOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" value="" maxlength="6" style="width:54px" />
                    <div class= "lovImgTbl valignT"><img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div><!--Modified table cell,image size width by a-7871 for ICRD-219443 -->
				</td>

				<td>
				    	<ihtml:text property="mailDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" value="" maxlength="6" style="width:54px" />
                    <div class= "lovImgTbl valignT"><img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
				</td>

				<td>
					<ihtml:select property="mailCat" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAT" value="" style="width:40px">
					<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
						<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
						<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
								<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
						</logic:iterate>
					</ihtml:select>
				</td>

				<td>
					<ihtml:text property="mailSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" value="" maxlength="2" style="width:22px" />
                    <div class= "lovImgTbl valignT"><img name="mailSCLov" id="mailSCLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
				</td>

				<td>
					<ihtml:text property="mailYr" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" value="" maxlength="1" style="width:18px" />
				</td>

				<td>
					<ihtml:text property="mailDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" value="" maxlength="4" style="width:34px"/>
				</td>

				<td>
				   	<ihtml:text property="mailRSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" value="" maxlength="3" style="width:34px" />
				</td>

				<td>
					<ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_HNI" value="" style="width:35px">
					<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
						<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
						  <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
							 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
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

				<td><!--modified by A-8353 for ICRD-274933-->
		
				<bean:define id="defWeightUnit" name="MailAcceptanceForm" property="defWeightUnit" />
						<ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight" unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT" unitListValue="<%=(String)defWeightUnit%>" indexId="index" unitTypePassed="MWT"/>
				</td>
				<td>
						<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  unitReq = "false"
									unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
									unitValue="0.0" style="width:35px"
						indexId="index" styleId="stdVolume" unitTypePassed="VOL" />

					<%--
					<ihtml:text property="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" value=""  maxlength="6"  />
					--%>
				</td>
				<td>
				    	<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE" value="" style="width:80px"/>
				</td>

				<td>
					<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="" style="width:40px"/>
				</td>

				<td>
					<ihtml:text property="mailCarrier" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" value="" maxlength="2" style="width:27px"/>
                    <div class= "lovImgTbl valignT"><img name="mailCarrierLov" id="mailCarrierLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
				</td>
				<% if(turkishFlag){%>
				<td>
						<ihtml:select property="mailCompanyCode" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CMPCOD" value=""   style="width:70px">
							<logic:notPresent name="form"  property="mailCompanyCode">
								<html:option value=""><common:message key="combo.select"/></html:option>
							</logic:notPresent>
							<bean:define id="oneTimeCmpCodeSess" name="oneTimeMailCompanyCodeSession" toScope="page" />
							<logic:iterate id="oneTimeCmpCodeVO" name="oneTimeCmpCodeSess" >
								<bean:define id="fieldValue" name="oneTimeCmpCodeVO" property="fieldValue" toScope="page" />
								<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCmpCodeVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
				<%}%>
				<td>
						<ihtml:text property="sealNo" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SEALNUM" value="" maxlength="15" style="width:50px"  />
				</td>
				<td>
				    <input type="checkbox" name="mailDamaged" />
				</td>
                 <!--Added as a part of ICRD-197419 by A-7540-->
				<td>
					   <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" />
				</td>

				<td>
					<ihtml:text property="mailCartId" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_BELLYCARDIT" value=""   maxlength="20"  style="width:50px"/>
				</td>

			</tr>