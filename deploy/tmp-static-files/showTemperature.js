const RECORDS_PER_PAGE = 20;

const tableBody = $("#tableBody")[0];
const nextBtn = $("#next");
const prevBtn = $("#prev");

const datePickerForm = $("#searchByDateForm");
const datePicker = $("#datePicker");

let pageNumber = 1;
let numberOfRecords = 0;

$(() => {
    /* on page opening, render data from first page */
    handleAction("/temperature?temperature_plugin=true&page=" + pageNumber);
});

nextBtn.on('click', () => {
    const maxNumberOfPages = Math.ceil(numberOfRecords / RECORDS_PER_PAGE);
    pageNumber++;
    if (pageNumber <= maxNumberOfPages) {
        handleAction("/temperature?temperature_plugin=true&page=" + pageNumber);
    }
});

prevBtn.on('click', () => {
    pageNumber--;
    if (pageNumber >= 1) {
        handleAction("/temperature?temperature_plugin=true&page=" + pageNumber);
    }
    if (pageNumber < 1) {
        pageNumber = 1;
    }
});

const handleAction = (getUrl) => {
    $.ajax({
        url: getUrl,
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

datePickerForm.on('submit', (e) => {
    e.preventDefault();
    const date = datePicker.val();
    if (date.length > 0) {
        handleAction("/temperature?temperature_plugin=true&date=" + date);
    }
    pageNumber = 0;
});