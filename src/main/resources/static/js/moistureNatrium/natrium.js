function onFormSubmission(form) {
    let morningElement = 0;
    let morningClass0 = document.getElementById('morning0').className;
    if(morningClass0 == "none active") morningElement = 0;
    let morningClass1 = document.getElementById('morning1').className;
    if(morningClass1 == "row active") morningElement = 1;
    let morningClass2 = document.getElementById('morning2').className;
    if(morningClass2 == "middle active") morningElement = 2;
    let morningClass3 = document.getElementById('morning3').className;
    if(morningClass3 == "high active") morningElement = 3;

    let lunchElement = 0;
    let lunchClass0 = document.getElementById('lunch0').className;
    if(lunchClass0 == "none active") lunchElement = 0;
    let lunchClass1 = document.getElementById('lunch1').className;
    if(lunchClass1 == "row active") lunchElement = 1;
    let lunchClass2 = document.getElementById('lunch2').className;
    if(lunchClass2 == "middle active") lunchElement = 2;
    let lunchClass3 = document.getElementById('lunch3').className;
    if(lunchClass3 == "high active") lunchElement = 3;

    let dinnerElement = 0;
    let dinnerClass0 = document.getElementById('dinner0').className;
    if(dinnerClass0 == "none active") dinnerElement = 0;
    let dinnerClass1 = document.getElementById('dinner1').className;
    if(dinnerClass1 == "row active") dinnerElement = 1;
    let dinnerClass2 = document.getElementById('dinner2').className;
    if(dinnerClass2 == "middle active") dinnerElement = 2;
    let dinnerClass3 = document.getElementById('dinner3').className;
    if(dinnerClass3 == "high active") dinnerElement = 3;
    submit_Click();
    const morning = morningElement;
    const lunch = lunchElement;
    const dinner = dinnerElement;
    const body = {
        morning : morning,
        lunch : lunch,
        dinner : dinner
    };

    const url = form.action;
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
        .then(data => {
            console.log(data);
            if (data.code === 'OK') {
                console.log("입력 성공하였습니다.")
            }  else {
                console.log("기록 실패하였습니다. 관리자에게 문의해주세요.");
                alert("기록 실패하였습니다. 관리자에게 문의해주세요.");
            }
        });

    return false;
}

function showHistory() {
    const element = document.querySelector("#natrium-history-get-action");
    location.href = element.value;
}

function natrium_caregiver() {
    let Item;
    let morningElement = 0;
    let morningClass0 = document.getElementById('morning0').className;
    if(morningClass0 == "none active") morningElement = 0;
    let morningClass1 = document.getElementById('morning1').className;
    if(morningClass1 == "row active") morningElement = 1;
    let morningClass2 = document.getElementById('morning2').className;
    if(morningClass2 == "middle active") morningElement = 2;
    let morningClass3 = document.getElementById('morning3').className;
    if(morningClass3 == "high active") morningElement = 3;

    let lunchElement = 0;
    let lunchClass0 = document.getElementById('lunch0').className;
    if(lunchClass0 == "none active") lunchElement = 0;
    let lunchClass1 = document.getElementById('lunch1').className;
    if(lunchClass1 == "row active") lunchElement = 1;
    let lunchClass2 = document.getElementById('lunch2').className;
    if(lunchClass2 == "middle active") lunchElement = 2;
    let lunchClass3 = document.getElementById('lunch3').className;
    if(lunchClass3 == "high active") lunchElement = 3;

    let dinnerElement = 0;
    let dinnerClass0 = document.getElementById('dinner0').className;
    if(dinnerClass0 == "none active") dinnerElement = 0;
    let dinnerClass1 = document.getElementById('dinner1').className;
    if(dinnerClass1 == "row active") dinnerElement = 1;
    let dinnerClass2 = document.getElementById('dinner2').className;
    if(dinnerClass2 == "middle active") dinnerElement = 2;
    let dinnerClass3 = document.getElementById('dinner3').className;
    if(dinnerClass3 == "high active") dinnerElement = 3;

    const morning = morningElement;
    const lunch = lunchElement;
    const dinner = dinnerElement;

    const body = {
        morning : morning,
        lunch : lunch,
        dinner : dinner
    };
    const url = document.getElementsByTagName("form")[0].action;
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
        .then(data => {
            console.log(data);
            if (data.code === 'OK') {
                console.log("입력 성공하였습니다.")
            }  else {
                console.log("기록 실패하였습니다. 관리자에게 문의해주세요.");
                alert("기록 실패하였습니다. 관리자에게 문의해주세요.");
            }
        });

    $("#layerSelectType").show();
    $("#dim").show();
    $("#buttonCaregiverType").show();
    $("#buttonRecordType").hide();
    Item = "<span> 보호자에게 먹은 식단을 알릴까요?</span>";
    $('#popUp2').append(Item);

    $("#buttonOk").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        $("#buttonCaregiverType").hide();

        window.location.reload();
        return false;
    });

    $("#buttonNo").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        $("#buttonCaregiverType").hide();

        window.location.reload();
        return false;
    });
}

function submit_Click() {

    $("#layerSelectType").show();

    $("#dim").show();
    $("#buttonCaregiverType").hide();
    $("#buttonRecordType").show();
    Item2 = "<span>기록이 완료되었습니다.</span>";
    $('#popUp2').append(Item2);
    $("#buttonCheck").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        $("#buttonRecordType").hide();
        window.location.reload();
        return false;
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // "저장" 버튼에 클릭 이벤트 핸들러를 추가합니다. 버튼은 id="saveButton"으로 선택됩니다.
    const saveButton = document.getElementById("saveButton");
    if (saveButton) {
        saveButton.addEventListener("click", function (event) {
            event.preventDefault(); // 기본 폼 제출 동작을 방지합니다.
        });
    }
});