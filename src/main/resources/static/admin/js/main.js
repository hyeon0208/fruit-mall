let charts = {};


/**
 * new Chart() 생성자를 두 번 호출하면서 오류 발생
 * 차트를 한 번만 생성하는데 문제가 없지만, 차트를 다시 생성하거나 특정 동작에 의해 차트가 업데이트되어야 할 때 문제가 발생
 * 차트 생성 중 다른 차트가 이미 동일한 캔버스를 사용하고 있는 경우 오류 발생
 * 이 이 문제 해결을 위해 createOrUpdateChart 함수에서 차트를 생성하기 전에 일부 확인 작업을 추가
 */


/**
 * reateOrUpdateChart()
 * 캔버스를 재사용하기 전에 기존 차트를 제거(파괴)하는 작업을 수행
 * 다른 작업을 수행하면서 차트가 제대로 업데이트되도록 하고, 여러 차트가 동일한 캔버스를 동시에 사용하는 문제가 발생 방지
 */
function createOrUpdateChart(elementId, chartConfig) {
    if (charts[elementId]) {
        charts[elementId].destroy();
        delete charts[elementId];
    }

    let ctx = $("#" + elementId);
    if(ctx && ctx.length > 0) {
        ctx = ctx[0].getContext('2d');
        charts[elementId] = new Chart(ctx, chartConfig);
    }
}

$(() => {
    const chartConfig1 = {
        type: 'bar',
        data: {
            labels: ['2023. 07. 01', '2023. 07. 02', '2023. 07. 03', '2023. 07. 04', '2023. 07. 05', '2023. 07. 06', '2023. 07. 07'],
            datasets: [
                {
                    label: '일 방문자',
                    data: [100, 207, 190, 150, 130, 250, 150],
                    borderWidth: 2,
                    borderColor: '#36A2EB',
                    backgroundColor: '#9BD0F5',
                    family: "'Noto Sans KR', 'serif'",
                    lineHeight: 1,

                },
                {
                    label: '일 회원가입자',
                    data: [52, 55, 45, 30, 55, 148, 55],
                    borderWidth: 2,
                    borderColor: '#FF6384',
                    backgroundColor: '#FFB1C1',
                    family: "'Noto Sans KR', 'serif'",
                    lineHeight: 1,
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            maintainAspectRatio: false,


        }
    };

    createOrUpdateChart('myChart', chartConfig1);

    const chartConfig2 = {
        type: 'bar',
        // 이전 차트 구성과 동일...
        data: {
            labels: ['2023. 07. 01', '2023. 07. 02', '2023. 07. 03', '2023. 07. 04', '2023. 07. 05', '2023. 07. 06', '2023. 07. 07'],
            datasets: [
                {
                    label: '일 방문자',
                    data: [100, 207, 190, 150, 130, 250, 150],
                    borderWidth: 2,
                    borderColor: '#36A2EB',
                    backgroundColor: '#9BD0F5',
                    family: "'Noto Sans KR', 'serif'",
                    lineHeight: 1,

                },
                {
                    label: '일 회원가입자',
                    data: [52, 55, 45, 30, 55, 148, 55],
                    borderWidth: 2,
                    borderColor: '#FF6384',
                    backgroundColor: '#FFB1C1',
                    family: "'Noto Sans KR', 'serif'",
                    lineHeight: 1,
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            maintainAspectRatio: false,


        }
    };
    createOrUpdateChart('myChart2', chartConfig2);
});