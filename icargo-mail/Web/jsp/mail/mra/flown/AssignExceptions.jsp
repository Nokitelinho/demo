
<%--
* Project	 		: iCargo
* Module Code & Name: mra-flown
* File Name			: AssignExceptions.jsp
* Date				: 21-Aug-2006
* Author(s)			: A-2401
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@include file="/jsp/includes/reports/printFrame.jsp" %>




		
	
<html>
<head>
		
		
	

	<title><bean:message bundle="mraarlassignexceptions" key="mailtracking.mra.flown.assignexceptions.title.assignexceptions" /> </title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/flown/AssignExceptions_Script.jsp" />


</head>

<body>
	
	
	
	
<business:sessionBean
  		id="KEY_ONETIMEVALUES"
  		moduleName="mailtacking.mra"
  		screenID="mailtracking.mra.flown.assignexceptions"
  		method="get"
  		attribute="oneTimeValues" />

<business:sessionBean
  		id="KEY_EXCEPTIONVOS"
  		moduleName="mailtarcking.mra"
  		screenID="mailtracking.mra.flown.assignexceptions"
  		method="get"
		attribute="exceptions" />
<%System.out.println("inside");%>

  <!--CONTENT STARTS-->
  <bean:define id="form" name="MRAFlnAssignExceptionsForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm" toScope="page" />
  
 <div class="iCargoContent" id="contentdiv" >
  <ihtml:form action="/mailtracking.mra.flown.assignexceptions.screenload.do">

   <input type="hidden" name="descriptionField"/>
   <ihtml:hidden property="viewFlownMailFlag" value="<%= form.getViewFlownMailFlag() %>"/>
    <ihtml:hidden property="statusFlag" value="<%= form.getStatusFlag() %>"/>
    <ihtml:hidden property="exceptionCodeForPrint" value="<%= form.getExceptionCodeForPrint() %>"/>
	 <div class="ic-content-main">
		<span class="ic-page-title"><common:message   key="mailtracking.mra.flown.assignexceptions.pagetitle.assignexceptions" scope="request"/></span>
			<div class="ic-head-container">
				<div class="ic-row">
					<h4><common:message   key="mailtracking.mra.flown.assignexceptions.headinglabel.searchcriteria" scope="request"/></h4>
				</div>
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class=" ic-border">
					<div class="ic-row ic-label-45">
					<div class="ic-row">
						<div class="ic-input ic-split-35">
							<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.flightno" scope="request"/></label>
						
							<%String carCode="";%>
							<%String fltNum="";%>

							<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode"
							  id="flightNumber"
							  flightCodeProperty="flightNumber"
							  carriercodevalue="<%=(String)form.getFlightCarrierCode()%>"
							  flightcodevalue="<%=(String)form.getFlightNumber()%>"
							  componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_FLIGHTNO"
							  carrierCodeStyleClass="iCargoTextFieldVerySmall"
							  flightCodeStyleClass="iCargoTextFieldSmall"
							  />
						</div>
						<div class="ic-input ic-split-65">
							<fieldset class="ic-field-set" >
								<legend>
										<common:message   key="mailtracking.mra.flown.assignexceptions.legend.flightdaterange" scope="request"/>
								</legend>
								<div class="ic-row ic-label-45">
								<div class="ic-input ic-mandatory ic-split-40" >
										<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.fromdate" scope="request"/></label>
										<ibusiness:calendar componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_FROMDATE"
									    property="fromDate" type="image" id="fromDate" value="<%=(String)form.getFromDate()%>" maxlength="11"/>
									</div>
									<div class="ic-input ic-mandatory ic-split-60" >
											<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.toDate" scope="request"/></label>
											 <ibusiness:calendar componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_TODATE"
									    property="toDate" type="image" id="toDate" value="<%=(String)form.getToDate()%>" maxlength="11"/>
										</div>
										</div>
						</div>
					</fieldset>
						</div>

				<div class="ic-row ic-label-45">
				<div class="ic-input ic-split-35">
							<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.exception" scope="request"/></label>
					
						<ihtml:text property="exceptionCode" name="MRAFlnAssignExceptionsForm" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_EXCEPTION"  maxlength="10" value="<%=(String)form.getExceptionCode()%>"/>
						<img name="exceptionlov" id="exceptionlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
					</div>
					<div class="ic-input ic-split-30">
						<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.assignee" scope="request"/></label>
						<ihtml:text property="assignee" name="MRAFlnAssignExceptionsForm" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_ASSIGNEE"  maxlength="10" value="<%=(String)form.getAssignee()%>"/>
						<img name="assigneelov" id="assigneelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.assignee.value,'Assignee','1','assignee','',0)" alt="" />
					</div>
					<div class="ic-input ic-split-30">
							<label><common:message   key="mailtracking.mra.flown.assignexceptions.lbl.assigneddate" scope="request"/></label>
								<ibusiness:calendar componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_ASSIGNEDDATE"
					  property="assignedDate" type="image" id="assignedDate" value="<%=(String)form.getAssignedDate()%>" maxlength="11"/>
					</div>

					</div>
				<div class="ic-row ic-label-45">
					<div class="ic-input ic-split-35">
							<label><common:message   key="mailtracking.mra.flown.assignexceptions.tooltip.segorigin" scope="request"/></label>
						<ihtml:text property="segmentOrigin" name="MRAFlnAssignExceptionsForm" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_ORIGIN"  maxlength="4" value="<%=(String)form.getSegmentOrigin()%>"/>
						<img name="segmentOriginlov" id="segmentOriginlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.segmentOrigin.value,'Airport','1','segmentOrigin','',0)" alt="" />
					</div>
					<div class="ic-input ic-split-30">
						<label><common:message   key="mailtracking.mra.flown.assignexceptions.tooltip.segdestination" scope="request"/></label>
						<ihtml:text property="segmentDestination" name="MRAFlnAssignExceptionsForm" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_DESTINATION"  maxlength="4" value="<%=(String)form.getSegmentDestination()%>"/>
						<img name="segmentDestiantionlov" id="segmentDestinationlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.segmentDestination.value,'Airport','1','segmentDestination','',0)" alt="" />
					</div>
					<div class="ic-input ic-split-35">
						<div class="ic-button-container">		
							<ihtml:nbutton property="listButton" accesskey = "L" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_LIST">
								  <common:message   key="mailtracking.mra.flown.assignexceptions.btn.lbl.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="clearButton" accesskey = "C" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_CLEAR">
								  <common:message   key="mailtracking.mra.flown.assignexceptions.btn.lbl.clear" />
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
		<div class="ic-row">
			<h4><common:message   key="mailtracking.mra.flown.assignexceptions.headinglabel.exceptions" scope="request"/></h4>
		</div>
		<div class="ic-row">
			
								<div class="tableContainer" id="div1" style="height:550px;">
							 		 <table width="100%" class="fixed-header-table" id="paymentDetails" >
										<thead>
											<tr class="iCargoTableHeadingLeft">

												<td width="2%" class="iCargoTableHeader">
													<input type="checkbox" name="headChk"  />
												</td>
												<td   class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.flightno" scope="request"/>
												</td>
												<td   class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.flightdate" scope="request"/>
												</td>
												<td   class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.flightsegment" scope="request"/>
												</td>
												<td   class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.consdocnumber" scope="request"/>
												</td>
												<td  class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.despatchnumber" scope="request"/>
												</td>
												<td  class="iCargoTableHeader" width="10%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.mailbagnumber" scope="request"/>
												</td>
                        									<td  class="iCargoTableHeader" width="7%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.exceptionCode" scope="request"/>
												</td>
												<td  class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.exceptiondetails" scope="request"/>
												</td>

												<td   class="iCargoTableHeader" width="15%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.assignee" scope="request"/>
												</td>
												<td   class="iCargoTableHeader" width="8%">
													<common:message   key="mailtracking.mra.flown.assignexceptions.tablehead.assigneddate" scope="request"/>
												</td>



										  </tr>

			                        </thead>
			                         <tbody>
			                         			<logic:present name="KEY_EXCEPTIONVOS">

									<logic:iterate id="exceptionVo" name="KEY_EXCEPTIONVOS"
											type="com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO" indexId="rowCount">
									<%System.out.println(rowCount);%>

									   <tr>
											<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="selectedElements" value="<%=rowCount%>" onclick="toggleTableHeaderCheckbox(this.form.selectedElements,this.form.headChk)"/>
												<ihtml:hidden name="exceptionVo" property="operationFlag" />

											 </td>
											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="airlineCode">
													<bean:write name="exceptionVo" property="airlineCode"/>
												</logic:present>

												<logic:present name="exceptionVo" property="airlineCode">
												<logic:present name="exceptionVo" property="flightNumber">
													&nbsp;
												</logic:present>
												</logic:present>
												<logic:present name="exceptionVo" property="flightNumber">
													<bean:write name="exceptionVo" property="flightNumber"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="flightDate">
													<%=exceptionVo.getFlightDate().toDisplayDateOnlyFormat()%>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="flightSegment">
													<bean:write name="exceptionVo" property="flightSegment"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd"  >
												<logic:present name="exceptionVo" property="consignmentDocNumber">
													<bean:write name="exceptionVo" property="consignmentDocNumber"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd"  >
												<logic:present name="exceptionVo" property="dsnNumber">
													<bean:write name="exceptionVo" property="dsnNumber"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd"  >
												<logic:present name="exceptionVo" property="mailBagCount">
													<bean:write name="exceptionVo" property="mailBagCount"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="exceptionCode">
													<bean:write name="exceptionVo" property="exceptionCode"/>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd">
												<logic:present name="KEY_ONETIMEVALUES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
														<bean:define id="parameterCode" name="oneTimeValue" property="key" />
														<logic:equal name="parameterCode" value="maitracking.flown.exceptioncode"><%System.out.println("onetimes");%>
															<bean:define id="parameterValues" name="oneTimeValue" property="value" />
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<logic:equal name="exceptionVo" property="exceptionCode" value="<%=fieldValue.toString()%>">
																		<bean:write name="parameterValue" property="fieldDescription"/>
																	</logic:equal>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
												</logic:present>
											</td>

											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="assigneeCode">
													<bean:define id="assigneeCode" name="exceptionVo" property="assigneeCode"/>
													<ihtml:text componentID="CMP_CRA_FLOWN_ASSIGNEXCEPTIONS_ASSIGNEEDTL" name="MRAFlnAssignExceptionsForm" property="asigneeCodes" style="text-transform : uppercase" value="<%=assigneeCode.toString()%>" styleClass="iCargoEditableTextFieldRowColor1" maxlength="10"/>
													<img name="asglov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.asigneeCodes.value,'asigneeCodes','1','asigneeCodes','',<%=rowCount%>)" alt="" />

												</logic:present>

												<logic:notPresent name="exceptionVo" property="assigneeCode"><%System.out.println("not present");%>
													<ihtml:text componentID="CMP_CRA_FLOWN_ASSIGNEXCEPTIONS_ASSIGNEEDTL" name="MRAFlnAssignExceptionsForm" property="asigneeCodes" style="text-transform : uppercase" value="" styleClass="iCargoEditableTextFieldRowColor1"  maxlength="10"/>
													<img name="asglov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.asigneeCodes.value,'asigneeCodes','1','asigneeCodes','',<%=rowCount%>)" alt="" />
												</logic:notPresent>

											</td>

											<td class="iCargoTableDataTd">
												<logic:present name="exceptionVo" property="assignedDate">
													<%=exceptionVo.getAssignedDate().toDisplayDateOnlyFormat()%>
												</logic:present>
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
		<div class="ic-row ic-buttonpane-multi-only">
					
			<div class="ic-button-container">
				
			<ihtml:multibutton componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_MAINPRINT" id="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_MAINPRINT"
										 mainMenu="mailtracking.mra.flown.assignexceptions.btn.lbl.printmain" 
										subMenu="{label:mailtracking.mra.flown.assignexceptions.btn.lbl.print,jsFunction:doPrint();,componentID:CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_PRINT},
									{label:mailtracking.mra.flown.assignexceptions.btn.lbl.printdetails,jsFunction:doPrintDetails();,componentID:CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_PRINTDETAILS}" />

								
					<ihtml:nbutton property="processButton" accesskey = "E" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_PROCESS">
						<common:message   key="mailtracking.mra.flown.assignexceptions.btn.lbl.process" />
					 </ihtml:nbutton>

				
					  <ihtml:nbutton property="saveButton" accesskey = "S" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_SAVE">
						<common:message   key="mailtracking.mra.flown.assignexceptions.btn.lbl.save" />
					  </ihtml:nbutton>

					  <ihtml:nbutton property="closeButton" accesskey = "O" componentID="CMP_MRA_FLOWN_ASSIGNEXCEPTIONS_CLOSE">
						<common:message   key="mailtracking.mra.flown.assignexceptions.btn.lbl.close" />
					  </ihtml:nbutton>
				</div>
	 	  	</div>
		</div>

</div>
</ihtml:form>
</div>	
		

	</body>
</html>
