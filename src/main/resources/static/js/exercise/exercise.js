function onFormSubmission(form) {
    const hourValue = form.querySelector("input[name=hour]").value
    const minuteValue = form.querySelector("input[name=minute]").value;
    if(isEmpty(hourValue)==true && isEmpty(minuteValue)==true ) {
        alert("� �ð��� �Է��� �ּ���.");
    }
    else if(hourValue == 0 && minuteValue == 0) //�Ѵ� 0�϶� �Է� ����
    {
        alert("0�� 0���� �Է� �Ұ����մϴ�.");
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
                    alert("�Է� �Ϸ�Ǿ����ϴ�.");
                }  else {
                    alert("�Է� �����Ͽ����ϴ�. �����ڿ��� �������ּ���.");
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