/*
* 1, 获取值
* 2判断非空
* 3发送ajax
* 4处理回调函数
*
* */
function login() {
    var userName=$("#username").val();
    var userPwd=$("#password").val();
    if (isEmpty(userName)){
        alert("用户名不能为空");
        return;
    }
    if (isEmpty(userPwd)){
        alert("密码不能为空!");
        return;
    }
    $.ajax({
        url:ctx+"/user/login",
        type:"post",
        data:{userName:userName,userPwd:userPwd},
        success:function (data) {
            if (data.code==200){
                var result= data.result;
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                $.cookie("userIdStr",result.userIdStr);
                alert(data.msg)
                window.location.href=ctx+"/main"
            }else {
                alert(data.msg)
            }
        }
    })

}
