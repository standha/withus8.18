
function radioClick1() {


    var checkButtons = document.getElementsByName("check_radio");
    var value = this.value;
    var checked=[];
    //var parentBox = this.closest(".rdo-box");

    function handleButtonClick1() {

        if (this.checked) {
            updateCheckedArray1(value);
            this.parentNode.parentNode.classList.add("checked");
        } else {
            deselectButton1(this);
            //this.parentNode.parentNode.classList.remove("check_radio");
        }
    }

    function updateCheckedArray1(value) {

        if (!checked.includes(value) && checked.length < 3) {
            checked.push(value);
        }
    }

    function deselectButton1(button) {
        button.checked = false;
        button.parentNode.parentNode.classList.remove("checked");

        var value = button.value;

        var index = checked.indexOf(value);
        if (index > -1) {
            checked.splice(index, 1);
        }
    }
    //
    // }
    //
    // for (var i = 0; i < checkButtons.length; i++) {
    //     checkButtons[i].addEventListener("change", handleButtonClick);
    // }
    //
    // // 배열 초기화
    // checked = [];
    for (var i = 0; i < checkButtons.length; i++) {
        checkButtons[i].addEventListener("change", handleButtonClick1);
    }


}




function onFormSubmission() {

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

        if(goaloption!="none"||goalweek!="none"){
            if(goal<=3){
                const selectedGoal = document.getElementById("selectedGoal");
                selectedGoal.classList.add("goaloptionpop");

                const selectedGoalElement = document.getElementById("selectedGoaloption");
                selectedGoalElement.textContent = goaloption;
                selectedGoalElement.classList.add("highlighted-text"); // 클래스 추가

                const selectedWeekElement = document.getElementById("selectedWeekoption");
                selectedWeekElement.textContent = goalweek;
                selectedWeekElement.classList.add("highlighted-text"); // 클래스 추가

                // HTML에 적은 내용을 업데이트합니다.
                const htmlContent = document.getElementById("htmlContent");
                htmlContent.textContent = "을(를) 주 ";
                htmlContent.classList.add("goaltext"); // 클래스 추가

                const htmlContent1 = document.getElementById("htmlContent1");
                htmlContent1.textContent = " 기록한다";
                htmlContent1.classList.add("goaltext"); // 클래스 추가
            }else {
                alert("목표 설정은 최대 3개까지 가능합니다.");
            }

        }


        //popup();

        const body = {
            goaloption: goaloption,
            goalweek: goalweek,
        };

        const url = "/goal0";
        //const url = form.action;
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
            .then(data => {
                console.log(data)});

    }

    return false;
}

function isEmpty(value){
    if (value.length == 0) {
        return true;
    } else {
        return false;
    }


}