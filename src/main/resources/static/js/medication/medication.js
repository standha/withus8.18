window.addEventListener('DOMContentLoaded', function () {
    var dateElement = document.getElementById('date');
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return dateElement.textContent = month + '월' + day + '일';

});

// var drug_yes,drug_no = 0;
// var sum_drug = drug_yes + drug_no;
//
function medication_caregiver(form) {
    const enable_morning = form.querySelector("input[name=alarmOnoffMorning]");
    const enable_lunch = form.querySelector("input[name=alarmOnoffLunch]");
    const enable_dinner = form.querySelector("input[name=alarmOnoffDinner]");
    console.log('a');

    $("#layerSelectType").show();
    $("#dim").show();
    Item2 = "<span>보호자에게 증상일지를 알릴까요? </span>";
    $('#popUp2').append(Item2);

    $("#buttonOk").click(function () {
        console.log("저장 ok");

        onFormSubmission(form);
       //  const body = {
       //      alarmOnoffMorning: enable_morning,
       //      alarmOnoffLunch: enable_lunch,
       //      alarmOnoffDinner: enable_dinner,
       //
       // }
       //
       //  const url = form.action;
       //  const options = {
       //      method: "POST",
       //      headers: {
       //          "Accept": "application/json",
       //          "Content-Type": "application/json"
       //      },
       //      body: JSON.stringify(body)
       //  };
       //  fetch(url, options)
       //      .then(response => response.json())
       //      .then(data => console.log(data));

        window.location.reload();
        return false;
    });

    $("#buttonNo").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        window.location.reload();
        // savePreviousWork();
        return false;
    });

}

//저장버튼 클릭시
function onFormSubmission(form) {

    console.log("kkkkkk");

    const enable_morning = form.querySelector("input[name=alarmOnoffMorning]");
    const enable_lunch = form.querySelector("input[name=alarmOnoffLunch]");
    const enable_dinner = form.querySelector("input[name=alarmOnoffDinner]");
    //
    // const enable_morning = enable_morning_element ? enable_morning_element.checked : false;
    // const enable_lunch = enable_lunch_element ? enable_lunch_element.checked : false;
    // const enable_dinner = enable_dinner_element ? enable_dinner_element.checked : false;
    //
    // const isAlarmOnoffMorning = alarmOnoffMorning;



    const morning = form.querySelector('input[name=morning]:checked').value;
    const lunch = form.querySelector('input[name=lunch]:checked').value;
    const dinner =form.querySelector('input[name=dinner]:checked').value;

    // let tmp = parseInt(morning) + parseInt(lunch)+ parseInt(dinner);
    let tmp = (morning ? 1 : 0) + (lunch ? 1 : 0) + (dinner ? 1 : 0); //활성화된 타임개수



    let completed_drug_count = 0; //복용했습니다
    let not_completed_drug_count = 0; //복용안했습니다

    //아침 복용여부확인
    if (morning === 1){
        completed_drug_count+=1;
    }else{
        not_completed_drug_count+=1;
    }
    //점심 복용여부 확인
    if (lunch === 1){
        completed_drug_count+=1;
    }else{
        not_completed_drug_count+=1;
    }
    //저녁 복용여부 확인
    if (dinner === 1){
        completed_drug_count+=1;
    }else {
        not_completed_drug_count+=1;
    }
    let total_drug_count = completed_drug_count + not_completed_drug_count;
    console.log('middddd');

    if (total_drug_count < tmp) {
        if (completed_drug_count === total_drug_count) {
            $("#layerSelectType").show();
            $("#dim").show();
            Item2 = "<span>남은 약도 잊지 말고 챙겨드세요!</span>";
            $('#popUp2').append(Item2);

            console.log("남은 약도 잊지 말고 챙겨드세요");
        }
        if (not_completed_drug_count >= 1) {
            $("#layerSelectType").show();
            $("#dim").show();
            Item2 = "<span>약 먹는 것을 깜빡하셨나요? 남은 약은 잊지 말고 챙겨드세요!</span>";
            $('#popUp2').append(Item2);

            console.log("약 먹는 것을 깜빡하셨나요? 남은 약은 잊지말고 챙겨드세요");

        }
    }

    if (total_drug_count === tmp) {
        if (completed_drug_count === total_drug_count) {
            $("#layerSelectType").show();
            $("#dim").show();
            Item2 = "<span>오늘도 수고하셨어요! 내일도 잊지 말고 약 챙겨드세요!</span>";
            $('#popUp2').append(Item2);

            console.log("오늘도 수고하셨어요! 내일도 잊지말고 약 챙겨드세요");

        }
        if (not_completed_drug_count >= 1) {
            $("#layerSelectType").show();
            $("#dim").show();
            Item2 = "<span>약 먹는 것을 깜빡하셨나요? 내일을 잊지 말고 챙겨드세요!</span>";
            $('#popUp2').append(Item2);

            console.log("약 먹는 것을 깜빡하셨나요? 내일을 잊지말고 챙겨드세요");

        }
    }




    // const sum =

    // let morningRadio = document.querySelector('input[name=morning]:checked');
    // if (morningRadio && morningRadio.value === '1') {
    //     drug_y++;
    // } else {
    //     drug_n++;
    // }

// // 점심 복용 여부 확인
//     let lunchRadio = document.querySelector('input[name=lunch]:checked');
//     if (lunchRadio && lunchRadio.value === '1') {
//         drug_y++;
//     } else {
//         drug_n++;
//     }
//
// // 저녁 복용 여부 확인
//     let dinnerRadio = document.querySelector('input[name=dinner]:checked');
//     if (dinnerRadio && dinnerRadio.value === '1') {
//         drug_y++;
//     } else {
//         drug_n++;
// //     }
//
//     let comple_drug = drug_y + drug_n;  //완료한 기록개수
//     let tmp = (enable_morning ? 1 : 0) + (enable_lunch ? 1 : 0) + (enable_dinner ? 1 : 0); //활성화된 타임개수
//



    const body = {
        alarmOnoffMorning: enable_morning,
        alarmOnoffLunch: enable_lunch,
        alarmOnoffDinner: enable_dinner,
        morning: morning,
        lunch: lunch,
        dinner: dinner,

    };
    const url = form.action;
    console.log("a");
    const options = {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    };
    console.log("저장");

    fetch(url, options)
        .then(response => response.json())
        .then(data => {console.log(data);
                window.location.reload();

            //     alert("입력 완료되었습니다.");
            // }  else {
            //     alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
            // }
        });
    console.log("저장");


}


