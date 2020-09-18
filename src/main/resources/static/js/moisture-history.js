$.datepicker._generateHTML = (function () {
    var realGenerateHtml = $.datepicker._generateHTML;
    return function (instance) {
        var html = realGenerateHtml.apply(this, arguments), $temp;

        if ( instance.input.is(".water") ) {
            $temp = $("<table></table>").append(html);
            $temp.find(".ui-datepicker-calendar td a").each(function () {
                var yy = $(this).parent().data("year"),
                    mm = $(this).parent().data("month"),
                    dd = +$(this).text();
                $(this).addClass('record-water')
            });

            html = $temp[0].innerHTML;
        }
        return html;
    };
})();

$( function() {
    $( "#dateRecord" ).datepicker();
} );