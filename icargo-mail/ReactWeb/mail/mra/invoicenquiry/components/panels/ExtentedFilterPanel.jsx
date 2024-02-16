import React, { Component } from 'react';
import { Row, Col, Label } from "reactstrap";
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ICheckbox,ISelect, IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class ExtentedFilterPanel extends React.Component {

    constructor(props) {
        super(props);
       this.onbucketSort = this.bucketSort.bind(this);
        this.state = {
            openPopover: false,
            remarksTarget: '',
            checkboxStatus:[],
            selectedClaimRange: []
        }

    }

    openGroupRemarks = (id) => {
        this.setState(() => {
            return {
                openPopover: true,
                remarksTarget: id
            }
        })
    }
    closeGroupRemarks = () => {
        this.setState(() => {
            return {
                openPopover: false
            }
        })
    }

    doneGroupRemarks = () => {
        const filter=this.props.invoicFilterForm;
        let isValid = true;
        let error = "" ;
        let gpaCode = null;
        let fromDate =null;
        let toDate =null;
        let invoicId =null;

        if(!isEmpty(filter.values)){
          gpaCode=filter.values.gpaCode;
          fromDate=filter.values.fromDate;
          toDate = filter.values.toDate;
          invoicId = filter.values.invoicId;
        }


        if (invoicId == null) {
        if (gpaCode == null || fromDate == null || toDate == null ) {
            isValid = false;
                this.props.displayError('Mandatory fields in filter cannot be blank')
        }
		 }
        if (!isValid) {
        dispatch(requestValidationError(error, ''));
        }
        else{
        let remarks = document.getElementById('groupremarks').value;
        let toProcessStatus = this.state.remarksTarget;
        let claimRangeforGroupRemarks = this.state.selectedClaimRange;
        let processStatusforGroupRemarks = this.state.checkboxStatus;
        let data = { remarks: remarks, toProcessStatus: toProcessStatus,selectedClaimRange:claimRangeforGroupRemarks,selectedProcessStatus:processStatusforGroupRemarks  }
        this.props.ondoneGroupRemarks(data);
        }
        this.setState(() => {
            return {
                openPopover: false,
            }
        })
    }

    bucketSort= () => {
        const filter=this.props.invoicFilterForm;
        let isValid = true;
        let error = "" ;
        let gpaCode = null;
        let fromDate =null;
        let toDate =null;
        let invoicId =null;

        if(!isEmpty(filter.values)){
          gpaCode=filter.values.gpaCode;
          fromDate=filter.values.fromDate;
          toDate = filter.values.toDate;
          invoicId = filter.values.invoicId;
        }


        if (invoicId == null) {
        if (gpaCode == null || fromDate == null || toDate == null ) {
            isValid = false;
                this.props.displayError('Mandatory fields in filter cannot be blank')
        }
		 }
        if (!isValid) {
        dispatch(requestValidationError(error, ''));
        }
        else
        this.props.onbucketSort(this.state);
    }

    checkboxStatus =(event) => {
        let checkbox=[];
        checkbox=this.state.checkboxStatus;
        (event.target.checked === true) ?
            checkbox.push(event.target.name) :
            checkbox.splice(checkbox.indexOf(event.target.name), 1)
        this.setState(() => {
            return {
                checkboxStatus: checkbox
            }
        })
    }
    claimRange = (selectedRange, event) => {
        let claimRange = [];
        claimRange = this.state.selectedClaimRange;

        (event.target.checked === true) ?
            claimRange.push(selectedRange) : claimRange.splice(claimRange.indexOf(selectedRange), 1)

        this.setState(() => {
            return {
                selectedClaimRange: claimRange

            }
        })

    }

    originInputChange = (values) =>{
        let sortwith = values;
        let origin = this.props.origin;

        let data ={sortwith:sortwith,origin:origin}
        this.props.originInputChange(data);
    }

    render() {

        let mailsubclass = [];
        let  displayOrigin=[];
        let mailSubClassCodes = [];

        let displayDestination =[];
        let origin = [];
        let destination = [];

        if (!isEmpty(this.props.oneTimeValues)) {
            mailsubclass = this.props.oneTimeValues['mailtracking.defaults.mailclass'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            mailSubClassCodes=this.props.mailSubClassCodes.map((value) => ({ value: value, label: value }));
        }
        if (!isEmpty(this.props.displayOrigin)) {
            displayOrigin = this.props.displayOrigin.map((value) => ({ value: value, label: value }));


        }
        if (!isEmpty(this.props.displayDestination)) {
            displayDestination =  this.props.displayOrigin.map((value) => ({ value: value, label: value }));
        }



      const clmlessfiv = this.props.clmlessfiv?'('+this.props.clmlessfiv+')':'';
      const clmfivtoten = this.props.clmfivtoten?'('+this.props.clmfivtoten+')':'';
      const clmtentotwentyfiv = this.props.clmtentotwentyfiv?'('+this.props.clmtentotwentyfiv+')':'';
      const clmtwentyfivtofifty = this.props.clmtwentyfivtofifty?'('+this.props.clmtwentyfivtofifty+')':'';
      const clmfiftytohundred = this.props.clmfiftytohundred?'('+this.props.clmfiftytohundred+')':'';
      const clmhundredtofivhundred = this.props.clmhundredtofivhundred?'('+this.props.clmhundredtofivhundred+')':'';
      const clmgrtfivhundred = this.props.clmgrtfivhundred?'('+this.props.clmgrtfivhundred+')':'';
      const currencycode = this.props.currencyCode?'('+this.props.currencyCode+')':'';
      const cntawtinc = this.props.cntawtinc?'('+this.props.cntawtinc+')':'';
      const cntovrnotmra = this.props.cntovrnotmra?'('+this.props.cntovrnotmra+')':'';
      const clmzropay = this.props.clmzropay?'('+this.props.clmzropay+')':'';
      const clmnoinc = this.props.clmnoinc?'('+this.props.clmnoinc+')':'';
      const clmratdif = this.props.clmratdif?'('+this.props.clmratdif+')':'';
      const clmwgtdif = this.props.clmwgtdif?'('+this.props.clmwgtdif+')':'';
      const clmmisscn = this.props.clmmisscn?'('+this.props.clmmisscn+')':'';
      const clmlatdlv = this.props.clmlatdlv?'('+this.props.clmlatdlv+')':'';
      const clmsrvrsp = this.props.clmsrvrsp?'('+this.props.clmsrvrsp+')':'';
      const latdlv = this.props.latdlv?'('+this.props.latdlv+')':'';
      const dlvscnwrg = this.props.dlvscnwrg?'('+this.props.dlvscnwrg+')':'';
      const misorgscn = this.props.misorgscn?'('+this.props.misorgscn+')':'';
      const misdstscn = this.props.misdstscn?'('+this.props.misdstscn+')':'';
      const fulpaid = this.props.fulpaid?'('+this.props.fulpaid+')':'';
      const ovrratdif = this.props.ovrratdif?'('+this.props.ovrratdif+')':'';
      const ovrwgtdif = this.props.ovrwgtdif?'('+this.props.ovrwgtdif+')':'';
      const ovrclsdif = this.props.ovrclsdif?'('+this.props.ovrclsdif+')':'';
      const ovrsrvrsp = this.props.ovrsrvrsp?'('+this.props.ovrsrvrsp+')':'';
      const ovrpayacp = this.props.ovrpayacp?'('+this.props.ovrpayacp+')':'';
      const ovrpayrej = this.props.ovrpayrej?'('+this.props.ovrpayrej+')':'';
      const ovroth = this.props.ovroth?'('+this.props.ovroth+')':'';
      const clmoth = this.props.clmoth?'('+this.props.clmoth+')':'';
      const clmnotinv = this.props.clmnotinv?'('+this.props.clmnotinv+')':'';
      const clmfrcmjr = this.props.clmfrcmjr?'('+this.props.clmfrcmjr+')':'';
      const dummyorg = this.props.dummyorg?'('+this.props.dummyorg+')':'';
      const dummydst = this.props.dummydst?'('+this.props.dummydst+')':'';
      const shrpayacp = this.props.shrpayacp?'('+this.props.shrpayacp+')':'';
      const clmstagen = this.props.clmstagen?'('+this.props.clmstagen+')':'';
      const clmstasub = this.props.clmstasub?'('+this.props.clmstasub+')':'';
      const amotobeact = this.props.amotobeact?'('+this.props.amotobeact+')':'';
      const amotact = this.props.amotact?'('+this.props.amotact+')':'';


        return (
            <div>
            <div className="filter-pane-right">
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name="AWTINC" type="checkbox" value='AWTINC' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Awaiting INVOIC {cntawtinc}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_AWTINC" onClick={() => this.openGroupRemarks('AWTINC')}></i>
                        </a>
                    </div>

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name="FULPAID" type="checkbox" value='FULPAID' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Paid in Full {fulpaid}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_FULPAID" onClick={() => this.openGroupRemarks('FULPAID')}></i>
                        </a>
                    </div>

                </Row>
               {/* <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name="MISSCN" type="checkbox" value='MISSCN' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Missing Both Scans(not in MRA)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_MISSCN" onClick={() => this.openGroupRemarks('MISSCN')}></i>
                        </a>
                    </div>

               </Row>*/}

                <div className="mar-t-md">
                    <span className="font-bold fs13">Service Failures</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name="MISORGSCN" type="checkbox" value='MISORGSCN' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Missing Origin Scan {misorgscn}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_MISORGSCN" onClick={() => this.openGroupRemarks('MISORGSCN')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="MISDSTSCN" type="checkbox" value='MISDSTSCN' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Missing Destination Scan {misdstscn}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_MISDSTSCN" onClick={() => this.openGroupRemarks('MISDSTSCN')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="LATDLV" type="checkbox" value='LATDLV' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Late Deliveries {latdlv}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_LATDLV" onClick={() => this.openGroupRemarks('LATDLV')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="DLVSCNWRG"  type="checkbox" value='DLVSCNWRG' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Delivered at Wrong airport</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_DLVSCNWRG" onClick={() => this.openGroupRemarks('DLVSCNWRG')}></i>
                        </a>
                    </div>

               </Row>

                <div className="mar-t-md">
                    <span className="font-bold fs13">Over Payment</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRNOTMRA" type="checkbox" value='OVRNOTMRA' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">In INVOIC,not in MRA {cntovrnotmra}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRNOTMRA" onClick={() => this.openGroupRemarks('OVRNOTMRA')}></i>
                        </a>
                    </div>

                </Row>
                {/* <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox" value='OVRWVR' onClick={this.checkboxStatus}></input>
                            <label className="form-check-label">Waiver</label>
                        </div>
        </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRWVR" onClick={() => this.openGroupRemarks('OVRWVR')}></i>
                        </a>
                    </div>

        </Row> */}
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRRATDIF" type="checkbox" value='OVRRATDIF' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Rate Difference {ovrratdif}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRRATDIF" onClick={() => this.openGroupRemarks('OVRRATDIF')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRWGTDIF" type="checkbox" value='OVRWGTDIF' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Weight Difference {ovrwgtdif}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRWGTDIF" onClick={() => this.openGroupRemarks('OVRWGTDIF')}></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRCLSDIF" type="checkbox" value='OVRCLSDIF' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Mail Class Difference {ovrclsdif}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRCLSDIF" onClick={() => this.openGroupRemarks('OVRCLSDIF')}></i>
                        </a>
                    </div>

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRSRVRSP" type="checkbox" value='OVRSRVRSP' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Service Responsiveness Mismatch {ovrsrvrsp}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRSRVRSP" onClick={() => this.openGroupRemarks('OVRSRVRSP')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRPAYACP" type="checkbox" value='OVRPAYACP' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Overpay Accepted {ovrpayacp}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRPAYACP" onClick={() => this.openGroupRemarks('OVRPAYACP')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRPAYREJ" type="checkbox" value='OVRPAYREJ' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Overpay Rejected{ovrpayrej}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRPAYREJ" onClick={() => this.openGroupRemarks('OVRPAYREJ')}></i>
                        </a>
                    </div>

                </Row>

                 <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVROTH" type="checkbox" value='OVROTH' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Others {ovroth}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVROTH" onClick={() => this.openGroupRemarks('OVROTH')}></i>
                        </a>
                    </div>

                </Row>



                {/*<Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox" value='OVRLOCDIF' onClick={this.checkboxStatus}></input>
                            <label className="form-check-label">Location Difference</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRLOCDIF" onClick={() => this.openGroupRemarks('OVRLOCDIF')}></i>
                        </a>
                    </div>
                </Row>*/}
               {/* <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OVRSRVFLR" type="checkbox" value='OVRSRVFLR' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Service Failure Paid in Full</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OVRSRVFLR" onClick={() => this.openGroupRemarks('OVRSRVFLR')}></i>
                        </a>
                    </div>
               </Row>*/}

                <div className="mar-t-md">
                    <span className="font-bold fs13">Claimable</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMZROPAY"  type="checkbox" value='CLMZROPAY' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Zero Pay(MSX) {clmzropay}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMZROPAY" onClick={() => this.openGroupRemarks('CLMZROPAY')}></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMNOTINV" type="checkbox" value='CLMNOTINV' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Not in  INVOIC(NPR) {clmnotinv}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMNOTINV" onClick={() => this.openGroupRemarks('CLMNOTINV')}></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMNOINC" type="checkbox" value='CLMNOINC' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Incentive Not Paid {clmnoinc}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMNOINC" onClick={() => this.openGroupRemarks('CLMNOINC')}></i>
                        </a>
                    </div>

                </Row>
               {/* <Row>
                   <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox" value='CLMDATDIF' onClick={this.checkboxStatus}></input>
                            <label className="form-check-label">Date Difference(EDT)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMDATDIF" onClick={() => this.openGroupRemarks('CLMDATDIF')}></i>
                        </a>
                    </div>
                      <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMSRVFLR"  type="checkbox" value='CLMSRVFLR' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Short paid Service failure</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMSRVFLR" onClick={() => this.openGroupRemarks('CLMSRVFLR')}></i>
                        </a>
            </div>
                </Row> */}
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMRATDIF" type="checkbox" value='CLMRATDIF' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Rate Difference(RVX) {clmratdif}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMRATDIF" onClick={() => this.openGroupRemarks('CLMRATDIF')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMWGTDIF" type="checkbox" value='CLMWGTDIF' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Weight Difference (WXX) {clmwgtdif}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMWGTDIF" onClick={() => this.openGroupRemarks('CLMWGTDIF')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMMISSCN" type="checkbox" value='CLMMISSCN' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Missing Scans {clmmisscn}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMMISSCN" onClick={() => this.openGroupRemarks('CLMMISSCN')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMLATDLV" type="checkbox" value='CLMLATDLV' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Late Delivery {clmlatdlv}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMLATDLV" onClick={() => this.openGroupRemarks('CLMLATDLV')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMSRVRSP" type="checkbox" value='CLMSRVRSP' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Service responsiveness Mismatch {clmsrvrsp}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMSRVRSP" onClick={() => this.openGroupRemarks('CLMSRVRSP')}></i>
                        </a>
                    </div>

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="CLMFRCMJR" type="checkbox" value='CLMFRCMJR' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Force Majeure Not Matching {clmfrcmjr}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_CLMFRCMJR" onClick={() => this.openGroupRemarks('CLMFRCMJR')}></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input'  name="OTH" type="checkbox" value='OTH' onClick={this.checkboxStatus}></ICheckbox>
                            <label className="form-check-label">Others {clmoth}</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="#" className="trigger_remarks">
                            <i className="icon ico-review " id="groupremarks_OTH" onClick={() => this.openGroupRemarks('OTH')}></i>
                        </a>
                    </div>

                </Row>
                <div className="border-top border-bottom mar-y-sm pad-y-sm">
                <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="AMOTOBEACT" type="checkbox" value='AMOTOBEACT' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label"> AMOT-To Be Actioned {amotobeact}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_AMOTOBEACT" onClick={() => this.openGroupRemarks('AMOTOBEACT')}></i>
                            </a>
                        </div>
                    </Row>
                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="AMOTACT" type="checkbox" value='AMOTACT' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">AMOT-Actioned {amotact}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_AMOTACT" onClick={() => this.openGroupRemarks('AMOTACT')}></i>
                            </a>
                        </div>
                    </Row>
                </div>

<div className="border-top border-bottom mar-y-sm pad-y-sm">
                <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="DUMMYORG" type="checkbox" value='DUMMYORG' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">Dummy Origin {dummyorg}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_DUMMYORG" onClick={() => this.openGroupRemarks('DUMMYORG')}></i>
                            </a>
                        </div>
                    </Row>

                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="DUMMYDST" type="checkbox" value='DUMMYDST' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">Dummy Destination {dummydst}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_DUMMYDST" onClick={() => this.openGroupRemarks('DUMMYDST')}></i>
                            </a>
                        </div>

                    </Row>


                </div>

                <div className="border-top border-bottom mar-y-sm pad-y-sm">
                <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="SHRPAYACP" type="checkbox" value='SHRPAYACP' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">Shortpay Accepted {shrpayacp}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_SHRPAYACP" onClick={() => this.openGroupRemarks('SHRPAYACP')}></i>
                            </a>
                        </div>
                    </Row>

                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="GEN" type="checkbox" value='GEN' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">Claim Generated {clmstagen}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_GEN" onClick={() => this.openGroupRemarks('GEN')}></i>
                            </a>
                        </div>

                    </Row>

                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <ICheckbox className='form-check-input'  name="SUB" type="checkbox" value='SUB' onClick={this.checkboxStatus}></ICheckbox>
                                <label className="form-check-label">Claim Submitted {clmstasub}</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="#" className="trigger_remarks">
                                <i className="icon ico-review " id="groupremarks_SUB" onClick={() => this.openGroupRemarks('SUB')}></i>
                            </a>
                        </div>

                    </Row>


                </div>

                <div className="mar-y-md row">
                    <Col>
                        <span className="font-bold fs13">Select Claim Range {currencycode} </span>
                    </Col>
                  {/*  <div className="col-auto"> <a href=""><i className="icon ico-arrow-left"></i>Clear</a></div>*/}
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='0-5' type="checkbox" value="0-5" onClick={(event) => this.claimRange('0-5', event)} ></ICheckbox>
                            <label className="form-check-label">5 and below {clmlessfiv} </label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='6-10' type="checkbox" value="6-10" onClick={(event) => this.claimRange('6-10', event)}></ICheckbox>
                            <label className="form-check-label">6 - 10 {clmfivtoten}</label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='11-25' type="checkbox" value="11-25" onClick={(event) => this.claimRange('11-25', event)}></ICheckbox>
                            <label className="form-check-label"> 11 - 25 {clmtentotwentyfiv} </label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='26-50' type="checkbox" value="26-50" onClick={(event) => this.claimRange('26-50', event)}></ICheckbox>
                            <label className="form-check-label">26 - 50 {clmtwentyfivtofifty} </label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='51-100' type="checkbox" value="51-100" onClick={(event) => this.claimRange('51-100', event)}></ICheckbox>
                            <label className="form-check-label"> 51 - 100 {clmfiftytohundred}</label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='101-500' type="checkbox" value="101-500" onClick={(event) => this.claimRange('101-500', event)}></ICheckbox>
                            <label className="form-check-label"> 101 - 500 {clmhundredtofivhundred}</label>
                        </div>
                    </Col>


                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <ICheckbox className='form-check-input' name='501-1000' type="checkbox" value="501-1000" onClick={(event) => this.claimRange('501-1000', event)}></ICheckbox>
                            <label className="form-check-label"> 501 and Above  {clmgrtfivhundred} </label>
                        </div>
                    </Col>


                </Row>
                <div className='select-origin border-top mar-t-md pad-t-md'>

                    <Row>
                        <Col>
                            <div className="form-group">
                            <Label className='form-control-label'>Select Origin</Label>

                               {/* <ISelect name="org"  multi={true}  options={displayOrigin} showTextField={true}  searchable={true}  onInputChange={this.originInputChange}/>*/}
                               <Lov name="org" lovTitle="Origin" uppercase={true} isMultiselect={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1&multiselect=Y"/>

                            </div>
                        </Col>
                    </Row>

                </div>

                <div className='select-dest pad-b-md"'>
                    <Row>
                        <Col>
                            <div className="form-group">
                            <Label className='form-control-label'>Select Destination</Label>
                                {/*<ISelect name="dest"  options={displayDestination} showTextField={true} multi={true} searchable={true} onInputChange={this.originInputChange}/>*/}
                                <Lov name="dest" lovTitle="Destination" uppercase={true} isMultiselect={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1&multiselect=Y"/>

                             </div>
                        </Col>
                    </Row>

                            </div>


                <div className='select-subclasses'>
                    <Row>
                        <Col>
                            <div className="form-group custom-typeahead">
                            <Label className='form-control-label'>Select Sub Class</Label>
                                <ISelect name="mailSubclass"  options={mailSubClassCodes} showTextField={true} multi={true} searchable={true} />
                            </div>
                        </Col>
                    </Row>

                </div>
               </div>



                <div className="row pad-t-2sm border-top text-center apply-fliter">
                    <Col>
                        <IButton  category="primary" className="btn  btn-block" onClick={this.bucketSort}>Apply</IButton>
                    </Col>
                </div>


                {
                    this.state.openPopover &&
                    <IPopover isOpen={this.state.openPopover} target={'groupremarks_' + this.state.remarksTarget} toggle={this.closeGroupRemarks} className="icpopover"> >
                      <IPopoverHeader>

                            Remarks
                        </IPopoverHeader>
                        <IPopoverBody>
                            <div className="queue-item pad-sm">
                                <textarea className="textarea" id="groupremarks" style={{ "width":"220px" }}>

                                </textarea>
                                <div >

                                </div>
                            </div>
                            <div className="pad-sm w-100 text-right common-border-color divider-top">
                                        <IButton category="primary" onClick={this.doneGroupRemarks} > <IMessage msgkey="mail.mra.invoicenquiry.button.done" /></IButton>
                                        <IButton category="default" onClick={this.closeGroupRemarks}><IMessage msgkey="mail.mra.invoicenquiry.button.close" /></IButton>
            </div>
                        </IPopoverBody>
                    </IPopover>
                }

            </div>




        );







    }
}
export default wrapForm('extendedFilter')(ExtentedFilterPanel);

