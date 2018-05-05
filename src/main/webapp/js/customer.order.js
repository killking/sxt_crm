function formatState(val) {
    if (val==0){
        return "未支付";
    }else if (val==1){
        return "已支付"
    }
}

function formatOpt(val,row) {
    console.log(val);
    return "<a href='javascript:openOrderDetail("+row.id+");'>查看详情</a>"
}
function openOrderDetail(orderId) {
    openAddOrUpdateDlg("dlg","订单详情展示");
    $.ajax({
        url:ctx+"/customerOrder/queryCustomerOrderById",
        data:{id:orderId},
        success:function (data) {
            if (data.code==200){
                $("#fm").form('load',data.result);
            }else{
                $.messager.alert('来自crm',data.msg,"error");
            }
        }
    })
$("#dg2").datagrid('load',{
    orderId:orderId
})

}