function httpRequest(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            console.log(xhr.responseText)
            callback(xhr.responseText);
        }
    }
    xhr.send();
}

httpRequest('http://2018.ip138.com/ic.asp', function (ip) {
    document.getElementById('ip_div').outerHTML = ip;
});