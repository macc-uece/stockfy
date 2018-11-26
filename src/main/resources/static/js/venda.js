$(document).ready(function () {

    var vendas = [];

    $('#autocomplete').autocomplete({
        paramName: 'referencia',
        serviceUrl: '/api/venda/getProdutos',
        minChars: 2,
        autoSelectFirst: true,
        triggerSelectOnValidInput: false,
        onSelect: function (suggestion) {
            $('#autocomplete').val(suggestion.value);
            $('.update-sub-produto').val(suggestion.value);
            $('#precoProduto').val(suggestion.data);
            $('.update-sub-produto').trigger("change");
            $('#quantidadeProduto').trigger("keyup");
        }
    });

    $("#subProduto").remoteChained({
        parents: ".update-sub-produto",
        url: "/api/venda/subProdutos"
    });

    $("#subProduto").on('change', function () {
        $('#quantidadeProduto').val(1).trigger("keyup");
    });

    $("#quantidadeProduto").on('keyup', function () {
        $('#valorTotal').text(this.value * $('#precoProduto').val());
    });

    $("#AddProdutoVenda").on('click', function (e) {
        e.preventDefault();

        var ref = $("#autocomplete").val();
        var subproduto = $("#subProduto").val();
        var quantidade = $("#quantidadeProduto").val();
        var total = $("#valorTotal").text();
        var data = $("#datepicker").val();
        var estabelecimento = $("#estabelecimento").val();

        /*Validate*/
        if (subproduto == 0) {
            $("#subProduto").focus();
            return;
        } else if (subproduto != 0 && quantidade <= 0) {
            $("#quantidadeProduto").focus();
            return;
        }
        if (data === '') {
            $("#datepicker").focus();
            return;
        }
        if (estabelecimento == 0) {
            $("#estabelecimento").focus();
            return;
        }
        /*Inserção*/
        var json = {
            "subproduto": subproduto,
            "quantidade": quantidade,
            "total": total,
            "data": data,
            "estabelecimento": estabelecimento
        };

        var jaAdicionado = false;
        var tabela = $("#tableVenda");
        var delData = "del-data='" + ref + "-" + subproduto + "-" + data + "-" + estabelecimento + "'";
        var delecao = "<a " + delData + " subproduto='" + subproduto + "' data='" + data + "' estabelecimento='" + estabelecimento + "' class='delVenda btn btn-sm btn-outline-danger'><i class='fa fa-trash-o'></i></a>";

        $.each(vendas, function () {
            if (this.subproduto == subproduto && this.data == data && this.estabelecimento == estabelecimento) {
                jaAdicionado = true;
                this.quantidade = parseInt(this.quantidade) + parseInt(quantidade);
                this.total = parseInt(this.total) + parseInt(total);
                $("a[" + delData + "]").closest("tr").remove();
                tabela.prepend("<tr><td>" + ref + "</td><td>" + $("#subProduto option:selected").text() + "</td><td>" + this.quantidade + "</td><td>" + data + "</td><td>" + $('#estabelecimento option:selected').text() + "</td><td>" + this.total + "</td><td>" + delecao + "</td></tr>");
            }
        });
        if (!jaAdicionado) {
            vendas.push(json);
            tabela.prepend("<tr><td>" + ref + "</td><td>" + $("#subProduto option:selected").text() + "</td><td>" + quantidade + "</td><td>" + data + "</td><td>" + $('#estabelecimento option:selected').text() + "</td><td>" + total + "</td><td>" + delecao + "</td></tr>");
        }

        $("#subTotal").text((parseInt($("#subTotal").text()) + parseInt(total)));
        $("#subProduto").val("0").change();
        $("#quantidadeProduto").val(1).keyup();
    });

    $(document).on('click', '.delVenda', function (e) {
        e.preventDefault();
        var venda = $(this).attr("del-data");
        var subproduto = $(this).attr("subproduto");
        var data = $(this).attr("data");
        var estabelecimento = $(this).attr("estabelecimento");

        vendas = $.grep(vendas, function (venda, idx) {
            if(venda.subproduto == subproduto && venda.data == data && venda.estabelecimento == estabelecimento){
                $("#subTotal").text((parseInt($("#subTotal").text()) - venda.total));
                return true;
            }else{
                return false;
            }
        }, true);
        $("a[del-data='" + venda + "']").closest("tr").remove();
    });

    /*Salvar*/
    $("#salvarVendas").on('click', function (e) {
       e.preventDefault();
       if(vendas.length > 0) {
           $.ajax({
               type: "POST",
               beforeSend: function (xhr) {
                   xhr.withCredentials = true;
               },
               contentType: "application/json; charset=utf-8",
               url: "/admin/vendas/new/venda",
               data: JSON.stringify(vendas),
               dataType: 'json',
               cache: false,
               timeout: 0,
               success: function (xhr) {
                   if (xhr === 200) {
                       window.location.href = '/admin/vendas';
                   }
               }
           });
       } else {
           $("#autocomplete").focus();
       }
    });
});