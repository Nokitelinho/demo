<%--
* Project	 		: iCargo
* Module Code & Name: Reco
* File Name			: ExceptionAWBEmbargo.jsp
* Date				: 18/04/2016
* Author(s)			: A-6843
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.Location"%>
<%@ page import = "com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO"%>
<html:html>


<head>
		
	
	
<title><common:message bundle="exceptionembargoresources" key="reco.defaults.exceptionEmbargo.pageTitle"/></title>
	
		<meta name="decorator" content="mainpanelrestyledui">

		<common:include type="script" src="/js/reco/defaults/ExceptionAWBEmbargo_Script.jsp"/>


</head>

<body id="bodyStyle">
		
	
	<% 
		String exceptionStartDate="";
		String exceptionEndDate="";
	%>
	
		
	<bean:define id="form"
		 name="ExceptionEmbargoForm"
		 toScope="page"
		 type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ExceptionEmbargoForm" />

	<business:sessionBean id="exceptionEmb" 
		moduleName="reco.defaults" 
		screenID="reco.defaults.exceptionembargo" 
		method="get" 
		attribute="exceptionEmbargoDetails" />
		
<div class="iCargoContent ic-masterbg">


<ihtml:form action="reco.defaults.onscreenloadexceptionembargo.do" >
	
					
			<div class="ic-content-main">
				<ihtml:hidden property="awbScribbledText" />
			<ihtml:hidden property="applicableTransactionCodes" />
				<html:hidden property="pageNumber" />
				<html:hidden property="displayPage" />
				<html:hidden property="navigationMode" />
			<div class="ic-head-container" >
				<div class="ic-filter-panel">
						<div class="ic-row">
								<div class="ic-input-container">
									<div class="ic-row">
										<div class="ic-input ic-col-40">
											
											<logic:present name="form" property="shipmentPrefixFilter">
												<ibusiness:awb id="awb" awpProperty="shipmentPrefixFilter" awbProperty="masterDocumentNumberFilter"
													awpValue="<%=form.getShipmentPrefixFilter()%>" awbValue="<%=form.getMasterDocumentNumberFilter()%>" 
													awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false"
													componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWB" />
												</logic:present>
												<logic:notPresent name="form" property="shipmentPrefixFilter">
													<ibusiness:awb id="awb" awpProperty="shipmentPrefixFilter" awbProperty="masterDocumentNumberFilter"
													awpValue="" awbValue="" 
													awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false"
													componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWB" />
												</logic:notPresent>
										</div>
										<div class="ic-input ic-col-30">
											<label>
												<common:message key="reco.defaults.exceptionembargo.lbl.startdate"/>
											</label>
												<ibusiness:calendar type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartDate" id="startDateFilter" property="startDateFilter" value="<%=form.getStartDateFilter()%>" />
										</div>
										<div class="ic-input ic-col-30">
											<label>
												<common:message key="reco.defaults.exceptionembargo.lbl.enddate"/>
											</label>
												<ibusiness:calendar type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndDate" id="endDateFilter" property="endDateFilter" value="<%=form.getEndDateFilter()%>" />
										</div>
									</div>
								</div>
						</div>
						<div class="ic-row">
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" componentID="CMP_Reco_Defaults_ExceptionEmbargo_List" accesskey="L">
									<common:message  key="reco.defaults.exceptionEmbargo.btlist" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Clear" accesskey="C">
									<common:message  key="reco.defaults.exceptionEmbargo.btClear" />
								</ihtml:nbutton>
							</div>
						</div>
				</div>
			</div>
			<div class="ic-main-container"  >
				<div class="ic-row">
					<h3><common:message key="reco.defaults.exceptionEmbargo.lbl.exceptiondetails"/></h3>
				</div>
				<div class="ic-row">
							<div class="ic-col-45">
								<logic:present name="exceptionEmb">
									<bean:define id="exceptionawb" name="exceptionEmb"/>
									<common:paginationTag pageURL="reco.details.listexceptionawb.do" name="exceptionawb" display="label" 
										labelStyleClass="iCargoResultsLabel" lastPageNum="<%=form.getPageNumber() %>"/>
								</logic:present>
							</div>
							<div class="ic-col-55">
								<div class="ic-button-container paddR5">	
									<logic:present name="exceptionEmb">
										<common:paginationTag   linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitPage('lastPageNum','displayPage')" name="exceptionEmb" display="pages" 
											lastPageNum="<%=form.getPageNumber()%>" exportToExcel="false" exportTableId="embargoDetails_container"
											exportAction="reco.details.listexceptionawb.do" />
									</logic:present>
										<a href="#" id="add" class="iCargoLink"> 
												<common:message key="reco.defaults.exceptionembargo.add" /> </a>
									|   <a href="#" id="delete" class="iCargoLink">
												<common:message key="reco.defaults.exceptionembargo.delete" scope="request" /></a>
								</div>
							</div>
				</div>
				
				<div class="ic-row">
					<div class="tableContainer" style="height:600px" id="embargoDetails_container">
						<table class="fixed-header-table" id="embargoDetails">
							<thead>
								<tr  class="iCargoTableHeadingLeft">
									<td width="3%" 	><input type="checkbox" name="checkAllBox" onclick="doCheckAll(this.form);" width="1%"><span></span></td>
									<td width="15%" class="ic-mandatory" ><label style="font-weight: inherit !important;"><common:message key="reco.defaults.exceptionembargo.lbl.awbno" /></label><span></span></td>
									<td width="11%"  class="ic-mandatory"><label style="font-weight: inherit !important;"><common:message key="reco.defaults.exceptionembargo.lbl.startdate" /></label><span></span></td>
									<td width="11%"  class="ic-mandatory"><label style="font-weight: inherit !important;"><common:message key="reco.defaults.exceptionembargo.lbl.enddate" /></label><span></span></td>
									<td width="60%"  class="ic-mandatory"><label style="font-weight: inherit !important;"><common:message key="reco.defaults.exceptionEmbargo.lbl.remarks" /></label><span></span></td>
								</tr>
							</thead>
							<tbody id="exceptionTableBody">
								<logic:present name="exceptionEmb">
								<logic:iterate id="exceptionEmbargoDetailsVO" name="exceptionEmb" 
										type="com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO" indexId="rowCount">
										<bean:define id="serialNumber" name="exceptionEmbargoDetailsVO" property="serialNumbers" />
										<tr>
										<logic:equal name="exceptionEmbargoDetailsVO" property="operationFlag" value="I">	
										<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>										
										<td class="iCargoTableDataTd ic-center">
											<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)"  />
											<ihtml:hidden property="hiddenOpFlag" value="I"/>
										</td>
										<td class="iCargoTableDataTd">	
											<logic:present name="exceptionEmbargoDetailsVO" property="masterDocumentNumber">
													<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" 
													awpValue="<%=exceptionEmbargoDetailsVO.getShipmentPrefix()%>" awbValue="<%=exceptionEmbargoDetailsVO.getMasterDocumentNumber()%>" 
													awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false" indexId="rowCount"
													componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" " />
											</logic:present>
											<logic:notPresent name="exceptionEmbargoDetailsVO" property="masterDocumentNumber">
													<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" 
													awpValue="" awbValue="" 
													awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false" indexId="rowCount"
													componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" " />
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="exceptionEmbargoDetailsVO" property="startDate">
												<bean:define id="exceptionLocalStartDate" name="exceptionEmbargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<% exceptionStartDate=TimeConvertor.toStringFormat(exceptionLocalStartDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
												<ibusiness:calendar id="startDate" indexId="rowCount" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value="<%=exceptionStartDate %>"/>
											</logic:present>
											<logic:notPresent name="exceptionEmbargoDetailsVO" property="startDate">
												<ibusiness:calendar id="startDate" indexId="rowCount" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value=""/>
											</logic:notPresent>
										</td>																									
										<td class="iCargoTableDataTd">
											<logic:present name="exceptionEmbargoDetailsVO" property="endDate">
												<bean:define id="exceptionLocalEndDate" name="exceptionEmbargoDetailsVO" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<% exceptionEndDate=TimeConvertor.toStringFormat(exceptionLocalEndDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
												<ibusiness:calendar id="endDate" indexId="rowCount" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value="<%=exceptionEndDate %>"/>
											</logic:present>
											<logic:notPresent name="exceptionEmbargoDetailsVO" property="endDate">
												<ibusiness:calendar id="endDate" indexId="rowCount" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value=""/>
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="exceptionEmbargoDetailsVO" property="remarks">
													<ihtml:text property="remarks" indexId="rowCount" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value="<%=exceptionEmbargoDetailsVO.getRemarks()%>"/>
											</logic:present>
											<logic:notPresent name="exceptionEmbargoDetailsVO" property="remarks">
													<ihtml:text property="remarks" indexId="rowCount" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value=""/>
											</logic:notPresent>
										</td>
										</logic:equal>
										<logic:equal name="exceptionEmbargoDetailsVO" property="operationFlag" value="N">
												
												<td class="iCargoTableDataTd ic-center">
													<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)"  />
													<ihtml:hidden property="hiddenOpFlag" value="N" />
													<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>
												</td>
												<td class="iCargoTableDataTd">	
													<logic:present name="exceptionEmbargoDetailsVO" property="masterDocumentNumber">
															<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" 
															awpValue="<%=exceptionEmbargoDetailsVO.getShipmentPrefix()%>" awbValue="<%=exceptionEmbargoDetailsVO.getMasterDocumentNumber()%>" 
															awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false" indexId="rowCount"
															componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" " />
													</logic:present>
													
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="startDate">
														<bean:define id="exceptionLocalStartDate" name="exceptionEmbargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
														<% exceptionStartDate=TimeConvertor.toStringFormat(exceptionLocalStartDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<ibusiness:calendar id="startDate" indexId="rowCount" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value="<%=exceptionStartDate %>"/>
													</logic:present>
												</td>																									
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="endDate">
															<bean:define id="exceptionLocalEndDate" name="exceptionEmbargoDetailsVO" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
															<% exceptionEndDate=TimeConvertor.toStringFormat(exceptionLocalEndDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
															<ibusiness:calendar id="endDate" indexId="rowCount" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value="<%=exceptionEndDate %>"/>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="remarks">
															<ihtml:text property="remarks" indexId="rowCount" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value="<%=exceptionEmbargoDetailsVO.getRemarks()%>"/>
													</logic:present>
												</td>
										</logic:equal>
										<logic:equal name="exceptionEmbargoDetailsVO" property="operationFlag" value="U">
												
												<td class="iCargoTableDataTd ic-center">
													<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)"  />
													<ihtml:hidden property="hiddenOpFlag" value="U" />
													<ihtml:hidden property="serialNumbers" value="<%=serialNumber.toString()%>"/>
												</td>
												<td class="iCargoTableDataTd">	
													<logic:present name="exceptionEmbargoDetailsVO" property="masterDocumentNumber">
															<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" 
															awpValue="<%=exceptionEmbargoDetailsVO.getShipmentPrefix()%>" awbValue="<%=exceptionEmbargoDetailsVO.getMasterDocumentNumber()%>" 
															awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false" indexId="rowCount"
															componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" " />
													</logic:present>
													<logic:notPresent name="exceptionEmbargoDetailsVO" property="masterDocumentNumber">
														<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber" 
														awpValue="" awbValue="" 
														awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false" indexId="rowCount"
														componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" "/>
													</logic:notPresent>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="startDate">
														<bean:define id="exceptionLocalStartDate" name="exceptionEmbargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
														<% exceptionStartDate=TimeConvertor.toStringFormat(exceptionLocalStartDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<ibusiness:calendar id="startDate" indexId="rowCount" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value="<%=exceptionStartDate %>"/>
													</logic:present>
													<logic:notPresent name="exceptionEmbargoDetailsVO" property="startDate">
														<ibusiness:calendar id="startDate" indexId="rowCount" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value=""/>
													</logic:notPresent>
												</td>																									
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="endDate">
															<bean:define id="exceptionLocalEndDate" name="exceptionEmbargoDetailsVO" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
															<% exceptionEndDate=TimeConvertor.toStringFormat(exceptionLocalEndDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
															<ibusiness:calendar id="endDate" indexId="rowCount" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value="<%=exceptionEndDate %>"/>
													</logic:present>
													<logic:notPresent name="exceptionEmbargoDetailsVO" property="endDate">
															<ibusiness:calendar id="endDate" indexId="rowCount" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value=""/>
													</logic:notPresent>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="exceptionEmbargoDetailsVO" property="remarks">
															<ihtml:text property="remarks" indexId="rowCount" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value="<%=exceptionEmbargoDetailsVO.getRemarks()%>"/>
													</logic:present>
													<logic:notPresent name="exceptionEmbargoDetailsVO" property="remarks">
															<ihtml:text property="remarks" indexId="rowCount" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value=""/>
													</logic:notPresent>
												</td>
										</logic:equal>											
										</tr>
										<% rowCount ++;	%>
									</logic:iterate>
									</logic:present>
									<% LocalDate strtDates = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
										LocalDate endDates = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
										endDates.addDays(3);
										String startDateStr = TimeConvertor.toStringFormat(strtDates.toCalendar(), "dd-MMM-yyyy");
										String endDateStr = TimeConvertor.toStringFormat(endDates.toCalendar(), "dd-MMM-yyyy"); %>
							<tr template="true" id="exceptionTemplateRow" style="display:none">
										<td class="ic-center">
											<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)" />
									<ihtml:hidden property="hiddenOpFlag" value="NOOP" />
											<ihtml:hidden property="serialNumbers" value=""/>
										</td> 
										<td>
										<ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="masterDocumentNumber"
													awpValue="" awbValue="" 
													awbStyleClass ="iCargoTextFieldMediumCaps" isCheckDigitMod="false"
													componentID="CMP_Reco_Defaults_ExceptionEmbargo__AWBnum" awbLabel=" "/>
										</td>
										<td>
											<ibusiness:calendar id="startDate" property="startDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__StartingDate" value="<%=(String)startDateStr%>"/>
										</td>																									
										<td>
											<ibusiness:calendar id="endDate" property="endDate" type="image" componentID="CMP_Reco_Defaults_ExceptionEmbargo__EndingDate" value="<%=(String)endDateStr %>"/>
										</td>
										<td>
											<ihtml:text property="remarks" id="remarks" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Remarks" style="width:740px;" value=""/>
										</td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container" >
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btSave"  accesskey="S" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Save_Button">
							<common:message key="reco.defaults.exceptionEmbargo.btn.save"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_Reco_Defaults_ExceptionEmbargo_Close_Button">
							<common:message key="reco.defaults.exceptionEmbargo.btn.close"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			</div>
		</ihtml:form>
	
	</div>

	</body>
</html:html>

