const RECORDS_PER_PAGE = 20;

const tableBody = $("#tableBody")[0];
const nextBtn = $("#next");
const prevBtn = $("#prev");

let pageNumber = 1;
let numberOfRecords = 0;

$(() => {
    handleAction();
});

nextBtn.on('click', () => {
    const maxNumberOfPages = Math.ceil(numberOfRecords / RECORDS_PER_PAGE);
    pageNumber++;
    if (pageNumber <= maxNumberOfPages) {
        handleAction();
    }
});

prevBtn.on('click', () => {
    pageNumber--;
    if (pageNumber >= 1) {
        handleAction();
    }
    if (pageNumber < 1) {
        pageNumber = 1;
    }
});

const handleAction = () => {
    $.ajax({
        url: "/temperature?temperature_plugin=true&page=" + pageNumber,
        type: "GET",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        success: (response) => {
            if (response != null) {
                if (response.result) {
                    renderTable(response.result);
                }
                if (response.recordsNumber) {
                    numberOfRecords = response.recordsNumber;
                }
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