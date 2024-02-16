<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: DamageDetails.jsp
* Date				: 17-July-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>

		
		
	<html:html>
	<head> 
		
		<title><common:message bundle="mailArrivalResources" key="mailtracking.defaults.damagedetails.lbl.title" /></title>
		<meta name="decorator" content="popup_panel">
		<common:include type="script" src="/js/mail/operations/ArrivalDamageDetails_Script.jsp" />
	</head>

<body>
	
	
<div class="iCargoPopUpContent" >

	<ihtml:form action="/mailtracking.defaults.arrivemail.damagedetails.screenload.do" styleClass="ic-main-form">
				<business:sessionBean id="damageCodes" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeDamageCodes" />
				<business:sessionBean id="damagedMailbagVOs" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="damagedMailbagVOs" />
				<ihtml:hidden property="selectedMailBag" />
				<ihtml:hidden property="damageFromScreen" />
<div class="ic-content-main">

		  <div class="ic-main-container">
		   <div class="ic-row">
			 <span class="ic-page-title ic-display-none"> <common:message key="mailtracking.defaults.damagedetails.lbl.pagetitle" />
					</span>
		 </div>
		  <div class ="ic-row">
		  <div class="ic-button-container">
		 <a id="addLink" name="formlink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.damagedetails.lbl.addlink" /></a> |
								<a id="deleteLink" name="formlink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.damagedetails.lbl.deletelink" /></a>
		  </div>
		  </div>
		  <div class ="ic-row">
		  <div class="tableContainer"  id="div1"  style="height:139px;">
		  <table  class="fixed-header-table">
		  	<thead>
				<tr class="iCargoTableHeader">
			  <td width="7%"><div align="center">
				  <input type="checkbox" name="damageCheckAll" value="checkbox">
				</div></td>
			  <td width="35%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.damagedetails.lbl.damagecode" /></td>
			  <td width="58%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.damagedetails.lbl.remarks" /></td>
			</tr>
			</thead>
					<tbody>
	<logic:present name="damagedMailbagVOs">
				<bean:define id="mailbagVOs" name="damagedMailbagVOs" toScope="page"/>
				<logic:iterate id="mailbagvo" name="mailbagVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO">

					<logic:present name="mailbagvo" property="operationFlag">
						<bean:define id="operFlag" name="mailbagvo" property="operationFlag" toScope="page"/>

						<logic:notEqual name="mailbagvo" property="operationFlag" value="D">

							<tr class="iCargoTableCellsCenterRowColor1">
							  <td><div align="center"><input type="checkbox" name="damageSubCheck" value="<%= rowid.toString() %>"></div></td>
							  <td>
							  	<logic:present name="mailbagvo" property="damageCode">
							  		<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGECODE" tabindex="1" value="<%= mailbagvo.getDamageCode() %>">
										<logic:present name="damageCodes">
										<bean:define id="damagecodes" name="damageCodes" toScope="page"/>

											<logic:iterate id="onetmvo" name="damagecodes">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

												<html:option value="<%= value.toString() %>"><%= desc %></html:option>

											</logic:iterate>
										</logic:present>
									</ihtml:select>
							  	</logic:present>
							  	<logic:notPresent name="mailbagvo" property="damageCode">
									<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGECODE" tabindex="1" value="">
										<logic:present name="damageCodes">
										<bean:define id="damagecodes" name="damageCodes" toScope="page"/>

											<logic:iterate id="onetmvo" name="damagecodes">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo" property="fieldValue"/>
												<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

												<html:option value="<%= value.toString() %>"><%= desc %></html:option>

											</logic:iterate>
										</logic:present>
									</ihtml:select>
							  	</logic:notPresent>

							  </td>
							  <td>
								<logic:present name="mailbagvo" property="remarks">
									<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGEREMARKS" maxlength="1000" tabindex="1" value="<%= mailbagvo.getRemarks() %>" style="width:320px"/>
								</logic:present>
								<logic:notPresent name="mailbagvo" property="remarks">
									<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGEREMARKS" maxlength="1000" tabindex="1" value="" style="width:320px"/>
								</logic:notPresent>
							  </td>
							</tr>

						</logic:notEqual>
						<logic:equal name="mailbagvo" property="operationFlag" value="D">

							<logic:present name="mailbagvo" property="damageCode">
								<ihtml:hidden property="damageCode" value="<%= mailbagvo.getDamageCode() %>"/>
							</logic:present>
							<logic:notPresent name="mailbagvo" property="damageCode">
								<ihtml:hidden property="damageCode" value=""/>
							</logic:notPresent>

							<logic:present name="mailbagvo" property="remarks">
								<ihtml:hidden property="damageRemarks" value="<%= mailbagvo.getRemarks() %>"/>
							</logic:present>
							<logic:notPresent name="mailbagvo" property="remarks">
								<ihtml:hidden property="damageRemarks" value=""/>
							</logic:notPresent>

						</logic:equal>
					</logic:present>
					<logic:notPresent name="mailbagvo" property="operationFlag">

						<tr class="iCargoTableCellsCenterRowColor1">
						  <td><div align="center"><input type="checkbox" name="damageSubCheck" value="<%= rowid.toString() %>"></div></td>
						  <td>
							<logic:present name="mailbagvo" property="damageCode">
								<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGECODE" tabindex="1" value="<%= mailbagvo.getDamageCode() %>">
									<logic:present name="damageCodes">
									<bean:define id="damagecodes" name="damageCodes" toScope="page"/>

										<logic:iterate id="onetmvo" name="damagecodes">
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

											<html:option value="<%= value.toString() %>"><%= desc %></html:option>

										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</logic:present>
							<logic:notPresent name="mailbagvo" property="damageCode">
								<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGECODE" tabindex="1" value="">
									<logic:present name="damageCodes">
									<bean:define id="damagecodes" name="damageCodes" toScope="page"/>

										<logic:iterate id="onetmvo" name="damagecodes">
											<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
											<bean:define id="value" name="onetimevo" property="fieldValue"/>
											<bean:define id="desc" name="onetimevo" property="fieldDescription"/>

											<html:option value="<%= value.toString() %>"><%= desc %></html:option>

										</logic:iterate>
									</logic:present>
								</ihtml:select>
							 </logic:notPresent>
						  </td>
						  <td>
							<logic:present name="mailbagvo" property="remarks">
								<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGEREMARKS" maxlength="1000" tabindex="1" value="<%= mailbagvo.getRemarks() %>" style="width:320px"/>
							</logic:present>
							<logic:notPresent name="mailbagvo" property="remarks">
								<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_DAMAGEREMARKS" maxlength="1000" tabindex="1" value="" style="width:320px"/>
							</logic:notPresent>
						  </td>
						</tr>

					</logic:notPresent>

				</logic:iterate>
          	</logic:present>


			</tbody>
		  </table>
		  </div>
		  </div>
		  </div>
		  <div class="ic-foot-container">
		  <div class="ic-button-container">
	<ihtml:nbutton property="btOk" tabindex="1" componentID="BUT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_OK">
								<common:message key="mailtracking.defaults.damagedetails.btn.ok" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btCancel" componentID="BUT_MAILTRACKING_DEFAULTS_DAMAGEDETAILS_CANCEL">
								<common:message key="mailtracking.defaults.damagedetails.btn.cancel" />
							</ihtml:nbutton>
		  </div>
		  </div>
</div>
</ihtml:form>
</div>
 
			
		 
	</body>
</html:html>
