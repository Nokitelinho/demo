
import { getFromBEnd } from './Common'; 

const bomForBuildAndApp = async (tenantId,buildNum,applId) =>{
    var bom = await getFromBEnd(`/builds/${buildNum}/bom`);
    var artefacts = await getFromBEnd(`/tenant/${tenantId}/applications/${applId}`);
    var appBom=[];
 

    if(bom && artefacts){
       appBom =  artefacts.map(art=>{
            return bom.filter(item=>item.artifactId===art.artifactId);
        })
    }
    return appBom;
}

export default bomForBuildAndApp;