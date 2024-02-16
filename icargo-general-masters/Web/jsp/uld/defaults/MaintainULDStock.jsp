<%--
* Project	 		: iCargo
* Module Code & Name: Tariff
* File Name			: SetupChargeHeads.jsp
* Date				: 04-Aug-2005
* Author(s)			: A-2052
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%--@ include file="/jsp/includes/reports/printFrame.jsp"--%>
<%@ page import="org.apache.struts.Globals"%>



<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<html:html locale="true">
<head>
		
	
<title>
<common:message bundle="maintainuldstock" key="uld.defaults.screentitle" />
</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/uld/defaults/SetUpStock_Script.jsp"/>
</head>


<body>
	

<%@include file="/jsp/includes/reports/printFrame.jsp" %>


<business:sessionBean id="KEY_STOCKDETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainuldstock"
		   method="get"
		   attribute="ULDStockDetails"/>

<business:sessionBean id="KEY_AIRLINE"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainuldstock"
		   method="get"
		   attribute="AirLineCode"/>

<business:sessionBean id="oneTimeValues"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainuldstock"
		   method="get"
		   attribute="oneTimeValues"/>

<bean:define id="form"
		name="maintainULDStockForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm"
		toScope="page" />

<logic:present name="KEY_STOCKDETAILS">
<bean:define id="KEY_STOCKDETAILS" name="KEY_STOCKDETAILS" />
</logic:present>

<div  class="iCargoContent ic-masterbg">
			
<ihtml:form action="/uld.defaults.screenloaduldsetupstock.do">

