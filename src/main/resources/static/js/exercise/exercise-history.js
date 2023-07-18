/*<![CDATA[*/
var exerciseHistory =/*[[ ${exercise} ]]*/;
/*]]*/

$.datepicker._generateHTML = (function () {
    var realGenerateHtml = $.datepicker._generateHTML;
    return function (instance) {
        var html = realGenerateHtml.apply(this, arguments), $temp;

        if (instance.input.is(".exercise")) {
            $temp = $("<table></table>").append(html);
            $temp.find(".ui-datepicker-calendar td a").each(function () {
                var yy = $(this).parent().data("year"),
                    mm = $(this).parent().data("month"),
                    dd = +$(this).text();

                var monthStr, dayStr;
                monthStr = (mm + 1).toString();
                if (monthStr.length == 1) {
                    monthStr = "0" + monthStr;
                }
                dayStr = dd.toString();
                if (dayStr.length == 1) {
                    dayStr = "0" + dayStr;
                }

                var str = yy.toString() + "-" + monthStr + "-" + dayStr;

                for (i = 0; i < exerciseHistory.length; i++) {
                    if (str == exerciseHistory[i].pk.date) {
                        var activityDropdownValue = $("#activityDropdown").val();
                        var className = "";
                        switch (activityDropdownValue) {
                            case "walking":
                                className = "walk-icon";
                                break;
                            case "cycling":
                                className = "cycle-icon";
                                break;
                            case "swimming":
                                className = "swim-icon";
                                break;
                            case "strengthTraining":
                                className = "strength-icon";
                                break;
                        }
                        $(this).addClass(className);
                    }
                }
            });
            html = $temp[0].innerHTML;
        }
        return html;
    };
})();
//
// $( function() {
//     $( "#dateRecord" ).datepicker();
$(function () {
    $("#dateRecord").datepicker();
    $("#activityDropdown").change(function () {
        $(".ui-datepicker-calendar td a").removeClass().addClass("record-exercise");
    });
});
