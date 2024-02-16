
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	

<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.dsnaudit" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.dsnaudit" method="get" attribute="oneTimeMailClass" />

<ihtml:form action="/mailtracking.defaults.dsnauditscreenload.do">

   <ihtml:hidden property="screenStatusFlag"/>
	
   <div id="_filter">
   		<div class="ic-section" style="height:80%;">
			<div class="ic-border" style="height:100%;">
				<div class="ic-col-15 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.ooe"/>
					</label>
					<ihtml:text property="ooe" componentID="TXT_MAILTRACKING_DEFAULTS_DSNAUDIT_OOE" maxlength="6"/>
					<div class="lovImg">
					<img name="ooeLov" id="ooeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.ooe.value,'OfficeOfExchange','1','ooe','',0)"></div>
					</div>
				<div class="ic-col-15 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.doe"/>
					</label>
					<ihtml:text property="doe" componentID="TXT_MAILTRACKING_DEFAULTS_DSNAUDIT_DOE" maxlength="6"/>
					<div class="lovImg">
					<img name="doeLov" id="doeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.doe.value,'OfficeOfExchange','1','doe','',0)"></div>
				</div>
				<div class="ic-col-15 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.cat"/>
					</label>
					<ihtml:select property="category" componentID="CMB_MAILTRACKING_DEFAULTS_DSNAUDIT_CAT" style="width:80px">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
					<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
					<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
						<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
						<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
					</logic:iterate>
					</ihtml:select>
				</div>
				<div class="ic-col-15 ic-input">
					<label>
						 <common:message key="mailtracking.defaults.dsnaudit.lbl.class"/>	
					</label>
					<ihtml:select property="mailClass" componentID="CMB_MAILTRACKING_DEFAULTS_DSNAUDIT_CLASS" style="width:80px">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
					<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
					<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
						<bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
						<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
					</logic:iterate>
					</ihtml:select>
				</div>
				<div class="ic-col-15 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.sc"/>
					</label>
					<ihtml:text property="subclass" componentID="TXT_MAILTRACKING_DEFAULTS_DSNAUDIT_SC" maxlength="2" />
					<div class="lovImg">
					<img name="scLov" id="scLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subclass.value,'OfficeOfExchange','1','subclass','',0)"></div>
				</div>
				<div class="ic-col-15 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.yr"/>
					</label>
					<ihtml:text property="year" componentID="TXT_MAILTRACKING_DEFAULTS_DSNAUDIT_YEAR" maxlength="1"/>
				</div>
				<div class="ic-col-10 ic-input">
					<label>
						<common:message key="mailtracking.defaults.dsnaudit.lbl.dsn"/>
					</label>
					<ihtml:text property="dsn" componentID="TXT_MAILTRACKING_DEFAULTS_DSNAUDIT_DSN" maxlength="4" />
				</div>
			</div>
		</div>
	   		
		<div class="ic-button-container paddR5">
			<ihtml:nbutton accesskey = "l" property="btList" componentID="BTN_MAILTRACKING_DEFAULTS_DSNAUDIT_LIST">
				<common:message key="mailtracking.defaults.dsnaudit.btn.list" />
			</ihtml:nbutton>

			<ihtml:nbutton accesskey = "c" property="btClear" componentID="BTN_MAILTRACKING_DEFAULTS_DSNAUDIT_CLEAR">
				<common:message key="mailtracking.defaults.dsnaudit.btn.clear" />
			</ihtml:nbutton>
		</div>
   </div>
   
</ihtml:form>
	
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
