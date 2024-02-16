import React, { Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import PropTypes from 'prop-types';
import { Row,  } from "reactstrap";
import { Key,ComponentId } from '../../constants/constants.js';
import {IMessage,ITextField,ITextArea } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';


class AddPaymentPopUp extends React.PureComponent {
    constructor(props) {
        super(props);
    }

    createPaymentPopup = () =>{
        this.props.createPaymentPopup();
    }
    closeAddPaymentPopup = () => {
        this.props.closeAddPaymentPopup();
    }

    okPaymentPopup = () => {
        this.props.okPaymentPopup();
    }
    render() {
        const fromEdit = this.props.fromEditBatch;
        return (
            <Fragment>
                <PopupBody>
                {(this.props.fromEditBatch === 'N' ) ?
                    <div className="pad-x-md pt-3">
                        <Row>
                           <div className="col-8">
                             <div className="form-group">
                               <label className="form-control-label " ><IMessage msgkey={Key.PACOD_LBL}  /></label>
                                <Lov name="poaCod" lovTitle="GPA Code" uppercase={true} maxlength="5" dialogWidth="600"  dialogHeight="453" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId={ComponentId.CMP_ADD_PA} />
                              </div>                                
                            </div>
                            <div className="col-8">
                              <div className="form-group">
                                <label className="form-control-label "><IMessage msgkey={Key.BATCH_ID_LBL}  /></label>
                                <ITextField name="batchID" type="text" uppercase={true} maxlength="16" componentId={ComponentId.CMP_ADD_BTHID}></ITextField>
                             </div>                                
                            </div>     
                            <div className="col-8">
                             <div className="form-group">
                                <label className="form-control-label "><IMessage msgkey={Key.BATCH_AMT_LBL}  /></label>
                                <ITextField name="batchAmt" type="text" componentId={ComponentId.CMP_ADD_BTHAMT}></ITextField>
                             </div>                                    
                            </div>                                                    
                         <div className="col-8">
                         <div className="form-group">
                               <label className="form-control-label " ><IMessage msgkey={Key.BATCH_CUR_LBL}  /></label>
                                <Lov name="batchCur" lovTitle="Currency" uppercase={true} maxlength="5" dialogWidth="600"  dialogHeight="453" actionUrl="ux.showCurrency.do?formCount=1" componentId={ComponentId.CMP_ADD_BTHCUR} />
                        </div>                                   
                        </div> 
                        <div className="col-8">
                        <div className="form-group">
                            <label className="form-control-label"><IMessage msgkey={Key.BATCH_DAT_LBL}  /></label>
                            <DatePicker name="batchDate"  componentId={ComponentId.CMP_ADD_BTHCUR} />
                        </div> 
                        </div>                                                      
                        </Row>
                        <Row>
                        <div className="col-24">
                        <div className="form-group">
                            <label className="form-control-label"><IMessage msgkey={Key.BATCH_RMK_LBL}  /></label>
                            <ITextArea className="textarea" maxlength="300" name="remarks" id="groupRemarks" cols="10" rows="4" ></ITextArea>
                        </div>
                        </div>  
                        </Row>
                    </div>
                : <div className="pad-x-md pt-3"> 
                    <Row>
                    <div className="col-8">
                        <div className="form-group">
                            <label className="form-control-label " ><IMessage msgkey={Key.PACOD_LBL}  /></label>
                            <div className="form-control-data">{this.props.paymentbatchdetail.paCode}</div>
                        </div>                                
                    </div> 
                    <div className="col-8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey={Key.BATCH_ID_LBL}  /></label>
                            <div className="form-control-data">{this.props.paymentbatchdetail.batchID}</div>
                        </div>  
                    </div> 
                    <div className="col-8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey={Key.BATCH_AMT_LBL}  /></label>
                            <ITextField name="batchAmt" type="text" componentId={ComponentId.CMP_ADD_BTHAMT}></ITextField>
                        </div>                                    
                    </div>                                                      
                    <div className="col-8">
                         <div className="form-group">
                            <label className="form-control-label " ><IMessage msgkey={Key.BATCH_CUR_LBL}  /></label>
                            <Lov name="batchCur" lovTitle="Currency" uppercase={true} maxlength="5" dialogWidth="600"  dialogHeight="453" actionUrl="ux.showCurrency.do?formCount=1" componentId={ComponentId.CMP_ADD_BTHCUR} />
                        </div>                                   
                    </div>
                    <div className="col-8">
                        <div className="form-group">
                            <label className="form-control-label"><IMessage msgkey={Key.BATCH_DAT_LBL}  /></label>
                            <div className="form-control-data">{this.props.paymentbatchdetail.date}</div>
                        </div> 
                    </div>                                                                                   
                    </Row>
                    <Row>
                    <div className="col-24">
                        <div className="form-group">
                            <label className="form-control-label"><IMessage msgkey={Key.BATCH_RMK_LBL}  /></label>
                            <ITextArea className="textarea" maxlength="300" name="remarks" id="groupRemarks" cols="10" rows="4" ></ITextArea>
                        </div>
                    </div>                     
                    </Row>
                </div>
                }
                </PopupBody>
                <PopupFooter>
                {(this.props.fromEditBatch === 'N' ) ?
                    <IButton category="default" bType="CREATE" accesskey="C" componentId={ComponentId.CMP_ADD_CRT} onClick={this.createPaymentPopup}><IMessage msgkey={Key.CRT_LBL}  /></IButton>
                :
                    <IButton category="default" bType="OK" accesskey="K"  componentId={ComponentId.CMP_ADD_OK} onClick={this.okPaymentPopup}><IMessage msgkey={Key.OK_LBL}  /></IButton>
                }
                    <IButton category="default" bType="CLOSE" accesskey="O" componentId={ComponentId.CMP_CLS} onClick={this.closeAddPaymentPopup}><IMessage msgkey={Key.CLS_LBL}  /></IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

AddPaymentPopUp.propTypes = {
    closeAddPaymentPopup: PropTypes.func

}

export default icpopup(wrapForm('addPaymentPopUp')(AddPaymentPopUp), { title: 'Add Advance Payment', className: 'modal_600px' })

