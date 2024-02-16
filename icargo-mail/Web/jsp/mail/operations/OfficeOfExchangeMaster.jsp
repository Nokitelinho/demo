<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  OfficeOfExchangeMaster.jsp
* Date					:  08-June-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "java.util.Calendar" %>
 <%@ page import="java.util.HashMap"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
	 <html:html>

 <head>
   	<title><common:message bundle="officeOfExchangeResources" key="mailtracking.defaults.oemaster.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/OfficeOfExchangeMaster_Script.jsp"/>

 </head>

 <body>

	<bean:define id="form"
		 name="OfficeOfExchangeMasterForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm"
		 toScope="page" />

	<business:sessionBean id="officeOfExchangeVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.officeofexchange"
		 method="get"
	 	 attribute="officeOfExchangeVOs"/>

	<div  class="iCargoContent ic-masterbg" >
		<ihtml:form action="/mailtracking.defaults.oemaster.screenload.do">
		<ihtml:hidden property="popUpStatus"/>
 		<ihtml:hidden property="status"/>
		<!--Added by A-8527 for bug IASCB-30982 starts-->
		<ihtml:hidden property="ooexchfltrval"/>
		<!--Added by A-8527 for bug IASCB-30982 ENDS-->
 		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="displayPage" />
			<div class="ic-content-main">
			
				<div class="ic-head-container">
			
					<div class="ic-filter-panel">
						<div class="ic-row">
						
							<h4> <common:message key="mailtracking.defaults.oemaster.lbl.search"/> </h4>
						
					</div>
						<div class="ic-row">
							<div class="ic-col-70 ic-input ic-pad-10">
								<label class="ic-label-50">
									<common:message key="mailtracking.defaults.oemaster.lbl.code"/>
								</label>
								<ihtml:text property="officeOfExchange" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_OE" maxlength="6"/>
								<div class="lovImg"><img id="oeLOV" value="oeLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>
							</div>
							<div class="ic-col-30 ic-right">						
								<div class="ic-button-container">
									<ihtml:nbutton property="btList" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_LIST" accesskey="L">
									<common:message key="mailtracking.defaults.oemaster.btn.list" />
									</ihtml:nbutton>

									<ihtml:nbutton property="btClear" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_CLEAR" accesskey="C">
									<common:message key="mailtracking.defaults.oemaster.btn.clear" />
									</ihtml:nbutton>
								</div>
							</div>	
						</div>
					</div>
				
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						
							<h4><common:message key="mailtracking.defaults.oemaster.lbl.details"/></h4>
						
						<div class="ic-row">
							<div class="ic-col-60">
								<%String lstPgNo = "";%>
								<logic:present name="OfficeOfExchangeMasterForm" property="lastPageNum">
								<bean:define id="lastPg" name="OfficeOfExchangeMasterForm" property="lastPageNum" scope="request" toScope="page" />
										<%
											lstPgNo = (String) lastPg;
										%>
								</logic:present>
								<logic:present name="officeOfExchangeVOs" >
									<bean:define id="pageObj" name="officeOfExchangeVOs"   toScope="page" />
									<common:paginationTag pageURL="mailtracking.defaults.oemaster.list.do"
									name="pageObj"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=lstPgNo%>" />
								</logic:present>
							</div>
							<div class="ic-col-40">
							<div class="ic-button-container ic-pad-5">
								<logic:present name="officeOfExchangeVOs" >
								<bean:define id="pageObj1" name="officeOfExchangeVOs"   toScope="page" />
								<common:paginationTag
									linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									pageURL="javascript:submitPage('lastPageNum','displayPage')"
									name="pageObj1"
									display="pages"
									lastPageNum="<%=lstPgNo%>"
									exportToExcel="true"
									exportTableId="officeMaster"
									exportAction="mailtracking.defaults.oemaster.list.do"/>
								</logic:present>
								<logic:notPresent name="officeOfExchangeVOs">
									&nbsp;
								</logic:notPresent>

							
							<a id="addLink" name="formlink" class="iCargoLink" href="#" value="add">
								<common:message key="mailtracking.defaults.oemaster.lbl.add" />
							</a>
							|
							<a id="deleteLink" name="formlink" class="iCargoLink" href="#" value="delete">
								<common:message key="mailtracking.defaults.oemaster.lbl.delete"/>
							</a>
						</div>
						</div>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer ic-pad-5" id="div1" style="height:635px">
							<table class="fixed-header-table" id="officeMaster">
								<thead>
									<tr >
										<td width="05" height="20" class="iCargoTableHeaderLabel">
											<input type="checkbox" name="checkAll" value="checkbox">
										</td>
										<td width="10" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.country"/>
										</td>
										<td width="20" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.city"/>
										</td>
										<td width="10" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.officecode"/>
										</td>
										<td width="20" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.name"/>
										</td>
										<td width="10" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.pacode"/>
										</td>
										<td width="20" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.arpcod"/>
										</td>
										<td width="05" class="iCargoTableHeaderLabel">
											<common:message key="mailtracking.defaults.oemaster.lbl.active"/>
										</td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="officeOfExchangeVOs">
										<logic:iterate id ="officeOfExchangeVO" name="officeOfExchangeVOs" type="OfficeOfExchangeVO" indexId="rowCount">
											<common:rowColorTag index="rowCount">
												<tr>
													<td class="iCargoTableDataTd">
														<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="countryCode" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_COUNTRY"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="cityCode" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_CITY"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="officeCode" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_OFFICE"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="codeDescription" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_OEDESC"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="poaCode" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_PACODE"/>
													</td>
													<td class="iCargoTableDataTd">
														<ihtml:text property="airportCode" name="officeOfExchangeVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_ARPCOD"/>
													</td>
													<td class="iCargoTableDataTd">
														<div >
															<!--<input type="checkbox" name="active" disabled="true" <%if(officeOfExchangeVO.isActive()){%>checked<%}%>/>-->
															<%if(officeOfExchangeVO.isActive()){%>
															<img id="isActive" src="<%=request.getContextPath()%>/images/icon_on.gif" />
															<%}else {%>
															<img id="isNotActive" src="<%=request.getContextPath()%>/images/icon_off.gif" />
															<%}%>
														</div>
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
					<div class="ic-button-container ic-pad-5">
						<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_OEMASTER_CLOSE" accesskey="O">
							<common:message key="mailtracking.defaults.oemaster.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			
		</ihtml:form>
	</div>
    
	
				
		<jsp:include page="/jsp/includes/footerSection.jsp"/>
			
		<logic:present name="popup">        		
		<logic:present name="icargo.uilayout">
			<logic:equal name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
			</logic:equal>

			<logic:notEqual name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/popupfooter_new.jsp" />
			</logic:notEqual>
		</logic:present>
		</logic:present>
		
		<logic:notPresent name="popup">	
		<logic:present name="icargo.uilayout">
			<logic:equal name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/footer_new_ui.jsp" />
			</logic:equal>

			<logic:notEqual name="icargo.uilayout" value="true">
			<jsp:include page="/jsp/includes/footer_new.jsp" />
			</logic:notEqual>
		</logic:present>		
		</logic:notPresent>
		
		<common:registerCharts/>
		<common:registerEvent />
	</body>
 </html:html>
