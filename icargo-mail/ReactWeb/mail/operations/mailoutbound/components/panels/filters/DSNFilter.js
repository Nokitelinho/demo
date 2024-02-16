import React, {  Fragment } from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
class DSNFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
    }   
    render() {
        let category = [];
        // let registeredorinsuredcode = [];
        // let highestnumbermail = []
        // let mailclass = []
        if (!isEmpty(this.props.oneTimeValues)) {
            // registeredorinsuredcode = this.props.mailbagOneTimeValues['mailtracking.defaults.registeredorinsuredcode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            // highestnumbermail = this.props.mailbagOneTimeValues['mailtracking.defaults.highestnumbermail'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            // mailclass = this.props.mailbagOneTimeValues['mailtracking.defaults.mailclass'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));


        }
        return (
            <Fragment>
                <Row>
                    <Col xs="7">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.dsnno" />
                            </Label>
                            <ITextField mode="edit" name="despatchSerialNumber" uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originoe" />
                            </Label> 
                            <Lov name="ooe" lovTitle="Origin OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                          </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destinationoe" />
                            </Label> 
                            <Lov name="doe" uppercase={true} lovTitle="Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="5">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.pacode" />
                            </Label> 
                            <Lov name="paCode" uppercase={true} lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.catgeory" />
                            </Label> 
                            <ISelect name="mailCategoryCode" defaultOption={true} options={category} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                        </div>
                    </Col>
                    <Col xs="5">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.subclass" />
                            </Label> 
                            <Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    {/* <Col xs="3">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.year" />
                            </Label> 
                            <ITextField mode="edit" name="year" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR"></ITextField>
                        </div>
                    </Col> */}
                    <Col xs="10">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.awbno" />
                            </Label> 
                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                        </div>
                    </Col>
                    {/* <Col xs="10">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.conDocno" />
                            </Label>
                            <ITextField mode="edit" name="conDocNo" uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO"></ITextField>
                        </div>
                    </Col> */}
                    {/* <Col xs="auto">
                        <div className="mar-t-md">
                            <ICheckbox label="Routing Info available" name="routingInfo" />
                        </div>
                    </Col>
                    <Col xs="auto">
                        <div className="mar-t-md">
                            <ICheckbox label="PLT" name="plt" />
                        </div>
                    </Col> */}
                </Row>
            </Fragment>
        )
    }
}
DSNFilter.propTypes = {
    mailbagOneTimeValues:PropTypes.object
}

export default wrapForm('dsnFilter')(DSNFilter)
