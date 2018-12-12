// 后台执行的js
function httpRequest(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            callback(true);
        }
    }
    xhr.onerror = function () {
        callback(false);
    }
    xhr.send();
}

setInterval(function () {
    httpRequest('http://www.baidu.con/', function (status) {
        chrome.browserAction.setIcon({
            path: 'images/' + (status ? 'online.png' : 'offline.png')
        });
    });
}, 5000);