// 页面右下角通知
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
var sign_net = localStorage.sign_net;
//1. 请求签到
//2. 成功后发送通知
signMessage('自动签到提醒', {
    body: '今日签到[' + sign_net + ']已完成',
    icon: '../images/icon19.png'
})