import React, {  Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { Label } from "reactstrap";
import { ITextField, ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


  class AddConsignerButtonPanel extends React.PureComponent{
    constructor(props) {
        super(props);
       
    }

    okConsignerButton = () => {
        {this.props.EditConsignerDetails?this.props.okEditConsignerButton(this.props.editedDetailcons, this.props.rowIndex, this.props.ConsignerDetails)
        :
        this.props.okConsignerButton(this.props.newConsignerDetails, this.props.ConsignerDetails)}
    }

    render(){

        let consignorStatusCode = [];
        if (!isEmpty(this.props.oneTimeValues)) {

            consignorStatusCode = this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        return(
        <Fragment>
             <PopupBody>
             <div class="pad-t-md">
				<div class="pad-x-md">
					<div class="row">
					    <div class="col-6">
                            <div class="form-group">
                                <label className="form-control-label">Agent Type</label>
                                <ISelect name="agenttype" id ="agenttype" options={consignorStatusCode}/>
                             </div>
                        </div>

                        <div class="col-6">
                            <div class="form-group">
                            <label className="form-control-label">ISO Country Code</label>
                            <ITextField  name="isoCountryCode"  id="isoCountryCode" type="text" uppercase={true} maxlength="2"></ITextField>
                            </div>
                        </div> 

                        <div class="col-6">
                            <div class="form-group">
                            <label className="form-control-label">Agent ID</label>
                            <ITextField  name="agentId"  id="agentId" type="text" maxlength="15" uppercase={true}></ITextField>
                            </div>
                        </div>
                        
                        <div class="col-6">
                            <div class="form-group">
                            <Label className="form-control-label">Expiry</Label>
                            <ITextField mode="edit" name="expiryDate" type="text" maxlength="4" componentId="TXT_MAIL_OPERATIONS_CONSIGNMENT_SECURITY_EXPIRY_DATE"  class="form-control" uppercase={true} ></ITextField>
                            </div>
                        </div>
                    </div>
                </div>  
            </div>
              </PopupBody>
              
              <PopupFooter>
                    <IButton category="primary" onClick={this.okConsignerButton} >Add</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeButton}>Cancel</IButton>
                </PopupFooter>
        </Fragment>
        )
    }
}

export default icpopup(wrapForm('addConsignerButtonPanelForm')(AddConsignerButtonPanel), { title:'Add Agent / Consignor Details' })