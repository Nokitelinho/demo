import React, { Fragment } from 'react';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Container } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'


class DSNTableFilter extends React.PureComponent {
    constructor(props) {
        super(props);
       
    }


    render() {

      
        return (  <Fragment>
        
  
            <Row>
            <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">DSN</label>
               <ITextField mode="edit" name="dsn" type="text" ></ITextField>
           </div>
           </Col>
            <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">OOE</label>
               <ITextField mode="edit" name="ooe" type="text" uppercase={true} ></ITextField>
           </div>
           </Col>
             <Col xs="6">
             <div className="form-group">
                 <label className="form-control-label ">DOE</label>
                 <ITextField mode="edit" name="doe" type="text" uppercase={true} ></ITextField>
             </div>
           </Col>
             <Col xs="6">
             <div className="form-group">
                 <label className="form-control-label ">Mail Class</label>  
                 <ITextField mode="edit" name="mailClass" type="text" uppercase={true} ></ITextField>
             </div>
          </Col>
          <Col xs="6">
             <div className="form-group">
                 <label className="form-control-label ">Sub Class</label>  
                 <ITextField mode="edit" name="subClass" type="text" uppercase={true} ></ITextField>
             </div>
           </Col>


          <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">Container No</label>
               <ITextField mode="edit" name="containerNo" type="text" uppercase={true} ></ITextField>
           </div>
           </Col>
            <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">Consignment Number</label>
               <ITextField mode="edit" name="consignmentNo" type="text" uppercase={true} ></ITextField>
           </div>
           </Col>
             <Col xs="6">
             <div className="form-group">
                 <label className="form-control-label ">POU</label>
                 <ITextField mode="edit" name="pou" type="text" uppercase={true}></ITextField>
             </div>
           </Col>
             <Col xs="6">
             <div className="form-group">
                 <label className="form-control-label ">Destination</label>  
                 <ITextField mode="edit" name="destination" type="text" uppercase={true} ></ITextField>
             </div>
          </Col>

           <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">Accepted Bags</label>
               <ITextField mode="edit" name="acceptedBags" type="text" ></ITextField>
           </div>
           </Col>
            <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">Accepted Weight</label>
               <ITextField mode="edit" name="acceptedWeight" type="text" ></ITextField>
           </div>
           </Col>
            
        
        </Row>
        </Fragment> )

    }
}
export default wrapForm('DSNTableFilter')(DSNTableFilter)