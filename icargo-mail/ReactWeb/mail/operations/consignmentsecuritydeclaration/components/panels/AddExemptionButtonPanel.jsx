import React, {  Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { Label } from "reactstrap";
import { ITextField, ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


  class AddExemptionButtonPanel extends React.PureComponent{
    constructor(props) {
        super(props);
       
    }

    okExemptionButton = () => {
        {this.props.EditsecurityExemption?this.props.okEditsecurityExemption(this.props.editedDetailexemption, this.props.rowIndex, this.props.securityExemption)
        :
        this.props.okExemptionButton(this.props.newSecurityExemption, this.props.securityExemption)}
    }

    render(){

        return(
        <Fragment>
             <PopupBody>
             <div class="pad-t-md">
				<div class="pad-x-md">
					<div class="row">
					    <div class="col-8">
                            <div class="form-group">
                                <label className="form-control-label">Reason for Exemption</label>
                                <ITextField  name="seScreeningReasonCode"  id="seScreeningReasonCode" type="text" uppercase={true}></ITextField>
                             </div>
                        </div>
                        <div class="col-8">
                            <div class="form-group">
                            <label className="form-control-label">SE Applicable Authority</label>
                            <ITextField  name="seScreeningAuthority"  id="seScreeningAuthority" type="text" uppercase={true}></ITextField>
                            </div>
                        </div>
                        <div class="col-8">
                            <div class="form-group">
                            <label className="form-control-label">SE Applicable Regulation</label>
                            <ITextField  name="seScreeningRegulation"  id="seScreeningRegulation" type="text" uppercase={true} ></ITextField>
                            </div>
                        </div>
                      
                    </div>
                </div>  
            </div>
              </PopupBody>
              
              <PopupFooter>
                    <IButton category="primary" onClick={this.okExemptionButton} >Add</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeButton}>Cancel</IButton>
                </PopupFooter>
        </Fragment>
        )
    }
}

export default icpopup(wrapForm('addExemptionButtonPanelForm')(AddExemptionButtonPanel), { title:'Security Exemption' })