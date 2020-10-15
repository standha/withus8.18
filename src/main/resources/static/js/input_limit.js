function maxLengthCheck(object){
    if (object.value.length > object.maxLength){
        //object.maxLength : 매게변수 오브젝트의 maxlength 속성 값입니다.
        object.value = object.value.slice(0, object.maxLength);
    }
}