// function getData(){
//     const top = document.getElementById('top').value == null ? '' : document.getElementById('top').value;
//     const mid = document.getElementById('mid').value == null ? '' : document.getElementById('mid').value ;
//     const bottom = document.getElementById('bottom').value == null ? '' : document.getElementById('bottom').value;
//     return {'top_goals':top, 'middle_goals':mid, 'bottom_goals':bottom}
// }
var goalList = {};


function radioClick() {
    var checkButtons = document.getElementsByName("check_radio");
    var checked=[];


    console.log(goalList);
    function updateCheckedArray(value) {

        if (!checked.includes(value) && checked.length < 3) {
            checked.push(value);
        }
    }

    function deselectButton(button) {
        button.checked = false;
        button.parentNode.parentNode.classList.remove("checked");

        var value = button.value;

        var index = checked.indexOf(value);
        if (index > -1) {
            checked.splice(index, 1);
        }
    }

    function handleButtonClick() {

        var value = this.value;


        //기존에 선택되어 있는 것
        var buttons = document.querySelectorAll(".rdo-box.checked");
        buttons.forEach(function(button) {
            var value0 = button.querySelector('input[name="check_radio"]').value;
            // var category0 = button.querySelector('input[name="check_radio"]').getAttribute("data-category");;
            if (button.checked) {
                updateCheckedArray(value0);
            }
            console.log("c", value0);
        });


        if (this.checked) {
            updateCheckedArray( value);
            this.parentNode.parentNode.classList.add("checked");
        } else {
            deselectButton(this);
        }


        var checkedDisabled = checked.length >= 3;


        for (var j = 0; j < checkButtons.length; j++) {
            var button = checkButtons[j];
            button.disabled = checkedDisabled;
        }

        var totalSelected = checked.length;


        if (totalSelected >= 3) {
            for (var j = 0; j < checkButtons.length; j++) {
                if (!checkButtons[j].checked) {
                    checkButtons[j].disabled = true;
                }
            }
        } else {
            for (var j = 0; j < checkButtons.length; j++) {
                checkButtons[j].disabled = false;

            }
        }


        console.log("Selected Goals:", checked);


        for(var i=0;i<checked.length;i++){
            if (goalList.top_goals==null){
                goalList.top_goals=checked;
            }else if(goalList.middle_goals=null){
                goalList.top_goals=checked;
            }else if(goalList.bottom_goals=null){
                goalList.bottom_goals=checked;
            }
        }


    }

    for (var i = 0; i < checkButtons.length; i++) {
        checkButtons[i].addEventListener("change", handleButtonClick);
    }

    // 배열 초기화
    checked = [];

}



// goalList에 있는 값들 중 빈 문자열("") 또는 null인 경우 선택한 값을 대체하는 함수
function replaceEmptyValues(value, goal) {
    if (value !== "" && value !== null) {
        return value;
    }
    return goal;
}

// 선택된 목표 값을 배열에 추가 ("" 또는 null인 경우 선택한 값으로 대체)
function addSelectedGoalValue(button, goalList, checkedgoal) {
    var value = button.querySelector('input[name="check_radio"]').value;
    //var category = button.querySelector('input[name="check_radio"]').getAttribute("data-category");
    var newValue = replaceEmptyValues(value,goalList);
    checkedgoal.push(newValue);
}
function getAjax(){
    var flag = true;
    $.ajax({
        url: "/goalList",
        type: "GET",
        dataType: "json",
        async:false
        ,
        success: function (res) {
            const goal = res["goalList"];
            goalList = {'top_goals':goal.top_goals,'middle_goals':goal.middle_goals,'bottom_goals':goal.bottom_goals };
            flag = false;
        },
        error: (e) => {
            console.log(e);  // 3.
            flag = true;
        }
    });
    return flag;
}


