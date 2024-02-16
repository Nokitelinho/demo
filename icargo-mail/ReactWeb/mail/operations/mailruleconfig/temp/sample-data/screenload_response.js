let response = {
    "disableVOtoString": true,
    "status": "success",
    "results": [
        {
            "companyCode": "IBS",
        }
    ]
};
export function getMockedScreenLoadResponse() {
    let screenLoadPromise = new Promise((resolve, reject) => {

        setTimeout(function () {
            const responseClone = { ...response };
            resolve(responseClone); 
        }, 400);
    });
    return screenLoadPromise;
}