function medication_Click_morning(div) {
    switch (div.id) {
        case "check_medi1":
            if (document.getElementById('check_medi1').className === "rdo-box") {
                document.getElementById('check_medi1').className = "rdo-box checked";
                document.getElementById('uncheck_medi1').className = "rdo-box";
                document.getElementById("drug1").setAttribute("checked", "");
                document.getElementById("drug2").removeAttribute("checked");
                // drug_yes+=1;
            }
            break;
        case "uncheck_medi1":
            if (document.getElementById('uncheck_medi1').className === "rdo-box") {
                document.getElementById('uncheck_medi1').className = "rdo-box checked2";
                document.getElementById('check_medi1').className = "rdo-box";
                document.getElementById("drug2").setAttribute("checked", "");
                document.getElementById("drug1").removeAttribute("checked");
                // drug_no+=1;
            }
            break;
    }
}

function medication_Click_lunch(div) {
    switch (div.id) {
        case "check_medi2":
            if (document.getElementById('check_medi2').className === "rdo-box") {
                document.getElementById('check_medi2').className = "rdo-box checked";
                document.getElementById('uncheck_medi2').className = "rdo-box";
                document.getElementById("drug3").setAttribute("checked", "");
                document.getElementById("drug4").removeAttribute("checked");
                // drug_yes+=1;
            }
            break;
        case "uncheck_medi2":
            if (document.getElementById('uncheck_medi2').className === "rdo-box") {
                document.getElementById('uncheck_medi2').className = "rdo-box checked2";
                document.getElementById('check_medi2').className = "rdo-box";
                document.getElementById("drug4").setAttribute("checked", "");
                document.getElementById("drug3").removeAttribute("checked");
                // drug_no+=1;

            }
            break;
    }
}

function medication_Click_dinner(div) {
    switch (div.id) {
        case "check_medi3":
            if (document.getElementById('check_medi3').className === "rdo-box") {
                document.getElementById('check_medi3').className = "rdo-box checked";
                document.getElementById('uncheck_medi3').className = "rdo-box";
                document.getElementById("drug5").setAttribute("checked", "");
                document.getElementById("drug6").removeAttribute("checked");
                // drug_yes+=1;
            }
            break;
        case "uncheck_medi3":
            if (document.getElementById('uncheck_medi3').className === "rdo-box") {
                document.getElementById('uncheck_medi3').className = "rdo-box checked2";
                document.getElementById('check_medi3').className = "rdo-box";
                document.getElementById("drug6").setAttribute("checked", "");
                document.getElementById("drug5").removeAttribute("checked");
                // drug_no+=1;

            }
            break;
    }
}
function showHistory() {
    const element = document.querySelector("#pill-history-get-action");
    location.href = element.value;
}


