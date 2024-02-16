<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  MailOnHandList.jsp
* Date          	 :  08-Oct-2014
* Author(s)     	 :  Senthilkumar.G

*************************************************************************/
 --%>



<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">

<head>
		
	
   
		
	
	<title><common:message bundle="MailOnHandListResources" key="mailtracking.defaults.mailhandlist.lbl.title" /></title>
	<meta name="decorator" content="mainpanelrestyledui">
	 <common:include type="script" src="/js/mail/operations/MailOnHandList_Script.jsp" />  

</head>

<body id="bodyStyle">
	
	
	
	

<bean:define id="MailOnHandListForm"
	name="MailOnHandListForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm"
    toScope="page" scope="request"/>

<business:sessionBean
	id="oneTimeValues"
	moduleName="mail.operations" screenID="mailtracking.defaults.mailonhandlist"
	method="get"
	attribute="oneTimeValues" />
<!-- new -->

<business:sessionBean id="MailOnHandDetailsVOcollection" moduleName="mail.operations" screenID="mailtracking.defaults.mailonhandlist" method="get" attribute="MailOnHandDetailsVO" />
	<logic:present name="MailOnHandDetailsVOcollection">
		<bean:define id="MailOnHandDetailsVOcollection" name="MailOnHandDetailsVOcollection" toScope="page"/>
	</logic:present>

	
	
	<business:sessionBean id="searchContainerFilterVO" moduleName="mail.operations" screenID="mailtracking.defaults.mailonhandlist" method="get" attribute="searchContainerFilterVO" />
	<logic:present name="searchContainerFilterVO">
		<bean:define id="searchContainerFilterVO" name="searchContainerFilterVO" toScope="page"/>
	</logic:present>
<!-- end -->

