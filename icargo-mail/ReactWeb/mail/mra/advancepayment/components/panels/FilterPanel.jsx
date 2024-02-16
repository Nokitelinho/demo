import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { Key,Constants,ComponentId } from '../../constants/constants.js';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.dateRange='AdvancePaymentDateRange'
        this.onclearPayment = this.onclearPayment.bind(this);
        this.onListPayment = this.onListPayment.bind(this);
        this.onAddPayment = this.onAddPayment.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);    
      
    }
    onListPayment(){
        this.props.onListPayment();
    }
    
    onclearPayment() {
        this.props.onclearPayment();
    }
    onAddPayment() {
        this.props.onAddPayment(); 
    }
    toggleFilter() {
        this.props.onToggleFilter((this.props.screenFilterMode === 'edit') ? 'display' : 'edit');
    }

    findArrayElement(array, value) {
        return array.find((element) => {
            return element.value === value;
        })
    }

    render() {
        
        let status = [];
        let batchStatusDisplay = "";

        if(!isEmpty(this.props.oneTimeValues)){
            status =this.props.oneTimeValues['mail.mra.receivablemanagement.batchstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        if(this.props.screenFilterMode==='display'){
            if(this.props.filterValues.batchStatus && this.props.filterValues.batchStatus.length>0){
            const selectedStatus = this.findArrayElement(status, this.props.filterValues.batchStatus);
            batchStatusDisplay = selectedStatus.label;
            }
        }
        return ( <Fragment>
        
        
        {(this.props.screenFilterMode === 'edit' || this.props.screenFilterMode === 'initial') ?
			
				<div className="header-filter-panel flippane" id="headerForm">
					<div className="pad-md pad-b-3xs">
                    <Row>
                    <div className="col-4 col-md-3">
                        <div className="form-group">
                            <label className="form-control-label " ><IMessage msgkey={Key.PACOD_LBL}  /></label>
                            <Lov name="paCode" lovTitle="GPA Code" uppercase={true} maxlength="5" dialogWidth="600"  dialogHeight="453" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId={ComponentId.CMP_PA} />
                        </div>
                    </div>                    
                    <div className="col-4 col-md-3">
                        <div className="form-group mandatory">
                            <label className="form-control-label mandatory_label"><IMessage msgkey={Key.DATE_FRM_LBL}  /></label>
                            <DatePicker name="fromDate"  componentId={ComponentId.CMP_DATE_FRM} dateFieldId={this.dateRange} type="from" toDateName="toDate"/>
                        </div>
                    </div>
                    <div className="col-4 col-md-3">
                        <div className="form-group mandatory">
                            <label className="form-control-label mandatory_label"><IMessage msgkey={Key.DATE_TO_LBL}  /></label>
                            <DatePicker name="toDate" componentId={ComponentId.CMP_DATE_TO} dateFieldId={this.dateRange} type="to" fromDateName="fromDate" />
                        </div>
                    </div> 
                    <div className="col-4 col-md-3">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey={Key.BATCH_STA_LBL}  /></label>
                            <ISelect  defaultOption={true} name="batchStatus" options={status} componentId={ComponentId.CMP_BATCH_STA}/> 
                        </div>
                    </div>  

                    <div className="col-auto">
                        <div className="mar-t-2lg"> 
                        <label className="form-control-label "><IMessage msgkey={Key.OR_LBL}  /></label>
                        </div>
                    </div>

                    <div className="col">
                        <div className="mt-3">
                        <IButton category="secondary" bType="ADDPAYMENT" accesskey="A" componentId={ComponentId.CMP_ADD_PAY} onClick={this.onAddPayment}><IMessage msgkey={Key.ADD_PAY_LBL}  /></IButton>
                        </div>
                    </div> 

                    </Row> 
                </div>
            <div className="btn-row">
                <IButton category="primary" bType="LIST" accesskey="L" componentId={ComponentId.CMP_LST} onClick={this.onListPayment}><IMessage msgkey={Key.LST_LBL}  /></IButton>
                <IButton category="default" bType="CLEAR" accesskey="C" componentId={ComponentId.CMP_CLR} onClick={this.onclearPayment}><IMessage msgkey={Key.CLR_LBL}  /></IButton>
            </div> 
            {(this.props.screenFilterMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
            </div>  
        
         :(
            
            <div className="header-summary-panel flippane" id="headerData">
                <div className="pad-md">
                 <Row>
                    {this.props.filterValues.paCode && this.props.filterValues.paCode.length > 0 ?
                        <div className="col-4 col-md-6 col-lg-4">
                            <label className="form-control-label"><IMessage msgkey={Key.PACOD_LBL}  /></label>
                            <div className="form-control-data">{this.props.filterValues.paCode}</div>
                        </div> : ""
                    }
                    {this.props.filterValues.fromDate && this.props.filterValues.fromDate.length > 0 ?
                        <div className="col-4">
                            <label className="form-control-label"><IMessage msgkey={Key.DATE_FRM_LBL}  /></label>
                            <div className="form-control-data">{this.props.filterValues.fromDate}</div>
                        </div> : ""
                    }    
                    {this.props.filterValues.toDate && this.props.filterValues.toDate.length > 0 ?
                         <div className="col-4">
                            <label className="form-control-label"><IMessage msgkey={Key.DATE_TO_LBL}  /></label>
                            <div className="form-control-data">{this.props.filterValues.toDate}</div>
                        </div> : ""
                    } 
                    {this.props.filterValues.batchStatus && this.props.filterValues.batchStatus.length > 0 ?
                         <div className="col-4">
                            <label className="form-control-label"><IMessage msgkey={Key.BATCH_STA_LBL}  /></label>
                            <div className="form-control-data">{batchStatusDisplay}</div>
                        </div> : ""
                    }                                                          
                 </Row>
                </div>
                <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleFilter}> </i>
                </div>
                
             
         )}
        
      

         </Fragment>)
    }
}


export default wrapForm('paymentBatchFilter')(FilterPanel);