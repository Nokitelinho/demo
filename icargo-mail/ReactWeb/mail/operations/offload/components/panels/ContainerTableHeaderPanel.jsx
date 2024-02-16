import React from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';

const Aux = (props) => props.children;

export default class ContainerTableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);


    }




    render() {
        return (
            <Aux>
                <Row>
                    <Col><h4>Container Details</h4></Col>
                </Row>
            </Aux>
        );
    }
}
