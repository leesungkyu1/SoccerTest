const data = {
    labels: [
        '슈팅',
        '패스',
        '어시',
        '태클',
        '경합'
    ],
    datasets: [{
      label: 'My First Dataset',
      data: [45,13,35,45,27],
      fill: true,
      backgroundColor: 'rgba(255, 228, 0, 0.3)',
      borderColor: 'rgb(255, 228, 0)',
    //   pointBackgroundColor: 'rgb(255, 99, 132)',
    //   pointBorderColor: '#fff',
    //   pointHoverBackgroundColor: '#fff',
    //   pointHoverBorderColor: 'rgb(255, 99, 132)',
      pointRadius:0
    }]
  };


const config = {
    type: 'radar',
    data: data,
    options: {
        scale:{
            gridLines:{
                color:['rgba(255,255,255,0.1)','rgba(255,255,255,0.1)','rgba(255,255,255,0.1)','rgba(255,255,255,0.1)','#fff'],
                lineWidth: [1,1,1,1,2]
            },
            angleLines: {
                display:false
            },
            ticks: {
                beginAtZero:true,
                min:0,
                max:100,
                stepSize:10,
                display:false,

            },
            pointLabels: {
                fontSize: 14,
                fontColor:"#fff"
            }
        },
        legend: {
            display:false
        },
        tooltips: {
            enabled:false
        }
    }
  };

  var myChart = new Chart(
    document.getElementById('myChart'),
    config
  );