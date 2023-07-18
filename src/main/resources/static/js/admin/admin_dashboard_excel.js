
    // ArrayBuffer 만들어주는 함수
function s2ab(s) {
    var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
    var view = new Uint8Array(buf);  //create uint8array as viewer
    for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
    return buf;
}
function execel(xlist, ylist){

    var wb = XLSX.utils.book_new();

    wb.SheetNames.push("대상자 등록 연황");
    wb.SheetNames.push("주차별 대상자 수");
    wb.SheetNames.push("성별");
    wb.SheetNames.push("연령");
    wb.SheetNames.push("환자와의 관계");
    wb.SheetNames.push("누적 버튼 클릭 수");

    var wsData1 = [['주차' , '다이애드']];
    for( var i = 1; i<xlist[0].length; i++){
        wsData1.push([xlist[0][i],ylist[0][i]])
    }
    var wsData2 = [['주차','다이애드']];
    for(var i = 1; i<xlist[1].length -1; i++){
        wsData2.push([xlist[1][i],ylist[1][i]])
    }
    var wsData3 = [['회원 유형', '성별', '회원 수']];
    for(var i = 0; i<xlist[2].length; i++){
        wsData3.push(['환자',xlist[2][i],ylist[2][i]])
    }
    for(var i = 0; i<xlist[3].length; i++){
        wsData3.push(['가족',xlist[3][i],ylist[3][i]])
    }

    var wsData4 = [['회원 유형','나이', '회원 수']];
    for(var i = 0; i<xlist[4].length;i++){
        if(i % 2 == 1) {
            wsData4.push(['환자',xlist[4][i],ylist[4][i]])
        }
    }
    for(var i = 0; i<xlist[4].length;i++){
        if(i % 2 == 0) {
            wsData4.push(['가족',xlist[4][i],ylist[4][i]])
        }
    }
    var wsData5 = [['환자와의 관계','회원 수']];
    for(var i = 0; i< xlist[5].length;i++){
        wsData5.push([xlist[5][i], ylist[5][i]])
    }
    var wsData6 = [['회원 유형', '메인 항목', '버튼 클릭 수']];
    for(var i = 0; i<xlist[6].length; i++){
        wsData6.push(['환자',xlist[6][i],ylist[6][i]])
    }
    for(var i = 0; i<xlist[6].length; i++){
        wsData6.push(['가족',xlist[6][i],ylist[7][i]])
    }


    var ws1 = XLSX.utils.aoa_to_sheet(wsData1);
    var ws2 = XLSX.utils.aoa_to_sheet(wsData2);
    var ws3 = XLSX.utils.aoa_to_sheet(wsData3);
    var ws4 = XLSX.utils.aoa_to_sheet(wsData4);
    var ws5 = XLSX.utils.aoa_to_sheet(wsData5);
    var ws6 = XLSX.utils.aoa_to_sheet(wsData6);



    wb.Sheets["대상자 등록 연황"] = ws1;
    wb.Sheets["주차별 대상자 수"] = ws2;
    wb.Sheets["성별"] = ws3;
    wb.Sheets["연령"] = ws4;
    wb.Sheets["환자와의 관계"] = ws5;
    wb.Sheets["누적 버튼 클릭 수"] = ws6;


    var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

    saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), '대시보드.xlsx');
}
function fnExcelReport(){
    var ageName = ['49세 이하','49세 이하','50~59세','50~59세','60~69세','60~69세', '70세 이상', '70세 이상'];
    var xlist = [signXList, dyadsXList, patientGenderXList, caregiverGenderXList, ageName, relationshipXList, buttonClickXList ];
    var ylist = [signYList,dyadsYList, patientGenderYList, caregiverGenderYList, ageDataList, relationshipYList, patientButtonClickList, caregiverButtonClickList];
    execel(xlist ,ylist );
}