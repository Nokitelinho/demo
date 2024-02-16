<!--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  Mail
* File Name     	 :  ListMailbagPopup.jsp
* Date          	 :  14-Januvary-2019
* Author(s)     	 :  A-7929

*************************************************************************/
 -->

<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimHistoryPopupForm" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>






<html:html>

<head>






   <title>.:iCargo Lite:.
       <common:message bundle="ClaimHistoryPopupResources" key="mail.mra.ux.ClaimHistoryPopup.title" />
   </title>
    <meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/mra/gpareporting/ux/ClaimHistoryPopup_Script.jsp" />

</head>

<body>
		<logic:present name="popup">
			<div id="walkthroughholder"/>
		</logic:present>




      <bean:define id="form"
	  name="ClaimHistoryPopupForm"
	  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimHistoryPopupForm"
	  toScope="page" />

	  <business:sessionBean id="mailbaghistory_vo"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory"
	  method="get"
	  attribute="mailbagHistoryVOsCollection" />

	  <business:sessionBean id="ONETIME_STATUS"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory" method="get"
	  attribute="oneTimeStatus" />

	  <business:sessionBean id="invoic"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory" method="get"
	  attribute="invoicDetails" />
	  <business:sessionBean id="claim"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory" method="get"
	  attribute="claimDetails" />

	  <business:sessionBean id="uspsHistory_VO"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory"
	  method="get"
	  attribute="uspsHistoryVOs" />

	   <business:sessionBean id="forcemajeure_VO"
	  moduleName="mail.mra"
	  screenID="mail.mra.gpareporting.ux.claimhistory"
	  method="get"
	  attribute="forceMajeureDetails" />


        <ihtml:form action="/mail.mra.gpareporting.ux.claimHistoryPopup.screenLoad.do">
		 <div class="claimhistory" title="Claim History">
		 <header></header>

		    <div id="tabs" class="tabs" style="display:block">
			<div class="thead-bg">
                    <ul class="nav nav-tabs nav-tabs-large">
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#revenue" >Revenue History</a>
                        </li>
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#operational"  >Operational History</a>
                        </li>
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#usps" disabled="true">USPS History Details</a>
                        </li>
						<li class="nav-item" >
                                <a class="nav-link showaddBtn" href="#forcemajeure"  >Force Majeure Details</a>
                        </li>
                    </ul>
               </div>
			     <div class="tabs-container">
				      <div id="revenue">
					  <p>&nbsp;</p>
						  <h4><label class="form-control-label">INVOIC Details</label> </h4>
							<div class="card" style="height:250px;">
                                <table class="table table-x-md m-0 " id="invoictable">
                                    <thead>
                                        <tr>
                                            <th><common:message key="mail.mra.ux.ClaimHistoryPopup.invoicid"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.ircvdat"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.iperiod"/> </th>
											<th>INVOIC Type</th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.iamount"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.paytyp"/></th>
                                        </tr>
                                    </thead>
									<tbody>
										<logic:present name="invoic">
											<logic:iterate id="invoic" name="invoic" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO" indexId="indexId">
												<tr>
													<td>
														<logic:present name="invoic" property="invoicID">
															<bean:write name="invoic" property="invoicID" />
														</logic:present>
														<logic:notPresent name="invoic" property="invoicID">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="invoic" property="invoicRcvDate">
															<bean:define id="date" name="invoic" property="invoicRcvDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
																<%
																String receivedDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
																<%=receivedDate%>
														</logic:present>
														<logic:notPresent name="invoic" property="invoicRcvDate">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="invoic" property="invoicPeriod">
															<bean:write name="invoic" property="invoicPeriod" />
														</logic:present>
														<logic:notPresent name="invoic" property="invoicPeriod">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="invoic" property="invoicPayType">
															<bean:write name="invoic" property="invoicPayType" />
														</logic:present>
														<logic:notPresent name="invoic" property="invoicPayType">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="invoic" property="invoicamount">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="invoic" moneyproperty="invoicamount" property="invoicamount" overrideRounding = "true" />
														</logic:present>
														<logic:notPresent name="invoic" property="invoicamount">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="invoic" property="payType">
															<bean:write name="invoic" property="payType" />
														</logic:present>
														<logic:notPresent name="invoic" property="payType">
															&nbsp;
														</logic:notPresent>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
                                    </tbody>
                                 </table>
							</div>
							<p>&nbsp;</p>
							<h4><label class="form-control-label">Claim Details</label> </h4>
							<div class="card" style="height:190px;">
                                <table class="table table-x-md m-0 " id="claimtable">
                                    <thead>
                                        <tr>
                                            <th><common:message key="mail.mra.ux.ClaimHistoryPopup.claimid"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.crcvdat"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.cperiod"/> </th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.camount"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.claimtyp"/></th>
                                        </tr>
                                    </thead>
									<tbody>
										<logic:present name="claim">
											<logic:iterate id="claim" name="claim" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO" indexId="indexId">
												<tr>
													<td>
														<logic:present name="claim" property="claimRefNumber">
															<bean:write name="claim" property="claimRefNumber" />
														</logic:present>
														<logic:notPresent name="claim" property="claimRefNumber">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="claim" property="claimRcvDate">
															<bean:define id="date" name="claim" property="claimRcvDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
																<%
																String receivedDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
																<%=receivedDate%>
														</logic:present>
														<logic:notPresent name="claim" property="claimRcvDate">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="claim" property="claimPeriod">
															<bean:write name="claim" property="claimPeriod" />
														</logic:present>
														<logic:notPresent name="claim" property="claimPeriod">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="claim" property="claimamount">
															<ibusiness:moneyDisplay showCurrencySymbol="false" name="claim" moneyproperty="claimamount" property="claimamount" overrideRounding = "true" />
														</logic:present>
														<logic:notPresent name="claim" property="claimamount">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="claim" property="claimReason">
															<bean:write name="claim" property="claimReason" />
														</logic:present>
														<logic:notPresent name="claim" property="claimReason">
															&nbsp;
														</logic:notPresent>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
                                    </tbody>
                                 </table>
							</div>
					  </div>
					  <div id="operational">
				       <div class="pad-sm">
                          <div class="row">
                            <div class="col-5">
                                <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.rdt"/></label>
                               <div> <bean:write name="form" property="reqDeliveryTime" /> </div>
                             </div>
                             <div class="col-5">
                                <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.ooe"/></label>
                              <div> <bean:write name="form" property="originExchangeOffice" /> </div>
                             </div>
                             <div class="col-5">
                                <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.doe"/></label>
                              <div>  <bean:write name="form" property="destinationExchangeOffice" /> </div>
                             </div>
                            <div class="col-5">
                               <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.category"/></label>
                                <div> <bean:write name="form" property="mailCategoryCode" /> </div>
                            </div>
                            <div class="col-4">
                               <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.subclass"/></label>
                              <div>  <bean:write name="form" property="mailSubclass" /> </div>
                            </div>
                          </div>

                      <div class="row mar-t-sm">
                         <div class="col-5">
                             <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.year"/></label>
                            <div>   <bean:write name="form" property="year" /> </div>
                        </div>
                        <div class="col-5">
                            <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.dsn"/></label>
                           <div>  <bean:write name="form" property="dsn" /> </div>
                        </div>
                        <div class="col-5">
                            <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.rsn"/></label>
                             <div> <bean:write name="form" property="rsn" /> </div>
                        </div>
                        <div class="col-5">
                            <label class="form-control-label"><common:message key="mail.mra.ux.ClaimHistoryPopup.weight"/></label>
                             <div> <bean:write name="form" property="weight" /> </div>
                        </div>
                      </div>
                </div>

				 <div class="card" style="height:392px;">
                                <table class="table table-x-md border-bottom" id="mailbaghistorytable">
                                    <thead>
                                        <tr>

                                            <th><common:message key="mail.mra.ux.ClaimHistoryPopup.date"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.time"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.messagetime"/> </th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.operation"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.airport"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.flight"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.container"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.pou"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.userid"/></th>

                                        </tr>
                                    </thead>
                                 <tbody>

									<logic:present name="mailbaghistory_vo">
									 <logic:iterate id="mailbaghistory_vo" name="mailbaghistory_vo" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO" indexId="indexId">
                                        <tr>

                                            <td>
											<logic:present
															name="mailbaghistory_vo" property="scanDate">
															<bean:define id="scanDate" name="mailbaghistory_vo"
																property="scanDate" toScope="page" />
															<%=scanDate.toString().substring(
											0, 11)%>
											</logic:present>
											<logic:notPresent name="mailbaghistory_vo" property="scanDate">
							                 &nbsp;
						                    </logic:notPresent>

											</td>



											<td>


										    <logic:present
															name="mailbaghistory_vo" property="scanDate">
															<bean:define id="scanDate" name="mailbaghistory_vo"
																property="scanDate" toScope="page" />
															<%=scanDate.toString().substring(
											12, 17)%>
											</logic:present>
											<logic:notPresent name="mailbaghistory_vo" property="scanDate">
							                 &nbsp;
						                    </logic:notPresent>
											</td>

											<logic:notEmpty name="mailbaghistory_vo"
														property="messageTime">
											<td><%=mailbaghistory_vo.getMessageTime()
													.toDisplayFormat(
														"dd-MMM-yyyy HH:mm")%>
											</td>
										</logic:notEmpty>
										<logic:empty name="mailbaghistory_vo" property="messageTime">
											<td>&nbsp;</td>
										</logic:empty>

											<td>
											<logic:present
												name="mailbaghistory_vo" property="mailStatus">
												<bean:define id="mailStatus" name="mailbaghistory_vo"
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
											</logic:present>
										</logic:present>
											</td>
											<td>
											<logic:present name="mailbaghistory_vo" >
											<bean:write name="mailbaghistory_vo" property="scannedPort" />
                                                          </logic:present>
											</td>
											<td>
											<logic:present
															name="mailbaghistory_vo" property="flightDate">
															<logic:present name="mailbaghistory_vo" property="carrierCode">

																<bean:define id="carrierCode" name="mailbaghistory_vo"
																	property="carrierCode" toScope="page" />
																<bean:define id="flightNumber" name="mailbaghistory_vo"
																	property="flightNumber" toScope="page" />

																<%=carrierCode%><%=flightNumber%>

															</logic:present>
														</logic:present> <logic:notPresent name="mailbaghistory_vo" property="flightDate">
															<logic:present name="mailbaghistory_vo" property="carrierCode">
																<bean:define id="carrierCode" name="mailbaghistory_vo"
																	property="carrierCode" toScope="page" />
																<%=carrierCode%>
															</logic:present>
														</logic:notPresent>
											</td>
											<td>
											<logic:present name="mailbaghistory_vo" >
											<bean:write name="mailbaghistory_vo" property="containerNumber" />
                                                          </logic:present>
											</td>
											<td>
											<logic:present name="mailbaghistory_vo" >
											<bean:write name="mailbaghistory_vo" property="pou" />
                                                          </logic:present>
											</td>
											<td>
											<logic:present name="mailbaghistory_vo" >
											<bean:write name="mailbaghistory_vo" property="scanUser" />
                                                          </logic:present>
											</td>




                                        </tr>
										</logic:iterate>
                                       </logic:present>
                                    </tbody>
                                 </table>
               </div>






					  </div>
		       <div id="usps">
		          <jsp:include page="/jsp/mail/mra/gpareporting/ux/USPSHistoryDetails.jsp"/>
                </div>
				
				<!--Force Majeure starts -->
				<div id="forcemajeure">
				<div class="card" style="height:392px;">
						<table class="table table-x-md border-bottom">
                                    <thead>
                                        <tr>
                                            <th><common:message key="mail.mra.ux.ClaimHistoryPopup.fmid"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.loadscan"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.receivescan"/> </th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.deliveryscan"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.latedelivery"/></th>
											<th><common:message key="mail.mra.ux.ClaimHistoryPopup.allscan"/></th>
                                        </tr>
                                    </thead>
									<tbody>
										<logic:present name="forcemajeure_VO">
											<logic:iterate id="forcemajeure_VO" name="forcemajeure_VO" type="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO" indexId="indexId">
												<tr>
													<td>
														<logic:present name="forcemajeure_VO" property="forceMajuereID">
															<bean:write name="forcemajeure_VO" property="forceMajuereID" />
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="forceMajuereID">
															&nbsp;
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="forcemajeure_VO" property="loadScan">
														<logic:equal name="forcemajeure_VO" property="loadScan" value="Y">
															<div class="fs12">Approved</div>
										                    <div class="fs12">Station: 
															   <logic:present name="forcemajeure_VO" property="airportCode">
															   <bean:write name="forcemajeure_VO" property="airportCode" />
															   </logic:present>
			</div>
															</logic:equal>
														<logic:notEqual name="forcemajeure_VO" property="loadScan" value="Y">
															<div class="fs12">--</div>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="loadScan">
															<div class="fs12">--</div>
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="forcemajeure_VO" property="recieveScan">
														<logic:equal name="forcemajeure_VO" property="recieveScan" value="Y">
															<div class="fs12">Approved</div>
										                    <div class="fs12">Station: 
															   <logic:present name="forcemajeure_VO" property="airportCode">
															   <bean:write name="forcemajeure_VO" property="airportCode" />
															   </logic:present>
															</div>
															</logic:equal>
														<logic:notEqual name="forcemajeure_VO" property="recieveScan" value="Y">
															<div class="fs12">--</div>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="recieveScan">
															<div class="fs12">--</div>
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="forcemajeure_VO" property="deliveryScan">
														<logic:equal name="forcemajeure_VO" property="deliveryScan" value="Y">
															<div class="fs12">Approved</div>
										                    <div class="fs12">Station: 
															   <logic:present name="forcemajeure_VO" property="airportCode">
															   <bean:write name="forcemajeure_VO" property="airportCode" />
															   </logic:present>
															</div>
															</logic:equal>
														<logic:notEqual name="forcemajeure_VO" property="deliveryScan" value="Y">
															<div class="fs12">--</div>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="deliveryScan">
															<div class="fs12">--</div>
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="forcemajeure_VO" property="lateDeliveryScan">
														<logic:equal name="forcemajeure_VO" property="lateDeliveryScan" value="Y">
															<div class="fs12">Approved</div>
										                    <div class="fs12">Station: 
															   <logic:present name="forcemajeure_VO" property="airportCode">
															   <bean:write name="forcemajeure_VO" property="airportCode" />
															   </logic:present>
															</div>
															</logic:equal>
														<logic:notEqual name="forcemajeure_VO" property="lateDeliveryScan" value="Y">
															<div class="fs12">--</div>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="lateDeliveryScan">
															<div class="fs12">--</div>
														</logic:notPresent>
													</td>
													<td>
														<logic:present name="forcemajeure_VO" property="allScan">
														<logic:equal name="forcemajeure_VO" property="allScan" value="Y">
															<div class="fs12">Approved</div>
										                    <div class="fs12">Station: 
															   <logic:present name="forcemajeure_VO" property="airportCode">
															   <bean:write name="forcemajeure_VO" property="airportCode" />
															   </logic:present>
															</div>
															</logic:equal>
														<logic:notEqual name="forcemajeure_VO" property="allScan" value="Y">
															<div class="fs12">--</div>
														</logic:notEqual>
														</logic:present>
														<logic:notPresent name="forcemajeure_VO" property="allScan">
															<div class="fs12">--</div>
														</logic:notPresent>
													</td>
												</tr>
											</logic:iterate>
										</logic:present>
                                    </tbody>
                                 </table>
							</div>
				</div>
<!--Force Majeure Ends-->
				
			</div>

	     </div>









        </ihtml:form>







	</body>

</html:html>











