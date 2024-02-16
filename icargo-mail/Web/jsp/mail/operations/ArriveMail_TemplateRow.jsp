<%@ include file="/jsp/includes/tlds.jsp" %>
<%boolean turkishFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="TURKISH_SPECIFIC">
					 <% turkishFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
<% if(turkishFlag){%>
<business:sessionBean id="oneTimeMailCompanyCodeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeCompanyCode" />
<%}%>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeRSN" />
<bean:define id="MailArrivalForm" name="MailArrivalForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm" toScope="page" scope="request"/>


<tr template="true" id="mailTemplateRow" style="display:none">
			<ihtml:hidden property="mailOpFlag" value="NOOP" />
			<td class="iCargoTableDataTd ic-center">
			<input type="checkbox" name="selectMailTag" ></td>
			<td>
			   	<ihtml:text property="mailbagId" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILBAGID" value="" maxlength="29" style="width:220px"/> <!-- modified. A-8164 for ICRD 257594-->
			</td>			
			<td>
			   	<ihtml:text property="mailOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILOOE" value="" maxlength="6" style="width:51px"/>
			  <div class="lovImgTbl valignT"><img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div><!--modified by a-7871 for ICRD-244927-->
			</td>
			<td>
				<ihtml:text property="mailDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDOE" value="" maxlength="6" style="width:51px"/>
			        <div class="lovImgTbl valignT"><img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div><!--modified by a-7871 for ICRD-244927-->
			</td>
			<td>
			  <ihtml:select property="mailCat" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CAT" value="" style="width:35px">
				<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
				<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
					<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
					<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
				</logic:iterate>
			  </ihtml:select>
			</td>
			<td>
				<ihtml:text property="mailSC" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILSC" value="" maxlength="2" style="width:22px" />
				<div class="lovImgTbl valignT"><img name="mailSCLov" id="mailSCLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div><!--modified by a-7871 for ICRD-244927-->
			</td>
			<td>
				<ihtml:text property="mailYr" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILYR" value="" maxlength="1" style="width:13px"/>
			</td>
			<td>
				<ihtml:text property="mailDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILDSN" value="" maxlength="4" />
			</td>
			<td>
				<ihtml:text property="mailRSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILRSN" value="" maxlength="3" style="width:23px"/>
			</td>
			<td>
			   <ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_HNI" value="" style="width:35px">
				<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
				<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
					 <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
					 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
			</td>
			<td>
			   <ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RI" value="" style="width:35px">
				<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
				<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
				      <bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
				      <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
			</td>
			<td>
			       <bean:define id="defWeightUnit" name="MailArrivalForm" property="defWeightUnit"/> 
                  <ibusiness:unitCombo  unitTxtName="mailWt" style="width:35px"  label="" title="Revised Gross Weight" unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" 
				  componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILWT" unitListValue="<%=(String)defWeightUnit%>" indexId="index" unitTypePassed="MWT"/>

			<td>
				<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_MAILVOLUME"  unitReq = "false" unitValueMaxLength="4"
				style="width:30px" unitValue="0.0"
				indexId="index" styleId="mailVolume"  unitTypePassed="VOL"/>
			</td>
			<td colspan="2">
				<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE"   value="" />
				<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="" style="width:37px"/>
			</td>
			<td class="ic-center">
				<input type="checkbox" name="mailReceived" value="true" checked disabled />
			</td>
			<td class="ic-center">
				<input type="checkbox" name="mailDelivered" />
			</td>
			<!--Added as a part of ICRD-197419 by A-7540-->
			<td>
			     <ihtml:text property="mailRemarks" styleClass="iCargoTextFieldExtraLong" value="" />
			</td>
			<td class="ic-center">
				<input type="checkbox" name="mailDamaged" />
			</td>
			<% if(turkishFlag){%>
			<td>
			    <% String cmpcod = ""; %>
			   <logic:present name="mailDetailsVO" property="mailCompanyCode">
			   <bean:define id="hni" name="mailDetailsVO"  property="mailCompanyCode"  toScope="page"/>

				<% cmpcod = (String) hni;%>
			   </logic:present>
			   <ihtml:select property="mailCompanyCode" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CMPCOD" value="<%=cmpcod%>"   style="width:75px">
			   <logic:notPresent name="form"  property="mailCompanyCode">
		               		<html:option value=""><common:message key="combo.select"/></html:option>
		               </logic:notPresent>
				<bean:define id="oneTimeCmpCodeSess" name="oneTimeMailCompanyCodeSession" toScope="page" />
				<logic:iterate id="oneTimeCmpcodeVO" name="oneTimeCmpCodeSess" >
					 <bean:define id="fieldValue" name="oneTimeCmpcodeVO" property="fieldValue" toScope="page" />
					 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCmpcodeVO" property="fieldValue"/></html:option>
				</logic:iterate>
			   </ihtml:select>
			</td>
			<%}%>
			<td>
				<ihtml:text property="sealNo" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SEALNUM" value="" maxlength="15" style="width:120px"  />
			</td>
			<td>
				<ihtml:text property="arrivalSealNo" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_ARRSEALNUM" value="" maxlength="15" style="width:90px" /> <!--modified by a-7871 for ICRD-244927-->
			</td>
		</tr>