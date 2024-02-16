import React, { Fragment } from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col,FormGroup } from "reactstrap";
import { IButton, ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { Key,Constants,ComponentId } from '../../constants/constants.js';
import PropTypes from 'prop-types';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistSettlementBatch = this.onlistSettlementBatch.bind(this);
        this.onclearSettlementBatch = this.onclearSettlementBatch.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);       
        this.dateRangeIdr = 'ListSettlementBatchDateRange'
   }
    onlistSettlementBatch() {
        this.props.onlistSettlementBatch(this.props.listSettlementBatchFilter.values);
    }
     onclearSettlementBatch() {
        this.props.reset();
        this.props.onclearSettlementBatch();
    }
    toggleFilter(){
        this.props.onToggleFilter((this.props.screenMode === 'edit')?'display':'edit');
    }
    render() {
       // this.computeDateRange(this.props.formValues)
       
        let batchStatus=[];        
        let selectedBatchStatus="";
        let selectedBatchStatusLabel="";        
         if (!isEmpty(this.props.oneTimeValues)) {
               batchStatus = this.props.oneTimeValues['mail.mra.receivablemanagement.batchstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
         }
        if (this.props.screenMode === 'display') {
            if (this.props.filterValues && this.props.filterValues.batchStatus && this.props.filterValues.batchStatus.length > 0) {
                selectedBatchStatus = batchStatus.find((element) => {return  element.value === this.props.filterValues.batchStatus});
                selectedBatchStatusLabel = selectedBatchStatus.label;
            }                       
        }
        return (<Fragment>
           
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial'  || (this.props.screenMode === 'list' && this.props.noRecords )) ? ( 
                    <div className="flippane animated fadeInDown" id="headerForm">
                        <div className="pad-md pad-b-3xs">
                            <Row>                                
                               <Col  xs="3" sm="4" md="3">
                                    <div className="form-group">
                                       <label className="form-control-label "><IMessage msgkey={Key.BATCH_STA_LBL} /></label>
                                        <ISelect defaultOption={true} name="batchStatus" options={batchStatus} componentId={ComponentId.CMP_BATCH_STA} />
                                    </div>
                                </Col>
                                <Col  xs="3" sm="4" md="3">
                                    <FormGroup className="mandatory">
                                        <div className="form-group">
                                            <label className="form-control-label mandatory_label"><IMessage msgkey={Key.DATE_FRM_LBL}/></label>
                                             <DatePicker name="fromDate"  dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" componentId={ComponentId.CMP_DATE_FRM} />
                                        </div>
                                    </FormGroup>
                                </Col>
                                <Col  xs="3" sm="4" md="3">
                                    <FormGroup className="mandatory">
                                        <div className="form-group">
                                            <label className="form-control-label mandatory_label"><IMessage msgkey={Key.DATE_TO_LBL} /></label>
                                            <DatePicker name="toDate"  dateFieldId={this.dateRangeIdr} type="to"  fromDateName="fromDate" componentId={ComponentId.CMP_DATE_TO} />
                                        </div>
                                    </FormGroup>
                                </Col>
                                <Col xs="3" sm="4" md="3" >
                                    <div className="form-group">
                                        <label className="form-control-label "><IMessage msgkey={Key.BATCH_ID_LBL}/></label>
                                        <ITextField componentId="CMP_MAIL_MRA_LISTSETTLEMENTBATCH_BATCHID" className="form-control" name="batchId" type="text" maxLength="16"></ITextField>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                        <div className="btn-row">
                            <IButton category="primary" bType="LIST" accesskey="L" componentId={ComponentId.CMP_LST} onClick={this.onlistSettlementBatch}>List</IButton>
                            <IButton category="default" bType="CLEAR" accesskey="C" componentId={ComponentId.CMP_CLR} onClick={this.onclearSettlementBatch}>Clear</IButton>
                        {(this.props.screenMode === 'edit') ?
                            <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i> :
                            ""}
                        </div>
                    </div>
                ) : (
                        <div className="flippane animated fadeInDown" id="headerData" >
                            <div className="pad-md">
                                <Row>
									{this.props.filterValues && this.props.filterValues.batchStatus && this.props.filterValues.batchStatus.length > 0 ?
                                    <Col xs="3">
                                        <label className="form-control-label"><IMessage msgkey={Key.BATCH_STA_LBL} /></label>
                                        <div className="form-control-data">{selectedBatchStatusLabel}</div>
                                    </Col> : ""
									}  
                                    {this.props.filterValues && this.props.filterValues.fromDate && this.props.filterValues.fromDate.length > 0 ?
                                    <Col xs="3">
                                        <label className="form-control-label"><IMessage msgkey={Key.DATE_FRM_LBL}/></label>
                                        <div className="form-control-data">{this.props.filterValues.fromDate}</div>
                                    </Col> : ""
									}
									{this.props.filterValues && this.props.filterValues.toDate && this.props.filterValues.toDate.length > 0 ?
                                    <Col xs="3">
                                        <label className="form-control-label"><IMessage msgkey={Key.DATE_TO_LBL} /></label>
                                        <div className="form-control-data">{this.props.filterValues.toDate}</div>
                                    </Col> : ""
									}                                                                  
                                    {this.props.filterValues && this.props.filterValues.batchId && this.props.filterValues.batchId.length > 0 ?
                                    <Col xs="3">
                                        <label className="form-control-label"><IMessage msgkey={Key.BATCH_ID_LBL} /></label>
                                        <div className="form-control-data">{this.props.filterValues.batchId}</div>
                                    </Col> : ""
									}                                                                   
                                </Row>
                            </div>
                                <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
                        </div>
                    )
                }
        </Fragment>)
}
}

FilterPanel.propTypes = {
    noRecords: PropTypes.bool,
    //listSettlementbatchesFilter: PropTypes.object,   
    onclearSettlementBatch: PropTypes.func,
    onToggleFilter: PropTypes.func,
    onlistSettlementBatch: PropTypes.func,
    oneTimeValues: PropTypes.object,
    filterValues: PropTypes.object,
    //filterDisplayMode: PropTypes.string
};
export default wrapForm('listSettlementBatchFilter')(FilterPanel);