function onFormSubmission(form) {
    const enabled_morning = form.querySelector("input[name=alarmOnoffMorning]").checked;
    const enabled_lunch = form.querySelector("input[name=alarmOnoffLunch]").checked;
    const enabled_dinner = form.querySelector("input[name=alarmOnoffDinner]").checked;

    let breakfastHour = form.querySelector("input[name=morningHour]").value;
    let breakfastMinute = form.querySelector("input[name=morningMinute]").value;
    let lunchHour = form.querySelector("input[name=lunchHour]").value;
    let lunchMinute = form.querySelector("input[name=lunchMinute]").value;
    let dinnerHour = form.querySelector("input[name=dinnerHour]").value;
    let dinnerMinute = form.querySelector("input[name=dinnerMinute]").value;
    let morning;
    let lunch;
    let dinner;



    if (form.querySelector("input[name=morning]:checked").value == 1) {
        if (breakfastHour == 0) {
            breakfastHour = null;
        } else {
            if (breakfastHour != 12) {
                breakfastHour = parseInt(breakfastHour) + 12;
            }
        }
    } else if (form.querySelector("input[name=morning]:checked").value == 0) {
        breakfastHour = transHour(breakfastHour);
    }
    if (form.querySelector("input[name=lunch]:checked").value == 1) {
        if (lunchHour == 0) {
            lunchHour = null;
        } else {
            if (lunchHour != 12) {
                lunchHour = parseInt(lunchHour) + 12;
            }
        }
    } else if (form.querySelector("input[name=lunch]:checked").value == 0) {
        lunchHour = transHour(lunchHour);
    }
    if (form.querySelector("input[name=dinner]:checked").value == 1) {
        if (dinnerHour == 0) {
            dinnerHour = null;
        } else {
            if (dinnerHour != 12) {
                dinnerHour = parseInt(dinnerHour) + 12;
            }
        }
    } else if (form.querySelector("input[name=dinner]:checked").value == 0) {
        dinnerHour = transHour(dinnerHour);
    }

    if (breakfastHour == null || transMinute(breakfastMinute) == null) {
        morning = null;
    } else {
        morning = breakfastHour + ":" + transMinute(breakfastMinute);
    }
    if (lunchHour == null || transMinute(lunchMinute) == null) {
        lunch = null;
    } else {
        lunch = lunchHour + ":" + transMinute(lunchMinute);
    }
    if (dinnerHour == null || transMinute(dinnerMinute) == null) {
        dinner = null;
    } else {
        dinner = dinnerHour + ":" + transMinute(dinnerMinute);
    }
    const body = {
        alarmOnoffMorning: enabled_morning,
        alarmOnoffLunch: enabled_lunch,
        alarmOnoffDinner: enabled_dinner,
        medicationTimeMorning: morning,
        medicationTimeLunch: lunch,
        medicationTimeDinner: dinner
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
            } else {
                alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
            }
        });

    setTimeout(function () {
        location.reload(true);
    }, 300);
    return false;
}

function setFinished(finished) {
    const body = {
        finished: finished
    };

    const url = document.querySelector("#pill-put-action").value;
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
        .then(object => {
            console.log(object);

            let data = object["data"];
            if (data) {
                let finishedResponse = data["finished"];

                if (finished === finishedResponse) {
                    if (finished) {
                        alert("복약하신 것으로 기록하였습니다.")
                    } else {
                        alert("아직 복약하지 않은 것으로 기록하였습니다.")
                    }
                } else {
                    alert("복약 처리에 실패했습니다.")
                }
            } else {
                alert("복약 처리에 실패했습니다.")
            }
        });
}

function showHistory() {
    const element = document.querySelector("#pill-history-get-action");
    location.href = element.value;
}

