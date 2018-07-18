function getSongInfo_pie(){
    var mychart = echarts.init(document.getElementById('singerview-pie'));

    $.get('singerinfo').done(function (data) {

        var seriesData = [];

        for(var i=0;i< data.country.length;i++){
            seriesData.push({
                name:data.country[i],
                value:data.song_num[i]
            });
        }

        mychart.setOption({
            title:{
                text:'不同地区歌曲数量统计',
                x:'center'
            },
            tooltip:{
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend:{
                type: 'scroll',
                orient: 'horizontal',
                bottom: 20,
                data: data.country
            },
            series:[
                {
                    name: 'country',
                    type: 'pie',
                    radius : '55%',
                    center: ['40%', '50%'],
                    data: seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        });
    });

}