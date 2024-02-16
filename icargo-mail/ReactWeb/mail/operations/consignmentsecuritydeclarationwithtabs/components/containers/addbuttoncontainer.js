import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import AddButtonPanel from '../panels/AddButtonPanel.jsx'



const mapStateToProps = (state) => {
    return {
        
       
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        
    }
}

const DetailsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(AddScreeningButtonPanel)

export default DetailsContainer