function onFormSubmission() {

    var Item2;
    var checkedgoal = [];
    goalList ={};
    var flag = getAjax();
    console.log(goalList)


/*
    let selectgoalElement = document.getElementById("goalDropdown");
    let goaloption = selectgoalElement.value == null ? null : selectgoalElement.value;
    let selectweekElement = document.getElementById("weekDropdown");
    let goalweek = selectweekElement.value == null ? null : selectweekElement.value;

    let goal=0;

    if(isEmpty(goaloption)==true) {
        alert("목표 항목을 입력해 주세요.");
    }
    else if(isEmpty(goalweek)==true)
    {
        alert("기록을 입력해 주세요.");
    }
    else {
        switch (goaloption) {
            case "복약":
                goaloption = "복약";
                console.log("복약 선택");
                goal+=1;
                break;
            case "혈압/맥박":
                goaloption = "혈압/맥박";
                console.log("혈압/맥박 선택");
                goal+=1;
                break;
            case "운동":
                goaloption = "운동";
                console.log("운동 선택");
                goal+=1;
                break;
            case "증상일지":
                goaloption = "증상일지";
                console.log("증상일지 선택");
                goal+=1;
                break;
            case "염분/수분" :
                goaloption = "염분/수분";
                console.log("염분/수분 해제");
                goal+=1;
                break;
            case "수분":
                goaloption = "수분";
                console.log("수분 선택");
                goal+=1;
                break;
            case "체중":
                goaloption = "체중";
                console.log("체중 선택");
                goal+=1;
                break;
            case "마음일기":
                goaloption = "마음일기";
                console.log("마음일기 선택");
                goal+=1;
                break;
            case "none" :
                //alert("목표종류를 선택해주세요. ");
                console.log("선택 해제");
                break;

        };
        console.log(goaloption);

        switch (goalweek) {
            case "1회 이상":
                goalweek = "1회 이상";
                console.log("1회 이상 선택");
                break;
            case "2회 이상":
                goalweek = "1회 이상";
                console.log("1회 이상 선택");
                break;
            case "3회 이상":
                goalweek = "1회 이상";
                console.log("1회 이상 선택");
                break;
            case "4회 이상":
                goalweek = "1회 이상";
                console.log("1회 이상 선택");
                break;
            case "5회 이상" :
                goalweek = "1회 이상";
                console.log("1회 이상 해제");
                break;
            case "6회 이상":
                goalweek = "1회 이상";
                console.log("1회 이상 선택");
                break;
            case "매일":
                goalweek = "매일";
                console.log("매일 선택");
                break;

            case "none" :
                //alert("기록을 선택해주세요. ");
                console.log("선택 해제");
                break;

        };

        console.log(goalweek);




        //

        if(goaloption!="none"||goalweek!="none"){
            if(goal<=3){
                // const selectedGoal = document.getElementById("selectedGoal");
                // selectedGoal.classList.add("goaloptionpop");

                // const selectedGoalElement = document.getElementById("selectedGoaloption");
                // selectedGoalElement.textContent = goaloption;
                // selectedGoalElement.classList.add("highlighted-text"); // 클래스 추가
                //
                // const selectedWeekElement = document.getElementById("selectedWeekoption");
                // selectedWeekElement.textContent = goalweek;
                // selectedWeekElement.classList.add("highlighted-text"); // 클래스 추가
                //
                // // HTML에 적은 내용을 업데이트합니다.
                // const htmlContent = document.getElementById("htmlContent");
                // htmlContent.textContent = "을(를) 주 ";
                // htmlContent.classList.add("goaltext"); // 클래스 추가
                //
                // const htmlContent1 = document.getElementById("htmlContent1");
                // htmlContent1.textContent = " 기록한다";
                // htmlContent1.classList.add("goaltext"); // 클래스 추가

                // 새로운 표시 요소 생성 및 추가
                const newSelectedOption = document.createElement("div");
                newSelectedOption.textContent = `${goaloption}을(를) 주 ${goalweek} 기록한다`;
                newSelectedOption.classList.add("selected-option");

                const selectedOptionsContainer = document.querySelector(".selected-options-container");
                selectedOptionsContainer.appendChild(newSelectedOption);
               // document.getElementById("selectedOptionsContainer").appendChild(newSelectedOption);

            }else {
                alert("목표 설정은 최대 3개까지 가능합니다.");
            }

        }
*/


    var checked = Array.from(document.querySelectorAll("input[name=check_radio]:checked"))
        .map((button) => button.value);
    console.log("1",checked)

    var selectedButtons = document.querySelectorAll(".rdo-box.checked");

    selectedButtons.forEach(function(button) {
        addSelectedGoalValue(button, goalList, checkedgoal);
    });

    // 합쳐진 값을 보여주기 위한 배열
    var combinedGoals = [];

    // 기존에 선택한 값들을 추가
    if (goalList.top_goals !== null && goalList.top_goals !== "") {
        combinedGoals.push(goalList.top_goals);
    }
    if (goalList.middle_goals !== null && goalList.middle_goals !== "") {
        combinedGoals.push(goalList.middle_goals);
    }
    if (goalList.bottom_goals !== null && goalList.bottom_goals !== "") {
        combinedGoals.push(goalList.bottom_goals);
    }

    console.log(combinedGoals);
   // combinedGoals.push(...checkedgoal);

    // 추가된 부분: 버튼의 상태 변경 시 combinedGoals 업데이트
    var selectedButtons = document.querySelectorAll(".rdo-box");
    selectedButtons.forEach(function (button) {
        var input = button.querySelector('input[name="check_radio"]');
        var value = input.value;

        input.addEventListener("change", function () {
            if (!this.checked && combinedGoals.includes(value)) {
                var index = combinedGoals.indexOf(value);
                if (index > -1) {
                    combinedGoals.splice(index, 1);
                    // 여기에서도 goalpopup을 호출하여 변경된 combinedGoals를 업데이트할 수 있음
                    //  goalpopup(combinedGoals, flag);
                    console.log(combinedGoals);
                }
            } else if (this.checked && !combinedGoals.includes(value)) {
                combinedGoals.push(value);
                console.log(combinedGoals);
                // goalpopup(combinedGoals, flag);
            }
        });
    });


    // 새롭게 선택된 값을 중복 없이 추가
    for (var i = 0; i < checkedgoal.length; i++) {
        if (!combinedGoals.includes(checkedgoal[i])) {
            combinedGoals.push(checkedgoal[i]);
        }
    }


    // 중복 제거
    combinedGoals = [...new Set(combinedGoals)];




    // // 출력 시에 checked CSS가 해제된 값이 combinedGoals 배열에 있다면 제거
    // var selectedButtons = document.querySelectorAll(".rdo-box");
    // selectedButtons.forEach(function (button) {
    //     var value = button.querySelector('input[name="check_radio"]').value;
    //     if (!button.checked && combinedGoals.includes(value)) {
    //         var index = combinedGoals.indexOf(value);
    //         if (index > -1) {
    //             combinedGoals.splice(index, 1);
    //         }
    //     }
    // });

    goalpopup(combinedGoals,flag);

  //  }

    return flag;
}

