<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>	
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO"%>
<%@ page info="lite" %>

			
<head>
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
<bean:define id="form"
		name="MailPerformanceForm" 
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm" 
		toScope="page" />

		 
		 <business:sessionBean id="serviceLevelSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.mailperformance" 
		 method="get" 
		 attribute="serviceLevels" />
		 
		 
		 <business:sessionBean id="mailServiceStandardVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailServiceStandardVOs"/>

		 <business:sessionBean id="mailServiceStndVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailServiceStndVOs"/>
		 

 <div class="card-header d-flex justify-content-end">
                    <div class="tool-bar align-items-center  pad-y-2sm">
						<ihtml:nbutton id='btnSerStdAdd' styleClass="btn btn-primary"  property="btnSerStdAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
						</ihtml:nbutton>
						<ihtml:nbutton id='btnDelete' styleClass="btn btn-primary"  property="btnDelete" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
                    </div>
					<!-- Pagination Starts -->
					 <div class="card-header card-header-action pad-y-xs">
						<div class="mega-pagination">
							<logic:present name="mailServiceStandardVOs">	
							  <bean:define id="serStdList" name="mailServiceStandardVOs"/>
								<common:enhancedPaginationTag
								pageURL='javascript:submitPage(lastPageNum,displayPage)'
								name="serStdList" id="serStdList"
								styleClass="ic-pagination-lg"
								lastPageNum="<%=form.getLastPageNum() %>"
								renderLengthMenu="true" lengthMenuName="defaultPageSize" 
								defaultSelectOption="<%=form.getDefaultPageSize() %>"  
								lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
								/>
				</logic:present>
				
                </div>
		</div>
                </div>
                <div class="card-body p-0" id="serviceStandard">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="srvStdTable" class="table table-x-md m-0" style="width:100%">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell"><input type="checkbox" name="checkAll" onclick="updateHeaderCheckBox(this.form,this,this.form.serviceStdrowId);"></th><!--Modified as part of ICRD-304479-->
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.origin" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.destination" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.servicelevel" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.servicestandard" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.contractid" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.scanningwaived" /></th>
                            </tr>
                        </thead>
                        <tbody id="serviceStandardTableBody" >
						<logic:present name="mailServiceStndVOs">
						<logic:iterate id ="MailServiceStandardVO" name="mailServiceStndVOs" type="MailServiceStandardVO" indexId="rowNum">
						<% String origin="Origin";
						   String dest="Dest";%>
						 <tr>
						<ihtml:hidden property="serviceStdoperationFlag" value="U" />
						 <td class="text-center">
							&nbsp;&nbsp; <ihtml:checkbox property="serviceStdrowId" id="<%=String.valueOf(rowNum)%>" value="<%=String.valueOf(rowNum)%>" />
						</td>
						<td>
						<logic:present name="MailServiceStandardVO" property="originCode">
						<div class="input-group">
						<ihtml:text tabindex="4" property="originCode" value="<%=MailServiceStandardVO.getOriginCode()%>" id="serStdOrigin" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ORGCODE" />
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="<%=origin+String.valueOf(rowNum)%>" property="serviceOriginLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>	
						</div>
						</logic:present>
						<logic:notPresent name="MailServiceStandardVO" property="originCode">
						<div class="input-group">
						<ihtml:text tabindex="4" value="" property="originCode" id="serStdOrigin" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ORGCODE"/>
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="<%=origin+String.valueOf(rowNum)%>" property="serviceOriginLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>	
						</div>
						</logic:notPresent>
						</td>
						<td>
						<logic:present name="MailServiceStandardVO" property="destinationCode">
						<div class="input-group">
						<ihtml:text tabindex="4" property="destinationCode" value="<%=MailServiceStandardVO.getDestinationCode()%>" id="serStdDest" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_DESTCODE"/>	
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="<%=dest+String.valueOf(rowNum)%>" property="serviceDestLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>
						</div>
						</logic:present>
						<logic:notPresent name="MailServiceStandardVO" property="destinationCode">
						<div class="input-group">
						<ihtml:text tabindex="4" value="" property="destinationCode" id="serStdDest" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_DESTCODE"/>
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="<%=dest+String.valueOf(rowNum)%>" property="serviceDestLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>
						</div>
						</logic:notPresent>
						</td>
						<td>
						<logic:present name="MailServiceStandardVO" property="servicelevel">
						<ihtml:select tabindex="4" property="servicelevel" value="<%=MailServiceStandardVO.getServicelevel()%>" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_SERLEVEL" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="serviceLevelSession">
									<logic:iterate id="serviceLevels" name="serviceLevelSession">
										<bean:define id="fieldValue" name="serviceLevels" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="serviceLevels" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>		
						</logic:present>
						<logic:notPresent name="MailServiceStandardVO" property="servicelevel">
						<ihtml:select tabindex="4" property="servicelevel" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_SERLEVEL" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="serviceLevelSession">
									<logic:iterate id="serviceLevels" name="serviceLevelSession">
										<bean:define id="fieldValue" name="serviceLevels" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="serviceLevels" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>		
						</logic:notPresent>
						</td>
						<td>
						<logic:present name="MailServiceStandardVO" property="servicestandard">
						<ihtml:text tabindex="4" property="servicestandard" value="<%=MailServiceStandardVO.getServicestandard()%>" id="ServiceStandard" styleClass="form-control" maxlength="5" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_SERVICETD"/>  
						</logic:present>
						<logic:notPresent name="MailServiceStandardVO" property="servicestandard">
						<ihtml:text value="" tabindex="4" property="servicestandard" id="ServiceStandard" styleClass="form-control" maxlength="5" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_SERVICETD"/>  
						</logic:notPresent>	
						</td>
						<td>
						<logic:present name="MailServiceStandardVO" property="contractid">
						<ihtml:text tabindex="4" property="contractid" value="<%=MailServiceStandardVO.getContractid()%>" id="ConId" styleClass="form-control" maxlength="10" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_CONID"/>   
						</logic:present>
						<logic:notPresent name="MailServiceStandardVO" property="contractid">
						<ihtml:text value="" tabindex="4" property="contractid" id="ConId" styleClass="form-control" maxlength="10" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_CONID"/>   
						</logic:notPresent>
						</td>
						 <td class="text-center"><!--modified by A-8353 for ICRD-293368 -->
						<logic:equal name="MailServiceStandardVO" property="scanWaived" value="Y">
						<ihtml:checkbox tabindex="4" id="scanWaived" styleClass="form-check-input" property="scanWaived" value="Y"/>
						</logic:equal>
						<logic:notEqual name="MailServiceStandardVO" property="scanWaived" value="Y">
						<ihtml:checkbox tabindex="4" id="scanWaived" styleClass="form-check-input" property="scanWaived" value=""/>
						</logic:notEqual>						
						</td>
                         </tr>
						</logic:iterate>
						</logic:present>
						<tr  template="true" id="serviceStandardTemplateRow" style="display:none">
						<ihtml:hidden property="serviceStdoperationFlag" value="NOOP" />
						 <td class="text-center">
							<input type="checkbox"  name="serviceStdrowId"/>
						</td>
						<td>
						<div class="input-group">
						<ihtml:text tabindex="4" value="" property="originCode" id="airportOrigin" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ORGCODE"/>
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="serviceOriginLOVTemp" property="serviceOriginLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>	
						</div>
						</td>
						<td>
						<div class="input-group">
						<ihtml:text tabindex="4" value="" property="destinationCode" id="airportDest" styleClass="form-control" maxlength="50" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_DESTCODE"/>
						<div class="input-group-append">
						<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="serviceDestLOVTemp" property="serviceDestLOVTemp">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
						</div>	
						</div>
						</td>
						<td>
						 <ihtml:select tabindex="4" property="servicelevel" value="" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_SERLEVEL" >
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="serviceLevelSession">
									<logic:iterate id="serviceLevels" name="serviceLevelSession">
										<bean:define id="fieldValue" name="serviceLevels" property="fieldValue" />
										<html:option value="<%=(String)fieldValue %>"><bean:write name="serviceLevels" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>
						</td>
						<td>
						<ihtml:text value="" tabindex="4" property="servicestandard" id="ServiceStandard" styleClass="form-control" maxlength="5" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_SERVICESTANDARDS_SERVICETD"/>      
						</td>
						<td>
						<ihtml:text value="" tabindex="4" property="contractid" id="ConId" styleClass="form-control" maxlength="10" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_CONID"/>                                                                                                 
						</td>
						<td class="text-center"><!--modified by A-8353 for ICRD-293368 -->
						<ihtml:checkbox tabindex="4" id="scanWaivedTemplate" styleClass="form-check-input" property="scanWaived" value=""/><!--Modified as part of ICRD-304479-->
						</td>
						
						</tr>
                        </tbody>
                    </table>
				 </div>
                </div>
