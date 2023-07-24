window.addEventListener('DOMContentLoaded', function() {
    var dateElement = document.getElementById('date');
    var date = new Date();
    var month = date.getMonth()+1;
    var day = date.getDate();
    return dateElement.textContent = month + '월' + day + '일';

});

// var drug_yes,drug_no = 0;
// var sum_drug = drug_yes + drug_no;
//

// function onFormSubmission(form) {
//     const enable_morning = form.querySelector("input[name=alarmOnoffMorning]");
//     const enable_lunch = form.querySelector("input[name=alarmOnoffLunch]");
//     const enable_dinner = form.querySelector("input[name=alarmOnoffDinner]");
//
//
//     let drug_y = 0; //복용했습니다
//     let drug_n = 0; //복용안했습니다
//
//     //아침 복용여부확인
//     let morningRadio = document.querySelector('input[name=morning]:checked');
//     if (morningRadio && morningRadio.value === '1') {
//         drug_y++;
//     } else {
//         drug_n++;
//     }
//
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
//     }
//
//     let comple_drug = drug_y + drug_n;  //완료한 기록개수
//     let tmp = (enable_morning ? 1 : 0) + (enable_lunch ? 1 : 0) + (enable_dinner ? 1 : 0); //활성화된 타임개수
//
//
//     if (comple_drug < tmp) {
//         if (drug_y === comple_drug) {
//             $("#layerSelectType").show();
//             $("#dim").show();
//             Item2 = "<span>남은 약도 잊지 말고 챙겨드세요!</span>";
//             $('#popUp2').append(Item2);
//
//             console.log("남은 약도 잊지 말고 챙겨드세요");
//         }
//         if (drug_n >= 1) {
//             $("#layerSelectType").show();
//             $("#dim").show();
//             Item2 = "<span>약 먹는 것을 깜빡하셨나요? 남은 약은 잊지 말고 챙겨드세요!</span>";
//             $('#popUp2').append(Item2);
//
//             console.log("약 먹는 것을 깜빡하셨나요? 남은 약은 잊지말고 챙겨드세요");
//
//         }
//     }
//
//     if (comple_drug === tmp) {
//         if (drug_y === comple_drug) {
//             $("#layerSelectType").show();
//             $("#dim").show();
//             Item2 = "<span>오늘도 수고하셨어요! 내일도 잊지 말고 약 챙겨드세요!</span>";
//             $('#popUp2').append(Item2);
//
//             console.log("오늘도 수고하셨어요! 내일도 잊지말고 약 챙겨드세요");
//
//         }
//         if (drug_n >= 1) {
//             $("#layerSelectType").show();
//             $("#dim").show();
//             Item2 = "<span>약 먹는 것을 깜빡하셨나요? 내일을 잊지 말고 챙겨드세요!</span>";
//             $('#popUp2').append(Item2);
//
//             console.log("약 먹는 것을 깜빡하셨나요? 내일을 잊지말고 챙겨드세요");
//
//         }
//     }
//
//
//     const body = {
//         alarmOnoffMorning: enable_morning,
//         alarmOnoffLunch: enabled_lunch,
//         alarmOnoffDinner: enabled_dinner,
//
//     };
//     const url = form.action;
//     const options = {
//         method: "POST",
//         headers: {
//             "Accept": "application/json",
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(body)
//     };
//
// fetch(url, options)
//     .then(response => response.json())
//     .then(data => {
//         console.log(data);
//         if (data.code === 'OK') {
//             alert("입력 완료되었습니다.");
//         }  else {
//             alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
//         }
//     });
//
//
// }


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



function submit_drug() {




    // if (sum_drug==drug_yes) {
    // // 팝업창 " 오늘도 수고하셨어요! 내일도 잊지 말고 약 챙겨드세요!"
    // }
    // else if(sum_drug > drug_yes) {
    //     // "
    // }



}


