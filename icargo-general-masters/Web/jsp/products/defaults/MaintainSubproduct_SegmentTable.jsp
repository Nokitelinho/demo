<%@ page language="java" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<bean:define id="form"
name="MaintainSubProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
toScope="page" />


<div class="iCargoContent" style="overflow:auto;">
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="products.defaults.maintainsubproductscreenload.do">

 <business:sessionBean id="segmentVOsFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainsubproducts" method="get" attribute="segmentVOs" />

<table class="fixed-header-table" width="100%">
   <thead>
  	<tr class="iCargoTableHeadingLeft">
	   <td width="18%" class="iCargoTableHeadingCenter ic-center">
		<input type="checkbox" name="checkbox7" value="checkbox"
		onclick="updateHeaderCheckBox(this.form,this,this.form.segmentRowId)"
		title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifysegment"
							scope="request"/>'/></td>
		<td width="82%">
			<common:message  key="segment" scope="request"/>
		</td>
 	 </tr>
   </thead>
   <tbody>
	<logic:present name="segmentVOsFromSession" >
		<bean:define id="segmentList" name="segmentVOsFromSession"/>
	          <%	int rowId = 0; String hiddenSegment = "hiddenSegment"; 	%>
	<logic:iterate id="segmentVO" name="segmentList" indexId="nIndex">
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
		  <input type="hidden" name="isSegmentRowModified" value="0" />
		  <td class="iCargoTableDataTd ic-center">
		   <ihtml:checkbox property="segmentRowId"
					value="<%=new Integer(rowId).toString()%>"
					componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_SEGMENT" />
			</td>
		  <td class="iCargoTableDataTd">

		  <ibusiness:route maxlength="7"
		  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ROUTE"
		  id="<%=new Integer(rowId).toString()%>"
		  property="segment" indexId="nIndex"
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
				<%String odPair = origin.toString()+"-"+destination.toString();%>
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
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

