

import React from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import DSNNoDataPanel from './DSNNoDataPanel.jsx';
const Aux = (props) => props.children;

export default class DSNNodataHeaderPanel extends React.Component {
    constructor(props) {
        super(props);


    }


    render() {
        return (
            <Aux>



                     <div className="card">
                    <div className="card-header card-header-action">
                    <div className="col">
                        <h4 >DSN Details</h4>
                    </div>
                     </div>
                     <div className="card-body p-0">
                        <DSNNoDataPanel/>  
                        </div>
                     </div>
               
            </Aux>
        );
    }
}
