import React, {  Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';

import { Label } from "reactstrap";
import { ITextField, ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip';

  class AddApplicableRegulationPanel extends React.PureComponent{
    constructor(props) {
        super(props);
       
		
    }

    okRegulationButton = () => {
        {this.props.EditRegulationDetails?this.props.okEditRegulationButton(this.props.editedDetailRegulation, this.props.rowIndex, this.props.applicableRegulation)
        :
        this.props.okRegulationButton(this.props.newApplicableRegulation, this.props.applicableRegulation)}
    }
    // toggleFilter(){
    //     this.props.onToggleFilter((this.props.screenMode === 'edit')?'display':'edit');
    // }

    onSelect = () => {
        this.props.onSelect();
    }
    onOKClick = () => {
        this.props.onOKClick();
    }
    render(){
       
        let applicableregulation = [];
        let Regulationapplicable = [];
        let applicableauthority =[];
        if (!isEmpty(this.props.oneTimeValues)) {
            applicableregulation = this.props.oneTimeValues['mail.operations.applicableregulationtransport'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        if (!isEmpty(this.props.oneTimeValues)) {
            Regulationapplicable = this.props.oneTimeValues['mail.operations.applicableRegulationFlag'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
          
        if (!isEmpty(this.props.oneTimeValues)) {
            applicableauthority = this.props.oneTimeValues['mail.operations.applicableRegulationBorderAgencyAuthority'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return(
        <Fragment>
             <PopupBody className="modal-width">
             <div class="pad-t-md">
				<div class="pad-x-md">
					<div class="row">
					    <div class="col-6">
                            <div class="form-group">
                                <label className="form-control-label">AR Transport Direction</label>
                                <ISelect name="applicableRegTransportDirection" id ="applicableRegTransportDirection" options={applicableregulation}/>
                             </div>
                        </div>

                        <div class="col-6">
                            <div class="form-group">
                            <label className="form-control-label">AR Border Agency Authority</label>
                            <ISelect name="applicableRegBorderAgencyAuthority" id ="applicableRegBorderAgencyAuthority" options={applicableauthority}/>
                            </div>
                        </div> 
                        <div class="col-6">
                            <div class="form-group">
                            <label className="form-control-label">AR Reference ID</label>
                            <ITextField  name="applicableRegReferenceID"  id="applicableRegReferenceID" type="text" uppercase={true} ></ITextField>
                            </div>
                        </div> 
                        <div class="col-6">
                        <div>
                        <label class="form-control-label d-flex align-items-center">
				            <div>AR Flag 
                        
                        {(this.props.showPopover) ?<i onClick={this.onOKClick} id={"resditimage_"} class="icon ico-ex-red mar-l-xs flag" data-target="webuiPopover47" ></i>
                                                  :  <i onClick={this.onSelect}  id={"resditimage_"}class="icon ico-ex-red mar-l-xs flag" data-target="webuiPopover47" ></i>}
</div></label>
                                        {this.props.showPopover && 
                                        
                                        <IPopover placement="bottom"  isOpen={this.props.showPopover} toggle={this.onOKClick} target={"resditimage_"} className="icpopover">
                                        <IPopoverBody>
                                           
                                                <table class="table m-0">
                                                    <thead>         
                                                        <tr>
                                                            <th>Flag No:</th>
                                                            <th>Flag Description</th>
                                                        </tr>                           
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td> 0</td>
                                                            <td> Advance cargo information but not required</td>
                                                        </tr>
                                                        <tr>
                                                            <td> 1</td>
                                                            <td> Required advance cargo information has been provided; no response received</td>
                                                        </tr>
                                                        <tr>
                                                            <td> 2</td>
                                                            <td> Required advance cargo information has been provided; positive response received</td>
                                                        </tr>
                                                        <tr>
                                                            <td> 3</td>
                                                            <td> Advance cargo information has been provided; treat as high risk cargo</td>
                                                        </tr>
                                                        <tr>
                                                            <td> X</td>
                                                            <td> All mail in consignment exempt from advance cargo information</td>
                                                        </tr>
                                                        <tr>
                                                            <td> N</td>
                                                            <td> Advance cargo info not required</td>
                                                        </tr>
                                                    </tbody>
                                                </table>                       
                                            
                                        </IPopoverBody>
                                    </IPopover>
                                        
                                        }
                     
                                        
			            </div>
                        <ISelect name="applicableRegFlag" id ="applicableRegFlag" options={Regulationapplicable}/>
                        </div>
                        
                        
                    </div>
                </div>  
            </div>
            
              </PopupBody>
              
              <PopupFooter>
                    <IButton category="primary" onClick={this.okRegulationButton} >Add</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeButton}>Cancel</IButton>
                </PopupFooter>
        </Fragment>
        )
    }
}

export default icpopup(wrapForm('addApplicableRegulationPanelform')(AddApplicableRegulationPanel), { title:'Applicable Regulation Information' })