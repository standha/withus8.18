window.addEventListener('DOMContentLoaded', function() {
    var dateElement = document.getElementById('date');
    var date = new Date();
    var month = date.getMonth()+1;
    var day = date.getDate();
    return dateElement.textContent = month + '월' + day + '일';
    console.log("watch out");
});




var drug_yes,drug_no = 0;
var sum_drug = drug_yes + drug_no;


function medication_Click_morning(div) {
    switch (div.id) {
        case "check_medi1":
            if (document.getElementById('check_medi1').className === "rdo-box") {
                document.getElementById('check_medi1').className = "rdo-box checked";
                document.getElementById('uncheck_medi1').className = "rdo-box";
                document.getElementById("drug1").setAttribute("checked", "");
                document.getElementById("drug2").removeAttribute("checked");
                drug_yes+=1;
            }
            break;
        case "uncheck_medi1":
            if (document.getElementById('uncheck_medi1').className === "rdo-box") {
                document.getElementById('uncheck_medi1').className = "rdo-box checked2";
                document.getElementById('check_medi1').className = "rdo-box";
                document.getElementById("drug2").setAttribute("checked", "");
                document.getElementById("drug1").removeAttribute("checked");
                drug_no+=1;
            }
            break;
    }
}

function medication_Click_lunch(div) {
    switch (div.id) {
        case "check_medi2":
            if (document.getElementById('check_medi2').className === "rdo-box") {
                document.getElementById('check_medi2').className = "rdo-box checked";
                document.getElementById('uncheck_medi2').className = "rdo-box";
                document.getElementById("drug3").setAttribute("checked", "");
                document.getElementById("drug4").removeAttribute("checked");
                drug_yes+=1;
            }
            break;
        case "uncheck_medi2":
            if (document.getElementById('uncheck_medi2').className === "rdo-box") {
                document.getElementById('uncheck_medi2').className = "rdo-box checked2";
                document.getElementById('check_medi2').className = "rdo-box";
                document.getElementById("drug4").setAttribute("checked", "");
                document.getElementById("drug3").removeAttribute("checked");
                drug_no+=1;

            }
            break;
    }
}
function medication_Click_dinner(div) {
    switch (div.id) {
        case "check_medi3":
            if (document.getElementById('check_medi3').className === "rdo-box") {
                document.getElementById('check_medi3').className = "rdo-box checked";
                document.getElementById('uncheck_medi3').className = "rdo-box";
                document.getElementById("drug5").setAttribute("checked", "");
                document.getElementById("drug6").removeAttribute("checked");
                drug_yes+=1;
            }
            break;
        case "uncheck_medi3":
            if (document.getElementById('uncheck_medi3').className === "rdo-box") {
                document.getElementById('uncheck_medi3').className = "rdo-box checked2";
                document.getElementById('check_medi3').className = "rdo-box";
                document.getElementById("drug6").setAttribute("checked", "");
                document.getElementById("drug5").removeAttribute("checked");
                drug_no+=1;

            }
            break;
    }
}



function submit_drug() {
    if (sum_drug==drug_yes) {
    // 팝업창 " 오늘도 수고하셨어요! 내일도 잊지 말고 약 챙겨드세요!"
    }
    else if(sum_drug > drug_yes) {
        // "
    }



}


