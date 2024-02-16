import React, { Component } from "react";
import { Row, Col, FormGroup } from "reactstrap";
import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import { ISelect,ITextField,IMessage} from "icoreact/lib/ico/framework/html/elements";
import CustomerLovPopupContainer from "../containers/customerlovpopupcontainer";
import { Lov } from "icoreact/lib/ico/framework/component/common/lov";
class ConsigneeDetailsFilterPanel extends Component {
 
  render() {
   let showPOA=[{label:"General POA",value:"General POA"},{label:"Special POA", value:"Special POA"}]

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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.consigneecode"
                defaultMessage="Consignee Code"
              />
            </label>
            <div className="input-group">
              <ITextField
                componentId=""
                //uppercase={true}
                className="text-transform"
                name="consigneeCode"
                type="text"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.consigneename"
                defaultMessage="Consignee Name"
              />
            </label>
            <div className="input-group">
              <ITextField
                componentId=""
                //uppercase={true}
                className="text-transform"
                name="consigneeName"
                type="text"
              />
            </div>
          </FormGroup>
        </Col>
        <Col xs="8">
          <FormGroup>
            <label class="form-control-label">
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.scci"
                defaultMessage="SCC Code (Include)"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_SCCI"
                //uppercase={true}
                className="text-transform"
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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.scce"
                defaultMessage="SCC Code (Exclude)"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_SCCE"
                //uppercase={true}
                className="text-transform"
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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.filter.origini"
                defaultMessage="Origin (Include)"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_ORIGINI"
                //uppercase={true}
                className="text-transform"
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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.filter.origine"
                defaultMessage="Origin (Exclude)"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="Y"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_ORIGINE"
                //uppercase={true}
                className="text-transform"
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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.destination"
                defaultMessage="Destination"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="N"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_DESTINATION"
                //uppercase={true}
                className="text-transform"
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
              <IMessage
                msgkey="customermanagement.defaults.brokermapping.station"
                defaultMessage="Station"
              />
            </label>
            <div className="input-group">
              <Lov
                isMultiselect="N"
                componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_FILTER_STATION"
                //uppercase={true}
                className="text-transform"
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

export default wrapForm("consigneeDetailsFilterForm")(ConsigneeDetailsFilterPanel);
