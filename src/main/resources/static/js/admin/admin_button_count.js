function deleteForm(){
    var table = document.getElementById("foo-table");
    table.parentNode.removeChild(table);
}
 function subButtonFormCreate(count){
    deleteForm();
     let sumCount ={};
     for(const key in count["0"]){
         let tempSum = 0;
         if(key != "key"){
             for(var i = 0; i<24; i++){
                 var tempData = count[String(i)];
                 tempSum += tempData[key];
             }
             sumCount[key] = tempSum;
         }
     }
    var table = document.createElement("table");
    table.id = "foo-table";
    table.classList.add("table", "table-bordered");
    table.style.tableLayout = "fixed";

// <thead> 요소 생성
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");

    th1.setAttribute("colspan", "16");
    th1.textContent = "하부 항목 버튼 클릭수";
    tr1.appendChild(th1);

    thead.appendChild(tr1);

// 첫 번째 <tr> 요소 생성
    var tr2 = document.createElement("tr");

// 첫 번째 <tr>에 <th> 요소 추가
    var th2 = document.createElement("th");
    th2.setAttribute("rowspan", "2");
    th2.textContent = "주차";
    tr2.appendChild(th2);

    var th3 = document.createElement("th");
    th3.setAttribute("colspan", "4");
    th3.textContent = "이주의 목표";
    tr2.appendChild(th3);

    var th4 = document.createElement("th");
    th4.setAttribute("colspan", "2");
    th4.textContent = "염분/수분";
    tr2.appendChild(th4);

    var th5 = document.createElement("th");
    th5.setAttribute("colspan", "3");
    th5.textContent = "마음건강";
    tr2.appendChild(th5);

    var th6 = document.createElement("th");
    th6.setAttribute("colspan", "4");
    th6.textContent = "게시판";
    tr2.appendChild(th6);

    var th7 = document.createElement("th");
    th7.setAttribute("colspan", "2");
    th7.textContent = "알람";
    tr2.appendChild(th7);

    thead.appendChild(tr2);

// 두 번째 <tr> 요소 생성
    var tr3 = document.createElement("tr");

// 두 번째 <tr>에 <th> 요소 추가
    var headers = ["난이도 하", "난이도 중", "난이도 상","나만의 목표 만들기", "저염식이", "수분섭취", "마음일기", "마음점수", "마음관리", "명예의 전당", "공지사항", "질문 게시판", "나눔 게시판", "복약 시간", "외래 방문 시간"];
    for (var i = 0; i < headers.length; i++) {
        var th = document.createElement("th");
        th.textContent = headers[i];
        tr3.appendChild(th);
    }

// 두 번째 <tr>을 <thead>에 추가
    thead.appendChild(tr3);

// <thead>를 테이블에 추가
    table.appendChild(thead);

// 생성한 테이블을 문서의 원하는 위치에 추가

    var tbody1 = document.createElement("tbody");
    tbody1.id = "history-table-data";
    tbody1.style.textAlign = "center";


    var tbody2 = document.createElement("tbody");
    tbody2.id = "sum-table-data";
    tbody2.style.textAlign = "center";
    tbody2.style.borderTop = "1px solid #222222";

    table.appendChild(tbody1);
// <tbody>를 테이블에 추가

    var sumItem = document.createElement("tr");
    sumItem.classList.add("allRecord");

    var td1 = document.createElement("td");
    td1.textContent = "총합";
    sumItem.appendChild(td1);

    var sumKeys = ["lowLevel", "middleLevel", "highLevel", "makeMyGoal", "natriumMoisture", "waterIntake", "mindDiary", "mindScore", "mindManagement", "hof", "notice", "question", "share", "medicineTime", "outPatientVisitTime"];

    for (var i = 0; i < sumKeys.length; i++) {
        var sumKey = sumKeys[i];
        var td = document.createElement("td");
        td.classList.add(sumKey);
        td.textContent = sumCount[sumKey];
        sumItem.appendChild(td);
    }
    tbody2.appendChild(sumItem);

    table.appendChild(tbody2);

    var container = document.querySelector(".center-table"); // 테이블을 추가할 컨테이너 요소를 선택
    container.appendChild(table);
// <tbody>를 테이블에 추가

    for(i=0; i<24; i++)
    {
        let tempButtonCount = count[i];
        if(i%2 == 0)
            var Item = "<tr class='odd-tr'>";
        else
            var Item = "<tr class='even-tr'>";

        Item += "<td>" + String(i+1) + "주차" + "</td>";
        Item += "<td class='lowLevel'>" + tempButtonCount["lowLevel"] + "</td>";
        Item += "<td class='middleLevel'>" + tempButtonCount["middleLevel"] + "</td>";
        Item += "<td class='highLevel'>" + tempButtonCount["highLevel"] + "</td>";
        Item += "<td class='makeMyGoal'>" + tempButtonCount["makeMyGoal"] + "</td>";
        Item += "<td class='natriumMoisture'>" + tempButtonCount["natriumMoisture"] + "</td>";
        Item += "<td class='waterIntake'>" + tempButtonCount["waterIntake"] + "</td>";
        Item += "<td class='mindDiary'>" + tempButtonCount["mindDiary"]+ "</td>";
        Item += "<td class='mindScore'>" + tempButtonCount["mindScore"] + "</td>";
        Item += "<td class='mindManagement'>" + tempButtonCount["mindManagement"] + "</td>";
        Item += "<td class='hof'>" + tempButtonCount["hof"] + "</td>";
        Item += "<td class='notice'>" + tempButtonCount["notice"] + "</td>";
        Item += "<td class='question'>" + tempButtonCount["question"] + "</td>";
        Item += "<td class='share'>" + tempButtonCount["share"] + "</td>";
        Item += "<td class='medicineTime'>" + tempButtonCount["medicineTime"] + "</td>";
        Item += "<td class='outPatientVisitTime'>" + tempButtonCount["outPatientVisitTime"] + "</td>";
        Item += "</tr>";
        $('#history-table-data').append(Item);
    }



}

