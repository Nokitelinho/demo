<%--************************************************************
* Project	 					: iCargo
* Module Code & Name: shared-CustomerLov
* File Name					: ServiceLov.jsp
* Date							:
* Author(s)					:A-2052
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceLovForm" %>


<bean:define id="serviceLovForm" name="CustomerServiceLovForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceLovForm"/>


<html>
<head>		
	
<title><common:message   bundle="servicelov" key="customermanagement.defaults.servicelov.pagetitle" scope="request"/></title>
<meta name="decorator" content="popup_panel">
<common:include type="script"  src="/js/customermanagement/defaults/ServiceLOV_Script.jsp" />
</head>

<body id="bodyStyle">
	
	

<bean:define id="strAction" name="serviceLovForm"  property="lovaction"  toScope="page"/>
<div class="iCargoPopUpContent" style="height:320px;"   >
<ihtml:form action="<%=(String)strAction%>" styleClass="ic-main-form">


<!--  Hidden to store the selections in case of pagination-->

<ihtml:hidden name="serviceLovForm" property="lovaction"  />
<ihtml:hidden name="serviceLovForm" property="selectedValues"  />
<ihtml:hidden name="serviceLovForm" property="lastPageNum" />
<ihtml:hidden name="serviceLovForm" property="displayPage" />
<ihtml:hidden name="serviceLovForm" property="multiselect" />
<ihtml:hidden name="serviceLovForm" property="pagination" />
<ihtml:hidden name="serviceLovForm" property="formCount" />
<ihtml:hidden name="serviceLovForm" property="lovTxtFieldName" />
<ihtml:hidden name="serviceLovForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="serviceLovForm" property="index" />


<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message key="customermanagement.defaults.servicelov.pagetitle" scope="request"/>
	</span>
	<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-col-100">	
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-80">
								<label class="ic-label-25">
									<common:message key="customermanagement.defaults.servicelov.service" scope="request"/>
								</label>
								<ihtml:text property="code" name="serviceLovForm" 
									componentID="CMP_Customer_Defaults_ServiceLov_Code"  maxlength="20"/>
							</div>
							<div class="ic-input ic-split-20">
								<div class="ic-button-container">
									<ihtml:nbutton property="listButton" componentID="CMP_Customer_Defaults_ServiceLov_List">
										<common:message key="customermanagement.defaults.servicelov.btlist" />
									</ihtml:nbutton>
									<ihtml:nbutton property="clearButton" componentID="CMP_Customer_Defaults_ServiceLov_Clear">
										<common:message key="customermanagement.defaults.servicelov.btclear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<logic:present name="serviceLovForm" property="serviceTypeLovPage" >
					<bean:define id="lovList" name="serviceLovForm" property="serviceTypeLovPage" toScope="page"/>
					<logic:present name="lovList">
						<bean:define id="multiselect" name="serviceLovForm" property="multiselect" />
						<logic:equal name="serviceLovForm" property="pagination" value="Y">
							<bean:define id="lastPageNum" name="serviceLovForm" property="lastPageNum" />
							<div class="ic-row">
								<div class="ic-col-75">
									<common:paginationTag 
										pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" 
										name="lovList"
										display="label" 
										labelStyleClass="iCargoResultsLabel" 
										lastPageNum="<%=(String)lastPageNum %>" />
								</div>
								<div class="ic-col-25">
									<div class="ic-button-container">
										<common:paginationTag 
											pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" 		
											name="lovList" display="pages"
											linkStyleClass="iCargoLink"
											disabledLinkStyleClass="iCargoLink"
											lastPageNum="<%=(String)lastPageNum%>"/>
									</div>
								</div>
							</div>
						</logic:equal>
					</logic:present>
				</logic:present>
				<bean:define id="strFormCount" name="serviceLovForm" property="formCount"  />
				<bean:define id="strMultiselect" name="serviceLovForm" property="multiselect" />
				<bean:define id="strLovTxtFieldName" name="serviceLovForm" property="lovTxtFieldName"  />
				<bean:define id="strLovDescriptionTxtFieldName" name="serviceLovForm" property="lovDescriptionTxtFieldName" />
				<bean:define id="strSelectedValues" name="serviceLovForm" property="selectedValues" />
				<bean:define id="arrayIndex" name="serviceLovForm" property="index"/>
				<div class="ic-row">
					<div class="tableContainer" align="center" id="div1" style="height:200px; width:100%;border:0px">
						<table id="lovListTable" width="100%" class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingCenter" >
									<td width="5%"> &nbsp;</td>
									<td width="25%" class="iCargoLabelleftAligned">
										<common:message key="customermanagement.defaults.servicelov.service" scope="request"/>
									</td>
									<td width="35%" class="iCargoLabelLeftAligned">
										<common:message key="customermanagement.defaults.servicelov.servicedescription" scope="request"/>
									</td>
									<td width="35%" class="iCargoLabelLeftAligned">
										<common:message key="customermanagement.defaults.servicelov.points" scope="request"/>
									</td>
								</tr>
							</thead>
							<logic:present name="serviceLovForm" property="serviceTypeLovPage">
								<bean:define id="lovList" name="serviceLovForm" property="serviceTypeLovPage" toScope="page"/>
								<logic:present name="lovList">
								<tbody>
									<% int i=0;%>
									<logic:iterate id = "val" name="lovList" indexId="indexId">
										
											<logic:notEqual name="serviceLovForm" property="multiselect" value="Y">
												<tr ondblclick="setValueOnDoubleClick('<%=((CustomerServicesVO)val).getServiceCode()%>','<%=((CustomerServicesVO)val).getServiceDescription()%>','<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
											</logic:notEqual>
											<logic:equal name="serviceLovForm" property="multiselect" value="Y">
											<tr>
												<td>
													<%if(((String)strSelectedValues).contains(((CustomerServicesVO)val).getServiceCode())){ %>
														<input type="checkbox" name="selectCheckBox" 
															value="<%=((CustomerServicesVO)val).getServiceCode()%>"  checked="checked"/>
													<%}else{ %>
														<input type="checkbox" name="selectCheckBox" 
															value="<%=((CustomerServicesVO)val).getServiceCode()%>"  />
													<% } %>
												</td>
											</logic:equal>
											<logic:notEqual name="serviceLovForm" property="multiselect" value="Y">
												<td>
													<%String checkVal = ((CustomerServicesVO)val).getServiceCode()+"-"+((CustomerServicesVO)val).getPoints()+"-"+((CustomerServicesVO)val).getKeyContactFlag();%>				
													<%--<input type="checkbox" name="selectCheckBox" 
														value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>--%>
													<%if(((String)strSelectedValues).equals(((CustomerServicesVO)val).getServiceCode())){ %>
														<input type="checkbox" name="selectCheckBox" 
															value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
													<%}else{ %>
														<input type="checkbox" name="selectCheckBox" 
															value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
													<% } %>
												</td>
											</logic:notEqual>
											<td class="ic-left">
												<bean:write name="val" property="serviceCode"/>
											</td>
											<td class="ic-left">
												<bean:write name="val" property="serviceDescription"/>
											</td>
											<td class="ic-left">
												<bean:write name="val" property="points"/>
											</td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
						</logic:present>
					</logic:present>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">       
				<input type="button"  name="okBut" class="iCargoButtonSmall" value="OK"  title="OK"  
					onclick="setValue('<%=strLovTxtFieldName %>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex %>);"  />
				<ihtml:nbutton property="closeButton" componentID="CMP_Customer_Defaults_ServiceLov_Close">
					<common:message   key="customermanagement.defaults.servicelov.btclose" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>
</div>

	</body>
</html>


