
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  mail.operations
* File Name				:  MailBagHistory.jsp
* Date					:  12-Sep-2018
* Author(s)				:  A-8164
*************************************************************************/ --%>
<%@ page language="java"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm"%>
<%@ page
	import="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO"%>
	<%@ page
	import="com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO"%>
<%@ page info="lite" %>

<html:html>
<head>	  

<title><common:message bundle="mailbagHistoryResources"
		key="mailtracking.defaults.mbHistory.lbl.title" /></title>
<meta name="decorator" content="popuppanelux">
<common:include type="script"
	src="/js/mail/operations/ux/MailBagHistory_Script.jsp" />
	<style>
	.dataTables_wrapper{ width:100% !important;}
	</style>
</head>	   

<body id="bodyStyle">   

	<bean:define id="popup" value="true" />
	<bean:define id="form" name="MailBagHistoryUxForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm"
		toScope="page" />
	<business:sessionBean id="mailBagHistoryVOs"
		moduleName="mail.operations"
		screenID="mail.operations.ux.mailbaghistory" method="get"
		attribute="mailBagHistoryVOs" />
	<business:sessionBean id="mailHistoryRemarksVOs"
		moduleName="mail.operations"
		screenID="mail.operations.ux.mailbaghistory" method="get"
		attribute="mailHistoryRemarksVOs" />
	<business:sessionBean id="ONETIME_STATUS"
		moduleName="mail.operations"
		screenID="mail.operations.ux.mailbaghistory" method="get"
		attribute="oneTimeStatus" />
	<business:sessionBean id="ONETIME_BILLING_STATUS"
		moduleName="mail.operations"
		screenID="mail.operations.ux.mailbaghistory" method="get"
		attribute="oneTimeBillingStatus" />
		<business:sessionBean id="ONETIME_SERVICELEVEL"
		moduleName="mail.operations"
		screenID="mail.operations.ux.mailbaghistory" method="get"
		attribute="oneTimeServiceLevel" />
		<ihtml:form action="/mail.operations.ux.mbHistory.screenload.do">
			<ihtml:hidden property="btnDisableReq" />
			<ihtml:hidden property="totalViewRecords" />
			<ihtml:hidden property="displayPopupPage" />   
			<ihtml:hidden property="fromScreenId" />
			<ihtml:hidden property="mailSequenceNumber" />
				<ihtml:hidden property="index" />
			<div class="main-container" >
        <header class="clearfix">
            <!--Filter Panel- Edit Fields-->
            <div class="flippane animated  fadeInDown" id="editSearch">
                <div class="pad-md pad-b-3xs col icl-filter">
                    <i class="icon ico-close-fade flipper" onClick="showMainTab();" ></i>
                    <div class="row">
                        <div class="col-5">
                            <logic:present name="form" property="mailbagId">	
								<bean:define id="mailbagId" name="form" property="mailbagId"/>	
								 <label class="text-grey-light"><common:message
											key="mailtracking.defaults.mbHistory.tooltip.mailbagid" /></label>
										<ihtml:text tabindex="1" labelStyleClass="form-control-label" 
										property="mailbagId" id="mailbagId" styleClass="form-control" maxlength="29" style="width:250px"   value="<%=(String)mailbagId%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILBAGID"/>                                                                                                 
							</logic:present>
							<logic:notPresent name="form" property="mailbagId">
							<label class="text-grey-light"><common:message
											key="mailtracking.defaults.mbHistory.tooltip.mailbagid" /></label>
								<ihtml:text tabindex="1"  labelStyleClass="form-control-label" 
										property="mailbagId" id="mailbagId" styleClass="form-control" maxlength="29" style="width:250px" componentID="CMP_MAILTRACKING_DEFAULTS_MAILBAGID"/>                                                                                                 
							</logic:notPresent>	
                        </div>
                        <div class="col">
                            <div class="mar-t-md">
                                <ihtml:nbutton  accesskey="L" id="btnList" property="btnList" styleClass="btn btn-primary"  
									componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_LIST"> 
										<common:message key="mailtracking.defaults.mbHistory.tooltip.list" />
								</ihtml:nbutton>
								<ihtml:nbutton  accesskey="C" id="btnClear" property="btnClear" styleClass="btn btn-default"  
									componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_CLEAR">
										<common:message key="mailtracking.defaults.mbHistory.tooltip.clear" />
								</ihtml:nbutton>
                            </div>
                        </div>
                    </div>
					 <div class="row">
						 <label > &nbsp; </label>
					 </div>
                </div>
            </div>
            <div style="display:none;" class="col pad-md flippane secondary-flippane animated fadeInDown" id="searchPreview">
                <div class="row">
                    <div class="col-md-3 col-sm-4">
                        <label class="form-control-label"><common:message key="mailtracking.defaults.mbHistory.tooltip.mailbagid" /></label>
                        <div class="form-control-data">
							 <logic:present name="form" property="mailbagId">
								<bean:write name="form" property="mailbagId"/>
							 </logic:present>
                        </div>
                    </div>
                </div>
				<logic:notPresent name="form" property="isPopUp">
                <a href="#" class="flipper">
                    <i class="icon ico-pencil-rounded-orange" onClick="showOverViewTab();"></i>
                </a>
				</logic:notPresent>
            </div>
            <!--Filter Panel- Edit Fields ENDS-->
        </header>
        <section class="mar-t-md" id="mainSection">
            <div class="row">
                <div class="col-24 col-md-7 col-lg-7 col-sm-7  mar-b-sm">
                    <div class="card" style="min-height:100%">
                        <div class="card-header pad-md">
                            <label class="form-control-label"><common:message key="mailtracking.defaults.mbHistory.tooltip.mailbagid" /></label>
                            <div class="suggest label-only">
                                <div class="row">
                                    <div class="col">
										<div class="form-control-data">
											<logic:present name="form" property="mailbagId">
												<bean:define id="mailId" name="form" property="mailbagId"/>
												<ihtml:text  
														property="totalViewRecords" id="mailToDisplay" styleClass="form-control" maxlength="29" value="<%= (String)mailId%>" /> 
										<!--	 <input type="text" id="mailToDisplay" value="<%= (String)mailId%>" style="width:250px" readonly onchange="listMailBag();" > </input> -->
											</logic:present>
										</div>
                                    </div>
									<div >
										 <button class="btn btn-icon" type="button" id="SUGGEST_mailToDisplay"><i class="icon ico-updown-dark ico-updown-dark-down"></i></button>
									</div>
									<div style="display:none" id="tmpSpan"></div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body pb-0">
                            <div class="row">
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">OOE:</label>
                                        <span class="mar-l-xs"><bean:write name="form" property="ooe" /></span>&nbsp;&nbsp;
                                         <label class="text-grey-light"> DOE:</label>
										 <span class="mar-l-xs"><bean:write name="form" property="doe" /></span>
                                    </div>
                                   <!-- <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
											key="mailtracking.defaults.mbHistory.lbl.destn" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="doe" /></span>
                                    </div>-->
									 <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">Origin:</label> 
                                        <span class="mar-l-xs"><bean:write name="form" property="origin" /> </span>  &nbsp;&nbsp;
										<label class="text-grey-light"> Destination:</label>
										 <span class="mar-l-xs"><bean:write name="form" property="destination" /></span>
                                    </div>
									<!-- <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">Destination</label>
                                        <span class="mar-l-xs"><bean:write name="form" property="destination" /></span>
                                    </div>-->
									<!-- <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">Consignment Date</label>
                                        <span class="mar-l-xs"><bean:write name="form" property="consignmentDate" /></span>
                                    </div>-->
									 <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">Consignment:</label>
										<logic:present name="form" property="consignmentNumber">
                                        <span class="mar-l-xs"><bean:write name="form" property="consignmentNumber" /> </span>  <span class="mar-l-xs"><bean:write name="form" property="consignmentDate" />
										</logic:present>
                                    </div>
									<div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">Billing Status:</label>
										<span class="mar-l-xs">
										<logic:present name="form" property="billingStatus"> 
											<logic:equal name="form" property="billingStatus" value="">NIL</logic:equal>
											<bean:define id="billingStatus" name="form"
														property="billingStatus" />
												<logic:present name="ONETIME_BILLING_STATUS">
												<logic:iterate id="oneTimeBillingStatus" name="ONETIME_BILLING_STATUS"
														type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="oneTimeBillingStatus"
																property="fieldValue">
															<bean:define id="fieldValue" name="oneTimeBillingStatus"
																	property="fieldValue" />
															<logic:equal name="oneTimeBillingStatus"
																	property="fieldValue" value="<%=(String) billingStatus%>">
															<bean:write name="oneTimeBillingStatus"
																	property="fieldDescription" />
															</logic:equal>
													</logic:present>
												</logic:iterate>
												</logic:present>
										</logic:present>
										<logic:notPresent name="form" property="billingStatus">
											NIL
										</logic:notPresent>
										</span> 
                                    </div>
										<common:xgroup>
								
									<div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.serlvl" /></label>
										<logic:present
												name="form" property="mailSerLvl">
												<bean:define id="mailSerLvl" name="form"
														property="mailSerLvl" />
										<logic:present name="ONETIME_SERVICELEVEL">
												<logic:iterate id="oneTimeStatus" name="ONETIME_SERVICELEVEL"
														type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="oneTimeStatus"
																property="fieldValue">
															<bean:define id="fieldValue" name="oneTimeStatus"
																	property="fieldValue" />
															<logic:equal name="oneTimeStatus"
																	property="fieldValue" value="<%=(String) mailSerLvl%>">
															<bean:write name="oneTimeStatus"
																	property="fieldDescription" />
															</logic:equal>
													</logic:present>
												</logic:iterate>
											</logic:present>
											</logic:present>
                                      <!-- <span class="mar-l-xs"><bean:write name="form" property="mailSerLvl" /></span>-->
                                    </div>	
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">TSW</label>
                                        <span class="mar-l-xs"><bean:write name="form" property="transportWindowDate" />  <bean:write name="form" property="transportWindowTime" /></span>
                                    </div>									
						     
                             </common:xgroup >
							  <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.RDT" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="reqDeliveryTime" /></span>
                                    </div>
                                   
                                   
							
									<common:xgroup>
									
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.ontimedelivery" /></label>
                                        <span class="mar-l-xs">
										<% int count = 0; %>
						
							<logic:present name="mailBagHistoryVOs">
								
							<logic:iterate id="mailbagHistoryVO" name="mailBagHistoryVOs"
											type="MailbagHistoryVO" indexId="rowCount">
							<% if(count == 0){ %> 
							<logic:present name="mailbagHistoryVO" property="onTimeDelivery">
													<bean:define id="onTimeDelivery" name="mailbagHistoryVO" property="onTimeDelivery" toScope="page"/>
													<% String onTimDlvFlag = "";
														if("Y".equals(onTimeDelivery)){
															onTimDlvFlag = "Yes";
														}else if("N".equals(onTimeDelivery)){
															onTimDlvFlag = "No";
														}else{
															onTimDlvFlag = "";
														}%>
													<%=onTimDlvFlag%>
													<% count++; %>
													
							 </logic:present>
							<%}%>
							 </logic:iterate>
							 <logic:notPresent name="mailbagHistoryVO" property="onTimeDelivery">
							 &nbsp;
							 </logic:notPresent>
							 
						</logic:present></span>
                                    </div>
						
                         </common:xgroup >
						 <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light">PA Code</label>
                                        <span class="mar-l-xs"><common:write name="form" property="poacod" /></span>
                          </div>
						 <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.wt" /></label>
                                        <span class="mar-l-xs"><common:write name="form" property="weightMeasure" unitFormatting="true"/></span>&nbsp;&nbsp;
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.actualwt" /></label>
                                        <span class="mar-l-xs"><common:write name="form" property="actualWeightMeasure" unitFormatting="true"/></span>
                                    </div>
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.catogory" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="catogory" /></span>&nbsp;&nbsp;
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.subclas" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="mailSubclass" /></span>
                                    </div>
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.year" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="year" /></span>
                                    </div>
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.despNo" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="dsn" /></span>
                                    </div>
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.rsn" /> </label>
                                        <span class="mar-l-xs"><bean:write name="form" property="rsn" /></span>
                                    </div>
									<!--Added by A-8353 starts-->
									<!--Added by A-7540 starts-->
							<!--Added by A-7540 ends-->
                                    <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.mailremarks" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="mailRemarks" /></span>
                                </div>
								 <div class="col-auto col-lg-24 col-md-24 col-sm-24 mar-b-xs">
                                        <label class="text-grey-light"><common:message
										key="mailtracking.defaults.mbHistory.lbl.acceptancePostalContainerNumber" /></label>
                                        <span class="mar-l-xs"><bean:write name="form" property="acceptancePostalContainerNumber" /></span>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-24 col-md-17 col-lg-17 col-sm-17 mar-b-sm">
				<div id="tabs" class="tabs" style="display:block">
						<div class="tab-row">
							<ul class="nav nav-tabs">
							<li class="nav-item">
								<a class="nav-link open-new" id="historyseltab" href="#tabs-history"><b>History Details</b></a>
							</li>
							<logic:notEmpty name="mailHistoryRemarksVOs">
							<li class="nav-item">
								<a class="nav-link open-req" id="notesseltab" href="#tabs-notes"><b>Notes</b></a>
								</li>
								</logic:notEmpty>
						</ul>
						</div>	
						<div id="tabs-history">
						<div class="card history_Tbl"id="historyforcetabid">
                    <div class="card"  style="min-height:100%">
                            </div>
                            
                        <div class="card-body p-0" id="mbHis">
						</div>
						<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                            <table id="mbHisTable" class="table table-x-md m-0" style="width:100%">
                                <thead>
                                    <tr>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.date" /> & <common:message
												key="mailtracking.defaults.mbHistory.lbl.time" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.actualtime" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.operation" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.airport" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.flt" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.container" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.pou" /></th>
                                        <th><common:message
												key="mailtracking.defaults.mbHistory.lbl.userid" /></th>
										<th><common:message
												key="mailtracking.defaults.mbHistory.lbl.additionalInformation" /></th>
										<th>Source</th>
                                    </tr>
                                </thead>
                                <tbody>
									<logic:present name="mailBagHistoryVOs">
										<logic:iterate id="mailbagHistoryVO" name="mailBagHistoryVOs"
											type="MailbagHistoryVO" indexId="rowCount">
                                    <tr>
                                        <td style="min-width:95px">
                                            <span class="d-block mar-t-2xs"><%=mailbagHistoryVO.getScanDate()
													.toDisplayFormat("dd-MMM-yyyy")%></span>
                                            <span class="d-block mar-t-2xs"><%=mailbagHistoryVO.getScanDate()
													.toDisplayFormat("HH:mm")%></span>
                                        </td> 
										<logic:notEmpty name="mailbagHistoryVO"
														property="messageTime">
											<td><%=mailbagHistoryVO.getMessageTime()
													.toDisplayFormat(
														"dd-MMM-yyyy HH:mm")%>
											</td>
										</logic:notEmpty>
										<logic:empty name="mailbagHistoryVO" property="messageTime">
											<td>&nbsp;</td>
										</logic:empty>
                                        <td>
											<logic:present
												name="mailbagHistoryVO" property="mailStatus">
												<bean:define id="mailStatus" name="mailbagHistoryVO"
														property="mailStatus" />
												<logic:present name="ONETIME_STATUS">
												<logic:iterate id="oneTimeStatus" name="ONETIME_STATUS"
														type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="oneTimeStatus"
																property="fieldValue">
															<bean:define id="fieldValue" name="oneTimeStatus"
																	property="fieldValue" />
															<logic:equal name="oneTimeStatus"
																	property="fieldValue" value="<%=(String) mailStatus%>">
															<bean:write name="oneTimeStatus"
																	property="fieldDescription" />
															</logic:equal>
													</logic:present>
												</logic:iterate>
												
														<logic:present
															name="mailbagHistoryVO" property="additionalInfo">
															<a class="scale-icon" id="addInfo">
																<img src="<%=request.getContextPath()%>/images/info.gif" width="17" height="17"/>
															</a>
															<div id="addInfoPopover"  class="webui-popover-content">									 
															 <bean:write
															name="mailbagHistoryVO" property="additionalInfo" /> 
															</div>
											</logic:present>
											
											</logic:present>
										</logic:present>
										</td>
                                        <td><bean:write
												name="mailbagHistoryVO" property="scannedPort" />
										</td>
                                        <td><bean:write
													name="mailbagHistoryVO" property="carrierCode" />&nbsp; 
											<logic:present
													name="mailbagHistoryVO" property="flightDate">
											<bean:write name="mailbagHistoryVO"
													property="flightNumber" />&nbsp;
											<%=mailbagHistoryVO.getFlightDate()
													.toDisplayFormat("dd-MMM-yyyy")%>
											</logic:present>
											
										</td>
                                        <td>
											<logic:present
															name="mailbagHistoryVO" property="paBuiltFlag">
															<logic:equal name="mailbagHistoryVO"
																property="paBuiltFlag" value="Y">
																<bean:write name="mailbagHistoryVO"
																	property="containerNumber" />
																<logic:present name="mailbagHistoryVO" property="containerNumber">																	
																<common:message
																	key="mailtracking.defaults.mailbaghistory.lbl.sb" />
																</logic:present>
															</logic:equal>
															<logic:equal name="mailbagHistoryVO"
																property="paBuiltFlag" value="N">
																<bean:write name="mailbagHistoryVO"
																	property="containerNumber" />
															</logic:equal>
														</logic:present> <logic:notPresent name="mailbagHistoryVO"
															property="paBuiltFlag">
															<bean:write name="mailbagHistoryVO"
																property="containerNumber" />
														</logic:notPresent>
											</td>
                                        <td><bean:write
													name="mailbagHistoryVO" property="pou" /></td>
                                        <td><bean:write
													name="mailbagHistoryVO" property="scanUser" /></td>
										<td>
											<logic:present
												name="mailbagHistoryVO" property="screeningUser">
													<%="Screening Staff: "+mailbagHistoryVO.getScreeningUser()%>
											</logic:present>
											<logic:present
												name="mailbagHistoryVO" property="storageUnit">
												<br><%="Storage Unit: "+mailbagHistoryVO.getStorageUnit()%>
											</logic:present></td>

										<td><logic:present name="mailbagHistoryVO" property="mailSource">
										<bean:write
													name="mailbagHistoryVO" property="mailSource" />
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
						<div id="tabs-notes">
							<div class="card notes_Tbl" id="notesforcetabid" style="display:none">
                    <div class="card"  style="min-height:100%">
                </div>
                        <div class="card-body p-0" id="mbHis">
						</div>
						<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
						 <table id="mbHisTable" class="table table-x-md m-0" style="width:100%">
                                <thead>
                                    <tr>
									    <th>SL No</th>
										<th>Scan Date & Time</th>
                                        <th>Notes</th>
                                        <th>User Name</th>
                                    </tr>
                                </thead>
                                <tbody>
								<logic:present name="mailHistoryRemarksVOs">
								<% int count = 1; %>
										<logic:iterate id="mailHistoryRemarksVO" name="mailHistoryRemarksVOs"
											type="MailHistoryRemarksVO" indexId="rowCount">
                                    <tr>
									<td>
									<%=count++%>
									</td>
                                        <td><logic:present name="mailHistoryRemarksVO" property="remarkDate">
										<!--<bean:write
													name="mailHistoryRemarksVO" property="remarkDate" />-->
													<%=mailHistoryRemarksVO.getRemarkDate()
													.toDisplayFormat(
														"dd-MMM-yyyy HH:mm:ss")%>
										</logic:present>											
										</td>
										<td><logic:present name="mailHistoryRemarksVO" property="remark">
										<bean:write
													name="mailHistoryRemarksVO" property="remark" />
										</logic:present>											
										</td>
										<td><logic:present name="mailHistoryRemarksVO" property="userName">
										<bean:write
													name="mailHistoryRemarksVO" property="userName" />
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
                    </div>
                </div>
            </div>
        </section>
		 </div>
		 <%@ include file="/jsp/includes/reports/printFrame.jsp" %>
        <footer>
			<!--<button class="btn btn-primary" id="printButton" type="button" onClick="printCommand()">Print</button>-->
			<ihtml:nbutton  accesskey="P" id="btnPrint" property="btnPrint" styleClass="btn btn-primary"  
					componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_PRINT">
					<common:message key="mailtracking.defaults.mbHistory.btn.print" />
             </ihtml:nbutton>
			<ihtml:nbutton  accesskey="C" id="btnClose" property="btnClose" styleClass="btn btn-default"  
					componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_CLOSE">
					<common:message key="mailtracking.defaults.mbHistory.btn.close" />
             </ihtml:nbutton>
        </footer>
   
		</ihtml:form>
	</body>
</html:html>