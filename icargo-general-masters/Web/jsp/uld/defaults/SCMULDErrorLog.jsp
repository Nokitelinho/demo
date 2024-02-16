<%--
* Project	 		: iCargo
* Module Code & Name: ULD:SCM ULD Error Log
* File Name			: SCMscmulderrorlog.jsp
* Date				: 19/07/06
* Author(s)			: Pradeep S: A-2046
--%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
	if (request.getProtocol().equals("HTTP/1.1")) {
	response.setHeader("Cache-Control","no-cache");
	}
%>
		
<html:html>
<head>
		
		
	
	<title>
		<common:message bundle="scmUldReconcileResources" key="uld.defaults.messaging.scmulderrorlog.pagetitle" scope="request"/>
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/uld/defaults/SCMULDErrorLog_Script.jsp"/>
</head>
<body >
	
	

	<bean:define id="form"
	name="SCMULDErrorLogForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm"
	toScope="page" />
	

	<business:sessionBean id="scmFilterVO"
	moduleName="uld.defaults"
	screenID="uld.defaults.scmulderrorlog"
	method="get" attribute="sCMULDFilterVO" />

	<business:sessionBean id="errorDesc"
	moduleName="uld.defaults"
	screenID="uld.defaults.scmulderrorlog"
	method="get" attribute="ErrorDescriptions" />
	
<div id="pageDiv" class="iCargoContent ic-masterbg"> 
<ihtml:form action="/uld.defaults.messaging.screenloadscmulderrorlog.do">
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />

	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="lastPageNum" />
	<ihtml:hidden property="rowIndex" />
	<ihtml:hidden property="returnTxn" />
	<ihtml:hidden property="pageUrl" />
	<ihtml:hidden property="scmULDDisable" />
	<ihtml:hidden property="listStatus" />	
	
