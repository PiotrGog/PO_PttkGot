function reloadPage(){

    var action_src = $("keywords").val();
    var your_form = $('search_form').val();

    var urlLink = "http://localhost:8080/employees/";
    urlLink = urlLink + action_src;

    your_form.action = urlLink;
}

function reloadReportPage(){

    var action_src = $("trip").val();
    var your_form = $('search_form').val();

    var urlLink = "http://localhost:8080/report/";
    urlLink = urlLink;

    your_form.action = urlLink;
}