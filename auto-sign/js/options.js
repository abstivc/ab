var sign_net = localStorage.sign_net;

document.getElementById('sign_net').value = sign_net;
document.getElementById('save').onclick = function () {
    localStorage.sign_net = document.getElementById('sign_net').value;
    signMessage('自动签到地址', {
        body: localStorage.sign_net + '保存成功',
        icon: '../images/icon19.png'
    })
}


var signMessage = function (message, options) {
    if (window.Notification && Notification.permission !== "denied") {
        Notification.requestPermission(function (status) {
            var notice_ = new Notification(message, options)
            notice_.onclick = function () { //单击消息提示框，进入浏览器页面
                notice_.close()
            }
        });
    }
}