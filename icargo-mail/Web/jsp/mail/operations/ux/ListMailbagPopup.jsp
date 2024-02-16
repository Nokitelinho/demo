<!--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name     :  Mail
* File Name     	 :  ListMailbagPopup.jsp
* Date          	 :  26-October-2018
* Author(s)     	 :  A-7929

*************************************************************************/
 -->

<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ListMailbagPopupForm" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO"%>
<%@ page import = "java.text.DecimalFormat" %>
	

<html:html>

<head>

  
		

   <title>.:iCargo Lite:.
       <common:message bundle="listMailbagPopupResources" key="mail.operations.ux.listMailbagPopup.title" />
   </title>
    <meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/operations/ux/ListMailbagPopup_Script.jsp" />

</head>

<body>

         <logic:present name="popup">
			<div id="walkthroughholder"/>
		 </logic:present>

      <bean:define id="form"
	  name="ListMailbagPopupForm"
	  type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ListMailbagPopupForm"
	  toScope="page" />	
	  
	  <business:sessionBean id="cardit_vo" 
	  moduleName="mail.operations" 
	  screenID="mail.operations.ux.listmailbagpopup" 
	  method="get" 
	  attribute="carditMailbagVOsCollection" />
	  
	  <business:sessionBean id="lyinglist_vo" 
	  moduleName="mail.operations" 
	  screenID="mail.operations.ux.listmailbagpopup" 
	  method="get" 
	  attribute="lyingMailbagVOs" />
	  
	  <business:sessionBean id="oneTimeValues" 
	  moduleName="mail.operations" 
	  screenID="mail.operations.ux.listmailbagpopup" 
	  method="get" 
	  attribute="oneTimeVOs" />
	  <%DecimalFormat df = new DecimalFormat("0.00");
 df.setDecimalSeparatorAlwaysShown(true);
 df.setMinimumFractionDigits(2);%>	
  
        <ihtml:form action="/mail.operations.ux.listmailpopup.screenLoad.do">
		
	

		<ihtml:hidden name="form" property="lastPageNum" />
		<ihtml:hidden name="form" property="displayPage" />
		<ihtml:hidden name="form" property="displayPageForLyingList" />
		<ihtml:hidden name="form" property="lastPageNumForLylingList" />
		<ihtml:hidden name="form" property="paginationFlag" />
		<ihtml:hidden name="form" property="filterFlag" />
		<ihtml:hidden name="form" property="okForScreenClose" />
		<ihtml:hidden name="form" property="refreshParent" />
		
		
  
 
  <div class="cardit" title="List Cardit">
        <header></header>
        <section>
            <div id="tabs" class="tabs" style="display:block">
                <div class="thead-bg">
                    <ul class="nav nav-tabs nav-tabs-large">
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#cardit" ><common:message key="mail.operations.ux.listMailbagPopup.lbl.cardit"/></a>
                        </li>
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#lyingList"  ><common:message key="mail.operations.ux.listMailbagPopup.lbl.lyinglist"/></a>
                        </li>
                    </ul>
                </div>
                <div class="tabs-container">
                    <div id="cardit">
                        <div class="card border-0" >
                            <div class="card-header card-header-action">
                                <div class="col">
                                    <h4><common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagdetails"/></h4>
                                </div>
								

								
                               <logic:present name="cardit_vo" >
							    <div class="mega-pagination">
                                   <common:enhancedPaginationTag
									pageURL='javascript:submitPage("lastPageNum","displayPage")'
							        name="cardit_vo" id="cardit_vo"
							        lastPageNum="<%=form.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=form.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
							      />

                                </div>
								   </logic:present>
								   
                                <div class="card-header-icons">
                                    <a href="#" class="btn-icon popover-text filter-sec" data-popover-text="Filter" ><i class="icon ico-filter"></i></a>
                                    
                                </div>
							
								
								
								
								
                                <div style="display:none;" class="filter-section" >
                                  
                                    <div class="w-100 pad-md border-bottom pad-b-2xs border-top">
                                        <div class="w-100">
                                            <div class="row">
                                                <div class="col-8">
                                                    <div class="form-group">
                                                        <label class="form-control-label ">
														<common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagid"/>
														</label>
														     
														       <div> 
                                                                     <ihtml:text id="mailbagId"  property="mailbagId" name="form"
							                                         styleClass="form-control" maxlength="29" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_MAILBAGID" />
													           </div>
                                                        
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.origin"/></label>
                                                        <div>
                                                                     <ihtml:text id="ooe"  property="ooe" name="form"
							                                         styleClass="form-control" maxlength="7" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_OOE" />
														</div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.dest"/></label>
                                                        <div>
                                                                     <ihtml:text id="doe"  property="doe" name="form"
							                                         styleClass="form-control" maxlength="7" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DOE" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label ">
														<common:message key="mail.operations.ux.listMailbagPopup.lbl.category"/>
														</label>
                                                       <div>
                                                                     <ihtml:text id="mailCategoryCode"  property="mailCategoryCode" name="form"
							                                         styleClass="form-control" maxlength="1" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_CATEGORY" />
														</div>
                                                    </div>
                                                </div>



                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.subclass"/></label>
                                                       <div>
                                                                     <ihtml:text id="mailSubclass"  property="mailSubclass" name="form"
							                                         styleClass="form-control" maxlength="2" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_SUBCLASS" />
														 </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="row">
                                                <div class="col-2">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.year"/></label>
                                                   
                                                       <div>
                                                                     <ihtml:text id="year"  property="year" name="form"
							                                         styleClass="form-control" maxlength="1" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_YEAR" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.dsn"/></label>
                                                       
                                                       <div>
                                                                     <ihtml:text id="despatchSerialNumber"  property="despatchSerialNumber" name="form"
							                                         styleClass="form-control" maxlength="4" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DSN" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.rsn"/></label>
                                                      
                                                       <div>
                                                                     <ihtml:text id="receptacleSerialNumber"  property="receptacleSerialNumber" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_RSN" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.consignmentno"/></label>
                                                      
                                                       <div>
                                                                     <ihtml:text id="consignmentNumber"  property="consignmentNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_CONSIGNMENTNO" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.fromdate"  styleClass="form-control" 
									                labelStyleClass="form-control-label" id="fromDate" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FROMDATE" 
									                property="fromDate"/>
                                                            
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.todate" styleClass="form-control" 
									                labelStyleClass="form-control-label" id="toDate" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_TODATE" 
									                property="toDate"/>
													
                                                       
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.pacode"/></label>
														 <div>
                                                                     <ihtml:text id="paCode"  property="paCode" name="form"
							                                         styleClass="form-control" maxlength="5" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_PACODE" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-8">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightnumber"/></label>
                                                        
                                                           
                                                              <div>
                                                                     <ihtml:text id="flightNumber"  property="flightNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FLIGHTNUMBER" />
														      </div>
                                                            
                                                       
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.flightdate" styleClass="form-control" 
									                labelStyleClass="form-control-label" id="flightDate" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FLIGHTDATE" 
									                property="flightDate"/>
													
													
                                                     
                                                       
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.upliftairport"/></label>
                                                       <div>
                                                                     <ihtml:text id="upliftAirport"  property="upliftAirport" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_UPLIFTAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.uldnumber"/></label>
                                                      <div>
                                                                     <ihtml:text id="uldNumber"  property="uldNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_ULDNUMBER" />
														 </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.originairport"/></label>
                                                      <div>
                                                                     <ihtml:text id="originAirportCode"  property="originAirportCode" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_ORIGINAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.destairport"/></label>
                                                      <div>
                                                                     <ihtml:text id="destinationAirportCode"  property="destinationAirportCode" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DESTAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "> <common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.status"/></label>
                                                        <ihtml:select styleClass="form-control" id="status"  componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_STATUS" property="status">
														 
														 	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="oneTimeValues">
														<logic:iterate id="oneTimeValue" name="oneTimeValues">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<logic:equal name="parameterCode" value="mailtracking.defaults.mailstatus">
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue" property="fieldValue">

															<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

															<logic:equal name="parameterValue" property="fieldValue"  value="CAP">
															<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
															</ihtml:option>
															</logic:equal>

															<logic:equal name="parameterValue" property="fieldValue"  value="ACP">
															<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
															</ihtml:option>
															</logic:equal>

														</logic:present>
														</logic:iterate>
														</logic:equal>
														</logic:iterate>
													    </logic:present>
															
														</ihtml:select>
														
														
                                                    </div>
                                                </div>




                                            </div>
                                        </div>
										
										
										 <div class="w-100">
                                        <div class="btn-row border-0 pad-t-sm">
                                           
											 <ihtml:nbutton styleClass="btn btn-primary" id="btOK" property="btOK" accesskey ="O" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNOK" >
			                                 <common:message key="mail.operations.ux.listMailbagPopup.btn.ok" />
		                                     </ihtml:nbutton>
           
			                                <ihtml:nbutton styleClass="btn btn-default" id="btnClearFilter" property="btnClearFilter" accesskey ="C" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNCLEAR" >
			                                <common:message key="mail.operations.ux.listMailbagPopup.btn.clear" />
		                                    </ihtml:nbutton>
                                        </div>
                                      </div>
										
                                    </div>
                                   
                                </div>
                            </div>

                            <div class="dataTableContainer card-body p-0 table-responsive" id="dataTableContainer">
                                <table class="table table-bordered table-x-md border-bottom mb-0 table-hover" id="cardittable">
                                    <thead>
                                        <tr>
                                            <th width="35"><input type="checkbox" name="allCarditChecked" onclick="updateHeaderCheckBox(this.form,this,this.form.carditRowId)"></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagid"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.origin"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.destination"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.category"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.subclass"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.year"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.dsn"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.rsn"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.hni"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.ri"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightnumber"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightdate"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.weight"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.consignmentno"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.consigntdate"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.rdt"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.uldnumber"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.acptedstatus"/></th>
                                            <th></th>
                                        </tr>
                                    </thead>
									
                                    <tbody>
									
									<logic:present name="cardit_vo">
									 <logic:iterate id="cardit_vo" name="cardit_vo" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO" indexId="indexId">
                                        <tr>
										    <td>
																																																					<input type="checkbox" name="carditRowId"   value="<%=String.valueOf(indexId)%>" />  														
                                            </td>                                                                      
                                            <td>
											<logic:present name="cardit_vo" property="mailbagId">
											<bean:write name="cardit_vo" property="mailbagId" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="ooe">
											<bean:write name="cardit_vo" property="ooe" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="doe">
											<bean:write name="cardit_vo" property="doe" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="mailCategoryCode">
											<bean:write name="cardit_vo" property="mailCategoryCode" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="mailSubclass">
											<bean:write name="cardit_vo" property="mailSubclass" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="year">
											<bean:write name="cardit_vo" property="year" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="despatchSerialNumber">
											<bean:write name="cardit_vo" property="despatchSerialNumber" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="receptacleSerialNumber">
											<bean:write name="cardit_vo" property="receptacleSerialNumber" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="highestNumberedReceptacle">
											<bean:write name="cardit_vo" property="highestNumberedReceptacle" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="registeredOrInsuredIndicator">
											<bean:write name="cardit_vo" property="registeredOrInsuredIndicator" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="flightNumber">
											<bean:write name="cardit_vo" property="flightNumber" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
																																							                    <logic:present name="cardit_vo" property="flightDate">
					                         <bean:define id="flightDate" name="cardit_vo" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                                            <%String fliDate=TimeConvertor.toStringFormat(flightDate.toCalendar(),"dd-MM-yy");%>
                                            <%=fliDate%>
								            </logic:present>
																	
                                 
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="weight">
												<%
												double weightValue = 0.0;
												weightValue = cardit_vo.getWeight().getDisplayValue();
												%>
													<%=df.format(weightValue)%>                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="consignmentNumber">
											<bean:write name="cardit_vo" property="consignmentNumber" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="consignmentDate">
											
					                         <bean:define id="consignmentDate" name="cardit_vo" property="consignmentDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                                             <%String consdate=TimeConvertor.toStringFormat(consignmentDate.toCalendar(),"dd-MM-yy");%>
                                             <%=consdate%>
                                                                                                                                                                                </logic:present>
																																						                        </td>
                                            <td>
											<logic:present name="cardit_vo" property="reqDeliveryTime">
											<%=cardit_vo.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm")%>
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
											<logic:present name="cardit_vo" property="uldNumber">
											<bean:write name="cardit_vo" property="uldNumber" />
                                                                                                                                                                                 </logic:present>
											</td>
                                            <td>
                                                                                                                                                                                 <logic:present name="cardit_vo" property="accepted">
											<logic:equal name="cardit_vo" property="accepted" value="Y" >
													<img id="isAccepted" src="<%=request.getContextPath()%>/images/icon_on.gif" />
											</logic:equal>
											<logic:equal name="cardit_vo" property="accepted" value="N">
													<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
											 </logic:equal>
										  </logic:present>
										   <logic:notPresent name="cardit_vo" property="accepted">
												<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
										  </logic:notPresent>
										  
											</td>
                                            <td><a href="#"><i class="icon ico-pencil"></i></a></td>
                                        </tr>
										</logic:iterate>
                                       </logic:present>                                                                         
                                    </tbody>
								 
                                </table>
                            </div>
                        </div>
                    </div>
                    <div id="lyingList">
                       <div class="card border-0" > 
						<div class="card-header card-header-action">
                                <div class="col">
                                    <h4><common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagdetails"/></h4>
                                </div>
                                <div class="card-header-btns openDel" style="display:none">
                                    <button class="btn btn-default ">Delete</button>
                                </div>
							  <logic:present name="lyinglist_vo" >	
                                <div class="mega-pagination">
								<bean:define id="lastPageNumForLylingList" name="form" property="lastPageNumForLylingList" />
								
                                    <common:enhancedPaginationTag
									
							        name="lyinglist_vo" id="lyinglist_vo"
							        lastPageNum="<%=(String)lastPageNumForLylingList %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=form.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
									pageURL='javascript:submitPageForLyingList("lastPageNum","displayPage")'
							      />

                                </div>
								  </logic:present>	
								
                                <div class="card-header-icons">
                                    <a href="#" class="btn-icon popover-text filter-secly" data-popover-text="Filter"> <i class="icon ico-filter"></i></a>
                           
                                </div>
								
						  	
                                
								
								<div style="display:none; " class="filter-sectionly" >
                                    
                                    <div class="w-100 pad-md border-bottom pad-b-2xs border-top">
                                        <div class="w-100">
                                            <div class="row">
                                                <div class="col-8">
                                                    <div class="form-group">
                                                        <label class="form-control-label ">
														<common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagid"/>
														</label>
														     
														       <div> 
                                                                     <ihtml:text id="mailbagIdLy"  property="mailbagId" name="form"
							                                         styleClass="form-control" maxlength="29" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_MAILBAGID" />
													           </div>
                                                        
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.origin"/></label>
                                                        <div>
                                                                     <ihtml:text id="ooeLy"  property="ooe" name="form"
							                                         styleClass="form-control" maxlength="7" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_OOE" />
														</div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.dest"/></label>
                                                        <div>
                                                                     <ihtml:text id="doeLy"  property="doe" name="form"
							                                         styleClass="form-control" maxlength="7" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DOE" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label ">
														<common:message key="mail.operations.ux.listMailbagPopup.lbl.category"/>
														</label>
                                                       <div>
                                                                     <ihtml:text id="mailCategoryCodeLy"  property="mailCategoryCode" name="form"
							                                         styleClass="form-control" maxlength="1" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_CATEGORY" />
														</div>
                                                    </div>
                                                </div>



                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.subclass"/></label>
                                                       <div>
                                                                     <ihtml:text id="mailSubclassLy"  property="mailSubclass" name="form"
							                                         styleClass="form-control" maxlength="2" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_SUBCLASS" />
														 </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="row">
                                                <div class="col-2">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.year"/></label>
                                                   
                                                       <div>
                                                                     <ihtml:text id="yearLy"  property="year" name="form"
							                                         styleClass="form-control" maxlength="1" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_YEAR" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.dsn"/></label>
                                                       
                                                       <div>
                                                                     <ihtml:text id="despatchSerialNumberLy"  property="despatchSerialNumber" name="form"
							                                         styleClass="form-control" maxlength="4" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DSN" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.rsn"/></label>
                                                      
                                                       <div>
                                                                     <ihtml:text id="receptacleSerialNumberLy"  property="receptacleSerialNumber" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_RSN" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.consignmentno"/></label>
                                                      
                                                       <div>
                                                                     <ihtml:text id="consignmentNumberLy"  property="consignmentNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_CONSIGNMENTNO" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.fromdate" styleClass="form-control" 
									                labelStyleClass="form-control-label" id="fromDateLy" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FROMDATE" 
									                property="fromDate"/>
													
                                                        
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.todate" styleClass="form-control" 
									                labelStyleClass="form-control-label" id="toDateLy" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_TODATE" 
									                property="toDate"/>
													
													
                                                        
                                                       
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.pacode"/></label>
														 <div>
                                                                     <ihtml:text id="paCodeLy"  property="paCode" name="form"
							                                         styleClass="form-control" maxlength="5" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_PACODE" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-8">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightnumber"/></label>
                                                        
                                                           
                                                              <div>
                                                                     <ihtml:text id="flightNumberLy"  property="flightNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FLIGHTNUMBER" />
														      </div>
                                                            
                                                       
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
													
													
													<ibusiness:litecalendar tabindex="6"    label="mail.operations.ux.listMailbagPopup.lbl.funnel.flightdate" styleClass="form-control" 
									                labelStyleClass="form-control-label" id="flightDateLy" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_FLIGHTDATE" 
									                property="flightDate"/>
													 
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.upliftairport"/></label>
                                                       <div>
                                                                     <ihtml:text id="upliftAirportLy"  property="upliftAirport" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_UPLIFTAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.uldnumber"/></label>
                                                      <div>
                                                                     <ihtml:text id="uldNumberLy"  property="uldNumber" name="form"
							                                         styleClass="form-control" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_ULDNUMBER" />
														 </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.originairport"/></label>
                                                      <div>
                                                                     <ihtml:text id="originAirportCodeLy"  property="originAirportCode" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_ORIGINAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="form-group">
                                                        <label class="form-control-label "><common:message key="mail.operations.ux.listMailbagPopup.lbl.funnel.destairport"/></label>
                                                      <div>
                                                                     <ihtml:text id="destinationAirportCodeLy"  property="destinationAirportCode" name="form"
							                                         styleClass="form-control" maxlength="3" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_DESTAIRPORT" />
														 </div>
                                                    </div>
                                                </div>
                                                




                                            </div>
                                        </div>
										
										
										 <div class="w-100">
                                        <div class="btn-row border-0 pad-t-sm">
                                           
											 <ihtml:nbutton styleClass="btn btn-primary" id="btOKLying" property="btOKLying" accesskey ="O" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNOK" >
			                                 <common:message key="mail.operations.ux.listMailbagPopup.btn.ok" />
		                                     </ihtml:nbutton>
           
			                                <ihtml:nbutton styleClass="btn btn-default" id="btnClearFilterLy" property="btnClearFilterLy" accesskey ="C" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNCLEAR" >
			                                <common:message key="mail.operations.ux.listMailbagPopup.btn.clear" />
		                                    </ihtml:nbutton>
                                        </div>
                                    </div>
										
                                    </div>
                                   
                                </div>
								
								
								
                            </div>
							<div class="card-body p-0 table-responsive">
							 <table class="table table-bordered table-x-md border-bottom mb-0 table-hover" id="lyinglisttable">
								 <thead>
                                        <tr>
                                            <th width="35"> <input type="checkbox" name="allLyingListChecked" onclick="updateHeaderCheckBox(this.form,this,this.form.lyingListRowId)"></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.mailbagid"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.origin"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.destination"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.category"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.subclass"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.year"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.dsn"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.rsn"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.hni"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.ri"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightnumber"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.flightdate"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.weight"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.consignmentno"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.consigntdate"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.rdt"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.uldnumber"/></th>
                                            <th><common:message key="mail.operations.ux.listMailbagPopup.lbl.acptedstatus"/></th>
                                            <th></th>
                                        </tr>
                                    </thead>
									<tbody>
									<logic:present name="lyinglist_vo">
									 <logic:iterate id="lyinglist_vo" name="lyinglist_vo" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO" indexId="indexId">
                                        <tr>
										    <td><input type="checkbox" name="lyingListRowId" value="<%=String.valueOf(indexId)%>" />  														
                                            </td>                                                                      
                                            <td>
											<logic:present name="lyinglist_vo" property="mailbagId">
											<bean:write name="lyinglist_vo" property="mailbagId" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="ooe">
											<bean:write name="lyinglist_vo" property="ooe" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="doe">
											<bean:write name="lyinglist_vo" property="doe" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="mailCategoryCode">
											<bean:write name="lyinglist_vo" property="mailCategoryCode" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="mailSubclass">
											<bean:write name="lyinglist_vo" property="mailSubclass" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="year">
											<bean:write name="lyinglist_vo" property="year" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="despatchSerialNumber">
											<bean:write name="lyinglist_vo" property="despatchSerialNumber" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="receptacleSerialNumber">
											<bean:write name="lyinglist_vo" property="receptacleSerialNumber" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="highestNumberedReceptacle">
											<bean:write name="lyinglist_vo" property="highestNumberedReceptacle" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="registeredOrInsuredIndicator">
											<bean:write name="lyinglist_vo" property="registeredOrInsuredIndicator" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="flightNumber">
											<bean:write name="lyinglist_vo" property="flightNumber" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
																																							 <logic:present name="lyinglist_vo" property="flightDate">
					                         <bean:define id="flightDate" name="lyinglist_vo" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                                            <%String fliDate=TimeConvertor.toStringFormat(flightDate.toCalendar(),"dd-MM-yy");%>
                                            <%=fliDate%>
								            </logic:present>
																	
                                 
											</td>
                                            <td>
						<logic:present name="lyinglist_vo" property="weight">
												<%
												double weightValue = 0.0;
												weightValue = lyinglist_vo.getWeight().getDisplayValue();
												%>
													<%=df.format(weightValue)%>                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="consignmentNumber">
											<bean:write name="lyinglist_vo" property="consignmentNumber" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="consignmentDate">
											
					                         <bean:define id="consignmentDate" name="lyinglist_vo" property="consignmentDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
                                             <%String consdate=TimeConvertor.toStringFormat(consignmentDate.toCalendar(),"dd-MM-yy");%>
                                             <%=consdate%>
                                                                                                                                                               </logic:present>
																																						    </td>
                                            <td>
											<logic:present name="lyinglist_vo" property="reqDeliveryTime">
											<%=lyinglist_vo.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm")%>
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
											<logic:present name="lyinglist_vo" property="uldNumber">
											<bean:write name="lyinglist_vo" property="uldNumber" />
                                                                                                                                                              </logic:present>
											</td>
                                            <td>
                                                                                                                                                             <logic:present name="lyinglist_vo" property="accepted">
											<logic:equal name="lyinglist_vo" property="accepted" value="Y" >
													<img id="isAccepted" src="<%=request.getContextPath()%>/images/icon_on.gif" />
											</logic:equal>
											<logic:equal name="lyinglist_vo" property="accepted" value="N">
													<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
											 </logic:equal>
										  </logic:present>
										   <logic:notPresent name="lyinglist_vo" property="accepted">
												<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
										  </logic:notPresent>
										  
											</td>
                                            <td><a href="#"><i class="icon ico-pencil"></i></a></td>
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
        </section>
        <footer>
		
		    <ihtml:nbutton styleClass="btn btn-primary" id="btAdd" property="btAdd" accesskey ="A" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNADD" >
			   <common:message key="mail.operations.ux.listMailbagPopup.btn.add" />
		    </ihtml:nbutton>
           
			<ihtml:nbutton styleClass="btn btn-default" id="btClose" property="btClose" accesskey ="X" componentID="CMP_MAIL_OPERATIONS_MAILBAGPOPUP_BTNCLOSE" >
			   <common:message key="mail.operations.ux.listMailbagPopup.btn.close" />
		    </ihtml:nbutton>	
        </footer>
    </div>	

	

 
  
        </ihtml:form>
	
			
			<script>
			
			jquery('body').on('click', '.filter-sec', function () {
            jquery(".filter-section").dialog({
                    classes: {
                        'ui-dialog': 'scribble-pad'
                    },
                    width: '800px',
                    title: "Filter",
                    position: {
                        my: "right top",
                        at: "right bottom",
                        of: jquery(this)
                    }
                });
              });
		
		jquery('body').on('click', '.filter-secly', function () {
            jquery(".filter-sectionly").dialog({
                    classes: {
                        'ui-dialog': 'scribble-pad'
                    },
                    width: '800px',
                    title: "Filter",
                    position: {
                        my: "right top",
                        at: "right bottom",
                        of: jquery(this)
                    }
                });
             });
		
		
			</script>
			
		   
</body>

</html:html>	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
                                                         