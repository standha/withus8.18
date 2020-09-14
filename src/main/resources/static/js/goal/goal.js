function radioClick(){
    var checklength = document.getElementsByName("check_radio").length;
    var checked;
    var goalNum;

    for(var i=0; i<checklength; i++){
        if(document.getElementsByName("check_radio")[i].checked == true){
            checked = document.getElementsByName("check_radio")[i].value;
            goalNum = i+1;
        }
    }

    switch(goalNum){
        case 1:
            checked = "'" + checked +
                "'으로 목표를 설정하셨군요.\n" +
                "매일 심장약을 복용하신 후\n" +
                "[위더스->알람->약물]에 기록해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요";
            break;

        case 2:
            checked = "'" + checked +
                "'으로 목표를 설정하셨군요.\n" +
                "매일 정해진 시간에 측정한 뒤\n" +
                "[위더스->혈압/맥박]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 3:
            checked = "'" + checked +
                "'으로 목표를 설정하셨군요.\n" +
                "매일 정해진 시간에 측정한 뒤\n" +
                "[위더스->체중]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 4:
            checked = "'" + checked +
                "'으로 목표를 설정하셨군요.\n" +
                "하루동안 증상이 있었는지 생각하며\n" +
                "[위더스->증상일지]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 5:
            checked = "'" + checked +
                "'으로 목표를 설정하셨군요.\n" +
                "하루동안 증상이 있었는지 생각하며\n" +
                "[위더스->증상일지]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 6:
            checked = "'" + checked +
                "'을 설정하셨군요.\n" +
                "식사 시 염도계로 음식의 소금양을 측정하고\n" +
                "[위더스->염분/수분]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 7:
            checked = "'" + checked +
                "'을 목표로 설정하셨군요.\n" +
                "매일 식사 시 염도계로 음식의 소금양을 측정하고\n" +
                "[위더스->염분/수분]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 8:
            checked = "'" + checked +
                "'을 목표를 설정하셨군요.\n" +
                "운동한 후 [위더스->운동]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;

        case 9:
            checked = "'" + checked +
                "'을 목표를 설정하셨군요.\n" +
                "운동한 후 [위더스->운동]에 입력해주세요.\n" +
                "목표를 변경하고 싶으면 '수정'버튼을 눌러주세요"
            break;
    }

    if(confirm(checked)==true){
        console.log(goalNum + "번 목표 설정");

        const body = {
            goal: goalNum
        };

        const url = '';
        const options = {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        };

        fetch(url, options)
            .then(response => response.json())
            .then(data =>console.log(data));

        history.back();
    }
    else{
        console.log("목표 설정 취소");
        window.location.reload();
    }

    return false;
}

// function popTest(){
//     var popUrl = "poptest.html";
//     var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";
//     window.open(popUrl,"", popOption);
//
// }