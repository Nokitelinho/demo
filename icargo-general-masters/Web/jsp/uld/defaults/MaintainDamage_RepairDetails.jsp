<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO"%>

<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
		 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
		 toScope="page" />

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="oneTimeValues" />
<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport" method="get" attribute="uLDDamageVO" />

<business:sessionBean id="uldNumbers"
moduleName="uld.defaults"
screenID="uld.defaults.maintaindamagereport"
method="get" attribute="uldNumbers"/>


 <logic:present name="uLDDamageVO">
	<bean:define id="uLDDamageVO" name="uLDDamageVO" />
 </logic:present>






                            <table class="fixed-header-table">
	                        <thead >
	                                       <tr>
	                                         <td width="5%"><input type="checkbox" name="checkRepAll" value="checkbox"/></td>
	                                         <td class="iCargoTableHeader" width="10%">Repair Head</td>
	                                         <td class="iCargoTableHeader" width="10%">Repair Airport</td>
	                                         <td class="iCargoTableHeader" width="15%">Repair Date</td>
	                                         <td class="iCargoTableHeader" width="15%">Damage Ref. No.</td>
	                                         <td class="iCargoTableHeader" width="15%">Amount</td>
	                                         <td class="iCargoTableHeader" width="10%">Currency</td>
	                                         <td class="iCargoTableHeader" width="20%">Remarks</td>
	                                       </tr>
	                       </thead>
	                       <tbody >
	                      <logic:present name="uLDDamageVO" property="uldRepairVOs">
						  <bean:define id="uldRepairVOs" name="uLDDamageVO" property="uldRepairVOs" />

						  <logic:iterate id="uldRepairVO" name="uldRepairVOs" indexId="repindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO">

						  <logic:notEqual name="uldRepairVO" property="operationFlag" value="D">

	                     <tr class="iCargoTableDataRow1" >
	                     <td  class="iCargoTableDataTd ic-center"> <html:checkbox property="selectedRepRowId" value="<%=String.valueOf(repindex)%>"/></td>
	                     <td class="iCargoTableDataTd" >
	                                         <logic:present name="uldRepairVO" property="repairHead">
						 <bean:define id="repairHead" name="uldRepairVO" property="repairHead" />
						       <!--  <bean:write name="uldRepairVO" property="repairHead"/>-->
						       <!-- Added by A-7359 for ICRD-258425 starts here -->
								<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									  <logic:equal name="parameterCode" value="uld.defaults.repairhead">
									   <bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
		    							 <logic:present name="parameterValue">
		    							   <bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
		    							    <bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										     <logic:equal name="uldRepairVO" property="repairHead" value="<%=(String)fieldValue%>">
											  <bean:write name="fieldDescription" />
										     </logic:equal>
						 </logic:present>
						                </logic:iterate>
						               </logic:equal>
						       </logic:iterate>
						       </logic:present>
							    <!-- Added by A-7359 for ICRD-258425 ends here -->
						   </logic:present>

	                     </td>
	                     <td  class="iCargoTableDataTd">
	                                         <logic:present name="uldRepairVO" property="repairStation">
						 <bean:define id="repairStation" name="uldRepairVO" property="repairStation" />
							<bean:write name="uldRepairVO" property="repairStation"/>
	                                         </logic:present>

	                     </td>
	                     <td class="iCargoTableDataTd">
						 <logic:present name="uldRepairVO" property="repairDate">
						  <%=uldRepairVO.getRepairDate().toDisplayDateOnlyFormat()%>
						  </logic:present>

		                 </td>
	                     <td  class="iCargoTableDataTd"style="text-align:right" >
	                                         <logic:present name="uldRepairVO" property="damageReferenceNumber">
						 <bean:define id="damageReferenceNumber" name="uldRepairVO" property="damageReferenceNumber" />
							<bean:write name="uldRepairVO" property="damageReferenceNumber"/>
						 </logic:present>

	                     </td>
	                     <td  class="iCargoTableDataTd" style="text-align:right">
	                      <logic:present name="uldRepairVO" property="displayAmount">
						  <bean:define id="displayAmount" name="uldRepairVO" property="displayAmount" />

	                                         <bean:write name="uldRepairVO" property="displayAmount" localeformat="true"/>
	                                         </logic:present>

	                     </td>
	                     <td  class="iCargoTableDataTd" >
	                                         <logic:present name="uldRepairVO" property="currency">
						  <bean:define id="currency" name="uldRepairVO" property="currency" />
	                                         <bean:write name="uldRepairVO" property="currency"/>
	                                         </logic:present>

	                    </td>
	     				<td  class="iCargoTableDataTd" >
						<logic:present name="uldRepairVO" property="remarks">
						<bean:define id="remarks" name="uldRepairVO" property="remarks" />
						<bean:write name="uldRepairVO" property="remarks"/>
						</logic:present>

	                    </td>
	                    </tr>
	                    </logic:notEqual>

	     			    </logic:iterate>

		  				</logic:present>

	                    </tbody>
	                    </table>

