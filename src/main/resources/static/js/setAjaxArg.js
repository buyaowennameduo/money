(function(){
$.ajaxSetup({cache:false,beforeSend:commonBeforeSend});
})();
hookAjax({
    open: function (arg) {
        if (arg[1].indexOf('?') != -1) {
            arg[1]+="&xxxx=";
        }else {
            arg[1]+="?xxxx=";
        }
    },
    onreadystatechange: function (arguments) {
        var xhr = arguments.xhr;
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                var res = "";
                try {
                    res = JSON.parse(xhr.response);
                    // if (res.count == "99") {
                    //      if (window.top) window.top.location = "login.html";
                    //       else window.location = "login.html";
                    // }
                } catch (arg) {

                } finally {

                }
            }
        }
    }
})
function commonBeforeSend(r){
    var myToken = localStorage.getItem("myToken")==null?"":localStorage.getItem("myToken");
    r.setRequestHeader('Authorization',myToken);
}

function divLoadPage(url){
    commonLoadPage(url,1,"commonContent");
}
function openNewPage(url){
    commonLoadPage(url,0,"");
}
function commonLoadPage(url,openType,divId){
    var myToken = localStorage.getItem("myToken")==null?"":localStorage.getItem("myToken");
    if (url.indexOf('?') != -1) {
        url+="&xxxx="+myToken;
    }else {
        url+="?xxxx="+myToken;
    }
    if (openType==0){
        window.location.href = url;
    } else {
        $("#"+divId).load(url);
    }

}
