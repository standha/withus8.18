function onFormSubmission(form) {
    const hourValue = form.querySelector("input[name=hour]").value
    const minuteValue = form.querySelector("input[name=minute]").value;
    if(isEmpty(hourValue)==true && isEmpty(minuteValue)==true ) {
        alert("운동 시간을 입력해 주세요.");
    }
    else if(hourValue == 0 && minuteValue == 0) //둘다 0일때 입력 막음
    {
        alert("0시 0분은 입력 불가능합니다.");
    }
    else{
        hour = isEmpty(hourValue) ? 0 : hourValue;
        minute = isEmpty(minuteValue)? 0 : minuteValue;

        const body = {
            hour: hour,
            minute: minute
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
    }

    return false;
}

function showHistory() {
    const element = document.querySelector("#exercise-history-get-action");
    location.href = element.value;
}

function isEmpty(value){
    if (value.length === 0) {
        return true;
    } else {
        return false;
    }
}