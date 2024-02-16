<%--************************************************************
* Project	 					: iCargo
* Module Code & Name            : mra-defaults
* File Name						: BillingSiteLOV.jsp
* Date							: 17/12/2013
* Author(s)						: A-5219
 ****************************************************************--%>

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO" %>
<%@ page import="java.util.Collection" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteLOVForm"%>

<bean:define id="billingSiteLOVForm" name="BillingSiteLOV" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteLOVForm"/>
		
		<!DOCTYPE html>			
	
<html>

<head> 
	
	
		
			
	
		
		
			
	

<title><common:message  key="mra.defaults.billingsitelov.title.billingSitelov" bundle="billingsitelovresource" scope="request"/></title>

<meta name="decorator" content="popuppanelrestyledui">


<common:include type="script" src="/js/mail/mra/defaults/BillingSiteLOV_Script.jsp"/>

</head>

<body id="bodyStyle">
	
	
	
	

<bean:define id="strAction" name="billingSiteLOVForm"  property="lovaction" scope="page" toScope="page"/>
<div class="iCargoPopUpContent" style="overflow:auto;">
<ihtml:form action="/mailtracking.mra.defaults.billingsitemaster.showBillingSiteLOVDetails.do" styleClass="ic-main-form">
<!--  Hidden to store the selections in case of pagination-->

<ihtml:hidden name="billingSiteLOVForm" property="lovaction"  />
<ihtml:hidden name="billingSiteLOVForm" property="selectedValues"  />
<ihtml:hidden name="billingSiteLOVForm" property="lastPageNum" />
<ihtml:hidden name="billingSiteLOVForm" property="displayPage" />
<ihtml:hidden name="billingSiteLOVForm" property="multiselect" />
<ihtml:hidden name="billingSiteLOVForm" property="pagination" />
<ihtml:hidden name="billingSiteLOVForm" property="formCount" />
<ihtml:hidden name="billingSiteLOVForm" property="lovTxtFieldName" />
<ihtml:hidden name="billingSiteLOVForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="billingSiteLOVForm" property="index" />
<ihtml:hidden name="billingSiteLOVForm" property="fromScreen" />



