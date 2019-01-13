function loadTripSections()
{
    var action_src = $("keywords").val();
    var your_form = $('search_form').val();

    var urlLink = "http://localhost:8080/employees/";
    urlLink = urlLink + action_src;

    your_form.action = urlLink;
}