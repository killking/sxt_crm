function queryCustomerLossByParams() {
    $("#dg").datagrid('load',{
        cusName:$("#cusName").val(),
        cusNo:$("#cusNo").val(),
        time:$("#time").datebox('getValue'),
    })
}

function formatState(val) {
    if (val==0){
        return '未支付'
    }else if(val==1){
        return '已支付'
    }
}

function formatOp(val,row) {
    console.log(row)
    var  state=row.state;
    if (state==0){
        var href="javascript:openAddRepDataTap("+row.id+")";
        return "<a href="+href+">添加暂缓<a>"
    }else{
        return "确认流失";
    }
}

function openAddRepDataTap(lossId) {
    window.parent.openTab("添加暂缓措施_"+lossId,ctx+"/customerReprieve/index?lossId="+lossId);
}