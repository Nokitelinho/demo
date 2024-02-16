<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  SegmentExceptions.jsp
* Date					:  11-Aug-2006
* Author(s)				:  Deepthi.E.S.
*************************************************************************/
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.SegmentExceptionsForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
	
<title>
<common:message bundle="uldPoolOwnersResources" key="uld.defaults.segmentexceptions.popup.lbl.segmentExceptionsTitle" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/SegmentExceptions_Script.jsp"/>
</head>
<body >
	


<business:sessionBean id="UldPoolSegmentVos"
	moduleName="uld.defaults"
	screenID="uld.defaults.uldpoolowners"
	method="get" attribute="UldPoolSegmentVos" />

<div  class="iCargoPopUpContent">
<ihtml:form action="uld.defaults.screenloadsegmentexceptions.do" styleClass="ic-main-form">
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<ihtml:hidden property="selectedRow"/>
	<ihtml:hidden property="errorFlag"/>
	<ihtml:hidden property="airlineOne"/>
	<ihtml:hidden property="airlineTwo"/>
	<ihtml:hidden property="airport"/>
	<ihtml:hidden property="remarks"/>
									
		<div class="ic-content-main">
			<span class="ic-page-title"><common:message key="uld.defaults.segmentexceptions.popup.lbl.sementexceptionsHeading" /></span>
				<div class="ic-main-container">
					<div class="ic-button-container">
						<a href="#" class="iCargoLink" id="addUld"  onclick="addDetails()"><common:message key="uld.defaults.segmentexceptions.popup.lbl.add" scope="request"/></a>
						|<a href="#" class="iCargoLink" id="deleteUld"  onclick="deleteDetails()"><common:message key="uld.defaults.segmentexceptions.popup.lbl.delete" scope="request"/></a> 
					</div>
			
					<div class="tableContainer"  id="div1"  style="height:260px;">
						<table  class="fixed-header-table" >
							 <thead>
								<tr class="iCargoTableHeadingLeft">
									 <td width="5%"> <input type="checkbox" name="masterRowId" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.selectedRows)"/></td>
									 <td width="48%"><common:message key="uld.defaults.segmentexceptions.popup.lbl.origin" scope="request"/></td>
									 <td width="47%"><common:message key="uld.defaults.segmentexceptions.popup.lbl.destination" scope="request"/></td>
								</tr>
							 </thead>
							<tbody id="ULDSegmentExceptionsTableBody">
											
												 <logic:present name="UldPoolSegmentVos">
												  <bean:define id="poolsegments" name="UldPoolSegmentVos"/>
												  <logic:iterate id="segmentExceptionsVO" name="poolsegments" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO">
												 
												  <logic:present name="segmentExceptionsVO" property="operationFlag">
												  <bean:define id="opFlag" name="segmentExceptionsVO" property="operationFlag"/>
												 </logic:present>
												 <logic:notPresent name="segmentExceptionsVO" property="operationFlag">
												  <bean:define id="opFlag"  value="NA"/>
												 </logic:notPresent>
								   
													<logic:notMatch name="opFlag" value="D">
														<tr>
														<logic:present name="segmentExceptionsVO" property="operationFlag">
														<ihtml:hidden property="operationFlag" value="<%=segmentExceptionsVO.getOperationFlag()%>"/>
														<ihtml:hidden property="hiddenOperationFlag" value="<%=segmentExceptionsVO.getOperationFlag()%>" />
														</logic:present>

														<logic:notPresent name="segmentExceptionsVO" property="operationFlag">
														<ihtml:hidden property="operationFlag" value="NA"/>
														<ihtml:hidden property="hiddenOperationFlag" value="NA" />
														</logic:notPresent>
												 
											
														<td> <ihtml:checkbox property="selectedRows"  onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterRowId)" value="<%=String.valueOf(nIndex)%>" /></td>
														<td  class="iCargoTableDataTd" > 
															<logic:present name="segmentExceptionsVO" property="origin">
															<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  readonly="false" indexId="nIndex" styleId="origin" property="origin" componentID="TXT_ULD_DEFAULTS_SEGEXP_ORIGIN"  value="<%=segmentExceptionsVO.getOrigin()%>" style="width:130px;border:0px" />
															<img name="originlov" id="originlov<%=nIndex.toString()%>"
															src="<%=request.getContextPath()%>/images/lov.gif"
															width="18" height="18" alt="Airline LOV" />
															</logic:present>

															<logic:notPresent name="segmentExceptionsVO" property="origin" >
															<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" property="origin"  componentID="TXT_ULD_DEFAULTS_SEGEXP_ORIGIN" readonly="false" indexId="nIndex" styleId="origin"  value="" style="width:130px;border:0px" />
															<img name="originlov" id="originlov<%=nIndex.toString()%>"
															src="<%=request.getContextPath()%>/images/lov.gif"
															width="18" height="18"  alt="Airline LOV"/>
															</logic:notPresent>
													 
														</td>
												 
														<td  class="iCargoTableDataTd"> 
															<logic:present name="segmentExceptionsVO" property="destination">
															<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  readonly="false" indexId="nIndex" styleId="destination" property="destination" componentID="TXT_ULD_DEFAULTS_SEGEXP_DESTINATION"  value="<%=segmentExceptionsVO.getDestination()%>" style="width:130px;border:0px" />
															<img name="destinationlov" id="destinationlov<%=nIndex.toString()%>"
															src="<%=request.getContextPath()%>/images/lov.gif"
															width="18" height="18" alt="Airline LOV" />
															</logic:present>

															<logic:notPresent name="segmentExceptionsVO" property="destination" >
															<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" property="destination" componentID="TXT_ULD_DEFAULTS_SEGEXP_DESTINATION"  readonly="false" indexId="nIndex" styleId="destination"  value="" style="width:130px;border:0px" />
															<img name="destinationlov" id="destinationlov<%=nIndex.toString()%>"
															src="<%=request.getContextPath()%>/images/lov.gif"
															width="18" height="18"  alt="Airline LOV"/>
															</logic:notPresent>
														 
														</td>
														</tr>
														</logic:notMatch>
														
														<logic:match name="opFlag" value="D">
															<ihtml:hidden property="origin" />
															<ihtml:hidden property="destination" />
															<ihtml:hidden property="operationFlag" value="D"/>
															<ihtml:hidden property="hiddenOperationFlag" value="D" />
														</logic:match>
													
													</logic:iterate>
													</logic:present>
													
									<!-- Template row started -->
									
									<bean:define id="templateRowCount" value="0"/>
										<tr template="true" id="SegmentExceptionsTemplateRow" style="display:none">
										<ihtml:hidden property="hiddenOperationFlag" value="NOOP" />
										<td> <input type="checkbox" name="selectedRows" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterRowId)"/></td>
										<td  class="iCargoTableDataTd"><ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" property="origin"  componentID="TXT_ULD_DEFAULTS_SEGEXP_ORIGIN" value="" style="width:130px;border:0px" indexId="templateRowCount"/>
										<img name="originlov" id="originlov" src="<%=request.getContextPath()%>/images/lov.gif"  width="18" height="18" alt="Airline LOV"/>
										</td>

										<td class="iCargoTableDataTd"><ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3" property="destination" componentID="TXT_ULD_DEFAULTS_SEGEXP_DESTINATION"  value="" style="width:130px;border:0px" indexId="templateRowCount"/>
										<img name="destinationlov" id="destinationlov" src="<%=request.getContextPath()%>/images/lov.gif"  width="18" height="18" alt="Airline LOV"/>
										</td>
										</tr>
									<!-- template row ended -->
									
							
							
							</tbody>
						</table>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnOk" componentID="BTN_ULD_DEFAULTS_SEGMENTEXCEPTIONS_OK">
						<common:message key="uld.defaults.segmentexceptions.popup.lbl.ok" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_SEGMENTEXCEPTIONS_CLOSE">
						<common:message key="uld.defaults.segmentexceptions.popup.lbl.close" scope="request"/>
						</ihtml:nbutton>
					 </div>
				</div>
			
</div>
    </ihtml:form>
    </div>
			
		 
	</body>
</html:html>
											 
											 

