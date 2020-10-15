function onFormSubmission(form) {
    // const morningElement = form.querySelector("input[name=morning]:checked");
    // const morning = morningElement? morningElement.value: 0;
    // const lunchElement = form.querySelector("input[name=lunch]:checked");
    // const lunch = lunchElement? lunchElement.value: 0;
    // const dinnerElement = form.querySelector("input[name=dinner]:checked");
    // const dinner = dinnerElement? dinnerElement.value: 0;

    var morningElement = 0;
    var morningClass0 = document.getElementById('morning0').className;
    if(morningClass0 == "none active") morningElement = 0;
    var morningClass1 = document.getElementById('morning1').className;
    if(morningClass1 == "row active") morningElement = 1;
    var morningClass2 = document.getElementById('morning2').className;
    if(morningClass2 == "middle active") morningElement = 2;
    var morningClass3 = document.getElementById('morning3').className;
    if(morningClass3 == "high active") morningElement = 3;

    var lunchElement = 0;
    var lunchClass0 = document.getElementById('lunch0').className;
    if(lunchClass0 == "none active") lunchElement = 0;
    var lunchClass1 = document.getElementById('lunch1').className;
    if(lunchClass1 == "row active") lunchElement = 1;
    var lunchClass2 = document.getElementById('lunch2').className;
    if(lunchClass2 == "middle active") lunchElement = 2;
    var lunchClass3 = document.getElementById('lunch3').className;
    if(lunchClass3 == "high active") lunchElement = 3;

    var dinnerElement = 0;
    var dinnerClass0 = document.getElementById('dinner0').className;
    if(dinnerClass0 == "none active") dinnerElement = 0;
    var dinnerClass1 = document.getElementById('dinner1').className;
    if(dinnerClass1 == "row active") dinnerElement = 1;
    var dinnerClass2 = document.getElementById('dinner2').className;
    if(dinnerClass2 == "middle active") dinnerElement = 2;
    var dinnerClass3 = document.getElementById('dinner3').className;
    if(dinnerClass3 == "high active") dinnerElement = 3;

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
                alert("입력 완료되었습니다.");
            }  else {
                alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
            }
        });

    // const response = fetch(url, options)
    //     .then(response => response.json())
    //     .then(data =>console.log(data))
    //     .then(data => alert("입력이 완료되었습니다."));
    // if (response && response.ok) {
    //     console.log(response);
    // }

    //window.location.reload();
    return false;
}

function showHistory() {
    const element = document.querySelector("#natrium-history-get-action");
    location.href = element.value;
}