window.addEventListener('DOMContentLoaded', function () {
    var dateElement = document.getElementById('date');
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return dateElement.textContent = month + '월' + day + '일';

});


function medication_caregiver(form) {
    $("#layerSelectType").show();
    $("#dim").show();
    // $("#buttonCaregiverType").show();
    // $("#buttonRecordType").hide();
    Item = "<span> 보호자에게 복약내용을 알릴까요?</span>";
    $('#popUp2').append(Item);


    $("#buttonOk").click(function () {
        console.log("저장 ok");
        onFormSubmission(form);
        $("#medicationsubmit").hide();
        $("#layerSelectType").hide();
        $("#dim").hide();

        window.location.reload();
        return false;


    });


    $("#buttonNo").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        // $("#buttonCaregiverType").hide();

        window.location.reload();
        return false;
    });

}



function popup(tmp, total_drug_count, completed_drug_count, not_completed_drug_count) {
    $("#medicationsubmit").show();
    $("#dim").show();

    console.log("팝업창 열렸다");

    if (total_drug_count < tmp) {
        if (total_drug_count === 0) {
            Item2 = "<span>기록을 깜빡하셨나요? 내일은 잊지 말고 기록해주세요!</span>";
            $('#popUp3').append(Item2);
            $("#medicationsubmit").show();
            $("#dim").show();
        } else {
            if (completed_drug_count === total_drug_count) {
                Item2 = "<span>남은 약도 잊지 말고 챙겨드세요!</span>";
                $('#popUp3').append(Item2);
                $("#medicationsubmit").show();
                $("#dim").show();
                console.log("남은 약도 잊지 말고 챙겨드세요");
            }
            if (not_completed_drug_count >= 1) {
                Item2 = "<span>약 먹는 것을 깜빡하셨나요? 남은 약은 잊지 말고 챙겨드세요!</span>";
                $('#popUp3').append(Item2);
                $("#medicationsubmit").show();
                $("#dim").show();
                console.log("약 먹는 것을 깜빡하셨나요? 남은 약은 잊지말고 챙겨드세요!!!!!!!");
            }
        }
    }

    if (total_drug_count === tmp) {
        if (completed_drug_count === total_drug_count) {
            Item2 = "<span>오늘도 수고하셨어요! 내일도 잊지 말고 약 챙겨드세요!</span>";
            $('#popUp3').append(Item2);
            $("#medicationsubmit").show();
            $("#dim").show();
            console.log("오늘도 수고하셨어요! 내일도 잊지말고 약 챙겨드세요");
        }
        if (not_completed_drug_count >= 1) {
            Item2 = "<span>약 먹는 것을 깜빡하셨나요? 내일은 잊지 말고 챙겨드세요!</span>";
            $('#popUp3').append(Item2);
            $("#medicationsubmit").show();
            $("#dim").show();
            console.log("약 먹는 것을 깜빡하셨나요? 내일은 잊지말고 챙겨드세요");
        }
    }
    $("#buttonCheck").click(function () {
        $("#medicationsubmit").hide();
        $("#dim").hide();

        window.location.reload();
        return false;
    });

}

//저장버튼 클릭시
function onFormSubmission(form) {

    console.log(form);


    const enable_morning_element = document.getElementById("morningDiv");
    const enable_morning_value = enable_morning_element ? enable_morning_element.getAttribute("value") : "0";
    const enable_morning = enable_morning_value === '1' ? 1 : 0;


    const enable_lunch_element = document.getElementById("lunchDiv");
    const enable_lunch_value = enable_lunch_element ? enable_lunch_element.getAttribute("value") : "0";
    const enable_lunch = enable_lunch_value === '1' ? 1 : 0;

    const enable_dinner_element = document.getElementById("dinnerDiv");
    const enable_dinner_value = enable_dinner_element ? enable_dinner_element.getAttribute("value") : "0";
    const enable_dinner = enable_dinner_value === '1' ? 1 : 0;


    const tmp = enable_morning + enable_lunch + enable_dinner; //활성화된 타임 개수
    console.log("활성화타임개수", tmp);

    const morning = form.querySelector('input[name=morning]:checked')?.value ?? "0";
    const lunch = form.querySelector('input[name=lunch]:checked')?.value ?? "0";
    const dinner = form.querySelector('input[name=dinner]:checked')?.value ?? "0";


    let completed_drug_count = 0;
    let not_completed_drug_count = 0;
    let cnt = 0;
    console.log("if문시작 ");
    //아침 복용여부확인
    if (morning === "y") {
        completed_drug_count++;
    } else if (morning === "n") {
        not_completed_drug_count++;
    } else {
        console.log("break;");
    }

    if (lunch === "y") {
        completed_drug_count++;
    } else if (lunch === "n") {
        not_completed_drug_count++;
    } else {
        console.log("break;");
    }

    if (dinner === "y") {
        completed_drug_count++;
    } else if (dinner === "n") {
        not_completed_drug_count++;
    } else {
        console.log("break;");
    }


    console.log('복용여부 확인해서 변수 저장완료');

    const total_drug_count = completed_drug_count + not_completed_drug_count;
    console.log("복용했습니다", completed_drug_count);
    console.log("복용안했습니다", not_completed_drug_count);
    console.log("기록완료", total_drug_count);

    popup(tmp, total_drug_count, completed_drug_count, not_completed_drug_count);


    const body = {
        alarmOnoffMorning: enable_morning,
        alarmOnoffLunch: enable_lunch,
        alarmOnoffDinner: enable_dinner,
        morning: morning,
        lunch: lunch,
        dinner: dinner,

    }

    const url = form.action;
    console.log("url action");

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
        .then(data => console.log(data));


    return false;
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

