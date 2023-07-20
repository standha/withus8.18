//기록빠져있으면 팝업창 출력
function popupOpen(){
    $("#popup").show();
    $("#dim").show();

}
//보호자에게 알림 클릭 시
function popup(symptomsum){
    $("#layerSelectType").show();
    $("#dim").show();

    // 선택 요소 가져오기
    const recordFaceElement = $(".record-face1, .record-face2, .record-face3,.record-face4");

    if(symptomsum==0){
        // 증상 개수가 0인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face1");
        Item2 =  "<span> 보호자에게 증상일지를 알릴까요? </span>";
        $('#popUp2').append(Item2);

    }else if(symptomsum==1){
        // 증상 개수가 1인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face2");
        Item2 =  "<span> 보호자에게 증상일지를 알릴까요? </span>";
        $('#popUp2').append(Item2);
    }else if(symptomsum==2){
        // 증상 개수가 2인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face3");
        Item2 =  "<span> 보호자에게 증상일지를 알릴까요? </span>";
        $('#popUp2').append(Item2);
    }
    else if(symptomsum==3){
        // 증상 개수가 3인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face3");
        Item2 =  "<span> 보호자에게 증상일지를 알릴까요? </span>";
        $('#popUp2').append(Item2);
    }
    else if(symptomsum==4){
        // 증상 개수가 4인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face4");
        Item2 =  "<span> 보호자에게 증상일지를 알릴까요? </span>";
        $('#popUp2').append(Item2);
    }
}
//저장 클릭시 팝업창 출력
function popuppop(symptomsum){
    $("#sysmptomsubmit").show();
    $("#dim").show();

    // 선택 요소 가져오기
    const recordFaceElement = $(".record-face1, .record-face2, .record-face3,.record-face4");

    if(symptomsum==0){
        // 증상 개수가 0인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face1");
        Item2="<span> 환자분 상태가 좋으시군요!</span>";
        $('#popUp20').append(Item2);

    }else if(symptomsum==1){
        // 증상 개수가 1인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face2");
        Item2="<span> 일부증상이 있으시지만 생활에 어려움은 없으시군요.</span>";
        $('#popUp20').append(Item2);
    }else if(symptomsum==2){
        // 증상 개수가 2인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face3");
        Item2="<span> 일부 증상이 있으시군요.증상이 변화되는지 계속적으로 기록해보세요.</span>";
        $('#popUp20').append(Item2);
    }
    else if(symptomsum==3){
        // 증상 개수가 3인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face3");
        Item2="<span> 증상이 심하시군요. 의사에게 증상을 이야기 하셨나요?</span>";
        $('#popUp20').append(Item2);
    }
    else if(symptomsum==4){
        // 증상 개수가 4인 경우에 해당하는 클래스 추가
        recordFaceElement.addClass("record-face4");
        Item2="<span> 증상이 심하시군요. 의사에게 증상을 이야기 해주세요.</span>";
        $('#popUp20').append(Item2);
    }
}
/*
function savePreviousWork() {
    // 이전 작업을 저장할 데이터 (예: 폼 입력 값)
    var previousData = {
        input1: $("#rdo1").val(),
        input2: $("#rdo2").val(),
        input3: $("#rdo3").val(),
        input4: $("#rdo4").val(),
        input5: $("#rdo5").val(),
        input6: $("#rdo6").val(),
        input7: $("#rdo7").val(),
        input8: $("#rdo8").val(),
    };

    // 데이터를 로컬 스토리지에 저장
    localStorage.setItem("previousWork", JSON.stringify(previousData));
}

// 이전 작업을 불러오는 함수
function loadPreviousWork() {
    // 로컬 스토리지에서 저장된 데이터 가져오기
    var previousData = localStorage.getItem("previousWork");

    // 저장된 데이터가 있을 경우 작업 복원
    if (previousData) {
        previousData = JSON.parse(previousData);
        $("#rdo1").val(previousData.input1);
        $("#rdo2").val(previousData.input2);
        $("#rdo3").val(previousData.input3);
        $("#rdo4").val(previousData.input4);
        $("#rdo5").val(previousData.input5);
        $("#rdo6").val(previousData.input6);
        $("#rdo7").val(previousData.input7);
        $("#rdo8").val(previousData.input8);
    }
}
*/
//보호자에게 알람 클릭 시
function symptom_caregiver(form) {
    console.log(form);
    if (form.querySelector('input[name=breath]:checked') == null ||
        form.querySelector('input[name=tired]:checked') == null ||
        form.querySelector('input[name=ankle]:checked') == null ||
        form.querySelector('input[name=cough]:checked') == null)
    {
        popupOpen();

        $("#okButton").click(function () {
            $("#popup").hide();
            $("#dim").hide();
            return false;
        });
    } else {
            breath = form.querySelector('input[name=breath]:checked').value;
            tired = form.querySelector('input[name=tired]:checked').value;
            ankle = form.querySelector('input[name=ankle]:checked').value;
            cough = form.querySelector('input[name=cough]:checked').value;
            text = form.querySelector('input[name=text]').value;

            const sum = parseInt(breath) + parseInt(tired) + parseInt(ankle) + parseInt(cough);
            popup(sum);

        $("#buttonOk").click(function () {
            const body = {
                outofbreath: breath,
                tired: tired,
                ankle: ankle,
                cough: cough,
                text: text,
                todaysymptom: sum
            }

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
                .then(data => console.log(data));

            window.location.reload();
            return false;
        });

        $("#buttonNo").click(function () {
            $("#layerSelectType").hide();
            $("#dim").hide();
           // savePreviousWork();
            window.location.reload();
            return false;
        });
        //
        // // 페이지 로드 시 이전 작업 불러오기
        // $(document).ready(function () {
        //     loadPreviousWork();
        // });
    }
    return false;
}

//저장버튼 클릭 시
function onFormSubmission(form) {
    console.log(form)
    if(form.querySelector('input[name=breath]:checked')==null ||
        form.querySelector('input[name=tired]:checked')==null ||
        form.querySelector('input[name=ankle]:checked')==null ||
        form.querySelector('input[name=cough]:checked')==null )
    {
        popupOpen();
        $("#okButton").click(function (){
            $("#popup").hide();
            $("#dim").hide();
            return false;
        });
    }
    else{
            breath = form.querySelector('input[name=breath]:checked').value;
            tired = form.querySelector('input[name=tired]:checked').value;
            ankle = form.querySelector('input[name=ankle]:checked').value;
            cough = form.querySelector('input[name=cough]:checked').value;
            text = form.querySelector('input[name=text]').value;

        const sum = parseInt(breath) + parseInt(tired) + parseInt(ankle) + parseInt(cough);

        popuppop(sum);

        $("#Button").click(function() {
            const body = {
                outofbreath: breath,
                tired: tired,
                ankle: ankle,
                cough: cough,
                text: text,
                todaysymptom: sum
            }

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
                .then(data => console.log(data));

            window.location.reload();
            return false;
        });
    }
    return false;
}


function showHistory() {
    const element = document.querySelector("#symptom-history-get-action");
    location.href = element.value;
}