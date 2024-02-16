<%--
* Project	 		: iCargo
* Module Code & Name            : ULD:SCM Reconcile
* File Name			: SCMReconcile.jsp
* Date				: 05/08/06
* Author(s)			: Pradeep S : A-2046
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

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

<bean:message bundle="scmReconcileResources" key="uld.defaults.messaging.scmreconcile.scmErrorLogTitle" scope="request"/>

</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/uld/defaults/SCMReconcile_Script.jsp"/>



</head>



<body >

	

<bean:define id="form"
	 name="SCMReconcileForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm"
	 toScope="page" />

<business:sessionBean id="filterVO"
moduleName="uld.defaults"
screenID="uld.defaults.scmreconcile"
method="get" attribute="messageFilterVO" />

<business:sessionBean id="statusValues"
			moduleName="uld.defaults"
			screenID="uld.defaults.scmreconcile" method="get"
			attribute="messageStatus"/>
<div class="iCargoContent ic-masterbg"  style="width:100%;height:100%;overflow:auto;">
<ihtml:form action="/uld.defaults.messaging.screenloadscmreconcile.do">

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />


<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="rowIndex" />
<ihtml:hidden property="scmDisable" />
<ihtml:hidden property="listStatus" />
<ihtml:hidden property="msgFlag" />
<!-- added listflag for ICRD-16060 by A-5142-->
<ihtml:hidden property="listflag" />
<ihtml:hidden property="navigationMode"/>
<div class="ic-content-main bg-white">
		<span class="ic-page-title ic-display-none">
              <bean:message bundle="scmReconcileResources" key="uld.defaults.messaging.scmreconcile.title" />
              </span>
			 <div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<h4><bean:message bundle="scmReconcileResources" key="uld.defaults.messaging.scmreconcile.search" /></h4>
			 </div>
			 <div class="ic-section ">
							<div class="ic-row" >
								<div class="ic-input ic-split-15 ic-mandatory">
									<label><bean:message bundle="scmReconcileResources" key="uld.defaults.messaging.scmreconcile.airport" />
									</label>

			<logic:present name="filterVO" property="airportCode">
			<bean:define id="arpCode" name="filterVO" property="airportCode" />

			<ihtml:text componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_AIRPORT" value="<%=(String)arpCode%>" property="airport"  style="text-transform : uppercase" maxlength="3" />
			</logic:present>
			<logic:notPresent name="filterVO" property="airportCode">
			<ihtml:text componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_AIRPORT" property="airport"  style="text-transform : uppercase" maxlength="3" />
			</logic:notPresent>
					<div class="lovImg"><img src="<%= request.getContextPath()%>/images/lov.png"   width="22" height="22" id="airportlov" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].airport.value,'CurrentAirport','1','airport','',0)" alt="Airport LOV"/></div>

			 </div>
			 <div class="ic-input ic-split-15 ic-mandatory">
					<label><common:message key="uld.defaults.scmreconcile.lbl.airline" /></label>
			 			<logic:present name="filterVO" property="airportCode">
						<bean:define id="airlineFromVO" name="filterVO" property="airlineCode" />

			 			<ihtml:text property="airline" value="<%=(String)airlineFromVO%>" maxlength="3" componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_AIRLINE"/>
			 			</logic:present>
			 			<logic:notPresent name="filterVO" property="airlineCode">
			 			<ihtml:text property="airline" maxlength="3" componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_AIRLINE"/>
			 			</logic:notPresent>

			 			<div class="lovImg"><img height="22"   src="<%=request.getContextPath()%>/images/lov.png" width="22" id="airlinelov" alt="Airline LOV"/></div>
			 </div>
			<div class="ic-input ic-split-20 ">
				<label><common:message key="uld.defaults.scmreconcile.lbl.seqno" /></label>
			<logic:present name="filterVO" property="sequenceNumber">
			<bean:define id="seqFromVO" name="filterVO" property="sequenceNumber" />
			<ihtml:text property="seqNo"  maxlength="10" value="<%=(String)seqFromVO%>" readonly="true" componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_SEQNO"/>
			</logic:present>
			<logic:notPresent name="filterVO" property="sequenceNumber">
			<ihtml:text property="seqNo"  maxlength="10" componentID="TXT_ULD_DEFAULTS_SCMRECONCILE_SEQNO"/>
			</logic:notPresent>
			<div class="lovImg"><img id="scmseqnolov"  src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="SCM Seq No.LOV"/></div>
			</div>
			<div class="ic-input ic-split-20 ">
				<label>	<common:message key="uld.defaults.scmreconcile.lbl.stockchkdate" /></label>
				<%String stkchDate="";%>
					<logic:present name="filterVO" property="stockControlDate">
						<bean:define id="dateFromVO" name="filterVO" property="stockControlDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"  />
						<%stkchDate=dateFromVO.toDisplayDateOnlyFormat();%>

				<ihtml:text property="stockChkdate" value="<%=stkchDate%>" componentID="ULD_DEFAULTS_SCMRECONCILE_STOCK_DATE" readonly="true"/>
				</logic:present>
				<logic:notPresent name="filterVO" property="stockControlDate">
				<ihtml:text property="stockChkdate" componentID="ULD_DEFAULTS_SCMRECONCILE_STOCK_DATE" readonly="true"/>
				</logic:notPresent>

				</div>
			<div class="ic-input ic-split-15 ">
				<label><common:message key="uld.defaults.messaging.stockchecktime" /></label>
					<%String stkTime="";%>
					<logic:present name="filterVO" property="stockControlDate">
						<bean:define id="timeFromVO" name="filterVO" property="stockControlDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
							<%stkTime=timeFromVO.toDisplayTimeOnlyFormat().substring(0,5);%>


				<ibusiness:releasetimer property="scmStockCheckTime" value="<%=stkTime%>" title="Stock Check Time"  type="asTimeComponent" id="stkChkTime"  readonly="true"/>
				</logic:present>
				<logic:notPresent name="filterVO" property="stockControlDate">
				<ibusiness:releasetimer   property="scmStockCheckTime" title="Stock Check Time"  type="asTimeComponent" id="stkChkTime"  readonly="true"/>
				</logic:notPresent>
			</div>
			<div class="ic-input ic-split-15 ">
				<div class="ic-button-container paddR5">
				        <ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_SCMRECONCILE_LIST" accesskey="L">
						<common:message  key="uld.defaults.scmreconcile.btn.list" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_SCMRECONCILE_CLEAR" accesskey="C">
						<common:message  key="uld.defaults.scmreconcile.btn.clear" />
						</ihtml:nbutton>
				</div>
			</div>
        </div>
        </div>
	</div>
        </div>
	</div>
		<div class="ic-main-container">
			<div class="ic-row">
					<h4><common:message  key="ULD.DEFAULTS.SCMerror" scope="request"/></h4>
            </div>
			<div class="ic-row">
					<business:sessionBean id="reconcileVOS"
					moduleName="uld.defaults"
					screenID="uld.defaults.scmreconcile"
					method="get" attribute="SCMReconcileVOs" />
			
				
				<logic:present name="reconcileVOS">
				<bean:define id="SCMReconcileVOs" name="reconcileVOS" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
				<%if (SCMReconcileVOs.size()>0){%>
				<div class="ic-input ic-split-50">
					<common:paginationTag
					name="SCMReconcileVOs"
					pageURL="javascript:submitList('lastPageNum','displayPage')"
					display="label"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=((SCMReconcileForm)form).getLastPageNumber() %>"/>
				</div>
				<%} %>				
				<%if (SCMReconcileVOs.size()>0){%>
					<div class="ic-input ic-split-50">
						<div class="ic-button-container">
					<common:paginationTag
					name="SCMReconcileVOs"
					linkStyleClass="iCargoLink"
					pageURL="javascript:submitList('lastPageNum','displayPage')"
					display="pages"
					
					disabledLinkStyleClass="iCargoLink"
					lastPageNum="<%=((SCMReconcileForm)form).getLastPageNumber()%>"
					exportToExcel="true"
					exportTableId="scmReconcileTable"
					exportAction="uld.defaults.messaging.listscmerrorlog.do?navigationMode=NAVIGATION"/>
						</div>
					</div>
				<%} %>
				</logic:present>
				
			</div>

			<div class="ic-row">
					<div id="div1" class="tableContainer" style="height:500px;">
						<table class="fixed-header-table" id="scmReconcileTable">
                                <thead>
								<tr>
                               <td  width="5%"> <input type="checkbox" name="selectAllSCMErrorLog" id="headerCheckBox" /></td>
                                <td width="25%"><common:message key="uld.defaults.scmreconcile.lbl.seqno" scope="request"/></td>
								<td width="25%"><common:message key="uld.defaults.scmreconcile.lbl.stockchkdate" scope="request"/></td>
							    <td width="25%"><common:message key="uld.defaults.scmreconcile.lbl.msgsnd" scope="request" /></td>
                                <td width="20%"><common:message key="uld.defaults.scmreconcile.lbl.reconcile" scope="request"/> </td>
                              </tr>
               </thead>
                            <tbody>
  					<logic:present name="reconcileVOS">
				    <bean:define id="requestPage" name="reconcileVOS" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>

				    <logic:iterate  id="scmReconcileVO"  name="requestPage" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO" indexId="indexId">
					 <tr >
                        <td   width="4%" height="19"  class="iCargoTableTd ic-center">
                                    <ihtml:checkbox property="selectedSCMErrorLog" value="<%=String.valueOf(indexId)%>" />
                                    </td>
                                  <td   class="iCargoTableDataTd ic-center">
                                   
					<logic:present name="scmReconcileVO" property="sequenceNumber">
					<bean:write name="scmReconcileVO" property="sequenceNumber"/>
					</logic:present>
				    
				  </td>
				 <td   class="iCargoTableDataTd ic-center">
                 	<logic:present name="scmReconcileVO" property="stockCheckDate">
					<%
								String stkChkdate ="";
								if(scmReconcileVO.getStockCheckDate() != null) {
								stkChkdate = TimeConvertor.toStringFormat(
											scmReconcileVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
								}
							%>
							<%=stkChkdate%>


					</logic:present>
				    
				   </td>

					  <td>
								 
									<logic:present name="scmReconcileVO" property="messageSendFlag">
											  <bean:define id="status" name="scmReconcileVO" property="messageSendFlag"/>
														<logic:present name="statusValues">
														<bean:define id="statusType" name="statusValues"/>

														<logic:iterate id="actualStatus" name="statusType">
														<bean:define id="onetimevo" name="actualStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>

														<%if(onetimevo.getFieldValue().equals(status)){%>
														<bean:write name="onetimevo" property="fieldDescription"/>

														<%}%>
														</logic:iterate>
														</logic:present>
														</logic:present>
														

									 </td>

<bean:define id="strMultiselect" name="indexId" />
				  <td   class="iCargoTableDataTd ic-center">
				  

				  <logic:equal name="scmReconcileVO" property="hasUldErrors" value="true">
				    <input type="hidden" name="hiddenError" value="true"/>
								<ihtml:button
								indexId="indexId"
								styleId="errorcodelov"
								property="errorcodelov" componentID="BTN_ULD_DEFAULTS_SCMERRORLOG_ERRORBTN"
								onclick="showULDDetails('errorcodelov',this)"  >


								<common:message key="uld.defaults.scmreconcile.errorcode.viewulderrorlog" />
								</ihtml:button>
								</logic:equal>

							<logic:equal name="scmReconcileVO" property="hasUldErrors" value="false">
							  <input type="hidden" name="hiddenError" value="false"/>
							&nbsp;
							</logic:equal>



					 </td>

                               </tr>
                               </logic:iterate>
                               </logic:present>
			      </tbody>
                              </table>

			            </div></div>
			        </div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
						  <ihtml:nbutton property="btnDetails" componentID="BTN_ULD_DEFAULTS_SCMRECONCILE_DETAILS" accesskey="D">
								<common:message key="uld.defaults.scmreconcile.btn.details" />
						</ihtml:nbutton>

        				<ihtml:nbutton property="btnSend" componentID="BTN_ULD_DEFAULTS_SCMRECONCILE_SEND" accesskey="S">
									<common:message key="uld.defaults.scmreconcile.btn.send" />
						</ihtml:nbutton>

        			<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_SCMRECONCILE_CLOSE" accesskey="O">
						<common:message key="uld.defaults.scmreconcile.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
</ihtml:form>
</div>
			

		

		
	</body>
</html:html>