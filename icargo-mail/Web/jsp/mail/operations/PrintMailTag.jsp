<%--****************************************************
* Project	 		: iCargo
* Module Code & Name		: Mail Tracking
* File Name			: MailTag.jsp
* Date				: 22-OCT-2007
* Author(s)			: A-1876
 ***************************************************--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm"%>



		
	
<html:html>

<head>


	<title><common:message bundle="printMailTagResources" key="mailtracking.defaults.mailtag.lbl.title" /></title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/PrintMailTag_Script.jsp" />
</head>

<body style="width:80%" class="ic-center">
		

<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<div id="pageDiv" class="iCargoContent"  style="height:100%;overflow:auto;"  >



<bean:define id="form"
	name="PrintMailTagForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm"
    toScope="page"
    scope="request"/>

<business:sessionBean id="oneTimeCat"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.printmailtag"
		  method="get"
		  attribute="oneTimeCat" />

<business:sessionBean id="oneTimeHNI"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.printmailtag"
		  method="get"
		  attribute="oneTimeHNI" />

<business:sessionBean id="oneTimeRI"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.printmailtag"
		  method="get"
		  attribute="oneTimeRI" />

<business:sessionBean id="mailbagVOs"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.printmailtag"
		  method="get"
		  attribute="mailbagVOs" />
