
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimHistoryPopupForm" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>

 <bean:define id="form"
	  name="ClaimHistoryPopupForm"
	  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimHistoryPopupForm"
	  toScope="page" />	
	  
  <business:sessionBean id="uspsHistory_VO" 
	  moduleName="mail.mra" 
	  screenID="mail.mra.gpareporting.ux.claimhistory" 
	  method="get" 
	  attribute="uspsHistoryVOs" />
	  
	  <div class="card" style="height:490px;">
	           <table class="table table-x-md border-bottom" id="uspsHistoryTable">
		                   
			              <thead>
                                <tr>
                                           
                                            <th><common:message key="mail.mra.ux.ClaimHistoryPopup.scanTimeUTC"/></th>
											<th class="text-center"><common:message key="mail.mra.ux.ClaimHistoryPopup.scanMode"/></th>
											<th class="text-center"><common:message key="mail.mra.ux.ClaimHistoryPopup.scanSite"/> </th>
											<th class="text-center"><common:message key="mail.mra.ux.ClaimHistoryPopup.indicator"/></th>
											<th class="text-center"><common:message key="mail.mra.ux.ClaimHistoryPopup.exceptionCode"/></th>
                                           
                                 </tr>
                         </thead>
					   <tbody>
						
							<logic:present name="uspsHistory_VO">
							
								<logic:iterate id="uspsHistory_VO" name="uspsHistory_VO" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO" indexId="indexId">
                                    <tr>
										 <td>
										   
										     <logic:present name="uspsHistory_VO" property="scanTimeUTC">
											
											 <bean:define id="scanTimeUTC" name="uspsHistory_VO" property="scanTimeUTC" toScope="page" />
												 <%=scanTimeUTC%>			
										     </logic:present> 
											 <logic:notPresent name="uspsHistory_VO" property="scanTimeUTC">
											
							                   &nbsp;
						                     </logic:notPresent>
											
										</td>
										<td class="text-center">
										    <logic:present name="uspsHistory_VO" property="scannedModeCode">
											
											 <bean:define id="scannedModeCode" name="uspsHistory_VO" property="scannedModeCode" toScope="page" />
												<%=scannedModeCode%>		
										     </logic:present> 
											 <logic:notPresent name="uspsHistory_VO" property="scannedModeCode">
							                   
						                     </logic:notPresent>
										</td>
										<td class="text-center">
										    <logic:present name="uspsHistory_VO" property="scannedport">
					
											 <bean:define id="scannedport" name="uspsHistory_VO" property="scannedport" toScope="page" />
												<%=scannedport%>			
										     </logic:present> 
											 <logic:notPresent name="uspsHistory_VO" property="scannedport">
							                   
						                     </logic:notPresent>
										</td>
										<td class="text-center"> 
										
										
										    <logic:present name="uspsHistory_VO" property="validForPayIndicator">
									
											 <bean:define id="validForPayIndicator" name="uspsHistory_VO" property="validForPayIndicator" toScope="page" />
											 <% if(validForPayIndicator.equals("Y")) { %>
												 <i class="icon ico-ok-green-md"></i>
											 <%
											  }
											else {
											%>
												<i class="icon ico-close-red"></i>
											<%}%>												
										     </logic:present> 
											 <logic:notPresent name="uspsHistory_VO" property="validForPayIndicator">
							                  
						                     </logic:notPresent>
										</td>
										<td class="text-center">
										    <logic:present name="uspsHistory_VO" property="exceptionCode">
											
											 <bean:define id="exceptionCode" name="uspsHistory_VO" property="exceptionCode" toScope="page" />
												<%=exceptionCode%>			
										     </logic:present> 
											 <logic:notPresent name="uspsHistory_VO" property="exceptionCode">
							                   
						                     </logic:notPresent>
										</td>
									 </tr>
								</logic:iterate>
                              </logic:present>    
                           </tbody>													
                   
					 </table>
					  </div>
										