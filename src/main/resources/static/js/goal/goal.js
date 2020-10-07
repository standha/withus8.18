function radioClick(){
    var checklength = document.getElementsByName("check_radio").length;
    var checked;
    var goalNum;
    var Item, Item2;
    for(var i=0; i<checklength; i++){
        if(document.getElementsByName("check_radio")[i].checked == true){
            checked = document.getElementsByName("check_radio")[i].value;
            goalNum = i+1;
        }
    }

    switch(goalNum){
        case 1:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>매일 심장약을 복용하신 후<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "알람" +  "<span>" + " > " + "</span>" + "약물" + "</span>" + "에 기록해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 2:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>매일 정해진 시간에 측정한 뒤<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "혈압/맥박" + "</span>" + "</span>" + "에 기록해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 3:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>매일 정해진 시간에 측정한 뒤<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "체중" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 4:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>하루동안 증상이 있었는지 생각하며<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "증상일지" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 5:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>하루동안 증상이 있었는지 생각하며<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "증상일지" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 6:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>식사 시 염도계로 음식의 소금양을 측정하고<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "염분/수분" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 7:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>매일 식사 시 염도계로 음식의 소금양을 측정하고<br></span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "염분/수분" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 8:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>운동한 후</span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "운동" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

        case 9:
            $("#layerSelectType").show();
            $("#dim").show();
            Item = "<span class=\"bold\">'" + checked  + "'</span>";
            Item += "<span>으로 목표를 설정하셨군요.</span>";
            $('#popUp1').append(Item);
            Item2 = "<span>운동한 후</span>";
            Item2 += "<span class=\"route\">" + "위더스" + "<span>" + " > " + "</span>" + "운동" + "</span>" + "</span>" + "에 입력해주세요.";
            $('#popUp2').append(Item2);
            break;

    }

    $("#buttonOk").click(function(){
        const body = {
            goal: goalNum
        };

        const url = '';
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
            .then(data =>console.log(data));

        setTimeout(function (){
            location.href='center';
        }, 300);
        return false;
    });

    $("#buttonNo").click(function(){
        $("#layerSelectType").hide();
        $("#dim").hide();
        window.location.reload();
        return false;
    });

    return false;
}
