






function loadRoleModules(id) {
    $("#roleId").val(id);//储存角色id

    $.ajax({
        url:ctx+"/role/queryAllModulesByRoleId?roleId="+id,
        success:function (data) {
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
        }
    })
}

function zTreeOnCheck(event, treeId, treeNode) {
    var nodes = zTreeObj.getCheckedNodes(true);
    $('#moduleIds').val('');
    if(nodes.length>0){
        var ids="moduleIds=";
        for(var i=0;i<nodes.length;i++){
            var node= nodes[i];
            if(i<=nodes.length-2){
                ids=ids+node.id+"&moduleIds=";
            }else{
                ids=ids+node.id;
            }
        }
        $("#moduleIds").val(ids);
    }

}



function openRelationPermissionDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if (rows==null){
        $.messager.alert("来自crm","选择要关联权限的条目");
        return;
    }
    if (rows.length>1){
        $.messager.alert("来自crm","只能选择一条进行关联");
        return;
    }

    
}

function openAddRoleDailog() {
    openAddOrUpdateDlg('dlg','添加角色');
}
function saveOrUpdateRole() {
    saveOrUpdateData('fm',ctx+'/role/addOrUpdateRole','dlg',queryRolesByParams);
}
function queryRolesByParams() {
    $("#dg").datagrid('load',{
        roleName:$("#roleName").val(),
        createDate:$("#time").datebox('getValue')
    })
    //窗口添加事件
    $("#dlg").dialog({
        onClose:function () {
            $("#fm").form('reset'),
            $("#id").val("")
        }
    })

}
function openModifyRoleDialog() {
   openModifyDialog('dg','fm','dlg','修改角色')
}

function openRelationPermissionDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert('来自crm','请选择一条记录');
        return;
    }
    if (rows.length>1){
        $.messager.alert('来自crm','最多只能选择一条记录')
        return;
    }

    loadRoleModules(rows[0].id);

    openAddOrUpdateDlg('permissionDlg', '权限分配');


}
function doGrant() {
    $.ajax({
        url: ctx +'/permission/savePermission?roleId='+$('#roleId').val()+'&'+$('#moduleIds').val(),
        success:function (data) {
            if(data.code==200){
                $.messager.alert('来自crm',data.msg,'info',function () {
                    // 关闭窗口, 清除roleId和moduleIds
                    closeDlgData('permissionDlg');
                    $('#roleId').val('');
                    $('#moduleIds').val('');
                    queryRolesByParams();
                });
            }else{
                $.messager.alert('来自crm',data.msg);
            }
        }
    })
}

function deleteRole() {
    deleteData('dg',ctx+'/role/deleteRoles',queryRolesByParams);
}