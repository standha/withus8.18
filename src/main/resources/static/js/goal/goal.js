function radioClick() {
    var checkButtons = document.getElementsByName("check_radio");
    var checked=[];
    var checkedTop = [];
    var checkedMiddle = [];
    var checkedBottom = [];

    function updateCheckedArray(category, value) {
        if (category === "top") {
            if (!checkedTop.includes(value) && checkedTop.length < 3) {
                checkedTop.push(value);
                checked.push(value);
            }
        } else if (category === "middle") {
            if (!checkedMiddle.includes(value) && checkedMiddle.length < 3) {
                checkedMiddle.push(value);
                checked.push(value);
            }
        } else if (category === "bottom") {
            if ( checkedBottom.length < 3) {
                //console.log("a")
                checkedBottom.push(value);
                checked.push(value);
            }
        }
    }

    function deselectButton(button) {
        button.checked = false;
        button.parentNode.parentNode.classList.remove("checked");
        var category = button.getAttribute("data-category");
        //console.log("7")
        var value = button.value;

        if (category === "top") {
            var index = checkedTop.indexOf(value);
            if (index > -1) {
                checkedTop.splice(index, 1);
                checked.splice(index, 1);
            }
        } else if (category === "middle") {
            var index = checkedMiddle.indexOf(value);
            if (index > -1) {
                checkedMiddle.splice(index, 1);
                checked.splice(index, 1);
            }
        } else if (category === "bottom") {
            var index = checkedBottom.indexOf(value);
            // console.log("6")
            if (index > -1) {
                checkedBottom.splice(index, 1);
                checked.splice(index, 1);
            }
        }
    }

    function handleButtonClick() {

        //console.log("b")
        var category = this.getAttribute("data-category");
        // console.log("5",category)
        var value = this.value;




        //기존에 선택되어 있는 것
        var buttons = document.querySelectorAll(".rdo-box.checked");
        buttons.forEach(function(button) {
            var value0 = button.querySelector('input[name="check_radio"]').value;;
            var category0 = button.querySelector('input[name="check_radio"]').getAttribute("data-category");;
            if (button.checked) {
                updateCheckedArray(category0, value0);
            }
            console.log("c", value0);
            console.log("c", category0);
        });









        if (this.checked) {
            updateCheckedArray(category, value);
            //  console.log("c",value)
            //   console.log("c",category)
            this.parentNode.parentNode.classList.add("checked");
        } else {
            deselectButton(this);
            //  console.log("d")
        }

        var topDisabled = checkedTop.length >= 3;
        var middleDisabled = checkedMiddle.length >= 3;
        var bottomDisabled = checkedBottom.length >= 3;

        for (var j = 0; j < checkButtons.length; j++) {
            var button = checkButtons[j];
            var buttonCategory = button.getAttribute("data-category");

            if (buttonCategory === "top") {
                button.disabled = topDisabled;
            } else if (buttonCategory === "middle") {
                button.disabled = middleDisabled;
            } else if (buttonCategory === "bottom") {
                button.disabled = bottomDisabled;
            }
        }

        var totalSelected = checkedTop.length + checkedMiddle.length + checkedBottom.length;

        if (totalSelected >= 3) {
            for (var j = 0; j < checkButtons.length; j++) {
                if (!checkButtons[j].checked) {
                    checkButtons[j].disabled = true;
                }
            }
        } else {
            for (var j = 0; j < checkButtons.length; j++) {
                checkButtons[j].disabled = false;
            }
        }

        console.log("Selected Top Goals:", checkedTop);
        console.log("Selected Middle Goals:", checkedMiddle);
        console.log("Selected Bottom Goals:", checkedBottom);
    }

    for (var i = 0; i < checkButtons.length; i++) {
        checkButtons[i].addEventListener("change", handleButtonClick);
    }

    // 배열 초기화
    checked = [];
}

radioClick();






function onFormSubmission(button) {

    var Item2;
    var checkedTop = [];
    var checkedMiddle = [];
    var checkedBottom = [];

    var checked = Array.from(document.querySelectorAll("input[name=check_radio]:checked"))
        .map((button) => button.value);
    console.log("1",checked)



    var selectedButtons = document.querySelectorAll(".rdo-box.checked");

    selectedButtons.forEach(function(button) {
        var value = button.querySelector('input[name="check_radio"]').value;
        console.log("2",value);
        var category = button.querySelector('input[name="check_radio"]').getAttribute("data-category");
        console.log("3",category);

        if (category === "top" && checkedTop.length < 3 && !checkedTop.includes(value)) {
            checkedTop.push(value);
            //checked.push(value);
        } else if (category === "middle" && checkedMiddle.length < 3 && !checkedMiddle.includes(value)) {
            checkedMiddle.push(value);
            //checked.push(value);
        } else if (category === "bottom" && checkedBottom.length < 3 && !checkedBottom.includes(value)) {
            checkedBottom.push(value);
            //checked.push(value);
            console.log("4",checkedBottom);
        }
        checkedarray(category)

    });


    $("#layerSelectType").show();
    $("#dim").show();
    //Item[i] = "<span class=\"bold\">'" + checked[i]  + "'</span>";
    Item = "<span> 목표를 설정하셨군요.</span>";
    $('#popUp1').append(Item);


    function checkedarray(category){
        checked=[];  //배열초기화
        if (category === "top"){
            for (var i = 0; i < checkedTop.length; i++) {
                checked.push(checkedTop[i]);
            }
        }else if(category === "middle"){
            for (var i = 0; i < checkedMiddle.length; i++) {
                checked.push(checkedMiddle[i]);
            }
        }else if (category === "bottom"){
            for (var i = 0; i < checkedBottom.length; i++) {
                checked.push(checkedBottom[i]);
                console.log("5",checked)
            }
        }
    }

    for (var i = 0; i < checked.length; i++) {
        Item2 = "<span class=\"bold\">'" + checked[i] + "'</span> <br>";
        $('#popUp2').append(Item2);
        console.log("6",Item2);
    }


    $("#buttonOk").click(function(){

        const topGoals = checkedTop.join(","); // 배열을 쉼표로 구분된 문자열로 변환
        const middleGoals = checkedMiddle.join(",");
        const bottomGoals = checkedBottom.join(",");

        const body = {
            goal: '2',
            top_goals: topGoals,
            middle_goals: middleGoals,
            bottom_goals: bottomGoals
        };

        const url = "";
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