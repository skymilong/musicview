function getSongWordsInfo() {
    var mychart = echarts.init(document.getElementById("songwords"));
    var maskImage = new Image();
    maskImage.src = "http://localhost:8080/static/img/black.png";
    console.log("start");
    $.get("wordcloud").done(function (data) {

        var seriesData = [];
        for(var key in data){
            var obj = {
                name:key,
                value:data[key]
            };

            seriesData.push(obj);
        }
        console.log(seriesData.length);
        mychart.setOption({
            series: [{
                type: 'wordCloud',

                shape: 'rectangle',

                // A silhouette image which the white area will be excluded from drawing texts.
                // The shape option will continue to apply as the shape of the cloud to grow.

                maskImage: maskImage,

                // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
                // Default to be put in the center and has 75% x 80% size.

                left: 'center',
                top: 'center',
                width: '100%',
                height: '50%',
                right: null,
                bottom: null,

                // Text size range which the value in data will be mapped to.
                // Default to have minimum 12px and maximum 60px size.

                sizeRange: [12, 60],

                // Text rotation range and step in degree. Text will be rotated randomly in range [-90, 90] by rotationStep 45

                rotationRange: [-90, 90],
                rotationStep: 45,

                // size of the grid in pixels for marking the availability of the canvas
                // the larger the grid size, the bigger the gap between words.

                gridSize: 3,

                // set to true to allow word being draw partly outside of the canvas.
                // Allow word bigger than the size of the canvas to be drawn
                drawOutOfBound: false,

                // Global text style
                textStyle: {
                    normal: {
                        fontFamily: 'sans-serif',
                        fontWeight: 'bold',
                        // Color can be a callback function or a color string
                        color: function () {
                            // Random color
                            return 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')';
                        }
                    },
                    emphasis: {
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },

                // Data is an array. Each array item must have name and value property.
                data: seriesData
            }]
        });

    });
}