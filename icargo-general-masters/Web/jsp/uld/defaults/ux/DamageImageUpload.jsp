<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: DocumentRepository.jsp
* Date				: 14/11/2018
* Author(s)			: A-7636
--%>

<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.ibsplc.icargo.framework.util.math.BigDouble"%>
<%@ page import="com.ibsplc.icargo.framework.model.DocumentModel"%>
<%@page import ="java.util.ArrayList"%>
<%@page import ="com.ibsplc.icargo.framework.model.UploadFileModel"%>
<%@ page info="lite" %>

		
		
<html:html>
	<bean:define id="form"
		name="maintainDamageReportRevampForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
		toScope="page"/>
	<!-- session bean -->	
		<business:sessionBean id="damageImageList" moduleName="uld.defaults"
		screenID="uld.defaults.ux.maintaindamagereport" method="get"
		attribute="damageImageList" screenMode="popup"/>
		
	
		
			
		
	<head> 	
		<title>Image upload</title>					
		<meta name="decorator" content="popup_panel">
		<common:include type="script" src="/js/uld/defaults/ux/DamageImageUpload_Script.jsp" />	
		<common:include type="css" src="/css/colorbox.css"/>
<common:include type="script" src="/js/jquery.colorbox-min.js"/>
	</head>
	<body>
		<div class="container" style="width:100%;height:100%">
			<ihtml:form action="/uld.defaults.ux.damageImageUploadScreenLoad.do" method="POST" enctype="multipart/form-data">		
			<ihtml:hidden id="statusFlag" property="statusFlag" />	
			<ihtml:hidden id="imageIndex" property="imageIndex" />
				<div id="dialog-confirm" class="custom-dialog ui-dialog">
                    <div class="popcontent form-body card-xs">
                        <div class="datagrid left full-width m-t-10 " id="div1" style="height:190px;">
								<table class="table-ui-set">
									<thead>
										<tr>
											<th class="text-center" style="width:10%">Select</th>
											<th class="text-left">Upload file</th>
											<th style="width:10%">&nbsp;</th>
										</tr>
									</thead>
									<tbody id="addDetailBody">
									<logic:present name="damageImageList">
									<bean:define id="damageImageList" name="damageImageList" />
									<!-- iterate the images -->
										<logic:iterate id ="image" name="damageImageList" type="com.ibsplc.icargo.framework.model.UploadFileModel" indexId="index">	
											<tr>
												<td class="text-center" style="width:10%">
													<input type="checkbox" value="" name="check">						
												</td>
												<td>
												<!--bean:define id="imageIndx" name="form"/-->
												<% String dmgIndex = Integer.toString((int)index);%>
												<% String imgIndex = form.getImageIndex();%> 
												<%
												String source = new StringBuilder()
												.append("uld.defaults.ux.maintaindamagereport.displayimage.img?imageIndex=")
												.append(dmgIndex)
												.append("&dmgIndex=")
												.append(imgIndex)
												.toString();
												%>
												<common:resourceURL  src="<%=source%>" id="finalImageUrl"/>
												<bean:define id="imgUrl" name="finalImageUrl" />
												<%--<span class="badge md attach-count" onClick="colorBox()">
													<bean:write name="image" property="fileName"/></span> --%>
												<span href="<%=imgUrl%>" rel="test" class="badge md attach-file" onClick="colorBox()"><bean:write name="image" property="fileName"/>
												</span>
												</td>
												<td>
													<a onClick="removeImage(this,<%=index%>)"><i class="icon delete"></i></a>
												</td>
											</tr>
										</logic:iterate>
									</logic:present>
									<!--bean:write name="length"/-->
									
										<bean:define id="templateRowCount" value="0"/>
										<tr template="true" id="otherRow" style="display:none">
											<td class="text-center" style="width:10%">
													<input type="checkbox" value="<%=templateRowCount%>" name="check">
													<ihtml:hidden property="attachmentOperationFlag" value="NOOP"/>									
											</td>
										<td>
											<ihtml:file  property="uploadFormFile" id="theFile"  templateRow="true"  fileAsyncUpload="true" />								
											</td>
                                        <td class="text-center" style="width:10%">
                                           <!-- <a id="btDelete" href="#" value="delete">
                                                <i class="icon delete"></i>
                                            </a> -->
                                        </td>
										</tr>
									</tbody>
								</table>
						</div>
							<div class="text-right no-pad m-t-10">
								<span class=" btm-fixed">   
									<ihtml:nbutton id="btAdd" property="btAdd" styleClass="btn primary">Add</ihtml:nbutton>
								</span>
							</div>
						<div class="left full-width m-t-10">
                            <span class="btmbtnpane btm-fixed">
							<ihtml:nbutton id="btnCancel" property="btnCancel" styleClass="btn default" componentID="CMP_DocumentRepository_Defaults_btnCancel">
								Cancel
							</ihtml:nbutton>							
							<ihtml:nbutton id="btnOk" property="btnOk" styleClass="btn primary"  componentID="CMP_DocumentRepository_Defaults_btnOk">
									OK
							</ihtml:nbutton>
                            </span>
						</div>
				</div>
				</div>		
			</ihtml:form>
		</div>
		
	</body>
</html:html>
