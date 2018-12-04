$(document).ready(function () {

    var options = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    };

    $.ajax({
        type: "GET",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/api/home/venda-mes",
        cache: false,
        timeout: 0,
        complete: function (data) {
            var ctx = $("#venda-mes-atual");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: data,
                options: options
            });
        }
    });
    $.ajax({
        type: "GET",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/api/home/despesa-mes",
        cache: false,
        timeout: 0,
        complete: function (data) {
            var ctx = $("#despesa-mes-atual");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: data,
                options: options
            });
        }
    });
    $.ajax({
        type: "GET",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/api/home/venda-despesa",
        cache: false,
        timeout: 0,
        complete: function (data) {
            var ctx = $("#venda-x-despesa");
            var myChart = new Chart(ctx, {
                type: 'line',
                data: data,
                options: options
            });
        }
    });
    $.ajax({
        type: "GET",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/api/home/produto-estabelecimento",
        cache: false,
        timeout: 0,
        complete: function (data) {
            var ctx = $("#produtos-estabelecimento");
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: data,
                options: options
            });
        }
    });
    $.ajax({
        type: "GET",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/api/home/categoria-venda",
        cache: false,
        timeout: 0,
        complete: function (data) {
            var ctx = $("#categoria-vendas");
            var myChart = new Chart(ctx, {
                type: 'doughnut',
                data: data
            });
        }
    });
});