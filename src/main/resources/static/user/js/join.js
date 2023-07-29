$(document).on('click', '#joinBtn', () => {
    $('#hidUserEmail').val($('#user_email').val())
    $('#hidUserName').val($('#user_name').val())
    $('#hidUserPwd').val($('#user_pwd').val())
})

// $(document).on('click', '#joinBtn', async () => {
//     await axios({
//         method: "post",
//         url: "/user/joinConfirm",
//         data: JSON.stringify({
//             "user": {
//                 "user_email": $('#userEmail').val(),
//                 "user_name": $('#userName').val(),
//                 "user_pwd": $('#userPwd').val()
//             },
//             "term": {
//                 "term_name": "선택약관5",
//                 "term_flag": $('#chk5').val()
//
//             },
//             "term": {
//                 "term_name": "선택약관6",
//                 "term_flag": $('#chk6').val()
//             }
//         }),
//         dataType: "application/json",
//         headers: {'Content-Type': 'application/json'}
//     }).then(res => {
//         location.href='/user/joinConfirm?email=' + res.data;
//     });
// });

// $(document).on('submit', '#joinBtn', async () => {
//     await axios({
//         method: "post",
//         url: "/user/joinConfirm",
//         data: JSON.stringify({
//             "user_email": $('#userEmail').val(),
//             "user_name": $('#userName').val(),
//             "user_pwd": $('#userPwd').val()
//         }),
//         dataType: "application/json",
//         headers: {'Content-Type': 'application/json'}
//     }).then(res => {
//         location.href='/user/joinConfirm?email=' + res.data;
//     })
// });