import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Col, Label, Row } from 'reactstrap'
import { IButton, IRadio, ITextArea ,ITextField} from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import ChangeFlightContainerDetailsTable from './ChangeFlightContainerDetailsTable.jsx';
import PopoverDetails from '../../../mailbagenquiry/components/panels/Popoverdetails.jsx';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class TransferMailPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transferFilterType: "C",
            latestIndex:-1,
            isOwnCarrierCode:'Y'
        }
    }

    onFilterChange = (event) => {
        this.setState({ transferFilterType: event.target.value })
        this.props.clearTransferPanel(event.target.value);
       
    }

    close=()=>{
        this.props.closeTransferMail('TRANSFER');
    }

    onContainerRowSelection=(data)=>{
        if(data.index!==-1)
             this.setState({latestIndex:data.index});
        this.props.setLatestIndex(data);
    }

    highlightRowOnSelection=(data)=>{
        if(data.index!==-1){
                if(this.state.latestIndex!==-1){
                    if(this.state.latestIndex===data.index){
                        return 'table-row table-row__bg-red text-primary' 
                    }
                }
        }
        return 'table-row' 

    }
   
    transferContainer=()=>{
        this.props.saveTransferMail(this.state.latestIndex);
    }

    setLatestIndex=(data)=>{
         if(data.index!==-1)
             this.setState({latestIndex:data.index});
    }
    clearTransferPanel=()=>{
this.props.clearTransferPanel(this.state.transferFilterType);
 this.setState({isOwnCarrierCode:'Y'});
    }
   
    validateCarrier =(event) =>{
       
        let partnerCarriers = this.props.partnerCarriers;
        if(event.target.defaultValue !== this.props.ownAirlineCode ){
             this.setState({isOwnCarrierCode:'N'});
        }
        if(event.target.defaultValue === undefined || event.target.defaultValue==='' || event.target.defaultValue=== null || partnerCarriers&&partnerCarriers.includes(event.target.defaultValue)){
            this.setState({isOwnCarrierCode:'Y'});
        }
      
    }
   
    render() {
        return (
            <Fragment>
                <PopupBody>
          <div className="pad-md pad-b-3xs border-bottom">
                        <div className="mar-b-2md">
                            <Row>
                <Col xs="auto" className="align-self-center">
                                         <IMessage msgkey="mail.operations.mailinbound.transferto" />
                    </Col> 
                                <Col>
                        <IRadio name="transferFilterType" options={[{ 'label': 'Carrier', 'value': 'C' },
                                                                     { 'label': 'Flight', 'value': 'F' }]} 
                                                                     value={this.state.transferFilterType} 
                                                                     onChange={this.onFilterChange} />
                     </Col> 
                            </Row>
                        </div>
                 
                        <Row>
                            {(this.state.transferFilterType === 'C') ?
                                <Col xs="8">
                                            <div className="form-group mandatory">
                                                <Label className="form-control-label mandatory_label">
                                         <IMessage msgkey="mail.operations.mailinbound.carriercode" />
                                    </Label>
                                                 <Lov name= "carrier" lovTitle= "Carrier Code"  uppercase={true} dialogWidth="600" dialogHeight="425" 
                                                    actionUrl="ux.showAirline.do?formCount=1" onBlur={this.validateCarrier}/>
                                            </div>
                                </Col>
                                : null}
                                 {(this.state.transferFilterType === 'C') ?
                                <Fragment>
                                    <div className="col-8">
                                        <div className="form-group">
                                        <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                                    </Label>
                                            {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                                        </div>
                                    </div>
                                    <div className="col-8">
                                        <div className="form-group">
                                        <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scantime" />
                                    </Label>
                                            <TimePicker name="mailScanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                                        </div>
                                    </div>
                                    <div className="col-8">
                                        <div className="form-group">
                                        <Label className="form-control-label"> Destination</Label>
                                          <Lov name="destination"  uppercase={true} disabled={this.state.isOwnCarrierCode ==='N'?true:false} lovTitle="Destination airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                                        </div>
                                    </div>
                                    <div className="col-8">
                                        <div className="form-group">
                                        <Label className="form-control-label"> Uplift</Label>
                                          <ITextField name="uplift" disabled={this.state.isOwnCarrierCode ==='N'?true:false} uppercase={true} />
                                        </div>
                                    </div>
                                </Fragment>
                                : null}
                          
                                <Fragment>
                            {(this.state.transferFilterType === 'F') ?
                                    <Col xs="17">
                                            <div className="form-group">
                                             <IFlightNumber mode="edit" ></IFlightNumber>
                                           </div>
                                    </Col>
                                      : null}
                                    <Col>
                                        <div className="mar-t-md">
                                                    <IButton category="primary" className="m-r-10" bType="LIST" disabled={this.state.isOwnCarrierCode ==='N'?true:false} accesskey="L" onClick={this.props.listTransfer}>List</IButton>
                                                    <IButton category="secondary" bType="CLEAR" accesskey="C"  onClick={this.clearTransferPanel}>Clear</IButton>
                                        </div>
                                    </Col>
                                </Fragment>
                                    </Row>
          </div>

                      {/*  {(this.state.transferFilterType === 'F') ?  */}
          <div className="card border-0">
                                 <div className="card-header card-header-action">
                                    <div className="col"><h4>Container Details</h4></div>
                                        <div className="col-auto">
                                        <PopoverDetails width={600} filterType={this.state.transferFilterType} isValidFlight={this.props.addContainerButtonShow}
                                        saveNewContainer={this.props.saveNewContainer} pabuiltUpdate={this.props.pabuiltUpdate}
                                        pous={this.props.pous} showTransfer={this.props.showTransfer} clearAddContainerPopover={this.props.clearAddContainerPopover}
                                        /> 
                                        </div>
                                </div>
            {(this.props.show)?
              <div className="mar-b-md border-bottom">
                                        <ChangeFlightContainerDetailsTable show={this.props.show} setLatestIndex={this.setLatestIndex} containerData={this.props.containerData} listChangeFlightMailPanel={this.props.listChangeFlightMailPanel} />
                                    </div> : null}
                     {(this.state.transferFilterType === 'F') ?  
              <div className="pad-x-md pad-b-3xs">
                    <Row>
                                    <Col xs="8">
                                        <div className="form-group">
                                        <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                                    </Label>
                                    {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                            
                                        </div>
                        </Col>
                                    <Col xs="8">
                                        <div className="form-group">
                                        <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scantime" />
                                    </Label>
                            <TimePicker name="mailScanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                                        </div>
                                    </Col>
                                </Row>
              </div> : null}
                            </div>
          <div className="pad-x-md pad-b-md">
                                <Label className="form-control-label">
                                <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.remarks" />
                                    </Label>
                            <ITextArea name="remarks" />
                                </div>
            </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.transferContainer} >Save</IButton>
                    <IButton category="primary" onClick={this.close}>Close</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

TransferMailPanel.propTypes = {
    closeTransferMail: PropTypes.func,
    saveTransferMail: PropTypes.func,
    setLatestIndex: PropTypes.func,
    listTransfer: PropTypes.func,
    clearTransferPanel: PropTypes.func,
    addContainerButtonShow:PropTypes.bool,
    saveNewContainer:PropTypes.object,
    pous:PropTypes.object,
    show:PropTypes.bool,
    containerData:PropTypes.array
}
export default icpopup(wrapForm('transferMailForm')(TransferMailPanel), { title: 'Transfer Mail' })


