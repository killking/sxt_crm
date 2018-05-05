
//格式化开发模式
function forDevRes(devResult) {
    if(devResult==0){
        return "未开发"
    }else if (devResult==1){
        return "开发中"
    }else if (devResult==2){
        return "开发成功"
    }else if (devResult==3){
        return "开发失败"
    }
}
function formatOp(vla,row) {
    var devResult=row.devResult;
    if (devResult==0||devResult==1){
        return "<a href=\"javascript:openSaleChanceInfoDialog('开发机会数据',"+row.id+")\">开发</a>";
    }else if (devResult==2||devResult==3){
        return "<a href=\"javascript:openSaleChanceInfoDialog('详情机会数据',"+row.id+")\">查看详情</a>";
    }

}
function openSaleChanceInfoDialog(title,sid) {
    window.parent.openTab(title+"_"+sid,ctx+"/cusDev/index/"+sid);
}
function querySaleChanceByParams() {
        $("#dg").datagrid('load',{
            customerName:$("#customerName").val(),
            devResult:$("#devResult").combobox("getValue"),
            createDate:$("#time").datebox("getValue")
    })
}