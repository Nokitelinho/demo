import React,{Component} from "react";

import { Lov } from "icoreact/lib/ico/framework/component/common/lov";
import { IMessage,IRadio,ITextField,IButton,IFile} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { FormGroup, Label } from "reactstrap";
import icpopup, { 
     PopupFooter, PopupBody 
} from 'icoreact/lib/ico/framework/component/common/modal';
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util";

import CustomerLovPopupContainer from "../../containers/customerlovpopupcontainer";
import { validateFile } from "../../../actions/commonaction";

class NewConsigneeDetailsPopUpPanel extends Component{
    constructor(props) {
        super(props);
		this.state={
			cutomerCode:""
		}
    }
	handleChange=(e)=>{
		this.setState({ cutomerCode: e.target.value });
	};
   /**
   * validating the customer code using onBlur event
   */
	handleBlur=()=>{
		if(!isEmpty(this.state.cutomerCode)){
			this.props.validateCustomer({customerCode:this.state.cutomerCode,flag:"C"});
      this.setState({cutomerCode:""})
		}else {
		  this.props.updateOnEmptyConsignee();
		}
	};
  onConsigneeCodeFocus=()=>{
    this.setState({cutomerCode:this.props.consigneeCodeValue})
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
                        <IMessage msgkey="customermanagement.defaults.brokermapping.consigneecode" defaultMessage="Consignee Code" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div className="input-group">
                        <ITextField
                          componentId=" "
                          //uppercase={true}
                          className="text-transform"
                          name="consigneeCode"
                          type="text"
                          onChange={this.handleChange}
                          onBlur={this.handleBlur}
                          onFocus={this.onConsigneeCodeFocus}
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
                            consigneeCode="Y"
                          />
                        </div>
                      </div>
                    </FormGroup>
                  </div>
                  <div className="col-12">
                    <FormGroup className="form-group">
                      <Label className="form-control-label">
                        <IMessage msgkey="customermanagement.defaults.brokermapping.consigneename" defaultMessage="Consignee Name" />
                        <span className="mandatory text-red"> *</span>
                      </Label>
                      <div className="buttondisabled">
                        <ITextField
                          componentId=""
                          //uppercase={true}
                          className="text-transform"
                          name="consigneeName"
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
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_SCCI"
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
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_SCCE"
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
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_ORIGIN"
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
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CONSIGNEE_DESTINATION"
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
                          //uppercase={true}
                          className="text-transform"
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
                          this.props.consigneeFileEnable
                            ? "buttondisabled"
                            : "disableFile"
                        }
                      >
                        <IFile
                          name="document"
                          fileIndex={this.props.consigneeFileIndex}
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
const newConsigneeDetailsPopUpPanel = wrapForm('addconsigneedetailsform')(NewConsigneeDetailsPopUpPanel);
export default icpopup((newConsigneeDetailsPopUpPanel), { title: 'Add POA'});