<ihtml:hidden property="closeStatus"/>
<ihtml:hidden property="flag"/>
<ihtml:hidden property="screenloadstatus"/>
<ihtml:hidden property="statusFlag" value="<%= form.getStatusFlag() %>"/>
<ihtml:hidden property="createStatus" value="<%= form.getCreateStatus() %>"/>
<ihtml:hidden property="validateStatus" value="<%= form.getValidateStatus() %>"/>
<ihtml:hidden property="selectedRows" value="<%= form.getSelectedRows() %>"/>
<ihtml:hidden property="linkStatus" value="<%= form.getLinkStatus() %>"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="screenStatusFlag"/>
<ihtml:hidden property="stkDisableStatus"/>
<ihtml:hidden property="listStatus"/>
<ihtml:hidden property="uldGroupCode"/>
<ihtml:hidden property="uldTypeCode"/>
				<div class="ic-content-main">
					
					<div class="ic-head-container">
						<div class="ic-filter-panel">
						
							<div class="ic-input-container">
								
									<b  style="text-transform : uppercase">
										<common:message key="uld.defaults.searchcriteria" scope="request"/>
									</b>
								<div class="ic-row">
									<div class="ic-input ic-split-25 ic-mandatory ">
										<label>
										<common:message bundle="maintainuldstock" key="uld.defaults.column2" />
										</label>
										<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_STATIONCODE" property="stationCode" name="maintainULDStockForm" maxlength="3" style="text-transform : uppercase"/>
										<div class="lovImg">
											<img name="airportlov" id="airportlov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" alt="Airport LOV"/>
										</div>
									</div>
									
									<div class="ic-input ic-split-25">
										<label>
										<common:message bundle="maintainuldstock" key="uld.defaults.column1" />
										</label>
										<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_AIRLINECODE" property="airlineCode"  maxlength="3" style="text-transform : uppercase"/>
										<div class="lovImg">
											<img name="airlinelov" id="airlinelov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" alt="Airline LOV"/>
										</div>
									</div>	
									<div class="ic-input ic-split-25">
										<label>
										<common:message key="uld.defaults.lbl.uldnature" />
										</label>
									
										<ihtml:select property="uldNature" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_ULDNATURE">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											 
											 <logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="uld.defaults.uldnature">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue">
															<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<ihtml:option value="<%=(String)fieldValue%>">
																	<%=(String)fieldDescription%>
																</ihtml:option>
															</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>	
									<div class="ic-input ic-split-25">
											<div class="ic-button-container">
												<ihtml:nbutton property="btList" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_LIST_BTN" accesskey="L">
												<common:message bundle="maintainuldstock" key="uld.defaults.btn.btlist" />
												</ihtml:nbutton>

												<ihtml:nbutton property="btClear" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_CLEAR_BTN" accesskey="C">
												<common:message bundle="maintainuldstock" key="uld.defaults.btn.btclear" />
												</ihtml:nbutton>
											</div>
									</div>
									
								</div>	
									
									
									
							</div>
						</div>
					</div>
					<div class="ic-main-container">
						<div class="ic-row">
							<label><h4><common:message key="uld.defaults.searchcriteria" scope="request"/></h4></label>
						</div>
						<div class="ic-button-container">
								<ul class="ic-list-link">			 					
									<li>
							<a href="#"id="createLink" class="iCargoLink" >
								<common:message bundle="maintainuldstock" key="uld.defaults.lbl.createlink" /></a>
									</li>  |
									<li>
									   <a href="#" id="modifyLink" class="iCargoLink">
										<common:message bundle="maintainuldstock" key="uld.defaults.lbl.modifylink" /></a> &nbsp;
									   </a>									
									</li>  								
								</ul>
						</div>
					<div class="ic-row">
						 <div id="div1" class="tableContainer" style="height:640px">
							<table  class="fixed-header-table" cellpadding="0" cellspacing="0" width="100%" class="scrollTable">
							<thead>
							<tr class="iCargoTableHeadingLeft">

							<td  width="30px" >
							<input type="checkbox" name="masterRowId" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.rowId)"/>
							</td>

							<td   >
							<common:message bundle="maintainuldstock" key="uld.defaults.lbl.column1" /><span></span>
							</td>

							<td   >
							<common:message bundle="maintainuldstock" key="uld.defaults.lbl.column2" /><span></span>
							</td>

							<td >
									<common:message key="uld.defaults.lbl.uldnature" /><span></span>

							</td>

							<td   >
							<common:message bundle="maintainuldstock" key="uld.defaults.lbl.column3" /><span></span>
							</td>

							<td   >
								<common:message bundle="maintainuldstock" key="uld.defaults.uldGroupCode" /><span></span>
							</td>
							
							<td  >
								<common:message bundle="maintainuldstock" key="uld.defaults.dwellTime" /><span></span>
							</td>

							<td   >
							<common:message bundle="maintainuldstock" key="uld.defaults.lbl.column5" /><span></span>
							</td>

							<td   >
							<common:message bundle="maintainuldstock" key="uld.defaults.lbl.column4" /><span></span>
							</td>
							
							<td  class="iCargoTableDataTd" >
								<common:message bundle="maintainuldstock" key="uld.defaults.lbl.remarks" /><span></span>
							</td>

							</tr>
							</thead>

							<tbody>
							<logic:present name="KEY_STOCKDETAILS">
							   <bean:define id="KEY_STOCKDETAILS" name="KEY_STOCKDETAILS" />
								 <logic:iterate id="iterator" name="KEY_STOCKDETAILS" indexId="repindex" type="com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO">
								 <input type="hidden" name="operationFlag" value="<%=(String)iterator.getOperationFlag()%>"/>
								 <logic:notEqual name="iterator" property="operationFlag" value="D">
								<tr>
								<td class="iCargoTableDataTd" style="text-align:center;">
								
								<html:checkbox property="rowId" value="<%=String.valueOf(repindex)%>" onclick="toggleTableHeaderCheckbox('rowId',this.form.masterRowId)"/>

								</td>

								<td class="iCargoTableDataTd" >
								<logic:present name="iterator" property="airlineCode">
								<bean:write name="iterator" property="airlineCode"/>
								</logic:present>
								
								</td>

								<td class="iCargoTableDataTd" >
								<logic:present name="iterator" property="stationCode">
								<bean:write name="iterator" property="stationCode"/>
								</logic:present>
								
								</td>

								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="uldNature">
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="uld.defaults.uldnature">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<logic:equal name="iterator" property="uldNature" value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
															</logic:equal>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
									</logic:present>
									
								</td>

								<td class="iCargoTableDataTd" >
								<logic:present name="iterator" property="uldTypeCode">
								<bean:write name="iterator" property="uldTypeCode"/>
								</logic:present>
								
								</td>

								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="uldGroupCode">
										<bean:write name="iterator" property="uldGroupCode"/>
									</logic:present>
								
								</td>
								
								<td class="iCargoTableDataTd" >
									<logic:present name="iterator" property="dwellTime">
										<bean:write name="iterator" property="dwellTime"/>
									</logic:present>
								
								</td>



								<td class="iCargoTableDataTd"style="text-align:right" >
								<logic:present name="iterator" property="minQty">
								<bean:write name="iterator" property="minQty"/>
								</logic:present>
								
								</td>

								<td class="iCargoTableDataTd"style="text-align:right" >
								<logic:present name="iterator" property="maxQty">
								<bean:write name="iterator" property="maxQty"/>
								</logic:present>
								
								</td>
								
								<td class="iCargoTableDataTd" >
								<logic:present name="iterator" property="remarks">
									<common:write name="iterator" property="remarks" splitLength="30"/>
								</logic:present>
											
								</td>

								</tr>
							</logic:notEqual>
							 </logic:iterate>
							</logic:present>
							</tbody>

							</table>
						 </div>
					</div>
						
					</div>
					<div class="ic-foot-container">
						<div class="ic-button-container paddR5">
							<%--<ihtml:button property="spmtSave" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_SAVE_BTN">
							<common:message bundle="maintainuldstock" key="uld.defaults.btn.btsave" />
							</ihtml:button>--%>

							<ihtml:nbutton property="btDelete" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_DELETE_BTN" accesskey="E">
							<common:message bundle="maintainuldstock" key="uld.defaults.btDelete" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btPrint" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_PRINT_BTN" accesskey="P">
							<common:message bundle="maintainuldstock" key="uld.defaults.btn.btprint" />
							</ihtml:nbutton>

							<ihtml:nbutton property="btClose" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_CLOSE_BTN" accesskey="O">
							<common:message bundle="maintainuldstock" key="uld.defaults.btn.btclose" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
</ihtml:form>
</div>


				
		
	</body>
</html:html>

