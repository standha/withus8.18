function addHelper(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        helper: count,
        symptom: symptom,
        alarm: alarm,
        weight: weight,
        bloodPressure: blood,
        goal: goal,
        diseaseInfo: disease,
        level: level,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
    const options = {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    };
    fetch(url, options)
        .then(response => response.json())
        .then(data => console.log(data));
    return false;
}

function helperRequest(){
    const url = document.querySelector("#helper-request").value;
    const body = {
        //body 처리 불필요
    }
    const options = {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },body: JSON.stringify(body)
    };
    fetch(url, options)
        .then(response => response.json())
        .then(data => console.log(data));
    return false;
}