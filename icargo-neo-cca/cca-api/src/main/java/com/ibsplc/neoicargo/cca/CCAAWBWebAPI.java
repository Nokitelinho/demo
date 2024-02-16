package com.ibsplc.neoicargo.cca;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.AttachmentsInfo;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.modal.BulkActionData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintFilterModel;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesPage;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataResponse;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.modal.CcaListViewPageInfo;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersPage;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.NetValuesData;
import com.ibsplc.neoicargo.cca.modal.RelatedCCAData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/cca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PrivateAPI
public interface CCAAWBWebAPI {

    @Path("/saveCCA")
    @POST
    CcaValidationData saveCCA(CCAMasterData ccaMasterData);

    @Path("/getccadetails")
    @POST
    CCAMasterData getCCADetails(CcaDataFilter ccaDataFilter);

    @Path("/getrelatedcca/{shipment_prefix}/{awb}")
    @GET
    List<RelatedCCAData> getRelatedCCA(@PathParam("shipment_prefix") String shipmentPrefix,
                                       @PathParam("awb") String masterDocumentNumber);

    @Path("/getCCAList")
    @POST
    List<CCAMasterData> getCCAList(CcaDataFilter ccaDataFilter);

    @Path("/deletecca")
    @POST
    BulkActionData deleteCCA(CcaDataFilterList ccaDataFilterList);

    @Path("/authorize")
    @POST
    BulkActionData authorizeCCA(CcaDataFilterList ccaDataFilterList);

    @Path("/reratecca/{rating_parameter}")
    @POST
    CCAMasterData reRateCCA(@PathParam("rating_parameter") String ratingParameter, CCAMasterData ccaMasterData);

    @Path("/generateccaprint")
    @POST
    CCAPrintModel generateCCAPrint(CCAPrintFilterModel reportFilterModel);

    @Path("/reasoncodes/available")
    @GET
    List<AvailableReasonCodeData> getAvailableReasonCodes();

    @Path("/getCCAListView")
    @POST
    CcaListViewPageInfo getCCAListView(CCAListViewFilterData ccaListViewFilterData);

    @Path("/attachment")
    @PUT
    AttachmentsInfo updateCcaMasterAttachments(AttachmentsData attachmentsData);

    @Path("/getCCANumbers")
    @POST
    CcaNumbersPage getCCANumbers(CcaSelectFilter ccaSelectFilter);

    @Path("/getccaassignees")
    @POST
    CcaAssigneesPage getCcaAssignees(CcaSelectFilter ccaSelectFilter);

    @Path("/ccaassignee")
    @PUT
    CcaValidationData updateCcaAssignee(CcaAssigneeData ccaAssigneeData) throws CcaBusinessException;

    @Path("/calculatetaxes")
    @POST
    CCAMasterData reCalculateCCATaxes(CCAMasterData ccaMasterData);

    @Path("/validateCass")
    @POST
    CcaCassValidationDataResponse validateCassIndicator(CcaCassValidationDataRequest ccaCassValidationDataRequest);

    @Path("/getnetvalues")
    @POST
    NetValuesData getNetValues(CCAMasterData ccaMasterData);

    @Path("/getCcaReferenceNumber")
    @POST
    CCAMasterData getCcaReferenceNumber(CCAMasterData ccaMasterData);
}
