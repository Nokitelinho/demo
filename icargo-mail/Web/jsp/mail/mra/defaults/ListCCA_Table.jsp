<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  ListCCA_Table.jsp
* Date					: 18-May-2017
* Author(s)				: A-6850
*************************************************************************/
 --%>
 <%@ include file="/jsp/includes/tlds.jsp"%>
  <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
	<bean:define id="form"
		 name="ListCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"
		 toScope="page" />
	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.listcca"
	 	  method="get"
	  attribute="OneTimeVOs" />

	 <business:sessionBean id="KEY_CCALIST"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.listcca"
		method="get"
		attribute="CCADetailsVOs" />
    <business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.listcca"
	method="get" attribute="systemparametres" />
<%String roundingReq="true";%>

<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>			
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>	

<div id="div1" class="tableContainer"  style="height:320px;">
				<table class="fixed-header-table" id="listCCATable">
					<thead>
						<tr class="iCargoTableHeadingLeft">
							<td width="3%"><input type="checkbox" name="headChk"  value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.selectedRows)"/><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcano" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.issuedate" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.orgoe" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.destoe" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mailcategory" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.subclass" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsn" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.rsn" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.hni" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.RI" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.condocno" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpacode" /><span></span></td>
							<td width="7%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpaname" /><span></span></td>
							<td width="7%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.weight" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcaamt" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.currency" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcastatus" /><span></span></td>
							<td width="6%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.billingstatus" /><span></span></td>
							<td width="5%"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.remarks" /><span></span></td>
						</tr>
					</thead>
					<tbody>
					<logic:present name="KEY_CCALIST">
						<logic:iterate id = "CCADetailsVO" name="KEY_CCALIST" indexId="rowCount" scope="page" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO">
							<tr>
								<td  class="iCargoTableDataTd" style="text-align:center;"> <ihtml:checkbox property="selectedRows"  onclick="toggleTableHeaderCheckbox('selectedRows',this.form.headChk)" value="<%=String.valueOf(rowCount)%>" /></td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="usrccanum">
									<%System.out.println("inside logic present");%>
									<bean:write name="CCADetailsVO" property="usrccanum"/>
									</logic:present>
									<logic:notPresent name="CCADetailsVO" property="usrccanum">
									<%System.out.println("inside logic notpresent");%>
									<bean:write name="CCADetailsVO" property="ccaRefNumber"/>
									</logic:notPresent>
								  
							   </td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="issueDat">
									<bean:define id="issueDateId" name="CCADetailsVO" property="issueDat" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
									<% String issueDate=TimeConvertor.toStringFormat(issueDateId.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
									<%=issueDate%>	
									</logic:present>
								  
							   </td>
						
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="originOE">
									<bean:write name="CCADetailsVO" property="originOE"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="destinationOE">
									<bean:write name="CCADetailsVO" property="destinationOE"/>
									</logic:present>
								  
								</td>
								<td class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="categoryCode">
									<bean:write name="CCADetailsVO" property="categoryCode"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="subClass">
									<bean:write name="CCADetailsVO" property="subClass"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="dsnNo">
									<bean:write name="CCADetailsVO" property="dsnNo"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="rsn">
									<bean:write name="CCADetailsVO" property="rsn"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="hni">
									<bean:write name="CCADetailsVO" property="hni"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="regind">
									<bean:write name="CCADetailsVO" property="regind"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="csgDocumentNumber">
									<bean:write name="CCADetailsVO" property="csgDocumentNumber"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="gpaCode">
									<bean:write name="CCADetailsVO" property="gpaCode"/>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="gpaName">
									<bean:write name="CCADetailsVO" property="gpaName"/>
									</logic:present>
								  
								</td>
								<!--Added by A-7540-->
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="revGrossWeight">
									<bean:write name="CCADetailsVO" property="revGrossWeight"/>
									
									<bean:write name="CCADetailsVO" property="displayWgtUnit" />
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
								   
											<logic:present name="CCADetailsVO" property="diffAmount">
											  <logic:equal name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="CCADetailsVO"  moneyproperty="diffAmount" property="diffAmount" overrideRounding = "true"/>
											</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="CCADetailsVO"  moneyproperty="diffAmount" property="diffAmount"/>								
													</logic:notEqual>
												</logic:present>
											<logic:notPresent name="CCADetailsVO" property="diffAmount">
												0.0					

									</logic:notPresent>
								  
							   </td>
							   <!--Added by A-7540-->
							   <td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="revContCurCode">
									<bean:write name="CCADetailsVO" property="revContCurCode"/>
									</logic:present>
								  
							   </td>
							   <td   class="iCargoTableDataTd">
									<logic:present name="CCADetailsVO" property="ccaStatus">
									 <bean:define id="ccaStatus" name="CCADetailsVO" property="ccaStatus"/>
									 <ihtml:hidden property="ccaStatus" value="<%=String.valueOf(ccaStatus)%>"/>
									<logic:present name="OneTimeValues">
									<bean:define id="onetimemaps" name="OneTimeValues"/>
									<logic:iterate id ="onetimemap" name="onetimemaps">
										
										 <bean:define id="keymap" name="onetimemap" property="key"/>
										 <logic:equal name="keymap" value ="mra.defaults.ccastatus">
											 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
												<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<bean:define id="value1" name="value" property="fieldValue" />
												<logic:equal name="value1" value="<%=String.valueOf(ccaStatus)%>">
												 <%=value.getFieldDescription()%>
														</logic:equal>
											</logic:iterate>
										</logic:equal>
										</logic:iterate>  
												</logic:present>
									</logic:present>
								  
								</td>
								<td  class="iCargoTableDataTd">
									<logic:present name="CCADetailsVO" property="billingStatus">
									 <bean:define id="billingStatus" name="CCADetailsVO" property="billingStatus"/>				
									<logic:present name="OneTimeValues">
									<bean:define id="onetimemaps" name="OneTimeValues"/>
									<logic:iterate id ="onetimemap" name="onetimemaps">										
										 <bean:define id="keymap" name="onetimemap" property="key"/>
										 <logic:equal name="keymap" value ="mailtracking.mra.gpabilling.gpabillingstatus">
											 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
												<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<bean:define id="value1" name="value" property="fieldValue" />
												<logic:equal name="value1" value="<%=String.valueOf(billingStatus)%>">
												 <%=value.getFieldDescription()%>
														</logic:equal>
											</logic:iterate>
										</logic:equal>
										</logic:iterate>  
												</logic:present>
									</logic:present>
								</td>
								<td  class="iCargoTableDataTd">
								  
									<logic:present name="CCADetailsVO" property="ccaRemark">
									<bean:write name="CCADetailsVO" property="ccaRemark"/>
									</logic:present>
								  
								</td>
							</tr>
						</logic:iterate>
						</logic:present>
					</tbody>
				</table>
			</div>