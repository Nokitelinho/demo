<%--
* Project	 		: iCargo
* Module Code & Name:
* File Name			:
* Date				: 26-Oct-2015
* Author(s)			: A-6767
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDNumberLovForm" %>
		
		<!DOCTYPE html>			
	
<html:html>
<head> 
	
<meta name="decorator" content="popup_panel" >
<common:include src="/js/uld/defaults/ULDNumberLov_Script.jsp" type="script"/>
</head>
<body >
	

<bean:define id="form"
			 name="uldNumberLovForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDNumberLovForm"
			 toScope="page" />

<logic:present name="uldNumberLovForm" property="uldnumLovPage">
		<bean:define id="uldnumLovPage"
		name="uldNumberLovForm"  property="uldnumLovPage"
		toScope="page" />
</logic:present>

<div class="iCargoPopUpContent" style="width:100%;height:100%;">
<ihtml:form action="/uld.defaults.transaction.finduldnumberlov.do" styleclass="ic-main-form">
<html:hidden name="uldNumberLovForm" property="selectedValues"  />
<html:hidden name="uldNumberLovForm" property="formCount" />
<html:hidden name="uldNumberLovForm" property="lovTxtFieldName" />
<html:hidden name="uldNumberLovForm" property="index" />
<ihtml:hidden property="lastPageNum"/>
<ihtml:hidden property="displayPage"/>
<bean:define id="strFormCount" name="uldNumberLovForm" property="formCount"  />
<bean:define id="strLovTxtFieldName"  name="uldNumberLovForm" property="lovTxtFieldName" />
<bean:define id="strSelectedValues" name="uldNumberLovForm" property="selectedValues" />
<bean:define id="arrayIndex" name="uldNumberLovForm" property="index"/>


<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message key="uld.defaults.uldnumberlov.heading" scope="request"/>
		</span>					
		<div class="ic-head-container">	
			<div class="ic-filter-panel">
				<div class="ic-input-container ">
					<div class="ic-row">
						<div class="ic-input ic-split-45 ic-right">
							<label>
								<common:message key="uld.defaults.uldnumberlov.lbl.uldnum" scope="request"/>
							</label>
								<ihtml:text property="code"	componentID="BTN_ULD_DEFAULTS_ULDNUMLOV_ULDNUM" />
						</div>	
						<div class="ic-input ic-split-55 ic-right">
							<div class="ic-button container">
								<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_ULDNUMLOV_LIST" >
									<common:message key="uld.defaults.uldnumberlov.btn.list" />
								</ihtml:nbutton>

								<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_ULDNUMLOV_CLEAR"  >
									<common:message key="uld.defaults.uldnumberlov.btn.clear" />
								</ihtml:nbutton>
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-col-50">
						
							<logic:present name="uldnumLovPage">
								<common:paginationTag
								pageURL="uld.defaults.transaction.finduldnumberlov.do"
								name="uldnumLovPage"
								display="label"
								linkStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNum() %>" />
								</logic:present>
						</div>
						<div class="ic-col-50 ic-right">
							<logic:present name="uldnumLovPage">
									<common:paginationTag
									pageURL="javascript:callFunction('lastPageNum','displayPage')"
									name="uldnumLovPage"
									display="pages"
									linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									lastPageNum="<%=form.getLastPageNum()%>" />
									</logic:present>							
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer"  id="div1" style="width:100%;height:336px"> 
							<table class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingCenter">
									<td width="7%" class="iCargoLabelleftAligned"></td>
									<td width="30%" class="iCargoLabelleftAligned"><common:message key="uld.defaults.uldnumberlov.lbl.uldnum" /></td>
									</tr>
								</thead>
							   <tbody>
									
								  <logic:present name="uldNumberLovForm" property="uldnumLovPage">
									<bean:define id="uldNums" name="uldNumberLovForm"  property="uldnumLovPage"  toScope="page"/>
									<logic:iterate id = "val" name="uldNums" indexId="nIndex">

										 <tr >
											<td width="3%" class="iCargoTableDataTd">
												<input type="checkbox" name="selectCheckBox" value="<%=val%>" onclick="singleSelect('<%=val%>')" />
												<logic:notEqual name="uldNumberLovForm" property="multiselect" value="Y">
													<a href="#" ondblclick="setValue(document.forms[0].selectedValues.value,'',document.forms[0].formCount.value,document.forms[0].lovTxtFieldName.value,'')" tabindex="-1"/>
												</logic:notEqual>
												
											</td>
											<td width="26%" align="left" class="iCargoTableDataTd">
											   <%=val%>
											</td>
									  </tr>
								
									</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>		
					</div>
			</div>
			<div class="ic-foot-container">
					<div class="ic-button-container">
					<input type="button" name="uldOK"  onclick="setValue('<%=strLovTxtFieldName%>',document.forms[0].lovTxtFieldName.value,'<%=arrayIndex%>')" value="OK" title="Ok" class="iCargoButtonSmall"/>

					<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_ULDNUMLOV_CLOSE">
						<common:message  key="uld.defaults.uldnumberlov.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>
</div>
</ihtml:form>
</div>
			
	</body>

</html:html>