<!--  END -->

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mra.defaults.billingsitelov.pagetitle.billingSitelov"/> 
		</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
                    <div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ">
			                    <label><common:message  key="mra.defaults.billingsitelov.code" scope="request"/></b></label>
								<ihtml:text property="code" name="billingSiteLOVForm"  componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CODE" maxlength="6"/>
							</div>
							<div class="ic-input">
			                    <label><common:message  key="mra.defaults.billingsitelov.description"  /></b></label>
								 <ihtml:text property="description" name="billingSiteLOVForm" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_NAME"  maxlength="16"/>
							</div>
							<div class="ic-button-container">
								<div class="ic-row">
									<ihtml:nbutton property="listButton" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_LIST" accesskey="L">
										<common:message  key="mra.defaults.billingsitelov.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="clearButton" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CLEAR" accesskey="C" styleClass="btn-inline btn-secondary">
										<common:message  key="mra.defaults.billingsitelov.clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>				
			<div class="ic-main-container">
				<div class="ic-row">
					<logic:present name="billingSiteLOVForm" property="billingSiteLov" >
					  <bean:define id="lovList" name="billingSiteLOVForm" property="billingSiteLov" toScope="page"/>
					</logic:present>
					<logic:notPresent name="billingSiteLOVForm" property="billingSiteLov">

					</logic:notPresent>
					<logic:present name="billingSiteLOVForm" property="billingSiteLov" >
					<bean:define id="strMultiselect" name="billingSiteLOVForm" property="multiselect" />

					<logic:equal name="billingSiteLOVForm" property="pagination" value="Y">
					<!-- -PAGINATION TAGS -->
					<bean:define id="lastPageNum" name="billingSiteLOVForm" property="lastPageNum" />
						<div class="ic-row">
								<logic:present name="billingSiteLOVForm" property="billingSiteLov" >
								<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
										display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
								</logic:present>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<div class="ic-button-container">
								<logic:present name="billingSiteLOVForm" property="billingSiteLov" >
								<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages"
								 linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink" lastPageNum="<%=(String)lastPageNum%>"/>
								</logic:present>
							</div>
						</div>
					</logic:equal>
					</logic:present>
					
				</div>
				<div class="ic-row">
					<bean:define id="strFormCount" name="billingSiteLOVForm" property="formCount"/>
					<bean:define id="strLovTxtFieldName" name="billingSiteLOVForm" property="lovTxtFieldName"  />
					<bean:define id="strLovDescriptionTxtFieldName" name="billingSiteLOVForm" property="lovDescriptionTxtFieldName" />
					<bean:define id="strSelectedValues" name="billingSiteLOVForm" property="selectedValues" />
					<bean:define id="arrayIndex" name="billingSiteLOVForm" property="index" />
					<bean:define id="strMultiselect" name="billingSiteLOVForm" property="multiselect" />
				
					<div class="tableContainer"  id="div1" style="height:310px; width:100%;">
						<table  class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingCenter">
									<td width="6%"> &nbsp;</td>
									<td width="44%" class="iCargoLabelleftAligned"><common:message  key="mra.defaults.billingsitelov.billingsitecode" /></td>
									<td width="50%" class="iCargoLabelLeftAligned"><common:message  key="mra.defaults.billingsitelov.description"  /></td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="billingSiteLOVForm" property="billingSiteLov">
									<bean:define id="lovList" name="billingSiteLOVForm" property="billingSiteLov" toScope="page"/>


									<% int i=0;%>
										<logic:iterate id = "val" name="lovList" indexId="indexId">
								
											<tr>

										<logic:notEqual name="billingSiteLOVForm" property="multiselect" value="Y">

										<!-- <a href="#" ondblclick="setValueOnDoubleClick('<%=((BillingSiteLOVVO)val).getBillingSiteCode()%>',<%=arrayIndex%>)"/> -->
										<a href="#" ondblclick="setValueOnDoubleClick('<%=((BillingSiteLOVVO)val).getBillingSiteCode()%>','<%=((BillingSiteLOVVO)val).getBillingSiteDescription()%>',
															'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)"/>
										</logic:notEqual>
											<logic:equal name="billingSiteLOVForm" property="multiselect" value="Y">
												<td width="3%">
													<%--<input type="checkbox" name="selectCheckBox" value="<%=((BillingSiteLOVVO)val).getBillingSiteCode()%>" onclick="updateSelectedValues(this.form)"> --%>
													
													<%

													if(((String)strSelectedValues).contains(((BillingSiteLOVVO)val).getBillingSiteCode())){%>
														<ihtml:hidden name="billingSiteLOVForm" property="billingSiteCode" value="<%=((BillingSiteLOVVO)val).getBillingSiteCode()%>"/>
														<input type="checkbox" name="selectCheckBox" value="<%=((BillingSiteLOVVO)val).getBillingSiteCode()%>" checked="checked"/>
													<%}else{ %>
														<ihtml:hidden name="billingSiteLOVForm" property="description" value="<%=((BillingSiteLOVVO)val).getBillingSiteDescription()%>"/>
														<input type="checkbox" name="selectCheckBox" value="<%=((BillingSiteLOVVO)val).getBillingSiteDescription()%>" />
													<% } %>
												</td>
											</logic:equal>

											<logic:notEqual name="billingSiteLOVForm" property="multiselect" value="Y">
													<td width="3%">
														<%String checkVal = ((BillingSiteLOVVO)val).getBillingSiteCode()+"-"+((BillingSiteLOVVO)val).getBillingSiteDescription();%>
														
														<%--<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>--%>
														 
													<%

													if(   ((String)strSelectedValues).equals(((BillingSiteLOVVO)val).getBillingSiteCode()  )){ %>
														<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
													<%}else{ %>
														<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
													<% } %>
													</td>
											</logic:notEqual>
											
											
											
													<td width="26%"  class="iCargoPageText">
															<bean:write name="val" property="billingSiteCode"/>
													</td>
													<td width="55%" class="iCargoPageText">
															<bean:write name="val" property="billingSiteDescription"/>
													</td>
													<logic:notEqual name="billingSiteLOVForm" property="multiselect" value="Y">
													</a>
													</logic:notEqual>
										</tr>
								
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
							<logic:present name="billingSiteLOVForm" property="fromScreen">
							<logic:equal name="billingSiteLOVForm" property="fromScreen" value="fromAudit">
								<input type="button" title="OK" name="okBut" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName %>','',<%=arrayIndex%>)" />
							</logic:equal>
							<logic:notEqual name="billingSiteLOVForm" property="fromScreen" value="fromAudit">
							<input type="button" name="okBut" title="OK" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName %>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="billingSiteLOVForm" property="fromScreen">
							<input type="button" name="okBut" value="OK" title="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName %>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
							</logic:notPresent>
		
							<ihtml:nbutton property="closeButton" componentID="CMP_MRA_DEFAULTS_BILLINGSITE_CLOSE" accesskey="O" styleClass="btn-inline btn-secondary">
							<common:message  key="mra.defaults.billingsitelov.close" bundle="billingsitelovresource" />
							
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
</html>

