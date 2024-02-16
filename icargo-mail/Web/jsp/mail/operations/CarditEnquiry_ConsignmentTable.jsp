<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO"%>

			<business:sessionBean id="carditEnquiryVO"
						  moduleName="mail.operations"
						  screenID="mailtracking.defaults.carditenquiry"
						  method="get"
						  attribute="carditEnquiryVO" />
 <div id="C"  style="z-index:99;height:100%">
			       		<div class="tableContainer" id="div3" align="center"  style="height:590px">
                		<table  style="width:100%;" class="fixed-header-table" border="1px"  align="center" cellpadding="0" id="documentTable" >
                  		<thead>
                    		<tr class="iCargoTableHeadingLeft" align="center">
                      			<td width="3%" height="20px;"class="iCargoTableHeaderLabel">
                      				<input type="checkbox" name="masterCheckboxC" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckboxC,this.form.selectedRows)"/>
<span></span></td>
                      			<td width="25%" class="iCargoTableHeaderLabel">
                      				<common:message key="mailtracking.defaults.carditenquiry.lbl.pa" />
<span></span></td>
                      			<td  width="35%" class="iCargoTableHeaderLabel">
                      				<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentNo" />
<span></span></td>
                      			<td  width="35%" class="iCargoTableHeaderLabel">
                      				<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentdate" />
<span></span></td>
                    		</tr>
                  		</thead>
                  		<tbody>
                  			<logic:present name="carditEnquiryVO" property="consignmentDocumentVos">
							<bean:define id="consignmentDocumentVos" name="carditEnquiryVO" property="consignmentDocumentVos" />
							<logic:iterate id="consignmentDocumentVo" name="consignmentDocumentVos" indexId="index" type="ConsignmentDocumentVO">
                    		<common:rowColorTag index="index">
                          	<tr  bgcolor="<%=color%>" >
                      			<td class="iCargoTableDataTd" ><center>
                      				<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckboxC)"/></center>
                      			</td>
                      			<td class="iCargoTableDataTd" >
                      			<logic:present name="consignmentDocumentVo" property="paCode">
									<bean:write name="consignmentDocumentVo" property="paCode" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="consignmentDocumentVo" property="consignmentNumber">
									<bean:write name="consignmentDocumentVo" property="consignmentNumber" />
						   		</logic:present>
						   		</td>
                      			<td class="iCargoTableDataTd">
                      			<logic:present name="consignmentDocumentVo" property="consignmentDate">
                      				<%=consignmentDocumentVo.getConsignmentDate().toDisplayDateOnlyFormat()%>
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