<ihtml:form action="/mailtracking.defaults.printmailtag.screenload.do" >
	<ihtml:hidden name="PrintMailTagForm" property="flag" />
	<ihtml:hidden name="PrintMailTagForm" property="selectedMailBagId" />
	<ihtml:hidden name="PrintMailTagForm" property="validPrintRequest" />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
		<common:message key="mailtracking.defaults.mailtag.lbl.pagetitle" /></td>
		</span>
		<div class="ic-main-container">
		<div class="ic-section  ic-pad-2">
		<div class="ic-row">
			<div class="ic-button-container ic-pad-5">
				<a href="#" id="addLink" value="add" name="addLink" class="iCargoLink">Add</a>
				|
				<a href="#" id="deleteLink" value="delete" name="deleteLink" class="iCargoLink">Delete</a>
			</div>
		</div>
		<div class="ic-row">
			<div class="tableContainer ic-center ic-pad-5" id="div2"  style="height:710px; ">
	      <table class="fixed-header-table" id="mailTableBody">
	      <thead>
		<tr >
		  <td class="iCargoTableHeaderLabel" width="3%"><div align="center"><input type="checkbox" name="masterMail" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);"/></div></td>
		  <td class="iCargoTableHeaderLabel"  width="20%" ><common:message key="mailtracking.defaults.mailtag.lbl.mailbagid" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="10%" ><common:message key="mailtracking.defaults.mailtag.lbl.ooe" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="10%"><common:message key="mailtracking.defaults.mailtag.lbl.doe" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="10%"><common:message key="mailtracking.defaults.mailtag.lbl.cat" /></td>
		  <td class="iCargoTableHeaderLabel"  width="10%"><common:message key="mailtracking.defaults.mailtag.lbl.sc" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="6%"><common:message key="mailtracking.defaults.mailtag.lbl.yr" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="6%"><common:message key="mailtracking.defaults.mailtag.lbl.dsn" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="6%"><common:message key="mailtracking.defaults.mailtag.lbl.rsn" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  <td class="iCargoTableHeaderLabel"  width="6%"><common:message key="mailtracking.defaults.mailtag.lbl.hni" /></td>
		  <td class="iCargoTableHeaderLabel" width="6%"><common:message key="mailtracking.defaults.mailtag.lbl.ri" /></td>
		  <td class="iCargoTableHeaderLabel"  width="7%"><common:message key="mailtracking.defaults.mailtag.lbl.weight" /><span class="iCargoMandatoryFieldIcon">*</span></td>
		  </tr>
	      </thead>
              <tbody id="mailTableBody">


              <logic:present name="mailbagVOs">

	        <logic:iterate id="mailbagVO" name="mailbagVOs" indexId="index">


		 <logic:present name="mailbagVO" property="operationalFlag">
			<bean:define id="operationFlag" name="mailbagVO" property="operationalFlag" toScope="request" />
			<ihtml:hidden property="opFlag" value="<%=((String)operationFlag)%>" />
		 </logic:present>
		 <logic:notPresent name="mailbagVO" property="operationalFlag">
			<ihtml:hidden property="opFlag" value="N" />
		 </logic:notPresent>


        	<tr >
	                <td ><div ><input type="checkbox" name="selectMail" value="<%=index%>"></div></td>
				<td>

					<logic:present name="mailbagVO" property="mailbagId">
					   <bean:define id="mailbagId" name="mailbagVO" property="mailbagId" toScope="page"/>
					   <ihtml:text property="mailbagId" maxlength="29" style="width:210px" componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_MAILBAGID" value="<%=(String)mailbagId%>" indexId="index" />		</logic:present> <!--modified. A-8164 for ICRD 257609-->
			     </td>
				<td>

					<logic:present name="mailbagVO" property="ooe">
					   <bean:define id="ooe" name="mailbagVO" property="ooe" toScope="page"/>
					   <ihtml:text property="originOE" maxlength="6" componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_OOE" value="<%=(String)ooe%>" indexId="index"/>
		    			</logic:present>
					<div class="lovImgTbl valignT"><img  id="originOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="org"/></div>
			        </td>
				<td>
					<logic:present name="mailbagVO" property="doe">
					   <bean:define id="doe" name="mailbagVO" property="doe" toScope="page"/>
					   <ihtml:text property="destnOE" maxlength="6"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_DOE" value="<%=(String)doe%>" indexId="index" />
					</logic:present>
					<div class="lovImgTbl valignT"><img  id="destnOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="dstn"/></div>
				</td>
				<td>

					<% String catValue = ""; %>
					<logic:present name="mailbagVO" property="mailCategoryCode">
					<bean:define id="mailCtgyCode" name="mailbagVO" property="mailCategoryCode" toScope="page"/>
						<% catValue = (String) mailCtgyCode; %>
					</logic:present>

					<ihtml:select property="category"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_CAT" value="<%=(String)catValue%>" style="width:35px" indexId="index">
					<logic:present name="oneTimeCat">
						<bean:define id="oneTime" name="oneTimeCat" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>
				<td>
					<logic:present name="mailbagVO" property="mailSubclass">
					   <bean:define id="mailSubclass" name="mailbagVO" property="mailSubclass" toScope="page"/>
					<ihtml:text property="subClass" maxlength="2"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_SC" value="<%=(String)mailSubclass%>" style="width:35px" indexId="index"/>
					</logic:present>
					<div class="lovImgTbl valignT"><img  id="subClassLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="subClas"/></div>
				</td>
				<td>
					<logic:present name="mailbagVO" property="year">
					   <bean:define id="year" name="mailbagVO" property="year" toScope="page"/>
					   <ihtml:text property="year" maxlength="1"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_YR" value="<%=year.toString()%>" style="width:15px" indexId="index"/>
					</logic:present>
				</td>
				<td>
					<logic:present name="mailbagVO" property="despatchSerialNumber">
					   <bean:define id="despatchSerialNumber" name="mailbagVO" property="despatchSerialNumber" toScope="page"/>
					   <ihtml:text property="dsn" maxlength="4"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_DSN" value="<%=(String)despatchSerialNumber%>" style="width:35px" indexId="index"/>
					</logic:present>
				</td>
				<td>
					<logic:present name="mailbagVO" property="receptacleSerialNumber">
					   <bean:define id="receptacleSerialNumber" name="mailbagVO" property="receptacleSerialNumber" toScope="page"/>
					   <ihtml:text property="rsn" maxlength="3"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_RSN" value="<%=(String)receptacleSerialNumber%>" style="width:30px" indexId="index"/>
					</logic:present>
				</td>

				<td>
				        <% String hniValue = ""; %>
					<logic:present name="mailbagVO" property="highestNumberedReceptacle">
					<bean:define id="hni" name="mailbagVO" property="highestNumberedReceptacle" toScope="page"/>
					<% hniValue = (String) hni;%>
					</logic:present>
					<ihtml:select property="hni"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_HNI" value="<%=(String)hniValue%>" style="width:35px" indexId="index">
					<logic:present name="oneTimeHNI">
						<bean:define id="oneTime" name="oneTimeHNI" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>
				<td>
					<% String riValue = ""; %>
					<logic:present name="mailbagVO" property="registeredOrInsuredIndicator">
					<bean:define id="ri" name="mailbagVO" property="registeredOrInsuredIndicator" toScope="page"/>
					<% riValue = (String) ri; %>
					</logic:present>
					<ihtml:select property="ri"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_RI" value="<%=(String)riValue%>" style="width:35px" indexId="index">
					<logic:present name="oneTimeRI">
						<bean:define id="oneTime" name="oneTimeRI" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>

				<td>
					<logic:present name="mailbagVO" property="strWeight">
				             <bean:define id="strWeight" name="mailbagVO" property="strWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- modified by A-7371-->
				  	     <ihtml:text property="weight" maxlength="4"  componentID="TXT_MAILTRACKING_DEFAULTS_MAILTAG_WEIGHT" value="<%=String.valueOf(strWeight.getDisplayValue())%>" style="width:35px" indexId="index"/>
				        </logic:present>
				</td>

			   </tr>

			    </logic:iterate>
			</logic:present>



		     <!-- templateRow -->
			<tr template="true" id="mailTemplateRow" style="display:none">

				<ihtml:hidden property="opFlag" value="NOOP" />
				<td  class="iCargoTableDataTd ic-center">

					<input type="checkbox" name="selectMail" >
				</td>
				<td>
					<ihtml:text property="mailbagId" maxlength="29"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_MAILBAGID" value="" style="width:210px"/> <!--modified. A-8164 for ICRD 257609-->	
				</td>
				<td>
					<ihtml:text property="originOE" maxlength="6"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_OOE" value=""/>
					<div class="lovImgTbl valignT"><img  id="originOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"/></div>
				</td>
				<td>
					<ihtml:text property="destnOE" maxlength="6"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_DOE" value=""/>
					<div class="lovImgTbl valignT"><img  id="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"/></div>
				</td>
				<td>
					<ihtml:select property="category"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_CAT" value="" style="width:50px">
					<logic:present name="oneTimeCat">
						<bean:define id="oneTime" name="oneTimeCat" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>
				<td>
					<ihtml:text property="subClass" maxlength="2"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_SC" value="" style="width:50px"/>
					<div class="lovImgTbl valignT"><img  id="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="subClas"/></div>
				</td>
				<td>
					<ihtml:text property="year" maxlength="1"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_YR" value="" style="width:50px"/>
				</td>
				<td>
					<ihtml:text property="dsn" maxlength="4"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_DSN" value="" style="width:50px"/>
				</td>
				<td>
					<ihtml:text property="rsn" maxlength="3"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_RSN" value="" style="width:40px"/>
				</td>

				<td>
					<ihtml:select property="hni"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_HNI" value="" style="width:50px">
					<logic:present name="oneTimeHNI">
						<bean:define id="oneTime" name="oneTimeHNI" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>
				<td>
					<ihtml:select property="ri"  componentID="CMB_MAILTRACKING_DEFAULTS_MAILTAG_RI" value="" style="width:50px">
					<logic:present name="oneTimeRI">
						<bean:define id="oneTime" name="oneTimeRI" toScope="page"/>
						<logic:iterate id="onetmvo" name="oneTime">
							<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
							<bean:define id="value" name="onetimevo" property="fieldValue"/>
							<html:option value="<%= value.toString() %>"><%= value.toString() %></html:option>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</td>

				<td>
					<ihtml:text property="weight" maxlength="4"  componentID="TXT_MAILTRACKING_DEFAULTS_MAILTAG_WEIGHT" value="" style="width:35px"/>
				</td>
			</tr>
		     <!--template row ends-->


			   </tbody>
			</table>
		</div>
	</div>
	</div>
  </div>
  <div class="ic-foot-container">
		<div class="ic-button-container ic-pad-5">
			  <ihtml:nbutton property="btnPrint"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_BTNPRINT" accesskey="P">
				<common:message key="mailtracking.defaults.mailtag.btn.print" />
			  </ihtml:nbutton>
			  <ihtml:nbutton property="btnClose"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILTAG_BTNCLOSE" accesskey="O">
				<common:message key="mailtracking.defaults.mailtag.btn.close" />
			  </ihtml:nbutton>
		</div>
	 </div>
</div>
</ihtml:form>

</div>


				
		
					
		
	</body>

</html:html>
