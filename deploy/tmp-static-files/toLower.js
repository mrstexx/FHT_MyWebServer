$(() => {
    const convertButton = $("#btnConvert");
    const textArea = $("#text1");
    const preArea = $("#pre-text");

    convertButton.click(() => {
        handleRequest(textArea.val());
    });

    const handleRequest = (value) => {
        $.ajax({
            url: encodeURI("/tolower?tolower_plugin=true"),
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: {
                value: value
            },
            success: (response) => {
                preArea.empty().append(response);
            }
        });
    };
});