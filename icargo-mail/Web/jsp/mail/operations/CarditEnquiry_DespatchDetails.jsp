<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO"%>
<bean:define id="form"
				name="CarditEnquiryForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm"
				toScope="page"
				scope="request"/>
			

<div id="D"  style="visibility:hidden;z-index:99;height:100%">
			     		<div class="tableContainer" id="div2" align="center"  style="height:590px">
                		<table  style="width:100%;" class="fixed-header-table" border="1px"  align="center" cellpadding="0" id="despatchTable">
                  		<thead>
                    		<tr class="iCargoTableHeadingLeft" align="center">
                      			<td width="3%" height="20px;"class="iCargoTableHeaderLabel">
                      				<input type="checkbox" name="masterCheckboxB" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckboxB,this.form.selectedRows)"/>
<span></span></td>
							  	<td class="iCargoTableHeaderLabel" >
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.originoe" />
<span></span></td>
							  	<td  class="iCargoTableHeaderLabel">
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.destinationoe" />
<span></span></td>
							  	<td class="iCargoTableHeaderLabel">
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.mailtype" />
<span></span></td>
							  	<td width="8%" class="iCargoTableHeaderLabel">
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.year" />
<span></span></td>
							  	<td width="8%" class="iCargoTableHeaderLabel">
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.dsn" />
<span></span></td>
							  	<td width="8%" class="iCargoTableHeaderLabel" >
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.pa" />
<span></span></td>
							  	<td class="iCargoTableHeaderLabel" >
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentNo" />
<span></span></td>
							  	<td class="iCargoTableHeaderLabel" >
							  		<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentdate" />
<span></span></td>
                    		</tr>
                  		</thead>
                  		<tbody>
                  			<logic:present name="carditEnquiryVO" property="despatchDetailVos">
							<bean:define id="despatchDetailVos" name="carditEnquiryVO" property="despatchDetailVos" />
							<logic:iterate id="despatchDetailVo" name="despatchDetailVos" indexId="index" type="DespatchDetailsVO">
                    		<common:rowColorTag index="index">
                          	<tr  bgcolor="<%=color%>" >
                      			<td class="iCargoTableDataTd" ><center>
                      				<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckboxB)"/></center>
                      			</td>
                      			<td class="iCargoTableDataTd" >
                      			<logic:present name="despatchDetailVo" property="originOfficeOfExchange">
									<bean:write name="despatchDetailVo" property="originOfficeOfExchange" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="despatchDetailVo" property="destinationOfficeOfExchange">
									<bean:write name="despatchDetailVo" property="destinationOfficeOfExchange" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="despatchDetailVo" property="mailCategoryCode">
									<logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
											<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<logic:equal name="despatchDetailVo" property="mailCategoryCode" value="<%=(String)fieldValue%>">
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
                      			<logic:present name="despatchDetailVo" property="year">
									<bean:write name="despatchDetailVo" property="year" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="despatchDetailVo" property="dsn">
									<bean:write name="despatchDetailVo" property="dsn" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="despatchDetailVo" property="paCode">
									<bean:write name="despatchDetailVo" property="paCode" />
						   		</logic:present>
						   		</td>
                    			<td class="iCargoTableDataTd">
                    			<logic:present name="despatchDetailVo" property="consignmentNumber">
									<bean:write name="despatchDetailVo" property="consignmentNumber" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="despatchDetailVo" property="consignmentDate">
									<%=despatchDetailVo.getConsignmentDate().toDisplayDateOnlyFormat()%>
						   		</logic:present>
						   		</td>
                   			</tr>
                   			</common:rowColorTag>
                   			</logic:iterate>
							</logic:present>
                  		</tbody>
                		</table>
			      		</div>
				   </div>