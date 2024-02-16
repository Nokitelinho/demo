import React from 'react';
import { Row, Col } from "reactstrap";

export default class TableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);
        
   }
    render() {
        return (
            <div>
               <Row>
                   <Col><h4>Billing Entries</h4></Col>                    
                </Row> 
                </div>
        );
    }
}
