<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ContainerVO"%>
<bean:define id="form"
				name="CarditEnquiryForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm"
				toScope="page"
				scope="request"/>
			
			<business:sessionBean id="carditEnquiryVO"
						  moduleName="mail.operations"
						  screenID="mailtracking.defaults.carditenquiry"
						  method="get"
						  attribute="carditEnquiryVO" />
<div id="M"  >
						<div class="tableContainer" id="div1" align="center"  style="height:590px">
						<table  style="width:100%;" class="fixed-header-table" border="1px"  align="center" cellpadding="0" id="mailBagTable" >
						<thead>
							<tr  align="center">
								<td width="3%" height="20px;"class="iCargoTableHeaderLabel">
									<input type="checkbox" name="masterCheckboxA" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckboxA,this.form.selectedRows)"/>
<span></span></td>
								<td  class="iCargoTableHeaderLabel" >
									<common:message key="mailtracking.defaults.carditenquiry.lbl.mailtag" />
<span></span></td>
								<td  class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.carditenquiry.lbl.containerno" />
<span></span></td>
								<td width="10%"  class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.carditenquiry.lbl.pa" />
<span></span></td>
								<td class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentNo" />
<span></span></td>
								<td  class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentdate" />
<span></span></td>
								<td class="iCargoTableHeaderLabel" >
									<common:message key="mailtracking.defaults.carditenquiry.lbl.resditstatus" />
<span></span></td>
							</tr>
						</thead>
						<tbody>
							<%int indexTot=0;%>
							<logic:present name="carditEnquiryVO" property="mailbagVos">
							<ihtml:hidden property="containerFlag" value="M"/>
							<bean:define id="mailbagVos" name="carditEnquiryVO" property="mailbagVos" />
							<logic:iterate id="mailbagVo" name="mailbagVos" indexId="index" type="MailbagVO">
							<common:rowColorTag index="index">
							<% String primaryKeyMail=(String)"M"+"~"+String.valueOf(index);%>
                          				<tr  bgcolor="<%=color%>" >
								<td class="iCargoTableDataTd"><center>
									<input type="checkbox" name="selectedRows" value="<%=primaryKeyMail%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckboxA)"/></center>
								</td>
								<td class="iCargoTableDataTd" >
								<logic:present name="mailbagVo" property="mailbagId">
									<bean:write name="mailbagVo" property="mailbagId" />
						   		</logic:present>
						   		</td>
								<td class="iCargoTableDataTd">
								<logic:present name="mailbagVo" property="containerNumber">
									<bean:write name="mailbagVo" property="containerNumber" />
						   		</logic:present>
						   		</td>
								<td class="iCargoTableDataTd">
								<logic:present name="mailbagVo" property="paCode">
									<bean:write name="mailbagVo" property="paCode" />
						   		</logic:present>
						   		</td>
								<td class="iCargoTableDataTd" >
								<logic:present name="mailbagVo" property="consignmentNumber">
									<bean:write name="mailbagVo" property="consignmentNumber" />
						   		</logic:present>
						   		</td>
								<td class="iCargoTableDataTd">
								<logic:present name="mailbagVo" property="consignmentDate">
									<%=mailbagVo.getConsignmentDate().toDisplayDateOnlyFormat()%>
						   		</logic:present>
						   		</td>
								<td class="iCargoTableDataTd">
								<logic:present name="mailbagVo" property="resditEventString">
									<bean:write name="mailbagVo" property="resditEventString" />
						   		</logic:present>
						   		</td>
						   		<%indexTot = index;%>
							</tr>
							</common:rowColorTag>
							</logic:iterate>
							</logic:present>
							<%indexTot = indexTot+1;%>
							<logic:present name="carditEnquiryVO" property="containerVos">
								<ihtml:hidden property="containerFlag" value="C"/>
								<bean:define id="containerVos" name="carditEnquiryVO" property="containerVos" />
								<logic:iterate id="containerVo" name="containerVos" indexId="index" type="ContainerVO">
								<common:rowColorTag index="index">
								<% String primaryKeyContainer=(String)"C"+"~"+String.valueOf(index);%>
								<tr  bgcolor="<%=color%>" >
									<td class="iCargoTableDataTd" >
										<input type="checkbox" name="selectedRows" value="<%=primaryKeyContainer%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckboxA)"/>
									</td>

									<td class="iCargoTableDataTd" >

									</td>
									<td class="iCargoTableDataTd">
									<logic:present name="containerVo" property="containerNumber">
										<bean:write name="containerVo" property="containerNumber" />
									</logic:present>
									</td>
									<td class="iCargoTableDataTd">
									<logic:present name="containerVo" property="paCode">
										<bean:write name="containerVo" property="paCode" />
									</logic:present>
									</td>
									<td class="iCargoTableDataTd" >
									<logic:present name="containerVo" property="consignmentDocumentNumber">
										<bean:write name="containerVo" property="consignmentDocumentNumber" />
									</logic:present>
									</td>
									<td class="iCargoTableDataTd">
									<logic:present name="containerVo" property="consignmentDate">
										<%=containerVo.getConsignmentDate().toDisplayDateOnlyFormat()%>
									</logic:present>
									</td>
									<td class="iCargoTableDataTd">
									<logic:present name="containerVo" property="resditEventString">
										<bean:write name="containerVo" property="resditEventString" />
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