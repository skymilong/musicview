function getSingersInfo() {
    var mychart = echarts.init(document.getElementById('singerview'));

    mychart.setOption({
        title:{
            text:"地区分布统计"
        },
        tooltip:{},
        legend:{
            data:['歌手数量','歌曲数量']
        },
        xAxis: {
            data: []
        },
        yAxis:{
            data:[]
        },
        series:[{
            name:'歌手数量',
            type: 'bar',
            data:[]
        },{
            name:'歌曲数量',
            type: 'bar',
            data:[]
        }
        ]
    }) ;
    window.addEventListener('resize', function () {
        mychart.resize();
    });
    // 异步加载数据
    $.get('singerinfo').done(function (data) {
       mychart.setOption({
           xAxis:{
               data:data.country
           },
           series:[{
               name:'歌手数量',
               data:data.singer_num
           }, {
               name: '歌曲数量',
               data: data.song_num
           }
           ]
       });
    });
}