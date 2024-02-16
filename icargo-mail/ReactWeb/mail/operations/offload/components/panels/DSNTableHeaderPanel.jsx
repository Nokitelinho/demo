import React from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
const Aux = (props) => props.children;

export default class DSNTableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);


    }




    render() {
        return (
            <Aux>
                <Row>
                    <Col><h4>DSN Details</h4></Col>
                </Row>
            </Aux>
        );
    }
}
