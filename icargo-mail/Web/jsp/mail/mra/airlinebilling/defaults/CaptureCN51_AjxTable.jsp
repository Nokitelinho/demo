<%--
* Project	 		: iCargo
* Module Code & Name		: mailtracking.mra-airlinebilling-defaults
* File Name			: CaptureCN51.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2122
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<business:sessionBean
  		id="KEY_ONETIMEVALUES"
  		moduleName="mailtracking.mra.airlinebilling.defaults"
  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
  		method="get"
  		attribute="oneTimeValues" />

<business:sessionBean
  		id="KEY_DETAILS"
  		moduleName="mailtracking.mra.airlinebilling.defaults"
  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
  		method="get"
  		attribute="cn51Details" />
  		
  		
  <bean:define id="form" name="CaptureCN51Form"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form"
	toScope="page" />

  <ihtml:form action="/mailtracking.mra.airlinebilling.defaults.capturecn51.screenload.do">


  <ihtml:hidden name="form" property="linkStatusFlag" />
  <ihtml:hidden name="form" property="statusFlag" />
  <ihtml:hidden name="form" property="screenFlag" />
  <div class="ic-content-main">
  <div class="ic-main-container">
  <table width="100%" class="scrollTable" id="Form-1Details" >
  <thead class="fixedHeader" id="fixedHeader">
	   <tr>
			<th class="iCargoTableHeader" width="2%" rowspan="2"><input type="checkbox" name="selectAll" id="selectAll" onclick="updateHeaderCheckBox(targetFormName, targetFormName.selectAll, targetFormName.select);"/></th>
			<th class="iCargoTableHeader"  colspan="2" >
			<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.carriage" />
			</th>
			<th class="iCargoTableHeader" rowspan="2" >
			<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.category" />
			</th>
			<th class="iCargoTableHeader"  colspan="3">
			<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.lcao" />
			</th>
			<th class="iCargoTableHeader" colspan="3">
			<common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.cp" />
			</th>
	  </tr>
		<tr>
			 <th   class="iCargoTableHeader">
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.from" />
			 </th>
			 <th   class="iCargoTableHeader">
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.to" />
			 </th>
			 <th  class="iCargoTableHeader">
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.wt" />
			 </th>
			 <th  class="iCargoTableHeader" >
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.rate" />
			 </th>
			 <th  class="iCargoTableHeader" >
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.amount" />
			 </th>
			 <th  class="iCargoTableHeader">
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.wt" />
			 </th>
			 <th class="iCargoTableHeader" >
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.rate" />
			 </th>
			 <th class="iCargoTableHeader" >
			 <common:message   key="mailtracking.mra.defaults.capturecn51.tablehead.amount" />
			 </th>
		  </tr>
				</thead>
				<tbody class="scrollContent">
				<logic:present name="KEY_DETAILS">
				<%System.out.println("KEY_DETAILSsssshihihi!!!!!");%>
				  <logic:present name="KEY_DETAILS" property="cn51DetailsVOs">
					<%System.out.println("KEY_DETAILSssss!!!!!");%>
					<bean:define id="airlineCN51DetailsVOs" name="KEY_DETAILS" property="cn51DetailsVOs"/>
					<%System.out.println("KEY_DETAILSssss!!!!!!"+airlineCN51DetailsVOs);%>
					<logic:iterate id="airlineCN51DetailsVO" name="airlineCN51DetailsVOs"
							type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO" indexId="rowCount">

					<logic:present name="airlineCN51DetailsVO" property="operationFlag">
						<ihtml:hidden name="airlineCN51DetailsVO" property="operationFlag"/>
					</logic:present>
					<logic:notPresent name="airlineCN51DetailsVO" property="operationFlag">
						<ihtml:hidden name="CaptureCN51Form" property="operationFlag" value=""/>
					</logic:notPresent>
					<logic:notEqual name="airlineCN51DetailsVO" property="operationFlag" value="D">
					<common:rowColorTag index="rowCount">
					<tr bgcolor="<%=color%>">
						<td class="iCargoTableDataTd">
							<ihtml:checkbox property="select" value="<%=rowCount.toString()%>" />
						</td>
						<td>
							<logic:present name="airlineCN51DetailsVO" property="carriagefrom" >
								<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGEFROM" property="carriagesFrom" styleId="carriagesFrom"  maxlength="4" style="text-align : right;border: 0px;background:" value="<%=airlineCN51DetailsVO.getCarriagefrom()%>"/>
							 </logic:present>
							 <logic:notPresent name="airlineCN51DetailsVO" property="carriagefrom" >
								<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGEFROM" property="carriagesFrom" styleId="carriagesFrom"  maxlength="4" style="text-align : right;border: 0px;background:" value=""/>													
							</logic:notPresent>

							<img name="carriagesFromlov" id="carriagesFromlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].carriagesFrom.value,'carriagesFrom','1','carriagesFrom','',<%=rowCount%>)" alt="" />
						</td>
						<td>
							<logic:present name="airlineCN51DetailsVO" property="carriageto" >
								<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGETO" property="carriagesTo" styleId="carriagesTo"  maxlength="4" style="text-align : right;border: 0px;background:" value="<%=airlineCN51DetailsVO.getCarriageto()%>"/>
							 </logic:present>
							 <logic:notPresent name="airlineCN51DetailsVO" property="carriageto" >
								<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGETO" property="carriagesTo" styleId="carriagesTo"  maxlength="4" style="text-align : right;border: 0px;background:" value=""/>
							</logic:notPresent>

							<img name="carriagesTolov" id="carriagesTolov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].carriagesTo.value,'carriagesTo','1','carriagesTo','',<%=rowCount%>)" alt="" />
						</td>
						<td>	
							<%String mailCat = "";%>
							<logic:present name="airlineCN51DetailsVO" property="mailcategory" >
								<%mailCat = airlineCN51DetailsVO.getMailcategory();%>
							 </logic:present>


							<ihtml:select property="categories" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CATEGORY" value="<%=mailCat%>">																	
							<logic:present name="KEY_ONETIMEVALUES">
							<html:option value=""></html:option>
							<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
								<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
							</logic:present>
							</ihtml:select>
						</td>
						<%double lcWt = 0.0;%>
						<%double lcRt = 0.0;%>
						<%double lcAt = 0.0;%>
						<%double cpWt = 0.0;%>
						<%double cpRt = 0.0;%>
						<%double cpAt = 0.0;%>
						<%boolean lcreadonly=true;
						boolean cpreadonly=true;%>
						<logic:equal name="airlineCN51DetailsVO" property="mailsubclass" value="LC">
						<%lcreadonly=false;%>
							<%lcWt = airlineCN51DetailsVO.getTotalweight();%>
							<%lcRt = airlineCN51DetailsVO.getApplicablerate();%>
							<%lcAt = airlineCN51DetailsVO.getTotalamountincontractcurrency().getAmount();%>
						</logic:equal>
						<logic:equal name="airlineCN51DetailsVO" property="mailsubclass" value="CP">
						<%cpreadonly=false;%>
							<%cpWt = airlineCN51DetailsVO.getTotalweight();;%>
							<%cpRt = airlineCN51DetailsVO.getApplicablerate();%>
							<%cpAt = airlineCN51DetailsVO.getTotalamountincontractcurrency().getAmount();%>									
						</logic:equal>
						<logic:equal name="airlineCN51DetailsVO" property="mailsubclass" value="N">
						<%cpreadonly=false;%>
						<%lcreadonly=false;%>
						</logic:equal>
						<td>
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHT"  property="wtLCAO" styleId="wtLCAO"  maxlength="20" indexId="rowCount" style="text-align : right;border: 0px;background:"  value="<%=String.valueOf(lcWt)%>" onblur="updateCpStatus(this)" disabled="<%=lcreadonly%>"/>
						</td>

						<td>
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_RATE"  property="rateLCAO" styleId="rateLCAO"  maxlength="20" indexId="rowCount" style="text-align : right;border: 0px;background:" value="<%=String.valueOf(lcRt)%>" onblur="updateCpStatus(this)" disabled="<%=lcreadonly%>"/>
						</td>

						<td>
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AMOUNT"  property="amountLCAO" styleId="amountLCAO"  maxlength="20" indexId="rowCount" style="text-align : right;border: 0px;background:" value="<%=String.valueOf(lcAt)%>" onblur="updateCpStatus(this)" readonly="true"/>
						</td>

						<td >
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTCP"  property="wtCP" styleId="wtCP"  maxlength="16" indexId="rowCount" style="text-align : right;border: 0px;background:"  value="<%=String.valueOf(cpWt)%>" onblur="updateLcStatus(this)" disabled="<%=cpreadonly%>"/>
						</td>
						<td >
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_RATECP"  property="rateCP" styleId="rateCP"  maxlength="20" indexId="rowCount" style="text-align : right;border: 0px;background:"  value="<%=String.valueOf(cpRt)%>" onblur="updateLcStatus(this)" disabled="<%=cpreadonly%>"/>
						</td>
						<td >
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AMOUNTCP"  property="amountCP" styleId="amountCP"  maxlength="20" indexId="rowCount" style="text-align : right;border: 0px;background:"  value="<%=String.valueOf(cpAt)%>" onblur="updateLcStatus(this)" readonly="true"/>
						</td>

					</tr>
					</common:rowColorTag>
					</logic:notEqual>

					</logic:iterate>
				   </logic:present>
				   </logic:present>

				</tbody>
			</table>
			</div>
			</div>
		</ihtml:form>
		<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>