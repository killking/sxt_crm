function queryCustomersByParams() {
    $("#dg").datagrid("load",{
        name:$("#cusName").val(),
        khno:$("#khno").val(),
        fr:$("#fr").val()
    })
}
function openAddCustomerDialog() {
    openAddOrUpdateDlg('dlg',"创建客户信息");
}
function saveOrUpdateCustomer() {
    saveOrUpdateData('fm',ctx+"/customer/addOrUpdateCustomer",'dlg',queryCustomersByParams);
}
function openModifyCustomerDialog() {
    openModifyDialog('dg','fm','dlg','修改客户信息')
}
function deleteCustomer() {
    deleteData('dg',ctx+'/customer/deleteCustomer',queryCustomersByParams);
}
function openCustomerOtherInfo(title,type) {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行更新!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行更新!");
        return ;
    }
    var cusId=rows[0].id;
    if(type==3){
        window.parent.openTab(title,ctx+"/customerOrder/index?cid="+cusId);
    }
}
