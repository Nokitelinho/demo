import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import AddMailbagsFieldArray from '../panels/AddMailbagsFieldArray.jsx'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'

const mapStateToProps = (state) => {
 
    const hasAddContainerPanelLoaded= state.containerReducer.hasAddContainerPanelLoaded;
    const initialValues ={addMailbagFieldArray : !isEmpty(state.containerReducer.newContainerDetails) ? state.containerReducer.newContainerDetails[0].mailDetails : []
    }
    return {
        initialValues, target: hasAddContainerPanelLoaded ? 'AddMailbags' : null
    }
}




const AddMailbagContainer = connectContainer(
    mapStateToProps,
    null
)(AddMailbagsFieldArray)

export default AddMailbagContainer