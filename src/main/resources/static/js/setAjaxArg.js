(function(){
$.ajaxSetup({cache:false});
// ,headers: { "Authorization": myToken }
})();  

hookAjax({
    onRequest: (config, handler) => {
        console.log(config)
    },
    open: function (arg) {
        var xxxx = localStorage.getItem("myToken")==null?"":localStorage.getItem("myToken");
        if (arg[1].indexOf('?') != -1) {
            arg[1]+="&xxxx="+xxxx;
        }else {
            arg[1]+="?xxxx="+xxxx;
        }
    },
    onload: function () {

    },
    send:function(arg){

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

function getRootPath() {
    var curPageUrl = window.document.location.href;
    var rootPath = curPageUrl.split("//")[0] + curPageUrl.split("//")[1].split("/")[0]
        + "/" + curPageUrl.split("//")[1].split("/")[1];
    return rootPath;
}
