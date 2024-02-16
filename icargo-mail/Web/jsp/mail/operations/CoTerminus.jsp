	<%@ page language="java" %>
	<%@ include file="/jsp/includes/tlds.jsp" %>	
	<%@ page import="com.ibsplc.icargo.framework.session.ApplicationSession" %>
	<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager" %>

	 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO"%>
	  <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO"%>
	<business:sessionBean id="resditModeSession" 
          moduleName="mail.operations"
 		  screenID="mailtracking.defaults.mailperformance" 
		  method="get" 
		  attribute="resditModes" />
	<bean:define id="form"
		 name="MailPerformanceForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm"
		 toScope="page" />
	<business:sessionBean id="coTerminusVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.mailperformance"
		 method="get"
		 attribute="coTerminusVOs"/>
		<head>
		<common:include type="script" src="/js/mail/operations/MailPerformance_Script.jsp"/>
		
		</head>
<%      
 ApplicationSession sessionPage = HttpSessionManager.getApplicationSession(request);
 String ssoFlag=sessionPage.isSSOUser();    
%>

    <div class="ic-input-container ">
	  <div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input-container"><h4>
							<common:message key="mailtracking.defaults.mailperformance.lbl.search" /></h4>
						</div>
					</div>
							<div class ="ic-row">
							
							<div class="ic-input ic-split-25 ic-mandatory">
							<label><common:message key="mailtracking.defaults.mailperformance.lbl.gpaCode" /></label>
								<logic:present name="form" property="pacode" >
									<bean:define id="pacode" name="form" property="pacode" />
									<ihtml:text property="pacode" value="<%=(String)pacode%>" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_GPA" maxlength="7"/>
								</logic:present>
								<logic:notPresent name="form" property="pacode" >
									<ihtml:text property="pacode" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_GPA" maxlength="7"/>
								</logic:notPresent>
								<div class="lovImg"><img name="gpaLOV" id="gpaLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="lov"/></div>
							</div>
							<div class="ic-input ic-split-25">
								<label><common:message key="mailtracking.defaults.mailperformance.lbl.airport" /></label>
								<logic:present name="form" property="airport">
								<bean:define id="airport" name="form" property="airport"/>
									<ihtml:text property="airport" value="<%=(String)airport%>" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD"  maxlength="50"/>
								</logic:present>
								<logic:notPresent name="form" property="airport">
									<ihtml:text property="airport" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD"  maxlength="50"/>
								</logic:notPresent>
								<div class="lovImg"><img name="airportLOV" id="airportLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="lov"/></div>
							</div>
							<div class="ic-input ic-split-25">
								<label><common:message key="mailtracking.defaults.mailperformance.lbl.resMod"/></label>
								
								<ihtml:select property="filterResdit" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_RESMOD" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="resditModeSession">
								
									<logic:iterate id="resditmodeVO" name="resditModeSession">
										<bean:define id="fieldValue" name="resditmodeVO" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="resditmodeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
									
								</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-25 ">
							<label><common:message key="mailtracking.defaults.mailperformance.lbl.recfrmTrk" /></label>
								<ihtml:checkbox property="receivedFromTruck" value="Y"/>
								
							</div>
							
							
						</div>
						<div class ="ic-row">
						<div class="ic-button-container">
									<ihtml:nbutton property="btList" componentID="BUT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_LIST" accesskey="L">
										<common:message key="mailtracking.defaults.mailperformance.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="BUT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_CLEAR" accesskey="C">
										<common:message key="mailtracking.defaults.mailperformance.btn.clear" />
									</ihtml:nbutton>
								</div>
								</div>
			</div>
			
					<div class="ic-button-container ic-pad-5">			
									<a class="iCargoLink" href="#"   id="addLink">Add</a>|
									<a class="iCargoLink"  id="deleteLink">Delete</a>			
					</div>
				
	    <div class="tableContainer" id="div1"   style="height:800px;" >
											<table  class="fixed-header-table">
												<thead>
													<tr class="iCargoTableHeadingLeft" height="25px">
													  <td width="5%" class="iCargoTableHeaderLabel">
														<input id="checkAll" type="checkbox" name="checkAll" />
													  </td>
													  <td width="10%" class="iCargoTableHeaderLabel">
														<common:message key="mailtracking.defaults.mailperformance.lbl.arpCode" />
													  </td>
													  <td width="10%" class="iCargoTableHeaderLabel">
														<common:message key="mailtracking.defaults.mailperformance.lbl.resMod" />
													  </td>
													  <td width="10%" class="iCargoTableHeaderLabel">
														<common:message key="mailtracking.defaults.mailperformance.lbl.recfrmTrk" />
													  </td>												 
												 </thead>
												<tbody id="coTerminusTableBody">
												
												<logic:present name="coTerminusVOs">
												<logic:iterate id ="coTerminusVO" name="coTerminusVOs" type="CoTerminusVO" indexId="rowCount">
												
												<tr >
												
												<logic:present name="coTerminusVO" property="operationFlag"> 
												
												<bean:define id="operationFlag" name="coTerminusVO" property="operationFlag" toScope="request" />
												<bean:define id="seqnum" name="coTerminusVO" property="seqnum" toScope="request" />
												<logic:notEqual name="coTerminusVO" property="operationFlag" value="D">

												<td class="iCargoTableDataTd ic-center" >
													<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>
												</td>
												<td class="iCargoTableDataTd ic-center">
													<logic:equal name="coTerminusVO" property="operationFlag" value="I">
														<ihtml:text property="airportCodes" name="coTerminusVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px" readonly="true"/>
													</logic:equal>
													<logic:notEqual name="coTerminusVO" property="operationFlag" value="I">
														<ihtml:text property="airportCodes" name="coTerminusVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px"/>
													</logic:notEqual>
												</td>
												<td class="iCargoTableDataTd ic-center"> 
												
													<ihtml:text property="resditModes" name="coTerminusVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_RESMOD" style="width:70px" readonly="true"/>
												</td>
												<td class="iCargoTableDataTd ic-center">
												<logic:equal name="coTerminusVO" property="truckFlag" value="Y">	
													<ihtml:checkbox property="truckFlag" name="coTerminusVO" value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" disabled="disabled"/>
												</logic:equal>
												<logic:equal name="coTerminusVO" property="truckFlag" value="N">
													<ihtml:checkbox property="truckFlag" name="coTerminusVO" value="" disabled="disabled"/>
												</logic:equal>
												</td>
													</logic:notEqual>
												<logic:equal name="coTerminusVO" property="operationFlag" value="D">
													<ihtml:hidden property="airportCodes" name="coTerminusVO"/>
													<ihtml:hidden property="resditModes" name="coTerminusVO"/>
													<ihtml:hidden property="truckFlag" name="coTerminusVO"/>
													
												</logic:equal>
												
												<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
												<ihtml:hidden property="operationFlag" value="<%=((String)operationFlag)%>" />
												</logic:present>
												<logic:notPresent name="coTerminusVO" property="operationFlag">
												<bean:define id="seqnum" name="coTerminusVO" property="seqnum" toScope="request" />
												<td class="iCargoTableDataTd ic-center" >
													<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>" readonly="true"/>
												</td>

												<td class="iCargoTableDataTd ic-center">
													<ihtml:text property="airportCodes" name="coTerminusVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px" readonly="true"/>
												</td>

												<td class="iCargoTableDataTd ic-center">
													
													<ihtml:text property="resditModes" indexId="rowCount" name="coTerminusVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_RESMOD" style="width:70px" readonly="true"/>
												</td>

												<td class="iCargoTableDataTd ic-center">
												<logic:equal name="coTerminusVO" property="truckFlag" value="Y">
													<ihtml:checkbox property="truckFlag" name="coTerminusVO" value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" disabled="true"/>
												</logic:equal>
												<logic:equal name="coTerminusVO" property="truckFlag" value="N">
													<ihtml:checkbox property="truckFlag" name="coTerminusVO" value="" disabled="true"/>
												</logic:equal>
												</td>
												<ihtml:hidden property="operationFlag" value="N" />
												<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
												</logic:notPresent>
												</tr>
												</logic:iterate>
												</logic:present>
												
												<tr  template="true" id="coTerminusTemplateRow" style="display:none">
												<ihtml:hidden property="operationFlag" value="NOOP" />
												<ihtml:hidden property="seqnum" value="" />
														 <td class="iCargoTableDataTd ic-center">
															<input type="checkbox" name="rowId"/>
															
														 </td>
														 <td class="iCargoTableDataTd ic-center">
															<ihtml:text property="airportCodes" value=""  style="width:230px"/>
														 </td>
														 <td class="iCargoTableDataTd ic-center">
															<ihtml:text property="resditModes" value=""  style="width:70px" />
														 </td>
														 <td class="ic-center">
															<input type="checkbox" name="truckFlag" />
														 </td>
												 </tr>
												 
												</tbody>
											</table>
										</div>
	  
<div class="ic-foot-container">
				<div class="ic-button-container ic-pad-5">
					<ihtml:nbutton property="btSave" componentID="BUT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_SAVE" accesskey="S">
						<common:message key="mailtracking.defaults.mailperformance.btn.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_CLOSE" accesskey="O">
						<common:message key="mailtracking.defaults.mailperformance.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>	
 </div>