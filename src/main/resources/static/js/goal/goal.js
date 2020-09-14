function radioClick(){
    var checklength = document.getElementsByName("check_radio").length;
    var checked;
    var checkedArray = new Array();

    for(var i=0; i<checklength; i++){
        if(document.getElementsByName("check_radio")[i].checked == true){
            //alert(document.getElementsByName("check_radio")[i].value);
            checked = document.getElementsByName("check_radio")[i].value;
            //console.log(checked);
            checkedArray[i] = document.getElementsByName("check_radio")[i].value;
        }
    }


}