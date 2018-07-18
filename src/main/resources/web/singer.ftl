<html>
<#assign path="${request.getContextPath()}">
<head>
    <title>歌手统计</title>
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css"  media="all">
    <script src="${path}/static/jquery-3.3.1.min.js"></script>
    <script src="${path}/static/echarts/echarts.js"></script>
</head>

<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>歌手统计</legend>
</fieldset>

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">歌手区域分布情况</div>
                <div class="layui-card-body">
                    <div id="singerview" style="width: 100%;height:600px;"></div>
                </div>
            </div>
        </div>

        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">歌手区域分布情况</div>
                <div class="layui-card-body">
                    <div id="singerview-pie" style="width: 100%;height:600px;"></div>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="${path}\static\echarts\singer-pie.js" charset="utf-8"></script>
<script src="${path}\static\echarts\singer.js" charset="utf-8"></script>
<script src="${path}\static\layui\layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    $(function(){
        getSingersInfo();
        getSongInfo_pie();
    })

</script>
</body>

</html>