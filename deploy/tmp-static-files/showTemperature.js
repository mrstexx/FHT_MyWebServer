const tableBody = $("#tableBody")[0];
const nextBtn = $("#next");
const prevBtn = $("#prev");

let pageNumber = 1;

$(() => {
    handleAction();
});

nextBtn.on('click', () => {
    pageNumber++;
    handleAction();
});

prevBtn.on('click', () => {
    pageNumber--;
    if (pageNumber < 1) {
        pageNumber = 1;
    }
    handleAction();
});

const handleAction = () => {
    $.ajax({
        url: "/temperature?temperature_plugin=true&page=" + pageNumber,
        type: "GET",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        success: (response) => {
            if (response != null && response.result) {
                renderTable(response.result);
            }
        }
    });
};

const renderTable = (result) => {
    $(tableBody).empty();
    let tableContent = "";
    for (let i = 0; i < result.length; i++) {
        tableContent += '<tr><td>' + result[i].date + '</td><td>' + result[i].value + '</td></tr>';
    }
    $(tableBody).prepend(tableContent);
};