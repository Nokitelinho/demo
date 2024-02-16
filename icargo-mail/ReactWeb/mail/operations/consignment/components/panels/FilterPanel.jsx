import React, { Fragment } from 'react';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistMaintainConsignment = this.onlistMaintainConsignment.bind(this);
        this.onclearConsignmentDetails = this.onclearConsignmentDetails.bind(this);
    }
    onlistMaintainConsignment() {
        this.props.onlistMaintainConsignment();
    }
    onclearConsignmentDetails() {
        this.props.onclearConsignmentDetails();
    }

    changeScreenMode=(event)=>{
        this.props.onChangeScreenMode(event.target.dataset.mode);
    }
	
	onConsignmentPaste=(event)=>{      
        let pastedText;
        if (window.clipboardData && window.clipboardData.getData) {
           pastedText = window.clipboardData.getData('Text');
        } else if (event.clipboardData && event.clipboardData.getData) {
            pastedText = event.clipboardData.getData('text/plain');
        }     
        let isValid = /^[a-zA-Z0-9_]*$/.test(pastedText);
        if(!isValid){
            event.preventDefault();
            return;
        }
    }
	
	validateConsignmentFormat=(event)=>{
		let value = event.target.value;
		let isValid = /^[a-zA-Z0-9_]*$/.test(value);
		if(!isValid){
            event.preventDefault();
            return;
        }
	}
	
    render() {

        return (
            <Fragment>
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial')  ? ( 
            <div className="header-filter-panel flippane">
            <div className="pad-md pad-b-3xs">
                  
                <Row>
                    <Col xs="7" md="5">
                        <div className="form-group">
                            <label className="form-control-label ">Conc Doc No</label>
                            <ITextField mode="edit" name="conDocNo" type="text" componentId="TXT_MAIL_OPERATIONS_CONSIGNMENT_CONDOCNO"  class="form-control" uppercase={true} onPaste={this.onConsignmentPaste} onChange={this.validateConsignmentFormat}></ITextField>
                        </div>
                    </Col>
                    <Col xs="3" md="2">
                        <div className="form-group">
                            <label className="form-control-label ">PA</label>
                            <Lov name="paCode" lovTitle="Select Postal Administration" componentId="TXT_MAIL_OPERATIONS_CONSIGNMENT_PA" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" uppercase={true} />
                        </div>
                    </Col>
                    <Col>
                        <div className="mar-t-md">
                            <IButton category="primary" bType="LIST" accesskey="L"  componentId="BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_LIST" privilegeCheck={false} onClick={this.onlistMaintainConsignment}>List</IButton>
                            <IButton category="default" bType="CLEAR" accesskey="C" componentId="BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_CLEAR" privilegeCheck={false} onClick={this.onclearConsignmentDetails}>Clear</IButton>
                        </div>
                    </Col>
                </Row>
                {(this.props.screenMode === 'edit') ? (<i onClick={this.changeScreenMode} data-mode="display" className="icon ico-close-fade flipper flipper-ico"></i>) : null}
               </div> </div>):<div><div className="header-summary-panel flippane">
                            <div className="pad-md">
                                <Row>
                                <Col xs="4">  <label className="form-control-label ">Conc Doc No</label>
                                                    <div className="form-control-data">{this.props.initialValues.conDocNo}  </div>
                                                </Col>
                                                <Col xs="4">
                                                <label className="form-control-label ">PA</label>
                                                    <div className="form-control-data">{this.props.initialValues.paCode}  </div>
                                                </Col></Row></div><i onClick={this.changeScreenMode} data-mode="edit" className="icon ico-pencil-rounded-orange flipper flipper-ico"></i></div></div>}
              </Fragment>
        )
    }
}


export default wrapForm('consignmentFilter')(FilterPanel);