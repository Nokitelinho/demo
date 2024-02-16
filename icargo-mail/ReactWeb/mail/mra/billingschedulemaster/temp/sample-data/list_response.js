let response = {
    "disableVOtoString": true,
    "status": "success",
    "results": [
        {
            "companyCode": "IBS",
        }
    ]
};
export function getMockedListResponse() {
    let listPromise = new Promise((resolve, reject) => {
        setTimeout(function () {
            const responseClone = { ...response };
            resolve(responseClone); 
        }, 400);
    });
    return listPromise;
}