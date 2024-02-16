import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Col, Row, Input, Label } from 'reactstrap'
import { IButton, ISelect, IRadio } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import PropTypes from 'prop-types';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'

class AddParameterPopup extends React.PureComponent {

  constructor(props) {
    super(props);
  }

  render() {

    return (
      <Fragment>
        <PopupBody>
          <div className="pad-md">
            <Row>
              <Col xs="8" >
                <div className="form-group">
                  <label className="form-control-label ">
                  Country
                    {/* <IMessage msgkey="mail.mra.defaults.billingschedule.lbl.country" /> */}
                  </label>
                  <Lov name="country" lovTitle="Country" maxlength="4"
                    uppercase={true} dialogWidth="600" dialogHeight="473"
                    actionUrl="ux.showCountry.do?formCount=1"
                    componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                </div>
              </Col>
              <Col xs="16">
                <div className="flex-row pt-3">
                  <IRadio name="includeEnabledCountry" options={[{ 'label': 'Include', 'value': 'I' },
                  { 'label': 'Exclude', 'value': 'E' }]} />
                </div>
              </Col>
            </Row>
            <Row>

              <Col xs="8" >
                <div className="form-group">
                  <label className="form-control-label" >
                  GPA code
                  {/* <IMessage msgkey="mail.mra.defaults.billingschedule.lbl.gpacode" /> */}
                  </label>
                  <Lov name="paCode" lovTitle="GPA Code" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_PACODE" uppercase={true} />
                </div>

              </Col>
              <Col xs="16">
                <div className="flex-row pt-3">
                    <IRadio name="includeEnabledPA" options={[{ 'label': 'Include', 'value': 'I' },
                    { 'label': 'Exclude', 'value': 'E' }]} />
                  </div>
              </Col>
            </Row>
          </div>
        </PopupBody>
        <PopupFooter>
          <IButton category="primary" bType="OK" accesskey="S" onClick={this.props.saveParameter}>OK</IButton>
          <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
        </PopupFooter>
      </Fragment>
    )
  }
}

AddParameterPopup.propTypes = {
  saveMailRuleConfig: PropTypes.func,
  toggleFn: PropTypes.func,
  oneTimeMap: PropTypes.object
}

export default icpopup(wrapForm('addParameterForm')(AddParameterPopup), { title: 'Add Parameter' })