<div class="ic-content-main bg-white">
		<span class="ic-page-title"><common:message  key="uld.defaults.messaging.scmulderrorlog.title" /></span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
						<div class="ic-row">
							<h4><common:message key="uld.defaults.messaging.scmulderrorlog.search" /></h4>
						</div>
					<div class="ic-input-container">
						
						<div class="ic-section ">
							<div class="ic-row ic-label-45">
								<div class="ic-input ic-split-10 ic-mandatory">
									<label><common:message  key="uld.defaults.messaging.scmulderrorlog.airport" /></label>
									<logic:present name="scmFilterVO" property="airportCode">
									<bean:define id="airportFromVO" name="scmFilterVO" property="airportCode" />
									<ihtml:text  componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_AIRPORT" property="scmUldAirport"  value="<%=(String)airportFromVO%>" maxlength="3" />
									</logic:present>
									<logic:notPresent name="scmFilterVO" property="airportCode">
									<ihtml:text componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_AIRPORT" property="scmUldAirport" maxlength="3" />
									</logic:notPresent>
									<div class="lovImg"><img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].scmUldAirport.value,'CurrentAirport','1','scmUldAirport','',0)" alt="Airport LOV"/></div>
								</div>
								
								<div class="ic-input ic-split-15 ic-mandatory">
									<label><common:message key="uld.defaults.scmulderrorlog.lbl.airline" /></label>
									<logic:present name="scmFilterVO" property="airlineCode">
									<bean:define id="airlineFromVO" name="scmFilterVO" property="airlineCode" />
									<ihtml:text property="airline"  maxlength="3" value="<%=(String)airlineFromVO%>" componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_AIRLINE"/>
									</logic:present>
									<logic:notPresent name="scmFilterVO" property="airlineCode">
									<ihtml:text property="airline"  maxlength="3" componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_AIRLINE"/>
									</logic:notPresent>
									<div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="airlinelov" alt="Airline LOV"/></div>
								</div>
								
								<div class="ic-input ic-split-15 ic-mandatory">
									<label><common:message key="uld.defaults.scmulderrorlog.lbl.scmseqno" /></label>
									<logic:present name="scmFilterVO" property="sequenceNumber">
									<bean:define id="seqFromVO" name="scmFilterVO" property="sequenceNumber" />
									<ihtml:text property="scmSeqNo"  maxlength="10" value="<%=(String)seqFromVO%>" readonly="true" componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_SEQNO"/>
									</logic:present>
									<logic:notPresent name="scmFilterVO" property="sequenceNumber">
									<ihtml:text property="scmSeqNo"  maxlength="10"  componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_SEQNO"/>
									</logic:notPresent>
									<div class="lovImg"><img name="scmseqnolov" id="scmseqnolov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="SCM Seq No.LOV "/></div>
								</div>
								
								<div class="ic-input ic-split-15">
									<label><common:message key="uld.defaults.scmulderrorlog.lbl.stockchkdate" /></label>
									<%String stkDate="";%>
									<logic:present name="scmFilterVO" property="stockControlDate">
									<bean:define id="dateFromVO" name="scmFilterVO" property="stockControlDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
									<%stkDate=dateFromVO.toDisplayDateOnlyFormat();%>
									<ihtml:text property="stockCheckdate" value="<%=stkDate%>" componentID="ULD_DEFAULTS_SCMULDRECONCILE_STOCK_DATE" />
									</logic:present>
									<logic:notPresent name="scmFilterVO" property="stockControlDate">
									<ihtml:text property="stockCheckdate" componentID="ULD_DEFAULTS_SCMULDRECONCILE_STOCK_DATE"/>
									</logic:notPresent>
								</div>
								
								<div class="ic-input ic-split-10">
									<label><common:message key="uld.defaults.messaging.stockchecktime" /></label>
									<%String stkTime="";%>
									<logic:present name="scmFilterVO" property="stockControlDate">
									<bean:define id="timeFromVO" name="scmFilterVO" property="stockControlDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
									<%stkTime=timeFromVO.toDisplayTimeOnlyFormat().substring(0,5);%>
									<ibusiness:releasetimer  property="scmStockCheckTime" title="Stock Check Time"  value="<%=stkTime%>" type="asTimeComponent" id="stkChkTime"  readonly="true"/>
									</logic:present>
									<logic:notPresent  name="scmFilterVO" property="stockControlDate" >
									<ibusiness:releasetimer  property="scmStockCheckTime" title="Stock Check Time"   type="asTimeComponent" id="stkChkTime"  readonly="true"/>
									</logic:notPresent>
								</div>
								<div class="ic-input ic-split-15 ">
									<label><common:message  key="uld.defaults.scmulderrorlog.lbl.uldno" /></label>
									<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_SCMULDERRORLOG_ULDNO" style="text-transform: uppercase" maxlength="12"/>
								</div>
								
								
								
								<div class="ic-input ic-split-15">
									<label><common:message  key="uld.defaults.scmulderrorlog.lbl.errordesciption" /></label>
									<logic:present name="errorDesc">
									<bean:define id="errorCode" name="errorDesc"/>
									<ihtml:select property="errorDescription" componentID="COMBO_ULD_DEFAULTS_SCMULDERRORLOG_ERROR_DESC" style="width:150px">
										<html:option value=""><common:message key="combo.select"/></html:option>
										<logic:iterate id="errorDescription" name="errorCode">
											<logic:present name="errorDescription" property="fieldDescription">
												<bean:define id="errorDesc" name="errorDescription" property="fieldDescription" />
													<logic:present name="errorDescription" property="fieldValue">
														<bean:define id="errorValue" name="errorDescription" property="fieldValue" />
														<html:option value="<%=(String)errorValue%>">
															<bean:write name="errorDesc" />
														</html:option>
													</logic:present>
											</logic:present>
										</logic:iterate>
									</ihtml:select>
									</logic:present>

									<logic:notPresent name="errorDesc">
									<ihtml:select property="errorDescription" componentID="COMBO_ULD_DEFAULTS_SCMULDERRORLOG_ERROR_DESC" style="width:150px">
										<html:option value=""/>
									</ihtml:select>
									</logic:notPresent>
								</div>
								<div class="ic-button-container paddR5">
									<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_LIST" accesskey="L">
						<common:message  key="uld.defaults.scmulderrorlog.btn.list" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_CLEAR" accesskey="C">
						<common:message  key="uld.defaults.scmulderrorlog.btn.clear" />
						</ihtml:nbutton>
								</div>
							</div>
						</div>	
					</div>
				</div>
			</div>
			
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message  key="ULD.DEFAULTS.SCMulderror" scope="request"/></h4>
				</div>
				<div  class="ic-row paddR5">
					<business:sessionBean id="reconcileVOS"
					moduleName="uld.defaults"
					screenID="uld.defaults.scmulderrorlog"
					method="get" attribute="sCMReconcileDetailVOs" />

					<logic:present name="reconcileVOS">
					<bean:define id="scmReconcileVOs" name="reconcileVOS" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
					</logic:present>
				<div class="ic-button-container">
					<business:sessionBean id="reconcileVOS"
					moduleName="uld.defaults"
					screenID="uld.defaults.scmulderrorlog"
					method="get" attribute="sCMReconcileDetailVOs" />

					<logic:present name="reconcileVOS">
					<bean:define id="scmReconcileVOs" name="reconcileVOS" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
					<%if (scmReconcileVOs.size()>0){%>
					<common:paginationTag
					name="scmReconcileVOs"
					pageURL="javascript:submitscmulderrorlog('lastPageNum','displayPage')"
					display="label"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=((SCMULDErrorLogForm)form).getLastPageNum() %>"/>
					<%} %>
		
					
					<%if (scmReconcileVOs.size()>0){%>
					<common:paginationTag
					name="scmReconcileVOs"
				
					pageURL="javascript:submitscmulderrorlog('lastPageNum','displayPage')"
					display="pages"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					lastPageNum="<%=((SCMULDErrorLogForm)form).getLastPageNum()%>"
					exportToExcel="true"
					exportTableId="scmULDErrorTable"
					exportAction="uld.defaults.messaging.listscmulderrorlog.do"/>
					<%} %>
					</logic:present>
				</div>
				</div>
			
				<div class="ic-row">
					<div id="div3" class="tableContainer" style="height:500px;">
						<table class="fixed-header-table" id="scmULDErrorTable">
							<thead>
								<tr>
									<td width="5%"><input type="checkbox" name="checkUldsAll" id="headerCheckBox" value="checkbox" class="marginT5 marginB5" /></td>
									<td width="20%" ><common:message key="uld.defaults.scmulderrorlog.lbl.scmseqno" scope="request"/></td>
									<td width="25%" ><common:message key="uld.defaults.scmulderrorlog.lbl.uldno" scope="request"/></td>
									<td width="25%" ><common:message key="uld.defaults.scmulderrorlog.lbl.station" scope="request"/></td>
									<td width="20%" ><common:message key="uld.defaults.scmulderrorlog.lbl.errordesc" scope="request"/></td>
									<td width="20%"><common:message key="uld.defaults.scmulderrorlog.lbl.reconcile" scope="request"/></td>
								</tr>
							</thead>
							<tbody>
									<logic:present name="reconcileVOS">
									<bean:define id="requestPage" name="reconcileVOS" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
									<logic:iterate  id="scmUldDetailsVO"  name="requestPage" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO" indexId="indexId">
									
								<tr> 
									<td  class="iCargoTableDataTd"  style="text-align:center;">
									<ihtml:checkbox property="selectedUlds" value="<%=String.valueOf(indexId)%>"/>
									</td>
									
									<td class="iCargoTableDataTd ic-center">
									<logic:present name="scmUldDetailsVO" property="sequenceNumber">
									<bean:write name="scmUldDetailsVO" property="sequenceNumber"/>
									</logic:present>
									</td>
									
									<td   style="height:25px" class="iCargoTableDataTd ic-center">
									<logic:present name="scmUldDetailsVO" property="uldNumber">
									<bean:write name="scmUldDetailsVO" property="uldNumber"/>
									</logic:present>
									</td>
			
									<td   class="iCargoTableDataTd ic-center">
									<logic:present name="scmUldDetailsVO" property="airportCode">
									<bean:write name="scmUldDetailsVO" property="airportCode"/>
									</logic:present>
									</td>
			
									<td   class="iCargoTableDataTd ic-center">
									<logic:present name="scmUldDetailsVO" property="errorCode">
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR1">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error1" />
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR2">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error2" />
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR3">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error3" />
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR4">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error4" />
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR5">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error5" />
									</logic:equal>
									</logic:present>
			
									<logic:notPresent name="scmUldDetailsVO" property="errorCode">
									<logic:present name="scmUldDetailsVO" property="uldStatus">
									<logic:equal name="scmUldDetailsVO" property="uldStatus" value="M">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error1" />
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="uldStatus" value="F">
									<common:message key="uld.defaults.scmulderrorlog.errorcode.error2" />
									</logic:equal>
									</logic:present>
									</logic:notPresent>
									</td>
			
									<td   class="iCargoTableDataTd ic-center">
									<logic:present name="scmUldDetailsVO" property="errorCode">
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR1">
									<ihtml:button
									indexId="indexId"
									styleId="errorcodelov"
									property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_ERRORBTN"
									onclick="reconcileULD('errorcodelov',this)"  >
									<bean:message bundle="scmUldReconcileResources" key="uld.defaults.scmulderrorlog.btn.errordesc" />
									</ihtml:button>
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR2">
									<ihtml:button
									indexId="indexId"
									styleId="errorcodelov"
									property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_ERRORBTN"
									onclick="reconcileULD('errorcodelov',this)"  >
									<common:message key="uld.defaults.scmulderrorlog.btn.errordesc" />
									</ihtml:button>
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR3">
									<ihtml:button
									indexId="indexId"
									styleId="errorcodelov"
									property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_ERRORBTN"
									onclick="reconcileULD('errorcodelov',this)"  >
									<common:message key="uld.defaults.scmulderrorlog.btn.errordesc" />
									</ihtml:button>
									</logic:equal>
									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR4">
									<ihtml:button
									indexId="indexId"
									styleId="errorcodelov"
									property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_ERRORBTN"
									onclick="reconcileULD('errorcodelov',this)"  >
									<common:message key="uld.defaults.scmulderrorlog.btn.errordesc" />
									</ihtml:button>
									</logic:equal>

									<logic:equal name="scmUldDetailsVO" property="errorCode" value="ERR5">
									<ihtml:button
									indexId="indexId"
									styleId="errorcodelov"
									property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_ERRORBTN"
									onclick="reconcileULD('errorcodelov',this)"  >
									<common:message key="uld.defaults.scmulderrorlog.btn.errordesc" />
									</ihtml:button>
									</logic:equal>
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
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnDelete"  componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_DELETE" accesskey="E">
						<common:message key="uld.defaults.scmulderrorlog.btn.delete" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_SCMULDERRORLOG_CLOSE" accesskey="O">
						<common:message key="uld.defaults.scmulderrorlog.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
</div>
</ihtml:form>
</div>

				
		
	</body>
</html:html>

