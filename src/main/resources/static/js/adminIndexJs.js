$(function () {
    adminIndexJs.bindEvent();
});
var adminIndexJs = {
    bindEvent: function () {
        laydate.render({
            elem: '#add-shoot'
        });
        laydate.render({
            elem: '#add-issue'
        });
        laydate.render({
            elem: '#edit-shoot'
        });
        laydate.render({
            elem: '#edit-issue'
        });
        adminIndexJs.event.userList();
    },
    event: {
        userList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#user-list").removeClass('layui-hide');
                $("#movie-list").addClass('layui-hide');
                //第一个实例
                table.render({
                    elem: '#user-list-table'
                    , height: 485
                    , url: '../admin/user-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , where: {
                        uid: $("#search-uid").val(),
                        user_name: $("#search-user-name").val()
                    }
                    , cols: [[ //表头
                        {field: 'uid', title: '用户编号', width: 100}
                        , {field: 'user_name', title: '用户名', width: 130}
                        , {field: 'password', title: '密码', width: 130}
                        , {field: 'first', title: '是否第一次登录', width: 200}
                        , {field: 'genres', title: '偏爱的电影类型', width: 350}
                        , {field: 'timestamp', title: '创建时间', width: 180, templet:'<div>{{ layui.util.toDateString(d.timestamp, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                        , {
                            field: 'operate',
                            title: '操作',
                            width: 140,
                            toolbar: "#user-list-table-operate",
                        }
                    ]]
                });
                table.on('tool(user-list-table-fit)', function (obj) {
                    if (obj.event === 'del') {
                        layer.confirm('确定删除该用户？', function (index) {
                            $.ajax({
                                url: '/admin/delete-user',
                                data: {
                                    uid: obj.data.uid
                                },
                                type: 'get',
                                success: function (result) {
                                    if (result.status_code == 200) {
                                        layer.msg("删除成功");
                                        adminIndexJs.event.userList();
                                    }
                                },
                                error: function () {
                                    layer.msg("数据请求异常");
                                }
                            })
                        })
                    } else {
                        $.ajax({
                            url: '/admin/get-user',
                            data: {
                                uid: obj.data.uid
                            },
                            type: 'get',
                            success: function (result) {
                                $("#edit-uid").val(result.vo.uid);
                                $("#edit-user-name").val(result.vo.user_name);
                                $("#edit-password").val(result.vo.password);
                                $("#edit-genres").val(result.vo.genres);
                                layui.use(['layer', 'form'], function (layer, form) {
                                    layer.open({
                                        type: 1
                                        , skin: 'examine-refuse-popup'
                                        , offset: 'auto'
                                        , title: '编辑用户'
                                        , id: 'layer-id'
                                        , area: ['600px', '700px']
                                        , content: $("#edit-user-panel")
                                        , btn: ['确定', '取消']
                                        , shade: 0.5 //不显示遮罩
                                        , end: function () {
                                            $("#edit-user-panel").css("display", "none");
                                        }
                                        , yes: function () {
                                            adminIndexJs.method.updateUserBtn();
                                        },
                                        btn2: function () {

                                        }
                                    });
                                });
                            }
                        })
                    }
                })
            });
        }
    },
    method: {
        movieList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#movie-list").removeClass('layui-hide');
                $("#user-list").addClass('layui-hide');
                //第一个实例
                table.render({
                    elem: '#movie-list-table'
                    , height: 485
                    , url: '../admin/movie-list'
                    , page: true //开启分页
                    , where: {
                        mid: $("#search-mid").val(),
                        name: $("#search-movie-name").val()
                    }
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'mid', title: '电影编号', width: 120}
                        , {field: 'name', title: '名称', width: 120}
                        , {field: 'descri', title: '描述', width: 160}
                        , {field: 'timelong', title: '时长', width: 160}
                        , {field: 'shoot', title: '拍摄时间', width: 160}
                        , {field: 'issue', title: '出版时间', width: 160}
                        , {field: 'language', title: '语言', width: 160}
                        , {field: 'genres', title: '类型', width: 160}
                        , {field: 'director', title: '导演', width: 160}
                        , {field: 'actors', title: '演员', width: 160}
                        , {
                            field: 'operate',
                            title: '操作',
                            width: 140,
                            toolbar: "#movie-list-table-operate",
                        }
                    ]]
                });
                table.on('tool(movie-list-table-fit)', function (obj) {
                    if (obj.event === 'del') {
                        layer.confirm('确定删除该电影？', function (index) {
                            $.ajax({
                                url: '/admin/delete-movie',
                                data: {
                                    mid: obj.data.mid
                                },
                                type: 'get',
                                success: function (result) {
                                    if (result.status_code == 200) {
                                        layer.msg("删除成功");
                                        adminIndexJs.method.movieList();
                                    }
                                },
                                error: function () {
                                    layer.msg("数据请求异常");
                                }
                            })
                        })
                    } else {
                        $.ajax({
                            url: '/admin/get-movie',
                            data: {
                                mid: obj.data.mid
                            },
                            type: 'get',
                            success: function (result) {
                                $("#edit-mid").val(result.vo.mid);
                                $("#edit-movie-name").val(result.vo.name);
                                $("#edit-descri").val(result.vo.descri);
                                $("#edit-timelong").val(result.vo.timelong);
                                $("#edit-shoot").val(result.vo.shoot);
                                $("#edit-issue").val(result.vo.issue);
                                $("#editlanguage").val(result.vo.language);
                                $("#edit-movie-genres").val(result.vo.genres);
                                $("#edit-director").val(result.vo.director);
                                $("#edit-actors").val(result.vo.actors);
                                layui.use(['layer', 'form'], function (layer, form) {
                                    layer.open({
                                        type: 1
                                        , skin: 'examine-refuse-popup'
                                        , offset: 'auto'
                                        , title: '编辑电影'
                                        , id: 'layer-id'
                                        , area: ['600px', '700px']
                                        , content: $("#edit-movie-panel")
                                        , btn: ['确定', '取消']
                                        , shade: 0.5 //不显示遮罩
                                        , end: function () {
                                            $("#edit-movie-panel").css("display", "none");
                                        }
                                        , yes: function () {
                                            adminIndexJs.method.updateMovieBtn();
                                        },
                                        btn2: function () {

                                        }
                                    });
                                });
                            }
                        })
                    }
                })
            });
        },
        updateUserBtn: function () {
            layer.closeAll();
            var data = {};
            data.uid = $("#edit-uid").val();
            data.user_name = $("#edit-user-name").val();
            data.password =$("#edit-password").val();
            data.genres = $("#edit-genres").val();
            $.ajax({
                url: '/admin/update-user',
                data: JSON.stringify(data),
                contentType: 'application/json',
                type: 'post',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg("修改成功");
                        adminIndexJs.event.userList();
                    }
                }
            })
        },
        addUserDialog: function () {
            layui.use('layer', function (layer) {
                layer.open({
                    type: 1,
                    title: '新增用户',
                    shift: 7,
                    area: 'auto',
                    maxWidth: 1000,
                    maxHeight: 800,
                    shadeClose: true,
                    content: $("#add-user-panel"),
                    end: function () {
                        $("#add-user-panel").css("display", "none");
                    }
                });
            });
        },
        addUserBtn: function () {
            layer.close(layer.index);
            if ($("#add-password").val() != $("#add-password-again").val()) {
                layer.msg("两次输入的密码不一致");
                return;
            }
            var data = {};
            data.uid = $("#add-uid").val();
            data.user_name = $("#add-user-name").val();
            data.password = $("#add-password").val();
            data.genres = $("#add-genres").val();
            $.ajax({
                url: '../admin/add-user',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg('添加用户成功');
                        adminIndexJs.event.userList();
                    }
                    layer.closeAll();
                },
                error: function () {
                    layer.msg('数据异常');
                    layer.closeAll();
                }
            })
        },
        addMovieDialog: function () {
            layui.use('layer', function (layer) {
                layer.open({
                    type: 1,
                    title: '新增电影',
                    shift: 7,
                    area: 'auto',
                    maxWidth: 1000,
                    maxHeight: 800,
                    shadeClose: true,
                    content: $("#add-movie-panel"),
                    end: function () {
                        $("#add-movie-panel").css("display", "none");
                    }
                });
            });
        },
        addMovieBtn: function () {
            layer.close(layer.index);
            var data = {};
            data.mid = $("#add-mid").val();
            data.name = $("#add-movie-name").val();
            data.descri = $("#add-descri").val();
            data.timelong = $("#add-timelong").val();
            data.shoot = $("#add-shoot").val();
            data.issue = $("#add-issue").val();
            data.language = $("#add-language").val();
            data.genres = $("#add-movie-genres").val();
            data.director = $("#add-director").val();
            data.actors = $("#add-actors").val();
            $.ajax({
                url: '../admin/add-movie',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg('添加电影成功');
                        adminIndexJs.method.movieList();
                    } else {
                        layer.msg('添加电影失败');
                    }
                    layer.closeAll();
                },
                error: function () {
                    layer.msg('数据异常');
                    layer.closeAll();
                }
            })
        },
        updateMovieBtn: function () {
            layer.closeAll();
            var data = {};
            data.mid = $("#edit-mid").val();
            data.name = $("#edit-movie-name").val();
            data.descri = $("#edit-descri").val();
            data.timelong = $("#edit-timelong").val();
            data.shoot = $("#edit-shoot").val();
            data.issue = $("#edit-issue").val();
            data.language = $("#edit-language").val();
            data.genres = $("#edit-movie-genres").val();
            data.director = $("#edit-director").val();
            data.actors = $("#edit-actors").val();
            $.ajax({
                url: '/admin/update-movie',
                data: JSON.stringify(data),
                contentType: 'application/json',
                type: 'post',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg("修改成功");
                        adminIndexJs.method.movieList();
                    }
                }
            })
        },

    }
}
