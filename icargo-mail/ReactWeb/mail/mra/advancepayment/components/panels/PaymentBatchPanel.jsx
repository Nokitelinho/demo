import React, { Fragment } from 'react';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import PaymentBatchDetailTable from './PaymentBatchDetailTable.jsx';




export default class PaymentBatchPanel extends  React.Component{
  

    render() {
            return (
                <div className="card inner-pannel">
                <ICustomHeaderHolder tableId='paymenttable' />
       
                       <PaymentBatchDetailTable paymentdetails={this.props.paymentdetails}
                       addAttachment={this.props.addAttachment}
                       onAddPayment={this.props.onAddPayment}
                       exportToExcel={this.props.exportToExcel} 
                       getNewPage={this.props.getNewPage} 
                       editBatch={this.props.editBatch}
                       deleteBatch={this.props.deleteBatch}
                       deleteAttachment={this.props.deleteAttachment}
                       filterValues={this.props.filterValues}
                       saveSelectedBatchIndexes={this.props.saveSelectedBatchIndexes}
                       />
                   
                </div>  
            
        )
    }
}