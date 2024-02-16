import React, { Component } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import ScreeningDetailsTable from './ScreeningDetailsTable.jsx';
import ConsignorDetailsTable from './ConsignorDetailsTable.jsx';
import ScreeningDetailsPopup from './ScreeningDetailsPopup.jsx';
import ConsignorDetailsPopup from './ConsignorDetailsPopup.jsx';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'


class DetailsPanel extends Component {


    constructor(props) {
        super(props);
        this.addScreenigDetails = this.addScreenigDetails.bind(this);
        this.addConsignorDetails = this.addConsignorDetails.bind(this);

    }

    addScreenigDetails(event) {
        let actionName = event.target.dataset.mode
        this.props.addScreenigDetails({ actionName: actionName });
    }

    addConsignorDetails(event) {
        let actionName = event.target.dataset.mode
        this.props.addConsignorDetails({ actionName: actionName });
    }
    toggleAddContainerPopup = () => {
        this.props.closeAddContainerPopup();
    }




    render() {


        return (


            (this.props.screenMode === 'initial') ?
                <div >
                </div> :


                <>
                    <div class="row flex-grow-1">
                        <div class="col-12 d-flex">
                            <div class="card p-0 flex-grow-1">
                                <div class="card-header card-header-action">
                                    <div class="col">
                                        <div class="d-flex justify-content-between align-items-center pr-2">
                                            <h4>Screening Details</h4>
                                            <IButton data-mode="ADD_SCREENING_DETAILS" className="btn btn-default" onClick={this.addScreenigDetails}>Add New</IButton>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0 d-flex">
                                    <ScreeningDetailsPopup show={this.props.screeningDetailPopupShow} toggleFn={this.toggleAddContainerPopup} initialValues={this.props.initialValues} oneTimeValues={this.props.oneTimeValues} onsaveNewScreeningDetails={this.props.onsaveNewScreeningDetails} />

                                    <ScreeningDetailsTable mailbagSecurityDetails={this.props.screeningDetails} editScreeningDetails={this.props.editScreeningDetails} deleteScreeningDetails={this.props.deleteScreeningDetails} />
                                </div>
                            </div>
                        </div>

                        <div class="col-12 d-flex">
                            <div class="card p-0 flex-grow-1">
                                <div class="card-header card-header-action">
                                    <div class="col">
                                        <div class="d-flex justify-content-between align-items-center pr-2">
                                            <h4>Agent/ Consignor Details</h4>
                                            <IButton data-mode="ADD_CONSIGNOR_DETAILS" className="btn btn-default" onClick={this.addConsignorDetails} >Add New</IButton>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0 d-flex">
                                    <ConsignorDetailsPopup show={this.props.consignorDetailsPopupShow} toggleFn={this.toggleAddContainerPopup} initialValues={this.props.initialValues} oneTimeValues={this.props.oneTimeValues} onsaveNewConsignorDetails={this.props.onsaveNewConsignorDetails}  />

                                    <ConsignorDetailsTable mailbagSecurityDetails={this.props.consignerDetails} editConsignorDetails={this.props.editConsignorDetails} deleteConsignorDetails={this.props.deleteConsignorDetails} oneTimeValues={this.props.oneTimeValues} onsaveNewConsignorDetails={this.props.onsaveNewConsignorDetails} />
                                </div>
                            </div>
                        </div>
                    </div>
                </>
        );

    }
}

export default wrapForm('mailbagdetails')(DetailsPanel)
