function detailButtonFormCreate(count){
    deleteForm();
    let sumCount ={};
    for(const key in count["0"]){
        let tempSum = 0;
        if(key != "key"){
            for(var i = 0; i<24; i++){
                var tempData = count[String(i)];
                tempSum += tempData[key];
            }
            sumCount[key] = tempSum;
        }
    }
    var table = document.createElement("table");
    table.id = "foo-table";
    table.classList.add("table", "table-bordered");
    table.style.tableLayout = "fixed";

// <thead> 요소 생성
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");

    th1.setAttribute("colspan", "14");
    th1.textContent = "세부 항목 버튼 클릭수";
    tr1.appendChild(th1);

    thead.appendChild(tr1);

// 첫 번째 <tr> 요소 생성
    var tr2 = document.createElement("tr");

// 첫 번째 <tr>에 <th> 요소 추가
    var th2 = document.createElement("th");
    th2.setAttribute("rowspan", "2");
    th2.textContent = "주차";
    tr2.appendChild(th2);

    var th3 = document.createElement("th");
    th3.textContent = "저염식이";
    tr2.appendChild(th3);

    var th4 = document.createElement("th");
    th4.setAttribute("colspan", "4");
    th4.textContent = "마음 관리";
    tr2.appendChild(th4);

    var th5 = document.createElement("th");
    th5.setAttribute("colspan", "8");
    th5.textContent = "보호자에게 알림";
    tr2.appendChild(th5);

    thead.appendChild(tr2);

// 두 번째 <tr> 요소 생성
    var tr3 = document.createElement("tr");

// 두 번째 <tr>에 <th> 요소 추가
    var headers = ["저염식이 식단추천","명상","신체활동","심호흡","상담","복용약 알림","혈압/맥박 알림","운동 알림","증상일지 알림","저염식이 알림","수분섭취 알림","체중 알림","마음 접수 알림"];
    for (var i = 0; i < headers.length; i++) {
        var th = document.createElement("th");
        th.textContent = headers[i];
        tr3.appendChild(th);
    }

// 두 번째 <tr>을 <thead>에 추가
    thead.appendChild(tr3);

// <thead>를 테이블에 추가
    table.appendChild(thead);

// 생성한 테이블을 문서의 원하는 위치에 추가

    var tbody1 = document.createElement("tbody");
    tbody1.id = "history-table-data";
    tbody1.style.textAlign = "center";


    var tbody2 = document.createElement("tbody");
    tbody2.id = "sum-table-data";
    tbody2.style.textAlign = "center";
    tbody2.style.borderTop = "1px solid #222222";

    table.appendChild(tbody1);
// <tbody>를 테이블에 추가

    var sumItem = document.createElement("tr");
    sumItem.classList.add("allRecord");

    var td1 = document.createElement("td");
    td1.textContent = "총합";
    sumItem.appendChild(td1);

    var sumKeys = ["recommendDiet", "meditation", "bodyActivity", "deepBreath", "consulting", "medicineAlarm", "bloodPressureAlarm", "symptomAlarm", "exerciseAlarm", "natriumMoistureAlarm", "waterIntakeAlarm", "weightAlarm", "mindScoreAlarm"];

    for (var i = 0; i < sumKeys.length; i++) {
        var sumKey = sumKeys[i];
        var td = document.createElement("td");
        td.classList.add(sumKey);
        td.textContent = sumCount[sumKey];
        sumItem.appendChild(td);
    }
    tbody2.appendChild(sumItem);

    table.appendChild(tbody2);

    var container = document.querySelector(".center-table"); // 테이블을 추가할 컨테이너 요소를 선택
    container.appendChild(table);
// <tbody>를 테이블에 추가

    for(i=0; i<24; i++)
    {
        let tempButtonCount = count[i];
        if(i%2 == 0)
            var Item = "<tr class='odd-tr'>";
        else
            var Item = "<tr class='even-tr'>";

        Item += "<td>" + String(i+1) + "주차" + "</td>";
        Item += "<td class='recommendDiet'>" + tempButtonCount["recommendDiet"] + "</td>";
        Item += "<td class='meditation'>" + tempButtonCount["meditation"] + "</td>";
        Item += "<td class='bodyActivity'>" + tempButtonCount["bodyActivity"] + "</td>";
        Item += "<td class='deepBreath'>" + tempButtonCount["deepBreath"] + "</td>";
        Item += "<td class='consulting'>" + tempButtonCount["consulting"] + "</td>";
        Item += "<td class='medicineAlarm'>" + tempButtonCount["medicineAlarm"] + "</td>";
        Item += "<td class='bloodPressureAlarm'>" + tempButtonCount["bloodPressureAlarm"]+ "</td>";
        Item += "<td class='exerciseAlarm'>" + tempButtonCount["exerciseAlarm"] + "</td>";
        Item += "<td class='symptomAlarm'>" + tempButtonCount["symptomAlarm"] + "</td>";
        Item += "<td class='natriumMoistureAlarm'>" + tempButtonCount["natriumMoistureAlarm"] + "</td>";
        Item += "<td class='waterIntakeAlarm'>" + tempButtonCount["waterIntakeAlarm"] + "</td>";
        Item += "<td class='weightAlarm'>" + tempButtonCount["weightAlarm"] + "</td>";
        Item += "<td class='mindScoreAlarm'>" + tempButtonCount["mindScoreAlarm"] + "</td>";
        Item += "</tr>";
        $('#history-table-data').append(Item);
    }



}


