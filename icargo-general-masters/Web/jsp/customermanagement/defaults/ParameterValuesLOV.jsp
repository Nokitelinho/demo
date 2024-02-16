<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  LoyaltyProgrammeLOV.jsp
* Date					:
* Author(s)				:  A-1862
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm" %>

<html:html>
<head>			
	
<title>
Parameter values LOV
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/customermanagement/defaults/ParameterValuesLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
	
<bean:define id="form"
	 name="maintainLoyaltyForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm"
	 toScope="page" />

<business:sessionBean id="parameterVOsForLOV"
                      moduleName="customermanagement.defaults"
		      screenID="customermanagement.defaults.maintainloyalty"
		      method="get"
		      attribute="parameterVOsForLOV"/>




<div class="iCargoPopUpContent" style="height:250px;"   >

<ihtml:form action="/customermanagement.defaults.maintainloyalty.screenloadparametervalues.do" styleClass="ic-main-form">
<ihtml:hidden property="statusFlag" />
<ihtml:hidden property="screenStatusValue"/>
<ihtml:hidden property="saveFlag"/>
<ihtml:hidden property="pageURL"/>
<ihtml:hidden property="dateChanged"/>
<ihtml:hidden property="parameterIndex"/>

<ihtml:hidden property="parameterInLOV"/>

<ihtml:hidden property="validateFlag"/>
<ihtml:hidden property="unitFlag"/>


<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="closeWindow"/>
<ihtml:hidden property="loyaltyName"/>

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		Parameter values LOV
	</span>	
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<div class="ic-col-75">
						<div class="ic-button-container">
							<ul class="ic-list-link">
								<li>
									<a class="iCargoLink" href="#" id="addValue" onclick="addValue();">Add</a>
								</li>
								<li>
									<a class="iCargoLink" href="#" id="delValue" onclick="delValue();">Delete</a>
								</li>
							</ul>							
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer"  style="height:180px;width:100%;">
						<table cellspacing="0" class="fixed-header-table" style="width:100%">
							<thead>
								<tr>
									<td width="10%" class="iCargoTableTd" >
										<input type="checkbox" name="selectedValueAll" />
									</td>
									<td width="90%" class="iCargoTableHeadingLeft">
										values
										<logic:notEqual name="maintainLoyaltyForm" property="unitFlag" value="NP">
											<bean:define id="unitDescription" name="maintainLoyaltyForm" property="unitFlag"/>
											(in <%=(String)unitDescription%>)
										</logic:notEqual>
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="parameterVOsForLOV">
									<%int index = 0;%>
									<logic:iterate id="collCharterreferenceNoLovVO" name="parameterVOsForLOV" indexId="nIndex">
										<logic:notPresent name="collCharterreferenceNoLovVO" property="operationFlag">
											<tr>
												<td class="iCargoTableTd">
													<input type="checkbox" name="selectedValue" value="<%=nIndex%>"/>
												</td>
												<td class="iCargoTableTd">
													<logic:present name="collCharterreferenceNoLovVO" property="parameterValue">
														<bean:define id="parameterValue" 
															name="collCharterreferenceNoLovVO" property="parameterValue" />
														<ihtml:text property="valueInLov" 
															styleClass="iCargoEditableTextFieldRowColor1" style="width:180px;border:0px" 
															value="<%=(String)parameterValue%>" maxlength="100"/>
													</logic:present>
													<logic:notPresent name="collCharterreferenceNoLovVO" property="parameterValue">
														<ihtml:text property="valueInLov" styleClass="iCargoEditableTextFieldRowColor1" 
															style="width:180px;border:0px" value=""	maxlength="100"/>
													</logic:notPresent>
												</td >
											</tr>
											<ihtml:hidden property="parameterValueOpFlag" value="null"/>
										</logic:notPresent>
										<logic:present name="collCharterreferenceNoLovVO" property="operationFlag">
											<bean:define id="operationFlag" name="collCharterreferenceNoLovVO" property="operationFlag" />
											<html:hidden property="parameterValueOpFlag" value="<%=(String)operationFlag%>"/>
											<logic:notEqual name="collCharterreferenceNoLovVO" property="operationFlag" value="D">
												<tr>
													<td class="iCargoTableTd">
														<input type="checkbox" name="selectedValue" value="<%=nIndex%>"/>
													</td>
													<td class="iCargoTableTd">
														<logic:present name="collCharterreferenceNoLovVO" property="parameterValue">
															<bean:define id="parameterValue" 
																name="collCharterreferenceNoLovVO" property="parameterValue" />
															<ihtml:text property="valueInLov" styleClass="iCargoEditableTextFieldRowColor1" 
																style="width:180px;border:0px" 
																value="<%=(String)parameterValue%>" maxlength="100"/>
														</logic:present>
														<logic:notPresent name="collCharterreferenceNoLovVO" property="parameterValue">
															<ihtml:text property="valueInLov" styleClass="iCargoEditableTextFieldRowColor1" 
																style="width:180px;border:0px" value=""	maxlength="100"/>
														</logic:notPresent>
													</td>
												</tr>
											</logic:notEqual>
											<logic:equal name="collCharterreferenceNoLovVO" property="operationFlag" value="D">
												<logic:present name="collCharterreferenceNoLovVO" property="parameterValue">
													<bean:define id="parameterValue" 
														name="collCharterreferenceNoLovVO" property="parameterValue" />
													<ihtml:hidden property="valueInLov"  value="<%=(String)parameterValue%>"/>
												</logic:present>
												<logic:notPresent name="collCharterreferenceNoLovVO" property="parameterValue">
													<ihtml:hidden property="valueInLov"  value=" "/>
												</logic:notPresent>
											</logic:equal>
										</logic:present>
										<%index++;%>
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">       
				<ihtml:button property="btOk" componentID="CMP_CUST_DEFAULTS_POPUP_OK_BTN"  tabindex="5">
					<common:message key="customermanagement.defaults.btn.btok" />
				</ihtml:button>
				<ihtml:button property="btClose" componentID="CMP_CUST_DEFAULTS_POPUP_CLOSE_BTN"  tabindex="6">
					<common:message key="customermanagement.defaults.btn.btclose" />
				</ihtml:button>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

	</body>
</html:html>
