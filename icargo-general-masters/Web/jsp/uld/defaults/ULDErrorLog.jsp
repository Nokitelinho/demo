<%--
* Project	 		: iCargo
* Module Code & Name: ULD:ULD Error Log
* File Name			: ULDErrorLog.jsp
* Date				: 19/07/06
* Author(s)			: Anitha George M : A-1862
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
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
		<bean:message bundle="ulderrorlogResources" key="uld.defaults.messaging.ulderrorlog.uldErrorLogTitle" scope="request"/>
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/uld/defaults/ULDErrorLog_Script.jsp"/>
</head>
<body >
	
	

<div  id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto">
	<bean:define id="form"
	name="ULDErrorLogForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm"
	toScope="page" />
	<ihtml:form action="/uld.defaults.messaging.screenloadulderrorlog.do">
	<business:sessionBean id="ucmNumberValues"
	moduleName="uld.defaults"
	screenID="uld.defaults.ulderrorlog"
	method="get" attribute="ucmNumberValues" />

	<business:sessionBean id="pouValues"
	moduleName="uld.defaults"
	screenID="uld.defaults.ulderrorlog"
	method="get" attribute="pouValues" />

	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<%--<ihtml:hidden property="screenStatusFlag"/>--%>
	<ihtml:hidden property="screenStatusValue"/>
	<ihtml:hidden property="pageURL"/>
	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="lastPageNumber" />
	<ihtml:hidden property="dupStat"/>
	<ihtml:hidden property="uldrowindex"/>
	<ihtml:hidden property="returnTxn"/>
	<ihtml:hidden property="uldDisableStat"/>
	<ihtml:hidden property="errorCode"/>
	<ihtml:hidden property="gha"/>
	<ihtml:hidden property="airlinegha"/>
	<ihtml:hidden property="screenFlag"/>

	<%  ULDErrorLogForm frm = (ULDErrorLogForm)request.getAttribute("ULDErrorLogForm");%>
	<div class="ic-content-main bg-white">
            <span class="ic-page-title ic-display-none">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.messaging.ulderrorlog.uldErrorLogTitle" />
			</span>
		   <div class="ic-head-container">	
				      <div class="ic-filter-panel">   
                        <div class="ic-input-container">
			  		    <div class="ic-row">
								<h4><bean:message bundle="ulderrorlogResources" key="uld.defaults.messaging.ulderrorlog.search" /></h4>
		                </div>
						
						<div class="ic-row">
					     <div class="ic-input ic-split-30 ic-mandatory " >
							    <%String carrierCode=(String)form.getCarrierCode();%>
						<%String flightNo=(String)form.getFlightNo();%>
						<%String flightDate=(String)form.getFlightDate();%>

							<ibusiness:flight
							id="flightNo"
							carrierCodeMaxlength="3"
							flightCodeMaxlength="5"
							carrierCodeProperty="carrierCode"
							flightCodeProperty="flightNo"
							calendarProperty="flightDate"
							carriercodevalue="<%=(String)carrierCode%>"
							flightcodevalue="<%=(String)flightNo%>"
							calendarValue="<%=(String)flightDate%>"
							carrierCodeStyleClass="iCargoTextFieldVerySmall"
							flightCodeStyleClass="iCargoTextFieldSmall"
							type="image"
							componentID="ULD_DEFAULTS_ULDERRORLOG_FLIGHT" />
						</div>	  
                        <div class="ic-input ic-split-20 ic-mandatory" >
							    <label>
                            	<bean:message bundle="ulderrorlogResources" key="uld.defaults.messaging.ulderrorlog.airport" />
                               </label>
							   <ihtml:text componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_AIRPORT"  property="ulderrorlogAirport"  name="ULDErrorLogForm" style="text-transform : uppercase" maxlength="3" />
			                 	<div class="lovImg">
							   <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airportLov" id="airportLov"   alt="Airport LOV"/>
						</div>	
						</div>	
 					
                         <div class="ic-input ic-split-20 ic-mandatory" >
							    <label>
								<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.messagetype" />
                            	
                               </label>
							   <ihtml:select property="messageType" name="ULDErrorLogForm" componentID="ULD_DEFAULTS_ULDERRORLOG_MSGTYPE">
								<html:option value="IN">
								<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.in" />
								</html:option>
								<html:option value="OUT">
								<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.out" />
								</html:option>
								</ihtml:select>
						</div>	 
							 <div class="ic-input ic-split-15" >
							    <label>
                            	<bean:message bundle="ulderrorlogResources" key="uld.defaults.messaging.ulderrorlog.uldno" />
                               </label>
							   <ibusiness:uld id="uldno" uldProperty="ulderrorlogULDNo" componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_ULDNO" style="text-transform: uppercase" maxlength="12"/>
                          </div>  
                  
					      
						 
						
						 <div class="ic-button-container paddR5">
											<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_LIST" accesskey="L">
								<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_CLEAR" accesskey="C">
								<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.clear" />
								</ihtml:nbutton>
						  </div>
						 
                  </div>  
