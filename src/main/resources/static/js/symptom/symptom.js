
function popupOpen(){
    $("#popup").show();
    $("#dim").show();
}


function onFormSubmission(form) {
    if(form.querySelector('input[name=breath]:checked')==null ||
        form.querySelector('input[name=tired]:checked')==null ||
        form.querySelector('input[name=ankle]:checked')==null ||
        form.querySelector('input[name=cough]:checked')==null )
    {
        popupOpen();
        $("#okButton").click(function (){
            $("#popup").hide();
            $("#dim").hide();
            return false;
        });
    }
    else{
        breath = form.querySelector('input[name=breath]:checked').value;
        tired = form.querySelector('input[name=tired]:checked').value;
        ankle = form.querySelector('input[name=ankle]:checked').value;
        cough = form.querySelector('input[name=cough]:checked').value;

        const sum = parseInt(breath)+parseInt(tired)+parseInt(ankle)+parseInt(cough);
        const body = {
            outofbreath:breath,
            tired:tired,
            ankle:ankle,
            cough:cough,
            todaysymptom:sum
        }

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

        // fetch(url, options)
        //     .then(response => response.json())
        //     .then(data => console.log(data))
        //     .then(data => alert("입력이 완료되었습니다."));
    }

    return false;
}

function showHistory() {
    const element = document.querySelector("#symptom-history-get-action");
    location.href = element.value;
}
