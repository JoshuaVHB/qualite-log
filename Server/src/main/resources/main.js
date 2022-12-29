const popupFrame = document.getElementById('popup-frame');

function promptError(err) {
    console.error(err);
    popupFrame.hidden = false;
    popupFrame.querySelector('#popup-title').innerText = err.toString();
}

function formDataToObject(formData) {
    let obj = {};
    formData.forEach((value, key) => obj[key] = value);
    return obj;
}

function encodeFormOptions(options = {}) {
    let params = new URLSearchParams();
    for (let key in options) {
        if (options[key] !== null && options[key] !== undefined) {
            if(options[key] == 'on')
                params.append(key, 'true'); // prevent checkbox from being unchecked
            else if(options[key] != 'off')
                params.append(key, options[key].toString());
        }
    }
    return params.toString();
}

function dateToString(date) {
    return date.substr(6, 2) + '/' + date.substr(4, 2) + '/' + date.substr(0, 4);
}