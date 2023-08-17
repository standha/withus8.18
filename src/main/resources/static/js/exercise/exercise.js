function popup(){
    $("#exercisesubmit").show();
    $("#dim").show();

    console.log("팝업창 열렸다");

    var Item2;
    Item2 = "<span> 기록이 완료되었습니다.</span>"
    $('#popUp2').append(Item2);


    $("#okButton").click(function () {
        $("#exercisesubmit").hide();
        $("#dim").hide();

        window.location.reload();
        return false;
    });

}

function onFormSubmission(form) {
    const hourValue = form.querySelector("input[name=hour]").value;
    const minuteValue = form.querySelector("input[name=minute]").value;
    const selectElement = document.getElementById("activityDropdown");
    const exercise = selectElement ? selectElement.value: null;
    // let walking = form.querySelector("input[value=walking]");
    // let cycling = form.querySelector("input[value=cycling]");
    // let swimming = form.querySelector("input[value=swimming]");
    // let strength = form.querySelector("input[value=strength]");
    // let walking = document.getElementById("exercise1");
    // let cycling = document.getElementById("exercise2");
    // let swimming = document.getElementById("exercise3");
    // let strength = document.getElementById("exercise4");
    //



    let walking = 0;
    let cycling = 0;
    let swimming = 0;
    let strength = 0;
    let recentExercise = null;

    if(isEmpty(hourValue)==true && isEmpty(minuteValue)==true ) {
        alert("운동 시간을 입력해 주세요.");
    }
    else if(hourValue == 0 && minuteValue == 0) //둘다 0일때 입력 막음
    {
        alert("0시 0분은 입력 불가능합니다.");
    }
    else{
        hour = isEmpty(hourValue) ? 0 : parseInt(hourValue,10);
        minute = isEmpty(minuteValue)? 0 : parseInt(minuteValue,10);
        let total_minute;
        total_minute = 60 * hour + minute;

        switch (exercise) {
            case "walking":
                walking = total_minute;
                recentExercise = 1;
                console.log("걷기 선택");
                break;
            case "cycling":
                cycling = total_minute;
                recentExercise =2 ;
                console.log("자전거타기 선택");
                break;
            case "swimming":
                swimming = total_minute;
                recentExercise =3;
                console.log("수영 선택");
                break;
            case "strength":
                strength = total_minute;
                recentExercise =4;
                console.log("근력운동 선택");
                break;
            case "none" :
                alert("운동종류를 선택해주세요. ");
                console.log("선택 해제");
                break;

        };
        console.log (recentExercise) ;


        popup();

        const body = {
            hour: hour,
            minute: minute,
            recentExercise:recentExercise ,
            walking : walking,
            cycling : cycling,
            swimming : swimming,
            strength : strength,

        }



        console.log(exercise);

        const url = form.action;
        const options = {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        };
        console.log("저장");
        console.log(walking);
        console.log(swimming);
        console.log(cycling);
        console.log(strength);

        fetch(url, options)
            .then(response => response.json())
            .then(data => {console.log(data)});
    }

    return false;
}


function alarm_caregiver() {
    $("#layerSelectType").show();
    $("#dim").show();
    Item = "<span> 보호자에게 운동한 것을 알릴까요?</span>"
    $('#popUp3').append(Item);


    $("#buttonNo").click(function () {
        $("#layerSelectType").hide();
        $("#dim").hide();
        window.location.reload();
        return false;
    });

}
function isEmpty(value){
    if (value.length === 0) {
        return true;
    } else {
        return false;
    }
}
function showHistory() {
    const element = document.querySelector("#exercise-history-get-action");
    location.href = element.value;
}