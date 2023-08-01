const ctx = document.getElementById('myChart');

new Chart(ctx, {
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
});

const ctx2 = document.getElementById('myChart2');

new Chart(ctx2, {
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
});