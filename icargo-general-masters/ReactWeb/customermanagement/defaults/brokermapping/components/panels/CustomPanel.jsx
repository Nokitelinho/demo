import React,{Component} from "react";
import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {ITextField} from 'icoreact/lib/ico/framework/html/elements';
/**
 * Component for search functionality
 * Common for both AdditinalNamesPopUpPanel and SinglePoaPopUpPanel
 * receives flag as props to determine which panel has been invoked 
 */
class CustomPanel extends Component {
    constructor(props) {
        super(props);
        this.state={
            values:""
      }
    }
    onChange=(e)=>{
        this.setState({value:e.target.value})
        this.props.onSearch({ value: e.target.value, flag: this.props.flag })
    }
    render() {
        return (
            <div className="border-0 card">
                <div className="card-header card-header-action">
                    <div className="col">{this.props.header}</div>
                    <div className="mega-pagination">
                        <div className="form-group m-0 d-inline-flex">
                            <ITextField
                                className="form-control text-transform"
                                type="search"
                                placeholder="Search"
                                onChange={this.onChange}
                                value={this.state.values}
                            />
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}
const mapStateToProps=(state)=>{
    return{
        // values:state.commonReducer.search?state.commonReducer.search:state.commonReducer.addNamSearch.toUpperCase()
     };
};

const mapDispatchToProps=(dispatch)=>{
    return{
        /**
         * call dispatch type based on the flag given A-Additional, S-Single POA
         * which will then renders the corresponding panel
         * @param {*} args 
         */
        onSearch:(args)=>{
            if(args.flag=="S"){
                dispatch({type:"UPDATE_SEARCH",data:args.value})
            }else {
                dispatch({type:"UPDATE_ADDNAM_SEARCH",data:args.value})
            }
        }
    };
};

const CustomConatiner=connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(CustomPanel)

export default CustomConatiner;