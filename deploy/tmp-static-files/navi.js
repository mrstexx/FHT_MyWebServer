const naviSearchInputField = $("#naviSearchInput");
const naviResult = $(".result");
const naviSearchForm = $("#naviSearch");

naviSearchForm.on("submit", (e) => {
    e.preventDefault();
    $.ajax({
        url: encodeURI("/navigation?navigation_plugin=true"),
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: {
            street: naviSearchInputField.val()
        },
        success: (response) => {
            naviResult.empty().append(response);
        }
    });
});