<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  SCMSequenceNoLOV.jsp
* Date					:
* Author(s)				:  A-2046
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMSequenceNoLovForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
		
<html:html>
<head>
		
			
	

<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/uld/defaults/SCMSeqNoLov_Script.jsp"/>
</head>

<body>
	
	
	
	
	<business:sessionBean id="scmSequenceVOs"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.scmreconcile"
		   method="get"
		   attribute="sCMReconcileLovVOs"/>

<bean:define id="form"
	 name="SCMSequenceNoLovForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMSequenceNoLovForm"
	 toScope="page" />
<div class="iCargoPopUpContent">
<ihtml:form action="/uld.defaults.messaging.screenloadscmseqnolov.do" styleclass="ic-main-form">



<ihtml:hidden property="companyCode" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDate" />
<ihtml:hidden property="textfiledTime" />
<ihtml:hidden property="airportCode"/>
<ihtml:hidden property="listSuccess"/>

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="uld.defaults.scmseqnolov" />
		</span>
		<div class="ic-head-container"> 
			
			<div class="ic-filter-panel">
			<div class="ic-row marginT5">
				<div class="ic-col ic-split-50"><label><common:message key="uld.defaults.lbl.airline" /></label>
					<ihtml:text property="airline" readonly="true" componentID="TXT_ULD_DEFAULTS_SCMSEQLOV_AIRLINE"/>
				</div>
				
				<div class="ic-col ic-split-50"><label><common:message key="uld.defaults.lbl.scmseqno" /></label>
					<ihtml:text property="sequenceNo"  componentID="TXT_ULD_DEFAULTS_SCMSEQLOV_SEQNO"/>
				</div>
			</div>
		   
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btList" componentID="CMP_ULD_DEFAULTS_SCMSEQLOV_LIST_BTN" accesskey="L">
						<common:message key="uld.defaults.btn.btlist" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btClear" componentID="CMP_ULD_DEFAULTS_SCMSEQLOV_CLEAR_BTN" accesskey="C" >
						<common:message key="uld.defaults.btn.btclear" />
					</ihtml:nbutton>
				</div>
			</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-col-50">
					<logic:present name="scmSequenceVOs">
		  					<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
		  						pageURL="javascript:submitList('lastPageNum','displayPage')"
		  						name="scmSequenceVOs"
		  						display="label"
		  						labelStyleClass="iCargoLink"
		  						lastPageNum="<%=form.getLastPageNum() %>" />
		  					</logic:present>
				</div>
				
				<div class="ic-col-50 ic-right paddR5">
				<logic:present name="scmSequenceVOs">

		  					<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
		  						pageURL="javascript:submitList('lastPageNum','displayPage')"
		  						name="scmSequenceVOs"
		  						display="pages"
		  						lastPageNum="<%=form.getLastPageNum()%>"/>
		  					</logic:present>
				</div>
			</div>
			
			<div class="ic-row">
				<div id="div1" class="tableContainer" style="height:280px;overflow:auto">
    				<table  class="fixed-header-table" >
                        <thead>  					
		  					<tr>
								<td width="5%" class="iCargoTableHeader">&nbsp;
								</td>
								<td  width="30%"class="iCargoTableHeader">
									<common:message key="uld.defaults.lbl.scmseqno" />
								</td>
								<td  width="65%" class="iCargoTableHeader">
									<common:message key="uld.defaults.lbl.stkchkdate" />
								</td>
							</tr>    
					</thead>
					<tbody>
						<logic:present name="scmSequenceVOs">
						<%int index = 0;%>

							<logic:iterate id="scmSeqLOVVO" name="scmSequenceVOs" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO">
							<bean:define id="name" name="scmSeqLOVVO" property="sequenceNumber" />
								<%String stkChkdate ="";
								String stkChkdateonly ="";
								String stkChktimeonly ="";%>
							<logic:present name="scmSeqLOVVO" property="stockCheckDate">
							<%

								if(scmSeqLOVVO.getStockCheckDate() != null) {
								stkChkdate = TimeConvertor.toStringFormat(
											scmSeqLOVVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
								stkChkdateonly = TimeConvertor.toStringFormat(
											scmSeqLOVVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy");
								stkChktimeonly = TimeConvertor.toStringFormat(
											scmSeqLOVVO.getStockCheckDate().toCalendar(),"HH:mm");
								}
							%>
							</logic:present>
						 
							<tr>
								<% String value=(String)name+";"+stkChkdateonly+";"+stkChktimeonly; %>
								<td class="iCargoTableDataTd ic-center">
								<input type="checkbox" name="suChecked" id="scmlov<%=nIndex.toString()%>"  value="<%=value%>" onclick="singleSelectCb(<%=nIndex%>)"/>
								</td>
								<td class="iCargoTableDataTd">
								<bean:write name="scmSeqLOVVO" property="sequenceNumber"/></center>
								</td >
								<td class="iCargoTableDataTd">
								<logic:present name="scmSeqLOVVO" property="stockCheckDate">
								<%=stkChkdate%>
								</logic:present>								
								</td >
							</tr>
						 
							<%index++;%>
							</logic:iterate>
						</logic:present>
					</tbody>
				</table>
			</div>
		</div>
		</div>
		<div class="ic-foot-container">
			
				<div class="ic-button-container paddR5">
				<!--Added accesske,tablecontainer by A-7359 for ICRD-255778-->
					  <ihtml:nbutton property="btOk"
						componentID="CMP_ULD_DEFAULTS_SCMSEQLOV_POPUP_OK_BTN" accesskey="K" tabindex="5">
						<common:message key="uld.defaults.btn.btok" />
					  </ihtml:nbutton>

					  <ihtml:nbutton property="btClose"
						componentID="CMP_ULD_DEFAULTS_ULDSCMSEQLOV__CLOSE_BTN" accesskey="O" tabindex="6">
						 <common:message key="uld.defaults.btn.btclose" />
					  </ihtml:nbutton>
				</div>

		</div>
	</div>
</ihtml:form>	
</div>
			
		  
	</body>
</html:html>

