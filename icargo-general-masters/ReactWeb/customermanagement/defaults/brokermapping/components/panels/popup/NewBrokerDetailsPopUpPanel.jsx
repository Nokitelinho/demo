import React,{Component} from "react";
import { FormGroup, Label } from "reactstrap";

import { Lov } from "icoreact/lib/ico/framework/component/common/lov";
import { IMessage,IRadio,ITextField,IButton,IFile} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from "icoreact/lib/ico/framework/component/util";
import icpopup, { 
    PopupFooter, PopupBody 
} from 'icoreact/lib/ico/framework/component/common/modal';

import CustomerLovPopupContainer from "../../containers/customerlovpopupcontainer";
import { validateFile } from "../../../actions/commonaction";
/**
 * Component gets called when creating a new broker poa
 */
class NewBrokerDetailsPopUpPanel extends Component{
    constructor(props) {
        super(props);
		this.state={
			cutomerCode:""
		}
    }
	handleChange=(e)=>{
		this.setState({ cutomerCode: e.target.value});
	};
  /**
   * validating the customer code using onBlur event
   */
	handleBlur=()=>{
		if(!isEmpty(this.state.cutomerCode)){
			this.props.validateCustomer({customerCode:this.state.cutomerCode,flag:"B"});
      this.setState({cutomerCode:""})
      } else {
        this.props.updateOnEmptyBroker();
      }
	};
  
  onBrokerCodeFocus=()=>{
    this.setState({cutomerCode:this.props.brokerCodeValue})
  };
  handleFileValidation = (fileObj) => {
		return validateFile(fileObj);
	};
  render(){
		let options=[{"label": "Include","value": "I"},{"label": "Exclude","value": "E"}]
        return (
          <>
            <PopupBody>
              <div className="p-3 d-flex flex-column">
                <div className="row mb-2">
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.brokercode" defaultMessage="Broker Code" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div className="input-group">
                        <ITextField
                          componentId=""
                          //uppercase={true}
                          className="text-transform"
                          name="brokerCode"
                          type="text"
                          onChange={this.handleChange}
                          onBlur={this.handleBlur}
                          onFocus={this.onBrokerCodeFocus}
                        />
                        <div className="input-group-append">
                          <IButton
                            className="btn btn-icon "
                            category="default"
                            bType=""
                            componentId=""
                            onClick={() => this.props.showCustomerPopup()}
                            tabindex="-1"
                          >
                            <i className="icon ico-expand"></i>
                          </IButton>
                          <CustomerLovPopupContainer
                            show={this.props.showCustomerLovPopupFlag}
                            brokerCode="Y"
                          />
                        </div>
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.brokername" defaultMessage="Broker Name" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div className="buttondisabled">
                        <ITextField
                          componentId=""
                          //uppercase={true}
                          className="text-transform"
                          name="brokerName"
                          type="text"
                          readOnly={true}
                          tabindex="-1"
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage
                          msgkey="customermanagement.defaults.brokermapping.scci"
                          defaultMessage="SCC Code (Include)"
                        />
                      </Label>
                      <div className="input-group">
                        <Lov
                          isMultiselect="Y"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_SCCI"
                          //uppercase={true}
                          className="text-transform"
                          name="sccCodeI"
                          lovTitle="SCC Code (Include)"
                          maxlength="40"
                          dialogWidth="600"
                          dialogHeight="540"
                          lovContainerHeight="540"
                          closeButtonIds={[
                            "CMP_Shared_Scc_SccLov_Close",
                            "btnOk",
                          ]}
                          actionUrl="ux.showScc.do?formNumber=0&maxlength=10"
                          // disabled={true}
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage
                          msgkey="customermanagement.defaults.brokermapping.scce"
                          defaultMessage="SCC Code (Exclude)"
                        />
                      </Label>
                      <div className="input-group">
                        <Lov
                          isMultiselect="Y"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_SCCE"
                          //uppercase={true}
                          className="text-transform"
                          name="sccCodeE"
                          lovTitle="SCC Code (Exclude)"
                          maxlength="40"
                          dialogWidth="600"
                          dialogHeight="500"
                          closeButtonIds={[
                            "CMP_Shared_Scc_SccLov_Close",
                            "btnOk",
                          ]}
                          actionUrl="ux.showScc.do?formNumber=0&maxlength=10"
                          // disabled={true}
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.origin" defaultMessage="Origin" />
                      </Label>
                      <div className="input-group">
                        <Lov
                          isMultiselect="Y"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_ORIGIN"
                          //uppercase={true}
                          className="text-transform"
                          name="orgin"
                          lovTitle="Origin"
                          maxlength="40"
                          dialogWidth="600"
                          dialogHeight="540"
                          lovContainerHeight="540"
                          actionUrl="ux.showAirport.do"
                          // disabled={true}
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <div className="d-flex pt-3">
                      <div className="form-check form-check-inline">
                        <FormGroup>
                          <IRadio name="orginRadio" options={options} />
                        </FormGroup>
                      </div>
                    </div>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.destination" defaultMessage="Destination" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div className="input-group">
                        <Lov
                          isMultiselect="N"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_DESTINATION"
                          //uppercase={true}
                          className="text-transform"
                          name="destination"
                          lovTitle="Destination"
                          maxlength="3"
                          dialogWidth="600"
                          dialogHeight="540"
                          lovContainerHeight="540"
                          actionUrl="ux.showAirport.do"
                          // disabled={true}
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.station" defaultMessage="Station" />
                      </Label>
                      <div className="buttondisabled">
                        <Lov
                          isMultiselect="N"
                          componentId=""
                          uppercase={true}
                          name="station"
                          lovTitle="Station"
                          maxlength="15"
                          dialogWidth="600"
                          dialogHeight="540"
                          lovContainerHeight="540"
                          // actionUrl="ux.showAirport.do"
                          disabled={true}
                        />
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-8">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.poadoc" defaultMessage="POA Document" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div
                        className={
                          this.props.brokerFileEnable
                            ? "buttondisabled"
                            : "disableFile"
                        }
                      >
                        <IFile
                          name="document"
                          fileIndex={this.props.brokerFileIndex}
                          fileNameMaxLength={50}
                          children="Browse Document"
                          customValidation={this.handleFileValidation}
                        />
                      </div>
                    </FormGroup>
                  </div>
                </div>
              </div>
            </PopupBody>
            <PopupFooter>
              <IButton category="primary" onClick={() => this.props.onOk()}>
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.ok"
                  defaultMessage="OK"
                />
              </IButton>
              <IButton category="default" onClick={() => this.props.onClose()}>
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.close"
                  defaultMessage="Close"
                />
              </IButton>
            </PopupFooter>
          </>
        );
    }
}
const newBrokerDetailsPopUpPanel = wrapForm('adddetailsform')(NewBrokerDetailsPopUpPanel);
export default icpopup((newBrokerDetailsPopUpPanel), { title: 'Add POA'});