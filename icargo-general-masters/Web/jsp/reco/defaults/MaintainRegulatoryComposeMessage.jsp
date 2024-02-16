<%--
* Project	 		: iCargo
* Module Code & Name: Reco
* File Name			: MaintainRegulatoryComposeMessage.jsp
* Date				: 09/06/2014
* Author(s)			: A-5867
--%>

<%@ page language="java" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.RegulatoryComposeMessageForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.Location"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html:html>
<head>


<title><common:message bundle="maintainonetimemaster" key="shared.defaults.maintainonetime.lbl.pageTitle" scope="request"/></title>
<bean:define id="form" name="ComposeMessage" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.RegulatoryComposeMessageForm" toScope="page" />
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/reco/defaults/RegulatoryComposeMessage_Script.jsp"/>
</head>
<body id="bodyStyle">

<div class="iCargoContent" id="pageDiv">
<div class="iCargoContent ic-masterbg" id="pageDiv">
<business:sessionBean id="regulatoryMessages" moduleName="reco.defaults" screenID="reco.defaults.maintainregulatorycompliance" method="get" attribute="regulatoryMessages" />
<business:sessionBean id="regulatoryMessageErrorList" moduleName="reco.defaults" screenID="reco.defaults.maintainregulatorycompliance" method="get" attribute="regulatoryMessageErrorList" />

