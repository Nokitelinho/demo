<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDDmgRprAjaxTable.jsp
* Date				: 14-July-2008
* Author(s)			: A-3093

****************************************************** --%>

<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="/uld.defaults.listdamagerepairdetails.do">
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="transactionFlag" />

<business:sessionBean 
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.misc.listuldmovement"
		method="get"
		attribute="oneTimeValues" />
		
<business:sessionBean 
		id="ULDDamageRepairDetailsVOs"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="ULDDamageRepairDetailsVOs" />
		
		<bean:define id="form" name="listULDMovementForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
			toScope="page" />
<div class="ic-row">				
		
		<div id="_damage">
		<div class="ic-row">
			<div class="ic-input ic-split-50">
			<logic:present name="ULDDamageRepairDetailsVOs">			 
			     <common:paginationTag
				pageURL="uld.defaults.listdamagerepairdetails.do"
				name="ULDDamageRepairDetailsVOs"
				display="label"
				labelStyleClass="iCargoLink"
			     lastPageNum="<%=form.getLastPageNum() %>" />
			    </logic:present>
		</div>
		<div class="ic-input ic-split-50">
			<div class="ic-button-container">				
		    <logic:present name="ULDDamageRepairDetailsVOs">				
			    <common:paginationTag
				pageURL="javascript:submitPage('lastPageNum','displayPage')"
				name="ULDDamageRepairDetailsVOs"
				display="pages"
				linkStyleClass="iCargoLink"
			    disabledLinkStyleClass="iCargoLink"
				lastPageNum="<%=form.getLastPageNum()%>"
				exportToExcel="true"
				exportTableId="damagedetails"
				exportAction="uld.defaults.listdamagerepairdetails.do"/>
				</logic:present>
		</div>
		</div>
		</div>
		<div id="div1" class="tableContainer" style="height:720px">
        <table id="damagedetails" data-ic-filter-table="true" class="fixed-header-table" >
				<thead>				
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter1" width="12%">
						<common:message key="uld.defaults.misc.uldIntMvt.lbl.DmgSection" scope="request"/><span></span>						
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter2" width="10%">
						<common:message key="uld.defaults.misc.uldIntMvt.lbl.Damage" scope="request"/><span></span>						
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter3" width="9%">
						<common:message key="uld.defaults.misc.uldIntMvt.lbl.DmgDate" scope="request"/><span></span>						
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter4" width="9%">
						<common:message key="uld.defaults.misc.uldIntMvt.lbl.DmgSts" scope="request"/><span></span>						
					</td>					
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter5" width="8%" >
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.RprCode" scope="request"/><span></span>							
					</td>												
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter6" width="10%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.RprDate" scope="request"/><span></span>							
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter7" width="8%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.RprSts" scope="request"/><span></span>							
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter8" width="10%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.RprAmt" scope="request"/> (<%=form.getSystemCurrency() %>)<span></span>							
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter9" width="7%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.InvceAmt" scope="request"/><span></span>							
					</td>					
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter10" width="7%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.InvceSts" scope="request"/><span></span>							
					</td>
					<td  class="iCargoTableHeader" data-ic-filter="ULDMovementHistoryDmgRprParameter11" width="10%">
							<common:message key="uld.defaults.misc.uldIntMvt.lbl.Remarks" scope="request"/><span></span>						
					</td>					
				</thead>
				
				
				<tbody>	
				<logic:present name="ULDDamageRepairDetailsVOs" >				
				<logic:iterate id="damageRepairVO" name="ULDDamageRepairDetailsVOs" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO" indexId="index">
									
					
				<tr>
				
					
	                 <td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter1">
							
						<logic:present name="damageRepairVO" property="damageCode">
								<bean:write name="damageRepairVO" property="damageCode" />						        		
						</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter2">
							
						<logic:present name="damageRepairVO" property="section">
								<bean:write name="damageRepairVO" property="section" />						        		
						</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter3">
							
							<logic:present name="damageRepairVO" property="damageDate">
								<%=damageRepairVO.getDamageDate().toDisplayFormat()%>																        		
							</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter4">
							
						<logic:present name="damageRepairVO" property="damageStatus">
										     
						 <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<logic:equal name="damageRepairVO" property="damageStatus" value="<%=(String)fieldValue%>">
											
												<%=(String)fieldDescription%>
											</logic:equal>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						
						</logic:present>
						
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter5">
							
							<logic:present name="damageRepairVO" property="repairHead">
								<bean:write name="damageRepairVO" property="repairHead" />							
							</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter6">
							
							<logic:present name="damageRepairVO" property="repairDate">
								<%=damageRepairVO.getRepairDate().toDisplayFormat()%>
							</logic:present>
						
					</td>
						
						 <td   class="iCargoLabelLeftAligned" data-ic-filter="ULDMovementHistoryDmgRprParameter7">
	                      <logic:present name="damageRepairVO" property="repairStatus">
				       	 
					 <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.repairStatus">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<logic:equal name="damageRepairVO" property="repairStatus" value="<%=(String)fieldValue%>">
											<%=(String)fieldDescription%>
										</logic:equal>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
					</logic:present>
					
					</logic:present>
												
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter8">
							
						<logic:present name="damageRepairVO" property="repairAmount">
							<bean:write name="damageRepairVO" property="repairAmount" />							
						</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter9">
							
						<logic:present name="damageRepairVO" property="invoicedAmount">
							<bean:write name="damageRepairVO" property="invoicedAmount" />							
						</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter10">
							
							<logic:present name="damageRepairVO" property="invoiceStatus">
								<bean:write name="damageRepairVO" property="invoiceStatus" />							
							</logic:present>
						
					</td>
					<td class="iCargoTableDataTd" data-ic-filter="ULDMovementHistoryDmgRprParameter11">
							
							<logic:present name="damageRepairVO" property="remarks">
						   		<bean:write name="damageRepairVO" property="remarks" />
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
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

