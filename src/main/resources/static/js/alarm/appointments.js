function onFormSubmission(form) {
    const enabled = form.querySelector("input[name=visitAlarm]").checked;
    let hour = form.querySelector("input[name=hour]").value;
    let min = form.querySelector("input[name=minute]").value;
    let time;
    const date = $("#dateReserve").datepicker().val();

    if (form.querySelector("input[name=time]:checked").value == 1) {
        if(hour == 0 ){
            hour = null;
        }
        else{
            if( hour != 12){
                hour = parseInt(hour) + 12;
            }
        }
    } else if (form.querySelector("input[name=time]:checked").value == 0) {
        hour = transHour(hour);
    }

    if(hour == null && transMinute(min) == null ){
        time = null;
    }
    else if(hour != null && transMinute(min) == null){
        time = hour + ":00";
    }
    else if(hour == null && transMinute(min) != null){
        time = null;
    }
    else{
        time = hour + ":" + transMinute(min);
    }

    const body = {
        visitAlarm: enabled,
        outPatientVisitDate: date,
        outPatientVisitTime: time
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

    setTimeout(function () {
        location.reload(true);
    }, 300);
    return false;
}

function transHour(hour) {
    if(hour == 12){
        return "00";
    }
    else if(hour >= 10)
        return hour;
    else {
        if (hour == 0) {
            return null;
        } else if (hour.startsWith("0")) {
            return hour;
        } else {
            return "0" + hour;
        }
    }
}

function transMinute(min){
    if(min < 10){
        if(min.startsWith("00")){
            return min;
        } else if (min == 0) {
            min = "0" + min;
            if (min.startsWith("00"))
                return min;
            else
                return null;
        } else if (min.startsWith("0")) {
            return min;
        } else {
            return "0" + min;
        }
    } else
        return min;
}

function check_radio(div) {
    switch (div.id) {
        case "morning":
            if (document.getElementById('morning').className === "rdo-box") {
                document.getElementById('morning').className = "rdo-box checked";
                document.getElementById('afternoon').className = "rdo-box";
                document.getElementById("rdo8").setAttribute("checked", "");
                document.getElementById("rdo9").removeAttribute("checked");
            }
            break;
        case "afternoon":
            if (document.getElementById('afternoon').className === "rdo-box") {
                document.getElementById('afternoon').className = "rdo-box checked";
                document.getElementById('morning').className = "rdo-box";
                document.getElementById("rdo8").setAttribute("checked", "");
                document.getElementById("rdo9").removeAttribute("checked");
            }
            break;
    }
}

function thCheck(time) {
    switch (time) {
        case 0:
            document.getElementById('morning').className = "rdo-box checked";
            document.getElementById('afternoon').className = "rdo-box";
            document.getElementById("rdo8").setAttribute("checked", "");
            document.getElementById("rdo9").removeAttribute("checked");
            break;
        case 1:
            document.getElementById('afternoon').className = "rdo-box checked";
            document.getElementById('morning').className = "rdo-box";
            document.getElementById("rdo9").setAttribute("checked", "");
            document.getElementById("rdo8").removeAttribute("checked");
            break;
    }
}

function check_box() {
    if (document.getElementById('alarm').className === "chk-box") {
        document.getElementById('alarm').className = "chk-box checked";
        document.getElementById('label').textContent = "켜짐";
        document.getElementById("onoff").setAttribute("checked", "");
    } else {
        document.getElementById('alarm').className = "chk-box";
        document.getElementById('label').textContent = "꺼짐";
        document.getElementById("onoff").removeAttribute("checked");
    }
}