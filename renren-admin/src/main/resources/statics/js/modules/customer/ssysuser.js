$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'customer/ssysuser/list',
        datatype: "json",
        colModel: [			
			{ label: '开发者ID', name: 'customerNo', index: 'customer_no', width: 50, key: true },
			{ label: '用户类型', name: 'userType', index: 'user_type', width: 80, formatter: function(value, options, row){
                    return value === 1 ?
                        '<span class="label label-danger">个人</span>' :
                        '<span class="label label-success">企业</span>';
                }},
            /*{ label: '公钥', name: 'pubKey', index: 'pub_key', width: 80 },
            { label: '服务状态', name: 'serviceState', index: 'service_state', width: 80 },
            { label: '签章服务状态', name: 'signService', index: 'sign_service', width: 80 },*/
			{ label: '用户状态', name: 'userState', index: 'user_state', width: 80, formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			/*{ label: 'ip地址', name: 'ipAddress', index: 'ip_address', width: 80 },
			{ label: '回调地址', name: 'pushUrl', index: 'push_url', width: 80 }*/
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sSysUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sSysUser = {};
		},
		update: function (event) {
			var customerNo = getSelectedRow();
			if(customerNo == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(customerNo)
		},
		saveOrUpdate: function (event) {
			var url = vm.sSysUser.customerNo == null ? "customer/ssysuser/save" : "customer/ssysuser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sSysUser),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var customerNos = getSelectedRows();
			if(customerNos == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "customer/ssysuser/delete",
                    contentType: "application/json",
				    data: JSON.stringify(customerNos),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(customerNo){
			$.get(baseURL + "customer/ssysuser/info/"+customerNo, function(r){
                vm.sSysUser = r.sSysUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});