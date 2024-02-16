import React from 'react';
import { Row, Col } from "reactstrap";

export default class ConsignmentTableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);
        
   }
    render() {
        return (
            <div class="card-header border-bottom-0 p-0">
               <Row>
                   <Col><h4>Consignment Groups </h4></Col>                    
                </Row> 
                </div>
        );
    }
}
