<%--
* Project	 		: iCargo
* Module Code & Name: ULD:ULD Error Log
* File Name			: ULDErrorLog_Ajax.jsp
* Date				: 18/06/07
* Author(s)			: A-2408
--%>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

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
	<business:sessionBean id="ULDFlightMessageReconcileDetailsVOs" moduleName="uld.defaults" screenID="uld.defaults.ulderrorlog" method="get" attribute="ULDFlightMessageReconcileDetailsVOs" />
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<ihtml:hidden property="screenStatusFlag"/>
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


 <div class="ic-row">
	 	<div class="tableContainer"  id="div1"  style="height:650px; ">
<table width="100%" class="fixed-header-table">
		<thead>
							  <tr class="iCargoTableHeadingLeft">
                                 <td width="5%" class="iCargoTableHeader">
					             <input type="checkbox" name="selectedULDErrorLogAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck,document.forms[1].selectedUsers)" /><span></span>
                                </td>
                            <td width="10%">
                        	UCM No.
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
			<ihtml:hidden name="ULDErrorLogForm" property="content"/>
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
		<tr >
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



			<ihtml:select componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_ULDNO" name="ULDFlightMessageReconcileDetailsVO" property="sequenceNumber" disabled="<%=seqreadonly%>">

			<logic:present name="ucmNumberValues">
				<logic:iterate id="ucmNumberValue" name="ucmNumberValues" type="java.lang.String">



					<ihtml:option value="<%=String.valueOf(ucmNumberValue).toUpperCase() %>"><%=String.valueOf(ucmNumberValue)%></ihtml:option>



				</logic:iterate>
			</logic:present>

			</ihtml:select>




			</td>
			<td class="iCargoTableDataTd ic-center">
			


			<ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=ULDFlightMessageReconcileDetailsVO.getUldNumber()%>" componentID="TXT_ULD_DEFAULTS_ULDERRORLOG_ULDNUM" style="text-transform: uppercase" disabled="<%=seqreadonly%>"/>




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
			<td class="iCargoTableDataTd ic-center">
		
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
			<td class="iCargoTableDataTd ic-center">
			
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
		</table>
    </div>
	</div>
</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

