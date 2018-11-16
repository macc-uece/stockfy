$("#file").on("change", loadImg);

function loadImg(e) {
    var reader = new FileReader();
    reader.onload = function (event) {
        $('.uploader img').attr('src',event.target.result);
    };
    reader.readAsDataURL(e.target.files[0]);
}