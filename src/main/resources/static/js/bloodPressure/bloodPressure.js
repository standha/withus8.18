function onFormSubmission(form) {
    if(form.querySelector("input[name=contraction]").value==0 ||
        form.querySelector("input[name=pressure]").value==0 ||
        form.querySelector("input[name=relaxation]").value==0 )
    {
        alert("모든 데이터를 입력해주세요.");
    }
    else{
         contraction = form.querySelector("input[name=contraction]").value;
         relaxation = form.querySelector("input[name=relaxation]").value;
         pressure = form.querySelector("input[name=pressure]").value;
    }
    console.log("C : "+contraction);
    const body = {
        contraction: contraction,
        relaxation: relaxation,
        pressure: pressure
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
                alert("입력 완료되었습니다.");
            }  else {
                alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
            }
        });

    return false;
}
function showHistory() {
    const element = document.querySelector("#bloodPressure-history-get-action");
    location.href = element.value;
}
function bloodPressure_caregiver() {
    var Item;
    if(inputCheck){
        $("#layerSelectType").show();
        $("#dim").show();
        $("#buttonCaregiverType").show();
        $("#buttonRecordType").hide();
        Item = "<span> 보호자에게 혈압/맥박을 알릴까요?</span>";
        $('#popUp2').append(Item);


        $("#buttonOk").click(function () {
            $("#layerSelectType").hide();
            $("#dim").hide();
            $("#buttonCaregiverType").hide();
            if(document.getElementsByTagName("form")[0].querySelector("input[name=contraction]").value == 0 ||
                document.getElementsByTagName("form")[0].querySelector("input[name=relaxation]").value == 0 ||
                document.getElementsByTagName("form")[0].querySelector("input[name=pressure]").value == 0){
            } else {
                const body = {
                    contraction: document.getElementsByTagName("form")[0].querySelector("input[name=contraction]").value,
                    relaxation: document.getElementsByTagName("form")[0].querySelector("input[name=relaxation]").value,
                    pressure: document.getElementsByTagName("form")[0].querySelector("input[name=pressure]").value
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
            }
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
}
function submit_Click() {

    var Item2;
    if(inputCheck){
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



