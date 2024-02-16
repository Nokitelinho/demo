import React, { Component } from 'react'
import PropTypes from 'prop-types';
export default class DetailsTableCustomRowPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            toggleButton: false,
        };
    }


    findAllotments=(props)=>{
       this.props.findAllotment(this.props);
        // this.setState({
        //     toggleButton: true
        // });
    }
    componentDidMount(){
        this.props.findAllotment(this.props);
    }
    componentDidUpdate(prevProps, prevState) {
        if (prevProps.pageCount !== this.props.pageCount ) {
            this.props.findAllotment(this.props);
        }
    }
    
    render() {
        let results = [];
        let mailTransPK=this.props.rowData.carrierCode+'-'+ this.props.rowData.mailBagDestination;

        if (!this.props.capacityDetailsPerRowMap.hasOwnProperty(mailTransPK)) {
               //this.props.findAllotment(this.props);
            results = this.props.capacityDetailsPerRowMap[mailTransPK]; 
        }
        else{
             results = this.props.capacityDetailsPerRowMap[mailTransPK]; 
        }
       
        
        
        return (
            <div>
                
                <div>
                    <div className="pad-2sm more-pane">
                    <div className="form-row next-row" >
                                      <div className="col-6 col-md-6 col-sm-6"> 
                                         <div className="mar-t-2xs"> 
                                            <div className="form-row">
                                                    {/* <div className="col-7 col-md-7 col-sm-6">  */}
        
                                                    <div className="text-gray fs12">Used Capacity : </div>
                                                        <div className="mar-x-2xs text-black">{results&&results.usedCapacityofMailbag?results.usedCapacityofMailbag:"0"}
                                                    </div>
                                                    {/* </div> */}
                                                   
    
                                                     
                    
                                            </div>
                                          </div>
                                        </div>
                    
                                       <div className="col-6 col-md-6 col-sm-6"> 
                                            <div className="mar-t-2xs"> 
                                                <div className="form-row">
                                                    {/* <div className="col-7 col-md-7 col-sm-6">  */}
        
                                                    <div className="text-gray fs12">Alotted : </div>
                                                    <div className="col-auto col-md-auto col-sm-24 p-0">
                                                <div className="pad-r-xs mar-r-2xs border-right d-inline-block text-black">
                                                 {results&&results.allotedULDPostnLDC?results.allotedULDPostnLDC:"0"} LDC
                                                </div>
                                                <div className="pad-r-xs mar-r-2xs border-sm-0 border-right d-inline-block text-black">
                                                {results&&results.allotedULDPostnLDP?results.allotedULDPostnLDP:"0"} LDP 
                                                </div>
                                                <div className="pad-r-xs mar-r-2xs border-sm-0 border-right d-inline-block text-black">
                                                {results&&results.allotedULDPostnMDP?results.allotedULDPostnMDP:"0"} MDP
                                                </div>
                                                 </div>        
                                                </div>
                                            </div>
                                        </div>
                                        <div className="col-6 col-md-6 col-sm-6"> 
                                         <div className="mar-t-2xs"> 
                                            <div className="form-row">
                                                    {/* <div className="col-7 col-md-7 col-sm-6">  */}
        
                                                    <div className="text-gray fs12">Free Sale Capacity : </div>
                                                        <div className="mar-x-2xs text-black"> {results&&results.availableFreeSaleCapacity?results.availableFreeSaleCapacity:"0"}
                                                    </div>
                                                    {/* </div> */}
                                                   
    
                                                     
                    
                                            </div>
                                          </div>
                                        </div>
                                        <div className="col-6 col-md-6 col-sm-6"> 
                                            <div className="mar-t-2xs"> 
                                                <div className="form-row">
                                                    {/* <div className="col-7 col-md-7 col-sm-6">  */}
        
                                                    <div className="text-gray fs12">Free Sale : </div>
                                                    <div className="col-auto col-md-auto col-sm-24 p-0">
                                                <div className="pad-r-xs mar-r-2xs border-right d-inline-block text-black">
                                                 {results&&results.freeSaleULDPostnLDC?results.freeSaleULDPostnLDC:"0"} LDC
                                                </div>
                                                <div className="pad-r-xs mar-r-2xs border-sm-0 border-right d-inline-block text-black">
                                                {results&&results.freeSaleULDPostnLDP?results.freeSaleULDPostnLDP:"0"} LDP 
                                                </div>
                                                <div className="pad-r-xs mar-r-2xs border-sm-0 border-right d-inline-block text-black">
                                                {results&&results.freeSaleULDPostnMDP?results.freeSaleULDPostnMDP:"0"} MDP
                                                </div>
                                                 </div>        
                                                </div>
                                            </div>
                                        </div>
                     </div>
                     </div>
                        

                </div>
                
            </div>
        )
        
    }
}
