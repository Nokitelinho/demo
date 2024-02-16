import React from 'react';
import { Row, Col, Label } from "reactstrap";

export default class ExtentedFilterNoActionPanel extends React.Component {

    render() {

        return (
            <div className="filter-pane-right">
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Awaiting INVOIC</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Paid in Full</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Missing Both Scans(not in MRA)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>

                <div className="mar-t-md">
                    <span className="font-bold fs13">Service Failures</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Missing Origin Scan</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Missing Destination Scan</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Late Deliveries</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Del Scan at Wrong Destination</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>

                <div className="mar-t-md">
                    <span className="font-bold fs13">Over Payment</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">In INVOIC,not in MRA</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Waiver</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Rate Difference</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Weight Difference</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Mail Class Difference</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Location Difference</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Service Failure Paid in Full</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>

                <div className="mar-t-md">
                    <span className="font-bold fs13">Claimable</span>
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Zero Pay(MSX)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Not in  INVOIC(NPR)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Incentive Not Paid</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Date Difference(EDT)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>
                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Rate Difference(RVX)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Weight Difference (WXX)</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>
                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">Others</label>
                        </div>
                    </Col>
                    <div className='col-4 d-flex align-items-end'>
                        <a href="">
                            <i className="icon ico-review"></i>
                        </a>
                    </div>

                </Row>

                <div className="border-top border-bottom mar-y-sm pad-y-sm">

                <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <input className='form-check-input' type="checkbox"></input>
                                <label className="form-check-label">Shortpay Accepted</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="">
                                <i className="icon ico-review"></i>
                            </a>
                        </div>

                    </Row>
                    
                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <input className='form-check-input' type="checkbox"></input>
                                <label className="form-check-label">Claim Generated</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="">
                                <i className="icon ico-review"></i>
                            </a>
                        </div>

                    </Row>

                    <Row>
                        <Col xs="20" >
                            <div className='form-check'>
                                <input className='form-check-input' type="checkbox"></input>
                                <label className="form-check-label">Claim Submitted</label>
                            </div>
                        </Col>
                        <div className='col-4 d-flex align-items-end'>
                            <a href="">
                                <i className="icon ico-review"></i>
                            </a>
                        </div>

                    </Row>


                </div>

                <div className="mar-y-md row">
                    <Col>
                        <span className="font-bold fs13">Select Claim Range</span>
                    </Col>
                   {/* <div className="col-auto"> <a href=""><i className="icon ico-arrow-left"></i>Clear</a></div>*/}
                </div>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label"> Below $5 </label>
                        </div>
                    </Col>
                   

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">$5 - $10</label>
                        </div>
                    </Col>
                  

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label"> $10 - $25 </label>
                        </div>
                    </Col>
                   

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label">$25 - $50 </label>
                        </div>
                    </Col>
                 

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label"> $50 - $100 </label>
                        </div>
                    </Col>
                   

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label"> $100 - $500 </label>
                        </div>
                    </Col>
                  

                </Row>

                <Row>
                    <Col xs="20" >
                        <div className='form-check'>
                            <input className='form-check-input' type="checkbox"></input>
                            <label className="form-check-label"> $500 and Above </label>
                        </div>
                    </Col>

                </Row>
                <div className="row pad-t-2sm border-top text-center apply-fliter">
                    <Col>
                        <button className="btn btn-primary btn-block" disabled={true} >Apply</button>
                    </Col>
                </div>

            </div>


        );







    }
}
