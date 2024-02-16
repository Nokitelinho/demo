import React, { Component } from 'react';
import { Row, Col, Container } from "reactstrap";
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import BillingDetailsTable from './BillingDetailsTable.jsx';
import MailbagNoDataPanel from './MailbagNoDataPanel.jsx';
import ConsignmentNoDataPanel from './ConsignmentNoDataPanel.jsx';
import ConsignmentContainer from '../containers/consignmentcontainer.js';
import BillingEntriesContainer from '../containers/billingentriescontainer.js';

export default class DetailsPanel extends  React.Component{

    render() {
      return (
            (this.props.displayMode==='list')?
            <div class="mar-y-md section-panel">
                <Row className="flex-grow-only">
                 <Col sm="6" xs="6" lg="6" className="flex-column-custom">                  
                    <ConsignmentContainer/>                 
                 </Col>
               
                <Col sm="18" xs="18" lg="18" className="flex-column-custom">
                    <BillingEntriesContainer/>
                </Col>                                   
                </Row>                 
            </div>
            :
            <div class="mar-y-md section-panel">
            <div>
          
            </div>
            </div>
        )
    }
}