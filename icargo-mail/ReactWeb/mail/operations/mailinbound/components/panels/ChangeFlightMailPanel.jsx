import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Col, Label, Row} from 'reactstrap'
import { IButton, ITextArea,ITextField } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import ChangeFlightContainerDetailsTable from './ChangeFlightContainerDetailsTable.jsx';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import PopoverDetails from '../../../mailbagenquiry/components/panels/Popoverdetails.jsx';

class ChangeFlightMailPanel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            latestIndex:-1
        }
    }

    setLatestIndex=(data)=>{
         if(data.index!==-1)
             this.setState({latestIndex:data.index});
    }

    saveChangeFlight=()=>{
        this.props.saveChangeFlight(this.state.latestIndex);
    }

    close=()=>{
        this.props.closeChangeFlight('CHANGE_FLIGHT');
    }



    render() {
        return (
            <Fragment>
                <PopupBody>
                <div className="pad-md"> 
                    <Row>
                            <Col>
                                <IFlightNumber mode="edit"></IFlightNumber>
                            </Col>
                            <Col xs="auto">
                                <div className="mar-t-md">
                                    <IButton category="primary" bType="LIST" accesskey="L" onClick={this.props.listChangeFlightMailPanel}>List</IButton>
                                               
                                                    <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.props.clearChangeFlight}>Clear</IButton>
                                </div>
                                            
                                        </Col>
                                    </Row>
                                   
                                    <div className="card border-0">
                                 <div className="card-header card-header-action">
                                    <div className="col"><h4>Container Details</h4></div>
                                        <div className="col-auto">
                                        
                                        <PopoverDetails width={600} isValidFlight={this.props.addContainerButtonShow}
                                        saveNewContainer={this.props.saveNewContainer}
                                        pous={this.props.pous} changeFlightClose={this.props.changeFlightClose} clearAddContainerPopover={this.props.clearAddContainerPopover}
                                        /> 
                                        </div>
                                </div>
                {(this.props.show)?
                            <div className="mar-b-md">
                        <ChangeFlightContainerDetailsTable show={this.props.show} setLatestIndex={this.setLatestIndex} containerData={this.props.containerData}
                                    listChangeFlightMailPanel={this.props.listChangeFlightMailPanel} />
                            </div> :
                    null}</div>
                      
                
                    <Row>
                            <Col xs="8">
                                <div className="form-group">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                                    </Label>
                            {/* <DatePicker name="date" /> */}
                            {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="date" disabled={true}/>:<DatePicker name="date" />}
                                   
                                </div>
                        </Col>
                            <Col xs="8">
                                <div className="form-group">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scantime" />
                                    </Label>
                            <TimePicker name="time"  disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                                </div>
                            </Col>
                            <Col xs="24">
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
                    <IButton category="primary" onClick={this.saveChangeFlight} >Save</IButton>
                    <IButton category="secondary" onClick={this.props.clearChangeFlight} >Clear</IButton>
                    <IButton category="secondary" onClick={this.close}>Close</IButton>
                </PopupFooter>
            </Fragment>

        )
    }
}
ChangeFlightMailPanel.propTypes = {
    saveChangeFlight: PropTypes.func,
    closeChangeFlight: PropTypes.func,
    listChangeFlightMailPanel: PropTypes.func,
    clearAttachRoutingPanel:PropTypes.func,
    show:PropTypes.bool,
    containerData:PropTypes.object,
    clearChangeFlight:PropTypes.func
}
//export default icpopup(PopUpComponent, { title: 'Discrepancy' })
export default icpopup(wrapForm('ChangeFlightMailDetails')(ChangeFlightMailPanel), { title: 'Change Flight'})