import React from 'react';
import AddPaymentPopUp from './AddPaymentPopUp.jsx';


export class PopupPanel extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
          
                <AddPaymentPopUp show={this.props.showAddPaymentPopup}
                    closeAddPaymentPopup={this.props.closeAddPaymentPopup}
                    createPaymentPopup={this.props.createPaymentPopup}
                    fromEditBatch={this.props.fromEditBatch}
                    paymentbatchdetail={this.props.paymentbatchdetail}
                    okPaymentPopup={this.props.okPaymentPopup}
                    filterValues={this.props.filterValues}
                    initialValues={this.props.fromEditBatch=='N'?null:this.props.initialValues}
                />
          
        );
    }
}

PopupPanel.propTypes = {}