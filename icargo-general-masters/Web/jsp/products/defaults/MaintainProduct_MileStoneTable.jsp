
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<bean:define id="form"
	name="MaintainProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
	toScope="page" />
<jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />
<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
<table class="fixed-header-table" id="milestoneTable" >
			<thead>
				<tr class="iCargoTableHeadingLeft">
				  <td width="2%" class="iCargoTableHeadingCenter" style="text-align:center">
				  <input type="checkbox" name="checkAllMilestone" onclick="updateHeaderCheckBox(this.form,this,this.form.mileStoneRowId)" /></td>
				  <td width="8%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Milestone" scope="request"/></td>
				  <td width="8%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Type" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.MinTime" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.MaxTime" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.External" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Internal" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Transit" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Alert" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Chaser" scope="request"/></td>
				  <td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.ChaserFrequency" scope="request"/></td>
				  <td width="10%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.maxNoChaser" scope="request"/></td>
				</tr>
				</thead>
		<tbody>
				<business:sessionBean id="milestoneVOsFromSession" moduleName="product.defaults"
					screenID="products.defaults.maintainproduct"
					method="get"
					attribute="productEventVOs" />


					<logic:present name="milestoneVOsFromSession" >
					  <bean:define id="eventList" name="milestoneVOsFromSession" />
						<% String operFlag = null;
							 int index = 0;
						%>


							 <logic:iterate name="eventList" id="eventVO" indexId="rowCount9">

							  	<logic:present name="eventVO" property="operationFlag">
										<bean:define name="eventVO" property="operationFlag" id="operatFlg" />
										<%operFlag=(String)operatFlg;
										%>
							  	</logic:present>

								<logic:present name="eventVO" property="operationFlag" >
							 		<logic:notEqual name="eventVO" property="operationFlag" value="D">
									<bean:define name="eventVO"  property="operationFlag"  id="evntopflag" />

									  <tr>
										 <html:hidden property="milestoneOpFlag" value="<%=(String)evntopflag%>" />
										  <html:hidden property="isRowModified" value="0" />
											<html:hidden property="isNewRowModified" value="0" />
											<td  class="iCargoTableTd" style="text-align:center">
												<html:checkbox property="mileStoneRowId"
													value="<%=new Integer(index).toString()%>" onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)"/>
											</td>

											<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventCode" /></td>
											<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
											<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventType" /></td>
											<td  class="iCargoTableTd">

											<ibusiness:releasetimer property="minTime" style="text-align:right"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
											 type="asTimeComponent"
											 styleId="minTime"
											 indexId="rowCount9"
											 value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>"
											 />
											 </td>
											<td  class="iCargoTableTd">
											<ibusiness:releasetimer property="maxTime"  style="text-align:right"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
											type="asTimeComponent"
											styleId="maxTime"
											indexId="rowCount9"
											value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"
											/>
											</td>
													<td  class="iCargoTableTd ic-center">
													<bean:define name="eventVO" property="external" id="isExternal" />
													<bean:define name="eventVO" property="internal" id="isInternal" />
													<bean:define name="eventVO" property="transit" id="isTransit" />

													<logic:equal name="eventVO" property="external" value="true">
														<input type="checkbox" name="isExternal"
														value="<%=isExternal.toString()%>"
														checked="checked"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
														scope="request"/>'/>
													</logic:equal>
													<logic:notEqual name="eventVO" property="external" value="true">
														<input type="checkbox" name="isExternal"
														value="<%=isExternal.toString()%>"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
														scope="request"/>'/>
													</logic:notEqual>

											</td>

											<td  class="iCargoTableTd ic-center">

													<logic:equal name="eventVO" property="internal" value="true">
														<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
															checked="checked"
															title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
																scope="request"/>' />
													</logic:equal>
													<logic:notEqual name="eventVO" property="internal" value="true">
														<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
															title='<common:message  bundle="MaintainProduct"
															key="products.defaults.milestone.internal"
															scope="request"/>'/>
													</logic:notEqual>
											</td>


											<td  class="iCargoTableTd ic-center">
												<logic:equal name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
														checked="checked"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>' />
												</logic:equal>
												<logic:notEqual name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>'/>
												</logic:notEqual>
											</td>

											<td  class="iCargoTableTd">
											<ihtml:text property="alertTime" indexId="rowCount9" styleId="alertTime"
											style="text-align:right"
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
											maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ALERTTIME"
											/></td>
											<td  class="iCargoTableTd">
											<ihtml:text property="chaserTime"  
											indexId="rowCount9" styleId="chaserTime"
											style="text-align:right"
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_CHASER"
											maxlength="5"
											/></td>

											<%
												double freq = ((ProductEventVO)eventVO).getChaserFrequency();
												int frequency = (int)freq;
											%>
											<td  class="iCargoTableTd">
											<ihtml:text property="chaserFrequency"
											style="text-align:right"
											value="<%=(new Integer(frequency)).toString()%>"
											indexId="rowCount9" styleId="chaserFrequency"
												maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_FREQUENCY"
												/></td>
											<td  class="iCargoTableTd">
											<ihtml:text property="maxNoOfChasers"
											style="text-align:right"
											value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
												maxlength="5"
												indexId="rowCount9" styleId="maxNoOfChasers"
												componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXNOOFCHASERS"

												/></td>
											  </tr>

										</logic:notEqual>
									</logic:present>



							<logic:notPresent name="eventVO" property="operationFlag" >

							  <tr>
								 <html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
								  <html:hidden property="isRowModified" value="0" />
									<html:hidden property="isNewRowModified" value="0" />
									<td  class="iCargoTableTd" style="text-align:center">
										<html:checkbox property="mileStoneRowId"
											value="<%=new Integer(index).toString()%>" onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)"/>
									</td>

									<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventCode" /></td>
									<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
									<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventType" /></td>
									<td  class="iCargoTableTd">

									<ibusiness:releasetimer property="minTime" style="text-align:right"
									 styleId="minTime"
									 componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
									 id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
									 type="asTimeComponent" indexId="rowCount9"
									 value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>"
									 />
									 </td>
									<td  class="iCargoTableTd">
									<ibusiness:releasetimer property="maxTime" style="text-align:right"
									styleId="maxTime"
									componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
									id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
									type="asTimeComponent" indexId="rowCount9"
									value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"
									/>
									</td>
									<td  class="iCargoTableTd ic-center">
											<bean:define name="eventVO" property="external" id="isExt" />
											<bean:define name="eventVO" property="internal" id="isInt" />
											<bean:define name="eventVO" property="transit" id="isTrans" />

											<logic:equal name="eventVO" property="external" value="true">
												<input type="checkbox" name="isExternal" value="<%=isExt.toString()%>"
												checked="checked"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
												scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="external" value="true">
												<input type="checkbox" name="isExternal"
												value="<%=isExt.toString()%>"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
													scope="request"/>'/>
											</logic:notEqual>

									</td>

									<td  class="iCargoTableTd ic-center">

											<logic:equal name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal" value="<%=isInt.toString()%>"
													checked="checked"
													title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
													scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal" value="<%=isInt.toString()%>"
													title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
													scope="request"/>'/>
											</logic:notEqual>
									</td>
									<td  class="iCargoTableTd ic-center">

										<logic:equal name="eventVO" property="transit" value="true">
										 <input type="checkbox" name="isTransit" value="<%=isTrans.toString()%>"
												checked="checked"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>' />
										</logic:equal>
										<logic:notEqual name="eventVO" property="transit" value="true">
											<input type="checkbox" name="isTransit" value="<%=isTrans.toString()%>"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>'/>
										</logic:notEqual>
									</td>

									<td  class="iCargoTableTd"><ihtml:text property="alertTime" 
									style="text-align:right" indexId="rowCount9" styleId="alertTime"
									value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
									maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ALERTTIME"/></td>
									<td  class="iCargoTableTd">
									<ihtml:text property="chaserTime" 
									style="text-align:right"
									indexId="rowCount9" styleId="chaserTime"
									value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
									componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_CHASER"
									maxlength="5"/></td>
											<%
												double freq = ((ProductEventVO)eventVO).getChaserFrequency();
												int frequency = (int)freq;
											%>
									<td  class="iCargoTableTd">
									<ihtml:text property="chaserFrequency" 
									style="text-align:right"
									indexId="rowCount9" styleId="chaserFrequency"
									value="<%=(new Integer(frequency)).toString()%>"
										maxlength="5"  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_FREQUENCY"
										/></td>
									<td  class="iCargoTableTd">
									<ihtml:text property="maxNoOfChasers" 
									style="text-align:right"
									indexId="rowCount9" styleId="maxNoOfChasers"
									value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
										maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXNOOFCHASERS"

												/></td>
									  </tr>

									</logic:notPresent>


									<logic:equal name="eventVO" property="operationFlag" value="D">

											<bean:define name="eventVO" property="external" id="ext" />
											<bean:define name="eventVO" property="internal" id="inter" />
											<bean:define name="eventVO" property="transit" id="trans" />
											<html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
											<html:hidden property="isRowModified" value="0" />
											<html:hidden property="isNewRowModified" value="0" />
											<html:hidden property="mileStoneRowId" value="<%=new Integer(index).toString()%>" />

											<html:hidden property="isExternal" value="<%=ext.toString()%>" />
											<html:hidden property="isInternal" value="<%=inter.toString()%>" />
											<html:hidden property="isTransit" value="<%=trans.toString()%>" />
											<html:hidden property="eventCode" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
											<html:hidden property="eventType" value="<%=((ProductEventVO)eventVO).getEventType()%>" />
											<html:hidden property="minTime" value="<%=new Double(((ProductEventVO)eventVO).getMinimumTime()).toString()%>" />
											<html:hidden property="maxTime" value="<%=new Double(((ProductEventVO)eventVO).getMaximumTime()).toString()%>" />
											<html:hidden property="alertTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>" />
											<html:hidden property="chaserTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>" />
											<html:hidden property="chaserFrequency" value="<%=new Double(((ProductEventVO)eventVO).getChaserFrequency()).toString()%>" />
											<html:hidden property="maxNoOfChasers" value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>" />

										</logic:equal>
										<%
										index++;

										%>
								</logic:iterate>
								</logic:present>

				</tbody>
				</table>
				</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />
