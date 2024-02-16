import React, { Fragment } from 'react';
import { Row, Col, Container, Input } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, ISelect, IButton, ICheckbox, IRadio } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import PropTypes from 'prop-types'
import DamageData from './DamageData.jsx';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';


class ReturnMail extends React.PureComponent {
    constructor(props) {
        super(props);
        //this.doReturnMail=this.doReturnMail.bind(this);

    }
    doReturnMail = () => {
        this.props.doReturnMail(this.props.selectedMail);
    }
    render() {

        let PACodes = [];
        let mailbagId = null;
        let reqDeliveryDateAndTime = null;
        let mailorigin = null;
        let mailDestination = null;
        let selectedMail = null;
         let multiple =false;
        if(this.props.selectedMail)
        {selectedMail = this.props.selectedMail;}
        if(this.props.selectedMailbag && this.props.selectedMailbag.length > 0 ){
            if(this.props.selectedMailbag.length===1){
         mailbagId = this.props.selectedMailbag[0].mailbagId;
         reqDeliveryDateAndTime = this.props.selectedMailbag[0].reqDeliveryDateAndTime;
         mailorigin = this.props.selectedMailbag[0].mailorigin;
         mailDestination = this.props.selectedMailbag[0].mailDestination;}
         else{
            
            multiple=true
        }
        }else{
            if(selectedMail!=null){
            mailbagId = this.props.mailbagDetails[selectedMail].mailbagId;
         reqDeliveryDateAndTime = this.props.mailbagDetails[selectedMail].reqDeliveryDateAndTime;
         mailorigin = this.props.mailbagDetails[selectedMail].mailorigin;
         mailDestination = this.props.mailbagDetails[selectedMail].mailDestination;
            }else{
            multiple=true 
            }
        }
         
         if(this.props.postalCodes){
        PACodes = this.props.postalCodes.map((data) => ({ value: data.paCode, label: data.paName }));}
        // const { row, index, addRow, deleteRow, numRows } = this.props;
        return (
            <Fragment>
                <PopupBody>
               {!multiple&& <div><div className="pad-x-md pad-b-md">
                MailBag Details
                </div>
                <div className="pad-x-md pad-b-md">
                        <Row>
                            <Col>
                                    <h name = "mailbagId">{mailbagId}</h>
                            </Col>
                            <Col>
                                    RDT:<h name="reqDeliveryDateAndTime">{reqDeliveryDateAndTime}</h>
                            </Col>
                            <Col md="5">
                                    <h name = "mailorigin">{mailorigin}</h>-<h name = "mailDestination">{mailDestination}</h>
                            </Col>
                        </Row>
                    </div></div> 
               }
                <div className="pad-x-md pad-b-md">
                        <Row>
                            <Col xs="auto mar-t-md">
                                <ICheckbox name="isReturnMail" label="Return" />
                            </Col>
                            <Col xs="9">
                                <div className="form-group m-0">
                                    <label className="form-control-label ">Postal Administration Code</label>
                                    <ISelect name="paCode" options={PACodes} />
                                </div>
                            </Col>
                        </Row>
                    </div>
                    <div className="pad-md scroll-y" style={{ maxHeight: "200px" }}>
                        <DamageData damageCodes={this.props.damageCodes} />
                    </div>
                    
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.doReturnMail}>Save</IButton>{' '}
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.close}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

ReturnMail.propTypes = {
    toggleFn: PropTypes.func,
    onSaveContainer: PropTypes.func
}
export default icpopup(wrapForm('returnMailForm')(ReturnMail), { title: 'Return Mail' })
