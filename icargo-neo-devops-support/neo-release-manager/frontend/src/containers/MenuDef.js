
import {asWebPath} from '../components/Common';

 const appMenu = [
      {
          name:'Maintain Application',
          action:`${asWebPath('/maintain-app')}`,
          disabled: false
      },
      {
          name:'List Applications',
          action:`${asWebPath('/list-app')}`,
          disabled: false
      } 
  ];

const depMenu = [
    //   {
    //       name:'Maintain Packages',
    //       action:`${asWebPath('/maintain-relp/-1')}`,
    //       disabled: false
    //   },
      {
          name:'List Packages',
          action:`${asWebPath('/list-relp')}`,
          disabled: false
      } 
  ];

  const buildMenu = [
      {
          name:'List Builds',
          action:`${asWebPath('/list-build')}`,
          disabled: false
      } 
  ];

   const artFMenu = [
      {
          name:'Maintain Artefact',
          action:`${asWebPath('/maintain-artefact')}`,
          disabled: false
      },         
      {
          name:'List Artefacts',
          action:`${asWebPath('/list-artfs')}`,
          disabled: false
      },         
      {
          name:'List Artefact Releases',
          action:`${asWebPath('/list-artf-releases')}`,
          disabled: false
      }
      
  ];

    const systemMenu = [
        {
            name:'Maintain Tenants',
            action:`${asWebPath('/maintain/tenants')}`,
            disabled: false
        },  
    ]

const menu = [
      {
          name:'Application',
          items:appMenu,
          disabled: false
      } ,
       {
          name:'Release Package',
          items:depMenu,
          disabled: false
      } ,   
      {
          name:'Builds',
          items:buildMenu,
          disabled: false
      } ,   
      {
          name:'Artefacts',
          items:artFMenu,
          disabled: false
      } ,  
     {
          name:'System',
          items:systemMenu,
          disabled: false
      } ,                   
  ];

  export default menu;