function check_radio(div) {
    switch (div.id) {
        case "yes":
            if (document.getElementById('yes').className === "rdo-box") {
                document.getElementById('yes').className = "rdo-box checked";
                document.getElementById('no').className = "rdo-box";
            }
            break;
        case "no":
            if (document.getElementById('no').className === "rdo-box") {
                document.getElementById('no').className = "rdo-box checked";
                document.getElementById('yes').className = "rdo-box";
            }
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


function morning_toggle(div) {
    switch (div.id) {
        case "morning_on":
            if (document.getElementById('morning_on').className === "rdo-box") {
                document.getElementById('morning_on').className = "rdo-box checked";
                document.getElementById('morning_off').className = "rdo-box";
                document.getElementById("rdo32").setAttribute("checked", "");
                document.getElementById("rdo33").removeAttribute("checked");

                document.getElementById('timeif1').classList.remove('with-overlay');

                console.log('아침 on');
            }
            break;
        case "morning_off" :
            if (document.getElementById('morning_off').className === "rdo-box") {
                document.getElementById('morning_off').className = "rdo-box checked";
                document.getElementById('morning_on').className = "rdo-box";
                document.getElementById("rdo33").setAttribute("checked", "");
                document.getElementById("rdo32").removeAttribute("checked");
                //overlay 필터 적용
                document.getElementById('timeif1').classList.add('with-overlay');

                console.log('아침 off');

            }
            break;
    }

}

function lunch_toggle(div) {
    switch (div.id) {
        case "lunch_on":
            if (document.getElementById('lunch_on').className === "rdo-box") {
                document.getElementById('lunch_on').className = "rdo-box checked";
                document.getElementById('lunch_off').className = "rdo-box";
                document.getElementById("rdo34").setAttribute("checked", "");
                document.getElementById("rdo35").removeAttribute("checked");
                document.getElementById('timeif2').classList.remove('with-overlay');
                console.log('점심 on');
            }
            break;
        case "lunch_off" :
            if (document.getElementById('lunch_off').className === "rdo-box") {
                document.getElementById('lunch_off').className = "rdo-box checked";
                document.getElementById('lunch_on').className = "rdo-box";
                document.getElementById("rdo35").setAttribute("checked", "");
                document.getElementById("rdo34").removeAttribute("checked");
                //overlay 필터 적용
                document.getElementById('timeif2').classList.add('with-overlay');
                console.log('점심 off');

            }
            break;
    }
}

function dinner_toggle(div) {
    switch (div.id) {
        case "dinner_on":
            if (document.getElementById('dinner_on').className === "rdo-box") {
                document.getElementById('dinner_on').className = "rdo-box checked";
                document.getElementById('dinner_off').className = "rdo-box";
                document.getElementById("rdo36").setAttribute("checked", "");
                document.getElementById("rdo37").removeAttribute("checked");
                document.getElementById('timeif3').classList.remove('with-overlay');
                // document.getElementById('rdo36').classList.add('label');
                console.log('저녁 on');

            }
            break;
        case "dinner_off" :
            if (document.getElementById('dinner_off').className === "rdo-box") {
                document.getElementById('dinner_off').className = "rdo-box checked";
                document.getElementById('dinner_on').className = "rdo-box";
                document.getElementById("rdo37").setAttribute("checked", "");
                document.getElementById("rdo36").removeAttribute("checked");
                //overlay 필터 적용
                document.getElementById('timeif3').classList.add('with-overlay');
                // document.getElementById('rdo36').classList.add('on-label');
                console.log('저녁 off');

            }
            break;
    }
}


function check_morning(div) {
    switch (div.id) {
        case "morning_am":
            if (document.getElementById('morning_am').className === "rdo-box") {
                document.getElementById('morning_am').className = "rdo-box checked";
                document.getElementById('morning_pm').className = "rdo-box";
                document.getElementById("rdo8").setAttribute("checked", "");
                document.getElementById("rdo9").removeAttribute("checked");
            }
            break;
        case "morning_pm":
            if (document.getElementById('morning_pm').className === "rdo-box") {
                document.getElementById('morning_pm').className = "rdo-box checked";
                document.getElementById('morning_am').className = "rdo-box";
                document.getElementById("rdo9").setAttribute("checked", "");
                document.getElementById("rdo8").removeAttribute("checked");
                // document.getElementById("rdo8").removeAttribute("checked");

            }
            break;
    }
}

function check_lunch(div) {
    switch (div.id) {
        case "lunch_am":
            if (document.getElementById('lunch_am').className === "rdo-box") {
                document.getElementById('lunch_am').className = "rdo-box checked";
                document.getElementById('lunch_pm').className = "rdo-box";
                document.getElementById("rdo10").setAttribute("checked", "");
                document.getElementById("rdo11").removeAttribute("checked");
            }
            break;
        case "lunch_pm":
            if (document.getElementById('lunch_pm').className === "rdo-box") {
                document.getElementById('lunch_pm').className = "rdo-box checked";
                document.getElementById('lunch_am').className = "rdo-box";
                document.getElementById("rdo11").setAttribute("checked", "");
                document.getElementById("rdo10").removeAttribute("checked");
            }
            break;
    }
}

function check_dinner(div) {
    switch (div.id) {
        case "dinner_am":
            if (document.getElementById('dinner_am').className === "rdo-box") {
                document.getElementById('dinner_am').className = "rdo-box checked";
                document.getElementById('dinner_pm').className = "rdo-box";
                document.getElementById("rdo12").setAttribute("checked", "");
                document.getElementById("rdo13").removeAttribute("checked");
            }
            break;
        case "dinner_pm":
            if (document.getElementById('dinner_pm').className === "rdo-box") {
                document.getElementById('dinner_pm').className = "rdo-box checked";
                document.getElementById('dinner_am').className = "rdo-box";
                document.getElementById("rdo13").setAttribute("checked", "");
                document.getElementById("rdo12").removeAttribute("checked");
            }
            break;
    }
}

function transHour(hour) {
    if (hour == 12) {
        return "00";
    } else if (hour >= 10)
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

function transMinute(min) {
    if (min < 10) {
        if (min.startsWith("00")) {
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