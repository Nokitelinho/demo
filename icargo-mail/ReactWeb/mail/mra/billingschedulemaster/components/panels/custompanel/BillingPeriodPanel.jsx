import React from 'react';
import { Row, Col } from "reactstrap";
import { IDropdownToggle, IDropdownItem, IDropdownMenu, IButtonDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
class BillingPeriodPanel extends React.PureComponent {
  constructor(props) {
    super(props);
  }
  addRow = () => {
    this.props.addRow(this.props.newRowData, this.props.lastRowData);
  }
  onDeleteRow = () => {
    this.props.deleteRow();
  }

  render() {
    return (
      <Row className="align-items-center">
        <Col>
        </Col>
        <Col xs="auto">
          <IButton category="primary" componentId='MRA_BILLING_ADD_ROW' onClick={this.addRow}>
            <IMessage
              msgkey="operations.shipment.ediagreement299.label.list"
              defaultMessage="Add" />
          </IButton>
            <IButton category="default" componentId='MRA_BILLING_DELETE_ROW' onClick={this.onDeleteRow}>

            <IMessage
              msgkey="operations.shipment.ediagreement299.label.clear"
              defaultMessage="Delete" />
          </IButton>
        </Col>
      </Row>
    )
  }
}
BillingPeriodPanel.propTypes = {
  onChangeScreenMode: PropTypes.func,
  submitFilter: PropTypes.func,
  clearFilter: PropTypes.func,
  screenMode: PropTypes.string,

}

export default BillingPeriodPanel

