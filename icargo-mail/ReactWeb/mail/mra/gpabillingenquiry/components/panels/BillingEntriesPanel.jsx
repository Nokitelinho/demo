import React, { Component, Fragment } from 'react';
import { Row, Col, Container } from "reactstrap";
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import BillingDetailsTable from './BillingDetailsTable.jsx';
import MailbagNoDataPanel from './MailbagNoDataPanel.jsx';
import ConsignmentNoDataPanel from './ConsignmentNoDataPanel.jsx';
import ConsignmentContainer from '../containers/consignmentcontainer.js';
import BillingEntriesContainer from '../containers/billingentriescontainer.js';

export default class BillingEntriesPanel extends  React.Component{

    render() {
        let length = 0
        length = this.props.selectedMailbagIndex ? this.props.selectedMailbagIndex.length : 0        
      return (
            <Fragment>
             
                    <ICustomHeaderHolder tableId='mailbagtable' />
                  
               <div className="card flex-column-custom">
                    <BillingDetailsTable mailbagsdetails={this.props.mailbagsdetails}
                    mailbagList={this.props.mailbagList}
                    exportToExcel={this.props.exportToExcel} 
                    updateSortVariables={this.props.updateSortVariables}
                    tableFilter={this.props.tableFilter} 
                    getNewPage={this.props.onlistBillingDetails} 
                    selectedMailbagIndex={this.props.selectedMailbagIndex} 
                    initialValues={this.props.initialValues} 
                    saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex}
                    openCompareRow={this.props.openCompareRow}
                    containerRatingPAList={this.props.containerRatingPAList}
                    />
         
                </div>                                                      
            </Fragment>
        )
    }
}