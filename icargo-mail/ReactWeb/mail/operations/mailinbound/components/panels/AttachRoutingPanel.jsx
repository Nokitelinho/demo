import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Col, Label, Row} from 'reactstrap'
import { ITextField, ISelect, IButton, ITextArea } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import OnwardRouting from './OnwardRouting.jsx';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class AttachRoutingPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transferFilterType: "C"
        }
    }

    onFilterChange = (event) => {
        this.setState({ transferFilterType: event.target.value })
    }
   
    transferContainer(){
        this.props.transferContainer();
    }

    
    close=()=>{
        this.props.from==='MAIL'?
            this.props.closeMailPopUp('ATTACH_ROUTING'):this.props.closeContainerPopUp('ATTACH_ROUTING');
    }
    
   
    render() {
        let type = [];
       if (!isEmpty(this.props.oneTimeValues)) {
            type = this.props.oneTimeValues['mailtracking.defaults.consignmentdocument.type'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
       }
        return (
            <Fragment>
                <PopupBody>
                <div className="pad-md"> 
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    {/* <Label className="form-control-label">Con Doc No.*</Label> */}
                                    <Label className="form-control-label mandatory_label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.condocno" />
                                    </Label>
                                       <ITextField name="consignemntNumber" /> 
                                            </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    {/* <Label className="form-control-label">PA*</Label> */}
                                    <Label className="form-control-label mandatory_label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.tooltip.pa" />
                              </Label>
                                   <Lov name="paCode" lovTitle="PA Code" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1"  />

                  
                    </div>                                    
                            </Col>
                            <Col>
                                <div className="mar-t-md">
                                                    <IButton category="primary" className="m-r-10" bType="LIST" accesskey="L" onClick={this.props.listAttachRouting}>List</IButton>
                                               
                                                    <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.props.clearAttachRoutingPanel}>Clear</IButton>
                                </div>
                            </Col>
                                            </Row>
                        <div className="strong pad-b-2xs border-bottom mar-b-2sm">Add Routing</div>
               


                    <Row>
                            <Col xs="7">
                            <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.tooltip.flightnumber" />
                              </Label>
                        </Col>
                            <Col xs="7">
                            <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.flightdate" />
                              </Label>
                        </Col>
                            <Col xs="4">
                            <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.pol" />
                              </Label>
                        </Col>
                            <Col xs="4">
                            <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pou" />
                              </Label>
                        </Col>
                    </Row>
                        {(this.props.show) ? <OnwardRouting fromPopup="AttachRouting" initialValues={this.props.initialValues}/> : null}


                    <Row>
                            <Col xs="12">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.dsndate" />
                              </Label>
                            <DatePicker name="consignmentDate" />
                                </div>
                        </Col>
                            <Col xs="12">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.consignmenttype" />
                              </Label>
                            <ISelect defaultOption={true} name="consignmentType" options={type} />
                                </div>
                        </Col> 
                           {/* <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">Sub Type</Label>
                            <ISelect name="subType" />
                                </div>
                            </Col>*/}
                            <Col>
                                <div className="form-group m-0">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.remarks" />
                              </Label>
                            <ITextArea name="remarks" />
                                </div>
                            </Col>
                        </Row>
                </div>

            </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.props.saveAttachRouting} >Save</IButton>
                    <IButton category="secondary" bType="CLOSE" onClick={this.close}>Close</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

AttachRoutingPanel.propTypes = {
    from: PropTypes.string,
    transferContainer: PropTypes.func,
    closeMailPopUp: PropTypes.func,
    closeContainerPopUp: PropTypes.func,
    initialValues:PropTypes.object,
    listAttachRouting:PropTypes.func,
    clearAttachRoutingPanel:PropTypes.func,
    show:PropTypes.bool,
    saveAttachRouting:PropTypes.func,
    oneTimeValues: PropTypes.object,
}
export default icpopup(wrapForm('attachRoutingForm')(AttachRoutingPanel), { title: 'Attach Routing' })


