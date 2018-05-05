

function deleteUser() {
    deleteData('dg',ctx+'/user/delUser',queryUsersByParams)
}

function openModifyUserDialog() {
    openModifyDialog('dg','fm','dlg','更新');
}

function saveOrUpdateUser() {
    saveOrUpdateData('fm',ctx+'/user/addOrUpdateUser','dlg',queryUsersByParams);
}


function openAddUserDailog() {
    openAddOrUpdateDlg("dlg","添加用户");
}

function queryUsersByParams() {
    $('#dg').datagrid('load',{
        userName: $('#userName').val(),
        email: $('#email').val(),
        phone: $('#phone').val()
    })
}