
		 <%--
  /***********************************************************************
 * Project	 			:  iCargo
 * Module Code & Name	:  IN - Inventory Control
 * File Name			:  MaintainProduct_Restriction_Tab_Route.jsp
 * Date					:  15-July-2001
 * Author(s)			:  Amritha S

 *************************************************************************/
  --%>

 <%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO" %>

 <bean:define id="form"
 	name="MaintainProductForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
 	toScope="page" />
	
<business:sessionBean id="segmentVOsFromSession"
		 	moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productSegmentVOs" />
			
<business:sessionBean id="stationVOsFromSession" moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
		attribute="productStationVOs" />
		<logic:present name="stationVOsFromSession">
			<bean:define id="stationList" name="stationVOsFromSession" />

		</logic:present>
	
	
	
<div class="ic-section">
	<div class="ic-row">
		<h3><common:message bundle="<%=form.getBundle() %>" key="products.defaults.routeRestr" scope="request"/></h3>
	</div>
	<div class="ic-row">
		<div class="ic-col-33">
			<div class="ic-section ic-marg-3">
				<div class="ic-border">
					<div class="ic-row">
						<ihtml:radio property="segmentStatus" value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_RESTRICTSEGMENT"/>
							<common:message  key="products.defaults.Restrict" scope="request"/>
						<ihtml:radio property="segmentStatus" value="Allow" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_ALLOWSEGMENT"/>
							<common:message  key="products.defaults.Allow" scope="request"/>
					</div>
					<div class="ic-row">
						<div class="ic-button-container">
							<a href="#" class="iCargoLink" onclick="addRowSegRestriction();">
								<common:message  key="products.defaults.add" scope="request"/>
							</a>
							| 
							<a href="#" class="iCargoLink" onclick="delRowSegRestriction();">
								<common:message  key="products.defaults.delete" scope="request"/>
							</a>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div12" style="height:120px">
							<table id="table5" class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingLeft">
										<td width="18%" class="iCargoTableHeadingCenter">
											<div>
												<input type="checkbox" name="checkAllSegment"
													onclick="updateHeaderCheckBox(this.form,this,this.form.segmentRowId)"
													title='<common:message  bundle="MaintainProduct" key="products.defaults.specifysegment"
													scope="request"/>'/>
											</div>
										</td>
										<td width="82%" class="iCargoTableHeadingCenter">
											<common:message  key="products.defaults.Segment" scope="request"/>
										</td>
									</tr>
								</thead>
							<tbody id="segmentBody">
								<logic:present name="segmentVOsFromSession" >
									<bean:define id="segmentList" name="segmentVOsFromSession"/>
									<%	int rowId = 0; String hiddenSegment = "hiddenSegment"; 	%>
									<logic:iterate id="segmentVO" name="segmentList" indexId="rowCount1">
										<%
											String operationFlag ="";
											if(((RestrictionSegmentVO)segmentVO).getOperationFlag()!=null){
												operationFlag = ((RestrictionSegmentVO)segmentVO).getOperationFlag();
											}
											if(!"D".equals(operationFlag)){
											if("I".equals(operationFlag)){
										%>
										<logic:present name="segmentVO" property="origin">
											<bean:define id="origin" name="segmentVO" property="origin"/>
												<logic:present name="segmentVO" property="destination">
													<bean:define id="destination" name="segmentVO" property="destination"/>
													<%
														String segmentValue=" ";
														if((!origin.toString().equals("")) && (!destination.toString().equals(""))){
															segmentValue=origin.toString()+"-"+destination.toString();
														}
													%>
								<tr>
									<html:hidden property="isSegmentRowModified" value="0" />
									<td style="text-align:center" class="iCargoTableTd">								
										<input type="hidden" name="segmentOperationFlag" value="I"/>
										<html:checkbox property="segmentRowId"
											value="<%=new Integer(rowId).toString()%>"
											onclick="toggleTableHeaderCheckbox('segmentRowId',this.form.checkAllSegment)"/>
									</td>
									<td  class="iCargoTableTd">
									<ibusiness:route
										componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ROUTE"
										id="<%=new Integer(rowId).toString()%>"
										maxlength="7" property="segment"
										value="<%=segmentValue.trim()%>" segmentLength="1"/>
									</td>
								</tr>
								</logic:present>
							</logic:present>

								<%}if(!"I".equals(operationFlag))
								{%>
								 
									<logic:present name="segmentVO" property="origin">
									<bean:define id="origin" name="segmentVO" property="origin"/>

									<logic:present name="segmentVO" property="destination">
									<bean:define id="destination" name="segmentVO" property="destination"/>


									<%
									String segmentValue=" ";
									if((!origin.toString().equals("")) && (!destination.toString().equals(""))){
											segmentValue=origin.toString()+"-"+destination.toString();
									}

									%>
								<tr>


								  <html:hidden property="isSegmentRowModified" value="0" />
								  <td style="text-align:center" class="iCargoTableTd">
								
								  <input type="hidden" name="segmentOperationFlag" value="U"/>
								  <html:checkbox property="segmentRowId"
											value="<%=new Integer(rowId).toString()%>"
											onclick="toggleTableHeaderCheckbox('segmentRowId',this.form.checkAllSegment)"/>
											</td>
								  <td  class="iCargoTableTd">

								  <ibusiness:route
								  	componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ROUTE"
								  	id="<%=new Integer(rowId).toString()%>"
								  	maxlength="7" property="segment"
										value="<%=segmentValue.trim()%>" segmentLength="1" readonly="true"/>
								  </td>
								</tr>
								</logic:present>
								</logic:present>

								<%}}else if("D".equals(operationFlag))
								{%>

									<logic:present name="segmentVO" property="origin">
									<bean:define id="origin" name="segmentVO" property="origin"/>
									<logic:present name="segmentVO" property="destination">
									<bean:define id="destination" name="segmentVO" property="destination"/>
									 <html:hidden property="segmentOperationFlag" value="D" />
										<html:hidden property="segmentRowId" value="<%=new Integer(rowId).toString()%>" />
										<html:hidden property="isSegmentRowModified" value="0" />
										<%String odPair = origin.toString()+"-"+destination.toString();
										%>
										<html:hidden value="<%=odPair%>" property="segment"/>
									</logic:present>
									</logic:present>

								 <% }
								 rowId++;
								 %>
								</logic:iterate>

						</logic:present>
						<tr template="true" id="segmentTemplateRow" style="display:none">

						<%int rowId = 0;%>
						<td style="text-align:center" class="iCargoTableTd" >

							  <input type="hidden" name="segmentOperationFlag" value="NOOP"/>
							  <ihtml:checkbox property="segmentRowId" onclick="toggleTableHeaderCheckbox('segmentRowId',this.form.checkAllSegment)"/>
						</td>
						 <td  class="iCargoTableTd">

								<ibusiness:route
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ROUTE"
								id=""
								maxlength="7" property="segment"
									value="" segmentLength="1"/>
						 </td>
						  <html:hidden property="isSegmentRowModified" value="" />
					  </tr>
					</tbody>
				  </table>
				</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-col-33">
			<div class="ic-section ic-marg-3">
				<div class="ic-border">
					<div class="ic-row">
						<ihtml:radio property="originStatus" value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_RESTRICTORIGIN" />
							<common:message  key="products.defaults.Restrict" scope="request"/>
						<ihtml:radio property="originStatus" value="Allow" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_ALLOWORIGIN"/>
							<common:message  key="products.defaults.Allow" scope="request"/>
					</div>
					<div class="ic-row">
						<div class="ic-button-container">
							<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenOriginLov.do',
								'products.defaults.screenloadStationLov.do', 'products.defaults.selectOriginNew.do','','div15')" >
								<common:message  key="products.defaults.add" scope="request"/>
							</a>
							| 
							<a href="#" class="iCargoLink" 
								onclick="deleteTableRow1('products.defaults.deleteOriginNew.do','originCheck','div15')">
								<common:message  key="products.defaults.delete" scope="request"/>
							</a>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div15" style="height:120px">

			  <table class="fixed-header-table" id="origin">
				  <thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="18%"class="iCargoTableHeadingCenter"><div>
					<input type="checkbox" name="checkAllOrigin"
					title='<common:message  bundle="MaintainProduct" key="products.defaults.specifyorigin"
					scope="request"/>'
					onclick="updateHeaderCheckBox(this.form,this,this.form.originCheck)" /></div></td>
					<td width="82%" class="iCargoTableHeadingCenter">
					<common:message  key="products.defaults.origin" scope="request"/></td>
				  </tr>
					</thead>
					<tbody>
					<logic:present name="stationList">
						<logic:iterate name="stationList" id="stationVO" indexId="rowCount5" >
							<bean:define name="stationVO" id="originFlag" property="isOrigin" />
							<logic:equal name="originFlag" value="true" >
							<bean:define name="stationVO" id="originStation" property="station" />
								<logic:present name="stationVO" property="operationFlag">
									<logic:notEqual name="stationVO" property="operationFlag" value="D">
									<tr >
										  <td  style="text-align:center" class="iCargoTableTd">
										  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
										  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkAllOrigin)"/></td>
										  <td  class="iCargoTableTd"><bean:write name="stationVO" property="station" />
										  <html:hidden property="origin" value="<%=(String)originStation%>" />
										  </td>
									</tr>
									</logic:notEqual>
								</logic:present>
								<logic:notPresent name="stationVO" property="operationFlag">
								<tr>
									  <td style="text-align:center" class="iCargoTableTd">
									  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
									  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkAllOrigin)"/></td>
									  <td  class="iCargoTableTd"><bean:write name="stationVO" property="station" />
									  <html:hidden property="origin" value="<%=(String)originStation%>" />
									  </td>
								</tr>
								</logic:notPresent>
						</logic:equal>
						</logic:iterate>
					</logic:present>
					</tbody>
				  </table>
				</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-col-33">
			<div class="ic-section ic-marg-3">
				<div class="ic-border">
					<div class="ic-row">
						<ihtml:radio property="destinationStatus" value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_RESTRICTDESTN"/>
							<common:message  key="products.defaults.Restrict" scope="request"/>
						<ihtml:radio property="destinationStatus" value="Allow" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_ALLOWDESTN"	/>
							<common:message  key="products.defaults.Allow" scope="request"/>
					</div>
					<div class="ic-row">
						<div class="ic-button-container">
							<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenDestnLov.do',
								'products.defaults.screenloadStationLov.do',
								'products.defaults.selectDestinationNew.do','','div16')">
								<common:message  key="products.defaults.add" scope="request"/>
							</a>
							|
							<a href="#" class="iCargoLink" 
								onclick="deleteTableRow1('products.defaults.deleteDestinationNew.do','destinationCheck','div16')">
								<common:message  key="products.defaults.delete" scope="request"/>
							</a>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div16" style="height:120px">
			  <table class="fixed-header-table" id="destn">
			  <thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="18%" class="iCargoTableHeadingCenter">
					<input type="checkbox" name="checkAllDestn"
					title='<common:message  bundle="MaintainProduct" key="products.defaults.specifydestn"
					scope="request"/>'
					onclick="updateHeaderCheckBox(this.form,this,this.form.destinationCheck)" /></td>
					<td class="iCargoTableHeadingCenter" width="82%">
					<common:message  key="products.defaults.dest" scope="request"/></td>
				  </tr>
					</thead>
				<tbody>
					<logic:present name="stationList">
						<logic:iterate name="stationList" id="stationVO" indexId="rowCount6" >
							<bean:define name="stationVO" id="originFlag" property="isOrigin" />
							<logic:equal name="originFlag" value="false" >
							<bean:define name="stationVO" id="destn" property="station" />
								<logic:present name="stationVO" property="operationFlag">
									<logic:notEqual name="stationVO" property="operationFlag" value="D">
									<tr>
										  <td style="text-align:center" class="iCargoTableTd">
										  <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
										  onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkAllDestn)"/></td>
										  <td  class="iCargoTableTd"><bean:write name="stationVO" property="station" />
										  <html:hidden property="destination" value="<%=(String)destn%>" />
										  </td>
									</tr>
									</logic:notEqual>
								</logic:present>
								<logic:notPresent name="stationVO" property="operationFlag">
								<tr>
									  <td style="text-align:center" class="iCargoTableTd">
									  <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
									  onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkAllDestn)"/></td>
									  <td class="iCargoTableTd"><bean:write name="stationVO" property="station" />
									  <html:hidden property="destination" value="<%=(String)destn%>" />
									  </td>
								</tr>
								</logic:notPresent>
						</logic:equal>
						</logic:iterate>
					</logic:present>
					</tbody>
				  </table>
				</div>
					</div>
				</div>
			</div>
		</div>
	</div>

