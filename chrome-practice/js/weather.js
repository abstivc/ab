function httpRequest(url, callback){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            callback(xhr.responseText);
        }
    }
    xhr.send();
}

function showWeather(result){
    result = JSON.parse(result);
    var list = result.list;
    var table = '<table><tr><th>日期</th><th>天气</th><th>最低温度</th><th>最高温度</th></tr>';
    for(var i in list){
        var d = new Date(list[i].dt*1000);
        table += '<tr>';
        table += '<td>'+d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate()+'</td>';
        table += '<td>'+list[i].weather[0].description+'</td>';
        table += '<td>' + Math.round(list[i].data.yesterday.high) + '</td>';
        table += '<td>' + Math.round(list[i].data.yesterday.low) + '</td>';
        table += '</tr>';
    }
    table += '</table>';
    document.getElementById('weather').innerHTML = table;
}

var city = localStorage.city;
city = city?city:'北京';
var url = 'https://www.sojson.com/open/api/weather/json.shtml?city=' + city + ',china&lang=zh_cn';
httpRequest(url, showWeather);