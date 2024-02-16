import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { Row, Col } from "reactstrap";
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'

export default class TabledetailsPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            messagingEnabled: "",

        }
    }
    render() {
        return (
                <div className="card-header">
                    <Row>
                        <Col><h4>Mail Rule List</h4></Col>
                        <Col md={12} sm={14} lg={12} >
                            <ICustomHeaderHolder tableId='mailrulelist' />

                        </Col>

                    </Row>
                </div>
        )
    }


}


TabledetailsPanel.propTypes = {
    onChangeScreenMode: PropTypes.func,

}