function mainButtonFormCreate(count){
    deleteForm();
    let sumCount ={};
    for(const key in count["0"]){
        let tempSum = 0;
        if(key != "key" && key != "infoEdit"){
            for(var i = 0; i<24; i++){
                var tempData = count[String(i)];
                tempSum += tempData[key];
            }
            sumCount[key] = tempSum;
        }
    }
    var table = document.createElement("table");
    table.id = "foo-table";
    table.classList.add("table", "table-bordered");
    table.style.tableLayout = "fixed";

// <thead> 요소 생성
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");

    th1.setAttribute("colspan", "15");
    th1.textContent = "메인 항목 버튼 클릭수";
    tr1.appendChild(th1);

    thead.appendChild(tr1);

// 첫 번째 <tr> 요소 생성
    var tr2 = document.createElement("tr");

// 두 번째 <tr>에 <th> 요소 추가
    var headers = ["주차","이주의 목표", "달성률", "위더스랑","질환정보", "도우미", "복용약", "혈압/맥박", "운동", "증상일지", "염분/수분", "체중", "마음건강", "게시판", "알람"];
    for (var i = 0; i < headers.length; i++) {
        var th = document.createElement("th");
        th.textContent = headers[i];
        tr2.appendChild(th);
    }

    thead.appendChild(tr2);
// 두 번째 <tr>을 <thead>에 추가

// <thead>를 테이블에 추가
    table.appendChild(thead);

// 생성한 테이블을 문서의 원하는 위치에 추가

    var tbody1 = document.createElement("tbody");
    tbody1.id = "history-table-data";
    tbody1.style.textAlign = "center";


    var tbody2 = document.createElement("tbody");
    tbody2.id = "sum-table-data";
    tbody2.style.textAlign = "center";
    tbody2.style.borderTop = "1px solid #222222";

    table.appendChild(tbody1);
// <tbody>를 테이블에 추가

    var sumItem = document.createElement("tr");
    sumItem.classList.add("allRecord");

    var td1 = document.createElement("td");
    td1.textContent = "총합";
    sumItem.appendChild(td1);

    var sumKeys = ["goal", "level", "withusRang","diseaseInfo", "helper", "medicine", "bloodPressure", "exercise", "symptom", "natriumMoisture", "weight", "mindHealth", "board", "alarm"];

    for (var i = 0; i < sumKeys.length; i++) {
        var sumKey = sumKeys[i];
        var td = document.createElement("td");
        td.classList.add(sumKey);
        td.textContent = sumCount[sumKey];
        sumItem.appendChild(td);
    }
    tbody2.appendChild(sumItem);

    table.appendChild(tbody2);

    var container = document.querySelector(".center-table"); // 테이블을 추가할 컨테이너 요소를 선택
    container.appendChild(table);
// <tbody>를 테이블에 추가

    for(i=0; i<24; i++)
    {
        let tempButtonCount = count[i];
        if(i%2 == 0)
            var Item = "<tr class='odd-tr'>";
        else
            var Item = "<tr class='even-tr'>";

        Item += "<td>" + String(i+1) + "주차" + "</td>";
        Item += "<td class='goal'>" + tempButtonCount["goal"] + "</td>";
        Item += "<td class='level'>" + tempButtonCount["level"] + "</td>";
        Item += "<td class='withusRang'>" + tempButtonCount["withusRang"] + "</td>";
        Item += "<td class='diseaseInfo'>" + tempButtonCount["diseaseInfo"] + "</td>";
        Item += "<td class='helper'>" + tempButtonCount["helper"] + "</td>";
        Item += "<td class='medicine'>" + tempButtonCount["medicine"] + "</td>";
        Item += "<td class='bloodPressure'>" + tempButtonCount["bloodPressure"]+ "</td>";
        Item += "<td class='exercise'>" + tempButtonCount["exercise"] + "</td>";
        Item += "<td class='symptom'>" + tempButtonCount["symptom"] + "</td>";
        Item += "<td class='natriumMoisture'>" + tempButtonCount["natriumMoisture"] + "</td>";
        Item += "<td class='weight'>" + tempButtonCount["weight"] + "</td>";
        Item += "<td class='mindHealth'>" + tempButtonCount["mindHealth"] + "</td>";
        Item += "<td class='board'>" + tempButtonCount["board"] + "</td>";
        Item += "<td class='alarm'>" + tempButtonCount["alarm"] + "</td>";
        Item += "</tr>";
        $('#history-table-data').append(Item);
    }



}



const getAjax = function(url) {
    return new Promise((resolve, reject) => { // 1.
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json"
            ,
            success: (res) => {
                resolve(res);  // 2.
            },
            error: (e) => {
                reject(e);  // 3.
            }
        });
    });
}
async function subButton(id){
    const url ="/patient-sub-button/"+id;
    try {
        const { counts } = await getAjax(url);
        subButtonFormCreate(counts);
    } catch(e) {
        console.log(e);
    }

}

async function mainButton(id){
    const url ="/patient-main-button/"+id;
    try {
        const { counts } = await getAjax(url);
        mainButtonFormCreate(counts);
    } catch(e) {
        console.log(e);
    }
}
async function detailButton(id){
    const url ="/patient-detail-button/"+id;
    try {
        const { counts } = await getAjax(url);
        detailButtonFormCreate(counts);
    } catch(e) {
        console.log(e);
    }
}


