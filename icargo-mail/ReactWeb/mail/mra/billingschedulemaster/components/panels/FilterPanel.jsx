import React, { PureComponent, Fragment } from 'react';
import { Field } from 'redux-form';
import { Row, Col, FormGroup, Label } from "reactstrap";
import { ITextField, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ISelect } from 'icoreact/lib/ico/framework/html/elements'

class FilterPanel extends PureComponent {

  constructor(props) {
    super(props);
    this.billingType = [];
    this.numberofBillingPeriod = [];
    let data = this.props.billingDetails;
    this.init();

  }
  init() {
    if (!isEmpty(this.props.oneTimeValues)) {
      this.billingType = this.props.oneTimeValues['mail.mra.masters.billingtype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
      this.numberofBillingPeriod = this.props.oneTimeValues['mail.mra.masters.billingperiods'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
    }
  }
  changeScreenMode = (event) => {
    this.props.onChangeScreenMode((this.props.screenMode === SCREEN_MODE.EDIT) ?
      SCREEN_MODE.DISPLAY : SCREEN_MODE.EDIT);
  }

  onlistbillingdetails = () => {
    this.props.onlistbillingdetails();
  }
  clearFilter = () => {
    this.props.reset();
    this.props.onClearFilter();
  }

  render() {
    let billingType = [];
    let billingPeriod = [];
    let selectedType = "";
    let selectedTypeLabel = "";
    let selectedPeriod = "";
    let selectedPeriodLabel = "";
    if (!isEmpty(this.props.oneTimeValues)) {
      billingType = this.props.oneTimeValues['mail.mra.masters.billingtype'].filter((value) => { if (value.fieldValue === 'G') return value });
      billingPeriod = this.props.oneTimeValues['mail.mra.masters.billingperiods'].filter((value) => { if (value.fieldValue === 'B'|| value.fieldValue === 'M') return value });
      billingType = billingType.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
      billingPeriod = billingPeriod.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
    }
    if (this.props.screenMode === SCREEN_MODE.DISPLAY) {
      if (this.props.filter.billingType && this.props.filter.billingType.length > 0) {
        selectedType = billingType.find((element) => { return element.value === this.props.filter.billingType });
        selectedTypeLabel = selectedType.label;
      }
      if (this.props.filter.billingPeriod && this.props.filter.billingPeriod.length > 0) {
        selectedPeriod = billingPeriod.find((element) => { return element.value === this.props.filter.billingPeriod });
        selectedPeriodLabel = selectedPeriod.label;
      }

    }
    const mainFilter =
      (this.props.screenMode === SCREEN_MODE.EDIT || this.props.screenMode === SCREEN_MODE.INITIAL) ?
        (<Fragment key={1}>

              <div className="pad-md pad-b-3xs">
                <Row>
                  <Col xs="4">
                    <div className="form-group">
                      <Label className="form-control-label">
                        Billing Type
                        </Label>
                      <ISelect name="billingType" options={this.billingType} componentId='billingType' id="billingType" />
                    </div>
                  </Col>
                  <Col xs="2">
                    <div className="form-group">
                      <label className="form-control-label">
                        <IMessage msgkey="Year"
                          defaultMessage="Year" />
                      </label>
                      <ITextField name={'year'} value="year"
                        type="text"
                        componentId="year" maxlength="4"
                      ></ITextField>
                    </div>
                  </Col>
                  <Col xs="4">
                    <div className="form-group">
                      <Label className="form-control-label">
                        No.of Billing Period
                        </Label>
                      <ISelect name="billingPeriod" options={this.numberofBillingPeriod} componentId='numberofBillingPeriod' id="billingType" />
                    </div>
                  </Col>
                </Row>

              </div>
              <div className="btn-row">
                <IButton category="primary" componentId='BILLING_SCHEDULE_LIST' onClick={this.onlistbillingdetails}>
                  <IMessage
                    msgkey="operations.shipment.ediagreement299.label.list"
                    defaultMessage="List" />
                </IButton>
                <IButton category="default" componentId='OPERATIONS_SHIPMENT_EDIAGREEMENT299_CLEAR' onClick={this.clearFilter}>

                  <IMessage
                    msgkey="operations.shipment.ediagreement299.label.clear"
                    defaultMessage="Clear" />
                </IButton>

              </div>
              {(this.props.screenMode === SCREEN_MODE.EDIT) &&
                <i className="icon ico-close-fade flipper flipper-ico" onClick={this.changeScreenMode}></i>}
        </Fragment>)
        :

        (<Fragment>
          <div className="header-summary-panel flippane">
            <div className="pad-md">


              <Row>
                {this.props.filter.billingType && this.props.filter.billingType.length > 0 ?
                  <Col xs="4">
                    <label className="form-control-label "><IMessage defaultMessage="Billing Type" /></label>
                    <div className="form-control-data">{selectedTypeLabel} </div>
                  </Col> : ""}
                {this.props.filter.year && this.props.filter.year.length > 0 ?
                  <Col xs="4">
                    <label className="form-control-label "><IMessage defaultMessage="Year" /></label>
                    <div className="form-control-data"> {this.props.filter.year}</div>
                  </Col> : ""}
                {this.props.filter.billingPeriod && this.props.filter.billingPeriod.length > 0 ?
                  <Col xs="4">
                    <label className="form-control-label "><IMessage defaultMessage="Billing Period" /></label>
                    <div className="form-control-data">{selectedPeriodLabel}</div>
                  </Col> : ""}
              </Row>

              <i className="icon ico-pencil-rounded-orange flipper flipper-ico"
                onClick={this.changeScreenMode}></i>
            </div>
          </div>
        </Fragment>)

    return [mainFilter]
  }

}

export default wrapForm('billingFilter')(FilterPanel);

FilterPanel.propTypes = {
  onChangeScreenMode: PropTypes.func,
  submitFilter: PropTypes.func,
  clearFilter: PropTypes.func,
  screenMode: PropTypes.string,

}