</div> 				  
                           						
		</div> 
			</div> 
			
			
		 <div class="ic-main-container">		
					    <div class="ic-row">
                     <h3>   <common:message  key="ULD.DEFAULTS.ULDreconcile" scope="request"/>  </h3>
                        </div>	
			
	                  <div class="ic-row">
		<business:sessionBean id="ULDFlightMessageReconcileDetailsVOs" moduleName="uld.defaults" screenID="uld.defaults.ulderrorlog" method="get" attribute="ULDFlightMessageReconcileDetailsVOs" />
		<logic:present name="ULDFlightMessageReconcileDetailsVOs">
		<bean:define id="ULDFlightMessageReconcileDetailsVOs" name="ULDFlightMessageReconcileDetailsVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
		</logic:present>
			
		<business:sessionBean id="ULDFlightMessageReconcileDetailsVOs" moduleName="uld.defaults" screenID="uld.defaults.ulderrorlog" method="get" attribute="ULDFlightMessageReconcileDetailsVOs" />
		<logic:present name="ULDFlightMessageReconcileDetailsVOs">
		<bean:define id="ULDFlightMessageReconcileDetailsVOs" name="ULDFlightMessageReconcileDetailsVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
		<div class="ic-col-50">
		<%if (ULDFlightMessageReconcileDetailsVOs.size()>0){%>
		<common:paginationTag
		name="ULDFlightMessageReconcileDetailsVOs"
		pageURL="javascript:submitULDErrorLog('lastPageNum','displayPage')"
		display="label"
		linkStyleClass="iCargoLink"
		disabledLinkStyleClass="iCargoLink"
		labelStyleClass="iCargoResultsLabel"
		lastPageNum="<%=frm.getLastPageNumber() %>"/>
		<%} %>
		</div>
		<div class="ic-button-container">
		<%if (ULDFlightMessageReconcileDetailsVOs.size()>0){%>
		<common:paginationTag
		name="ULDFlightMessageReconcileDetailsVOs"
		linkStyleClass="iCargoLink"
		
		pageURL="javascript:submitULDErrorLog('lastPageNum','displayPage')"
		display="pages"
	
		disabledLinkStyleClass="iCargoResultsLabel"
		lastPageNum="<%=frm.getLastPageNumber()%>"
		exportToExcel="true"
		exportTableId="uldErrorLogTable"
		exportAction="uld.defaults.messaging.listulderrorlog.do"/>
		<%} %>
		<a href="#" class="iCargoLink" id="btnAdd"><bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.add" /></a>
				| <a href="#" class="iCargoLink" id="btnDelete"><bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.delete" /></a>
		</div>
		</logic:present>
		
				
		
		
	</div>
	 <div class="ic-row">
	 	<div class="tableContainer"  id="div1"  style="height:700px;">
						  <table width="100%" class="fixed-header-table"  id="uldErrorLogTable">
							<thead>
							  <tr class="iCargoTableHeadingLeft">
                                 <td width="5%" class="iCargoTableHeader">
					             <input type="checkbox" name="selectedULDErrorLogAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck,document.forms[1].selectedUsers)" /><span></span>
                                </td>
                            <td width="10%">
                        	<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.ucmno" />
                           <span></span></td>
                             <td width="10%">
                        	<common:message key="uld.defaults.ulderrorlog.lbl.uldno" scope="request"/>
                           <span></span></td>
                            <td width="08%">
                        	<common:message key="uld.defaults.ulderrorlog.lbl.pou" scope="request"/>
                           <span></span></td>
                            <td width="10%">
                            <common:message key="uld.defaults.ulderrorlog.lbl.content" scope="request"/>
                           <span></span></td>
                             <td width="09%">
                           <common:message key="uld.defaults.ulderrorlog.lbl.flightno" scope="request"/>
                           <span></span></td>
						   <td width="10%">
                           <common:message key="uld.defaults.ulderrorlog.lbl.flightdate" scope="request"/>
                           <span></span></td>
						   <td width="10%">
                            <common:message key="uld.defaults.ulderrorlog.lbl.type" scope="request"/>
                           <span></span></td>
                             <td width="08%">
                          <common:message key="uld.defaults.ulderrorlog.lbl.station" scope="request"/>
                           <span></span></td>
						   <td width="10%">
							<common:message key="uld.defaults.ulderrorlog.lbl.errordesc" scope="request"/>
                           <span></span></td>
						   <td width="10%">
                          <common:message key="uld.defaults.ulderrorlog.lbl.reconcile" scope="request"/>
                           <span></span></td>

			  		  </tr>
					  </thead> 
	 		
		<tbody>
		<logic:present name="ULDFlightMessageReconcileDetailsVOs">
		<bean:define id="requestPage" name="ULDFlightMessageReconcileDetailsVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
		<logic:iterate  id="ULDFlightMessageReconcileDetailsVO"  name="requestPage" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO" indexId="indexId">
		<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="operationFlag" value="D">


					<ihtml:hidden name="ULDErrorLogForm" property="flightNo"/>
					<ihtml:hidden name="ULDErrorLogForm" property="flightDate"/>
					<ihtml:hidden name="ULDErrorLogForm" property="pou"/>
					<ihtml:hidden name="ULDErrorLogForm" property="uldNumber"/>
					<ihtml:hidden name="ULDErrorLogForm" property="sequenceNumber"/>
			   </logic:equal>
		  <logic:notEqual name="ULDFlightMessageReconcileDetailsVO" property="operationFlag" value="D">
		<common:rowColorTag index="indexId">
		<%
		int rows = 1;
		String tableClass = "";
		String fltDate = "";
		if (ULDFlightMessageReconcileDetailsVO.getFlightDate()!= null)
		{
		int index = ULDFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().indexOf(",");
		fltDate = ULDFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().substring(0 ,index);
		}
		%>
		<tr>
            <td class="iCargoTableTd ic-center">


			   <ihtml:checkbox property="selectedULDErrorLog" value="<%=String.valueOf(indexId)%>" />

            </td>
            <%
			            boolean seqreadonly=true;
			            %>
			             <logic:equal name="ULDFlightMessageReconcileDetailsVO" property="operationFlag" value="I">
			           <%  seqreadonly=false; %>
             </logic:equal>
			<td class="iCargoTableDataTd">



						<ihtml:select componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_UCMNO" name="ULDFlightMessageReconcileDetailsVO" property="sequenceNumber" disabled="<%=seqreadonly%>">

						<logic:present name="ucmNumberValues">
							<logic:iterate id="ucmNumberValue" name="ucmNumberValues" type="java.lang.String">



								<ihtml:option value="<%=String.valueOf(ucmNumberValue).toUpperCase() %>"><%=String.valueOf(ucmNumberValue)%></ihtml:option>



							</logic:iterate>
						</logic:present>

			</ihtml:select>

			</td>
			<td class="iCargoTableDataTd ic-center">
			
			<ihtml:text name="ULDFlightMessageReconcileDetailsVO" componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_ULDNUM" property="uldNumber" value="<%=ULDFlightMessageReconcileDetailsVO.getUldNumber()%>" disabled="<%=seqreadonly%>" />
			
			</td>
			<td class="iCargoTableDataTd">


						<ihtml:select componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_POU" name="ULDFlightMessageReconcileDetailsVO" property="pou">

						<logic:present name="pouValues">
							<logic:iterate id="pouValue" name="pouValues" type="java.lang.String">


								<ihtml:option value="<%=String.valueOf(pouValue).toUpperCase() %>"><%=String.valueOf(pouValue)%></ihtml:option>



							</logic:iterate>
						</logic:present>

						</ihtml:select>

			</td>
			<td>

			<business:sessionBean id="uldContent"
			                            moduleName="uld.defaults"
							            screenID="uld.defaults.ulderrorlog" method="get"
										attribute="content"/>

				<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="messageType" value="OUT">
					<ihtml:select componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_CONTENT" name="ULDFlightMessageReconcileDetailsVO" property="content">
						<logic:present name="uldContent">
							 <html:options collection="uldContent" property="fieldValue"  labelProperty="fieldDescription" />
						</logic:present>
					</ihtml:select>
				</logic:equal>
				<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="messageType" value="IN">
					<ihtml:select componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_CONTENT" name="ULDFlightMessageReconcileDetailsVO" property="content" disabled="true">
						<ihtml:option value="" ></ihtml:option>
					</ihtml:select>
				</logic:equal>

			</td>
			<td   class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="carrierCode">
			<bean:write name="ULDFlightMessageReconcileDetailsVO" property="carrierCode"/>
			</logic:present>
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="flightNumber">
			<bean:write name="ULDFlightMessageReconcileDetailsVO" property="flightNumber"/>
			</logic:present>
			
			</td>
			<td class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="flightDate">
			<%=fltDate%>
			</logic:present>
			
			</td>
			<td class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="messageType">
			<bean:write name="ULDFlightMessageReconcileDetailsVO" property="messageType"/>
			</logic:present>
			
			</td>
			<td class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="airportCode">
			<bean:write name="ULDFlightMessageReconcileDetailsVO" property="airportCode"/>
			</logic:present>
			
			</td>
			<td   class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="errorCode">
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E3">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error3" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E4">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error4" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E7">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error7" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E5">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error5" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E6">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error6" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E14">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error14" />
			</logic:equal>
			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E15">
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.errorcode.error15" />
			</logic:equal>
			</logic:present>
			
			</td>
			<td class="iCargoTableDataTd ic-center">
			
			<logic:present name="ULDFlightMessageReconcileDetailsVO" property="errorCode">

			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E3">

					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>

			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E4">
					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>


			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E7">
					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>


			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E5">
					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>

			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E6">
					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>

			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E14">
					<logic:equal name="ULDErrorLogForm" property="gha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="gha" value="Y">
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="N">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  disabled="true">
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					<logic:equal name="ULDErrorLogForm" property="airlinegha" value="Y">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
					onclick="reconcileULD('errorcodelov',this)"  >
					<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
					</ihtml:button>
					</logic:equal>
					</logic:equal>
			</logic:equal>

			<logic:equal name="ULDFlightMessageReconcileDetailsVO" property="errorCode" value="E15">
			<ihtml:button
			indexId="indexId"
			styleId="errorcodelov"
			property="errorcodelov" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_ERRORBTN"
			onclick="reconcileULD('errorcodelov',this)"  >
			<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.errordesc" />
			</ihtml:button>
			</logic:equal>
			</logic:present>
			
			</td>
		</tr>
		</common:rowColorTag>
		</logic:notEqual>
		</logic:iterate>
		</logic:present>
		</tbody>
		</table></div>
	</div>
	
	</div>
	
	
	
	
	<div class="ic-foot-container">						
				    <div class="ic-row">
						<div class="ic-button-container paddR5">
	<ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_SAVE" accesskey="S">
		<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.save" />
	</ihtml:nbutton>
	<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_ULDERRORLOG_CLOSE" accesskey="O">
		<bean:message bundle="ulderrorlogResources" key="uld.defaults.ulderrorlog.btn.close" />
	</ihtml:nbutton>
	</div>
	</div>	
	</div>

	
	</div>
	<span style="display:none" id="tmpSpan"></span>
	</ihtml:form>
	</div>
				
		
	</body>
</html:html>

