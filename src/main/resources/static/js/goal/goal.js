// function getData(){
//     const top = document.getElementById('top').value == null ? '' : document.getElementById('top').value;
//     const mid = document.getElementById('mid').value == null ? '' : document.getElementById('mid').value ;
//     const bottom = document.getElementById('bottom').value == null ? '' : document.getElementById('bottom').value;
//     return {'top_goals':top, 'middle_goals':mid, 'bottom_goals':bottom}
// }

const getAjax = function(url) {
    return new Promise((resolve, reject) => { // 1.
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json"
            ,
            success: (res) => {
                resolve(res);  // 2.
            },
            error: (e) => {
                reject(e);  // 3.
            }
        });
    });
}
async function getgoalListData(){
    const url ="/goalList";
    try {
        const goal = await getAjax("/goalList");
        const goalList = goal["goalList"]
        return {'top_goals':goalList.top_goals,'middle_goals':goalList.middle_goals,'bottom_goals':goalList.bottom_goals };
    } catch(e) {
        console.log(e);
    }

}

async function radioClick() {
    var checkButtons = document.getElementsByName("check_radio");
    var checked=[];
    var goalList= await getgoalListData();


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

async function onFormSubmission() {

    var Item2;
    var checkedgoal = [];

    var goalList=  await getgoalListData();
    console.log(goalList.top_goals)

    var checked = Array.from(document.querySelectorAll("input[name=check_radio]:checked"))
        .map((button) => button.value);
    console.log("1",checked)

    var selectedButtons = document.querySelectorAll(".rdo-box.checked");

    selectedButtons.forEach(function(button) {
        addSelectedGoalValue(button, goalList, checkedgoal);
    });

    // 합쳐진 값을 보여주기 위한 배열
    var combinedGoals = [];
    combinedGoals.push(...checkedgoal);


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



    // });

    await goalpopup(combinedGoals);

    return false;
}

async function goalpopup(combinedGoals) {
    $("#layerSelectType").show();
    $("#dim").show();
    Item = "<span> 목표를 설정하셨군요.</span>";
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

    await new Promise((resolve) => {
        $("#buttonOk").click(function () {
            var selectedRadio = $("input[type=radio][name=selected_goal]:checked");
            let selectedValue = selectedRadio.val();
            console.log("Selected Value11111:", selectedValue);

            let topGoals = combinedGoals[0] == null ? '' : combinedGoals[0];
            let middleGoals = combinedGoals[1] == null ? '' : combinedGoals[1];
            let bottomGoals = combinedGoals[2] == null ? '' : combinedGoals[2];
            let goal = selectedValue == null ? "0" : selectedValue;

            const body = {
                goal: goal,
                top_goals: topGoals,
                middle_goals: middleGoals,
                bottom_goals: bottomGoals
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
                resolve(); // Promise 완료 처리
            }, 300);
            return false;
        });

        $("#buttonNo").click(function () {
            $("#layerSelectType").hide();
            $("#dim").hide();
            window.location.reload();
            resolve(); // Promise 완료 처리
            return false;
        });
    });
}

function createRadioButton(value) {
    var radioButton = '<label><input type="radio" name="selected_goal"  value="' + value + '"> ' + value + '</label><br>';
    return radioButton;
}

// 처음 페이지 로딩 시 라디오 버튼 클릭 이벤트 등록
radioClick();
