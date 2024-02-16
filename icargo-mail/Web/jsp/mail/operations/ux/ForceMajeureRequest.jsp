<!DOCTYPE html>
<%@ include file="/jsp/includes/ux/tlds.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"%>
<%@ page import="java.util.Collection"%>

<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO"%>
<%@ page info="lite"%>


<html:html>

<head>

	<jsp:include page="/jsp/includes/tab_support.jsp" />
<title>.: iCargo Lite :. <common:message bundle="ForceMajeureResources"
		key="mail.operations.ux.forcemajeure.pagetitle" /></title>

	<meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/operations/ux/ForceMajeureRequest_Script.jsp"/>

</head>
<body>



	<bean:define id="form" name="ForceMajeureRequestForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm" toScope="page" />
	 
		 <business:sessionBean id="OneTimeValue"
	 	 moduleName="mail.operations"
		 screenID="mail.operations.ux.forcemajeure"
	 	  method="get"
		attribute="source" />
		<business:sessionBean id="scanTypeValues"
	 	 moduleName="mail.operations"
		 screenID="mail.operations.ux.forcemajeure"
	 	  method="get"
		attribute="scanType" />
		<business:sessionBean id="totalRecordsSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.forcemajeure" 
		 method="get" 
		 attribute="totalRecords" />
		 
		 <business:sessionBean id="filterparamSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.forcemajeure" 
		 method="get" 
		 attribute="filterParamValues" />
		 
		 <business:sessionBean id="ForceMajeureRequestVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.forcemajeure"
		 method="get"
		 attribute="Listforcemajeurevos"/>		

		<business:sessionBean id="ForceMajeureReqlistVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.forcemajeure"
		 method="get"
		 attribute="reqforcemajeurevos"/>
		<%
		ForceMajeureRequestForm frm = (ForceMajeureRequestForm) (request
					.getAttribute("ForceMajeureRequestForm"));
			String isNonEditable = "true";
		%>
	
	<ihtml:form action="/mail.operations.ux.forcemajeure.screenload.do">

		<ihtml:hidden name="form" property="lastPageNum" />
		<ihtml:hidden name="form" property="displayPage" />
		<ihtml:hidden name="form" property="actionFlag" />
		<ihtml:hidden name="form" property="displaytype" />
		<ihtml:hidden name="form" property="reqStatus" />
		<ihtml:hidden name="form" property="sortingField" />
		<ihtml:hidden name="form" property="sortOrder" />
		<input type="hidden" name="defaultPageSizeTemp" value="<%=frm.getDefaultPageSize() %>"></input>
		<div class="main-container footer-height-scroll">
			<header>
				<div id="tabs" class="tabs" style="display:block">
						
						<div class="tab-row">
							<ul class="nav nav-tabs">
							<li class="nav-item">
								<a class="nav-link open-new" id="newseltab" href="#tabs-new"><b><common:message key="mail.operations.ux.ForceMajeure.lbl.new" /></b></a>
							</li>
							<li class="nav-item">
								<a class="nav-link open-req" id="reqseltab" href="#tabs-req"><b><common:message key="mail.operations.ux.ForceMajeure.lbl.requested" /></b></a>
								</li>
						</ul>
						</div>		
					<div id="tabs-new">
						<div class="flippane animated fadeInDown" id="headerFormNew">
							<a href="#" class="flipper" flipper="headerDataNew"><i class="icon ico-close-fade"></i></a>
							<div class="pad-md pad-b-3xs">
							<div class="row">
				
								<div class="col-2">
                                    <div class="form-group">
                                        <label class="form-control-label"> <common:message key="mail.operations.ux.ForceMajeure.lbl.originAirport" scope="request" /></label>
                                        <div class="input-group">
                                           <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="origin_airport" id="origin_airport" styleClass="form-control" maxlength="4"   componentID="CMP_Mail_Operations_ForceMajeure_OriginAirpot"/>	
                                        </div>
                                    </div>
                                </div>
							
								<div class="col-2">
                                    <div class="form-group">
                                        <label class="form-control-label"> <common:message key="mail.operations.ux.ForceMajeure.lbl.destinationAirport" scope="request" /></label>
                                        <div class="input-group">
                                            <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"   property="destination" id="destination" styleClass="form-control" maxlength="4"  componentID="CMP_Mail_Operations_ForceMajeure_DestinationAirport" />
                                        </div>
                                    </div>
                                </div>
									
								 		
								
								<div class="col-2">
                                    <div class="form-group">
                                        <label class="form-control-label"> <common:message key="mail.operations.ux.ForceMajeure.lbl.affectedAirport" scope="request" /></label>
                                        <div class="input-group">
                                            <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="affectedAirport" id="affectedAirport" styleClass="form-control" maxlength="3" 	componentID="CMP_Mail_Operations_ForceMajeure_affectedAirport" />
                                        </div>
                                    </div>
                                </div>								
								
								<div class="col-2">
                                    <div class="form-group">
                                        <label class="form-control-label"> <common:message key="mail.operations.ux.ForceMajeure.lbl.paCode" scope="request" /></label>
                                        <div class="input-group">
                                           <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="pacode" id="pacode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_Operations_ForceMajeure_paCode"/>	
                                        </div>
                                    </div>
                                </div>
								
								<div class="col-2">
                                    <div class="form-group">
                                                <ibusiness:flightnumber id="flightNumber" tabindex="1" componentID="CMP_Mail_Operations_ForceMajeure_FlightNumber"
                                             
									carrierCodeProperty="carrierCode" flightCodeProperty="flightNumber" 
									carrierCodeMaxlength="3" flightCodeMaxlength="5"/>
                                        
                                        </div>
								</div>
								 <div class="col-2">
                                    <div class="form-group">
									<label class="form-control-label"> <common:message key="mail.operations.ux.ForceMajeure.lbl.flightdate" scope="request" /></label>
                                            <div class="input-group">
                                                <ibusiness:litecalendar 							labelStyleClass="form-control-label" tabindex="2"  id="flightDate" property="flightDateStr"
									componentID="CMP_Mail_Operations_ForceMajeure_FlightDate" />
                                            </div>

                                    </div>
                                </div>
							
								<div class="col-3">
                                    <div class="form-group">
                                       <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.scantype" /></label>
                                            <div class="input-group">
									   <ihtml:select property="scanType" multiSelect="true" multiSelectNoneSelectedText="SELECT" multiSelectMinWidth="135" multiple="multiple"
									componentID="CMP_Mail_Operations_ForceMajeure_ScanType"  style="width : 100px;" tabindex="2">
								 <logic:present name="scanTypeValues">
								  
								   <logic:iterate id="scanType" name="scanTypeValues">
								    
										
										 <bean:define id="fieldValue" name="scanType" property="fieldValue"/>
										  <bean:define id="fieldDescription" name="scanType" property="fieldDescription"/>
										    <ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
										   
										
									 </logic:iterate>
								 </logic:present>
								</ihtml:select>
                                            </div>
                                      
                                    </div>
                                </div>								
								<div class="col-3 col-md-4 col-sm-4">
                                    <div class="form-group">
                                <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.fromDate"/></label>
                                <div class="form-row">
                                    <div class="col-16">
                                            <div class="input-group">
                                            <ibusiness:litecalendar 
												id="frmDate" 
												property="frmDate" 
												componentID="CMP_Mail_Operations_ForceMajeure_FromDate" />
                                            </div>
                                        
                                    </div>
                                    <div class="col-8">
                                        <ibusiness:releasetimer id="frmTime" property="frmTime" type="asTimeComponent" componentID="CMP_Mail_Operations_ForceMajeure_TIME" styleClass="form-control" />
                                </div>								
                                </div>
                            </div>
                        </div>							
								<div class="col-3 col-md-4 col-sm-4">
                            <div class="form-group">
                                <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.toDate"/></label>
                                <div class="form-row">
                                    <div class="col-16">
                                        <div class="input-group">
                                            <ibusiness:litecalendar 
												id="toDate" 
												property="toDate" 
												componentID="CMP_Mail_Operations_ForceMajeure_Todate" />
                                        </div>
                                    </div>
                                    <div class="col-8">
                                        <ibusiness:releasetimer id="toTime" property="toTime" type="asTimeComponent" componentID="CMP_Mail_Operations_ForceMajeure_TIME" styleClass="form-control" />
                                    </div>
                                </div>
                            </div>
                        </div>
															
								
																
							</div>
							<div class="hidden" id="morelessPane">
						<div class="row">
								<div class="col-2">
                                    <div class="form-group">
                                       <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.source" /></label>
									   
									   <ihtml:select tabindex="2" property="source" styleClass="form-control" componentID="CMP_Mail_Operations_ForceMajeure_source">
										<logic:present name="OneTimeValue">
											<logic:iterate id="source" name="OneTimeValue">
												<bean:define id="fieldValue" name="source" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="source" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present>
										</ihtml:select>
                                    </div>
                                </div>								
								
								<div class="col-2">
                                    <div class="form-group">
                                        <label class="form-control-label"> <common:message	key="mail.operations.ux.ForceMajeure.lbl.viaPoint" scope="request" /></label>
                                        <div class="input-group">
                                            <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"  property="viaPoint"  id="viaPoint" styleClass="form-control" maxlength="4"  componentID="CMP_Mail_Operations_ForceMajeure_ViaPoint" />
							</div>
                                    </div>
                                </div>	
						</div>
						</div>
						</div>

							<div class="btn-row">
							<a class="more-less" href="#">
                        <label style="display: inline-block;"><i class="icon ico-orange-right down-arow"></i>More Filters</label>
                        <label class="hidden" style="display: none;"><i class="icon ico-orange-right up-arow"></i>Less Filters</label>
                    </a>
                            <ihtml:nbutton tabindex="2" styleClass="btn btn-primary flipper" flipper="headerDataNew" property="btnNewList" id="btnNewList" componentID="BUT_Mail_Operations_ForceMajeure_List" accesskey="L">
							<common:message key="mail.operations.ux.forcemajeure.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton tabindex="2" styleClass="btn btn-default" property="btnNewClear" id="btnNewClear" componentID="BUT_Mail_Operations_ForceMajeure_Clear" accesskey="C">
							<common:message key="mail.operations.ux.forcemajeure.btn.clear" />
							</ihtml:nbutton>
						</div>						
					</div>
				</div>
				
				<div id="tabs-req">
                    <div class="flippane animated fadeInDown" id="headerFormReq">
                        <a href="#" class="flipper" flipper="headerDataReq"><i class="icon ico-close-fade"></i></a>
                        <div class="pad-md">
                            <!--For Requested-->
                            <div class="row">
                                <div class="col-5">
                                    <div class="form-group">
                                        <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.forcemajId" scope="request" /></label>
                                        <div class="input-group">
                                            <ihtml:text tabindex="2" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label"  property="forceid"  id="forceid" styleClass="form-control" maxlength="25"  componentID="CMP_Mail_Operations_ForceMajeure_Forceid" />
										
                                        </div>
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <div class="mar-t-md">
									<ihtml:nbutton tabindex="2" styleClass="btn btn-primary flipper" flipper="headerDataReq" property="btnReqList" id="btnReqList" componentID="BUT_Mail_Operations_ForceMajeure_List" accesskey="L">
									<common:message key="mail.operations.ux.forcemajeure.btn.list" /></ihtml:nbutton>
                                     <ihtml:nbutton tabindex="2" styleClass="btn btn-default" property="btnreqClear" id="btnreqClear" componentID="BUT_Mail_Operations_ForceMajeure_Clear" accesskey="C">
							<common:message key="mail.operations.ux.forcemajeure.btn.clear" />
							</ihtml:nbutton>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
				
				</div>
						
			 <div class="flippane animated fadeInDown" id="headerDataNew" style="display: none;">

                <div class="pad-md">
                    <div class="row">
							<logic:present name="filterparamSession">
						<bean:define id="forceMajeureRequestFilterVO" name="filterparamSession" toScope="page" />
						<logic:notEqual name="forceMajeureRequestFilterVO" property="orginAirport" value="">
					    <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.originAirport" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="orginAirport"/> </div>
                        </div>
						</logic:notEqual>
						<logic:notEqual name="forceMajeureRequestFilterVO" property="destinationAirport" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.destinationAirport" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="destinationAirport"/></div>
                        </div>
						</logic:notEqual>
						<logic:notEqual  name="forceMajeureRequestFilterVO" property="viaPoint" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.viaPoint" scope="request" /></label></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="viaPoint"/></div>
                        </div>
						</logic:notEqual>
						<logic:notEqual  name="forceMajeureRequestFilterVO" property="affectedAirport" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.affectedAirport" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="affectedAirport"/></div>
                        </div>
						</logic:notEqual>
						<logic:notEqual name="forceMajeureRequestFilterVO" property="poaCode" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.paCode" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="poaCode"/></div>
                        </div>
						</logic:notEqual>
						<logic:notEqual name="forceMajeureRequestFilterVO" property="flightNumber" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.flightNo" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="flightNumber"/></div>
                        </div>
						</logic:notEqual>
						<logic:notEqual name="forceMajeureRequestFilterVO" property="flightDate" value="">
                        <div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.flightdate" scope="request" /></label>
                            <div class="form-control-data">
							<logic:present name="forceMajeureRequestFilterVO" property="flightDate">
							<bean:define id="flightDate" name="forceMajeureRequestFilterVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
							<%String fltDate=TimeConvertor.toStringFormat(flightDate.toCalendar(),"dd-MM-yy");%>
                             <%=fltDate%>
							 </logic:present>
							</div>
                        </div>
						</logic:notEqual>
                        <div class="col-3">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.fromDate" scope="request" /></label>
                            <div class="form-control-data">
							<logic:present name="forceMajeureRequestFilterVO" property="fromDate">
							<bean:define id="fromDate" name="forceMajeureRequestFilterVO" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
							<%String frmDate=TimeConvertor.toStringFormat(fromDate.toCalendar(),"dd-MM-yy  hh:mm");%>
                             <%=frmDate%>
							  </logic:present>
							</div>
                        </div>
                        <div class="col-3">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.toDate" scope="request" /></label>
                            <div class="form-control-data">
							<logic:present name="forceMajeureRequestFilterVO" property="toDate">
							<bean:define id="toDate" name="forceMajeureRequestFilterVO" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
							<%String toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),"dd-MM-yy  hh:mm");%>
                             <%=toDat%>
							 </logic:present>
							</div>
                        </div>
						<div class="col-2">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.source" scope="request" /></label>
							<% String sourcetype = ""; %>
							<logic:present name="forceMajeureRequestFilterVO" property="source">
							<bean:define id="Source" name="forceMajeureRequestFilterVO" property="source" toScope="page"/>
							<logic:iterate id="source" name="OneTimeValue">
								<logic:equal name="source" property="fieldValue" value="<%=(String)Source %>">
								<bean:define id="fieldDescription" name="source" property="fieldDescription"/>
								<% sourcetype = String.valueOf(fieldDescription); %>
								</logic:equal>
								</logic:iterate>
						</logic:present>
                            <div class="form-control-data">							
							<%=sourcetype%>
							</div>
                        </div>
						<logic:notEqual name="forceMajeureRequestFilterVO" property="scanTypeDetail" value="">
					    <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.scantype" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="scanTypeDetail"/> </div>
                        </div>
						</logic:notEqual>
						
						</logic:present>
                    </div>
                </div>	
						<span class="d-block w-10 position-relative mar-l-xs">
                            <i class="icon ico-pencil-rounded-orange mar-t-xs flip-to-one" id="edittab"></i>
                         </span>
			</div>
			<!-- Display Mode for Requested -->
            <div class="flippane animated fadeInDown" id="headerDataReq" style="display: none;">
                <div class="pad-md">
				    <div class="row">
					<logic:present name="filterparamSession">
						<bean:define id="forceMajeureRequestFilterVO" name="filterparamSession" toScope="page" />
                        <div class="col-5 border-right">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.forcemajId" scope="request" /></label>
                            <div class="form-control-data">
							<bean:write name="forceMajeureRequestFilterVO" property="forceMajeureID"/>
							</div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.reqRemarks" scope="request" /></label>
                            <div class="form-control-data"><bean:write name="forceMajeureRequestFilterVO" property="reqRemarks"/></div>
                        </div>
						</logic:present>
                    </div>
                </div>			
							<span class="d-block w-10 position-relative mar-l-xs">
                            <i class="icon ico-pencil-rounded-orange mar-t-xs flip-to-one" id="editreqtab"></i>
							</span>
            </div>
			</header>
			<section class="mar-y-md">
			<div class="card new_Tbl"id="newforcetabid">
                <div class="card-header card-header-action">
                    <div class="col">
					 <h4><label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.mailbag.details" /></label> </h4>
					 </div>
					 <logic:present  name="ForceMajeureRequestVOs">
					   <div class="mega-pagination">
									<common:enhancedPaginationTag
									pageURL='javascript:submitPage("lastPageNum","displayPage")'
							        name="ForceMajeureRequestVOs" id="ForceMajeureRequestVOs"
									exportToExcel="true" 
									exportTableId="newForceTable" 
									exportAction="mail.operations.ux.forcemajeure.list.do" 
							        lastPageNum="<%=frm.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=frm.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesforlisting" pageNumberFormAttribute="displayPage"/>
                       
     
                    </div>
					</logic:present>
					<!-- Sorting Section-->
					<div class="card-header-icons">
                        <a href="#" alt="Settings" class="open-sorting">
                                <i class="icon ico-to-from rotate-90 align-middle mar-x-xs"></i></a>
                           <jsp:include page="ForceMajeureRequestNewSort_UX.jsp"/>	

                    </div>
					<!-- Sorting Section-->
				</div>	
				<!--List mail details --> 
				<div class="card-body p-0" >
                    <div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="newForceTable" class="table table-x-md m-0" style="width:100%;">
                            <thead>
                                <tr>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.mailid"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.Airport"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.flightno"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.flightDate"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.type"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.wt"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.org"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.dest"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.consignno"/></th>
                                    <!--<th><common:message  key="mail.operations.ux.ForceMajeure.lbl.userid"/></th>-->
                                </tr>
                            </thead>
                            <tbody>
							<logic:present name="ForceMajeureRequestVOs">
							<logic:iterate id ="ForceMajeureRequestVO" name="ForceMajeureRequestVOs" type="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO">
								<tr>
									<td class="text-left "><bean:write name="ForceMajeureRequestVO" property="mailID"/></td>
                                    <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="airportCode"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="carrierCode"/> <bean:write name="ForceMajeureRequestVO" property="flightNumber"/></td>
                                   <td class="text-left ">
										<logic:present name="ForceMajeureRequestVO" property="flightDate">
											<bean:define id="flightDate" name="ForceMajeureRequestVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
											<%String fltDate=TimeConvertor.toStringFormat(flightDate.toCalendar(),"dd-MM-yyyy");%>
											 <%=fltDate%>
											 </logic:present>
									</td>
                                    <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="type"/></td>
                                  <td class="text-left "><common:write name="ForceMajeureRequestVO" property="weight" unitFormatting="true" /></td>
                                   <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="originAirport"/></td>
                                    <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="destinationAirport"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureRequestVO" property="consignmentDocNumber"/></td>
                                    <!--<td class="text-left "><bean:write name="ForceMajeureRequestVO" property="lastUpdatedUser"/></td>-->
                                </tr>
								</logic:iterate>
								</logic:present>
							</tbody>	
						</table>
					</div>
					
					<div class="pad-md  border-top">
                        <div class="row">
                            <div class="col-9 col-sm-12 col-md-9 mr-auto">
                                <div class="form-group">
                                    <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.remarks" scope="request" /></label>&nbsp;<span class="text-red d-inline-block">*</span>
                                    <ihtml:textarea componentID="CMP_Mail_Operations_ForceMajeure_newTabRemarks" labelStyleClass="form-control-label" property="newTabRemarks"> </ihtml:textarea>
                                </div>
                            </div>
							<div class="col-4">
                                <div class="form-group">
                                    <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.userid" scope="request" /></label>
                                   <!-- <ihtml:textarea componentID="CMP_Mail_Operations_ForceMajeure_newTabRemarks" labelStyleClass="form-control-label" property="userId" value="" disabled="true"> </ihtml:textarea>-->
								   <ihtml:text id="userId"  property="userId" name="form"
							                                         styleClass="form-control"  componentID="CMP_Mail_Operations_ForceMajeure_USERID" value="" disabled="true"/>
                        </div>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
			<!--Request List table -->
			<div class="card req_Tbl" id="reqforcetabid" style="display:none">
                <div class="card-header card-header-action">
                    <div class="col">
                        <h4><label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.mailbag.details" /></label> </h4>
                    </div>
					<div class="card-header-btns" id="openDel" style="display:none">
                     <ihtml:nbutton styleClass="btn btn-default"  property="btndeleteall" id="btndeleteall" componentID="BUT_Mail_Operations_ForceMajeure_deleteAll" accesskey="C"><common:message key="mail.operations.ux.ForceMajeure.lbl.btn.delete" /></ihtml:nbutton>
                    </div>
					 <logic:present  name="ForceMajeureReqlistVOs">
					   <div class="mega-pagination">
									<common:enhancedPaginationTag
									pageURL='javascript:submitPage("lastPageNum","displayPage")'
							        name="ForceMajeureReqlistVOs" id="ForceMajeureReqlistVOs"
							        lastPageNum="<%=frm.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=frm.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesforreqlisting" pageNumberFormAttribute="displayPage"
									exportToExcel="true"
									exportTableId="reqForceTable"
									exportAction="mail.operations.ux.forcemajeure.reqlist.do"/>
						</div>
					</logic:present>
					<div class="card-header-icons">
					 <jsp:include page="ForceMajeureAdditionalFilter.jsp"/>	
					</div>
					<div class="card-header-icons">
                        <a href="#" alt="Settings"  class="open-sorting2">
                            <i class="icon ico-to-from rotate-90 align-middle mar-x-xs"></i></a>
                            <jsp:include page="ForceMajeureRequestReqSort_UX.jsp"/>	          
                    </div>
                </div>
				<div class="card-body p-0">
                    <div class="w-100">
                        <table id="reqForceTable" class="table mb-0" style="width:100%" >
                            <thead>
                                <tr>
								<logic:notEqual name="form" property="actiontype" value="exportexcel">
                                    <th class="text-center check-box-cell">
									<input type="checkbox" name="checkall" onclick="updateHeaderCheckBox(this.form,this,this.form.checkSel);enabledeletebtn();">
                                    </th>
									</logic:notEqual>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.mailid"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.Airport"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.flightno"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.flightDate"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.type"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.wt"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.org"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.dest"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.consignno"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.forcemajId"/></th>
                                    <th><common:message  key="mail.operations.ux.ForceMajeure.lbl.status"/></th>
                                    <!--<th><common:message  key="mail.operations.ux.ForceMajeure.lbl.userid"/></th>-->
									<logic:notEqual name="form" property="actiontype" value="exportexcel">
                                     <th width="35"></th>
									 </logic:notEqual>
                                </tr>
                            </thead>
                            <tbody>
							<logic:present name="ForceMajeureReqlistVOs">
							<logic:iterate id ="ForceMajeureReqlistVOs" name="ForceMajeureReqlistVOs" type="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO" indexId="rowCount">
                                <tr>
								<logic:notEqual name="form" property="actiontype" value="exportexcel">
                                    <td class="text-center ">
									&nbsp;&nbsp; <!--<ihtml:checkbox id="<%=String.valueOf(rowCount)%>" onclick="checkOnEnableDelbtn()" property="checkSel" value="<%=String.valueOf(rowCount)%>"/>-->
									<input type="checkbox" name="checkSel" value="<%=String.valueOf(rowCount)%>" indexId="rowCount" onclick="checkOnEnableDelbtn()"/>
								</td>
								</logic:notEqual>
                                    <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="mailID"/></td>
                                    <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="airportCode"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="carrierCode"/> <bean:write name="ForceMajeureReqlistVOs" property="flightNumber"/></td>
                                   <td class="text-left ">
										<logic:present name="ForceMajeureReqlistVOs" property="flightDate">
											<bean:define id="flightDate" name="ForceMajeureReqlistVOs" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
											<%String fltDate=TimeConvertor.toStringFormat(flightDate.toCalendar(),"dd-MM-yyyy");%>
											 <%=fltDate%>
											 </logic:present>
									</td>
                                    <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="type"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="weight" unitFormatting="true"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="originAirport"/></td>
                                    <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="destinationAirport"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="consignmentDocNumber"/></td>
                                   <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="forceMajuereID"/></td>
                                    <td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="status"/></td>
                                   <!--<td class="text-left "><bean:write name="ForceMajeureReqlistVOs" property="lastUpdatedUser"/></td>-->
								   <logic:notEqual name="form" property="actiontype" value="exportexcel">
                                   <td class="text-center">
										 <a  id="delete<%=rowCount%>" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
										 </td>
									</logic:notEqual>
                                </tr>
								</logic:iterate>
								</logic:present>
							</tbody>
                        </table>
                    </div>	
					<div class="pad-md  border-top">
                        <div class="row">
                            <div class="col-9 col-sm-12 col-md-9 mr-auto">
                                <div class="form-group">
                                    <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.remarks" scope="request" /></label>
                                  <ihtml:textarea labelStyleClass="form-control-label" property="reqTabRemarks"> </ihtml:textarea>
                                </div>
                            </div>
							<div class="col-4">
                                <div class="form-group">
                                    <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.userid" scope="request" /></label>
                                      <ihtml:text id="userId"  property="userId" name="form"
							                                         styleClass="form-control"  componentID="CMP_Mail_Operations_ForceMajeure_USERID" disabled="true"/>
									<!--<ihtml:textarea componentID="CMP_Mail_Operations_ForceMajeure_newTabRemarks" labelStyleClass="form-control-label" property="userId"  disabled="true"> </ihtml:textarea>-->
                        </div>
                            </div>
                        </div>
                    </div>
				</div>
			
			</section>
		</div>
						<footer id="newMajeure">
							<ihtml:nbutton styleClass="btn btn-primary flipper" property="btnsave" disabled="true" id="btnsave" componentID="CMP_Mail_Operations_forcemajeure_Save" accesskey="S">
							<common:message key="mail.operations.ux.forcemajeure.btn.RequestMajeure" />
							</ihtml:nbutton>
							
							<ihtml:nbutton styleClass="btn btn-default" property="btnClose" id="btnNewClose" componentID="BUT_Mail_Operations_ForceMajeure_CLOSE" accesskey="C">
							<common:message key="mail.operations.ux.forcemajeure.btn.close" />
							</ihtml:nbutton>
						</footer>
						
						<footer id="reqMajeure" style="display:none">
						<ihtml:nbutton styleClass="btn btn-primary" id="btnFileUpload"
						property="btnFileUpload"
						componentID="BUT_Mail_Operations_ForceMajeure_FileUpload"
						accesskey="U">
						<common:message
							key="mail.operations.ux.forcemajeure.btn.fileupload" />
						</ihtml:nbutton>						
						<ihtml:nbutton styleClass="btn btn-primary" id="btnaccept"
						property="btnaccept"
						componentID="BUT_Mail_Operations_ForceMajeure_accept"
						accesskey="A">
						<common:message
							key="mail.operations.ux.forcemajeure.btn.accept" />
						</ihtml:nbutton>
						<ihtml:nbutton styleClass="btn btn-primary" id="btnReject"
						property="btnReject"
						componentID="BUT_Mail_Operations_ForceMajeure_reject"
						accesskey="R">
						<common:message
							key="mail.operations.ux.forcemajeure.btn.reject" />
							</ihtml:nbutton>
						<ihtml:nbutton styleClass="btn btn-default" id="btnPrint"
						property="btnPrint"
						componentID="BUT_Mail_Operations_ForceMajeure_print"
						accesskey="P">
						<common:message
							key="mail.operations.ux.forcemajeure.btn.print" />
							</ihtml:nbutton>
							<ihtml:nbutton styleClass="btn btn-default" id="btnReqClose"
						property="btnClose"
						componentID="BUT_Mail_Operations_ForceMajeure_CLOSE"
						accesskey="C">
						<common:message
							key="mail.operations.ux.forcemajeure.btn.close" />
						</ihtml:nbutton>

        </footer>
		
			
	</ihtml:form>
	 <script>
		jquery('.open-sorting').webuiPopover({
            url: '.show-sorting',
            placement: 'auto',
            closeable: true,
            title: "Sort By",
            width:'200',
            padding: false
        });
		jquery('.open-sorting2').webuiPopover({
            url: '.show-sorting2',
            placement: 'auto',
            closeable: true,
            title: "Sort By",
            width: '200',
            padding: false
        });
		jquery('.open-filter').webuiPopover({
			container: document.getElementById('filterContainer'),
            url: '.show-filter',
            placement: 'auto',
            closeable: true,
            title: "Filter",
            width: '500',
            padding: false
        });
    </script>
</body>

			

		

	

		
</html:html>