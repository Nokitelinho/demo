import React, { Component, Fragment } from 'react';
import { ITable } from 'icoreact/lib/ico/framework/component/common/grid';
import { IColumn, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import ConsignmentDetailsTable from './ConsignmentDetailsTable.jsx';
import { Row, Col } from "reactstrap";
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import ConsignmentContainer from '../containers/consignmentcontainer.js';

export default class ConsignmentPanel extends React.Component {
    render() {
        return (
            <Fragment>
          

                    <ICustomHeaderHolder tableId='mailbagtable' />

                    <div className="card flex-column-custom">

                        <ConsignmentDetailsTable consignmentdetails={this.props.consignmentdetails}
                         selectedConsignmentIndex={this.props.selectedConsignmentIndex} 
                         getNewPage={this.props.onlistBillingDetails} 
                         saveSelectedConsignmentIndex={this.props.saveSelectedConsignmentIndex} 
							selectedConsignments={this.props.selectedConsignments} />
                    </div>
             
            </Fragment>
        );
    }
}