function goalpopup(combinedGoals,flag) {


    $("#layerSelectType").show();
    $("#dim").show();
    Item = "<span> 목표를 설정하셨군요!</span>";
    $('#popUp1').append(Item);


    for (var i = 0; i < combinedGoals.length; i++) {
        var Item2 = createRadioButton(combinedGoals[i]);
        $('#popUp2').append(Item2);
        console.log("6", Item2);
    }

    $("input[type=radio][name=selected_goal]").change(function () {
        var selectedValue = $(this).val();
        console.log("Selected Value:", selectedValue);
    });



    // // 추가된 부분: 버튼의 상태 변경 시 combinedGoals 업데이트
    // var selectedButtons = document.querySelectorAll(".rdo-box");
    // selectedButtons.forEach(function (button) {
    //     var input = button.querySelector('input[name="check_radio"]');
    //     var value = input.value;
    //
    //     input.addEventListener("change", function () {
    //         if (!this.checked && combinedGoals.includes(value)) {
    //             var index = combinedGoals.indexOf(value);
    //             if (index > -1) {
    //                 combinedGoals.splice(index, 1);
    //                 // 여기에서도 goalpopup을 호출하여 변경된 combinedGoals를 업데이트할 수 있음
    //               //  goalpopup(combinedGoals, flag);
    //             }
    //         } else if (this.checked && !combinedGoals.includes(value)) {
    //             combinedGoals.push(value);
    //            // goalpopup(combinedGoals, flag);
    //         }
    //     });
    // });
    //

    $("#buttonOk").click(function () {
        var selectedRadio = $("input[type=radio][name=selected_goal]:checked");
        let selectedValue = selectedRadio.val();
        console.log("Selected Value11111:", selectedValue);

        let topGoals = combinedGoals[0] == null ? '' : combinedGoals[0];
        let middleGoals = combinedGoals[1] == null ? '' : combinedGoals[1];
        let bottomGoals = combinedGoals[2] == null ? '' : combinedGoals[2];
        let goal = selectedValue == null ? "이번주 목표를 설정해봐요!" : selectedValue;

        const body = {
            goal: goal,
            top_goals: topGoals,
            middle_goals: middleGoals,
            bottom_goals: bottomGoals,
        };

        const url = "/goal1";
        const options = {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        };

        fetch(url, options)
            .then(response => response.json())
            .then(data => console.log(data));

        setTimeout(function () {
            location.href = 'center';
        }, 300);
        return flag;
    });

    $("#buttonNo").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        window.location.reload();
        return flag;
    });
    return flag;
}

function createRadioButton(value) {
    return "<label><span class=\"rdo-mark\"></span> <input type=\"radio\" name=\"selected_goal\"  value=\"" + (value) + "\"> " + (value) + "</label><br>";
}


function isEmpty(value) {
    if (value =="none") {
        return true;
    } else {
        return false;
    }
}
