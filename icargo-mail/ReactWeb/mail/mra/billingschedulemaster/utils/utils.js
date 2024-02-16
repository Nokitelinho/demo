

export const sortCollection = (sortDetails, collObj) => {

    if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
        collObj.sort((record1, record2) => {
            let sortVal = 0;
            let data1;
            let data2;
            data1 = record1[sortBy] && typeof record1[sortBy] === "object" ? record1[sortBy].systemValue : record1[sortBy];
            data2 = record2[sortBy] && typeof record2[sortBy] === "object" ? record2[sortBy].systemValue : record2[sortBy];

            if (data1 === null) {
                data1 = '';
            }
            if (data2 === null) {
                data2 = '';
            }
            if (data1 > data2) {
                sortVal = 1;
            }
            if (data1 < data2) {
                sortVal = -1;
            }
            if (sortorder === 'DSC') {
                sortVal = sortVal * -1;
            }
            return sortVal;
        });
    }
    return collObj;
}

