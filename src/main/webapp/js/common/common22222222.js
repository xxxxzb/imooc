//用js模拟一个命名空间: var common={}
// window.common||  验证，防止对第三方js插件有同名命名空间产生污染（同名会直接覆盖掉）
var common=window.common||{};

//
common.showMessage=function(msg){
	if(msg){
		alert(msg);
	}
}