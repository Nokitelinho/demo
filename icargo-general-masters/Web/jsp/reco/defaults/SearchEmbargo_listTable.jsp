<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"%>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.*" %>

<business:sessionBean id="embargoDetails" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="EmabrgoDetailVOs" />
<business:sessionBean id="regulatoryComplianceRules" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="regulatoryComplianceRules" />
<bean:define id="form" name="SearchEmbargoForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"/>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="reco.defaults.onscreenloadsearchembargo.do">
<div id="_embargolistTable">
	<div class="ic-row">
			<div class="ic-col-50">
				<logic:present name="regulatoryComplianceRules">
					<common:paginationTag pageURL="reco.defaults.simplesearch.do" name="regulatoryComplianceRules" display="label"
						labelStyleClass="iCargoResultsLabel" lastPageNum="<%=form.getLastPageNum() %>" />										 
				</logic:present>
			</div>
			<div class="ic-col-50">
				<div class="ic-button-container">
					<logic:present name="regulatoryComplianceRules">
						<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitPage('lastPageNum','displayPage')" name="regulatoryComplianceRules" display="pages"
							lastPageNum="<%=form.getLastPageNum() %>" exportToExcel="false" exportTableId="embargoDetails"
							exportAction="reco.defaults.simplesearch.do"/>
					</logic:present>	
				</div>
			</div>
	</div>	
	<div class="ic-row">		
			<div class="tableContainer"  style="height:545px">
				<table class="fixed-header-table"  id="embargoDetails" id="tableContainer3">
					<thead>
						<tr>        								
							<td style="width:13%;"><common:message  key="reco.defaults.searchembargo.tbl.geographiclevel"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.complianceissuetype"/></td>
							<td style="width:40%;"><common:message  key="reco.defaults.searchembargo.tbl.description"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.startdate"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.enddate"/></td>										
							<td style="width:15%"><common:message  key="reco.defaults.searchembargo.tbl.complancesource"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.refnumber"/></td>
						</tr>
					</thead>
					<tbody>
						<% String description=null;
						String referenceNumber=null;%>							
						<logic:present id="embargoDetails" name="embargoDetails">
								<logic:iterate id="embargoDetailsVO" name="embargoDetails" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO" indexId="nIndex">										
								<tr>
									<td class="iCargoTableDataTd">											
									<logic:present name="embargoDetailsVO" property="processType">
										<bean:define id="processType"  name="embargoDetailsVO" property="processType" />
										<%
										String process= processType.toString();
										String processAry[] =process.split("~");											
										%>													
										<%=processAry[0]%><br/>
										<%=processAry[1]%><br/>
										<%=processAry[2]%>
										</logic:present>												
									</td>
									<td class="iCargoTableDataTd">
										<logic:present name="embargoDetailsVO" property="complianceTypeDescription">
											<bean:define id="complianceTypeDescription" name="embargoDetailsVO" property="complianceTypeDescription" />
											<bean:write name="complianceTypeDescription" />
										</logic:present>						
									</td>
									<td class="iCargoTableDataTd">
										<logic:present name="embargoDetailsVO" property="embargoDescription">
										<bean:define id="embargoDescription" name="embargoDetailsVO" property="embargoDescription" />
										<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" />
										<% description=(String)embargoDescription;%>
										<% referenceNumber=(String)embargoReferenceNumber;%>
										<% if(null != description && description.length()>450){%>
										<%=description.substring(0,450)+"..."%>
										<div class="ic-input-round-border" name="descriptionDiv"  id="descriptionDivId_<%=referenceNumber%>" style="display:none;">
											<div class="ic-row">
												<label><%=description%></label>
											</div>
										</div>
										<img id="descriptionImageId_<%=referenceNumber%>" src="<%=request.getContextPath()%>/images/info.gif" style="cursor:pointer;height:13px;width:13px;" class="info_image" onclick="javascript:showDescription(this,'descriptionDivId_<%=referenceNumber%>','descriptionDiv');"/>										
											<%}else{%>
											<%=description%>
											<%}%>
										</logic:present>
										
									</td>
									<td class="iCargoTableDataTd">
										<logic:notEmpty name="embargoDetailsVO" property="startDate">
											<bean:define id="startDate" name="embargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>													
											<%=startDate.toDisplayDateOnlyFormat()%>
										</logic:notEmpty>
									</td>
									<td class="iCargoTableDataTd">
										<logic:notEmpty name="embargoDetailsVO" property="endDate">
											<bean:define id="endDate" name="embargoDetailsVO" property="endDate"  type="com.ibsplc.icargo.framework.util.time.LocalDate"/>													
											<%=endDate.toDisplayDateOnlyFormat()%>
										</logic:notEmpty>
									</td>																				
									<td class="iCargoTableDataTd">										
										<logic:present name="embargoDetailsVO" property="categoryDescription">
											<bean:define id="categoryDescription" name="embargoDetailsVO" property="categoryDescription" />
											<bean:write name="categoryDescription" />
										</logic:present>
									</td>
									<td class="iCargoTableDataTd">										
										<logic:present name="embargoDetailsVO" property="embargoReferenceNumber">
											<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" />
											<bean:write name="embargoReferenceNumber" />
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

