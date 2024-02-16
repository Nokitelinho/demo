import React from 'react';
import { Row } from "reactstrap";

export default class NoImgPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (

            <Row>
                <div className="col-8">
                    <div className="card p-0 active-card-pane">
                        <div className="card-header pad-2xs">
                            <h4>Service Failures</h4>
                        </div>


                    </div>
                </div>
                <div className="col-8">
                    <div className="card p-0" >
                        <div className="card-header pad-2xs">
                            <h4>Station on time performance</h4>
                        </div>

                    </div>
                </div>
                <div className="col-8">
                    <div className="card p-0">
                        <div className="card-header pad-2xs">
                            <h4>Force Majeure Mailbags</h4>
                        </div>

                    </div>
                </div>

            </Row>

        );
    }
}
