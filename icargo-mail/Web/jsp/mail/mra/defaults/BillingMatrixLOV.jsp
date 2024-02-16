<%--************************************************************
* Project	 		: iCargo
* Module Code & Name            : mra-defaults
* File Name			: BillingMatrixLOV.jsp
* Date				: 06/03/2007
* Author(s)			: A-2408
 ****************************************************************--%>
 
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixLovForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO" %>
		
		<!DOCTYPE html>			
	
<html:html>
<head> 
		
	
	<title><common:message   bundle="billingmatrixlov" key="mailtracking.mra.defaults.billingmtxlovtitle" scope="request"/></title>
	<meta name="decorator" content="popuppanelrestyledui">
  	<common:include type="script" src="/js/mail/mra/defaults/BillingMatrixLOV_Script.jsp" />

</head>
<body>
	
   
	<div id="divmain"  class="iCargoPopUpContent">
		<ihtml:form action="/showBillingMatrixLOV.do" styleClass="ic-main-form">
		<bean:define id="BillingMatrixLovForm" name="BillingMatrixLovForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixLovForm" toScope="page" />
		<logic:present name="billingMatrixLovForm"  property="lovaction">
			<bean:define id="lovaction" name="billingMatrixLovForm" property="lovaction" toScope="page"/>
		</logic:present>
		
		<ihtml:hidden name="BillingMatrixLovForm" property="lovaction"  />
		<ihtml:hidden name="BillingMatrixLovForm" property="selectedValues"  />
		<ihtml:hidden name="BillingMatrixLovForm" property="lastPageNum" />
		<ihtml:hidden name="BillingMatrixLovForm" property="displayPage" />
		<ihtml:hidden name="BillingMatrixLovForm" property="multiselect" />
		<ihtml:hidden name="BillingMatrixLovForm" property="pagination" />
		<ihtml:hidden name="BillingMatrixLovForm" property="formCount" />
		<ihtml:hidden name="BillingMatrixLovForm" property="lovTxtFieldName" />
		<ihtml:hidden name="BillingMatrixLovForm" property="lovNameTxtFieldName" />
		<ihtml:hidden name="BillingMatrixLovForm" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="BillingMatrixLovForm" property="index" />

		

		<div class="ic-content-main">
			<div class="ic-head-container">		
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input">
								<label>
									<common:message   key="mailtracking.mra.defaults.billingmtxid" scope="request"/>
								</label>
								<ihtml:text property="code" name="BillingMatrixLovForm" componentID="MRA_DEFAULTS_BILLINGMTXID"  maxlength="20"/>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_BILLINGMTX_LIST" >
									<common:message   key="mailtracking.mra.defaults.billingmtxlov.btn.btlist" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_BILLINGMTX_CLEAR">
									<common:message   key="mailtracking.mra.defaults.billingmtxlov.btn.btclear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<logic:present name="BillingMatrixLovForm" property="billingMatrixLovPage" >
					<bean:define id="billingMatrixLovPage"  name="BillingMatrixLovForm" property="billingMatrixLovPage" toScope="page"/>
					<logic:present name="billingMatrixLovPage">
						<logic:present name="BillingMatrixLovForm" property="multiselect">
							<bean:define id="multiselect" name="BillingMatrixLovForm" property="multiselect" />
						</logic:present>
						<logic:equal name="BillingMatrixLovForm" property="pagination" value="Y">
						<!-- -PAGINATION TAGS -->
						<bean:define id="lastPageNum" name="BillingMatrixLovForm" property="lastPageNum" toScope="page"/>
						<div class="ic-row">
							<div class="ic-col-50">
								<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
								name="billingMatrixLovPage" display="label" labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=(String)lastPageNum %>" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="ic-col-50 ic-right">
								<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
						name="billingMatrixLovPage" display="pages" linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
						lastPageNum="<%=(String)lastPageNum%>"/>
							</div>
						</div>
						</logic:equal>
						</logic:present>
					</logic:present>
				</div>
				<div class="ic-row">
					<bean:define id="strFormCount" name="BillingMatrixLovForm" property="formCount"  />
					<bean:define id="strMultiselect" name="BillingMatrixLovForm" property="multiselect" />
					<%String strLovTxtField = "";%>
					<logic:present name="BillingMatrixLovForm" property="lovTxtFieldName">
						<bean:define id="strLovTxtFieldName" name="BillingMatrixLovForm" property="lovTxtFieldName"  />
						<%strLovTxtField=strLovTxtFieldName.toString();%>
					</logic:present>
					<%String strTxtFieldName = "";%>
					<logic:present name="BillingMatrixLovForm" property="lovDescriptionTxtFieldName">
						<bean:define id="strLovDescriptionTxtFieldName" name="BillingMatrixLovForm" property="lovDescriptionTxtFieldName" />
						<%strTxtFieldName=strLovDescriptionTxtFieldName.toString();%>
					</logic:present>
					<bean:define id="strSelectedValues" name="BillingMatrixLovForm" property="selectedValues" />
					<bean:define id="arrayIndex" name="BillingMatrixLovForm" property="index"/>
				
					<div class="tableContainer" id="div1" style="height:250px;">
						<table class="fixed-header-table" id="lovListTable">
							<thead>
								<tr>
									<td width="10%"> &nbsp;</td>
									<td width="45%">
										<common:message   key="mailtracking.mra.defaults.billingmtxid" scope="request"/>
									</td>
									<td  width="45%">
										<common:message   key="mailtracking.mra.defaults.billingmtxdesp" scope="request"/>
									</td>
								</tr>
							</thead>
							<logic:present name="BillingMatrixLovForm" property="billingMatrixLovPage">
								<bean:define id="lovList" name="BillingMatrixLovForm" property="billingMatrixLovPage" toScope="page"/>
							<logic:present name="lovList">
							<tbody>
								<% int i=0;%>
								<logic:iterate id = "val" name="lovList" indexId="indexId">
								
							
									<logic:notEqual name="BillingMatrixLovForm" property="multiselect" value="Y">
										<tr ondblclick="setValueOnDoubleClick('<%=((BillingMatrixLovVO)val).getBillingMatrixCode()%>','<%=((BillingMatrixLovVO)val).getBillingMatrixDescription()%>',
														'<%= strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)">
									</logic:notEqual>
									<logic:equal name="BillingMatrixLovForm" property="multiselect" value="Y">
									<tr>
										<td>
											<%if(((String)strSelectedValues).contains(((BillingMatrixLovVO)val).getBillingMatrixCode())){ %>
											<input type="checkbox" name="selectCheckBox" value="<%=((BillingMatrixLovVO)val).getBillingMatrixCode()%>"  checked="checked"/>
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=((BillingMatrixLovVO)val).getBillingMatrixCode()%>"  />
											<% } %>
										</td>
									</logic:equal>
									<%String checkVal = "";%>

									<logic:notEqual name="BillingMatrixLovForm" property="multiselect" value="Y">
										<td>
											<%checkVal = ((BillingMatrixLovVO)val).getBillingMatrixCode()+"&"+((BillingMatrixLovVO)val).getBillingMatrixDescription();

											%>

											<% System.out.println("========------"+checkVal);%>

											<%

											if(   ((String)strSelectedValues).equals(((BillingMatrixLovVO)val).getBillingMatrixCode()  )){ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
											<% } %>
										</td>
									</logic:notEqual>
									<!-- table -->

									<td>
										<bean:write name="val" property="billingMatrixCode"/>
									</td>
									<td>
										<bean:write name="val" property="billingMatrixDescription"/>
									</td>

									

								</tr>
								
								</logic:iterate>
								</tbody>
							</logic:present>
					</logic:present>
						</table>
						
					</div>
					<!-- Added by A-5497 regarding ICRD-40566 -->
				<logic:equal name="BillingMatrixLovForm" property="multiselect" value="Y">
				<tr>
				<td>
				<div id="selectiondiv" class="multiselectPanelStyle" name="BillingMatrix">
				</div>
				</td>
				</tr>
				</logic:equal>
			</div>	
			</div>
			
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<input type="button" name="okButton" value="OK" class="iCargoButtonSmall"
						onclick="setValueOnOk('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)"  />
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_BILLINGMTX_CLOSE">
						<common:message   key="mailtracking.mra.defaults.billingmtxlov.btn.btclose" />
					</ihtml:nbutton>
				</div>
			</div>	
		</div>	
		</ihtml:form>
	</div>


			
		   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>
		   <logic:present name="icargo.uilayout">
		       <logic:equal name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
		       </logic:equal>

		       <logic:notEqual name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new.jsp" />
		       </logic:notEqual>
		   </logic:present>
		   <common:registerCharts/>
		   <common:registerEvent />
	</body>
</html:html>


