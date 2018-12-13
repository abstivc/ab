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

// 必须页面加载完成后执行
$(function () {
    //1. 请求签到
    //2. 成功后发送通知
    $('#notify').click(() => {
        signMessage('自动签到提醒', {
            body: '今日签到已完成',
            icon: '../images/icon19.png'
        })
    })

    $('#option').click(() =>{
        var url = chrome.extension.getURL('../html/options.html')
        chrome.tabs.create({
           url
        });
    });

    $('#open').click(() => {
        var sign_net = localStorage.sign_net;
        if (sign_net) {
          chrome.tabs.create({
              url: sign_net
            });
        } else {
            signMessage('自动签到地址', {
                body: '请先设定地址',
                icon: '../images/icon19.png'
            })
        }
    })
    $('button').mouseover(function() {
        $(this).css('background-color', 'rgb(238,201,0)')
        $(this).css('color', 'white')
    });
    $('button').mouseout(function () {
       $(this).css('background-color', 'rgb(255, 255, 255)')
       $(this).css('color', 'rgb(97, 97, 97)')
    });
});