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
