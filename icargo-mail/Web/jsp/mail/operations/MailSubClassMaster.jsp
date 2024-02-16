<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MailSubClassMaster.jsp
* Date					:  06-June-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO"%>

			


		
	 <html:html>

 <head>
		
	
 	<title><common:message bundle="mailSubClassResources" key="mailtracking.defaults.mailsubclassmaster.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/MailSubClassMaster_Script.jsp"/>
 </head>

 <body style="width:65%" class="ic-center">
	
	

	<bean:define id="form"
		 name="MailSubClassForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm"
		 toScope="page" />

	<business:sessionBean id="mailSubClassVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.subclass"
		 method="get"
		 attribute="mailSubClassVOs"/>

	<business:sessionBean id="OneTimeValues"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.subclass"
		 method="get"
		 attribute="subClassGroups"/>

	<div  class="iCargoContent" style="width:100%;height:100%;overflow:auto;">
	<ihtml:form action="/mailtracking.defaults.subclassmaster.screenload.do" >

 	<ihtml:hidden property="screenStatusFlag"/>
		<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message key="mailtracking.defaults.mailsubclassmaster.lbl.heading" /></span>   
		
		<div class="ic-head-container">
			<div class="ic-row">
				<div class="ic-input-container"><h4>
				<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.search" /></h4>
				</div>
			</div>
		<!--DWLayoutTable-->
			<div class="ic-filter-panel">
				<div class ="ic-row">
				<div class=" ic-col-50">
				<div class="ic-input-container">
				<div class="ic-input ic-split-100" >
				<label>
			   <common:message key="mailtracking.defaults.mailsubclassmaster.lbl.subclass" />
				</label>
		 	   <ihtml:text property="subClassFilter" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASS" maxlength="2"/>
			   <div class="lovImg"><img id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>
			   </div>
			   </div>
			   </div>
			   <div class=" ic-col-50">
					<div class="ic-button-container">
					<ihtml:nbutton property="btList" componentID="BUT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_LIST" accesskey="L">
					<common:message key="mailtracking.defaults.mailsubclassmaster.btn.list" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btClear" componentID="BUT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_CLEAR" accesskey="C">
					<common:message key="mailtracking.defaults.mailsubclassmaster.btn.clear" />
					</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
		</div>
		<div class="ic-main-container">
		<div class="ic-section  ic-pad-2">
			<div class="ic-row"><h4>
			<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.details" /></h4>
				<div class="ic-button-container ic-pad-5">
				<a id="addLink" name="addLink" class="iCargoLink" href="#" value="add">
				<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.add" />
				</a>
				|
				<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete">
				<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.delete"/></a>
				</div>
			</div>
			<div class="ic-row">
			<div class="tableContainer" id="div1"  style="height:650px;">
			<table   class="fixed-header-table" id="subclassTableBody">
			<!--DWLayoutTable-->
			<thead>
				<tr >
					<td class="iCargoTableHeader" width="10%">
						<input type="checkbox" name="checkAll" value="checkbox">
					</td>
					<td class="iCargoTableHeader" width="30%">
						<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.subclass"/>
					
					</td>
					<td class="iCargoTableHeader" width="30%">
						<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.description"/>
					</td>
					<td class="iCargoTableHeader" width="30%">
						<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.subclassgp"/>
					</td>
				</tr>
			 </thead>
			 <tbody id="subclassTableBody">
				<logic:present name="mailSubClassVOs">
				<logic:iterate id ="mailSubClassVO" name="mailSubClassVOs" type="MailSubClassVO" indexId="rowCount">
				
				    <tr >
					<logic:present name="mailSubClassVO" property="operationFlag">
					<bean:define id="operationFlag" name="mailSubClassVO" property="operationFlag" toScope="request" />
					<logic:notEqual name="mailSubClassVO" property="operationFlag" value="D">

				 	<td class="iCargoTableDataTd ic-center" >
					    <ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>
					</td>
					<td class="iCargoTableDataTd ic-center">
						<logic:equal name="mailSubClassVO" property="operationFlag" value="I">
							<ihtml:text property="code" name="mailSubClassVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSCODE" maxlength="2" style="width:200px" />
						</logic:equal>
						<logic:notEqual name="mailSubClassVO" property="operationFlag" value="I">
							<ihtml:text property="code" name="mailSubClassVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSCODE" style="width:200px"/>
						</logic:notEqual>
					</td>
					<td class="iCargoTableDataTd ic-center"> 
						<ihtml:text property="description" name="mailSubClassVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSDESC" style="width:100px"/>
					</td>
					<td class="iCargoTableDataTd ic-center">
						<ihtml:select componentID="CMP_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SBGRP" name="mailSubClassVO" property="subClassGroup" >
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="OneTimeValues">
							<logic:iterate id="oneTimeValue" name="OneTimeValues">
							<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
							</logic:present>
							</logic:iterate>
							</logic:iterate>
						</logic:present>
						</ihtml:select>
					</td>
				        </logic:notEqual>
					<logic:equal name="mailSubClassVO" property="operationFlag" value="D">
						<ihtml:hidden property="code" name="mailSubClassVO"/>
						<ihtml:hidden property="description" name="mailSubClassVO"/>
						<ihtml:hidden property="subClassGroup" name="mailSubClassVO"/>
					</logic:equal>

					<ihtml:hidden property="operationFlag" value="<%=((String)operationFlag)%>" />
					</logic:present>
					<logic:notPresent name="mailSubClassVO" property="operationFlag">
					<td class="iCargoTableDataTd ic-center" >
						<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>
					</td>

					<td class="iCargoTableDataTd ic-center">
						<ihtml:text property="code" name="mailSubClassVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSCODE" style="width:200px"/>
					</td>

					<td class="iCargoTableDataTd ic-center">
						<ihtml:text property="description" indexId="rowCount" name="mailSubClassVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSDESC" style="width:200px"/>
					</td>

					<td class="iCargoTableDataTd ic-center">
						<ihtml:select indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SBGRP" name="mailSubClassVO" property="subClassGroup" style="width:100px">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="OneTimeValues">
							<logic:iterate id="oneTimeValue" name="OneTimeValues">
							<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
							<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<logic:present name="parameterValue" property="fieldValue">
								<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
							</logic:present>
							</logic:iterate>
							</logic:iterate>
						</logic:present>
						</ihtml:select>
					</td>
					<ihtml:hidden property="operationFlag" value="N" />
					</logic:notPresent>
				</tr>
		
			</logic:iterate>
			</logic:present>

			<!-- templateRow -->
			<tr template="true" id="subclassTemplateRow" style="display:none">
				<ihtml:hidden property="operationFlag" value="NOOP" />
				<td  class="iCargoTableDataTd ic-center">

					<input type="checkbox" name="rowId"></td>

				<td class="iCargoTableDataTd ic-center"><ihtml:text property="code" maxlength ="2" value=""  componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSCODE" style="width:100px" /></td>

				<td class="iCargoTableDataTd ic-center"><ihtml:text property="description" value="" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SUBCLASSDESC" style="width:100px" /></td>

				<td class="iCargoTableDataTd ic-center">
				   <ihtml:select componentID="CMP_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SBGRP" name="mailSubClassVO" value="" property="subClassGroup" style="width:100px">
				   <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
				   <logic:present name="OneTimeValues">
					<logic:iterate id="oneTimeValue" name="OneTimeValues">
					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="parameterValue" property="fieldValue">
						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
							<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
					</logic:present>
					</logic:iterate>
					</logic:iterate>
				   </logic:present>
				   </ihtml:select>
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
	   
		<!--DWLayoutTable-->
		<div class="ic-button-container ic-pad-5">
		    
			<ihtml:nbutton property="btSave" componentID="BUT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_SAVE" accesskey="S">
				<common:message key="mailtracking.defaults.mailsubclassmaster.btn.save" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_CLOSE" accesskey="O">
				<common:message key="mailtracking.defaults.mailsubclassmaster.btn.close" />
			</ihtml:nbutton>
		 </div>
</div>
</div>		 

		  
   

 </ihtml:form>
 </div>

 
				
		
	</body>
 </html:html>

