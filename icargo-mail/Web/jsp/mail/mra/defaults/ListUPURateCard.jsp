<%--
* Project	 				: iCargo
* Module Code & Name		: mra.mailrating
* File Name					: ListUPURateCard.jsp
* Date						: 22/01/2007
* Author(s)					: A-2521

--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.Location"%>

	
	
<html:html>
<head>
	
	
	
	<title>
		<common:message  key="mailtracking.mra.defaults.listupuratecard.title" bundle="listupuratecardresources" scope="page"/>
	</title>
       <meta name="decorator" content="mainpanelrestyledui"/>

	<common:include type="script" src="/js/mail/mra/defaults/ListUPURateCard_Script.jsp"/>

</head>

<body>
	
	
	
	

  <!--CONTENT STARTS-->
<div class="iCargoContent ic-masterbg" id="pagediv" >
	<ihtml:form action="/mailtracking.mra.defaults.listupuratecard.onScreenLoad.do" focus="rateCardID">
	<business:sessionBean id="RateCardVOs" moduleName="mailtracking.mra.defaults" screenID="mailtracking.mra.defaults.listupuratecard" method="get" attribute="rateCardVOs" />
	<business:sessionBean id="OneTimeValues" moduleName="mailtracking.mra.defaults" screenID="mailtracking.mra.defaults.listupuratecard" method="get" attribute="statusOneTime" />
	<bean:define id="form" name="ListUPURateCardForm" type="ListUPURateCardForm" toScope="page" />
	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="lastPageNum" />
	<ihtml:hidden property="changeStatusFlag"/>
	<ihtml:hidden property="viewStatus"/>
	<ihtml:hidden property="screenStatusFlag"/>
	<input type="hidden" name="mySearchEnabled" />
        <div class="ic-content-main">
	        <span class="ic-page-title ic-display-none">
			    <common:message key="mailtracking.mra.defaults.listupuratecard.heading" />
			</span>
			    <div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input  ic-split-25 ic-label-30">
						    <label>
						        <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.ratecardid" />
							</label>
							    <ihtml:text  componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_RATECDID" property="rateCardID"  maxlength="20"  readonly="false"/>
								<div class="lovImg">
								<img name="rateCardLov"
					           id="rateCardLov" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" alt="" /></div>
						</div>
						<div class="ic-input  ic-split-20 ic-label-30">
						    <label>
							    <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.status" />
							</label>
								<ihtml:select componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_STATUS" property="rateCardStatus">
									<ihtml:option value=""></ihtml:option>
									<logic:present name="OneTimeValues">
										<logic:iterate id="oneTimeValue" name="OneTimeValues">
										<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue" property="fieldValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
										</logic:present>
										</logic:iterate>
										</logic:iterate>
									</logic:present>
					            </ihtml:select>
						</div>
						<div class="ic-input ic-mandatory ic-split-20 ic-label-30">
						    <label>
							    <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.fromdate" />
							</label>
							    <ibusiness:calendar componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_FROMDATE" property="startDate" type="image" id="aa" />
						</div>
						<div class="ic-input ic-mandatory">
						    <label>
							    <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.todate" />
							</label>
							    <ibusiness:calendar componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_TODATE" property="endDate" type="image" id="bb" />
						</div>
						
						    <div class="ic-button-container marginT10">
							    <ihtml:nbutton property="btnList" accesskey="L"  componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_BTLIST" >
				                    <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.list"/>
				                </ihtml:nbutton>
				                <ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_BTCLEAR" >
				                    <common:message key="mailtracking.mra.defaults.listupuratecard.lbl.clear"/>
				                </ihtml:nbutton>
							</div>
						</div>
					</div>
				<div class="ic-main-container">
				    <div class="ic-col-30">
						<logic:present name="RateCardVOs">
							<common:paginationTag pageURL="/mailtracking.mra.defaults.listupuratecard.onList.do"
							name="RateCardVOs" display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>" />

							</logic:present>
						<logic:notPresent name="RateCardVOs">
						&nbsp;
						</logic:notPresent>
					</div>
					<div class="ic-right paddR5">
                        <logic:present name="RateCardVOs">
							<common:paginationTag 
							linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink" 
							pageURL="javascript:submitPage('lastPageNum','displayPage')" 
							name="RateCardVOs"
							display="pages"
							lastPageNum="<%=form.getLastPageNum()%>" 
							exportToExcel="true"
							exportTableId="captureAgtSettlementMemo"
							exportAction="/mailtracking.mra.defaults.listupuratecard.onList.do"/>
                    	</logic:present>
						<logic:notPresent name="RateCardVOs">
							&nbsp;
						</logic:notPresent>
                    </div>
				
				<div class="tableContainer" id="div1" style="height:720px">
                    <table class="fixed-header-table" id="captureAgtSettlementMemo" >
				        <thead>
							<tr>
                                <td  class="iCargoTableHeader" width="2%"><input type="checkbox" name="headChk" /></td>

								<td  class="iCargoTableHeader" width="16%" ><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.ratecardid" /><span></span></td>

								<td class="iCargoTableHeader" width="16%" ><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.descr" /><span></span></td>

								<td   class="iCargoTableHeader" width="16%"><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.noofrateline" /><span></span></td>

								<td  class="iCargoTableHeader" width="16%"><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.validfrm" /><span></span></td>

								<td  class="iCargoTableHeader" width="16%"><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.validto" /><span></span></td>

								<td  class="iCargoTableHeader" width="16%"><common:message key="mailtracking.mra.defaults.listupuratecard.hdr.status" /><span></span></td>
							</tr>
                        </thead>
				        <tbody>
								<logic:present name="RateCardVOs">
									<logic:iterate id="RateCardVO" name="RateCardVOs" indexId="rowCount">
                                        <tr class="iCargoTableDataRow1">

											<%! LocalDate date = null; %>

											<td class="ic-center"><html:checkbox   property="rowCount" value="<%= String.valueOf(rowCount) %>" /></td>

											<td >&nbsp;<common:write name="RateCardVO" property="rateCardID"/></td>

											<td >&nbsp;<common:write name="RateCardVO" property="rateCardDescription"/></td>

											<bean:define id="noOfLines" name="RateCardVO" property="numberOfRateLines"/>
											<td >&nbsp;<%=String.valueOf(noOfLines)%></td>

											<td >&nbsp;

											<logic:present name="RateCardVO" property="validityStartDate">
												<bean:define id="fromDate" name="RateCardVO" property="validityStartDate"/>
												<%  String strFromDate = TimeConvertor.toStringFormat
												(((LocalDate)fromDate).toCalendar(),"dd-MMM-yyyy"); %>
												<%=strFromDate%>
											</logic:present>

											<td >&nbsp;
											<logic:present name="RateCardVO" property="validityEndDate">
												<bean:define id="toDate" name="RateCardVO" property="validityEndDate"/>
												<%  String strToDate = TimeConvertor.toStringFormat
												(((LocalDate)toDate).toCalendar(),"dd-MMM-yyyy");
													date = (LocalDate)toDate;
												%>
												<%=strToDate%>
											</logic:present>

											</td>

											<td>&nbsp;

													<logic:equal name="RateCardVO" property="rateCardStatus" value="A">
														<common:message key="mailtracking.mra.defaults.listupuratecard.status.active"/>
													</logic:equal>
													<logic:equal name="RateCardVO" property="rateCardStatus" value="I">
														<common:message key="mailtracking.mra.defaults.listupuratecard.status.inactive"/>
													</logic:equal>
													<logic:equal name="RateCardVO" property="rateCardStatus" value="C">
														<common:message key="mailtracking.mra.defaults.listupuratecard.status.cancelled"/>
													</logic:equal>
													<logic:equal name="RateCardVO" property="rateCardStatus" value="N">
														<common:message key="mailtracking.mra.defaults.listupuratecard.status.new"/>
													</logic:equal>
													<logic:equal name="RateCardVO" property="rateCardStatus" value="E">
														<common:message key="mailtracking.mra.defaults.listupuratecard.status.expired"/>
													</logic:equal>

											</td>
									   </tr>
	                             	</logic:iterate>
								</logic:present>
				            </tbody>
				        </table>
					</div>
				</div>
					<div class="ic-foot-container paddR5">
					    <div class="ic-button-container">
							<ihtml:nbutton property="btnCopy" accesskey="Y" componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_BTCOPY" >
							<common:message key="mailtracking.mra.defaults.listupuratecard.lbl.copy"/>
							</ihtml:nbutton>
							
							<ihtml:nbutton property="btnView" accesskey="V"  componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_BTVIEW" >
							<common:message key="mailtracking.mra.defaults.listupuratecard.lbl.view"/>
							</ihtml:nbutton>

							<ihtml:nbutton property="btnClose" accesskey="O"  componentID="CMP_MRA_DEFAULTS_LISTUPARATECARD_BTCLOSE" >
							<common:message key="mailtracking.mra.defaults.listupuratecard.lbl.close"/>
							</ihtml:nbutton>
						</div>
					</div>
		</div>

	</ihtml:form>
</div>

<!---CONTENT ENDS-->
	
	</body>

</html:html>