<ihtml:form action="/reco.defaults.listcomposemessage.do">
<html:hidden property="displayPage" />
<html:hidden property="lastPageNum" />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message  key="reco.composemessage.title" scope="request"/></span>
		<!-- head-container starts -->
		<div class="ic-head-container">
			
			<div class="ic-filter-panel">
				<div class="ic-row">
						<div class="ic-input-container">
							<div class="ic-input ic-split-25">
								<label>
									 <common:message  key="reco.composemessage.rolegroup" scope="request"/>
								</label>
								<ihtml:text  property="roleGroup"  componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroup"  />
								 <div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLovFilter" width="22" height="22"/> </div>
							</div>
							<div class="ic-input ic-split-25">
								<label>
									<common:message key="reco.composemessage.startdate" scope="request"/>
								</label>
								<ibusiness:calendar id="startDate"  property="startDate" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDate" />
							</div>
							<div class="ic-input ic-split-25">
								<label>
									<common:message key="reco.composemessage.enddate" scope="request"/>
								</label>
								<ibusiness:calendar id="endDate" property="endDate" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDate" />
							</div>
							<div class="ic-input ic-split-20">
								<div class="ic-button-container">
										<ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_BtnList">
											<common:message  key="reco.composemessage.tooltip.list" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_BtnClear">
											<common:message key="reco.composemessage.tooltip.clear" />
										</ihtml:nbutton>
								</div>
							</div>																				
						</div>						
				</div>
			</div>
		</div>
		<!-- head-container end -->
		
		<!-- main-container starts -->
		<div class="ic-main-container">
			<div  class="ic-row">
				<h3>
					<common:message  key="reco.composemessage.messagedetails" scope="request"/>
				</h3>
			</div>
			<div class="ic-row">
				<div class="ic-col-55">
					<logic:present name="regulatoryMessages">
						<common:paginationTag pageURL="reco.defaults.listcomposemessage.do" name="regulatoryMessages" display="label"
							labelStyleClass="iCargoResultsLabel" lastPageNum="<%=form.getLastPageNum()%>" />										 
					</logic:present>
					<logic:notPresent name="regulatoryMessages">
						&nbsp;
					</logic:notPresent>
				</div>
				<div class="ic-col-35">
					<div class="ic-button-container">
						<logic:present name="regulatoryMessages">
							<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								pageURL="javascript:submitPage('lastPageNum','displayPage')" name="regulatoryMessages" display="pages"
								lastPageNum="<%=form.getLastPageNum()%>" exportToExcel="true" exportTableId="composemessagetable"
								exportAction="reco.defaults.listcomposemessage.do"/>
						</logic:present>
						<logic:notPresent name="regulatoryMessages">
							&nbsp;
						</logic:notPresent>	
					</div>
				</div>
				<div class="ic-col-10">
					<div class="ic-button-container paddR5">
						<a class="iCargoLink" href="#" id="addNotes" onclick="addRows('composeMessageTableTemplateRow','composeMessageTableBody','hiddenOpFlag')">
							<common:message  key="reco.composemessage.add" scope="request"/></a>
						| <a class="iCargoLink" href="#" id="delNotes" onclick="deleteRows('mstChexk','hiddenOpFlag')">
						<common:message  key="reco.composemessage.delete" scope="request"/></a>
					</div>
				</div>
			</div>	
			<div class="tableContainer" id="div1" style="height:600px;">
					<table class="fixed-header-table" id="composemessagetable">
						<thead>
						<tr>
							<td width="5%"> 
							<input type="checkbox" name="checkAll" onclick="updateHeaderCheckBox(this.form,this.form.checkAll,this.form.mstChexk)"/>
							<span></span>
							</td>
							<td width="22%"><common:message  key="reco.composemessage.rolegroup" scope="request"/>
								<span class="iCargoMandatoryFieldIcon">*</span>
							</td>
							<td width="53%"><common:message  key="reco.composemessage.message" scope="request"/>
								<span class="iCargoMandatoryFieldIcon">*</span>
							</td>
							<td width="10%"><common:message  key="reco.composemessage.startdates" scope="request"/>
								<span class="iCargoMandatoryFieldIcon">*</span>
							</td>
							<td width="10%"><common:message  key="reco.composemessage.enddates" scope="request"/>
								<span class="iCargoMandatoryFieldIcon">*</span>
							</td>
						</tr>
						</thead>
						<tbody id="composeMessageTableBody" align="center">
					<%int rowNo=0;%>
						<logic:present name="regulatoryMessages">												
						<logic:iterate id="regulatoryMessage" name="regulatoryMessages" indexId="rowCount">					
			<!-- ----------------------------------------------- NA------------------------------------------------ -->
						<bean:define id="serialNumber" name="regulatoryMessage" property="serialNumber" />
						<logic:equal name="regulatoryMessage" property="operationFlag" value="U">
						   <tr>						  
						   	<td class="ic-center">
						   		<ihtml:checkbox property="mstChexk" onclick="toggleTableHeaderCheckbox('mstChexk',this.form.checkAll)"/>
								<ihtml:hidden property="hiddenOpFlag" value="N"/>
								<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>
							</td>														
							<td class="iCargoTableDataTd">									 
					<logic:present name="regulatoryMessage" property="rolGroup">
						<logic:notEqual name="regulatoryMessage" property="rolGroup" value="null">
						<bean:define id="rolGroup" name="regulatoryMessage" property="rolGroup" />
						<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value="<%=(String)rolGroup%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="rolGroup">						
						<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value=""/>
					</logic:notPresent>
					<div class= "lovImgTbl" style="padding-top:2px;"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLov" name="roleGroupLov" width="16" height="16" style="vertical-align: middle;
                                                 onclick="displayLOV('admin.accesscontrol.showrolegroup.do','Y','Y','admin.accesscontrol.showrolegroup.do',document.forms[1].roleGroups.value,'Rolegroup','4','roleGroups','',<%=rowNo%>)"/>	</div>
							</td>
							<td class="iCargoTableDataTd ic-center">
					<logic:present name="regulatoryMessage" property="message">
						<logic:notEqual name="regulatoryMessage" property="message" value="null">
						<bean:define id="message" name="regulatoryMessage" property="message" />
						<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="<%=(String)message%>">
						</ihtml:textarea>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="message">						
						<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="">
						</ihtml:textarea>																													
					</logic:notPresent>
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="startDate">
						<logic:notEqual name="regulatoryMessage" property="startDate" value="null">
							<bean:define id="startDate" name="regulatoryMessage" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String startDateStr=startDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value="<%=startDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="startDate">		
						<ibusiness:calendar id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value=""/>																														
					</logic:notPresent>																		
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="endDate">
						<logic:notEqual name="regulatoryMessage" property="endDate" value="null">
							<bean:define id="endDate" name="regulatoryMessage" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String endDateStr=endDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount" value="<%=endDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="endDate">		
						<ibusiness:calendar id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount"  value=""/>																														
					</logic:notPresent>																		
							</td>
						   </tr>
						   <% rowNo++;%>
						   </logic:equal>			
			<!------------------------------------------------- Delete-------------------------------------------------->									   
						   <logic:equal name="regulatoryMessage" property="operationFlag" value="D">
						   <tr class="iCargoTableCellsLeftRowColor1" style="display:none" >						  
						   	<td class="ic-center">
						   		<ihtml:checkbox property="mstChexk" onclick="toggleTableHeaderCheckbox('mstChexk',this.form.checkAll)"/>
								<ihtml:hidden property="hiddenOpFlag" value="D"/>
								<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>
							</td>							
							<td class="iCargoTableDataTd">
								<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100"/>
                                <div class= "lovImgTbl" style="padding-top:2px;"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLov" name="roleGroupLov" onclick="showRoleGroupLov(this)" style="vertical-align: middle; width="16" height="16" /></div>
							</td>
							<td class="iCargoTableDataTd ic-center" >
								<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong">
								</ihtml:textarea>
							</td>
							<td class="iCargoTableDataTd">
								<ibusiness:calendar id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount"/>
							</td>
							<td class="iCargoTableDataTd">
								<ibusiness:calendar id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount"/>
							</td>
						   </tr>
						   </logic:equal>						
						 </logic:iterate>
						</logic:present>						
				<!-- ----------------------------------------------- Insert------------------------------------------------ -->
			<logic:present name="regulatoryMessageErrorList">
					<bean:define id="regulatoryMessageErrorList" name="regulatoryMessageErrorList" scope="page" toScope="page"/>						
					<logic:iterate id="regulatoryMessage" name="regulatoryMessageErrorList"  indexId="rowCount">	
						<logic:equal name="regulatoryMessage" property="operationFlag" value="I">
						   <tr>						  
						   	<td class="ic-center">
						   		<ihtml:checkbox property="mstChexk" onclick="toggleTableHeaderCheckbox('mstChexk',this.form.checkAll)"/>
								<ihtml:hidden property="hiddenOpFlag" value="I"/>
								<ihtml:hidden property="serialNumbers" value=""/>
							</td>							
							<td class="iCargoTableDataTd">															 
					<logic:present name="regulatoryMessage" property="rolGroup">
						<logic:notEqual name="regulatoryMessage" property="rolGroup" value="null">
						<bean:define id="rolGroup" name="regulatoryMessage" property="rolGroup" />
						<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value="<%=(String)rolGroup%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="rolGroup">						
					<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value=""/>
					</logic:notPresent>
					<div class= "lovImgTbl" style="padding-top:2px;"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLov" name="roleGroupLov" width="16" height="16" style="vertical-align: middle;
                                                                          onclick="displayLOV('admin.accesscontrol.showrolegroup.do','Y','Y','admin.accesscontrol.showrolegroup.do',document.forms[1].roleGroups.value,'Rolegroup','4','roleGroups','',<%=rowNo%>)"/>	</div>
							</td>
							<td class="iCargoTableDataTd ic-center">
					<logic:present name="regulatoryMessage" property="message">
						<logic:notEqual name="regulatoryMessage" property="message" value="null">
						<bean:define id="message" name="regulatoryMessage" property="message" />
						<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="<%=(String)message%>">
						</ihtml:textarea>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="message">						
					<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="">
					</ihtml:textarea>																													
					</logic:notPresent>
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="startDate">
						<logic:notEqual name="regulatoryMessage" property="startDate" value="null">
							<bean:define id="startDate" name="regulatoryMessage" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String startDateStr=startDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value="<%=startDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="startDate">		
					<ibusiness:calendar id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value=""/>																														
					</logic:notPresent>																		
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="endDate">
						<logic:notEqual name="regulatoryMessage" property="endDate" value="null">
							<bean:define id="endDate" name="regulatoryMessage" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String endDateStr=endDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount" value="<%=endDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="endDate">		
					<ibusiness:calendar id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount"  value=""/>																														
					</logic:notPresent>																	
							</td>
						   </tr>
						   <% rowNo++;%>
						   </logic:equal>						   
						   <logic:equal name="regulatoryMessage" property="operationFlag" value="N">
						   <bean:define id="serialNumber" name="regulatoryMessage" property="serialNumber" />
						   <tr>						  
						   	<td class="ic-center">
						   		<ihtml:checkbox property="mstChexk" onclick="toggleTableHeaderCheckbox('mstChexk',this.form.checkAll)"/>
								<ihtml:hidden property="hiddenOpFlag" value="N"/>
								<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>
							</td>							
							<td class="iCargoTableDataTd">															 
					<logic:present name="regulatoryMessage" property="rolGroup">
						<logic:notEqual name="regulatoryMessage" property="rolGroup" value="null">
						<bean:define id="rolGroup" name="regulatoryMessage" property="rolGroup" />
						<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value="<%=(String)rolGroup%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="rolGroup">						
					<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups" style="width:80% !important;"  maxlength="100" value=""/>
					</logic:notPresent>
					<div class= "lovImgTbl" style="padding-top:2px;"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLov" name="roleGroupLov" width="16" height="16" style="vertical-align: middle;
                                                                          onclick="displayLOV('admin.accesscontrol.showrolegroup.do','Y','Y','admin.accesscontrol.showrolegroup.do',document.forms[1].roleGroups.value,'Rolegroup','4','roleGroups','',<%=rowNo%>)"/>	</div>
							</td>
							<td class="iCargoTableDataTd ic-center">
					<logic:present name="regulatoryMessage" property="message">
						<logic:notEqual name="regulatoryMessage" property="message" value="null">
						<bean:define id="message" name="regulatoryMessage" property="message" />
						<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="<%=(String)message%>">
						</ihtml:textarea>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="message">						
					<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="">
					</ihtml:textarea>																													
					</logic:notPresent>
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="startDate">
						<logic:notEqual name="regulatoryMessage" property="startDate" value="null">
							<bean:define id="startDate" name="regulatoryMessage" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String startDateStr=startDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value="<%=startDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="startDate">		
					<ibusiness:calendar id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" indexId="rowCount" value=""/>																														
					</logic:notPresent>																		
							</td>
							<td class="iCargoTableDataTd">
					<logic:present name="regulatoryMessage" property="endDate">
						<logic:notEqual name="regulatoryMessage" property="endDate" value="null">
							<bean:define id="endDate" name="regulatoryMessage" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
						<%String endDateStr=endDate.toDisplayDateOnlyFormat();%>																		
						<ibusiness:calendar  id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount" value="<%=endDateStr%>"/>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="regulatoryMessage" property="endDate">		
					<ibusiness:calendar id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" indexId="rowCount"  value=""/>																														
					</logic:notPresent>																		
						</td>
					</tr>
						   <% rowNo++;%>
						   </logic:equal>
						</logic:iterate>						
			</logic:present> 
				<!-- ----------------------------------------------- Template Row------------------------------------------------ -->
			<% LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			 LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			 endDate.addDays(3);
			String startDateStr = TimeConvertor.toStringFormat(startDate.toCalendar(), "dd-MMM-yyyy");
			String endDateStr = TimeConvertor.toStringFormat(endDate.toCalendar(), "dd-MMM-yyyy"); %>
				<tr template="true" id="composeMessageTableTemplateRow" style="display:none">						  
					<td class="ic-center">
						<ihtml:checkbox property="mstChexk" onclick="toggleTableHeaderCheckbox('mstChexk',this.form.checkAll)"/>
						<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
						<ihtml:hidden property="serialNumbers" value=""/>
					</td>							
					<td class="iCargoTableDataTd">
						<ihtml:text  property="roleGroups" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_RoleGroups"  style="width:80% !important;" maxlength="100" value=""/>
                        <div class= "lovImgTbl" style="padding-top:2px;"><img src="<%=request.getContextPath()%>/images/lov.png" id="roleGroupLov" name="roleGroupLov" onclick="showRoleGroupLov(this)" width="16" style="vertical-align: middle;" height="16"/></div>
					</td>
					<td class="iCargoTableDataTd ic-center" >
						<ihtml:textarea property="messages" style="width:99.5%;" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_Message" cols="138" rows="2" maxlength="3000" styleClass="iCargoTextAreaExtraLong" value="">
						</ihtml:textarea>
					</td>
					<td class="iCargoTableDataTd">
						<ibusiness:calendar id="startDates" property="startDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_StartDates" value="<%=(String)startDateStr%>" />
					</td>
					<td class="iCargoTableDataTd">
						<ibusiness:calendar id="endDates" property="endDates" type="image" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_EndDates" value="<%=(String)endDateStr %>" />		
					</td>
				</tr>
			</tbody>
			</table>
		</div>
	</div>
		<!-- main-container end -->
		
		<!-- foot-container starts -->
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnSave" accesskey="S" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_BtnSave">
							<common:message key="reco.composemessage.tooltip.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_Reco_Defaults_MaintainRegulatoryComplianceMessages_BtnClose">
							<common:message  key="reco.composemessage.tooltip.close" />
						</ihtml:nbutton>
				</div>
			</div>
		</div>
		<!-- foot-container end -->
	</div>
	</ihtml:form>
</div>

	</body>
</html:html>