package com.ibsplc.neoicargo.tracking;

import com.ibsplc.neoicargo.awb.model.AwbUserNotificationModel;
import com.ibsplc.neoicargo.awb.model.ShipperConsigneeDetailsModel;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;
import com.ibsplc.neoicargo.tracking.model.MilestoneMasterModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentActivityModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentDetailsModel;
import com.ibsplc.neoicargo.tracking.model.SplitModel;
import com.ibsplc.neoicargo.tracking.model.TrackingValidationModel;

import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Tracking API
 *
 * <p>API for Tracking
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PrivateAPI
public interface TrackingAPI {
    /**
     * Get shipment details by AWBs.
     */
    @GET
    @Path("/v1/shipments")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    List<ShipmentDetailsModel> getShipmentDetails(@QueryParam("awb") List<String> awbs);

    /**
     * Get shipment splits by AWB.
     */
    @GET
    @Path("/v1/shipments/splits/{awb}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    List<SplitModel> getShipmentSplits(@PathParam("awb") @Pattern(message = "Invalid AWB Format", regexp = TrackingValidationModel.AWB_FORMAT) String awb);

    /**
     * Get shipper and consignee details by AWB.
     */
    @GET
    @Path("/v1/shipments/shipperconsigneedetails/{awb}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    ShipperConsigneeDetailsModel getShipperConsigneeDetails(@PathParam("awb") @Pattern(message = "Invalid AWB Format", regexp = TrackingValidationModel.AWB_FORMAT) String awb);

    /**
     * Get activity data by AWB.
     */
    @GET
    @Path("/v1/shipments/activities/{awb}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    List<ShipmentActivityModel> getShipmentActivities(@PathParam("awb") @Pattern(message = "Invalid AWB Format", regexp = TrackingValidationModel.AWB_FORMAT) String awb);

    /**
     * Get user milestone notifications by AWB.
     */
    @GET
    @Path("/v1/shipments/notifications/{awb}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    AwbUserNotificationModel getUserAwbNotifications(@PathParam("awb") @Pattern(message = "Invalid AWB Format", regexp = TrackingValidationModel.AWB_FORMAT) String awb);

    /**
     * Save user milestone notifications for AWB.
     */
    @POST
    @Path("/v1/shipments/notifications/{awb}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    AwbUserNotificationModel saveUserAwbNotifications(@PathParam("awb") @Pattern(message = "Invalid AWB Format", regexp = TrackingValidationModel.AWB_FORMAT) String awb, AwbUserNotificationModel model);


    /**
     * Delete user milestone notifications for AWB.
     */
    @DELETE
    @Path("/v1/shipments/notifications/{trackingAwbSerialNumber}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    void deleteUserAwbNotifications(@PathParam("trackingAwbSerialNumber") Long trackingAwbSerialNumber);

    @GET
    @Path("/v1/tracking/milestonemaster")
    @Consumes({"application/json"})
    @Produces({"application/json"})
	List<MilestoneMasterModel> findAllMilestones();
}
