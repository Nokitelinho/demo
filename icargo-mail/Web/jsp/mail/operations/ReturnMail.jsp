<%--
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: ReturnMail.jsp
* Date				: 18-July-2006
* Author(s)			: A-1861
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>


<html:html>
<head> 
	
		<%@ include file="/jsp/includes/customcss.jsp" %>
	
	<title><common:message bundle="returnMailResources" key="mailtracking.defaults.returnmail.lbl.title" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/ReturnMail_Script.jsp" />
</head>

<body id="bodyStyle">
<div class="iCargoPopUpContent" >
<ihtml:form action="/mailtracking.defaults.returnmail.screenload.do" styleClass="ic-main-form" >
<business:sessionBean id="damageCodes"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.returnmail"
		  method="get"
		  attribute="oneTimeDamageCodes" />

<business:sessionBean id="damagedMailbagVOs"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.returnmail"
		  method="get"
		  attribute="damagedMailbagVOs" />

<business:sessionBean id="postalAdministrationVOs"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.returnmail"
		  method="get"
		  attribute="postalAdministrationVOs" />

<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="selectedContainers" />
<ihtml:hidden property="successFlag" />
<ihtml:hidden property="flagSBReturn" />
<ihtml:hidden property="paBuiltFlag" />
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="currentDialogId" />
<div class="ic-content-main">

		  <div class="ic-main-container">
		   <div class="ic-row">
			 <span class="ic-page-title ic-display-none">  <common:message key="mailtracking.defaults.damagedMailbag.lbl.heading" />
					</span>
		 </div>
		  <div class ="ic-row">
		  <div class="ic-button-container">
		  <a id="addLink" name="formlink" class="iCargoLink" href="#" value="add"><common:message key="mailtracking.defaults.returnmail.lbl.addlink" /></a> |
				<a id="deleteLink" name="formlink" class="iCargoLink" href="#" value="delete"><common:message key="mailtracking.defaults.returnmail.lbl.deletelink" /></a>
		  </div>
		  </div>
		  <div class ="ic-row">
		  <div class="tableContainer"  id="div1"  style="height:139px;">
		  <table  class="fixed-header-table" id="tab1">
		  	<thead>
				<tr>
				  <td class="iCargoTableHeaderLabel" width="4%"> 
					  <input type="checkbox" name="returnCheckAll" value="checkbox">
					</td>
				  <td width="47%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returnmail.lbl.damagecode" /></td>
				  <td  width="50%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.returnmail.lbl.remarks" /></td>
				</tr>
			</thead>
					<tbody>

				<logic:present name="damagedMailbagVOs">
					<bean:define id="mailbagVOs" name="damagedMailbagVOs" toScope="page"/>
					<logic:iterate id="mailbagvo" name="mailbagVOs" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO">
						

						<logic:present name="mailbagvo" property="operationFlag">
							<bean:define id="operFlag" name="mailbagvo" property="operationFlag" toScope="page"/>

							<logic:notEqual name="mailbagvo" property="operationFlag" value="D">

								<tr >
								  <td class="iCargoTableDataTd"><div ><input type="checkbox" name="returnSubCheck" value="<%= rowid.toString() %>"></div></td>
								  <td class="iCargoTableDataTd"><center>
									<logic:present name="mailbagvo" property="damageCode">
										<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGECODE" tabindex="1" value="<%= mailbagvo.getDamageCode() %>">
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
										<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGECODE" tabindex="1" value="">
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
									</center>
								  </td>
								  <td class="iCargoTableDataTd">
									<logic:present name="mailbagvo" property="remarks">
										<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGEREMARKS" maxlength="70" tabindex="1" value="<%= mailbagvo.getRemarks() %>" />


									</logic:present>
									<logic:notPresent name="mailbagvo" property="remarks">
									<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGEREMARKS" maxlength="70" tabindex="1" value="" />


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

							<tr >
							  <td class="iCargoTableDataTd"><div align="center"><input type="checkbox" name="returnSubCheck" value="<%= rowid.toString() %>"></div></td>
							  <td class="iCargoTableDataTd">
								<logic:present name="mailbagvo" property="damageCode">
									<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGECODE" tabindex="1" value="<%= mailbagvo.getDamageCode() %>">
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
									<ihtml:select property="damageCode" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGECODE" tabindex="1" value="">
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
							  <td class="iCargoTableDataTd">
								<logic:present name="mailbagvo" property="remarks">
									<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGEREMARKS" maxlength="70" tabindex="1" value="<%= mailbagvo.getRemarks() %>" style="width:410px"/>
								</logic:present>
								<logic:notPresent name="mailbagvo" property="remarks">
									<ihtml:text property="damageRemarks" componentID="TXT_MAILTRACKING_DEFAULTS_RETURNMAIL_DAMAGEREMARKS" maxlength="70" tabindex="1" value="" style="width:410px"/>
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
		  <div class="ic-row">
		  <div class="ic-input  ic-split-25">
		  </div>
		  <div class="ic-input marginT20 ic-split-5">
		  <ihtml:checkbox property="returnMail" />
		  </div>
		   <div class="ic-input marginT20 ic-split-25">
		 <common:message key="mailtracking.defaults.returnmail.lbl.return" />
		  </div>
		   
		   <div class="ic-input  ic-split-40">
		<label>   
		<common:message key="mailtracking.defaults.returnmail.lbl.pa" />
		</label>
		  
		   	<ihtml:select property="postalAdmin" componentID="COMBO_MAILTRACKING_DEFAULTS_RETURNMAIL_PA" tabindex="1">
              <logic:present name="postalAdministrationVOs">
				<bean:define id="postalAdministrationVOs" name="postalAdministrationVOs" type="java.util.Collection" toScope="page"/>

					<logic:iterate id="postalAdminVO" name="postalAdministrationVOs">
						<bean:define id="value" name="postalAdminVO" property="paCode"/>
						<bean:define id="desc" name="postalAdminVO" property="paName"/>

						<html:option value="<%= value.toString() %>"><%= desc %></html:option>

					</logic:iterate>
				</logic:present>
            </ihtml:select>
		  </div>
		
			
		  </div>
		  
		  </div>
		  <div class="ic-foot-container">
		  <div class="ic-button-container">
		   <ihtml:nbutton property="btOk" tabindex="1" componentID="BUT_MAILTRACKING_DEFAULTS_RETURNMAIL_OK">
		<common:message key="mailtracking.defaults.returnmail.btn.ok" />
	  </ihtml:nbutton>
	  <ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_RETURNMAIL_CANCEL">

		<common:message key="mailtracking.defaults.returnmail.btn.cancel" />
	  </ihtml:nbutton>
		  </div>
		  </div>
</div>
</ihtml:form>
</div>
 
	</body>
</html:html>