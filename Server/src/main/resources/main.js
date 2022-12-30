const popupFrame = document.getElementById('popup-frame');
const popupContent = document.getElementById('popup-message');

function popupValid(string) {
    popupFrame.hidden = false;
    popupFrame.querySelector('#popup-title').innerText = string;
    popupFrame.querySelector('button').setAttribute("onClick", "window.location.href = '/accueil'");
}

function promptError(err, message=null) {
    console.error(err);
    popupFrame.hidden = false;
    if(popupContent && message) popupContent.innerHTML = message;
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
            else if(options[key] == 'off')
                params.append(key, 'false');
            else
                params.append(key, options[key].toString());
        }
    }
    return params.toString();
}

function dateToString(date) {
    return date.substr(8, 2) + '/' + date.substr(5, 2) + '/' + date.substr(0, 4);
}