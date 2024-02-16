import React, { PureComponent, Fragment } from 'react';
import { Field } from 'redux-form';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';

class FilterPanel extends PureComponent {

    constructor(props) {
        super(props);

        this.toggleFilter = this.toggleFilter.bind(this);
        this.onlistConsignment = this.onlistConsignment.bind(this);
        this.onclearDetails = this.onclearDetails.bind(this);
    }

    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    onlistConsignment() {
        this.props.onlistConsignment();
    }

    onclearDetails = () => {
        this.props.onclearDetails();
    }

    render() {

        return (<Fragment>
        {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ?

                   <div className="header-filter-panel flippane position-relative" id="headerForm" >
					 <div className="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label">Con. Doc No.</label>
                                            <ITextField  name ="consignDocNo" value= "consignDocNo" type="text" disabled={this.props.filterList} uppercase={true} ></ITextField>
                                        </div>
                                    </Col>
                                    <Col>
								        <div className="mar-t-md">
                                            <IButton id = "list" category="btn btn-primary flipper" bType="LIST" accesskey="L" flipper="headerData" onClick={this.onlistConsignment} disabled={this.props.filterList && !(this.props.isSaved)}>List</IButton>
                                            <IButton id ="Clear" category="btn btn-default" bType="CLEAR" accesskey="C" onClick={this.onclearDetails}>Clear</IButton>
                                        </div>
							        </Col>
                                </Row>
                        </div>
                            {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                    </div>
                 :( 
                 
                 <div className="header-summary-panel flippane position-relative" id="headerData">
                    <div className="px-3">
                     <Row>
                        <Col xs="6">
                        <div className={this.props.routingInConsignment[0].pou!==null && this.props.routingInConsignment[0].pol!==null?"d-flex border-right flex-column py-3" :"py-3"}>
                            <span className="font-bold fs16 mb-1 text-blue">{this.props.ConsignmentNumber}</span>
								<div className="split-text mb-1">
                                    <span className="separation">{this.props.consignmentDate}</span>
                                    {(this.props.consignmentOrigin.length!==0 && this.props.consigmentDest.length!==0)?
                                    <span className="separation">{this.props.consignmentOrigin}-{this.props.consigmentDest}</span>
                                    :''}
                                    {this.props.category.length!==0?
                                    <span className="separation">Category {this.props.category}</span>
                                    :''}
                                </div>
                                
							<div className="split-text mb-1">
                            {this.props.securityStatusCode.length!==0?
                                <span class="separation">
                                    <span className="pl-0 text-grey">Security Status Code : </span>
                                    <span className="p-0">{this.props.securityStatusCode} </span>
                                </span>:''}
                                <span className="separation"><span className="pl-2 text-grey">PA Code : </span>
                                <span className="p-0">{this.props.paCode} </span>
                                </span>
                            </div>
                        </div>
                        </Col>
                        {this.props.routingInConsignment[0].pou!==null && this.props.routingInConsignment[0].pol!==null?
                        <Col>

							<div className="d-flex flex-column py-3">
                                <span className="font-bold fs14 mb-1">Transit Point Details</span>
							      <div className="split-text mb-1">
                                  {this.props.routingInConsignment.map((value)=>
                                        <span className="separation">
                                            <span className="pl-0 text-grey">{value.pou} : </span>
                                            <span className="pl-0">{value.flightCarrierCode}</span>
                                    </span>
                                  )
                                }
                                </div>
							</div>
                        </Col> :''}
                     </Row>
                 </div>
                 <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleFilter}> </i>
             </div>
            )}
            </Fragment>)

                
    }

}

export default wrapForm('filterPanelForm')(FilterPanel);

FilterPanel.propTypes = {
    onChangeScreenMode: PropTypes.func,
    submitFilter: PropTypes.func,
    clearFilter: PropTypes.func,
    screenMode: PropTypes.string,

}
