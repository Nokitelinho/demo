<%--/***********************************************************************
* Project	     		 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ProrationLog.jsp
* Date                 	 : 17-Sep-2008
* Author(s)              : A-3229,A-2391
*************************************************************************/
--%>
 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 
 

 <html:html>		

<head>
	
	
		
	
 	<title><common:message bundle="mraprorationlogresources" key="mailtracking.mra.defaults.prorationlog.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/defaults/ProrationLog_Script.jsp"/>
 </head>

<body class="ic-center" style="width:73%;">
	
	
	
	
	
	


	<div id="pageDiv" class="iCargoContent ic-masterbg">
		<ihtml:form action="/mailtracking.mra.defaults.prorationlog.screenload.do">

			<bean:define id="form"
				name="MRAProrationLogForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm"
				toScope="page" />
			<business:sessionBean id="mailProrationLogVOs" 
				 moduleName="mailtracking.mra.defaults" 
				 screenID="mailtracking.mra.defaults.prorationlog"
				 method="get" 
				 attribute="MailProrationLogVOs" />
			<business:sessionBean id="triggerPoints" 
				 moduleName="mailtracking.mra.defaults" 
				 screenID="mailtracking.mra.defaults.prorationlog"
				 method="get" 
				 attribute="TriggerPoints" />
				 
			<business:sessionBean id="mailProrationLogVOMap"
				moduleName="mailtracking.mra.defaults"
				screenID="mailtracking.mra.defaults.prorationlog" 
				method="get"
				attribute="mailProrationLogVOMap" />
				 
			<!--hidden fields-->
			<ihtml:hidden property="prorateFlag" />
			<ihtml:hidden property="fromScreen" />
			 <input type="hidden" name="currentDialogOption" />
			 <input type="hidden"  name="currentDialogId" />
			<ihtml:hidden property="showDsnPopUp" />


	
	
	<div class="ic-content-main">
	    <span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.mra.defaults.prorationlog.pagetitle" />
		</span>	
		<div class="ic-head-container">
			    <div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input ic-split-50 ic-mandatory">
						<label>
							<common:message key="mailtracking.mra.defaults.prorationlog.lbl.dsn" scope="request"/>
						</label>	
							<ihtml:text property="dsn" componentID="CMP_MRA_DEFAULTS_PRORATIONLOG_DSN"  maxlength="29" />
							<div class="lovImg">
  							 <img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" name="dsnlov" height="22" width="22" alt=""/> 
						   </div>
						</div>
						<div class="ic-button-container">
							<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_PRORATIONLOG_LIST" accesskey="L" >
							<common:message key="mailtracking.mra.defaults.prorationlog.button.list" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_PRORATIONLOG_CLEAR" accesskey="C" >
							<common:message key="mailtracking.mra.defaults.prorationlog.button.clear" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="mailtracking.mra.defaults.prorationlog.tabletitle" /></h4>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer table-border-solid"  style="width:100%;height:850px;"><!--modified by a-7531-->
					<table class="fixed-header-table" id="prorationlogtable">

					<thead>
						<tr class="ic-th-all">
									<th style="width: 1%" />
									<th style="width: 4%" />
									<th style="width: 3%" />
									<th style="width: 3%" />
									<th style="width: 7%" />
									<th style="width: 7%" />
									<th style="width: 6%" />
									<th style="width: 4%" />
									<th style="width: 7%" />
									
								</tr>

						<tr class="iCargoTableHeadingLeft">
						<td rowspan="2" align="center" width="1%">&nbsp;</td>
						<td rowspan="2"  width="4%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.versionno" /></td>
						<td colspan="2"  width="7%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.carrriage" /></td>
						<td rowspan="2"  width="7%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.carriercode" /></td>
						<td rowspan="2"  width="6%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.triggerpoint" /></td>
						<td rowspan="2"  width="4%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.dateandtime"/></td>
						<td rowspan="2"  width="7%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.user" /></td>
						<td rowspan="2"  width="7%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.remarks" /></td>

						</tr>
						<tr>
						<td  width="3%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.carriagefrom" /></td>
						<td  width="3%"><common:message   key="mailtracking.mra.defaults.prorationlog.lbl.carriageto" /></td>
					</tr>
				</thead>			
					
					
				<tbody>
		
					<%int vercount=0;%>
					<logic:present name="mailProrationLogVOMap">
						<bean:define id="mailProrationLogVOMap" name="mailProrationLogVOMap" type="java.util.HashMap" />
						<logic:iterate id="logvo" name="mailProrationLogVOMap"  indexId="rowCount">
							<common:rowColorTag index="rowCount" >
								<logic:present name="logvo" property="key">
									<%vercount++;%>
								</logic:present>
	
								<tr class="iCargoTableDataRow1" bgcolor="<%=color%>">
									<td class="iCargoTableTd">
										<input type="checkbox" name="checkBoxLog" value="<%=String.valueOf(vercount)%>" onclick="singleSelectCb(this.form,'<%=String.valueOf(vercount)%>','checkBoxLog');"/>
									</td>
									<td>
										<center>
											<logic:present name="logvo" property="key">
												<bean:define id="keyValue" name="logvo" property="key"/>
												<common:write name="keyValue" />
											</logic:present>
										</center>
									</td>
					
									<td colspan="2" >
										<%int count=0;%>
										<logic:present  name="logvo" property="value"   >
											<bean:define id="innerlogvos" name="logvo" property="value" type="java.util.Collection" />
											<table class="iCargoBorderLessTable" >
												<logic:iterate id="vo" name="innerlogvos"   >
													<%count++;%>
														<logic:present  name="vo" >
															<tr  border=0 >
																<td class="iCargoTableTd" width="50%">
																	<center>
																		<logic:present name="vo" property="carriageFrom">
																			<bean:write name="vo" property="carriageFrom" />	
																		</logic:present>
																		<logic:notPresent name="vo" property="carriageFrom">
																		<bean:write name="form" property="carriageFrom" />	
																		</logic:notPresent>
																	</center>
																</td>
																<td class="iCargoTableTd" width="50%">
																	<center>
																		<logic:present name="vo" property="carriageTo">
																			<bean:write name="vo" property="carriageTo" />	
																		</logic:present>
																		<logic:notPresent name="vo" property="carriageTo">
																			<bean:write name="form" property="carriageTo" />	
																		</logic:notPresent>
																	</center>
																</td>
															</tr>
														</logic:present>
													</logic:iterate>	
												</table>
											</logic:present>
										</td>
										<td>
											<center>
												<logic:present name="vo" property="carrierCode">
													<bean:write name="vo" property="carrierCode" />	
												</logic:present>
												<logic:notPresent name="vo" property="carrierCode">
													<bean:write name="form" property="carrierCode" />	
												</logic:notPresent>
											</center>
										</td>
										<td>
											<center>
												<logic:present name="vo" property="triggerPoint">
													<bean:write name="vo" property="triggerPoint" />	
												</logic:present>
												<logic:notPresent name="vo" property="triggerPoint">
													<bean:write name="form" property="triggerPoint" />	
												</logic:notPresent>
											</center>
										</td>
										<td>
											<center>
												<logic:present name="vo" property="dateTime">
													<bean:define id="sDate" name="vo" property="dateTime" />
													<% String newDate = ((LocalDate)sDate).toDisplayDateOnlyFormat(); %>
													<% String newTime = ((LocalDate)sDate).toDisplayTimeOnlyFormat(); %>
													<% String dateFinal=(newDate+','+newTime);%>
													<%= dateFinal%>
												</logic:present>
												<logic:notPresent name="vo" property="dateTime">
													<bean:write name="form" property="dateTime" />	
												</logic:notPresent>
											</center>
										</td>
										<td>
											<center>
												<logic:present name="vo" property="user">
													<bean:write name="vo" property="user" />	
												</logic:present>
												<logic:notPresent name="vo" property="user">
													<bean:write name="form" property="user" />	
												</logic:notPresent>
											</center>
										</td>
										<td>
											<center>
												<logic:present name="vo" property="remarks">
													<bean:write name="vo" property="remarks" />	
												</logic:present>
												<logic:notPresent name="vo" property="remarks">
													<bean:write name="form" property="remarks" />	
												</logic:notPresent>
											</center>
										</td>
								</tr>
							</common:rowColorTag>
						</logic:iterate>
					</logic:present>	
				</tbody>
			</table>
		</div>
	</div>
	</div>
	<div class="ic-foot-container">
	<div class="ic-row">
		<div class="ic-button-container">
			<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_PRORATIONLOG_VIEWPRORATION" property="btViewProration" accesskey="V" >
						<common:message key="mailtracking.mra.defaults.prorationlog.button.viewproration"/>
			</ihtml:nbutton>
			<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_PRORATIONLOG_CLOSE" property="btClose" accesskey="O" >
						<common:message key="mailtracking.mra.defaults.prorationlog.button.close"/>
			</ihtml:nbutton>
		</div>
	</div>	
</div>
</ihtml:form>
</div>
	
	</body>
</html:html>
