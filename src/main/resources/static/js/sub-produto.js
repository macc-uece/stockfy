function addSubProduto() {
    var idProduto = {idProduto: $(".idProduto").val()};
    $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/admin/produtos/subProduto/add",
        data:idProduto,
        cache: false,
        timeout: 0,
        success: function (data) {
            $("#modalSubProdutoContent").html(data);
            $("#modalSubProduto").modal("show");
        }
    });
}
function editSubProduto(id) {
    var idProduto = {idProduto: $(".idProduto").val()};
    $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        url: "/admin/produtos/subProduto/"+id,
        data:idProduto,
        cache: false,
        timeout: 0,
        success: function (data) {
            $("#modalSubProdutoContent").html(data);
            $("#modalSubProduto").modal("show");
        }
    });
}