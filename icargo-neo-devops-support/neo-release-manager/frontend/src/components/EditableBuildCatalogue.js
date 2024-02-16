import Box from '@material-ui/core/Box';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import Table from '@material-ui/core/Table';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import BookmarkIcon from '@material-ui/icons/Bookmark';

import moment from 'moment';
import React,{useState,useEffect} from 'react';
import { getFromBEnd,NoListRow,asApiURL } from '../components/Common'; 
import { Tooltip } from '@material-ui/core';




const ArtVersionCell = (props)=>{
    const {editable,version,allArtReleases,artReleasePickedCallBack} = props;
    const [pickedVersion,setPickedVersion] = useState(version);
    const versionPickedCallBack = (version)=> {
       const artRelase =  allArtReleases.find(element => element.artifactVersion === version);
       if(artRelase){
            artReleasePickedCallBack(artRelase);
            setPickedVersion(version); 
       }
    }

    const onVersionPicked = (event,versionPickedCallBack) => {
        const version = event.target.value;
        versionPickedCallBack(version)
    }
    return(
      <React.Fragment>  
        { editable?
            <TableCell align="right">
                <TextField id="artefactVersion" select label="Pick a New Version" value={pickedVersion}
                    onChange={event=> onVersionPicked(event,versionPickedCallBack)}
                        style={{width:'20ch'}} >
                    {allArtReleases?allArtReleases.map((row,idx) => (
                    <MenuItem key={idx} value={row.artifactVersion}>
                        {row.artifactVersion}
                    </MenuItem>
                    )):''}
                </TextField>
            </TableCell>
             :
            <TableCell align="right">{pickedVersion}</TableCell>    
        }
       </React.Fragment> 
    )
}



const ArtefactRow = (props) => {
    const {artefact,artReleasePicked,edited} = props;
    const [editable,setEditable] = useState(false);
    const [selectedArtRelease,setSelectedArtRelease] = useState(artefact);
    const [allArtReleases,setAllArtReleases] = useState([]);

    const onArtefactRowSelect = (artifactId) => {
        getFromBEnd(`/releases/latest?artifactId=${artefact.artifactId}`)
        .then(setAllArtReleases);
        setEditable(!editable);              
    } 
    const artReleasePickedCallBack = (artRelease)=>{
        setSelectedArtRelease(artRelease)
        setEditable(true); 
        artReleasePicked && artReleasePicked(artRelease);
    }
    return (
        <TableRow selected={editable}  onClick={()=>onArtefactRowSelect(selectedArtRelease.artifactId)}>
            <TableCell>
                {edited?
                    <Tooltip title="Edited" placement="left-start">
                        <BookmarkIcon fontSize="small" />
                    </Tooltip>
                    :''
                }
            </TableCell>
            <TableCell component="th" scope="row">
            {selectedArtRelease.artifactId}
            </TableCell>
            <TableCell align="right">{selectedArtRelease.branch}</TableCell>

            <ArtVersionCell editable={editable} version={selectedArtRelease.artifactVersion}
                 allArtReleases={allArtReleases} artReleasePickedCallBack={artReleasePickedCallBack}/> 
            <TableCell align="right">{moment(selectedArtRelease.releaseTime,moment.ISO_8601).format("LLLL")}</TableCell>
            <TableCell align="right">{selectedArtRelease.description}</TableCell>                    
        </TableRow>
    )

}


export default function EditableBuildCatalogue(props) {
  const { artifacts,description,artReleasePickedCallBack } = props;
  const [selectedArts, setSelectedArts] = React.useState([]);
  
  const artReleasePicked=(artRelease)=>{
    if(!selectedArts.includes(artRelease.artifactId)){
        const clone = [...selectedArts]
        clone.push(artRelease.artifactId)
        setSelectedArts(clone) 
    }      

    artReleasePickedCallBack && artReleasePickedCallBack(artRelease)
  }

    return (
        <Box margin={1}>
            <Typography  gutterBottom component="div">
            {description}
            </Typography>
            <Table size="small" aria-label="purchases">
            <TableHead>
                <TableRow>
                    <TableCell/>
                    <TableCell>Artefact Id</TableCell>
                    <TableCell align="right">Branch</TableCell>
                    <TableCell align="right">Version</TableCell>
                    <TableCell align="right">Released Date</TableCell>
                    <TableCell align="right">Commit Desc.</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {artifacts.map(artefact => (
                    <ArtefactRow key={artefact.artifactId} artefact={artefact} artReleasePicked={artReleasePicked} edited={selectedArts.includes(artefact.artifactId)}/>
                ))}
            </TableBody>
            </Table>
        </Box>
    )

}