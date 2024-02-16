import React, { Component } from "react";
import { FormGroup, Label } from "reactstrap";

import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import { Lov } from "icoreact/lib/ico/framework/component/common/lov";

import {
  IButton,
  IMessage,
  ITextField,
} from "icoreact/lib/ico/framework/html/elements";

import { getFirstSixDetails } from "../../../utils/utils.js";
import AdditionalDetailsPopUpContainer from "../../containers/popup/additionaldetailspopupcontainer.js";
import AdditionalNamesPoaPopUpContainer from "../../containers/popup/additionalnamepopupcontainer.js";
class CustomerDetailsPanel extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    let flag = this.props.disableCustomerFields;
    return (
      <div className="pad-md">
        <div className="row">
          <div className="col-16">
            <div className="row">
              <div className="col-10">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.custName"
                      defaultMessage="Customer Name"
                    />
                    <span className="mandatory text-red"> *</span>
                  </Label>
                  <ITextField
                    className="form-control text-transform"
                    componentId=""
                    // uppercase={true}
                    name="customerName"
                    type="text"
                    readOnly={flag}
                    onBlur={() => this.props.removeSpace("CU")}
                  />
                </FormGroup>
              </div>
              <div className="col-14">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.adlName"
                      defaultMessage="Additional Name"
                    />
                  </Label>
                  <div className="input-group">
                    <ITextField
                      className="disable text-transform"
                      componentId=""
                      //uppercase={true}
                      name="adittionalName"
                      type="text"
                      readOnly={flag}
                      tabindex="-1"
                    />
                    <div className="input-group-append">
                      <IButton
                        className="btn btn-icon additional-name"
                        accesskey=""
                        bType=""
                        componentId=""
                        onClick={() => this.props.onShowAdditionalName()}
                      >
                        <i className="icon ico-maximize"></i>
                        <AdditionalNamesPoaPopUpContainer
                          show={this.props.showAddNamPopUp}
                        />
                      </IButton>
                    </div>
                  </div>
                </FormGroup>
              </div>
              <div className="col-5">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.street"
                      defaultMessage="Street"
                    />
                  </Label>
                  <ITextField
                    className="form-control text-transform"
                    componentId=""
                    // uppercase={true}
                    name="street"
                    type="text"
                    readOnly={flag}
                    onBlur={() => this.props.removeSpace("S")}
                  />
                </FormGroup>
              </div>
              <div className="col-5">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.city"
                      defaultMessage="City"
                    />
                    <span className="mandatory text-red"> *</span>
                  </Label>
                  <ITextField
                    className="form-control text-transform"
                    componentId=""
                    // uppercase={true}
                    name="city"
                    type="text"
                    readOnly={flag}
                    onBlur={() => this.props.removeSpace("C")}
                  />
                </FormGroup>
              </div>
              <div className="col-5">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.country"
                      defaultMessage="Country"
                    />
                    <span className="mandatory text-red"> *</span>
                  </Label>
                  <div
                    className={
                      this.props.disableCustomerFields ? "disableLov" : ""
                    }
                  >
                    <div className="input-group-append">
                      <Lov
                        className="text-transform"
                        isMultiselect="N"
                        componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_COUNTRY"
                        // uppercase={true}
                        name="country"
                        lovTitle="Country"
                        maxlength="3"
                        dialogWidth="600"
                        dialogHeight="540"
                        lovContainerHeight="540"
                        actionUrl="ux.showCountry.do?formCount=1"
                        readOnly={flag}
                      />
                    </div>
                  </div>
                </FormGroup>
              </div>
              <div className="col-4">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.zip"
                      defaultMessage="Zip Code"
                    />
                    <span className="mandatory text-red"> *</span>
                  </Label>
                  <ITextField
                    className="form-control text-transform"
                    componentId=""
                    // uppercase={true}
                    name="zipCode"
                    type="text"
                    maxlength="10"
                    readOnly={flag}
                    onBlur={() => this.props.removeSpace("Z")}
                  />
                </FormGroup>
              </div>
              <div className="col-5">
                <FormGroup className="form-group">
                  <Label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.station"
                      defaultMessage="Station"
                    />
                    <span className="mandatory text-red"> *</span>
                  </Label>
                  <div
                    className={
                      this.props.disableCustomerFields ? "disableLov" : ""
                    }
                  >
                    <div className="input-group-append">
                      <Lov
                        className="text-transform"
                        isMultiselect="N"
                        componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_STATION"
                        // uppercase={true}
                        name="station"
                        lovTitle="Station"
                        maxlength="3"
                        dialogWidth="600"
                        dialogHeight="540"
                        lovContainerHeight="540"
                        actionUrl="ux.showAirport.do"
                        readOnly={flag}
                      />
                    </div>
                  </div>
                </FormGroup>
              </div>
            </div>
          </div>
          <div className="col-8">
            <FormGroup className="form-group">
              <Label className="form-control-label">
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.adlDetails"
                  defaultMessage="Additional Details"
                />
              </Label>
              <div className="position-relative additional-area" style={{overflow:"hidden"}}>
                <div className="wrap-view">
                  <div className="p-2 pr-5">
                    <span style={{ whiteSpace: "pre-line" ,overflow:"hidden"}}>
                      {getFirstSixDetails(
                        this.props.customerDetails.additionalDetails
                      )}
                    </span>
                  </div>
                </div>
                <div className="input-group-append">
                  <IButton
                    className="btn btn-icon additional-btn"
                    accesskey=""
                    bType=""
                    componentId=""
                    onClick={() => this.props.onShowAdditionalPopUp()}
                  >
                    <i className="icon ico-maximize"></i>
                    <AdditionalDetailsPopUpContainer
                      show={this.props.showAdditionalPopUp}
                    />
                  </IButton>
                </div>
              </div>
            </FormGroup>
          </div>
        </div>
      </div>
    );
  }
}
export default wrapForm("customerDetailsForm")(CustomerDetailsPanel);
