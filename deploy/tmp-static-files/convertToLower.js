$(() => {
    const convertButton = $("#btnConvert");
    const textArea = $("#text1");
    const preArea = $("#pre-text");

    convertButton.click(() => {
        handleRequest(textArea.val());
    });

    const handleRequest = (value) => {
        $.ajax({
            url: "/tolower?tolower_plugin=true",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            transformRequest: (obj) => {
                let str = [];
                for (let p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            data: {
                value: value
            },
            success: (response) => {
                preArea.empty().append(response);
            }
        })
    };
});