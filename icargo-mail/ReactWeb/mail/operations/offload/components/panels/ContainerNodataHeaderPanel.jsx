import React from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import ContainerNoDataPanel from './ContainerNoDataPanel.jsx';
const Aux = (props) => props.children;

export default class ContainerNodataHeaderPanel extends React.Component {
    constructor(props) {
        super(props);


    }




    render() {
        return (
            <Aux>
                     <div className="card">
                        <div className="card-header card-header-action">
                        <div className="col">
                            <h4 >Container Details</h4>
                            </div>
                        </div>
                        <div className="card-body p-0">
                               <ContainerNoDataPanel /> 
                            </div>
                            </div>
            </Aux>
        );
    }
}
