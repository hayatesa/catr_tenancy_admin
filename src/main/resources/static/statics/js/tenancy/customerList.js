layui.use('table', function () {
        var table = layui.table;
        var tableIns=table.render({
            elem: '#tableDemo'
            , url: '/api/customer/list'
            , height: 537
            , toolbar: '#toolbarDemo'
            , title: '用户数据表'
            , cols: [
                [
                    {field: 'id', title: 'ID', width: 70, fixed: 'left', unresize: true, sort: true}
                    , {field: 'name', title: '用户名', width: 80}
                    , {field: 'gender', title: '性别', width: 65, sort: true,templet: function (res) {
                        if(res.gender=='1') {return "男"}
                        else {return "女"}
                    }}
                    , {field: 'phone', title: '手机', width: 120}
                    , {field: 'email', title: '邮箱', width: 150}
                    , {field: 'tncPoint', title: '积分', width: 65, sort: true, templet:function(res){
                        if(!res.tncPoint) {return 0;}
                        return res.tncPoint.point;
                     }}
                    , {field: 'tncAddress', title: '地址', templet:function (res) {
                        var province = res.tncAddress==null ? "": res.tncAddress.province.name;
                        var city = res.tncAddress==null ? "": res.tncAddress.city.name;
                        var area = res.tncAddress==null ? "": res.tncAddress.area.name;
                        var detail = res.tncAddress==null ? "": res.tncAddress.detail;
                        return  province + city + area +detail;
                    }}
                    , {field: 'emergencyName', title: '紧急联系人', width: 100}
                    , {field: 'emergencyPhone', title: '紧急联系号', width: 120}
                    , {field: 'lastAccessTime', title: '上次登录时间', width: 130,sort: true}
                    , {fixed: 'right', title: '操作', width: 170, templet:function (res) {
                        var a = '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>'+
                        '<a class="layui-btn layui-btn-danger layui-btn-xs"  onclick="deleteCustomer('+res.id+')">删除</a>';
                        if(res.status === 1) {
                            a= a+'<a class="layui-btn layui-btn-normal layui-btn-xs" onclick="disableCustomer('+res.id+')">禁用</a>'
                        }else {
                            a= a+'<a class="layui-btn layui-btn-warm layui-btn-xs" onclick="disabledCustomer('+res.id+')">已禁用</a>'
                        }
                        return a;
                    }
            }]
            ]
            , page: true}
        );
        //工具栏事件
        table.on('tool(customer)', function (obj) {
            switch (obj.event) {
                case 'edit':
                    var data = obj.data;
                    editCustomer(data.id);
                    break;
                case 'search':
                    doSearch();
                    break;
            };
        });

    });
    function doSearch() {
        var searchText = $("#searchText").val();
        if(searchText ===""){
            layer.msg("请输入数据");
            $("#searchText").focus();
            return;
        }
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                search: searchText
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }
    /*禁用客户*/
    var disableCustomer = function(id){
        $.ajax({
            type:"POST",
            url:"/api/customer/disable",
            data:{
                uid:id,
                select:0
            },
            success:function (res) {
                if(res.code == 0){
                    layer.msg("操作成功", {
                        time:1500
                    }, function () {
                        layui.table.reload("tableDemo")
                    })
                }
            }
        })
    }
    /*解禁客户*/
    var disabledCustomer = function(id){
        $.ajax({
            type:"POST",
            url:"/api/customer/disable",
            data:{
                uid:id,
                select:1
            },
            success:function (res) {
                if(res.code == 0){
                    layer.msg("操作成功", {
                        time:1500
                    }, function () {
                        layui.table.reload("tableDemo")
                    })
                }
            }
        })
    }
    //根据Id删除客户
    var deleteCustomer = function(id) {
        $.ajax({
            type: "Post",
            url: "/api/customer/delete",
            data: {
                uid: id,
                select:3
            },
            success: function (res) {
                if (res.code === 0) {
                    layer.msg("删除成功", {
                        time: 1500
                    }, function () {
                        layui.table.reload("tableDemo");
                    })
                }
            },
            fail: function (res) {
                console.log(res);
            }
        })
    }
    //跳转添加页面
    var userList_add = function() {
        var url = './customerInput.html';
        layer.open({
            type: 2 //此处以iframe举例
            , title: '添加客户'
            , area: ['500px', '380px']
            , shade: 0
            , id: "2"
            , anim: 4
            , maxmin: true
            , offset: 'auto'
            , content: url
            , zIndex: layer.zIndex //重点1
            , success: function (layero) {
                layer.setTop(layero); //重点2
            }
        });
    };
    /*编辑用户*/
    var editCustomer = function (id) {
        $(this)[0].location.href = '/tenancy/p/customerEdit?id=' + id;
    }