<div id="pageDiv" class="iCargoContent">
<ihtml:form action="mailtracking.defaults.mailonhandlist.screenload.do">
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<input type=hidden name="mySearchEnabled"/>
    <div class="ic-content-main" style="width:69%;">
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.defaults.mailhandlist.lbl.pagetitle" />
			</span>
		     <div class="ic-head-container">
			    <div class="ic-filter-panel">
				    <div class="ic-left">
				    	<h4><b>	
						    <common:message key="mailtracking.defaults.mailhandlist.lbl.searchcriteria" />
						</b></h4>
					</div>
					<div class="ic-row">
					    <div class="ic-input ic-label-30 ic-split-25">
						    <label>
							    <common:message key="mailtracking.defaults.mailhandlist.lbl.fromdate" />
							</label>
								<logic:notPresent name="searchContainerFilterVO" property="strFromDate">
								    <ibusiness:calendar property="fromDate" id="fromDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_FROMDATE" />
								</logic:notPresent>
								<logic:present name="searchContainerFilterVO" property="strFromDate">
									<bean:define id="frmDate" name="searchContainerFilterVO" property="strFromDate" toScope="page"/>
									<ibusiness:calendar property="fromDate" id="fromDate" type="image" value="<%=(String)frmDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_FROMDATE" />
								</logic:present>
						</div>
						  <div class="ic-input ic-label-30 ic-split-25">
						    <label>
							    <common:message key="mailtracking.defaults.mailhandlist.lbl.todate" />
							</label>
							    <logic:notPresent name="searchContainerFilterVO" property="strToDate">
							        <ibusiness:calendar property="toDate" id="toDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_TODATE" />
						        </logic:notPresent>
						        <logic:present name="searchContainerFilterVO" property="strToDate">
								  <bean:define id="tDate" name="searchContainerFilterVO" property="strToDate" toScope="page"/>
								  <ibusiness:calendar property="toDate" id="toDate" type="image" value="<%=(String)tDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_TODATE" />
					            </logic:present>
						</div>
						<div class="ic-input ic-mandatory ic-label-30 ic-split-20">
						    <label>
							    <common:message key="mailtracking.defaults.mailhandlist.lbl.depport" />
							</label>
							    <ihtml:text property="airport"  maxlength="4" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_DEPPORT" />
								<div class= "lovImg">
							    <img  id="airportlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
						</div>
					</div>
						<div class="ic-input ic-label-30 ic-split-25">
						    <label>
							    <common:message key="mailtracking.defaults.mailhandlist.lbl.assignto" />
							</label>
							    <% String mode="";%>
									<logic:notPresent name="searchContainerFilterVO" property="searchMode">
										<% mode="";%>
									</logic:notPresent>
									<logic:present name="searchContainerFilterVO" property="searchMode">
										<bean:define id="searchMode" name="searchContainerFilterVO" property="searchMode" toScope="page"/>
									<% mode=(String)searchMode; %>
									</logic:present>
									<ihtml:select property="assignedto" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_ASSIGNTYPE" value="<%=(String)mode%>" >
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.containersearchmode">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<html:option value="<%=(String)fieldValue%>">
																		<%=(String)fieldDescription%>
																	</html:option>
															</logic:present>
														</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
						</div>
					</div>
					<div class="ic-row">
					    
						<div class="ic-button-container">
						    <ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_LIST_BUTTON" accesskey="L">
			       	            <common:message key="mailtracking.defaults.mailhandlist.btn.list" />
				            </ihtml:nbutton>
  	                        <ihtml:nbutton property="btnClear"   componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_CLEAR_BUTTON" accesskey="E" >
			      	            <common:message key="mailtracking.defaults.mailhandlist.btn.clear" />
       	       	            </ihtml:nbutton>
						</div>
					</div>
				</div>
			 </div>
			 <div class="ic-main-container">
			 <div class="ic-row">
			    <div class="ic-left">
					<h4>
						<common:message key="mailtracking.defaults.mailhandlist.lbl.containerdtls" />
					</h4>
				</div>
			 </div>
				<div class="ic-row ic-marg-top-5">
				   <%String lstPgNo = "";%>
				    <div class="ic-col-30">
						<logic:present name="MailOnHandListForm" property="lastPageNum">
								<bean:define id="lastPg" name="MailOnHandListForm" property="lastPageNum" scope="request"  toScope="page" />
								<%
									lstPgNo = (String) lastPg;
								%>
						</logic:present>
						<logic:present name="MailOnHandDetailsVOcollection" >
							<bean:define id="pageObj" name="MailOnHandDetailsVOcollection"  />
							<common:paginationTag pageURL="mailtracking.defaults.mailonhandlist.listmailhandlist.do"
									name="pageObj"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=lstPgNo%>" />
						</logic:present>
						<logic:notPresent name="MailOnHandDetailsVOcollection" >
							&nbsp;
						 </logic:notPresent>
					 </div>
					<div class="ic-right paddR10">
						<logic:present name="MailOnHandDetailsVOcollection" >
							 <bean:define id="pageObj1" name="MailOnHandDetailsVOcollection"  />
							<common:paginationTag
							  linkStyleClass="iCargoLink"
							  disabledLinkStyleClass="iCargoLink"
						  pageURL="javascript:submitPage('lastPageNum','displayPage')"
						  name="pageObj1"
						  display="pages"
						  lastPageNum="<%=lstPgNo%>"
						  exportToExcel="true"
						  exportTableId="mailonlist"
						  exportAction="mailtracking.defaults.mailonhandlist.listmailhandlist.do"/>
						</logic:present>
						 <logic:notPresent name="MailOnHandDetailsVOcollection" >
							&nbsp;
						</logic:notPresent>
					</div>
				</div>
				<div class="ic-row">
				    <div class="tableContainer" id="div1" align="center"  style="width:100%;height:620px;">
                        <table class="fixed-header-table" id="mailonlist">
							  <thead>
								<tr class="iCargoTableHeadingLeft">
									<td width="3%"><input type="checkbox" name="mailonhandlist" onclick="updateHeaderCheckBox(this.form,this,this.form.selectmaillist);"/><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.currentport" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.destination" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.subclassgroup" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.noofuld" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.noofmt" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.noofMTSpace" /><span></span></td>
									<td width="7%"><common:message key="mailtracking.defaults.mailhandlist.lbl.noofdays" /><span></span></td>				
								
								</tr>
							</thead>
							<tbody>
							    <logic:present name="MailOnHandDetailsVOcollection" >
										<% int i = 1;
										 int count = 1;
									  %>
										<bean:define id="MailListVOsColl" name="MailOnHandDetailsVOcollection" scope="page" toScope="page"/>
										 <% Collection<String> selectedrows = new ArrayList<String>(); %>
                                        <logic:iterate id="MailOnHandDetailsVO" name="MailListVOsColl" indexId="rowCount">
										<index="rowCount">
									<tr>
									    <td>
											<div class="ic-center">
												<input type="checkbox" name="selectmaillist"   value="<%=count%>"/>
											</div>
                                        </td>
									    <td>
									        <bean:write name="MailOnHandDetailsVO" property="currentAirport"/>
										</td>
										<td>
									        <bean:write name="MailOnHandDetailsVO" property="destination"/>
										</td>
										<td>
									        <bean:write name="MailOnHandDetailsVO" property="subclassGroup"/>
										</td>
									    <td>
											<logic:present name="MailOnHandDetailsVO" property="uldCount">
											   <img src="<%=request.getContextPath()%>/images/info.gif" width="16" height="16" 
																name="booking_link"  id="booking_link_<%=String.valueOf(rowCount)%>" onclick="prepareAttributes(event,this,'booking_','bookingDetails')"/>
																				
										
												<bean:write name="MailOnHandDetailsVO" property="uldCountDisplay"/>
											   <div style="display:none;height:expression((document.body.clientHeight*15)/100 ;width:100%" id="booking_<%=String.valueOf(rowCount)%>" name="bookingDetails" tabindex="0">
												<bean:write id="uldCounts"  name="MailOnHandDetailsVO" property="uldCount"/>
											   </div>	
											</logic:present >		
										</td>
										<td>
									        <bean:write name="MailOnHandDetailsVO" property="mailTrolleyCount"/>
										</td>
										<td>
											<logic:present name="MailOnHandDetailsVO" property="noOfMTSpace">
											  <bean:write name="MailOnHandDetailsVO" property="noOfMTSpace"/>
											</logic:present >
										</td>
										<td>
									        <bean:write name="MailOnHandDetailsVO" property="noOfDaysInCurrentLoc"/>
                                        </td>
								    </tr>
									   <%
										  count++;
										%>
									   
										</logic:iterate>
										</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			 </div>
		    
			 <div class="ic-foot-container" style="width:100%;">
			    <div class="ic-button-container paddR10">
				    <ihtml:nbutton property="btnViewContainer"  componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_VIEWCONTAINER" accesskey="O" >
               	        <common:message key="mailtracking.defaults.mailhandlist.btn.containerdetail" />
                    </ihtml:nbutton>
                    <ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_DEFAULTS_MAILONHANDLIST_CLOSE_BUTTON" accesskey="O">
            	        <common:message key="mailtracking.defaults.mailhandlist.btn.close" />
                    </ihtml:nbutton>
				</div>
			 </div>
			
	
</div>


</ihtml:form>

</div>

		
	</body>
</html:html>
