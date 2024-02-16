
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">

 <business:sessionBean id="segmentVOsFromSession"
		 	moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productSegmentVOs" />

<table id="table5" class="fixed-header-table">
				 <!-- thead Goes here-->
				<thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="16%" class="iCargoTableHeadingCenter"><div>
					<input type="checkbox" name="checkAllSegment"
					onclick="updateHeaderCheckBox(this.form,this,this.form.segmentRowId)"
					  title='<common:message  bundle="MaintainProduct" key="products.defaults.specifysegment"
									scope="request"/>'/></div></td>
					<td width="64%" class="iCargoTableHeadingCenter">
					<common:message  key="products.defaults.Segment" scope="request"/></td>
				  </tr>
				</thead>
				<tbody>

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
								  %>

								  <html:hidden property="segmentOperationFlag" value="<%=operationFlag%>" />
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
								  <html:checkbox property="segmentRowId"
											value="<%=new Integer(rowId).toString()%>" 
											onclick="toggleTableHeaderCheckbox('segmentRowId',this.form.checkAllSegment)"/></td>
								  <td class="iCargoTableTd">

								  <ibusiness:route
								  	componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ROUTE"
								  	id="<%=new Integer(rowId).toString()%>"
								  	maxlength="7" property="segment"
										value="<%=segmentValue.trim()%>" segmentLength="1"/>
								  </td>
								</tr>
								</logic:present>
								</logic:present>

								<%}else
								{%>

									<logic:present name="segmentVO" property="origin">
									<bean:define id="origin" name="segmentVO" property="origin"/>
									<logic:present name="segmentVO" property="destination">
									<bean:define id="destination" name="segmentVO" property="destination"/>
									 <html:hidden property="segmentOperationFlag" value="<%=operationFlag%>" />
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

					</tbody>

				  </table>
	</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />

