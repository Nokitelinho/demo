<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDIntMvmtAjaxTable.jsp
* Date				: 14-July-2008
* Author(s)			: a-3093

****************************************************** --%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp"%>

<ihtml:form action="/uld.defaults.misc.listintuldmovement.do">

<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="transactionFlag"/>
<ihtml:hidden property="isUldValid"/>

<business:sessionBean id="KEY_INTULDMVTDETAILS"
					moduleName="uld.defaults"
					screenID="uld.defaults.misc.uldintmvthistory" method="get"
					attribute="intULDMvtDetails" />
		<business:sessionBean id="KEY_ULDMOVEMENTDTLS" 
					moduleName="uld.defaults"
					screenID="uld.defaults.misc.listuldmovement" method="get"
			attribute="uldMovementDetails" />
			
			<bean:define id="form" name="listULDMovementForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
			toScope="page" />

	<div class="ic-row">				
		
		<div id="_divmain1">
		<div class="ic-row">
			<div class="ic-input ic-split-50">
			  <logic:present name="KEY_INTULDMVTDETAILS">				
			     <common:paginationTag
				pageURL="uld.defaults.misc.listuldmovement.do"
				name="KEY_INTULDMVTDETAILS"
				display="label"
				labelStyleClass="iCargoLink"
			     lastPageNum="<%=form.getLastPageNum() %>" />
			    </logic:present>
		</div>
		<div class="ic-input ic-split-50">
			<div class="ic-button-container">		
			    
					    <logic:present name="KEY_INTULDMVTDETAILS">
					    <common:paginationTag
						pageURL="javascript:submitPage('lastPageNum','displayPage')"
						name="KEY_INTULDMVTDETAILS"
						display="pages"
						linkStyleClass="iCargoLink"
					    disabledLinkStyleClass="iCargoLink"
						lastPageNum="<%=form.getLastPageNum()%>"
						exportToExcel="true"
						exportTableId="Int"
						exportAction="uld.defaults.misc.listintuldmovement.do"/>
						</logic:present>
				    </div>
			    </div>
			</div>
			<div id="div1" class="tableContainer" style="height:610px">
        <table id="Int" data-ic-filter-table="true" class="fixed-header-table" >
		
			<thead>
			<tr class="iCargoTableHeadingLeft">			
					<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDIntMovementHistoryParameter1"><common:message
						key="uld.defaults.misc.uldIntlMvt.lbl.movementdate" scope="request" /><span></span>
					</td>
					<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDIntMovementHistoryParameter2"><common:message
						key="uld.defaults.misc.uldIntlMvt.lbl.reason" scope="request" /><span></span>
					</td>
					<td rowspan="2"class="iCargoTableHeader" data-ic-filter="ULDIntMovementHistoryParameter3" ><common:message
						key="uld.defaults.misc.uldIntlMvt.lbl.destination" scope="request" /><span></span>
					</td>
					<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDIntMovementHistoryParameter4"><common:message
						key="uld.defaults.misc.uldIntlMvt.lbl.returnstatus" scope="request" /><span></span>
					</td>
					<td rowspan="2" class="iCargoTableHeader" data-ic-filter="ULDIntMovementHistoryParameter5"><common:message
						key="uld.defaults.misc.uldIntlMvt.lbl.remarks" scope="request" /><span></span> 
					</td>	
			</tr>				
			</thead>
			<tbody>
					<logic:present name="KEY_INTULDMVTDETAILS">
				<logic:iterate id="iterator" name="KEY_INTULDMVTDETAILS"
					type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO"
					indexId="index">
					<tr>							
								<td class="iCargoTableDataTd" data-ic-filter="ULDIntMovementHistoryParameter1">
									<center>	
										<logic:present name="iterator" property="displayMvtDate">
											<%=iterator.getDisplayMvtDate()%>	
										</logic:present>
									</center>
								</td>
								<td data-ic-filter="ULDIntMovementHistoryParameter2">
								<center>	
										<logic:present	name="iterator" property="content">
										<logic:equal name="iterator" property="content" value="AGT">
											<common:message
												key="uld.defaults.misc.uldIntlMvt.agentLoan" scope="request" /> 	
										</logic:equal>
										<logic:equal name="iterator" property="content" value="RPR">
											<common:message
												key="uld.defaults.misc.uldIntlMvt.uldDamage" scope="request" /> 	
										</logic:equal>																				
										</logic:present>
								</center>
								</td>
								<td class="iCargoTableDataTd" data-ic-filter="ULDIntMovementHistoryParameter3">
									<center>									
										<logic:present	name="iterator" property="airport">
										<bean:write name="iterator" property="airport"/>
										</logic:present>
									</center>
								</td>
								
								<td data-ic-filter="ULDIntMovementHistoryParameter4">
								<center>	
									<logic:present	name="iterator" property="returnStatus">
										<logic:equal name="iterator" property="returnStatus" value="Y">
											<common:message
												key="uld.defaults.misc.uldIntlMvt.returned" scope="request" /> 	
										</logic:equal>
										<logic:notEqual name="iterator" property="returnStatus" value="Y">
											<common:message
												key="uld.defaults.misc.uldIntlMvt.pending" scope="request" /> 	
										</logic:notEqual>																				
									</logic:present>	
								</center>	
								</td>
								<td class="iCargoTableDataTd" data-ic-filter="ULDIntMovementHistoryParameter5">
									<center>
										<logic:present name="iterator" property="remark">
										<bean:write name="iterator" property="remark" />
										</logic:present>
									</center>
								</td>
								</tr>
						</logic:iterate>
					</logic:present>
			</tbody>
		</table>
		</div>
	</div>
	</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp"%>


