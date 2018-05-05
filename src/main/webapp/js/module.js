function  formateGrade(val) {
    if (val==0){
        return "根级菜单"
    }
    if (val==1){
        return "一级菜单"
    }
    if (val==2){
        return "二级菜单"
    }
}
function queryModulesByParams() {
    $("#dg").datagrid("load",{
        moduleName:$("#moduleName").val(),
        parentId:$("#pid").val(),
        grade:$("#grade").combobox('getValue'),
        optValue:$("#optValue").val()
    })
}
function openAddModuleDailog () {
    
}