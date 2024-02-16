import React, { Component } from "react";
import { Row, Col, FormGroup } from "reactstrap";
import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import {
  ISelect,
  ITextField,
  IMessage,
} from "icoreact/lib/ico/framework/html/elements";
import { Lov } from "icoreact/lib/ico/framework/component/common/lov";

class BrokerDetailsFilterPanel extends Component {
  render() {
    let showPOA = [
      { label: "General POA", value: "General POA" },
      { label: "Special POA", value: "Special POA" },
    ];

    return (
      <Row>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.poaType"
                defaultMessage="POA Type"
              />
            </label>
            <ISelect
              name="poaType"
              options={showPOA}
              showTextField={true}
              multi={false}
            />
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.brokercode" defaultMessage="Broker Code" />
            </label>
            <div className="input-group">
              <ITextField
                className="text-transform"
                componentId=""
                //uppercase={true}
                name="brokerCode"
                type="text"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.brokername" defaultMessage="Broker Name" />
            </label>
            <div className="input-group">
              <ITextField
                className="text-transform"
                componentId=""
                //uppercase={true}
                name="brokerName"
                type="text"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.scci" defaultMessage="SCC Code (Include)" />
            </label>
            <div className="input-group">
              <Lov
                className="text-transform"
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_SCCI"
                //uppercase={true}
                name="sccCodeI"
                lovTitle="SCC Code (Include)"
                maxlength="40"
                dialogWidth="600"
                dialogHeight="540"
                lovContainerHeight="540"
                closeButtonIds={["CMP_Shared_Scc_SccLov_Close", "btnOk"]}
                actionUrl="ux.showScc.do?formNumber=0&maxlength=10"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.scce" defaultMessage="SCC Code (Exclude)" />
            </label>
            <div className="input-group">
              <Lov
                className="text-transform"
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_SCCE"
                //uppercase={true}
                name="sccCodeE"
                lovTitle="SCC Code (Exclude)"
                maxlength="40"
                dialogWidth="600"
                dialogHeight="500"
                closeButtonIds={["CMP_Shared_Scc_SccLov_Close", "btnOk"]}
                actionUrl="ux.showScc.do?formNumber=0&maxlength=10"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.filter.origini" defaultMessage="Origin (Include)" />
            </label>
            <div className="input-group">
              <Lov
                className="text-transform"
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_ORIGINI"
                //uppercase={true}
                name="orginInclude"
                lovTitle="Orgin"
                maxlength="15"
                dialogWidth="600"
                dialogHeight="540"
                lovContainerHeight="540"
                actionUrl="ux.showAirport.do"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.filter.origine" defaultMessage="Origin (Exclude)" />
            </label>
            <div className="input-group">
              <Lov
                className="text-transform"
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_ORIGINE"
                //uppercase={true}
                name="orginExclude"
                lovTitle="Orgin"
                maxlength="15"
                dialogWidth="600"
                dialogHeight="540"
                lovContainerHeight="540"
                actionUrl="ux.showAirport.do"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.destination" defaultMessage="Destination" />
            </label>
            <div className="input-group">
              <Lov
                className="text-transform"
                isMultiselect="N"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_DESTINATION"
                //uppercase={true}
                name="destination"
                lovTitle="Destination"
                maxlength="3"
                dialogWidth="600"
                dialogHeight="540"
                lovContainerHeight="540"
                actionUrl="ux.showAirport.do"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage msgkey="customermanagement.defaults.brokermapping.station" defaultMessage="Station" />
            </label>
            <div className="input-group">
              <Lov
               className="text-transform"
                isMultiselect="N"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_BROKER_FILTER_STATION"
                //uppercase={true}
                name="station"
                lovTitle="Station"
                maxlength="3"
                dialogWidth="600"
                dialogHeight="540"
                lovContainerHeight="540"
                actionUrl="ux.showAirport.do"
              />
            </div>
          </FormGroup>
        </Col>
      </Row>
    );
  }
}

export default wrapForm("brokerDetailsFilterForm")(BrokerDetailsFilterPanel);
