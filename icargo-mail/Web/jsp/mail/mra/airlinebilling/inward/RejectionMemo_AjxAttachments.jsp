<%--
* Project	 		: iCargo
* Module Code & Name            : mra airlinebilling inward rejectionmemo
* File Name			: IssueRejectionMemo_AjxAttachments.jsp
* Date				: 29-10-2018
* Author(s)			: A-8061
 --%>
 
 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 
 <business:sessionBean
  	id="KEY_MEMOVO"
  	moduleName="mailtracking.mra.airlinebilling"
  	screenID="mailtracking.mra.airlinebilling.inward.rejectionmemo"
  	method="get"
	attribute="RejectionMemoVO" />
	
	<ihtml:form action="/mail.mra.airlinebilling.inward.refreshrejectionmemoattachment.do">
 
 <table class="iCargoBorderLessTable" width="100%"  align="Left">
					<tr> <td></td>
					
					<td ><div class="ic-button-container"> <a href="#" class="iCargoLink" id="addLink" onclick="addfn()" enabled="true" >
											Add
										      </a> |
										      <a href="#" class="iCargoLink"  id="deleteLink" onclick="deletefn()" enabled="true">
											Delete
										      </a> 
											  </div>
											  </td>
											  </tr>
					 			                              
					 			                      <tr>
					 			                      <td width="10%">
											 <logic:present name="KEY_MEMOVO" property="attachmentIndicator">
												<bean:define id="attachmentIndicator" name="KEY_MEMOVO" property="attachmentIndicator" />
					
												<logic:equal name="attachmentIndicator" value="Y" >
													<input type="checkbox" tabindex="32" name="attachmentIndicator" value="Y" checked style="width:40px;" disabled="true" />
												</logic:equal>
												<logic:notEqual name="attachmentIndicator" value="Y">
													<input type="checkbox" tabindex="32" name="attachmentIndicator" value="N" style="width:40px;" disabled="true" />
												</logic:notEqual>
											</logic:present>
											<logic:notPresent name="KEY_MEMOVO" property="attachmentIndicator">
												   <input type="checkbox" tabindex="32" name="attachmentIndicator"  style="width:40px;" />
										       </logic:notPresent></td>
										    <td class="iCargoLabelLeftAligned">  <common:message scope="request" key="mra.airlinebilling.rejectionmemo.attachmentIndicator" />
					 			       			</td></tr>
					 			       			
					 			                              
					 			    <tr><logic:present name="KEY_MEMOVO" property="sisSupportingDocumentDetailsVOs">
					 			      <bean:define id="supportingDocumentDetailsVO" name="KEY_MEMOVO" property="sisSupportingDocumentDetailsVOs" />
					 				<logic:iterate id="supportingDocumentDetails" name="supportingDocumentDetailsVO" indexId="nIndex" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO">
					 				
					 				<logic:notEqual name="supportingDocumentDetails" property="operationFlag" value="D">
					 				<tr>
					 				<td>
					 				<input type="checkbox" tabindex="32" name="fileNameCheck" value="<%=nIndex%>" style="width:40px;" /></td>
					 				
					 				
					 				<td>
					 					<logic:present name="supportingDocumentDetails" property="filename">
					 					<bean:define id="filename" name="supportingDocumentDetails" property="filename" />
					 						<a href="#" id="fileDownload" class="iCargoLink" indexId="nIndex" 
					 							onclick="onfileDownload(<%=String.valueOf(nIndex)%>)" >
					 								<%=String.valueOf(filename)%> 
					 						</a>
					 					</logic:present>
					 					
					 				 </td>
									 </tr>
					 				 </logic:notEqual>
					 				 </logic:iterate></logic:present>
					 				 
 					</tr>
					</table>
	</ihtml:form>
 
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>