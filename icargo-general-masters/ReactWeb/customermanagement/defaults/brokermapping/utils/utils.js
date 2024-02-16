import { isEmpty } from "icoreact/lib/ico/framework/component/util";
export const getFirstSixDetails =(additionalDetails) =>{
    if(additionalDetails)
    {
        const getFirstSixValuesAndJoin=additionalDetails.split("\n").slice(0,6).join("\n");
        return getFirstSixValuesAndJoin;

    }
}
export const getDest = dest=>{
    if(dest)
    {
        let stringDest=dest.slice(0,3).join(",");
        return stringDest;
    }
}
export const getGenericDetails =(Exclude,Include,flag) =>{
    if(Exclude!==null||Include!==null)
    {
        let newE,newI
        if(Exclude.length!==0)
        {
            newE=Exclude.map(function(e){
                return e+" (E)"
            })
        }
        else{newE=[]}
        if(Include.length!==0)
        {
            newI=Include.map(function(i){
                return i+" (I)";
            })
        }
        else{newI=[]}
        if(flag)
        {
        const merge=[...newI,...newE]
        const getThree=merge.slice(0,3);
        const joinDetails=getThree.join(",")
        return joinDetails;
        }
        else{
            const merge=[...newI,...newE]
            return merge;
        }
    }
    else
    {
        return ""
    }
};

export const getLength=(lenght1,length2)=>{
    if(lenght1!==null&&length2!==null)
    {
        const length=lenght1.length+length2.length;
        return length;
    }
    else{
        return 0;
    }
}

export const getFirstNameofAdditionalName =(additionalName) =>{
    if(additionalName){
        const firstName=additionalName.split(";");
        return firstName[0];
    }
};

export const getLengthOfListData =(additionalName) =>{
    if(additionalName){
        const nameArray=additionalName.split(";");
        return nameArray.length;
    }
}

export const convertStringToArray=(string)=>{
    if(string){
        let arry=string.split(";");
        return arry;
    }
}
export function filterBrokerDetails(brokerDetails,brokerfilters)
{
    if(!isEmpty(brokerfilters))
    {   
        let {poaType,brokerCode,brokerName,sccCodeI,sccCodeE,orginInclude,orginExclude,destination,station}=brokerfilters
        return brokerDetails.filter((brokerDetails)=>{
            if(poaType != undefined && !isEmpty(poaType) && !(poaType.includes(brokerDetails.poaType)))
            {
                return false;
            }
            if(brokerCode != undefined && !isEmpty(brokerCode) && !(brokerDetails.agentCode.includes(brokerCode)))
            {
                return false;
            }
            if(brokerName != undefined && !isEmpty(brokerName) && !(brokerDetails.agentName.toUpperCase().includes(brokerName)))
            {
                return false;
            }
            if(sccCodeI != undefined && !isEmpty(sccCodeI) && !(brokerDetails.sccCodeInclude.some(sccI => sccCodeI.split(",").includes(sccI))))
            {
                return false;
            }
            if(sccCodeE != undefined && !isEmpty(sccCodeE) && !(brokerDetails.sccCodeExclude.some(sccE => sccCodeE.split(",").includes(sccE))))
            {
                return false;
            }
            if(orginInclude != undefined && !isEmpty(orginInclude) && !(brokerDetails.orginInclude.some(oI => orginInclude.split(",").includes(oI))))
            {
                return false;
            }
            if(orginExclude != undefined && !isEmpty(orginExclude) && !(brokerDetails.orginExclude.some(oE => orginExclude.split(",").includes(oE))))
            {
                return false;
            }
            if(destination != undefined && !isEmpty(destination) && !(brokerDetails.destination.some(dest => destination.split(",").includes(dest))))
            {
                return false;
            }
            if(station != undefined && !isEmpty(station) && !(station.includes(brokerDetails.station)))
            {
                return false;
            }
            return true;
        });
    }
    else{
        return brokerDetails;
    }
}
export function filterConsigneeDetails(consigneeDetails,consigneefilters)
{
    if(!isEmpty(consigneefilters))
    {   
        let {poaType,consigneeCode,consigneeName,sccCodeI,sccCodeE,orginInclude,orginExclude,destination,station}=consigneefilters
        return consigneeDetails.filter((consigneeDetails)=>{
            if(poaType != undefined && !isEmpty(poaType) && !(poaType.includes(consigneeDetails.poaType)))
            {
                return false;
            }
            if(consigneeCode != undefined && !isEmpty(consigneeCode) && !(consigneeDetails.agentCode.includes(consigneeCode)))
            {
                return false;
            }
            if(consigneeName != undefined && !isEmpty(consigneeName) && !(consigneeDetails.agentName.toUpperCase().includes(consigneeName)))
            {
                return false;
            }
            if(sccCodeI != undefined && !isEmpty(sccCodeI) && !(consigneeDetails.sccCodeInclude.some(sccI => sccCodeI.split(",").includes(sccI))))
            {
                return false;
            }
            if(sccCodeE != undefined && !isEmpty(sccCodeE) && !(consigneeDetails.sccCodeExclude.some(sccE => sccCodeE.split(",").includes(sccE))))
            {
                return false;
            }
            if(orginInclude != undefined && !isEmpty(orginInclude) && !(consigneeDetails.orginInclude.some(oI => orginInclude.split(",").includes(oI))))
            {
                return false;
            }
            if(orginExclude != undefined && !isEmpty(orginExclude) && !(consigneeDetails.orginExclude.some(oE => orginExclude.split(",").includes(oE))))
            {
                return false;
            }
            if(destination != undefined && !isEmpty(destination) && !(consigneeDetails.destination.some(dest => destination.split(",").includes(dest))))
            {
                return false;
            }
            if(station != undefined && !isEmpty(station) && !(station.includes(consigneeDetails.station)))
            {
                return false;
            }
            return true;
        });
    }
    else{
        return consigneeDetails;
    }
}
