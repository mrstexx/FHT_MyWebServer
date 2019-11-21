const naviSearchInputField = $("#naviSearchInput");
const naviResult = $(".result");
const naviSearchForm = $("#naviSearch");
const loadNewMapButton = $("#loadNewMap");

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

loadNewMapButton.on("click", (e) => {
    e.preventDefault();
    loadNewMapButton.empty().append("" +
        "<span class=\"spinner-border spinner-border-sm\" role=\"status\" aria-hidden=\"true\"></span>\n" +
        "  Loading...");
    loadNewMapButton.prop("disabled", true);
    $.ajax({
        url: encodeURI("/navigation?navigation_plugin=true&load_map=true"),
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        success: () => {
            alert("Data successfully loaded");
            loadNewMapButton.empty().append("Load new map");
            loadNewMapButton.prop("disabled", false);
        },
        error: () => {
            alert("An error occurred");
            loadNewMapButton.empty().append("Load new map");
            loadNewMapButton.prop("disabled", false);
        }
    });
});